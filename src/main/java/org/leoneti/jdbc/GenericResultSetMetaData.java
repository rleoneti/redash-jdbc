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
package org.leoneti.jdbc;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.leoneti.TraceLog;

public abstract class GenericResultSetMetaData extends TraceLog implements ResultSetMetaData {

    public GenericResultSetMetaData() {
        super();
    }

    public GenericResultSetMetaData(boolean trace, Class<?> clazz) {
        super(trace,clazz);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        logMethod("unwrap", iface);
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        logMethod("isWrapperFor", iface);
        return false;
    }

    @Override
    public int getColumnCount() throws SQLException {
        logMethod("getColumnCount");
        return 0;
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        logMethod("isAutoIncrement", column);
        return false;
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        logMethod("isCaseSensitive", column);
        return false;
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        logMethod("isSearchable", column);
        return false;
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        logMethod("isCurrency", column);
        return false;
    }

    @Override
    public int isNullable(int column) throws SQLException {
        logMethod("isNullable", column);
        return 0;
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        logMethod("isSigned", column);
        return false;
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        logMethod("getColumnDisplaySize", column);
        return 0;
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        logMethod("getColumnLabel", column);
        return null;
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        logMethod("getColumnName", column);
        return null;
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        logMethod("getSchemaName", column);
        return null;
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        logMethod("getPrecision", column);
        return 0;
    }

    @Override
    public int getScale(int column) throws SQLException {
        logMethod("getScale", column);
        return 0;
    }

    @Override
    public String getTableName(int column) throws SQLException {
        logMethod("getTableName", column);
        return null;
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        logMethod("getCatalogName", column);
        return null;
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        logMethod("getColumnType", column);
        return 0;
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        logMethod("getColumnTypeName", column);
        return null;
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        logMethod("isReadOnly", column);
        return false;
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        logMethod("isWritable", column);
        return false;
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        logMethod("isDefinitelyWritable", column);
        return false;
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        logMethod("getColumnClassName", column);
        return null;
    }

}
