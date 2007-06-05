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
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.cronos.onlinereview.external.RatingType;

import junit.framework.TestCase;
/**
 * <p>
 * Unit test cases for class NullValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NullValidatorTest extends TestCase {
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
     * The NullValidator instance for testing purpose.
     * </p>
     */
    private NullValidator validator;

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
        validator = new NullValidator(bundleInfo);
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
     * Verifies NullValidator subclasses AbstractObjectValidator.
     * </p>
     */
    public void testInheritance1() {
        assertTrue(
                "NullValidator does not subclass AbstractObjectValidator.",
                new NullValidator(bundleInfo) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies NullValidator subclasses
     * com.topcoder.util.datavalidator.NullValidator.
     * </p>
     */
    public void testInheritance2() {
        assertTrue(
                "NullValidator does not subclass com.topcoder.util.datavalidator.NullValidator.",
                new NullValidator(bundleInfo) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Tests ctor NullValidator() for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created NullValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor1() {
        validator = new NullValidator();
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        assertNotNull("Failed to create a new NullValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor NullValidator(BundleInfo) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created NullValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new NullValidator(bundleInfo);
        assertNotNull("Failed to create a new NullValidator instance.",
                validator);
    }
    /**
     * <p>
     * Tests ctor NullValidator(BundleInfo) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the bundleInf is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_NullBundleInfo() {
        try {
            new NullValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
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
     * Tests NullValidator#getMessage(Object) for accuracy.
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
        String message = validator.getMessage(null);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests NullValidator#getMessage(Object) for accuracy.
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
        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));
    }

    /**
     * <p>
     * Tests NullValidator#getMessage(Object) for failure.
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
     * Tests NullValidator#getMessages(Object) for accuracy.
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
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String message[] = validator.getMessages(null);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests NullValidator#getMessages(Object) for accuracy.
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
        String[] messages = validator.getMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
     * <p>
     * Tests NullValidator#getMessages(Object) for failure.
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
     * Tests NullValidator#getMessage(Object) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the template is bad formed when generating
     * message.
     * </p>
     *
     * <p>
     * Should have thrown ValidationProcessingException.
     * </p>
     *
     */
    public void testGetMessage_BadTemplate() {
        bundleInfo.setDefaultMessage("badTemplate.txt");
        validator = new NullValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        Object obj = TestHelper.createValidationInfoForTest();
        try {
            validator.getMessage(obj);
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests NullValidator#getAllMessages(Object) for accuracy.
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
    public void testGetAllMessages_True() {
        String message[] = validator.getAllMessages(null);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests NullValidator#getAllMessages(Object) for accuracy.
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
    public void testGetAllMessages_False() {
        Object obj = TestHelper.createValidationInfoForTest();
        validator = new NullValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
        String[] messages = validator.getAllMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
     * <p>
     * Tests NullValidator#getAllMessages(Object) for failure.
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
    public void testGetAllMessages_NotValidationInfo() {
        validator = new NullValidator(bundleInfo);
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
     * Tests NullValidator#getAllMessages(Object, int) for accuracy.
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
        String[] messages = validator.getAllMessages(null, 10);
        assertNull("Failed to get the error messages correctly.", messages);
    }

    /**
     * <p>
     * Tests NullValidator#getAllMessages(Object, int) for accuracy.
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
        String[] messages = validator.getAllMessages(obj, 10);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
     * <p>
     * Tests NullValidator#getAllMessages(Object, int) for failure.
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
     * Tests NullValidator#getAllMessages(Object, int) for failure.
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
     * Tests NullValidator#valid(Object) for accuracy.
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
        assertTrue("Failed to get the error messages priority.", validator
                .valid(null));
    }

    /**
     * <p>
     * Tests NullValidator#valid(Object) for accuracy.
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
        assertFalse("Failed to get the error messages correctly.", validator
                .valid(obj));
    }
}
