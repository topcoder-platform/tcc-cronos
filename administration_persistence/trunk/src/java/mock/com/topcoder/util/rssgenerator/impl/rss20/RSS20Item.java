
package com.topcoder.util.rssgenerator.impl.rss20;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the RSSItemImpl class to add properties specific to RSS 2.0 <p>Implementation: We decorate over some more properties to provide objects specific to this format. We extend RSSItemImpl</p> <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 * 
 */
public class RSS20Item extends com.topcoder.util.rssgenerator.impl.RSSItemImpl {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSItem interface or its sub-interfaces will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 * 
 */
    public  RSS20Item() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * 
 * @param object 
 */
    public  RSS20Item(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Gets the comments URI of the item.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:comments&quot;</p> <p>Returns: The comments URI of the feed.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getComments() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Sets the comments URI of the item.</p> <p>Args: comments - The comments URI to set. Possibly null</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:comments&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param comments 
 */
    public void setComments(java.net.URI comments) {        
        // your code here
    } 
 }
