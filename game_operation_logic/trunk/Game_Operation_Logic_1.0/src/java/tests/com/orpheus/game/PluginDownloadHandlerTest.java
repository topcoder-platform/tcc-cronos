/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

import org.w3c.dom.Element;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;


/**
 * Test case for PluginDownloadHandler.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PluginDownloadHandlerTest extends TestCase {
    /** Default ActionContext used in this test. */
    private ActionContext context;

    /** Default HttpRequest used in this test. */
    private MockHttpRequest request;

    /** Default PluginDownloadHandler used in this test. */
    private PluginDownloadHandler handler;

    /**
     * Test for {@link PluginDownloadHandler#execute(ActionContext)} with valid value. It should return null.
     *
     * @throws Exception exception thrown to JUnit.
     */
    public void testExecute() throws Exception {
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
     * Test for {@link PluginDownloadHandler#PluginDownloadHandler(Element)}. No exception should be thrown.
     *
     * @throws Exception exception thrown to JUnit
     */
    public void testPluginDownloadHandlerElement() throws Exception {
        Element element = DocumentHelper.getDocument("/PluginDownloadHandler.xml", "config", "valid_config");
        handler = new PluginDownloadHandler(element);
    }

    /**
     * Test for {@link PluginDownloadHandler#PluginDownloadHandler(String)}. No exception should be thrown.
     */
    public void testPluginDownloadHandlerString() {
        assertNotNull("PluginDownloadHandler should be instantiated successfully", handler);
    }

    /**
     * Test method for {@link
     * com.orpheus.game.PluginDownloadHandler#PluginDownloadHandler(org.w3c.dom.Element)}. In this caes, the element
     * is null.
     */
    public void testPluginDownloadHandler_Element_NullElement() {
        try {
            new PluginDownloadHandler((Element) null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PluginDownloadHandler(java.lang.String). In this case, the name is empty.
     */
    public void testPluginDownloadHandler_String_EmptyKey() {
        try {
            new PluginDownloadHandler(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for PluginDownloadHandler(java.lang.String). In this case, the name is null.
     */
    public void testPluginDownloadHandler_String_NullKey() {
        try {
            new PluginDownloadHandler((String) null);
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

        request = new MockHttpRequest(null);
        request.setParameter("plugin", "some_plugin");
        handler = new PluginDownloadHandler("plugin");
        context = new ActionContext(request, new MockHttpResponse());

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
