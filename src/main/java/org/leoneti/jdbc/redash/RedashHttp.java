/*****************************************************************************************
* Copyright (C) 2023-2023  Ricardo Leoneti                           Date: 2023-01-15
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
*
* Contributors:
*     Ricardo Leoneti <ricardo.leoneti@gmail.com>    - initial API and implementation
* 
*****************************************************************************************/
package org.leoneti.jdbc.redash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import org.leoneti.TraceLog;

public class RedashHttp extends TraceLog {
    
    private static SSLSocketFactory SSL_FACTORY;
    public static String DEFAULT_USERAGENT = String.format("JDBCDriver/%2$s (%1$s; x%3$s) RedashDriver/%4$s"
            , System.getProperty("os.name"), System.getProperty("java.version"), System.getProperty("sun.arch.data.model"), RedashHttp.class.getPackage().getImplementationVersion() );

    private String host;
    private String token;
    private boolean ssl;
    
    private String userAgent = DEFAULT_USERAGENT;
    
    public RedashHttp(boolean trace, String host, String token, boolean ssl) {
        super(trace, RedashHttp.class);
        this.host = host;
        this.token = token;
        this.ssl = ssl;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public RedashHttp copy() {
        return new RedashHttp(isTraced(), this.host, this.token, this.ssl);
    }

    public StringBuffer get( String apipath, Object... args ) throws SQLException {
        return request("GET", apipath, null, args );
    }

    public StringBuffer post( String apipath, String data, Object... args ) throws SQLException {
        return request("POST", apipath, data, args );
    }

    public StringBuffer request(String method, String apipath, String data, Object... args) throws SQLException {
        if(isTraced()) logMethod("request", method, apipath, data, String.format( "[%s]",String.join(",",Arrays.stream(args).map(Object::toString).toArray(String[]::new)) ) );
    	HttpURLConnection con = null;
        try {
        	apipath = String.format(apipath, args);
            URL requrl = new URL(String.format("http%s://%s%s",(ssl?"s":""), this.host, apipath));
            if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, String.format("Request %5s %s '%s'", method, requrl.toString(), data) );
            con = (HttpURLConnection) requrl.openConnection();
            if (con instanceof HttpsURLConnection) {
                ((HttpsURLConnection) con).setSSLSocketFactory(SSL_FACTORY);
            }
            con.setRequestProperty("User-Agent", this.userAgent);
            con.setRequestProperty("Authorization", String.format("Key %s", token));
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod(method);
            if( data != null ) {
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                byte[] input = data.getBytes("utf-8");
                con.getOutputStream().write(input, 0, input.length);
            }
            int responseCode = con.getResponseCode();
            if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, "{0} Response Code : {1} \t ContentLength : {2}" , new Object[] {method, responseCode, con.getContentLength()} );
            StringBuffer response = new StringBuffer();
            BufferedReader in = new BufferedReader( new InputStreamReader(responseCode < HttpURLConnection.HTTP_BAD_REQUEST ? con.getInputStream() : con.getErrorStream() ) );
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //System.out.println( response );
            if( responseCode == HttpURLConnection.HTTP_OK )
                return response;
            if( responseCode == HttpURLConnection.HTTP_MOVED_PERM )
                throw new RedashException( "ERROR: " + response );
            if( response.length() > 0 ) {
                Logger.getLogger("redash.jdbc").log(Level.SEVERE, "ERROR: REPONSE: " + response );
                final JSONObject jo = new JSONObject(response.toString());
                throw new SQLException(String.valueOf(jo.get("message")), "HTTP " + responseCode, responseCode);
            } else
            	throw new SQLException( String.format( "%s request did not work(%d).\nToken:%s\nURL:%s\ndata:%s\nContentLength:%d",method,responseCode,token,requrl.toString(),data,con.getContentLength()) );
        } catch (IOException e) {
            Logger.getLogger("redash.jdbc").log(Level.SEVERE, e.getMessage(), e);
            throw new SQLException(e);
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception e) {}
            }
        }
    }


    public static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
        public X509Certificate[] getAcceptedIssuers() { return null; }
    }

    static {
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
            SSL_FACTORY = sc.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    
    }
}
