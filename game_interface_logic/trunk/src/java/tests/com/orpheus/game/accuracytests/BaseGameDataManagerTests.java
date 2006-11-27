/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import java.util.Date;

import com.orpheus.game.BaseGameDataManager;
import com.orpheus.game.persistence.Game;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for BaseGameDataManager.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseGameDataManagerTests extends TestCase {
	/**
	 * Instance of BaseGameDataManager for tests.
	 */
	private BaseGameDataManager manager;

	/**
     * Sets up the environments. Create the instance.
     * @throws Exception
     *             to JUnit
     */
	protected void setUp() throws Exception {
		manager = new AccuracyTestBaseGameDataManager();
	}

	/**
     * Clears all the test environments.
     * @throws Exception
     *             to JUnit
     */
	protected void tearDown() throws Exception {
	}

	/**
	 * Accuracy test the constructor. verify the list is created.
	 * @throws Exception
	 *              to JUnit
	 */
	public void testCtorAccuracy() throws Exception {
	    assertNotNull("Unable to create the BaseGameDataManager.", manager);
	}

	/**
	 * Accuracy test the method
	 *            <code>gameStatusChangedToStarted(Game)</code>.
	 * @throws Exception
	 *              to JUnit
	 */
	public void testGameStatusChangedToStarted() throws Exception {
        AccuracyTestGame game1 = new AccuracyTestGame();
        game1.setStartDate(new Date(new Date().getTime() + 1000));

        AccuracyTestGame game2 = new AccuracyTestGame();
        game2.setStartDate(new Date(new Date().getTime() + 1000));

        AccuracyTestGame game3 = new AccuracyTestGame();
        game3.setStartDate(new Date(new Date().getTime() + 1000));

        manager.newGameAvailable(game1);
        manager.newGameAvailable(game2);
        manager.newGameAvailable(game3);

        manager.gameStatusChangedToStarted(game1);

        Game[] games = manager.getAllCurrentNotStartedGames();

        assertEquals("The games is not started properly.", 2, games.length);
	}

	/**
     * The accuracy test of the method newGameAvailable.
     * Make sure that the game is added the sorted properly.
     *
     * @throws Exception to JUnit
     */
    public void testNewGameAvailableAccuracy() throws Exception {
    	Date date1 = new Date(new Date().getTime() + 1000);
    	Date date2 = new Date(new Date().getTime() + 1000);
    	Date date3 = new Date(new Date().getTime() + 1000);

    	AccuracyTestGame game1 = new AccuracyTestGame();
        game1.setStartDate(date1);

        AccuracyTestGame game2 = new AccuracyTestGame();
        game2.setStartDate(date2);

        AccuracyTestGame game3 = new AccuracyTestGame();
        game3.setStartDate(date3);

        manager.newGameAvailable(game1);
        manager.newGameAvailable(game2);
        manager.newGameAvailable(game3);

        Game[] games = manager.getAllCurrentNotStartedGames();
        assertEquals("The games are not sorted properly.",
            games[0].getStartDate(), date3);
        assertEquals("The games are not sorted properly.",
            games[1].getStartDate(), date2);
        assertEquals("The games are not sorted properly.",
            games[2].getStartDate(), date1);
    }

    /**
     * The accuracy test of the method getAllCurrentNotStartedGames.
     * @throws Exception to JUnit
     */
    public void testGetAllCurrentNotStartedGamesAccuracy() throws Exception {
    	Date date1 = new Date(new Date().getTime() + 1000);
    	Date date2 = new Date(new Date().getTime() + 1000);
    	Date date3 = new Date(new Date().getTime() + 1000);

    	AccuracyTestGame game1 = new AccuracyTestGame();
        game1.setStartDate(date1);

        AccuracyTestGame game2 = new AccuracyTestGame();
        game2.setStartDate(date2);

        AccuracyTestGame game3 = new AccuracyTestGame();
        game3.setStartDate(date3);

        manager.newGameAvailable(game1);
        manager.newGameAvailable(game2);
        manager.newGameAvailable(game3);

        Game[] games = manager.getAllCurrentNotStartedGames();
        assertEquals("The games are not sorted properly.",
            games[0].getStartDate(), date3);
        assertEquals("The games are not sorted properly.",
            games[1].getStartDate(), date2);
        assertEquals("The games are not sorted properly.",
            games[2].getStartDate(), date1);	
    }
    /**
     * The accuracy test of the method setCurrentNotStartedGames.
     * @throws Exception to JUnit
     */
    public void testSetCurrentNotStartedGamesAccuracy() throws Exception {
    	Date date1 = new Date(new Date().getTime() + 1000);
    	Date date2 = new Date(new Date().getTime() + 1000);
    	Date date3 = new Date(new Date().getTime() + 1000);

    	AccuracyTestGame game1 = new AccuracyTestGame();
        game1.setStartDate(date1);

        AccuracyTestGame game2 = new AccuracyTestGame();
        game2.setStartDate(date2);

        AccuracyTestGame game3 = new AccuracyTestGame();
        game3.setStartDate(date3);

        Game[] games = new Game[] {game2, game1, game3};
        manager.setCurrentNotStartedGames(games);

        games = manager.getAllCurrentNotStartedGames();
        assertEquals("The games are not sorted properly.",
            games[0].getStartDate(), date3);
        assertEquals("The games are not sorted properly.",
            games[1].getStartDate(), date2);
        assertEquals("The games are not sorted properly.",
            games[2].getStartDate(), date1);
    }

    /**
     * The accuracy test of the method removeStartedGameFromNotStartedList.
     * @throws Exception to JUnit
     */
    public void testRemoveStartedGameFromNotStartedListAccuracy() throws Exception {
    	AccuracyTestGame game1 = new AccuracyTestGame();
    	game1.setId(new Long(1));
    	game1.setStartDate(new Date());
    	AccuracyTestGame game2 = new AccuracyTestGame();
    	game2.setId(new Long(2));
    	game2.setStartDate(new Date());

    	manager.addNewNotStartedGame(game1);
        manager.addNewNotStartedGame(game2);

        manager.removeStartedGameFromNotStartedList(1);
        Game[] games = manager.getAllCurrentNotStartedGames();
        assertEquals("The game is not removed successfully.", 1, games.length);
        manager.removeStartedGameFromNotStartedList(3);
        assertEquals("The game is not removed successfully.", 1, games.length);
    }
}
