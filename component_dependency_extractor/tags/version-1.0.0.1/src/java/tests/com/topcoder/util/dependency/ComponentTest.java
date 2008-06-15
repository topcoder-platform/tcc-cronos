/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit tests for <code>Component</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ComponentTest extends TestCase {
    /**
     * <p>
     * Represents <code>Component</code> object to be tested.
     * </p>
     */
    private Component component;

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
    }

    /**
     * <p>
     * Gets the test suite for <code>Component</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>Component</code> class.
     */
    public static Test suite() {
        return new TestSuite(ComponentTest.class);
    }

    /**
     * <p>
     * Accuracy test for constructor <code>Component(String,String,ComponentLanguage)</code>.
     * </p>
     * <p>
     * Uses the constructor to create a non-null instance. No exception should be thrown.
     * </p>
     */
    public void testCtor1_Accuracy() {
        assertNotNull("It should return non-null instance.", component);
    }

    /**
     * <p>
     * Failure test for constructor <code>Component(String,String,ComponentLanguage)</code>.
     * </p>
     * <p>
     * Passes in empty name. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure1() throws Exception {
        try {
            new Component(" ", "version", ComponentLanguage.JAVA);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for constructor <code>Component(String,String,ComponentLanguage)</code>.
     * </p>
     * <p>
     * Passes in null language. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCtor1_Failure2() throws Exception {
        try {
            new Component("name", "version", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getName()</code>.
     * </p>
     * <p>
     * Passes in a test <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testGetName_Accuracy_WithNonNullNonEmptyValue() {
        component.setName("testGet_String");
        assertEquals("It doesn't return the same String value which is just set.", "testGet_String", component
            .getName());
    }

    /**
     * <p>
     * Accuracy test for <code>setName(String)</code>.
     * </p>
     * <p>
     * Sets a <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testSetName_Accuracy_WithNonNullNonEmptyValue1() {
        component.setName("testSet_String");
        assertEquals("It doesn't return the same String value which is just set.", "testSet_String", component
            .getName());
    }

    /**
     * <p>
     * Accuracy test for <code>setName(String)</code>.
     * </p>
     * <p>
     * Sets another <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testSetName_Accuracy_WithNonNullNonEmptyValue2() {
        component.setName("testSet String2 ");
        assertEquals("It doesn't return the same String value which is just set.", "testSet String2 ", component
            .getName());
    }

    /**
     * <p>
     * Failure test for <code>setName(String)</code>.
     * </p>
     * <p>
     * Sets null <code>String</code> value. It is not allowed. IllegalArgumentException exception should be thrown.
     * </p>
     */
    public void testSetName_Failure_NullValue() {
        try {
            component.setName(null);
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>setName(String)</code>.
     * </p>
     * <p>
     * Sets empty <code>String</code> value. It is not allowed. IllegalArgumentException exception should be thrown.
     * </p>
     */
    public void testSetName_Failure_EmptyValue() {
        try {
            component.setName(" ");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getVersion()</code>.
     * </p>
     * <p>
     * Passes in a test <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testGetVersion_Accuracy_WithNonNullNonEmptyValue() {
        component.setVersion("testGet_String");
        assertEquals("It doesn't return the same String value which is just set.", "testGet_String", component
            .getVersion());
    }

    /**
     * <p>
     * Accuracy test for <code>setVersion(String)</code>.
     * </p>
     * <p>
     * Sets a <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testSetVersion_Accuracy_WithNonNullNonEmptyValue1() {
        component.setVersion("testSet_String");
        assertEquals("It doesn't return the same String value which is just set.", "testSet_String", component
            .getVersion());
    }

    /**
     * <p>
     * Accuracy test for <code>setVersion(String)</code>.
     * </p>
     * <p>
     * Sets another <code>String</code> value and gets the same value back. No exception should be thrown.
     * </p>
     */
    public void testSetVersion_Accuracy_WithNonNullNonEmptyValue2() {
        component.setVersion("testSet String2 ");
        assertEquals("It doesn't return the same String value which is just set.", "testSet String2 ", component
            .getVersion());
    }

    /**
     * <p>
     * Failure test for <code>setVersion(String)</code>.
     * </p>
     * <p>
     * Sets null <code>String</code> value. It is not allowed. IllegalArgumentException exception should be thrown.
     * </p>
     */
    public void testSetVersion_Failure_NullValue() {
        try {
            component.setVersion(null);
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>setVersion(String)</code>.
     * </p>
     * <p>
     * Sets empty <code>String</code> value. It is allowed. No exception should be thrown.
     * </p>
     */
    public void testSetVersion_Accuracy_EmptyValue() {
        component.setVersion(" ");
        assertEquals("It should be ' '.", " ", component.getVersion());
    }

    /**
     * <p>
     * Accuracy test for <code>getLanguage()</code>.
     * </p>
     * <p>
     * Sets in new value and gets it back correctly. No exception should be thrown.
     * </p>
     */
    public void testGetLanguage_Accuracy() {
        component.setLanguage(ComponentLanguage.JAVA);
        assertTrue("It should return java enum value.", ComponentLanguage.JAVA == component.getLanguage());
    }

    /**
     * <p>
     * Accuracy test for <code>setLanguage(ComponentLanguage)</code>.
     * </p>
     * <p>
     * Sets a new value successfully. No exception should be thrown.
     * </p>
     */
    public void testSetLanguage_Accuracy() {
        component.setLanguage(ComponentLanguage.DOT_NET);
        assertTrue("It should return dot net enum value.", ComponentLanguage.DOT_NET == component.getLanguage());
    }

    /**
     * <p>
     * Failure test for <code>setLanguage(ComponentLanguage)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testSetLanguage_Failure1() throws Exception {
        try {
            component.setLanguage(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

}
