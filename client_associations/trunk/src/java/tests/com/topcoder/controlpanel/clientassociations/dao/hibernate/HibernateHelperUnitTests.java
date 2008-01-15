/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations.dao.hibernate;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link HibernateHelper}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateHelperUnitTests extends TestCase {

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(HibernateHelperUnitTests.class);
    }

    /**
     * <p>
     * Unit test for <code>{@link HibernateHelper#getSessionFactory()}</code> method.
     * </p>
     * <p>
     * Should return an non-null instance.
     * </p>
     */
    public void testGetSessionFactory_accuracy() {
        assertNotNull("Should not return null.", HibernateHelper.getSessionFactory());
    }
}
