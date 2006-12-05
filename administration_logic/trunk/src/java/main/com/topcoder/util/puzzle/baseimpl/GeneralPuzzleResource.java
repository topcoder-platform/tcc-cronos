package com.topcoder.util.puzzle.baseimpl;

import com.topcoder.util.puzzle.PuzzleException;

/**
 * <p> A named and typed block of data associated with a puzzle, such as an image, an audio clip, or a lengthy 
 * piece of text.
 * </p>
 * 
 * <p> Thread-Safety
 * This implemantation is not thread-safe, since side-effects ffrom internal  references are possible.
 * Please note that this implementation is not fully encapsulated since getData method returns the actual data array reference.
 * The reason here is for efficiency so that memory is not copied each time a client calls this method. It is possible
 * that data will be quite sizeable.
 * </p>
 * 
 * <p> Serializability
 * This class is Serializable and nothing special needs to be done to it.
 * </p>
 * 
 * 
 * 
 * 
 */
public class GeneralPuzzleResource implements java.io.Serializable,
        com.topcoder.util.puzzle.PuzzleResource {

    /**
     * <p>Represents the name of the resources that this instance represents.
     * Set in the constructor and never changes after that. Can be anything but a null or an empty string.
     * </p>
     * 
     */
    private final String name;

    /**
     * <p>Represents the media type of the resource that this instance represents. 
     * This is basically the MIME media type of this 
     * resource as a String.
     * Set in the constructor and never changes after that. Can not be null or empty.
     * </p>
     * 
     */
    private final String mediaType;

    /**
     * <p>Represents binary data of this resource that this instance represents. This data is described using the provided MIME 
     * media type so that readers if this resource can decide how to deal with the data. 
     * Set in the constructor and never changes after that. Can not be null or an empty array.
     * </p>
     * 
     */
    private final byte[] data;

    /**
     * <p>Creates a new instance of this class with the provided parammeters set to internal vairables.
     * </p>
     * 
     * 
     * @param name name of this resource
     * @param mediaType MIME type of this resource
     * @param data data content of this resource
     * @throws IllegalArgumentException if name, or mediaType is null or an empty string, or if data is null or an ampty array
     */
    public GeneralPuzzleResource(String name, String mediaType, byte[] data) {
        this.name = name;
        this.mediaType = mediaType;
        this.data = data;
    }

    /**
     * <p>Retrieves the name of this puzzle resource as a String</p>
     * 
     * 
     * @return name of this puzzle resource
     */
    public String getName() {
        // your code here
        return name;
    }

    /**
     * <p>Retrieves the MIME media type of this resource as a String.</p>
     * 
     * 
     * @return MIME type of this resource
     */
    public String getMediaType() {

        return mediaType;
    }

    /**
     * <p>returns the data content for this resource. Since the data could be large the actual reference is returned.
     * </p>
     * 
     * 
     * @return data content of this resource
     */
    public byte[] getData() {

        return data;
    }
}
