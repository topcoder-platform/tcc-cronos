/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.failuretests;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.latetracker.LateDeliverable;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesProcessingException;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTrackerConfigurationException;
import com.topcoder.management.deliverable.latetracker.processors.LateDeliverableProcessorImpl;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.project.phases.Phase;


/**
 * Failure test cases <code>LateDeliverableProcessorImpl</code>.
 *
 * @author gjw99
 * @version 1.0
 */
public class LateDeliverableProcessorImplFailureTests extends TestCase {
    /** Represents the instance to be tested. */
    private LateDeliverableProcessorImpl instance;

    /**
     * Represents the ConfigurationObject to configure.
     */
    private ConfigurationObject config;

    /**
     * Setup the test environment.
     * @throws Exception for errors
     */
    public void setUp() throws Exception {
        this.instance = new LateDeliverableProcessorImpl();
        config = TestHelper.getConfigurationObject("failure/Processor.xml",
                "failuretests");
    }

    /**
     * Tear down the environment
     */
    public void tearDown() {
        this.instance = null;
        config = null;
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
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
     * @throws Exception if any error
     */
    public void test_configure2() throws Exception {
        try {
            config.setPropertyValue("loggerName", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure3() throws Exception {
        try {
            config.setPropertyValue("connectionName", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure4() throws Exception {
        try {
            config.removeProperty("notificationDeliverableIds");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure5() throws Exception {
        try {
            config.setPropertyValue("notificationDeliverableIds", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure6() throws Exception {
        try {
            config.setPropertyValue("notificationDeliverableIds", "1,2,-1");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure7() throws Exception {
        try {
            config.setPropertyValue("notificationDeliverableIds", "1,2,");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure8() throws Exception {
        try {
            config.removeChild("objectFactoryConfig");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure9() throws Exception {
        try {
            config.removeProperty("resourceManagerKey");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure10() throws Exception {
        try {
            config.setPropertyValue("resourceManagerKey", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure11() throws Exception {
        try {
            config.removeProperty("emailSender");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure12() throws Exception {
        try {
            config.setPropertyValue("emailSender", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure13() throws Exception {
        try {
            config.removeProperty("userRetrievalKey");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure14() throws Exception {
        try {
            config.setPropertyValue("userRetrievalKey", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure15() throws Exception {
        try {
            config.setPropertyValue("timestampFormat", " ");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test configure.
     *
     * @throws Exception if any error
     */
    public void test_configure16() throws Exception {
        try {
            config.setPropertyValue("notificationInterval", "aa");
            instance.configure(config);
            fail(
                "LateDeliverablesTrackerConfigurationException should be thrown.");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * Test processLateDeliverable.
     *
     * @throws Exception if any error
     */
    public void test_processLateDeliverable1() throws Exception {
        try {
            instance.processLateDeliverable(null);
            fail("IAE should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test processLateDeliverable.
     *
     * @throws Exception if any error
     */
    public void test_processLateDeliverable2() throws Exception {
        try {
            instance.processLateDeliverable(new LateDeliverable());
            fail("ISE should be thrown.");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * Test processLateDeliverable.
     *
     * @throws Exception if any error
     */
    public void test_processLateDeliverable3() throws Exception {
        try {
            instance.configure(config);

            LateDeliverable late = new LateDeliverable();
            late.setDeliverable(new Deliverable(0, 0, 0, null, false));
            late.setPhase(new Phase(null, 0));
            late.setProject(new Project(2,
                    new ProjectCategory(2, "test", new ProjectType(1, "test")),
                    new ProjectStatus(1, "test")));
            TestHelper.setPrivateField(LateDeliverableProcessorImpl.class, instance, "connectionName", "wrong name");
            instance.processLateDeliverable(late);
            fail("LateDeliverablesProcessingException should be thrown.");
        } catch (LateDeliverablesProcessingException e) {
            // pass
        }
    }
}
