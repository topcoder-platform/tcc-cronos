/**
 *
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.digitalrun.track.failuretests;

import com.topcoder.service.digitalrun.track.failuretests.dao.implementations.JPADigitalRunPointsCalculatorDAOTest;
import com.topcoder.service.digitalrun.track.failuretests.dao.implementations.JPADigitalRunProjectTypeDAOTest;
import com.topcoder.service.digitalrun.track.failuretests.dao.implementations.JPADigitalRunTrackDAOTest;
import com.topcoder.service.digitalrun.track.failuretests.dao.implementations.JPADigitalRunTrackStatusDAOTest;
import com.topcoder.service.digitalrun.track.failuretests.dao.implementations.JPADigitalRunTrackTypeDAOTest;
import com.topcoder.service.digitalrun.track.failuretests.dao.implementations.AbstractDAOTest;
import com.topcoder.service.digitalrun.track.failuretests.manager.bean.DigitalRunTrackManagerBeanTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Failure test cases.</p>
 *
 * @author Orange_Cloud
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * Returns all failure tests in one suite.
     *
     * @return all failure tests together.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(JPADigitalRunPointsCalculatorDAOTest.suite());
        suite.addTest(JPADigitalRunProjectTypeDAOTest.suite());
        suite.addTest(JPADigitalRunTrackDAOTest.suite());
        suite.addTest(JPADigitalRunTrackStatusDAOTest.suite());
        suite.addTest(JPADigitalRunTrackTypeDAOTest.suite());
        suite.addTest(DigitalRunTrackFilterFactoryTest.suite());
        suite.addTest(DigitalRunTrackManagerBeanTest.suite());
        suite.addTest(AbstractDAOTest.suite());
        return suite;
    }
}
