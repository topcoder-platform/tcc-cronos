/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import java.util.Date;

import javax.ejb.CreateException;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * <p>Accuracy tests for <code>Helper</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class HelperTestAcc extends BaseTestCase {
    /**
     * <p>Test method for validatePositiveWithIAE(long, String). Given id is -1,
     * IllegalArgumentException expected.</p>
     */
    public void testValidatePositiveWithIAE() {
        try {
            Helper.validatePositiveWithIAE(-1, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validatePositiveWithIPE(long, String). Given id is -1,
     * InvalidPropertyException expected</p>
     */
    public void testValidatePositiveWithIPE() {
        try {
            Helper.validatePositiveWithIPE(-1, "test");
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * <p>Test validateNotNullWithIAE(Object, String). Given object is null,
     * IllegalArgumentException expected.</p>
     */
    public void testValidateNotNullWithIAE() {
        try {
            Helper.validateNotNullWithIAE(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validateNotNullWithIPE(Object, String). Given object is null,
     * InvalidPropertyException expected.</p>
     */
    public void testValidateNotNullWithIPE() {
        try {
            Helper.validateNotNullWithIPE(null, "test");
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * <p>Test validateIdsArrayWithIAE(long[], String). Given array is null,
     * IllegalArgumentException expected.</p>
     */
    public void testValidateIdsArrayWithIAE1() {
        try {
            Helper.validateIdsArrayWithIAE(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validateIdsArrayWithIAE(long[], String). Given array contains negative,
     * IllegalArgumentException expected.</p>
     */
    public void testValidateIdsArrayWithIAE2() {
        try {
            Helper.validateIdsArrayWithIAE(new long[]{-1}, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validateObjectsArrayWithIAE(Object[], String). Given array is null,
     * IllegalArgumentException expected.</p>
     */
    public void testValidateObjectsArrayWithIAE1() {
        try {
            Helper.validateObjectsArrayWithIAE(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validateObjectsArrayWithIAE(Object[], String). Given array contains null
     * member, IllegalArgumentException expected.</p>
     */
    public void testValidateObjectsArrayWithIAE2() {
        try {
            Helper.validateObjectsArrayWithIAE(new Object[]{null}, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validateDatesRange(Date, Date, String). Both ends are null,
     * IllegalArgumentException expected.</p>
     */
    public void testValidateDatesRange() {
        try {
            Helper.validateDatesRange(null, null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>Test validateNotExceed(Date, Date, String, String). After is null,
     * InvalidPropertyException expected.</p>
     */
    public void testValidateNotExceed1() {
        try {
            Helper.validateNotExceed(new Date(), null, "", "");
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * <p>Test validateNotExceed(Date, Date, String, String). Before date exceeds after date,
     * InvalidPropertyException expected.</p>
     */
    public void testValidateNotExceed2() {
        try {
            Date current = new Date();
            Helper.validateNotExceed(current, new Date(current.getTime() - 1000), "", "");
            fail("InvalidPropertyException expected");
        } catch (InvalidPropertyException e) {
            //good
        }
    }

    /**
     * <p>
     * Test validateStringWithIAE(String, String).
     * Given string is null, IllegalArgumentException expected.
     * </p>
     */
    public void testValidateStringWithIAE1() {
        try {
            Helper.validateStringWithIAE(null, "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }
    /**
     * <p>
     * Test validateStringWithIAE(String, String).
     * Given string is empty, IllegalArgumentException expected.
     * </p>
     */
    public void testValidateStringWithIAE2() {
        try {
            Helper.validateStringWithIAE(" ", "test");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getPropertyValue(String, String, boolean).
     * Given namespace is unknown, ConfigurationException expected.
     * </p>
     */
    public void testGetPropertyValue1() {
        try {
            Helper.getPropertyValue("Unknown", "property", true);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getPropertyValue(String, String, boolean).
     * Given property is missing, ConfigurationException expected.
     * </p>
     */
    public void testGetPropertyValue2() {
        try {
            Helper.getPropertyValue("ObjectFactoryNS_Error_1", "required", true);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getPropertyValue(String, String, boolean).
     * Given property is missing, but it's not required,
     * ConfigurationException is not expected.
     * </p>
     */
    public void testGetPropertyValue3() {
        try {
            String value =
                Helper.getPropertyValue("ObjectFactoryNS_Error_1", "not required", false);
            assertNull("property is missed", value);
        } catch (ConfigurationException e) {
            fail("ConfigurationException is not expected");
        }
    }

    /**
     * <p>
     * Test getObjectFactory(String).
     * Namespace is null, ConfigurationException expected.
     * </p>
     */
    public void testGetObjectFactory1() {
        try {
            Helper.getObjectFactory(null);
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getObjectFactory(String).
     * Namespace is empty, ConfigurationException expected.
     * </p>
     */
    public void testGetObjectFactory2() {
        try {
            Helper.getObjectFactory(" ");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getObjectFactory(String).
     * Namespace is configured improper, ConfigurationException expected.
     * </p>
     */
    public void testGetObjectFactory3() {
        try {
            Helper.getObjectFactory("ObjectFactoryNS_Error_0");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getObjectFactory(String).
     * Namespace is unknown, ConfigurationException expected.
     * </p>
     */
    public void testGetObjectFactory4() {
        try {
            Helper.getObjectFactory("unknown");
            fail("ConfigurationException expected");
        } catch (ConfigurationException e) {
            //good
        }
    }
    /**
     * <p>
     * Test getDAO(boolean).
     * Required JNDI binding is not found, CreateException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO1() throws Exception {
        try {
            new InitialContext().unbind("java:comp/env/SpecificationNamespace");
        } catch (NamingException e) {
            //
        }
        try {
            Helper.getDAO(true);
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getDAO(boolean).
     * Required JNDI is binded with an unknown namespace for Object Factory, CreateException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO2() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "unknown");
        } catch (NamingException e) {
            //
        }
        try {
            Helper.getDAO(true);
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getDAO(boolean).
     * Required JNDI is binded with an namespace within which Object Factory points to an unknown namspace
     * for AddressDAO. CreateException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO3() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_1");
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        } catch (NamingException e) {
            //
        }
        try {
            Helper.getDAO(true);
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }

    /**
     * <p>
     * Test getDAO(boolean).
     * Requried JNDI is binded with an namespace within which Object Factory points to an invalid class type
     * for AddressDAO. CreateException expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetDAO4() throws Exception {
        try {
            this.bind("java:comp/env/SpecificationNamespace", "ObjectFactoryNS_Error_2");
            this.bind("java:comp/env/AddressDAOKey", "AddressDAO");
        } catch (NamingException e) {
            //
        }
        try {
            Helper.getDAO(true);
            fail("CreateException expected");
        } catch (CreateException e) {
            //good
        }
    }
}
