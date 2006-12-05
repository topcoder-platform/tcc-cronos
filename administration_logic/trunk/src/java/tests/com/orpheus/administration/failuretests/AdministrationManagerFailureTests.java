/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests;

import com.orpheus.administration.AdministrationException;
import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.ConfigurationException;
import com.topcoder.util.puzzle.PuzzleTypeSource;
import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>AdministrationManager</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class AdministrationManagerFailureTests extends TestCase {
    /**
     * The configuration namespace used to create AdministrationManager instance.
     */
    private static final String NAMESPACE = AdministrationManager.class.getName();

    /**
     * The AdministrationManager instance to test.
     */
    private AdministrationManager manager = null;

    /**
     * The PuzzleTypeSource used in test cases.
     */
    private PuzzleTypeSource pts = null;

    /**
     * <p>
     * Setup for each test cases.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        FailureHelper.loadConfiguration();
        FailureHelper.setupDatabase();

        pts = new MockPuzzleTypeSource();
        manager = new AdministrationManager(pts, NAMESPACE);
    }

    /**
     * <p>
     * Clean up for each test cases.
     * </p>
     *
     * @throws Exception the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        FailureHelper.cleanupDatabase();
        FailureHelper.clearAllConfigurationNS();
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'puzzleTypeSource' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorNullPTS() throws Exception {
        try {
            new AdministrationManager(null, NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'namespace' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorNullNamespace() throws Exception {
        try {
            new AdministrationManager(pts, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'namespace' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorEmptyNamespace() throws Exception {
        try {
            new AdministrationManager(pts, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the constructor.
     * In this test case the 'namespace' parameter is null. ConfigurationException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testConstructorInvalidNamespace() throws Exception {
        try {
            new AdministrationManager(pts, "invalid_NS");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>regeneratePuzzle()</code>.
     * In this test case the id parameter is invalid. AdministrationException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRegeneratePuzzleInvalidId() throws Exception {
        try {
            manager.regeneratePuzzle(13567);
            fail("AdministrationException should be thrown.");
        } catch (AdministrationException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>regenerateBrainTeaser()</code>.
     * In this test case the id parameter is invalid. AdministrationException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRegenerateBrainTeaserInvalidId() throws Exception {
        try {
            manager.regenerateBrainTeaser(13567);
            fail("AdministrationException should be thrown.");
        } catch (AdministrationException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>generateHuntTargets()</code>.
     * In this test case the id parameter is invalid. AdministrationException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGenerateHuntTargetsInvalidId() throws Exception {
        try {
            manager.generateHuntTargets(13567);
            fail("AdministrationException should be thrown.");
        } catch (AdministrationException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>initializeSlotsForBlock()</code>.
     * In this test case the id parameter is invalid. AdministrationException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testInitializeSlotsForBlockInvalidId() throws Exception {
        try {
            manager.initializeSlotsForBlock(13567);
            fail("AdministrationException should be thrown.");
        } catch (AdministrationException e) {
            // fine
        }
    }
}
