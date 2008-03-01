/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.prerequisite.document.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Aggregates all stress tests.
     * </p>
     *
     * @return test suite aggregating all stress tests.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(PrerequisiteDocumentManagerStressTests.suite());

        return suite;
    }
}
