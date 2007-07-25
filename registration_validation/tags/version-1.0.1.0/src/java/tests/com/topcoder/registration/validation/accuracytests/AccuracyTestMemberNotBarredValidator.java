/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.cronos.onlinereview.external.impl.ExternalUserImpl;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.validators.simple.MemberNotBarredValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for <code>MemberNotBarredValidator</code>
 * class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestMemberNotBarredValidator extends TestCase {
	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The MemberNotBarredValidator instance used to test.
	 */
	private MemberNotBarredValidator validator = null;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		validator = new MemberNotBarredValidator("MemberMinimumReliabilityForRatingTypeValidator.ns");
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
	 * <code>MemberNotBarredValidator(BundleInfo bundleInfo,
	 * int minimumNumRatings, RatingType ratingType)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testMemberNotBarredValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new MemberNotBarredValidator(bundleInfo));
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy, numRatings >= this.minimumNumRatings
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testGetMessageAccuracy1() throws Exception {
		ValidationInfo info = AccuracyTestHelper.createValidationInfo();
		info.setUser(new ExternalUserImpl(2, "handle1", "firstName", "lastName", "email@dd.dd"));
		String msg = validator.getMessage(info);
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
		validator = new MemberNotBarredValidator(bundleInfo);
		AccuracyTestHelper.setLog(validator);

		ValidationInfo info = AccuracyTestHelper.createValidationInfo();
		info.setUser(new ExternalUserImpl(3, "handle1", "firstName", "lastName", "email@dd.dd"));
		String msg = validator.getMessage(info);
		assertNotNull("Null is allowed.", msg);
	}
}