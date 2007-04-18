/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.failuretests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;


/**
 * <p>
 * Defines a mock class which implements the <code>RejectReasonDAO</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockRejectReasonDAO implements RejectReasonDAO {
    /** Represents the flag which will make all the methods of this mock class always throw exception. */
    private boolean alwaysThrowException = false;

    /**
     * Creates a new MockRejectReasonDAO object.
     */
    public MockRejectReasonDAO() {
    }

    /**
     * <p>
     * Sets the flag which will make all the methods of this mock class always throw exception.
     * </p>
     */
    public void setAlwaysThrowException() {
        alwaysThrowException = true;
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param rejectReason ignored.
     * @param username ignored.
     * @param isAudit ignored.
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public RejectReason createRejectReason(RejectReason rejectReason, String username, boolean isAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param rejectReason ignored.
     * @param username ignored.
     * @param isAudit ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void deleteRejectReason(RejectReason rejectReason, String username, boolean isAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public RejectReason[] listRejectReasons() {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Retrieves a RejectReason object for given id.
     * </p>
     *
     * @param id the given id.
     *
     * @return a RejectReason object for given id.
     *
     * @throws RejectReasonDAOException if it is required to be thrown.
     */
    public RejectReason retrieveRejectReason(long id) throws RejectReasonDAOException {
        if (alwaysThrowException) {
            throw new RejectReasonDAOException("RejectReasonDAOException");
        }

        RejectReason rejectReason = TestHelper.BuildRejectReason();

        if (rejectReason.getId() == id) {
            return rejectReason;
        }

        return null;
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param filter ignored.
     *
     * @return ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public RejectReason[] searchRejectReasons(Filter filter) {
        throw new UnsupportedOperationException("not implemented.");
    }

    /**
     * <p>
     * Mock method, not implemented.
     * </p>
     *
     * @param rejectReason ignored.
     * @param username ignored.
     * @param isAudit ignored.
     *
     * @throws UnsupportedOperationException always thrown.
     */
    public void updateRejectReason(RejectReason rejectReason, String username, boolean isAudit) {
        throw new UnsupportedOperationException("not implemented.");
    }
}
