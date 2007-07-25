/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.simple.MemberMinimumVolatilityForRatingTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for MemberMinimumVolatilityForRatingTypeValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class MemberMinimumVolatilityForRatingTypeValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/MMVFRTV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/MMVFRTV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/MMVFRTV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/MMVFRTV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/MMVFRTV_invalidconfig5.xml";
    
    /**
     * Represents the MemberMinimumVolatilityForRatingTypeValidator instance.
     */
    private MemberMinimumVolatilityForRatingTypeValidator validator = null;
    
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
        validator = new MemberMinimumVolatilityForRatingTypeValidator(bundle, 1000, RatingType.DEVELOPMENT);
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
     * Tests MemberMinimumVolatilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumVolatility,
     * RatingType ratingType) method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_NullBundleInfo() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator(null, 1000, RatingType.DESIGN);
            fail("testMemberMinimumVolatilityForRatingTypeValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumVolatility,
     * RatingType ratingType) method with empty bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_EmptyBundleInfo() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator(new BundleInfo(), 1000, RatingType.DESIGN);
            fail("testMemberMinimumVolatilityForRatingTypeValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumVolatility,
     * RatingType ratingType) method with negative int minimumVolatility, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_NegativeMinimumVolatility() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator(bundle, -1, RatingType.DESIGN);
            fail("testMemberMinimumVolatilityForRatingTypeValidator_NegativeMinimumVolatility is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_NegativeMinimumVolatility.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumVolatility,
     * RatingType ratingType) method with null RatingType ratingType, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_NullRatingType() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator(bundle, 1000, null);
            fail("testMemberMinimumVolatilityForRatingTypeValidator_NullRatingType is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_NullRatingType.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_NullNamespace() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator(null);
            fail("testMemberMinimumVolatilityForRatingTypeValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_NullNamespace.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_EmptyNamespace() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator("  ");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_UnknownNamespace() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator("unknown");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new MemberMinimumVolatilityForRatingTypeValidator("MemberMinimumVolatilityForRatingTypeValidator");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new MemberMinimumVolatilityForRatingTypeValidator("MemberMinimumVolatilityForRatingTypeValidator");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new MemberMinimumVolatilityForRatingTypeValidator("MemberMinimumVolatilityForRatingTypeValidator");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new MemberMinimumVolatilityForRatingTypeValidator("MemberMinimumVolatilityForRatingTypeValidator");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests MemberMinimumVolatilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new MemberMinimumVolatilityForRatingTypeValidator("MemberMinimumVolatilityForRatingTypeValidator");
            fail("testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumVolatilityForRatingTypeValidator_InvalidConfig5.");
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
