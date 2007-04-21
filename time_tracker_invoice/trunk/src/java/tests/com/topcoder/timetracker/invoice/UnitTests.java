/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.timetracker.invoice.delegate.InvoiceManagerDelegateTest;
import com.topcoder.timetracker.invoice.ejb.InvoiceSessionBeanFailureTest;
import com.topcoder.timetracker.invoice.ejb.InvoiceSessionBeanTest;
import com.topcoder.timetracker.invoice.informix.DBUtilTest;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceDAOTest;
import com.topcoder.timetracker.invoice.informix.InformixInvoiceStatusDAOTest;
import com.topcoder.timetracker.invoice.informix.filterfactory.InformixInvoiceFilterFactoryTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author enefem21
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * Creates a test suite of the tests contained all unit tests.
     * </p>
     *
     * @return a test suite of the tests contained all unit tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // demo
        // suite.addTest(Demo.suite());

        // package com.topcoder.timetracker.invoice
        suite.addTest(ArgumentCheckUtilTest.suite());
        suite.addTest(InvoiceDataAccessExceptionTest.suite());
        suite.addTest(InvoiceExceptionTest.suite());
        suite.addTest(InvoiceUnrecognizedEntityExceptionTest.suite());
        suite.addTest(InvoiceTest.suite());
        suite.addTest(InvoiceStatusTest.suite());
        suite.addTest(InvoiceDAOFactoryTest.suite());

        // package com.topcoder.timetracker.invoice.delegate
        suite.addTest(InvoiceManagerDelegateTest.suite());

        // package com.topcoder.timetracker.invoice.ejb
        suite.addTest(InvoiceSessionBeanTest.suite());
        suite.addTest(InvoiceSessionBeanFailureTest.suite());

        // package com.topcoder.timetracker.invoice.informix
        suite.addTest(InformixInvoiceDAOTest.suite());
        suite.addTest(InformixInvoiceStatusDAOTest.suite());
        suite.addTest(DBUtilTest.suite());

        // package com.topcoder.timetracker.invoice.informix.filter
        suite.addTest(InformixInvoiceFilterFactoryTest.suite());

        return suite;
    }

}
