/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.service.actions.CreateProjectAction;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This class is the stress test for CreateProjectAction.
 * version 1.0.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class CreateProjectActionStressTest extends TestCase {

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
     * The CreateProjectAction instance for stress test.
     */
    CreateProjectAction instance = null;

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
        instance = new CreateProjectAction();
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
    public void test_executeAction1() throws Exception {
        startTime = System.currentTimeMillis();
        instance.prepare();
        for (int i = 0; i < COUNT; ++i) {

            String result = instance.execute();

            assertEquals("The action failed", result, Action.SUCCESS);
            assertTrue("The action failed", instance.getResult() instanceof ProjectData);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for execute() " + COUNT + " times costs: "
                + (endTime - startTime) + " milliseconds.");
    }

}
