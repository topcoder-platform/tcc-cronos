/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.clients.dao.DAOConfigurationExceptionTest;
import com.topcoder.clients.dao.DAOExceptionTest;
import com.topcoder.clients.dao.EntityNotFoundExceptionTest;
import com.topcoder.clients.dao.ejb3.ClientDAOBeanTest;
import com.topcoder.clients.dao.ejb3.ClientStatusDAOBeanTest;
import com.topcoder.clients.dao.ejb3.CompanyDAOBeanTest;
import com.topcoder.clients.dao.ejb3.GenericEJB3DAOTest;
import com.topcoder.clients.dao.ejb3.HelperTest;
import com.topcoder.clients.dao.ejb3.ProjectDAOBeanTest;
import com.topcoder.clients.dao.ejb3.ProjectStatusDAOBeanTest;
import com.topcoder.clients.dao.ejb3.SearchByFilterUtilityImplTest;
import com.topcoder.clients.model.AuditableEntityTest;
import com.topcoder.clients.model.ClientStatusTest;
import com.topcoder.clients.model.ClientTest;
import com.topcoder.clients.model.CompanyTest;
import com.topcoder.clients.model.ProjectContestFeeAuditTest;
import com.topcoder.clients.model.ProjectContestFeeTest;
import com.topcoder.clients.model.ProjectStatusTest;
import com.topcoder.clients.model.ProjectTest;

/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {

    /**
     * <p>
     * This test case aggregates all unit tests for the component.
     * </p>
     * @return the test suite for the component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(EntityNotFoundExceptionTest.class);
        suite.addTestSuite(DAOExceptionTest.class);
        suite.addTestSuite(DAOConfigurationExceptionTest.class);

        suite.addTestSuite(CompanyDAOBeanTest.class);
        suite.addTestSuite(ClientStatusDAOBeanTest.class);
        suite.addTestSuite(ProjectDAOBeanTest.class);
        suite.addTestSuite(ClientDAOBeanTest.class);
        suite.addTestSuite(ProjectStatusDAOBeanTest.class);
        suite.addTestSuite(HelperTest.class);

        suite.addTestSuite(ProjectStatusTest.class);
        suite.addTestSuite(CompanyTest.class);
        suite.addTestSuite(ClientStatusTest.class);
        suite.addTestSuite(ProjectTest.class);
        suite.addTestSuite(AuditableEntityTest.class);
        suite.addTestSuite(ClientTest.class);
        suite.addTestSuite(GenericEJB3DAOTest.class);
        suite.addTestSuite(SearchByFilterUtilityImplTest.class);

        suite.addTestSuite(DemoTests.class);

        suite.addTestSuite(ProjectContestFeeTest.class);
        suite.addTestSuite(ProjectContestFeeAuditTest.class);
        return suite;
    }

}
