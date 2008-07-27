/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class StressTests extends TestCase {

    /**
     * The suite test.
     * @return the suite test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTest(JPADigitalRunPointsDAOTest.suite());
        suite.addTest(JPADigitalRunPointsOperationDAOTest.suite());
        suite.addTest(JPADigitalRunPointsStatusDAOTest.suite());
        suite.addTest(JPADigitalRunPointsTypeDAOTest.suite());

        return suite;
    }

}
