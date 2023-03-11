/*****************************************************************************************
* Copyright (C) 2023-2023  Ricardo Leoneti                           Date: 2023-03-05
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
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
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
import static java.sql.JDBCType.*;
import static org.leoneti.Utils.toJDBCType;

public class RedashResultSet extends GenericResultSet {
    
    private JSONArray rows;
    private Iterator<Object> it;
    private Map<String, JDBCType> maptypes;
    private RedashResultSetMetaData metadata;
    private String dsType;
    
    private JSONObject currentObj;
    private Object lastObj;
    private int rowNumber = 0;
    
    public static SimpleDateFormat datetime_micro = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"); //23
    public static SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //19
    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd"); //10
    public static SimpleDateFormat time_micro = new SimpleDateFormat("HH:mm:ss.SSS"); //12
    public static SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss"); //8

    public RedashResultSet(boolean trace, JSONArray cols, JSONArray rows, String dsType) {
        super(trace, RedashResultSet.class);
        this.dsType = dsType;
        this.rows = rows;
        maptypes = new LinkedHashMap<String, JDBCType>(10);
        //final JSONObject firstObj = rows.isEmpty() ? null : rows.getJSONObject(0);
        for(Object obj : cols ) {
            final JSONObject coltype = (JSONObject) obj;
            final String colName = coltype.getString("name");
            if( !coltype.isNull("type") ) {
                final String type = coltype.getString("type");
                /**
                 * BUG: Redash with decimal values on Oracle
                 */
                if( !getDsType().equalsIgnoreCase("oracle") || (!(type.equalsIgnoreCase("float") || type.equalsIgnoreCase("integer")) && getDsType().equalsIgnoreCase("oracle")) ) {
                    maptypes.put( colName, toJDBCType(type) );
                } else {
                    maptypes.put( colName, DECIMAL );
                }
            //} else if( firstObj == null) {
            //    maptypes.put( colName, null );
            } else {
                maptypes.put( colName, JAVA_OBJECT );
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

    public String getDsType() { return dsType; }

    public RedashResultSet(boolean trace, JSONObject jo, String dsType) {
    	this(trace, jo.getJSONObject("query_result").getJSONObject("data").getJSONArray("columns") ,  jo.getJSONObject("query_result").getJSONObject("data").getJSONArray("rows"), dsType );
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
        switch( maptypes.get(columnLabel) ) {
            case TIME_WITH_TIMEZONE:
            case TIME: return getTime(columnLabel);
            case DATE: return getDate(columnLabel);
            case TIMESTAMP_WITH_TIMEZONE:
            case TIMESTAMP: return getTimestamp(columnLabel);
            case REAL:
            case DECIMAL:
            case NUMERIC: return getBigDecimal(columnLabel);
            case DOUBLE: return getDouble(columnLabel);
            case FLOAT: return getFloat(columnLabel);
            case TINYINT:
            case SMALLINT:
            case INTEGER: return getInt(columnLabel);
            case BIGINT: return getLong(columnLabel);
            case BOOLEAN: return getBoolean(columnLabel);
            case JAVA_OBJECT: return getJavaObject(columnLabel);
            //case ARRAY: return getArray(columnLabel);
            default:
                return getString(columnLabel);
        }
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return getObject( metadata.getColumnName(columnIndex) );
    }
    
    private Object getJavaObject(String columnLabel) throws SQLException {
        readObject( columnLabel );
        if( wasNull() ) return null;
        final String strObj = this.lastObj.toString().trim();
        final int len = strObj.length();
        if( len == 23 && strObj.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\d\\.\\d\\d\\d") ) {
            try {
                return new Timestamp( datetime_micro.parse( strObj ).getTime() ); //yyyy-MM-dd'T'HH:mm:ss.SSS
            } catch (ParseException e) {}
        }        
        if( len == 19 && strObj.matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d\\:\\d\\d\\:\\d\\d") ) {
            try {
                return new Timestamp( datetime.parse( strObj ).getTime() ); //yyyy-MM-dd'T'HH:mm:ss
            } catch (ParseException e) {}
        }        
        if( len == 10 && strObj.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d") ) {
            try {
                return new Date( date.parse( strObj ).getTime() ); //yyyy-MM-dd
            } catch (ParseException e) {}
        }        
        if( len == 12 && strObj.matches("\\d\\d\\:\\d\\d\\:\\d\\d\\.\\d\\d\\d") ) {
            try {
                return new Time( time_micro.parse( strObj ).getTime() ); //HH:mm:ss.SSS
            } catch (ParseException e) {}
        }        
        if( len == 8 && strObj.matches("\\d\\d\\:\\d\\d\\:\\d\\d") ) {
            try {
                return new Timestamp( time.parse( strObj ).getTime() ); //HH:mm:ss
            } catch (ParseException e) {}
        }
        if( strObj.matches("[\\d\\.-]+") ) {
            try {
                return Long.parseLong( strObj );
            } catch (NumberFormatException e) {
                try {
                    return Double.parseDouble( strObj );
                } catch (NumberFormatException ee) {}
            }
        }
        return this.lastObj;
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        readObject( columnLabel );
        try {
            return wasNull()?null:new Timestamp( datetime_micro.parse( this.lastObj.toString() ).getTime() );
        } catch (ParseException e) {
            try {
                return wasNull()?null:new Timestamp( datetime.parse( this.lastObj.toString() ).getTime() );
            } catch (ParseException ee) {
                throw new SQLException(e);
            }
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
        return wasNull() ? -1 : currentObj.getNumber( columnLabel ).intValue();
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return getInt( metadata.getColumnName(columnIndex) );
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        readObject(columnLabel);
        return wasNull() ? -1L : currentObj.getNumber(columnLabel).longValue();
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return getLong( metadata.getColumnName(columnIndex) );
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull() ? -1F : currentObj.getNumber( columnLabel ).floatValue();
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return getFloat( metadata.getColumnName(columnIndex) );
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull() ? -1D : currentObj.getNumber( columnLabel ).doubleValue();
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return getDouble( metadata.getColumnName(columnIndex) );
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        readObject( columnLabel );
        return wasNull() ? null : currentObj.getBigDecimal( columnLabel );
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
        return wasNull() ? false : currentObj.getBoolean( columnLabel );
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return getBoolean( metadata.getColumnName(columnIndex) );
    }
    
    @Override
    public Date getDate(int columnIndex) throws SQLException {
        return getDate( metadata.getColumnName(columnIndex) );
    }
    
    @Override
    public Date getDate(String columnLabel) throws SQLException {
        final Timestamp dt = getTimestamp(columnLabel);
        if( dt != null ) {
            return new Date( dt.getTime() );
        }
        return null;
    }
    
    @Override
    public Time getTime(int columnIndex) throws SQLException {
        return getTime( metadata.getColumnName(columnIndex) );
    }
    
    @Override
    public Time getTime(String columnLabel) throws SQLException {
        final Timestamp dt = getTimestamp(columnLabel);
        if( dt != null ) {
            return new Time( dt.getTime() );
        }
        return null;
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
