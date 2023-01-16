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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Map;

import org.leoneti.jdbc.GenericResultSetMetaData;

public class RedashResultSetMetaData extends GenericResultSetMetaData {

    private Map<String, String> maptypes;

    public RedashResultSetMetaData(boolean trace, Map<String, String> maptypes) {
        super(trace, RedashResultSetMetaData.class);
        this.maptypes = maptypes;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return maptypes.size();
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        return maptypes.keySet().toArray()[column - 1].toString();
    }
    
    public int findColumn(String columnLabel) throws SQLException {
        return Arrays.binarySearch( maptypes.keySet().toArray(), columnLabel )+1;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return getColumnName(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInterface() && iface.isInstance(this)) return (T) this;
        throw new SQLException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        if (iface.isInterface()) return iface.isInstance(this);
        throw new SQLException();
    }
    
    public int getColumnType(String colname) throws SQLException {
        if( maptypes.get(colname) == null )
            return Types.VARCHAR;
        if( maptypes.get(colname).equalsIgnoreCase("datetime") || maptypes.get(colname).equalsIgnoreCase("timestamp") )
            return Types.TIMESTAMP;
        if( maptypes.get(colname).equalsIgnoreCase("integer") || maptypes.get(colname).equalsIgnoreCase("int")
        	|| maptypes.get(colname).equalsIgnoreCase("long") || maptypes.get(colname).equalsIgnoreCase("smalint") )
            return Types.BIGINT;
        if( maptypes.get(colname).equalsIgnoreCase("double") || maptypes.get(colname).equalsIgnoreCase("float"))
            return Types.DECIMAL;
        if( maptypes.get(colname).equalsIgnoreCase("boolean") || maptypes.get(colname).equalsIgnoreCase("bool"))
            return Types.BOOLEAN;
        return Types.VARCHAR;
    }
    
    @Override
    public int getColumnType(int column) throws SQLException {
        String colname = getColumnName(column);
        return getColumnType(colname);
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        String colname = getColumnName(column);
    	if( maptypes.get(colname) == null )
            return "VARCHAR";
        if( maptypes.get(colname).equalsIgnoreCase("datetime") || maptypes.get(colname).equalsIgnoreCase("timestamp") )
            return "TIMESTAMP";
        if( maptypes.get(colname).equalsIgnoreCase("integer") || maptypes.get(colname).equalsIgnoreCase("int")
        	|| maptypes.get(colname).equalsIgnoreCase("long") || maptypes.get(colname).equalsIgnoreCase("smalint") )
            return "BIGINT";
        if( maptypes.get(colname).equalsIgnoreCase("double") || maptypes.get(colname).equalsIgnoreCase("float"))
            return "DECIMAL";
        if( maptypes.get(colname).equalsIgnoreCase("boolean") || maptypes.get(colname).equalsIgnoreCase("bool"))
            return "BOOLEAN";
        return "VARCHAR";
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        String colname = getColumnName(column);
        if( maptypes.get(colname) == null )
            return String.class.getName();
        if( maptypes.get(colname).equalsIgnoreCase("datetime") || maptypes.get(colname).equalsIgnoreCase("timestamp") )
            return Timestamp.class.getName();
        if( maptypes.get(colname).equalsIgnoreCase("integer") || maptypes.get(colname).equalsIgnoreCase("int")
            	|| maptypes.get(colname).equalsIgnoreCase("long") || maptypes.get(colname).equalsIgnoreCase("smalint") )
            return Long.class.getName();
        if( maptypes.get(colname).equalsIgnoreCase("double") || maptypes.get(colname).equalsIgnoreCase("float"))
            return Double.class.getName();;
        if( maptypes.get(colname).equalsIgnoreCase("boolean") || maptypes.get(colname).equalsIgnoreCase("bool"))
            return Boolean.class.getName();;
        return String.class.getName();
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException { return true; }

    @Override
    public String getTableName(int column) throws SQLException { return ""; }

    @Override
    public String getCatalogName(int column) throws SQLException { return ""; }

}
