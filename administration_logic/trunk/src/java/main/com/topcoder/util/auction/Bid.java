
package com.topcoder.util.auction;
/**
 * Purpose: This interface provides the contract for the properties that a bid must satisfy.
 * <p>Implementation: Implementations may use simple member variables to implement the properties.</p>
 * <p>Thread Safety: It is required that implementations be thread safe.</p>
 * 
 */
public interface Bid {
/**
 * <p>Purpose: Returns the bidder ID of the bid.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The bidder ID of the bid.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public long getBidderId();
/**
 * <p>Purpose: Returns the effective amount of the bid.</p>
 * <p>Args: None.</p>
 * <p>Returns: The effective amount of the bid.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public Integer getEffectiveAmount();
/**
 * <p>Purpose: Returns the max amount of the bid.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The max amount of the bid.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public int getMaxAmount();
/**
 * <p>Purpose: Returns the timestamp of the bid.</p>
 * <p>Args: None.</p>
 * <p>mplementation: Simply return the member variable.</p>
 * <p>Returns: The timestamp of the bid.</p>
 * <p>Ecxeptions: None.</p>
 * <p></p>
 * 
 * 
 * @return 
 */
    public java.util.Date getTimestamp();
}


