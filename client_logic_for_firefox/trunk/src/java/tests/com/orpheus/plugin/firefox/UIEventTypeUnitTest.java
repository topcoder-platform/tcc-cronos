/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.plugin.firefox;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>UIEventType</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UIEventTypeUnitTest extends TestCase {
    /**
     * <p>
     * Tests the accuracy of LOGIN_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testLOGIN_CLICK_GetName_Accuracy() {
        assertEquals("The LOGIN_CLICK UIEventType should be correct.", "login", UIEventType.LOGIN_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of LOGIN_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testLOGIN_CLICK_ToString_Accuracy() {
        assertEquals("The LOGIN_CLICK UIEventType should be correct.", "login", UIEventType.LOGIN_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SUCCESSFUL_LOGIN UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSUCCESSFUL_LOGIN_GetName_Accuracy() {
        assertEquals("The SUCCESSFUL_LOGIN UIEventType should be correct.", "successful login",
            UIEventType.SUCCESSFUL_LOGIN.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SUCCESSFUL_LOGIN UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSUCCESSFUL_LOGIN_ToString_Accuracy() {
        assertEquals("The SUCCESSFUL_LOGIN UIEventType should be correct.", "successful login",
            UIEventType.SUCCESSFUL_LOGIN.toString());
    }

    /**
     * <p>
     * Tests the accuracy of LOGOUT_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testLOGOUT_CLICKGetName_Accuracy() {
        assertEquals("The LOGOUT_CLICK UIEventType should be correct.", "logout", UIEventType.LOGOUT_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of LOGOUT_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testLOGOUT_CLICK_ToString_Accuracy() {
        assertEquals("The LOGOUT_CLICK UIEventType should be correct.", "logout", UIEventType.LOGOUT_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SUCCESSFUL_LOGOUT UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSUCCESSFUL_LOGOUT_GetName_Accuracy() {
        assertEquals("The SUCCESSFUL_LOGOUT UIEventType should be correct.", "successful logout",
            UIEventType.SUCCESSFUL_LOGOUT.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SUCCESSFUL_LOGOUT UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSUCCESSFUL_LOGOUT_ToString_Accuracy() {
        assertEquals("The SUCCESSFUL_LOGOUT UIEventType should be correct.", "successful logout",
            UIEventType.SUCCESSFUL_LOGOUT.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_ACTIVE_GAMES_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSHOW_ACTIVE_GAMES_CLICK_GetName_Accuracy() {
        assertEquals("The SHOW_ACTIVE_GAMES_CLICK UIEventType should be correct.", "show active games",
            UIEventType.SHOW_ACTIVE_GAMES_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_ACTIVE_GAMES_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSHOW_ACTIVE_GAMES_CLICK_ToString_Accuracy() {
        assertEquals("The SHOW_ACTIVE_GAMES_CLICK UIEventType should be correct.", "show active games",
            UIEventType.SHOW_ACTIVE_GAMES_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_MY_GAMES_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSHOW_MY_GAMES_CLICK_GetName_Accuracy() {
        assertEquals("The SHOW_MY_GAMES_CLICK UIEventType should be correct.", "show my games",
            UIEventType.SHOW_MY_GAMES_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_MY_GAMES_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSHOW_MY_GAMES_CLICK_ToString_Accuracy() {
        assertEquals("The SHOW_MY_GAMES_CLICK UIEventType should be correct.", "show my games",
            UIEventType.SHOW_MY_GAMES_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_UNLOCKED_DOMAINS_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSHOW_UNLOCKED_DOMAINS_CLICK_GetName_Accuracy() {
        assertEquals("The SHOW_UNLOCKED_DOMAINS_CLICK UIEventType should be correct.", "show unlocked domains",
            UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_UNLOCKED_DOMAINS_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSHOW_UNLOCKED_DOMAINS_CLICK_ToString_Accuracy() {
        assertEquals("The SHOW_UNLOCKED_DOMAINS_CLICK UIEventType should be correct.", "show unlocked domains",
            UIEventType.SHOW_UNLOCKED_DOMAINS_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_UPCOMING_GAMES_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSHOW_UPCOMING_GAMES_CLICK_GetName_Accuracy() {
        assertEquals("The SHOW_UPCOMING_GAMES_CLICK UIEventType should be correct.", "show upcoming games",
            UIEventType.SHOW_UPCOMING_GAMES_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_UPCOMING_GAMES_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSHOW_UPCOMING_GAMES_CLICK_ToString_Accuracy() {
        assertEquals("The SHOW_UPCOMING_GAMES_CLICK UIEventType should be correct.", "show upcoming games",
            UIEventType.SHOW_UPCOMING_GAMES_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_LEADERS_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSHOW_LEADERS_CLICK_GetName_Accuracy() {
        assertEquals("The SHOW_LEADERS_CLICK UIEventType should be correct.", "show leaders",
            UIEventType.SHOW_LEADERS_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_LEADERS_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSHOW_LEADERS_CLICK_ToString_Accuracy() {
        assertEquals("The SHOW_LEADERS_CLICK UIEventType should be correct.", "show leaders",
            UIEventType.SHOW_LEADERS_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_LATEST_CLUE_CLICK UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testSHOW_LATEST_CLUE_CLICK_GetName_Accuracy() {
        assertEquals("The SHOW_LATEST_CLUE_CLICK UIEventType should be correct.", "show latest clue",
            UIEventType.SHOW_LATEST_CLUE_CLICK.getName());
    }

    /**
     * <p>
     * Tests the accuracy of SHOW_LATEST_CLUE_CLICK UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testSHOW_LATEST_CLUE_CLICK_ToString_Accuracy() {
        assertEquals("The SHOW_LATEST_CLUE_CLICK UIEventType should be correct.", "show latest clue",
            UIEventType.SHOW_LATEST_CLUE_CLICK.toString());
    }

    /**
     * <p>
     * Tests the accuracy of PAGE_CHANGED UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testPAGE_CHANGED_GetName_Accuracy() {
        assertEquals("The PAGE_CHANGED UIEventType should be correct.", "page changed",
            UIEventType.PAGE_CHANGED.getName());
    }

    /**
     * <p>
     * Tests the accuracy of PAGE_CHANGED UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testPAGE_CHANGED_ToString_Accuracy() {
        assertEquals("The PAGE_CHANGED UIEventType should be correct.", "page changed",
            UIEventType.PAGE_CHANGED.toString());
    }

    /**
     * <p>
     * Tests the accuracy of WORKING_GAME_ID_UPDATE UIEventType method <code>getName()</code>.
     * </p>
     */
    public void testWORKING_GAME_ID_UPDATE_GetName_Accuracy() {
        assertEquals("The WORKING_GAME_ID_UPDATE UIEventType should be correct.", "working game id update",
            UIEventType.WORKING_GAME_ID_UPDATE.getName());
    }

    /**
     * <p>
     * Tests the accuracy of WORKING_GAME_ID_UPDATE UIEventType method <code>toString()</code>.
     * </p>
     */
    public void testWORKING_GAME_ID_UPDATE_ToString_Accuracy() {
        assertEquals("The WORKING_GAME_ID_UPDATE UIEventType should be correct.", "working game id update",
            UIEventType.WORKING_GAME_ID_UPDATE.toString());
    }
}
