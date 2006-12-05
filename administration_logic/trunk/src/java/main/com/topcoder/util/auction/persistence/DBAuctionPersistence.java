
package com.topcoder.util.auction.persistence;
import com.topcoder.util.auction.Auction;
import com.topcoder.util.auction.AuctionPersistenceException;
import com.topcoder.util.auction.Bid;
import com.topcoder.util.auction.ConfigurationException;
import com.topcoder.util.config.ConfigManager;

/**
 * Purpose: Implements the AuctionPersistence contract using a DB. The database schema and the SQL queries used to accomplish the persistence tasks may be seen in the algorithms section of the component specification.
 * <p>Implementation: We use a DB Connection Factory to get a connection and an IDGEnerator to generate IDs when required. Please refer to the sequence diagrams for an understanding of how the methods of this class work.</p>
 * <p>Thread -safety : This class is not thread safe as it modifies underlying data.</p>
 * 
 * This class also uses
 * AuctionImpl and BidImpl
 */
public class DBAuctionPersistence implements com.topcoder.util.auction.AuctionPersistence {


/**
 * <p>Purpose: No-args constructor. Constructs this object from the default namespace.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call this(DEFAULT_NAMESPACE)</p>
 * <p>Exceptions: ConfigurationException - thrown by DBAuctionPersistence(namespace)</p>
 * <p></p>
 * 
 */
    public  DBAuctionPersistence() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object from properties in the given namepsace.</p>
 * <p>Args: namespace - The name of the namespace from which properties are to be read. Must not be null or empty.</p>
 * <p>Implementation: Refer to the CS for the structure of properties.</p>
 * <p>1. Get the db_conection_factory property to create an object of DBConnectionFactoryImpl.</p>
 * <p>2. Get the id_generator_bame to create an ID generator from the IDGeneratorFactory()</p>
 * <p>If any properties are missing throw a ConfigurationException or any other exception occurs.</p>
 * <p>Exceptions:</p>
 * <p>IllegaArgumentException - If the given argument is null.</p>
 * <p>ConfigurationException - If any other error occurs.</p>
 * 
 * 
 * @param namespace 
 */
    public  DBAuctionPersistence(String namespace) {        
        // your code here
    } 

/**
 * <p>Purpose: Creates a new Auction in the underlying persistence and returns the created Auction object. If the given Auction object has a null auctionId, a unique auctionId is automatically generated and the returned Auction object contains the generated Id.</p>
 * <p>Args: auction - The Auction object to be created. Must not be null.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>If no ID is provided generate one using the ID Generator.</p>
 * <p>Get a connection.</p>
 * <p>Begin a transaction.</p>
 * <p>Persist the auction and its bids (see CS for SQL)</p>
 * <p>Commit transaction.</p>
 * <p>Return the created Auction object (with the genrated ID if any)</p>
 * <p>Wrap any exception in AuctionPersistenceException and rethrow it.</p>
 * <p>Returns: The Auction object that has been created in the underlying persistence.</p>
 * <p>Exceptions: AuctionPersistenceException - If the creation of the Auction fails.</p>
 * <p>IllegalArgumentException - If the given argument is null.</p>
 * <p></p>
 * 
 * 
 * @param auction 
 * @return 
 */
    public com.topcoder.util.auction.Auction createAuction(com.topcoder.util.auction.Auction auction) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets an Auction from underlying persistence.</p>
 * <p>Args: auctionId - The ID of the Auction object to be retrieved.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>Get a connection.</p>
 * <p>Query for the object with this auction id. (see CS for SQL)</p>
 * <p>Return the retrieved Auction object.</p>
 * <p>If no auction is retrieved thrown an AuctionPersistenceException.</p>
 * <p>Wrap any exception in AuctionPersistenceException and rethrow it.</p>
 * <p>Returns: The Auction object with the given ID.</p>
 * <p>Exceptions: AuctionPersistenceException - If the retrieval of the Auction fails.</p>
 * <p></p>
 * 
 * 
 * @param auctionId 
 * @return 
 */
    public com.topcoder.util.auction.Auction getAuction(long auctionId) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Updates the Auction in the underlying persistence.</p>
 * <p>Args: auction - The Auction object to be updated. Must not be null.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>Get a connection.</p>
 * <p>Begin a transaction.</p>
 * <p>Update the auction and its bids (see CS for SQL)</p>
 * <p>Commit transaction.</p>
 * <p>Wrap any exception in AuctionPersistenceException and rethrow it.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: AuctionPersistenceException - If the creation of the Auction fails.</p>
 * <p>IllegalArgumentException - If the given argument is null.</p>
 * <p></p>
 * 
 * 
 * @param auction 
 */
    public void updateAuction(com.topcoder.util.auction.Auction auction) {        
        // your code here
    } 

/**
 * <p>Purpose: Updates the Bids of the Auction in the underlying persistence.</p>
 * <p>Args: auction - The Auction object whose bids are to be updated. Must not be null.</p>
 * <p>bids - The new set of bids of this auction. Must not be null.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>Get a connection.</p>
 * <p>Begin a transaction.</p>
 * <p>Update the bids (see CS for SQL)</p>
 * <p>Commit transaction.</p>
 * <p>Wrap any exception in AuctionPersistenceException and rethrow it.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: AuctionPersistenceException - If the update of the bds of the Auction fails.</p>
 * <p>IllegalArgumentException - If any of the given arguments is null.</p>
 * <p></p>
 * 
 * 
 * @param auctionId 
 * @param bids 
 */
    public void updateBids(long auctionId, com.topcoder.util.auction.Bid[] bids) {        
        // your code here
    } 

/**
 * <p>Purpose: Deletes the Auction from the underlying persistence.</p>
 * <p>Args: auctionId - The ID of the Auction object to be deleted.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>Get a connection.</p>
 * <p>Begin a transaction.</p>
 * <p>Delete the auction and its bids (see CS for SQL)</p>
 * <p>Commit transaction.</p>
 * <p>Wrap any exception in AuctionPersistenceEception and rethrow it.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: AuctionPersistenceException - If the deletion of the Auction fails.</p>
 * <p></p>
 * 
 * 
 * @param auctionId 
 */
    public void deleteAuction(long auctionId) {        
        // your code here
    } 

/**
 * <p>Purpose: Retrieves the Auction objects from the underlying persistence whose start and end times lie within the given range.</p>
 * <p>Args: startingBy - The minimum starting date. For all returned auctions, auction.startDate &gt;= startingBy. Possibly null indicating that this criterion must be ignored.</p>
 * <p>endingAfter - The maximum ending date. For all returned auctions, auction.endDate &lt; endingAfter. Possibly null indicating that this criterion must be ignored.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>Get a connection.</p>
 * <p>Query for the auctions satisfying the criteria (see CS for SQL)</p>
 * <p>For each auction, get its bids and make an AcuctionImpl object.</p>
 * <p>Return the array of created objects.</p>
 * <p>Wrap any exception in AuctionPersistenceException and rethrow it.</p>
 * <p>Returns: An array of auction objects satisfying the given date criteria.</p>
 * <p>Exceptions: AuctionPersistenceException - If the search for the Auction fails.</p>
 * <p></p>
 * 
 * 
 * @param startingBy 
 * @param endingAfter 
 * @return 
 */
    public com.topcoder.util.auction.Auction[] findAuctionsByDate(java.util.Date startingBy, java.util.Date endingAfter) {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Retrieves the Auction objects from the underlying persistence whose end times lie within the given date and which has a bid by the given bidder.</p>
 * <p>Args: bidderId - The ID of the bidder who must have a bid on the returned auctions.</p>
 * <p>endingAfter - The maximum ending date. For all returned auctions, auction.endDate &lt; endingAfter. Possibly null indicating that this criterion must be ignored.</p>
 * <p>Implementation: Also refer to the CS for SQL queries and the sequence diagram.</p>
 * <p>Get a connection.</p>
 * <p>Query for the auctions satisfying the criteria (see CS for SQL)</p>
 * <p>For each auction, get its bids and make an AuctionImpl object.</p>
 * <p>Return the array of created objects.</p>
 * <p>Wrap any exception in AuctionPersistenceException and rethrow it.</p>
 * <p>Returns: An array of auction objects satisfying the given date and bidder criteria.</p>
 * <p>Exceptions: AuctionPersistenceException - If the search for the Auction fails.</p>
 * 
 * 
 * @param bidderId 
 * @param endingAfter 
 * @return 
 */
    public com.topcoder.util.auction.Auction[] findAuctionsByBidder(long bidderId, java.util.Date endingAfter) {        
        // your code here
        return null;
    } 
}