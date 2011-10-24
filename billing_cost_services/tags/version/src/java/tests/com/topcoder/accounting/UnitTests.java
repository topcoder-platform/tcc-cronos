/**
 *
 * Copyright (c) 2011, TopCoder, Inc. All rights reserved
 */
package com.topcoder.accounting;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.accounting.entities.dao.AccountingAuditRecordTest;
import com.topcoder.accounting.entities.dao.BillingCostExportDetailTest;
import com.topcoder.accounting.entities.dao.BillingCostExportTest;
import com.topcoder.accounting.entities.dao.IdentifiableEntityTest;
import com.topcoder.accounting.entities.dao.PaymentAreaTest;
import com.topcoder.accounting.entities.dto.AccountingAuditRecordCriteriaTest;
import com.topcoder.accounting.entities.dto.BillingCostExportHistoryCriteriaTest;
import com.topcoder.accounting.entities.dto.BillingCostReportCriteriaTest;
import com.topcoder.accounting.entities.dto.BillingCostReportEntryTest;
import com.topcoder.accounting.entities.dto.PagedResultTest;
import com.topcoder.accounting.entities.dto.PaymentIdentifierTest;
import com.topcoder.accounting.entities.dto.QuickBooksImportUpdateTest;
import com.topcoder.accounting.service.BillingCostConfigurationExceptionTest;
import com.topcoder.accounting.service.BillingCostServiceExceptionTest;
import com.topcoder.accounting.service.EntityNotFoundExceptionTest;
import com.topcoder.accounting.service.impl.BaseServiceTest;
import com.topcoder.accounting.service.impl.BillingCostAuditServiceImplTest;
import com.topcoder.accounting.service.impl.BillingCostDataServiceImplTest;
import com.topcoder.accounting.service.impl.LookupServiceImplTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(AccountingAuditRecordTest.suite());
        suite.addTest(BillingCostExportDetailTest.suite());
        suite.addTest(BillingCostExportTest.suite());
        suite.addTest(IdentifiableEntityTest.suite());
        suite.addTest(PaymentAreaTest.suite());

        suite.addTest(AccountingAuditRecordCriteriaTest.suite());
        suite.addTest(BillingCostExportHistoryCriteriaTest.suite());
        suite.addTest(BillingCostReportCriteriaTest.suite());
        suite.addTest(BillingCostReportEntryTest.suite());
        suite.addTest(PagedResultTest.suite());
        suite.addTest(PaymentIdentifierTest.suite());
        suite.addTest(QuickBooksImportUpdateTest.suite());

        suite.addTest(BillingCostConfigurationExceptionTest.suite());
        suite.addTest(BillingCostServiceExceptionTest.suite());
        suite.addTest(EntityNotFoundExceptionTest.suite());

        suite.addTest(BaseServiceTest.suite());
        suite.addTest(BillingCostAuditServiceImplTest.suite());
        suite.addTest(BillingCostDataServiceImplTest.suite());
        suite.addTest(LookupServiceImplTest.suite());

        suite.addTest(DemoTest.suite());

        return suite;
    }

}
