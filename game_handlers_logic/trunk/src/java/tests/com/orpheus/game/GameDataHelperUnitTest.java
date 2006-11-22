/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingSlot;


/**
 * UnitTest for GameDataHelper class.
 *
 * @author mittu
 * @version 1.0
 */
public class GameDataHelperUnitTest extends TestCase {

    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GameDataHelperUnitTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig(TestHelper.CONFIG);
        TestHelper.deployEJB();
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.releaseConfigs();
    }  

    /**
     * Accuracy test of <code>getInstance()</code> method.
     *
     * <p>
     * Expects a non null instance.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetInstanceAccuracy() throws Exception {
        GameDataHelper dataHelper = GameDataHelper.getInstance();
        assertNotNull("failed to get GameDataHelper", dataHelper);
    }

    /**
     * Accuracy test of <code>getActiveGames()</code> method.
     *
     * <p>
     * Expects active games with out any exceptions from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetActiveGamesAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        Game[] games = helper.getActiveGames();
        assertNotNull("failed to get active games", games);
        // expect the same count which is set in the mock bean.
        assertEquals("failed to get active games", games.length, 3);
    }

    /**
     * Accuracy test of <code>getRegisteredGames(long playerId)</code> method.
     *
     * <p>
     * Expects registered games with out any exceptions from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetRegisteredGamesAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        Game[] games = helper.getRegisteredGames(TestHelper.PLAYER_ID);
        assertNotNull("failed to get registered games", games);
        // expect the same count which is set in the mock bean.
        assertEquals("failed to get active games", games.length, 2);
    }

    /**
     * Accuracy test of <code>getGame(long gameId)</code> method.
     *
     * <p>
     * Expects the game with out any exceptions from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetGameAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        Game game = helper.getGame(101);
        assertNotNull("failed to get game", game);
        // expect the same game id which is set in the mock bean.
        assertEquals("failed to get game", game.getId(), new Long(101));
    }

    /**
     * Accuracy test of <code>findSlotForDomain(long gameId, long playerId, String domain)</code> method.
     *
     * <p>
     * Expects the hosting slot without any exception from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindSlotForDomainAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        HostingSlot slot = helper.findSlotForDomain(101, 10001, "junit");
        assertNotNull("failed to find slot for domain", slot);
    }

    /**
     * Accuracy test of <code>getUnlockedDomains(long gameId)</code> method.
     *
     * <p>
     * Expects the unlocked domains without any exception from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetUnlockedDomainsAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        Domain[] domains = helper.getUnlockedDomains(101);
        assertNotNull("failed to get unlocked domains", domains);
        assertEquals("failed to get unlocked domains", domains.length, 0);
    }

    /**
     * Accuracy test of <code>getUpcomingDomains(long gameId)</code> method.
     *
     * <p>
     * Expects the upcoming domains without any exception from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetUpcomingDomainsAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        Domain[] domains = helper.getUpcomingDomains(101);
        assertNotNull("failed to get unlocked domains", domains);
        assertEquals("failed to get unlocked domains", domains.length, 3);
    }

    /**
     * Accuracy test of <code>findGamesByDomain(String domain, long playerId)</code> method.
     *
     * <p>
     * Expects the games without any exception from the MockBean.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testFindGamesByDomainAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        Game[] games = helper.findGamesByDomain("junit", 101);
        assertNotNull("failed to get games by domain", games);
        // expect the same count which is set in the mock bean.
        assertEquals("failed to get games by domain", games.length, 3);
    }

    /**
     * Accuracy test of <code>getLeaderBoard(long gameId, int maxLeaders)</code> method.
     *
     * <p>
     * Expects the first 5 leaders from MockBean without any exception.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetLeaderBoardAccuracy() throws Exception {
        GameDataHelper helper = GameDataHelper.getInstance();
        long[] leaders = helper.getLeaderBoard(101, 5);
        assertNotNull("failed to get leader board", leaders);
        // expect the same count which is set in the mock bean.
        assertEquals("failed to get leader board", leaders.length, 0);
    }
}
