
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
/**
 * This interface specifies the contract for implementations of a Contact DAOs. A ContactDAO is responsible for accessing the database.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>The implementation of this interface will be created either by the ObjectFactory in the constructor of the ContactManager or by the application directly (and passed to the ContactManager constructor). The implementation will be called in the corresponding methods in the ContactManager.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>Implementations of this interface should be thread safe.</p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm2bd7]
 */
public interface ContactDAO {
/**
 * 
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm72ee]
 */
    public com.topcoder.timetracker.contact.Contact contact = null;
/**
 * <p>Retrieve Contact by ID.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7653]
 * @param id the >0 id of the contact
 * @return the contact with given id, null if not found
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Contact retrieveContact(long id);
/**
 * <p>Retrieve the Contacts with given ids.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm765d]
 * @param ids non null, possible empty, >0 ids
 * @return non null, possible empty array containing found Contacts, the contact may by null if not found
 * @throws IllegalArgumentException if ids is null or any id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Contact[] retrieveContacts(long[] ids);
/**
 * <p>Deassociate the contact with the entity.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm766b]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(com.topcoder.timetracker.contact.Contact contact, long entity_id, boolean doAudit);
/**
 * <p>Update the Contact</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7679]
 * @param contact non null contact to be updated
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if any exception occurs
 * @throws EntityNotFoundException if the contact can't be found
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void updateContact(com.topcoder.timetracker.contact.Contact contact, boolean doAudit);
/**
 * <p>Add the given Contacts</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7689]
 * @param contacts the non null contacts to be added
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contacts is null or any contact contained is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addContacts(com.topcoder.timetracker.contact.Contact[] contacts, boolean doAudit);
/**
 * <p>Search for the contacts with given filter.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7693]
 * @param filter non null filter
 * @return non null, possible empty array containing found Contact
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if the filter is null
 */
    public com.topcoder.timetracker.contact.Contact[] searchContacts(com.topcoder.search.builder.filter.Filter filter);
/**
 * <p>Remove the Contacts with given&nbsp;ids.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm76a1]
 * @param ids the non null, possible empty >0 ids of the Contacts
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 */
    public void removeContacts(long[] ids, boolean doAudit);
/**
 * <p>Add the given Contact into the database.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm76af]
 * @param contact non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addContact(com.topcoder.timetracker.contact.Contact contact, boolean doAudit);
/**
 * <p>Retrieve all the contacts</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm76b6]
 * @return non null, possible empty array containing all the non null contacts
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Contact[] getAllContacts();
/**
 * <p>Retrieve Contact by ID.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm76c2]
 * @param id the >0 id of the contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <=0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeContact(long id, boolean doAudit);
/**
 * <p>Update the given Contacts</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm76d3]
 * @param contacts non null, possible empty array containing non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if contacts is null or any contact is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateContacts(com.topcoder.timetracker.contact.Contact[] contacts, boolean doAudit);
/**
 * <p>Associate the contact with the entity.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm76e1]
 * @param contact the non null contact
 * @param entity_id the non null >=0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(com.topcoder.timetracker.contact.Contact contact, long entity_id, boolean doAudit);
}


