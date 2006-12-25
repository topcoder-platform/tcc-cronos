/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import java.util.HashMap;
import java.util.Map;


/**
 * Test case for RegisterGameHandler.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegisterGameHandlerTest extends TestCase {
    /** Represents the default ActionContext used in this test. */
    private ActionContext context;

    /** Represents the default HttpRequest used in this test. */
    private MockHttpRequest request;

    /** Represents the default HttpResponse used in this test. */
    private MockHttpResponse response;

    /** Represents the default HttpSession used in this test. */
    private MockHttpSession session;

    /** Represents the default ServletContext used in this test. */
    private MockServletContext servletContext;

    /** Represents the default RegisterGameHandler used in this test. */
    private RegisterGameHandler handler;

    /**
     * Test for {@link RegisterGameHandler#execute(ActionContext)}. It should return null.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testExecute() throws Exception {
        handler.execute(context);
        assertEquals("execute failed.", null, handler.execute(context));
    }

    /**
     * Test method for execute(ActionContext). In this case, the context is null. Expected : {@link
     * IllegalArgumentException}.
     *
     * @throws Exception to JUnit
     */
    public void testExecute_NullContext() throws Exception {
        try {
            handler.execute(null);
            fail("IllegalARgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test for {@link RegisterGameHandler#RegisterGameHandler(Element)}. No exception should be thrown.
     *
     * @throws Exception exception thrown to JUnit
     */
    public void testRegisterGameHandlerElement() throws Exception {
        Element element = DocumentHelper.getDocument("/RegisterGameHandler.xml", "config", "valid_config");
        RegisterGameHandler handler = new RegisterGameHandler(element);
        handler.execute(context);
    }

    /**
     * Test for {@link RegisterGameHandler#RegisterGameHandler(String)}. No exception should be thrown.
     */
    public void testRegisterGameHandlerString() {
        assertNotNull("RegisterGameHandler should be instantiated successfully", handler);
    }

    /**
     * Test method for RegisterGameHandler(org.w3c.dom.Element). In this case, the element has no param key.
     *
     * @throws Exception to JUnit
     */
    public void testRegisterGameHandler_Element_NoParamKey()
        throws Exception {
        try {
            String xml = "<handler type=\"x\"> <game_id_param_key>  </game_id_param_key> </handler>";
            new RegisterGameHandler(TestHelper.parseElement(xml));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RegisterGameHandler(org.w3c.dom.Element). In this case, the element is null.
     */
    public void testRegisterGameHandler_Element_NullElement() {
        try {
            new RegisterGameHandler((Element) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RegisterGameHandler(java.lang.String). In this case, the name is empty.
     */
    public void testRegisterGameHandler_String_EmptyName() {
        try {
            new RegisterGameHandler(" ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for RegisterGameHandler(java.lang.String). In this case, the name is null.
     */
    public void testRegisterGameHandler_String_NullName() {
        try {
            new RegisterGameHandler((String) null);
            fail("IllegalArgumentException expected.");
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

        Map map = new HashMap();
        map.put(new AttributeScope("request_property_name", "request"), "property1");
        handler = new RegisterGameHandler("game_id");

        servletContext = new MockServletContext();

        session = new MockHttpSession(servletContext);
        request = new MockHttpRequest(session);

        response = new MockHttpResponse();
        context = new ActionContext(request, response);

        request.setParameter("game_id", "1");

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
