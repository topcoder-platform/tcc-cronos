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
 * Unit test for <code>{@link GetAllProjectsAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class GetAllProjectsActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>GetAllProjectsAction</code> instance.
     * </p>
     */
    private GetAllProjectsAction getAllProjectsAction;

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
        return new TestSuite(GetAllProjectsActionUnitTests.class);
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

        getAllProjectsAction = new GetAllProjectsAction();

        getAllProjectsAction.prepare();

        projectServiceFacade = new MockProjectServiceFacade();
        getAllProjectsAction.setProjectServiceFacade(projectServiceFacade);
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
        getAllProjectsAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>GetAllProjectsAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", getAllProjectsAction);

        assertTrue("invalid super class.", getAllProjectsAction instanceof ProjectAction);
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
        getAllProjectsAction = new GetAllProjectsAction();
        try {
            getAllProjectsAction.executeAction();

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
     * @see MockProjectServiceFacade#getAllProjects()
     */
    @SuppressWarnings("unchecked")
    public void testExecuteAction_accuracy() throws Exception {
        // invoke
        getAllProjectsAction.executeAction();

        // verify the result
        Object result = getAllProjectsAction.getResult();

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
