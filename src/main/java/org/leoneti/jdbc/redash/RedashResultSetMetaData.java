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

import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

import org.leoneti.jdbc.GenericResultSetMetaData;
import static java.sql.JDBCType.*;

import java.sql.Array;

public class RedashResultSetMetaData extends GenericResultSetMetaData {

    private Map<String, JDBCType> maptypes;

    public RedashResultSetMetaData(boolean trace, Map<String, JDBCType> maptypes) {
        super(trace, RedashResultSetMetaData.class);
        this.maptypes = maptypes;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return maptypes.size();
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        logMethod("getColumnName", column);
        return maptypes.keySet().toArray()[column - 1].toString();
    }
    
    public int findColumn(String columnLabel) throws SQLException {
        logMethod("findColumn", columnLabel);
        return Arrays.binarySearch( maptypes.keySet().toArray(), columnLabel )+1;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        logMethod_("getColumnLabel", true , column);
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
        logMethod("getColumnType", colname);
        if( maptypes.get(colname) == null )
            return VARCHAR.ordinal();
        return maptypes.get(colname).ordinal();
    }
    
    @Override
    public int getColumnType(int column) throws SQLException {
        String colname = getColumnName(column);
        return getColumnType(colname);
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        logMethod("getColumnTypeName", column);
        String colname = getColumnName(column);
        if( maptypes.get(colname) == null )
            return VARCHAR.name();
        return maptypes.get(colname).name();
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        logMethod("getColumnClassName", column);
        String colname = getColumnName(column);
        if( maptypes.get(colname) == null )
            return String.class.getName();
        switch( maptypes.get(colname) ) {
            case TIME_WITH_TIMEZONE:
            case TIME: return java.sql.Time.class.getName();
            case DATE: return java.sql.Date.class.getName();
            case TIMESTAMP_WITH_TIMEZONE:
            case TIMESTAMP: return java.sql.Timestamp.class.getName();
            case REAL:
            case DECIMAL:
            case NUMERIC: return Number.class.getName();
            case DOUBLE: return Double.class.getName();
            case FLOAT: return Float.class.getName();
            case TINYINT:
            case SMALLINT:
            case INTEGER: return Integer.class.getName();
            case BIGINT: return Long.class.getName();
            case BOOLEAN: return Boolean.class.getName();
            case JAVA_OBJECT: return Object.class.getName();
            case ARRAY: return Array.class.getName();
            default:
                return String.class.getName();
        }
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException { return true; }

    @Override
    public String getTableName(int column) throws SQLException { return ""; }

    @Override
    public String getCatalogName(int column) throws SQLException { return ""; }

}
