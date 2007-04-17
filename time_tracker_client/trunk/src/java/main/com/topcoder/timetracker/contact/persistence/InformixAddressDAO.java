
package com.topcoder.timetracker.contact.persistence;
import com.topcoder.timetracker.contact.Country;
import com.topcoder.timetracker.contact.State;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import java.sql.*;

/**
 * This class is the Informix database implementation of the AddressDAO. It provides general retrieve/update/remove/add functionality to access the database. And it provides SearchAddresses method to search addresses with filter.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>The class will be created either by the ObjectFactory in the constructor of the AddressManager or by the application directly (and passed to the AddressManager constructor). It will be called in the corresponding methods in the AddressManager.</p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread safe by being immutable. However the thread safety is also depended on database transactions.</p>
 * <ID="UML note. note-id=[I62bc0b48m1111c1c5704m2334] ">InformixAddressDAO will throw the exceptions
 * which will be thrown by InformixContactDAO
 * <BR></ID>
 * @poseidon-object-id [I4887f14em110e3d467dfm12dd]
 */
public class InformixAddressDAO implements com.topcoder.timetracker.contact.AddressDAO {

/**
 * <p>Represents the connection factory used to generate the Connections. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by createConnection method.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12db]
 */
    private final com.topcoder.db.connectionfactory.DBConnectionFactory connectionFactory = null;

/**
 * <p>Represents the connection name used by the connectionFactory to generate the Connections. This variable is set in constructor,&nbsp; is immutable and possible null, possible empty(trim'd). It is referenced by createConnection method.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12d8]
 */
    private final String connectionName = null;

/**
 * <p>Represents the SearchBundle will be used to perform seach with filter. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by searchContact method.</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm129d]
 */
    private final com.topcoder.search.builder.SearchBundle searchBundle = null;

/**
 * <p>Represents the AuditManager used by this DAO. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by all methods which can be audited.</p>
 * 
 * @poseidon-object-id [Im2d1cf4aem111159e26f0md18]
 */
    private final com.topcoder.timetracker.audit.AuditManager auditManager = null;

/**
 * <p>Represents the IDGenerator used to generate the id of the address. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by addAddress method.</p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m377e]
 */
    private final com.topcoder.util.idgenerator.IDGenerator addressIDGenerator = null;

/**
 * <p>Constructs InformixAddressDAO with default namespace.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call this(InformixAddressDAO.class.getName())</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m49c7]
 * @throws ConfigurationException if it is thrown while calling the constructor
 */
    public  InformixAddressDAO() {        
        // your code here
    } 

/**
 * <p>Constructs InformixAddressDAO with the given namespace.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call this(namespace, null)</p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12d0]
 * @param namespace non null, non empty(trim'd) namespace
 * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
 * @throws ConfigurationException if any exception prevents creating the connection factory successfully
 */
    public  InformixAddressDAO(String namespace) {        
        // your code here
    } 

/**
 * <p>Constructs InformixAddressDAO with the given arguments.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the configManager by ConfigManager.getInstance()</li>
 * <li>Get the connectionFactoryNamespace from the configManager with namespace and key as &quot;connection_factory_namespace&quot;</li>
 * <li>Create the connectionFactory by new DBConnectionFactoryImpl(connection_factory_namespace)</li>
 * <li>Get the connectionName from the configManager with namespace and key as &quot;connection_name&quot;</li>
 * <li>Get the idGeneratorName from the configManager with namespace and key as &quot;idgenerator_name&quot;</li>
 * <li>Get addressIDGenerator by IDGeneratorFactory.getIDGenerator(idGeneratorName)</li>
 * <li>Get the searchBundleName from the configManager with namespace and key as &quot;search_bundle_name&quot;</li>
 * <li>Get the searchBundleNamespace from the configManager with namespace and key as &quot;search_bundle_namespace&quot;</li>
 * <li>Create search bundle manager with searchBundleNamespace</li>
 * <li>Get searchBundle by manager.getSearchBundle(search_bundle_name)</li>
 * <li>If auditManager is null, get the auditManagerNamespace from the configManager with namespace and key as &quot;audit_manager_namespace&quot;. Create the ObjectFactory with a new ConfigManagerSpecificationFactory created with the auditManagerNamespace. Create auditManager by the ObjectFactory with the key as &quot;AuditManager&quot;. Else save auditManager to the like named variable.</li>
 * </ol>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m4aee]
 * @param namespace non null, non empty(trim'd) namespace
 * @param auditManager possible null audit manager
 * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
 * @throws ConfigurationException if any exception prevents creating the connection factory successfully
 */
    public  InformixAddressDAO(String namespace, com.topcoder.timetracker.audit.AuditManager auditManager) {        
        // your code here
    } 

/**
 * <p>Add the given Address into the database.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Throw InvalidPropertyException if any property of the address is null.</li>
 * <li>Set the ID of the address by idGenerator.getNextID()</li>
 * <li>Set the creation and modification date to current date.</li>
 * <li>If audit add address is true, Create an auditHeader with the info of this address and createAuditRecord by AuditManager</li>
 * <li>Get a connection by createConnection, and create a statement.</li>
 * <li>Build sql statement to add the given address to the address table.</li>
 * <li>Execute the statement.</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12c8]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addAddress(com.topcoder.timetracker.contact.Address address, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Add the given addresses</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>For each address ...</li>
 * <li>Throw InvalidPropertyException if any property of the address is null.</li>
 * <li>Set the ID of the address by idGenerator.getNextID()</li>
 * <li>If audit add address is true, create an auditHeader with the info of this address and create it by AuditManager</li>
 * <li>Add the id of the header to the&nbsp; auditId which is created by new long[addresses.length]</li>
 * <li>... end each</li>
 * <li>Get a connection by createConnection and create a prepared statement to add the address to the address table.</li>
 * <li>For each address, set all the parameters of the statement according to all the properties of address and addBatch</li>
 * <li>Execute the batch, and get the returned int[]</li>
 * <li>
 * If BatchUpdateException is thrown
 * <ol>
 * <li>Create a boolean[], set boolean[i] to int[i]!=EXECUTE_FAILED, create BatchOperationException with the boolean array.</li>
 * <li>Throw the BatchOperationException.</li>
 * </ol>
 * </li>
 * </ol>
 * 
 * @poseidon-object-id [I17670492m110e88da4a5mm20f7]
 * @param addresses non null, possible empty addresses
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the addresses is null or any address contained is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addAddresses(com.topcoder.timetracker.contact.Address[] addresses, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Retrieve address by ID.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get a connection by createConnection, and create a prepared statement to retieve the address from the address table with the address type, country, state from address_type, country, state table.</li>
 * <li>Set the id of contact in the statement and execute it.</li>
 * <li>If the record is found, create Address with the AddressType, Country and State according this record.</li>
 * <li>Else return null</li>
 * <li>Close the connection before return</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12bf]
 * @param id the >0 id of the address
 * @return the address with given id, null if not found
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Address retrieveAddress(long id) {        
        // your code here
        return null;
    } 

/**
 * <p>Retrieve the addresses with given ids.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get a connection by createConnection, and create a prepared statement to retieve the all the address with given ids from the address table with the address type, country, state from address_type, country, state table.</li>
 * <li>Execute the statement.</li>
 * <li>For each record, create Address with the AddressType, Country and State according this record.</li>
 * <li>Close the connection and return an array containing all the Addresses</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12b7]
 * @param ids non null, possible empty, >0 ids
 * @return non null, possible empty array containing found addresses, the contact may by null if not found
 * @throws IllegalArgumentException if ids is null or any id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Address[] retrieveAddresses(long[] ids) {        
        // your code here
        return null;
    } 

/**
 * <p>Retrieve address by ID.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If audit remove address is true, Create an auditHeader with the info of this address and createAuditRecord by AuditManager</li>
 * <li>Get a connection by createConnection, and create a prepared statement to remove the address from the address table.</li>
 * <li>Set the parameter of the statement to the id of address and&nbsp; execute it.</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12ad]
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
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the addresses by retrieveAddresses(ids)</li>
 * <li>For each address ...</li>
 * <li>If audit remove address is true, create an auditHeader with the info of this address and create it by AuditManager</li>
 * <li>Add the id of the header to the&nbsp; auditId which is created by new long[addresses.length]</li>
 * <li>... end each</li>
 * <li>Get a connection by createConnection</li>
 * <li>Create a statement.</li>
 * <li>For each address, create a sql statement to remove the address and addBatch</li>
 * <li>Execute the batch, and get the returned int[]</li>
 * <li>Create a boolean[], set boolean[i] to int[i]!=EXECUTE_FAILED</li>
 * <li>Close the connection and return the boolean[]</li>
 * </ol>
 * 
 * @poseidon-object-id [I17670492m110e88da4a5mm20ed]
 * @param ids the non null, possible empty >0 ids of the addresses
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 */
    public void removeAddresses(long[] ids, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Update the address</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Throw InvalidPropertyException if the id of the address &lt;=0, or any property of the address is null.</li>
 * <li>If the bean is not changed(address.isChanged() is false), simply return.</li>
 * <li>Set the modification date to current date.</li>
 * <li>If doAudit is true, get the address with the id by retrieveAddress. Create an auditHeader with the different and create it by AuditManager</li>
 * <li>Get a connection by createConnection, and create a prepared statement to update the address.</li>
 * <li>Set all the parameters of the statement according to all the properties of address and execute the statement.</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm12a6]
 * @param address non null address
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateAddress(com.topcoder.timetracker.contact.Address address, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Update the given addresses</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Throw InvalidPropertyException if the id of the address &lt;=0, or any property of the address is null.</li>
 * <li>Set the modification date to current date.</li>
 * <li>Get all the ids of addresses</li>
 * <li>Get the orgAddresses by retrieveAddresses(ids)</li>
 * <li>For each address ...</li>
 * <li>If doAudit is true, create an auditHeader with the difference between orgAddresses and address, and create it by AuditManager</li>
 * <li>Add the id of the header to the&nbsp; auditId which is created by new long[addresses.length]</li>
 * <li>... end each</li>
 * <li>Get a connection by createConnection and create a prepared statement to update the address in the address table.</li>
 * <li>For each address, set all the parameters of the statement according to all the properties of address and addBatch</li>
 * <li>Execute the batch, and get the returned int[]</li>
 * <li>
 * If the BatchUpdateOperation is thrown
 * <ol>
 * <li>Create a boolean[], set boolean[i] to int[i]!=EXECUTE_FAILED</li>
 * <li>Throw the BatchOperationException.</li>
 * </ol>
 * </li>
 * </ol>
 * 
 * @poseidon-object-id [I17670492m110e88da4a5mm2104]
 * @param addresses non null, possible emtpy array containing non null addresses 
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if addressed is null or any address is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateAddresses(com.topcoder.timetracker.contact.Address[] addresses, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Retrieve all the addresses</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get a connection by createConnection, and create a statement.</li>
 * <li>Build sql query to retrieve all the addresses with given ids from the address table with the address type, country, state from address_type, country, state table.</li>
 * <li>For each record</li>
 * <li>Create a new address with AddressType, Country and State according to the record</li>
 * <li>... end each</li>
 * <li>Close the connection and return an array containing all the addresses</li>
 * </ol>
 * <p></p>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm1298]
 * @return non null, possible empty array containing all the non null addresses
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Address[] getAllAddresses() {        
        // your code here
        return null;
    } 

/**
 * <p>Search for the addresses with given filter.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Call searchBundle.search(filter)</li>
 * <li>For each record</li>
 * <li>Create a new address with AddressType, Country and State according to the record</li>
 * <li>... end each</li>
 * <li>Return an array containing all the addresses</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm1291]
 * @param filter non null filter
 * @return non null, possible empty array containing found addresses
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if the filter is null
 */
    public com.topcoder.timetracker.contact.Address[] searchAddresses(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 

/**
 * <p>Deassociate the address with the entity.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If audit deassociate is true, Create an auditHeader with the info of this association and createAuditRecord by AuditManager</li>
 * <li>Get a connection by createConnection, and create a statement.</li>
 * <li>Build sql statement to remove the association which address id, address type id and entity id equal to the given from the address_relation table.</li>
 * <li>Execute the statement.</li>
 * <li>Close the connection</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm1287]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(com.topcoder.timetracker.contact.Address address, long entity_id, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Associate the address with the entity.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>
 * <p>If audit associate is true, Create an auditHeader with the info of this association and createAuditRecord by AuditManager</p>
 * </li>
 * <li>
 * <p>Get a connection by createConnection, and create a statement.</p>
 * </li>
 * <li>
 * <p>Build sql statement to add the association(use address's creation/modification date/user) to the&nbsp;address_relation table.</p>
 * </li>
 * <li>
 * <p>Execute the statement.</p>
 * </li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm127b]
 * @param address non null address
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the address is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(com.topcoder.timetracker.contact.Address address, long entity_id, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Retrieves all states.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ul type="disc">
 * <li>Get a connection by createConnection, and create a statement.</li>
 * <li>Build sql query to retrieve all state records from the state table.</li>
 * <li>
 * For each record
 * <ul type="disc">
 * <li>Create a new State</li>
 * </ul>
 * </li>
 * <li>Close the connection and return an array containing all retrieved states</li>
 * </ul>
 * 
 * @poseidon-object-id [I5906274cm11144b5f5aemm7d29]
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
 * <ul type="disc">
 * <li>Get a connection by createConnection, and create a statement.</li>
 * <li>Build sql query to retrieve all country records from the country table.</li>
 * <li>
 * For each record
 * <ul type="disc">
 * <li>Create a new Country</li>
 * </ul>
 * </li>
 * <li>Close the connection and return an array containing all retrieved states</li>
 * </ul>
 * <p></p>
 * 
 * @poseidon-object-id [I5906274cm11144b5f5aemm7d11]
 * @return non null, possible empty array containing all the non null countries
 * @throws PersistenceException if any exception occurs
 */
    public Country[] getAllCountries() {        
        // your code here
        return null;
    } 

/**
 * <p>Create a Connection.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If the name is null or empty(trim'd), return connectionFactory.createConnection()</li>
 * <li>Else return connectionFactory.createConnection(connectionName)</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfm1271]
 * @return non null created Connection
 * @throws PersistenceException if any exception prevents creating the Connection successfully
 */
    private java.sql.Connection createConnection() {        
        // your code here
        return null;
    } 
 }
