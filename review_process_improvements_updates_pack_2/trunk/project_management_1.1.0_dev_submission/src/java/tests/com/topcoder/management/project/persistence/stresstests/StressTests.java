/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases for Project Management Persistence 1.1.
 * </p>
 *
 * @author Hacker_QC, morehappiness
 * @version 1.1
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(PersistenceStressTest.class);
        suite.addTestSuite(ApplicationsManagerImplStressTest.class);

        return suite;
    }
}
