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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.leoneti.jdbc.GenericResultSet;

public class RedashResultSet extends GenericResultSet {
    
    private JSONArray rows;
    private Iterator<Object> it;
    private Map<String, String> maptypes;
    private RedashResultSetMetaData metadata;
    
    private JSONObject currentObj;
    private Object lastObj;
    private int rowNumber = 0;
    
    public static SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SSS");

    public RedashResultSet(boolean trace, JSONArray cols, JSONArray rows) {
        super(trace, RedashResultSet.class);
        this.rows = rows;
        maptypes = new LinkedHashMap<String, String>(10);
        JSONObject firstObj = rows.isEmpty() ? null : rows.getJSONObject(0);
        for(Object obj : cols ) {
            JSONObject coltype = (JSONObject) obj;
            if( !coltype.isNull("type") ) {
                maptypes.put( coltype.getString("name"), coltype.getString("type") );
            } else if( firstObj == null) {
                maptypes.put( coltype.getString("name"), null );
            } else {
                Object val = firstObj.get( coltype.getString("name") );
                if( val instanceof Long || val instanceof Integer ) {
                    maptypes.put( coltype.getString("name"), "int" );
                } else if( val instanceof Float || val instanceof Double || val instanceof BigDecimal || val instanceof Number ) {
                    maptypes.put( coltype.getString("name"), "double" );
                } else if( val instanceof Boolean ) {
                    maptypes.put( coltype.getString("name"), "bool" );
                } else if( val instanceof java.util.Date || val instanceof java.sql.Date || val instanceof Timestamp ) {
                    maptypes.put( coltype.getString("name"), "datetime" );
                } else {
                    maptypes.put( coltype.getString("name"), null );
                }
            }
        }
        if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, maptypes.toString() );
        this.metadata = new RedashResultSetMetaData(isTraced(), maptypes);
        this.it = this.rows.iterator();
        this.rowNumber = 1;
        //for(Object obj : jo.getJSONObject("query_result").getJSONObject("data").getJSONArray("rows") ) {
        //    JSONObject row = (JSONObject) obj;
        //    System.out.println( row.toString() );
        //}
    }

    public RedashResultSet(boolean trace, JSONObject jo) {
    	this(trace, jo.getJSONObject("query_result").getJSONObject("data").getJSONArray("columns") ,  jo.getJSONObject("query_result").getJSONObject("data").getJSONArray("rows") );
    }

    @Override
    public boolean next() throws SQLException {
        if( it.hasNext() ) {
            this.currentObj = (JSONObject) it.next();
            this.rowNumber++;
            return true;
        }
        return false;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.metadata;
    }

    private Object readObject(String columnLabel) {
        this.lastObj = currentObj.isNull(columnLabel) ? null : currentObj.get( columnLabel );
        return this.lastObj;
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        readObject( columnLabel );
        if( this.lastObj != null && this.metadata.getColumnType(columnLabel) == Types.TIMESTAMP ) {
            try {
                this.lastObj = new Timestamp( datetime.parse( this.lastObj.toString() ).getTime() );
            } catch (ParseException e) {
                throw new SQLException(e);
            }
        }
        return this.lastObj;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return getObject( metadata.getColumnName(columnIndex) );
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        readObject( columnLabel );
        try {
            return wasNull()?null:new Timestamp( datetime.parse( this.lastObj.toString() ).getTime() );
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        return getTimestamp( metadata.getColumnName(columnIndex) );
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull()?null:this.lastObj.toString();
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return getString( metadata.getColumnName(columnIndex) );
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull()?null:currentObj.getInt( columnLabel );
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return getInt( metadata.getColumnName(columnIndex) );
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
            readObject( columnLabel );
            return wasNull()?null:currentObj.getLong( columnLabel );
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return getLong( metadata.getColumnName(columnIndex) );
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull()?null:currentObj.getFloat( columnLabel );
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return getFloat( metadata.getColumnName(columnIndex) );
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull()?null:currentObj.getDouble( columnLabel );
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return getDouble( metadata.getColumnName(columnIndex) );
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull()?null:currentObj.getBigDecimal( columnLabel );
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return getBigDecimal( metadata.getColumnName(columnIndex) );
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        return getBigDecimal(columnLabel);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        return getBigDecimal(columnIndex);
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull()?null:currentObj.getBoolean( columnLabel );
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return getBoolean( metadata.getColumnName(columnIndex) );
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.lastObj == null || this.lastObj.equals(JSONObject.NULL);
    }

    @Override
    public void close() throws SQLException {
    	this.rows = null;
    	this.currentObj = null;
    	this.it = null;
    	this.lastObj = null;
    	this.maptypes = null;
    	this.metadata = null;
    }

    @Override
    public boolean isClosed() throws SQLException { return this.rows == null; }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        return metadata.findColumn(columnLabel);
    }

    @Override
    public boolean isFirst() throws SQLException {
        return this.rowNumber == 1;
    }

    @Override
    public boolean isLast() throws SQLException {
        return this.rowNumber == this.rows.length()+1;
    }

    @Override
    public boolean first() throws SQLException {
        this.it = this.rows.iterator();
        this.rowNumber = 1;
        return true;
    }

    @Override
    public boolean last() throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getRow() throws SQLException {
        // TODO Auto-generated method stub
        return this.rowNumber;
    }

    @Override
    public int getFetchSize() throws SQLException {
        return this.rows.length()+1;
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
