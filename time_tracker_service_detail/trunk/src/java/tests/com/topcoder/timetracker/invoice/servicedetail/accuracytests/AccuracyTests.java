/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.invoice.servicedetail.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.timetracker.invoice.servicedetail.accuracytests.dao.impl.AccracyTestServiceDetailDAOImpl;
import com.topcoder.timetracker.invoice.servicedetail.accuracytests.ejb.AccuracyTestsServiceDetailBean;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author KLW
 * @version 1.1
 */
public class AccuracyTests extends TestCase {
    /**
     * Test suite.
     * @return Test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AccuracyTestArgumentCheck.class);
        suite.addTestSuite(AccuracyTestInvoiceServiceDetail.class);
        suite.addTestSuite(AccuracyTestServiceDetailManagerImpl.class);
        suite.addTestSuite(AccracyTestServiceDetailDAOImpl.class);
        suite.addTestSuite(AccuracyTestsBatchExecutionException.class);
        suite.addTestSuite(AccuracyTestsServiceDetailBean.class);
        
        return suite;
    }
}
