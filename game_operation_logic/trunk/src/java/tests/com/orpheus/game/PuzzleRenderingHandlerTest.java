/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.topcoder.util.puzzle.MockPuzzleTypeSource;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

public class PuzzleRenderingHandlerTest extends TestCase {

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
    
    private PuzzleRenderingHandler handler;
    
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name","request"),"property1");
        handler = new PuzzleRenderingHandler("puzzle_id","media_type","base_name","puzzle_string");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        
        request.setAttribute("puzzle_id",new Long(1));
        request.setAttribute("media_type","type");
        servletContext.setAttribute("PuzzleTypeSource", new MockPuzzleTypeSource());
        JNDIHelper.initJNDI();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /*
     * Test method for 'com.orpheus.game.PuzzleRenderingHandler.PuzzleRenderingHandler(String, String, String, String)'
     */
    public void testPuzzleRenderingHandlerStringStringStringString() {
        assertNotNull("PuzzleRenderingHandler should be instantiated successfully",handler);
    }

    /*
     * Test method for 'com.orpheus.game.PuzzleRenderingHandler.PuzzleRenderingHandler(Element)'
     */
    public void testPuzzleRenderingHandlerElement() throws Exception{
        Element element = DocumentHelper.getDocument("/PuzzleRenderingHandler.xml", "config", "valie_config");
        PuzzleRenderingHandler handler = new PuzzleRenderingHandler(element);
        handler.execute(context);
    }

    /*
     * Test method for 'com.orpheus.game.PuzzleRenderingHandler.execute(ActionContext)'
     */
    public void testExecute() throws Exception {
        handler.execute(context);
    }

}
