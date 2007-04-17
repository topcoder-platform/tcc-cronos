/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test case aggregates all accuracy test cases.
     * </p>
     *
     * @return all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // tests for package com.topcoder.timetracker.client
        suite.addTest(ClientUtilityImplAccuracyTests.suite());
        suite.addTest(ClientAccuracyTests.suite());
        suite.addTest(BatchOperationExceptionAccuracyTests.suite());
        suite.addTest(ClientPersistenceExceptionAccuracyTests.suite());
        suite.addTest(ConfigurationExceptionAccuracyTests.suite());
        suite.addTest(ClientAuditExceptionAccuracyTests.suite());
        suite.addTest(InvalidClientPropertyExceptionAccuracyTests.suite());
        suite.addTest(InvalidClientIDExceptionAccuracyTests.suite());
        suite.addTest(DepthAccuracyTests.suite());
        suite.addTest(PropertyOperationExceptionAccuracyTests.suite());
        suite.addTest(ClientFilterFactoryAccuracyTests.suite());

        // tests for package com.topcoder.timetracker.client.db
        suite.addTest(ClientProjectColumnNameAccuracyTests.suite());
        suite.addTest(ClientColumnNameAccuracyTests.suite());
        suite.addTest(InformixClientDAOAccuracyTests.suite());

        // tests for package com.topcoder.timetracker.client.depth
        suite.addTest(ClientOnlyDepthAccuracyTests.suite());
        suite.addTest(SummaryDepthAccuracyTests.suite());
        suite.addTest(ClientProjectDepthAccuracyTests.suite());
        suite.addTest(ClientIDOnlyDepthAccuracyTests.suite());

        // tests for package com.topcoder.timetracker.client.ejb
        suite.addTest(ClientUtilityFactoryAccuracyTests.suite());
        suite.addTest(ClientUtilitySessionBeanAccuracyTests.suite());

        return suite;
    }

}
