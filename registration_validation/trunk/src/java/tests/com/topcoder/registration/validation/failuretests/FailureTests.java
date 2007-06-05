/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
 package com.topcoder.registration.validation.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AbstractConfigurableValidatorFailureTests.class);
        suite.addTestSuite(AndValidatorFailureTests.class);
        suite.addTestSuite(DataValidationRegistrationValidatorFailureTests.class);
        suite.addTestSuite(MemberMinimumNumberOfRatingsForRatingTypeValidatorFailureTests.class);
        suite.addTestSuite(MemberMinimumRatingForRatingTypeValidatorFailureTests.class);
        suite.addTestSuite(MemberMinimumReliabilityForRatingTypeValidatorFailureTests.class);
        suite.addTestSuite(MemberMinimumVolatilityForRatingTypeValidatorFailureTests.class);
        suite.addTestSuite(MemberNotBarredValidatorFailureTests.class);
        suite.addTestSuite(MemberNotExceededMaxProjectRegistrationLimitValidatorFailureTests.class);
        suite.addTestSuite(MemberNotRegisteredWithRoleForProjectValidatorFailureTests.class);
        suite.addTestSuite(MemberNotTeamCaptainWithMembersForProjectValidatorFailureTests.class);
        suite.addTestSuite(MemberNotTeamMemberForProjectValidatorFailureTests.class);
        suite.addTestSuite(NotValidatorFailureTests.class);
        suite.addTestSuite(NullValidatorFailureTests.class);
        suite.addTestSuite(OperationResultImplFailureTests.class);
        suite.addTestSuite(OrValidatorFailureTests.class);
        suite.addTestSuite(ProjectCategoryConditionalValidatorFailureTests.class);
        suite.addTestSuite(ProjectIdentifierConditionalValidatorFailureTests.class);
        suite.addTestSuite(ProjectInPhaseValidatorFailureTests.class);
        suite.addTestSuite(ProjectOfCategoryValidatorFailureTests.class);
        suite.addTestSuite(ProjectOfTypeValidatorFailureTests.class);
        suite.addTestSuite(ProjectTypeConditionalValidatorFailureTests.class);
        suite.addTestSuite(RegisteringResourceRoleConditionalValidatorFailureTests.class);
        suite.addTestSuite(ValidationInfoFailureTests.class);
        
        return suite;
    }

}
