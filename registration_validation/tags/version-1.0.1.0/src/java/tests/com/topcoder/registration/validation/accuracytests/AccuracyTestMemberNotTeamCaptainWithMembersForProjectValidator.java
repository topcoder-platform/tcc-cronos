/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.topcoder.registration.validation.validators.simple.MemberNotTeamCaptainWithMembersForProjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for
 * <code>MemberNotTeamCaptainWithMembersForProjectValidator extends</code>
 * class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestMemberNotTeamCaptainWithMembersForProjectValidator extends TestCase {
	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The MemberNotTeamCaptainWithMembersForProjectValidator instance used to
	 * test.
	 */
	private MemberNotTeamCaptainWithMembersForProjectValidator validator = null;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		validator = new MemberNotTeamCaptainWithMembersForProjectValidator(
				"MemberNotTeamCaptainWithMembersForProjectValidator.ns");
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
	 * <code>MemberNotTeamCaptainWithMembersForProjectValidator(BundleInfo bundleInfo,
	 * int minimumNumRatings, RatingType ratingType)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testMemberNotTeamCaptainWithMembersForProjectValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new MemberNotTeamCaptainWithMembersForProjectValidator(bundleInfo,
				4));
	}

	/**
	 * Function test : Tests <code>getMessage(Object obj)</code> method for
	 * accuracy, numRatings >= this.minimumNumRatings
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
		validator = new MemberNotTeamCaptainWithMembersForProjectValidator(bundleInfo, 3);
		AccuracyTestHelper.setLog(validator);
		String msg = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is allowed.", msg);
	}
}