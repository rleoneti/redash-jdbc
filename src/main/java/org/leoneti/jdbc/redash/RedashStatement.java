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
package org.leoneti.jdbc.redash;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.leoneti.jdbc.GenericStatement;
import org.leoneti.jdbc.redash.commands.RedashCommands;
import org.leoneti.jdbc.redash.commands.RedashExecuteQueryCommand;
import org.leoneti.jdbc.redash.commands.RedashHelpCommand;
import org.leoneti.jdbc.redash.commands.RedashUsersCommand;

public class RedashStatement extends GenericStatement {
    
    private RedashConnection con;
    
    private String sql;
    private ResultSet rs;
    private RedashExecuteQueryCommand execQueryCommand;

    public RedashStatement(RedashConnection con) {
        super(con.isTraced(), RedashStatement.class);
        this.con = con;
        this.execQueryCommand = new RedashExecuteQueryCommand(con);
    }

    public RedashStatement(RedashConnection con,String sql) {
        this(con);
        this.sql = sql;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        logMethod("executeQuery", sql);

        if( sql.trim().matches( RedashCommands.QUERIES.regex() ) ) {
            return con.getQueryCommand().get(false, true);
        } else if( sql.trim().matches( RedashCommands.MY_QUERIES.regex() ) || sql.trim().matches( RedashCommands.MYQUERIES.regex() ) ) {
            return con.getQueryCommand().get(false, false);
        } else if( sql.trim().matches( RedashCommands.ALL_QUERIES.regex() ) ) {
            return con.getQueryCommand().get(true, true);
        } else if( sql.trim().matches( RedashCommands.DATA_SOURCES.regex() ) ) {
            return con.getDataSourcesCommand().asResultSet();
        } else if( sql.trim().matches( RedashCommands.USERS.regex() ) || sql.trim().matches( RedashCommands.USER.regex() ) ) {
            return new RedashUsersCommand(con).get();
        } else if( sql.trim().matches( RedashCommands.HELP.regex() ) ) {
            return new RedashHelpCommand().get();
        } else  {
            RedashCommands commandType = null;
            Matcher m = Pattern.compile( RedashCommands.SHOW_QUERY.regex() ).matcher( sql.trim() );
            if( m.find() ) {
                commandType = RedashCommands.SHOW_QUERY;
            } else {
                m = Pattern.compile( RedashCommands.PAGINATOR.regex() ).matcher( sql.trim() );
                if( m.find() ) commandType = RedashCommands.PAGINATOR;
            }
            JSONObject jo;
            if( commandType == RedashCommands.SHOW_QUERY ) {
                final int query_result_id = Integer.parseInt( m.group(1) );
                try {
                    jo = execQueryCommand.results(query_result_id);
                } catch(SQLException sqle) {
                    try {
                        jo = execQueryCommand.resultsByQueryDataId(query_result_id);
                    } catch(Exception e) {
                        throw sqle;
                    }
                }
            } else if( commandType == RedashCommands.PAGINATOR ) {
                return new RedashPaginatorResultSet(con, execQueryCommand, m.group(1), m.group(2), con.getDsType() );
            } else {
                this.sql = sql;
                jo = execQueryCommand.executeQuery(this.sql);
            }
            RedashDriver.log.info( jo.toString() );
            return new RedashResultSet(con.isResultSetTraced(), jo, con.getDsType());
        }
    }
    
    @Override
    public ResultSet executeQuery() throws SQLException {
        logMethod_("executeQuery", true );
        return executeQuery(this.sql);
    }

    @Override
    public boolean execute() throws SQLException {
        logMethod_("execute", true );
        this.rs = executeQuery();
        return this.rs != null;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        logMethod_("execute", true , sql);
        this.sql = sql;
        return execute();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        logMethod("getResultSet");
        return this.rs;
    }

    @Override
    public void close() throws SQLException {
    	this.con = null;
    	this.rs = null;
    	this.sql = null;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        // TODO Auto-generated method stub
        throw new SQLException();
    }

    @Override
    public void cancel() throws SQLException {
        execQueryCommand.cancel();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.con;
    }

    @Override
    public boolean isClosed() throws SQLException { return this.con == null; }
    
}
