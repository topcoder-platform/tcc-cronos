
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSText interface by decorating over an RSSObject instance. <p>Implementation: This class extends the BaseRSSObjectDecorator class which takes care of implementing all of the RSSObject functions. The two extra methods of the RSSText interface are implemented by decorating over the &quot;type&quot; attribute. CS 1.3.1 may be referred to see how this decoration takes place.</p> <p>Thread Safety: This class is not thread safe depite not having a member variable, as its base class is mutable.</p>
 *
 */
public class RSSTextImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSText {
/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSText interfaces will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 *
 */
    public  RSSTextImpl() {
        // your code here
    }

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 *
 *
 * @param object
 */
    public  RSSTextImpl(com.topcoder.util.rssgenerator.RSSObject object) {
        // your code here
    }

/**
 * <p>Purpose: This method returns the type of the text of this RSSText.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:type&quot;.</p> <p>Returns: The type of the text of this RSSText.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @return
 */
    public String getType() {
        // your code here
        return null;
    }

/**
 * <p>Purpose: This method sets the type of the text of this RSSText.</p> <p>Args: type - The type of this text. Possibly null indicating the property should be removed or empty.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:type&quot;.</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 *
 *
 * @param type
 */
    public void setType(String type) {
        // your code here
    }
 }
