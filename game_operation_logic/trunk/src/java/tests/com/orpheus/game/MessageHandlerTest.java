/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.topcoder.message.messenger.Messenger;
import com.topcoder.web.frontcontroller.ActionContext;

public class MessageHandlerTest extends TestCase {
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

    /**
     * Represents TODO DOCUMENT ME!
     */
    private MessageHandler handler;
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        
        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name","request"),"property1");
        handler = new MessageHandler("some_name",map);

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        
        request.setAttribute("request_property_name","request_property_value");
        
        Messenger messenger = Messenger.createInstance();
        messenger.registerPlugin("some_name","com.topcoder.message.messenger.MockMessengerPlugin");
        
        JNDIHelper.initJNDI();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /*
     * Test method for 'com.orpheus.game.MessageHandler.MessageHandler(String, Map)'
     */
    public void testMessageHandlerStringMap() {
        assertNotNull("MessageHandler should be initiated successfully",handler);
    }

    /*
     * Test method for 'com.orpheus.game.MessageHandler.MessageHandler(Element)'
     */
    public void testMessageHandlerElement() throws Exception{
        Element element = DocumentHelper.getDocument("/MessageHandler.xml", "config", "valie_config");
        handler = new MessageHandler(element);
        handler.execute(context);
    }

    /*
     * Test method for 'com.orpheus.game.MessageHandler.execute(ActionContext)'
     */
    public void testExecute() throws Exception{
        handler.execute(context);
    }

}
