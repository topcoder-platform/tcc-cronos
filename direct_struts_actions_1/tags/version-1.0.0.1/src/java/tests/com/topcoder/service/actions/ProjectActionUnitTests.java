/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.facade.project.ProjectServiceFacade;

/**
 * <p>
 * Unit test for <code>{@link ProjectAction}</code> class.
 * </p>
 *
 * @author FireIce
 * @version 1.0
 */
public class ProjectActionUnitTests extends TestCase {

    /**
     * <p>
     * Represents the <code>ProjectAction</code> instance.
     * </p>
     */
    private ProjectAction projectAction;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectActionUnitTests.class);
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

        projectAction = new ProjectAction() {

            /**
             * <p>
             * Represents the unique serial version id.
             * </p>
             */
            private static final long serialVersionUID = -4633766887769883177L;

            @Override
            protected void executeAction() throws Exception {
            }
        };

        projectAction.prepare();
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
        projectAction = null;

        super.tearDown();
    }

    /**
     * <p>
     * Tests the <code>ProjectAction()</code> constructor.
     * </p>
     * <p>
     * The constructor simply do nothing, instance should be created.
     * </p>
     */
    public void testCtor() {
        assertNotNull("instance should not null", projectAction);

        assertTrue("invalid super class.", projectAction instanceof BaseDirectStrutsAction);
    }

    /**
     * <p>
     * Tests the <code>getProjectServiceFacade()</code> method.
     * </p>
     * <p>
     * By default, The projectServiceFacade is null.
     * </p>
     */
    public void testGetProjectServiceFacade_default() {
        assertNull("The projectServiceFacade should be null", projectAction.getProjectServiceFacade());
    }

    /**
     * <p>
     * Tests the <code>setProjectServiceFacade(ProjectServiceFacade)</code> method.
     * </p>
     * <p>
     * If the projectServiceFacade is null, should throw IllegalArgumentException.
     * </p>
     */
    public void testSetProjectServiceFacade_null() {
        try {
            projectAction.setProjectServiceFacade(null);

            fail("If the projectServiceFacade is null, should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests the <code>setProjectServiceFacade(ProjectServiceFacade)</code> method.
     * </p>
     * <p>
     * If the projectServiceFacade is not null, should set internally.
     * </p>
     */
    public void testSetProjectServiceFacade_accuracy() {
        ProjectServiceFacade projectServiceFacade = new MockProjectServiceFacade();
        projectAction.setProjectServiceFacade(projectServiceFacade);

        assertSame("The projectServiceFacade is not set.", projectServiceFacade, projectAction
                .getProjectServiceFacade());
    }

}
