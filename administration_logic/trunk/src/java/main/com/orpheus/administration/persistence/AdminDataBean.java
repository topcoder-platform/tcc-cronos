package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleData;

/**
 * The EJB that handles the actual client requests. It simply delegates all operations to the AdminDataDAO it obtains from the DAOFactory.
 * <p><strong>Thread Safety</strong></p>
 * <p>This object is immutable and thread-safe</p>
 * 
 */
public class AdminDataBean implements javax.ejb.SessionBean {

    /**
     * <p>Empty constructor.</p>
     * 
     */
    public AdminDataBean() {
        // your code here
    }

    /**
     * <p>Retrieves an administrative data summary from the persistent store. Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call and return adminDataDAO.getAdminSummary()</li>
     * </ul>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>PersistenceException If any persistence error occurs.</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @return the admin summary
     */
    public com.orpheus.administration.persistence.AdminSummary getAdminSummary() {

        return new AdminSummary() {
        };
    }

    /**
     * <p>Sets the approval flag for the specified domain. Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call adminDataDAO.setDomainApproval(domainId,approved)</li>
     * </ul>
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
    public void setDomainApproval(long domainId, boolean approved) {
        // your code here
    }

    /**
     * <p>Sets the approval flag for the specified image. Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call adminDataDAO.setImageApproval(imageId,approved)</li>
     * </ul>
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
    public void setImageApproval(long imageId, boolean approved) {
        // your code here
    }

    /**
     * <p>Records the specified PuzzleData objects in the application's database, returning an array of the unique IDs assigned to them (in the same order as the puzzles appear in the argument). Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call and return adminDataDAO.storePuzzles(puzzles):long[]</li>
     * </ul>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If puzzles is null or empty, or contains null elements in the array</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param puzzles puzzles to store
     * @return ids of stored puzzles
     */
    public long[] storePuzzles(PuzzleData[] puzzles) {
        // your code here
        return null;
    }

    /**
     * <p>Retrieves the currently-pending winners' information. Will return an empty array of there are no pending winners. Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call and return adminDataDAO.getPendingWinners():PendingWinner[]</li>
     * </ul>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>PersistenceException If any persistence error occurs.</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @return the pending winners in all games
     */
    public com.orpheus.administration.persistence.PendingWinner[] getPendingWinners() {
        // your code here
        return null;
    }

    /**
     * <p>Approves the specified PendingWinner's win. Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call adminDataDAO.approveWinner(winner,date)</li>
     * </ul>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>EntryNotFoundException If winner's playerID/gameId combo does not exist in the data store</li>
     * <li>InvalidEntryException If the game already has a winner, or this person has already been declared as a winner</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If winner or date is null</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param winner winner to approve
     * @param date date of approval
     */
    public void approveWinner(
            com.orpheus.administration.persistence.PendingWinner winner,
            java.util.Date date) {
        // your code here
    }

    /**
     * <p>Rejects the specified PendingWinner's win. Uses the DAO FActory to obtain the AdminDataDAO to delegate this action.</p>
     * <p><strong>Implementation Notes</strong></p>
     * <ul type="disc">
     * <li>Get DAO from factory: DAOFactory.getAdminDataDAO()</li>
     * <li>Call adminDataDAO.rejectWinner(winner)</li>
     * </ul>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>EntryNotFoundException If winner's playerID/gameId combo does not exist in the data store</li>
     * <li>InvalidEntryException If this person has already been rejected</li>
     * <li>PersistenceException If any persistence error occurs.</li>
     * <li>IllegalArgumentException If winner is null</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param winner winner to reject
     */
    public void rejectWinner(
            com.orpheus.administration.persistence.PendingWinner winner) {
        // your code here
    }

    /**
     * <p>Creates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbCreate() {
        // your code here
    }

    /**
     * <p>Removes the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbRemove() {
        // your code here
    }

    /**
     * <p>Activates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbActivate() {
        // your code here
    }

    /**
     * <p>Passivates the bean. This is an empty implementation.</p>
     * 
     */
    public void ejbPassivate() {
        // your code here
    }

    /**
     * <p>Sets the session context. This is an empty implementation.</p>
     * 
     * 
     * @param ctx session context
     */
    public void setSessionContext(javax.ejb.SessionContext ctx) {
        // your code here
    }
}
