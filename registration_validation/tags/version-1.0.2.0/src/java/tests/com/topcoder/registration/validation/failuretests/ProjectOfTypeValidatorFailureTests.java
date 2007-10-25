/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.simple.ProjectOfTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;


/**
 * Failure Tests for ProjectOfTypeValidator class.
 *
 * @author slion
 * @version 1.0
 */
public class ProjectOfTypeValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/POTV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/POTV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/POTV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/POTV_invalidconfig4.xml";

    /**
     * Represents the ProjectOfTypeValidator instance.
     */
    private ProjectOfTypeValidator validator = null;
    
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
        validator = new ProjectOfTypeValidator(bundle, 1000);
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
     * Tests ProjectOfTypeValidator(BundleInfo bundleInfo, long projectTypeId)
     * method with empty BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testProjectOfTypeValidator_EmptyBundleInfo() {
        try {
            new ProjectOfTypeValidator(new BundleInfo(), 1000);
            fail("testProjectOfTypeValidator_EmptyBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_EmptyBundleInfo.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(BundleInfo bundleInfo, long projectTypeId)
     * method with null BundleInfo bundleInfo, IllegalArgumentException should be thrown.
     */
    public void testProjectOfTypeValidator_NullBundleInfo() {
        try {
            new ProjectOfTypeValidator(null, 1000);
            fail("testProjectOfTypeValidator_NullBundleInfo is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_NullBundleInfo.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(BundleInfo bundleInfo, long projectTypeId)
     *  method with negative long projectTypeId, IllegalArgumentException should be thrown.
     */
    public void testProjectOfTypeValidator_NegativeprojectTypeId() {
        try {
            new ProjectOfTypeValidator(bundle, -1);
            fail("testProjectOfTypeValidator_NegativeprojectTypeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in " +
                    "testProjectOfTypeValidator_NegativeprojectTypeId.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with null String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testProjectOfTypeValidator_NullNamespace() {
        try {
            new ProjectOfTypeValidator(null);
            fail("testProjectOfTypeValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_NullNamespace.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with empty String
     * namespace, IllegalArgumentException should be thrown.
     */
    public void testProjectOfTypeValidator_EmptyNamespace() {
        try {
            new ProjectOfTypeValidator("  ");
            fail("testProjectOfTypeValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfTypeValidator_UnknownNamespace() {
        try {
            new ProjectOfTypeValidator("unknown");
            fail("testProjectOfTypeValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfTypeValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new ProjectOfTypeValidator("ProjectOfTypeValidator");
            fail("testProjectOfTypeValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfTypeValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new ProjectOfTypeValidator("ProjectOfTypeValidator");
            fail("testProjectOfTypeValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfTypeValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new ProjectOfTypeValidator("ProjectOfTypeValidator");
            fail("testProjectOfTypeValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests ProjectOfTypeValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectOfTypeValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new ProjectOfTypeValidator("ProjectOfTypeValidator");
            fail("testProjectOfTypeValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectOfTypeValidator_InvalidConfig4.");
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
