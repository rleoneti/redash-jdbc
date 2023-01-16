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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.leoneti.jdbc.iterable.MapResultSet;
import org.leoneti.jdbc.redash.RedashConnection;
import org.leoneti.jdbc.redash.RedashConstants;
import org.leoneti.jdbc.redash.RedashException;

public class RedashUsersCommand {

    private RedashConnection con;

    private JSONArray rows = new JSONArray();

    public RedashUsersCommand(RedashConnection con) throws SQLException {
        this.con = con;
        int page = 1;
        while (true) {
            // &pending=true &disabled=true
            StringBuffer response = this.con.getRedashHttp().get("/api/users?page_size=%d&page=%d", con.pageSize(), page);
            JSONObject jo = new JSONObject(response.toString());
            if (jo.has("message"))
                throw new RedashException(jo);
            for (Object obj : jo.getJSONArray("results"))
                this.rows.put(obj);
            if (jo.getInt(RedashConstants.HTTP_PARAM_COUNT) <= jo.getInt(RedashConstants.HTTP_PARAM_PAGE) * jo.getInt(RedashConstants.HTTP_PARAM_PAGE_SIZE))
                break;
            page++;
        }
    }

    public ResultSet get() throws SQLException {
        final ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>(this.rows.length());
        for (Object obj : this.rows) {
            JSONObject jo = (JSONObject) obj;
            Map<String, Object> sc = new LinkedHashMap<>();
            sc.put("id", jo.getInt("id"));
            sc.put("name", jo.getString("name"));
            sc.put("email", jo.getString("email"));
            sc.put("created_at", jo.getString("created_at"));
            // sc.put("created_at", new Timestamp(
            // datetime.parse(jo.getString("created_at")).getTime() ) );
            sc.put("groups", jo.getJSONArray("groups").toString());
            sc.put("is_disabled", jo.getBoolean("is_disabled"));
            sc.put("disabled_at", jo.isNull("disabled_at") ? null : jo.getString("disabled_at"));
            // sc.put("disabled_at", new Timestamp(
            // datetime.parse(jo.getString("disabled_at")).getTime() ) );
            sc.put("auth_type", jo.getString("auth_type"));
            rows.add(sc);
        }
        Map<String, String> rstypes = new LinkedHashMap<>();
        rstypes.put("id", "int");
        rstypes.put("name", "string");
        rstypes.put("email", "string");
        rstypes.put("created_at", "timestamp");
        rstypes.put("groups", "string");
        rstypes.put("is_disabled", "boolean");
        rstypes.put("disabled_at", "timestamp");
        rstypes.put("auth_type", "string");
        return new MapResultSet(con.isTraced(), rows, rstypes);
    }

}
