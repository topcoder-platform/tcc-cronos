package com.orpheus.administration.persistence;

import java.rmi.RemoteException;

import com.topcoder.util.puzzle.PuzzleData;

/**
 * <p>
 * This the remote interface used to talk to the AdminDataBean. Supports all
 * client operations.
 * </p>
 * <p>
 * <strong>Thread Safety</strong>
 * </p>
 * <p>
 * The container assumes all responsibility for thread-safety of these
 * implementations.
 * </p>
 * 
 */
public interface AdminData extends javax.ejb.EJBObject {
    /**
     * <p>
     * Retrieves an administrative data summary from the persistent store
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @return the admin summary
     */
    public com.orpheus.administration.persistence.AdminSummary getAdminSummary()
        throws PersistenceException, RemoteException;

    /**
     * <p>
     * Sets the approval flag for the specified domain
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>EntryNotFoundException If domain with the domainId does not exist in
     * the data store</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @param domainId
     *            domain id
     * @param approved
     *            approved flag
     */
    public void setDomainApproval(long domainId, boolean approved)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * <p>
     * Sets the approval flag for the specified image
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>EntryNotFoundException If image with the imageId does not exist in
     * the data store</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @param imageId
     *            image id
     * @param approved
     *            approved flag
     */
    public void setImageApproval(long imageId, boolean approved)
        throws EntryNotFoundException, PersistenceException, RemoteException;

    /**
     * <p>
     * Records the specified PuzzleData objects in the application's database,
     * returning an array of the unique IDs assigned to them (in the same order
     * as the puzzles appear in the argument)
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If puzzles is null &nbsp;or empty, or
     * contains null elements in the array</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @param puzzles
     *            puzzles to store
     * @return ids of stored puzzles
     */
    public long[] storePuzzles(PuzzleData[] puzzles)
        throws PersistenceException, RemoteException;

    /**
     * <p>
     * Retrieves the currently-pending winners' information. Will return an
     * empty array of there are no pending winners.
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @return the pending winners in all games
     */
    public com.orpheus.administration.persistence.PendingWinner[] getPendingWinners()
        throws PersistenceException, RemoteException;

    /**
     * <p>
     * Approves the specified PendingWinner's win
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>EntryNotFoundException If winner's playerID/gameId combo does not
     * exist in the data store</li>
     * <li>InvalidEntryException If the game already has a winner, or this
     * person has already been declared as a winner</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If winner or date is null</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @param winner
     *            winner to approve
     * @param date
     *            date of approval
     */
    public void approveWinner(
            com.orpheus.administration.persistence.PendingWinner winner,
            java.util.Date date) throws EntryNotFoundException,
        InvalidEntryException, PersistenceException, RemoteException;

    /**
     * <p>
     * Rejects the specified PendingWinner's win
     * </p>
     * <p>
     * <strong>Exception Handling</strong>
     * </p>
     * <ul type="disc">
     * <li>EntryNotFoundException If winner's playerID/gameId combo does not
     * exist in the data store</li>
     * <li>InvalidEntryException If this person has already been rejected</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If winner is null</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p>
     * </p>
     * 
     * 
     * @param winner
     *            winner to reject
     */
    public void rejectWinner(
            com.orpheus.administration.persistence.PendingWinner winner)
        throws EntryNotFoundException, InvalidEntryException,
        PersistenceException, RemoteException;;

}
