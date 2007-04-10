/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.company.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(CompanyDAOSynchronizedWrapperTest.suite());
        suite.addTest(CompanySearchBuilderTest.suite());
        suite.addTest(CompanyTest.suite());
        suite.addTest(DbCompanyDAOTest.suite());
        suite.addTest(LocalCompanyManagerDelegateTest.suite());
        return suite;
    }

}
