
package com.topcoder.util.rssgenerator.impl.atom10;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the RSSTextImpl class to provide an Atom 1.0 conent <p>Implementation: We decorate over some more properties to provide a content object.</p> <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 * 
 */
public class Atom10Content extends com.topcoder.util.rssgenerator.impl.RSSTextImpl {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of this class will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 * 
 */
    public  Atom10Content() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * 
 * @param object 
 */
    public  Atom10Content(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Gets the source of the content.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:source&quot;</p> <p>Returns: The source of this content.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getSource() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Sets the source of the content.</p> <p>Args: source - The source of this content to be set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:source&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param source 
 */
    public void setSource(java.net.URI source) {        
        // your code here
    } 
 }
