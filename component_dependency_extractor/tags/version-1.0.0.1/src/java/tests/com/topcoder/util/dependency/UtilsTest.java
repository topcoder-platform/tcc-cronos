/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for <code>Utils</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UtilsTest extends TestCase {
    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
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
    }

    /**
     * <p>
     * Gets the test suite for <code>Utils</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>Utils</code> class.
     */
    public static Test suite() {
        return new TestSuite(UtilsTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>checkNull(Object,String)</code>.
     * </p>
     * <p>
     * Passes in not null value. No exception should be thrown.
     * </p>
     */
    public void testCheckNull_Accuracy() {
        Utils.checkNull(new Object(), "not null object");
    }

    /**
     * <p>
     * Failure test for <code>checkNull(Object,String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckNull_Failure1() throws Exception {
        try {
            Utils.checkNull(null, "null object");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkStringNullOrEmpty(String,String)</code>.
     * </p>
     * <p>
     * Passes in not null/not empty string. No exception should be thrown.
     * </p>
     */
    public void testCheckStringNullOrEmpty_Accuracy() {
        Utils.checkStringNullOrEmpty("not empty/not null", "valid string");
        // pass
    }

    /**
     * <p>
     * Failure test for <code>checkStringNullOrEmpty(String,String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckStringNullOrEmpty_Failure1() throws Exception {
        try {
            Utils.checkStringNullOrEmpty(null, "null string");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>checkStringNullOrEmpty(String,String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckStringNullOrEmpty_Failure2() throws Exception {
        try {
            Utils.checkStringNullOrEmpty(" ", "null string");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>checkCollection(Collection,String,boolean)</code>.
     * </p>
     * <p>
     * Passes in valid collection. No exception should be thrown.
     * </p>
     */
    public void testCheckCollection_Accuracy1() {
        Collection<Object> col = new ArrayList<Object>();
        col.add(new Object());
        Utils.checkCollection(col, "col", false);
        // pass
    }

    /**
     * <p>
     * Accuracy test for <code>checkCollection(Collection,String,boolean)</code>.
     * </p>
     * <p>
     * Passes in empty collection and allowed to be empty. No exception should be thrown.
     * </p>
     */
    public void testCheckCollection_Accuracy2() {
        Collection<Object> col = new ArrayList<Object>();
        Utils.checkCollection(col, "col", true);
        // pass
    }

    /**
     * <p>
     * Failure test for <code>checkCollection(Collection,String,boolean)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckCollection_Failure1() throws Exception {
        try {
            Utils.checkCollection(null, "col", false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>checkCollection(Collection,String,boolean)</code>.
     * </p>
     * <p>
     * Passes in empty value and not allowed to be empty. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckCollection_Failure2() throws Exception {
        try {
            Collection<Object> col = new ArrayList<Object>();
            Utils.checkCollection(col, "col", false);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>checkCollection(Collection,String,boolean)</code>.
     * </p>
     * <p>
     * Passes in collection which contains null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCheckCollection_Failure3() throws Exception {
        try {
            Collection<Object> col = new ArrayList<Object>();
            col.add(new Object());
            col.add(null);
            Utils.checkCollection(col, "col", true);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>isStringNullOrEmpty(String)</code>.
     * </p>
     * <p>
     * Passes in null value. No exception should be thrown.
     * </p>
     */
    public void testIsStringNullOrEmpty_Accuracy1() {
        assertTrue("it should return true.", Utils.isStringNullOrEmpty(null));
    }

    /**
     * <p>
     * Accuracy test for <code>isStringNullOrEmpty(String)</code>.
     * </p>
     * <p>
     * Passes in empty value. No exception should be thrown.
     * </p>
     */
    public void testIsStringNullOrEmpty_Accuracy2() {
        assertTrue("it should return true.", Utils.isStringNullOrEmpty(""));
    }

    /**
     * <p>
     * Accuracy test for <code>isStringNullOrEmpty(String)</code>.
     * </p>
     * <p>
     * Passes in empty value. No exception should be thrown.
     * </p>
     */
    public void testIsStringNullOrEmpty_Accuracy3() {
        assertTrue("it should return true.", Utils.isStringNullOrEmpty(" "));
    }

    /**
     * <p>
     * Accuracy test for <code>isStringNullOrEmpty(String)</code>.
     * </p>
     * <p>
     * Passes in non-null/non-empty string value. No exception should be thrown.
     * </p>
     */
    public void testIsStringNullOrEmpty_Accuracy4() {
        assertFalse("it should return false.", Utils.isStringNullOrEmpty("no empty string"));
    }

    /**
     * <p>
     * Accuracy test for <code>logEnter(Log,String)</code>.
     * </p>
     * <p>
     * Please check log to see if it has been logged. No exception should be thrown.
     * </p>
     */
    public void testLogEnter_Accuracy() {
        Utils.logEnter(LogManager.getLog(), "SomeClass#someMethod");
    }

    /**
     * <p>
     * Accuracy test for <code>logExit(Log,String)</code>.
     * </p>
     * <p>
     * Please look at log. No exception should be thrown.
     * </p>
     */
    public void testLogExit_Accuracy() {
        Utils.logExit(LogManager.getLog(), "SomeClass#someMethod");
    }

    /**
     * <p>
     * Accuracy test for <code>logInfo(Log,String)</code>.
     * </p>
     * <p>
     * Please look at log. No exception should be thrown.
     * </p>
     */
    public void testLogInfo_Accuracy() {
        Utils.logInfo(LogManager.getLog(), "info");
    }

    /**
     * <p>
     * Accuracy test for <code>logWarningException(Log,String,Exception)</code>.
     * </p>
     * <p>
     * Please look at log. No exception should be thrown.
     * </p>
     */
    public void testLogWarningException_Accuracy() {
        Utils.logWarningException(LogManager.getLog(), "SomeClass#someMethod", null);
    }

    /**
     * <p>
     * Accuracy test for <code>logException(Log,T)</code>.
     * </p>
     * <p>
     * Please look at log No exception should be thrown.
     * </p>
     */
    public void testLogException_Accuracy() {
        assertNotNull("it should return non-null exception back.", Utils.logException(LogManager.getLog(),
            new Exception()));
    }

    /**
     * <p>
     * Accuracy test for <code>closeInputStreamSafely(InputStream)</code>.
     * </p>
     * <p>
     * Closes out input stream successfully (error will be ignored if any). No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCloseInputStreamSafely_Accuracy() throws Exception {
        FileInputStream is = new FileInputStream("test_files" + File.separator + "testCloseInputStream.txt");
        Utils.closeInputStreamSafely(is);
    }

    /**
     * <p>
     * Accuracy test for <code>closeOutputStreamSafely(OutputStream)</code>.
     * </p>
     * <p>
     * Closes out output stream successfully (error will be ignored if any). No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCloseOutputStreamSafely_Accuracy() throws Exception {
        FileOutputStream os = new FileOutputStream("test_files" + File.separator + "testCloseOuputStream.txt");
        Utils.closeOutputStreamSafely(os);
    }

    /**
     * <p>
     * Accuracy test for <code>getRequiredStringValue(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Gets required string value successfully. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetRequiredStringValue_Accuracy() throws Exception {
        assertEquals("It should return 'value'.", "value", Utils.getRequiredStringValue(getTestConfig(),
            "requiredString"));
        // ok
    }

    /**
     * <p>
     * Failure test for <code>getRequiredStringValue(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Tries to get a required string which doesn't exist. A <code>ConfigurationException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetRequiredStringValue_Failure1() throws Exception {
        try {
            Utils.getRequiredStringValue(getTestConfig(), "requiredStringNotExists");
            fail("ConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>getRequiredStringValue(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Tries to get a required string which is empty value. A <code>ConfigurationException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetRequiredStringValue_Failure2() throws Exception {
        try {
            Utils.getRequiredStringValue(getTestConfig(), "requiredStringEmpty");
            fail("ConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getOptionalStringValue(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Gets an optional string. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetOptionalStringValue_Accuracy1() throws Exception {
        assertEquals("It should return 'valueOptional'.", "valueOptional", Utils.getOptionalStringValue(
            getTestConfig(), "optionalString"));
    }

    /**
     * <p>
     * Accuracy test for <code>getOptionalStringValue(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Returns a null for an optional string value . No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetOptionalStringValue_Accuracy2() throws Exception {
        assertNull("It should return null.", Utils.getOptionalStringValue(getTestConfig(), "optionalStringNotExist"));
    }

    /**
     * <p>
     * Failure test for <code>getOptionalStringValue(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>ComponentDependencyConfigurationException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetOptionalStringValue_Failure1() throws Exception {
        try {
            Utils.getOptionalStringValue(getTestConfig(), "optionalStringEmpty");
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>getRequiredConfigurationObject(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Gets non-null configuration object. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetRequiredConfigurationObject_Accuracy() throws Exception {
        assertNotNull("It should return non-null configuration object.", Utils.getRequiredConfigurationObject(
            getTestConfig(), "ofConfigKey"));
    }

    /**
     * <p>
     * Failure test for <code>getRequiredConfigurationObject(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Can not get a required configuration object. A <code>ComponentDependencyConfigurationException</code> should be
     * thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetRequiredConfigurationObject_Failure1() throws Exception {
        try {
            Utils.getRequiredConfigurationObject(getTestConfig(), "not.exist");
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>getRequiredConfigurationObject(ConfigurationObject,String)</code>.
     * </p>
     * <p>
     * Can not get a required configuration object. It is a string value under the key. A
     * <code>ComponentDependencyConfigurationException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testGetRequiredConfigurationObject_Failure2() throws Exception {
        try {
            Utils.getRequiredConfigurationObject(getTestConfig(), "objectKey");
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createObject(Class,ConfigurationObject,String,String)</code>.
     * </p>
     * <p>
     * Creates an object through configuration object and object factory. No exception should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateObject_Accuracy() throws Exception {
        assertNotNull("It should return non-null object being created.", Utils.createObject(ArrayList.class,
            getTestConfig(), "ofConfigKey", "objectKey"));
    }

    /**
     * <p>
     * Failure test for <code>createObject(Class,ConfigurationObject,String,String)</code>.
     * </p>
     * <p>
     * The class type is not matched with expected one. A <code>ComponentDependencyConfigurationException</code>
     * should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateObject_Failure1() throws Exception {
        try {
            Utils.createObject(ArrayList.class, getTestConfig(), "ofConfigKeyWrong", "objectKey");
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createObject(Class,ConfigurationObject,String,String)</code>.
     * </p>
     * <p>
     * The class type declared in object factory configuration is not a valid class type. A
     * <code>ComponentDependencyConfigurationException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateObject_Failure2() throws Exception {
        try {
            Utils.createObject(ArrayList.class, getTestConfig(), "ofConfigKeyWrong2", "objectKey");
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createObject(Class,ConfigurationObject,String,String)</code>.
     * </p>
     * <p>
     * The class type declared in object factory configuration is an interface type. A
     * <code>ComponentDependencyConfigurationException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateObject_Failure3() throws Exception {
        try {
            Utils.createObject(ArrayList.class, getTestConfig(), "ofConfigKeyWrong3", "objectKey");
            fail("ComponentDependencyConfigurationException should be thrown.");
        } catch (ComponentDependencyConfigurationException e) {
            // pass
        }
    }

    /**
     * <p>
     * Gets test configuration object.
     * </p>
     *
     * @return test configuration object
     *
     * @throws Exception to JUnit, indicates an error
     */
    private ConfigurationObject getTestConfig() throws Exception {
        String configManagerFile = "UtilsUnitTest.properties";
        String namespace = "UtilsUnitTest";
        ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(configManagerFile);
        return configurationFileManager.getConfiguration(namespace).getChild(namespace);
    }

    /**
     * <p>
     * Test to see if a private Ctor exists.
     * </p>
     *
     * @throws Exception to JUnit, indicates error
     */
    public void testPrivateCtor() throws Exception {
        try {
            Constructor privateCtor = Utils.class.getDeclaredConstructors()[0];
            assertTrue(Modifier.isPrivate(privateCtor.getModifiers()));
            privateCtor.setAccessible(true);
            privateCtor.newInstance(new Object[] {});
        } catch (SecurityException e) {
            System.out.println("Skip test private constructor due to security reason.");
        }
    }
}
