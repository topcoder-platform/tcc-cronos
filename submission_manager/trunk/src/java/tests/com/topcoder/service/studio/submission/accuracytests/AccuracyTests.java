/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Accuracy test cases.
 * </p>
 * @author TopCoder
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * <p>
     * Suite.
     * </p>
     * @return the test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(SubmissionManagerBeanAccV12Tests.class);

        return suite;
    }
}
