/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.Game;

import junit.framework.TestCase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * <p>
 * The unit test of BaseGameDataManager.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BaseGameDataManagerTests extends TestCase {
    /**
     * The date format.
     */
    private static DateFormat format = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * The base manager instance used to test.
     */
    private BaseGameDataManager manager = null;

    /**
     * The setUp of the base manager unit test.
     */
    protected void setUp() {
        manager = new BaseGameDataManagerMock();
    }

    /**
     * The unit test of the constructor.
     *
     */
    public void test_ctor() {
        assertNotNull("can not create BaseGameDataManager instance.", manager);
    }

    /**
     * The failure test of the method gameStatusChangedToStarted,
     * the game can not be null,
     * so IllegalArgumentException is expected.
     *
     */
    public void test_gameStatusChangedToStarted_failure1() {
        try {
            manager.gameStatusChangedToStarted(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the method gameStatusChangedToStarted,
     * the manager is already stopped,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_gameStatusChangedToStarted_failure2() throws Exception {
        try {
            manager.stopManager();
            manager.gameStatusChangedToStarted(new GameImpl());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * The accuracy test of the method gameStatusChangedToStarted,
     * the game is started and it is removed from the not start games list.
     *
     * @throws Exception to JUnit
     */
    public void test_gameStatusChangedToStarted_accuracy()
        throws Exception {
        Date date1 = format.parse("10-20-2006");
        Date date2 = format.parse("10-21-2006");
        Date date3 = format.parse("10-22-2006");

        //create 3 Game intance.
        GameImpl game1 = new GameImpl();
        game1.setStartDate(date1);

        GameImpl game2 = new GameImpl();
        game2.setStartDate(date2);

        GameImpl game3 = new GameImpl();
        game3.setStartDate(date3);

        manager.newGameAvailable(game1);
        manager.newGameAvailable(game2);
        manager.newGameAvailable(game3);

        manager.gameStatusChangedToStarted(game1);

        Game[] games = manager.getAllCurrentNotStartedGames();

        assertEquals("The games is not started properly.", 2, games.length);
    }

    /**
     * The failure test of the method newGameAvailable,
     * the game can not be null,
     * so IllegalArgumentException is expected.
     *
     */
    public void test_newGameAvailable_failure1() {
        try {
            manager.newGameAvailable(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the method newGameAvailable,
     * the manager is already stopped,
     * so IllegalStateException is expected.
     *
     * @throws Exception  to Junit
     */
    public void test_newGameAvailable_failure2() throws Exception {
        try {
            manager.stopManager();
            manager.newGameAvailable(new GameImpl());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * The accuracy test of the method newGameAvailable.
     * Make sure that the game is added the sorted properly.
     *
     * @throws Exception to JUnit
     */
    public void test_newGameAvailable_accuracy() throws Exception {
        Date date1 = format.parse("10-20-2006");
        Date date2 = format.parse("10-21-2006");
        Date date3 = format.parse("10-22-2006");

        //create 3 Game intance.
        GameImpl game1 = new GameImpl();
        game1.setStartDate(date1);

        GameImpl game2 = new GameImpl();
        game2.setStartDate(date2);

        GameImpl game3 = new GameImpl();
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
     * The failure test of the method getAllCurrentNotStartedGames,
     * the manager is stopped,
     * so IllegalStateException is expected.
     *
     * @throws Exception  to JUnit
     */
    public void test_getAllCurrentNotStartedGames_failure1() throws Exception {
        try {
            manager.stopManager();
            manager.getAllCurrentNotStartedGames();
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * The accuracy test of the method getAllCurrentNotStartedGames.
     *
     * @throws Exception to JUnit
     */
    public void test_getAllCurrentNotStartedGames_accuracy()
        throws Exception {
        Date date1 = format.parse("10-20-2006");
        Date date2 = format.parse("10-21-2006");
        Date date3 = format.parse("10-22-2006");

        //create 3 Game intance.
        GameImpl game1 = new GameImpl();
        game1.setStartDate(date1);

        GameImpl game2 = new GameImpl();
        game2.setStartDate(date2);

        GameImpl game3 = new GameImpl();
        game3.setStartDate(date3);

        manager.newGameAvailable(game1);
        manager.newGameAvailable(game2);
        manager.newGameAvailable(game3);

        Game[] games = manager.getAllCurrentNotStartedGames();

        assertEquals("All not started games are not retrieved properly.", 3,
            games.length);
    }

    /**
     * The failure test of the method setCurrentNotStartedGames,
     * the game array can not be null,
     * so IllegalArgumentException is expected.
     *
     */
    public void test_setCurrentNotStartedGames_failure1() {
        try {
            manager.setCurrentNotStartedGames(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the method setCurrentNotStartedGames,
     * the game array can not contain null,
     * so IllegalArgumentException is expected.
     *
     */
    public void test_setCurrentNotStartedGames_failure2() {
        try {
            manager.setCurrentNotStartedGames(new Game[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the method setCurrentNotStartedGames,
     * the manager is stopped,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_setCurrentNotStartedGames_failure3() throws Exception {
        try {
            manager.stopManager();
            manager.setCurrentNotStartedGames(new Game[] {new GameImpl()});
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * The failure test of the method addNewNotStartedGame,
     * the game can not be null,
     * so IllegalArgumentException is expected.
     *
     */
    public void test_addNewNotStartedGame_failure1() {
        try {
            manager.addNewNotStartedGame(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the method addNewNotStartedGame,
     * the game can not be null,
     * so IllegalStateException is expected.
     *
     * @throws Exception to Junit
     */
    public void test_addNewNotStartedGame_failure2() throws Exception {
        try {
            manager.stopManager();
            manager.addNewNotStartedGame(new GameImpl());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * The accuracy test of the method addNewNotStartedGame.
     * Make sure that the game is added the sorted properly.
     *
     * @throws Exception to JUnit
     */
    public void test_addNewNotStartedGame_accuracy() throws Exception {
        Date date1 = format.parse("10-20-2006");
        Date date2 = format.parse("10-21-2006");
        Date date3 = format.parse("10-22-2006");

        //create 3 Game intance.
        GameImpl game1 = new GameImpl();
        game1.setStartDate(date1);

        GameImpl game2 = new GameImpl();
        game2.setStartDate(date2);

        GameImpl game3 = new GameImpl();
        game3.setStartDate(date3);

        manager.addNewNotStartedGame(game1);
        manager.addNewNotStartedGame(game2);
        manager.addNewNotStartedGame(game3);

        Game[] games = manager.getAllCurrentNotStartedGames();
        assertEquals("The games are not sorted properly.",
            games[0].getStartDate(), date3);
        assertEquals("The games are not sorted properly.",
            games[1].getStartDate(), date2);
        assertEquals("The games are not sorted properly.",
            games[2].getStartDate(), date1);
    }

    /**
     * The failure test of the method removeStartedGameFromNotStartedList,
     * the game can not be null,
     * so IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_removeStartedGameFromNotStartedList_failure2() throws Exception {
        try {
            manager.stopManager();
            manager.removeStartedGameFromNotStartedList(1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }

    /**
     * The failure test of the method removeStartedGameFromNotStartedList,
     * the game can not be null,
     * so IllegalStateException is expected.
     *
     */
    public void test_removeStartedGameFromNotStartedList_accuracy() {
        //create 3 Game intance.
        GameImpl game1 = new GameImpl();
        game1.setId(new Long(1));
        game1.setStartDate(new Date());

        GameImpl game2 = new GameImpl();
        game2.setId(new Long(2));
        game2.setStartDate(new Date());

        manager.addNewNotStartedGame(game1);
        manager.addNewNotStartedGame(game2);

        manager.removeStartedGameFromNotStartedList(1);

        Game[] games = manager.getAllCurrentNotStartedGames();

        //one is removed, only 1 left
        assertEquals("The game is not removed successfully.", 1, games.length);
    }
}
