/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import com.topcoder.clientcockpit.phases.InDangerPhaseHandler;
import com.topcoder.service.studio.contest.ContestManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for InDangerPhaseHandler.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class InDangerPhaseHandlerFailureTests extends TestCase {

    /**
     * <p>
     * The InDangerPhaseHandler instance for testing.
     * </p>
     */
    private InDangerPhaseHandler instance;

    /**
     * <p>
     * Setup test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        System.setProperty("java.naming.factory.initial",
            "com.topcoder.clientcockpit.phases.failuretests.MockInitialContextFactory");
        TestHelper.loadXMLConfig(TestHelper.CONFIG_FILE);

        instance = new InDangerPhaseHandler();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        System.clearProperty("java.naming.factory.initial");
        TestHelper.clearConfig();
        instance = null;
    }

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(InDangerPhaseHandlerFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor InDangerPhaseHandler#InDangerPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullNamespace() throws Exception {
        try {
            new InDangerPhaseHandler(null, new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor InDangerPhaseHandler#InDangerPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_EmptyNamespace() throws Exception {
        try {
            new InDangerPhaseHandler(" ", new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor InDangerPhaseHandler#InDangerPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullBean() throws Exception {
        try {
            new InDangerPhaseHandler("com.topcoder.clientcockpit.phases", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor InDangerPhaseHandler#InDangerPhaseHandler(String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullNamespace() throws Exception {
        try {
            new InDangerPhaseHandler((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor InDangerPhaseHandler#InDangerPhaseHandler(String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyNamespace() throws Exception {
        try {
            new InDangerPhaseHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor InDangerPhaseHandler#InDangerPhaseHandler(ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_NullBean() throws Exception {
        try {
            new InDangerPhaseHandler((ContestManager) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests InDangerPhaseHandler#canPerform(Phase) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCanPerform_NullPhase() throws Exception {
        try {
            instance.canPerform(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests InDangerPhaseHandler#perform(Phase,String) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testPerform_NullPhase() throws Exception {
        try {
            instance.perform(null, "In Danger");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}