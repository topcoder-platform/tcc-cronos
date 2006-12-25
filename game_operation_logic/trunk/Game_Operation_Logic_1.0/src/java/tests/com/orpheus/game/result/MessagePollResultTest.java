/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.result;

import com.orpheus.game.DocumentHelper;
import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.JNDIHelper;
import com.orpheus.game.TestHelper;

import com.topcoder.util.rssgenerator.MockDataStore;

import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.ResultExecutionException;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;


/**
 * Test case for MessagePollResult.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MessagePollResultTest extends TestCase {
    /** Default ActionContext used in this test. */
    private ActionContext context;

    /** Default MessagePollResult used in this test. */
    private MessagePollResult messagePollResult;

    /** Default HttpRequest used in this test. */
    private MockHttpRequest request;

    /** Default HttpResponse used in this test. */
    private MockHttpResponse response;

    /** Default HttpSession used in this test. */
    private MockHttpSession session;

    /** Default ServletContext used in this test. */
    private MockServletContext servletContext;

    /**
     * Test execute(ActionContext) with valid config. No exception should be thrown in this case.
     *
     * @throws ResultExecutionException to junit
     */
    public void testExecute() throws ResultExecutionException {
        messagePollResult.execute(context);
    }

    /**
     * Test method for execute(ActionContext). In this case, the context is null. Expected : {@link
     * IllegalArgumentException}.
     *
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            messagePollResult.execute(null);
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test ctor(Element) with valid config, no exception should be thrown.
     *
     * @throws Exception to junit
     */
    public void testMessagePollResultElement() throws Exception {
        Element element = DocumentHelper.getDocument("/MessagePollResult.xml", "config", "valid_config");
        MessagePollResult result = new MessagePollResult(element);
        result.execute(context);
    }

    /**
     * Test ctor(String dateParamKey, String[] categoryNames) with valid config, no exception should be thrown.
     */
    public void testMessagePollResultStringStringArray() {
        assertNotNull("MessagePollResult should be instantiated successfully", messagePollResult);
    }

    /**
     * Test method for MessagePollResult(org.w3c.dom.Element). In this case, the element contains empty name.
     *
     * @throws Exception to JUnit
     */
    public void testMessagePollResult_Element_EmptyNameInElement()
        throws Exception {
        try {
            String xml = "<result name=\"x\" userLocalInterface=\"true\">" +
                "<date_param_key> some key </date_param_key>" + "<category_names>" + "<value> </value>" +
                "<value>some value</value>" + "<value>some value</value>" + "</category_names>" + "</result>";
            new MessagePollResult(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(org.w3c.dom.Element). In this case, the element contains empty names.
     *
     * @throws Exception to JUnit
     */
    public void testMessagePollResult_Element_EmptyNamesInElement()
        throws Exception {
        try {
            String xml = "<result name=\"x\" userLocalInterface=\"true\">" +
                "<date_param_key> some key </date_param_key>" + "<category_names>" + "</category_names>" + "</result>";
            new MessagePollResult(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(org.w3c.dom.Element). In this case, the element is null.
     */
    public void testMessagePollResult_Element_NullElement() {
        try {
            new MessagePollResult(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]). In this case, the names
     * contains empty.
     */
    public void testMessagePollResult_StringStringArray_EmptyInNames() {
        try {
            new MessagePollResult("test", new String[] { " " });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]). In this case, the names
     * contains null.
     */
    public void testMessagePollResult_StringStringArray_NullInNames() {
        try {
            new MessagePollResult("test", new String[] { null });
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]). In this case, the names is
     * null.
     */
    public void testMessagePollResult_StringStringArray_NullNames() {
        try {
            new MessagePollResult(null, null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Sets up test environment.
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();

        messagePollResult = new MessagePollResult("date", new String[] { "gameA", "gameB", "gameC" });

        servletContext = new MockServletContext();
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getDataStoreKey(), new MockDataStore());

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);
        request.setParameter("date", "2006-10-10T12:33:32,000-05:00");
        JNDIHelper.initJNDI();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }
}
