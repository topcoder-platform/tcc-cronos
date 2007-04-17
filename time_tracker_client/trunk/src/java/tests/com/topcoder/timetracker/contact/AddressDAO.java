
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
/**
 * This interface specifies the contract for implementations of a Address DAOs. An AddressDAO is responsible for accessing the database.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>The implementation of this interface will be created either by the ObjectFactory in the constructor of the AddressManager or by the application directly (and passed to the AddressManager constructor). The implementation will be called in the corresponding methods in the AddressManager.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>Implementations of this interface should be thread safe.</p>
 * <ID="UML note. note-id=[I62bc0b48m1111c1c5704m1b35] ">AddressDAO will throw the exceptions which are thrown by the ContactDAO
 * <BR></ID>
 * @poseidon-object-id [I4887f14em110e3d467dfme05]
 */
public interface AddressDAO {
/**
 * 
 * 
 * @poseidon-object-id [I17670492m110e88da4a5mm256d]
 */
    public com.topcoder.timetracker.contact.Address address = null;
/**
 * <p>Retrieve address by ID.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7ae7]
 * @param id the >0 id of the address
 * @return the address with given id, null if not found
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Address retrieveAddress(long id);
/**
 * <p>Deassociate the address with the entity.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7af5]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(com.topcoder.timetracker.contact.Address address, long entity_id, boolean doAudit);
/**
 * <p>Update the address</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b03]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateAddress(com.topcoder.timetracker.contact.Address address, boolean doAudit);
/**
 * <p>Add the given addresses</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b13]
 * @param addresses non null, possible empty addresses
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the addresses is null or any address contained is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addAddresses(com.topcoder.timetracker.contact.Address[] addresses, boolean doAudit);
/**
 * <p>Remove the addresses with given&nbsp;ids.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b21]
 * @param ids the non null, possible empty >0 ids of the addresses
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 */
    public void removeAddresses(long[] ids, boolean doAudit);
/**
 * <p>Search for the addresses with given filter.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b2b]
 * @param filter non null filter
 * @return non null, possible empty array containing found addresses
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if the filter is null
 */
    public com.topcoder.timetracker.contact.Address[] searchAddresses(com.topcoder.search.builder.filter.Filter filter);
/**
 * <p>Add the given Address into the database.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b39]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addAddress(com.topcoder.timetracker.contact.Address address, boolean doAudit);
/**
 * <p>Retrieve all the addresses</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b40]
 * @return non null, possible empty array containing all the non null addresses
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Address[] getAllAddresses();
/**
 * <p>Retrieve address by ID.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b4c]
 * @param id the >0 id of the address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeAddress(long id, boolean doAudit);
/**
 * <p>Update the given addresses</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b5c]
 * @param addresses non null, possible emtpy array containing non null addresses 
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if addressed is null or any address is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateAddresses(com.topcoder.timetracker.contact.Address[] addresses, boolean doAudit);
/**
 * <p>Retrieve the addresses with given ids.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b66]
 * @param ids non null, possible empty, >0 ids
 * @return non null, possible empty array containing found addresses, the contact may by null if not found
 * @throws IllegalArgumentException if ids is null or any id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Address[] retrieveAddresses(long[] ids);
/**
 * <p>Associate the address with the entity.</p>
 * 
 * @poseidon-object-id [Im511f6665m1111ced5bfdm7b74]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(com.topcoder.timetracker.contact.Address address, long entity_id, boolean doAudit);
/**
 * <p>Retrieves all states.</p>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm267d]
 * @return non null, possible empty array containing all the non null states
 * @throws PersistenceException if any exception occurs
 */
    public State[] getAllStates();
/**
 * <p>Retrieves all countries</p>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm2665]
 * @return non null, possible empty array containing all the non null countries
 * @throws PersistenceException if any exception occurs
 */
    public Country[] getAllCountries();
}


