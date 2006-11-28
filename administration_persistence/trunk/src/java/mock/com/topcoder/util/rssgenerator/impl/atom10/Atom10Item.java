
package com.topcoder.util.rssgenerator.impl.atom10;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the RSSItemImpl class to add properties specific to Atom 1.0 <p>Implementation: We decorate over some more properties to provide objects specific to this format. We extend RSSItemImpl</p> <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 * 
 */
public class Atom10Item extends com.topcoder.util.rssgenerator.impl.RSSItemImpl {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of this class will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 * 
 */
    public  Atom10Item() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p> <p></p>
 * 
 * 
 * @param object 
 */
    public  Atom10Item(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Gets the contributors of the item.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p> <p>Returns: The contributors of this item.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSEntity[] getContributors() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the content of the item.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:content&quot;</p> <p>Returns: The content of this item.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.impl.atom10.Atom10Content getContent() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Sets the contributors of the item.</p> <p>Args: contributors - The contributors of this item. Posisbly null. Individual elements must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If contributors' element&nbsp; is null.</p> <p></p>
 * 
 * 
 * @param contributors 
 */
    public void setContributors(com.topcoder.util.rssgenerator.RSSEntity[] contributors) {        
        // your code here
    } 

/**
 * <p>Purpose: Adds to the contributors of the feed.</p> <p>Args: contributors - The contributorto add. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If contributor is null.</p>
 * 
 * 
 * @param contributor 
 */
    public void addContributor(com.topcoder.util.rssgenerator.RSSEntity contributor) {        
        // your code here
    } 

/**
 * <p>Purpose: removes from the contributors of the item.</p> <p>Args: contributors - The contributor to remove. Must not be null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p>IllegalArgumentException - If contributor is null.</p>
 * 
 * 
 * @param contributor 
 */
    public void removeContributor(com.topcoder.util.rssgenerator.RSSEntity contributor) {        
        // your code here
    } 

/**
 * <p>Purpose: Clears the contributors of the item.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 */
    public void clearContributors() {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the content of the item.</p> <p>Args: content - The content to set. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:content&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param content 
 */
    public void setContent(com.topcoder.util.rssgenerator.impl.atom10.Atom10Content content) {        
        // your code here
    } 
 }
