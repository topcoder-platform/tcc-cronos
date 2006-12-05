
package com.orpheus.administration.persistence;

/**
 * The EJB that handles the actual client requests. It simply delegates all operations to the MessageDAO it obtains from the DAOFactory.
 * <p><strong>Thread Safety</strong></p>
 * <p>This object is immutable and thread-safe</p>
 * 
 */
public class MessageBean implements javax.ejb.SessionBean {

/**
 * <p>Empty constructor.</p>
 * 
 */
    public  MessageBean() {        
        // your code here
    } 

/**
 * <p>Looks for items in this DataStore matching the specified criteria, and returns an array containing all (possibly zero) hits. Uses the DAO FActory to obtain the MessageDAO to delegate this action.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Get DAO from factory: DAOFactory.getMessageDAO()</li>
 * <li>Call and return messageDAO.findMessages(criteria):Message[]</li>
 * </ul>
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
    public com.orpheus.administration.persistence.Message[] findMessages(com.orpheus.administration.persistence.SearchCriteriaDTO criteria) {        
        // your code here
        return null;
    } 

/**
 * <p>Records a new message. Uses the DAO FActory to obtain the MessageDAO to delegate this action.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Get DAO from factory: DAOFactory.getMessageDAO()</li>
 * <li>Call&nbsp; messageDAO.createMessage(message)</li>
 * </ul>
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
    public void createMessage(com.orpheus.administration.persistence.Message message) {        
        // your code here
    } 

/**
 * <p>Updates the message. The message must already present in this DataStore. Uses the DAO FActory to obtain the MessageDAO to delegate this action.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Get DAO from factory: DAOFactory.getMessageDAO()</li>
 * <li>Call&nbsp; messageDAO.updateMessage(message)</li>
 * </ul>
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
    public void updateMessage(com.orpheus.administration.persistence.Message message) {        
        // your code here
    } 

/**
 * <p>Deletes the message from this DataStore. Uses the DAO FActory to obtain the MessageDAO to delegate this action.</p>
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Get DAO from factory: DAOFactory.getMessageDAO()</li>
 * <li>Call&nbsp; messageDAO.deleteMessage(message)</li>
 * </ul>
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
    public void deleteMessage(com.orpheus.administration.persistence.Message message) {        
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
