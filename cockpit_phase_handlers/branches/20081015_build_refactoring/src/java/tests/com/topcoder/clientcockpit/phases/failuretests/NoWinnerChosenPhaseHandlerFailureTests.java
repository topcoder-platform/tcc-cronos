/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import com.topcoder.clientcockpit.phases.NoWinnerChosenPhaseHandler;
import com.topcoder.service.studio.contest.ContestManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for NoWinnerChosenPhaseHandler.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class NoWinnerChosenPhaseHandlerFailureTests extends TestCase {

    /**
     * <p>
     * The NoWinnerChosenPhaseHandler instance for testing.
     * </p>
     */
    private NoWinnerChosenPhaseHandler instance;

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

        instance = new NoWinnerChosenPhaseHandler();
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
        return new TestSuite(NoWinnerChosenPhaseHandlerFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor NoWinnerChosenPhaseHandler#NoWinnerChosenPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullNamespace() throws Exception {
        try {
            new NoWinnerChosenPhaseHandler(null, new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NoWinnerChosenPhaseHandler#NoWinnerChosenPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_EmptyNamespace() throws Exception {
        try {
            new NoWinnerChosenPhaseHandler(" ", new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NoWinnerChosenPhaseHandler#NoWinnerChosenPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullBean() throws Exception {
        try {
            new NoWinnerChosenPhaseHandler("com.topcoder.clientcockpit.phases", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NoWinnerChosenPhaseHandler#NoWinnerChosenPhaseHandler(String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullNamespace() throws Exception {
        try {
            new NoWinnerChosenPhaseHandler((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NoWinnerChosenPhaseHandler#NoWinnerChosenPhaseHandler(String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyNamespace() throws Exception {
        try {
            new NoWinnerChosenPhaseHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor NoWinnerChosenPhaseHandler#NoWinnerChosenPhaseHandler(ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_NullBean() throws Exception {
        try {
            new NoWinnerChosenPhaseHandler((ContestManager) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests NoWinnerChosenPhaseHandler#canPerform(Phase) for failure.
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
     * Tests NoWinnerChosenPhaseHandler#perform(Phase,String) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testPerform_NullPhase() throws Exception {
        try {
            instance.perform(null, "No Winner Chosen");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}