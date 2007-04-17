
package com.topcoder.timetracker.contact;
import com.topcoder.search.builder.filter.*;
import com.topcoder.timetracker.contact.Address;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
/**
 * This interface defines the contract for the complete management of an address. It provides single and batch CRUD operations as well as the means to associate or disassociate an address from an entity. It also provides the ability to audit each operation, if so desired. It has one implementation in this design: AddressManagerLocalDelegate.
 * <p><strong>Thread Safety</strong></p>
 * Implementations need not necessarily be thread safe. Each implementation should specify whether it is thread-safe or not. The application should pick the correct implementation for it's requirements.
 * 
 */
public interface AddressManager {
/**
 * <p>Add the given Address</p>
 * 
 * 
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws InvalidPropertyException if the properties of given address is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void addAddress(Address address, boolean doAudit) throws PersistenceException, IDGenerationException, InvalidPropertyException, AuditException;
/**
 * <p>Add the given addresses</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm30cd]
 * @param addresses non null, possible empty addresses
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void addAddresses(Address[] addresses, boolean doAudit);
/**
 * <p>Retrieve the given address</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm30be]
 * @return the address with the given id, null if the address if not found
 * @param id the id of the address to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Address retrieveAddress(long id) throws PersistenceException;
/**
 * <p>Retrieve the given addresses</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm30b3]
 * @return the non null addresses with given id, the containing non null addresses
 * @param ids the non null, possible empty >0 ids of the addresses
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Address[] retrieveAddresses(long[] ids);
/**
 * <p>Remove the address with given id.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm30a7]
 * @param id the >0 id of the address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeAddress(long id, boolean doAudit) throws PersistenceException, AuditException;
/**
 * <p>Remove the addresses with given&nbsp;ids.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm309a]
 * @param ids the non null, possible empty >0 ids of the addresses
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void removeAddresses(long[] ids, boolean doAudit);
/**
 * <p>Update the given address</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm308c]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given address is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the address can't be found
 */
    public void updateAddress(Address address, boolean doAudit);
/**
 * <p>Update the given addresses</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm307d]
 * @param addresses non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any address is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given address is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the address can't be found
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void updateAddresses(Address[] addresses, boolean doAudit);
/**
 * <p>Retrieve all the addresses</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm306f]
 * @return Non null, possible empty array containing all non null addresses
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Address[] getAllAddresses();
/**
 * <p>Search the addresses with the given Filter.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm3066]
 * @return non null, possible empty array containing all addresses with given condition
 * @param filter non null filter
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if filter is null
 */
    public Address[] searchAddresses(Filter filter) throws PersistenceException;
/**
 * <p>Associate the address with the entity.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm3059]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(Address address, long entity_id, boolean doAudit) throws PersistenceException, AuditException;
/**
 * <p>Deassociate the address with the entity.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm304a]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(Address address, long entity_id, boolean doAudit) throws PersistenceException, AuditException;
/**
 * <p>Retrieves all states.</p>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm303c]
 * @return non null, possible empty array containing all the non null states
 * @throws PersistenceException if any exception occurs
 */
    public State[] getAllStates();
/**
 * <p>Retrieves all countries</p>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmm3032]
 * @return non null, possible empty array containing all the non null countries
 * @throws PersistenceException if any exception occurs
 */
    public Country[] getAllCountries();
}


