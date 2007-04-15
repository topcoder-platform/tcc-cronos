/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.handler.ChangeManagerStatusHandler;

import junit.framework.TestCase;


/**
 * The test of ChangeManagerStatusHandler.
 *
 * @author brain_cn
 * @version 1.0
 */
public class ChangeManagerStatusHandlerAccuracyTests extends TestCase {
    /** The tset ChangeManagerStatusHandler for testing. */
    private ChangeManagerStatusHandler instance;

    /**
     * <p>
     * Setup the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Utility.loadNamespaces();
        instance = new ChangeManagerStatusHandler();
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
     * Test method for 'ChangeManagerStatusHandler()'
     */
    public void testChangeManagerStatusHandler() {
        assertNotNull("Failed to create instance", instance);
    }

    /**
     * Test method for 'handle(Element, HttpServletRequest, HttpServletResponse)'
     *
     * @throws Exception to JUnit
     */
    public void testHandle() throws Exception {
        String inputXml = "<request type=\"ChangeManagerStatus\"><status>Busy</status></request>";
        String output = Utility.handle(inputXml, instance);
        assertEquals("incorrect output", "<response><success>the manager status is successfully changed</success></response>", output);
    }
}
