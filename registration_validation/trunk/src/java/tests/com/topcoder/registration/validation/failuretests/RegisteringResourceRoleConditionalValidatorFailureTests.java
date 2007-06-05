/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import junit.framework.TestCase;

import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.conditional.RegisteringResourceRoleConditionalValidator;
/**
 * Failure Tests for RegisteringResourceRoleConditionalValidator class.
 * @author slion
 * @version 1.0
 */
public class RegisteringResourceRoleConditionalValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/RRRCV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/RRRCV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/RRRCV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/RRRCV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/RRRCV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/RRRCV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/RRRCV_invalidconfig7.xml";

    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/RRRCV_invalidconfig8.xml";

    /**
     * Represents the RegisteringResourceRoleConditionalValidator instance.
     */
    private RegisteringResourceRoleConditionalValidator validator = null;
    
    /**
     * Represents the ConfigurableValiator instance.
     */
    private ConfigurableValidator configValidator;

    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        configValidator = new MockAbstractConfigurableValidator("test");
        validator = new RegisteringResourceRoleConditionalValidator(configValidator, 5);
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
        configValidator = null;
        TestHelper.clearConfiguration();
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(ConfigurableValidator innerValidator, long roleId)
     *  method with null ConfigurableValidator innerValidator,
     * IllegalArgumentException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_NullInnerValidator() {
        try {
            new RegisteringResourceRoleConditionalValidator(null, 1);
            fail("testRegisteringResourceRoleConditionalValidator_NullInnerValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_NullInnerValidator.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(ConfigurableValidator innerValidator, long roleId)
     *  method with negative long roleId,
     * IllegalArgumentException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_NegativeRegisteringResourceRoleId() {
        try {
            new RegisteringResourceRoleConditionalValidator(configValidator, -1);
            fail("testRegisteringResourceRoleConditionalValidator_NegativeRegisteringResourceRoleId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_NegativeRegisteringResourceRoleId.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_NullNamespace() {
        try {
            new RegisteringResourceRoleConditionalValidator(null);
            fail("testRegisteringResourceRoleConditionalValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_NullNamespace.");
        }
    }
    
    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_EmptyNamespace() {
        try {
            new RegisteringResourceRoleConditionalValidator("  ");
            fail("testRegisteringResourceRoleConditionalValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_UnknownNamespace() {
        try {
            new RegisteringResourceRoleConditionalValidator("unknown");
            fail("testRegisteringResourceRoleConditionalValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests RegisteringResourceRoleConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testRegisteringResourceRoleConditionalValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new RegisteringResourceRoleConditionalValidator("RegisteringResourceRoleConditionalValidator");
            fail("testRegisteringResourceRoleConditionalValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testRegisteringResourceRoleConditionalValidator_InvalidConfig8.");
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
            validator.getMessage("fail");
            fail("testGetMessage_InvalidObj is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_InvalidObj.");
        }
    }

//    /**
//     * Tests getMessage(Object obj) method with ValidationProcessingException thrown,
//     * 
//     */
//    public void testGetMessage_VPE1() {
//        try {
//            validator.getMessage(new ValidationInfo());
//            fail("testGetMessage_VPE1 is failure.");
//        } catch (ValidationProcessingException vpe) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testGetMessage_VPE1.");
//        }
//    }

    /**
     * Tests getMessage(Object obj) method with ValidationProcessingException thrown,
     * 
     */
    public void testGetMessage_VPE2() {
        try {
            ((MockAbstractConfigurableValidator)configValidator).setThrowException(true);
            ValidationInfo info = new ValidationInfo();
            RegistrationInfo registrationInfo = new RegistrationInfoImpl();
            registrationInfo.setRoleId(5);
            info.setRegistration(registrationInfo);
            validator.getMessage(info);
            fail("testGetMessage_VPE2 is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testGetMessage_VPE2.");
        }
    }

    /**
     * Tests setRegistrationValidator(DataValidationRegistrationValidator dataValidationRegistrationValidator)
     *  method with null DataValidationRegistrationValidator dataValidationRegistrationValidator,
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
}