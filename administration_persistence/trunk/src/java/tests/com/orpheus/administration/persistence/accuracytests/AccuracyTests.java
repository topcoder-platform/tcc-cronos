/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all accuracy test cases for the <i>Administration Persistence</i> component.</p>
 *
 * @author waits
 * @version 1.0
 */

public class AccuracyTests extends TestCase {
    /**
     * Returns all accuracy test cases for the <i>Administration Persistence</i> component.
     *
     * @return all accuracy test cases for the <i>Administration Persistence</i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AdminDataBeanAccTests.class);
        suite.addTestSuite(AndFilterAccTests.class);
        suite.addTestSuite(DAOFactoryAccTests.class);
        suite.addTestSuite(LocalOrpheusMessageDataStoreAccTests.class);
        suite.addTestSuite(LocalOrpheusMessengerPluginAccTests.class);
        suite.addTestSuite(MessageBeanAccTests.class);
        suite.addTestSuite(RemoteOrpheusMessageDataStoreAccTests.class);
        suite.addTestSuite(RemoteOrpheusMessengerPluginAccTests.class);
        suite.addTestSuite(SQLServerAdminDataDAOAccTests.class);
        suite.addTestSuite(SQLServerMessageDAOAccTests.class);
        return suite;
    }
}
