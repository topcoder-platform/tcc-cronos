/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.registration.validation.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * 
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

	public static Test suite() {
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(AccuracyTestProjectOfTypeValidator.class);
		suite.addTestSuite(AccuracyTestProjectOfCategoryValidator.class);
		suite.addTestSuite(AccuracyTestProjectInPhaseValidator.class);
		suite.addTestSuite(AccuracyTestProjectIdentifierConditionalValidator.class);
		suite.addTestSuite(AccuracyTestProjectCategoryConditionalValidator.class);
		suite.addTestSuite(AccuracyTestOrValidator.class);
		suite.addTestSuite(AccuracyTestOperationResultImpl.class);
		suite.addTestSuite(AccuracyTestNullValidator.class);
		suite.addTestSuite(AccuracyTestNotValidator.class);
		suite.addTestSuite(AccuracyTestMemberNotTeamMemberForProjectValidator.class);
		suite.addTestSuite(AccuracyTestMemberNotTeamCaptainWithMembersForProjectValidator.class);
		suite.addTestSuite(AccuracyTestMemberNotRegisteredWithRoleForProjectValidator.class);
		suite.addTestSuite(AccuracyTestMemberNotExceededMaxProjectRegistrationLimitValidator.class);
		suite.addTestSuite(AccuracyTestMemberNotBarredValidator.class);
		suite.addTestSuite(AccuracyTestMemberMinimumVolatilityForRatingTypeValidator.class);
		suite.addTestSuite(AccuracyTestMemberMinimumReliabilityForRatingTypeValidator.class);
		suite.addTestSuite(AccuracyTestMemberMinimumRatingForRatingTypeValidator.class);
		suite.addTestSuite(AccuracyTestMemberMinimumNumberOfRatingsForRatingTypeValidator.class);
		suite.addTestSuite(AccuracyTestAndValidator.class);
		suite.addTestSuite(AccuracyTestDataValidationRegistrationValidator.class);
		suite.addTestSuite(AccuracyTestProjectTypeConditionalValidator.class);
		suite.addTestSuite(AccuracyTestRegisteringResourceRoleConditionalValidator.class);
		suite.addTestSuite(AccuracyTestRegistrationValidationConfigurationException.class);
		suite.addTestSuite(AccuracyTestValidationInfo.class);
		suite.addTestSuite(AccuracyTestValidationProcessingException.class);
		return suite;
	}

}
