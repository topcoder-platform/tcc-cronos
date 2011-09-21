/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.action.accuracytests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.topcoder.accounting.action.BaseActionAccuracyTest;
import com.topcoder.accounting.action.BaseBillingCostAuditActionAccuracyTest;
import com.topcoder.accounting.action.BaseBillingCostReportActionAccuracyTest;
import com.topcoder.accounting.action.accuracytests.converter.DateConverterAccuracyTest;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ BaseActionAccuracyTest.class, BaseBillingCostReportActionAccuracyTest.class,
    BaseBillingCostAuditActionAccuracyTest.class, BillingCostReportActionAccuracyTest.class,
    DateConverterAccuracyTest.class, BillingCostReportQuickBooksExportActionAccuracyTest.class,
    BillingCostExportDetailsActionAccuracyTest.class, AuditHistoryActionAccuracyTest.class,
    BillingCostExportHistoryActionAccuracyTest.class })
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * Test suite.
     * </p>
     *
     * @return the test suite
     */
    public static Test suite() {
        return new JUnit4TestAdapter(AccuracyTests.class);
    }
}
