
package com.topcoder.timetracker.common.persistence;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManagerConfigurationException;
import com.topcoder.timetracker.common.DuplicatePaymentTermException;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAO;
import com.topcoder.timetracker.common.PaymentTermDAOException;
import com.topcoder.timetracker.common.PaymentTermNotFoundException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

/**
 * <p><strong>Usage:</strong> Database implementation of the PaymentTermDAO interface. It is capable of persisting and retrieving PaymentTerm information from the database. Add, Retrieve, Update, Delete methods are provided. DB Connection is realized using DBConnectionFactory.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li><strong>implements </strong>PaymentTermDAO.</li>
 * </ul>
 * <p><strong>Thread-safety:</strong> The class itself is thread safe because of immutability. NOTE: All modification database operations should be put in transaction.</p>
 * 
 */
public class DatabasePaymentTermDAO implements PaymentTermDAO {

/**
 * <p><strong>Usage:</strong> Represents the connection name to obtain the db connection from DBConnectionFactory.&nbsp;Initialized in constructor, and never changed afterwards.</p>
 * <p><strong>Valid values:</strong> can not be null or empty string.</p>
 * 
 */
    private final String connectionName = null;

/**
 * <p><strong>Usage:</strong> Represents the DBConnectionFactory object to obtain the db connection.&nbsp; Initialized in constructor, and never changed afterwards.</p>
 * <p><strong>Valid values</strong>: can not be null.</p>
 * 
 */
    private final com.topcoder.db.connectionfactory.DBConnectionFactory connectionFactory = null;

/**
 * <p><strong>Usage:</strong> Constructor to load configuration values from the given namespace.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <p>1. use <em>ConfigManager</em> to load the following properties from the given namespace:</p>
 * <ul type="disc">
 * <li>&quot;<strong>connection_name</strong>&quot; property value, <strong>required,</strong> must be non-empty string.</li>
 * <li>&quot;<strong>of_namespace</strong>&quot; property value, <strong>required,</strong> must be non-empty string.</li>
 * <li>&quot;<strong>db_connection_factory_key</strong>&quot; property value, <strong>required,</strong> must be non-empty string.</li>
 * </ul>
 * 2. <em>create of = new ObjectFactory(new ConfigManagerSpecificationFactory(<strong>of_namespace</strong>))</em>.
 * <p>3. and then initialize <em>connectionFactory = (DBConnectionFactory) of.createObject(<strong>db_connection_factory_key</strong>)</em>; assign the <strong>connection_name</strong> property value to corresponding instance variable.</p>
 * 
 * 
 * @param namespace the namespace to load configuration values
 * @throws IllegalArgumentException if the argument is null or empty string
 * @throws CommonManagerConfigurationException if any configured value is invalid or any required value is missing, it is also used to wrap the exceptions from ConfigManager
 */
    public  DatabasePaymentTermDAO(String namespace) {        
        // your code here
    } 

/**
 * <p><strong>Usage:</strong> Constructor.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <ul type="disc">
 * <li>assign the arguments to the corresponding instance variables.</li>
 * </ul>
 * 
 * 
 * @param connectionFactory the DBConnectionFactory object to obtain the connection
 * @param connectionName the connection name
 * @throws IllegalArgumentException connectionFactory, connectionName is null, or connectionName is empty string
 */
    public  DatabasePaymentTermDAO(com.topcoder.db.connectionfactory.DBConnectionFactory connectionFactory, String connectionName) {        
        // your code here
    } 

/**
 * <strong>Usage:</strong> Add the given PaymentTerm in the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p>If this paymentTerm already exists in the persistence, (check if the id exists) throw DuplicatePaymentTermException.</p>
 * </li>
 * <li>
 * <p>Inserta new record for the corresponding PaymentTerm:</p>
 * <p>for all fields of the new record from payment_terms<em> </em>set the fields using getters of paymentTerm: getId, getCreationDate, getModificationDate, getCreationUser, getModificationUser, getTerm, getDescription, isActive.</p>
 * <p>Throw PaymentTermDAOException if there is an error while inserting the values.</p>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened.</p>
 * </li>
 * </ol>
 * 
 * 
 * @param paymentTerm the PaymentTerm to add
 * @throws PaymentTermDAOException if any other problem occurs while accessing the data store
 * @throws IllegalArgumentException  if the given PaymentTerm is null; creation user is null, empty string or length >64; description is null, empty or length >64; term <=0
 * @throws DuplicatePaymentTermException if PaymentTerm is already added
 */
    public void addPaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {        
        // your code here
    } 

/**
 * <strong>Usage:</strong> Update the given PaymentTerm in the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p>If this paymentTerm doesn't exist in the persistence, (check if the id exists) throw PaymentTermNotFoundException.</p>
 * </li>
 * <li>
 * <p>Insert into the corresponding PaymentTerm:</p>
 * <p>for all fields of the record from payment_terms<em> WHERE payment_terms.payment_terms_id = paymentTerm.getId()</em> update the fields using getters of paymentTerm: getId, getCreationDate, getModificationDate, getCreationUser, getModificationUser, getTerm, getDescription, isActive.</p>
 * <p>Throw PaymentTermDAOException if there is an error while inserting the values.</p>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened.</p>
 * </li>
 * </ol>
 * 
 * 
 * @param paymentTerm the PaymentTerm to update
 * @throws PaymentTermDAOException if PaymentTerm is not added yet or any other problem occurs while accessing the data store
 * @throws IllegalArgumentException  if the given PaymentTerm is null; Date and String attributes of PaymentTerm are null; String attributes are empty; description length >64; creation user length >64; modification user length >64; term <=0; id<=0
 * @throws PaymentTermNotFoundException if the PaymentTerm to update was not found in the data store
 */
    public void updatePaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {        
        // your code here
    } 

/**
 * <strong>Usage: </strong>Retrieve the PaymentTerm corresponding to given id from the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p>If this id doesn't exist in the persistence, throw PaymentTermNotFoundException.</p>
 * </li>
 * <li>
 * <p>Retrieve the corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened. Return the obtained PaymentTerm.</p>
 * </li>
 * </ol>
 * 
 * 
 * @return the PaymentTerm corresponding to given id
 * @param id the id of PaymentTerm to be retrieved
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if id<0
 * @throws PaymentTermNotFoundException if there is no PaymentTerm with the given id in the data store
 */
    public PaymentTerm retrievePaymentTerm(long id) throws PaymentTermDAOException {        
        return null;
    } 

/**
 * <strong>Usage: </strong>Retrieve the PaymentTerms corresponding to given ids from the data store. If nothing is found, return zero length array.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p>If any id from ids array doesn't exist in the persistence, throw PaymentTermNotFoundException.</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened. Return the obtained array of PaymentTerms.</p>
 * </li>
 * </ol>
 * 
 * 
 * @return the array with PaymentTerms corresponding to given ids
 * @param ids the array with ids of PaymentTerms to be retrieved
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if ids contains negative values
 * @throws PaymentTermNotFoundException if there is no PaymentTerm like one of the given ids in the data store
 */
    public PaymentTerm[] retrievePaymentTerms(long[] ids) throws CommonManagementException {        
        return new PaymentTerm[0];
    } 

/**
 * <p><strong>Usage: </strong>Returns an array with all the PaymentTerms within the datastore. If nothing is found, return zero length array.</p>
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p>Create an ids array with ids found in the payment_terms.</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened. Return the obtained array of PaymentTerms.</p>
 * </li>
 * </ol>
 * 
 * 
 * @return the arraw with all the PaymentTerms
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 */
    public PaymentTerm[] retrieveAllPaymentTerms() throws PaymentTermDAOException {        
        return new PaymentTerm[0];
    } 

/**
 * <strong>Usage: </strong>Retrieve an array with all the active PaymentTerms from the data store. If nothing is found, return zero length array.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p>Create an ids array with ids found in the payment_terms that correspond to condition WHERE payment_terms.active = 1.</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened. Return the obtained array of PaymentTerms.</p>
 * </li>
 * </ol>
 * 
 * 
 * @return the arraw with all the active PaymentTerms
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 */
    public PaymentTerm[] retrieveActivePaymentTerms() throws PaymentTermDAOException {        
        return new PaymentTerm[0];
    } 

/**
 * <strong>Usage:</strong> Get an array with recently created PaymentTerms with specified recent days from the data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found, return zero length array.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p><strong>if recentDays is -1</strong></p>
 * <ol>
 * <li>
 * <p>Create an ids array with ids found in the payment_terms.</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * </ol>
 * </li>
 * <li>
 * <p><strong>else</strong></p>
 * <ol>
 * <li>
 * <p>Create an ids array with ids found in the payment_terms that correspond to condition <em>WHERE current-date-time -payment_terms.creation_date&lt;=recentDays. </em>(convert all 3 values to milliseconds when compared into an array. NOTE: as the target database is Informix, developers can also choose to incorpate the date-comparing in the sql statement directly).</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * </ol>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened. Return the obtained array of PaymentTerms.</p>
 * </li>
 * </ol>
 * 
 * 
 * @return the array with recently created PaymentTerms with specified recent days
 * @param recentDays the nubmer of recent days
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
 */
    public PaymentTerm[] retrieveRecentlyCreatedPaymentTerms(int recentDays) throws PaymentTermDAOException {        
        return new PaymentTerm[0];
    } 

/**
 * <strong>Usage:</strong> Get an array with recently modified PaymentTerms with specified recent days from the data store. If recentDays is -1, it means all the recently requested users should be returned. If nothing is found, return zero length array.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li>
 * <p><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></p>
 * </li>
 * <li>
 * <p><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></p>
 * </li>
 * </ul>
 * <ol>
 * <li>
 * <p>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</p>
 * </li>
 * <li>
 * <p><strong>if recentDays is -1</strong></p>
 * <ol>
 * <li>
 * <p>Create an ids array with ids found in the payment_terms.</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * </ol>
 * </li>
 * <li>
 * <p><strong>else</strong></p>
 * <ol>
 * <li>
 * <p>Create an ids array with ids found in the payment_terms that correspond to condition <em>WHERE current-date-time -payment_terms.modification_date&lt;=recentDays. </em>(convert all 3 values to milliseconds when compared into an array. NOTE: as the target database is Informix, developers can also choose to incorpate the date-comparing in the sql statement directly).</p>
 * </li>
 * <li>
 * <p>Create an array of PaymentTerms with length equal to ids array length.</p>
 * </li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array retrieve tre corresponding PaymentTerm:</p>
 * <p><em>SELECT 1 FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error while retrieving the PaymentTerm.</p>
 * </li>
 * <li>
 * <p>Create a new PaymentTerm and for each attribute obtained from the previous operation, populate the PaymentTerm attributes using appropriate setters (setId, setCreationDate, setModificationDate, setCreationUser, setModificationUser, setTerm, setDescription, setActive).</p>
 * </li>
 * <li>
 * <p>Add the PaymentTerm to the array. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * </ol>
 * </li>
 * <li>
 * <p>Commit the changes to the database and close any resources opened. Return the obtained array of PaymentTerms.</p>
 * </li>
 * </ol>
 * 
 * 
 * @return the array with recently modified PaymentTerms with specified recent days
 * @param recentDays the nubmer of recent days
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if the recentDays argument is non-positive value and not -1
 */
    public PaymentTerm[] retrieveRecentlyModifiedPaymentTerms(int recentDays) throws PaymentTermDAOException {        
        return new PaymentTerm[0];
    } 

/**
 * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id from the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></li>
 * <li><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></li>
 * </ul>
 * <ol>
 * <li>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</li>
 * <li>If this id doesn't exist in the persistence, throw PaymentTermNotFoundException.</li>
 * <li>
 * <p>Remove the corresponding PaymentTerm:</p>
 * <p><em>DELETE FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error deleting the PaymentTerm.</p>
 * </li>
 * <li>Commit the changes to the database and close any resources opened.</li>
 * </ol>
 * 
 * 
 * @param id the id of PaymentTerm to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if id<0
 * @throws PaymentTermNotFoundException if there is no PaymentTerm with the given id in the data store
 */
    public void deletePaymentTerm(long id) throws PaymentTermDAOException {        
        // your code here
    } 

/**
 * <strong>Usage: </strong>Delete the PaymentTerm corresponding to given id of the provided PaymentTerm from the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></li>
 * <li><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></li>
 * </ul>
 * <ol>
 * <li>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</li>
 * <li>If this PaymentTerm doesn't exist in the persistence, throw PaymentTermNotFoundException.</li>
 * <li>
 * <p>Remove the PaymentTerm:</p>
 * <p><em>DELETE FROM payment_terms WHERE payment_terms.payment_terms_id = paymentTerm.getId();</em></p>
 * <p>Throw PaymentTermDAOException if there is an error deleting the PaymentTerm.</p>
 * </li>
 * <li>Commit the changes to the database and close any resources opened.</li>
 * </ol>
 * 
 * 
 * @param paymentTerm the PaymentTerm to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if id of PaymentTerm<0
 * @throws PaymentTermNotFoundException if the PaymentTerm to delete was not found in the data store
 */
    public void deletePaymentTerm(PaymentTerm paymentTerm) throws PaymentTermDAOException {        
        // your code here
    } 

/**
 * <strong>Usage: </strong>Delete the PaymentTerms corresponding to the given ids from the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></li>
 * <li><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></li>
 * </ul>
 * <ol>
 * <li>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</li>
 * <li>If any of the given ids doesn't exist in the persistence, throw PaymentTermNotFoundException.</li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> id from ids array remove the corresponding PaymentTerm:</p>
 * <p><em>DELETE FROM payment_terms WHERE payment_terms.payment_terms_id = id;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error deleting the PaymentTerm. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * <li>Commit the changes to the database and close any resources opened.</li>
 * </ol>
 * 
 * 
 * @param ids the array with ids of PaymentTerms to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if ids contains negative values
 * @throws PaymentTermNotFoundException if there is no PaymentTerm like one of the given ids in the data store
 */
    public void deletePaymentTerms(long[] ids) throws PaymentTermDAOException {        
        // your code here
    } 

/**
 * <strong>Usage: </strong>Delete the PaymentTerms corresponding to ids of given PaymentTerms from the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></li>
 * <li><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></li>
 * </ul>
 * <ol>
 * <li>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</li>
 * <li>If any of the given PaymentTerms id doesn't exist in the persistence, throw PaymentTermNotFoundException.</li>
 * <li>
 * <p><span style="text-decoration:underline">Foreach</span> paymentTerm from given paymentTerms array remove:</p>
 * <p><em>DELETE FROM payment_terms WHERE payment_terms.payment_terms_id = paymentTerm.getId();</em></p>
 * <p>Throw PaymentTermDAOException if there is an error deleting the PaymentTerm. <span style="text-decoration:underline">end foreach.</span></p>
 * </li>
 * <li>Commit the changes to the database and close any resources opened.</li>
 * </ol>
 * 
 * 
 * @param paymentTerms the array with PaymentTerms to be deleted
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 * @throws IllegalArgumentException if ids of PaymentTerms contains negative values
 * @throws PaymentTermNotFoundException if there is no PaymentTerm like one from the given paymentTerms in the data store
 */
    public void deletePaymentTerms(PaymentTerm[] paymentTerms) throws PaymentTermDAOException {        
        // your code here
    } 

/**
 * <strong>Usage: </strong>Delete all the PaymentTerms from the data store.
 * <p><strong>Implementation notes:</strong></p>
 * <ol>
 * <li>
 * <p><em><strong>Notes:</strong> </em></p>
 * <ul type="disc">
 * <li><em>Comments regarding SQL queries are simply for illustrative purposes. It is the responsability of the developer to create appropriate queries and to handle the efficiency of the query.</em></li>
 * <li><em>It is highly recommended that the developer create the necesssary SQL query strings in the constructor.&nbsp; The SQL queries are created dynamically based on the schema instance field, but since this field can only be set during construction, the necessary queries will remain the same throughout this instance's lifetime.</em></li>
 * </ul>
 * <ol>
 * <li>Create the DB Connection using the connectionFactory instance and disable autocommit.&nbsp; If any exceptions are thrown, they should be wrapped in PaymentTermDAOException.&nbsp; The developer should take care to properly manage the connection and the transaction during subsequent steps (for instance, if an exception occurs while interacting with the db, the transaction should be rolled back and the Connection should be closed).</li>
 * <li>
 * <p>Delete all PaymentTerms:</p>
 * <p><em>DELETE FROM payment_terms ;</em></p>
 * <p>Throw PaymentTermDAOException if there is an error deleting the PaymentTerms.</p>
 * </li>
 * <li>Commit the changes to the database and close any resources opened.</li>
 * </ol>
 * </li>
 * </ol>
 * 
 * 
 * @throws PaymentTermDAOException if a problem occurs while accessing the data store
 */
    public void deleteAllPaymentTerms() throws PaymentTermDAOException {        
        // your code here
    } 
 }
