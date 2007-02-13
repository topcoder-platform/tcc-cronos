/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.accuracytests;

import com.topcoder.timetracker.common.RejectReason;
import com.topcoder.timetracker.common.RejectReasonDAOException;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>RejectReasonDAOException </code>.
 *
 * @author Chenhong
 * @version 2.0
 */
public class TestRejectReasonDAOExceptionAccuracy extends TestCase {

    /**
     * Represents the RejectReasonDAOException instance for test.
     */
    private RejectReasonDAOException exception = null;

    /**
     * Represents RejectReason instance for test.
     */
    private RejectReason reason = null;

    /**
     * Set up the enviroment.
     */
    public void setUp() {
        reason = new RejectReason();
        reason.setCompanyId(111);

        exception = new RejectReasonDAOException("error", new NullPointerException(), reason);
    }

    /**
     * Test constructor.
     *
     */
    public void testRejectReasonDAOException() {
        assertNotNull("Should not be null.", exception);
    }

    /**
     * test method getProblemRejectReason
     *
     */
    public void testGetProblemRejectReason() {
        assertEquals("Equal is expected.", reason, exception.getProblemRejectReason());
    }

}
