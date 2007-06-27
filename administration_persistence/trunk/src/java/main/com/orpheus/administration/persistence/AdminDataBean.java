/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.util.Date;

/**
 * <p>The EJB that handles the actual client requests. It simply delegates all operations to the {@link AdminDataDAO
 * AdminDataDAO} it obtains from the {@link DAOFactory DAOFactory}.
 *
 * <p><strong>Thread Safety</strong>: This object is immutable and thread-safe.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public class AdminDataBean implements SessionBean {
    /**
     * <p>Represents the session context of this bean. It is used to indicate to the container if the bean wants to
     * rollback a transaction. This would usually occur if an application exception occurs. Set in the
     * {@link #setSessionContext setSessionContext()} method by the container.</p>
     */
    private SessionContext sessionContext;

    /**
     * <p>Empty default constructor.</p>
     */
    public AdminDataBean() {
    }

    /**
     * <p>Retrieves an administrative data summary from the persistent store. Uses the DAO factory to obtain the
     * {@link AdminDataDAO AdminDataDAO} to which to delegate this action.</p>
     *
     * @return the admin summary
     * @throws PersistenceException if any persistence error occurs
     */
    public AdminSummary getAdminSummary() throws PersistenceException {
        try {
            return getAdminDataDAO().getAdminSummary();
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Sets the approval flag for the specified domain. Uses the DAO factory to obtain the {@link AdminDataDAO
     * AdminDataDAO} to which to delegate this action.</p>
     *
     * @param domainId domain id
     * @param approved approved flag
     * @throws EntryNotFoundException if the specified domain does not exist in the data store
     * @throws PersistenceException if any persistence error occurs
     */
    public void setDomainApproval(long domainId, boolean approved) throws PersistenceException {
        try {
            getAdminDataDAO().setDomainApproval(domainId, approved);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Sets the approval flag for the specified image. Uses the DAO fctory to obtain the {@link AdminDataDAO
     * AdminDataDAO} to which to delegate this action.</p>
     *
     * @param imageId the image ID
     * @param approved the approval flag
     * @throws EntryNotFoundException if the specified image does not exist in the data store
     * @throws PersistenceException if any persistence error occurs
     */
    public void setImageApproval(long imageId, boolean approved) throws PersistenceException {
        try {
            getAdminDataDAO().setImageApproval(imageId, approved);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Records the specified PuzzleData objects in the application's database, returning an array of the unique
     * IDs assigned to them (in the same order as the puzzles appear in the argument). Uses the DAO factory to
     * obtain the {@link AdminDataDAO AdminDataDAO} to which to delegate this action.</p>
     *
     * @param puzzles puzzles to store
     * @return ids of stored puzzles
     * @throws PersistenceException if any persistence error occurs
     * @throws IllegalArgumentException if <code>puzzles</code> is <code>null</code> or empty or contains
     *   <code>null</code> elements
     */
    public long[] storePuzzles(PuzzleData[] puzzles) throws PersistenceException {
        try {
            return getAdminDataDAO().storePuzzles(puzzles);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Retrieves the currently-pending winners' information. Will return an empty array of there are no pending
     * winners. Uses the DAO factory to obtain the {@link AdminDataDAO AdminDataDAO} to which to delegate this
     * action.</p>
     *
     * @return the pending winners in all games
     * @throws PersistenceException if any persistence error occurs
     */
    public PendingWinner[] getPendingWinners() throws PersistenceException {
        try {
            return getAdminDataDAO().getPendingWinners();
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Approves the specified pending winner's win. Uses the DAO factory to obtain the {@link AdminDataDAO
     * AdminDataDAO} to which to delegate this action.</p>
     *
     * @param winner winner to approve
     * @param date date of approval
     * @throws EntryNotFoundException if the specified combination of player ID and game ID does not exist
     * @throws InvalidEntryException if the speciied player is not currently the pending winner of the specified game
     * @throws IllegalArgumentException if either argument is <code>null</code>
     * @throws PersistenceException if any persistence error occurs
     */
    public void approveWinner(PendingWinner winner, Date date) throws PersistenceException {
        try {
            getAdminDataDAO().approveWinner(winner, date);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Rejects the specified pending winner's win. Uses the DAO factory to obtain the {@link AdminDataDAO
     * AdminDataDAO} to which to delegate this action.</p>
     *
     * @param winner winner to reject
     * @throws EntryNotFoundException if the specified combination of player ID and game ID does not exist
     * @throws InvalidEntryException if the speciied player is not currently the pending winner of the specified game
     * @throws IllegalArgumentException if <code>winner</code> is <code>null</code>
     * @throws PersistenceException if any persistence error occurs
     */
    public void rejectWinner(PendingWinner winner) throws PersistenceException {
        try {
            getAdminDataDAO().rejectWinner(winner);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Creates new Ball color with specified name and associated with the specified image providing the Ball color
     * icon.  Uses the DAO factory to obtain the {@link AdminDataDAO} to which to delegate this action.</p>
     *
     * @param colorName a <code>String</code> providing the name for the new Ball color.
     * @param imageId a <code>long</code> providing the ID of an image associated with new Ball color.
     * @return a <code>long</code> providing the unique ID for the create Ball color.
     * @throws PersistenceException if an error occurs while accessing the persistent storage.
     * @throws IllegalArgumentException if specified <code>colorName</code> is <code>null</code> or empty.
     */
    public long createBallColor(String colorName, long imageId) throws PersistenceException {
        try {
            return getAdminDataDAO().createBallColor(colorName, imageId);
        } catch (PersistenceException ex) {
            sessionContext.setRollbackOnly();
            throw ex;
        }
    }

    /**
     * <p>Creates the bean. This is an empty implementation.</p>
     */
    public void ejbCreate() {
    }

    /**
     * <p>Removes the bean. This is an empty implementation.</p>
     */
    public void ejbRemove() {
    }

    /**
     * <p>Activates the bean. This is an empty implementation.</p>
     */
    public void ejbActivate() {
    }

    /**
     * <p>Passivates the bean. This is an empty implementation.</p>
     */
    public void ejbPassivate() {
    }

    /**
     * <p>Sets the session context.</p>
     *
     * @param ctx the session context
     */
    public void setSessionContext(SessionContext ctx) {
        this.sessionContext = ctx;
    }

    /**
     * Helper method to call <code>getAdminDataDAO</code> and rethrow <code>InstantiationException</code> as
     * <code>PersistenceException</code>.
     *
     * @return the admin data DAO instance
     * @throws PersistenceException if the admin data DAO cannot be instantiated
     */
    private static AdminDataDAO getAdminDataDAO() throws PersistenceException {
        try {
            return DAOFactory.getAdminDataDAO();
        } catch (InstantiationException ex) {
            throw new PersistenceException("failed to instantiate admin data DAO", ex);
        }
    }
}
