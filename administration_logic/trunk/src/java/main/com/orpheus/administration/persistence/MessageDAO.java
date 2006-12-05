package com.orpheus.administration.persistence;

/**
 * Interface specifying the methods for message persistence. Works with the Message and SearchCriteriaDTO as the data transfer objects. Supports all methods in the client.
 * <p><strong>Thread Safety</strong></p>
 * Implementations are expected to be thread-safe.
 * 
 */
public interface MessageDAO {
    /**
     * <p>Looks for items in this DataStore matching the specified criteria, and returns an array containing all (possibly zero) hits.</p>
     * <p><strong>Exception Handling</strong></p>
     * <ul type="disc">
     * <li>PersistenceException if any checked exception prevents successful completion of this method</li>
     * <li>IllegalArgumentException If criteria is null</li>
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
     * </ul>
     * <p></p>
     * 
     * 
     * @param message message
     */
    public void deleteMessage(
            com.orpheus.administration.persistence.Message message);
}
