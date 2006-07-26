/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.resource.persistence;

import com.topcoder.management.resource.persistence.sql.BenchmarkTest;
import com.topcoder.management.resource.persistence.sql.DemoTest;
import com.topcoder.management.resource.persistence.sql.TestSqlResourcePersistence;
import com.topcoder.management.resource.persistence.sql.TestSqlResourcePersistenceArgumentFailure;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Unit test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * Aggregates all Unit test cases.
     * @return Test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestSqlResourcePersistenceArgumentFailure.class);
        suite.addTestSuite(TestSqlResourcePersistence.class);
        suite.addTestSuite(DemoTest.class);
        suite.addTestSuite(BenchmarkTest.class);

        return suite;
    }
}
