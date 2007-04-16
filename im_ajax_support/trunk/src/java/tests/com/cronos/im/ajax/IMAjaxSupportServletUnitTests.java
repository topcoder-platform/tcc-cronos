/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;

import com.cronos.im.messenger.Messenger;
import com.topcoder.chat.session.ChatSessionManager;
import com.topcoder.chat.status.ChatStatusTracker;
import com.topcoder.service.ServiceEngine;

/**
 * <p>
 * Unit test cases for IMAjaxSupportServlet class.
 * </p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class IMAjaxSupportServletUnitTests extends TestCase {

    /**
     * An instance of IMAjaxSupportServletTester for the following tests.
     */
    private IMAjaxSupportServletTester tester = null;

    /**
     * Initialize the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();

        tester = new IMAjaxSupportServletTester();
        tester.init();
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
        new IMAjaxSupportServlet();
    }

    /**
     * Test the init method. No exception should be thrown.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testInit_accuracy1() throws Exception {
        // no exception
        tester.init();

        // verify the results
        ServletContext context = tester.getServletContext();
        assertTrue("Messenger is not created correctly.", context.getAttribute(IMAjaxSupportUtility
                .getIMMessengerKey()) instanceof Messenger);
        assertTrue("ChatSessionManager is not created correctly.", context.getAttribute(IMAjaxSupportUtility
                .getChatSessionManagerKey()) instanceof ChatSessionManager);
        assertTrue("ChatStatusTracker is not created correctly.", context.getAttribute(IMAjaxSupportUtility
                .getChatStatusTrackerKey()) instanceof ChatStatusTracker);
        assertTrue("ServiceEngine is not created correctly.", context.getAttribute(IMAjaxSupportUtility
                .getServiceEngineKey()) instanceof ServiceEngine);
    }

    /**
     * Test the init method. After the initialization, the servlet should work properly.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testInit_accuracy2() throws Exception {
        tester = new IMAjaxSupportServletTester();
        // after the initialization, the servlet should work properly,
        // this will test if the init method works as expected
        tester.init();

        String requestXml = "<request type=\"PostTextMessage\"> <session_id>456</session_id> <chat_text>some chat text</chat_text> </request>";
        MockHttpServletRequest req = (MockHttpServletRequest) TestHelper.createHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), requestXml);

        MockHttpServletResponse res = new MockHttpServletResponse();
        tester.service(req, res);

        String expected = "<response><success>the text is posted</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Failure test for the init method. ServletException should be thrown for invalid configuration.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void testInit_failure_invalid_config() throws Exception {
        tearDown();
        try {
            tester.init();
            fail("ServletException should be thrown for invalid configuration.");
        } catch (ServletException e) {
            // ok
        }
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_service_1() throws Exception {
        String requestXml = "<request type=\"PostTextMessage\"> <session_id>456</session_id> <chat_text>some chat text</chat_text> </request>";
        MockHttpServletRequest req = (MockHttpServletRequest) TestHelper.createHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), requestXml);

        MockHttpServletResponse res = new MockHttpServletResponse();
        tester.service(req, res);

        String expected = "<response><success>the text is posted</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Failure test for the handle method. Error message response should be returned.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_service_failure_1() throws Exception {
        HttpServletRequest req = TestHelper.createHttpServletRequest();

        MockHttpServletResponse res = new MockHttpServletResponse();
        tester.service(req, res);

        String expected = "<response><failure>";
        assertTrue("The handle method is incorrect.", res.getContent().indexOf(expected) >= 0);
    }

    /**
     * Failure test for the handle method. If any argument is null, IllegalArgumentException should be thrown.
     * 
     * @throws Exception
     *             to JUnit
     */
    public void test_service_failure_2() throws Exception {
        try {
            tester.service(null, new MockHttpServletResponse());
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
    public void test_service_failure_3() throws Exception {
        try {
            tester.service(new MockHttpServletRequest(), null);
            fail("If any argument is null, IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
