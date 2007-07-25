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
import com.topcoder.registration.validation.validators.conditional.ProjectCategoryConditionalValidator;
import junit.framework.TestCase;
/**
 * Failure Tests for ProjectCategoryConditionalValidator class.
 * @author slion
 * @version 1.0
 */
public class ProjectCategoryConditionalValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/PCCV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/PCCV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/PCCV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/PCCV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/PCCV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/PCCV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/PCCV_invalidconfig7.xml";

    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/PCCV_invalidconfig8.xml";

    /**
     * Represents the ProjectCategoryConditionalValidator instance.
     */
    private ProjectCategoryConditionalValidator validator = null;
    
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
        validator = new ProjectCategoryConditionalValidator(configValidator, 2);
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
     * Tests ProjectCategoryConditionalValidator(ConfigurableValidator innerValidator, long projectCategoryId)
     *  method with null ConfigurableValidator innerValidator,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_NullInnerValidator() {
        try {
            new ProjectCategoryConditionalValidator(null, 1);
            fail("testProjectCategoryConditionalValidator_NullInnerValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_NullInnerValidator.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(ConfigurableValidator innerValidator, long projectCategoryId)
     *  method with negative long projectCategoryId,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_NegativeProjectCategoryId() {
        try {
            new ProjectCategoryConditionalValidator(configValidator, -1);
            fail("testProjectCategoryConditionalValidator_NegativeProjectCategoryId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_NegativeProjectCategoryId.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_NullNamespace() {
        try {
            new ProjectCategoryConditionalValidator(null);
            fail("testProjectCategoryConditionalValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_NullNamespace.");
        }
    }
    
    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_EmptyNamespace() {
        try {
            new ProjectCategoryConditionalValidator("  ");
            fail("testProjectCategoryConditionalValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_UnknownNamespace() {
        try {
            new ProjectCategoryConditionalValidator("unknown");
            fail("testProjectCategoryConditionalValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests ProjectCategoryConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectCategoryConditionalValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new ProjectCategoryConditionalValidator("ProjectCategoryConditionalValidator");
            fail("testProjectCategoryConditionalValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectCategoryConditionalValidator_InvalidConfig8.");
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
            project.setProjectHeader(new Project(1, new ProjectCategory(2, "test", new ProjectType(3, "Design")), new ProjectStatus(4, "Open")));
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