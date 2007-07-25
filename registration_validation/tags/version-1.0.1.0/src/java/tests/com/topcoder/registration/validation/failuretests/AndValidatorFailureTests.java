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
import com.topcoder.registration.validation.validators.util.AndValidator;
/**
 * Failure Tests for AndValidator class.
 * @author slion
 * @version 1.0
 */
public class AndValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/AV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/AV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/AV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/AV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/AV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/AV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/AV_invalidconfig7.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/AV_invalidconfig8.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG9 = "test_files/failure/AV_invalidconfig9.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG10 = "test_files/failure/AV_invalidconfig10.xml";
    
    /**
     * Represents the AndValidator instance.
     */
    private AndValidator validator = null;

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
        validator = new AndValidator(validators, false);
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
     * Tests AndValidator(ConfigurableValidator[] validators, boolean shortCircuited) method with
     * null ConfigurableValidator[] validators,
     * IllegalArgumentException should be thrown.
     */
    public void testAndValidator_NullValidators() {
        try {
            new AndValidator(null, false);
            fail("testAndValidator_NullValidators is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_NullValidators.");
        }
    }

    /**
     * Tests AndValidator(ConfigurableValidator[] validators, boolean shortCircuited) method with
     * null validator in array,
     * IllegalArgumentException should be thrown.
     */
    public void testAndValidator_NullValidator() {
        try {
            new AndValidator(new ConfigurableValidator[]{null}, false);
            fail("testAndValidator_NullValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_NullValidator.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testAndValidator_NullNamespace() {
        try {
            new AndValidator(null);
            fail("testAndValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_NullNamespace.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testAndValidator_EmptyNamespace() {
        try {
            new AndValidator("  ");
            fail("testAndValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_UnknownNS() {
        try {
            new AndValidator("fail.namespace");
            fail("testAndValidator_UnknownNS is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_UnknownNS.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig2.");
        }
    }

//    /**
//     * Tests AndValidator(String namespace) method with invalid configuration,
//     * RegistrationValidationConfigurationException should be thrown.
//     */
//    public void testAndValidator_InvalidConfig3() {
//        try {
//            TestHelper.loadConfiguration(INVALID_CONFIG3);
//            new AndValidator("AndValidator");
//            fail("testAndValidator_InvalidConfig3 is failure.");
//        } catch (RegistrationValidationConfigurationException rvce) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testAndValidator_InvalidConfig3.");
//        }
//    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig8.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig9() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG9);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig9 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig9.");
        }
    }

    /**
     * Tests AndValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testAndValidator_InvalidConfig10() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG10);
            new AndValidator("AndValidator");
            fail("testAndValidator_InvalidConfig10 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testAndValidator_InvalidConfig10.");
        }
    }

    /**
     * Tests setRegistrationValidator(DataValidationRegistrationValidator dataValidationRegistrationValidator)
     * method with null DataValidationRegistrationValidator dataValidationRegistrationValidator,
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