/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.message.messenger.Messenger;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Test case for MessageHandler.
 *
 * @author TCSDEVELOPER
 * @version 1.0
  */
public class MessageHandlerTest extends TestCase {
    /** Represents default ActionContext used in this test. */
    private ActionContext context;

    /** Represents the attributes. */
    private Map attributes = new HashMap();

    /** Represents default MessageHandler used in this test. */
    private MessageHandler handler;

    /** Represents default HttpRequest used in this test. */
    private MockHttpRequest request;

    /** Represents default HttpResponse used in this test. */
    private MockHttpResponse response;

    /** Represents default HttpSession used in this test. */
    private MockHttpSession session;

    /** Represents default ServletContext used in this test. */
    private MockServletContext servletContext;

    /**
     * Test method for execute(ActionContext). In this case, the context is null. Expected : {@link
     * IllegalArgumentException}.
     *
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test for {@link MessageHandler#MessageHandler(Element)}. No exception should be thrown.
     *
     * @throws Exception exception thrown to JUnit
     */
    public void testMessageHandler_Constructor2() throws Exception {
        Element element = TestHelper.parseElement("<?xml version=\"1.0\" ?>" + "<Root>" +
                "    <config name=\"valid_config\">" + "        <handler type=\"x\" >" +
                "            <plugin_name>some_name</plugin_name>" + "            <attribute_names>" +
                "                <value scope=\"request\">request_property_name</value>" +
                "            </attribute_names>" + "            <message_property_names>" +
                "                <value>property1</value>" + "            </message_property_names>" +
                "        </handler>" + "    </config>" + "</Root>");
        handler = new MessageHandler(element);
        testMessageHandler_Execute();
    }

    /**
     * Test method for {@link com.orpheus.game.MessageHandler#MessageHandler(org.w3c.dom.Element)}. In this
     * case, the element is null.
     */
    public void testMessageHandler_Element_NullElement() {
        try {
            new MessageHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test for {@link MessageHandler#execute(ActionContext)}. It should return null.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testMessageHandler_Execute() throws Exception {
        assertEquals("execute failed.", null, handler.execute(context));
    }

    /**
     * Test method for MessageHandler(java.lang.String, java.util.Map). In this case, the name is empty.
     */
    public void testMessageHandler_StringMap_EmptyName() {
        try {
            new MessageHandler(" ", attributes);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessageHandler(java.lang.String, java.util.Map). In this case, the attributes contains
     * empty..
     */
    public void testMessageHandler_StringMap_EmptyValueInAttributes() {
        try {
            attributes.put(new AttributeScope("tst", "request"), " ");
            new MessageHandler("test", attributes);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessageHandler(java.lang.String, java.util.Map). In this case, the attributes is null..
     */
    public void testMessageHandler_StringMap_NullAttributes() {
        try {
            new MessageHandler("test", null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessageHandler(java.lang.String, java.util.Map). In this case, the name is null.
     */
    public void testMessageHandler_StringMap_NullName() {
        try {
            new MessageHandler(null, attributes);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessageHandler(java.lang.String, java.util.Map). In this case, the attributes contains
     * null..
     */
    public void testMessageHandler_StringMap_NullValueInAttributes() {
        try {
            attributes.put(new AttributeScope("tst", "request"), null);
            new MessageHandler("test", attributes);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();

        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");
        handler = new MessageHandler("some_name", map);

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setAttribute("request_property_name", "request_property_value");

        Messenger messenger = Messenger.createInstance();
        messenger.registerPlugin("some_name", "com.topcoder.message.messenger.MockMessengerPlugin");

        JNDIHelper.initJNDI();
    }

    /**
     * Clear test environment.
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }
}
