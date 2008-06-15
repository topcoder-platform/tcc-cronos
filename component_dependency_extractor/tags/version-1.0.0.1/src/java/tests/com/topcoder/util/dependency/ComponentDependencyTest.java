/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>ComponentDependency</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentDependencyTest extends TestCase {
    /**
     * <p>
     * Represents <code>ComponentDependency</code> object to be tested.
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
        componentDependency = new ComponentDependency("name", "version", ComponentLanguage.JAVA,
            DependencyCategory.COMPILE, DependencyType.INTERNAL, "path");
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
        componentDependency = null;
    }

    /**
     * <p>
     * Gets the test suite for <code>ComponentDependency</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>ComponentDependency</code> class.
     */
    public static Test suite() {
        return new TestSuite(ComponentDependencyTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor
     * <code>ComponentDependency(String,String,ComponentLanguage,DependencyCategory,DependencyType,String)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", componentDependency);
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>ComponentDependency(String,String,ComponentLanguage,DependencyCategory,DependencyType,String)</code>.
     * </p>
     * <p>
     * Passes in null category. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure1() throws Exception {
        try {
            new ComponentDependency("name", "version", ComponentLanguage.JAVA, null, DependencyType.INTERNAL, "path");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor
     * <code>ComponentDependency(String,String,ComponentLanguage,DependencyCategory,DependencyType,String)</code>.
     * </p>
     * <p>
     * Passes in empty path. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure2() throws Exception {
        try {
            new ComponentDependency("name", "version", ComponentLanguage.JAVA, null, DependencyType.INTERNAL, "");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getCategory()</code>.
     * </p>
     * <p>
     * Gets the category value correctly. No exception should be thrown.
     * </p>
     */
    public void testGetCategory_Accuracy() {
        assertTrue("it should be compile enum value.", DependencyCategory.COMPILE == componentDependency.getCategory());
    }

    /**
     * <p>
     * Accuracy test for <code>setCategory(DependencyCategory)</code>.
     * </p>
     * <p>
     * Passes in a new category value and gets it back correctly. No exception should be thrown.
     * </p>
     */
    public void testSetCategory_Accuracy() {
        componentDependency.setCategory(DependencyCategory.TEST);
        assertTrue("it should be test enum value.", DependencyCategory.TEST == componentDependency.getCategory());
    }

    /**
     * <p>
     * Failure test for <code>setCategory(DependencyCategory)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetCategory_Failure1() throws Exception {
        try {
            componentDependency.setCategory(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getType()</code>.
     * </p>
     * <p>
     * Gets the type value correctly. No exception should be thrown.
     * </p>
     */
    public void testGetType_Accuracy() {
        assertTrue("it should be internal enum value.", DependencyType.INTERNAL == componentDependency.getType());
    }

    /**
     * <p>
     * Accuracy test for <code>setType(DependencyType)</code>.
     * </p>
     * <p>
     * Passes in new type value and gets it back correctly. No exception should be thrown.
     * </p>
     */
    public void testSetType_Accuracy() {
        componentDependency.setType(DependencyType.EXTERNAL);
        assertTrue("it should be external enum value.", DependencyType.EXTERNAL == componentDependency.getType());
    }

    /**
     * <p>
     * Failure test for <code>setType(DependencyType)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetType_Failure1() throws Exception {
        try {
            componentDependency.setType(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getPath()</code>.
     * </p>
     * <p>
     * Passes in a test <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testGetPath_Accuracy_WithNonNullNonEmptyValue() {
        componentDependency.setPath("testGet_String");
        assertEquals("It doesn't return the same String value which is just set.", "testGet_String",
            componentDependency.getPath());
    }

    /**
     * <p>
     * Accuracy test for <code>setPath(String)</code>.
     * </p>
     * <p>
     * Sets a <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testSetPath_Accuracy_WithNonNullNonEmptyValue1() {
        componentDependency.setPath("testSet_String");
        assertEquals("It doesn't return the same String value which is just set.", "testSet_String",
            componentDependency.getPath());
    }

    /**
     * <p>
     * Accuracy test for <code>setPath(String)</code>.
     * </p>
     * <p>
     * Sets another <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testSetPath_Accuracy_WithNonNullNonEmptyValue2() {
        componentDependency.setPath("testSet String2 ");
        assertEquals("It doesn't return the same String value which is just set.", "testSet String2 ",
            componentDependency.getPath());
    }

    /**
     * <p>
     * Failure test for <code>setPath(String)</code>.
     * </p>
     * <p>
     * Sets null <code>String</code> value. It is not allowed. IllegalArgumentException exception should be thrown.
     * </p>
     */
    public void testSetPath_Failure_NullValue() {
        try {
            componentDependency.setPath(null);
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>setPath(String)</code>.
     * </p>
     * <p>
     * Sets empty <code>String</code> value. It is not allowed. IllegalArgumentException exception should be thrown.
     * </p>
     */
    public void testSetPath_Failure_EmptyValue() {
        try {
            componentDependency.setPath(" ");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

}
