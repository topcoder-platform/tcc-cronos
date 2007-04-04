/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import com.topcoder.timetracker.audit.persistence.InformixAuditPersistence;

import com.topcoder.util.objectfactory.ObjectFactory;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>TimeTrackerAuditHelper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class TimeTrackerAuditHelperUnitTest extends TestCase {
    /**
     * <p>
     * Sets up the test environment. The test instance is created. The configuration namespace is loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateNotNull_NullObject() {
        try {
            TimeTrackerAuditHelper.validateNotNull(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is not null.
     */
    public void testValidateNotNull_Accuracy() {
        TimeTrackerAuditHelper.validateNotNull(new Object(), "name");
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_NullString() {
        try {
            TimeTrackerAuditHelper.validateString(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is empty,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_EmptyString() {
        try {
            TimeTrackerAuditHelper.validateString(" ", "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given string is correct.
     */
    public void testValidateString_Accuracy() {
        TimeTrackerAuditHelper.validateString("string", "name");
    }

    /**
     * Test the method of <code>createObjectFactory(String)</code> when the given namespace is incorrect,
     * AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObjectFactory_NotCorrect() throws Exception {
        try {
            TimeTrackerAuditHelper.createObjectFactory("incorrect");
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>createObjectFactory(String)</code> when the given namespace is correct.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObjectFactory_Accuracy() throws Exception {
        assertNotNull("The object factory should be created properly.",
            TimeTrackerAuditHelper.createObjectFactory("com.topcoder.timetracker.audit.ejb.objectfactory"));
    }

    /**
     * Test the method of <code>createObject(ObjectFactory, String, Class)</code> when the given key is incorrect,
     * AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObject_NotCorrect() throws Exception {
        ObjectFactory factory = TimeTrackerAuditHelper.createObjectFactory(
                "com.topcoder.timetracker.audit.ejb.objectfactory");

        try {
            TimeTrackerAuditHelper.createObject(factory, "Invalid", InformixAuditPersistence.class);
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createObject(ObjectFactory, String, Class)</code> when the generated class type is
     * expected, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObject_NotExpectedClass() throws Exception {
        ObjectFactory factory = TimeTrackerAuditHelper.createObjectFactory(
                "com.topcoder.timetracker.audit.ejb.objectfactory");

        try {
            TimeTrackerAuditHelper.createObject(factory, "InformixAuditPersistence", String.class);
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createObject(ObjectFactory, String, Class)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObject_Accuracy() throws Exception {
        ObjectFactory factory = TimeTrackerAuditHelper.createObjectFactory(
                "com.topcoder.timetracker.audit.ejb.objectfactory");

        Object obj = TimeTrackerAuditHelper.createObject(factory, "InformixAuditPersistence",
                InformixAuditPersistence.class);
        assertTrue("The generated class type should be correct.", obj instanceof InformixAuditPersistence);
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the namespace does not
     * exist, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_NotExistNamespace() throws Exception {
        try {
            TimeTrackerAuditHelper.getStringPropertyValue("NotExist", "idGeneratorName", true);
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the property value is
     * missing and it is required, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_MissingRequiredPropertyValue() throws Exception {
        try {
            TimeTrackerAuditHelper.getStringPropertyValue(InformixAuditPersistence.class.getName(), "NotExist", true);
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the property value is
     * empty and it is required, AuditConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_EmptyRequiredPropertyValue() throws Exception {
        try {
            TimeTrackerAuditHelper.getStringPropertyValue(
                "com.topcoder.timetracker.audit.persistence.DefaultValuesContainer.InvalidId", "id", true);
            fail("AuditConfigurationException should be thrown.");
        } catch (AuditConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the property value is
     * missing and it is not required, AuditConfigurationException is not expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_MissingOptionalPropertyValue() throws Exception {
        try {
            TimeTrackerAuditHelper.getStringPropertyValue(InformixAuditPersistence.class.getName(), "NotExist", false);
        } catch (AuditConfigurationException e) {
            // good
            fail("AuditConfigurationException should not be thrown.");
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_Accuracy() throws Exception {
        String value = TimeTrackerAuditHelper.getStringPropertyValue(InformixAuditPersistence.class.getName(),
                "idGeneratorName", true);
        assertEquals("The property value should be got properly.", "AuditPersistence", value);
    }

    /**
     * Test the method of <code>getExceptionStaceTrace(Exception)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetExceptionStaceTrace_Accuracy() throws Exception {
        TimeTrackerAuditHelper.getExceptionStaceTrace(new NullPointerException("null"));
    }
}
