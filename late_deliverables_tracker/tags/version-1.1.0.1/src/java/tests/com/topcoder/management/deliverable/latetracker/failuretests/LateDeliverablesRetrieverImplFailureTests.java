/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.failuretests;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesRetrievalException;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTrackerConfigurationException;
import com.topcoder.management.deliverable.latetracker.retrievers.LateDeliverablesRetrieverImpl;

/**
 * Failure test cases <code>LateDeliverablesRetrieverImpl</code>.
 *
 * @author gjw99, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class LateDeliverablesRetrieverImplFailureTests extends TestCase {

    /** Represents the instance to be tested. */
    private LateDeliverablesRetrieverImpl instance;

    /**
     * Represents the ConfigurationObject to configure.
     */
    private ConfigurationObject config;

    /**
     * Set up for each test.
     *
     * @throws Exception
     *             to jUnit.
     */
    public void setUp() throws Exception {
        TestHelper.addConfig();
        TestHelper.executeSqlFile("test_files/failure/insert.sql");
        this.instance = new LateDeliverablesRetrieverImpl();
        config = TestHelper.getConfigurationObject("failure/Retriever.xml", "failuretests");
    }

    /**
     * Tear down for each test.
     *
     * @throws Exception
     *             to jUnit.
     */
    public void tearDown() throws Exception {
        TestHelper.cleanTables();
        TestHelper.clearNamespace();
        this.instance = null;
        config = null;
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure1() throws Exception {
        try {
            instance.configure(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure2() throws Exception {
        try {
            config.setPropertyValue("loggerName", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure3() throws Exception {
        try {
            config.setPropertyValue("loggerName", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure4() throws Exception {
        try {
            config.removeProperty("trackingDeliverableIds");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure5() throws Exception {
        try {
            config.setPropertyValue("trackingDeliverableIds", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure6() throws Exception {
        try {
            config.setPropertyValue("trackingDeliverableIds", "1,2,-1");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure7() throws Exception {
        try {
            config.setPropertyValue("trackingDeliverableIds", "1,2,,3");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure8() throws Exception {
        try {
            config.removeChild("objectFactoryConfig");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure9() throws Exception {
        try {
            config.setPropertyValue("projectManagerKey", new Exception());
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure10() throws Exception {
        try {
            config.setPropertyValue("projectManagerKey", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure11() throws Exception {
        try {
            config.removeProperty("phaseManagerKey");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure12() throws Exception {
        try {
            config.setPropertyValue("phaseManagerKey", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure13() throws Exception {
        try {
            config.setPropertyValue("deliverablePersistenceKey", null);
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure14() throws Exception {
        try {
            config.setPropertyValue("deliverablePersistenceKey", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception
     *             if any error
     */
    public void test_configure15() throws Exception {
        try {
            config.removeChild("deliverableChecker1");
            config.removeChild("deliverableChecker2");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure. maxDurationOfPhaseWithCompensatedDeadline is empty string.
     *
     * @throws Exception
     *             if any error
     * @since 1.1
     */
    public void test_configure16() throws Exception {
        try {
            config.setPropertyValue("maxDurationOfPhaseWithCompensatedDeadline", " ");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure. maxDurationOfPhaseWithCompensatedDeadline is invalid type.
     *
     * @throws Exception
     *             if any error
     * @since 1.1
     */
    public void test_configure17() throws Exception {
        try {
            config.setPropertyValue("maxDurationOfPhaseWithCompensatedDeadline", new Exception());
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure. maxDurationOfPhaseWithCompensatedDeadline is negative.
     *
     * @throws Exception
     *             if any error
     * @since 1.1
     */
    public void test_configure18() throws Exception {
        try {
            config.setPropertyValue("maxDurationOfPhaseWithCompensatedDeadline", "-1");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure. maxDurationOfPhaseWithCompensatedDeadline is out of range.
     *
     * @throws Exception
     *             if any error
     * @since 1.1
     */
    public void test_configure19() throws Exception {
        try {
            config.setPropertyValue("maxDurationOfPhaseWithCompensatedDeadline", Long.MIN_VALUE + "1");
            instance.configure(config);
            fail("LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test retrieve.
     *
     * @throws Exception
     *             if any error
     */
    public void test_retrieve1() throws Exception {
        try {
            instance.retrieve();
            fail("ISE should be thrown.");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Test retrieve.
     *
     * @throws Exception
     *             if any error
     */
    public void test_retrieve2() throws Exception {
        try {
            instance.configure(config);
            TestHelper.setPrivateField(LateDeliverablesRetrieverImpl.class, instance, "projectManager",
                new ProjectManagerMock());
            instance.retrieve();
            fail("LateDeliverablesRetrievalException should be thrown.");
        } catch (LateDeliverablesRetrievalException e) {
            // pass
        }
    }
}
