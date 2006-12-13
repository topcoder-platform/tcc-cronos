
package com.topcoder.util.puzzle;
/**
 * <p> A named and typed block of data associated with a puzzle, such as an image, an audio clip, or a lengthy 
 * piece of text.
 * This is a read-only API so it should be thread-safe by default.
 *  </p>
 * 
 * 
 */
public interface PuzzleResource {
/**
 * <p>Retrieves the name of this puzzle resource as a String</p>
 * 
 * 
 * @return name of this puzzle resource
 */
    public String getName();
/**
 * <p>Retrieves the MIME media type of this resource as a String.</p>
 * 
 * 
 * @return MIME type of this resource
 */
    public String getMediaType();
/**
 * <p>Retrieves the data content of this resource as a byte[]</p>
 * 
 * 
 * @return data content of this resource
 */
    public byte[] getData();
}


