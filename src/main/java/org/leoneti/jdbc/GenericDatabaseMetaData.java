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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;

import org.leoneti.TraceLog;

public abstract class GenericDatabaseMetaData extends TraceLog implements DatabaseMetaData {

    public GenericDatabaseMetaData() {
        super();
    }

    public GenericDatabaseMetaData(boolean trace, Class<?> clazz) {
        super(trace, clazz);
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
    public boolean allProceduresAreCallable() throws SQLException {
        logMethod("allProceduresAreCallable");
        return false;
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
        logMethod("allTablesAreSelectable");
        return false;
    }

    @Override
    public String getURL() throws SQLException {
        logMethod("getURL");
        return null;
    }

    @Override
    public String getUserName() throws SQLException {
        logMethod("getUserName");
        return null;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        logMethod("isReadOnly");
        return false;
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        logMethod("nullsAreSortedHigh");
        return false;
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        logMethod("nullsAreSortedLow");
        return false;
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        logMethod("nullsAreSortedAtStart");
        return false;
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        logMethod("nullsAreSortedAtEnd");
        return false;
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        logMethod("getDatabaseProductName");
        return null;
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        logMethod("getDatabaseProductVersion");
        return null;
    }

    @Override
    public String getDriverName() throws SQLException {
        logMethod("getDriverName");
        return null;
    }

    @Override
    public String getDriverVersion() throws SQLException {
        logMethod("getDriverVersion");
        return null;
    }

    @Override
    public int getDriverMajorVersion() {
        logMethod("getDriverMajorVersion");
        return 0;
    }

    @Override
    public int getDriverMinorVersion() {
        logMethod("getDriverMinorVersion");
        return 0;
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        logMethod("usesLocalFiles");
        return false;
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        logMethod("usesLocalFilePerTable");
        return false;
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        logMethod("supportsMixedCaseIdentifiers");
        return false;
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        logMethod("storesUpperCaseIdentifiers");
        return false;
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        logMethod("storesLowerCaseIdentifiers");
        return false;
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        logMethod("storesMixedCaseIdentifiers");
        return false;
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        logMethod("supportsMixedCaseQuotedIdentifiers");
        return false;
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        logMethod("storesUpperCaseQuotedIdentifiers");
        return false;
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        logMethod("storesLowerCaseQuotedIdentifiers");
        return false;
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        logMethod("storesMixedCaseQuotedIdentifiers");
        return false;
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        logMethod("getIdentifierQuoteString");
        return null;
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        logMethod("getSQLKeywords");
        return null;
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        logMethod("getNumericFunctions");
        return null;
    }

    @Override
    public String getStringFunctions() throws SQLException {
        logMethod("getStringFunctions");
        return null;
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        logMethod("getSystemFunctions");
        return null;
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        logMethod("getTimeDateFunctions");
        return null;
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        logMethod("getSearchStringEscape");
        return null;
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        logMethod("getExtraNameCharacters");
        return null;
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        logMethod("supportsAlterTableWithAddColumn");
        return false;
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        logMethod("supportsAlterTableWithDropColumn");
        return false;
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        logMethod("supportsColumnAliasing");
        return false;
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        logMethod("nullPlusNonNullIsNull");
        return false;
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        logMethod("supportsConvert");
        return false;
    }

    @Override
    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        logMethod("supportsConvert", fromType, toType);
        return false;
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        logMethod("supportsTableCorrelationNames");
        return false;
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        logMethod("supportsDifferentTableCorrelationNames");
        return false;
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        logMethod("supportsExpressionsInOrderBy");
        return false;
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        logMethod("supportsOrderByUnrelated");
        return false;
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        logMethod("supportsGroupBy");
        return false;
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        logMethod("supportsGroupByUnrelated");
        return false;
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        logMethod("supportsGroupByBeyondSelect");
        return false;
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        logMethod("supportsLikeEscapeClause");
        return false;
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        logMethod("supportsMultipleResultSets");
        return false;
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        logMethod("supportsMultipleTransactions");
        return false;
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        logMethod("supportsNonNullableColumns");
        return false;
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        logMethod("supportsMinimumSQLGrammar");
        return false;
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        logMethod("supportsCoreSQLGrammar");
        return false;
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        logMethod("supportsExtendedSQLGrammar");
        return false;
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        logMethod("supportsANSI92EntryLevelSQL");
        return false;
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        logMethod("supportsANSI92IntermediateSQL");
        return false;
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        logMethod("supportsANSI92FullSQL");
        return false;
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        logMethod("supportsIntegrityEnhancementFacility");
        return false;
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        logMethod("supportsOuterJoins");
        return false;
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        logMethod("supportsFullOuterJoins");
        return false;
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        logMethod("supportsLimitedOuterJoins");
        return false;
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        logMethod("getSchemaTerm");
        return null;
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        logMethod("getProcedureTerm");
        return null;
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        logMethod("getCatalogTerm");
        return null;
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        logMethod("isCatalogAtStart");
        return false;
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        logMethod("getCatalogSeparator");
        return null;
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        logMethod("supportsSchemasInDataManipulation");
        return false;
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        logMethod("supportsSchemasInProcedureCalls");
        return false;
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        logMethod("supportsSchemasInTableDefinitions");
        return false;
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        logMethod("supportsSchemasInIndexDefinitions");
        return false;
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        logMethod("supportsSchemasInPrivilegeDefinitions");
        return false;
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        logMethod("supportsCatalogsInDataManipulation");
        return false;
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        logMethod("supportsCatalogsInProcedureCalls");
        return false;
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        logMethod("supportsCatalogsInTableDefinitions");
        return false;
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        logMethod("supportsCatalogsInIndexDefinitions");
        return false;
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        logMethod("supportsCatalogsInPrivilegeDefinitions");
        return false;
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        logMethod("supportsPositionedDelete");
        return false;
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        logMethod("supportsPositionedUpdate");
        return false;
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        logMethod("supportsSelectForUpdate");
        return false;
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        logMethod("supportsStoredProcedures");
        return false;
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        logMethod("supportsSubqueriesInComparisons");
        return false;
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        logMethod("supportsSubqueriesInExists");
        return false;
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        logMethod("supportsSubqueriesInIns");
        return false;
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        logMethod("supportsSubqueriesInQuantifieds");
        return false;
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        logMethod("supportsCorrelatedSubqueries");
        return false;
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        logMethod("supportsUnion");
        return false;
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        logMethod("supportsUnionAll");
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        logMethod("supportsOpenCursorsAcrossCommit");
        return false;
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        logMethod("supportsOpenCursorsAcrossRollback");
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        logMethod("supportsOpenStatementsAcrossCommit");
        return false;
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        logMethod("supportsOpenStatementsAcrossRollback");
        return false;
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        logMethod("getMaxBinaryLiteralLength");
        return 0;
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        logMethod("getMaxCharLiteralLength");
        return 0;
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        logMethod("getMaxColumnNameLength");
        return 0;
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        logMethod("getMaxColumnsInGroupBy");
        return 0;
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        logMethod("getMaxColumnsInIndex");
        return 0;
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        logMethod("getMaxColumnsInOrderBy");
        return 0;
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        logMethod("getMaxColumnsInSelect");
        return 0;
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        logMethod("getMaxColumnsInTable");
        return 0;
    }

    @Override
    public int getMaxConnections() throws SQLException {
        logMethod("getMaxConnections");
        return 0;
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        logMethod("getMaxCursorNameLength");
        return 0;
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        logMethod("getMaxIndexLength");
        return 0;
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        logMethod("getMaxSchemaNameLength");
        return 0;
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        logMethod("getMaxProcedureNameLength");
        return 0;
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        logMethod("getMaxCatalogNameLength");
        return 0;
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        logMethod("getMaxRowSize");
        return 0;
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        logMethod("doesMaxRowSizeIncludeBlobs");
        return false;
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        logMethod("getMaxStatementLength");
        return 0;
    }

    @Override
    public int getMaxStatements() throws SQLException {
        logMethod("getMaxStatements");
        return 0;
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        logMethod("getMaxTableNameLength");
        return 0;
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        logMethod("getMaxTablesInSelect");
        return 0;
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        logMethod("getMaxUserNameLength");
        return 0;
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        logMethod("getDefaultTransactionIsolation");
        return 0;
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        logMethod("supportsTransactions");
        return false;
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        logMethod("supportsTransactionIsolationLevel", level);
        return false;
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        logMethod("supportsDataDefinitionAndDataManipulationTransactions");
        return false;
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        logMethod("supportsDataManipulationTransactionsOnly");
        return false;
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        logMethod("dataDefinitionCausesTransactionCommit");
        return false;
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        logMethod("dataDefinitionIgnoredInTransactions");
        return false;
    }

    @Override
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        logMethod("getProcedures", catalog, schemaPattern, procedureNamePattern);
        return null;
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        logMethod("getProcedureColumns", catalog, schemaPattern, procedureNamePattern, columnNamePattern);
        return null;
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        logMethod("getTables", catalog, schemaPattern, tableNamePattern, types);
        return null;
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        logMethod("getSchemas");
        return null;
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        logMethod("getCatalogs");
        return null;
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        logMethod("getTableTypes");
        return null;
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        logMethod("getColumns", catalog, schemaPattern, tableNamePattern, columnNamePattern);
        return null;
    }

    @Override
    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        logMethod("getColumnPrivileges", catalog, schema, table, columnNamePattern);
        return null;
    }

    @Override
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        logMethod("getTablePrivileges", catalog, schemaPattern, tableNamePattern);
        return null;
    }

    @Override
    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
        logMethod("getBestRowIdentifier", catalog, schema, table, scope, nullable);
        return null;
    }

    @Override
    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        logMethod("getVersionColumns", catalog, schema, table);
        return null;
    }

    @Override
    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        logMethod("getPrimaryKeys", catalog, schema, table);
        return null;
    }

    @Override
    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        logMethod("getImportedKeys", catalog, schema, table);
        return null;
    }

    @Override
    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        logMethod("getExportedKeys", catalog, schema, table);
        return null;
    }

    @Override
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        logMethod("getCrossReference", parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable);
        return null;
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        logMethod("getTypeInfo");
        return null;
    }

    @Override
    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
        logMethod("getIndexInfo", catalog, schema, table, unique, approximate);
        return null;
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        logMethod("supportsResultSetType", type);
        return false;
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        logMethod("supportsResultSetConcurrency", type, concurrency);
        return false;
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        logMethod("ownUpdatesAreVisible", type);
        return false;
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        logMethod("ownDeletesAreVisible", type);
        return false;
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        logMethod("ownInsertsAreVisible", type);
        return false;
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        logMethod("othersUpdatesAreVisible", type);
        return false;
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        logMethod("othersDeletesAreVisible", type);
        return false;
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        logMethod("othersInsertsAreVisible", type);
        return false;
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        logMethod("updatesAreDetected", type);
        return false;
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        logMethod("deletesAreDetected", type);
        return false;
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        logMethod("insertsAreDetected", type);
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        logMethod("supportsBatchUpdates");
        return false;
    }

    @Override
    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        logMethod("getUDTs", catalog, schemaPattern, typeNamePattern, types);
        return null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        logMethod("getConnection");
        return null;
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        logMethod("supportsSavepoints");
        return false;
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        logMethod("supportsNamedParameters");
        return false;
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        logMethod("supportsMultipleOpenResults");
        return false;
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        logMethod("supportsGetGeneratedKeys");
        return false;
    }

    @Override
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        logMethod("getSuperTypes", catalog, schemaPattern, typeNamePattern);
        return null;
    }

    @Override
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        logMethod("getSuperTables", catalog, schemaPattern, tableNamePattern);
        return null;
    }

    @Override
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        logMethod("getAttributes", catalog, schemaPattern, typeNamePattern, attributeNamePattern);
        return null;
    }

    @Override
    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        logMethod("supportsResultSetHoldability", holdability);
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        logMethod("getResultSetHoldability");
        return 0;
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        logMethod("getDatabaseMajorVersion");
        return 0;
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        logMethod("getDatabaseMinorVersion");
        return 0;
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        logMethod("getJDBCMajorVersion");
        return 0;
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        logMethod("getJDBCMinorVersion");
        return 0;
    }

    @Override
    public int getSQLStateType() throws SQLException {
        logMethod("getSQLStateType");
        return 0;
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        logMethod("locatorsUpdateCopy");
        return false;
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        logMethod("supportsStatementPooling");
        return false;
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        logMethod("getRowIdLifetime");
        return null;
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        logMethod("getSchemas", catalog, schemaPattern);
        return null;
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        logMethod("supportsStoredFunctionsUsingCallSyntax");
        return false;
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        logMethod("autoCommitFailureClosesAllResultSets");
        return false;
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        logMethod("getClientInfoProperties");
        return null;
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        logMethod("getFunctions", catalog, schemaPattern, functionNamePattern);
        return null;
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        logMethod("getFunctionColumns", catalog, schemaPattern, functionNamePattern, columnNamePattern);
        return null;
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        logMethod("getPseudoColumns", catalog, schemaPattern, tableNamePattern, columnNamePattern);
        return null;
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        logMethod("generatedKeyAlwaysReturned");
        return false;
    }

}
