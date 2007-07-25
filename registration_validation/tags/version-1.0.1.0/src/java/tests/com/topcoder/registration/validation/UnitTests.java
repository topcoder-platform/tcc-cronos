/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.registration.validation;

import com.topcoder.registration.validation.validators.simple.MemberMinimumNumberOfRatingsForRatingTypeValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberMinimumRatingForRatingTypeValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberMinimumReliabilityForRatingTypeValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberNotExceededMaxProjectRegistrationLimitValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberMinimumVolatilityForRatingTypeValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberNotBarredValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberNotRegisteredWithRoleForProjectValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamCaptainWithMembersForProjectValidatorTest;
import com.topcoder.registration.validation.validators.simple.MemberNotTeamMemberForProjectValidatorTest;
import com.topcoder.registration.validation.validators.simple.ProjectInPhaseValidatorTest;
import com.topcoder.registration.validation.validators.simple.ProjectOfCategoryValidatorTest;
import com.topcoder.registration.validation.validators.simple.ProjectOfTypeValidatorTest;

import com.topcoder.registration.validation.validators.conditional.ProjectCategoryConditionalValidatorTest;
import com.topcoder.registration.validation.validators.conditional.ProjectIdentifierConditionalValidatorTest;
import com.topcoder.registration.validation.validators.conditional.ProjectTypeConditionalValidatorTest;
import com.topcoder.registration.validation.validators.conditional.RegisteringResourceRoleConditionalValidatorTest;

import com.topcoder.registration.validation.validators.util.AndValidatorTest;
import com.topcoder.registration.validation.validators.util.NotValidatorTest;
import com.topcoder.registration.validation.validators.util.NullValidatorTest;
import com.topcoder.registration.validation.validators.util.OrValidatorTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // adds tests for the main calsses
        suite.addTestSuite(DataValidationRegistrationValidatorTest.class);
        suite.addTestSuite(ValidationInfoTest.class);
        suite.addTestSuite(OperationResultImplTest.class);
        suite.addTestSuite(AbstractConfigurableValidatorTest.class);

        // adds tests for the helper calss
        suite.addTestSuite(RegistrationValidationHelperTest.class);

        // adds tests for exceptions
        suite.addTestSuite(ValidationProcessingExceptionTest.class);
        suite.addTestSuite(RegistrationValidationConfigurationExceptionTest.class);

        // adds tests for demo
        suite.addTestSuite(ComponentDemo.class);

        // adds tests for classes in package simple
        suite.addTestSuite(MemberMinimumNumberOfRatingsForRatingTypeValidatorTest.class);
        suite.addTestSuite(MemberMinimumRatingForRatingTypeValidatorTest.class);
        suite.addTestSuite(MemberMinimumReliabilityForRatingTypeValidatorTest.class);
        suite.addTestSuite(MemberMinimumVolatilityForRatingTypeValidatorTest.class);
        suite.addTestSuite(MemberNotBarredValidatorTest.class);
        suite.addTestSuite(MemberNotExceededMaxProjectRegistrationLimitValidatorTest.class);
        suite.addTestSuite(MemberNotRegisteredWithRoleForProjectValidatorTest.class);
        suite.addTestSuite(MemberNotTeamCaptainWithMembersForProjectValidatorTest.class);
        suite.addTestSuite(MemberNotTeamMemberForProjectValidatorTest.class);
        suite.addTestSuite(ProjectInPhaseValidatorTest.class);
        suite.addTestSuite(ProjectOfCategoryValidatorTest.class);
        suite.addTestSuite(ProjectOfTypeValidatorTest.class);

        // adds tests for classes in package conditional
        suite.addTestSuite(ProjectCategoryConditionalValidatorTest.class);
        suite.addTestSuite(ProjectIdentifierConditionalValidatorTest.class);
        suite.addTestSuite(ProjectTypeConditionalValidatorTest.class);
        suite.addTestSuite(RegisteringResourceRoleConditionalValidatorTest.class);

        // adds tests for classes in package util
        suite.addTestSuite(AndValidatorTest.class);
        suite.addTestSuite(NotValidatorTest.class);
        suite.addTestSuite(NullValidatorTest.class);
        suite.addTestSuite(OrValidatorTest.class);

        return suite;
    }

}