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
package org.leoneti.jdbc.iterable;

import java.sql.JDBCType;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;

import org.leoneti.jdbc.GenericResultSetMetaData;

public class MapResultSetMetaData extends GenericResultSetMetaData {

    private Map<String,JDBCType> maptypes;

    public MapResultSetMetaData(boolean trace, Map<String, JDBCType> maptypes) {
        super(trace, MapResultSetMetaData.class);
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

    @Override
    public String getColumnLabel(int column) throws SQLException {
        return getColumnName(column);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        logMethodWithReturn("unwrap", iface.isInterface() && iface.isInstance(this), iface);
        if (iface.isInterface() && iface.isInstance(this)) return (T) this;
        throw new SQLException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        logMethodWithReturn("isWrapperFor", iface.isInstance(this), iface);
        if (iface.isInterface()) return iface.isInstance(this);
        throw new SQLException();
    }
    
    public int getColumnType(String colname) throws SQLException {
        logMethodWithReturn("getColumnType", maptypes.get(colname), colname);
        return (maptypes.containsKey(colname) && maptypes.get(colname) != null) ? maptypes.get(colname).getVendorTypeNumber() : JDBCType.NULL.getVendorTypeNumber();
    }
    
    @Override
    public int getColumnType(int column) throws SQLException {
        String colname = getColumnName(column);
        return getColumnType(colname);
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        logMethod("getColumnTypeName", column);
        
        final String colname = getColumnName(column);
        return (maptypes.containsKey(colname) && maptypes.get(colname) != null) ? maptypes.get(colname).name() : JDBCType.NULL.name();
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        final String colname = getColumnName(column);
        switch(maptypes.get(colname)) {
        case TIMESTAMP:
        case TIMESTAMP_WITH_TIMEZONE: return Timestamp.class.getName();
        case TIME:
        case TIME_WITH_TIMEZONE: return Time.class.getName();
        case INTEGER:
        case BIGINT:
        case SMALLINT:
        case TINYINT: return Long.class.getName();
        case DECIMAL:
        case FLOAT:
        case DOUBLE:
        case REAL:
        case NUMERIC: return Double.class.getName();
        case BOOLEAN: return Boolean.class.getName();
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
