/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationInfoImpl;
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
 * Unit test cases for class MemberNotTeamMemberForProjectValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberNotTeamMemberForProjectValidatorTest extends TestCase {

    /**
     * <p>
     * Represents a valid formed namespace for this class.
     * </p>
     */
    private static final String NAMESPACE = "simple.Good";

    /**
     * <p>
     * Represents the resource bundle information used by this validator.
     * </p>
     */
    private BundleInfo bundleInfo;

    /**
     * <p>
     * The MemberNotTeamMemberForProjectValidator instance for testing purpose.
     * </p>
     */
    private MemberNotTeamMemberForProjectValidator validator;

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
        TestHelper.loadXMLConfig("MemberNotTeamMemberForProjectValidator.xml");

        bundleInfo = RegistrationValidationHelper.createBundleInfo(NAMESPACE);

        validator = new MemberNotTeamMemberForProjectValidator(NAMESPACE);
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
     * Verifies MemberNotTeamMemberForProjectValidator subclasses
     * AbstractObjectValidator.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "MemberNotTeamMemberForProjectValidator does not subclass AbstractObjectValidator.",
                new MemberNotTeamMemberForProjectValidator(NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(BundleInfo) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created MemberNotTeamMemberForProjectValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor1() {


        validator = new MemberNotTeamMemberForProjectValidator(bundleInfo);
        assertNotNull(
                "Failed to create a new MemberNotTeamMemberForProjectValidator instance.",
                validator);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));


    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(BundleInfo) for failure.
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
            validator = new MemberNotTeamMemberForProjectValidator(bundleInfo);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(BundleInfo) for failure.
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
            bundleInfo = null;
            validator = new MemberNotTeamMemberForProjectValidator(bundleInfo);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created MemberNotTeamMemberForProjectValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new MemberNotTeamMemberForProjectValidator(NAMESPACE);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(String) for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created MemberNotTeamMemberForProjectValidator
     * instance should not be null.
     * </p>
     *
     */
    public void testCtor2_NoBundleName() {
        validator = new MemberNotTeamMemberForProjectValidator(
                "simple.NoBundleName");
        assertNotNull(
                "Failed to create MemberNotTeamMemberForProjectValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(String) for failure.
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
            String namespace = null;
            new MemberNotTeamMemberForProjectValidator(namespace);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor MemberNotTeamMemberForProjectValidator(String) for failure.
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
            new MemberNotTeamMemberForProjectValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(String) for failure.
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
            new MemberNotTeamMemberForProjectValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(String) for failure.
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
            new MemberNotTeamMemberForProjectValidator("unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamMemberForProjectValidator(String) for failure.
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
            new MemberNotTeamMemberForProjectValidator(
                    "simple.InvalidBundleInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests MemberNotTeamMemberForProjectValidator#getMessage(Object) for
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
     * <p>
     * It verifies the method return null if obj is valid. Otherwise, an error
     * message is returned.
     * </p>
     *
     */
    public void testGetMessage_True() {


        Object obj = TestHelper.createValidationInfoForTest();

        // Sets registration to make the required resource can not be found
        RegistrationInfo registration = new RegistrationInfoImpl();
        registration.setProjectId(2);
        registration.setRoleId(3);
        registration.setUserId(9);
        ((ValidationInfo) obj).setRegistration(registration);

        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);


    }

    /**
     * <p>
     * Tests MemberNotTeamMemberForProjectValidator#getMessage(Object) for
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
    public void testGetMessage_False() {


        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 5;
        int volatility = 20;
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
     * Tests MemberNotTeamMemberForProjectValidator#getMessage(Object) for
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
     * Tests MemberNotTeamMemberForProjectValidator#getMessage(Object) for
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
     * Tests MemberNotTeamMemberForProjectValidator#getMessage(Object) for
     * failure.
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
        validator = new MemberNotTeamMemberForProjectValidator(bundleInfo);
        TestHelper.setDefaultRegistrationValidator(validator,
                "validatorLog.txt");

        Object obj = TestHelper.createValidationInfoForTest();
        ExternalUser user = ((ValidationInfo) obj).getUser();
        RatingType ratingType = RatingType.DESIGN;
        int rating = 900;
        int numRatings = 5;
        int volatility = 20;
        double reliability = 50.3;
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
