
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSImage interface by decorating over the child lements of an RSSObject.
 * <p>Implementation: It extends the RSSBaseObjectDecorator and only implements the methods of the RSSImage interface.</p>
 * <p>Thread Safety: This class is not thread safe as its super class is mutable.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm34d1]
 */
public class RSSImageImpl extends com.topcoder.util.rssgenerator.impl.BaseRSSObjectDecorator implements com.topcoder.util.rssgenerator.RSSImage {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSObject or RSSImage interfaces will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm34bd]
 */
    public  RSSImageImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm34a5]
 * @param object 
 */
    public  RSSImageImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the URI where the image may be found.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:source&quot;.</p>
 * <p>Returns: The URI where the image may be found.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3457]
 * @return 
 */
    public java.net.URI getSource() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the title of this image.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:title&quot;.</p>
 * <p>Returns: The title of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm3430]
 * @return 
 */
    public String getTitle() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the link to which this image links.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p>
 * <p>Returns: The link to which this image links.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm340b]
 * @return 
 */
    public java.net.URI getLink() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the width of this image.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:width&quot;.</p>
 * <p>Returns: The width of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm33d9]
 * @return 
 */
    public Integer getWidth() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the height of this image.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:height&quot;.</p>
 * <p>Returns: The height of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm33b4]
 * @return 
 */
    public Integer getHeight() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the description of this image.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:description&quot;.</p>
 * <p>Returns: The description of this image.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm338f]
 * @return 
 */
    public String getDescription() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the source of this image.</p>
 * <p>Args: source - The source URI of this image. Possibly null indicating no source.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:source&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm336a]
 * @param source 
 */
    public void setSource(java.net.URI source) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the title of this image.</p>
 * <p>Args: title - The title of this image. Possibly null indicating no title or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:title&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm332a]
 * @param title 
 */
    public void setTitle(String title) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the link to which this image links.</p>
 * <p>Args: link - The link to which this image links. Possibly null indicating no such link.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:link&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm32ea]
 * @param link 
 */
    public void setLink(java.net.URI link) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the width of this image.</p>
 * <p>Args: width - The width of this image. Possibly null indicating no width.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:width&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm32aa]
 * @param width 
 */
    public void setWidth(Integer width) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the height of this image.</p>
 * <p>Args: height - The height of this image. Possibly null indicating no height.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:height&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm326a]
 * @param height 
 */
    public void setHeight(Integer height) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the description of this image.</p>
 * <p>Args: description - The description of this image. Possibly null indicating no description or empty.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:description&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm322a]
 * @param description 
 */
    public void setDescription(String description) {        
        // your code here
    } 
 }
