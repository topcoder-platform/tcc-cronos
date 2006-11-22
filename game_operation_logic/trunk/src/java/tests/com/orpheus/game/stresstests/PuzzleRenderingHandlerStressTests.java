/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import servlet.MockHttpSession;
import servlet.MockServletContext;
import junit.framework.TestCase;

import com.orpheus.game.PuzzleRenderingHandler;
import com.topcoder.util.puzzle.MockPuzzleTypeSource;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Stress test case for PuzzleRenderingHandler,the execute method has been tested.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class PuzzleRenderingHandlerStressTests extends TestCase {
    /**
     * keep the instance of the <c>ActionContext</c> for test.
     */
    private ActionContext context;

    /**
     * keep the instance of the <c>PuzzleRendering</c> for test.
     */
    private PuzzleRenderingHandler handler;

    /**
     * create PuzzleRendering instance.
     *
     * @throws Exception
     *         into Junit
     */
    protected void setUp() throws Exception {
        TestsHelper.removeAllCMNamespaces();
        TestsHelper.loadConfig();
        MockServletContext servletContext = new MockServletContext();
        servletContext.setAttribute("PuzzleTypeSource", new MockPuzzleTypeSource());

        MockHttpSession session = new MockHttpSession(servletContext);
        MockHttpRequest request = new MockHttpRequest(session);

        request.setAttribute("puzzle_id", new Long(1));
        request.setAttribute("media_type", "type");

        context = new ActionContext(request, new MockHttpResponse());
        handler = new PuzzleRenderingHandler("puzzle_id", "media_type", "base_name", "puzzle_string");

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
        System.out.println("handling " + TestsHelper.MAX_COUNT + " requests using  PuzzleRenderingHandler will cost "
                        + (System.currentTimeMillis() - start) + " milliseconds.");
    }

}

