/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.impl;

import java.io.File;
import java.util.Iterator;

import org.easymock.EasyMock;

import com.cronos.onlinereview.review.statistics.ProjectNotFoundException;
import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.cronos.onlinereview.review.statistics.StatisticsCalculatorException;
import com.cronos.onlinereview.review.statistics.handler.StatisticsPostCalculationHandler;
import com.cronos.onlinereview.review.statistics.handler.StatisticsPostCalculationHandlerException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.ReviewerStatistics;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for <code>ReviewerStatisticsCalculator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReviewerStatisticsCalculatorUnitTests extends TestCase {
    /**
     * <p>
     * The default config file path of <code>DBConnectionFactory</code> for test.
     * </p>
     */
    private static final String DB_CONFIG_FILE = "test_files/ConnectionFactory.xml";

    /**
     * <p>
     * The default config file path of <code>SearchBundleManager</code> for test.
     * </p>
     */
    private static final String SB_CONFIG_FILE = "test_files/SearchBundleManager.xml";

    /**
     * <p>
     * The default config file path of <code>PhaseManager</code> for test.
     * </p>
     */
    private static final String PH_CONFIG_FILE = "test_files/PhaseManager.xml";

    /**
     * <p>
     * The default config file path of <code>ReviewManager</code> for test.
     * </p>
     */
    private static final String RM_CONFIG_FILE = "test_files/ReviewManager.xml";

    /**
     * <p>
     * The default config file path of <code>ProjectManager</code> for test.
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
     * Configuration object.
     */
    private ConfigurationObject cfgObject;

    /**
     * Set up test.
     *
     * @throws Exception for JUnit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        cfgObject = TestsHelper.loadConfig("config.xml").getChild(
            "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator");

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
     * Accuracy.
     *
     * @throws Exception for JUnit.
     */
    public void test_Ctor() throws Exception {
        ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();

        assertNotNull(instance);
    }

    /**
     * <p>
     * Accuracy test for the method <code>configure(ConfigurationObject)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure() throws Exception {
        cfgObject = TestsHelper.loadConfig("config.xml").getChild(
            "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator");

        ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
        instance.configure(cfgObject);
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method with config <code>null</code>. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_null() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.configure(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertEquals("'config' should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method without timelineReliabilityCalculatorKey. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_no_timelineReliabilityCalculatorKey() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.removeProperty("timelineReliabilityCalculatorKey");
            instance.configure(cfgObject);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method without coverageCalculatorKey. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_no_coverageCalculatorKey() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.removeProperty("coverageCalculatorKey");
            instance.configure(cfgObject);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method without accuracyCalculatorKey. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_no_accuracyCalculatorKey() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.removeProperty("accuracyCalculatorKey");
            instance.configure(cfgObject);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method with an empty
     * timelineReliabilityCalculatorKey . <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_empty_timelineReliabilityCalculatorKey() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.setPropertyValue("timelineReliabilityCalculatorKey", " ");
            instance.configure(cfgObject);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method with an empty coverageCalculatorKey . <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_empty_coverageCalculatorKey() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.setPropertyValue("coverageCalculatorKey", " ");
            instance.configure(cfgObject);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code> method with an empty accuracyCalculatorKey . <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_empty_accuracyCalculatorKey() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.setPropertyValue("accuracyCalculatorKey", " ");
            instance.configure(cfgObject);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code>. ClassCastException. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_ClassCastException() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject = TestsHelper.loadConfig("configError.xml").getChild("classExceptionError");
            instance.configure(cfgObject);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertTrue(e.getCause() instanceof ClassCastException);
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code>. IllegalReferenceException. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_illegalReference() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.getChild("objectFactoryConfig").removeChild("persistence");
            instance.configure(cfgObject);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertTrue(e.getCause() instanceof IllegalReferenceException);
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code>. IllegalArgumentException. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_illegalArgument() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            cfgObject.removeChild("objectFactoryConfig");
            instance.configure(cfgObject);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    /**
     * <p>
     * Tests failure of <code>configure(ConfigurationObject)</code>. InvalidClassSpecificationException. <br>
     * <code>StatisticsCalculatorConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_configure_invalidClassSpecification() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            ConfigManager cm = ConfigManager.getInstance();
            cm.removeNamespace("com.topcoder.management.phase.Default");
            instance.configure(cfgObject);

            fail("StatisticsCalculatorConfigurationException is expected.");
        } catch (StatisticsCalculatorConfigurationException e) {
            // Good
            assertTrue(e.getCause() instanceof InvalidClassSpecificationException);
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>calculateStatistics(long, double)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics() throws Exception {
        ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
        instance.configure(cfgObject);

        instance.calculateStatistics(1L, 2);
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code>. Handler exception. <br>
     * <code>StatisticsCalculatorException</code> is expected.
     * </p>
     *
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_HandlerException() throws Exception {
        ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
        instance.configure(cfgObject);

        StatisticsPostCalculationHandler handler = EasyMock.createMock(StatisticsPostCalculationHandler.class);
        instance.registerPostCalculationHandler(handler);

        try {
            handler.handleStatisticsResult(EasyMock.isA(ReviewerStatistics.class));
            EasyMock.expectLastCall().andStubThrow(new StatisticsPostCalculationHandlerException("error."));
            EasyMock.replay(handler);

            instance.calculateStatistics(11L, 0.1);

            fail("StatisticsCalculatorException is expected.");
        } catch (StatisticsCalculatorException e) {
            EasyMock.verify(handler);
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code> method with no
     * primaryReviewEvaluatorResourceRoleId. <br>
     * <code>IllegalStateException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_No_primaryReviewEvaluatorResourceRoleId()
        throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.calculateStatistics(11L, 0.1);

            fail("IllegalStateException is expected.");
        } catch (IllegalStateException e) {
            // Good
            assertEquals("'primaryReviewEvaluatorResourceRoleId' should not be null.", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code> method with negative projectId. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_projectId() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.calculateStatistics(-1L, 0.1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertEquals("'projectId' should be positive.", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code> method with negative eligibilityPointsPool. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_eligibilityPointsPool() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.calculateStatistics(1L, -0.1);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
            assertEquals("'eligibilityPointsPool' should not be negative.", e.getMessage());
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code> method with projectId not found. <br>
     * <code>ProjectNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_projectIdNotFound() throws Exception {
        try {
            cfgObject = TestsHelper.loadConfig("config.xml").getChild(
                "com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator");

            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.configure(cfgObject);
            instance.calculateStatistics(99L, 0.1);

            fail("ProjectNotFoundException is expected.");
        } catch (ProjectNotFoundException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code>. PersistenceException thrown. <br>
     * <code>StatisticsCalculatorException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_persistence() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.configure(cfgObject);
            instance.calculateStatistics(1010L, 0.1);

            fail("StatisticsCalculatorException is expected.");
        } catch (StatisticsCalculatorException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code>. NullPointerException thrown. <br>
     * <code>StatisticsCalculatorException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_nullPointer() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.configure(cfgObject);
            instance.calculateStatistics(12L, 0.1);

            fail("StatisticsCalculatorException is expected.");
        } catch (StatisticsCalculatorException e) {
            // Good
        }
    }

    /**
     * <p>
     * Tests failure of <code>calculateStatistics(long, double)</code>. No primary reviewer. <br>
     * <code>StatisticsCalculatorException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_calculateStatistics_noPrimaryReviewer() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.configure(cfgObject);
            instance.calculateStatistics(21L, 0.1);

            fail("StatisticsCalculatorException is expected.");
        } catch (StatisticsCalculatorException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>registerPostCalculationHandler(StatisticsPostCalculationHandler)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_registerPostCalculationHandler() throws Exception {
        ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
        StatisticsPostCalculationHandler handler = EasyMock.createMock(StatisticsPostCalculationHandler.class);
        instance.registerPostCalculationHandler(handler);
    }

    /**
     * <p>
     * Tests failure of <code>registerPostCalculationHandler(StatisticsPostCalculationHandler)</code> method with
     * handler <code>null</code>. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_registerPostCalculationHandler_hanlder_Null() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.registerPostCalculationHandler(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }

    /**
     * <p>
     * Accuracy test for the method <code>unregisterPostCalculationHandler(StatisticsPostCalculationHandler)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_unregisterPostCalculationHandler() throws Exception {
        ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
        StatisticsPostCalculationHandler handler = EasyMock.createMock(StatisticsPostCalculationHandler.class);
        instance.registerPostCalculationHandler(handler);
        instance.unregisterPostCalculationHandler(handler);
    }

    /**
     * <p>
     * Tests failure of <code>unregisterPostCalculationHandler(StatisticsPostCalculationHandler)</code> method with
     * handler <code>null</code>. <br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    public void test_ReviewerStatisticsCalculator_unregisterPostCalculationHandler_hanlder_Null() throws Exception {
        try {
            ReviewerStatisticsCalculator instance = new ReviewerStatisticsCalculator();
            instance.unregisterPostCalculationHandler(null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // Good
        }
    }
}
