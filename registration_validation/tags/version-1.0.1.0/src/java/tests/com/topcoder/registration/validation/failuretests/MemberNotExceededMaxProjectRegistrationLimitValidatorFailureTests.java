/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.simple.MemberNotExceededMaxProjectRegistrationLimitValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for MemberNotExceededMaxProjectRegistrationLimitValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class MemberNotExceededMaxProjectRegistrationLimitValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/MNEMPRLV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/MNEMPRLV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/MNEMPRLV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/MNEMPRLV_invalidconfig4.xml";

    /**
     * Represents the MemberNotExceededMaxProjectRegistrationLimitValidator instance.
     */
    private MemberNotExceededMaxProjectRegistrationLimitValidator validator = null;
    
    /**
     * Represents the BundleInfo instance for testing.
     */
    private BundleInfo bundle = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        bundle = new BundleInfo();
        bundle.setBundle("sun.security.util.Resources_zh_CN");
        bundle.setDefaultMessage("some message.");
        bundle.setMessageKey("key");
        validator = new MemberNotExceededMaxProjectRegistrationLimitValidator(bundle, 1000);
        TestHelper.clearConfiguration();
        TestHelper.loadConfiguration("test_files/failure/validconfig.xml");
        validator.setRegistrationValidator(new DataValidationRegistrationValidator());
    }

    /**
     * Teardown the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        validator = null;
        bundle = null;
        TestHelper.clearConfiguration();
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(BundleInfo bundleInfo, int maxRegistrationCount)
     * method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_EmptyBundleInfo() {
        try {
            new MemberNotExceededMaxProjectRegistrationLimitValidator(new BundleInfo(), 1000);
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(BundleInfo bundleInfo, int maxRegistrationCount)
     * method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_NullBundleInfo() {
        try {
            new MemberNotExceededMaxProjectRegistrationLimitValidator(null, 1000);
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(BundleInfo bundleInfo, int maxRegistrationCount)
     *  method with negative int maxRegistrationCount, IllegalArgumentException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_NegativeMaxRegistrationCount() {
        try {
            new MemberNotExceededMaxProjectRegistrationLimitValidator(bundle, -1);
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_NegativeMaxRegistrationCount is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in " +
                    "testMemberNotExceededMaxProjectRegistrationLimitValidator_NegativeMaxRegistrationCount.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_NullNamespace() {
        try {
            new MemberNotExceededMaxProjectRegistrationLimitValidator(null);
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_NullNamespace.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_EmptyNamespace() {
        try {
            new MemberNotExceededMaxProjectRegistrationLimitValidator("  ");
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_UnknownNamespace() {
        try {
            new MemberNotExceededMaxProjectRegistrationLimitValidator("unknown");
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new MemberNotExceededMaxProjectRegistrationLimitValidator("MemberNotExceededMaxProjectRegistrationLimitValidator");
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new MemberNotExceededMaxProjectRegistrationLimitValidator("MemberNotExceededMaxProjectRegistrationLimitValidator");
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new MemberNotExceededMaxProjectRegistrationLimitValidator("MemberNotExceededMaxProjectRegistrationLimitValidator");
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests MemberNotExceededMaxProjectRegistrationLimitValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new MemberNotExceededMaxProjectRegistrationLimitValidator("MemberNotExceededMaxProjectRegistrationLimitValidator");
            fail("testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotExceededMaxProjectRegistrationLimitValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests getMessage(Object obj) method with null Object obj, IllegalArgumentException should be thrown.
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
     * Tests getMessage(Object obj) method with invalid Object obj, IllegalArgumentException should be thrown.
     */
    public void testGetMessage_InvalidObj() {
        try {
            validator.getMessage(new Byte("1"));
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
//    public void testGetMessage_VPE() {
//        try {
//            validator.getMessage(new ValidationInfo());
//            fail("testGetMessage_VPE is failure.");
//        } catch (ValidationProcessingException vpe) {
//            // pass
//        } catch (Exception e) {
//            fail("Unknown exception occurs in testGetMessage_VPE.");
//        }
//    }
}
