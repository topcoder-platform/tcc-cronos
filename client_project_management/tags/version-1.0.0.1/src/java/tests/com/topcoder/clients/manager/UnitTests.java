/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.clients.manager;

import com.topcoder.clients.manager.dao.TestAbstractDAOManager;
import com.topcoder.clients.manager.dao.TestDAOClientManager;
import com.topcoder.clients.manager.dao.TestDAOCompanyManager;
import com.topcoder.clients.manager.dao.TestDAOProjectManagerAccuracy;
import com.topcoder.clients.manager.dao.TestDAOProjectManagerFailure;
import com.topcoder.clients.manager.dao.TestEntityFormat;

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

    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestClientEntityNotFoundException.class);
        suite.addTestSuite(TestManagerConfigurationException.class);
        suite.addTestSuite(TestCompanyEntityNotFoundException.class);
        suite.addTestSuite(TestManagerException.class);
        suite.addTestSuite(TestClientManagerException.class);
        suite.addTestSuite(TestProjectEntityNotFoundException.class);
        suite.addTestSuite(TestCompanyManagerException.class);
        suite.addTestSuite(TestProjectManagerException.class);


        suite.addTestSuite(TestAbstractDAOManager.class);

        suite.addTestSuite(TestDAOClientManager.class);

        suite.addTestSuite(TestDAOCompanyManager.class);

        suite.addTestSuite(TestDAOProjectManagerAccuracy.class);

        suite.addTestSuite(TestDAOProjectManagerFailure.class);

        suite.addTestSuite(Demo.class);

        suite.addTestSuite(TestEntityFormat.class);
        return suite;
    }

}
