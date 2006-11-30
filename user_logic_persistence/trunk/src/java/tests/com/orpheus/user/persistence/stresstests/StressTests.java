/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Stress test cases.</p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class StressTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(LocalOrpheusPendingConfirmationStorageStressTests.class);
        suite.addTestSuite(RemoteOrpheusPendingConfirmationStorageStressTests.class);
        suite.addTestSuite(LocalOrpheusUserProfilePersistenceStressTests.class);
        suite.addTestSuite(RemoteOrpheusUserProfilePersistenceStressTests.class);

        return suite;
    }
}
