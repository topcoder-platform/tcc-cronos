
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface defines the contract for a data store to store feeds and items. Tehe contract consists of the standard create, update, retrieve and delete methods - only in this case there is a find instead of a retrieve method.
 * <p>Implementation: Implementations would use some form of persistence, XML Flat files or Databases to persist the feeds and items. They would also have to be capable of searching these stores for particular feeds and items.</p>
 * <p>Thread Safety: Implementations are not required to be thread safe.</p>
 * 
 * 
 */
public interface DataStore {

/**
 * <p>Purpose: Creates a feed in the data store. Note that if a feed with a null or empty ID is passed in, the data store&nbsp; must generate a suitable ID and set the feed's ID field with the generated ID.</p>
 * <p>Args: feed - (in) The feed to be created. Must not be null. (out) The feed with its ID set, in case it was originally null or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If feed is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param feed 
 */
    public void createFeed(com.topcoder.util.rssgenerator.RSSFeed feed);
/**
 * <p>Purpose: Retrieves feeds from the data store</p>
 * <p>Args: searchCriteria - The search criteria according to which feeds must be retrieved. Must not be null.</p>
 * <p>Returns: An array of feeds satisfying the given search crieteria.</p>
 * <p>Exceptions: IllegalArgumentException - If searchCriteria is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param searchCriteria 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSFeed[] findFeeds(com.topcoder.util.rssgenerator.SearchCriteria searchCriteria);
/**
 * <p>Purpose: Updates a feed in the data store.</p>
 * <p>Args: feed - The feed to be updated. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If feed is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param feed 
 */
    public void updateFeed(com.topcoder.util.rssgenerator.RSSFeed feed);
/**
 * <p>Purpose: Deletes a feed in the data store.</p>
 * <p>Args: feed - The feed to be deleted. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If feed is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param feed 
 */
    public void deleteFeed(com.topcoder.util.rssgenerator.RSSFeed feed);
/**
 * <p>Purpose: Creates an item in the data store. Note that if an item with a null or empty ID is passed in, the data store&nbsp; must generate a suitable ID and set the item's ID field with the generated ID.</p>
 * <p>Args: item - (in) The item to be created. Must not be null. (out) The item with its ID set, in case it was originally null or empty.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param item 
 */
    public void createItem(com.topcoder.util.rssgenerator.RSSItem item);
/**
 * <p>Purpose: Retrieves items from the data store</p>
 * <p>Args: searchCriteria - The search criteria according to which items must be retrieved. Must not be null.</p>
 * <p>Returns: An array of items satisfying the given search crieteria.</p>
 * <p>Exceptions: IllegalArgumentException - If searchCriteria is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * <p></p>
 * 
 * 
 * @param searchCriteria 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSItem[] findItems(com.topcoder.util.rssgenerator.SearchCriteria searchCriteria);
/**
 * <p>Purpose: Updates an item in the data store.</p>
 * <p>Args: item - The item to be updated. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param item 
 */
    public void updateItem(com.topcoder.util.rssgenerator.RSSItem item);
/**
 * <p>Purpose: Deletes an item in the data store.</p>
 * <p>Args: item - The item to be deleted. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * <p>RSSPersistenceException - If any other error occurs.</p>
 * 
 * 
 * @param item 
 */
    public void deleteItem(com.topcoder.util.rssgenerator.RSSItem item);

}


