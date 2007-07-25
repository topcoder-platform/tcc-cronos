/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.conditional.ProjectTypeConditionalValidator;


import junit.framework.TestCase;
/**
 * Failure Tests for ProjectTypeConditionalValidator class.
 * @author slion
 * @version 1.0
 */
public class ProjectTypeConditionalValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/PTCV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/PTCV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/PTCV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/PTCV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/PTCV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/PTCV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/PTCV_invalidconfig7.xml";

    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/PTCV_invalidconfig8.xml";

    /**
     * Represents the ProjectTypeConditionalValidator instance.
     */
    private ProjectTypeConditionalValidator validator = null;
    
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
        validator = new ProjectTypeConditionalValidator(configValidator, 3);
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
     * Tests ProjectTypeConditionalValidator(ConfigurableValidator innerValidator, long projectId)
     *  method with null ConfigurableValidator innerValidator,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectTypeConditionalValidator_NullInnerValidator() {
        try {
            new ProjectTypeConditionalValidator(null, 1);
            fail("testProjectTypeConditionalValidator_NullInnerValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_NullInnerValidator.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(ConfigurableValidator innerValidator, long projectId)
     *  method with negative long projectTypeId,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectTypeConditionalValidator_NegativeProjectTypeId() {
        try {
            new ProjectTypeConditionalValidator(configValidator, -1);
            fail("testProjectTypeConditionalValidator_NegativeProjectTypeId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_NegativeProjectTypeId.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectTypeConditionalValidator_NullNamespace() {
        try {
            new ProjectTypeConditionalValidator(null);
            fail("testProjectTypeConditionalValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_NullNamespace.");
        }
    }
    
    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectTypeConditionalValidator_EmptyNamespace() {
        try {
            new ProjectTypeConditionalValidator("  ");
            fail("testProjectTypeConditionalValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_UnknownNamespace() {
        try {
            new ProjectTypeConditionalValidator("unknown");
            fail("testProjectTypeConditionalValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests ProjectTypeConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectTypeConditionalValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new ProjectTypeConditionalValidator("ProjectTypeConditionalValidator");
            fail("testProjectTypeConditionalValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectTypeConditionalValidator_InvalidConfig8.");
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
            FullProjectData project = new FullProjectData(new Date(), new DefaultWorkdays());
            project.setProjectHeader(new Project(1, new ProjectCategory(2, "test",
                    new ProjectType(3, "Design")), new ProjectStatus(4, "Open")));
            info.setProject(project);
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