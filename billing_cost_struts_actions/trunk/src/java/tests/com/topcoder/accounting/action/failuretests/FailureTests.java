/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Test suite for the failure tests.
     *
     * @return the failure test suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BillingCostExportHistoryActionFailureTests.suite());
        suite.addTest(BaseBillingCostReportActionFailureTests.suite());
        suite.addTest(BaseActionFailureTests.suite());
        suite.addTest(BillingCostReportQuickBooksExportActionFailureTests.suite());
        suite.addTest(BillingCostReportActionFailureTests.suite());
        suite.addTest(AuditHistoryActionFailureTests.suite());
        suite.addTest(BillingCostExportDetailsActionFailureTests.suite());
        suite.addTest(DateConverterFailureTests.suite());

        return suite;
    }

}
