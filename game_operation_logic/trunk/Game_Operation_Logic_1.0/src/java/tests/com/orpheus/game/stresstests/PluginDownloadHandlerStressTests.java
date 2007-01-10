/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import servlet.MockHttpSession;
import servlet.MockServletContext;
import junit.framework.TestCase;

import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.PluginDownloadHandler;
import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Stress test case for PluginDownloadHandler,the execute method has been tested.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class PluginDownloadHandlerStressTests extends TestCase {
    /**
     * keep the instance of the <c>ActionContext</c> for test.
     */
    private ActionContext context;

    /**
     * keep the instance of the <c>PluginDownloadHandler</c> for test.
     */
    private PluginDownloadHandler handler;

    /**
     * create MessageHandler instance.
     *
     * @throws Exception
     *         into Junit
     */
    protected void setUp() throws Exception {
        TestsHelper.removeAllCMNamespaces();
        TestsHelper.loadConfig();
		ServletContext servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());
        HttpSession session = new MockHttpSession(servletContext);
        MockHttpRequest request = new MockHttpRequest(session);
        context = new ActionContext(request, new MockHttpResponse());
        request.setAttribute("request_property_name", "request_property_value");
        request.setParameter("plugin", "some_plugin");
        handler = new PluginDownloadHandler("plugin");
    }
    
    /**
     * clear the config
     *
     * @throws Exception
     *             into Junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        TestsHelper.removeAllCMNamespaces();
    }

    /**
     * test execute with context,with valid value. *
     *
     * @throws Exception
     *         into Junit
     */
    public void testExecute() throws Exception {
        long start = System.currentTimeMillis();
        for (int j = 0; j < TestsHelper.MAX_COUNT; j++) {
            handler.execute(context);
        }
        System.out.println("handling " + TestsHelper.MAX_COUNT + " requests using  PluginDownloadHandler will cost "
            + (System.currentTimeMillis() - start) + " milliseconds.");
    }
}

