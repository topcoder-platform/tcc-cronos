/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author enefem21
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * Returns the test suite.
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(FailureTestInformixInvoiceDAO.class);
        suite.addTestSuite(FailureTestInvoiceStatus.class);
        suite.addTestSuite(FailureTestInvoiceManagerDelegate.class);
        suite.addTestSuite(FailureTestInvoiceDAOFactory.class);
        suite.addTestSuite(FailureTestInformixInvoiceStatusDAO.class);
        suite.addTestSuite(FailureTestInformixInvoiceFilterFactory.class);
        return suite;
    }

}
