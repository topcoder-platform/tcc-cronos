
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSBaseItem contract to represent feeds. An RSSFeed has three addtional properties - the feed generator, images associated with the feed (primarily to represent its logo) and the items associated with a feed. Get.set methods are provided for all three including add/remove/clear methods for the multi-valued properties. <p>Impementation: Implementations can provide the addtional properties either through decoration over the properties of an RSSObject or as member variables.</p> <p>Thread Safety: Implementations need not be thread safe.</p>
 * 
 */
public interface RSSFeed extends com.topcoder.util.rssgenerator.RSSBaseItem {
/**
 * <p>Purpose: This method returns the generator that generated this feed.</p> <p>Args: None.</p> <p>Returns: The generator that generated this feed.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSGenerator getGenerator();
/**
 * <p>Purpose: This method returns the images associated with this feed.</p> <p>Args: None.</p> <p>Returns: The images associated with this feed.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSImage[] getImages();
/**
 * <p>Purpose: This method returns the items associated with this feed.</p> <p>Args: None.</p> <p>Returns: The items associated with this feed.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public com.topcoder.util.rssgenerator.RSSItem[] getItems();
/**
 * <p>Purpose: This method sets the generator that generated this feed.</p> <p>Args: generator - The generator that generated this feed. Possibly null indicating no generator info.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param generator 
 */
    public void setGenerator(com.topcoder.util.rssgenerator.RSSGenerator generator);
/**
 * <p>Purpose: This method sets the images associated with this feed.</p> <p>Args: images - The images associated with this feed. Possibly null implying an empty array. All elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * 
 * @param images 
 */
    public void setImages(com.topcoder.util.rssgenerator.RSSImage[] images);
/**
 * <p>Purpose: This method adds to the images associated with this feed.</p> <p>Args: image - The image to add to those associated with this feed. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If image is null.</p>
 * 
 * 
 * @param image 
 */
    public void addImage(com.topcoder.util.rssgenerator.RSSImage image);
/**
 * <p>Purpose: This method removes from the images associated with this feed.</p> <p>Args: image - The image to remove from those associated with this feed. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If image is null.</p>
 * 
 * 
 * @param image 
 */
    public void removeImage(com.topcoder.util.rssgenerator.RSSImage image);
/**
 * <p>Purpose: This method clears all images associated with this feed.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearImages();
/**
 * <p>Purpose: This method sets the items associated with this feed.</p> <p>Args: items - The items associated with this feed. Possibly null implying an empty array. All elements of the array must be non-null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If any of the elements of the array is null.</p>
 * 
 * 
 * @param items 
 */
    public void setItems(com.topcoder.util.rssgenerator.RSSItem[] items);
/**
 * <p>Purpose: This method adds to the items associated with this feed.</p> <p>Args: item - The item to add to those associated with this feed. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * 
 * 
 * @param item 
 */
    public void addItem(com.topcoder.util.rssgenerator.RSSItem item);
/**
 * <p>Purpose: This method removes from the items associated with this feed.</p> <p>Args: item - The item to remove from those associated with this feed. Must not be null.</p> <p>Returns: None.</p> <p>Exceptions: IllegalArgumentException - If item is null.</p>
 * 
 * 
 * @param item 
 */
    public void removeItem(com.topcoder.util.rssgenerator.RSSItem item);
/**
 * <p>Purpose: This method clears all items associated with this feed.</p> <p>Args: None.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 */
    public void clearItems();
}


