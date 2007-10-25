/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.util.NotValidator;
import com.topcoder.util.datavalidator.BundleInfo;

import junit.framework.TestCase;
/**
 * Failure Tests for NotValidator class.
 * @author slion
 * @version 1.0
 */
public class NotValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/NV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/NV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/NV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/NV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/NV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/NV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/NV_invalidconfig7.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/NV_invalidconfig8.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG9 = "test_files/failure/NV_invalidconfig9.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG10 = "test_files/failure/NV_invalidconfig10.xml";
    
    /**
     * Represents the BundleInfo instance for testing.
     */
    private BundleInfo bundle = null;

    /**
     * Represents the NotValidator instance.
     */
    private NotValidator validator = null;

    /**
     * Represents the ConfigurableValiator instance.
     */
    private ConfigurableValidator configValidator;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bundle = new BundleInfo();
        bundle.setBundle("sun.security.util.Resources_zh_CN");
        bundle.setDefaultMessage("some message.");
        bundle.setMessageKey("key");
        configValidator = new MockAbstractConfigurableValidator("test");
        validator = new NotValidator(configValidator, bundle);
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
        bundle = null;
        configValidator = null;
        TestHelper.clearConfiguration();
    }

    /**
     * Tests NotValidator(ConfigurableValidator validator, BundleInfo bundleInfo) method with null
     * ConfigurableValidator validator,
     * IllegalArgumentException should be thrown.
     */
    public void testNotValidator_NullValidator() {
        try {
            new NotValidator(null, bundle);
            fail("testNotValidator_NullValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_NullValidator.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testNotValidator_NullNamespace() {
        try {
            new NotValidator(null);
            fail("testNotValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_NullNamespace.");
        }
    }
    /**
     * Tests NotValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testNotValidator_EmptyNamespace() {
        try {
            new NotValidator("  ");
            fail("testNotValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_UnknownNS() {
        try {
            new NotValidator("fail.namespace");
            fail("testNotValidator_UnknownNS is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_UnknownNS.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig8.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig9() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG9);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig9 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig9.");
        }
    }

    /**
     * Tests NotValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testNotValidator_InvalidConfig10() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG10);
            new NotValidator("NotValidator");
            fail("testNotValidator_InvalidConfig10 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testNotValidator_InvalidConfig10.");
        }
    }
    
    /**
     * Tests setRegistrationValidator(DataValidationRegistrationValidator dataValidationRegistrationValidator) method with null DataValidationRegistrationValidator dataValidationRegistrationValidator,
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
     * Tests valid(Object object) method with VPE thrown,
     *
     */
    public void testValid_VPE() {
        try {
            ((MockAbstractConfigurableValidator)configValidator).setThrowException(true);
            validator.valid(new ValidationInfo());
            fail("testValid_VPE is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValid_VPE.");
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
            validator.getMessage(new Integer(1));
            fail("testGetMessage_InvalidObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_InvalidObj.");
        }
    }

    /**
     * Tests getMessage(Object object) method with VPE thrown,
     *
     */
    public void testGetMessage_VPE() {
        try {
            ((MockAbstractConfigurableValidator)configValidator).setThrowException(true);
            validator.getMessage(new ValidationInfo());
            fail("testGetMessage_VPE is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_VPE.");
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
     * Tests getMessages(Object object) method with VPE thrown,
     *
     */
    public void testGetMessages_VPE() {
        try {
            ((MockAbstractConfigurableValidator)configValidator).setThrowException(true);
            validator.getMessages(new ValidationInfo());
            fail("testGetMessages_VPE is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessages_VPE.");
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
            validator.getAllMessages(new Long(0));
            fail("testGetAllMessages_InvalidObject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages_InvalidObject.");
        }
    }

    /**
     * Tests getAllMessages(Object object) method with VPE thrown,
     */
    public void testGetAllMessages_VPE() {
        try {
            ((MockAbstractConfigurableValidator)configValidator).setThrowException(true);
            validator.getAllMessages(new ValidationInfo());
            fail("testGetAllMessages_VPE is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages_VPE.");
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
     * Tests getAllMessages(Object object, int messageLimit) method with ValidationProcessingException thrown.
     */
    public void testGetAllMessages2_VPE() {
        try {
            ((MockAbstractConfigurableValidator)configValidator).setThrowException(true);
            validator.getAllMessages(new ValidationInfo(), 1);
            fail("testGetAllMessages2_VPE is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetAllMessages2_VPE.");
        }
    }
}