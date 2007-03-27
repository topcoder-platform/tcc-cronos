/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAOTestAcc;
import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAOTestExp1;
import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAOTestExp2;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all Unit test cases.
     * </p>
     *
     * @return All Unit test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

//        suite.addTestSuite(HelperTestAcc.class);
//
//        suite.addTestSuite(CommonManagementExceptionTestAcc.class);
//        suite.addTestSuite(CommonManagerConfigurationExceptionTestAcc.class);
//        suite.addTestSuite(PaymentTermDAOExceptionTestAcc.class);
//        suite.addTestSuite(DuplicatePaymentTermExceptionTestAcc.class);
//        suite.addTestSuite(PaymentTermNotFoundExceptionTestAcc.class);
//
//        suite.addTestSuite(TimeTrackerBeanTestAcc.class);
//        suite.addTestSuite(PaymentTermTestAcc.class);

        suite.addTestSuite(DatabasePaymentTermDAOTestAcc.class);
        suite.addTestSuite(DatabasePaymentTermDAOTestExp1.class);
        suite.addTestSuite(DatabasePaymentTermDAOTestExp2.class);

        suite.addTestSuite(SimpleCommonManagerTestAcc.class);
        suite.addTestSuite(SimpleCommonManagerTestExp1.class);
        suite.addTestSuite(SimpleCommonManagerTestExp2.class);

//        suite.addTestSuite(Demo.class);
        return suite;
    }

}
