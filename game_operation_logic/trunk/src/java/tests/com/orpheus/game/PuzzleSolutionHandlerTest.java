/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.topcoder.user.profile.UserProfile;
import com.topcoder.util.puzzle.MockSolutionTester;
import com.topcoder.web.frontcontroller.ActionContext;

public class PuzzleSolutionHandlerTest extends TestCase {

    /**
     * Represents TODO DOCUMENT ME!
     */
    private ActionContext context;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockHttpRequest request;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockHttpResponse response;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockHttpSession session;

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MockServletContext servletContext;
    
    private PuzzleSolutionHandler handler;
    
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        
        JNDIHelper.initJNDI();
        
        handler = new PuzzleSolutionHandler("puzzle_id","Slot_id","base_name","incorrect_solution_result","slotCompletion");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        
        request.setParameter("puzzle_id","19");
        request.setParameter("Slot_id","1");
        
        session.setAttribute("base_name19", new MockSolutionTester());
		session.setAttribute("user_profile", new UserProfile(new Long(1)));
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getGameManagerKey(), new MockGameDataManager());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /*
     * Test method for 'com.orpheus.game.PuzzleSolutionHandler.PuzzleSolutionHandler(String, String, String, String)'
     */
    public void testPuzzleSolutionHandlerStringStringStringString() {
        assertNotNull("PuzzleSolutionHandler should be instantiated successfully",handler);
    }

    /*
     * Test method for 'com.orpheus.game.PuzzleSolutionHandler.PuzzleSolutionHandler(Element)'
     */
    public void testPuzzleSolutionHandlerElement() throws Exception{
        Element element = DocumentHelper.getDocument("/PuzzleSolutionHandler.xml", "config", "valie_config");
        PuzzleSolutionHandler handler = new PuzzleSolutionHandler(element);
        handler.execute(context);
    }

    /*
     * Test method for 'com.orpheus.game.PuzzleSolutionHandler.execute(ActionContext)'
     */
    public void testExecute() throws Exception{
        handler.execute(context);
    }

}
