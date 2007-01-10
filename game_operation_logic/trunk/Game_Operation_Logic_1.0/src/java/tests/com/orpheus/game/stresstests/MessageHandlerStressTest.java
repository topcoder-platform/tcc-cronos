/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.AttributeScope;
import com.orpheus.game.GameOperationLogicUtility;
import com.orpheus.game.MessageHandler;
import com.orpheus.game.stresstests.TestsHelper;
import com.topcoder.message.messenger.Messenger;

import com.topcoder.util.rssgenerator.MockDataStore;
import com.topcoder.web.frontcontroller.ActionContext;

import junit.framework.TestCase;

/**
 * <p>
 * Stress test case for MessageHandler,the execute method has been tested.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class MessageHandlerStressTest extends TestCase {
    /**
     * keep the instance of the <c>ActionContext</c> for test.
     */
    private ActionContext context;

    /**
     * keep the instance of the <c>MessageHandler</c> for test.
     */
    private MessageHandler handler;

    /**
     * Represent the max number of the scopes.
     */
    private final int MAX_SCOPES = 10000;

    /**
     * create MessageHandler instance.
     *
     * @throws Exception
     *             into Junit
     */
    protected void setUp() throws Exception {
        TestsHelper.removeAllCMNamespaces();
        TestsHelper.loadConfig();
        Map map = new HashMap();
        for (int j = 0; j < MAX_SCOPES; j++) {
            map.put(new AttributeScope("request_property_name" + j, "request"), "property" + j);
        }
        handler = new MessageHandler("stress", map);
		ServletContext servletContext = new MockServletContext();
		servletContext.setAttribute(GameOperationLogicUtility.getInstance()
				.getDataStoreKey(), new MockDataStore());
        HttpSession session = new MockHttpSession(servletContext);
        MockHttpRequest request = new MockHttpRequest(session);
        context = new ActionContext(new MockHttpRequest(session), new MockHttpResponse());
        request.setAttribute("request_property_name", "request_property_value");
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
     * test execute with context,with valid value.
     *
     * @throws Exception
     *             into Junit
     */
    public void testExecute() throws Exception {
        Messenger messenger = Messenger.createInstance();
        messenger.registerPlugin("stress", "com.orpheus.game.stresstests.MockMessengerPlugin");

        long start = System.currentTimeMillis();
        for (int j = 0; j < TestsHelper.MAX_COUNT; j++) {
            handler.execute(context);
        }
        System.out.println("handling " + TestsHelper.MAX_COUNT + " requests using MessageHandler will cost "
            + (System.currentTimeMillis() - start) + " milliseconds.");
    }

}
