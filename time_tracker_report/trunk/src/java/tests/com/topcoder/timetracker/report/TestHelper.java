/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.timetracker.report;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 * This class tests the <code>Helper</code> class. It tests:
 * <ol>
 * <li>no public constructor available</li>
 * <li>checkNotNull(Object, String) method</li>
 * <li>checkString(String, String) method</li>
 * <li>checkPositive(long, String) method</li>
 * <li>checkArrayNull(Object[], String) method</li>
 * <li>checkArrayPositive(long[], String) method</li>
 * <li>checkDateRange(Date, Date, String, String) method</li>
 * <li>getPropertyValue(String, String, boolean) method</li>
 * <li>getObjectFactory(String, String) method</li>
 * </ol>
 * </p>
 *
 * @author rinoavd
 * @version 3.1
 */
public class TestHelper extends BaseTestCase {

    /**
     * <p>
     * Represents the location of the sample configuration file to test the Config Manager.
     * </p>
     */
    public static final String HELPER_TEST_CONFIG_FILE_LOCATION = "TestHelper_ConfigManager.xml";

    /**
     * <p>
     * Represents the location of the sample configuration file to test the Config Manager.
     * </p>
     */
    public static final String HELPER_TEST_CONFIG_MANAGER_NAMESPACE = "HelperTest_ConfigManagerNS";

    /**
     * <p>
     * Setup the test case. Initial the <code>ConfigManager</code> with the configuration files
     * stored in test_files folder.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        super.setUp();

        this.removeConfigManagerNS();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(HELPER_TEST_CONFIG_FILE_LOCATION);
    }

    /**
     * <p>
     * Tear down the test case.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        this.removeConfigManagerNS();
        super.tearDown();
    }

    /**
     * <p>
     * The only constructor is with modifier 'private'.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testHelper_Ctor() throws Exception {
        assertEquals("Class Helper contains public Constructor.", 0, Helper.class.getConstructors().length);

        Constructor[] ctor = Helper.class.getDeclaredConstructors();
        assertTrue(
                "Class Helper should contain only 1 private Constructor.",
                (ctor.length == 1 && ctor[0].getModifiers() == Modifier.PRIVATE));
    }

    /**
     * <p>
     * Test method <code>checkNotNull()</code>.
     * </p>
     *
     * <p>
     * Object passed in is not null, <code>IllegalAccessException</code> is not expected.
     * </p>
     */
    public void testCheckNotNull_1_NotNullObject() {
        Helper.checkNotNull(new Object(), "test object");
    }

    /**
     * <p>
     * Test method <code>checkNotNull()</code>.
     * </p>
     *
     * <p>
     * Object passed in is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckNotNull_2_NullObject() {
        try {
            Helper.checkNotNull(null, "test object");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Parameter argument 'test object' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>checkString()</code>.
     * </p>
     *
     * <p>
     * String to be checked is not null or empty, <code>IllegalArgumentException</code> is not
     * expected.
     * </p>
     */
    public void testCheckString_1_NotNullNotEmpty() {
        Helper.checkString("test", "test string");
    }

    /**
     * <p>
     * Test method <code>checkString()</code>.
     * </p>
     *
     * <p>
     * String to be checked is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckString_2_NullString() {
        try {
            Helper.checkString(null, "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Parameter argument 'test string' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>checkString()</code>.
     * </p>
     *
     * <p>
     * String to be checked is empty string, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckString_3_EmptyString() {
        try {
            Helper.checkString(" ", "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            assertEquals(
                    ex.getMessage(),
                    "Parameter argument 'test string' can not be empty string (trimmed)!");
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotNull()</code>.
     * </p>
     *
     * <p>
     * The array with some elements is passed in, <code>IllegalArgumentException</code> is not
     * expected.
     * </p>
     */
    public void testCheckArrayNotNull_1_ValidArray() {
        Helper.checkArrayNotNull(new Object[] {new Object(), new Object() }, "test array");
    }

    /**
     * <p>
     * Test method <code>checkArrayNotNull()</code>.
     * </p>
     *
     * <p>
     * The array with no elements is passed, <code>IllegalArgumentException</code> is not
     * expected.
     * </p>
     */
    public void testCheckArrayNotNull_2_EmptyArray() {
        Helper.checkArrayNotNull(new Object[] {}, "test array");
    }

    /**
     * <p>
     * Test method <code>checkArrayNotNull()</code>.
     * </p>
     *
     * <p>
     * The array to be checked is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckArrayNotNull_3_NullArray() {
        try {
            Helper.checkArrayNotNull(null, "test array");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Parameter argument 'test array' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotNull()</code>.
     * </p>
     *
     * <p>
     * The array with some elements is passed in, one of the element is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testCheckArrayNotNull_4_ArrayContainsNull() {
        try {
            Helper.checkArrayNotNull(new Object[] {new Object(), null }, "test array");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), "Parameter argument 'test array[1]' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayString()</code>.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     */
    public void testCheckArrayString_1_NullArray() {
        try {
            Helper.checkArrayString(null, "test array");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayString()</code>.
     * </p>
     *
     * <p>
     * An array containing null element is passed in. IllegalArgumentException is expected.
     * </p>
     */
    public void testCheckArrayString_2_ArrayContainsNull() {
        try {
            Helper.checkArrayString(new String[] {null, "abc" }, "test array");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayString()</code>.
     * </p>
     *
     * <p>
     * An array containing empty element is passed in.
     * </p>
     */
    public void testCheckArrayString_3_ArrayContainsEmptyString() {
        try {
            Helper.checkArrayString(new String[] {"abc", "  " }, "test array");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotEmptyNotNull()</code>.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     */
    public void test_checkArrayNotEmptyNotNull_1_NullArray() {
        try {
            Helper.checkArrayNotEmptyNotNull(null, "test array");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotEmptyNotNull()</code>.
     * </p>
     *
     * <p>
     * An array containing null element is passed in. IllegalArgumentException is expected.
     * </p>
     */
    public void test_checkArrayNotEmptyNotNull_2_ArrayContainsNull() {
        try {
            Helper.checkArrayNotEmptyNotNull(new String[] {null, "abc" }, "test array");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotEmptyNotNull()</code>.
     * </p>
     *
     * <p>
     * An array containing no element is passed in.
     * </p>
     */
    public void test_checkArrayNotEmptyNotNull_3_EmptyArray() {
        try {
            Helper.checkArrayNotEmptyNotNull(new String[] {}, "test array");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException ex) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotEmpty()</code>.
     * </p>
     *
     * <p>
     * null is passed in. IllegalArgumentException is expected.
     * </p>
     */
    public void test_checkArrayNotEmpty_1_NullArray() {
        try {
            Helper.checkArrayNotEmpty(null, "test array");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>checkArrayNotEmpty()</code>.
     * </p>
     *
     * <p>
     * An array containing no element is passed in. IllegalArgumentException is expected.
     * </p>
     */
    public void test_checkArrayNotEmpty_2_EmptyArray() {
        try {
            Helper.checkArrayNotEmptyNotNull(new String[] {}, "test array");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_1_NullNamespace() throws Exception {
        try {
            Helper.getPropertyValue(null, "property_not_empty", true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter argument 'namespace' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Namespace is empty string, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_2_EmptyNamespace() throws Exception {
        try {
            Helper.getPropertyValue("   ", "property_not_empty", false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter argument 'namespace' can not be empty string (trimmed)!");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property name is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_3_NullPropertyName() throws Exception {
        try {
            Helper.getPropertyValue("HELPER_TEST_CONFIG_MANAGER_NAMESPACE", null, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter argument 'propertyName' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property name is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_4_EmptyPropertyName() throws Exception {
        try {
            Helper.getPropertyValue("HELPER_TEST_CONFIG_MANAGER_NAMESPACE", "   ", false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(
                    e.getMessage(),
                    "Parameter argument 'propertyName' can not be empty string (trimmed)!");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * namespace is unknown, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_5_UnknownNamespace() throws Exception {
        try {
            Helper.getPropertyValue("Unknown", "property_not_empty", true);
            fail("ReportConfigException is expected");
        } catch (ReportConfigException e) {
            assertTrue(e.getMessage().indexOf("The namespace 'Unknown' is unknown.") >= 0);
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property is required, and it is missing, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_6_MissingRequiredProperty() throws Exception {
        try {
            Helper.getPropertyValue(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, "unknown", true);
            fail("ReportConfigException is expected");
        } catch (ReportConfigException e) {
            assertEquals(e.getMessage(), "Missed property 'unknown'.");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property is not required, and it is missing, <code>ReportConfigException</code> is not
     * expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_7_MissingOptionalProprety() throws Exception {
        assertNull(Helper.getPropertyValue(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, "Unknown", false));
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property is empty, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_8_EmptyProperty() throws Exception {
        try {
            Helper.getPropertyValue(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, "property_empty", true);
            fail("ReportConfigException is expected");
        } catch (ReportConfigException ex) {
            assertEquals(ex.getMessage(), "The value for property 'property_empty' is empty.");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Within namespace "ConfigManagerTest_NS", the value of "property_not_empty" should be "this is
     * the value".
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue_9_CorrectPropertyValueRetrieved() throws Exception {
        assertEquals("this is the value", Helper.getPropertyValue(
                HELPER_TEST_CONFIG_MANAGER_NAMESPACE,
                "property_not_empty",
                true));
    }

    /**
     * <p>
     * Test method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_1_NullNamespace() throws Exception {
        try {
            Helper.getObjectFactory(null, "property_object_factory_ns");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter argument 'namespace' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * namespace is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_2_EmptyNamespace() throws Exception {
        try {
            Helper.getObjectFactory("   ", "property_object_factory_ns");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter argument 'namespace' can not be empty string (trimmed)!");
        }
    }

    /**
     * <p>
     * Test method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * propertyName is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_3_NullProperty() throws Exception {
        try {
            Helper.getObjectFactory(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Parameter argument 'propertyName' can not be null!");
        }
    }

    /**
     * <p>
     * Test method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * propertyName is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_4_EmptyProperty() throws Exception {
        try {
            Helper.getObjectFactory(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, "   ");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(
                    e.getMessage(),
                    "Parameter argument 'propertyName' can not be empty string (trimmed)!");
        }
    }

    /**
     * <p>
     * Test method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * namespace is unknown, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_5_UnknownNamespace() throws Exception {
        try {
            Helper.getObjectFactory("Unknown", "property_object_factory_ns");
            fail("ReportConfigException is expected");
        } catch (ReportConfigException e) {
            assertTrue(e.getMessage().indexOf("The namespace 'Unknown' is unknown.") >= 0);
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory property is missing, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_6_MissingObjectFactoryNamespace() throws Exception {
        try {
            Helper.getObjectFactory(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, "unknown_property");
            fail("ReportConfigException is expected");
        } catch (ReportConfigException e) {
            assertEquals(e.getMessage(), "Missed property 'unknown_property'.");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory property is empty, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_7_EmptyObjectFactoryNamespace() throws Exception {
        try {
            Helper.getObjectFactory(HELPER_TEST_CONFIG_MANAGER_NAMESPACE, "property_empty");
            fail("ReportConfigException is expected");
        } catch (ReportConfigException e) {
            assertEquals(e.getMessage(), "The value for property 'property_empty' is empty.");
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory namespace is unknown, <code>ReportConfigException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_8_UnknownObjectFactoryNamespace() throws Exception {
        try {
            Helper.getObjectFactory(
                    HELPER_TEST_CONFIG_MANAGER_NAMESPACE,
                    "property_unknown_object_factory_ns");
            fail("ReportConfigException is expected");
        } catch (ReportConfigException ex) {
            assertTrue(ex.getCause() instanceof SpecificationConfigurationException);
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory namespace contains invalid configuration, <code>ReportConfigException</code>
     * is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_9_InvalidObjectFactoryNamespace() throws Exception {
        try {
            Helper.getObjectFactory(
                    HELPER_TEST_CONFIG_MANAGER_NAMESPACE,
                    "property_invalid_object_factory_ns");
            fail("ReportConfigException is expected");
        } catch (ReportConfigException e) {
            assertTrue(e.getCause() instanceof IllegalReferenceException);
        }
    }

    /**
     * <p>
     * Test method <code>getPropertyValue()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory_10_Acc() throws Exception {
        ObjectFactory objFactory =
                Helper.getObjectFactory(
                        HELPER_TEST_CONFIG_MANAGER_NAMESPACE,
                        "property_valid_object_factory_ns");
        assertNotNull(objFactory);
    }

}
