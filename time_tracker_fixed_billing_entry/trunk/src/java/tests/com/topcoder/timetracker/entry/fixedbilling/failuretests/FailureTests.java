/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DbFixedBillingEntryDAOTest.suite());
        suite.addTest(DbFixedBillingEntryRejectReasonDAOTest.suite());
        suite.addTest(DbFixedBillingStatusDAOTest.suite());
        suite.addTest(FixedBillingEntryManagerDelegateTest.suite());
        suite.addTest(FixedBillingEntryManagerImplTest.suite());
        suite.addTest(FixedBillingEntryTest.suite());
        suite.addTest(FixedBillingStatusManagerDelegateTest.suite());
        suite.addTest(FixedBillingStatusManagerImplTest.suite());
        suite.addTest(FixedBillingStatusTest.suite());
        suite.addTest(MappedBaseFilterFactoryTest.suite());
        suite.addTest(MappedFixedBillingEntryFilterFactoryTest.suite());
        suite.addTest(MappedFixedBillingStatusFilterFactoryTest.suite());
        return suite;
    }

}
