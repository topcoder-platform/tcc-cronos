/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.conditional;

import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class RegisteringResourceRoleConditionalValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RegisteringResourceRoleConditionalValidatorTest extends TestCase {
    /**
     * <p>
     * Represents a valid formed namespace for this class.
     * </p>
     */
    private static final String NAMESPACE = "conditional.Good";

    /**
     * <p>
     * Represents the validator that will be called if the condition in this
     * validator is meet.
     */
    private ConfigurableValidator innerValidator;

    /**
     * <p>
     * Represents the ID of the role the registration would have to perform the
     * validation.
     * </p>
     *
     */
    private long roleId = 3;

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
     * The RegisteringResourceRoleConditionalValidator instance for testing
     * purpose.
     * </p>
     */
    private RegisteringResourceRoleConditionalValidator validator;

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
        TestHelper.loadXMLConfig("InnerValidator.xml");
        TestHelper
                .loadXMLConfig("RegisteringResourceRoleConditionalValidator.xml");
        innerValidator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
                "simple.GoodInnerValidator");
        dataValidationRegistrationValidator = new DataValidationRegistrationValidator();
        validator = new RegisteringResourceRoleConditionalValidator(NAMESPACE);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *             when error occurs
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
     * Verifies RegisteringResourceRoleConditionalValidator subclasses
     * AbstractObjectValidator.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "RegisteringResourceRoleConditionalValidator does not subclass AbstractObjectValidator.",
                new RegisteringResourceRoleConditionalValidator(NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Tests ctor
     * RegisteringResourceRoleConditionalValidator(ConfigurableValidator, long)
     * for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created RegisteringResourceRoleConditionalValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor1() {
        validator = new RegisteringResourceRoleConditionalValidator(
                innerValidator, roleId);
        assertNotNull(
                "Failed to create a new RegisteringResourceRoleConditionalValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor
     * RegisteringResourceRoleConditionalValidator(ConfigurableValidator, long)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when the innerValidator is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullInnerValidator() {
        try {
            validator = new RegisteringResourceRoleConditionalValidator(null,
                    roleId);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor
     * RegisteringResourceRoleConditionalValidator(ConfigurableValidator, long)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when the roleId is negative.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NegativeMinimumReliability() {
        try {
            validator = new RegisteringResourceRoleConditionalValidator(
                    innerValidator, -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created RegisteringResourceRoleConditionalValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new RegisteringResourceRoleConditionalValidator(NAMESPACE);
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.BadSpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.UnknownObjectFactorySpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when innerValidator property is missing in the
     * namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingInnerValidator() {
        try {
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.MissingInnerValidator");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
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
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.InvalidInnerValidator");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when roleId property is missing in the namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingValidatorInfo() {
        try {
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.MissingValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the roleId property value is invalid.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidValidatorInfo() {
        try {
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.InvalidValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor RegisteringResourceRoleConditionalValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the roleId is negative.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_NegativeValidatorInfo() {
        try {
            new RegisteringResourceRoleConditionalValidator(
                    "conditional.NegativeValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the condition is false and the obj is invalid.
     * </p>
     *
     * <p>
     * Should have returned null whether obj is valid or invalid.
     * </p>
     *
     */
    public void testGetMessage_ConditionFalseValidationFalse() {

        Object obj = TestHelper.createValidationInfoForTest();
        // Sets validationInfo.registrationInfo.roleId
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        // Sets the user information
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 1;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the condition is false and the obj is invalid.
     * </p>
     *
     * <p>
     * Should have returned null whether obj is valid or invalid.
     * </p>
     *
     */
    public void testGetMessage_ConditionFalseValidationTrue() {

        Object obj = TestHelper.createValidationInfoForTest();
        // Sets validationInfo.registrationInfo.roleId
        ((ValidationInfo) obj).getRegistration().setRoleId(1);

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

        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the condition is true and the obj is valid.
     * </p>
     *
     * <p>
     * Should have returned null if obj is valid.
     * </p>
     *
     */
    public void testGetMessage_ConditionTrueValidationTrue() {

        Object obj = TestHelper.createValidationInfoForTest();

        // Sets validationInfo.registrationInfo.roleId
        ((ValidationInfo) obj).getRegistration().setRoleId(3);

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

        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages correctly.", message);
    }

    /**
     * <p>
     * Tests RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the condition is true and the obj is invalid.
     * </p>
     *
     * <p>
     * Should have returned an error message if obj is invalid.
     * </p>
     *
     */
    public void testGetMessage_ConditionTrueValidationFalse() {

        Object obj = TestHelper.createValidationInfoForTest();

        // Sets validationInfo.registrationInfo.roleId
        ((ValidationInfo) obj).getRegistration().setRoleId(3);
        // Sets the user information
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 1;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));
    }

    /**
     * <p>
     * Tests RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * failure.
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
     * Tests RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * failure.
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
     * Tests
     * RegisteringResourceRoleConditionalValidator#getMessage(Object) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the validation is false and the template of the
     * inner validator is bad formed.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testGetMessage_BadTemplate() {
        innerValidator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
            "simple.BadDefaultMessageInnerValidator");
        validator = new RegisteringResourceRoleConditionalValidator(innerValidator,
                roleId);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object obj = TestHelper.createValidationInfoForTest();

        // Sets validationInfo.registrationInfo.roleId
        ((ValidationInfo) obj).getRegistration().setRoleId(3);
        // Sets the user information
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 2300;
        int numRatings = 1;
        int volatility = 20;
        double reliability = 80.2;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ratingInfo.getReliability();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        try {
            validator.getMessage(obj);
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
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
}
