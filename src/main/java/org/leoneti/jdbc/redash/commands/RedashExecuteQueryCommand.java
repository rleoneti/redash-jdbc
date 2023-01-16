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

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.leoneti.jdbc.redash.RedashConnection;
import org.leoneti.jdbc.redash.RedashException;
import org.leoneti.jdbc.redash.RedashHttp;

public class RedashExecuteQueryCommand implements Cloneable {

    private boolean cancelExecution = false;
    private RedashQueryCommand queryCommand;
    private RedashHttp rh;

    public RedashExecuteQueryCommand(RedashConnection con) {
        this.queryCommand = con.getQueryCommand();
        this.rh = con.getRedashHttp().copy();
    }

    public int getQueryId() {
        return getQueryCommand().getQueryId();
    }
    
    private RedashHttp getRedashHttp() {
        return rh;
    }
    
    private RedashQueryCommand getQueryCommand() {
        return queryCommand;
    }

    protected boolean isTraced() {
        return queryCommand.isTraced();
    }

    public JSONObject results(int queryId) throws SQLException {
        final StringBuffer response = getRedashHttp().get("/api/queries/%d/results", getQueryId());
        final JSONObject jo = new JSONObject(response.toString());
        return jo;
    }

    public JSONObject resultsByQueryDataId(int latest_query_data_id) throws SQLException {
        final StringBuffer response = getRedashHttp().get("/api/query_results/%d", getQueryId());
        final JSONObject jo = new JSONObject(response.toString());
        return jo;
    }

    public void cancel() {
        this.cancelExecution = true;
    }

    public JSONObject executeQuery(String sql) throws SQLException {
        JSONObject jo = getQueryCommand().updateQuery(sql);

        // {"id":22,"parameters":{},"apply_auto_limit":false,"max_age":0}
        // /api/queries/22/results

        final JSONObject postData = new JSONObject();
        postData.put("id", getQueryId());
        postData.put("parameters", new JSONObject());
        postData.put("apply_auto_limit", false);
        postData.put("max_age", 0);
        if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, postData.toString());
        StringBuffer response = getRedashHttp().post("/api/queries/%d/results", postData.toString(), getQueryId());
        jo = new JSONObject(response.toString());
        if (jo.getJSONObject("job").getInt("status") == 4) {
            throw new SQLException(jo.getJSONObject("job").getString("error"));
        }
        String jobId = jo.getJSONObject("job").getString("id");
        int c = 0;
        while (true) {
            try {
                Thread.sleep((c < 5 ? 1000 : (c < 10 ? 2000 : (c < 15 ? 3000 : 4000))));
            } catch (InterruptedException e) {
                throw new SQLException(e);
            }
            if (this.cancelExecution) {
                this.cancelExecution = false;
                return null;
            }
            response = getRedashHttp().get("/api/jobs/%s", jobId);
            if(isTraced()) Logger.getLogger("redash.jdbc").log(Level.INFO, "RESPONSE: " + response);
            jo = new JSONObject(response.toString());
            if( jo.has("job") ) {
                if (jo.getJSONObject("job").getInt("status") == 3)
                    break;
                if (jo.getJSONObject("job").getInt("status") == 4) {
                    throw new RedashException(jo.getJSONObject("job"));
                }
            } else {
                throw new RedashException(jo);
            }
            c++;
        }
        //this.latest_query_data_id = jo.getJSONObject("job").get("query_result_id");
        return results(getQueryId());
    }

}
