
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.*;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.timetracker.contact.ejb.ContactHomeLocal;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

/**
 * Implements the ContactManager interface to provide management of the Contact objects through the use of a local session EBJ. It will obtain the handle to the beans local interface and will simply delegate all calls to it. It implements all methods.
 * <p><strong>Thread Safety</strong></p>
 * This class is immutable and thread-safe. It is not expected that the data beans will be used by more
 * than one thread at a time.
 * <ID="UML note. note-id=[I3998e7b8m111455da48fmm493c] ">ContactManagerLocalDelegate will throw the exceptions 
 * which are thrown by the ContactManager, plus ConfigurationException
 * <BR></ID>
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd9d]
 */
public class ContactManagerLocalDelegate implements com.topcoder.timetracker.contact.ContactManager {

/**
 * Represents the local session ejb instance used for all calls. Created in the constructor, will not be null,
 * and will not change.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm97d]
 */
    private final ContactLocal localEJB = null;

/**
 * <p>Constructs the ContactManagerLocalDelegate with default namespace.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call this(ContactManagerLocalDelegate.class.getName())</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm977]
 * @throws ConfigurationException if it is thrown by the constructor
 */
    public  ContactManagerLocalDelegate() {        
 
        // your code here
    } 

/**
 * Instantiates new ContactLocal instance from the given namespace.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Obtain jndi ejb reference from ConfigManager.</li>
 * <li>InitialContext ic = new InitialContext()</li>
 * <li>ContactHomeLocal home = (ContactHomeLocal) ic.lookup(reference)</li>
 * <li>this.localEJB = home.create()</li>
 * </ul>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm96f]
 * @param namespace configuration namespace
 * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
 * @throws ConfigurationException if any exception is thrown by config manager or the required property isn't provided
 */
    public  ContactManagerLocalDelegate(String namespace) {        
 
        // your code here
    } 

/**
 * <p>Add the given Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call localEJB.addContacts(contacts, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm964]
 * @param contact non null contact to be added
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the localEJB
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
 * <p>Call localEJB.addContacts(contacts, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm955]
 * @param contacts the non null contacts to be added
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the localEJB
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
 * <li>Get the contact by localEJB.retrieveContact(id)</li>
 * <li>Return the contact</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm946]
 * @return the contact with the given id, null if the contact if not found
 * @param id the id of the contact to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Contact retrieveContact(long id) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Retrieve the given Contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the contacts by localEJB.retrieveContacts(ids)</li>
 * <li>Return the contacts</li>
 * </ol>
 * <p></p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm93b]
 * @return the non null contacts with given id, the containing non null contacts
 * @param ids the non null, possible empty >0 ids of the contacts
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Contact[] retrieveContatcts(long[] ids) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Remove the Contact with given id.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Remove the contact by localEJB.removeContact(id, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm92f]
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
 * <p>Call localEJB.removeContacts(ids, doAudit)</p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm922]
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
 * <p>Simply call localEJB.updateContact(contact, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm914]
 * @param contact the non null contact to be updated
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the localEJB
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
 * <p>Call localEJB.updateContacts(contacts, doAudit)</p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm905]
 * @param contacts non null, possible emtpy array containing non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any client is null
 * @throws PersistenceException if it is thrown by the localEJB
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
 * <li>Get contacts localEJB.retrieveAllContacts()</li>
 * <li>return contacts</li>
 * </ol>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm8f7]
 * @return Non null, possible empty array containing all non null contacts
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Contact[] getAllContacts() {        
 
        // your code here
        return null;
    } 

/**
 * <p>Search the Contacts with the given Filter.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return localEJB.searchContacts(filter)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm8ee]
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
 * <p>Add this association by localEJB.associate</p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm8e1]
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
 * <p>Remove this association by localEJB.deassociate</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm8d2]
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
