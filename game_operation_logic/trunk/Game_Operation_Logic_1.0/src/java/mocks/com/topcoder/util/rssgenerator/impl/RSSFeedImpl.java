
package com.topcoder.util.rssgenerator.impl;
import com.topcoder.util.rssgenerator.impl.InvalidRSSObjectException;

/**
 * Purpose: This class implements the RSSFeed interface by decorating over an RSSObject instance.
 * <p>Implementation: We extend the RSSBaseItem class and thus need to implement only the methods specific to RSSFeed. These are done by decorating over child leements. Please see CS 1.3.1 to see how this decoration is done.</p>
 * <p>Thead Safety: This class is not thread safe despite having no member variables as its base class is not thread safe.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm558e]
 */
public class RSSFeedImpl extends com.topcoder.util.rssgenerator.impl.RSSBaseItemImpl implements com.topcoder.util.rssgenerator.RSSFeed {

/**
 * <p>Purpose: This no-args constructor is to allow construction of this object through reflection while reading from an externalized source. Note that the underlying object has a null reference after construction and calling any of the methods of the RSSFeed interface or its sub-interfaces will result in an IllegalStateException being thrown.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Simply call super().</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm557a]
 */
    public  RSSFeedImpl() {        
        // your code here
    } 

/**
 * <p>Purpose: Constructs this object by decorating the given RSSObject instance.</p>
 * <p>Args: object - The RSSObject to be decorated over. Must not be null.</p>
 * <p>Implementation: Simply call super(object).</p>
 * <p>Exceptions: IllegalArgumentException - If object is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5562]
 * @param object 
 */
    public  RSSFeedImpl(com.topcoder.util.rssgenerator.RSSObject object) {        
        // your code here
    } 

/**
 * <p>Purpose: This method returns the generator that generated this feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:generator&quot;.</p>
 * <p>Returns: The generator that generated this feed.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm554a]
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSGenerator getGenerator() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the images associated with this feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:image&quot;.</p>
 * <p>Returns: The images associated with this feed.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm54f0]
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSImage[] getImages() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method returns the items associated with this feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:item&quot;.</p>
 * <p>Returns: The items associated with this feed.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm54cb]
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSItem[] getItems() {        
        // your code here
        return null;
    } 

/**
 * <p>Purpose: This method sets the generator that generated this feed.</p>
 * <p>Args: generator - The generator that generated this feed. Possibly null indicating no generator info.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:generator&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm54a6]
 * @param generator 
 */
    public void setGenerator(com.topcoder.util.rssgenerator.RSSGenerator generator) {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the images associated with this feed.</p>
 * <p>Args: images - The images associated with this feed. Possibly null implying an empty array. All elements of the array must be non-null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:image&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5466]
 * @param images 
 */
    public void setImages(com.topcoder.util.rssgenerator.RSSImage[] images) {        
        // your code here
    } 

/**
 * <p>Purpose: This method adds to the images associated with this feed.</p>
 * <p>Args: image - The image to add to those associated with this feed. Must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:image&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If image is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5426]
 * @param image 
 */
    public void addImage(com.topcoder.util.rssgenerator.RSSImage image) {        
        // your code here
    } 

/**
 * <p>Purpose: This method removes from the images associated with this feed.</p>
 * <p>Args: image - The image to remove from those associated with this feed. Must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:image&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If image is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm53e6]
 * @param image 
 */
    public void removeImage(com.topcoder.util.rssgenerator.RSSImage image) {        
        // your code here
    } 

/**
 * <p>Purpose: This method clears all images associated with this feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:image&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm53a6]
 */
    public void clearImages() {        
        // your code here
    } 

/**
 * <p>Purpose: This method sets the items associated with this feed.</p>
 * <p>Args: items - The items associated with this feed. Possibly null implying an empty array. All elements of the array must be non-null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:item&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5381]
 * @param items 
 */
    public void setItems(com.topcoder.util.rssgenerator.RSSItem[] items) {        
        // your code here
    } 

/**
 * <p>Purpose: This method adds to the items associated with this feed.</p>
 * <p>Args: item - The item to add to those associated with this feed. Must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:item&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5341]
 * @param item 
 */
    public void addItem(com.topcoder.util.rssgenerator.RSSItem item) {        
        // your code here
    } 

/**
 * <p>Purpose: This method removes from the items associated with this feed.</p>
 * <p>Args: item - The item to remove from those associated with this feed. Must not be null.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:item&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm5301]
 * @param item 
 */
    public void removeItem(com.topcoder.util.rssgenerator.RSSItem item) {        
        // your code here
    } 

/**
 * <p>Purpose: This method clears all items associated with this feed.</p>
 * <p>Args: None.</p>
 * <p>Implementation: Refer to CS 1.3.1. Decorate over &quot;element:item&quot;.</p>
 * <p>Returns: None.</p>
 * <p>Exceptions: None.</p>
 * 
 * @poseidon-object-id [Imc11a95dm10e1e826a7fmm52c1]
 */
    public void clearItems() {        
        // your code here
    } 
 }
