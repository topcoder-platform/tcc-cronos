/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.MockControl;

import com.orpheus.game.result.MessagePollResult;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;
import com.topcoder.web.frontcontroller.ResultExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>MessagePollResult</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class MessagePollResultTest extends TestCase {

    /**
     * Represents the result to test.
     */
    private MessagePollResult result;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        result = new MessagePollResult("test", new String[] {"test"});
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]).
     * In this case, the names is null.
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
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]).
     * In this case, the names is empty.
     */
    public void testMessagePollResult_StringStringArray_EmptyNames() {
        try {
            new MessagePollResult("test", new String[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]).
     * In this case, the names contains empty.
     */
    public void testMessagePollResult_StringStringArray_EmptyInNames() {
        try {
            new MessagePollResult("test", new String[] {" "});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(java.lang.String, java.lang.String[]).
     * In this case, the names contains null.
     */
    public void testMessagePollResult_StringStringArray_NullInNames() {
        try {
            new MessagePollResult("test", new String[] {null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(org.w3c.dom.Element).
     * In this case, the element is null.
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
     * Test method for MessagePollResult(org.w3c.dom.Element).
     * In this case, the element contains empty name.
     * @throws Exception to JUnit
     */
    public void testMessagePollResult_Element_EmptyNameInElement() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<result name=\"x\" userLocalInterface=\"true\">"
                + "<date_param_key> some key </date_param_key>"
                + "<category_names>"
                + "<value> </value>"
                + "<value>some value</value>"
                + "<value>some value</value>"
                + "</category_names>"
                + "</result>";
            new MessagePollResult(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for MessagePollResult(org.w3c.dom.Element).
     * In this case, the element contains empty names.
     * @throws Exception to JUnit
     */
    public void testMessagePollResult_Element_EmptyNamesInElement() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<result name=\"x\" userLocalInterface=\"true\">"
                + "<date_param_key> some key </date_param_key>"
                + "<category_names>"
                + "</category_names>"
                + "</result>";
            new MessagePollResult(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            result.execute(null);
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link ResultExecutionException}.
     * @throws Exception to JUnit
     */
    public void testExecute_FailedToLoad() throws Exception {
        try {
            MockControl reqControl = MockControl.createNiceControl(HttpServletRequest.class);
            MockControl resControl = MockControl.createControl(HttpServletResponse.class);
            ActionContext ac = new ActionContext(
                    (HttpServletRequest) reqControl.getMock(),
                    (HttpServletResponse) resControl.getMock());
            result.execute(ac);
            fail("ResultExecutionException expected.");
        } catch (ResultExecutionException e) {
            // should land here
        }
    }

}
