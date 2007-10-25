/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.ConfigurableValidator;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.registration.validation.validators.simple.MemberMinimumRatingForRatingTypeValidator;
import com.topcoder.registration.validation.validators.util.AndValidator;

/**
 * This class contains unit tests for <code>AndValidator</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestAndValidator extends TestCase {

	/**
	 * The ConfigurableValidator instance used to test.
	 */
	private ConfigurableValidator[] vs;

	/**
	 * The DataValidationRegistrationValidator instance used to test.
	 */
	private DataValidationRegistrationValidator drv;

	/**
	 * The AndValidator instance used to test.
	 */
	private AndValidator validator;

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

		vs = new ConfigurableValidator[] { v1, v2 };

		validator = new AndValidator(vs, false);
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
	 * Function test : Tests <code>AndValidator()</code> method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testAndValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new AndValidator(vs, true));
	}

	/**
	 * Function test : Tests <code>AndValidator(BundleInfo bundleInfo)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testAndValidator2Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new AndValidator(vs, false));
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
		assertTrue("false is expected.", validator.valid(AccuracyTestHelper.createValidationInfo()));
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy() throws Exception {
		String message = validator.getMessage(AccuracyTestHelper.createValidationInfo());
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
		assertNull("Null is not allowed.", messages);
	}

	/**
	 * Function test : Tests <code>getAllMessages(Object obj)</code> method
	 * for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetAllMessagesAccuracy1() throws Exception {
		String message[] = validator.getAllMessages(AccuracyTestHelper.createValidationInfo());
		assertNull("Failed to get the error messages priority.", message);
	}
}