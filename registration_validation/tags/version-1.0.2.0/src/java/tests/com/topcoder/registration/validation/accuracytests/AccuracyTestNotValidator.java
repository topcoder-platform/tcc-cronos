/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;
import com.topcoder.registration.validation.DataValidationRegistrationValidator;
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.registration.validation.validators.util.NotValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for <code>NotValidator</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestNotValidator extends TestCase {
	/**
	 * The MemberMinimumNumberOfRatingsForRatingTypeValidator instance used to
	 * test.
	 */
	private MemberMinimumNumberOfRatingsForRatingTypeValidator cv;

	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The DataValidationRegistrationValidator instance used to test.
	 */
	private DataValidationRegistrationValidator drv;

	/**
	 * The NotValidator instance used to test.
	 */
	private NotValidator validator;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();

		AccuracyTestHelper.loadNamespaces();
		cv = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
				"MemberMinimumNumberOfRatingsForRatingTypeValidator.ns");

		AccuracyTestHelper.setLog(cv);

		bundleInfo = cv.getBundleInfo();

		validator = new NotValidator(cv, bundleInfo);
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
	 * Function test : Tests <code>NotValidator(BundleInfo bundleInfo)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testNotValidator2Accuracy() throws Exception {
		assertNotNull("Null is not allowed.", new NotValidator(cv, bundleInfo));
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
		String message = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is expected.", message);
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
		String message[] = validator.getAllMessages(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Failed to get the error messages priority.", message);
	}
}