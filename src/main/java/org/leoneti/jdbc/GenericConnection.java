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

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.leoneti.TraceLog;

public abstract class GenericConnection extends TraceLog implements Connection {

    public GenericConnection() {
        super();
    }

    public GenericConnection(boolean trace, Class<?> clazz) {
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
    public Statement createStatement() throws SQLException {
        logMethod("createStatement");
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        logMethod("prepareStatement", sql);
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        logMethod("prepareCall", sql);
        return null;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        logMethod("nativeSQL", sql);
        return null;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        logMethod("setAutoCommit", autoCommit);

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        logMethod("getAutoCommit");
        return false;
    }

    @Override
    public void commit() throws SQLException {
        logMethod("commit");

    }

    @Override
    public void rollback() throws SQLException {
        logMethod("rollback");

    }

    @Override
    public void close() throws SQLException {
        logMethod("close");

    }

    @Override
    public boolean isClosed() throws SQLException {
        logMethod("isClosed");
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        logMethod("getMetaData");
        return null;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        logMethod("setReadOnly", readOnly);

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        logMethod("isReadOnly");
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        logMethod("setCatalog", catalog);

    }

    @Override
    public String getCatalog() throws SQLException {
        logMethod("getCatalog");
        return null;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        logMethod("setTransactionIsolation", level);

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        logMethod("getTransactionIsolation");
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        logMethod("getWarnings");
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {
        logMethod("clearWarnings");

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        logMethod("createStatement", resultSetType, resultSetConcurrency);
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        logMethod("prepareStatement", sql, resultSetType, resultSetConcurrency);
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        logMethod("prepareCall", sql, resultSetType, resultSetConcurrency);
        return null;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        logMethod("getTypeMap");
        return null;
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        logMethod("setTypeMap", map);

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        logMethod("setHoldability", holdability);

    }

    @Override
    public int getHoldability() throws SQLException {
        logMethod("getHoldability");
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        logMethod("setSavepoint");
        return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        logMethod("setSavepoint", name);
        return null;
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        logMethod("rollback", savepoint);

    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        logMethod("releaseSavepoint", savepoint);

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logMethod("createStatement", resultSetType, resultSetConcurrency, resultSetHoldability);
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logMethod("prepareStatement", sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        logMethod("prepareCall", sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        logMethod("prepareStatement", sql, autoGeneratedKeys);
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        logMethod("prepareStatement", sql, columnIndexes);
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        logMethod("prepareStatement", sql, columnNames);
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        logMethod("createClob");
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        logMethod("createBlob");
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        logMethod("createNClob");
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        logMethod("createSQLXML");
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        logMethod("isValid", timeout);
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        logMethod("setClientInfo", name, value);

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        logMethod("setClientInfo", properties);

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        logMethod("getClientInfo", name);
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        logMethod("getClientInfo");
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        logMethod("createArrayOf", typeName, elements);
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        logMethod("createStruct", typeName, attributes);
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        logMethod("setSchema", schema);

    }

    @Override
    public String getSchema() throws SQLException {
        logMethod("getSchema");
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        logMethod("abort", executor);

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        logMethod("setNetworkTimeout", executor, milliseconds);

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        logMethod("getNetworkTimeout");
        return 0;
    }

}
