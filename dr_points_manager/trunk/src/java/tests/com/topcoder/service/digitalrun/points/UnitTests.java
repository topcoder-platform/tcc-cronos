/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.digitalrun.points.dao.implementations.AbstractDAOTest;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsDAOTest;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsOperationDAOTest;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsReferenceTypeDAOTest;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsStatusDAOTest;
import com.topcoder.service.digitalrun.points.dao.implementations.JPADigitalRunPointsTypeDAOTest;
import com.topcoder.service.digitalrun.points.manager.bean.DigitalRunPointsManagerBeanTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * The suite test.
     *
     * @return the suite test
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        // DemoEJB
        suite.addTest(Demo.suite());
        suite.addTest(DemoEJB.suite());

        // package com.topcoder.service.digitalrun.points
        suite.addTest(ConfigurationProviderTest.suite());
        suite.addTest(DigitalRunPointsManagerBeanConfigurationExceptionTest.suite());
        suite.addTest(DigitalRunPointsManagerExceptionTest.suite());
        suite.addTest(DigitalRunPointsManagerPersistenceExceptionTest.suite());
        suite.addTest(EntityNotFoundExceptionTest.suite());
        suite.addTest(DigitalRunPointsFilterFactoryTest.suite());
        suite.addTest(HelperTest.suite());

        // package com.topcoder.service.digitalrun.points.manager.bean
        suite.addTest(DigitalRunPointsManagerBeanTest.suite());

        // package com.topcoder.service.digitalrun.points.dao.implementations
        suite.addTest(AbstractDAOTest.suite());
        suite.addTest(JPADigitalRunPointsDAOTest.suite());
        suite.addTest(JPADigitalRunPointsOperationDAOTest.suite());
        suite.addTest(JPADigitalRunPointsReferenceTypeDAOTest.suite());
        suite.addTest(JPADigitalRunPointsStatusDAOTest.suite());
        suite.addTest(JPADigitalRunPointsTypeDAOTest.suite());

        return suite;
    }

}
