/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.accuracytests;

import com.cronos.im.ajax.handler.ChangeManagerStatusHandler;

import junit.framework.TestCase;


/**
 * The test of AbstractRequestHandler.
 *
 * @author brain_cn
 * @version 1.0
 */
public class AbstractRequestHandlerAccuracyTests extends TestCase {
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
     * Test method for 'AbstractRequestHandler()'
     */
    public void testAbstractRequestHandler() {
        assertNotNull("Failed to create instance", instance);
    }
}
