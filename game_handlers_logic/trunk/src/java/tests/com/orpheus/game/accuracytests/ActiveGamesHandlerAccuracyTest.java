/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.ActiveGamesHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.orpheus.game.persistence.Game;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for ActiveGamesHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ActiveGamesHandlerAccuracyTest extends TestCase {
    /**
     * The game key.
     */
    private static String KEY = "games";

    /**
     * Represents the request.
     */
    private MockControl requestControl; 
    
    /**
     * Represents the response.
     */
    private MockControl responseControl;
    
    /**
     * Set up for each test.
     * 
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception  {
        super.setUp();
        AccuracyHelper.loadConfig();
        AccuracyHelper.deployEJB();
        requestControl = MockControl.createNiceControl(HttpServletRequest.class);
        responseControl = MockControl.createControl(HttpServletResponse.class);
    }
    
    /**
     * Tear down for each test.
     * 
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception  {
        AccuracyHelper.removeAllNamespace();
        super.tearDown();
    }
    
    /**
     * Test ctor ActiveGamesHandler(String gamesKey),
     * an instance with the given gamesKey should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {        
        ActiveGamesHandler handler = new ActiveGamesHandler(KEY);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", KEY, 
                AccuracyHelper.getFieldValue(ActiveGamesHandler.class, "gamesKey", handler));
    }
    
    /**
     * Test ctor ActiveGamesHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        ActiveGamesHandler handler = new ActiveGamesHandler(
                AccuracyHelper.getDomElement("ActiveGamesHandler.xml"));
        assertEquals("The value not set correctly", KEY, 
                AccuracyHelper.getFieldValue(ActiveGamesHandler.class, "gamesKey", handler));
    }

    /**
     * Test execute(ActionContext context), the games should be set in attributes.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecuteAccuracy() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        ActionContext context = new ActionContext(wrapper, response);        
        assertNull("failed to execute handler", new ActiveGamesHandler(KEY).execute(context));
        Game[] games = (Game[]) wrapper.getAttribute(KEY);
        assertEquals("failed to execute handler", 3, games.length);
        for (int i = 0; i < games.length; i++) {
            assertEquals("failed to execute handler", i + 1, games[i].getId().longValue());
        }
    }
}