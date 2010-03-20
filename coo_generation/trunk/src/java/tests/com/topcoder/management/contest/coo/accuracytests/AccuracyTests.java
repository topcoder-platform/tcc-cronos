/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases.</p>
 *
 * @author myxgyy
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * Aggregates all accuracy tests together.
     *
     * @return all accuracy tests in one suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(PDFCOOReportSerializerAccuracyTest.class);

        return suite;
    }
}