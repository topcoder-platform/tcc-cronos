/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * Unit test for <code>{@link CreateProjectAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class CreateProjectActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>CreateProjectAction</code> instance.
     * </p>
     */
    private CreateProjectAction createProjectAction;

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
        return new TestSuite(CreateProjectActionUnitTests.class);
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

        createProjectAction = new CreateProjectAction();

        createProjectAction.prepare();

        projectServiceFacade = new MockProjectServiceFacade();
        createProjectAction.setProjectServiceFacade(projectServiceFacade);
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
        createProjectAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>CreateProjectAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", createProjectAction);

        assertTrue("invalid super class.", createProjectAction instanceof ProjectAction);
    }

    /**
     * <p>
     * Tests the <code>getProjectName()</code> method.
     * </p>
     * <p>
     * By default, The projectName is null.
     * </p>
     */
    public void testGetProjectName_default() {
        assertNull("The projectName should be null", createProjectAction.getProjectName());
    }

    /**
     * <p>
     * Tests the <code>setProjectName(String)</code> method.
     * </p>
     * <p>
     * The projectName should be set internally.
     * </p>
     */
    public void testSetProjectName_accuracy() {
        String projectName = "Test";
        createProjectAction.setProjectName(projectName);

        assertEquals("field not set.", projectName, createProjectAction.getProjectName());
    }

    /**
     * <p>
     * Tests the <code>getProjectDescription()</code> method.
     * </p>
     * <p>
     * By default, The projectDescription is null.
     * </p>
     */
    public void testGetProjectDescription_default() {
        assertNull("The projectDescription should be null", createProjectAction.getProjectDescription());
    }

    /**
     * <p>
     * Tests the <code>setProjectDescription(String)</code> method.
     * </p>
     * <p>
     * The projectDescription should be set internally.
     * </p>
     */
    public void testSetProjectDescription_accuracy() {
        String projectDescription = "Test";
        createProjectAction.setProjectDescription(projectDescription);

        assertEquals("field not set.", projectDescription, createProjectAction.getProjectDescription());
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
        createProjectAction = new CreateProjectAction();

        try {
            // invoke
            createProjectAction.executeAction();
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
     */
    public void testExecuteAction_accuracy() throws Exception {
        createProjectAction.setProjectName("Test Project");
        createProjectAction.setProjectDescription("Test");

        // invoke
        createProjectAction.executeAction();

        // verify the result
        Object result = createProjectAction.getResult();

        assertTrue("incorrect return type", result instanceof ProjectData);

        ProjectData projectData = (ProjectData) result;

        assertTrue("id is populated", projectData.getProjectId() > 0);
        assertEquals("incorrect project name.", "Test Project", projectData.getName());
        assertEquals("incorrect project description.", "Test", projectData.getDescription());
    }

}
