/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.failuretests;

import com.topcoder.clientcockpit.phases.CompletedPhaseHandler;
import com.topcoder.service.studio.contest.ContestManager;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for CompletedPhaseHandler.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class CompletedPhaseHandlerFailureTests extends TestCase {

    /**
     * <p>
     * The CompletedPhaseHandler instance for testing.
     * </p>
     */
    private CompletedPhaseHandler instance;

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

        instance = new CompletedPhaseHandler();
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
        return new TestSuite(CompletedPhaseHandlerFailureTests.class);
    }

    /**
     * <p>
     * Tests ctor CompletedPhaseHandler#CompletedPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullNamespace() throws Exception {
        try {
            new CompletedPhaseHandler(null, new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor CompletedPhaseHandler#CompletedPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_EmptyNamespace() throws Exception {
        try {
            new CompletedPhaseHandler(" ", new MockContestManager());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor CompletedPhaseHandler#CompletedPhaseHandler(String,ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor1_NullBean() throws Exception {
        try {
            new CompletedPhaseHandler("com.topcoder.clientcockpit.phases", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor CompletedPhaseHandler#CompletedPhaseHandler(String) for failure.
     * It tests the case that when namespace is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_NullNamespace() throws Exception {
        try {
            new CompletedPhaseHandler((String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor CompletedPhaseHandler#CompletedPhaseHandler(String) for failure.
     * It tests the case that when namespace is empty and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor2_EmptyNamespace() throws Exception {
        try {
            new CompletedPhaseHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests ctor CompletedPhaseHandler#CompletedPhaseHandler(ContestManager) for failure.
     * It tests the case that when bean is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testCtor3_NullBean() throws Exception {
        try {
            new CompletedPhaseHandler((ContestManager) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

    /**
     * <p>
     * Tests CompletedPhaseHandler#canPerform(Phase) for failure.
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
     * Tests CompletedPhaseHandler#perform(Phase,String) for failure.
     * It tests the case that when phase is null and expects IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testPerform_NullPhase() throws Exception {
        try {
            instance.perform(null, "Completed");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            //good
        }
    }

}