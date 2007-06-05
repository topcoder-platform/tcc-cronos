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
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for MemberMinimumNumberOfRatingsForRatingTypeValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class MemberMinimumNumberOfRatingsForRatingTypeValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/MMNORFRTV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/MMNORFRTV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/MMNORFRTV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/MMNORFRTV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/MMNORFRTV_invalidconfig5.xml";
 
    /**
     * Represents the MemberMinimumNumberOfRatingsForRatingTypeValidator instance.
     */
    private MemberMinimumNumberOfRatingsForRatingTypeValidator validator = null;
    
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
        validator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(bundle, 1000, RatingType.DEVELOPMENT);
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
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(BundleInfo bundleInfo, int minimumNumRatings,
     * RatingType ratingType) method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_EmptyBundleInfo() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator(new BundleInfo(), 1000, RatingType.DESIGN);
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(BundleInfo bundleInfo, int minimumNumRatings,
     * RatingType ratingType) method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullBundleInfo() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator(null, 1000, RatingType.DESIGN);
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(BundleInfo bundleInfo, int minimumNumRatings,
     * RatingType ratingType) method with negative int minimumNumRatings, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_NegativeMinimumNumRatings() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator(bundle, -1, RatingType.DESIGN);
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_NegativeMinimumNumRatings is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_NegativeMinimumNumRatings.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(BundleInfo bundleInfo, int minimumNumRatings,
     * RatingType ratingType) method with null RatingType ratingType, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullRatingType() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator(bundle, 1000, null);
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullRatingType is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullRatingType.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullNamespace() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator(null);
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_NullNamespace.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_EmptyNamespace() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("  ");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_UnknownNamespace() {
        try {
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("unknown");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("MemberMinimumNumberOfRatingsForRatingTypeValidator");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("MemberMinimumNumberOfRatingsForRatingTypeValidator");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("MemberMinimumNumberOfRatingsForRatingTypeValidator");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("MemberMinimumNumberOfRatingsForRatingTypeValidator");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests MemberMinimumNumberOfRatingsForRatingTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new MemberMinimumNumberOfRatingsForRatingTypeValidator("MemberMinimumNumberOfRatingsForRatingTypeValidator");
            fail("testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberMinimumNumberOfRatingsForRatingTypeValidator_InvalidConfig5.");
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
