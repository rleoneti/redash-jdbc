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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import org.leoneti.TraceLog;
import org.leoneti.jdbc.redash.RedashException;
import org.leoneti.jdbc.redash.RedashResource;

public abstract class GenericStatement extends TraceLog implements PreparedStatement {

    public GenericStatement() {
        super();
    }

    public GenericStatement(boolean trace, Class<?> clazz) {
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
    public ResultSet executeQuery() throws SQLException {
        logMethod("executeQuery");
        return null;
    }

    @Override
    public int executeUpdate() throws SQLException {
        logMethod("executeUpdate");
        return 0;
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        logMethod("setNull", parameterIndex, sqlType);

    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        logMethod("setBoolean", parameterIndex, x);

    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        logMethod("setByte", parameterIndex, x);

    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        logMethod("setShort", parameterIndex, x);

    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        logMethod("setInt", parameterIndex, x);

    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        logMethod("setLong", parameterIndex, x);

    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        logMethod("setFloat", parameterIndex, x);

    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        logMethod("setDouble", parameterIndex, x);

    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        logMethod("setBigDecimal", parameterIndex, x);

    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        logMethod("setString", parameterIndex, x);

    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        logMethod("setBytes", parameterIndex, x);

    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        logMethod("setDate", parameterIndex, x);

    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        logMethod("setTime", parameterIndex, x);

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        logMethod("setTimestamp", parameterIndex, x);

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        logMethod("setAsciiStream", parameterIndex, x, length);

    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        logMethod("setUnicodeStream", parameterIndex, x, length);

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        logMethod("setBinaryStream", parameterIndex, x, length);

    }

    @Override
    public void clearParameters() throws SQLException {
        logMethod("clearParameters");

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        logMethod("setObject", parameterIndex, x, targetSqlType);

    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        logMethod("setObject", parameterIndex, x);

    }

    @Override
    public boolean execute() throws SQLException {
        logMethod("execute");
        return false;
    }

    @Override
    public void addBatch() throws SQLException {
        logMethod("addBatch");

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        logMethod("setCharacterStream", parameterIndex, reader, length);

    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        logMethod("setRef", parameterIndex, x);

    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        logMethod("setBlob", parameterIndex, x);

    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        logMethod("setClob", parameterIndex, x);

    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        logMethod("setArray", parameterIndex, x);

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        logMethod("getMetaData");
        return null;
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        logMethod("setDate", parameterIndex, x, cal);

    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        logMethod("setTime", parameterIndex, x, cal);

    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        logMethod("setTimestamp", parameterIndex, x, cal);

    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        logMethod("setNull", parameterIndex, sqlType, typeName);

    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        logMethod("setURL", parameterIndex, x);

    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        logMethod("getParameterMetaData");
        return null;
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        logMethod("setRowId", parameterIndex, x);

    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        logMethod("setNString", parameterIndex, value);

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        logMethod("setNCharacterStream", parameterIndex, value, length);

    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        logMethod("setNClob", parameterIndex, value);

    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        logMethod("setClob", parameterIndex, reader, length);

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        logMethod("setBlob", parameterIndex, inputStream, length);

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        logMethod("setNClob", parameterIndex, reader, length);

    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        logMethod("setSQLXML", parameterIndex, xmlObject);

    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        logMethod("setObject", parameterIndex, x, targetSqlType, scaleOrLength);

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        logMethod("setAsciiStream", parameterIndex, x, length);

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        logMethod("setBinaryStream", parameterIndex, x, length);

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        logMethod("setCharacterStream", parameterIndex, reader, length);

    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        logMethod("setAsciiStream", parameterIndex, x);

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        logMethod("setBinaryStream", parameterIndex, x);

    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        logMethod("setCharacterStream", parameterIndex, reader);

    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        logMethod("setNCharacterStream", parameterIndex, value);

    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        logMethod("setClob", parameterIndex, reader);

    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        logMethod("setBlob", parameterIndex, inputStream);

    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        logMethod("setNClob", parameterIndex, reader);

    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        logMethod("executeQuery", sql);
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        logMethod("executeUpdate", sql);
        return 0;
    }

    @Override
    public void close() throws SQLException {
        logMethod("close");

    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        logMethod("getMaxFieldSize");
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        logMethod("setMaxFieldSize", max);

    }

    @Override
    public int getMaxRows() throws SQLException {
        logMethod("getMaxRows");
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        logMethod("setMaxRows", max);

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        logMethod("setEscapeProcessing", enable);

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        logMethod("getQueryTimeout");
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        logMethod("setQueryTimeout", seconds);

    }

    @Override
    public void cancel() throws SQLException {
        logMethod("cancel");

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
    public void setCursorName(String name) throws SQLException {
        logMethod("setCursorName", name);

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        logMethod("execute", sql);
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        logMethod("getResultSet");
        return null;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        logMethod("getUpdateCount");
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        logMethod("getMoreResults");
        throw new RedashException(RedashResource.getString("methodNotSupported"));
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        logMethod("setFetchDirection", direction);

    }

    @Override
    public int getFetchDirection() throws SQLException {
        logMethod("getFetchDirection");
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        logMethod("setFetchSize", rows);

    }

    @Override
    public int getFetchSize() throws SQLException {
        logMethod("getFetchSize");
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        logMethod("getResultSetConcurrency");
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        logMethod("getResultSetType");
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        logMethod("addBatch", sql);
        throw new RedashException(RedashResource.getString("methodNotSupported"));
    }

    @Override
    public void clearBatch() throws SQLException {
        logMethod("clearBatch");

    }

    @Override
    public int[] executeBatch() throws SQLException {
        logMethod("executeBatch");
        throw new RedashException(RedashResource.getString("methodNotSupported"));
    }

    @Override
    public Connection getConnection() throws SQLException {
        logMethod("getConnection");
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        logMethod("getMoreResults", current);
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        logMethod("getGeneratedKeys");
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        logMethod("executeUpdate", sql, autoGeneratedKeys);
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        logMethod("executeUpdate", sql, columnIndexes);
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        logMethod("executeUpdate", sql, columnNames);
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        logMethod("execute", sql, autoGeneratedKeys);
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        logMethod("execute", sql, columnIndexes);
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        logMethod("execute", sql, columnNames);
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        logMethod("getResultSetHoldability");
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        logMethod("isClosed");
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        logMethod("setPoolable", poolable);

    }

    @Override
    public boolean isPoolable() throws SQLException {
        logMethod("isPoolable");
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        logMethod("closeOnCompletion");

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        logMethod("isCloseOnCompletion");
        return false;
    }

}
