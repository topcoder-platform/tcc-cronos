/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.accuracytests;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.easymock.MockControl;
import com.orpheus.game.GameDetailHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.orpheus.game.persistence.Game;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.Handler;

/**
 * <p>
 * Accuracy test for GameDetailHandler class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDetailHandlerAccuracyTest extends TestCase {
    /**
     * The game key.
     */
    private static String ID_KEY = "id";

    /**
     * The game key.
     */
    private static String DETAIL_KEY = "detail";

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
     * Test ctor GameDetailHandler(String gameIdParamKey, String gameDetailKey),
     * an instance with the given arguments should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor1() throws Exception  {        
        GameDetailHandler handler = new GameDetailHandler(ID_KEY, DETAIL_KEY);
        assertTrue("The instance should be extened from Handler", handler instanceof Handler);
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(GameDetailHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DETAIL_KEY, 
                AccuracyHelper.getFieldValue(GameDetailHandler.class, "gameDetailKey", handler));
    }
    
    /**
     * Test ctor GameDetailHandler(Element element),
     * an instance with the given element should be created.
     * 
     * @throws Exception to JUnit
     */
    public void testCtor2() throws Exception  {        
        GameDetailHandler handler = new GameDetailHandler(
                AccuracyHelper.getDomElement("GameDetailHandler.xml"));
        assertEquals("The value not set correctly", ID_KEY, 
                AccuracyHelper.getFieldValue(GameDetailHandler.class, "gameIdParamKey", handler));
        assertEquals("The value not set correctly", DETAIL_KEY, 
                AccuracyHelper.getFieldValue(GameDetailHandler.class, "gameDetailKey", handler));
    }

    /**
     * Test execute(ActionContext context), the game should be set in attributes.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testExecute() throws Exception {        
        HttpServletRequest request = (HttpServletRequest) requestControl.getMock();
        MyHttpServletRequest wrapper = new MyHttpServletRequest(request);
        wrapper.setParameter(ID_KEY, "10001");
        HttpServletResponse response = (HttpServletResponse) responseControl.getMock();
        ActionContext context = new ActionContext(wrapper, response);
        
        assertNull("failed to execute handler", new GameDetailHandler(ID_KEY, DETAIL_KEY).execute(context));
        assertEquals("failed to execute handler", 10001, 
                ((Game) wrapper.getAttribute(DETAIL_KEY)).getId().longValue());
    }
}
