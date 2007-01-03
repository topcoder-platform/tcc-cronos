/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Run all the Time Tracker User unit tests.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests {

    /** Hide default constructor of this static class. */
    private UnitTests() {
    }

    /**
     * Builds a test suite of all the unit tests.
     *
     * @return A fully-populated test suite.
     */
    public static Test suite() {

        TestSuite suite = new TestSuite("Unit Tests for Time Tracker User component");

        // tests of the Exception classes
        suite.addTestSuite(ConfigurationExceptionTest.class);
        suite.addTestSuite(PersistenceExceptionTest.class);
        suite.addTestSuite(UnknownUserExceptionTest.class);
        suite.addTestSuite(UnknownUserStoreExceptionTest.class);

        // Tests of the main classes (normal cases only)
        suite.addTestSuite(DbUserStoreDefaultCtorTest.class);
        suite.addTestSuite(DbUserStoreTwoArgCtorTest.class);
        suite.addTestSuite(UserManagerDefaultCtorTest.class);
        suite.addTestSuite(UserManager3ArgCtorTest.class);
        suite.addTestSuite(UserPersistenceImplOneArgCtorTest.class);
        suite.addTestSuite(UserPersistenceImplTwoArgCtorTest.class);
        suite.addTestSuite(UserStoreManagerImplTest.class);
        suite.addTestSuite(UserTest.class);

        // Exception/failure tests of the main classes
        suite.addTestSuite(DbUserStoreExceptionsTest.class);
        suite.addTestSuite(UserPersistenceImplExceptionsTest.class);
        suite.addTestSuite(UserManagerExceptionsTest.class);

        // Demonstration
        suite.addTestSuite(DemoTest.class);
        return suite;
    }
}
