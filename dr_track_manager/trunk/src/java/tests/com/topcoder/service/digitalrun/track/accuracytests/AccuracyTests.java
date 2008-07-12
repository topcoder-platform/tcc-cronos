/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import com.topcoder.service.digitalrun.track.ConfigurationProviderTests;
import com.topcoder.service.digitalrun.track.Demo;
import com.topcoder.service.digitalrun.track.DigitalRunTrackFilterFactoryTests;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerBeanConfigurationExceptionTests;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerExceptionTests;
import com.topcoder.service.digitalrun.track.DigitalRunTrackManagerPersistenceExceptionTests;
import com.topcoder.service.digitalrun.track.EntityNotFoundExceptionTests;
import com.topcoder.service.digitalrun.track.HelperTests;
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
 * This test case aggregates all accuracy test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * All the accuracy test cases.
     * </p>
     *
     * @return Test into JUnit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(AbstractDAOAccTests.class);
        suite.addTestSuite(ConfigurationProviderAccTests.class);
        suite.addTestSuite(DigitalRunTrackFilterFactoryAccTests.class);
        suite.addTestSuite(DigitalRunTrackManagerBeanAccTests.class);
        suite.addTestSuite(JPADigitalRunPointsCalculatorDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunProjectTypeDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunTrackDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunTrackStatusDAOAccTests.class);
        suite.addTestSuite(JPADigitalRunTrackTypeDAOAccTests.class);

        return suite;
    }
}
