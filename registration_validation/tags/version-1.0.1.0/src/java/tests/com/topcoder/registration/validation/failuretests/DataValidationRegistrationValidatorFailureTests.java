/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.failuretests;
import java.util.Date;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.management.ban.BanManager;
import com.topcoder.management.ban.manager.MockBanManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.impl.MockTeamManagerImpl;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.impl.MockProjectServicesImpl;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.validators.util.NullValidator;

import junit.framework.TestCase;
/**
 * Failure tests for DataValidationRegistrationValidator class.
 * @author slion
 * @version 1.0
 */
public class DataValidationRegistrationValidatorFailureTests extends TestCase {
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG1 = "test_files/failure/DVRV_invalidconfig1.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG2 = "test_files/failure/DVRV_invalidconfig2.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG3 = "test_files/failure/DVRV_invalidconfig3.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG4 = "test_files/failure/DVRV_invalidconfig4.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG5 = "test_files/failure/DVRV_invalidconfig5.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG6 = "test_files/failure/DVRV_invalidconfig6.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG7 = "test_files/failure/DVRV_invalidconfig7.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG8 = "test_files/failure/DVRV_invalidconfig8.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG9 = "test_files/failure/DVRV_invalidconfig9.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG10 = "test_files/failure/DVRV_invalidconfig10.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG11 = "test_files/failure/DVRV_invalidconfig11.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG12 = "test_files/failure/DVRV_invalidconfig12.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG13 = "test_files/failure/DVRV_invalidconfig13.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG14 = "test_files/failure/DVRV_invalidconfig14.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG15 = "test_files/failure/DVRV_invalidconfig15.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG16 = "test_files/failure/DVRV_invalidconfig16.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG17 = "test_files/failure/DVRV_invalidconfig17.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG18 = "test_files/failure/DVRV_invalidconfig18.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG19 = "test_files/failure/DVRV_invalidconfig19.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG20 = "test_files/failure/DVRV_invalidconfig20.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG21 = "test_files/failure/DVRV_invalidconfig21.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG22 = "test_files/failure/DVRV_invalidconfig22.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG23 = "test_files/failure/DVRV_invalidconfig23.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG24 = "test_files/failure/DVRV_invalidconfig24.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG25 = "test_files/failure/DVRV_invalidconfig25.xml";
    
    /**
     * Represents the invalid configuration file path.
     */
    private static final String INVALID_CONFIG26 = "test_files/failure/DVRV_invalidconfig26.xml";

    /**
     * Represents the DataValidationRegistrationValidator instance.
     */
    private DataValidationRegistrationValidator validator = null;
    
    /**
     * Represents the TeamManager instance.
     */
    private TeamManager teamManager;

    /**
     * Represents the ProjectServices instance.
     */
    private ProjectServices projectServices;

    /**
     * Represents the BanManager instance.
     */
    private BanManager banManager;

    /**
     * Represents the configurable validator.
     */
    private ConfigurableValidator configValidator = null;

    /**
     * Represents the user.
     */
    private ExternalUser user;

    /**
     * Represents the project data.
     */
    private FullProjectData project;

    /**
     * Represents the registration info.
     */
    private RegistrationInfo info;
    
    /**
     * Setup the test environment.
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfiguration();
        TestHelper.loadConfiguration("test_files/failure/validconfig.xml");
        validator = new DataValidationRegistrationValidator();
        teamManager = new MockTeamManagerImpl();
        projectServices = new MockProjectServicesImpl();
        banManager = new MockBanManager();
        configValidator = new NullValidator();
        user = new ExternalUserImpl(100, "user", "handle", "firstName", "lastName");
        project = new FullProjectData(new Date(), new DefaultWorkdays());
        info = new RegistrationInfoImpl();
    }

    /**
     * Teardown the test environment.
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfiguration();
        validator = null;
        teamManager = null;
        projectServices = null;
        banManager = null;
        configValidator = null;
        user = null;
        project = null;
        info = null;
    }

    /**
     * Tests DataValidationRegistrationValidator(String namespace) method with null String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testDataValidationRegistrationValidator_NullNamespace() {
        try {
            new DataValidationRegistrationValidator(null);
            fail("testDataValidationRegistrationValidator_NullNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_NullNamespace.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator(String namespace) method with empty String namespace,
     * IllegalArgumentException should be thrown.
     */
    public void testDataValidationRegistrationValidator_EmptyNamespace() {
        try {
            new DataValidationRegistrationValidator("  ");
            fail("testDataValidationRegistrationValidator_EmptyNamespace is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_EmptyNamespace.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with unknown namespace,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_UnknownNamespace() {
        try {
            TestHelper.clearConfiguration();
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_UnknownNamespace is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_UnknownNamespace.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig1() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG1);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig1 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig1.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig2() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG2);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig2 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig2.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig3() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG3);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig3 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig3.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig4() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG4);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig4 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig4.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig5() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG5);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig5 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig5.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig6() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG6);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig6 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig6.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig7() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG7);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig7 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig7.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig8() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG8);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig8 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig8.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig9() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG9);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig9 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig9.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig10() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG10);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig10 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig10.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig11() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG11);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig11 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig11.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig12() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG12);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig12 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig12.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig13() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG13);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig13 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig13.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig14() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG14);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig14 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig14.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig15() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG15);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig15 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig15.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig16() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG16);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig16 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig16.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig17() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG17);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig17 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig17.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig18() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG18);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig18 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig18.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig19() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG19);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig19 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig19.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig20() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG20);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig20 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig20.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig21() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG21);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig21 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig21.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig22() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG22);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig22 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig22.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig23() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG23);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig23 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig23.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig24() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG24);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig24 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig24.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig25() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG25);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig25 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig25.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator() method with invalid configurations,
     * RegistrationValidationConfigurationException should be thrown.
     */
    public void testDataValidationRegistrationValidator_InvalidConfig26() {
        try {
            TestHelper.clearConfiguration();
            TestHelper.loadConfiguration(INVALID_CONFIG26);
            new DataValidationRegistrationValidator();
            fail("testDataValidationRegistrationValidator_InvalidConfig26 is failure.");
        } catch (RegistrationValidationConfigurationException rvce) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_InvalidConfig26.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator(TeamManager teamManager, ProjectServices projectServices,
     *  BanManager banManager, Log logger, ConfigurableValidator validator) method with null 
     *  TeamManager teamManager,
     * IllegalArgumentException should be thrown.
     */
    public void testDataValidationRegistrationValidator_NullTeamManager() {
        try {
            new DataValidationRegistrationValidator(null, projectServices, banManager, null, configValidator);
            fail("testDataValidationRegistrationValidator_NullTeamManager is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_NullTeamManager.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator(TeamManager teamManager, ProjectServices projectServices,
     *  BanManager banManager, Log logger, ConfigurableValidator validator) method with 
     *  null ProjectServices projectServices,
     * IllegalArgumentException should be thrown.
     */
    public void testDataValidationRegistrationValidator_NullProjectServices() {
        try {
            new DataValidationRegistrationValidator(teamManager, null, banManager, null, configValidator);
            fail("testDataValidationRegistrationValidator_NullProjectServices is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_NullProjectServices.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator(TeamManager teamManager, ProjectServices projectServices,
     *  BanManager banManager, Log logger, ConfigurableValidator validator) method with 
     *  null BanManager banManager,
     * IllegalArgumentException should be thrown.
     */
    public void testDataValidationRegistrationValidator_NullBanManager() {
        try {
            new DataValidationRegistrationValidator(teamManager, projectServices, null, null, configValidator);
            fail("testDataValidationRegistrationValidator_NullBanManager is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_NullBanManager.");
        }
    }

    /**
     * Tests DataValidationRegistrationValidator(TeamManager teamManager, ProjectServices projectServices,
     * BanManager banManager, Log logger, ConfigurableValidator validator) method with null
     * ConfigurableValidator validator,
     * IllegalArgumentException should be thrown.
     */
    public void testDataValidationRegistrationValidator_NullValidator() {
        try {
            new DataValidationRegistrationValidator(teamManager, projectServices, banManager, null, null);
            fail("testDataValidationRegistrationValidator_NullValidator is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testDataValidationRegistrationValidator_NullValidator.");
        }
    }

    /**
     * Tests validate(RegistrationInfo registration, ExternalUser user, FullProjectData project) method
     * with null RegistrationInfo registration,
     * IllegalArgumentException should be thrown.
     */
    public void testValidate_NullRegistration() {
        try {
            validator.validate(null, user, project);
            fail("testValidate_NullRegistration is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidate_NullRegistration.");
        }
    }

    /**
     * Tests validate(RegistrationInfo registration, ExternalUser user, FullProjectData project) method
     * with null ExternalUser user,
     * IllegalArgumentException should be thrown.
     */
    public void testValidate_NullUser() {
        try {
            validator.validate(info, null, project);
            fail("testValidate_NullUser is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidate_NullUser.");
        }
    }

    /**
     * Tests validate(RegistrationInfo registration, ExternalUser user, FullProjectData project) method
     * with null FullProjectData project,
     * IllegalArgumentException should be thrown.
     */
    public void testValidate_NullProject() {
        try {
            validator.validate(info, user, null);
            fail("testValidate_NullProject is failure.");
        } catch (IllegalArgumentException iae) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidate_NullProject.");
        }
    }

    /**
     * Tests validate(RegistrationInfo registration, ExternalUser user, FullProjectData project) method
     * with ValidationProcessingException thrown.
     */
    public void testValidate_ValidationProcessingException() {
        try {
            configValidator = new MockAbstractConfigurableValidator("test");
            ((MockAbstractConfigurableValidator) configValidator).setThrowException(true);
            validator = new DataValidationRegistrationValidator(teamManager, projectServices,
                    banManager, null, configValidator);
            validator.validate(info, user, project);
            fail("testValidate_ValidationProcessingException is failure.");
        } catch (ValidationProcessingException vpe) {
            // pass
        } catch (Exception e) {
            fail("Unknown exception occurs in testValidate_ValidationProcessingException.");
        }
    }

}