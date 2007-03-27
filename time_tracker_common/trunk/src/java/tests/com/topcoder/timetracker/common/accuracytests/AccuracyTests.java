/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AccuracyTests extends TestCase {

    /**
     * Assembly all the accuracy tests.
     *
     * @return the test suite
     */
    public static Test suite() {
        // represents the test suite
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(DatabasePaymentTermDAOAccuracyTest.class);
        suite.addTestSuite(PaymentTermAccuracyTest.class);
        suite.addTestSuite(SimpleCommonManagerAccuracyTest.class);

        return suite;
    }

}
