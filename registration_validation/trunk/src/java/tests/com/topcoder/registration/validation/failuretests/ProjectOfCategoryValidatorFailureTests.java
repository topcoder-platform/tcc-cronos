/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.simple.ProjectOfCategoryValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for ProjectOfCategoryValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class ProjectOfCategoryValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/POCV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/POCV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/POCV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/POCV_invalidconfig4.xml";

    /**
     * Represents the ProjectOfCategoryValidator instance.
     */
    private ProjectOfCategoryValidator validator = null;
    
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
        validator = new ProjectOfCategoryValidator(bundle, 1000);
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
     * Tests ProjectOfCategoryValidator(BundleInfo bundleInfo, long projectCategoryId)
     * method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testProjectOfCategoryValidator_EmptyBundleInfo() {
        try {
            new ProjectOfCategoryValidator(new BundleInfo(), 1000);
            fail("testProjectOfCategoryValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(BundleInfo bundleInfo, long projectCategoryId)
     * method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testProjectOfCategoryValidator_NullBundleInfo() {
        try {
            new ProjectOfCategoryValidator(null, 1000);
            fail("testProjectOfCategoryValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(BundleInfo bundleInfo, long projectCategoryId)
     *  method with negative long projectCategoryId, IllegalArgumentException should be thrown.
     */
    public void testProjectOfCategoryValidator_NegativeprojectCategoryId() {
        try {
            new ProjectOfCategoryValidator(bundle, -1);
            fail("testProjectOfCategoryValidator_NegativeprojectCategoryId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in " +
                    "testProjectOfCategoryValidator_NegativeprojectCategoryId.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testProjectOfCategoryValidator_NullNamespace() {
        try {
            new ProjectOfCategoryValidator(null);
            fail("testProjectOfCategoryValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_NullNamespace.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testProjectOfCategoryValidator_EmptyNamespace() {
        try {
            new ProjectOfCategoryValidator("  ");
            fail("testProjectOfCategoryValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfCategoryValidator_UnknownNamespace() {
        try {
            new ProjectOfCategoryValidator("unknown");
            fail("testProjectOfCategoryValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfCategoryValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new ProjectOfCategoryValidator("ProjectOfCategoryValidator");
            fail("testProjectOfCategoryValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfCategoryValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new ProjectOfCategoryValidator("ProjectOfCategoryValidator");
            fail("testProjectOfCategoryValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfCategoryValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new ProjectOfCategoryValidator("ProjectOfCategoryValidator");
            fail("testProjectOfCategoryValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests ProjectOfCategoryValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfCategoryValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new ProjectOfCategoryValidator("ProjectOfCategoryValidator");
            fail("testProjectOfCategoryValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfCategoryValidator_InvalidConfig4.");
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
