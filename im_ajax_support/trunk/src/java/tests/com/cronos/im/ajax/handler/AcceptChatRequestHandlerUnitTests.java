/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import junit.framework.TestCase;

import com.cronos.im.ajax.TestHelper;
import javax.servlet.http.HttpServletRequest;
import com.cronos.im.ajax.MockHttpServletResponse;
import com.cronos.im.ajax.MockHttpServletRequest;

/**
 * <p>
 * Unit test cases for AcceptChatRequestHandler class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AcceptChatRequestHandlerUnitTests extends TestCase {

    /**
     * Reference to an instance of AcceptChatRequestHandler for the following tests.
     */
    private AcceptChatRequestHandler handler = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();

        handler = new AcceptChatRequestHandler();
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
        new AcceptChatRequestHandler();
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_1() throws Exception {
        String requestXml = "<request type=\"AcceptChatRequest\"> <user_id>123</user_id> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>the chat request is accepted</success></response>";
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
