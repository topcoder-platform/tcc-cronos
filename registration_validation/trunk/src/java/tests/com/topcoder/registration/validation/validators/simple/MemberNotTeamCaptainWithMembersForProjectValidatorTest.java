/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.validators.simple;

import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.validation.TestHelper;
import com.topcoder.registration.validation.RegistrationValidationHelper;

import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.util.datavalidator.AbstractObjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;
import com.topcoder.registration.validation.ValidationProcessingException;
import com.topcoder.registration.validation.RegistrationValidationConfigurationException;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for class MemberNotTeamCaptainWithMembersForProjectValidator.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MemberNotTeamCaptainWithMembersForProjectValidatorTest extends
        TestCase {

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
     * Represents the ID of the team captain role. type.
     * </p>
     *
     */
    private long teamCaptainRoleId = 3;

    /**
     * <p>
     * The MemberNotTeamCaptainWithMembersForProjectValidator instance for
     * testing purpose.
     * </p>
     */
    private MemberNotTeamCaptainWithMembersForProjectValidator validator;




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
                .loadXMLConfig("MemberNotTeamCaptainWithMembersForProjectValidator.xml");

        bundleInfo = RegistrationValidationHelper.createBundleInfo(NAMESPACE);

        validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                NAMESPACE);
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
     * Verifies MemberNotTeamCaptainWithMembersForProjectValidator subclasses
     * AbstractObjectValidator.
     * </p>
     */
    public void testInheritance() {
        assertTrue(
                "MemberNotTeamCaptainWithMembersForProjectValidator does not subclass AbstractObjectValidator.",
                new MemberNotTeamCaptainWithMembersForProjectValidator(
                        NAMESPACE) instanceof AbstractObjectValidator);
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo, long)
     * for accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created
     * MemberNotTeamCaptainWithMembersForProjectValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor1() {


        validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                bundleInfo, teamCaptainRoleId);
        assertNotNull(
                "Failed to create a new MemberNotTeamCaptainWithMembersForProjectValidator instance.",
                validator);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));


    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo, long)
     * for accuracy.
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
            validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                    bundleInfo, teamCaptainRoleId);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo, long)
     * for accuracy.
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
            validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                    null, teamCaptainRoleId);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo, long)
     * for accuracy.
     * </p>
     *
     * <p>
     * It tests the case when the teamCaptainRoleId is negative.
     * </p>
     *
     * <p>
     * Should have thrown IllegalArgumentException.
     * </p>
     *
     */
    public void testCtor1_NegativeMinimumReliability() {


        try {
            validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                    bundleInfo, -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created
     * MemberNotTeamCaptainWithMembersForProjectValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor2() {
        validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                NAMESPACE);
        BundleInfo bundleInfoField = validator.getBundleInfo();
        assertEquals("Failed to initialize the bundleInfo field.", null,
                TestHelper.compareBundleInfos(bundleInfoField, bundleInfo));
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
     * accuracy.
     * </p>
     *
     * <p>
     * It verifies the newly created
     * MemberNotTeamCaptainWithMembersForProjectValidator instance should not be
     * null.
     * </p>
     *
     */
    public void testCtor2_NoBundleName() {
        validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                "simple.NoBundleName");
        assertNotNull(
                "Failed to create MemberNotTeamCaptainWithMembersForProjectValidator instance.",
                validator);
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
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
            new MemberNotTeamCaptainWithMembersForProjectValidator(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Test ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
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
            new MemberNotTeamCaptainWithMembersForProjectValidator("");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
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
            new MemberNotTeamCaptainWithMembersForProjectValidator("  ");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
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
            new MemberNotTeamCaptainWithMembersForProjectValidator(
                    "unknownNamespace");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
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
            new MemberNotTeamCaptainWithMembersForProjectValidator(
                    "simple.InvalidBundleInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when teamCaptainRoleId property is missing in the
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
            new MemberNotTeamCaptainWithMembersForProjectValidator(
                    "simple.MissingValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the teamCaptainRoleId property value is invalid.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_InvalidValidatorInfo() {

        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator(
                    "simple.InvalidValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests ctor MemberNotTeamCaptainWithMembersForProjectValidator(String) for
     * failure.
     * </p>
     *
     * <p>
     * It tests the case when the teamCaptainRoleId is negative.
     * </p>
     *
     * <p>
     * Should have thrown RegistrationValidationConfigurationException.
     * </p>
     *
     */
    public void testCtor2_NegativeValidatorInfo() {

        try {
            new MemberNotTeamCaptainWithMembersForProjectValidator(
                    "simple.NegativeValidatorInfo");
            fail("RegistrationValidationConfigurationException expected.");
        } catch (RegistrationValidationConfigurationException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests
     * MemberNotTeamCaptainWithMembersForProjectValidator#getMessage(Object) for
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
        // change the value of validationInfo.getRegistration().getRoleId()
        RegistrationInfo registration = new RegistrationInfoImpl();
        registration.setRoleId(6);
        ((ValidationInfo) obj).setRegistration(registration);

        String message = validator.getMessage(obj);
        assertNull("Failed to get the error messages priority.", message);


    }

    /**
     * <p>
     * Tests
     * MemberNotTeamCaptainWithMembersForProjectValidator#getMessage(Object) for
     * accuracy.
     * </p>
     *
     * <p>
     * It tests the case when can not find resource in
     * validationInfo.project.resources whose custom property &quot;External
     * Reference ID&quot; is equal to validationInfo.registrationInfo.userId
     * </p>
     *
     * <p>
     * Should have returned null if obj is valid.
     * </p>
     *
     */
    public void testGetMessage_NoResourceFound() {
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
     * Tests
     * MemberNotTeamCaptainWithMembersForProjectValidator#getMessage(Object) for
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
        String message = validator.getMessage(obj);
        assertNotNull("Failed to get the error messages correctly.", message);
        assertTrue("Failed to get the error messages correctly.", (message
                .trim().length() > 0));
    }

    /**
     * <p>
     * Tests
     * MemberNotTeamCaptainWithMembersForProjectValidator#getMessage(Object) for
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
     * Tests
     * MemberNotTeamCaptainWithMembersForProjectValidator#getMessage(Object) for
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
     * MemberNotExceededMaxProjectRegistrationLimitValidator#getMessage(Object)
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
        validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
                bundleInfo, teamCaptainRoleId);
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
}
