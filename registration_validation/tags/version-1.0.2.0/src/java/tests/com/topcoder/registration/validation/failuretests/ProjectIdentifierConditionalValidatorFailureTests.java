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
import com.topcoder.registration.validation.validators.conditional.ProjectIdentifierConditionalValidator;

import junit.framework.TestCase;
/**
 * Failure Tests for ProjectIdentifierConditionalValidator class.
 * @author slion
 * @version 1.0
 */
public class ProjectIdentifierConditionalValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/PICV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/PICV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/PICV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/PICV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/PICV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/PICV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/PICV_invalidconfig7.xml";

    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/PICV_invalidconfig8.xml";

    /**
     * Represents the ProjectIdentifierConditionalValidator instance.
     */
    private ProjectIdentifierConditionalValidator validator = null;
    
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
        validator = new ProjectIdentifierConditionalValidator(configValidator, 1);
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
     * Tests ProjectIdentifierConditionalValidator(ConfigurableValidator innerValidator, long projectId)
     *  method with null ConfigurableValidator innerValidator,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_NullInnerValidator() {
        try {
            new ProjectIdentifierConditionalValidator(null, 1);
            fail("testProjectIdentifierConditionalValidator_NullInnerValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_NullInnerValidator.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(ConfigurableValidator innerValidator, long projectId)
     *  method with negative long projectIdentifierId,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_NegativeProjectIdentifierId() {
        try {
            new ProjectIdentifierConditionalValidator(configValidator, -1);
            fail("testProjectIdentifierConditionalValidator_NegativeProjectIdentifierId is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_NegativeProjectIdentifierId.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_NullNamespace() {
        try {
            new ProjectIdentifierConditionalValidator(null);
            fail("testProjectIdentifierConditionalValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_NullNamespace.");
        }
    }
    
    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_EmptyNamespace() {
        try {
            new ProjectIdentifierConditionalValidator("  ");
            fail("testProjectIdentifierConditionalValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_UnknownNamespace() {
        try {
            new ProjectIdentifierConditionalValidator("unknown");
            fail("testProjectIdentifierConditionalValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig1() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig2() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig3() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig4() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig5() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig6() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig7() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests ProjectIdentifierConditionalValidator(String namespace) method with invalid configuration,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testProjectIdentifierConditionalValidator_InvalidConfig8() {
        try {
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new ProjectIdentifierConditionalValidator("ProjectIdentifierConditionalValidator");
            fail("testProjectIdentifierConditionalValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testProjectIdentifierConditionalValidator_InvalidConfig8.");
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