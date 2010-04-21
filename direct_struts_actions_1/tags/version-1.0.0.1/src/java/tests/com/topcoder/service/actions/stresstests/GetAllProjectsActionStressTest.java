/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import java.util.List;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.service.actions.GetAllProjectsAction;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This class is the stress test for GetAllProjectsAction.
 * version 1.0.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class GetAllProjectsActionStressTest extends TestCase {

    /**
     * The looping count for testing
     */
    private static final int COUNT = 3000;

    /**
     * The start time for the stress test.
     */
    private long startTime = 0;

    /**
     * The end time for the stress test.
     */
    private long endTime = 0;

    /**
     * The GetAllProjectsAction instance for stress test.
     */
    GetAllProjectsAction instance = null;

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        instance = new GetAllProjectsAction();
        instance.setProjectServiceFacade(new StressMockProjectServiceFacade());
    }

    /**
     * <p>
     * Clears the test environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>
     * This method tests the executeAction().
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @SuppressWarnings("unchecked")
    public void test_executeAction1() throws Exception {
        startTime = System.currentTimeMillis();
        instance.prepare();
        for (int i = 0; i < COUNT; ++i) {

            String result = instance.execute();

            assertEquals("The action failed", result, Action.SUCCESS);
            assertTrue("The action failed", instance.getResult() instanceof List);
            assertEquals("The action failed", 3000, ((List<ProjectData>) instance.getResult()).size());
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for execute() " + COUNT + " times costs: "
                + (endTime - startTime) + " milliseconds.");
    }

}
