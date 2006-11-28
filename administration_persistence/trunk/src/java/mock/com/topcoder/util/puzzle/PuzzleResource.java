/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.util.puzzle;

/**
 * A named and typed block of data associated with a puzzle, such as an image, an audio clip, or a lengthy piece of
 * text.
 *
 * @author TopCoder, TCSDEVELOPER
 * @version 1.0
 */

public interface PuzzleResource {
    /**
     * Retrieves the name of this puzzle resource.
     *
     * @return the name of this puzzle resource
     */
    public String getName();

    /**
     * Retrieves the MIME media type of this resource.
     *
     * @return the MIME media type of this resource
     */
    public String getMediaType();

    /**
     * Retrieves the data content of this resource.
     *
     * @return the data content of this resource
     */
    public byte[] getData();
}
