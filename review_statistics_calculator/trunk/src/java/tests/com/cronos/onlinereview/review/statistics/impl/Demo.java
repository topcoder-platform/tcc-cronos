/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.io.File;
import java.util.Iterator;

import org.easymock.EasyMock;

import com.cronos.onlinereview.review.statistics.handler.StatisticsPostCalculationHandler;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

/**
 * <p>
 * Demo test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * <p>
     * The default configuration file path of <code>DBConnectionFactory</code> for test.
     * </p>
     */
    private static final String DB_CONFIG_FILE = "test_files/ConnectionFactory.xml";

    /**
     * <p>
     * The default configuration file path of <code>SearchBundleManager</code> for test.
     * </p>
     */
    private static final String SB_CONFIG_FILE = "test_files/SearchBundleManager.xml";

    /**
     * <p>
     * The default configuration file path of <code>PhaseManager</code> for test.
     * </p>
     */
    private static final String PH_CONFIG_FILE = "test_files/PhaseManager.xml";

    /**
     * <p>
     * The default configuration file path of <code>ReviewManager</code> for test.
     * </p>
     */
    private static final String RM_CONFIG_FILE = "test_files/ReviewManager.xml";

    /**
     * <p>
     * The default configuration file path of <code>ProjectManager</code> for test.
     * </p>
     */
    private static final String PM_CONFIG_FILE = "test_files/ProjectManager.xml";

    /**
     * <p>
     * The default config file path of <code>InformixPersistence</code> for test.
     * </p>
     */
    private static final String IN_CONFIG_FILE = "test_files/InformixPersistence.xml";

    /**
     * Set up test.
     *
     * @throws Exception for JUnit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        // load configuration files
        cm.add(new File(DB_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(SB_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(PH_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(RM_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(PM_CONFIG_FILE).getAbsolutePath());
        cm.add(new File(IN_CONFIG_FILE).getAbsolutePath());

        TestsHelper.updateData(new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()),
            "test_files/insert_DB.sql");
    }

    /**
     * Clean up test.
     *
     * @throws Exception for JUnit
     */
    public void tearDown() throws Exception {
        TestsHelper.updateData(new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName()),
            "test_files/clean_DB.sql");

        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext();) {
            cm.removeNamespace((String) itr.next());
        }
        cm = null;
    }

    /**
     * Demo test of component.
     *
     * @throws Exception for JUnit
     */
    public void test_Demo() throws Exception {
        // Create an instance of ReviewerStatisticsCalculator
        ReviewerStatisticsCalculator reviewerStatisticsCalculator = new ReviewerStatisticsCalculator();

        // Get configuration for ReviewerStatisticsCalculator
        ConfigurationObject config = TestsHelper.loadConfig("config.xml").getChild(
            "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator");

        // Configure the calculator
        reviewerStatisticsCalculator.configure(config);

        // Register post calculation handler
        StatisticsPostCalculationHandler handler = EasyMock.createMock(StatisticsPostCalculationHandler.class);
        reviewerStatisticsCalculator.registerPostCalculationHandler(handler);

        // Unregister this post calculation handler
        reviewerStatisticsCalculator.unregisterPostCalculationHandler(handler);

        // Calculate statistics for project with ID=1,
        // distribute 2 eligibility points among secondary reviewers
        ReviewerStatistics[] reviewerStatistics = reviewerStatisticsCalculator.calculateStatistics(1L, 2);

        assertEquals(3, reviewerStatistics.length);

        double value = 1.0 - (2.5 * 0.04 + 1.2 * 0.02 + 3.5 * 0.02);
        assertEquals(value, reviewerStatistics[0].getTimelineReliability());
        assertEquals(-1.0, reviewerStatistics[0].getAccuracy());
        assertEquals(-1.0, reviewerStatistics[0].getCoverage());

        assertEquals(0.95, reviewerStatistics[1].getTimelineReliability());
        assertEquals(32.0 / 62.0, reviewerStatistics[1].getAccuracy());
        assertEquals(32.0 / 78.0, reviewerStatistics[1].getCoverage());

        assertEquals(1.0, reviewerStatistics[2].getTimelineReliability());
        assertEquals(46.0 / 86.0, reviewerStatistics[2].getAccuracy());
        assertEquals(46.0 / 78.0, reviewerStatistics[2].getCoverage());
    }

}
