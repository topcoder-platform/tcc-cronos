
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSItem interface by decorating over an RSSObject instance.
 * <p>Implementation: It extends the RSSBaseItemImpl class and hence has to implement only the two methods defined in the RSSItem interface. Both of them are implemented by decorating over a &quot;sourceId&quot; child element. Refer to CS 1.3.1 to see how the decoration is done.</p>
 * <p>Thread Safety: This class is not thread safe despite having no members, as its base class is not thread safe.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5c8a]
 */
public class RSSItemImpl extends com.topcoder.util.rssgenerator.impl.RSSBaseItemImpl implements com.topcoder.util.rssgenerator.RSSItem {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSItem interface or its sub-interfaces will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5c76]
 */
    public  RSSItemImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5c5e]
 * @param object 
 */
    public  RSSItemImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the ID of the source feed of this item.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:sourceId&quot;.</p>
 * <p>Returns: The ID of the source feed of this item.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5c46]
 * @return 
 */
    public String getSourceId() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the ID of the source feed of this item.</p>
 * <p>Args: id - The source ID of this item. Possibly null indicating no source ID or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:sourceId&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5bec]
 * @param id 
 */
    public void setSourceId(String id) {        
        // your code here
    } 
 }
