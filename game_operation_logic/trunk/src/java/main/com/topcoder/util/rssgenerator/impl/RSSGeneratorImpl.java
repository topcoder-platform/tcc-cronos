
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSGenerator interface by decorating over an RSSObject instance.
 * <p>Implementation: We extend the RSSbaseObjectDecorator class and thus only have to implement the methods added by the RSSGenerator interface. The properties are provided by decorating over attributes as well as the text of the element.</p>
 * <p>Thread Safety: This class is not thread safe despite a lack of members as its base class is not thread safe.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3eb5]
 */
public class RSSGeneratorImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSGenerator {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSGenerator interfaces will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3ea0]
 */
    public  RSSGeneratorImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3e88]
 * @param object 
 */
    public  RSSGeneratorImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the name of this generator.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over getText().</p>
 * <p>Returns: The name of this generator.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3e70]
 * @return 
 */
    public String getName() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the version of this generator.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:version&quot;.</p>
 * <p>Returns: The version of this generator.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3e16]
 * @return 
 */
    public String getVersion() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the link to this generator.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:link&quot;.</p>
 * <p>Returns: The link to this generator.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3df1]
 * @return 
 */
    public java.net.URI getLink() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the name of this generator.</p>
 * <p>Args: name - The name of this generator. Possibly null indicating no name or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over getText().</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3dcc]
 * @param name 
 */
    public void setName(String name) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the version of this generator.</p>
 * <p>Args: version - The version of this generator. Possibly null indicating no version-info or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:version&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3d8c]
 * @param version 
 */
    public void setVersion(String version) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the link to this generator.</p>
 * <p>Args: link - The link to this generator. Possibly null indicating no link.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;attribute:link&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalStateException - If the underlying object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3d4c]
 * @param link 
 */
    public void setLink(java.net.URI link) {        
        // your code here
    } 
 }
