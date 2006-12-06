/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox.accuracytests;

import com.orpheus.plugin.firefox.UIEventType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests the functionality of <code>UIEventType</code> class. The names and <code>toString()</code> methods are
 * tested.
 * 
 * @author visualage
 * @version 1.0
 */
public class UIEventTypeTestCase extends TestCase {
    /**
     * Aggragates all tests in this class.
     * 
     * @return test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(UIEventTypeTestCase.class);
    }

    /**
     * Tests the accuracy of <code>LOGIN_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'login'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testLoginClickAccuracy() {
        assertTrue("LOGIN_CLICK should be UIEventType.", UIEventType.LOGIN_CLICK instanceof UIEventType);
        assertEquals("LOGIN_CLICK should have the name 'login'.", "login", UIEventType.LOGIN_CLICK.getName());
        assertEquals("LOGIN_CLICK should have the string form 'login'.", "login", UIEventType.LOGIN_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>SUCCESSFUL_LOGIN</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'successful login'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testSuccessfulLoginAccuracy() {
        assertTrue("SUCCESSFUL_LOGIN should be UIEventType.", UIEventType.SUCCESSFUL_LOGIN instanceof UIEventType);
        assertEquals("SUCCESSFUL_LOGIN should have the name 'successful login'.", "successful login",
            UIEventType.SUCCESSFUL_LOGIN.getName());
        assertEquals("SUCCESSFUL_LOGIN should have the string form 'successful login'.", "successful login",
            UIEventType.SUCCESSFUL_LOGIN.toString());
    }

    /**
     * Tests the accuracy of <code>LOGOUT_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'logout'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testLogoutClickAccuracy() {
        assertTrue("LOGOUT_CLICK should be UIEventType.", UIEventType.LOGOUT_CLICK instanceof UIEventType);
        assertEquals("LOGOUT_CLICK should have the name 'logout'.", "logout", UIEventType.LOGOUT_CLICK.getName());
        assertEquals("LOGOUT_CLICK should have the string form 'logout'.", "logout", UIEventType.LOGOUT_CLICK
            .toString());
    }

    /**
     * Tests the accuracy of <code>SUCCESSFUL_LOGOUT</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'successful logout'. This is verified by
     * both <code>getName()</code> and <code>toString()</code>.
     */
    public void testSuccessfulLogoutAccuracy() {
        assertTrue("SUCCESSFUL_LOGOUT should be UIEventType.", UIEventType.SUCCESSFUL_LOGOUT instanceof UIEventType);
        assertEquals("SUCCESSFUL_LOGOUT should have the name 'successful logout'.", "successful logout",
            UIEventType.SUCCESSFUL_LOGOUT.getName());
        assertEquals("SUCCESSFUL_LOGOUT should have the string form 'successful logout'.", "successful logout",
            UIEventType.SUCCESSFUL_LOGOUT.toString());
    }

    /**
     * Tests the accuracy of <code>SHOW_ACTIVE_GAMES_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'show active games'. This is verified by
     * both <code>getName()</code> and <code>toString()</code>.
     */
    public void testShowActiveGamesClickAccuracy() {
        assertTrue("SHOW_ACTIVE_GAMES_CLICK should be UIEventType.",
            UIEventType.SHOW_ACTIVE_GAMES_CLICK instanceof UIEventType);
        assertEquals("SHOW_ACTIVE_GAMES_CLICK should have the name 'show active games'.", "show active games",
            UIEventType.SHOW_ACTIVE_GAMES_CLICK.getName());
        assertEquals("SHOW_ACTIVE_GAMES_CLICK should have the string form 'show active games'.", "show active games",
            UIEventType.SHOW_ACTIVE_GAMES_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>SHOW_MY_GAMES_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'show my games'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testShowMyGamesClickAccuracy() {
        assertTrue("SHOW_MY_GAMES_CLICK should be UIEventType.", UIEventType.SHOW_MY_GAMES_CLICK instanceof UIEventType);
        assertEquals("SHOW_MY_GAMES_CLICK should have the name 'show my games'.", "show my games",
            UIEventType.SHOW_MY_GAMES_CLICK.getName());
        assertEquals("SHOW_MY_GAMES_CLICK should have the string form 'show my games'.", "show my games",
            UIEventType.SHOW_MY_GAMES_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>SHOW_UNLOCKED_DOMAINS_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'show unlocked domains'. This is verified by
     * both <code>getName()</code> and <code>toString()</code>.
     */
    public void testShowUnlockedDomainsClickAccuracy() {
        assertTrue("SHOW_UNLOCKED_DOMAINS_CLICK should be UIEventType.",
            UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK instanceof UIEventType);
        assertEquals("SHOW_UNLOCKED_DOMAINS_CLICK should have the name 'show unlocked domains'.",
            "show unlocked domains", UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK.getName());
        assertEquals("SHOW_UNLOCKED_DOMAINS_CLICK should have the string form 'show unlocked domains'.",
            "show unlocked domains", UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>SHOW_UPCOMING_GAMES_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'show upcoming games'. This is verified by
     * both <code>getName()</code> and <code>toString()</code>.
     */
    public void testShowUpcomingGamesAccuracy() {
        assertTrue("SHOW_UPCOMING_GAMES_CLICK should be UIEventType.",
            UIEventType.SHOW_UPCOMING_GAMES_CLICK instanceof UIEventType);
        assertEquals("SHOW_UPCOMING_GAMES_CLICK should have the name 'show upcoming games'.", "show upcoming games",
            UIEventType.SHOW_UPCOMING_GAMES_CLICK.getName());
        assertEquals("SHOW_UPCOMING_GAMES_CLICK should have the string form 'show upcoming games'.",
            "show upcoming games", UIEventType.SHOW_UPCOMING_GAMES_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>SHOW_LEADERS_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'show leaders'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testShowLeadersClickAccuracy() {
        assertTrue("SHOW_LEADERS_CLICK should be UIEventType.", UIEventType.SHOW_LEADERS_CLICK instanceof UIEventType);
        assertEquals("SHOW_LEADERS_CLICK should have the name 'show leaders'.", "show leaders",
            UIEventType.SHOW_LEADERS_CLICK.getName());
        assertEquals("SHOW_LEADERS_CLICK should have the string form 'show leaders'.", "show leaders",
            UIEventType.SHOW_LEADERS_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>SHOW_LATEST_CLUE_CLICK</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'show latest clue'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testShowLatestClueClickAccuracy() {
        assertTrue("SHOW_LATEST_CLUE_CLICK should be UIEventType.",
            UIEventType.SHOW_LATEST_CLUE_CLICK instanceof UIEventType);
        assertEquals("SHOW_LATEST_CLUE_CLICK should have the name 'show latest clue'.", "show latest clue",
            UIEventType.SHOW_LATEST_CLUE_CLICK.getName());
        assertEquals("SHOW_LATEST_CLUE_CLICK should have the string form 'show latest clue'.", "show latest clue",
            UIEventType.SHOW_LATEST_CLUE_CLICK.toString());
    }

    /**
     * Tests the accuracy of <code>PAGE_CHANGED</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'page changed'. This is verified by both
     * <code>getName()</code> and <code>toString()</code>.
     */
    public void testPageChangedAccuracy() {
        assertTrue("PAGE_CHANGED should be UIEventType.", UIEventType.PAGE_CHANGED instanceof UIEventType);
        assertEquals("PAGE_CHANGED should have the name 'page changed'.", "page changed", UIEventType.PAGE_CHANGED
            .getName());
        assertEquals("PAGE_CHANGED should have the string form 'page changed'.", "page changed",
            UIEventType.PAGE_CHANGED.toString());
    }

    /**
     * Tests the accuracy of <code>WORKING_GAME_ID_UPDATE</code> public field. It should be an instance of
     * <code>UIEventType</code> class. The name of the instance should be 'working game id update'. This is verified
     * by both <code>getName()</code> and <code>toString()</code>.
     */
    public void testWorkingGameIDUpdateAccuracy() {
        assertTrue("WORKING_GAME_ID_UPDATE should be UIEventType.",
            UIEventType.WORKING_GAME_ID_UPDATE instanceof UIEventType);
        assertEquals("WORKING_GAME_ID_UPDATE should have the name 'working game id update'.", "working game id update",
            UIEventType.WORKING_GAME_ID_UPDATE.getName());
        assertEquals("WORKING_GAME_ID_UPDATE should have the string form 'working game id update'.",
            "working game id update", UIEventType.WORKING_GAME_ID_UPDATE.toString());
    }
}
