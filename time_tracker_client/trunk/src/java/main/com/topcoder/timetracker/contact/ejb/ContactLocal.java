
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.*;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import javax.ejb.CreateException;
/**
 * The local component interface of the Contact EJB, which provides access to the persistent store for contacts managed by the application.
 * <p><strong>Thread Safety</strong></p>
 * The container assumes all responsibility for thread-safety of these implementations.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd98]
 */
public interface ContactLocal extends javax.ejb.EJBLocalObject {
/**
 * <p>Add the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma1c]
 * @param contact non null contact to be added
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void addContact(Contact contact, boolean doAudit);
/**
 * <p>Add the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmma0d]
 * @param contacts the non null contacts to be added
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void addContacts(Contact[] contacts, boolean doAudit);
/**
 * <p>Retrieve the given Contact</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9fe]
 * @return the contact with the given id, null if the contact if not found
 * @param id the id of the contact to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Contact retrieveContact(long id);
/**
 * <p>Retrieve the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9f3]
 * @return the non null contacts with given id, the containing non null contacts
 * @param ids the non null, possible empty >0 ids of the contacts
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Contact[] retrieveContatcts(long[] ids);
/**
 * <p>Remove the Contact with given id.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9e7]
 * @param id the >0 id of the Contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeContact(long id, boolean doAudit);
/**
 * <p>Remove the Contacts with given&nbsp;ids.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9da]
 * @param ids the non null, possible empty >0 ids of the Contacts
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void removeContacts(long[] ids, boolean doAudit);
/**
 * <p>Update the given Contact</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9cc]
 * @param contact the non null contact to be updated
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateContact(Contact contact, boolean doAudit);
/**
 * <p>Update the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9bd]
 * @param contacts non null, possible emtpy array containing non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any client is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void updateContacts(Contact[] contacts, boolean doAudit);
/**
 * <p>Retrieve all the Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9af]
 * @return Non null, possible empty array containing all non null contacts
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Contact[] getAllContacts();
/**
 * <p>Search the Contacts with the given Filter.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm9a6]
 * @return non null, possible empty array containing all contacts with given condition
 * @param filter non null filter
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if filter is null
 */
    public Contact[] searchContacts(Filter filter);
/**
 * <p>Associate the contact with the entity.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm999]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(Contact contact, long entity_id, boolean doAudit);
/**
 * <p>Deassociate the contact with the entity.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm98a]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <=0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(Contact contact, long entity_id, boolean doAudit);
}


