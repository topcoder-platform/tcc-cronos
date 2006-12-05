/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import com.orpheus.administration.handlers.MockHttpRequest;
import com.orpheus.game.persistence.GameData;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * Test the <code>AdministrationManager</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AdministrationManagerUnitTests extends TestCase {
    /**
     * The namespace used to construct AdministrationManager instance.
     */
    private static final String NAMESPACE = AdministrationManager.class
            .getName();

    /**
     * The HttpServletRequest instance used in tests.
     */
    private HttpServletRequest request;

    /**
     * <p>
     * An instance of <code>AdministrationManager</code> which is tested.
     * </p>
     */
    private AdministrationManager target = null;

    /**
     * PuzzleTypeSource used in tests.
     */
    private PuzzleTypeSource src = null;

    /**
     * <p>
     * setUp() routine. Creates test AdministrationManager instance and deploy
     * ejb.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.prepareTest();
        src = new MockPuzzleTypeSource();
        target = new AdministrationManager(src, NAMESPACE);
        request = new MockHttpRequest();
    }

    /**
     * <p>
     * Clean up all namespace of ConfigManager.
     * </p>
     *
     * @throws Exception
     *             the exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearTestEnvironment();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>AdministrationManager()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to get AdministrationManager instance.", target);
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdministrationManager()</code> for proper
     * behavior. IllegalArgumentException should be thrown if puzzleTypeSource
     * is null, or if namespace is null or empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_failure() throws Exception {
        try {
            new AdministrationManager(null, NAMESPACE);
            fail("IllegalArgumentException should be thrown if puzzleTypeSource is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdministrationManager()</code> for proper
     * behavior. IllegalArgumentException should be thrown if puzzleTypeSource
     * is null, or if namespace is null or empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_2_failure() throws Exception {
        try {
            new AdministrationManager(src, null);
            fail("IllegalArgumentException should be thrown if namespace is null.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdministrationManager()</code> for proper
     * behavior. IllegalArgumentException should be thrown if puzzleTypeSource
     * is null, or if namespace is null or empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_3_failure() throws Exception {
        try {
            new AdministrationManager(src, "  ");
            fail("IllegalArgumentException should be thrown if namespace is empty.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdministrationManager()</code> for proper
     * behavior. ConfigurationException should be thrown if configuration values
     * are missing or invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_4_failure() throws Exception {
        configFailureTestBase(
                "ConfigurationException should be thrown if configuration values are empty.",
                NAMESPACE + "1");
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdministrationManager()</code> for proper
     * behavior. ConfigurationException should be thrown if configuration values
     * are missing or invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_5_failure() throws Exception {
        configFailureTestBase(
                "ConfigurationException should be thrown if configuration values are invalid.",
                NAMESPACE + "2");
    }

    /**
     * <p>
     * Failure test. Tests the <code>AdministrationManager()</code> for proper
     * behavior. ConfigurationException should be thrown if configuration values
     * are missing or invalid.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_6_failure() throws Exception {
        configFailureTestBase(
                "ConfigurationException should be thrown if configuration values are missing.",
                NAMESPACE + "3");
    }

    /**
     * test structure for config failure test.
     *
     * @param msg message
     * @param namespace the namespace
     */
    private void configFailureTestBase(String msg, String namespace) {
        try {
            new AdministrationManager(src, namespace);
            fail(msg);
        } catch (ConfigurationException e) {
            // ok
        }
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>regeneratePuzzle(long)</code> for proper
     * behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRegeneratePuzzle_1_Accuracy() throws Exception {
        target.regeneratePuzzle(1);
        GameData gameData = Helper.getGameData(TestHelper.GAME_DATA_JNDI_NAME,
                request, "fail");
        assertEquals("The Puzzle should be regenerated correctly.", gameData
                .getSlot(1).getPuzzleId().intValue(), 1);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>regenerateBrainTeaser(long)</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testRegenerateBrainTeaser_1_Accuracy() throws Exception {
        target.regenerateBrainTeaser(1);
        GameData gameData = Helper.getGameData(TestHelper.GAME_DATA_JNDI_NAME,
                request, "fail");
        assertEquals("The brainTesaser should be regenerated correctly.",
                gameData.getSlot(1).getBrainTeaserIds()[0], 1);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>generateHuntTargets(long)</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGenerateHuntTargets_1_Accuracy() throws Exception {
        target.generateHuntTargets(1);
        GameData gameData = Helper.getGameData(TestHelper.GAME_DATA_JNDI_NAME,
                request, "fail");
        assertNotNull("The HuntTargets should be generated correctly.",
                gameData.getSlot(1).getDomainTargets());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>initializeSlotsForBlock(long)</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testinitializeSlotsForBlock_1_Accuracy() throws Exception {
        target.initializeSlotsForBlock(1);
        GameData gameData = Helper.getGameData(TestHelper.GAME_DATA_JNDI_NAME,
                request, "fail");
        assertNotNull("The HuntTargets should be generated correctly.",
                gameData.getSlot(1).getDomainTargets());
        assertEquals("The brainTesaser should be regenerated correctly.",
                gameData.getSlot(1).getBrainTeaserIds()[0], 1);
        assertEquals("The Puzzle should be regenerated correctly.", gameData
                .getSlot(1).getPuzzleId().intValue(), 1);
    }
}
