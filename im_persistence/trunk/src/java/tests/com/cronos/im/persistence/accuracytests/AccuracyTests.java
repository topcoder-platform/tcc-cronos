/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases for the <i>IM Persistence</i> component.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class AccuracyTests extends TestCase {
    /**
     * Returns the accuracy test cases for the <i>IM Persistence</i> component.
     * @return the accuracy test cases for the <i>IM Persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(CategoryAccuracyTests.class);
        suite.addTestSuite(CategoryNotFoundExceptionAccuracyTests.class);
        suite.addTestSuite(CategoryValidationExceptionAccuracyTests.class);
        suite.addTestSuite(ChatUserProfileValidationExceptionAccuracyTests.class);
        suite.addTestSuite(ConfigurationExceptionAccuracyTests.class);
        suite.addTestSuite(ConfigurationExceptionAccuracyTests.class);
        suite.addTestSuite(EntityStatusValidationExceptionAccuracyTests.class);
        suite.addTestSuite(InformixEntityStatusTrackerAccuracyTests.class);
        suite.addTestSuite(ManagerNotFoundExceptionAccuracyTests.class);
        suite.addTestSuite(NullChatUserProfileAttributeValidatorAccuracyTests.class);
        suite.addTestSuite(ProfileKeyValidationExceptionAccuracyTests.class);
        suite.addTestSuite(RoleCategoryPersistenceExceptionAccuracyTests.class);
        suite.addTestSuite(RoleNotFoundExceptionAccuracyTests.class);
        suite.addTestSuite(UserAccuracyTests.class);
        suite.addTestSuite(UserDefinedAttributeNamesAccuracyTests.class);
        suite.addTestSuite(InformixProfileKeyManagerAccuracyTests.class);
        suite.addTestSuite(InformixRoleCategoryPersistenceAccuracyTests.class);
        suite.addTestSuite(RegisteredChatUserProfileInformixPersistenceAccuracyTests.class);
        suite.addTestSuite(UnregisteredChatUserProfileInformixPersistenceAccuracyTests.class);
        return suite;
    }
}
