/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.RegistrationValidationHelper;

import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.cronos.onlinereview.external.RatingInfo;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class MemberMinimumVolatilityForRatingTypeValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberMinimumVolatilityForRatingTypeValidatorTest extends TestCase {

    /**
     * <p>
     * Represents a valid formed namespace for this class.
     * </p>
     */
    private static final String NAMESPACE = "simple.Good";

    /**
     * <p>
     * Represents a namespace in which the minimumVolatility is negative.
     * </p>
     *
     */

    /**
     * <p>
     * Represents the resource bundle information used by this validator.
     * </p>
     */
    private BundleInfo bundleInfo;

    /**
     * <p>
     * Represents the rating type that will be validated.
     * </p>
     *
     */
    private RatingType ratingType = RatingType.DESIGN;

    /**
     * <p>
     * Represents the minimum reliability the user must have for the given
     * rating type.
     * </p>
     *
     */
    private int minimumVolatility = 3;

    /**
     * <p>
     * The MemberMinimumVolatilityForRatingTypeValidator instance for testing
     * purpose.
     * </p>
     */
    private MemberMinimumVolatilityForRatingTypeValidator validator;




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
                .loadXMLConfig("MemberMinimumVolatilityForRatingTypeValidator.xml");

        bundleInfo = RegistrationValidationHelper.createBundleInfo(NAMESPACE);

        validator = new MemberMinimumVolatilityForRatingTypeValidator(NAMESPACE);
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
     * Verifies MemberMinimumVolatilityForRatingTypeValidator subclasses
     * AbstractObjectValidator.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "MemberMinimumVolatilityForRatingTypeValidator does not subclass AbstractObjectValidator.",
                new MemberMinimumVolatilityForRatingTypeValidator(NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(BundleInfo, int,
     * RatingType) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created
     * MemberMinimumVolatilityForRatingTypeValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor1() {


        validator = new MemberMinimumVolatilityForRatingTypeValidator(
                bundleInfo, minimumVolatility, ratingType);
        assertNotNull(
                "Failed to create a new MemberMinimumVolatilityForRatingTypeValidator instance.",
                validator);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));


    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(BundleInfo, int,
     * RatingType) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the bundleInfo is invalid.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_InvalidBundleInfo() {

        try {
            bundleInfo = new BundleInfo();
            validator = new MemberMinimumVolatilityForRatingTypeValidator(
                    bundleInfo, minimumVolatility, ratingType);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(BundleInfo, int,
     * RatingType) for failure.
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


        try {
            validator = new MemberMinimumVolatilityForRatingTypeValidator(null,
                    minimumVolatility, ratingType);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(BundleInfo, int,
     * RatingType) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the minimumVolatility is negative.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NegativeMinimumReliability() {


        try {
            validator = new MemberMinimumVolatilityForRatingTypeValidator(
                    bundleInfo, -1, ratingType);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(BundleInfo, int,
     * RatingType) for failure.
     * </p>
     *
     * <p>
     * It tests the case when the ratingType is null.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NullRatingType() {
        try {
            validator = new MemberMinimumVolatilityForRatingTypeValidator(
                    bundleInfo, minimumVolatility, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created
     * MemberMinimumVolatilityForRatingTypeValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new MemberMinimumVolatilityForRatingTypeValidator(NAMESPACE);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created
     * MemberMinimumVolatilityForRatingTypeValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor2_NoBundleName() {
        validator = new MemberMinimumVolatilityForRatingTypeValidator(
                "simple.NoBundleName");
        assertNotNull(
                "Failed to create MemberMinimumVolatilityForRatingTypeValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
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
            new MemberMinimumVolatilityForRatingTypeValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
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
            new MemberMinimumVolatilityForRatingTypeValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
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
            new MemberMinimumVolatilityForRatingTypeValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
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
            new MemberMinimumVolatilityForRatingTypeValidator(
                    "unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when bundleInfo is not configured properly.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidBundleInfo() {

        try {
            new MemberMinimumVolatilityForRatingTypeValidator(
                    "simple.InvalidBundleInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when ratingType property is missing in the namespace.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_MissingRatingType() {
        try {
            new MemberMinimumVolatilityForRatingTypeValidator(
                    "simple.MissingRatingType");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when minimumVolatility property is missing in the
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
            new MemberMinimumVolatilityForRatingTypeValidator(
                    "simple.MissingValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the minimumVolatility property value is not
     * invalid.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidValidatorInfo() {

        try {
            new MemberMinimumVolatilityForRatingTypeValidator(
                    "simple.InvalidValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberMinimumVolatilityForRatingTypeValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the minimumVolatility is negative.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_NegativeValidatorInfo() {

        try {
            new MemberMinimumVolatilityForRatingTypeValidator(
                    "simple.NegativeValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests MemberMinimumVolatilityForRatingTypeValidator#getMessage(Object)
     * for accuracy.
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
        ratingType = RatingType.DESIGN;
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
     * Tests MemberMinimumVolatilityForRatingTypeValidator#getMessage(Object)
     * for accuracy.
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
        ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 5;
        int volatility = 19;
        double reliability = 50.3;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));


    }

    /**
     * <p>
     * Tests MemberMinimumVolatilityForRatingTypeValidator#getMessage(Object)
     * for failure.
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
     * Tests MemberMinimumVolatilityForRatingTypeValidator#getMessage(Object)
     * for failure.
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
     * Tests MemberMinimumVolatilityForRatingTypeValidator#getMessage(Object)
     * for failure.
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
        validator = new MemberMinimumVolatilityForRatingTypeValidator(
                bundleInfo, minimumVolatility, ratingType);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        ratingType = RatingType.DESIGN;
        int rating = 0;
        int numRatings = 0;
        int volatility = 0;
        double reliability = 0;
        RatingInfo ratingInfo = new RatingInfo(ratingType, rating, numRatings,
                volatility, reliability);
        ((ExternalUserImpl) user).addRatingInfo(ratingInfo);

        try {
            validator.getMessage(obj);
            fail("ValidationProcessingException expected.");
        } catch (ValidationProcessingException e) {
            // good
        }
    }
}
