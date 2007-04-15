/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.RequestHandler;
import com.cronos.im.ajax.RequestHandlerManager;
import com.cronos.im.ajax.handler.AcceptChatRequestHandler;
import com.cronos.im.ajax.handler.ChangeManagerStatusHandler;
import com.cronos.im.ajax.handler.PostTextMessageHandler;
import com.cronos.im.ajax.handler.ReadClientSessionMessageHandler;
import com.cronos.im.ajax.handler.ReadClientUserMessageHandler;
import com.cronos.im.ajax.handler.ReadManagerSessionMessageHandler;
import com.cronos.im.ajax.handler.ReadManagerUserMessageHandler;

import junit.framework.TestCase;

import java.util.Map;


/**
 * The test of RequestHandlerManager.
 *
 * @author brain_cn
 * @version 1.0
 */
public class RequestHandlerManagerAccuracyTests extends TestCase {
    /** The tset RequestHandlerManager for testing. */
    private RequestHandlerManager instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new RequestHandlerManager();
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        Utility.releaseNamespaces();
    }

    /**
     * Test method for 'RequestHandlerManager()'
     */
    public void testRequestHandlerManager() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'RequestHandlerManager(String)'
     *
     * @throws Exception to JUnit
     */
    public void testRequestHandlerManagerString() throws Exception {
        assertNotNull("Failed to create instance", new RequestHandlerManager(RequestHandlerManager.class.getName()));
    }

    /**
     * Test method for 'handle(HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testHandle() throws Exception {
        String inputXml = "<request type=\"ReadManagerUserMessage\"><session_id>1</session_id><chat_text>accuracy chat message</chat_text></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>successfully</success><messages></messages></response>", output);
    }

    /**
     * Test method for 'getAllHandlers()'
     */
    public void testGetAllHandlers() {
        Map handlers = instance.getAllHandlers();
        assertEquals("incorect size of handlers", handlers.size(), 7);

        // Verify every handler
        String type = "AcceptChatRequest";
        RequestHandler handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof AcceptChatRequestHandler);

        type = "ChangeManagerStatus";
        handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof ChangeManagerStatusHandler);

        type = "PostTextMessage";
        handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof PostTextMessageHandler);

        type = "ReadClientSessionMessage";
        handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof ReadClientSessionMessageHandler);

        type = "ReadClientUserMessage";
        handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof ReadClientUserMessageHandler);

        type = "ReadManagerSessionMessage";
        handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof ReadManagerSessionMessageHandler);

        type = "ReadManagerUserMessage";
        handler = (RequestHandler) handlers.get(type);
        assertNotNull("Miss handler for type " + type, handler);
        assertTrue("Incorrect handler for type " + type, handler instanceof ReadManagerUserMessageHandler);
    }
}
