/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.handler.ReadClientSessionMessageHandler;

import junit.framework.TestCase;


/**
 * The test of ReadClientSessionMessageHandler.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReadClientSessionMessageHandlerAccuracyTests extends TestCase {
    /** The tset ReadClientSessionMessageHandler for testing. */
    private ReadClientSessionMessageHandler instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new ReadClientSessionMessageHandler();
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
     * Test method for 'ReadManagerUserMessageHandler()'
     */
    public void testReadManagerUserMessageHandler() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'handle(Element, HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testHandle() throws Exception {
        String inputXml = "<request type=\"ReadClientSessionMessage\"><session_id>1</session_id> <chat_text>accuracy chat message</chat_text></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>successfully</success><messages></messages></response>", output);

    }
}
