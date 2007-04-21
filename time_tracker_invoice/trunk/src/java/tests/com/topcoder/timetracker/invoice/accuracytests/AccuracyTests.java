/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author enefem21
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all Accuracy test cases.
     * </p>
     *
     * @return all Accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(InvoiceAccuracyTests.suite());
        suite.addTest(InvoiceUnrecognizedEntityExceptionAccuracyTests.suite());
        suite.addTest(InvoiceStatusAccuracyTests.suite());
        suite.addTest(InvoiceDataAccessExceptionAccuracyTests.suite());
        suite.addTest(InvoiceConfigurationExceptionAccuracyTests.suite());
        suite.addTest(InvoiceDAOFactoryAccuracyTests.suite());
        suite.addTest(InvoiceExceptionAccuracyTests.suite());
        suite.addTest(InvoiceSessionBeanAccuracyTests.suite());
        suite.addTest(InformixInvoiceDAOAccuracyTests.suite());
        suite.addTest(InformixInvoiceStatusDAOAccuracyTests.suite());
        suite.addTest(InformixInvoiceFilterFactoryAccuracyTests.suite());

        return suite;
    }

}
