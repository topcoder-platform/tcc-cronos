/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import com.topcoder.timetracker.company.daoimplementation.DbCompanyDAOUnitTest;
import com.topcoder.timetracker.company.ejb.CompanyBeanUnitTest;
import com.topcoder.timetracker.company.ejb.InstantiationExceptionUnitTest;
import com.topcoder.timetracker.company.ejb.LocalCompanyManagerDelegateUnitTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(BatchCompanyDAOExceptionUnitTest.class);
        suite.addTestSuite(CompanyUnitTest.class);
        suite.addTestSuite(CompanyDAOExceptionUnitTest.class);
        suite.addTestSuite(CompanyDAOSynchronizedWrapperUnitTest.class);
        suite.addTestSuite(CompanyDAOSynchronizedWrapperWrapperTest.class);
        suite.addTestSuite(CompanyNotFoundExceptionUnitTest.class);
        suite.addTestSuite(InvalidIdExceptionUnitTest.class);
        suite.addTestSuite(TimeTrackerCompanyHelperUnitTest.class);
        suite.addTestSuite(CompanySearchBuilderUnitTest.class);

        suite.addTestSuite(CompanyBeanUnitTest.class);
        suite.addTestSuite(InstantiationExceptionUnitTest.class);
        suite.addTestSuite(LocalCompanyManagerDelegateUnitTest.class);

        suite.addTestSuite(DbCompanyDAOUnitTest.class);

        suite.addTestSuite(DemoTest.class);
        return suite;
    }
}
