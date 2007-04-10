/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company.failuretests;

import com.topcoder.timetracker.company.CompanyDAOSynchronizedWrapper;

import junit.framework.TestCase;

/**
 * <p>
 * Failure test for <code>{@link CompanyDAOSynchronizedWrapper}</code> class.
 * </p>
 * <p>
 * all the methods act as a delegate. so not tested.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class CompanyDAOSynchronizedWrapperFailureTests extends TestCase {

    /**
     * <p>
     * Failure test for
     * <code>{@link CompanyDAOSynchronizedWrapper#CompanyDAOSynchronizedWrapper(com.topcoder.timetracker.company.CompanyDAO)}</code>
     * constructor.
     * </p>
     */
    public void testCompanyDAOSynchronizedWrapper_NullCompanyDAO() {
        try {
            new CompanyDAOSynchronizedWrapper(null);
            fail("expect throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
