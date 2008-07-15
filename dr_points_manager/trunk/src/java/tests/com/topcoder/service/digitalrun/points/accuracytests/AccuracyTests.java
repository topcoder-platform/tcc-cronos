/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    /**
     * The suite test.
     * @return the suite test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AbstractDAOAccTests.class);
        suite.addTestSuite(ConfigurationProviderAccTests.class);
        suite.addTestSuite(DigitalRunPointsFilterFactoryAccTests.class);
        suite.addTestSuite(DigitalRunPointsManagerBeanAccTests.class);
        suite.addTestSuite(JPADigitalRunPointsDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunPointsOperationDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunPointsStatusDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunPointsTypeDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunPointsReferenceTypeDAOAccTests.class);
        return suite;
    }

}
