
package com.topcoder.util.rssgenerator;
/**
 * Purpose: This interface extends the RSSBaseItem contract to add the one additional property of RSS items. It adds the source-id property which holds the ID of the source of this item. <p>Implementation: Implementations might use decoration over an RSSObject or member variables to provide the additional property.</p> <p>Thread Safety: Implementtaions are not required to be thread safe.</p>
 * 
 */
public interface RSSItem extends com.topcoder.util.rssgenerator.RSSBaseItem {
/**
 * <p>Purpose: This method returns the ID of the source feed of this item.</p> <p>Args: None.</p> <p>Returns: The ID of the source feed of this item.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @return 
 */
    public String getSourceId();
/**
 * <p>Purpose: This method sets the ID of the source feed of this item.</p> <p>Args: id - The source ID of this item. Possibly null indicating no source ID or empty.</p> <p>Returns: None.</p> <p>Exceptions: None.</p>
 * 
 * 
 * @param sourceId 
 */
    public void setSourceId(String sourceId);
}


