/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import junit.framework.TestCase;
import servlet.MockHttpSession;
import servlet.MockServletContext;

import com.orpheus.game.GameOperationLogicUtility;

import com.orpheus.game.PuzzleSolutionHandler;
import com.topcoder.util.puzzle.MockPuzzleTypeSource;
import com.topcoder.util.puzzle.MockSolutionTester;
import com.topcoder.web.frontcontroller.ActionContext;

/**
 * <p>
 * Stress test case for PuzzleSolutionHandler,the execute method has been tested.
 * </p>
 *
 * @author catcher
 * @version 1.0
 */
public class PuzzleSolutionHandlerStressTest extends TestCase {
    /**
     * keep the instance of the <c>ActionContext</c> for test.
     */
    private ActionContext context;

    /**
     * keep the instance of the <c>PuzzleSolutionHandler</c> for test.
     */
    private PuzzleSolutionHandler handler;

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

        request.setParameter("puzzle_id", "1");
        request.setParameter("Slot_id", "1");

        context = new ActionContext(request, new MockHttpResponse());
        handler = new PuzzleSolutionHandler("puzzle_id", "Slot_id", "base_name", "incorrect_solution_result", "test");
        session.setAttribute("base_name123", new MockSolutionTester());
        servletContext.setAttribute(GameOperationLogicUtility.getInstance().getGameManagerKey(),
                        new MockGameDataManager());
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
        System.out.println("handling " + TestsHelper.MAX_COUNT + " requests using  PuzzleSolutionHandler will cost "
                        + (System.currentTimeMillis() - start) + " milliseconds.");
    }
}

