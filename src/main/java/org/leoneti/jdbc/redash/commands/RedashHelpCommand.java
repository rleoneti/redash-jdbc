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
package org.leoneti.jdbc.redash.commands;

import static org.leoneti.jdbc.redash.RedashResource.getString;
import static org.leoneti.jdbc.redash.RedashResource.toCamelCase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.JDBCType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.leoneti.jdbc.iterable.MapResultSet;

public class RedashHelpCommand {

    private JSONArray rows = new JSONArray();

    public RedashHelpCommand() throws SQLException {}

    public ResultSet get() throws SQLException {
        final ArrayList<Map<String, Object>> rows = new ArrayList<Map<String, Object>>(this.rows.length());
        for(RedashCommands cmd : RedashCommands.values()) {
            final Map<String, Object> sc= new LinkedHashMap<>();
            sc.put("COMMAND", cmd.strCmd() );
            sc.put("DESCRIPTION", getString( String.format("%sCommandDescription", toCamelCase(cmd.name()) ) ) );
            rows.add(sc);
        }

        Map<String, JDBCType> rstypes = new LinkedHashMap<>();
        rstypes.put("COMMAND", JDBCType.VARCHAR );
        rstypes.put("DESCRIPTION", JDBCType.VARCHAR );
        return new MapResultSet(false, rows, rstypes);
    }

}
