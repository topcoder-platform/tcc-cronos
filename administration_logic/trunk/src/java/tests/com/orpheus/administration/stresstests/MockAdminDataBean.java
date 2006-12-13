/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.stresstests;

import java.rmi.RemoteException;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.orpheus.administration.persistence.AdminSummary;
import com.orpheus.administration.persistence.PendingWinner;
import com.topcoder.util.puzzle.PuzzleData;

/**
 * <p>
 * Mock implementation of <code>AdminDataBean</code> only for testing purpose.
 * </p>
 * @author FireIce
 * @version 1.0
 */
public class MockAdminDataBean implements SessionBean {
    /**
     * Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * setSessionContext() method by the container.
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    public MockAdminDataBean() {
    }

    /**
     * <p>
     * Retrieves an administrative data summary from the persistent store. Uses the DAO FActory to obtain the
     * AdminDataDAO to delegate this action.
     * </p>
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
     * Sets the approval flag for the specified domain. Uses the DAO FActory to obtain the AdminDataDAO to delegate this
     * action.
     * </p>
     * @param domainId
     *            domain id
     * @param approved
     *            approved flag
     */
    public void setDomainApproval(long domainId, boolean approved) {
        // your code here
    }

    /**
     * <p>
     * Sets the approval flag for the specified image. Uses the DAO FActory to obtain the AdminDataDAO to delegate this
     * action.
     * </p>
     * @param imageId
     *            image id
     * @param approved
     *            approved flag
     */
    public void setImageApproval(long imageId, boolean approved) {
        // your code here
    }

    /**
     * <p>
     * Records the specified PuzzleData objects in the application's database, returning an array of the unique IDs
     * assigned to them (in the same order as the puzzles appear in the argument). Uses the DAO FActory to obtain the
     * AdminDataDAO to delegate this action.
     * </p>
     * @param puzzles
     *            puzzles to store
     * @return ids of stored puzzles
     */
    public long[] storePuzzles(PuzzleData[] puzzles) {
        // your code here
        return null;
    }

    /**
     * <p>
     * Retrieves the currently-pending winners' information. Will return an empty array of there are no pending winners.
     * Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.
     * </p>
     * @return the pending winners in all games
     */
    public PendingWinner[] getPendingWinners() {
        PendingWinnerImpl pendingWinnerImpl = new PendingWinnerImpl();
        pendingWinnerImpl.setPlayerId(1);
        pendingWinnerImpl.setGameId(1);
        return new PendingWinner[] {pendingWinnerImpl};
    }

    /**
     * <p>
     * Approves the specified PendingWinner's win. Uses the DAO FActory to obtain the AdminDataDAO to delegate this
     * action.
     * </p>
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call adminDataDAO.approveWinner(winner,date)</li>
     * </ul>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>EntryNotFoundException If winner's playerID/gameId combo does not exist in the data store</li>
     * <li>InvalidEntryException If the game already has a winner, or this person has already been declared as a winner</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If winner or date is null</li>
     * </ul>
     * <p>
     * </p>
     * @param winner
     *            winner to approve
     * @param date
     *            date of approval
     */
    public void approveWinner(com.orpheus.administration.persistence.PendingWinner winner, java.util.Date date) {
        // your code here
    }

    /**
     * <p>
     * Rejects the specified PendingWinner's win. Uses the DAO FActory to obtain the AdminDataDAO to delegate this
     * action.
     * </p>
     * <p>
     * <strong>Implementation Notes</strong>
     * </p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call adminDataDAO.rejectWinner(winner)</li>
     * </ul>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>EntryNotFoundException If winner's playerID/gameId combo does not exist in the data store</li>
     * <li>InvalidEntryException If this person has already been rejected</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If winner is null</li>
     * </ul>
     * <p>
     * </p>
     * @param winner
     *            winner to reject
     */
    public void rejectWinner(PendingWinner winner) {
        // your code here
    }

    /**
     * <p>
     * Sets the session context. This is an empty implementation.
     * </p>
     * @param sessionContext
     *            session context
     */
    public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
        this.sessionContext = sessionContext;
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
    public void ejbRemove() throws EJBException, RemoteException {
    }

    /**
     * <p>
     * Activates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbActivate() throws EJBException, RemoteException {
    }

    /**
     * <p>
     * Passivates the bean. This is an empty implementation.
     * </p>
     */
    public void ejbPassivate() throws EJBException, RemoteException {
    }

    private class PendingWinnerImpl implements PendingWinner {

        private long playerId;

        private long gameId;

        private int payout;

        public long getPlayerId() {
            return playerId;
        }

        public long getGameId() {
            return gameId;
        }

        public int getPayout() {
            return payout;
        }

        public void setGameId(long gameId) {
            this.gameId = gameId;
        }

        public void setPayout(int payout) {
            this.payout = payout;
        }

        public void setPlayerId(long playerId) {
            this.playerId = playerId;
        }

    }
}
