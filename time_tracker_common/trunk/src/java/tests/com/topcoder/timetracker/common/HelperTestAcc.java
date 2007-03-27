/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Date;

import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>Accuracy tests for <code>Helper</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class HelperTestAcc extends BaseBaseTestCase {

    /**
     * <p>
     * The only constructor is with modifier 'private', <code>IllegalAccessException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCtor() throws Exception {
        try {
            Class.forName("com.topcoder.timetracker.common.Helper").newInstance();
            fail("IllegalAccessException is expected");
        } catch (IllegalAccessException e) {
            // good
        }
    }

    /**
     * <p>
     * Test method <code>validateNotNull()</code>. Given object is not null,
     * <code>IllegalAccessException</code> is not expected.
     * </p>
     */
    public void testValidateNotNull1() {
        Helper.validateNotNull(new Object(), "test object");
    }
    /**
     * <p>
     * Test accuracy of method <code>validateNotNull()</code>.
     * </p>
     *
     * <p>
     * Object is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateNotNull2() {
        try {
            Helper.validateNotNull(null, "test object");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test object should not be null.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateString()</code>.
     * </p>
     *
     * <p>
     * String is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateString1() {
        try {
            Helper.validateString(null, "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test string should not be null.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validateString()</code>.
     * </p>
     *
     * <p>
     * String is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateString2() {
        try {
            Helper.validateString(" ", "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test string should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateStringWithMaxLength1() {
        try {
            Helper.validateStringWithMaxLength(" ", "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test string should not be empty (trimmed).");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validateStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateStringWithMaxLength2() {
        try {
            Helper.validateStringWithMaxLength(null, "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test string should not be null.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is with length &gt; 64, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateStringWithMaxLength3() {
        try {
            Helper.validateStringWithMaxLength(this.getStringWithLength65(), "test string");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test string should not be with length greater than 64");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>checkStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is with length &gt; 64, false is expected.
     * </p>
     */
    public void testCheckStringWithMaxLength1() {
        assertFalse(Helper.checkStringWithMaxLength(this.getStringWithLength65()));
    }
    /**
     * <p>
     * Test accuracy of method <code>checkStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is null, false is expected.
     * </p>
     */
    public void testCheckStringWithMaxLength2() {
        assertFalse(Helper.checkStringWithMaxLength(null));
    }

    /**
     * <p>
     * Test accuracy of method <code>checkStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is empty, false is expected.
     * </p>
     */
    public void testCheckStringWithMaxLength3() {
        assertFalse(Helper.checkStringWithMaxLength(""));
    }
    /**
     * <p>
     * Test accuracy of method <code>checkStringWithMaxLength()</code>.
     * </p>
     *
     * <p>
     * String is valid, true is expected.
     * </p>
     */
    public void testCheckStringWithMaxLength4() {
        assertTrue(Helper.checkStringWithMaxLength("string"));
    }
    /**
     * <p>
     * Test accuracy of method <code>checkPositive()</code>.
     * </p>
     *
     * <p>
     * id is 1, true is expected.
     * </p>
     */
    public void testCheckPositive1() {
        assertTrue(Helper.checkPositive(1));
    }
    /**
     * <p>
     * Test accuracy of method <code>checkPositive()</code>.
     * </p>
     *
     * <p>
     * id is -1, false is expected.
     * </p>
     */
    public void testCheckPositive2() {
        assertFalse(Helper.checkPositive(-1));
    }

    /**
     * <p>
     * Test accuracy of method <code>checkPositive()</code>.
     * </p>
     *
     * <p>
     * id is 0, false is expected.
     * </p>
     */
    public void testCheckPositive3() {
        assertFalse(Helper.checkPositive(0));
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePositive()</code>.
     * </p>
     *
     * <p>
     * id is -1, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePositive1() {
        try {
            Helper.validatePositive(-1, "test id");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test id should be positive, but is '-1'.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validatePositive()</code>.
     * </p>
     *
     * <p>
     * id is 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePositive2() {
        try {
            Helper.validatePositive(0, "test id");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test id should be positive, but is '0'.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validateIdsArray()</code>.
     * </p>
     *
     * <p>
     * ids array is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateIdsArray1() {
        try {
            Helper.validateIdsArray(null, "test ids");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test ids should not be null.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateIdsArray()</code>.
     * </p>
     *
     * <p>
     * ids array contains non-positive value,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateIdsArray2() {
        try {
            Helper.validateIdsArray(new long[]{1, 2, -3}, "test ids");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test ids should be positive, but is '-3'.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateIdsArray()</code>.
     * </p>
     *
     * <p>
     * ids array is empty, false is expected.
     * </p>
     */
    public void testValidateIdsArray3() {
        assertFalse(Helper.validateIdsArray(new long[0], "test ids"));
    }
    /**
     * <p>
     * Test accuracy of method <code>validateIdsArray()</code>.
     * </p>
     *
     * <p>
     * ids array is not empty, true is expected.
     * </p>
     */
    public void testValidateIdsArray4() {
        assertTrue(Helper.validateIdsArray(new long[]{1}, "test ids"));
    }
    /**
     * <p>
     * Test accuracy of method <code>validateRecentDays()</code>.
     * </p>
     *
     * <p>
     * Recent days is 0, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateRecentDays1() {
        try {
            Helper.validateRecentDays(0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The recent days must be positive or equal -1, but is '0'.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validateRecentDays()</code>.
     * </p>
     *
     * <p>
     * Recent days is -2, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateRecentDays2() {
        try {
            Helper.validateRecentDays(-2);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "The recent days must be positive or equal -1, but is '-2'.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateRecentDays()</code>.
     * </p>
     *
     * <p>
     * Recent days is -1, <code>IllegalArgumentException</code> is not expected.
     * </p>
     */
    public void testValidateRecentDays3() {
        Helper.validateRecentDays(-1);
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue1() throws Exception {
        try {
            Helper.getPropertyValue(null, null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "namespace should not be null.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Namespace is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue2() throws Exception {
        try {
            Helper.getPropertyValue("", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "namespace should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property name is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue3() throws Exception {
        try {
            Helper.getPropertyValue("namespace", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "propertyName should not be null.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property name is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue4() throws Exception {
        try {
            Helper.getPropertyValue("namespace", "");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "propertyName should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Namespace is unknown, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue5() throws Exception {
        try {
            Helper.getPropertyValue("Unknown", "property");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The namespace 'Unknown' is unknown.") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property is missing, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue6() throws Exception {
        try {
            Helper.getPropertyValue("SimpleCommonManager_Error_1", "of_namespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertEquals(e.getMessage(), "Missed property 'of_namespace'.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Property is empty, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue7() throws Exception {
        try {
            Helper.getPropertyValue("SimpleCommonManager_Error_2", "of_namespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertEquals(e.getMessage(), "The value for property 'of_namespace' is empty.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Within namespace "SimpleCommonManager_Error_2", the value of "payment_term_dao_key" should be
     * "PaymentTermDAOKey", the value of "recent_days" should be "3".
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetPropertyValue8() throws Exception {
        assertEquals("PaymentTermDAOKey",
                Helper.getPropertyValue("SimpleCommonManager_Error_2", "payment_term_dao_key"));
        assertEquals("3", Helper.getPropertyValue("SimpleCommonManager_Error_2", "recent_days"));
    }

    /**
     * <p>
     * Test accuracy of method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * Namespace is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory1() throws Exception {
        try {
            Helper.getObjectFactory(null, "");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "namespace should not be null.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * Namespace is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory2() throws Exception {
        try {
            Helper.getObjectFactory("", "");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "namespace should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * propertyName is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory3() throws Exception {
        try {
            Helper.getObjectFactory("a", null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "propertyName should not be null.");
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * propertyName is empty, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory4() throws Exception {
        try {
            Helper.getObjectFactory("a", "");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "propertyName should not be empty (trimmed).");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getObjectFactory()</code>.
     * </p>
     *
     * <p>
     * Namespace is unknown, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory5() throws Exception {
        try {
            Helper.getObjectFactory("Unknown", "a");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The namespace 'Unknown' is unknown.") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory property is missing, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory6() throws Exception {
        try {
            Helper.getObjectFactory("SimpleCommonManager_Error_1", "of_namespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertEquals(e.getMessage(), "Missed property 'of_namespace'.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory property is empty, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory7() throws Exception {
        try {
            Helper.getObjectFactory("SimpleCommonManager_Error_2", "of_namespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertEquals(e.getMessage(), "The value for property 'of_namespace' is empty.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory namespace is unknown, <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory8() throws Exception {
        try {
            Helper.getObjectFactory("DatabasePaymentTermDAO_Error_3", "of_namespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof SpecificationConfigurationException);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * <p>
     * Object factory namespace contains invalid configuration,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory9() throws Exception {
        try {
            Helper.getObjectFactory("DatabasePaymentTermDAO_Error_4", "of_namespace");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof IllegalReferenceException);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>getPropertyValue()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetObjectFactory10() throws Exception {
        Helper.getObjectFactory("DatabasePaymentTermDAO", "of_namespace");
    }
    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code> is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm1() {
        try {
            Helper.validatePaymentTerm(null, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "PaymentTerm should not be null.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s term is not positive,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm2() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setTerm(-2);
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The term of PaymentTerm should be positive") >= 0);
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s description is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm3() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setDescription(null);
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The description of PaymentTerm should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s description is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm4() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setDescription("");
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The description of PaymentTerm should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s description is with length greater than 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm5() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setDescription(this.getStringWithLength65());
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf(
                    "The description of PaymentTerm should not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s creation user is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm6() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setCreationUser(null);
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The creation user of PaymentTerm should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s creation user is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm7() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setCreationUser("");
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The creation user of PaymentTerm should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s creation user is with length greater than 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm8() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setCreationUser(this.getStringWithLength65());
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf(
                    "The creation user of PaymentTerm should not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s modification user is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm9() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setModificationUser(null);
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The modification user of PaymentTerm should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s modification user is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm10() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setModificationUser("");
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The modification user of PaymentTerm should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s modification user is with length greater than 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm11() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setModificationUser(this.getStringWithLength65());
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", false, true);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf(
                    "The modification user of PaymentTerm should not be with length greater than 64") >= 0);
        }
    }
    /**
     * <p>
     * Test accuracy of method <code>validatePaymentTerm()</code>.
     * </p>
     *
     * <p>
     * <code>PaymentTerm</code>'s id is not positive,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidatePaymentTerm12() {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setId(-2);
            Helper.validatePaymentTerm(paymentTerm, "PaymentTerm", true, false);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("The id of PaymentTerm should be positive") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateNotExceed()</code>.
     * </p>
     *
     * <p>
     * date is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateNotExceed1() {
        try {
            Helper.validateNotExceed(null, null, null, "test date", "");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test date should not be null.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateNotExceed()</code>.
     * </p>
     *
     * <p>
     * date is null, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateNotExceed2() {
        try {
            Helper.validateNotExceed(new Date(), null, null, "", "test date");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "test date should not be null.");
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateNotExceed()</code>.
     * </p>
     *
     * <p>
     * date1 exceeds date2, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateNotExceed3() {
        try {
            Date date2 = new Date();
            Date date1 = this.getDateWithOneDayForward(date2);
            Helper.validateNotExceed(date1, date2, null, "date1", "date2");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("date1 must not exceed date2") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateNotExceed()</code>.
     * </p>
     *
     * <p>
     * date1 does not equal date2, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateNotExceed4() {
        try {
            Date date2 = new Date();
            Date date1 = this.getDateWithOneDayBackward(date2);
            Helper.validateNotExceed(date1, date2, Boolean.TRUE, "date1", "date2");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("date1 must equal date2") >= 0);
        }
    }

    /**
     * <p>
     * Test accuracy of method <code>validateNotExceed()</code>.
     * </p>
     *
     * <p>
     * date1 equals date2, <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    public void testValidateNotExceed5() {
        try {
            Date date2 = new Date();
            Date date1 = date2;
            Helper.validateNotExceed(date1, date2, Boolean.FALSE, "date1", "date2");
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf("date1 must not equal date2") >= 0);
        }
    }
}
