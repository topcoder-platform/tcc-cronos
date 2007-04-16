/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.ajax;

import javax.servlet.http.HttpServletRequest;

import com.cronos.im.ajax.handler.AcceptChatRequestHandler;
import com.cronos.im.ajax.handler.ChangeManagerStatusHandler;
import com.cronos.im.ajax.handler.PostTextMessageHandler;
import com.cronos.im.ajax.handler.ReadClientSessionMessageHandler;
import com.cronos.im.ajax.handler.ReadClientUserMessageHandler;
import com.cronos.im.ajax.handler.ReadManagerSessionMessageHandler;
import com.cronos.im.ajax.handler.ReadManagerUserMessageHandler;

import junit.framework.TestCase;

/**
 * <p>
 * Demo for this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {

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
     * Demo for the IMAjaxSupportServlet.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_servlet() throws Exception {
        // set up servlet
        IMAjaxSupportServletTester service = new IMAjaxSupportServletTester();
        service.init();

        // prepare request
        String requestXml = "<request type=\"PostTextMessage\"> <session_id>456</session_id> <chat_text>some chat text</chat_text> </request>";
        MockHttpServletRequest req = (MockHttpServletRequest) TestHelper.createHttpServletRequest();
        req.setParameter(IMAjaxSupportUtility.getXMLRequestParamKey(), requestXml);

        // do service
        MockHttpServletResponse res = new MockHttpServletResponse();
        service.service(req, res);

        // validate result
        String expected = "<response><success>the text is posted</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the AcceptChatRequestHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_AcceptChatRequestHandler() throws Exception {
        String requestXml = "<request type=\"AcceptChatRequest\"> <user_id>123</user_id> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new AcceptChatRequestHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>the chat request is accepted</success></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the ChangeManagerStatusHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_ChangeManagerStatusHandler() throws Exception {
        String requestXml = "<request type=\"ChangeManagerStatus\"> <status>Online</status> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new ChangeManagerStatusHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>the manager status is successfully changed</success></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the PostTextMessageHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_PostTextMessageHandler() throws Exception {
        String requestXml = "<request type=\"PostTextMessage\"> <session_id>456</session_id> <chat_text>some chat text</chat_text> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new PostTextMessageHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>the text is posted</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the ReadClientSessionMessageHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_ReadClientSessionMessageHandler() throws Exception {
        String requestXml = "<request type=\"ReadClientSessionMessage\"> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new ReadClientSessionMessageHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>successfully</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the ReadClientUserMessageHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_ReadClientUserMessageHandler() throws Exception {
        String requestXml = "<request type=\"ReadClientUserMessage\"> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new ReadClientUserMessageHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>successfully</success><messages></messages>"
                + "<client_position>-1</client_position></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the ReadManagerSessionMessageHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_ReadManagerSessionMessageHandler() throws Exception {
        String requestXml = "<request type=\"ReadManagerSessionMessage\"> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new ReadManagerSessionMessageHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>successfully</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

    /**
     * Demo for the ReadManagerUserMessageHandler.
     *
     * @throws Exception
     *             to JUnit
     */
    public void test_demo_ReadManagerUserMessageHandler() throws Exception {
        String requestXml = "<request type=\"ReadManagerUserMessage\"> <session_id>456</session_id> </request>";
        HttpServletRequest req = TestHelper.createHttpServletRequest();
        MockHttpServletResponse res = new MockHttpServletResponse();
        new ReadManagerUserMessageHandler().handle(TestHelper.getElementFromString(requestXml), req, res);

        String expected = "<response><success>successfully</success><messages></messages></response>";
        assertEquals("The handle method is incorrect.", expected, res.getContent());
    }

}
