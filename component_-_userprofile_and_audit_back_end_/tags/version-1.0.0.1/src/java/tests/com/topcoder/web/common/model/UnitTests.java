/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.web.common.dao.hibernate.AuditDAOHibernateTest;
import com.topcoder.web.common.dao.hibernate.HelperTest;
import com.topcoder.web.common.dao.hibernate.UserDAOHibernateTest;
import com.topcoder.web.ejb.user.UserBeanTest;

/**
 * Aggregates all Unit tests for this component.
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UnitTests extends TestCase {
    /**
     * <p>
     * All unit test cases.
     * </p>
     * @return The test suite.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(UserTest.class);
        suite.addTestSuite(UserProfileTest.class);
        suite.addTestSuite(UserDAOHibernateTest.class);
        suite.addTestSuite(UserBeanTest.class);
        suite.addTestSuite(Demo.class);
        suite.addTestSuite(AuditRecordTest.class);
        suite.addTestSuite(AuditDAOHibernateTest.class);
        suite.addTestSuite(HelperTest.class);

        return suite;
    }
}
