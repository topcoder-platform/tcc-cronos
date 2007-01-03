/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.user;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.cronos.timetracker.common.AddressTest;
import com.cronos.timetracker.common.ContactTest;
import com.cronos.timetracker.common.DbRejectEmailDAOTest;
import com.cronos.timetracker.common.DbRejectReasonDAOTest;
import com.cronos.timetracker.common.EncryptionRepositoryTest;
import com.cronos.timetracker.common.RejectEmailSearchBuilderTest;
import com.cronos.timetracker.common.RejectEmailTest;
import com.cronos.timetracker.common.RejectReasonSearchBuilderTest;
import com.cronos.timetracker.common.RejectReasonTest;
import com.cronos.timetracker.common.StateTest;
import com.cronos.timetracker.common.TimeTrackerBeanTest;
import com.cronos.timetracker.company.CompanySearchBuilderTest;
import com.cronos.timetracker.company.CompanyTest;
import com.cronos.timetracker.company.DbCompanyDAOTest;


/**
 * Run all the Time Tracker User unit tests.
 *
 * @author kr00tki
 * @version 2.0
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

        // common
        suite.addTestSuite(AddressTest.class);
        suite.addTestSuite(ContactTest.class);
        suite.addTestSuite(StateTest.class);
        suite.addTestSuite(TimeTrackerBeanTest.class);
        suite.addTestSuite(RejectReasonTest.class);
        suite.addTestSuite(RejectEmailTest.class);
        suite.addTestSuite(RejectReasonSearchBuilderTest.class);
        suite.addTestSuite(RejectEmailSearchBuilderTest.class);
        suite.addTestSuite(EncryptionRepositoryTest.class);
        suite.addTestSuite(DbRejectEmailDAOTest.class);
        suite.addTestSuite(DbRejectReasonDAOTest.class);

        // company
        suite.addTestSuite(CompanyTest.class);
        suite.addTestSuite(CompanySearchBuilderTest.class);
        suite.addTestSuite(DbCompanyDAOTest.class);

        // user
        suite.addTestSuite(AccountStatusTest.class);
        suite.addTestSuite(UserTest.class);
        suite.addTestSuite(UserSearchBuilderTest.class);
        suite.addTestSuite(DbUserAuthenticatorTest.class);
        suite.addTestSuite(DbUserDAOTest.class);
        suite.addTestSuite(UserManagerTest.class);

        suite.addTestSuite(ExceptionsTests.class);
        suite.addTestSuite(Demo.class);

        return suite;
    }
}
