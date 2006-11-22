/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.MockControl;

import com.orpheus.game.TestDomainHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.orpheus.game.persistence.Game;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy test for TestDomainHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestDomainHandlerAccuracyTest extends TestCase {
    /**
     * The game key.
     */
    private static String DOMAIN_KEY = "domain";
    
    /**
     * The game key.
     */
    private static String GAMES_KEY = "games";
    
    /**
     * The not logged key.
     */
    private static String NOT_LOGGED_KEY = "not_logged_in";

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
     * Test ctor TestDomainHandler(String domainNameParamKey, String gamesKey, String notLoggedInResultCode),
     * an instance with the given arguments should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {        
        TestDomainHandler handler = new TestDomainHandler(DOMAIN_KEY, GAMES_KEY, NOT_LOGGED_KEY);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(TestDomainHandler.class, "domainNameParamKey", handler));
        assertEquals("The value not set correctly", GAMES_KEY, 
                AccuracyHelper.getFieldValue(TestDomainHandler.class, "gamesKey", handler));
        assertEquals("The value not set correctly", NOT_LOGGED_KEY, 
                AccuracyHelper.getFieldValue(TestDomainHandler.class, "notLoggedInResultCode", handler));
    }
    
    /**
     * Test ctor TestDomainHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        TestDomainHandler handler = new TestDomainHandler(
                AccuracyHelper.getDomElement("TestDomainHandler.xml"));
        assertEquals("The value not set correctly", DOMAIN_KEY, 
                AccuracyHelper.getFieldValue(TestDomainHandler.class, "domainNameParamKey", handler));
        assertEquals("The value not set correctly", GAMES_KEY, 
                AccuracyHelper.getFieldValue(TestDomainHandler.class, "gamesKey", handler));
        assertEquals("The value not set correctly", NOT_LOGGED_KEY, 
                AccuracyHelper.getFieldValue(TestDomainHandler.class, "notLoggedInResultCode", handler));
    }

    /**
     * Test execute(ActionContext context),
     * when session not login in, NOT_LOGGED_KEY is returned.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute_UnAuthenticated() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(true);
        ActionContext context = new ActionContext(wrapper, response);
        
        assertEquals("failed to execute handler", NOT_LOGGED_KEY, 
                new TestDomainHandler(DOMAIN_KEY, GAMES_KEY, NOT_LOGGED_KEY).execute(context));
    }

    /**
     * Test execute(ActionContext context),
     * all game in domain should be returned.
     * 
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute() throws Exception {
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setNullSesssion(false);
        wrapper.setParameter(DOMAIN_KEY, "domain");
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler", new TestDomainHandler(DOMAIN_KEY, GAMES_KEY, NOT_LOGGED_KEY).execute(context));
        Game[] games = (Game[]) wrapper.getAttribute(GAMES_KEY);
        assertEquals("failed to execute handler", 3, games.length);
        assertEquals("failed to execute handler", 1, games[0].getId().longValue());
        assertEquals("failed to execute handler", 2, games[1].getId().longValue());
        assertEquals("failed to execute handler", 3, games[2].getId().longValue());
    }
}
