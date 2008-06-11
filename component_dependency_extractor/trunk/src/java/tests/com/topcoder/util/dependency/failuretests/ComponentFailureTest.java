/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.failuretests;

import com.topcoder.util.dependency.Component;
import com.topcoder.util.dependency.ComponentLanguage;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for Component class.
 *
 * @author King_Bette
 * @version 1.0
 */
public class ComponentFailureTest extends TestCase {
	/**
	 * This instance is used in the test.
	 */
	private Component component = new Component("component1", "1.0", ComponentLanguage.DOT_NET);
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ComponentFailureTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // do nothing
    }

    /**
     * Tears down the environment for the TestCase.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // do nothing
    }

    /**
     * Failure test of <code>Component(String name, String version, ComponentLanguage language)</code> constructor.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testComponent_failure_null_name() throws Exception {
        try {
        	new Component(null, "1.0", ComponentLanguage.JAVA);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>Component(String name, String version, ComponentLanguage language)</code> constructor.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testComponent_failure_empty_name() throws Exception {
        try {
        	new Component(" ", "1.0", ComponentLanguage.JAVA);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>Component(String name, String version, ComponentLanguage language)</code> constructor.
     *
     * <p>
     * version is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testComponent_failure_null_version() throws Exception {
        try {
        	new Component("component1", null, ComponentLanguage.JAVA);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
    /**
     * Failure test of <code>Component(String name, String version, ComponentLanguage language)</code> constructor.
     *
     * <p>
     * language is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testComponent_failure_null_language() throws Exception {
        try {
        	new Component("component1", "1.0", null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     *
     * <p>
     * name is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetName_failure_null_name() throws Exception {
        try {
        	component.setName(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setName(String name)</code> method.
     *
     * <p>
     * name is empty.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetName_failure_empty_name() throws Exception {
        try {
        	component.setName("  ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setVersion(String version)</code> method.
     *
     * <p>
     * version is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetVersion_failure_null_version() throws Exception {
        try {
        	component.setVersion(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>setLanguage(ComponentLanguage language)</code> method.
     *
     * <p>
     * language is null.
     * </p>
     *
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    public void testSetLanguage_failure_null_language() throws Exception {
        try {
        	component.setLanguage(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
