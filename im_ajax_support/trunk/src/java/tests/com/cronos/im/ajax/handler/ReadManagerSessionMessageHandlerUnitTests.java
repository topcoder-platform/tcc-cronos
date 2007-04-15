/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import junit.framework.TestCase;
import com.cronos.im.ajax.MockHttpServletRequest;
import com.cronos.im.ajax.MockHttpServletResponse;
import com.cronos.im.ajax.TestHelper;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Unit test cases for ReadManagerSessionMessageHandler class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReadManagerSessionMessageHandlerUnitTests extends TestCase {

    /**
     * Reference to an instance of ReadManagerSessionMessageHandler for the following tests.
     */
    private ReadManagerSessionMessageHandler handler = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();

        handler = new ReadManagerSessionMessageHandler();
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
        // no exception
        new ReadManagerSessionMessageHandler();
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_1() throws Exception {
        String requestXml = "<request type=\"ReadManagerSessionMessage\"> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>successfully</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_2() throws Exception {
        TestHelper.tearDown();
        TestHelper.setUp2();

        String requestXml = "<request type=\"ReadManagerSessionMessage\"> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><failure>invalid role</failure></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Failure test for the handle method. If any argument is null, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_failure_1() throws Exception {
        try {
            handler.handle(null, new MockHttpServletRequest(), new MockHttpServletResponse());
            fail("If any argument is null, IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the handle method. If any argument is null, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_failure_2() throws Exception {
        try {
            handler.handle(TestHelper.getElementFromString("<a/>"), null, new MockHttpServletResponse());
            fail("If any argument is null, IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the handle method. If any argument is null, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_failure_3() throws Exception {
        try {
            handler.handle(TestHelper.getElementFromString("<a/>"), new MockHttpServletRequest(), null);
            fail("If any argument is null, IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
