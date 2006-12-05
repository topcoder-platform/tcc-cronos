package com.orpheus.administration.persistence;

/**
 * <p>This the remote interface used to talk to the MessageBean.</p>
 * <p><strong>Thread Safety</strong></p>
 * <p>The container assumes all responsibility for thread-safety of these implementations.</p>
 * 
 */
public interface MessageRemote extends
        javax.ejb.EJBObject {
    /**
     * <p>Looks for items in this DataStore matching the specified criteria, and returns an array containing all (possibly zero) hits.</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>PersistenceException if any checked exception prevents successful completion of this method</li>
     * <li>IllegalArgumentException If criteria is null</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param criteria search criteria
     * @return retrieved messages
     */
    public com.orpheus.administration.persistence.Message[] findMessages(
            com.orpheus.administration.persistence.SearchCriteriaDTO criteria);

    /**
     * <p>Records a new message.</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>DuplicateEntryException If message.guid already exists in the persistence</li>
     * <li>PersistenceException if any checked exception prevents successful completion of this method</li>
     * <li>IllegalArgumentException If message is null</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param message message
     */
    public void createMessage(
            com.orpheus.administration.persistence.Message message);

    /**
     * <p>Updates the message. The message must already present in this DataStore.</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>EntryNotFoundException If message.guid is not found in the persistence</li>
     * <li>PersistenceException if any checked exception prevents successful completion of this method</li>
     * <li>IllegalArgumentException If message is null</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param message message
     */
    public void updateMessage(
            com.orpheus.administration.persistence.Message message);

    /**
     * <p>Deletes the message from this DataStore</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>PersistenceException if any checked exception prevents successful completion of this method</li>
     * <li>IllegalArgumentException If message is null</li>
     * <li>RemoteException If there is a network issue</li>
     * </ul>
     * <p></p>
     * 
     * 
     * @param message message
     */
    public void deleteMessage(
            com.orpheus.administration.persistence.Message message);
}
