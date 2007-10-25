/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import junit.framework.TestCase;

import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;

import com.topcoder.registration.validation.validators.util.OrValidator;
/**
 * Failure Tests for OrValidator class.
 * @author slion
 * @version 1.0
 */
public class OrValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/OV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/OV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/OV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/OV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/OV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/OV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/OV_invalidconfig7.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/OV_invalidconfig8.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG9 = "test_files/failure/OV_invalidconfig9.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG10 = "test_files/failure/OV_invalidconfig10.xml";
    
    /**
     * Represents the OrValidator instance.
     */
    private OrValidator validator = null;

    /**
     * Represents the configurable validators.
     */
    private ConfigurableValidator[] validators = null;
    
    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        validators = new ConfigurableValidator[]{new MockAbstractConfigurableValidator("test")};
        validator = new OrValidator(validators, false);
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
        validators = null;
        TestHelper.clearConfiguration();
    }

    /**
     * Tests OrValidator(ConfigurableValidator[] validators, boolean shortCircuited) method with
     * null ConfigurableValidator[] validators,
     * IllegalArgumentException should be thrown.
     */
    public void testOrValidator_NullValidators() {
        try {
            new OrValidator(null, false);
            fail("testOrValidator_NullValidators is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_NullValidators.");
        }
    }

    /**
     * Tests OrValidator(ConfigurableValidator[] validators, boolean shortCircuited) method with
     * null validator in array,
     * IllegalArgumentException should be thrown.
     */
    public void testOrValidator_NullValidator() {
        try {
            new OrValidator(new ConfigurableValidator[]{null}, false);
            fail("testOrValidator_NullValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_NullValidator.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testOrValidator_NullNamespace() {
        try {
            new OrValidator(null);
            fail("testOrValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_NullNamespace.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testOrValidator_EmptyNamespace() {
        try {
            new OrValidator("  ");
            fail("testOrValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_UnknownNS() {
        try {
            new OrValidator("fail.namespace");
            fail("testOrValidator_UnknownNS is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_UnknownNS.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig2.");
        }
    }

//    /**
//     * Tests OrValidator(String namespace) method with invalid configuration,
//     * RegistrationValidationConfigurationException should be thrown.
//     */
//    public void testOrValidator_InvalidConfig3() {
//        try {
//            TestHelper.loadConfiguration(INVALID_CONFIG3);
//            new OrValidator("OrValidator");
//            fail("testOrValidator_InvalidConfig3 is failure.");
//        } catch (RegistrationValidationConfigurationException rvce) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testOrValidator_InvalidConfig3.");
//        }
//    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig8.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig9() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG9);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig9 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig9.");
        }
    }

    /**
     * Tests OrValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testOrValidator_InvalidConfig10() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG10);
            new OrValidator("OrValidator");
            fail("testOrValidator_InvalidConfig10 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testOrValidator_InvalidConfig10.");
        }
    }

    /**
     * Tests setRegistrationValidator(DataValidationRegistrationValidator DataValidationRegistrationValidator)
     * method with null DataValidationRegistrationValidator DataValidationRegistrationValidator,
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
            validator.valid(new Long(1));
            fail("testValid_InvalidObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValid_InvalidObj.");
        }
    }

    /**
     * Tests valid(Object object) method with ValidationProcessingException
     * thrown.
     */
    public void testValid_ValidationProcessingException() {
        try {
            ((MockAbstractConfigurableValidator)validators[0]).setThrowException(true);
            validator.valid(new ValidationInfo());
            fail("testValid_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValid_ValidationProcessingException.");
        }
    }
    /**
     * Tests getMessage(Object obj) method with null Object obj,
     * IllegalArgumentException should be thrown.
     */
    public void testGetMessage_NullObj() {
        try {
            validator.getMessage(null);
            fail("testGetMessage_NullObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_NullObj.");
        }
    }

    /**
     * Tests getMessage(Object obj) method with invalid Object obj,
     * IllegalArgumentException should be thrown.
     */
    public void testGetMessage_InvalidObj() {
        try {
            validator.getMessage("faile");
            fail("testGetMessage_InvalidObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_InvalidObj.");
        }
    }

    /**
     * Tests getMessage(Object object) method with ValidationProcessingException
     * thrown.
     */
    public void testGetMessage_ValidationProcessingException() {
        try {
            ((MockAbstractConfigurableValidator)validators[0]).setThrowException(true);
            validator.getMessage(new ValidationInfo());
            fail("testGetMessage_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_ValidationProcessingException.");
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
     * Tests getMessages(Object object) method with ValidationProcessingException
     * thrown.
     */
    public void testGetMessages_ValidationProcessingException() {
        try {
            ((MockAbstractConfigurableValidator)validators[0]).setThrowException(true);
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
     * Tests getAllMessages(Object object) method with ValidationProcessingException
     * thrown.
     */
    public void testGetAllMessages_ValidationProcessingException() {
        try {
            ((MockAbstractConfigurableValidator)validators[0]).setThrowException(true);
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
     * Tests getAllMessages(Object object, int messageLimit) method with ValidationProcessingException
     * thrown.
     */
    public void testGetAllMessages2_ValidationProcessingException() {
        try {
            ((MockAbstractConfigurableValidator)validators[0]).setThrowException(true);
            validator.getAllMessages(new ValidationInfo(), 1);
            fail("testGetAllMessages2_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages2_ValidationProcessingException.");
        }
    }
}