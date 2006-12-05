
package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;

/**
 * Interface specifying the methods for administration persistence. Supports the RSSItem-related methods, as RSSItem translates to the Message, the DTO used here.
 * <p><strong>Thread Safety</strong></p>
 * Implementations are expected to be thread-safe.
 * 
 */
public interface AdminDataDAO {
/**
 * <p>Retrieves an administrative data summary from the persistent store</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>PersistenceException If any persistence error occurs.</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @return the admin summary
 */
    public com.orpheus.administration.persistence.AdminSummary getAdminSummary();
/**
 * <p>Sets the approval flag for the specified domain</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>EntryNotFoundException If domain with the domainId does not exist in the data store</li>
 * <li>PersistenceException If any persistence error occurs.</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param domainId domain id
 * @param approved approved flag
 */
    public void setDomainApproval(long domainId, boolean approved);
/**
 * <p>Sets the approval flag for the specified image</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>EntryNotFoundException If image with the imageId does not exist in the data store</li>
 * <li>PersistenceException If any persistence error occurs.</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param imageId image id
 * @param approved approved flag
 */
    public void setImageApproval(long imageId, boolean approved);
/**
 * <p>Records the specified PuzzleData objects in the application's database, returning an array of the unique IDs assigned to them (in the same order as the puzzles appear in the argument)</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>PersistenceException If any persistence error occurs.</li>
 * <li>IllegalArgumentException If puzzles is null&nbsp;or empty, or contains null elements in the arra</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param puzzles puzzles to store
 * @return ids of stored puzzles
 */
    public long[] storePuzzles(PuzzleData[] puzzles);
/**
 * <p>Retrieves the currently-pending winners' information. Will return an empty array of there are no pending winners.</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>PersistenceException If any persistence error occurs.</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @return the pending winners in all games
 */
    public com.orpheus.administration.persistence.PendingWinner[] getPendingWinners();
/**
 * <p>Approves the specified PendingWinner's win</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>EntryNotFoundException If winner's playerID/gameId combo does not exist in the data store</li>
 * <li>PersistenceException If any persistence error occurs.</li>
 * <li>IllegalArgumentException If winner or date is null</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param winner winner to approve
 * @param date date of approval
 */
    public void approveWinner(com.orpheus.administration.persistence.PendingWinner winner, java.util.Date date);
/**
 * <p>Rejects the specified PendingWinner's win</p>
 * <p><strong>Exception Handling</strong></p>
 * <ul type="disc">
 * <li>EntryNotFoundException If winner's playerID/gameId combo does not exist in the data store</li>
 * <li>PersistenceException If any persistence error occurs.</li>
 * <li>IllegalArgumentException If winner is null</li>
 * </ul>
 * <p></p>
 * 
 * 
 * @param winner winner to reject
 */
    public void rejectWinner(com.orpheus.administration.persistence.PendingWinner winner);

}


