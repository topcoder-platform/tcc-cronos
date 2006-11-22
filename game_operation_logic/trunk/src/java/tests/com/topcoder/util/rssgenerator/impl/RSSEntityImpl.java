
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSEntity interface bydecorating over an RSSObject instance. It decorates over the child elements of the object.
 * <p>Implementation: We extend the RSSBaseObjectDecorator so that we need only implement the functions added by the RSSEntity interface.</p>
 * <p>Thread Safety: This class is not thread safe as its super class is mutable.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3b6c]
 */
public class RSSEntityImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSEntity {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSEntity interfaces will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3b57]
 */
    public  RSSEntityImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3b3f]
 * @param object 
 */
    public  RSSEntityImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the name of this entity.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:name&quot;.</p>
 * <p>Returns: The name of this entity.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3b27]
 * @return 
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the link to this entity.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p>
 * <p>Returns: The link to this entity.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3acd]
 * @return 
 */
    public java.net.URI getLink() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the email address of this entity.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:email&quot;.</p>
 * <p>Returns: The email address of this entity.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3aa8]
 * @return 
 */
    public String getEmailAddress() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the name of this entity.</p>
 * <p>Args: name - The name of this entity. Possibly null indicating no name or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:name&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3a83]
 * @param name 
 */
    public void setName(String name) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the link to this entity.</p>
 * <p>Args: link - The link of this entity. Possibly null indicating no link.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3a43]
 * @param link 
 */
    public void setLink(java.net.URI link) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the email address of this entity.</p>
 * <p>Args: emailAddress - The email address of this entity. Possibly null indicating no email-address or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:email&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3a03]
 * @param emailAddress 
 */
    public void setEmailAddress(String emailAddress) {        
        // your code here
    } 
 }
