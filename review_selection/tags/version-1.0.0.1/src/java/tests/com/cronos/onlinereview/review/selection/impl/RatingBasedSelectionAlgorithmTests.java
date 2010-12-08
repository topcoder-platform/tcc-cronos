/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.selection.ReviewSelectionConfigurationException;
import com.cronos.onlinereview.review.selection.ReviewSelectionResult;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test cases for class RatingBasedSelectionAlgorithm.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class RatingBasedSelectionAlgorithmTests extends TestCase {
    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config
        ConfigManager.getInstance().add("selection_algorithm_config.xml");
        ConfigManager.getInstance().add("db_connection_factory.xml");
        ConfigManager.getInstance().add("search_bundle_manager.xml");
        executeFile("test_files/clear_db.sql");
        executeFile("test_files/init_db.sql");
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // remove the namespace
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator<?> iter = cm.getAllNamespaces(); iter.hasNext();) {
            String nameSpace = (String) iter.next();
            if (nameSpace.equals("com.topcoder.util.log")) {
                continue;
            }
            cm.removeNamespace(nameSpace);
        }
    }

    /**
     * Tests constructor: RatingBasedSelectionAlgorithm(). No exception is expected.
     */
    public void testCtor() {
        new RatingBasedSelectionAlgorithm();
    }

    /**
     * Tests configure(ConfigurationObject) with valid config. No exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigure1() throws Exception {
        RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild("valid_config1");
        algorithm.configure(config);
    }

    /**
     * Tests configure(ConfigurationObject) with valid config. No exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigure2() throws Exception {
        RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild("valid_config2");
        algorithm.configure(config);
    }

    /**
     * Tests configure(ConfigurationObject) with null config. IllegalArgumentException should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithNullConfig() throws Exception {
        try {
            RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
            algorithm.configure(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. ReviewApplicationProcessorConfigurationException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig1() throws Exception {
        try {
            RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild(
                "invalid_config1");
            algorithm.configure(config);
            fail("Expect ReviewSelectionConfigurationException.");
        } catch (ReviewSelectionConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests configure(ConfigurationObject) with invalid config. ReviewApplicationProcessorConfigurationException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithInvalidConfig2() throws Exception {
        try {
            RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild(
                "invalid_config2");
            algorithm.configure(config);
            fail("Expect ReviewSelectionConfigurationException.");
        } catch (ReviewSelectionConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests selectReviewers(ReviewApplication[]).
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testSelectReviewers() throws Exception {
        RatingBasedSelectionAlgorithm algorithm = new RatingBasedSelectionAlgorithm();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("selection_algorithm_config").getChild("valid_config1");
        algorithm.configure(config);
        ReviewerStatisticsManager reviewerStatisticsManager = mock(ReviewerStatisticsManager.class);
        setFieldValue(algorithm, "reviewerStatisticsManager", reviewerStatisticsManager);

        ReviewApplication application1 = new ReviewApplication();
        application1.setProjectId(1);
        application1.setReviewerId(1);
        application1.setAcceptPrimary(false);
        application1.setApplicationDate(new Date());

        ReviewApplication application2 = new ReviewApplication();
        application2.setProjectId(1);
        application2.setReviewerId(2);
        application2.setAcceptPrimary(false);
        application2.setApplicationDate(new Date());

        ReviewApplication application3 = new ReviewApplication();
        application3.setProjectId(1);
        application3.setReviewerId(3);
        application3.setAcceptPrimary(true);
        application3.setApplicationDate(new Date());

        ReviewApplication[] applications = new ReviewApplication[] {application1, application2, application3};


        // mocking
        double eligibilityPoints = 1.0;
        ReviewerStatistics statistics = new ReviewerStatistics();
        statistics.setStatisticsType(StatisticsType.AVERAGE);
        statistics.setCoverage(0.9);
        statistics.setAccuracy(0.9);
        statistics.setTimelineReliability(0.9);
        statistics.setEligibilityPoints(eligibilityPoints);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(1, 1)).thenReturn(statistics);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(2, 1)).thenReturn(statistics);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(3, 1)).thenReturn(statistics);

        ReviewSelectionResult result = algorithm.selectReviewers(applications);

        // 0.9 - 0.01 * 3 = 0.87
        assertEquals("The primary reviewer id of the result is incorrect.", 3, result.getPrimaryReviewer().getReviewerId());
        assertEquals("The secondary reviewer count of the result is incorrect.", 2, result.getSecondaryReviewers().length);
        assertEquals("The secondary reviewer id of the result is incorrect.", 1, result.getSecondaryReviewers()[0].getReviewerId());
        assertEquals("The secondary reviewer id of the result is incorrect.", 2, result.getSecondaryReviewers()[1].getReviewerId());
    }

    /**
     * <p>
     * Sets to object field value given fieldValue.
     * </p>
     *
     * @param object the object to set field value
     * @param fieldName the field name
     * @param fieldValue the field value to be set
     * @throws Exception if any error occurs
     */
    private void setFieldValue(Object object, String fieldName, Object fieldValue) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, fieldValue);
    }

    /**
     * executes a file.
     * @param string the file name
     * @throws Exception to invoker
     */
    private static void executeFile(String string) throws Exception {
        Class.forName("com.informix.jdbc.IfxDriver");

        Properties props = new Properties();
        props.load(new FileInputStream("test_files/stress/test.properties"));

        Connection conn = DriverManager.getConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));

        BufferedReader br = new BufferedReader(new FileReader(string));
        String line = "";
        while (br.ready()) {
            line += " " + br.readLine();
            if (line.endsWith(";")) {
                PreparedStatement ps = conn.prepareStatement(line);
                try {
                    ps.executeUpdate();
                } catch (SQLException e) {
                    throw e;
                }
                line = "";
            }
        }
        conn.close();
    }
}
