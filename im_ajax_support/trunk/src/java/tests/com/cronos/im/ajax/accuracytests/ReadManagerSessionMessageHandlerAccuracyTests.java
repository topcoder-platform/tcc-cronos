/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.handler.ReadManagerSessionMessageHandler;

import junit.framework.TestCase;


/**
 * The test of ReadManagerSessionMessageHandler.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ReadManagerSessionMessageHandlerAccuracyTests extends TestCase {
    /** The tset ReadManagerSessionMessageHandler for testing. */
    private ReadManagerSessionMessageHandler instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new ReadManagerSessionMessageHandler();
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
     * Test method for 'ReadManagerSessionMessageHandler()'
     */
    public void testReadManagerSessionMessageHandler() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'handle(Element, HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testHandle() throws Exception {
        String inputXml = "<request type=\"ReadManagerSessionMessage\"><session_id>1</session_id> <chat_text>accuracy chat message</chat_text></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>successfully</success><messages></messages></response>", output);

    }
}
