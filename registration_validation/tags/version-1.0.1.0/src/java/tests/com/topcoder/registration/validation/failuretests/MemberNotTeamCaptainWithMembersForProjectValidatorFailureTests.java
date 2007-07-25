/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamCaptainWithMembersForProjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for MemberNotTeamCaptainWithMembersForProjectValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class MemberNotTeamCaptainWithMembersForProjectValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/MNTCWMFPV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/MNTCWMFPV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/MNTCWMFPV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/MNTCWMFPV_invalidconfig4.xml";

    /**
     * Represents the MemberNotTeamCaptainWithMembersForProjectValidator instance.
     */
    private MemberNotTeamCaptainWithMembersForProjectValidator validator = null;
    
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
        validator = new MemberNotTeamCaptainWithMembersForProjectValidator(bundle, 1000);
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
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo bundleInfo, long teamCaptainRoleId)
     * method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_EmptyBundleInfo() {
        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator(new BundleInfo(), 1000);
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo bundleInfo, long teamCaptainRoleId)
     * method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_NullBundleInfo() {
        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator(null, 1000);
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo bundleInfo, long teamCaptainRoleId)
     *  method with negative long teamCaptainRoleId, IllegalArgumentException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_NegativeTeamCaptainRoleId() {
        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator(bundle, -1);
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_NegativeTeamCaptainRoleId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in " +
                    "testMemberNotTeamCaptainWithMembersForProjectValidator_NegativeTeamCaptainRoleId.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_NullNamespace() {
        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator(null);
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_NullNamespace.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_EmptyNamespace() {
        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator("  ");
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_UnknownNamespace() {
        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator("unknown");
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new MemberNotTeamCaptainWithMembersForProjectValidator("MemberNotTeamCaptainWithMembersForProjectValidator");
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new MemberNotTeamCaptainWithMembersForProjectValidator("MemberNotTeamCaptainWithMembersForProjectValidator");
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new MemberNotTeamCaptainWithMembersForProjectValidator("MemberNotTeamCaptainWithMembersForProjectValidator");
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests MemberNotTeamCaptainWithMembersForProjectValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new MemberNotTeamCaptainWithMembersForProjectValidator("MemberNotTeamCaptainWithMembersForProjectValidator");
            fail("testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testMemberNotTeamCaptainWithMembersForProjectValidator_InvalidConfig4.");
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
