/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax.handler;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import com.cronos.im.ajax.MockHttpServletRequest;
import com.cronos.im.ajax.MockHttpServletResponse;
import com.cronos.im.ajax.TestHelper;

/**
 * <p>
 * Unit test cases for ChangeManagerStatusHandler class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ChangeManagerStatusHandlerUnitTests extends TestCase {

    /**
     * Reference to an instance of ChangeManagerStatusHandler for the following tests.
     */
    private ChangeManagerStatusHandler handler = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();

        handler = new ChangeManagerStatusHandler();
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
        new ChangeManagerStatusHandler();
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_1() throws Exception {
        String requestXml = "<request type=\"ChangeManagerStatus\"> <status>Online</status> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>the manager status is successfully changed</success></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_invalid_role() throws Exception {
        TestHelper.tearDown();
        TestHelper.setUp2();

        String requestXml = "<request type=\"ChangeManagerStatus\"> <status>Online</status> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><failure>invalid role</failure></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_invalid_status() throws Exception {
        String requestXml = "<request type=\"ChangeManagerStatus\"> <status>unknown</status> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        handler.handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><failure>status must be Online, Busy or Offline</failure></response>";
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
