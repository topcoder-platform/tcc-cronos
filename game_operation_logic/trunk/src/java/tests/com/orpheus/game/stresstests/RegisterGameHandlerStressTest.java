/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.AttributeScope;
import com.orpheus.game.RegisterGameHandler;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Stress test case for RegisterGameHandler,the execute method has been tested.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class RegisterGameHandlerStressTest extends TestCase {
    /**
     * keep the instance of the <c>ActionContext</c> for test.
     */
    private ActionContext context;

    /**
     * keep the instance of the <c>RegisterGameHandler</c> for test.
     */
    private RegisterGameHandler handler;

    /**
     * Represent the max number of the scopes.
     */
    private final int MAX_SCOPES = 10000;

    /**
     * create PuzzleRendering instance.
     *
     * @throws Exception
     *         into Junit
     */
    protected void setUp() throws Exception {
        TestsHelper.removeAllCMNamespaces();
        TestsHelper.loadConfig();
        
        Map map = new HashMap();
        for (int j = 0; j < MAX_SCOPES; j++) {
            map.put(new AttributeScope("request_property_name", "request"), "property1");
        }

        MockServletContext servletContext = new MockServletContext();

        MockHttpSession session = new MockHttpSession(servletContext);
        MockHttpRequest request = new MockHttpRequest(session);

        request.setParameter("game_id", "1");

        context = new ActionContext(request, new MockHttpResponse());
        handler = new RegisterGameHandler("game_id");
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
     *         into Junit
     */
    public void testExecute() throws Exception {
        long start = System.currentTimeMillis();
        for (int j = 0; j < TestsHelper.MAX_COUNT; j++) {
            handler.execute(context);
        }
        System.out.println("handling " + TestsHelper.MAX_COUNT + " requests using  RegisterGameHandler will cost "
                        + (System.currentTimeMillis() - start) + " milliseconds.");
    }
}
