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

import com.topcoder.message.messenger.Messenger;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

public class RegisterGameHandlerTest extends TestCase {

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
    
    private RegisterGameHandler handler;
    
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name","request"),"property1");
        handler = new RegisterGameHandler("game_id");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        
        request.setParameter("game_id","1");
        
        
        JNDIHelper.initJNDI();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /*
     * Test method for 'com.orpheus.game.RegisterGameHandler.RegisterGameHandler(String)'
     */
    public void testRegisterGameHandlerString() {
        assertNotNull("RegisterGameHandler should be instantiated successfully",handler);
    }

    /*
     * Test method for 'com.orpheus.game.RegisterGameHandler.RegisterGameHandler(Element)'
     */
    public void testRegisterGameHandlerElement() throws Exception{
        Element element = DocumentHelper.getDocument("/RegisterGameHandler.xml", "config", "valie_config");
        RegisterGameHandler handler = new RegisterGameHandler(element);
        handler.execute(context);
    }

    /*
     * Test method for 'com.orpheus.game.RegisterGameHandler.execute(ActionContext)'
     */
    public void testExecute() throws Exception{
        handler.execute(context);
    }

}
