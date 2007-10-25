/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.util;
import com.topcoder.registration.validation.validators.simple.MemberNotBarredValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotRegisteredWithRoleForProjectValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamCaptainWithMembersForProjectValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamMemberForProjectValidator;
import com.topcoder.registration.validation.validators.conditional.ProjectCategoryConditionalValidator;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;

import junit.framework.TestCase;
/**
 * <p>
 * Unit test cases for class OrValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class OrValidatorTest extends TestCase {

    /**
     * <p>
     * Represents a valid formed namespace for this class.
     * </p>
     */
    private static final String NAMESPACE = "util.Good";

    /**
     * <p>
     * ConfigurableValidators to be put in this associative validator.
     * </p>
     *
     */
    private ConfigurableValidator[] validators;

    /**
     * <p>
     * Represents a flag as to whether validation should stop on first error, or
     * continue accumulating messages until all validators are asked.
     * </p>
     *
     */
    private boolean shortCircuited;

    /**
     * <p>
     * Represents the resource bundle information used by this validator.
     * </p>
     */
    private BundleInfo bundleInfo;

    /**
     * <p>
     * Represents the RegistrationValidator that acts as the manager for
     * validation in this component.
     * </p>
     *
     */
    private DataValidationRegistrationValidator dataValidationRegistrationValidator;

    /**
     * <p>
     * The OrValidator instance for testing purpose.
     * </p>
     */
    private OrValidator validator;

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
        TestHelper.loadXMLConfig("OrValidator.xml");
        bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo");

        // member is not barred
        MemberNotBarredValidator val1 = new MemberNotBarredValidator(bundleInfo);

        // if project is in ¡°Assembly¡± category, the registrant is not yet a
        // team
        // member.
        MemberNotTeamMemberForProjectValidator val21 = new MemberNotTeamMemberForProjectValidator(
                bundleInfo);
        ProjectCategoryConditionalValidator val2 = new ProjectCategoryConditionalValidator(
                val21, 4);

        // Check that member registering as team captain as is not a team
        // captain
        // on the team already.
        MemberNotRegisteredWithRoleForProjectValidator val3 =
            new MemberNotRegisteredWithRoleForProjectValidator(
                bundleInfo, 3);

        // Check that member is not a team captain with registrants
        MemberNotTeamCaptainWithMembersForProjectValidator val4 =
            new MemberNotTeamCaptainWithMembersForProjectValidator(
                bundleInfo, 3);
        validators = new ConfigurableValidator[] {val1, val2, val3, val4 };
        dataValidationRegistrationValidator = new DataValidationRegistrationValidator();
        validator = new OrValidator(validators, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
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
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies OrValidator subclasses AbstractObjectValidator.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("OrValidator does not subclass AbstractObjectValidator.",
                new OrValidator(NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies OrValidator subclasses
     * com.topcoder.util.datavalidator.OrValidator.
     * </p>
     */
    public void testInheritance2() {
        assertTrue(
                "OrValidator does not subclass com.topcoder.util.datavalidator.OrValidator.",
                new OrValidator(NAMESPACE) instanceof com.topcoder.util.datavalidator.OrValidator);
    }

    /**
     * <p>
     * Tests ctor OrValidator(ConfigurableValidator[], boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created OrValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor1() {
        validator = new OrValidator(validators, shortCircuited);
        assertNotNull("Failed to create a new OrValidator instance.", validator);
    }

    /**
     * <p>
     * Tests ctor OrValidator(ConfigurableValidator[], boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the validators is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullInnervalidators() {
        try {
            validator = new OrValidator(null, shortCircuited);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(ConfigurableValidator[], boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the validators contains null elements
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullInnervalidatorsEelements() {
        try {
            validator = new OrValidator(new ConfigurableValidator[] {
                validators[0], null }, shortCircuited);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created OrValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new OrValidator(NAMESPACE);
        assertNotNull("Failed to create a new OrValidator instance.", validator);
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when there is no inner validator can be added into
     * the OrValidator instance.
     * </p>
     *
     * <p>
     * It verifies the newly created AndValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor2_NoInnerValidator() { // ???
        validator = new OrValidator("util.NoInnerValidator");
        assertNotNull("Failed to create a new AndValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
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
            new OrValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor OrValidator(String) for failure.
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
            new OrValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
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
            new OrValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
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
            new OrValidator("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
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
            new OrValidator("util.BadSpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
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
            new OrValidator("util.UnknownObjectFactorySpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }


    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when innerValidator is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidInnerValidator() {
        try {
            new OrValidator("util.InvalidInnerValidator");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when shortCircuited property is missing in the
     * namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingValidatorInfo() {
        try {
            new OrValidator("util.MissingValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor OrValidator(String) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the shortCircuited property value is invalid.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidValidatorInfo() {
        try {
            new OrValidator("util.InvalidValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests setRegistrationValidator(DataValidationRegistrationValidator) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the dataValidationRegistrationValidator is not
     * null.
     * </p>
     *
     * <p>
     * Should have set dataValidationRegistrationValidator field.
     * </p>
     *
     */
    public void testSetRegistrationValidator() {
        validator.setRegistrationValidator(dataValidationRegistrationValidator);
    }

    /**
     * <p>
     * Tests setRegistrationValidator(DataValidationRegistrationValidator) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the dataValidationRegistrationValidator is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testSetRegistrationValidator_Null() {
        try {
            validator.setRegistrationValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getMessage(Object) for accuracy.
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
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testGetMessage_True() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        // Sets [validationInfo.getRegistration().getRoleId()] tobe not [3]
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests OrValidator#getMessage(Object) for accuracy.
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
    public void testGetMessage_False() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 2;
        int volatility = 20;
        double reliability = 50.3;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        validator = new OrValidator(new ConfigurableValidator[] {
            validators[2], validators[3] }, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));
    }

    /**
     * <p>
     * Tests OrValidator#getMessage(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetMessage_Null() {
        try {
            validator.getMessage(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getMessage(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetMessage_NotValidationInfo() {
        try {
            validator.getMessage(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getMessages(Object) for accuracy.
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
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testGetMessages_True() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        // Sets [validationInfo.getRegistration().getRoleId()] tobe not [3]
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        String message[] = validator.getMessages(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests OrValidator#getMessages(Object) for accuracy.
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
    public void testGetMessages_False() {
        Object obj = TestHelper.createValidationInfoForTest();

        // for the ProjectCategoryConditionalValidator
        // Sets validationInfo.project.projectHeader.projectCategory.id
        ((ValidationInfo) obj).getProject().getProjectHeader()
                .getProjectCategory().setId(3);

        // Sets the user information
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        validator = new OrValidator(new ConfigurableValidator[] {
            validators[2], validators[3] }, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String[] messages = validator.getMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 2,
                messages.length);
    }

    /**
     * <p>
     * Tests OrValidator#getMessages(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetMessages_Null() {
        try {
            validator.getMessages(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getMessages(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetMessages_NotValidationInfo() {
        try {
            validator.getMessages(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for accuracy.
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
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testGetAllMessages_ShortCircuitedTrue() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        // Sets [validationInfo.getRegistration().getRoleId()] tobe not [3]
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        validator = new OrValidator(validators, true);
        TestHelper.setDefaultRegistrationValidator(validator,
            "validatorLog.txt");
        String message[] = validator.getAllMessages(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for accuracy.
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
    public void testGetAllMessages_ShortCircuitedFalse() {
        Object obj = TestHelper.createValidationInfoForTest();

        // for the ProjectCategoryConditionalValidator
        // Sets validationInfo.project.projectHeader.projectCategory.id
        ((ValidationInfo) obj).getProject().getProjectHeader()
                .getProjectCategory().setId(3);

        // Sets the user information
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        validator = new OrValidator(new ConfigurableValidator[] {
            validators[2], validators[3] }, true);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String[] messages = validator.getAllMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_ShortCircuitedNull() {
        validator = new OrValidator(validators, true);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        try {
            validator.getAllMessages(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_ShortCircuitedNotValidationInfo() {
        validator = new OrValidator(validators, true);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        try {
            validator.getAllMessages(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for accuracy.
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
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testGetAllMessages_NotShortCircuitedTrue() {
        Object obj = TestHelper.createValidationInfoForTest();

        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        // Sets [validationInfo.getRegistration().getRoleId()] tobe not [3]
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        validator = new OrValidator(validators, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String message[] = validator.getAllMessages(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for accuracy.
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
    public void testGetAllMessages_NotShortCircuitedFalse() {
        Object obj = TestHelper.createValidationInfoForTest();

        // for the ProjectCategoryConditionalValidator
        // Sets validationInfo.project.projectHeader.projectCategory.id
        ((ValidationInfo) obj).getProject().getProjectHeader()
                .getProjectCategory().setId(3);

        // Sets the user information
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        validator = new OrValidator(new ConfigurableValidator[] {
            validators[2], validators[3] }, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String[] messages = validator.getAllMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 2,
                messages.length);
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_NotShortCircuitedNull() {
        validator = new OrValidator(validators, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        try {
            validator.getAllMessages(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_NotShortCircuitedNotValidationInfo() {
        validator = new OrValidator(validators, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        try {
            validator.getAllMessages(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object, int) for accuracy.
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
    public void testGetAllMessages_Limit_True() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        String[] messages = validator.getAllMessages(obj, 10);
        assertNull("Failed to get the error messages correctly.", messages);
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object, int) for accuracy.
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
    public void testGetAllMessages_Limit_False() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 2;
        int volatility = 20;
        double reliability = 50.3;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        validator = new OrValidator(new ConfigurableValidator[] {
            validators[2], validators[3] }, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String[] messages = validator.getAllMessages(obj, 10);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 2,
                messages.length);
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object, int) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the max number of messages is negative,
     * messagelimit parameter should be ignored.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_Limit_NegativeLimit() {
        Object object = TestHelper.createValidationInfoForTest();
        try {
            validator.getAllMessages(object, -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }

    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object, int) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_Limit_Null() {
        try {
            validator.getAllMessages(null, 10);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#getAllMessages(Object, int) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testGetAllMessages_Limit_NotValidationInfo() {
        try {
            validator.getAllMessages(new Object(), 10);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#valid(Object) for accuracy.
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
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testValid_True() {
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        // Sets [validationInfo.getRegistration().getRoleId()] tobe not [3]
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        assertTrue("Failed to get the error messages priority.", validator
                .valid(obj));
    }

    /**
     * <p>
     * Tests OrValidator#valid(Object) for accuracy.
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
        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 2;
        int volatility = 20;
        double reliability = 50.3;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        validator = new OrValidator(new ConfigurableValidator[] {
            validators[2], validators[3] }, false);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        assertFalse("Failed to get the error messages correctly.", validator
                .valid(obj));
    }

    /**
     * <p>
     * Tests OrValidator#valid(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValid_Null() {
        try {
            validator.valid(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests OrValidator#valid(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the obj is not ValidationInfo.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testValid_NotValidationInfo() {
        try {
            validator.valid(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }
}
