/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;

import com.topcoder.util.objectfactory.ObjectFactory;

import junit.framework.TestCase;


/**
 * <p>
 * Tests functionality and error cases of <code>ExpenseEntryHelper</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class ExpenseEntryHelperUnitTest extends TestCase {
    /**
     * <p>
     * Sets up the test environment. The test instance is created. The configuration namespace is loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.clearConfig();
        UnitTestHelper.addConfig("Invalid.xml");
        UnitTestHelper.addConfig("DBConnectionFactory_Config.xml");
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
            ExpenseEntryHelper.validateNotNull(null, "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateNotNull(Object, String)</code> when the given object is not null.
     */
    public void testValidateNotNull_Accuracy() {
        ExpenseEntryHelper.validateNotNull(new Object(), "name");
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given String is null,
     * IllegalArgumentException is expected.
     */
    public void testValidateString_NullString() {
        try {
            ExpenseEntryHelper.validateString(null, "name");
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
            ExpenseEntryHelper.validateString(" ", "name");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Accuracy test for the method <code>validateString(String, String)</code> when the given string is correct.
     */
    public void testValidateString_Accuracy() {
        ExpenseEntryHelper.validateString("string", "name");
    }

    /**
     * Test the method of <code>createObjectFactory(String)</code> when the given namespace is incorrect,
     * ConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObjectFactory_NotCorrect() throws Exception {
        try {
            ExpenseEntryHelper.createObjectFactory("incorrect");
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
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
            ExpenseEntryHelper.createObjectFactory("com.topcoder.timetracker.entry.expense.objectfactory"));
    }

    /**
     * Test the method of <code>createObject(ObjectFactory, String, Class)</code> when the given key is incorrect,
     * ConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObject_NotCorrect() throws Exception {
        ObjectFactory factory = ExpenseEntryHelper.createObjectFactory(
                "com.topcoder.timetracker.entry.expense.objectfactory");

        try {
            ExpenseEntryHelper.createObject(factory, "Invalid", String.class);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createObject(ObjectFactory, String, Class)</code> when the generated class type is
     * expected, ConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObject_NotExpectedClass() throws Exception {
        ObjectFactory factory = ExpenseEntryHelper.createObjectFactory(
                "com.topcoder.timetracker.entry.expense.objectfactory");

        try {
            ExpenseEntryHelper.createObject(factory, "DBConnectionFactoryImpl", String.class);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createObject(ObjectFactory, String, Class)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateObject_Accuracy() throws Exception {
        ObjectFactory factory = ExpenseEntryHelper.createObjectFactory(
                "com.topcoder.timetracker.entry.expense.objectfactory");

        Object obj = ExpenseEntryHelper.createObject(factory, "DBConnectionFactoryImpl", DBConnectionFactory.class);
        assertTrue("The generated class type should be correct.", obj instanceof DBConnectionFactory);
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the namespace does not
     * exist, ConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_NotExistNamespace() throws Exception {
        try {
            ExpenseEntryHelper.getStringPropertyValue("NotExist", "Name3", true);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the property value is
     * missing and it is required, ConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_MissingRequiredPropertyValue() throws Exception {
        try {
            ExpenseEntryHelper.getStringPropertyValue("Test", "Name1", true);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the property value is empty
     * and it is required, ConfigurationException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_EmptyRequiredPropertyValue() throws Exception {
        try {
            ExpenseEntryHelper.getStringPropertyValue("Test", "Name2", true);
            fail("ConfigurationException should be thrown.");
        } catch (ConfigurationException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createConnection(DBConnectionFactory connectionFactory, String connectionName)</code>
     * when the connection name does not exist, PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateConnection_NotExistName() throws Exception {
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DBConnectionFactoryImpl.class.getName());

        try {
            ExpenseEntryHelper.createConnection(factory, "Invalid");
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> when the property value is
     * missing and it is not required, ConfigurationException is not expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_MissingOptionalPropertyValue() throws Exception {
        try {
            ExpenseEntryHelper.getStringPropertyValue("Test", "Name1", false);
        } catch (ConfigurationException e) {
            // good
            fail("ConfigurationException should not be thrown.");
        }
    }

    /**
     * Test the method of <code>getStringPropertyValue(String, String, boolean)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetStringPropertyValue_Accuracy() throws Exception {
        String value = ExpenseEntryHelper.getStringPropertyValue("Test", "Name3", true);
        assertEquals("The property value should be got properly.", "Value", value);
    }

    /**
     * Test the method of <code>getExceptionStaceTrace(Exception)</code> for accuracy.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetExceptionStaceTrace_Accuracy() throws Exception {
        ExpenseEntryHelper.getExceptionStaceTrace(new NullPointerException("null"));
    }
}
