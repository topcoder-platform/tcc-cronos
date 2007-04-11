/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence;

import com.cronos.im.persistence.rolecategories.CategoryNotFoundExceptionTests;
import com.cronos.im.persistence.rolecategories.CategoryTests;
import com.cronos.im.persistence.rolecategories.CategoryValidationExceptionTests;
import com.cronos.im.persistence.rolecategories.InformixRoleCategoryPersistenceTests;
import com.cronos.im.persistence.rolecategories.ManagerNotFoundExceptionTests;
import com.cronos.im.persistence.rolecategories.RoleCategoryPersistenceExceptionTests;
import com.cronos.im.persistence.rolecategories.RoleNotFoundExceptionTests;
import com.cronos.im.persistence.rolecategories.UserTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all unit test cases for the <i>IM persistence</i> component.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class UnitTests extends TestCase {
    /**
     * Returns the unit test suite for the <i>IM persistence</i> component.
     *
     * @return the unit test suite for the <i>IM persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(ChatUserProfileValidationExceptionTests.class);
        suite.addTestSuite(ConfigurationExceptionTests.class);
        suite.addTestSuite(EntityStatusValidationExceptionTests.class);
        suite.addTestSuite(InformixRoleCategoryPersistenceTests.class);
        suite.addTestSuite(NullChatUserProfileAttributeValidatorTests.class);
        suite.addTestSuite(ParameterHelpersTests.class);
        suite.addTestSuite(ProfileKeyValidationExceptionTests.class);

        suite.addTestSuite(CategoryTests.class);
        suite.addTestSuite(CategoryNotFoundExceptionTests.class);
        suite.addTestSuite(CategoryValidationExceptionTests.class);
        suite.addTestSuite(InformixEntityStatusTrackerTests.class);
        suite.addTestSuite(InformixProfileKeyManagerTests.class);
        suite.addTestSuite(ManagerNotFoundExceptionTests.class);
        suite.addTestSuite(RegisteredChatUserProfileInformixPersistenceTests.class);
        suite.addTestSuite(RoleCategoryPersistenceExceptionTests.class);
        suite.addTestSuite(RoleNotFoundExceptionTests.class);
        suite.addTestSuite(UnregisteredChatUserProfileInformixPersistenceTests.class);
        suite.addTestSuite(UserTests.class);

        suite.addTestSuite(Demo.class);

        return suite;
    }
}
