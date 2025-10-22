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
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.leoneti.jdbc.GenericDatabaseMetaData;
import org.leoneti.jdbc.iterable.MapResultSet;
import static org.leoneti.Utils.toJDBCType;

public class RedashDatabaseMetaData extends GenericDatabaseMetaData {

    private RedashConnection con;
    private JSONObject info = new JSONObject();
    private String redashServerVersion;
    private String userName;
    private String userEmail;
    private int userId;
    
    public RedashDatabaseMetaData(RedashConnection con) throws SQLException {
        super(con.isTraced(), RedashDatabaseMetaData.class);
        logMethod("RedashDatabaseMetaData", con);
        
        this.con = con;
        StringBuffer response;
        JSONObject jo;

        response = con.getRedashHttp().get("/api/session");
        jo = new JSONObject(response.toString());
        redashServerVersion = jo.getJSONObject("client_config").getString("version");
        userName = jo.getJSONObject("user").getString("name");
        userEmail = jo.getJSONObject("user").getString("email");
        userId = jo.getJSONObject("user").getInt("id");
    }

    @Override
    public String getUserName() throws SQLException {
        return this.userEmail;
    }
    
    public String getRedashServerVersion() {
        return redashServerVersion;
    }
    
    public String getName() {
        return userName;
    }
    
    public int getUserId() {
		return userId;
	}

    @Override
    public ResultSet getCatalogs() throws SQLException {
        logMethod("getCatalogs");
        
        ArrayList<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(0);
        for(Object obj : con.getDataSourcesCommand().get() ) {
            Map<String,Object> sc = new LinkedHashMap<>();
            sc.put("TABLE_CAT", ((JSONObject)obj).getString("name") );
            rows.add( sc );
        }
        Map<String,JDBCType> types = new LinkedHashMap<>();
        types.put("TABLE_CAT", JDBCType.VARCHAR);
        return new MapResultSet(this.isTraced(), rows, types);
    }
    
    protected void refresh(String catalog, boolean force) throws SQLException {
        try {
            final int dsId = con.getDataSourcesCommand().getDataSourceId(catalog);
            JSONArray joSchema;
            JSONObject jo;
            StringBuffer response;
            if( force ) {
                response = con.getRedashHttp().get( "/api/data_sources/%d/schema?refresh=true",dsId);
                jo = new JSONObject(response.toString());
            } else {
                response = con.getRedashHttp().get( "/api/data_sources/%d/schema",dsId );
                jo = new JSONObject(response.toString());
            }
            if( jo.has("job") ) {
                //System.out.println( jo.toString(2) );
                String jobId = jo.getJSONObject("job").getString("id");
                int c=0;
                while(true) {
                    Thread.sleep( (c<5?1000:(c<10?2000:(c<15?3000:4000))) );
                    response = con.getRedashHttp().get( String.format("/api/jobs/%s", jobId) );
                    jo = new JSONObject(response.toString());
                    //System.out.println( jo.toString(2) );
                    //if (jo.getJSONObject("job").getInt("status") == 3)
                    if( jo.getJSONObject("job").get("status").toString().equalsIgnoreCase("finished") || jo.getJSONObject("job").get("status").toString().equalsIgnoreCase("SUCCESS") || jo.getJSONObject("job").get("status").toString().equals("3") )
                        break;
                    //if (jo.getJSONObject("job").getInt("status") == 4) {
                    if( jo.getJSONObject("job").get("status").toString().equalsIgnoreCase("FAILURE") || jo.getJSONObject("job").get("status").toString().equals("4") ) {
                        throw new RedashException(jo.getJSONObject("job"));
                    }
                    c++;
                }
                //jo = new JSONObject(response.toString());
                if( jo.getJSONObject("job").has("result") ) {
                    joSchema = jo.getJSONObject("job").getJSONArray("result");
                } else {
                	String msg;
                	try {
                		msg = jo.getJSONObject("job").getJSONObject("result_id").getJSONObject("error").getJSONObject("message") +": " +
                    		jo.getJSONObject("job").getJSONObject("result_id").getJSONObject("error").getJSONObject("details");
                	} catch(Exception e) {
                		msg = response.toString();
                	}
                    this.con.setWarning( new SQLWarning( msg ) );
                    joSchema = new JSONArray();
                }
            } else if( jo.has("schema") ) {
                joSchema = jo.getJSONArray("schema");
            } else {
                throw new RedashException("JSON Data not expected:\n" + jo.toString(2) );
            }
            final JSONObject dsInfo = new JSONObject();
            for( Object o : joSchema ) {
                jo = (JSONObject) o;
                String name = jo.getString("name");
                String schema;
                String tbname;
                if( name.contains(".")  ) {
                    String[] tmp = name.split("\\.");
                    schema = tmp[0];
                    tbname = tmp[1];
                } else {
                    schema = RedashConstants.DEFAULT_SCHEMA;
                    tbname = name;
                }
                if( !dsInfo.has(schema) ) 
                    dsInfo.put(schema, new JSONObject() );
                if( !dsInfo.getJSONObject(schema).has(tbname) )
                    dsInfo.getJSONObject(schema).put(tbname, jo.has("columns") ? jo.getJSONArray("columns") : new JSONArray() );
            }
            if( this.info.has(catalog) ) 
                this.info.remove(catalog);
            this.info.put(catalog, dsInfo);
        } catch(InterruptedException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        logMethod_("getSchemas", true);
        return getSchemas(con.getDs(),"%");
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        logMethod("getSchemas", catalog, schemaPattern);
        
        if( !info.has(catalog) ) {
            refresh(catalog, false);
        }
        
        final String curSchemaPattern = schemaPattern != null ? schemaPattern.replaceAll("%", ".*") : null;

        final ArrayList<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();
        for(String schema : this.info.getJSONObject(catalog).keySet() ) {
            if( curSchemaPattern!=null && !schema.matches(curSchemaPattern) ) continue;
            Map<String,Object> sc = new LinkedHashMap<>();
            sc.put("TABLE_SCHEM", schema);
            rows.add( sc );
        }
        final Map<String,JDBCType> types = new LinkedHashMap<>();
        types.put("TABLE_SCHEM", JDBCType.VARCHAR);
        return new MapResultSet(this.isTraced(), rows, types);
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        logMethod("getTables", catalog, schemaPattern, tableNamePattern, types);
        
        if( !info.has(catalog) ) {
            refresh(catalog, false);
        }
        
        final String curSchemaPattern = schemaPattern != null ? schemaPattern.replaceAll("%", ".*") : null;
        final String curTableNamePattern = tableNamePattern != null ? tableNamePattern.replaceAll("%", ".*") : null;
        final ArrayList<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(0);
        final JSONObject dsInfo = this.info.getJSONObject(catalog);
        for(String schema : dsInfo.keySet() ) {
            if( curSchemaPattern!=null && !schema.matches(curSchemaPattern) ) continue;
            for(String table : dsInfo.getJSONObject(schema).keySet() ) {
                if( curTableNamePattern!=null && !table.matches(curTableNamePattern) ) continue;
                Map<String,Object> sc = new LinkedHashMap<>();
                sc.put("TABLE_CAT", (String)null);
                sc.put("TABLE_SCHEM", schema);
                sc.put("TABLE_NAME", table);
                sc.put("TABLE_TYPE", "TABLE");
                sc.put("REMARKS", (String)null);
                rows.add( sc );
            }
        }
        final Map<String,JDBCType> rstypes = new LinkedHashMap<>();
        rstypes.put("TABLE_CAT", JDBCType.VARCHAR);
        rstypes.put("TABLE_SCHEM", JDBCType.VARCHAR);
        rstypes.put("TABLE_NAME", JDBCType.VARCHAR);
        rstypes.put("TABLE_TYPE", JDBCType.VARCHAR);
        rstypes.put("REMARKS", JDBCType.VARCHAR);
        return new MapResultSet(this.isTraced(), rows, rstypes);
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        logMethod("getColumns", catalog, schemaPattern, tableNamePattern, columnNamePattern);
        
        if( !info.has(catalog) ) {
            refresh(catalog, false);
        }

        final String curSchemaPattern = schemaPattern != null ? schemaPattern.replaceAll("%", ".*") : null;
        final String curTableNamePattern = tableNamePattern != null ? tableNamePattern.replaceAll("%", ".*") : null;
        final String curColumnNamePattern = columnNamePattern != null ? columnNamePattern.replaceAll("%", ".*") : null;
        final ArrayList<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(0);
        final JSONObject dsInfo = this.info.getJSONObject(catalog);
        for(String schema : dsInfo.keySet() ) {
            if( curSchemaPattern!=null && !schema.matches(curSchemaPattern) ) continue;
            for(String table : dsInfo.getJSONObject(schema).keySet() ) {
                if( curTableNamePattern!=null && !table.matches(curTableNamePattern) ) continue;
                for(Object column : dsInfo.getJSONObject(schema).getJSONArray(table) ) {
                    final Map<String,Object> sc = new LinkedHashMap<>();
                    String columnName = column.toString();
                    if( column instanceof JSONObject ) {
                        JSONObject columnJson = (JSONObject) column;
                        columnName = columnJson.getString("name");
                        final JDBCType jdbctype = toJDBCType(columnJson.getString("type"));
                        sc.put("DATA_TYPE", jdbctype.getVendorTypeNumber() );
                        sc.put("TYPE_NAME", jdbctype.name() );
                    } else {
                        sc.put("DATA_TYPE", Types.OTHER );
                        sc.put("TYPE_NAME", "UNKNOWN");
                    }
                    if( curColumnNamePattern!=null && !columnName.matches(curColumnNamePattern) ) continue;
                    sc.put("TABLE_CAT", (String)null);
                    sc.put("TABLE_SCHEM", schema);
                    sc.put("TABLE_NAME", table);
                    sc.put("COLUMN_NAME", columnName);
                    sc.put("COLUMN_SIZE", 0);
                    sc.put("BUFFER_LENGTH", 0);
                    sc.put("DECIMAL_DIGITS", 0);
                    sc.put("NUM_PREC_RADIX", 0);
                    sc.put("NULLABLE", 0);
                    sc.put("REMARKS", (String)null);
                    sc.put("COLUMN_DEF", (String)null);
                    sc.put("SQL_DATA_TYPE", 0);
                    sc.put("SQL_DATETIME_SUB", 0);
                    sc.put("CHAR_OCTET_LENGTH", 0);
                    sc.put("ORDINAL_POSITION", dsInfo.getJSONObject(schema).getJSONArray(table).toList().indexOf(column)+1);
                    sc.put("IS_NULLABLE", "");
                    sc.put("SCOPE_CATALOG", (String)null);
                    sc.put("SCOPE_SCHEMA", (String)null);
                    sc.put("SCOPE_TABLE", (String)null);
                    sc.put("SOURCE_DATA_TYPE", (Short)null);
                    sc.put("IS_AUTOINCREMENT", "");
                    sc.put("IS_GENERATEDCOLUMN", "");
                    rows.add( sc );
                }
            }
        }
        final Map<String,JDBCType> rstypes = new LinkedHashMap<>();
        rstypes.put("TABLE_CAT", JDBCType.VARCHAR);
        rstypes.put("TABLE_SCHEM", JDBCType.VARCHAR);
        rstypes.put("TABLE_NAME", JDBCType.VARCHAR);
        rstypes.put("COLUMN_NAME", JDBCType.VARCHAR);
        rstypes.put("DATA_TYPE", JDBCType.INTEGER);
        rstypes.put("TYPE_NAME", JDBCType.VARCHAR);
        rstypes.put("COLUMN_SIZE", JDBCType.INTEGER);
        rstypes.put("BUFFER_LENGTH", JDBCType.INTEGER);
        rstypes.put("DECIMAL_DIGITS", JDBCType.INTEGER);
        rstypes.put("NUM_PREC_RADIX", JDBCType.INTEGER);
        rstypes.put("NULLABLE", JDBCType.INTEGER);
        rstypes.put("REMARKS", JDBCType.VARCHAR);
        rstypes.put("COLUMN_DEF", JDBCType.VARCHAR);
        rstypes.put("SQL_DATA_TYPE", JDBCType.INTEGER);
        rstypes.put("SQL_DATETIME_SUB", JDBCType.INTEGER);
        rstypes.put("CHAR_OCTET_LENGTH", JDBCType.INTEGER);
        rstypes.put("ORDINAL_POSITION", JDBCType.INTEGER);
        rstypes.put("IS_NULLABLE", JDBCType.VARCHAR);
        rstypes.put("SCOPE_CATALOG", JDBCType.VARCHAR);
        rstypes.put("SCOPE_SCHEMA", JDBCType.VARCHAR);
        rstypes.put("SCOPE_TABLE", JDBCType.VARCHAR);
        rstypes.put("SOURCE_DATA_TYPE", JDBCType.INTEGER);
        rstypes.put("IS_AUTOINCREMENT", JDBCType.VARCHAR);
        rstypes.put("IS_GENERATEDCOLUMN", JDBCType.VARCHAR);
        return new MapResultSet(this.isTraced(), rows, rstypes);
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        return "Redash";
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        return redashServerVersion;
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        try {
            return Integer.parseInt( redashServerVersion.split("\\.")[0] );
        } catch(NumberFormatException e) {
            return 1;
        }
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        try {
            return Integer.parseInt( redashServerVersion.split("\\.")[1] );
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String getDriverName() throws SQLException {
        return RedashConstants.DRIVER_NAME;
    }

    @Override
    public String getDriverVersion() throws SQLException {
        return RedashDatabaseMetaData.class.getPackage().getImplementationVersion();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.con;
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern)
            throws SQLException {
        logMethod("getProcedureColumns", catalog, schemaPattern, procedureNamePattern, columnNamePattern);
    	throw new UnsupportedOperationException(String.format("%s DatabaseMetaData.getProcedureColumns(String,String,String,String)", RedashResource.getString("methodNotSupported") ));
    }

    @Override
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema,
            String foreignTable) throws SQLException {
        logMethod("getCrossReference", parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable);
    	throw new UnsupportedOperationException(String.format("%s DatabaseMetaData.getCrossReference(String,String,String,String,String,String)", RedashResource.getString("methodNotSupported") ));
    }

    @Override
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        logMethod("getSuperTypes", catalog, schemaPattern, typeNamePattern);
    	throw new UnsupportedOperationException(String.format("%s DatabaseMetaData.getSuperTypes(String,String,String)", RedashResource.getString("methodNotSupported") ));
    }

    @Override
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        logMethod("getSuperTables", catalog, schemaPattern, tableNamePattern);
    	throw new UnsupportedOperationException(String.format("%s DatabaseMetaData.getSuperTables(String,String,String)", RedashResource.getString("methodNotSupported") ));
    }

    @Override
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        logMethod("getAttributes", catalog, schemaPattern, typeNamePattern, attributeNamePattern);
    	throw new UnsupportedOperationException(String.format("%s DatabaseMetaData.getAttributes(String,String,String,String)", RedashResource.getString("methodNotSupported") ));
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        logMethod("getCatalogTerm");
        // TODO Auto-generated method stub
        return "database"; //"CATALOG" : "database"
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        logMethod("getCatalogSeparator");
        // TODO Auto-generated method stub
        return ".";
    }

    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "Vesion:" + this.redashServerVersion + "\tuserId:" + this.userId + "\tuserName:" + this.userName + "\tuserEmail:" + this.userEmail + "\tinfo:" + info.toString();
    }
}
