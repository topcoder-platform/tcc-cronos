/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.timetracker.entry.fixedbilling.db.BaseDAOTest;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryDAOTest;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingEntryRejectReasonDAOTest;
import com.topcoder.timetracker.entry.fixedbilling.db.DbFixedBillingStatusDAOTest;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedBaseFilterFactoryTest;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingEntryFilterFactoryTest;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingStatusFilterFactoryTest;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntryManagerDelegateTest;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingEntrySessionBeanTest;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusManagerDelegateTest;
import com.topcoder.timetracker.entry.fixedbilling.j2ee.FixedBillingStatusSessionBeanTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author flytoj2ee
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * Creates the test cases suite.
     *
     * @return the test case suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        //The ManagerFactory fail test cases should be at first.
        suite.addTest(ManagerFactoryInvalidTest.suite());
        suite.addTest(BatchOperationExceptionTest.suite());
        suite.addTest(ConfigurationExceptionTest.suite());
        suite.addTest(DataAccessExceptionTest.suite());

        suite.addTest(FixedBillingEntryManagerImplTest.suite());
        suite.addTest(FixedBillingEntryTest.suite());
        suite.addTest(FixedBillingStatusManagerImplTest.suite());
        suite.addTest(FixedBillingStatusTest.suite());
        suite.addTest(InvalidCompanyExceptionTest.suite());
        suite.addTest(InvalidFilterExceptionTest.suite());

        suite.addTest(StringMatchTypeTest.suite());
        suite.addTest(UnrecognizedEntityExceptionTest.suite());
        suite.addTest(BaseDAOTest.suite());
        suite.addTest(DbFixedBillingEntryDAOTest.suite());
        suite.addTest(DbFixedBillingEntryRejectReasonDAOTest.suite());
        suite.addTest(DbFixedBillingStatusDAOTest.suite());
        suite.addTest(MappedBaseFilterFactoryTest.suite());
        suite.addTest(MappedFixedBillingEntryFilterFactoryTest.suite());
        suite.addTest(MappedFixedBillingStatusFilterFactoryTest.suite());
        suite.addTest(FixedBillingEntrySessionBeanTest.suite());
        suite.addTest(FixedBillingEntryManagerDelegateTest.suite());

        suite.addTest(FixedBillingStatusSessionBeanTest.suite());
        suite.addTest(FixedBillingStatusManagerDelegateTest.suite());
        suite.addTest(ManagerFactoryTest.suite());
        suite.addTest(Demo.suite());

        return suite;
    }
}
