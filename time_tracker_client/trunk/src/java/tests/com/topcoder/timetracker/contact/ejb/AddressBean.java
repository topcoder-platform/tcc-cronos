
package com.topcoder.timetracker.contact.ejb;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.contact.*;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import javax.ejb.CreateException;
import javax.ejb.SessionContext;

/**
 * The session EJB that handles the actual manager requests. It simply delegates all operations to the AddressDAO it obtains from the ObjectFactory.
 * <p><b>Thread Safety:</b> This object is mutable and thread-safe, as the container handles this.</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd9c]
 */
public class AddressBean implements javax.ejb.SessionBean {

/**
 * Represents the session context of this bean. It is used to indicate to the container if the bean wants
 * to rollback a transaction. This would usually occur if an application exception occurs. Set in the
 * setSessionContext() method by the container.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd67]
 */
    private SessionContext sessionContext = null;

/**
 * Represents the data access object for performing the persistence operations for the address entries.
 * It is initialized in the ejbCreate method, and not changed afterwards. There will be one instantiation per
 * one session bean lifetime.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd61]
 */
    private AddressDAO addressDAO = null;

/**
 * Empty constructor.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd5a]
 */
    public  AddressBean() {        
        // your code here
    } 

/**
 * Creates the bean. Initializes the AddressDAO instance to be used by this component.
 * <p><strong>Implementation Notes</strong></p>
 * <ul type="disc">
 * <li>Performs a lookup on the envirnmental variable SpecificationNamespace</li>
 * <li>Creates a new ConfigManagerSpecificationFactory with this namespace</li>
 * <li>Creates new ObjectFactory with this specification factory</li>
 * <li>Performs a lookup on the envirnmental variable AddressDAOKey</li>
 * <li>Gets instance of AddressDAO from object factory with the retrieved dao key</li>
 * </ul>
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd54]
 * @throws CreateException If any error occurs while instantiating
 */
    public void ejbCreate() {        
        // your code here
    } 

/**
 * Removes the bean. This is an empty implementation.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd4d]
 */
    public void ejbRemove() {        
        // your code here
    } 

/**
 * Activates the bean. This is an empty implementation.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd47]
 */
    public void ejbActivate() {        
        // your code here
    } 

/**
 * Passivates the bean. This is an empty implementation.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd41]
 */
    public void ejbPassivate() {        
        // your code here
    } 

/**
 * Sets the session context.
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd3a]
 * @param ctx session context
 */
    public void setSessionContext(SessionContext ctx) {        
        // your code here
    } 

/**
 * <p>Add the given Address</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Add the address by addressDAO.addAddress(address, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd31]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if it is thrown by the addressDAO
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
 * <p>Call addressDAO.addAddresses(addresses, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd22]
 * @param addresses non null, possible empty addresses
 * @param doAudit whether this action should be audited
 * @throws InvalidPropertyException if the properties of given Client is invalid
 * @throws IllegalArgumentException if the client is null or any client contained is null
 * @throws PersistenceException if it is thrown by the addressDAO
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
 * <li>Get the address by addressDAO.retrieveAddress(id)</li>
 * <li>Return the address</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd13]
 * @return the address with the given id, null if the address if not found
 * @param id the id of the address to be retrieved
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if it is thrown by the addressDAO
 */
    public Address retrieveAddress(long id) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Retrieve the given addresses</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the addresses by addressDAO.retrieveAddresses(ids)</li>
 * <li>Return the addresses</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * <p></p>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmd08]
 * @return the non null addresses with given id, the containing non null addresses
 * @param ids the non null, possible empty >0 ids of the addresses
 * @throws PersistenceException if it is thrown by the addressDAO
 * @throws IllegalArgumentException if ids is null or any id <= 0
 */
    public Address[] retrieveAddresses(long[] ids) {        
 
        // your code here
        return null;
    } 

/**
 * <p>Remove the address with given id.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Remove the address by addressDAO.removeAddress(id, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmcfc]
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
 * <p>Call addressDAO.removeAddresses(ids, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmcef]
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
 * <p>Simply call addressDAO.updateAddress(address, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmce1]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if it is thrown by the addressDAO
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
 * <p>Call addressDAO.updateAddresses(addresses, doAudit)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmcd2]
 * @param addresses non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any address is null
 * @throws PersistenceException if it is thrown by the addressDAO
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
 * <li>Get addresses addressDAO.retrieveAlladdresses()</li>
 * <li>return addresses</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * <p></p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmcc4]
 * @return Non null, possible empty array containing all non null addresses
 * @throws PersistenceException if it is thrown by the addressDAO
 */
    public Address[] getAllAddresses() {        
 
        // your code here
        return null;
    } 

/**
 * <p>Search the addresses with the given Filter.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Return addressDAO.searchAddresses(filter)</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmcbb]
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
 * <p>Add this association by addressDAO.associate</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmcae]
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
 * <p>Remove this association by addressDAO.deassociate</p>
 * <p>If exception is thrown, call sessionContext.setRollbackOnly()</p>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmc9f]
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
 * <li>Get addresses addressDAO.getAllStates()</li>
 * <li>return states</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmc93]
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
 * <li>Get addresses addressDAO.getAllCountries()</li>
 * <li>return countries</li>
 * <li>If exception is thrown, call sessionContext.setRollbackOnly()</li>
 * </ol>
 * 
 * 
 * @poseidon-object-id [I3998e7b8m111451c0abcmmc8b]
 * @return non null, possible empty array containing all the non null countries
 * @throws PersistenceException if any exception occurs
 */
    public Country[] getAllCountries() {        
 
        // your code here
        return null;
    } 
 }
