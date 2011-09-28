/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.accounting.action.converter.DateConversionExceptionTest;
import com.topcoder.accounting.action.converter.DateConverterTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { AuditHistoryActionTest.class, BaseActionTest.class,
        BaseBillingCostAuditActionTest.class,
        BaseBillingCostReportActionTest.class,
        BillingCostActionConfigurationExceptionTest.class,
        BillingCostExportDetailsActionTest.class,
        BillingCostExportHistoryActionTest.class,
        BillingCostReportActionTest.class,
        BillingCostReportQuickBooksExportActionTest.class,
        DateConversionExceptionTest.class, DateConverterTest.class,
        HelperTests.class
// ,Demo.class
})
public class UnitTests extends TestCase {
    /**
     * <p>
     * Test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        return new JUnit4TestAdapter(UnitTests.class);
    }

}
