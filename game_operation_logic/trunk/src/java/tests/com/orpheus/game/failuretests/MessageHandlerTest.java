/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.AttributeScope;
import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.MessageHandler;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ResultExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>MessageHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class MessageHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private MessageHandler handler;

    /**
     * Represents the attributes.
     */
    private Map attributes = new HashMap();

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        attributes.put(new AttributeScope("test", "request"), "message");
        handler = new MessageHandler("test", attributes);
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for MessageHandler(java.lang.String, java.util.Map).
     * In this case, the name is null.
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
     * Test method for MessageHandler(java.lang.String, java.util.Map).
     * In this case, the name is empty.
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
     * Test method for MessageHandler(java.lang.String, java.util.Map).
     * In this case, the attributes is null..
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
     * Test method for MessageHandler(java.lang.String, java.util.Map).
     * In this case, the attributes contains null..
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
     * Test method for MessageHandler(java.lang.String, java.util.Map).
     * In this case, the attributes contains empty..
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
     * Test method for {@link com.orpheus.game.MessageHandler#MessageHandler(org.w3c.dom.Element)}.
     * In this case, the element is null.
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
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link IllegalArgumentException}.
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
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link ResultExecutionException}.
     * @throws Exception to JUnit
     */
    public void testExecute_FailedToLoad() throws Exception {
        try {
        	ServletContext servletContext = new MockServletContext();
    		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
    				.getDataStoreKey(), new MockDataStore());

    		HttpSession session = new MockHttpSession(servletContext);
    		MockHttpRequest mockRequest = new MockHttpRequest(session);
    		HttpServletRequest request = mockRequest;

    		HttpServletResponse response = new MockHttpResponse();
            ActionContext ac = new ActionContext(request, response);
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }

}
