/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.TestCase;

import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.impl.RegistrationInfoImpl;
import com.topcoder.registration.validation.ValidationInfo;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamMemberForProjectValidator;
import com.topcoder.util.datavalidator.BundleInfo;

/**
 * This class contains unit tests for
 * <code>MemberNotTeamMemberForProjectValidator</code> class.
 * 
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestMemberNotTeamMemberForProjectValidator extends TestCase {
	/**
	 * The BundleInfo instance used to test.
	 */
	private BundleInfo bundleInfo;

	/**
	 * The MemberNotTeamMemberForProjectValidator instance used to test.
	 */
	private MemberNotTeamMemberForProjectValidator validator = null;

	/**
	 * Set Up the test environment before testing.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		AccuracyTestHelper.loadNamespaces();
		validator = new MemberNotTeamMemberForProjectValidator(
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
	 * <code>MemberNotTeamMemberForProjectValidator(BundleInfo bundleInfo,
	 * int minimumNumRatings, RatingType ratingType)</code>
	 * method for accuracy.
	 * 
	 * @throws Exception
	 *             to JUnit.
	 */
	public void testMemberNotTeamMemberForProjectValidator1Accuracy() throws Exception {
		assertNotNull("Null is allowed.", new MemberNotTeamMemberForProjectValidator(bundleInfo));
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
		RegistrationInfo registration = new RegistrationInfoImpl();
		registration.setProjectId(2);
		registration.setRoleId(3);
		registration.setUserId(9);
		info.setRegistration(registration);

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
		validator = new MemberNotTeamMemberForProjectValidator(bundleInfo);
		AccuracyTestHelper.setLog(validator);
		String msg = validator.getMessage(AccuracyTestHelper.createValidationInfo());
		assertNotNull("Null is allowed.", msg);
	}
}