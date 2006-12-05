
package com.topcoder.util.auction;
import com.topcoder.util.auction.AuctionPersistenceException;
/**
 * Purpose: This interface defines the contract for persisting auction objects. It allows for plugging in different persistence implementations. The contract consists of CRUD methods for an Auction. It also has an extra update method to only update the bids of an auction. Apart from these, two find methods allow searching the persistence medium for auctions that occur in particular date ranges and also for auctions having bids by particular bidders.
 * <p>Implementation: Implementations would use some kind of persistence medium (flat files, or databases) to store auctions and bids. Care must be taken by implementations when applying CRUD operations on an Auction object to ensure that the Bids of the object are also correspondingly persisted.</p>
 * <p>Thread-Safety: Since the underlying medium would change, enforcing thread-safety would lead to performance losses. Therefore implementations need not be thread safe and users of this interface will do any needed synchronization themselves.</p>
 * 
 */
public interface AuctionPersistence {
/**
 * <p>Purpose: Creates a new Auction in the underlying persistence and returns the created Auction object. If the given Auction object has a null auctionId, a unique auctionId is automatically generated and the returned Auction object contains the generated Id.</p>
 * <p>Args: auction - The Auction object to be created. Must not be null.</p>
 * <p>Returns: The Auction object that has been created in the underlying persistence.</p>
 * <p>Exceptions: AuctionPersistenceException - If the creation of the Auction fails.</p>
 * <p>IllegalArgumentException - If the given argument is null.</p>
 * <p></p>
 * 
 * 
 * @param auction 
 * @return 
 */
    public com.topcoder.util.auction.Auction createAuction(com.topcoder.util.auction.Auction auction);
/**
 * <p>Purpose: Gets an Auction from underlying persistence.</p>
 * <p>Args: auctionId - The ID of the Auction object to be retrieved.</p>
 * <p>Returns: The Auction object with the given ID.</p>
 * <p>Exceptions: AuctionPersistenceException - If the retrieval of the Auction fails.</p>
 * 
 * 
 * @param auctionId 
 * @return 
 */
    public com.topcoder.util.auction.Auction getAuction(long auctionId);
/**
 * <p>Purpose: Updates the Auction in the underlying persistence.</p>
 * <p>Args: auction - The Auction object to be updated. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: AuctionPersistenceException - If the creation of the Auction fails.</p>
 * <p>IllegalArgumentException - If the given argument is null.</p>
 * <p></p>
 * 
 * 
 * @param auction 
 */
    public void updateAuction(com.topcoder.util.auction.Auction auction);
/**
 * <p>Purpose: Updates the Bids of the Auction in the underlying persistence.</p>
 * <p>Args: auction - The Auction object whose bids are to be updated. Must not be null.</p>
 * <p>bids - The new set of bids of this auction. Must not be null.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: AuctionPersistenceException - If the update of the bds of the Auction fails.</p>
 * <p>IllegalArgumentException - If any of the given arguments is null.</p>
 * 
 * 
 * @param auctionId 
 * @param bids 
 */
    public void updateBids(long auctionId, com.topcoder.util.auction.Bid[] bids);
/**
 * <p>Purpose: Deletes the Auction from the underlying persistence.</p>
 * <p>Args: auctionId - The ID of the Auction object to be deleted.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: AuctionPersistenceException - If the deletion of the Auction fails.</p>
 * <p></p>
 * 
 * 
 * @param auctionId 
 */
    public void deleteAuction(long auctionId);
/**
 * <p>Purpose: Retrieves the Auction objects from the underlying persistence whose start and end times lie within the given range.</p>
 * <p>Args: startingBy - The minimum starting date. For all returned auctions, auction.startDate &gt;= startingBy. Possibly null indicating that this criterion must be ignored.</p>
 * <p>endingAfter - The maximum ending date. For all returned auctions, auction.endDate &lt; endingAfter. Possibly null indicating that this criterion must be ignored.</p>
 * <p>Returns: An array of auction objects satisfying the given date criteria.</p>
 * <p>Exceptions: AuctionPersistenceException - If the search for the Auction fails.</p>
 * 
 * 
 * @param startingBy 
 * @param endingAfter 
 * @return 
 */
    public com.topcoder.util.auction.Auction[] findAuctionsByDate(java.util.Date startingBy, java.util.Date endingAfter);
/**
 * <p>Purpose: Retrieves the Auction objects from the underlying persistence whose end times lie within the given date and which has a bid by the given bidder.</p>
 * <p>Args: bidderId - The ID of the bidder who must have a bid on the returned auctions.</p>
 * <p>endingAfter - The maximum ending date. For all returned auctions, auction.endDate &lt; endingAfter. Possibly null indicating that this criterion must be ignored.</p>
 * <p>Returns: An array of auction objects satisfying the given date and bidder criteria.</p>
 * <p>Exceptions: AuctionPersistenceException - If the search for the Auction fails.</p>
 * 
 * 
 * @param bidderId 
 * @param endingAfter 
 * @return 
 */
    public com.topcoder.util.auction.Auction[] findAuctionsByBidder(long bidderId, java.util.Date endingAfter);
}


