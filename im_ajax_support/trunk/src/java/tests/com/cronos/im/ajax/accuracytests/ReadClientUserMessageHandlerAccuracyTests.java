/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.handler.ReadClientUserMessageHandler;

import junit.framework.TestCase;


/**
 * The test of ReadClientUserMessageHandler.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReadClientUserMessageHandlerAccuracyTests extends TestCase {
    /** The tset ReadClientUserMessageHandler for testing. */
    private ReadClientUserMessageHandler instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new ReadClientUserMessageHandler();
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
     * Test method for 'ReadClientUserMessageHandler()'
     */
    public void testReadClientUserMessageHandler() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'handle(Element, HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testHandle() throws Exception {
        String inputXml = "<request type=\"ReadClientUserMessage\"><session_id>1</session_id> <chat_text>accuracy chat message</chat_text></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>successfully</success><messages></messages><client_position>-1</client_position></response>", output);

    }
}
