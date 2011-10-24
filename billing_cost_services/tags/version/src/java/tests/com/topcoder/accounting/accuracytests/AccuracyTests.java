/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.accuracytests;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * <p>This test case aggregates all failure test cases.</p>
 *
 * @author gjw99
 * @version 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({QuickBooksImportUpdateTests.class,
	BillingCostReportCriteriaTests.class,
	BillingCostExportTests.class,
	BillingCostExportHistoryCriteriaTests.class,
	BillingCostExportDetailTests.class,
	AccountingAuditRecordTests.class,
	AccountingAuditRecordCriteriaTests.class,
	BillingCostReportEntryTests.class,
	PaymentAreaTests.class,
	PaymentIdentifierTests.class,
	BillingCostAuditServiceImplTests.class
})
public class AccuracyTests extends TestCase {
}
