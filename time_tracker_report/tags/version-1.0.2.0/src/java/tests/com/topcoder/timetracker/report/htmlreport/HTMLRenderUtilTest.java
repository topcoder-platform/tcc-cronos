/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.htmlreport;

import com.topcoder.timetracker.report.BaseTimeTrackerReportTest;
import com.topcoder.timetracker.report.BasicColumnDecorator;
import com.topcoder.timetracker.report.Column;
import com.topcoder.timetracker.report.ReportCategory;
import com.topcoder.timetracker.report.ReportConfiguration;
import com.topcoder.timetracker.report.ReportConfigurationException;
import com.topcoder.timetracker.report.ReportType;
import com.topcoder.timetracker.report.dbhandler.DBHandlerFactory;
import com.topcoder.timetracker.report.dbhandler.DBHandlerNotFoundException;
import com.topcoder.timetracker.report.dbhandler.ReportSQLException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;

import java.util.ArrayList;


/**
 * This class contains the unit tests for {@link HTMLRenderUtil}. Additional test cases are contained in {@link
 * HTMLRenderUtilAccuracyTest}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HTMLRenderUtilTest extends BaseTimeTrackerReportTest {
    /**
     * This is an empty {@link HTMLRenderUtil.Aggregator} array instance used in the test cases. It is instantiated in
     * {@link #setUp()}.
     */
    private static final HTMLRenderUtil.Aggregator[] EMPTY_AGGREGATORS = new HTMLRenderUtil.Aggregator[0];

    /**
     * This is the {@link ReportConfiguration} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ReportConfiguration reportConfiguration;
    /**
     * This is an empty {@link ArrayList} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private ArrayList list;
    /**
     * This is the {@link DBHandlerFactory} instance used in the test cases. It is instantiated in {@link #setUp()}.
     */
    private DBHandlerFactory dbHandlerFactory;

    /**
     * This method tests the correct behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTable() throws Exception {
        HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
        HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, null);
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: report configuration is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailNullNamespace() throws Exception {
        try {
            HTMLRenderUtil.renderTable(null, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: dbHandlerFactory is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailNullRenderFactory() throws Exception {
        try {
            HTMLRenderUtil.renderTable(reportConfiguration, null, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: aggregators contains <tt>null</tt> value
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailNullInAggregators() throws Exception {
        try {
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, new HTMLRenderUtil.Aggregator[]{null});
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: dbHandlerFactory is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailUnknownNamespace() throws Exception {
        try {
            clearConfig();
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: the DBHandler name specified in configuration is invalid
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailInvalidDBHandlerName() throws Exception {
        try {
            loadDefaultConfiguration("Default_invalidDBHandler.xml");
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (DBHandlerNotFoundException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: the DBHandler name specified in configuration is empty
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailEmptyDBHandlerName() throws Exception {
        try {
            loadDefaultConfiguration("Default_emptyDBHandler.xml");
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: the DBHandler name specified in configuration is <tt>null</tt>
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailNullDBHandlerName() throws Exception {
        try {
            loadDefaultConfiguration("Default_nullDBHandler.xml");
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: no DBHandler name is specified in configuration
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableFailNoDBHandlerName() throws Exception {
        try {
            loadDefaultConfiguration("Default_noDBHandler.xml");
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (ReportConfigurationException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: aggregator references {@link Column} not part of the
     * {@link java.sql.ResultSet}
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableInvalidColumnInAggregator() throws Exception {
        try {
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory,
                new HTMLRenderUtil.Aggregator[]{new HTMLRenderUtil.Aggregator(Column.HOURS)});
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: aggregator references {@link Column} which contains
     * textual information
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableTextColumnInAggregator() throws Exception {
        try {
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory,
                new HTMLRenderUtil.Aggregator[]{new HTMLRenderUtil.Aggregator(Column.CLIENT)});
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
    }

    /**
     * This method tests the correct failure behavior of {@link HTMLRenderUtil#renderTable(ReportConfiguration,
     * DBHandlerFactory, HTMLRenderUtil.Aggregator[])}.
     * <p/>
     * <b>Failure reason</b>: columns in report configuration references {@link Column}
     * not part of the {@link java.sql.ResultSet}
     *
     * @throws Exception in case an unexpected Exception occurs
     */
    public void testRenderTableInvalidColumnInRenderers() throws Exception {
        try {
            list.add(new BasicColumnDecorator(Column.HOURS.getName(), "Hrs"));
            reportConfiguration.setColumnDecorators(list);
            HTMLRenderUtil.renderTable(reportConfiguration, dbHandlerFactory, EMPTY_AGGREGATORS);
            fail("should throw");
        } catch (ReportSQLException expected) {
            //expected
        }
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
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        reportConfiguration = new ReportConfiguration(ReportCategory.EXPENSE,
            ReportType.CLIENT,
            DEFAULT_CONFIGURATION_NAMESPACE);
        list = new ArrayList();
        dbHandlerFactory = new DBHandlerFactory();
    }
}
