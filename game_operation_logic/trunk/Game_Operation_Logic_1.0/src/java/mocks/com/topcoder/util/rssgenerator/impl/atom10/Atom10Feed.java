
package com.topcoder.util.rssgenerator.impl.atom10;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the RSSFeedImpl class to add properties specific to Atom 1.0
 * <p>Implementation: We decorate over some more properties to provide objects specific to this format. We extend RSSFeedImpl</p>
 * <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 * 
 * @poseidon-object-id [Im4ae9c223m10e2c2b0f00mm4e3e]
 */
public class Atom10Feed extends com.topcoder.util.rssgenerator.impl.RSSFeedImpl {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of this class will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm41d2]
 */
    public  Atom10Feed() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm41ba]
 * @param object 
 */
    public  Atom10Feed(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Gets the contributors of the feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p>
 * <p>Returns: The contributors of this feed.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm41a2]
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSEntity[] getContributors() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the subtitle of the feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:subtitle&quot;</p>
 * <p>Returns: The subtitle of this feed.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm418a]
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSText getSubtitle() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Sets the contributors of the feed.</p>
 * <p>Args: contributors - The contributors of this feed. Posisbly null. Individual elements must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * <p>IllegalArgumentException - If contributors' element&nbsp; is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm4172]
 * @param contributors 
 */
    public void setContributors(com.topcoder.util.rssgenerator.RSSEntity[] contributors) {        
        // your code here
    } 

/**
 * <p>Purpose: Adds to the contributors of the feed.</p>
 * <p>Args: contributors - The contributorto add. Must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * <p>IllegalArgumentException - If contributor is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm415a]
 * @param contributor 
 */
    public void addContributor(com.topcoder.util.rssgenerator.RSSEntity contributor) {        
        // your code here
    } 

/**
 * <p>Purpose: removes from the contributors of the feed.</p>
 * <p>Args: contributors - The contributor to remove. Must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * <p>IllegalArgumentException - If contributor is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm40cb]
 * @param contributor 
 */
    public void removeContributor(com.topcoder.util.rssgenerator.RSSEntity contributor) {        
        // your code here
    } 

/**
 * <p>Purpose: Clears the contributors of the feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:contributor&quot;</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm408b]
 */
    public void clearContributors() {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the subtitle of the feed.</p>
 * <p>Args: subtitle - The subtitle to set. Possibly null or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:subtitle&quot;</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [I45cbadbcm10e31f94a38mm4066]
 * @param subtitle 
 */
    public void setSubtitle(com.topcoder.util.rssgenerator.RSSText subtitle) {        
        // your code here
    } 
 }
