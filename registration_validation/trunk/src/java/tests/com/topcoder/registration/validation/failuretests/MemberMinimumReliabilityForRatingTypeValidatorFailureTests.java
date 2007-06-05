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
import com.topcoder.registration.validation.validators.simple.MemberMinimumReliabilityForRatingTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for MemberMinimumReliabilityForRatingTypeValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class MemberMinimumReliabilityForRatingTypeValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/MMReliabilityFRTV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/MMReliabilityFRTV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/MMReliabilityFRTV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/MMReliabilityFRTV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/MMReliabilityFRTV_invalidconfig5.xml";

    /**
     * Represents the MemberMinimumReliabilityForRatingTypeValidator instance.
     */
    private MemberMinimumReliabilityForRatingTypeValidator validator = null;
    
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
        validator = new MemberMinimumReliabilityForRatingTypeValidator(bundle, 1000, RatingType.DEVELOPMENT);
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
     * Tests MemberMinimumReliabilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumReliability,
     * RatingType ratingType) method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_NullBundleInfo() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator(null, 1000, RatingType.DESIGN);
            fail("testMemberMinimumReliabilityForRatingTypeValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumReliability,
     * RatingType ratingType) method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_EmptyBundleInfo() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator(new BundleInfo(), 1000, RatingType.DESIGN);
            fail("testMemberMinimumReliabilityForRatingTypeValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumReliability,
     * RatingType ratingType) method with negative int minimumReliability, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_NegativeMinimumReliability() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator(bundle, -1, RatingType.DESIGN);
            fail("testMemberMinimumReliabilityForRatingTypeValidator_NegativeMinimumReliability is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_NegativeMinimumReliability.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(BundleInfo bundleInfo, int minimumReliability,
     * RatingType ratingType) method with null RatingType ratingType, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_NullRatingType() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator(bundle, 1000, null);
            fail("testMemberMinimumReliabilityForRatingTypeValidator_NullRatingType is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_NullRatingType.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_NullNamespace() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator(null);
            fail("testMemberMinimumReliabilityForRatingTypeValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_NullNamespace.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_EmptyNamespace() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator("  ");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_UnknownNamespace() {
        try {
            new MemberMinimumReliabilityForRatingTypeValidator("unknown");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new MemberMinimumReliabilityForRatingTypeValidator("MemberMinimumReliabilityForRatingTypeValidator");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new MemberMinimumReliabilityForRatingTypeValidator("MemberMinimumReliabilityForRatingTypeValidator");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new MemberMinimumReliabilityForRatingTypeValidator("MemberMinimumReliabilityForRatingTypeValidator");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new MemberMinimumReliabilityForRatingTypeValidator("MemberMinimumReliabilityForRatingTypeValidator");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests MemberMinimumReliabilityForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new MemberMinimumReliabilityForRatingTypeValidator("MemberMinimumReliabilityForRatingTypeValidator");
            fail("testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumReliabilityForRatingTypeValidator_InvalidConfig5.");
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
