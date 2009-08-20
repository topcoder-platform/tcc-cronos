/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for ProjectNotFoundFault class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectNotFoundFaultTest extends TestCase {
    /**
     * Bean to test.
     */
    private ProjectNotFoundFault target;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(ProjectNotFoundFaultTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        target = new ProjectNotFoundFault();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests empty constructor.
     */
    public void testConstructor() {
        assertNotNull("created instance", new ProjectNotFoundFault());
    }

    /**
     * Tests setter/getter for projectIdNotFound field.
     */
    public void testProjectIdNotFound() {
        assertEquals("default value", 0, target.getProjectIdNotFound());
        target.setProjectIdNotFound(35);
        assertEquals("new value", 35, target.getProjectIdNotFound());
    }
}
