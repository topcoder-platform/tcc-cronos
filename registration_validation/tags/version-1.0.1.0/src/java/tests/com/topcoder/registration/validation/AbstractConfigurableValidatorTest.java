/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation;

import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.RatingType;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;

import java.util.Locale;
import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class AbstractConfigurableValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AbstractConfigurableValidatorTest extends TestCase {

    /**
     * <p>
     * This represents the resource bundle information used by this validator.
     * </p>
     */
    private BundleInfo bundleInfo;

    /**
     * <p>
     * This represents the validation message to use for the underlying
     * validator.
     * </p>
     *
     */
    private String validationMessage = "The validation is failed.";

    /**
     * <p>
     * The AbstractConfigurableValidator instance for testing purpose.
     * </p>
     */
    private AbstractConfigurableValidator validator;

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
        bundleInfo = new BundleInfo();
        Locale locale = new Locale("en", "ca", "Traditional_WIN");
        bundleInfo.setBundle("myBundle", locale);
        bundleInfo.setDefaultMessage("defaultMessage");
        bundleInfo.setMessageKey("messageKey");

        validator = new MockAbstractConfigurableValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");
    }

    /**
     * Clears the testing environment.
     *
     * @throws Exception
     *             when error occurs
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
     * Verifies AbstractConfigurableValidator subclasses
     * AbstractObjectValidator.
     * </p>
     */
    public void testInheritance1() {
        assertTrue(
                "AbstractConfigurableValidator does not subclass AbstractObjectValidator.",
                new MockAbstractConfigurableValidator(bundleInfo) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     *
     * <p>
     * Verifies AbstractConfigurableValidator subclasses ConfigurableValidator.
     * </p>
     */
    public void testInheritance2() {
        assertTrue(
                "AbstractConfigurableValidator does not subclass ConfigurableValidator.",
                new MockAbstractConfigurableValidator(bundleInfo) instanceof ConfigurableValidator);
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(BundleInfo)
     * for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created AbstractConfigurableValidator instance
     * should not be null.
     * </p>
     *
     */
    public void testCtor1() {
        validator = new MockAbstractConfigurableValidator(bundleInfo);
        assertNotNull(
                "Failed to create a new AbstractConfigurableValidator instance.",
                validator);
        BundleInfo bundleInfoField = validator.getBundleInfo();

        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(BundleInfo)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when the bundleInfo is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullBundleInfo() {
        bundleInfo = null;
        try {
            new MockAbstractConfigurableValidator(bundleInfo);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(BundleInfo)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when the locale of the bundleInfo is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullLocale() {
        bundleInfo = new BundleInfo();
        // bundleInfo.setBundle("", Locale.getDefault());

        // bundleInfo.setBundle("myBundle", Locale.getDefault());
        bundleInfo.setDefaultMessage("defaultMessage");
        bundleInfo.setMessageKey("messageKey");

        try {
            new MockAbstractConfigurableValidator(bundleInfo);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(BundleInfo)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when the defaultMessage of the bundleInfo is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullDefaultMessage() {
        bundleInfo = new BundleInfo();
        bundleInfo.setBundle("myBundle", Locale.getDefault());
        // bundleInfo.setDefaultMessage("defaultMessage");
        //bundleInfo.setMessageKey("messageKey");

        try {
            new MockAbstractConfigurableValidator(bundleInfo);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created AbstractConfigurableValidator instance
     * should not be null.
     * </p>
     *
     */
    public void testCtor2() {

        validator = new MockAbstractConfigurableValidator(validationMessage);
        assertNotNull(
                "Failed to create a new AbstractConfigurableValidator instance.",
                validator);
        assertEquals("Failed to initialize the validationMessage field.",
                validationMessage, validator.getValidationMessage());

    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the validationMessage is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_NullMessageKey() {

        validationMessage = null;
        try {
            new MockAbstractConfigurableValidator(validationMessage);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the validationMessage is empty.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_EmptyMessageKey() {

        validationMessage = "";
        try {
            new MockAbstractConfigurableValidator(validationMessage);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor
     * AbstractConfigurableValidator#AbstractConfigurableValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the validationMessage is full of space.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor2_TrimmedEmptyMessageKey() {

        validationMessage = "  ";
        try {
            new MockAbstractConfigurableValidator(validationMessage);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessage(Object) for accuracy.
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
    public void testGetMessage_True() {

        Object object = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DEVELOPMENT;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = ((ValidationInfo) object).getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        String message = validator.getMessage(object);

        assertNull("Failed to get the error message correctly.", message);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessage(Object) for accuracy.
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

        Object object = TestHelper.createValidationInfoForTest();
        String message = validator.getMessage(object);

        assertNotNull("Failed to get the error message correctly.", message);
        assertTrue("Failed to get the error message correctly.", (message
                .trim().length() > 0));

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessage(Object) for accuracy.
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
    public void testGetMessage_False_EmptyMessage() {

        bundleInfo.setMessageKey("emptyMessageKey");
        validator = new MockAbstractConfigurableValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object object = TestHelper.createValidationInfoForTest();
        String message = validator.getMessage(object);

        assertNotNull("Failed to get the error message correctly.", message);
        assertTrue("Failed to get the error message correctly.", (message
                .trim().length() > 0));

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessage(Object) for failure.
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
     * Tests AbstractConfigurableValidator#getMessage(Object) for failure.
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
     * to JUnit
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
     * Tests AbstractConfigurableValidator#getMessages(Object) for accuracy.
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
    public void testGetMessages_True() {
        Object object = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DEVELOPMENT;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = ((ValidationInfo) object).getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        String[] messages = validator.getMessages(object);

        assertNull("Failed to get the error messages correctly.", messages);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessages(Object) for accuracy.
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

        Object object = TestHelper.createValidationInfoForTest();
        String[] messages = validator.getMessages(object);

        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessages(Object) for accuracy.
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
    public void testGetMessages_False_EmptyMessage() {

        bundleInfo.setMessageKey("emptyMessageKey");
        validator = new MockAbstractConfigurableValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object object = TestHelper.createValidationInfoForTest();
        String[] messages = validator.getMessages(object);

        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getMessages(Object) for failure.
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
     * Tests AbstractConfigurableValidator#getMessages(Object) for failure.
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
     * Tests AbstractConfigurableValidator#getAllMessages(Object) for accuracy.
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
    public void testGetAllMessages_True() {

        Object object = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DEVELOPMENT;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = ((ValidationInfo) object).getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        String[] messages = validator.getAllMessages(object);

        assertNull("Failed to get the error messages correctly.", messages);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getAllMessages(Object) for accuracy.
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

        Object object = TestHelper.createValidationInfoForTest();
        String[] messages = validator.getAllMessages(object);

        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getAllMessages(Object) for accuracy.
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
    public void testGetAllMessages_False_EmptyMessage() {

        bundleInfo.setMessageKey("emptyMessageKey");
        validator = new MockAbstractConfigurableValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object object = TestHelper.createValidationInfoForTest();
        String[] messages = validator.getAllMessages(object);

        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
                messages.length);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getAllMessages(Object) for failure.
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
     * Tests AbstractConfigurableValidator#getAllMessages(Object) for failure.
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
     * Tests AbstractConfigurableValidator#getAllMessages(Object, int) for
     * accuracy.
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
        Object object = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DEVELOPMENT;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = ((ValidationInfo) object).getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        String[] messages = validator.getAllMessages(object, 10);

        assertNull("Failed to get the error messages correctly.", messages);

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getAllMessages(Object, int) for
     * accuracy.
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
        Object object = TestHelper.createValidationInfoForTest();
        String[] messages = validator.getAllMessages(object, 10);

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
        Object object = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DEVELOPMENT;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = ((ValidationInfo) object).getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);
        String[] messages = validator.getAllMessages(object, 10);

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
        Object object = TestHelper.createValidationInfoForTest();
        String[] messages = validator.getAllMessages(object, -10);

        assertNotNull("Failed to get the error messages correctly.", messages);
        assertEquals("Failed to get the error messages correctly.", 1,
            messages.length);
    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#getAllMessages(Object, int) for
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
     * Tests AbstractConfigurableValidator#getAllMessages(Object, int) for
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
     * Tests AbstractConfigurableValidator#valid(Object) for accuracy.
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

        Object object = TestHelper.createValidationInfoForTest();
        RatingType ratingType = RatingType.DEVELOPMENT;
        int rating = 900;
        int numRatings = 1;
        int volatility = 20;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility);
        ExternalUser user = ((ValidationInfo) object).getUser();
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        assertTrue(
                "Failed to determine if the given Object is valid correctly.",
                validator.valid(object));

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#valid(Object) for accuracy.
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

        Object object = TestHelper.createValidationInfoForTest();
        assertFalse(
                "Failed to determine if the given Object is valid correctly.",
                validator.valid(object));

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#valid(Object) for accuracy.
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
    public void testValid_False_EmptyMessage() {

        bundleInfo.setMessageKey("emptyMessageKey");
        validator = new MockAbstractConfigurableValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object object = TestHelper.createValidationInfoForTest();

        assertFalse(
                "Failed to determine if the given Object is valid correctly.",
                validator.valid(object));

    }

    /**
     * <p>
     * Tests AbstractConfigurableValidator#valid(Object) for failure.
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
     * Tests AbstractConfigurableValidator#valid(Object) for failure.
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

    /**
     * <p>
     * Tests
     * AbstractConfigurableValidator#setRegistrationValidator(RegistrationValidator)
     * for failure.
     * </p>
     *
     * <p>
     * It tests the case when registrationValidator is null.
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
     * Tests
     * AbstractConfigurableValidator#setRegistrationValidator(RegistrationValidator)
     * for accuracy.
     * </p>
     *
     * <p>
     * Should have set the project correctly.
     * </p>
     *
     */
    public void testSetRegistrationValidator() {

        DataValidationRegistrationValidator dataValidationRegistrationValidator
            = new DataValidationRegistrationValidator();
        validator.setRegistrationValidator(dataValidationRegistrationValidator);
        assertEquals("Failed to set rgistrationValidator correctly.",
                dataValidationRegistrationValidator, validator
                        .getRegistrationValidator());
    }

    /**
     * <p>
     * Tests
     * AbstractConfigurableValidator#getRegistrationValidator(RegistrationValidator)
     * for accuracy.
     * </p>
     *
     * <p>
     * Should have get the project correctly.
     * </p>
     *
     */
    public void testGetRegistrationValidator() {

        DataValidationRegistrationValidator dataValidationRegistrationValidator
            = new DataValidationRegistrationValidator();
        validator.setRegistrationValidator(dataValidationRegistrationValidator);
        assertEquals("Failed to get rgistrationValidator correctly.",
                dataValidationRegistrationValidator, validator
                        .getRegistrationValidator());
    }

}
