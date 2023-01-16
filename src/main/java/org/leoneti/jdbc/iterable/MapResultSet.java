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
package org.leoneti.jdbc.iterable;

import static org.leoneti.jdbc.redash.RedashResultSet.datetime;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.leoneti.jdbc.GenericResultSet;

public class MapResultSet extends GenericResultSet {

    private Iterable<Map<String, Object>> rows;
    private Iterator<Map<String, Object>> it;
    private MapResultSetMetaData metadata;

    private Map<String, Object> currentObj;
    private Object lastObj;

    public MapResultSet(boolean trace, Iterable<Map<String, Object>> rows, Map<String, String> types) {
        super(trace, MapResultSet.class);
        this.rows = rows;
        this.it = this.rows.iterator();
        this.metadata = new MapResultSetMetaData(trace, types);
    }

    @Override
    public boolean next() throws SQLException {
        if (it.hasNext()) {
            this.currentObj = (Map<String, Object>) it.next();
            return true;
        }
        return false;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        logMethod("getMetaData");
        return this.metadata;
    }

    private Object readObject(String columnLabel) {
        // System.out.println( currentObj.toString() );
        if (currentObj instanceof Map<?, ?>) {
            this.lastObj = ((Map<?, ?>) currentObj).get(columnLabel);
        } else if (currentObj instanceof JSONObject) {
            this.lastObj = ((JSONObject) currentObj).get(columnLabel);
        } else {
            this.lastObj = currentObj;
        }
        return this.lastObj;
    }

    @Override
    public Object getObject(String columnLabel) throws SQLException {
        logMethod("getObject", columnLabel);
        readObject(columnLabel);
        if (this.lastObj != null && this.metadata.getColumnType(columnLabel) == Types.TIMESTAMP && !(this.lastObj instanceof Timestamp)) {
            try {
                this.lastObj = new Timestamp(datetime.parse(this.lastObj.toString()).getTime());
            } catch (ParseException e) {
                throw new SQLException(e);
            }
        }
        return this.lastObj;
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        logMethod("getObject", columnIndex);
        String colname = metadata.getColumnName(columnIndex);
        return getObject(colname);
    }

    @Override
    public Timestamp getTimestamp(String columnLabel) throws SQLException {
        logMethod("getTimestamp", columnLabel);
        try {
            Object obj = readObject(columnLabel);
            if (obj instanceof Timestamp)
                return (Timestamp) obj;
            return new Timestamp(datetime.parse(obj.toString()).getTime());
        } catch (ParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        logMethod("getTimestamp", columnIndex);
        return getTimestamp(metadata.getColumnName(columnIndex));
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
        Object obj = readObject(columnLabel);
        if (obj == null)
            return null;
        return obj.toString();
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return getString(metadata.getColumnName(columnIndex));
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
        int i = (int) currentObj.get(columnLabel);
        this.lastObj = i;
        return i;
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return getInt(metadata.getColumnName(columnIndex));
    }

    @Override
    public long getLong(String columnLabel) throws SQLException {
        long i = (long) currentObj.get(columnLabel);
        this.lastObj = i;
        return i;
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return getLong(metadata.getColumnName(columnIndex));
    }

    @Override
    public float getFloat(String columnLabel) throws SQLException {
        float i = (float) currentObj.get(columnLabel);
        this.lastObj = i;
        return i;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return getFloat(metadata.getColumnName(columnIndex));
    }

    @Override
    public double getDouble(String columnLabel) throws SQLException {
        double i = (double) currentObj.get(columnLabel);
        this.lastObj = i;
        return i;
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return getDouble(metadata.getColumnName(columnIndex));
    }

    @Override
    public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
        BigDecimal i = (BigDecimal) currentObj.get(columnLabel);
        this.lastObj = i;
        return i;
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return getBigDecimal(metadata.getColumnName(columnIndex));
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
        boolean i = (boolean) currentObj.get(columnLabel);
        this.lastObj = i;
        return i;
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        return getBoolean(metadata.getColumnName(columnIndex));
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.lastObj == null;
    }

    @Override
    public void close() throws SQLException {
        this.rows = null;
        this.metadata = null;
        this.currentObj = null;
        this.it = null;
        this.lastObj = null;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.rows == null;
    }

}
