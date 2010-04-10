/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import com.opensymphony.xwork2.Action;
import com.topcoder.service.actions.SaveDraftContestAction;
import com.topcoder.service.pipeline.CompetitionType;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * This class is the stress test for SaveDraftContestAction.
 * version 1.0.
 * </p>
 *
 * @author Beijing2008
 * @version 1.0
 */
public class SaveDraftContestActionStressTest extends TestCase {

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
     * The SaveDraftContestAction instance for stress test.
     */
    SaveDraftContestAction instance = null;

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
        instance = new SaveDraftContestAction();
        instance.setContestServiceFacade(new StressMockContestServiceFacade());
        instance.setStartDate(new Date());
        instance.setEndDate(new Date());
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
        instance.setProjectId(1l);
        instance.prepare();
        for (int i = 0; i < COUNT; ++i) {

            String result = instance.execute();

            assertEquals("The action failed", result, Action.SUCCESS);
            assertTrue("The action failed", instance.getResult() instanceof SoftwareCompetition);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for execute() " + COUNT + " times costs: "
                + (endTime - startTime) + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the executeAction().
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_executeAction2() throws Exception {
        startTime = System.currentTimeMillis();
        instance.setCompetitionType(CompetitionType.SOFTWARE);
        instance.prepare();
        for (int i = 0; i < COUNT; ++i) {

            String result = instance.execute();

            assertEquals("The action failed", result, Action.SUCCESS);
            assertTrue("The action failed", instance.getResult() instanceof SoftwareCompetition);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for execute() " + COUNT + " times costs: "
                + (endTime - startTime) + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the executeAction().
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_executeAction3() throws Exception {
        startTime = System.currentTimeMillis();
        instance.setContestId(1l);
        instance.prepare();
        for (int i = 0; i < COUNT; ++i) {

            String result = instance.execute();

            assertEquals("The action failed", result, Action.SUCCESS);
            assertTrue("The action failed", instance.getResult() instanceof StudioCompetition);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for execute() " + COUNT + " times costs: "
                + (endTime - startTime) + " milliseconds.");
    }

    /**
     * <p>
     * This method tests the executeAction().
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void test_executeAction4() throws Exception {
        startTime = System.currentTimeMillis();
        instance.setCompetitionType(CompetitionType.STUDIO);
        instance.prepare();
        for (int i = 0; i < COUNT; ++i) {

            String result = instance.execute();

            assertEquals("The action failed", result, Action.SUCCESS);
            assertTrue("The action failed", instance.getResult() instanceof StudioCompetition);
        }

        endTime = System.currentTimeMillis();
        System.out.println("The stress test for execute() " + COUNT + " times costs: "
                + (endTime - startTime) + " milliseconds.");
    }
}
