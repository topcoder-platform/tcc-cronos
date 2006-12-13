/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import java.util.Date;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.orpheus.administration.persistence.AdminSummary;
import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.util.puzzle.PuzzleData;

/**
 * <p>
 * Mock implementation of <code>AdminDataBean</code> only for testing purpose.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAdminDataBean implements SessionBean {
    /**
     * A flag to represent if Domain is Approval.
     */
    private static boolean isDomainApproval = false;

    /**
     * A flag for approve.
     */
    private static boolean isApproveWinner = false;

    /**
     * A flag for reject.
     */
    private static boolean isRejectWinner = false;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MockAdminDataBean() {
    }

    /**
     * <p>
     * Retrieves an administrative data summary from the persistent store. Uses
     * the DAO FActory to obtain the AdminDataDAO to delegate this action.
     * </p>
     *
     * @return the admin summary
     */
    public AdminSummary getAdminSummary() {
        return new AdminSummary() {

            public int getActiveGameCount() {
                // TODO Auto-generated method stub
                return 0;
            }

            public int getPendingSponsorCount() {
                // TODO Auto-generated method stub
                return 0;
            }

            public int getPendingWinnerCount() {
                // TODO Auto-generated method stub
                return 0;
            }
        };
    }

    /**
     * <p>
     * Sets the approval flag for the specified domain. Uses the DAO FActory to
     * obtain the AdminDataDAO to delegate this action.
     * </p>
     *
     * @param domainId
     *            domain id
     * @param approved
     *            approved flag
     */
    public void setDomainApproval(long domainId, boolean approved) {
        isDomainApproval = approved;
    }

    /**
     * Returns if domain is approval.
     *
     * @return true if approval
     */
    public static boolean isDomainApproval() {
        try {
            return isDomainApproval;
        } finally {
            isDomainApproval = false;
        }
    }

    /**
     * <p>
     * Sets the approval flag for the specified image. Uses the DAO FActory to
     * obtain the AdminDataDAO to delegate this action.
     * </p>
     *
     * @param imageId
     *            image id
     * @param approved
     *            approved flag
     */
    public void setImageApproval(long imageId, boolean approved) {

    }

    /**
     * <p>
     * Records the specified PuzzleData objects in the application's database,
     * returning an array of the unique IDs assigned to them (in the same order
     * as the puzzles appear in the argument). Uses the DAO FActory to obtain
     * the AdminDataDAO to delegate this action.
     * </p>
     *
     * @param puzzles
     *            puzzles to store
     * @return ids of stored puzzles
     */
    public long[] storePuzzles(PuzzleData[] puzzles) {

        return new long[] {1};
    }

    /**
     * <p>
     * Retrieves the currently-pending winners' information. Will return an
     * empty array of there are no pending winners. Uses the DAO FActory to
     * obtain the AdminDataDAO to delegate this action.
     * </p>
     *
     * @return the pending winners in all games
     */
    public PendingWinner[] getPendingWinners() {
        MockPendingWinner pendingWinner = new MockPendingWinner();
        pendingWinner.setPlayerId(1);
        pendingWinner.setGameId(1);
        return new PendingWinner[] {pendingWinner};
    }

    /**
     * <p>
     * Approves the specified PendingWinner's win. Uses the DAO FActory to
     * obtain the AdminDataDAO to delegate this action.
     * </p>
     *
     * @param winner
     *            winner to approve
     * @param date
     *            date of approval
     */
    public void approveWinner(PendingWinner winner, Date date) {
        isApproveWinner = true;
    }

    /**
     * Return isApproveWinner.
     *
     * @return isApproveWinner
     */
    public static boolean IsApproveWinner() {
        try {
            return isApproveWinner;
        } finally {
            isApproveWinner = false;
        }
    }

    /**
     * return isRejectWinner.
     *
     * @return isRejectWinner
     */
    public static boolean IsRejectWinner() {
        try {
            return isRejectWinner;
        } finally {
            isRejectWinner = false;
        }
    }

    /**
     * <p>
     * Rejects the specified PendingWinner's win. Uses the DAO FActory to obtain
     * the AdminDataDAO to delegate this action.
     * </p>
     *
     * @param winner
     *            winner to reject
     */
    public void rejectWinner(PendingWinner winner) {
        isRejectWinner = true;
    }

    /**
     * <p>
     * Sets the session context. This is an empty implementation.
     * </p>
     *
     * @param sessionContext
     *            session context
     */
    public void setSessionContext(SessionContext sessionContext) {

    }

    /**
     * <p>
     * Creates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbCreate() {
    }

    /**
     * <p>
     * Removes the bean. This is an empty implementation.
     * </p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() {
    }
}
