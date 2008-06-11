/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import java.util.ArrayList;
import java.util.List;

import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentDependency;
import com.topcoder.util.dependency.ComponentLanguage;
import com.topcoder.util.dependency.DependenciesEntry;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for DependenciesEntry class.
 * @author King_Bette
 * @version 1.0
 */
public class DependenciesEntryFailureTest extends TestCase {
    /**
     * This instance is used in the test.
     */
    private Component component = new Component("component1", "1.0", ComponentLanguage.JAVA);
    /**
     * This instance is used in the test.
     */
    private List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
    /**
     * This instance is used in the test.
     */
    private DependenciesEntry entry = new DependenciesEntry(component, dependencies);

    /**
     * Aggregates all tests in this class.
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DependenciesEntryFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // do nothing
    }

    /**
     * Tears down the environment for the TestCase.
     * @throws Exception throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // do nothing
    }

    /**
     * Failure test of
     * <code>DependenciesEntry(Component targetComponent, List<ComponentDependency> dependencies)</code>
     * constructor.
     * <p>
     * targetComponent is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDependenciesEntry_failure_null_targetComponent() throws Exception {
        try {
            new DependenciesEntry(null, new ArrayList<ComponentDependency>());
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>DependenciesEntry(Component targetComponent, List<ComponentDependency> dependencies)</code>
     * constructor.
     * <p>
     * dependencies is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDependenciesEntry_failure_null_dependencies() throws Exception {
        try {
            new DependenciesEntry(component, null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of
     * <code>DependenciesEntry(Component targetComponent, List<ComponentDependency> dependencies)</code>
     * constructor.
     * <p>
     * dependencies contains null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testDependenciesEntry_failure_null_dependency() throws Exception {
        dependencies.add(null);
        try {
            new DependenciesEntry(component, dependencies);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setTargetComponent(Component targetComponent)</code> method.
     * <p>
     * targetComponent is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSetTargetComponent_failure_null_targetComponent() throws Exception {
        try {
            entry.setTargetComponent(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setDependencies(List<ComponentDependency> dependencies)</code> method.
     * <p>
     * dependencies is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSetDependencies_failure_null_dependencies() throws Exception {
        try {
            entry.setDependencies(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setDependencies(List<ComponentDependency> dependencies)</code> method.
     * <p>
     * dependency is null.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testSetDependencies_failure_null_dependency() throws Exception {
        dependencies.add(null);
        try {
            entry.setDependencies(dependencies);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
