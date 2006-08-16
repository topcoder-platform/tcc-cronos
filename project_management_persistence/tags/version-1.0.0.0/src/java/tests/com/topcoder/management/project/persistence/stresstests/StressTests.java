/**
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.project.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author mgmg
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(InformixProjectPersistenceStressTest.class);

        return suite;
    }
}
