
package com.topcoder.util.auction;

/**
 * Purpose: This exception is thrown by the AuctionStrategy to indicate that a bid cannot be processed as it is illegal.
 * <p>Implementation: Extends the AuctionException class and does not add any variables.</p>
 * <p>Thread Safety: This class is thread safe as it has no state.</p>
 * 
 */
public class IllegalBidException extends com.topcoder.util.auction.AuctionException {

/**
 * <p>Purpose: Constructs this exception with a null message and cause.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super()</p>
 * <p>Exceptions: None.</p>
 * <p></p>
 * 
 */
    public  IllegalBidException() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with an error message and null cause.</p>
 * <p>Args: message - The error message. Possibly null or empty.</p>
 * <p>Implementation: Simply call super(message)</p>
 * <p>Exceptions: None.</p>
 * <p></p>
 * 
 * 
 * @param message 
 */
    public  IllegalBidException(String message) {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this exception with an error message and cause.</p>
 * <p>Args: message - The error message. Possibly null or empty.</p>
 * <p>cause - The cause of this exception. Possibly null.</p>
 * <p>Implementation: Simply call super(message,cause)</p>
 * <p>Exceptions: None.</p>
 * <p></p>
 * 
 * 
 * @param message 
 * @param cause 
 */
    public  IllegalBidException(String message, Throwable cause) {        
        // your code here
    } 
 }
