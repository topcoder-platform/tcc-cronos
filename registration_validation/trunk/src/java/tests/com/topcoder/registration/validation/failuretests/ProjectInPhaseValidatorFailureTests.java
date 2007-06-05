/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.simple.ProjectInPhaseValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for ProjectInPhaseValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class ProjectInPhaseValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/PIPV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/PIPV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/PIPV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/PIPV_invalidconfig4.xml";

    /**
     * Represents the ProjectInPhaseValidator instance.
     */
    private ProjectInPhaseValidator validator = null;
    
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
        validator = new ProjectInPhaseValidator(bundle, 1000);
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
     * Tests ProjectInPhaseValidator(BundleInfo bundleInfo, long phaseId)
     * method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testProjectInPhaseValidator_EmptyBundleInfo() {
        try {
            new ProjectInPhaseValidator(new BundleInfo(), 1000);
            fail("testProjectInPhaseValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(BundleInfo bundleInfo, long phaseId)
     * method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testProjectInPhaseValidator_NullBundleInfo() {
        try {
            new ProjectInPhaseValidator(null, 1000);
            fail("testProjectInPhaseValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(BundleInfo bundleInfo, long phaseId)
     *  method with negative long phaseId, IllegalArgumentException should be thrown.
     */
    public void testProjectInPhaseValidator_NegativePhaseId() {
        try {
            new ProjectInPhaseValidator(bundle, -1);
            fail("testProjectInPhaseValidator_NegativePhaseId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in " +
                    "testProjectInPhaseValidator_NegativePhaseId.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testProjectInPhaseValidator_NullNamespace() {
        try {
            new ProjectInPhaseValidator(null);
            fail("testProjectInPhaseValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_NullNamespace.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testProjectInPhaseValidator_EmptyNamespace() {
        try {
            new ProjectInPhaseValidator("  ");
            fail("testProjectInPhaseValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectInPhaseValidator_UnknownNamespace() {
        try {
            new ProjectInPhaseValidator("unknown");
            fail("testProjectInPhaseValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectInPhaseValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new ProjectInPhaseValidator("ProjectInPhaseValidator");
            fail("testProjectInPhaseValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectInPhaseValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new ProjectInPhaseValidator("ProjectInPhaseValidator");
            fail("testProjectInPhaseValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectInPhaseValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new ProjectInPhaseValidator("ProjectInPhaseValidator");
            fail("testProjectInPhaseValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests ProjectInPhaseValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectInPhaseValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new ProjectInPhaseValidator("ProjectInPhaseValidator");
            fail("testProjectInPhaseValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectInPhaseValidator_InvalidConfig4.");
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
