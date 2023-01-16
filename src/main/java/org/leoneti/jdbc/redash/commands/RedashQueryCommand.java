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
package org.leoneti.jdbc.redash.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.leoneti.jdbc.iterable.MapResultSet;
import org.leoneti.jdbc.redash.RedashConnection;
import org.leoneti.jdbc.redash.RedashConstants;
import org.leoneti.jdbc.redash.RedashDatabaseMetaData;
import org.leoneti.jdbc.redash.RedashException;

public class RedashQueryCommand implements Cloneable {
    
    private RedashConnection con;
    private JSONArray rows = new JSONArray();
    private int queryId = -1;
    private long queryVersion = 0;
    private Object latest_query_data_id;
    private boolean cancelExecution = false;

    public RedashQueryCommand(RedashConnection con) {
        this.con = con;
    }
    
    protected boolean isTraced() {
        return con.isTraced();
    }

    public RedashQueryCommand clone() {
        final RedashQueryCommand qCommand = new RedashQueryCommand(this.con);
        qCommand.queryId = this.queryId;
        qCommand.latest_query_data_id = this.latest_query_data_id;
        return qCommand;
    }

    public int getQueryId() { return queryId; }

    
    public JSONArray getRows(boolean load) throws SQLException {
        if (load) {
            JSONArray newRows = new JSONArray();
            int page = 1;
            while (true) {
                // TODO /api/queries/my  /api/queries/archive    /api/queries/favorites
                StringBuffer response = this.con.getRedashHttp().get("/api/queries?page_size=%d&page=%d", con.pageSize(), page);
                JSONObject jo = new JSONObject(response.toString());
                if (jo.has("message")) throw new RedashException(jo);
                for (Object obj : jo.getJSONArray("results"))
                    newRows.put(obj);
                if (jo.getInt(RedashConstants.HTTP_PARAM_COUNT) <= jo.getInt(RedashConstants.HTTP_PARAM_PAGE) * jo.getInt(RedashConstants.HTTP_PARAM_PAGE_SIZE)) 
                    break;
                page++;
            }
            synchronized (this) {
                this.rows = newRows;
            }
        }
        return rows;
    }
    
    public void identifyOrCreateQueryId() throws SQLException {
        final String queryName = con.getQueryName();
        final int userId = ((RedashDatabaseMetaData) con.getMetaData()).getUserId();
        for (Object obj : this.getRows(true)) {
            JSONObject jo = (JSONObject) obj;
            if (jo.getJSONObject("user").getInt("id") == userId && jo.getString("name").equalsIgnoreCase(queryName)) {
                this.queryId = jo.getInt("id");
                this.queryVersion = jo.getLong("version");
                return;
            }
        }
        createQuery(queryName);
    }
    
    public ResultSet get(boolean admin, boolean all) throws SQLException {
    	JSONArray rows = getRows(true);
        final ArrayList<Map<String,Object>> rsrows = new ArrayList<Map<String,Object>>( rows.length() );
        for(Object obj : rows ) {
            JSONObject jo = (JSONObject) obj;
            final String userName = jo.getJSONObject("user").getString("email");
            final int data_source_id = jo.getInt("data_source_id");
            if (!admin && data_source_id != con.getDsId()) continue;
            // System.out.println( userName + "\t" + all + "\t" + jo.toString() );
            if (!admin && !all && !con.getMetaData().getUserName().equalsIgnoreCase(userName)) continue;
            Map<String,Object> sc = new LinkedHashMap<>();
            sc.put("id", jo.getInt("id"));
            sc.put("query_name", jo.getString("name"));
            sc.put("query", jo.has("query") ? jo.getString("query").trim() : null );
            sc.put("userId", jo.getJSONObject("user").getInt("id"));
            sc.put("userName", userName);
            sc.put("latest_query_data_id", jo.isNull("latest_query_data_id") ? null : jo.getInt("latest_query_data_id"));
            sc.put("runtime", jo.isNull("runtime") ? null : jo.getDouble("runtime"));
            sc.put("updated_at", jo.isNull("updated_at") ? null : jo.getString("updated_at"));
            sc.put("data_source", con.getDataSourcesCommand().getDataSourceName(data_source_id));
            rsrows.add( sc );
        }
        Map<String,String> rstypes = new LinkedHashMap<>();
        rstypes.put("id", "int");
        rstypes.put("query_name", "string");
        rstypes.put("query", "string");
        rstypes.put("userId", "int");
        rstypes.put("userName", "string");
        rstypes.put("latest_query_data_id", "int");
        rstypes.put("runtime", "double");
        rstypes.put("updated_at", "timestamp");
        rstypes.put("data_source", "string");
        return new MapResultSet(isTraced(), rsrows, rstypes);
    }
    
    public void createQuery(String queryName) throws SQLException {
        JSONObject options = new JSONObject();
        options.put("apply_auto_limit", false);
        options.put("parameters", new JSONArray());
        
        JSONObject postData = new JSONObject(); //EX: {"schedule":None,"name":"dbeaver","query":"SELECT * FROM DUAL WHERE rownum < 5","data_source_id":432,"options":{"apply_auto_limit":False,"parameters":[]},"latest_query_data_id":893602,"tags":[]}
        postData.put( "schedule", JSONObject.NULL );
        postData.put( "name", queryName );
        postData.put( "data_source_id", con.getDsId() );
        postData.put( "options", options );
        postData.put( "query", "" );
        postData.put( "tags", new JSONArray() );
        //postData.put( "query_id", new Random(System.nanoTime()).nextInt() );
        if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, postData.toString() );
        StringBuffer response = con.getRedashHttp().post( "/api/queries", postData.toString() );
        //System.out.println( "QUERY_CREATE:" + response.toString() );
        JSONObject jo = new JSONObject(response.toString());
        this.queryId = jo.getInt("id");
        this.queryVersion = jo.getLong("version");
    }
    
    protected JSONObject updateQuery(String sql) throws SQLException {
    	JSONObject postData = new JSONObject(); //EX: {"data_source_id":432,"parameters":{},"query":"SELECT * FROM RECEIVABLES_ADM.PAYMENT_INTENT WHERE rownum < 10","apply_auto_limit":false,"max_age":0,"query_id":18}
        postData.put( "id", getQueryId() );
        postData.put( "data_source_id", con.getDsId() );
        postData.put( "query", sql );
        postData.put( "latest_query_data_id", JSONObject.NULL );
        postData.put( "is_draft", true );
        postData.put( "version", this.queryVersion );
        if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, postData.toString() );
        final StringBuffer response = con.getRedashHttp().post( "/api/queries/%d", postData.toString(), getQueryId() );
        final JSONObject jo = new JSONObject(response.toString());
        this.latest_query_data_id = jo.get("latest_query_data_id");
        return jo;
    }

}
