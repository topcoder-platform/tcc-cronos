/**
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved
 */
package com.topcoder.service.digitalrun.track;

import com.topcoder.service.digitalrun.track.dao.implementations.AbstractDAOTests;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunPointsCalculatorDAOTests;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunProjectTypeDAOTests;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackDAOTests;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackStatusDAOTests;
import com.topcoder.service.digitalrun.track.dao.implementations.JPADigitalRunTrackTypeDAOTests;
import com.topcoder.service.digitalrun.track.manager.bean.DigitalRunTrackManagerBeanTests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author waits
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All the unit test cases.
     * </p>
     *
     * @return Test into JUnit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTest(DigitalRunTrackManagerBeanTests.suite());
        suite.addTest(JPADigitalRunPointsCalculatorDAOTests.suite());
        suite.addTest(JPADigitalRunProjectTypeDAOTests.suite());
        suite.addTest(JPADigitalRunTrackDAOTests.suite());
        suite.addTest(JPADigitalRunTrackStatusDAOTests.suite());
        suite.addTest(JPADigitalRunTrackTypeDAOTests.suite());
        suite.addTestSuite(AbstractDAOTests.class);
        suite.addTestSuite(ConfigurationProviderTests.class);
        suite.addTestSuite(DigitalRunTrackFilterFactoryTests.class);
        suite.addTestSuite(DigitalRunTrackManagerBeanConfigurationExceptionTests.class);
        suite.addTestSuite(DigitalRunTrackManagerExceptionTests.class);
        suite.addTestSuite(DigitalRunTrackManagerPersistenceExceptionTests.class);
        suite.addTestSuite(EntityNotFoundExceptionTests.class);
        suite.addTestSuite(HelperTests.class);
        suite.addTest(Demo.suite());
        //suite.addTestSuite(SaveTest.class);
        return suite;
    }
}
