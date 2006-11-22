/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.failuretests;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.easymock.MockControl;

import com.orpheus.game.SlotValidationHandler;
import com.orpheus.game.TestDomainHandler;
import com.orpheus.game.accuracytests.mock.MyHttpServletRequest;
import com.topcoder.web.frontcontroller.ActionContext;
import com.topcoder.web.frontcontroller.HandlerExecutionException;

import junit.framework.TestCase;

/**
 * Test case for <code>TestDomainHandler</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class TestDomainHandlerTest extends TestCase {

    /**
     * Represents the handler to test.
     */
    private TestDomainHandler handler;

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
        handler = new TestDomainHandler("id1", "id2", "id3");
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for TestDomainHandler(java.lang.String, java.lang.String, java.lang.String).
     * In this case, the domain name key is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_StringStringString_NullDomainNameKey() {
        try {
            new TestDomainHandler(null, "id2", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(java.lang.String, java.lang.String, java.lang.String).
     * In this case, the domain name key is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_StringStringString_EmptyDomainNameKey() {
        try {
            new TestDomainHandler(" ", "id2", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(java.lang.String, java.lang.String, java.lang.String).
     * In this case, the games key is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_StringStringString_NullGamesKey() {
        try {
            new TestDomainHandler("id1", null, "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(java.lang.String, java.lang.String, java.lang.String).
     * In this case, the games key is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_StringStringString_EmptyGamesKey() {
        try {
            new TestDomainHandler("id1", " ", "id3");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(java.lang.String, java.lang.String, java.lang.String).
     * In this case, the result code is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_StringStringString_NullResultCode() {
        try {
            new TestDomainHandler("id1", "id2", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(java.lang.String, java.lang.String, java.lang.String).
     * In this case, the result code is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_StringStringString_EmptyResultCode() {
        try {
            new TestDomainHandler("id1", "id2", " ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testTestDomainHandler_Element_NullElement() {
        try {
            new TestDomainHandler(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element has no domain name param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testTestDomainHandler_Element_MissingDomainNameParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<domain_name_param_key_1>id</domain_name_param_key_1>"
                + "<games_key>id</games_key>"
                + "<not_logged_in_result_code>code</not_logged_in_result_code>"
                + "</handler>";
            new TestDomainHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element has empty domain name param key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testTestDomainHandler_Element_EmptyDomainNameParamKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<domain_name_param_key> </domain_name_param_key>"
                + "<games_key>id</games_key>"
                + "<not_logged_in_result_code>code</not_logged_in_result_code>"
                + "</handler>";
            new TestDomainHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element has no games key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testTestDomainHandler_Element_MissingGamesKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<domain_name_param_key>id</domain_name_param_key>"
                + "<games_key_1>id</games_key_1>"
                + "<not_logged_in_result_code>code</not_logged_in_result_code>"
                + "</handler>";
            new TestDomainHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element has empty games key.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testTestDomainHandler_Element_EmptyGamesKey() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<domain_name_param_key>id</domain_name_param_key>"
                + "<games_key> </games_key>"
                + "<not_logged_in_result_code>code</not_logged_in_result_code>"
                + "</handler>";
            new TestDomainHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element has no result code.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testTestDomainHandler_Element_MissingResultCode() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<domain_name_param_key>id</domain_name_param_key>"
                + "<games_key>id</games_key>"
                + "<not_logged_in_result_code_1>code</not_logged_in_result_code_1>"
                + "</handler>";
            new TestDomainHandler(FailureTestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for TestDomainHandler(org.w3c.dom.Element).
     * In this case, the element has empty result code.
     * Expected : {@link IllegalArgumentException}.
     * @throws Exception to JUnit
     */
    public void testTestDomainHandler_Element_EmptyResultCode() throws IOException, ParserConfigurationException, Exception {
        try {
            String xml = "<handler type=\"x\">"
                + "<domain_name_param_key>id</domain_name_param_key>"
                + "<games_key>id</games_key>"
                + "<not_logged_in_result_code>  </not_logged_in_result_code>"
                + "</handler>";
            new TestDomainHandler(FailureTestHelper.parseElement(xml));
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
            handler.execute(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for execute(ActionContext).
     * In this case, the context is null.
     * Expected : {@link HandlerExecutionException}.
     * @throws Exception to JUnit
     */
    public void testExecute_FailedToLoad() throws Exception {
        try {
            MockControl reqControl = MockControl.createNiceControl(HttpServletRequest.class);
            MockControl resControl = MockControl.createControl(HttpServletResponse.class);
            reqControl.replay();
            MyHttpServletRequest wrapper = new MyHttpServletRequest((HttpServletRequest) reqControl.getMock());
            wrapper.setNullSesssion(false);
            ActionContext ac = new ActionContext(
                    wrapper,
                    (HttpServletResponse) resControl.getMock());
            handler.execute(ac);
            fail("HandlerExecutionException expected.");
        } catch (HandlerExecutionException e) {
            // should land here
        }
    }
}
