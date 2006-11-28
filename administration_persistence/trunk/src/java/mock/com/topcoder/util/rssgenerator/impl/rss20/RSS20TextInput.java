
package com.topcoder.util.rssgenerator.impl.rss20;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: Extends the BaseRSSObjectDecorator class to provide an RSS 2.0 text input <p>Implementation: We decorate over some more properties to provide a text-input object.</p> <p>Thread Safety: This class is not thread safe as its base class is not thread safe either.</p>
 * 
 */
public class RSS20TextInput extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of this class will result in an IllegalStateException being thrown.</p> <p>Args: None.</p> <p>Implementation: Simply call super().</p> <p>Exceptions: None.</p>
 * 
 */
    public  RSS20TextInput() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p> <p>Args: object - The RSSObject to be decorated over. Must not be null.</p> <p>Implementation: Simply call super(object).</p> <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * 
 * @param object 
 */
    public  RSS20TextInput(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: Gets the title of the text-input.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:title&quot;</p> <p>Returns: The title of this text-input.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public String getTitle() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the description of the text-input.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:description&quot;</p> <p>Returns: The description of this text-input.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public String getDescription() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the name of the text-input.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:name&quot;</p> <p>Returns: The name of this text-input.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Gets the link of the text-input.</p> <p>Args: None.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;</p> <p>Returns: The link of this text-input.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @return 
 */
    public java.net.URI getLink() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: Sets the title of the text-input.</p> <p>Args: title - The title of this text-input. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:title&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param title 
 */
    public void setTitle(String title) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the description of the text-input.</p> <p>Args: description - The description of this text-input. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:description&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 * 
 * 
 * @param description 
 */
    public void setDescription(String description) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the name of the text-input.</p> <p>Args: name - The name of this text-input. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:name&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p> <p></p>
 * 
 * 
 * @param name 
 */
    public void setName(String name) {        
        // your code here
    } 

/**
 * <p>Purpose: Sets the link of the text-input.</p> <p>Args: link - The link of this text-input. Possibly null.</p> <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;</p> <p>Returns: None.</p> <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * 
 * @param link 
 */
    public void setLink(java.net.URI link) {        
        // your code here
    } 
 }
