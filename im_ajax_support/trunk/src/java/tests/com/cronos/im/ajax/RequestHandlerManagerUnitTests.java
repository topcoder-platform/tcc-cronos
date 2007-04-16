/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import junit.framework.TestCase;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Unit test cases for RequestHandlerManager class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RequestHandlerManagerUnitTests extends TestCase {

    /**
     * Reference to an instance of RequestHandlerManager for the following tests.
     */
    private RequestHandlerManager manager = null;

    /**
     * Initialize the environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.setUp();
        manager = new RequestHandlerManager();
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
     * Tests the constructor RequestHandlerManager() and the getAllHandlers method. No exception should be
     * thrown for valid configuration. And the handlers should be created correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor1_1() throws Exception {
        RequestHandlerManager mgr = new RequestHandlerManager();

        assertEquals("The handlers are not created correctly.", 7, mgr.getAllHandlers().size());
        assertNotNull("The AcceptChatRequest handler is incorrect.", mgr.getAllHandlers().get(
                "AcceptChatRequest"));
        assertNotNull("The ChangeManagerStatus handler is incorrect.", mgr.getAllHandlers().get(
                "ChangeManagerStatus"));
        assertNotNull("The PostTextMessage handler is incorrect.", mgr.getAllHandlers()
                .get("PostTextMessage"));
        assertNotNull("The ReadClientSessionMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadClientSessionMessage"));
        assertNotNull("The ReadClientUserMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadClientUserMessage"));
        assertNotNull("The ReadManagerSessionMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadManagerSessionMessage"));
        assertNotNull("The ReadManagerUserMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadManagerUserMessage"));
    }

    /**
     * Failure test for the constructor. IMAjaxConfigurationException should be thrown for invalid
     * configuration.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor1_Failure1() throws Exception {
        tearDown();
        try {
            new RequestHandlerManager();
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Tests the constructor RequestHandlerManager(String namespace) and the getAllHandlers method. No
     * exception should be thrown for valid configuration. And the handlers should be created correctly.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor2_1() throws Exception {
        RequestHandlerManager mgr = new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager");

        assertEquals("The handlers are not created correctly.", 7, mgr.getAllHandlers().size());
        assertNotNull("The AcceptChatRequest handler is incorrect.", mgr.getAllHandlers().get(
                "AcceptChatRequest"));
        assertNotNull("The ChangeManagerStatus handler is incorrect.", mgr.getAllHandlers().get(
                "ChangeManagerStatus"));
        assertNotNull("The PostTextMessage handler is incorrect.", mgr.getAllHandlers()
                .get("PostTextMessage"));
        assertNotNull("The ReadClientSessionMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadClientSessionMessage"));
        assertNotNull("The ReadClientUserMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadClientUserMessage"));
        assertNotNull("The ReadManagerSessionMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadManagerSessionMessage"));
        assertNotNull("The ReadManagerUserMessage handler is incorrect.", mgr.getAllHandlers().get(
                "ReadManagerUserMessage"));
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IMAjaxConfigurationException
     * should be thrown for invalid configuration (missing object_factory_namespace).
     */
    public void testConstructor2_Failure1() {
        try {
            new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager.Invalid1");
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IMAjaxConfigurationException
     * should be thrown for invalid configuration (incorrect object_factory_namespace).
     */
    public void testConstructor2_Failure2() {
        try {
            new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager.Invalid2");
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IMAjaxConfigurationException
     * should be thrown for invalid configuration (missing handler_types).
     */
    public void testConstructor2_Failure3() {
        try {
            new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager.Invalid3");
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IMAjaxConfigurationException
     * should be thrown for invalid configuration (empty handler_types).
     */
    public void testConstructor2_Failure4() {
        try {
            new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager.Invalid4");
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IMAjaxConfigurationException
     * should be thrown for invalid configuration (missing property for a handler type).
     */
    public void testConstructor2_Failure5() {
        try {
            new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager.Invalid5");
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IMAjaxConfigurationException
     * should be thrown for invalid configuration (empty string handler type).
     */
    public void testConstructor2_Failure6() {
        try {
            new RequestHandlerManager("com.cronos.im.ajax.RequestHandlerManager.Invalid6");
            fail("IMAjaxConfigurationException should be thrown for invalid configuration.");
        } catch (IMAjaxConfigurationException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IllegalArgumentException
     * should be thrown for null argument.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor2_Failure7() throws Exception {
        try {
            new RequestHandlerManager(null);
            fail("IllegalArgumentException should be thrown for null argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Failure test for the constructor RequestHandlerManager(String namespace). IllegalArgumentException
     * should be thrown for empty string argument.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor2_Failure8() throws Exception {
        try {
            new RequestHandlerManager("    ");
            fail("IllegalArgumentException should be thrown for empty string argument.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * Test the handle method. No exception should be thrown and the response text should be correct.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_1() throws Exception {
        String requestXml = "<request type=\"PostTextMessage\"> <session_id>456</session_id> <chat_text>some chat text</chat_text> </request>";
        MockHttpServletRequest req = (MockHttpServletRequest) TestHelper.createHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), requestXml);

        MockHttpServletResponse res = new MockHttpServletResponse();
        manager.handle(req, res);

        String expected = "<response><success>the text is posted</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Failure test for the handle method. Error message response should be returned.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_error1() throws Exception {
        HttpServletRequest req = TestHelper.createHttpServletRequest();

        MockHttpServletResponse res = new MockHttpServletResponse();
        manager.handle(req, res);

        String expected = "<response><failure>Error occured during handling the request</failure></response>";
        String s = res.getContent();
        assertEquals("The handle method is incorrect.", expected, s);
    }

    /**
     * Failure test for the handle method. Error message response should be returned.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_error2() throws Exception {
        String requestXml = "<request type=\"PostTextMessage\"> <session_id>456</session_id> <chat_text>some chat text</chat_text> </request>";
        MockHttpServletRequest req = (MockHttpServletRequest) TestHelper.createHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), requestXml);

        MockHttpServletResponse res = new MockHttpServletResponse();
        TestHelper.tearDown();
        manager.handle(req, res);

        String expected = "<response><failure>Error occured during handling the request</failure></response>";
        String s = res.getContent();
        assertEquals("The handle method is incorrect.", expected, s);
    }

    /**
     * Failure test for the handle method. If any argument is null, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_handle_failure_2() throws Exception {
        try {
            manager.handle(null, new MockHttpServletResponse());
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
            manager.handle(new MockHttpServletRequest(), null);
            fail("If any argument is null, IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

}
