
package com.topcoder.timetracker.contact.persistence;
import com.topcoder.timetracker.contact.AuditException;
import com.topcoder.timetracker.contact.BatchOperationException;
import com.topcoder.timetracker.contact.ConfigurationException;
import com.topcoder.timetracker.contact.EntityNotFoundException;
import com.topcoder.timetracker.contact.IDGenerationException;
import com.topcoder.timetracker.contact.InvalidPropertyException;
import com.topcoder.timetracker.contact.PersistenceException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import java.sql.*;

/**
 * This class is the Informix database implementation of the ContactDAO. It provides general retrieve/update/remove/add functionality to access the database. And it provides SearchContacts method to search contact with filter.
 * <p><strong>Implementation Notes:</strong></p>
 * <p>The class will be created either by the ObjectFactory in the constructor of the ContactManager or by the application directly (and passed to the ContactManager constructor). It will be called in the corresponding methods in the ContactManager. </p>
 * <p><strong>Thread Safety:</strong></p>
 * <p>This class is thread safe by being immutable. However the thread safety is also depended on database transactions.</p>
 * 
 * @poseidon-object-id [I3a0be951m110a1d4a958mm2a06]
 */
public class InformixContactDAO implements com.topcoder.timetracker.contact.ContactDAO {

/**
 * <p>Represents the connection factory used to generate the Connections. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by createConnection method.</p>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm7774]
 */
    private final com.topcoder.db.connectionfactory.DBConnectionFactory connectionFactory = null;

/**
 * <p>Represents the connection name used by the connectionFactory to generate the Connections. This variable is set in constructor,&nbsp; is immutable and possible null, possible empty(trim'd). It is referenced by createConnection method.</p>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm7763]
 */
    private final String connectionName = null;

/**
 * <p>Represents the SearchBundle will be used to perform seach with filter. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by searchContact method.</p>
 * 
 * @poseidon-object-id [Im4fe31dd0m110b25e33d4m7b75]
 */
    private final com.topcoder.search.builder.SearchBundle searchBundle = null;

/**
 * <p>Represents the AuditManager used by this DAO. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by all methods which can be audited.</p>
 * 
 * @poseidon-object-id [Im2d1cf4aem111159e26f0md41]
 */
    private final com.topcoder.timetracker.audit.AuditManager auditManager = null;

/**
 * <p>Represents the IDGenerator used to generate the id of the contact. This variable is set in constructor,&nbsp; is immutable and never be null. It is referenced by addContact method.</p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m37a3]
 */
    private final com.topcoder.util.idgenerator.IDGenerator contactIDGenerator = null;

/**
 * <p>Constructs InformixContactDAO with default namespace</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Simply call this(InformixAddressDAO.class.getName())</p>
 * <p></p>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m4a15]
 * @throws ConfigurationException if it is thrown while calling the constructor
 */
    public  InformixContactDAO() {        
        // your code here
    } 

/**
 * <p>Constructs InformixContactDAO with the given arguments.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <p>Call this(namespace, null)</p>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm76d8]
 * @param namespace non null, non empty(trim'd) namespace
 * @throws IllegalArgumentException if the namespace is null or empty(trim'd)
 * @throws ConfigurationException if any exception prevents creating the connection factory successfully
 */
    public  InformixContactDAO(String namespace) {        
        // your code here
    } 

/**
 * <p>Constructs InformixContactDAO with the given arguments.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the configManager by ConfigManager.getInstance()</li>
 * <li>Get the connectionFactoryNamespace from the configManager with namespace and key as &quot;connection_factory_namespace&quot;</li>
 * <li>Create the connectionFactory by new DBConnectionFactoryImpl(connection_factory_namespace)</li>
 * <li>Get the connectionName from the configManager with namespace and key as &quot;connection_name&quot;</li>
 * <li>Get the idGeneratorName from the configManager with namespace and key as &quot;idgenerator_name&quot;</li>
 * <li>Get contactIDGenerator by IDGeneratorFactory.getIDGenerator(idGeneratorName)</li>
 * <li>Get the searchBundleName from the configManager with namespace and key as &quot;search_bundle_name&quot;</li>
 * <li>Get the searchBundleNamespace from the configManager with namespace and key as &quot;search_bundle_namespace&quot;</li>
 * <li>Create search bundle manager with searchBundleNamespace</li>
 * <li>Get searchBundle by manager.getSearchBundle(search_bundle_name)</li>
 * <li>If auditManager is null, get the auditManagerNamespace from the configManager with namespace and key as &quot;audit_manager_namespace&quot;. Create the ObjectFactory with a new ConfigManagerSpecificationFactory created with the auditManagerNamespace. Create auditManager by the ObjectFactory with the key as &quot;AuditManager&quot;. Else save auditManager to the like named variable.</li>
 * </ol>
 * 
 * @poseidon-object-id [Im31e8a819m11116265165m4a67]
 * @param namespace non null, non empty(trim'd) namespace
 * @param auditManager possible null audit manager
 * @throws IllegalArgumentException if the namespace is null
 * @throws ConfigurationException if any exception prevents creating the connection factory successfully
 */
    public  InformixContactDAO(String namespace, com.topcoder.timetracker.audit.AuditManager auditManager) {        
        // your code here
    } 

/**
 * <p>Add the given Contact into the database.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Throw InvalidPropertyException if any property of the contact is null.</li>
 * <li>Set the ID of the contact by idGenerator.getNextID()</li>
 * <li>Set the creation and modification date to current date.</li>
 * <li>If doAudit is true, create an auditHeader with the info of this contact and create it by AuditManager</li>
 * <li>Get a connection by createConnection, and create a prepared statement to add the contact to the contact table.</li>
 * <li>Set all the parameters of the statement according to all the properties of contact and execute the statement.</li>
 * </ol>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm7579]
 * @param contact non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addContact(com.topcoder.timetracker.contact.Contact contact, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Add the given Contacts</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>For each contact ...</li>
 * <li>Throw InvalidPropertyException if any property of the contact is null.</li>
 * <li>Set the ID of the contact by contactIDGenerator.getNextID()</li>
 * <li>If doAudit is true, create an auditHeader with the info of this contact and create it by AuditManager</li>
 * <li>Add the id of the header to the&nbsp; auditId which is created by new long[contacts.length]</li>
 * <li>... end each</li>
 * <li>Get a connection by createConnection and create a prepared statement to add the contact to the contact table.</li>
 * <li>For each contact, set all the parameters of the statement according to all the properties of contact and addBatch</li>
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
 * @poseidon-object-id [I17670492m110e88da4a5mm2173]
 * @param contacts the non null contacts to be added
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contacts is null or any contact contained is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws IDGenerationException if any exception occurs while generating ID
 */
    public void addContacts(com.topcoder.timetracker.contact.Contact[] contacts, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Retrieve Contact by ID.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get a connection by createConnection, and create a prepared statement to retieve the contact from the contact table with the contact type from contact_type table.</li>
 * <li>Set the id of contact in the statement and execute it.</li>
 * <li>If the record is found, create Contact with the ContactType(the id of the ContactType should equal the contact_id) according this record.</li>
 * <li>Else return null</li>
 * <li>Close the connection before return</li>
 * </ol>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm73c6]
 * @param id the >0 id of the contact
 * @return the contact with given id, null if not found
 * @throws IllegalArgumentException if the id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Contact retrieveContact(long id) {        
        // your code here
        return null;
    } 

/**
 * <p>Retrieve the Contacts with given ids.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get a connection by createConnection, and create a prepared statement to retieve the all the contacts with given ids from the contact table with the contact type from contact_type table.</li>
 * <li>Execute the statement.</li>
 * <li>For each record, create Contact with the ContactType(the id of the ContactType should equal the contact_id)&nbsp; according this record.</li>
 * <li>Close the connection and return an array containing all the Contacts</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm693]
 * @param ids non null, possible empty, >0 ids
 * @return non null, possible empty array containing found Contacts, the contact may by null if not found
 * @throws IllegalArgumentException if ids is null or any id <= 0
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Contact[] retrieveContacts(long[] ids) {        
        // your code here
        return null;
    } 

/**
 * <p>Retrieve Contact by ID.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If doAudit is true, create an auditHeader with the info of this contact and create it by AuditManager</li>
 * <li>Get a connection by createConnection, and create a prepared statement to remove the contact from the contact table.</li>
 * <li>Set the parameter of the statement to the id of contact and&nbsp; execute it.</li>
 * </ol>
 * 
 * @poseidon-object-id [Im4fe31dd0m110b27892bfmm1ca8]
 * @param id the >0 id of the contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the id <=0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void removeContact(long id, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Remove the Contacts with given&nbsp;ids.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get the contacts by retrieveContacts(ids)</li>
 * <li>For each contact ...</li>
 * <li>If doAudit is true, create an auditHeader with the info of this contact and create it by AuditManager</li>
 * <li>Add the id of the header to the&nbsp; auditId which is created by new long[contacts.length]</li>
 * <li>... end each</li>
 * <li>Get a connection by createConnection and create a prepared statement to remove the contact with given id from the contact table.</li>
 * <li>For each contact, set all the parameter of the statement to id of contact and addBatch</li>
 * <li>Execute the batch, and get the returned int[]</li>
 * <li>
 * If the BatchUpdateOperation is thrown
 * <ol>
 * <li>Create a boolean[], set boolean[i] to int[i]!=EXECUTE_FAILED, create BatchOperationException with the boolean array.</li>
 * <li>Throw the BatchOperationException.</li>
 * </ol>
 * </li>
 * </ol>
 * 
 * @poseidon-object-id [I17670492m110e88da4a5mm2180]
 * @param ids the non null, possible empty >0 ids of the Contacts
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if any id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws AuditException if exception occurs when audit
 */
    public void removeContacts(long[] ids, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Update the Contact</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Throw InvalidPropertyException if the id of the contact &lt;=0, or any property of the contact is null.</li>
 * <li>If the bean is not changed(contact.isChanged() is false), simply return.</li>
 * <li>Set the modification date to current date.</li>
 * <li>If doAudit is true, get the contact with the id by retrieveContact. Create an auditHeader with the different and create it by AuditManager</li>
 * <li>Get a connection by createConnection, and create a prepared statement to update the contact.</li>
 * <li>Set all the parameters of the statement according to all the properties of contact and execute the statement.</li>
 * </ol>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm73ae]
 * @param contact non null contact to be updated
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null
 * @throws PersistenceException if any exception occurs
 * @throws EntityNotFoundException if the contact can't be found
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 */
    public void updateContact(com.topcoder.timetracker.contact.Contact contact, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Update the given Contacts</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Throw InvalidPropertyException if the id of the contact &lt;=0, or any property of the contact is null.</li>
 * <li>Set the modification date to current date.</li>
 * <li>Get all the ids of contacts</li>
 * <li>Get the orgContacts by retrieveContacts(ids)</li>
 * <li>For each contact ...</li>
 * <li>If doAudit is true, create an auditHeader with the difference between orgContact and contact, and create it by AuditManager</li>
 * <li>Add the id of the header to the&nbsp; auditId which is created by new long[contacts.length]</li>
 * <li>... end each</li>
 * <li>Get a connection by createConnection and create a prepared statement to update the contact in the contact table.</li>
 * <li>For each contact, set all the parameters of the statement according to all the properties of contact and addBatch</li>
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
 * @poseidon-object-id [I17670492m110e88da4a5mm2169]
 * @param contacts non null, possible empty array containing non null contact
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if contacts is null or any contact is null
 * @throws PersistenceException if it is thrown by the dao
 * @throws BatchOperationException if any operation in the batch is failed
 * @throws InvalidPropertyException if the properties of given contact is invalid
 * @throws AuditException if exception occurs when audit
 * @throws EntityNotFoundException if the contact can't be found
 */
    public void updateContacts(com.topcoder.timetracker.contact.Contact[] contacts, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Retrieve all the contacts</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Get a connection by createConnection, and create a statement.</li>
 * <li>Build sql query to retrieve contacts with given ids from the contact table with the contact type from contact_type table and execute it.</li>
 * <li>For each record</li>
 * <li>Create a new Contact and ContactType(the id of the ContactType should equal the contact_id)&nbsp; according to the record</li>
 * <li>... end each</li>
 * <li>Close the connection and return an array containing all the Contacts</li>
 * </ol>
 * <p></p>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm7346]
 * @return non null, possible empty array containing all the non null contacts
 * @throws PersistenceException if any exception occurs
 */
    public com.topcoder.timetracker.contact.Contact[] getAllContacts() {        
        // your code here
        return null;
    } 

/**
 * <p>Search for the contacts with given filter.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>Call searchBundle.search(filter)</li>
 * <li>For each record</li>
 * <li>Create a new Contact&nbsp;and ContactType(the id of the ContactType should equal the contact_id)&nbsp; according to the record</li>
 * <li>... end each</li>
 * <li>Return an array containing all the Contacts</li>
 * </ol>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm7321]
 * @param filter non null filter
 * @return non null, possible empty array containing found Contact
 * @throws PersistenceException if any exception occurs
 * @throws IllegalArgumentException if the filter is null
 */
    public com.topcoder.timetracker.contact.Contact[] searchContacts(com.topcoder.search.builder.filter.Filter filter) {        
        // your code here
        return null;
    } 

/**
 * <p>Deassociate the contact with the entity.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If audit deassociate is true, create an auditHeader with the info of this association and create it by AuditManager</li>
 * <li>Get a connection by createConnection, and create a prepared statement to delete the association to the contact_relation table.</li>
 * <li>Set all the parameters of the statement according to the contact ID, contact type ID, entity ID and execute the statement.</li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm297a]
 * @param contact the non null contact
 * @param entity_id the non null >0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void deassociate(com.topcoder.timetracker.contact.Contact contact, long entity_id, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Associate the contact with the entity.</p>
 * <p><span style="color:Blue">Note: For v3.2, all rollbacks occur in the EJB. Therefore, even the audit manager is not called programatically to rollback the created audit. </span></p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>
 * <p>If audit associate is true, create an auditHeader with the info of this association and create it by AuditManager</p>
 * </li>
 * <li>
 * <p>Get a connection by createConnection, and create a prepared statement to add the association to the contact_relation table.</p>
 * </li>
 * <li>
 * <p>Set all the parameters of the statement according to the contact ID, contact type ID, entity ID and execute the statement.</p>
 * </li>
 * </ol>
 * 
 * @poseidon-object-id [I4887f14em110e3d467dfmm296d]
 * @param contact the non null contact
 * @param entity_id the non null >=0 id of entity
 * @param doAudit whether this action should be audited
 * @throws IllegalArgumentException if the contact is null or the id <= 0
 * @throws PersistenceException if any exception occurs
 * @throws AuditException if exception occurs when audit
 */
    public void associate(com.topcoder.timetracker.contact.Contact contact, long entity_id, boolean doAudit) {        
        // your code here
    } 

/**
 * <p>Create a Connection.</p>
 * <p><strong>Implementation Notes:</strong></p>
 * <ol>
 * <li>If the name is null or empty(trim'd), return connectionFactory.createConnection()</li>
 * <li>Else return connectionFactory.createConnection(connectionName)</li>
 * </ol>
 * 
 * @poseidon-object-id [I1dab968dm110a233d42cmm7265]
 * @return non null created Connection
 * @throws PersistenceException if any exception prevents creating the Connection successfully
 */
    private java.sql.Connection createConnection() {        
        // your code here
        return null;
    } 
 }
