/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.timetracker.report.dbhandler;

import com.cronos.timetracker.report.BaseTimeTrackerReportTest;
import com.cronos.timetracker.report.Column;
import com.cronos.timetracker.report.EqualityFilter;
import com.cronos.timetracker.report.FilterCategory;
import com.cronos.timetracker.report.RangeFilter;
import com.cronos.timetracker.report.ReportCategory;
import com.cronos.timetracker.report.ReportConfiguration;
import com.cronos.timetracker.report.ReportConfigurationException;
import com.cronos.timetracker.report.ReportType;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * This class contains the unit tests for {@link InformixDBHandler}. Additional test cases are contained in {@link
 * InformixDBHandlerAccuracyTest}
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class InformixDBHandlerTest extends BaseTimeTrackerReportTest {
    /**
     * This is the {@link InformixDBHandler} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private InformixDBHandler informixDBHandler;
    /**
     * This is the {@link ReportConfiguration} instance used in the TIME_EXPENSE test cases. It is instantiated in
     * {@link #setUp()}.
     */
    private ReportConfiguration timeExpenseConfig;

    /**
     * This is the {@link ReportConfiguration} instance used in the TIME and EXPENSE test cases. It is instantiated in
     * {@link #setUp()}.
     */
    private ReportConfiguration reportConfiguration;

    /**
     * This is the {@link RangeFilter} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private RangeFilter dateFilter;

    /**
     * This is the {@link EqualityFilter} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private EqualityFilter billableFilter;

    /**
     * This is an empty {@link List} instance used in the test cases (where it will be filled). It is instantiated in
     * {@link #setUp()}.
     */
    private List filters;

    /**
     * This method tests the correct behavior of {@link InformixDBHandler#InformixDBHandler()}.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testCtor() throws Exception {
        new InformixDBHandler();
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: invalid sql in queries configuration
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailInvalidSQL() throws Exception {
        loadQueriesConfiguration("Queries_invalidSQL.xml");
        try {
            new InformixDBHandler().getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#InformixDBHandler()}.
     * <p/>
     * <b>Failure reason</b>: no config namespace in CM
     */
    public void testNoArgCtorFailureNoConfig() {
        try {
            clearConfig();
            new InformixDBHandler();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#InformixDBHandler()}.
     * <p/>
     * <b>Failure reason</b>: a baseQuery is missing in CM
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testNoArgCtorFailureBaseQueryMissing() throws Exception {
        try {
            loadQueriesConfiguration("Queries_baseQueryMissing.xml");
            new InformixDBHandler();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#InformixDBHandler()}.
     * <p/>
     * <b>Failure reason</b>: a baseQuery is <tt>null</tt> in CM
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testNoArgCtorFailureBaseQueryNull() throws Exception {
        try {
            loadQueriesConfiguration("Queries_baseQueryNull.xml");
            new InformixDBHandler();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#InformixDBHandler()}.
     * <p/>
     * <b>Failure reason</b>: a baseQuery is an empty String in CM
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testNoArgCtorFailureBaseQueryEmpty() throws Exception {
        try {
            loadQueriesConfiguration("Queries_baseQueryEmpty.xml");
            new InformixDBHandler();
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct behavior of {@link InformixDBHandler#release(java.sql.ResultSet)}.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRelease() throws Exception {
        final ResultSet reportData = informixDBHandler.getReportData(reportConfiguration);
        informixDBHandler.release(reportData);
        try {
            reportData.next();
            fail("should throw");
        } catch (SQLException expected) {
            //expected as resultset should be closed
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#release(java.sql.ResultSet)}.
     * <p/>
     * <b>Failure reason</b>: resultset is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testReleaseFailNullResultset() throws Exception {
        try {
            new InformixDBHandler().release(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: missing filter query
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailMissingFilterQuery() throws Exception {
        try {
            loadQueriesConfiguration("Queries_missingFilterQuery.xml");
            filters.add(billableFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: configured filter query is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNullFilterQuery() throws Exception {
        try {
            loadQueriesConfiguration("Queries_nullFilterQuery.xml");
            filters.add(billableFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: configured filter query is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailEmptyFilterQuery() throws Exception {
        try {
            loadQueriesConfiguration("Queries_emptyFilterQuery.xml");
            filters.add(billableFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: configured filter query contains invalid SQL
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailInvalidSQLInFilterQuery() throws Exception {
        try {
            loadQueriesConfiguration("Queries_invalidSQLInFilterQuery.xml");
            billableFilter.addFilterValue("1");
            filters.add(billableFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: configured namespace does not exist
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNonexistingNamespace() throws Exception {
        try {
            informixDBHandler.getReportData(new ReportConfiguration(ReportCategory.EXPENSE, ReportType.CLIENT, "argh"));
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for DBConnectionFactory namespace does not exist
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNoDBNamespace() throws Exception {
        try {
            loadDefaultConfiguration("Default_noDBCFNS.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for DBConnectionFactory namespace is null
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNullDBNamespace() throws Exception {
        try {
            loadDefaultConfiguration("Default_nullDBCFNS.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for DBConnectionFactory namespace is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailEmptyDBNamespace() throws Exception {
        try {
            loadDefaultConfiguration("Default_emptyDBCFNS.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for DBConnectionFactory namespace refers to unknown namespace
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailUnkownDBNamespace() throws Exception {
        try {
            loadDefaultConfiguration("Default_unknownDBCFNS.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for Connection value refers to unknown namespace
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailUnkownConnection() throws Exception {
        try {
            loadDefaultConfiguration("Default_unknownConnection.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for Connection does not exist
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNoDBConnection() throws Exception {
        try {
            loadDefaultConfiguration("Default_noConnection.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for Connection is null
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNullDBConnection() throws Exception {
        try {
            loadDefaultConfiguration("Default_nullConnection.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for Connection is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailEmptyDBConnection() throws Exception {
        try {
            loadDefaultConfiguration("Default_emptyConnection.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: {@link com.topcoder.db.connectionfactory.DBConnectionFactory} config is invalid
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailInvalidDBConnectionConfig() throws Exception {
        try {
            loadInformixConfiguration("InformixConnection_invalidConfig.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: {@link com.topcoder.db.connectionfactory.DBConnectionFactory} refers to unknown server
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailDBConnectionConfigNoConnect() throws Exception {
        try {
            loadInformixConfiguration("InformixConnection_invalidDatabaseURL.xml");
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: BaseQuery for {@link ReportCategory#TIMEEXPENSE} contains no UNION
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailTEBaseQueryNoUnion() throws Exception {
        try {
            loadQueriesConfiguration("Queries_TEQueryNoUnion.xml");
            new InformixDBHandler().getReportData(timeExpenseConfig);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: BaseQuery for {@link ReportCategory#TIMEEXPENSE} contains multiple UNION
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailTEBaseQueryMultipleUnion() throws Exception {
        try {
            loadQueriesConfiguration("Queries_TEQueryMultipleUnion.xml");
            new InformixDBHandler().getReportData(timeExpenseConfig);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: FilterQuery for {@link ReportCategory#TIMEEXPENSE} contains no UNION
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailTEFilterQueryNoUnion() throws Exception {
        try {
            loadQueriesConfiguration("Queries_TEFilterQueryNoUnion.xml");
            filters.add(billableFilter);
            timeExpenseConfig.setFilters(filters);
            new InformixDBHandler().getReportData(timeExpenseConfig);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: FilterQuery for {@link ReportCategory#TIMEEXPENSE} contains multiple UNION
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailTEFilterQueryMultipleUnion() throws Exception {
        try {
            loadQueriesConfiguration("Queries_TEFilterQueryMultipleUnion.xml");
            filters.add(billableFilter);
            timeExpenseConfig.setFilters(filters);
            new InformixDBHandler().getReportData(timeExpenseConfig);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for column type of {@link Column#DATE} refers to unknown {@link ColumnType}
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailUnkownColumnType() throws Exception {
        try {
            loadColumnTypesConfiguration("ColumnTypes_unknownColumnType.xml");
            filters.add(dateFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for column type of {@link Column#DATE} does not exist
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNoColumnType() throws Exception {
        try {
            loadColumnTypesConfiguration("ColumnTypes_missingColumnType.xml");
            filters.add(dateFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for column type of {@link Column#DATE} is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailNullColumnType() throws Exception {
        try {
            loadColumnTypesConfiguration("ColumnTypes_nullColumnType.xml");
            filters.add(dateFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link InformixDBHandler#getReportData(ReportConfiguration)}.
     * <p/>
     * <b>Failure reason</b>: property for column type of {@link Column#DATE} is empty String
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testGetReportDataFailEmptyColumnType() throws Exception {
        try {
            loadColumnTypesConfiguration("ColumnTypes_emptyColumnType.xml");
            filters.add(dateFilter);
            reportConfiguration.setFilters(filters);
            informixDBHandler.getReportData(reportConfiguration);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method clears the {@link #QUERIES_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadQueriesConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(QUERIES_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method clears the {@link #COLUMN_TYPES_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadColumnTypesConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(COLUMN_TYPES_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method clears the {@link #DEFAULT_CONFIGURATION_NAMESPACE} and loads the file with the given name into
     * {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadDefaultConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(DEFAULT_CONFIGURATION_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method clears the {@link #INFORMIX_DB_CONNECTION_FACTORY_NAMESPACE} and loads the file with the given name
     * into {@link ConfigManager} afterwards.
     *
     * @param filename the name of the configuration file to be loaded
     *
     * @throws ConfigManagerException in case an unexpected error occurs
     */
    private void loadInformixConfiguration(final String filename) throws ConfigManagerException {
        removeNamespace(INFORMIX_DB_CONNECTION_FACTORY_NAMESPACE);
        ConfigManager.getInstance().add("configuration/" + filename);
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        filters = new ArrayList();
        billableFilter = new EqualityFilter(Column.BILLABLE, FilterCategory.BILLABLE);
        dateFilter = new RangeFilter(Column.DATE, FilterCategory.DATE);
        informixDBHandler = new InformixDBHandler();
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE,
            ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        timeExpenseConfig = new ReportConfiguration(ReportCategory.TIMEEXPENSE, ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
    }
}
