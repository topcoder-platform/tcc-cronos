/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.accounting.stresstests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 *
 * @author dingying131
 * @version 1.0
 */
public class StressTests {

    /**
     * <p>
     * Create stress tests suite.
     * </p>
     *
     * @return stress tests suite
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(BillingCostAuditServiceImplStressTest.suite());
        suite.addTest(LookupServiceImplStressTest.suite());
        suite.addTest(BillingCostDataServiceImplStressTest.suite());
        return suite;
    }

}
