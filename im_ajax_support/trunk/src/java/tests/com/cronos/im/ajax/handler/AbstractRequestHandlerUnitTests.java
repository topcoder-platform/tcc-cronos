/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import junit.framework.TestCase;

import com.cronos.im.ajax.TestHelper;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.Level;

/**
 * <p>
 * Unit test cases for AbstractRequestHandler class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractRequestHandlerUnitTests extends TestCase {

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();
    }

    /**
     * Clear the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.tearDown();
    }

    /**
     * Test the constructor. No exception should be thrown.
     */
    public void testConstructor() {
        // this will invoke the constructor of the base class
        new AbstractRequestHandlerTester();
    }

    /**
     * Test the constructor. No exception should be thrown.
     */
    public void testGetLog() {
        // this will invoke the constructor of the base class
        Log log = new AbstractRequestHandlerTester().callGetLog();
        assertNotNull("The getLog method is incorrect.", log);

        // no exception
        log.log(Level.ERROR, "Logging: some error message");
    }
}
