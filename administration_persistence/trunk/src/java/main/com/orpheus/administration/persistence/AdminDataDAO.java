/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;

import java.util.Date;

/**
 * <p>Interface specifying the methods for administration persistence. Supports the RSSItem-related methods, as
 * RSSItem translates to the Message, the DTO used here.
 *
 * <p><strong>Thread Safety</strong>: Implementations are expected to be thread-safe.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */

public interface AdminDataDAO {
    /**
     * <p>Retrieves an administrative data summary from the persistent store.</p>
     *
     * @return the admin summary
     * @throws PersistenceException if any persistence error occurs
     */
    public AdminSummary getAdminSummary() throws PersistenceException;

    /**
     * <p>Sets the approval flag for the specified domain.</p>
     *
     * @param domainId domain id
     * @param approved approved flag
     * @throws EntryNotFoundException if the specified domain does not exist in the data store
     * @throws PersistenceException if any persistence error occurs
     */
    public void setDomainApproval(long domainId, boolean approved) throws PersistenceException;

    /**
     * <p>Sets the approval flag for the specified image.</p>
     *
     * @param imageId the image ID
     * @param approved the approval flag
     * @throws EntryNotFoundException if the specified image does not exist in the data store
     * @throws PersistenceException if any persistence error occurs
     */
    public void setImageApproval(long imageId, boolean approved) throws PersistenceException;

    /**
     * <p>Records the specified PuzzleData objects in the application's database, returning an array of the unique
     * IDs assigned to them (in the same order as the puzzles appear in the argument).</p>
     *
     * @param puzzles puzzles to store
     * @return ids of stored puzzles
     * @throws PersistenceException if any persistence error occurs
     * @throws IllegalArgumentException if <code>puzzles</code> is <code>null</code> or empty or contains
     *   <code>null</code> elements
     */
    public long[] storePuzzles(PuzzleData[] puzzles) throws PersistenceException;

    /**
     * <p>Retrieves the currently-pending winners' information. Will return an empty array of there are no pending
     * winners.</p>
     *
     * @return the pending winners in all games
     * @throws PersistenceException if any persistence error occurs
     */
    public PendingWinner[] getPendingWinners() throws PersistenceException;

    /**
     * <p>Approves the specified pending winner's win.</p>
     *
     * @param winner winner to approve
     * @param date date of approval
     * @throws EntryNotFoundException if the specified combination of player ID and game ID does not exist
     * @throws InvalidEntryException if the speciied player is not currently the pending winner of the specified game
     * @throws IllegalArgumentException if either argument is <code>null</code>
     * @throws PersistenceException if any persistence error occurs
     */
    public void approveWinner(PendingWinner winner, Date date) throws PersistenceException;

    /**
     * <p>Rejects the specified pending winner's win.</p>
     *
     * @param winner winner to reject
     * @throws EntryNotFoundException if the specified combination of player ID and game ID does not exist
     * @throws InvalidEntryException if the speciied player is not currently the pending winner of the specified game
     * @throws IllegalArgumentException if <code>winner</code> is <code>null</code>
     * @throws PersistenceException if any persistence error occurs
     */
    public void rejectWinner(PendingWinner winner) throws PersistenceException;
}


