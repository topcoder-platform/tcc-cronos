/**
 *
 * Copyright (c) 2007, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.audit.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.TestResult;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(ApplicationAreaTest.suite());
        suite.addTest(AuditDetailTest.suite());
        suite.addTest(AuditEjbTest.suite());
        suite.addTest(AuditHeaderTest.suite());
        suite.addTest(InformixAuditPersistenceTest.suite());
        return suite;
    }

}
