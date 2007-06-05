/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.util.datavalidator.BundleInfo;

import junit.framework.TestCase;
/**
 * Tests for AbstractConfigurableValidator class.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractConfigurableValidatorFailureTests extends TestCase {
    /**
     * Represents the BundleInfo instance for testing.
     */
    private BundleInfo bundle = null;

    /**
     * Represents the MockAbstractConfigurableValidator instance for testing.
     */
    private MockAbstractConfigurableValidator validator = null;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bundle = new BundleInfo();
        bundle.setBundle("sun.security.util.Resources_zh_CN");
        bundle.setDefaultMessage("some message.");
        bundle.setMessageKey("key");
        validator = new MockAbstractConfigurableValidator(bundle);
        TestHelper.clearConfiguration();
        TestHelper.loadConfiguration("test_files/failure/validconfig.xml");
        validator.setRegistrationValidator(new DataValidationRegistrationValidator());
    }

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        bundle = null;
        validator = null;
        TestHelper.clearConfiguration();
    }

    /**
     * Tests AbstractConfigurableValidator(BundleInfo bundle)
     * method with null bundle,
     * IllegalArgumentException should be thrown.
     */
    public void testCtor_NullBundle() {
        try {
            new MockAbstractConfigurableValidator((BundleInfo) null);
            fail("testCtor_NullBundle is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCtor_NullBundle.");
        }
    }

    /**
     * Tests AbstractConfigurableValidator(BundleInfo bundle)
     * method with invalid bundle,
     * IllegalArgumentException should be thrown.
     */
    public void testCtor_InvalidBundle() {
        try {
            new MockAbstractConfigurableValidator(new BundleInfo());
            fail("testCtor_InvalidBundle is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCtor_InvalidBundle.");
        }
    }

    /**
     * Tests AbstractConfigurableValidator(String validationMessage)
     * method with null message,
     * IllegalArgumentException should be thrown.
     */
    public void testCtor_NullMessage() {
        try {
            new MockAbstractConfigurableValidator((String) null);
            fail("testCtor_NullMessage is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCtor_NullMessage.");
        }
    }

    /**
     * Tests AbstractConfigurableValidator(String validationMessage)
     * method with empty message,
     * IllegalArgumentException should be thrown.
     */
    public void testCtor_EmptyMessage() {
        try {
            new MockAbstractConfigurableValidator("   ");
            fail("testCtor_EmptyMessage is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testCtor_EmptyMessage.");
        }
    }

    /**
     * Tests setRegistrationValidator(DataValidationRegistrationValidator dataValidationRegistrationValidator)
     * method with null dataValidationRegistrationValidator,
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

    /**
     * Tests valid(Object obj) method with null Object obj,
     * IllegalArgumentException should be thrown.
     */
    public void testValid_NullObj() {
        try {
            validator.valid(null);
            fail("testValid_NullObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValid_NullObj.");
        }
    }

    /**
     * Tests valid(Object obj) method with invalid Object obj,
     * IllegalArgumentException should be thrown.
     */
    public void testValid_InvalidObj() {
        try {
            validator.valid(new Object());
            fail("testValid_InvalidObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValid_InvalidObj.");
        }
    }

    /**
     * Tests valid(Object obj) method with ValidationProcessingException thrown,
     * ValidationProcessingException should be thrown.
     */
    public void testValid_ValidationProcessingException() {
        try {
            validator.setThrowException(true);
            validator.valid(new ValidationInfo());
            fail("testValid_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValid_ValidationProcessingException.");
        }
    }

    /**
     * Tests getMessages(Object object) method with null Object object,
     * IllegalArgumentException should be thrown.
     */
    public void testGetMessages_NullObject() {
        try {
            validator.getMessages(null);
            fail("testGetMessages_NullObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessages_NullObject.");
        }
    }

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

    /**
     * Tests getMessages(Object object) method with ValidationProcessingException thrown,
     * ValidationProcessingException should be thrown.
     */
    public void testGetMessages_ValidationProcessingException() {
        try {
            validator.setThrowException(true);
            validator.getMessages(new ValidationInfo());
            fail("testGetMessages_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessages_ValidationProcessingException.");
        }
    }

    /**
     * Tests getAllMessages(Object object) method with null Object object,
     * IllegalArgumentException should be thrown.
     */
    public void testGetAllMessages_NullObject() {
        try {
            validator.getAllMessages(null);
            fail("testGetAllMessages_NullObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages_NullObject.");
        }
    }

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

    /**
     * Tests getAllMessages(Object object) method with ValidationProcessingException thrown,
     * ValidationProcessingException should be thrown.
     */
    public void testGetAllMessages_ValidationProcessingException() {
        try {
            validator.setThrowException(true);
            validator.getAllMessages(new ValidationInfo());
            fail("testGetAllMessages_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages_ValidationProcessingException.");
        }
    }

    /**
     * Tests getAllMessages(Object object, int messageLimit) method with null Object object,
     * IllegalArgumentException should be thrown.
     */
    public void testGetAllMessages2_NullObject() {
        try {
            validator.getAllMessages(null, 1);
            fail("testGetAllMessages2_NullObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages2_NullObject.");
        }
    }

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

    /**
     * Tests getAllMessages(Object object, int messageLimit) method with ValidationProcessingException thrown,
     * ValidationProcessingException should be thrown.
     */
    public void testGetAllMessages2_ValidationProcessingException() {
        try {
            validator.setThrowException(true);
            validator.getAllMessages(new ValidationInfo(), 1);
            fail("testGetAllMessages2_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages2_ValidationProcessingException.");
        }
    }
}