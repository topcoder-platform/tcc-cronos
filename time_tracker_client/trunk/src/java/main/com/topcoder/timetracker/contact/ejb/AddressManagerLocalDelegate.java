
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.*;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.timetracker.contact.ejb.AddressLocalHome;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

/**
 * Implements the AddressManager interface to provide management of the Address objects through the use of a local session EBJ. It will obtain the handle to the beans local interface and will simply delegate all calls to it. It implements all methods.
 * <p><strong>Thread Safety</strong></p>
 * This class is immutable and thread-safe. It is not expected that the data beans will be used by more
 * than one thread at a time.
 * <ID="UML note. note-id=[I3998e7b8m111455da48fmm492f] ">AddressManagerLocalDelegate will throw the exceptions 
 * which are thrown by the AddressManager, plus ConfigurationException
 * <BR></ID>
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd97]
 */
public class AddressManagerLocalDelegate implements com.topcoder.timetracker.contact.AddressManager {

/**
 * Represents the local session ejb instance used for all calls. Created in the constructor, will not be null,
 * and will not change.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmbc6]
 */
    private final AddressLocal localEJB = null;

/**
 * <p>Constructs the AddressManagerLocalDelegate with default namespace.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call this(AddressManagerLocalDelegate.class.getName())</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmbc0]
 * @throws ConfigurationException if it is thrown by the constructor
 */
    public  AddressManagerLocalDelegate() {        
 
        // your code here
    } 

/**
 * Instantiates new AddressLocal instance from the given namespace.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Obtain jndi ejb reference from ConfigManager.</li>
 * <li>InitialContext ic = new InitialContext()</li>
 * <li>AddressHomeLocal home = (AddressHomeLocal) ic.lookup(reference)</li>
 * <li>this.localEJB = home.create()</li>
 * </ul>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmbb8]
 * @param namespace configuration namespace
 * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
 * @throws ConfigurationException if any exception is thrown by config manager or the required property isn't provided
 */
    public  AddressManagerLocalDelegate(String namespace) {        
 
        // your code here
    } 

/**
 * <p>Add the given Address</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Add the address by localEJB.addAddress(address, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmbad]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws InvalidPropertyException if the properties of given address is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void addAddress(Address address, boolean doAudit) {        
 
        // your code here
    } 

/**
 * <p>Add the given addresses</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call localEJB.addAddresses(addresses, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb9e]
 * @param addresses non null, possible empty addresses
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IDGenerationException if any exception occurs while generating ID
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void addAddresses(Address[] addresses, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Retrieve the given address</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the address by localEJB.retrieveAddress(id)</li>
 * <li>Return the address</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb8f]
 * @return the address with the given id, null if the address if not found
 * @param id the id of the address to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Address retrieveAddress(long id) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Retrieve the given addresses</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the addresses by localEJB.retrieveAddresses(ids)</li>
 * <li>Return the addresses</li>
 * </ol>
 * <p></p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb84]
 * @return the non null addresses with given id, the containing non null addresses
 * @param ids the non null, possible empty >0 ids of the addresses
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Address[] retrieveAddresses(long[] ids) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Remove the address with given id.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Remove the address by localEJB.removeAddress(id, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb78]
 * @param id the >0 id of the address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeAddress(long id, boolean doAudit) {        
 
        // your code here
    } 

/**
 * <p>Remove the addresses with given&nbsp;ids.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call localEJB.removeAddresses(ids, doAudit)</p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb6b]
 * @param ids the non null, possible empty >0 ids of the addresses
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void removeAddresses(long[] ids, boolean doAudit) {        
 
        // your code here
    } 

/**
 * <p>Update the given address</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call localEJB.updateAddress(address, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb5d]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given address is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the address can't be found
 */
    public void updateAddress(Address address, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Update the given addresses</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call localEJB.updateAddresses(addresses, doAudit)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb4e]
 * @param addresses non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any address is null
 * @throws PersistenceException if it is thrown by the localEJB
 * @throws InvalidPropertyException if the properties of given address is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the address can't be found
 * @throws BatchOperationException if any operation in the batch is failed
 */
    public void updateAddresses(Address[] addresses, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Retrieve all the addresses</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get addresses localEJB.retrieveAlladdresses()</li>
 * <li>return addresses</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb40]
 * @return Non null, possible empty array containing all non null addresses
 * @throws PersistenceException if it is thrown by the localEJB
 */
    public Address[] getAllAddresses() {        
 
        // your code here
        return null;
    } 

/**
 * <p>Search the addresses with the given Filter.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return localEJB.searchAddresses(filter)</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb37]
 * @return non null, possible empty array containing all addresses with given condition
 * @param filter non null filter
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if filter is null
 */
    public Address[] searchAddresses(Filter filter) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Associate the address with the entity.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Add this association by localEJB.associate</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb2a]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(Address address, long entity_id, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Deassociate the address with the entity.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Remove this association by localEJB.deassociate</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb1b]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(Address address, long entity_id, boolean doAudit) {        
        
        // your code here
    } 

/**
 * <p>Retrieves all states.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get addresses localEJB.getAllStates()</li>
 * <li>return states</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb0f]
 * @return non null, possible empty array containing all the non null states
 * @throws PersistenceException if any exception occurs
 */
    public State[] getAllStates() {        
 
        // your code here
        return null;
    } 

/**
 * <p>Retrieves all countries</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get addresses localEJB.getAllCountries()</li>
 * <li>return countries</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmb07]
 * @return non null, possible empty array containing all the non null countries
 * @throws PersistenceException if any exception occurs
 */
    public Country[] getAllCountries() {        
 
        // your code here
        return null;
    } 
 }
