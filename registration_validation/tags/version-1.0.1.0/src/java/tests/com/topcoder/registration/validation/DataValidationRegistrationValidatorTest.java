/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.RatingType;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.management.ban.BanManager;
import com.topcoder.management.team.TeamManager;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.management.team.impl.MockTeamManagerImpl;
import com.topcoder.management.ban.manager.MockBanManager;
import com.topcoder.project.service.impl.MockProjectServicesImpl;
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.util.log.Log;
import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class DataValidationRegistrationValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DataValidationRegistrationValidatorTest extends TestCase {

    /**
     * Represents the default namespace used by the default constructor to
     * access configuration info in the construction.
     *
     */
    public static final String DEFAULT_NAMESPACE =
        "com.topcoder.registration.validation.DataValidationRegistrationValidator";

    /**
     * <p>
     * The DataValidationRegistrationValidator instance for testing purpose.
     * </p>
     */
    private DataValidationRegistrationValidator registrationValidator;


    /**
     * <p>
     * Represents the TeamManager instance that is used to retrieve teams.
     * </p>
     *
     */
    private TeamManager teamManager;

    /**
     * <p>
     * Represents the ProjectServices instance that is used to obtain
     * information about current projects.
     * </p>
     *
     */
    private ProjectServices projectServices;

    /**
     * <p>
     * Represents the BanManager instance that is used to check bans on
     * resources.
     * </p>
     *
     */
    private BanManager banManager;

    /**
     * <p>
     * Used extensively in the class DataValidationRegistrationValidator to log information.
     * </p>
     *
     */
    private Log logger;

    /**
     * <p>
     * Represents the actual validator that will perform the validation.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private ConfigurableValidator validator;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfig();

        TestHelper.loadXMLConfig("DataValidationRegistrationValidator.xml");
        TestHelper.loadXMLConfig("Document_Generator.xml");
        TestHelper
                .loadXMLConfig("MemberMinimumRatingForRatingTypeValidator.xml");

        teamManager = new MockTeamManagerImpl();
        projectServices = new MockProjectServicesImpl();
        banManager = new MockBanManager();
        logger = TestHelper.getLogger("validatorLog.txt");

        validator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
                "test.AbstractConfigurableValidator");
        registrationValidator = new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, logger, validator);
    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DataValidationRegistrationValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor1() {
        registrationValidator = new DataValidationRegistrationValidator();
        assertNotNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator);
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DataValidationRegistrationValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor2() {
        registrationValidator = new DataValidationRegistrationValidator(
                DEFAULT_NAMESPACE);
        assertNotNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator);
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_NullNamespace() {
        try {
            new DataValidationRegistrationValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is empty.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_EmptyNamespace() {
        try {
            new DataValidationRegistrationValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is full of space.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_TrimmedEmptyNamespace() {
        try {
            new DataValidationRegistrationValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is unknown.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_UnknownNamespace() {
        try {
            new DataValidationRegistrationValidator("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the InvalidClassSpecificationException is thrown
     * by ObjectFactory.createObject(String)
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_BadObjectFactorySpecify() {
        try {
            new DataValidationRegistrationValidator(
                    "test.DataValidationRegistrationValidator.BadSpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the namespace is unknown.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_UnknownObjectFactorySpecify() {
        try {
            new DataValidationRegistrationValidator(
                    "test.DataValidationRegistrationValidator.UnknownObjectFactorySpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the validator property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingValidator() {
        try {
            new DataValidationRegistrationValidator("test.MissingValidatorKey");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when validator is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidValidator() {
        try {
            new DataValidationRegistrationValidator("test.InvalidValidator");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the banManager property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingBanManager() {
        try {
            new DataValidationRegistrationValidator("test.MissingBanManagerKey");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationBanManager(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when banManager is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidBanManager() {
        try {
            new DataValidationRegistrationValidator("test.InvalidBanManager");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the projectServices property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingProjectServices() {
        try {
            new DataValidationRegistrationValidator(
                    "test.MissingProjectServicesKey");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationProjectServices(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when projectServices is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidProjectServices() {
        try {
            new DataValidationRegistrationValidator(
                    "test.InvalidProjectServices");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the teamManager property is missing.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingTeamManager() {
        try {
            new DataValidationRegistrationValidator(
                    "test.MissingTeamManagerKey");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationteamManager(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when teamManager is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidteamManager() {
        try {
            new DataValidationRegistrationValidator("test.InvalidTeamManager");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the loggerName property is missing.
     * </p>
     *
     * <p>
     * It verifies the newly created DataValidationRegistrationValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor2_NoLoggerName() {
        registrationValidator = new DataValidationRegistrationValidator(
                "test.NoLoggerName");
        assertNotNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator);
        assertNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator.getLog());
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationLoggerName(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when loggerName is not configured properly.
     * </p>
     *
     * <p>
     * It verifies the newly created DataValidationRegistrationValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor2_InvalidLoggerName() {
        registrationValidator = new DataValidationRegistrationValidator(
                "test.EmptyLoggerName");
        assertNotNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator);
        assertNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator.getLog());
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(TeamManager,
     * ProjectServices, BanManager, Log, ConfigurableValidator ) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created DataValidationRegistrationValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor3() {
        registrationValidator = new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, logger, validator);
        assertNotNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator);
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(TeamManager,
     * ProjectServices, BanManager, Log, ConfigurableValidator ) for accuracy.
     * </p>
     * <p>
     * It tests the case when the logger is null
     * </p>
     *
     * <p>
     * It verifies the newly created DataValidationRegistrationValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor3_NullLogger() {
        registrationValidator = new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, logger, validator);
        assertNotNull(
                "Failed to create a new DataValidationRegistrationValidator instance.",
                registrationValidator);
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(TeamManager,
     * ProjectServices, BanManager, Log, ConfigurableValidator ) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the teamManager is null
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor3_NullTeamManager() {
        try {
            new DataValidationRegistrationValidator(null, projectServices,
                    banManager, logger, validator);

            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(TeamManager,
     * ProjectServices, BanManager, Log, ConfigurableValidator ) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the projectServices is null
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor3_NullProjectServices() {
        try {
            new DataValidationRegistrationValidator(teamManager, null,
                    banManager, logger, validator);

            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(TeamManager,
     * ProjectServices, BanManager, Log, ConfigurableValidator ) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the banManager is null
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor3_NullBanManager() {
        try {
            new DataValidationRegistrationValidator(teamManager,
                    projectServices, null, logger, validator);

            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor DataValidationRegistrationValidator(TeamManager,
     * ProjectServices, BanManager, Log, ConfigurableValidator ) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the validator is null
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor3_NullValidator() {
        try {
            new DataValidationRegistrationValidator(teamManager,
                    projectServices, banManager, logger, null);

            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#getBanManager() for accuracy.
     * </p>
     *
     *
     * <p>
     * Should have gotten the banManager correctly.
     * </p>
     *
     */
    public void testGetBanManager() {
        assertEquals("Failed to get banManager correctly.", banManager,
                registrationValidator.getBanManager());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#getLog() for accuracy.
     * </p>
     *
     *
     * <p>
     * Should have gotten the logger correctly.
     * </p>
     *
     */
    public void testGetLog() {
        registrationValidator = new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, logger, validator);

        assertEquals("Failed to get teamManager correctly.", logger,
                registrationValidator.getLog());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#getTeamManager() for accuracy.
     * </p>
     *
     *
     * <p>
     * Should have gotten the logger correctly.
     * </p>
     *
     */
    public void testGetLog_NullLogger() {
        registrationValidator = new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, null, validator);

        assertNull("Failed to get teamManager correctly.",
                registrationValidator.getLog());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#getProjectServices() for
     * accuracy.
     * </p>
     *
     *
     * <p>
     * Should have gotten the projectServices correctly.
     * </p>
     *
     */
    public void testGetProjectServices() {
        assertNotNull("Failed to get teamManager correctly.",
                registrationValidator.getProjectServices());
        assertEquals("Failed to get teamManager correctly.", projectServices,
                registrationValidator.getProjectServices());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#getTeamManager() for accuracy.
     * </p>
     *
     *
     * <p>
     * Should have gotten the teamManager correctly.
     * </p>
     *
     */
    public void testGetTeamManager() {
        assertNotNull("Failed to get teamManager correctly.",
                registrationValidator.getTeamManager());
        assertEquals("Failed to get teamManager correctly.", teamManager,
                registrationValidator.getTeamManager());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#getValidator() for accuracy.
     * </p>
     *
     *
     * <p>
     * Should have gotten the validator correctly.
     * </p>
     *
     */
    public void testGetValidator() {
        registrationValidator = new DataValidationRegistrationValidator(
                teamManager, projectServices, banManager, null, validator);

        assertEquals("Failed to get teamManager correctly.", validator,
                registrationValidator.getValidator());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#validate(RegistrationInfo,
     * ExternalUser, FullProjectData) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the obj is valid.
     * </p>
     *
     * <p>
     * Should have returned null if obj is valid.
     * </p>
     *
     */
    public void testValid_True() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 5;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = validationInfo.getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        assertTrue(
                "Failed to determine if the given Object is valid correctly.",
                registrationValidator.validate(
                        validationInfo.getRegistration(),
                        validationInfo.getUser(), validationInfo.getProject())
                        .isSuccessful());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#validate(RegistrationInfo,
     * ExternalUser, FullProjectData) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the obj is invalid.
     * </p>
     *
     * <p>
     * Should have returned an error message if obj is invalid.
     * </p>
     *
     */
    public void testValid_False() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();
        assertFalse(
                "Failed to determine if the given Object is valid correctly.",
                registrationValidator.validate(
                        validationInfo.getRegistration(),
                        validationInfo.getUser(), validationInfo.getProject())
                        .isSuccessful());
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#validate(RegistrationInfo,
     * ExternalUser, FullProjectData) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the registration is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValid_NullRegistration() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();
        try {
            registrationValidator.validate(null, validationInfo.getUser(),
                    validationInfo.getProject());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests DataValidationRegistrationValidator#validate(RegistrationInfo,
     * ExternalUser, FullProjectData) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the externalUser is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValid_NullExternalUser() {
        ValidationInfo validationInfo = TestHelper.createValidationInfoForTest();
        try {
            registrationValidator.validate(validationInfo.getRegistration(),
                    null, validationInfo.getProject());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

}
