/*****************************************************************************************
* Copyright (C) 2023-2023  Ricardo Leoneti                           Date: 2025-10-22
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html
*
* Contributors:
*     Ricardo Leoneti <ricardo.leoneti@gmail.com>    - paginator implementation
* 
*****************************************************************************************/
package org.leoneti.jdbc.redash;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.leoneti.jdbc.GenericResultSet;
import org.leoneti.jdbc.redash.commands.RedashExecuteQueryCommand;


public class RedashPaginatorResultSet extends GenericResultSet {
    
    private RedashConnection con;
    
    private RedashExecuteQueryCommand execQueryCommand;
    private String column;
    private String sql;
    private List<Object> result_id_list = new ArrayList<Object>(10);
    private int page=0;
    private RedashResultSet currentResultSet;
    private String dsType;
    private JSONArray rows;
    private Comparable<Object> lastColumnValue = null;

    private int rowNumber = 0;

    public RedashPaginatorResultSet(RedashConnection con, RedashExecuteQueryCommand execQueryCommand, String column, String sql, String dsType ) throws SQLException {
        super(con.isTraced(), RedashPaginatorResultSet.class);
        this.con = con;
        this.execQueryCommand = execQueryCommand;
        this.column = column == null ? null : column.trim();
        this.sql = sql.trim();
        this.dsType = dsType;
        this.executeNextFetch();
    }
    
    private void executeNextFetch() throws SQLException {
        logMethodWithReturn( "executeNextFetch", "page:"+page );
        final String sql = getPaginatorQuery();
        JSONObject currentJo = execQueryCommand.executeQuery( sql );
        this.rows = currentJo.getJSONObject("query_result").getJSONObject("data").getJSONArray("rows");
        this.result_id_list.add( execQueryCommand.getLastResultId() );
        this.currentResultSet = new RedashResultSet(con.isResultSetTraced(), currentJo, this.dsType);
    }
    
    private String getPaginatorQuery() {
        final int pagesize = con.getResultSetFetchSize();
        final String query;
        final String fetchLimit;
        if( this.dsType.equalsIgnoreCase("oracle") ) {
            fetchLimit = String.format("OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", page*pagesize, pagesize );
        } else {
            fetchLimit = String.format("LIMIT %d OFFSET %d", pagesize, page*pagesize );
        }
        if( this.column != null ) {
            query = "SELECT * FROM ( " + this.sql + " ) tmp_paginator ORDER BY " + this.column + " ASC " + fetchLimit;
        } else {
            query = this.sql + " " + fetchLimit;
        }
        logMethodWithReturn("getPaginatorQuery", query);
        return query;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public boolean next() throws SQLException {
        final boolean next = currentResultSet.next();
        if( next ) {
            if( this.column != null ) {
                final Object lastRef = currentResultSet.getObject( this.column );
                if( this.lastColumnValue == null || this.lastColumnValue.compareTo(lastRef) < 0 ) {
                    this.lastColumnValue = (Comparable<Object>) lastRef;
                }
            }
            if( con.isResultSetTraced() ) logMethodWithReturn("next", true);
            this.rowNumber++;
            return true;
        }
        if( this.rows.isEmpty() ) {
            if( con.isResultSetTraced() ) logMethodWithReturn("next", false);
            return false;
        }
        this.page += 1;
        this.executeNextFetch();
        return this.next();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return currentResultSet.getMetaData();
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        return currentResultSet.getObject(columnLabel);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return currentResultSet.getObject(columnIndex);
    }
    
    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        return currentResultSet.getTimestamp(columnLabel);
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return currentResultSet.getTimestamp(columnIndex);
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        return currentResultSet.getString(columnLabel);
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return currentResultSet.getString(columnIndex);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        return currentResultSet.getInt(columnLabel);
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return currentResultSet.getInt(columnIndex);
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        return currentResultSet.getLong(columnLabel);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return currentResultSet.getLong(columnIndex);
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        return currentResultSet.getFloat(columnLabel);
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return currentResultSet.getFloat(columnIndex);
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        return currentResultSet.getDouble(columnLabel);
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return currentResultSet.getDouble(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        return currentResultSet.getBigDecimal(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return currentResultSet.getBigDecimal(columnIndex);
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return currentResultSet.getBigDecimal(columnLabel, scale);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return getBigDecimal(columnIndex, scale);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        return currentResultSet.getBoolean(columnLabel);
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return currentResultSet.getBoolean(columnIndex);
    }
    
    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return currentResultSet.getDate(columnIndex);
    }
    
    @Override
    public Date getDate(String columnLabel) throws SQLException {
        return currentResultSet.getDate(columnLabel);
    }
    
    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return currentResultSet.getTime(columnIndex);
    }
    
    @Override
    public Time getTime(String columnLabel) throws SQLException {
        return currentResultSet.getTime(columnLabel);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return currentResultSet.wasNull();
    }
    
    @Override
    public void close() throws SQLException {
        this.execQueryCommand = null;
    }

    @Override
    public boolean isClosed() throws SQLException { return this.execQueryCommand == null; }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return currentResultSet.getInternalMetaData().findColumn(columnLabel);
    }

    @Override
    public int getRow() throws SQLException {
        if( con.isResultSetTraced() ) logMethodWithReturn("getRow", this.rowNumber);
        return this.rowNumber;
    }

    @Override
    public int getType() throws SQLException {
        return ResultSet.TYPE_FORWARD_ONLY;
    }

    @Override
    public int getConcurrency() throws SQLException {
        return ResultSet.CONCUR_READ_ONLY;
    }
    
}
