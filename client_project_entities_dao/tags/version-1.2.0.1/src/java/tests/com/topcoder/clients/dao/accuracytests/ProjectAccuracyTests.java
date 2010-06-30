/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.accuracytests;

import com.topcoder.clients.model.Project;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for the {@link Project} class.
 * </p>
 *
 * @author akinwale
 * @version 1.2
 * @since 1.2
 */
public class ProjectAccuracyTests extends TestCase {
    /**
     * <p>
     * The {@link Project} instance to be tested.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Setup for each unit test in the test case.
     * </p>
     *
     * @throws Exception
     *             exception to pass to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        project = new Project();
    }

    /**
     * <p>
     * Tests that the getBudget() method works properly by returning the correct value corresponding
     * to the private field.
     * </p>
     */
    public void testGetBudget_Accuracy() {
        project.setBudget(1.0);
        assertEquals("getBudget does not work properly", 1.0, project.getBudget());
    }

    /**
     * <p>
     * Tests that the setBudget() method works properly by setting the specified value for the
     * corresponding private field.
     * </p>
     */
    public void testSetBudget_Accuracy() {
        project.setBudget(1.0);
        assertEquals("setBudget does not work properly", 1.0, project.getBudget());
    }
}
