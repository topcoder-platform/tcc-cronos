/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.resource.persistence.sql.accuracytests;



import com.topcoder.management.resource.persistence.sql.accuracytests.AbstractResourcePersistenceAccuracyTests;
import com.topcoder.management.resource.persistence.sql.accuracytests.SqlResourcePersistenceAccuracyTests;
import com.topcoder.management.resource.persistence.sql.accuracytests.UnmanagedTransactionResourcePersistenceAccuracyTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author tianniu
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * The test suite.
     * </p>
     *
     * @return the test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // package com.topcoder.management.resource.persistence.sql
        suite.addTestSuite(AbstractResourcePersistenceAccuracyTests.class);
        suite.addTestSuite(SqlResourcePersistenceAccuracyTests.class);
        suite.addTestSuite(UnmanagedTransactionResourcePersistenceAccuracyTests.class);
 
        return suite;
    }
}