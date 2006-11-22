
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSLink contract by decorating over an RSSObject instance.
 * <p>Implementation: It extends the BaseRSSObjectDecorator class and only implements the methods specific to the RSSLink interface. They are implemented by decorating over the attributes of RSSObject as detailed in the method docs. Also refer to CS 1.3.1 to see how decoration takes place.</p>
 * <p>Thread Safety: This class is not thread safe despite having no members, as its base class is mutable.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5662]
 */
public class RSSLinkImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSLink {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSLink interfaces will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm564e]
 */
    public  RSSLinkImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5636]
 * @param object 
 */
    public  RSSLinkImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the URL where this RSSLink points to.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:href&quot;.</p>
 * <p>Returns: The URL where this RSSLink points to.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm561e]
 * @return 
 */
    public java.net.URI getLink() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the relationship of the link.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:rel&quot;.</p>
 * <p>Returns: The relationship of the link.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm55c1]
 * @return 
 */
    public String getRelationship() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the mime-type of the content at the link.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:type&quot;.</p>
 * <p>Returns: The mime-type of the content at the link.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm559c]
 * @return 
 */
    public String getMimeType() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the language of the content at the link.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:hreflang&quot;.</p>
 * <p>Returns: The language of the content at the link.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5577]
 * @return 
 */
    public String getLinkLanguage() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the title of the link.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:title&quot;.</p>
 * <p>Returns: The title of the link.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5552]
 * @return 
 */
    public String getTitle() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the length of the content at the link.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:length&quot;.</p>
 * <p>Returns: The length of the content at the link.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm552d]
 * @return 
 */
    public Integer getLength() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the URL where this RSSLink points to.</p>
 * <p>Args: link - The URL where this RSSLink should point to. Possibly null indicating the link property should be removed.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:href&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5508]
 * @param link 
 */
    public void setLink(java.net.URI link) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the relationship of this link.</p>
 * <p>Args: relationship - The relationship of this link. Possibly null indicating the relationship property should be removed or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:rel&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm54c8]
 * @param relationship 
 */
    public void setRelationship(String relationship) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the mime-type of the content at this link.</p>
 * <p>Args: mimeType - The mime-type of the content at this link. Possibly null indicating the mime-type property should be removed or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:type&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5488]
 * @param mimeType 
 */
    public void setMimeType(String mimeType) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the language of the content at this link.</p>
 * <p>Args: mimeType - The language of the content at this link. Possibly null indicating the mime-type property should be removed or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:hreflang&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5448]
 * @param language 
 */
    public void setLinkLanguage(String language) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the title of this link.</p>
 * <p>Args: title - The title of this link. Possibly null indicating the title property should be removed or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:title&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm5408]
 * @param title 
 */
    public void setTitle(String title) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the length of the content at this link.</p>
 * <p>Args: length - The length of the content at this link. Possibly null indicating the length property should be removed.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:length&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Im579fc457m10e1824c6ffmm53c8]
 * @param length 
 */
    public void setLength(Integer length) {        
        // your code here
    } 
 }
