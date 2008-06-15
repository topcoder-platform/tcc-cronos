/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>DependenciesEntry</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DependenciesEntryTest extends TestCase {
    /**
     * <p>
     * Represents <code>DependenciesEntry</code> object to be tested.
     * </p>
     */
    private DependenciesEntry entry;

    /**
     * <p>
     * Represents <code>Component</code> object to be used in tests.
     * </p>
     */
    private Component component;

    /**
     * <p>
     * Represents <code>ComponentDependency</code> object to be used in tests.
     * </p>
     */
    private ComponentDependency componentDependency;

    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
        component = new Component("name", "", ComponentLanguage.DOT_NET);
        componentDependency = new ComponentDependency("name", "version", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
        List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
        dependencies.add(componentDependency);
        entry = new DependenciesEntry(component, dependencies);
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        component = null;
        componentDependency = null;
        entry = null;
    }

    /**
     * <p>
     * Gets the test suite for <code>DependenciesEntry</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>DependenciesEntry</code> class.
     */
    public static Test suite() {
        return new TestSuite(DependenciesEntryTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>DependenciesEntry(Component,List&lt;ComponentDependency&gt;)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", entry);
    }

    /**
     * <p>
     * Failure test for constructor <code>DependenciesEntry(Component,List&lt;ComponentDependency&gt;)</code>.
     * </p>
     * <p>
     * Passes in null component value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure1() throws Exception {
        try {
            new DependenciesEntry(null, new ArrayList<ComponentDependency>());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>DependenciesEntry(Component,List&lt;ComponentDependency&gt;)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure2() throws Exception {
        try {
            List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
            dependencies.add(null);
            new DependenciesEntry(component, dependencies);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getTargetComponent()</code>.
     * </p>
     * <p>
     * Gets back component correctly. No exception should be thrown.
     * </p>
     */
    public void testGetTargetComponent_Accuracy() {
        assertEquals("the component's name should 'name'.", "name", entry.getTargetComponent().getName());
    }

    /**
     * <p>
     * Accuracy test for <code>getDependencies()</code>.
     * </p>
     * <p>
     * Gets back right number of dependencies. No exception should be thrown.
     * </p>
     */
    public void testGetDependencies_Accuracy() {
        assertEquals("returned dependencies should have 1 member.", 1, entry.getDependencies().size());
    }

    /**
     * <p>
     * Accuracy test for <code>setTargetComponent(Component)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testSetTargetComponent_Accuracy() {
        entry.setTargetComponent(new Component("test", "", ComponentLanguage.DOT_NET));
        assertTrue("returned component is not what it is just set.", "test"
            .equals(entry.getTargetComponent().getName())
            && ComponentLanguage.DOT_NET == entry.getTargetComponent().getLanguage());
    }

    /**
     * <p>
     * Failure test for <code>setTargetComponent(Component)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetTargetComponent_Failure1() throws Exception {
        try {
            entry.setTargetComponent(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setDependencies(List&lt;ComponentDependency&gt;)</code>.
     * </p>
     * <p>
     * Sets a new value and gets right count of dependencies. No exception should be thrown.
     * </p>
     */
    public void testSetDependencies_Accuracy() {
        List<ComponentDependency> dependencies = new ArrayList<ComponentDependency>();
        dependencies.add(componentDependency);
        dependencies.add(componentDependency);
        entry.setDependencies(dependencies);
        assertEquals("returned dependencies should have 2 members.", 2, entry.getDependencies().size());
    }

    /**
     * <p>
     * Failure test for <code>setDependencies(List&lt;ComponentDependency&gt;)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetDependencies_Failure1() throws Exception {
        try {
            entry.setDependencies(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
