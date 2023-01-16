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
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.leoneti.TraceLog;

public abstract class GenericResultSet extends TraceLog implements ResultSet {

    public GenericResultSet() {
        super();
    }

    public GenericResultSet(boolean trace, Class<?> clazz) {
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
    public boolean next() throws SQLException {
        logMethod("next");
        return false;
    }

    @Override
    public void close() throws SQLException {
        logMethod("close");

    }

    @Override
    public boolean wasNull() throws SQLException {
        logMethod("wasNull");
        return false;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        logMethod("getString", columnIndex);
        return null;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        logMethod("getBoolean", columnIndex);
        return false;
    }

    @Override
    public byte getByte(int columnIndex) throws SQLException {
        logMethod("getByte", columnIndex);
        return 0;
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        logMethod("getShort", columnIndex);
        return 0;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        logMethod("getInt", columnIndex);
        return 0;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        logMethod("getLong", columnIndex);
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        logMethod("getFloat", columnIndex);
        return 0;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        logMethod("getDouble", columnIndex);
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
        logMethod("getBigDecimal", columnIndex, scale);
        return null;
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        logMethod("getBytes", columnIndex);
        return null;
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        logMethod("getDate", columnIndex);
        return null;
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        logMethod("getTime", columnIndex);
        return null;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        logMethod("getTimestamp", columnIndex);
        return null;
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        logMethod("getAsciiStream", columnIndex);
        return null;
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        logMethod("getUnicodeStream", columnIndex);
        return null;
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        logMethod("getBinaryStream", columnIndex);
        return null;
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        logMethod("getString", columnLabel);
        return null;
    }

    @Override
    public boolean getBoolean(String columnLabel) throws SQLException {
        logMethod("getBoolean", columnLabel);
        return false;
    }

    @Override
    public byte getByte(String columnLabel) throws SQLException {
        logMethod("getByte", columnLabel);
        return 0;
    }

    @Override
    public short getShort(String columnLabel) throws SQLException {
        logMethod("getShort", columnLabel);
        return 0;
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        logMethod("getInt", columnLabel);
        return 0;
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        logMethod("getLong", columnLabel);
        return 0;
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        logMethod("getFloat", columnLabel);
        return 0;
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        logMethod("getDouble", columnLabel);
        return 0;
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
        logMethod("getBigDecimal", columnLabel, scale);
        return null;
    }

    @Override
    public byte[] getBytes(String columnLabel) throws SQLException {
        logMethod("getBytes", columnLabel);
        return null;
    }

    @Override
    public Date getDate(String columnLabel) throws SQLException {
        logMethod("getDate", columnLabel);
        return null;
    }

    @Override
    public Time getTime(String columnLabel) throws SQLException {
        logMethod("getTime", columnLabel);
        return null;
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        logMethod("getTimestamp", columnLabel);
        return null;
    }

    @Override
    public InputStream getAsciiStream(String columnLabel) throws SQLException {
        logMethod("getAsciiStream", columnLabel);
        return null;
    }

    @Override
    public InputStream getUnicodeStream(String columnLabel) throws SQLException {
        logMethod("getUnicodeStream", columnLabel);
        return null;
    }

    @Override
    public InputStream getBinaryStream(String columnLabel) throws SQLException {
        logMethod("getBinaryStream", columnLabel);
        return null;
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
    public String getCursorName() throws SQLException {
        logMethod("getCursorName");
        return null;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        logMethod("getMetaData");
        return null;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        logMethod("getObject", columnIndex);
        return null;
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        logMethod("getObject", columnLabel);
        return null;
    }

    @Override
    public int findColumn(String columnLabel) throws SQLException {
        logMethod("findColumn", columnLabel);
        return 0;
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        logMethod("getCharacterStream", columnIndex);
        return null;
    }

    @Override
    public Reader getCharacterStream(String columnLabel) throws SQLException {
        logMethod("getCharacterStream", columnLabel);
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        logMethod("getBigDecimal", columnIndex);
        return null;
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        logMethod("getBigDecimal", columnLabel);
        return null;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        logMethod("isBeforeFirst");
        return false;
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        logMethod("isAfterLast");
        return false;
    }

    @Override
    public boolean isFirst() throws SQLException {
        logMethod("isFirst");
        return false;
    }

    @Override
    public boolean isLast() throws SQLException {
        logMethod("isLast");
        return false;
    }

    @Override
    public void beforeFirst() throws SQLException {
        logMethod("beforeFirst");

    }

    @Override
    public void afterLast() throws SQLException {
        logMethod("afterLast");

    }

    @Override
    public boolean first() throws SQLException {
        logMethod("first");
        return false;
    }

    @Override
    public boolean last() throws SQLException {
        logMethod("last");
        return false;
    }

    @Override
    public int getRow() throws SQLException {
        logMethod("getRow");
        return 0;
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        logMethod("absolute", row);
        return false;
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        logMethod("relative", rows);
        return false;
    }

    @Override
    public boolean previous() throws SQLException {
        logMethod("previous");
        return false;
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
    public int getType() throws SQLException {
        logMethod("getType");
        return 0;
    }

    @Override
    public int getConcurrency() throws SQLException {
        logMethod("getConcurrency");
        return 0;
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        logMethod("rowUpdated");
        return false;
    }

    @Override
    public boolean rowInserted() throws SQLException {
        logMethod("rowInserted");
        return false;
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        logMethod("rowDeleted");
        return false;
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        logMethod("updateNull", columnIndex);

    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        logMethod("updateBoolean", columnIndex, x);

    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        logMethod("updateByte", columnIndex, x);

    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        logMethod("updateShort", columnIndex, x);

    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        logMethod("updateInt", columnIndex, x);

    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        logMethod("updateLong", columnIndex, x);

    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        logMethod("updateFloat", columnIndex, x);

    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        logMethod("updateDouble", columnIndex, x);

    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
        logMethod("updateBigDecimal", columnIndex, x);

    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        logMethod("updateString", columnIndex, x);

    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        logMethod("updateBytes", columnIndex, x);

    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        logMethod("updateDate", columnIndex, x);

    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        logMethod("updateTime", columnIndex, x);

    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
        logMethod("updateTimestamp", columnIndex, x);

    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
        logMethod("updateAsciiStream", columnIndex, x, length);

    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
        logMethod("updateBinaryStream", columnIndex, x, length);

    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
        logMethod("updateCharacterStream", columnIndex, x, length);

    }

    @Override
    public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
        logMethod("updateObject", columnIndex, x, scaleOrLength);

    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        logMethod("updateObject", columnIndex, x);

    }

    @Override
    public void updateNull(String columnLabel) throws SQLException {
        logMethod("updateNull", columnLabel);

    }

    @Override
    public void updateBoolean(String columnLabel, boolean x) throws SQLException {
        logMethod("updateBoolean", columnLabel, x);

    }

    @Override
    public void updateByte(String columnLabel, byte x) throws SQLException {
        logMethod("updateByte", columnLabel, x);

    }

    @Override
    public void updateShort(String columnLabel, short x) throws SQLException {
        logMethod("updateShort", columnLabel, x);

    }

    @Override
    public void updateInt(String columnLabel, int x) throws SQLException {
        logMethod("updateInt", columnLabel, x);

    }

    @Override
    public void updateLong(String columnLabel, long x) throws SQLException {
        logMethod("updateLong", columnLabel, x);

    }

    @Override
    public void updateFloat(String columnLabel, float x) throws SQLException {
        logMethod("updateFloat", columnLabel, x);

    }

    @Override
    public void updateDouble(String columnLabel, double x) throws SQLException {
        logMethod("updateDouble", columnLabel, x);

    }

    @Override
    public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
        logMethod("updateBigDecimal", columnLabel, x);

    }

    @Override
    public void updateString(String columnLabel, String x) throws SQLException {
        logMethod("updateString", columnLabel, x);

    }

    @Override
    public void updateBytes(String columnLabel, byte[] x) throws SQLException {
        logMethod("updateBytes", columnLabel, x);

    }

    @Override
    public void updateDate(String columnLabel, Date x) throws SQLException {
        logMethod("updateDate", columnLabel, x);

    }

    @Override
    public void updateTime(String columnLabel, Time x) throws SQLException {
        logMethod("updateTime", columnLabel, x);

    }

    @Override
    public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
        logMethod("updateTimestamp", columnLabel, x);

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
        logMethod("updateAsciiStream", columnLabel, x, length);

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
        logMethod("updateBinaryStream", columnLabel, x, length);

    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
        logMethod("updateCharacterStream", columnLabel, reader, length);

    }

    @Override
    public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
        logMethod("updateObject", columnLabel, x, scaleOrLength);

    }

    @Override
    public void updateObject(String columnLabel, Object x) throws SQLException {
        logMethod("updateObject", columnLabel, x);

    }

    @Override
    public void insertRow() throws SQLException {
        logMethod("insertRow");

    }

    @Override
    public void updateRow() throws SQLException {
        logMethod("updateRow");

    }

    @Override
    public void deleteRow() throws SQLException {
        logMethod("deleteRow");

    }

    @Override
    public void refreshRow() throws SQLException {
        logMethod("refreshRow");

    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        logMethod("cancelRowUpdates");

    }

    @Override
    public void moveToInsertRow() throws SQLException {
        logMethod("moveToInsertRow");

    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        logMethod("moveToCurrentRow");

    }

    @Override
    public Statement getStatement() throws SQLException {
        logMethod("getStatement");
        return null;
    }

    @Override
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
        logMethod("getObject", columnIndex, map);
        return null;
    }

    @Override
    public Ref getRef(int columnIndex) throws SQLException {
        logMethod("getRef", columnIndex);
        return null;
    }

    @Override
    public Blob getBlob(int columnIndex) throws SQLException {
        logMethod("getBlob", columnIndex);
        return null;
    }

    @Override
    public Clob getClob(int columnIndex) throws SQLException {
        logMethod("getClob", columnIndex);
        return null;
    }

    @Override
    public Array getArray(int columnIndex) throws SQLException {
        logMethod("getArray", columnIndex);
        return null;
    }

    @Override
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
        logMethod("getObject", columnLabel, map);
        return null;
    }

    @Override
    public Ref getRef(String columnLabel) throws SQLException {
        logMethod("getRef", columnLabel);
        return null;
    }

    @Override
    public Blob getBlob(String columnLabel) throws SQLException {
        logMethod("getBlob", columnLabel);
        return null;
    }

    @Override
    public Clob getClob(String columnLabel) throws SQLException {
        logMethod("getClob", columnLabel);
        return null;
    }

    @Override
    public Array getArray(String columnLabel) throws SQLException {
        logMethod("getArray", columnLabel);
        return null;
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        logMethod("getDate", columnIndex, cal);
        return null;
    }

    @Override
    public Date getDate(String columnLabel, Calendar cal) throws SQLException {
        logMethod("getDate", columnLabel, cal);
        return null;
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        logMethod("getTime", columnIndex, cal);
        return null;
    }

    @Override
    public Time getTime(String columnLabel, Calendar cal) throws SQLException {
        logMethod("getTime", columnLabel, cal);
        return null;
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
        logMethod("getTimestamp", columnIndex, cal);
        return null;
    }

    @Override
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
        logMethod("getTimestamp", columnLabel, cal);
        return null;
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        logMethod("getURL", columnIndex);
        return null;
    }

    @Override
    public URL getURL(String columnLabel) throws SQLException {
        logMethod("getURL", columnLabel);
        return null;
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        logMethod("updateRef", columnIndex, x);

    }

    @Override
    public void updateRef(String columnLabel, Ref x) throws SQLException {
        logMethod("updateRef", columnLabel, x);

    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        logMethod("updateBlob", columnIndex, x);

    }

    @Override
    public void updateBlob(String columnLabel, Blob x) throws SQLException {
        logMethod("updateBlob", columnLabel, x);

    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        logMethod("updateClob", columnIndex, x);

    }

    @Override
    public void updateClob(String columnLabel, Clob x) throws SQLException {
        logMethod("updateClob", columnLabel, x);

    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        logMethod("updateArray", columnIndex, x);

    }

    @Override
    public void updateArray(String columnLabel, Array x) throws SQLException {
        logMethod("updateArray", columnLabel, x);

    }

    @Override
    public RowId getRowId(int columnIndex) throws SQLException {
        logMethod("getRowId", columnIndex);
        return null;
    }

    @Override
    public RowId getRowId(String columnLabel) throws SQLException {
        logMethod("getRowId", columnLabel);
        return null;
    }

    @Override
    public void updateRowId(int columnIndex, RowId x) throws SQLException {
        logMethod("updateRowId", columnIndex, x);

    }

    @Override
    public void updateRowId(String columnLabel, RowId x) throws SQLException {
        logMethod("updateRowId", columnLabel, x);

    }

    @Override
    public int getHoldability() throws SQLException {
        logMethod("getHoldability");
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        logMethod("isClosed");
        return false;
    }

    @Override
    public void updateNString(int columnIndex, String nString) throws SQLException {
        logMethod("updateNString", columnIndex, nString);

    }

    @Override
    public void updateNString(String columnLabel, String nString) throws SQLException {
        logMethod("updateNString", columnLabel, nString);

    }

    @Override
    public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
        logMethod("updateNClob", columnIndex, nClob);

    }

    @Override
    public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
        logMethod("updateNClob", columnLabel, nClob);

    }

    @Override
    public NClob getNClob(int columnIndex) throws SQLException {
        logMethod("getNClob", columnIndex);
        return null;
    }

    @Override
    public NClob getNClob(String columnLabel) throws SQLException {
        logMethod("getNClob", columnLabel);
        return null;
    }

    @Override
    public SQLXML getSQLXML(int columnIndex) throws SQLException {
        logMethod("getSQLXML", columnIndex);
        return null;
    }

    @Override
    public SQLXML getSQLXML(String columnLabel) throws SQLException {
        logMethod("getSQLXML", columnLabel);
        return null;
    }

    @Override
    public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
        logMethod("updateSQLXML", columnIndex, xmlObject);

    }

    @Override
    public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
        logMethod("updateSQLXML", columnLabel, xmlObject);

    }

    @Override
    public String getNString(int columnIndex) throws SQLException {
        logMethod("getNString", columnIndex);
        return null;
    }

    @Override
    public String getNString(String columnLabel) throws SQLException {
        logMethod("getNString", columnLabel);
        return null;
    }

    @Override
    public Reader getNCharacterStream(int columnIndex) throws SQLException {
        logMethod("getNCharacterStream", columnIndex);
        return null;
    }

    @Override
    public Reader getNCharacterStream(String columnLabel) throws SQLException {
        logMethod("getNCharacterStream", columnLabel);
        return null;
    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        logMethod("updateNCharacterStream", columnIndex, x, length);

    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        logMethod("updateNCharacterStream", columnLabel, reader, length);

    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
        logMethod("updateAsciiStream", columnIndex, x, length);

    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
        logMethod("updateBinaryStream", columnIndex, x, length);

    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
        logMethod("updateCharacterStream", columnIndex, x, length);

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
        logMethod("updateAsciiStream", columnLabel, x, length);

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
        logMethod("updateBinaryStream", columnLabel, x, length);

    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
        logMethod("updateCharacterStream", columnLabel, reader, length);

    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
        logMethod("updateBlob", columnIndex, inputStream, length);

    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
        logMethod("updateBlob", columnLabel, inputStream, length);

    }

    @Override
    public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
        logMethod("updateClob", columnIndex, reader, length);

    }

    @Override
    public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
        logMethod("updateClob", columnLabel, reader, length);

    }

    @Override
    public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
        logMethod("updateNClob", columnIndex, reader, length);

    }

    @Override
    public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
        logMethod("updateNClob", columnLabel, reader, length);

    }

    @Override
    public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
        logMethod("updateNCharacterStream", columnIndex, x);

    }

    @Override
    public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
        logMethod("updateNCharacterStream", columnLabel, reader);

    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
        logMethod("updateAsciiStream", columnIndex, x);

    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
        logMethod("updateBinaryStream", columnIndex, x);

    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
        logMethod("updateCharacterStream", columnIndex, x);

    }

    @Override
    public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
        logMethod("updateAsciiStream", columnLabel, x);

    }

    @Override
    public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
        logMethod("updateBinaryStream", columnLabel, x);

    }

    @Override
    public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
        logMethod("updateCharacterStream", columnLabel, reader);

    }

    @Override
    public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
        logMethod("updateBlob", columnIndex, inputStream);

    }

    @Override
    public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
        logMethod("updateBlob", columnLabel, inputStream);

    }

    @Override
    public void updateClob(int columnIndex, Reader reader) throws SQLException {
        logMethod("updateClob", columnIndex, reader);

    }

    @Override
    public void updateClob(String columnLabel, Reader reader) throws SQLException {
        logMethod("updateClob", columnLabel, reader);

    }

    @Override
    public void updateNClob(int columnIndex, Reader reader) throws SQLException {
        logMethod("updateNClob", columnIndex, reader);

    }

    @Override
    public void updateNClob(String columnLabel, Reader reader) throws SQLException {
        logMethod("updateNClob", columnLabel, reader);

    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        logMethod("getObject", columnIndex, type);
        return null;
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        logMethod("getObject", columnLabel, type);
        return null;
    }

}
