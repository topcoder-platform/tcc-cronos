/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * Unit test for <code>{@link GetContestAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GetContestActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetContestAction</code> instance.
     * </p>
     */
    private GetContestAction getContestAction;

    /**
     * <p>
     * Represents the <code>ContestServiceFacade</code> instance.
     * </p>
     */
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetContestActionUnitTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        getContestAction = new GetContestAction();
        getContestAction.prepare();

        contestServiceFacade = new MockContestServiceFacade();
        getContestAction.setContestServiceFacade(contestServiceFacade);
    }

    /**
     * <p>
     * Tear down the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        getContestAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>GetContestAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", getContestAction);

        assertTrue("invalid super class.", getContestAction instanceof ContestAction);
    }

    /**
     * <p>
     * Tests the <code>getProjectId()</code> method.
     * </p>
     * <p>
     * By default, The project id is 0.
     * </p>
     */
    public void testGetProjectId_default() {
        assertEquals("The projectId should be 0", 0, getContestAction.getProjectId());
    }

    /**
     * <p>
     * Tests the <code>setProjectId(long)</code> method.
     * </p>
     * <p>
     * The projectId should be set internally.
     * </p>
     */
    public void testSetProjectId_accuracy() {
        long projectId = 100;
        getContestAction.setProjectId(projectId);

        assertEquals("field not set.", projectId, getContestAction.getProjectId());
    }

    /**
     * <p>
     * Tests the <code>getContestId()</code> method.
     * </p>
     * <p>
     * By default, The project id is 0.
     * </p>
     */
    public void testGetContestId_default() {
        assertEquals("The projectId should be 0", 0, getContestAction.getContestId());
    }

    /**
     * <p>
     * Tests the <code>setContestId(long)</code> method.
     * </p>
     * <p>
     * The projectId should be set internally.
     * </p>
     */
    public void testSetContestId_accuracy() {
        long contestId = 100;
        getContestAction.setContestId(contestId);

        assertEquals("field not set.", contestId, getContestAction.getContestId());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If contestServiceFacade is null, should throw IllegalStateException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_ISE() throws Exception {
        getContestAction = new GetContestAction();

        try {
            getContestAction.executeAction();
            fail("If contestServiceFacade is null, should throw IllegalStateException.");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @see MockContestServiceFacade#getContest(long)
     */
    public void testExecuteAction_accuracy1() throws Exception {
        getContestAction.setContestId(100);

        // invoke
        getContestAction.executeAction();

        // verify the result
        Object result = getContestAction.getResult();

        assertTrue("incorrect return type", result instanceof StudioCompetition);

        StudioCompetition studioCompetition = (StudioCompetition) result;

        assertEquals("id is populated", 100, studioCompetition.getId());
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     * @see MockContestServiceFacade#getSoftwareContestByProjectId(long)
     */
    public void testExecuteAction_accuracy2() throws Exception {
        getContestAction.setProjectId(100);

        // invoke
        getContestAction.executeAction();

        // verify the result
        Object result = getContestAction.getResult();

        assertTrue("incorrect return type", result instanceof SoftwareCompetition);

        SoftwareCompetition softwareCompetition = (SoftwareCompetition) result;

        assertEquals("id is populated", 100, softwareCompetition.getId());
    }

}
