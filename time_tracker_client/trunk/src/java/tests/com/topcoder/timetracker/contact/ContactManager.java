
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;
import com.topcoder.timetracker.contact.PersistenceException;
/**
 * This interface defines the contract for the complete management of a contact.
 * It provides single and batch CRUD operations as well as the means to associate
 * or disassociate a contact from an entity. It also provides the ability to
 * audit each operation, if so desired. It has one implementation in this design:
 * ContactManagerLocalDelegate.
 * <p><strong>Thread Safety</strong></p>
 * Implementations need not necessarily be thread safe. Each implementation
 * should specify whether it is thread-safe or not. The application should pick
 * the correct implementation for it's requirements.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm30ee]
 */
public interface ContactManager {
/**
 * <p>Add the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm3025]
 * @param contact non null contact to be added
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void addContact(Contact contact, boolean doAudit) throws PersistenceException, IDGenerationException, InvalidPropertyException, AuditException;
/**
 * <p>Add the given Contacts</p>
 * 
 * 
 * @param contacts the non null contacts to be added
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void addContacts(Contact[] contacts, boolean doAudit) throws InvalidPropertyException, PersistenceException, IDGenerationException, AuditException, BatchOperationException;
/**
 * <p>Retrieve the given Contact</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm3006]
 * @return the contact with the given id, null if the contact if not found
 * @param id the id of the contact to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Contact retrieveContact(long id) throws PersistenceException;
/**
 * <p>Retrieve the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2ffb]
 * @return the non null contacts with given id, the containing non null contacts
 * @param ids the non null, possible empty >0 ids of the contacts
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Contact[] retrieveContatcts(long[] ids) throws PersistenceException;
/**
 * <p>Remove the Contact with given id.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fef]
 * @param id the >0 id of the Contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeContact(long id, boolean doAudit) throws PersistenceException, AuditException;
/**
 * <p>Remove the Contacts with given&nbsp;ids.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fe2]
 * @param ids the non null, possible empty >0 ids of the Contacts
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void removeContacts(long[] ids, boolean doAudit) throws PersistenceException , AuditException, BatchOperationException;
/**
 * <p>Update the given Contact</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fd4]
 * @param contact the non null contact to be updated
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateContact(Contact contact, boolean doAudit) throws PersistenceException, InvalidPropertyException, AuditException, EntityNotFoundException;
/**
 * <p>Update the given Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fc5]
 * @param contacts non null, possible emtpy array containing non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any client is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void updateContacts(Contact[] contacts, boolean doAudit) throws PersistenceException, InvalidPropertyException, AuditException, EntityNotFoundException, BatchOperationException;
/**
 * <p>Retrieve all the Contacts</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fb7]
 * @return Non null, possible empty array containing all non null contacts
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Contact[] getAllContacts() throws PersistenceException;
/**
 * <p>Search the Contacts with the given Filter.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fae]
 * @return non null, possible empty array containing all contacts with given condition
 * @param filter non null filter
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if filter is null
 */
    public Contact[] searchContacts(Filter filter) throws PersistenceException;
/**
 * <p>Associate the contact with the entity.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2fa1]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(Contact contact, long entity_id, boolean doAudit) throws PersistenceException, AuditException;
/**
 * <p>Deassociate the contact with the entity.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2f92]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <=0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(Contact contact, long entity_id, boolean doAudit) throws PersistenceException, AuditException;
}


