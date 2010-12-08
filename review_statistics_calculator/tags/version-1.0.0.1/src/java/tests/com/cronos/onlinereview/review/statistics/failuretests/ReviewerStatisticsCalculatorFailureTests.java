/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.failuretests;

import java.util.Iterator;

import junit.framework.TestCase;

import com.cronos.onlinereview.review.statistics.StatisticsCalculatorConfigurationException;
import com.cronos.onlinereview.review.statistics.impl.ReviewerStatisticsCalculator;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.config.ConfigManager;


/**
 * Failure test cases <code>ReviewerStatisticsCalculator</code>.
 *
 * @author gjw99
 * @version 1.0
 */
public class ReviewerStatisticsCalculatorFailureTests extends TestCase {
    /** Represents the instance to be tested. */
    private ReviewerStatisticsCalculator instance;

    /** Valid configuration object. */
    private ConfigurationObject config;

    /**
     * Setup the test environment.
     *
     * @throws Exception if any error
     */
    public void setUp() throws Exception {
    	loadConfig();
        this.instance = new ReviewerStatisticsCalculator();

        ConfigurationFileManager manager = new ConfigurationFileManager("test_files/failure/failure.properties");
        config = manager.getConfiguration("ReviewerStatisticsCalculator").getChild("ReviewerStatisticsCalculator");
    }

    /**
     * Tear down the environment.
     *
     * @throws Exception if any error
     */
    public void tearDown() throws Exception {
    	clearConfig();
        this.instance = null;
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure1() throws Exception {
        try {
            instance.configure(null);
            fail("IAE is required");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure2() throws Exception {
        try {
            config.removeProperty("timelineReliabilityCalculatorKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure3() throws Exception {
        try {
            config.removeProperty("coverageCalculatorKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure4() throws Exception {
        try {
            config.removeProperty("accuracyCalculatorKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure5() throws Exception {
        try {
            config.removeProperty("timelineReliabilityCalculatorConfig");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure6() throws Exception {
        try {
            config.removeProperty("coverageCalculatorConfig");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure7() throws Exception {
        try {
            config.removeProperty("accuracyCalculatorConfig");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure8() throws Exception {
        try {
            config.removeProperty("resourceManagerKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure9() throws Exception {
        try {
            config.removeProperty("phaseManagerKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure10() throws Exception {
        try {
            config.removeProperty("deliverableManagerKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure11() throws Exception {
        try {
            config.removeProperty("reviewManagerKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure12() throws Exception {
        try {
            config.removeProperty("projectManagerKey");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure13() throws Exception {
        try {
            config.removeProperty("screeningPhaseTypeId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure14() throws Exception {
        try {
            config.removeProperty("secondaryReviewerReviewPhaseTypeId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure15() throws Exception {
        try {
            config.removeProperty("primaryReviewEvaluationPhaseTypeId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure16() throws Exception {
        try {
            config.removeProperty("primaryReviewAppealsResponsePhaseTypeId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure17() throws Exception {
        try {
            config.removeProperty("aggregationReviewPhaseTypeId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure18() throws Exception {
        try {
            config.removeProperty("finalReviewPhaseTypeId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure19() throws Exception {
        try {
            config.removeProperty("secondaryReviewerResourceRoleId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure20() throws Exception {
        try {
            config.removeProperty("primaryReviewEvaluatorResourceRoleId");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }


    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure21() throws Exception {
        try {
            config.setPropertyValue("timelineReliabilityCalculatorKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure22() throws Exception {
        try {
            config.setPropertyValue("coverageCalculatorKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure23() throws Exception {
        try {
            config.setPropertyValue("accuracyCalculatorKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure25() throws Exception {
        try {
            config.setPropertyValue("resourceManagerKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure26() throws Exception {
        try {
            config.setPropertyValue("phaseManagerKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure27() throws Exception {
        try {
            config.setPropertyValue("deliverableManagerKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure28() throws Exception {
        try {
            config.setPropertyValue("reviewManagerKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure29() throws Exception {
        try {
            config.setPropertyValue("projectManagerKey", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure30() throws Exception {
        try {
            config.setPropertyValue("screeningPhaseTypeId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure31() throws Exception {
        try {
            config.setPropertyValue("secondaryReviewerReviewPhaseTypeId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure32() throws Exception {
        try {
            config.setPropertyValue("primaryReviewEvaluationPhaseTypeId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure33() throws Exception {
        try {
            config.setPropertyValue("primaryReviewAppealsResponsePhaseTypeId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure34() throws Exception {
        try {
            config.setPropertyValue("aggregationReviewPhaseTypeId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure35() throws Exception {
        try {
            config.setPropertyValue("finalReviewPhaseTypeId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure36() throws Exception {
        try {
            config.setPropertyValue("secondaryReviewerResourceRoleId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method configure.
     *
     * @throws Exception if any error
     */
    public void test_configure37() throws Exception {
        try {
            config.setPropertyValue("primaryReviewEvaluatorResourceRoleId", " ");
            instance.configure(config);
            fail("StatisticsCalculatorConfigurationException is required");
        } catch (StatisticsCalculatorConfigurationException e) {
            // ok
        }
    }

    /**
     * Test method calculateStatistics.
     *
     * @throws Exception if any error
     */
    public void test_calculateStatistics3() throws Exception {
        try {
            instance.calculateStatistics(1, 1);
            fail("ISE is required");
        } catch (IllegalStateException e) {
            // ok
        }
    }

    /**
     * Add the namespace.
     *
     * @throws Exception to JUnit
     */
    private static void loadConfig() throws Exception {
        clearConfig();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("failure/db_connection_factory.xml");
        cm.add("failure/search_bundle_manager.xml");
        cm.add("failure/review_manager_config.xml");
    }

    /**
     * <p>
     * Remove all the namespace.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private static void clearConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }
}
