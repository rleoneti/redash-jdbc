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

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;

import org.leoneti.jdbc.GenericConnection;
import org.leoneti.jdbc.redash.commands.RedashDataSourcesCommand;
import org.leoneti.jdbc.redash.commands.RedashQueryCommand;

public class RedashConnection extends GenericConnection {
	
    private String url;
    private String ds;
    private int dsId;
    private String dsType;
    private Properties info;
    private RedashHttp rh;
    private RedashQueryCommand queryCommand;
    private RedashDataSourcesCommand dsCommand;
    private RedashDatabaseMetaData metadata;
    
    public RedashHttp getRedashHttp() { return rh; }
    
    public int getDsId() { return dsId; }
    
    public String getDs() { return ds; }
    
    public String getDsType() { return dsType; }
    
    public RedashQueryCommand getQueryCommand() { return queryCommand; }
    
    public RedashDataSourcesCommand getDataSourcesCommand() { return dsCommand; }
        
    protected String getToken() {
        return info.getProperty("token", info.getProperty("password"));
    }
    
    public boolean isSSL() { return Boolean.valueOf(info.getProperty(RedashConstants.DRIVER_PROPERTY_SSL, "true")); }
    
    public int pageSize() { return Integer.valueOf(info.getProperty(RedashConstants.DRIVER_PROPERTY_PAGE_SIZE, "250")); }

    public String getQueryName() { return info.getProperty(RedashConstants.DRIVER_PROPERTY_QUERY_NAME, RedashConstants.DRIVER_NAME); }

    @Override
    public String toString() {
        if( ds != null )
            return String.format("%s%s@%s\tid:%d",RedashConstants.DRIVER_PREFIX, url, ds, dsId);
        return String.format("%s%s",RedashConstants.DRIVER_PREFIX, url);
    }
    
    protected RedashConnection(String url, String ds, Properties info) throws SQLException {
        super( info.containsKey(RedashConstants.DRIVER_PROPERTY_TRACE) && Boolean.valueOf( info.getProperty(RedashConstants.DRIVER_PROPERTY_TRACE, "false") ), RedashConnection.class  );
        logMethod("RedashConnection", url,ds);

        this.info = info;
        this.url = url;
        this.rh = new RedashHttp(isTraced(), url, getToken(), isSSL());
        if( info.containsKey(RedashConstants.DRIVER_PROPERTY_USER_AGENT) )
            this.rh.setUserAgent( info.getProperty( RedashConstants.DRIVER_PROPERTY_USER_AGENT ) );

        this.dsCommand = new RedashDataSourcesCommand(this);
        if( ds != null && !ds.isEmpty() ) {
            setCatalog(ds);
        } else {
            setCatalog( this.dsCommand.get().getJSONObject(0).getString("name") );
        }
        
        this.metadata = new RedashDatabaseMetaData(this);
        this.queryCommand = new RedashQueryCommand(this);
        this.queryCommand.identifyOrCreateQueryId();
    }
    

    @Override
    public Statement createStatement() throws SQLException {
        logMethod_("createStatement", true );
        return new RedashStatement(this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        logMethod_("prepareStatement", true, sql);
        return new RedashStatement(this, sql);
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        logMethodWithReturn("getMetaData", this.metadata);
        if( this.metadata == null ) {
            this.metadata = new RedashDatabaseMetaData(this);
            if( this.queryCommand == null ) {
                this.queryCommand = new RedashQueryCommand(this);
                this.queryCommand.identifyOrCreateQueryId();
            }
        }
        return this.metadata;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException();
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new SQLException();
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new SQLException();
    }

    @Override
    public void close() throws SQLException {
        logMethod("close");
        this.metadata = null;
        this.rh = null;
        this.info = null;
        this.url = null;
        this.ds = null;
        this.dsCommand = null;
        this.queryCommand = null;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        logMethod("setCatalog", catalog);
        this.ds = catalog;
        this.dsId = getDataSourcesCommand().getDataSourceId(catalog);
        this.dsType = getDataSourcesCommand().getDataSourceType(dsId);
    }

    @Override
    public String getCatalog() throws SQLException {
        logMethodWithReturn("getCatalog", ds);
        return ds; 
    }

    @Override
    public boolean isClosed() throws SQLException { return this.url == null || this.info == null; }

    @Override
    public boolean isReadOnly() throws SQLException { return true; }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        logMethod_("createStatement", true, resultSetType, resultSetConcurrency);
        return createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        logMethod_("prepareStatement", true, sql, resultSetType, resultSetConcurrency);
        return prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        logMethod_("prepareCall", true, sql, resultSetType, resultSetConcurrency);
        return prepareCall(sql);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        throw new SQLException();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        throw new SQLException();
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logMethod_("createStatement", true, resultSetType, resultSetConcurrency, resultSetHoldability);
        return createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logMethod_("prepareStatement", true, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logMethod_("prepareCall", true, sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return prepareCall(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        logMethod_("prepareStatement", true, sql, autoGeneratedKeys);
        return prepareStatement(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        logMethod_("prepareStatement", true, sql, columnIndexes);
        return prepareStatement(sql);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        logMethod_("prepareStatement", true, sql, columnNames);
        return prepareStatement(sql);
    }

}
