/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.management.ban.BanManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.management.resource.Resource;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.util.log.Log;

import java.util.Locale;
import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class RegistrationValidationHelper, the helper class for
 * the component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegistrationValidationHelperTest extends TestCase {
    /**
     * <p>
     * Represents the class name used to log.
     * </p>
     *
     */
    private final String className = "RegistrationValidationHelperTest";

    /**
     * <p>
     * Represents the ObjectFactory instance used to test
     * the createArrayFromObjectFactory and createObjectFromObjectFactory method.
     * </p>
     *
     */
    private ObjectFactory factory;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();
        TestHelper.loadXMLConfig("DataValidationRegistrationValidator.xml");
        TestHelper.loadXMLConfig("RegistrationValidationHelperTest.xml");
        TestHelper.loadXMLConfig("Document_Generator.xml");

        // creates object factory from the given namespace
        String namespace = "test.CreateObjectFromObjectFactory";
        // loads namespace for object factory
        String objectFactoryNamespace = RegistrationValidationHelper.getString(
                "test.CreateArrayFromObjectFactory", "specNamespace", true);
        // creates object factory from the given namespace
        factory = new ObjectFactory(
                new ConfigManagerSpecificationFactory(objectFactoryNamespace));
    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNull(Object,String) method.
     * </p>
     *
     * <p>
     * It test the case when a not null object is passed in and expects success.
     * </p>
     */
    public void testValidateNotNull_NotNullObject() {
        RegistrationValidationHelper.validateNotNull("", "test");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNull(Object,String) method.
     * </p>
     *
     * <p>
     * It test the case when a null object is passed in and expects
     * IllegalArgumentException.
     * </p>
     */
    public void testValidateNotNull_NullObject() {
        try {
            RegistrationValidationHelper.validateNotNull(null, "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateStringNotNullOrEmpty(String,String)
     * method for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the string is valid.
     * </p>
     *
     * <p>
     * Should have passed the validation.
     * </p>
     */
    public void testValidateStringNotNullOrEmpty() {
        RegistrationValidationHelper.validateStringNotNullOrEmpty(
                "valid string", "test");
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateStringNotNullOrEmpty(String,String)
     * method for failure.
     * </p>
     *
     * <p>
     * It tests the case when the string is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     */
    public void testValidateStringNotNullOrEmpty_Null() {
        try {
            RegistrationValidationHelper.validateStringNotNullOrEmpty(null,
                    "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateStringNotNullOrEmpty(String,String)
     * method for failure.
     * </p>
     *
     * <p>
     * It tests the case when the string is empty.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     */
    public void testValidateStringNotNullOrEmpty_Empty() {
        try {
            RegistrationValidationHelper.validateStringNotNullOrEmpty("",
                    "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateStringNotNullOrEmpty(String,String)
     * method for failure.
     * </p>
     *
     * <p>
     * It tests the case when the string is full of space.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     */
    public void testValidateStringNotNullOrEmpty_TrimmedEmpty() {
        try {
            RegistrationValidationHelper.validateStringNotNullOrEmpty("  ",
                    "test");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the normal case.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo() {
        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo");

        Locale locale = new Locale("en", "ca", "Traditional_WIN");
        BundleInfo myBundleInfo = new BundleInfo();
        myBundleInfo.setBundle("myBundle", locale);
        myBundleInfo.setDefaultMessage("./test_files/myTemplate.txt");
        myBundleInfo.setMessageKey("myMessageKey");
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(myBundleInfo, bundleInfo));
        assertEquals("Failed to create a new bundleInfo instance correctly.",
                locale.getLanguage(), bundleInfo.getLocale().getLanguage());
        assertEquals("Failed to create a new bundleInfo instance correctly.",
                locale.getCountry(), bundleInfo.getLocale().getCountry());
        assertEquals("Failed to create a new bundleInfo instance correctly.",
                locale.getVariant(), bundleInfo.getLocale().getVariant());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when bundleLocaleLanguage dose not exist.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo_NonBundleLocaleLanguage() {
        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.NonBundleLocaleLanguage");

        Locale locale = Locale.getDefault();
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);
        assertEquals("Failed to create a new bundleInfo instance correctly.",
                locale.getLanguage(), bundleInfo.getLocale().getLanguage());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when bundleLocaleCountry dose not exist.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo_NonBundleLocaleCountry() {

        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.NonBundleLocaleCountry");

        Locale locale = new Locale("en");
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);
        assertEquals("Failed to create a new bundleInfo instance correctly.",
                locale.getCountry(), bundleInfo.getLocale().getCountry());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when bundleLocaleVariant dose not exist.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo_NonBundleLocaleVariant() {

        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.NonBundleLocaleVariant");

        Locale locale = new Locale("en", "ca");
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);
        assertEquals("Failed to create a new bundleInfo instance correctly.",
                locale.getVariant(), bundleInfo.getLocale().getVariant());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when namespace is unknown.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateBundleInfo_UnknownNamespace() {
        try {
            RegistrationValidationHelper.createBundleInfo("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when bundleName is missing in the namespace.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo_MissingBundleName() {
        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.MissingBundleName");
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);
        assertNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getBundleName());
        assertNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getMessageKey());
        assertNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getResourceBundle());
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getDefaultMessage());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when both of bundleName and defaultMessage are missing
     * in the namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateBundleInfo_MissingBundleNameAndDefaultMessage() {
        try {
            RegistrationValidationHelper
                    .createBundleInfo("test.BundleInfo.MissingBundleNameAndDefaultMessage");

            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when bundleName is empty.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo_EmptyBundleName() {

        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.EmptyBundleName");
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);
        assertNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getBundleName());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when bundleName is used but messageKey is missing in
     * the namespace.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateBundleInfo_MissingMessageKey() {
        try {
            RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.MissingMessageKey");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createBundleInfo(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when defaultMessage is missing in the namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateBundleInfo_MissingDefaultMessage() {
        BundleInfo bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo.MissingDefaultMessage");
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo);

        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getBundleName());
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getMessageKey());
        assertNotNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getResourceBundle());
        assertNull("Failed to create a new bundleInfo instance correctly.",
                bundleInfo.getDefaultMessage());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#appendInnerDataValue(String, String,
     * String) for accuracy.
     * </p>
     *
     * <p>
     * Should have returned the data string after append the inner data value
     * with the inner data name.
     * </p>
     *
     */
    public void testAppendInnerDataValue() {

        String newData = RegistrationValidationHelper.appendInnerDataValue(
                "<DATA>\n</DATA>", "Name", "value");
        assertEquals("Failed to set user correctly.",
                "<DATA>\n<Name>value</Name>\n</DATA>", newData);

    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#appendInnerDataValue(String, String,
     * String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when innerDataValue is null.
     * </p>
     *
     * <p>
     * Should have returned the data string after append empty string as inner
     * data value.
     * </p>
     *
     */
    public void testAppendInnerDataValue_NullValue() {

        String newData = RegistrationValidationHelper.appendInnerDataValue(
                "<DATA>\n</DATA>", "Name", null);
        assertEquals("Failed to set user correctly.",
                "<DATA>\n<Name></Name>\n</DATA>", newData);

    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#buildStandInfo(ValidationInfo) for
     * accuracy.
     * </p>
     *
     *
     * <p>
     * Should have returned the template data string.
     * </p>
     *
     */
    public void testBuildStandInfo() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();

        String data = RegistrationValidationHelper
                .buildStandInfo(validationInfo);
        assertNotNull(
                "Failed to build template data string for the stand information.",
                data);

    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getBoolean(String, String) for
     * accuracy.
     * </p>
     *
     * <p>
     * Should have parsed the property value correctly.
     * </p>
     *
     */
    public void testGetBoolean() {
        assertTrue("Failed to get the boolean value.",
                RegistrationValidationHelper.getBoolean(
                        "test.RegistrationValidationHelper", "trueProperty"));

        assertFalse("Failed to get the boolean value.",
                RegistrationValidationHelper.getBoolean(
                        "test.RegistrationValidationHelper", "falseProperty"));
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getBoolean(String, String) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetBoolean_Missing() {
        try {
            RegistrationValidationHelper.getBoolean(
                    "test.RegistrationValidationHelper", "Missing");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getBoolean(String, String) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the property value is invalid
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetBoolean_Invalid() {
        try {
            RegistrationValidationHelper.getBoolean(
                    "test.RegistrationValidationHelper", "bad");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getDouble(String, String) for
     * accuracy.
     * </p>
     *
     * <p>
     * Should have parsed the property value correctly.
     * </p>
     *
     */
    public void testGetDouble() {
        assertEquals("Failed to get the boolean value.", new Double(1),
                RegistrationValidationHelper.getDouble(
                        "test.RegistrationValidationHelper", "aDoubleProperty"));
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getDouble(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetDouble_Missing() {
        try {
            RegistrationValidationHelper.getDouble(
                    "test.RegistrationValidationHelper", "Missing");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getDouble(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property value is invalid
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetDouble_Invalid() {

        try {
            RegistrationValidationHelper.getDouble(
                    "test.RegistrationValidationHelper", "bad");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getDouble(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property value is negative
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetDouble_Negative() {
        try {
            RegistrationValidationHelper.getDouble(
                    "test.RegistrationValidationHelper", "negative");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getLong(String, String) for accuracy.
     * </p>
     *
     * <p>
     * Should have parsed the property value correctly.
     * </p>
     *
     */
    public void testGetLong() {

        assertEquals("Failed to get the boolean value.", new Long(1),
                RegistrationValidationHelper.getLong(
                        "test.RegistrationValidationHelper", "aLongProperty"));
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getLong(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetLong_Missing() {

        try {
            RegistrationValidationHelper.getLong(
                    "test.RegistrationValidationHelper", "Missing");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getLong(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property value is invalid
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetLong_Invalid() {

        try {
            RegistrationValidationHelper.getLong(
                    "test.RegistrationValidationHelper", "bad");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getLong(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property value is negative.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetLong_Negative() {
        try {
            RegistrationValidationHelper.getLong(
                    "test.RegistrationValidationHelper", "negative");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getInteger(String, String) for
     * accuracy.
     * </p>
     *
     * <p>
     * Should have parsed the property value correctly.
     * </p>
     *
     */
    public void testGetInteger() {
        assertEquals("Failed to get the boolean value.", new Integer(1),
                RegistrationValidationHelper
                        .getInteger("test.RegistrationValidationHelper",
                                "aIntegerProperty"));
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getInteger(String, String) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetInteger_Missing() {
        try {
            RegistrationValidationHelper.getInteger(
                    "test.RegistrationValidationHelper", "Missing");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getInteger(String, String) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the property value is invalid
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetInteger_Invalid() {
        try {
            RegistrationValidationHelper.getInteger(
                    "test.RegistrationValidationHelper", "bad");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getInteger(String, String) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the property value is negative
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetInteger_Negative() {
        try {
            RegistrationValidationHelper.getInteger(
                    "test.RegistrationValidationHelper", "negative");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getString(String, String) for
     * accuracy.
     * </p>
     *
     * <p>
     * Should have returned the property value correctly.
     * </p>
     *
     */
    public void testGetString() {
        assertEquals("Failed to get the boolean value.",
                "myStringPropertyValue", RegistrationValidationHelper
                        .getString("test.RegistrationValidationHelper",
                                "aStringProperty", true));
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getString(String, String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the property is missing and the required flag is
     * false.
     * </p>
     * <p>
     * Should have returned null.
     * </p>
     *
     */
    public void testGetString_Missing1() {
        assertEquals("Failed to get the boolean value.", null,
                RegistrationValidationHelper.getString(
                        "test.RegistrationValidationHelper", "Missing", false));
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#getString(String, String) for failure.
     * </p>
     * <p>
     * It tests the case when the property is missing and the required flag is
     * true.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testGetString_Missing2() {
        try {
            RegistrationValidationHelper.getString(
                    "test.RegistrationValidationHelper", "Missing", true);
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for accuracy.
     * </p>
     *
     * <p>
     * It tests the normal case.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory() {
        String namespace = "test.CreateObjectFromObjectFactory";
        BanManager banManager = (BanManager) RegistrationValidationHelper
                .createObjectFromObjectFactory(namespace, "banManagerKey",
                        factory, BanManager.class, true);

        assertNotNull("Failed to create object from ObjectFactory.", banManager);

    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the required flag is false.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory_Null() {
        String namespace = "test.CreateObjectFromObjectFactory.NoLoggerName";
        Log logger = (Log) RegistrationValidationHelper
                .createObjectFromObjectFactory(namespace, "loggerName",
                        factory, Log.class, false);

        assertNull("Failed to create object from ObjectFactory.", logger);

    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the created object is not the instance of the
     * specified type.
     * </p>
     *
     * <p>
     * Should have created BundleInfo correctly.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory_BadType() {

        String namespace = "test.CreateObjectFromObjectFactory";
        try {
            RegistrationValidationHelper.createObjectFromObjectFactory(
                    namespace, "banManagerKey", factory, TeamManager.class,
                    true);
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }

    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the InvalidClassSpecificationException is thrown
     * by ObjectFactory.createObject(String)
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory_BadObjectFactorySpecify() {
        try {
            RegistrationValidationHelper.createObjectFromObjectFactory(
                    "test.CreateObjectFromObjectFactory.BadSpecify",
                    "banManagerKey", factory, BanManager.class, true);
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is unknown.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory_UnknownNamespace() {
        try {
            RegistrationValidationHelper.createObjectFromObjectFactory(
                    "unknownNamespace", "banManagerKey", factory,
                    TeamManager.class, true);
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the property which stores the object key is
     * mising.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory_MisingKeyProperty() {
        String namespace = "test.CreateObjectFromObjectFactory.MisingKeyProperty";
        try {
            RegistrationValidationHelper
                .createObjectFromObjectFactory(namespace, "banManagerKey",
                    factory, BanManager.class, true);
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#createObjectFromObjectFactory(String,
     * String, ObjectFactory, Class, boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the key property value is empty.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCreateObjectFromObjectFactory_EmptyKeyPropertyValue() {
        String namespace = "test.CreateObjectFromObjectFactory.EmptyKeyPropertyValue";
        try {
            RegistrationValidationHelper.createObjectFromObjectFactory(
                    namespace, "EmptyKeyPropertyValue", factory,
                    BanManager.class, true);
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#findResource(ValidationInfo) for
     * accuracy.
     * </p>
     *
     *
     * <p>
     * Should have returned the open resource.
     * </p>
     *
     */
    public void testFindResource() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();
        Resource resource = RegistrationValidationHelper.findResource(
                validationInfo, null);

        assertEquals("Failed to find resource.", 11, resource.getId());
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#findResource(ValidationInfo) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when there is no open resource.
     * </p>
     * <p>
     * Should have returned null.
     * </p>
     *
     */
    public void testFindResource_NoOpenResource() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();
        // Sets the UserId
        validationInfo.getRegistration().setUserId(5);

        Resource resource = RegistrationValidationHelper.findResource(
                validationInfo, null);
        assertNull("Failed to find resource.", resource);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#fillMessage(String, String, Log) for
     * accuracy.
     * </p>
     *
     * <p>
     * Should have returned the generated text document
     * </p>
     *
     */
    public void testFillMessage() {
        String data = "<DATA>\n<HANDLE>happy girl</HANDLE>\n</DATA>";
        String doc = RegistrationValidationHelper.fillMessage(
                "./test_files/fillMessageTemplate.txt", data, null, "methodName");
        assertEquals("Failed to set user correctly.",
                "Hello happy girl, wellcome!", doc);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#fillMessage(String, String, Log) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the template name is null.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testFillMessage_NullTemplateName() {
        String data = "<DATA>\n<HANDLE>happy girl</HANDLE>\n</DATA>";
        try {
            RegistrationValidationHelper.fillMessage(null, data, null, "methodName");
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#fillMessage(String, String, Log) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the template name is empty.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testFillMessage_EmptyTemplateName() {
        String data = "<DATA>\n<HANDLE>happy girl</HANDLE>\n</DATA>";
        try {
            RegistrationValidationHelper.fillMessage("", data, null, "methodName");
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#fillMessage(String, String, Log) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the template file can not be found.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testtFillMessage_NoTemplateFile() {
        String data = "<DATA>\n<HANDLE>happy girl</HANDLE>\n</DATA>";
        try {
            RegistrationValidationHelper.fillMessage(
                    "./test_files/noTemplate.txt", data, null, "methodName");
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#fillMessage(String, String, Log) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the template is bad formed.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testtFillMessage_BadFormedTemplate() {
        String data = "<DATA>\n<HANDLE>happy girl</HANDLE>\n</DATA>";
        try {
            RegistrationValidationHelper.fillMessage(
                    "./test_files/myBadTemplate.txt", data, null, "methodName");
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#fillMessage(String, String, Log) for
     * failure.
     * </p>
     * <p>
     * It tests the case when the template data is bad formed.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testFillMessage_BadFormedTemplate() {
        String data = "<DATA>\n<BAD>happy girl</HANDLE>\n</DATA>";
        try {
            RegistrationValidationHelper.fillMessage(
                    "./test_files/fillMessageTemplate.txt", data, null, "methodName");
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateArrayNotNullOrContainsNull(Object[],
     * String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the object array is null
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateArrayNotNullOrContainsNull_Null() {

        try {
            RegistrationValidationHelper.validateArrayNotNullOrContainsNull(
                    null, "testObjArray");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateArrayNotNullOrContainsNull(Object[],
     * String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the object array contains null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateArrayNotNullOrContainsNull_ContainsNull() {

        Object[] testObjArray = new Object[] {null, new Object(), new Object() };
        try {
            RegistrationValidationHelper.validateArrayNotNullOrContainsNull(
                testObjArray, "testObjArray");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * RegistrationValidationHelper#validateArrayNotNullOrContainsNull(Object[],
     * String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the object array is valid
     * </p>
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateArrayNotNullOrContainsNull() {

        Object[] testObjArray = new Object[] {new Object(), new Object() };
        RegistrationValidationHelper.validateArrayNotNullOrContainsNull(
                testObjArray, "testObjArray");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors is a non-empty array but successful is
     * true.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateErrorMessages_NonEmpty_True() {
        String[] errors = new String[] {"errors1", "errors2" };
        try {
            RegistrationValidationHelper.validateErrorMessages(true, errors);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains null elements in the array.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateErrorMessages_NullElement() {
        String[] errors = new String[] {null, "errors2" };
        try {
            RegistrationValidationHelper.validateErrorMessages(false, errors);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for failure.
     * </p>
     *
     * <p>
     * It tests the case when errors contains empty elements in the array.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateErrorMessages_EmptyElement() {
        String[] errors = new String[] {"  ", "errors2" };
        try {
            RegistrationValidationHelper.validateErrorMessages(false, errors);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for accuracy.
     * </p>
     * <p>
     * It tests the case when errors is null and successful is false.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateErrorMessages_Null_False() {
        RegistrationValidationHelper.validateErrorMessages(false, null);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for accuracy.
     * </p>
     * <p>
     * It tests the case when errors is null and successful is true.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateErrorMessages_Null_True() {
        RegistrationValidationHelper.validateErrorMessages(true, null);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for accuracy.
     * </p>
     * <p>
     * It tests the case when errors is a empty array and successful is false.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateErrorMessages_Empty_False() {
        String[] errors = new String[0];
        RegistrationValidationHelper.validateErrorMessages(false, errors);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for accuracy.
     * </p>
     * <p>
     * It tests the case when errors is a empty array and successful is true.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateErrorMessages_Empty_True() {
        String[] errors = new String[0];
        RegistrationValidationHelper.validateErrorMessages(true, errors);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateErrorMessages(boolean,
     * String[]) for accuracy.
     * </p>
     * <p>
     * It tests the case when errors is a non-empty array and successful is
     * false.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateErrorMessages_NonEmpty_False() {
        String[] errors = new String[] {"errors1", "errors2" };
        RegistrationValidationHelper.validateErrorMessages(false, errors);
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateIsValidationInfo(Object,
     * String) for accuracy.
     * </p>
     * <p>
     * It tests the input object is not null and an instance of ValidationInfo.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testvalidateIsValidationInfo() {
        ValidationInfo obj = TestHelper.createValidationInfoForTest();
        RegistrationValidationHelper.validateIsValidationInfo(obj,
                "validationInfo");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#alidateIsValidationInfo(Object,
     * String) for failure.
     * </p>
     * <p>
     * It tests the input object is null.
     * </p>
     *
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testvalidateIsValidationInfo_Null() {
        try {
            RegistrationValidationHelper.validateIsValidationInfo(null,
                    "validationInfo");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#alidateIsValidationInfo(Object,
     * String) for failure.
     * </p>
     * <p>
     * It tests the input object is not an instance of ValidationInfo.
     * </p>
     *
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testvalidateIsValidationInfo_NotValidationInfo() {
        try {
            RegistrationValidationHelper.validateIsValidationInfo(new Object(),
                "validationInfo");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(long, long) for
     * accuracy.
     * </p>
     * <p>
     * It tests the value is positive.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateNotNegative_PositiveLong() {
        long value = 1;
        RegistrationValidationHelper.validateNotNegative(value, "value");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(int, int) for
     * accuracy.
     * </p>
     * <p>
     * It tests the value is positive.
     * </p>
     *
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateNotNegative_PositiveInt() {
        int value = 1;
        RegistrationValidationHelper.validateNotNegative(value, "value");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(double, double)
     * for failure.
     * </p>
     * <p>
     * It tests the value is negative.
     * </p>
     *
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateNotNegative_NegativeDouble() {
        try {
            double value = -1.2;
            RegistrationValidationHelper.validateNotNegative(value, "value");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(long, long) for
     * failure.
     * </p>
     * <p>
     * It tests the value is negative.
     * </p>
     *
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateNotNegative_NegativeLong() {
        try {
            long value = -1;
            RegistrationValidationHelper.validateNotNegative(value, "value");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(int, int) for
     * failure.
     * </p>
     * <p>
     * It tests the value is negative.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValidateNotNegative_NegativeInt() {
        try {
            int value = -1;
            RegistrationValidationHelper.validateNotNegative(value, "value");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(double, double)
     * for accuracy.
     * </p>
     * <p>
     * It tests the value is zero.
     * </p>
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateNotNegative_ZeroDouble() {
        double value = 0;
        RegistrationValidationHelper.validateNotNegative(value, "value");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(long, long) for
     * accuracy.
     * </p>
     * <p>
     * It tests the value is zero.
     * </p>
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateNotNegative_ZeroLong() {
        long value = 0;
        RegistrationValidationHelper.validateNotNegative(value, "value");
    }

    /**
     * <p>
     * Tests RegistrationValidationHelper#validateNotNegative(int, int) for
     * accuracy.
     * </p>
     * <p>
     * It tests the value is zero.
     * </p>
     *
     * <p>
     * Should have passed the validation.
     * </p>
     *
     */
    public void testValidateNotNegative_ZeroInt() {
        int value = 0;
        RegistrationValidationHelper.validateNotNegative(value, "value");
    }
}
