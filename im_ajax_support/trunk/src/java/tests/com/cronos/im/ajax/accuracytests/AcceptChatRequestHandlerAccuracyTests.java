/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.handler.AcceptChatRequestHandler;

import junit.framework.TestCase;


/**
 * The test of AcceptChatRequestHandler.
 *
 * @author brain_cn
 * @version 1.0
 */
public class AcceptChatRequestHandlerAccuracyTests extends TestCase {
    /** The tset AcceptChatRequestHandler for testing. */
    private AcceptChatRequestHandler instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new AcceptChatRequestHandler();
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
     * Test method for 'AcceptChatRequestHandler()'
     */
    public void testAcceptChatRequestHandler() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'handle(Element, HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testHandle() throws Exception {
        String inputXml = "<request type=\"AcceptChatRequest\"><user_id>1</user_id><session_id>1</session_id></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>the chat request is accepted</success></response>", output);
    }
}
