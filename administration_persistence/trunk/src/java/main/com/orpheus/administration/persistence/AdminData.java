/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * <p>This the remote interface used to talk to the {@link AdminDataBean AdminDataBean}. It supports all client
 * operations.</p>
 *
 * <p><strong>Thread Safety</strong>: The container assumes all responsibility for thread-safety of these
 * implementations.</p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface AdminData extends EJBObject {
    /**
     * <p>Retrieves an administrative data summary from the persistent store.
     *
     * @return an administrative data summary from the persistent store
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public AdminSummary getAdminSummary() throws PersistenceException, RemoteException;

    /**
     * <p>Sets the approval flag for the specified domain.
     *
     * @param domainId the domain ID
     * @param approved the approved flag
     * @throws EntryNotFoundException if the domain ID does not exist
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public void setDomainApproval(long domainId, boolean approved)
        throws PersistenceException, RemoteException;

    /**
     * <p>Sets the approval flag for the specified image.</p>
     *
     * @param imageId the image ID
     * @param approved the approved flag
     * @throws EntryNotFoundException if the image ID does not exist
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public void setImageApproval(long imageId, boolean approved)
        throws PersistenceException, RemoteException;

    /**
     * <p>Records the specified PuzzleData objects in the application's database, returning an array of the unique
     * IDs assigned to them (in the same order as the puzzles appear in the argument).
     *
     * @param puzzles the puzzles to store
     * @return the IDs of the stored puzzles
     * @throws IllegalArgumentException if <code>puzzles</code> is <code>null</code> or empty or contains
     *   <code>null</code> elements
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public long[] storePuzzles(PuzzleData[] puzzles) throws PersistenceException, RemoteException;

    /**
     * <p>Retrieves the currently-pending winners' information. Will return an empty array of there are no pending
     * winners.
     *
     * @return the pending winners in all games
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public PendingWinner[] getPendingWinners() throws PersistenceException, RemoteException;

    /**
     * <p>Approves the specified pending winner's win.</p>
     *
     * @param winner winner to approve
     * @param date date of approval
     * @throws EntryNotFoundException if the combination of player ID and game ID does not exist in the data store
     * @throws IllegalArgumentException if either argument is <code>null</code>
     * @throws InvalidEntryException if the winner is not currently the pending winner of the game
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public void approveWinner(PendingWinner winner, Date date) throws PersistenceException, RemoteException;

    /**
     * <p>Rejects the specified pending winner's win.</p>
     *
     * @param winner winner to reject
     * @throws EntryNotFoundException if the combination of player ID and game ID does not exist in the data store
     * @throws IllegalArgumentException if <code>winner</code> is <code>null</code>
     * @throws InvalidEntryException if the winner is not currently the pending winner of the game
     * @throws PersistenceException if any persistence error occurs
     * @throws RemoteException if there is a network issue
     */
    public void rejectWinner(PendingWinner winner) throws PersistenceException, RemoteException;

    /**
     * Creates new Ball color with specified name and associated with the specified image providing the Ball color icon.
     *
     * @param colorName a <code>String</code> providing the name for the new Ball color.
     * @param imageId a <code>long</code> providing the ID of an image associated with new Ball color.
     * @return a <code>long</code> providing the unique ID for the create Ball color.
     * @throws PersistenceException if an error occurs while accessing the persistent storage.
     * @throws RemoteException if there is a network issue
     * @throws IllegalArgumentException if specified <code>colorName</code> is <code>null</code> or empty.
     */
    public long createBallColor(String colorName, long imageId) throws PersistenceException, RemoteException;
}


