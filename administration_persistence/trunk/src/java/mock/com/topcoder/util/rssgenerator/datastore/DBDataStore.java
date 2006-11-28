
package com.topcoder.util.rssgenerator.datastore;
import com.topcoder.util.rssgenerator.RSSPersistenceException;
import com.topcoder.util.rssgenerator.datastore.ConfigurationException;

/**
 * Purpose: This class implements the DataStore interface for databases. <p>Implementation: We use the DBConnectionFactory to connect to the database. We use the IDGenerator to generate IDs when required. We use the SearchBundle to do searching.</p> <p>Thread Safety: This class is not thread safe as it changes the state of the database. For example when one method of the class is updating a feed, another might be deleting it leading to inconsistent results for the user. (Note that due to the use of transactions, the database itself will not be inconsistent).&nbsp; To overcome this, we would need to declare all methods of this class synchronized. To avoid this unecessary expense for single threaded applications it is not done. Therefore applications wishing to use it in a multi-threaded scenario must use it in a syhnchronized way.</p>
 *
 */
public class DBDataStore implements com.topcoder.util.rssgenerator.DataStore {

/**
 * <p>Purpose: This is the object used to execute searches on search filters. It is frozen as the reference does not change after construction. It is initialized by the constructor. It is used in both findXXX methods.</p>
 *
 */
    private final com.topcoder.search.builder.SearchBundle searchBundle = null;

/**
 * <p>Purpose: This is the object used to generate IDs when required. It is frozen as the reference does not change after construction. It is initialized by the constructor. It is used everytime an ID is required during creation of an item/feed is needed.</p>
 *
 */
    //private final com.topcoder.util.idgenerator.IDGenerator idGenerator = null;

/**
 * <p>Purpose: This is the object used to get connections from the database. It is frozen as the reference does not change after construction. It is initialized by the constructor. It is used everytime a Connection is needed.</p>
 *
 */
    private final com.topcoder.db.connectionfactory.DBConnectionFactory connectionFactory = null;

/**
 * <p>Purpose: Stores the name of the default namespace. It is static as it is ocmmon to the class and frozen as it will never change. It is initiazlied to &quot;com.topcoder.util.rssgenerator.datastore&quot;</p>
 *
 */
    public static final String DEFAULT_NAMESPACE = "...";
/**
 *
 *
 */
    public com.topcoder.util.rssgenerator.datastore.SearchCriteriaImpl searchCriteriaImpl;

/**
 * <p>Purpose: Constructs this object from the default namespace.</p> <p>Args: None</p> <p>Implmentation: this(DEFAULT_NAMESPACE)</p> <p>Exceptions: None.</p>
 *
 */
    public  DBDataStore() {
        // your code here
    }

/**
 * <p>Purpose; Constructs this object from the given configuration namespace</p> <p>Args: namespace - The namespace to get the configuration properties. Must not be null or empty.</p> <p>Implementation: As shown in the CS, read the three properties to get the three required objects. Assign them to the member variables.</p> <p>Exceptions: ConfigurationException - If any error occurs during configuration</p> <p>IllegalAgrumentException - If name is null or empty.</p>
 *
 *
 * @param namespace
 */
    public  DBDataStore(String namespace) {
        // your code here
    }

/**
 * <p>Purpose: Creates a feed in the data store. Note that if a feed with a null or empty ID is passed in, the data store&nbsp; must generate a suitable ID and set the feed's ID field with the generated ID.</p> <p>Args: feed - (in) The feed to be created. Must not be null. (out) The feed with its ID set, in case it was originally null or empty.</p> <p>Implementation:</p> <p>1. Get a connection.</p> <p>2. Begin a transaction.</p> <p>3. Use the SQL shown in the CS to write the feed.</p> <p>4. Close the transaction and connection.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If feed is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param feed
 */
    public void createFeed(com.topcoder.util.rssgenerator.RSSFeed feed) {
        // your code here
    }

/**
 * <p>Purpose: Retrieves feeds from the data store</p> <p>Args: searchCriteria - The search criteria according to which feeds must be retrieved. Must not be null.</p> <p>Implementation:</p> <p>1. Use the search bundle with the context from the CS.</p> <p>2. Get the feed IDs from the CustomResultSet.</p> <p>3. Use the SQL shown in the CS to get the feed obects and its item.</p> <p>4. Return the feeds.</p> <p>Returns: An array of feeds satisfying the given search crieteria.</p> <p>Exceptions: IllegalArgumentException - If searchCriteria is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param searchCriteria
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSFeed[] findFeeds(com.topcoder.util.rssgenerator.SearchCriteria searchCriteria) {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Updates a feed in the data store.</p> <p>Args: feed - The feed to be updated. Must not be null.</p> <p>Implementation:</p> <p>1. Get a connection.</p> <p>2. Begin a transaction.</p> <p>3. Use the SQL shown in the CS to update the feed.</p> <p>4. Close the transaction and connection.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If feed is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param feed
 */
    public void updateFeed(com.topcoder.util.rssgenerator.RSSFeed feed) {
        // your code here
    }

/**
 * <p>Purpose: Deletes a feed in the data store.</p> <p>Args: feed - The feed to be deleted. Must not be null.</p> <p>Implementation:</p> <p>1. Get a connection.</p> <p>2. Begin a transaction.</p> <p>3. Use the SQL shown in the CS to delete the feed.</p> <p>4. Close the transaction and connection.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If feed is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param feed
 */
    public void deleteFeed(com.topcoder.util.rssgenerator.RSSFeed feed) {
        // your code here
    }

/**
 * <p>Purpose: Creates an item in the data store. Note that if an item with a null or empty ID is passed in, the data store&nbsp; must generate a suitable ID and set the item's ID field with the generated ID.</p> <p>Args: item - (in) The item to be created. Must not be null. (out) The item with its ID set, in case it was originally null or empty.</p> <p>Implementation:</p> <p>1. Get a connection.</p> <p>2. Begin a transaction.</p> <p>3. Use the SQL shown in the CS to write the item.</p> <p>4. Close the transaction and connection.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If item is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param item
 */
    public void createItem(com.topcoder.util.rssgenerator.RSSItem item) {
        // your code here
    }

/**
 * <p>Purpose: Retrieves items from the data store</p> <p>Args: searchCriteria - The search criteria according to which items must be retrieved. Must not be null.</p> <p>1. Use the search bundle with the context from the CS.</p> <p>2. Get the item IDs from the CustomResultSet.</p> <p>3. Use the SQL shown in the CS to get the item objects.</p> <p>4. Return the feeds.</p> <p>Returns: An array of items satisfying the given search crieteria.</p> <p>Exceptions: IllegalArgumentException - If searchCriteria is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param searchCriteria
 * @return
 */
    public com.topcoder.util.rssgenerator.RSSItem[] findItems(com.topcoder.util.rssgenerator.SearchCriteria searchCriteria) {
        // your code here
        return null;
    }

/**
 * <p>Purpose: Updates an item in the data store.</p> <p>Args: item - The item to be updated. Must not be null.</p> <p>Implementation:</p> <p>1. Get a connection.</p> <p>2. Begin a transaction.</p> <p>3. Use the SQL shown in the CS to update the item.</p> <p>4. Close the transaction and connection.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If item is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param item
 */
    public void updateItem(com.topcoder.util.rssgenerator.RSSItem item) {
        // your code here
    }

/**
 * <p>Purpose: Deletes an item in the data store.</p> <p>Args: item - The item to be deleted. Must not be null.</p> <p>Implementation:</p> <p>1. Get a connection.</p> <p>2. Begin a transaction.</p> <p>3. Use the SQL shown in the CS to delete the feed.</p> <p>4. Close the transaction and connection.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If item is null.</p> <p>RSSPersistenceException - If any other error occurs.</p>
 *
 *
 * @param item
 */
    public void deleteItem(com.topcoder.util.rssgenerator.RSSItem item) {
        // your code here
    }
 }
