/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.dao.hibernate;

import junit.framework.TestCase;

import org.hibernate.Session;

import com.topcoder.web.common.HibernateUtils;

/**
 * Unit test cases for {@link Helper} class.
 * @author kalc
 * @version 1.0
 */
public class HelperTest extends TestCase {
    /**
     * Failure test case for {@link Helper#checkParam(org.hibernate.Session)} method. Expects an exception to be thrown.
     */
    public void testCheckParam() {
        try {
            Helper.checkParam(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test case for {@link Helper#checkParam(Session)} method.
     */
    public void testCheckParam1() {
        Session session = HibernateUtils.getSession();
        assertNotNull("Error checking the parameter", Helper.checkParam(session));
        HibernateUtils.closeSession();
    }
}
