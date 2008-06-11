/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Stress test cases.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
public class StressTests extends TestCase {
    /**
     * Aggregates the test cases.
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(BinaryFileDependenciesEntryPersistenceStressTest.class);
        suite.addTestSuite(DefaultXmlDependenciesEntryPersistenceStressTest.class);
        suite.addTestSuite(JavaDependenciesEntryExtractorStressTest.class);
        suite.addTestSuite(DotNetDependenciesEntryExtractorStressTest.class);
        return suite;
    }
}
