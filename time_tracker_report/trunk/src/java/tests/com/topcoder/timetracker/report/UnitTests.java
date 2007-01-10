/**
 *
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.cronos.timetracker.report;

import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.cronos.timetracker.report.dbhandler.ColumnTypeTest;
import com.cronos.timetracker.report.dbhandler.DBHandlerFactoryTest;
import com.cronos.timetracker.report.dbhandler.DBHandlerNotFoundExceptionTest;
import com.cronos.timetracker.report.dbhandler.InformixDBHandlerAccuracyTest;
import com.cronos.timetracker.report.dbhandler.InformixDBHandlerTest;
import com.cronos.timetracker.report.dbhandler.ReportSQLExceptionTest;
import com.cronos.timetracker.report.htmlreport.AggregatorTest;
import com.cronos.timetracker.report.htmlreport.ExpenseReportTest;
import com.cronos.timetracker.report.htmlreport.HTMLRenderUtilAccuracyTest;
import com.cronos.timetracker.report.htmlreport.HTMLRenderUtilTest;
import com.cronos.timetracker.report.htmlreport.TimeExpenseReportTest;
import com.cronos.timetracker.report.htmlreport.TimeReportTest;
import com.topcoder.util.config.ConfigManager;
import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

import java.sql.Connection;
import java.util.Iterator;


/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DBHandlerFactoryTest.class);
        suite.addTestSuite(InformixDBHandlerTest.class);
        suite.addTestSuite(InformixDBHandlerAccuracyTest.class);
        suite.addTestSuite(HTMLRenderUtilTest.class);
        suite.addTestSuite(HTMLRenderUtilAccuracyTest.class);
        suite.addTestSuite(AggregatorTest.class);
        suite.addTestSuite(ColumnTest.class);
        suite.addTestSuite(ColumnTypeTest.class);
        suite.addTestSuite(FilterCategoryTest.class);
        suite.addTestSuite(FilterTypeTest.class);
        suite.addTestSuite(ReportCategoryTest.class);
        suite.addTestSuite(ReportTypeTest.class);
        suite.addTestSuite(StyleConstantTest.class);
        suite.addTestSuite(ReportExceptionTest.class);
        suite.addTestSuite(ReportConfigurationExceptionTest.class);
        suite.addTestSuite(ReportNotFoundExceptionTest.class);
        suite.addTestSuite(ReportSQLExceptionTest.class);
        suite.addTestSuite(DBHandlerNotFoundExceptionTest.class);
        suite.addTestSuite(BasicColumnDecoratorTest.class);
        suite.addTestSuite(EqualityFilterTest.class);
        suite.addTestSuite(RangeFilterTest.class);
        suite.addTestSuite(ReportFactoryTest.class);
        suite.addTestSuite(ExpenseReportTest.class);
        suite.addTestSuite(TimeExpenseReportTest.class);
        suite.addTestSuite(TimeReportTest.class);
        suite.addTestSuite(AbstractReportTest.class);
        suite.addTestSuite(ReportDisplayTagTest.class);
        suite.addTestSuite(ProgrammaticallyReportGenerationDemoTest.class);
        return new TimeTrackerDBSetup(suite);
    }

    /**
     * This class is used as a wrapper around the complete {@link UnitTests} test suite. It does insert the expected
     * data into the database and does cleanup the data after all tests of the suite have been run.
     */
    private static final class TimeTrackerDBSetup extends TestSetup {
        /**
         * This is the dataSet holding the test data read from XML file.
         */
        private static IDataSet fullDataSet;
        /**
         * This is the database connection used for inserting test data and cleaning up.
         */
        protected static DatabaseConnection databaseConnection;

        /**
         * Creates a new TimeTrackerDBSetup.
         *
         * @param test the test to be run in this setup
         */
        private TimeTrackerDBSetup(final Test test) {
            super(test);
        }

        /**
         * This method does the test setup.
         *
         * @throws Exception in case some unexpected Exception occurs
         */
        protected void setUp() throws Exception {
            super.setUp();

            //load config for DBConnectionFactoryImpl
            removeNamespace();
            ConfigManager.getInstance().add(BaseTimeTrackerReportTest.DEFAULT_TIME_TRACKER_REPORT_CONFIGURATION);
            final DBConnectionFactoryImpl factory = new DBConnectionFactoryImpl(
                BaseTimeTrackerReportTest.INFORMIX_DB_CONNECTION_FACTORY_NAMESPACE);
            final Connection jdbcConnection = factory.createConnection(
                BaseTimeTrackerReportTest.INFORMIX_CONNECTION_PROPERTY_NAME);

            //write data
            databaseConnection = new DatabaseConnection(jdbcConnection);
            fullDataSet = new FlatXmlDataSet(getClass().getClassLoader().getResourceAsStream(
                BaseTimeTrackerReportTest.DEFAULT_DATABASE_CONTENT_FILE_NAME));
            DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, fullDataSet);
        }

        /**
         * This method does the cleanup after test run.
         *
         * @throws Exception in case some unexpected Exception occurs
         */
        protected void tearDown() throws Exception {
            //clear data
            try {
                DatabaseOperation.DELETE_ALL.execute(databaseConnection, fullDataSet);
            } finally {
                databaseConnection.close();
            }
            removeNamespace();
            super.tearDown();
        }

        /**
         * <p>
         * Removes all namespace.
         * </p>
         * @throws Exception in case some unexpected Exception occurs
         */
        private void removeNamespace() throws Exception {
            ConfigManager cm = ConfigManager.getInstance();
            for (Iterator itor = cm.getAllNamespaces(); itor.hasNext();) {
                cm.removeNamespace((String) itor.next());
            }
        }
    }
}
