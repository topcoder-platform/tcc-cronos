
package com.topcoder.util.puzzle;
/**
 * <p> A named and typed block of data associated with a puzzle, such as an image, an audio clip, or a lengthy 
 * piece of text.
 * This is a read-only API so it should be thread-safe by default.
 *  </p>
 * 
 * 
 * @poseidon-object-id [Ifdeeecm10df11a47b1mm7982]
 */
public interface PuzzleResource {
/**
 * <p>Retrieves the name of this puzzle resource as a String</p>
 * 
 * @poseidon-object-id [Ifdeeecm10df11a47b1mm795a]
 * @return name of this puzzle resource
 */
    public String getName();
/**
 * <p>Retrieves the MIME media type of this resource as a String.</p>
 * 
 * @poseidon-object-id [Ifdeeecm10df11a47b1mm7935]
 * @return MIME type of this resource
 */
    public String getMediaType();
/**
 * <p>Retrieves the data content of this resource as a byte[]</p>
 * 
 * @poseidon-object-id [Ifdeeecm10df11a47b1mm7910]
 * @return data content of this resource
 */
    public byte[] getData();
}


