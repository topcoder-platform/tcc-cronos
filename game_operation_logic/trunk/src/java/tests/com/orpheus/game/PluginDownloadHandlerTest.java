/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import org.w3c.dom.Element;

import com.topcoder.web.frontcontroller.ActionContext;

import servlet.MockHttpRequest;
import servlet.MockHttpResponse;
import junit.framework.TestCase;

public class PluginDownloadHandlerTest extends TestCase {
    
    private PluginDownloadHandler handler;
    private MockHttpRequest request;
    private ActionContext context;
    
    
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        
        request = new MockHttpRequest(null);
        request.setParameter("plugin","some_plugin");
        handler = new PluginDownloadHandler("plugin");
        context = new ActionContext(request, new MockHttpResponse());
        
        JNDIHelper.initJNDI();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.unloadConfig();
    }

    /*
     * Test method for 'com.orpheus.game.PluginDownloadHandler.PluginDownloadHandler(String)'
     */
    public void testPluginDownloadHandlerString() {
        assertNotNull("PluginDownloadHandler should be instantiated successfully",handler);
    }

    /*
     * Test method for 'com.orpheus.game.PluginDownloadHandler.PluginDownloadHandler(Element)'
     */
    public void testPluginDownloadHandlerElement() throws Exception{
        Element element = DocumentHelper.getDocument("/PluginDownloadHandler.xml", "config", "valie_config");
        handler = new PluginDownloadHandler(element);
        handler.execute(context);
    }

    /*
     * Test method for 'com.orpheus.game.PluginDownloadHandler.execute(ActionContext)'
     */
    public void testExecute() throws Exception{
        handler.execute(context);
    }

}
