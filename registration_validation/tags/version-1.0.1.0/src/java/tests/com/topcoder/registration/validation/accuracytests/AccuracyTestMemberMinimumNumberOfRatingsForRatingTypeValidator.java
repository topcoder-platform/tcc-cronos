/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;
import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for
 * <code>MemberMinimumNumberOfRatingsForRatingTypeValidator</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestMemberMinimumNumberOfRatingsForRatingTypeValidator extends TestCase {

	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The MemberMinimumNumberOfRatingsForRatingTypeValidator instance used to
	 * test.
	 */
	private MemberMinimumNumberOfRatingsForRatingTypeValidator validator = null;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		validator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(
				"MemberMinimumNumberOfRatingsForRatingTypeValidator.ns");
		bundleInfo = validator.getBundleInfo();
		AccuracyTestHelper.setLog(validator);
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
	 * Function test : Tests
	 * <code>MemberMinimumNumberOfRatingsForRatingTypeValidator(BundleInfo bundleInfo,
	 * int minimumNumRatings, RatingType ratingType)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testMemberMinimumNumberOfRatingsForRatingTypeValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new MemberMinimumNumberOfRatingsForRatingTypeValidator(bundleInfo,
				2, RatingType.DESIGN));
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy, numRatings = this.minimumNumRatings
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy1() throws Exception {
		String msg = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNull("Null is expected.", msg);
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy, numRatings < this.minimumNumRatings
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy2() throws Exception {
		validator = new MemberMinimumNumberOfRatingsForRatingTypeValidator(bundleInfo, 4, RatingType.DESIGN);
		AccuracyTestHelper.setLog(validator);
		String msg = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is allowed.", msg);
	}
}