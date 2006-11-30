/*
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.user.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all failure test cases.
 * </p>
 *
 * @author crackme
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Aggregates all failure test cases.
     *
     * @return Test instance
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestUserProfileTranslator.class);
        suite.addTestSuite(TestSQLServerUserProfileDAO.class);
        suite.addTestSuite(TestSQLServerPendingConfirmationDAO.class);
        suite.addTestSuite(TestOrpheusUserProfilePersistence.class);
        suite.addTestSuite(TestOrpheusPendingConfirmationStorage.class);
        suite.addTestSuite(TestConfirmationMessageTranslator.class);

        return suite;
    }
}
