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

import com.topcoder.registration.validation.validators.simple.MemberMinimumRatingForRatingTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for MemberMinimumRatingForRatingTypeValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class MemberMinimumRatingForRatingTypeValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/MMRFRTV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/MMRFRTV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/MMRFRTV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/MMRFRTV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/MMRFRTV_invalidconfig5.xml";

    /**
     * Represents the MemberMinimumRatingForRatingTypeValidator instance.
     */
    private MemberMinimumRatingForRatingTypeValidator validator = null;
    
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
        validator = new MemberMinimumRatingForRatingTypeValidator(bundle, 1000, RatingType.DEVELOPMENT);
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
     * Tests MemberMinimumRatingForRatingTypeValidator(BundleInfo bundleInfo, int minimumRating,
     * RatingType ratingType) method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_EmptyBundleInfo() {
        try {
            new MemberMinimumRatingForRatingTypeValidator(new BundleInfo(), 1000, RatingType.DESIGN);
            fail("testMemberMinimumRatingForRatingTypeValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(BundleInfo bundleInfo, int minimumRating,
     * RatingType ratingType) method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_NullBundleInfo() {
        try {
            new MemberMinimumRatingForRatingTypeValidator(null, 1000, RatingType.DESIGN);
            fail("testMemberMinimumRatingForRatingTypeValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(BundleInfo bundleInfo, int minimumRating,
     * RatingType ratingType) method with negative int minimumRating, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_NegativeMinimumRating() {
        try {
            new MemberMinimumRatingForRatingTypeValidator(bundle, -1, RatingType.DESIGN);
            fail("testMemberMinimumRatingForRatingTypeValidator_NegativeMinimumRating is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_NegativeMinimumRating.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(BundleInfo bundleInfo, int minimumRating,
     * RatingType ratingType) method with null RatingType ratingType, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_NullRatingType() {
        try {
            new MemberMinimumRatingForRatingTypeValidator(bundle, 1000, null);
            fail("testMemberMinimumRatingForRatingTypeValidator_NullRatingType is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_NullRatingType.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_NullNamespace() {
        try {
            new MemberMinimumRatingForRatingTypeValidator(null);
            fail("testMemberMinimumRatingForRatingTypeValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_NullNamespace.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_EmptyNamespace() {
        try {
            new MemberMinimumRatingForRatingTypeValidator("  ");
            fail("testMemberMinimumRatingForRatingTypeValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_UnknownNamespace() {
        try {
            new MemberMinimumRatingForRatingTypeValidator("unknown");
            fail("testMemberMinimumRatingForRatingTypeValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new MemberMinimumRatingForRatingTypeValidator("MemberMinimumRatingForRatingTypeValidator");
            fail("testMemberMinimumRatingForRatingTypeValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new MemberMinimumRatingForRatingTypeValidator("MemberMinimumRatingForRatingTypeValidator");
            fail("testMemberMinimumRatingForRatingTypeValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new MemberMinimumRatingForRatingTypeValidator("MemberMinimumRatingForRatingTypeValidator");
            fail("testMemberMinimumRatingForRatingTypeValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new MemberMinimumRatingForRatingTypeValidator("MemberMinimumRatingForRatingTypeValidator");
            fail("testMemberMinimumRatingForRatingTypeValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests MemberMinimumRatingForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumRatingForRatingTypeValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new MemberMinimumRatingForRatingTypeValidator("MemberMinimumRatingForRatingTypeValidator");
            fail("testMemberMinimumRatingForRatingTypeValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumRatingForRatingTypeValidator_InvalidConfig5.");
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
