/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.registration.validation.validators.simple.MemberMinimumRatingForRatingTypeValidator;
import com.topcoder.registration.validation.validators.util.NullValidator;
import com.topcoder.util.datavalidator.BundleInfo;

import junit.framework.TestCase;

/**
 * This class contains unit tests for <code>NullValidator</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestNullValidator extends TestCase {

	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The DataValidationRegistrationValidator instance used to test.
	 */
	private DataValidationRegistrationValidator drv;

	/**
	 * The NullValidator instance used to test.
	 */
	private NullValidator validator;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();

		AccuracyTestHelper.loadNamespaces();
		MemberMinimumNumberOfRatingsForRatingTypeValidator v1 = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
				"MemberMinimumNumberOfRatingsForRatingTypeValidator.ns");

		MemberMinimumRatingForRatingTypeValidator v2 = new MemberMinimumRatingForRatingTypeValidator(
				"MemberMinimumRatingForRatingTypeValidator.ns");

		AccuracyTestHelper.setLog(v1);
		AccuracyTestHelper.setLog(v2);

		bundleInfo = v1.getBundleInfo();

		validator = new NullValidator(bundleInfo);
		AccuracyTestHelper.setLog(validator);

		drv = new DataValidationRegistrationValidator();
	}

	/**
	 * Clean up the test environment after testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		AccuracyTestHelper.clearNamespaces();
	}

	/**
	 * Function test : Tests <code>NullValidator()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testNullValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new NullValidator());
	}

	/**
	 * Function test : Tests <code>NullValidator(BundleInfo bundleInfo)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testNullValidator2Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new NullValidator(bundleInfo));
	}

	/**
	 * Function test : Tests
	 * <code>setRegistrationValidator(DataValidationRegistrationValidator registrationValidator)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testSetRegistrationValidatorAccuracy() throws Exception {
		validator.setRegistrationValidator(drv);
	}

	/**
	 * Function test : Tests <code>valid(Object obj)</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testValidAccuracy() throws Exception {
		assertFalse("false is expected.", validator.valid(AccuracyTestHelper.createValidationInfo()));
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy() throws Exception {
		String message = validator.getMessage(null);
		assertNull("Null is expected.", message);
	}

	/**
	 * Function test : Tests <code>getMessages(Object obj)</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessagesAccuracy() throws Exception {
		String[] messages = validator.getMessages(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is not allowed.", messages);
	}

	/**
	 * Function test : Tests <code>getAllMessages(Object obj)</code> method
	 * for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetAllMessagesAccuracy1() throws Exception {
		String message[] = validator.getAllMessages(null);
		assertNull("Failed to get the error messages priority.", message);
	}

}