/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class FailureTests extends TestCase {

    /**
     * The suite test.
     *
     * @return the suite test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(AbstractDAOFailureTests.class);
        suite.addTestSuite(ConfigurationProviderFailureTests.class);
        suite.addTestSuite(DigitalRunPointsFilterFactoryFailureTests.class);

        suite.addTestSuite(JPADigitalRunPointsDAOFailureTests.class);
        suite.addTestSuite(JPADigitalRunPointsOperationDAOFailureTests.class);
        suite.addTestSuite(JPADigitalRunPointsReferenceTypeDAOFailureTests.class);
        suite.addTestSuite(JPADigitalRunPointsStatusDAOFailureTests.class);
        suite.addTestSuite(JPADigitalRunPointsTypeDAOFailureTests.class);
        return suite;
    }

}
