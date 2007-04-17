
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import javax.ejb.CreateException;
import javax.ejb.SessionContext;

/**
 * The session EJB that handles the actual manager requests. It simply delegates all operations to the ContactDAO it obtains from the ObjectFactory.
 * <p><strong>Thread Safety</strong></p>
 * This object is mutable and thread-safe, as the container handles this.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd9b]
 */
public class ContactBean implements javax.ejb.SessionBean {

/**
 * Represents the session context of this bean. It is used to indicate to the container if the bean wants
 * to rollback a transaction. This would usually occur if an application exception occurs. Set in the
 * setSessionContext() method by the container.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmafe]
 */
    private SessionContext sessionContext = null;

/**
 * Represents the data access object for performing the persistence operations for the contact entries.
 * It is initialized in the ejbCreate method, and not changed afterwards. There will be one instantiation per
 * one session bean lifetime.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmaf8]
 */
    private ContactDAO contactDAO = null;

/**
 * Empty constructor.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmaf1]
 */
    public  ContactBean() {        
        // your code here
    } 

/**
 * Creates the bean. Initializes the ContactDAO instance to be used by this component.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Performs a lookup on the envirnmental variable SpecificationNamespace</li>
 * <li>Creates a new ConfigManagerSpecificationFactory with this namespace</li>
 * <li>Creates new ObjectFactory with this specification factory</li>
 * <li>Performs a lookup on the envirnmental variable ContactDAOKey</li>
 * <li>Gets instance of ContactDAO from object factory with the retrieved dao key</li>
 * </ul>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmaeb]
 * @throws CreateException If any error occurs while instantiating
 */
    public void ejbCreate() {        
        // your code here
    } 

/**
 * Removes the bean. This is an empty implementation.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmae4]
 */
    public void ejbRemove() {        
        // your code here
    } 

/**
 * Activates the bean. This is an empty implementation.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmade]
 */
    public void ejbActivate() {        
        // your code here
    } 

/**
 * Passivates the bean. This is an empty implementation.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmad8]
 */
    public void ejbPassivate() {        
        // your code here
    } 

/**
 * Sets the session context.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmad1]
 * @param ctx session context
 */
    public void setSessionContext(SessionContext ctx) {        
        // your code here
    } 

/**
 * <p>Add the given Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call contactDAO.addContacts(contacts, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmac8]
 * @param contact non null contact to be added
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the contactDAO
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void addContact(Contact contact, boolean doAudit) {        
 
        // your code here
    } 

/**
 * <p>Add the given Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call contactDAO.addContacts(contacts, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmab9]
 * @param contacts the non null contacts to be added
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the contactDAO
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void addContacts(Contact[] contacts, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Retrieve the given Contact</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the contact by contactDAO.retrieveContact(id)</li>
 * <li>Return the contact</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmaaa]
 * @return the contact with the given id, null if the contact if not found
 * @param id the id of the contact to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the contactDAO
 */
    public Contact retrieveContact(long id) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Retrieve the given Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the contacts by contactDAO.retrieveContacts(ids)</li>
 * <li>Return the contacts</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * <p></p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma9f]
 * @return the non null contacts with given id, the containing non null contacts
 * @param ids the non null, possible empty >0 ids of the contacts
 * @throws PersistenceException if it is thrown by the contactDAO
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Contact[] retrieveContatcts(long[] ids) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Remove the Contact with given id.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Remove the contact by contactDAO.removeContact(id, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma93]
 * @param id the >0 id of the Contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeContact(long id, boolean doAudit) {        
 
        // your code here
    } 

/**
 * <p>Remove the Contacts with given&nbsp;ids.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call contactDAO.removeContacts(ids, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma86]
 * @param ids the non null, possible empty >0 ids of the Contacts
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void removeContacts(long[] ids, boolean doAudit) {        
 
        // your code here
    } 

/**
 * <p>Update the given Contact</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call contactDAO.updateContact(contact, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma78]
 * @param contact the non null contact to be updated
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the contactDAO
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateContact(Contact contact, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Update the given Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call contactDAO.updateContacts(contacts, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma69]
 * @param contacts non null, possible emtpy array containing non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any client is null
 * @throws PersistenceException if it is thrown by the contactDAO
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void updateContacts(Contact[] contacts, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Retrieve all the Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get contacts contactDAO.retrieveAllContacts()</li>
 * <li>return contacts</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma5b]
 * @return Non null, possible empty array containing all non null contacts
 * @throws PersistenceException if it is thrown by the contactDAO
 */
    public Contact[] getAllContacts() {        
 
        // your code here
        return null;
    } 

/**
 * <p>Search the Contacts with the given Filter.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return contactDAO.searchContacts(filter)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma52]
 * @return non null, possible empty array containing all contacts with given condition
 * @param filter non null filter
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if filter is null
 */
    public Contact[] searchContacts(Filter filter) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Associate the contact with the entity.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Add this association by contactDAO.associate</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma45]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(Contact contact, long entity_id, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Deassociate the contact with the entity.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Remove this association by contactDAO.deassociate</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma36]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <=0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(Contact contact, long entity_id, boolean doAudit) {        
        
        // your code here
    } 
 }
