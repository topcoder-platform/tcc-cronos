/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.selection.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.cronos.onlinereview.review.selection.ReviewApplicationProcessorConfigurationException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.management.project.ReviewApplication;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.management.resource.ReviewerStatisticsManager;
import com.topcoder.management.resource.StatisticsType;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test cases for class DefaultReviewApplicationProcessor.
 *
 * @author xianwenchen
 * @version 1.0
 */
public class DefaultReviewApplicationProcessorTests extends TestCase {
    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // load config
        ConfigManager.getInstance().add("processor_config.xml");
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
     * Tests constructor: DefaultReviewApplicationProcessor(). No exception is expected.
     */
    public void testCtor() {
        new DefaultReviewApplicationProcessor();
    }

    /**
     * Tests configure(ConfigurationObject) with valid config. No exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigure() throws Exception {
        DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
        processor.configure(config);
    }

    /**
     * Tests configure(ConfigurationObject) with null config. IllegalArgumentException should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testConfigureWithNullConfig() throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            processor.configure(null);
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
    public void testConfigureWithInvalidConfig() throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("processor_config").getChild("invalid_config");
            processor.configure(config);
            fail("Expect ReviewApplicationProcessorConfigurationException.");
        } catch (ReviewApplicationProcessorConfigurationException e) {
            // expect
        }
    }

    /**
     * Tests updateReviewAssignmentStatistics(ReviewApplication[]), no exception is expected. Currently, the
     * implementation of this function is do nothing.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateReviewAssignmentStatistics() throws Exception {
        DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
        processor.configure(config);
        ReviewApplication[] applications = new ReviewApplication[] {new ReviewApplication()};
        processor.updateReviewAssignmentStatistics(applications);
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with 3 ReviewApplications, 1 of which is
     * application for primary reviewer. The statistics of the primary application will be updated after execution. No
     * exception is expected.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatistics() throws Exception {
        DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
        ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
        ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
        processor.configure(config);
        ReviewerStatisticsManager reviewerStatisticsManager = mock(ReviewerStatisticsManager.class);
        setFieldValue(processor, "reviewerStatisticsManager", reviewerStatisticsManager);

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

        // mocking
        double eligibilityPoints = 1.0;
        ReviewerStatistics statistics = new ReviewerStatistics();
        statistics.setStatisticsType(StatisticsType.AVERAGE);
        statistics.setCoverage(0.9);
        statistics.setAccuracy(0.9);
        statistics.setTimelineReliability(0.9);
        statistics.setEligibilityPoints(eligibilityPoints);
        when(reviewerStatisticsManager.getReviewerStatisticsByCompetitionType(3, 1)).thenReturn(statistics);

        ReviewApplication[] applications = new ReviewApplication[] {application1, application2, application3};
        processor.updateUnassignedReviewersStatistics(applications);

        // assert
        assertEquals("Eligibility points should be changed.", eligibilityPoints
            + DefaultReviewApplicationProcessor.DEFAULT_PRIMARY_ELIGIBILITY_POINTS,
            statistics.getEligibilityPoints(), 0.00001);
        verify(reviewerStatisticsManager, times(1)).update(statistics);
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with null application. IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatisticsWithNullApplication() throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
            processor.configure(config);
            processor.updateUnassignedReviewersStatistics(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with empty application. IllegalArgumentException
     * should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatisticsWithEmptyApplication() throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
            processor.configure(config);
            processor.updateUnassignedReviewersStatistics(new ReviewApplication[0]);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with application containing null.
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatisticsWithApplicationsContainingNull() throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
            processor.configure(config);
            ReviewApplication[] applications = new ReviewApplication[] {new ReviewApplication(), null};
            processor.updateUnassignedReviewersStatistics(applications);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with applications containing different project
     * id. IllegalArgumentException should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatisticsWithApplicationsContainingDifferentProjectId()
        throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
            processor.configure(config);
            ReviewApplication application1 = new ReviewApplication();
            application1.setProjectId(1);
            ReviewApplication application2 = new ReviewApplication();
            application2.setProjectId(2);
            ReviewApplication[] applications = new ReviewApplication[] {application1, application2};
            processor.updateUnassignedReviewersStatistics(applications);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with applications containing duplicate reviewer
     * id. IllegalArgumentException should be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatisticsWithApplicationsContainingDuplicateReviewerId()
        throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ConfigurationFileManager manager = new ConfigurationFileManager("conf.properties");
            ConfigurationObject config = manager.getConfiguration("processor_config").getChild("valid_config");
            processor.configure(config);
            ReviewApplication application1 = new ReviewApplication();
            application1.setProjectId(1);
            application1.setReviewerId(1);
            ReviewApplication application2 = new ReviewApplication();
            application2.setProjectId(1);
            application2.setReviewerId(1);
            ReviewApplication[] applications = new ReviewApplication[] {application1, application2};
            processor.updateUnassignedReviewersStatistics(applications);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Tests updateUnassignedReviewersStatistics(ReviewApplication[]) with invalid state. IllegalStateException should
     * be thrown.
     *
     * @throws Exception throw exception to JUnit.
     */
    public void testUpdateUnassignedReviewersStatisticsWithInvalidState() throws Exception {
        try {
            DefaultReviewApplicationProcessor processor = new DefaultReviewApplicationProcessor();
            ReviewApplication[] applications = new ReviewApplication[] {new ReviewApplication()};
            processor.updateUnassignedReviewersStatistics(applications);
            fail("Expect IllegalStateException.");
        } catch (IllegalStateException e) {
            // expect
        }
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
