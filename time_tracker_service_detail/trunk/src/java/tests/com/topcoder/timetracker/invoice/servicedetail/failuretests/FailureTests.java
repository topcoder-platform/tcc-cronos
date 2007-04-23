/*
 * Copyright (C) 2007 TopCoder Inc., All rights reserved.
 */
 package com.topcoder.timetracker.invoice.servicedetail.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class FailureTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(InvoiceServiceDetailFailureTests.suite());
        suite.addTest(ServiceDetailManagerImplFailureTests.suite());
        suite.addTest(ServiceDetailBeanFailureTests.suite());
        suite.addTest(ServiceDetailDAOImplFailureTests.suite());
        return suite;
    }

}
