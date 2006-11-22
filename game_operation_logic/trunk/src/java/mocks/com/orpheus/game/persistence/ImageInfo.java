
package com.orpheus.game.persistence;
/**
 * An interface representing the stored information about an image associated with a specific domain
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm318a]
 */
public interface ImageInfo extends java.io.Serializable {
/**
 * Returns the unique ID associated with this image information, or null if none has yet been assigned
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm302b]
 * @return 
 */
    public Long getId();
/**
 * Returns the unique ID of the downloadable image data associated with this image information
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm3024]
 * @return 
 */
    public long getDownloadId();
/**
 * Returns a String description of the image
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm301d]
 * @return 
 */
    public String getDescription();
/**
 * Returns the value of the approval flag for this image, or null if no approval decision has yet been made
 * 
 * @poseidon-object-id [I8c0162m10e34ec0315mm3016]
 * @return 
 */
    public Boolean isApproved();
}


