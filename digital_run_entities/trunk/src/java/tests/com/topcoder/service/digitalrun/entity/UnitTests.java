/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.digitalrun.entity;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.digitalrun.entity.idgenerator.DigitalRunEntityIDGeneratorTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class UnitTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(BaseEntityTest.suite());
        suite.addTest(DigitalRunPointsOperationTest.suite());
        suite.addTest(DigitalRunPointsReferenceTypeTest.suite());
        suite.addTest(DigitalRunPointsStatusTest.suite());
        suite.addTest(DigitalRunPointsTest.suite());
        suite.addTest(DigitalRunPointsTypeTest.suite());
        suite.addTest(HelperTest.suite());
        suite.addTest(PointsCalculatorTest.suite());
        suite.addTest(ProjectTypeTest.suite());
        suite.addTest(TrackContestResultCalculatorTest.suite());
        suite.addTest(TrackContestTest.suite());
        suite.addTest(TrackContestTypeTest.suite());
        suite.addTest(TrackStatusTest.suite());
        suite.addTest(TrackTest.suite());
        suite.addTest(TrackTypeTest.suite());
        suite.addTest(DigitalRunEntityIDGeneratorTest.suite());
        suite.addTest(PersistenceTests.suite());
        suite.addTest(PersistenceFailureTests.suite());
        suite.addTest(Demo.suite());
        return suite;
    }

}
