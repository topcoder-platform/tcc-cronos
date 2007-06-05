/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.cronos.onlinereview.external.RatingType;
import com.topcoder.registration.validation.validators.simple.MemberMinimumReliabilityForRatingTypeValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for
 * <code>MemberMinimumReliabilityForRatingTypeValidator extends</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestMemberMinimumReliabilityForRatingTypeValidator extends TestCase {
	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The MemberMinimumReliabilityForRatingTypeValidator instance used to test.
	 */
	private MemberMinimumReliabilityForRatingTypeValidator validator = null;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		validator = new MemberMinimumReliabilityForRatingTypeValidator(
				"MemberMinimumReliabilityForRatingTypeValidator.ns");
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
	 * <code>MemberMinimumReliabilityForRatingTypeValidator(BundleInfo bundleInfo,
	 * int minimumNumRatings, RatingType ratingType)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testMemberMinimumReliabilityForRatingTypeValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new MemberMinimumReliabilityForRatingTypeValidator(bundleInfo,
				99.1, RatingType.DESIGN));
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
		validator = new MemberMinimumReliabilityForRatingTypeValidator(bundleInfo, 101.01, RatingType.DESIGN);
		AccuracyTestHelper.setLog(validator);
		String msg = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is allowed.", msg);
	}
}