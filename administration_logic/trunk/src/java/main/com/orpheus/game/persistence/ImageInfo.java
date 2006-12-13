
package com.orpheus.game.persistence;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.puzzle.*;
import com.topcoder.web.frontcontroller.results.*;
/**
 * An interface representing the stored information about an image associated with a specific domain
 * <p><strong>Thread Safety</strong></p>
 * <p>Implementations should strive to be thread-safe, but they can expect to be used in a thread-safe manner.</p>
 * 
 */
public interface ImageInfo extends java.io.Serializable {
/**
 * Returns the unique ID associated with this image information,
 * or null if none has yet been assigned
 * 
 * 
 * @return the id
 */
    public Long getId();
/**
 * Returns the unique ID of the downloadable image data
 * associated with this image information
 * 
 * 
 * @return the download id
 */
    public long getDownloadId();
/**
 * Returns a String description of the image
 * 
 * 
 * @return the description
 */
    public String getDescription();
/**
 * Returns the value of the approval flag for this image, or null
 * if no approval decision has yet been made
 * 
 * 
 * @return true if approved, false otherwise, or null if not yet decided
 */
    public Boolean isApproved();
}


