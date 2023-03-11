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
package org.leoneti.jdbc.redash.commands;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.leoneti.jdbc.iterable.MapResultSet;
import org.leoneti.jdbc.redash.RedashConnection;
import org.leoneti.jdbc.redash.RedashResource;

public class RedashDataSourcesCommand {
	
    private RedashConnection con;
    
    private JSONArray rows;

	public RedashDataSourcesCommand(RedashConnection con) throws SQLException {
		this.con = con;
		final StringBuffer response = this.con.getRedashHttp().get( "/api/data_sources" );
		//System.out.println( response );
		this.rows = new JSONArray(response.toString());
		if( rows.isEmpty() || rows.length() == 0 ) {
		    throw new SQLException( "Empty /api/data_sources !!" );
		}
	}
	
	public JSONArray get() {
        return rows;
    }
	
	public RedashDataSourcesCommand refresh() throws SQLException {
        final StringBuffer response = this.con.getRedashHttp().get( "/api/data_sources" );
        synchronized(this) {
            this.rows = new JSONArray(response.toString());
        }
        return this;
	}
	
    public ResultSet asResultSet() throws SQLException {
        final ArrayList<Map<String,Object>> rows = new ArrayList<Map<String,Object>>( this.rows.length() );
        for(Object obj : this.rows ) {
        	JSONObject jo = (JSONObject) obj;
            Map<String,Object> sc = new LinkedHashMap<>();
            sc.put("id", jo.getInt("id"));
            sc.put("name", jo.getString("name"));
            sc.put("type", jo.getString("type"));
            sc.put("syntax", jo.getString("syntax"));
            sc.put("paused", jo.getInt("paused"));
            sc.put("pause_reason", jo.get("pause_reason"));
            sc.put("supports_auto_limit", jo.getBoolean("supports_auto_limit"));
            sc.put("view_only", jo.getBoolean("view_only"));
            rows.add( sc );
        }
        Map<String,JDBCType> rstypes = new LinkedHashMap<>();
        rstypes.put("id", JDBCType.INTEGER);
        rstypes.put("name", JDBCType.VARCHAR);
        rstypes.put("type", JDBCType.VARCHAR);
        rstypes.put("syntax", JDBCType.VARCHAR);
        rstypes.put("paused", JDBCType.INTEGER);
        rstypes.put("pause_reason", JDBCType.VARCHAR);
        rstypes.put("supports_auto_limit", JDBCType.BOOLEAN);
        rstypes.put("view_only", JDBCType.BOOLEAN);
        return new MapResultSet(con.isTraced(), rows, rstypes);
    }

    public int getDataSourceId(String ds) throws SQLException {
        if( ds == null ) return 0;
        for(Object x : get() ) {
            if( ((JSONObject)x).getString("name").equalsIgnoreCase(ds) ) {
                return ((JSONObject)x).getInt("id");
            }
        }
        throw new SQLException(String.format(RedashResource.getString("dataSourceNotFount"), ds) );
    }

    public String getDataSourceName(int id) throws SQLException {
        for(Object x : get() ) {
            if( ((JSONObject)x).getInt("id") == id ) {
                return ((JSONObject)x).getString("name");
            }
        }
        throw new SQLException(String.format(RedashResource.getString("dataSourceNotFount"), String.valueOf(id)) );
    }

    public String getDataSourceType(int id) throws SQLException {
        for(Object x : get() ) {
            if( ((JSONObject)x).getInt("id") == id ) {
                return ((JSONObject)x).getString("type");
            }
        }
        throw new SQLException(String.format(RedashResource.getString("dataSourceNotFount"), String.valueOf(id)) );
    }
    
}
