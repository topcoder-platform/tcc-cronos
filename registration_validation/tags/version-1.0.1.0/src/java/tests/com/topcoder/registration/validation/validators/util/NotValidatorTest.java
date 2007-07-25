/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.util;

import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.registration.validation.validators.simple.MemberNotBarredValidator;
import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.RegistrationValidationHelper;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.BundleInfo;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;
import com.topcoder.registration.validation.ValidationProcessingException;

import junit.framework.TestCase;
/**
 * <p>
 * Unit test cases for class NotValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NotValidatorTest extends TestCase {
    /**
     * <p>
     * Represents a valid formed namespace for this class.
     * </p>
     */
    private static final String NAMESPACE = "util.Good";

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
     * Represents the validator that will be negated in this validator.
     * </p>
     * <p>
     * It is set in the constructor to a non-null value, and will never change.
     * </p>
     *
     */
    private ConfigurableValidator innerValidator;

    /**
     * <p>
     * The NotValidator instance for testing purpose.
     * </p>
     */
    private NotValidator validator;

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
        TestHelper.loadXMLConfig("NotValidator.xml");
        bundleInfo = RegistrationValidationHelper
                .createBundleInfo("test.BundleInfo");

        // member is not barred
        innerValidator = new MemberNotBarredValidator(bundleInfo);
        dataValidationRegistrationValidator = new DataValidationRegistrationValidator();
        validator = new NotValidator(innerValidator, bundleInfo);
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
     * Verifies NotValidator subclasses AbstractObjectValidator.
     * </p>
     */
    public void testInheritance1() {
        assertTrue("NotValidator does not subclass AbstractObjectValidator.",
            new NotValidator(NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies NotValidator subclasses ConfigurableValidator.
     * </p>
     */
    public void testInheritance2() {
        assertTrue("NotValidator does not subclass ConfigurableValidator.",
            new NotValidator(NAMESPACE) instanceof ConfigurableValidator);
    }

    /**
     * <p>
     * Tests ctor NotValidator(ConfigurableValidator, boolean) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created NotValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor1() {
        validator = new NotValidator(innerValidator, bundleInfo);
        assertNotNull("Failed to create a new NotValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor NotValidator(ConfigurableValidator, boolean) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the inner validator is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullInnervalidators() {
        try {
            validator = new NotValidator(null, bundleInfo);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor NotValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created NotValidator instance should not be null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new NotValidator(NAMESPACE);
        assertNotNull("Failed to create a new NotValidator instance.",
                validator);
    }


    /**
     * <p>
     * Tests ctor NotValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created NotValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor2_NoBundleName() {
        validator = new NotValidator("util.NoBundleName");
        assertNotNull(
                "Failed to create NotValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor NotValidator(String) for failure.
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
            new NotValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor NotValidator(String) for failure.
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
            new NotValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor NotValidator(String) for failure.
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
            new NotValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor NotValidator(String) for failure.
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
            new NotValidator("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor NotValidator(String) for failure.
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
            new NotValidator("util.BadSpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor NotValidator(String) for failure.
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
            new NotValidator("util.UnknownObjectFactorySpecify");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }


    /**
     * <p>
     * Tests ctor NotValidator(String) for failure.
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
            new NotValidator("util.InvalidInnerValidator");
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
     * Tests NotValidator#getMessage(Object) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(3, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests NotValidator#getMessage(Object) for accuracy.
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
        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));
    }

    /**
     * <p>
     * Tests NotValidator#getMessage(Object) for failure.
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
     * Tests NotValidator#getMessage(Object) for failure.
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
     * Tests NotValidator#getMessage(Object) for failure.
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
        // Sets the defaultMessage to a bad formed template
        bundleInfo.setDefaultMessage("badTemplate.txt");
        // Creates NotValidator instance using the bundleInfo
        validator = new NotValidator(innerValidator, bundleInfo);
        // Sets the dataValidationRegistrationValidator to the validator
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object obj = TestHelper.createValidationInfoForTest();
        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        try {
            validator.getMessage(obj);
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests NotValidator#getMessages(Object) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(3, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String message[] = validator.getMessages(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests NotValidator#getMessages(Object) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String[] messages = validator.getMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
     * <p>
     * Tests NotValidator#getMessages(Object) for failure.
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
     * Tests NotValidator#getMessages(Object) for failure.
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
     * Tests NotValidator#getAllMessages(Object) for accuracy.
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
        Object obj = TestHelper.createValidationInfoForTest();

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(3, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String message[] = validator.getAllMessages(obj);
        assertNull("Failed to get the error messages priority.", message);
    }

    /**
     * <p>
     * Tests NotValidator#getAllMessages(Object) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1",
            "firstName", "lastName", "email");
        ((ValidationInfo) obj).setUser(user);

        String[] messages = validator.getAllMessages(obj);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
     * <p>
     * Tests NotValidator#getAllMessages(Object) for failure.
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
    public void testGetAllMessages_Null() {
        try {
            validator.getAllMessages(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests NotValidator#getAllMessages(Object) for failure.
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
        try {
            validator.getAllMessages(new Object());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests NotValidator#getAllMessages(Object, int) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(3, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String[] messages = validator.getAllMessages(obj);
        assertNull("Failed to get the error messages correctly.", messages);
    }

    /**
     * <p>
     * Tests NotValidator#getAllMessages(Object, int) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String[] messages = validator.getAllMessages(obj, 10);
        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);
    }

    /**
    * <p>
    * Tests AbstractConfigurableValidator#getAllMessages(Object, int) for
    * accuracy.
    * </p>
    *
    * <p>
    * It tests the case when the max number of messages is negative,
    * messagelimit parameter should be ignored.
    * </p>
    *
    * <p>
    * Should have ignored the  messageLimit parameter and returned null if obj is valid.
    * </p>
    *
    */
    public void testGetAllMessages_Limit_NegativeLimit_True() {
        Object obj = TestHelper.createValidationInfoForTest();
        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(3, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String[] messages = validator.getAllMessages(obj, 10);

        assertNull("Failed to get the error messages correctly.", messages);
    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator(Object, int) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the max number of messages is negative,
     * messagelimit parameter should be ignored.
     * </p>
     *
     * <p>
    * Should have ignored the  messageLimit parameter and returned an
    * error message if obj is invalid.
     * </p>
     *
     */
    public void testGetAllMessages_Limit_NegativeLimit_False() {
        Object obj = TestHelper.createValidationInfoForTest();
        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(2, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);
        String[] messages = validator.getAllMessages(obj, -10);

        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
            messages.length);
    }


    /**
     * <p>
     * Tests NotValidator#getAllMessages(Object, int) for failure.
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
     * Tests NotValidator#getAllMessages(Object, int) for failure.
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
     * Tests NotValidator#valid(Object) for accuracy.
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

        // change validationInfo.registrationInfo.userid
        ExternalUserImpl user = new ExternalUserImpl(3, "handle1", "firstName",
                "lastName", "email");
        ((ValidationInfo) obj).setUser(user);

        // Sets [validationInfo.getRegistration().getRoleId()] tobe not [3]
        ((ValidationInfo) obj).getRegistration().setRoleId(1);
        assertTrue("Failed to get the error messages priority.", validator
                .valid(obj));
    }

    /**
     * <p>
     * Tests NotValidator#valid(Object) for accuracy.
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

    /**
     * <p>
     * Tests NotValidator#valid(Object) for failure.
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
     * Tests NotValidator#valid(Object) for failure.
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
