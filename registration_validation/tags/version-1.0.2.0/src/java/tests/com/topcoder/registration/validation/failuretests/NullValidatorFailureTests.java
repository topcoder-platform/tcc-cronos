/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.validators.util.NullValidator;

import junit.framework.TestCase;
/**
 * Failure Tests for NullValidator class.
 * @author slion
 * @version 1.0
 */
public class NullValidatorFailureTests extends TestCase {
    /**
     * Represents the NullValidator instance.
     */
    private NullValidator validator = null;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        validator = new NullValidator();
        TestHelper.clearConfiguration();
        TestHelper.loadConfiguration("test_files/failure/validconfig.xml");
        validator.setRegistrationValidator(new DataValidationRegistrationValidator());
    }

    /**
     * Teardown the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        validator = null;
        TestHelper.clearConfiguration();
    }

    /**
     * Tests NullValidator(BundleInfo bundleInfo) method with null
     *  bundleInfo,
     * IllegalArgumentException should be thrown.
     */
    public void testNullValidator_NullBundleInfo() {
        try {
            new NullValidator(null);
            fail("testNullValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNullValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests setRegistrationValidator(DataValidationRegistrationValidator
     * dataValidationRegistrationValidator) method with null 
     *  dataValidationRegistrationValidator,
     * IllegalArgumentException should be thrown.
     */
    public void testSetRegistrationValidator_NullDataValidationRegistrationValidator() {
        try {
            validator.setRegistrationValidator(null);
            fail("testSetRegistrationValidator_NullDataValidationRegistrationValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testSetRegistrationValidator_NullDataValidationRegistrationValidator.");
        }
    }
//
//    /**
//     * Tests valid(Object obj) method with null Object obj,
//     * IllegalArgumentException should be thrown.
//     */
//    public void testValid_NullObj() {
//        try {
//            validator.valid(null);
//            fail("testValid_NullObj is failure.");
//        } catch (IllegalArgumentException iae) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testValid_NullObj.");
//        }
//    }
//
//    /**
//     * Tests valid(Object obj) method with invalid Object obj,
//     * IllegalArgumentException should be thrown.
//     */
//    public void testValid_InvalidObj() {
//        try {
//            validator.valid("");
//            fail("testValid_InvalidObj is failure.");
//        } catch (IllegalArgumentException iae) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testValid_InvalidObj.");
//        }
//    }

//    /**
//     * Tests getMessage(Object obj) method with null Object obj,
//     * IllegalArgumentException should be thrown.
//     */
//    public void testGetMessage_NullObj() {
//        try {
//            validator.getMessage(null);
//            fail("testGetMessage_NullObj is failure.");
//        } catch (IllegalArgumentException iae) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testGetMessage_NullObj.");
//        }
//    }

    /**
     * Tests getMessage(Object obj) method with invalid Object obj,
     * IllegalArgumentException should be thrown.
     */
    public void testGetMessage_InvalidObj() {
        try {
            validator.getMessage("");
            fail("testGetMessage_InvalidObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_InvalidObj.");
        }
    }

//    /**
//     * Tests getMessages(Object object) method with null Object object,
//     * IllegalArgumentException should be thrown.
//     */
//    public void testGetMessages_NullObject() {
//        try {
//            validator.getMessages(null);
//            fail("testGetMessages_NullObject is failure.");
//        } catch (IllegalArgumentException iae) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testGetMessages_NullObject.");
//        }
//    }

    /**
     * Tests getMessages(Object object) method with invalid Object object,
     * IllegalArgumentException should be thrown.
     */
    public void testGetMessages_InvalidObject() {
        try {
            validator.getMessages(new Object());
            fail("testGetMessages_InvalidObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessages_InvalidObject.");
        }
    }

//    /**
//     * Tests getAllMessages(Object object) method with null Object object,
//     * IllegalArgumentException should be thrown.
//     */
//    public void testGetAllMessages_NullObject() {
//        try {
//            validator.getAllMessages(null);
//            fail("testGetAllMessages_NullObject is failure.");
//        } catch (IllegalArgumentException iae) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testGetAllMessages_NullObject.");
//        }
//    }

    /**
     * Tests getAllMessages(Object object) method with invalid Object object,
     * IllegalArgumentException should be thrown.
     */
    public void testGetAllMessages_InvalidObject() {
        try {
            validator.getAllMessages(new Object());
            fail("testGetAllMessages_InvalidObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages_InvalidObject.");
        }
    }
//
//    /**
//     * Tests getAllMessages(Object object, int messageLimit) method with null Object object,
//     * IllegalArgumentException should be thrown.
//     */
//    public void testGetAllMessages2_NullObject() {
//        try {
//            validator.getAllMessages(null, 1);
//            fail("testGetAllMessages2_NullObject is failure.");
//        } catch (IllegalArgumentException iae) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testGetAllMessages2_NullObject.");
//        }
//    }   

    /**
     * Tests getAllMessages(Object object, int messageLimit) method with invalid Object object,
     * IllegalArgumentException should be thrown.
     */
    public void testGetAllMessages2_InvalidObject() {
        try {
            validator.getAllMessages(new Object(), 1);
            fail("testGetAllMessages2_InvalidObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages2_InvalidObject.");
        }
    }
}