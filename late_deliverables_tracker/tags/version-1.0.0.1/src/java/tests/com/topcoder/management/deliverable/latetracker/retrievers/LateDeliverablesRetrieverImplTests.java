/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.latetracker.retrievers;

import com.topcoder.configuration.ConfigurationObject;

import com.topcoder.management.deliverable.latetracker.BaseTestCase;
import com.topcoder.management.deliverable.latetracker.LateDeliverable;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesRetrievalException;
import com.topcoder.management.deliverable.latetracker.LateDeliverablesTrackerConfigurationException;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;
import com.topcoder.management.deliverable.persistence.DeliverablePersistenceException;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.project.PersistenceException;

import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;

import java.util.List;
import java.util.Set;

/**
 * Unit tests for <code>{@link LateDeliverablesRetrieverImpl}</code> class.
 *
 * @author myxgyy
 * @version 1.0
 */
public class LateDeliverablesRetrieverImplTests extends BaseTestCase {
    /**
     * The <code>{@link LateDeliverablesRetrieverImpl}</code> instance used for testing.
     */
    private LateDeliverablesRetrieverImpl target;

    /**
     * The <code>ConfigurationObject</code> instance used for testing.
     */
    private ConfigurationObject config;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        super.setUp();

        target = new LateDeliverablesRetrieverImpl();
        config = BaseTestCase.getConfigurationObject("config/LateDeliverablesRetrieverImpl.xml",
            LateDeliverablesRetrieverImpl.class.getName());
        target.configure(config);
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Accuracy test case for the {@link
     * LateDeliverablesRetrieverImpl#LateDeliverablesRetrieverImpl()} method.
     * </p>
     * <p>
     * Verifies the default value of all class fields.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Constructor() throws Exception {
        target = new LateDeliverablesRetrieverImpl();
        assertNull("projectManager field should be null", getField(target, "projectManager"));
        assertNull("phaseManager field should be null", getField(target, "phaseManager"));
        assertNull("deliverableManager field should be null", getField(target, "deliverableManager"));
        assertNull("log field should be null", getField(target, "log"));
        assertNull("trackingDeliverableIds field should be null", getField(target,
            "trackingDeliverableIds"));
    }

    /**
     * <p>
     * Accuracy test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * Verifies all class fields have been set by configuration correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_1() throws Exception {
        assertNotNull("projectManager field should be null", getField(target, "projectManager"));
        assertNotNull("phaseManager field should be null", getField(target, "phaseManager"));
        assertNotNull("deliverableManager field should be null", getField(target, "deliverableManager"));
        assertNotNull("log field should be null", getField(target, "log"));

        Set<?> trackingDeliverableIds = (Set) getField(target, "trackingDeliverableIds");
        assertEquals("trackingDeliverableIds field wrong", 2, trackingDeliverableIds.size());
        assertTrue("trackingDeliverableIds field wrong", trackingDeliverableIds.contains(new Long(3)));
        assertTrue("trackingDeliverableIds field wrong", trackingDeliverableIds.contains(new Long(4)));
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The given config is <code>null</code>, <code>IllegalArgumentException</code>
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_2() throws Exception {
        try {
            target.configure(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The loggerName property is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_3() throws Exception {
        config.setPropertyValue("loggerName", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The loggerName property is not type of String in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_4() throws Exception {
        config.setPropertyValue("loggerName", new Exception());

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The trackingDeliverableIds property is missing in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_5() throws Exception {
        config.removeProperty("trackingDeliverableIds");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The trackingDeliverableIds property is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_6() throws Exception {
        config.setPropertyValue("trackingDeliverableIds", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The trackingDeliverableIds property is invalid in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_7() throws Exception {
        config.setPropertyValue("trackingDeliverableIds", "x,3");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The trackingDeliverableIds property is not type of String in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_8() throws Exception {
        config.setPropertyValue("trackingDeliverableIds", new Exception());

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The trackingDeliverableIds property is invalid in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_9() throws Exception {
        config.setPropertyValue("trackingDeliverableIds", ",");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The child config to create object factory does not exist,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_10() throws Exception {
        config.removeChild("objectFactoryConfig");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The projectManagerKey property value is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_11() throws Exception {
        config.setPropertyValue("projectManagerKey", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The projectManagerKey property value is missing in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_12() throws Exception {
        config.removeProperty("projectManagerKey");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The projectManagerKey property value can not be found in object factory config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_13() throws Exception {
        config.setPropertyValue("projectManagerKey", "not_exist");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The phaseManagerKey property value is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_14() throws Exception {
        config.setPropertyValue("phaseManagerKey", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The phaseManagerKey property value is missing in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_15() throws Exception {
        config.removeProperty("phaseManagerKey");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The phaseManagerKey property value can not be found in object factory config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_16() throws Exception {
        config.setPropertyValue("phaseManagerKey", "not_exist");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverablePersistenceKey property value is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_17() throws Exception {
        config.setPropertyValue("deliverablePersistenceKey", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverablePersistenceKey property value is missing in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_18() throws Exception {
        config.removeProperty("deliverablePersistenceKey");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverablePersistenceKey property value can not be found in object factory
     * config, <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_19() throws Exception {
        config.setPropertyValue("deliverablePersistenceKey", "not_exist");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The searchBundleManagerNamespace property value is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_20() throws Exception {
        config.setPropertyValue("searchBundleManagerNamespace", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The searchBundleManagerNamespace property value is missing in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_21() throws Exception {
        config.removeProperty("searchBundleManagerNamespace");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The searchBundleManagerNamespace property value is not type of String,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_22() throws Exception {
        config.setPropertyValue("searchBundleManagerNamespace", new Exception());

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The searchBundleManagerNamespace property value is invalid to create search bundle
     * manager, <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_23() throws Exception {
        config.setPropertyValue("searchBundleManagerNamespace", "not_exist");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            assertTrue("check inner cause", e.getCause() instanceof SearchBuilderConfigurationException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The child config start with deliverableChecker does not exist,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_24() throws Exception {
        config.removeChild("deliverableChecker1");
        config.removeChild("deliverableChecker2");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverableName property value is empty,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_25() throws Exception {
        config.getChild("deliverableChecker1").setPropertyValue("deliverableName", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverableName property value is missing,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_26() throws Exception {
        config.getChild("deliverableChecker1").removeProperty("deliverableName");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverableName property value is not type of String,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_27() throws Exception {
        config.getChild("deliverableChecker1").setPropertyValue("deliverableName", new Exception());

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverableCheckerKey property value is empty in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_28() throws Exception {
        config.getChild("deliverableChecker1").setPropertyValue("deliverableCheckerKey", "");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverableCheckerKey property value is missing in config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_29() throws Exception {
        config.getChild("deliverableChecker1").removeProperty("deliverableCheckerKey");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link
     * LateDeliverablesRetrieverImpl#configure(ConfigurationObject)} method.
     * </p>
     * <p>
     * The deliverableCheckerKey property value can not be found in object factory config,
     * <code>LateDeliverablesTrackerConfigurationException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_Configure_30() throws Exception {
        config.getChild("deliverableChecker1").setPropertyValue("deliverableCheckerKey", "not found");

        try {
            target.configure(config);
            fail("should have thrown LateDeliverablesTrackerConfigurationException");
        } catch (LateDeliverablesTrackerConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * Verifies all late deliverables have been retrieved correctly. In this case, there
     * is only one late phase.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_1() throws Exception {
        setupPhases(new long[] {101L}, new long[] {3L}, new long[] {2L}, true);

        List<LateDeliverable> result = target.retrieve();
        assertEquals("should have one late deliverable", 1, result.size());

        LateDeliverable deliverable = result.get(0);
        assertEquals("the project in late deliverable wrong", 1, deliverable.getProject().getId());
        assertEquals("the phase in late deliverable wrong", 101, deliverable.getPhase().getId());
        assertEquals("the deliverable name in late deliverable wrong", "Screening Scorecard",
            deliverable.getDeliverable().getName());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * Verifies all late deliverables have been retrieved correctly. In this case, there
     * are three phases, the first phase is closed, and the other two are late phases.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_2() throws Exception {
        // setup two phases have late deliverable
        setupPhases(new long[] {101L, 102L, 103L}, new long[] {2L, 3L, 4L}, new long[] {3L, 2L, 2L},
            true);

        List<LateDeliverable> result = target.retrieve();
        assertEquals("should have two late deliverables", 2, result.size());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * In this case, no active project &quot;Track Late Deliverables&quot; property set to
     * &quot;true&quot;. Empty list expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_3() throws Exception {
        assertEquals("empty list expected", 0, target.retrieve().size());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * In this case, no phase is late. Empty list expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_4() throws Exception {
        // the phase is not late
        setupPhases(new long[] {101L}, new long[] {3L}, new long[] {2L}, false);

        assertEquals("empty list expected", 0, target.retrieve().size());
    }

    /**
     * <p>
     * Accuracy test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * In this case, the late phase in not with type in tracking list. Empty list
     * expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_5() throws Exception {
        setupPhases(new long[] {101L}, new long[] {5L}, new long[] {2L}, true);

        assertEquals("empty list expected", 0, target.retrieve().size());
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * The <code>projectManager</code> field is <code>null</code>,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_6() throws Exception {
        setField(LateDeliverablesRetrieverImpl.class, target, "projectManager", null);

        try {
            target.retrieve();
            fail("should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * The <code>phaseManager</code> field is <code>null</code>,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_7() throws Exception {
        setField(LateDeliverablesRetrieverImpl.class, target, "phaseManager", null);

        try {
            target.retrieve();
            fail("should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * The <code>deliverableManager</code> field is <code>null</code>,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_8() throws Exception {
        setField(LateDeliverablesRetrieverImpl.class, target, "deliverableManager", null);

        try {
            target.retrieve();
            fail("should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * The <code>trackingDeliverableIds</code> field is <code>null</code>,
     * <code>IllegalStateException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_9() throws Exception {
        setField(LateDeliverablesRetrieverImpl.class, target, "trackingDeliverableIds", null);

        try {
            target.retrieve();
            fail("should have thrown IllegalStateException");
        } catch (IllegalStateException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * Error occurred when searching projects,
     * <code>LateDeliverablesRetrievalException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_10() throws Exception {
        setupPhases(new long[] {101L}, new long[] {3L}, new long[] {2L}, true);

        // configure with failure configuration
        target.configure(getConfigurationObject("invalid_config/LateDeliverablesRetrieverImpl1.xml",
            LateDeliverablesRetrieverImpl.class.getName()));

        try {
            target.retrieve();
            fail("should have thrown LateDeliverablesRetrievalException");
        } catch (LateDeliverablesRetrievalException e) {
            assertTrue("check inner cause", e.getCause() instanceof PersistenceException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * Error occurred when getting phase projects,
     * <code>LateDeliverablesRetrievalException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_11() throws Exception {
        setupPhases(new long[] {101L}, new long[] {3L}, new long[] {2L}, true);

        // configure with failure configuration
        target.configure(getConfigurationObject("invalid_config/LateDeliverablesRetrieverImpl2.xml",
            LateDeliverablesRetrieverImpl.class.getName()));

        try {
            target.retrieve();
            fail("should have thrown LateDeliverablesRetrievalException");
        } catch (LateDeliverablesRetrievalException e) {
            assertTrue("check inner cause", e.getCause() instanceof PhaseManagementException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * Error occurred when searching deliverables,
     * <code>LateDeliverablesRetrievalException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_12() throws Exception {
        setupPhases(new long[] {101L}, new long[] {3L}, new long[] {2L}, true);

        // configure with failure configuration
        target.configure(getConfigurationObject("invalid_config/LateDeliverablesRetrieverImpl3.xml",
            LateDeliverablesRetrieverImpl.class.getName()));

        try {
            target.retrieve();
            fail("should have thrown LateDeliverablesRetrievalException");
        } catch (LateDeliverablesRetrievalException e) {
            assertTrue("check inner cause", e.getCause() instanceof DeliverablePersistenceException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * Error occurred when searching deliverables,
     * <code>LateDeliverablesRetrievalException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_13() throws Exception {
        setupPhases(new long[] {101L}, new long[] {3L}, new long[] {2L}, true);

        // configure with failure configuration
        target.configure(getConfigurationObject("invalid_config/LateDeliverablesRetrieverImpl4.xml",
            LateDeliverablesRetrieverImpl.class.getName()));

        try {
            target.retrieve();
            fail("should have thrown LateDeliverablesRetrievalException");
        } catch (LateDeliverablesRetrievalException e) {
            assertTrue("check inner cause", e.getCause() instanceof SearchBuilderException);
        }
    }

    /**
     * <p>
     * Failure test case for the {@link LateDeliverablesRetrieverImpl#retrieve()} method.
     * </p>
     * <p>
     * The deliverable fails to pass the check,
     * <code>LateDeliverablesRetrievalException</code> expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void test_retrieve_14() throws Exception {
        setupPhases(new long[] {1000L}, new long[] {3L}, new long[] {2L}, true);

        try {
            target.retrieve();
            fail("should have thrown LateDeliverablesRetrievalException");
        } catch (LateDeliverablesRetrievalException e) {
            assertTrue("check inner cause", e.getCause() instanceof DeliverableCheckingException);
        }
    }
}
