/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * Unit test for <code>{@link GetAllBillingProjectsAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GetAllBillingProjectsActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetAllBillingProjectsAction</code> instance.
     * </p>
     */
    private GetAllBillingProjectsAction getAllBillingProjectsAction;

    /**
     * <p>
     * Represents the <code>ProjectServiceFacade</code> instance.
     * </p>
     */
    private ProjectServiceFacade projectServiceFacade;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(GetAllBillingProjectsActionUnitTests.class);
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

        getAllBillingProjectsAction = new GetAllBillingProjectsAction();

        getAllBillingProjectsAction.prepare();

        projectServiceFacade = new MockProjectServiceFacade();
        getAllBillingProjectsAction.setProjectServiceFacade(projectServiceFacade);
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
        getAllBillingProjectsAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>GetAllBillingProjectsAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", getAllBillingProjectsAction);

        assertTrue("invalid super class.", getAllBillingProjectsAction instanceof ProjectAction);
    }

    /**
     * <p>
     * Tests the <code>executeAction()</code> method.
     * </p>
     * <p>
     * If projectServiceFacade is null, should throw IllegalStateException.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testExecuteAction_ISE() throws Exception {
        getAllBillingProjectsAction = new GetAllBillingProjectsAction();

        try {
            getAllBillingProjectsAction.executeAction();
            fail("If projectServiceFacade is null, should throw IllegalStateException.");
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
     * @see MockProjectServiceFacade#getClientProjectsByUser()
     */
    @SuppressWarnings("unchecked")
    public void testExecuteAction_accuracy() throws Exception {
        // invoke
        getAllBillingProjectsAction.executeAction();

        // verify the result
        Object result = getAllBillingProjectsAction.getResult();

        assertTrue("incorrect return type", result instanceof List<?>);

        List<ProjectData> projectDatas = (List<ProjectData>) result;

        assertEquals("The list should have 5 element.", 5, projectDatas.size());

        for (ProjectData projectData : projectDatas) {
            assertTrue("id is not set.", projectData.getProjectId() > 0);
            assertNotNull("project name is not set.", projectData.getName());
            assertNotNull("project description is not set.", projectData.getDescription());
        }
    }

}
