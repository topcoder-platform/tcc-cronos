
package com.topcoder.util.auction;
import com.topcoder.util.auction.Bid;
/**
 * Purpose: This interface provides the contract for the properties that an Auction must satisfy.
 * <p>Implementation: Implementations may use simple member variables to implement the properties.</p>
 * <p>Thread Safety: It is required that implementations be thread safe.</p>
 * 
 */
public interface Auction {
/**
 * <p>Purpose: Returns the ID of the auction.</p>
 * <p>Args: None.</p>
 * <p>Returns: The ID of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public Long getId();
/**
 * <p>Purpose: Returns the summary of the auction.</p>
 * <p>Args: None.</p>
 * <p>Returns: The summary of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getSummary();
/**
 * <p>Purpose: Returns the description of the auction.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The description of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public String getDescription();
/**
 * <p>Purpose: Returns the item count of the auction.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The item count of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public int getItemCount();
/**
 * <p>Purpose: Returns the minimum bid of the auction.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The minimum bid of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public int getMinimumBid();
/**
 * <p>Purpose: Returns the start date of the auction.</p>
 * <p>Args: None.</p>
 * <p>Returns: The start date of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public java.util.Date getStartDate();
/**
 * <p>Purpose: Returns the end date of the auction.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The end date of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public java.util.Date getEndDate();
/**
 * <p>Purpose: Returns the bids of the auction.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Return a copy of the member array.</p>
 * <p>Returns: The bids of the auction.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.auction.Bid[] getBids();
}


