/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import com.topcoder.util.puzzle.PuzzleResource;

import java.io.Serializable;

/**
 * <p>A simple data structure containing the name, MIME type, and binary data of a puzzle resource.
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and therefore thread safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class PuzzleResourceImpl implements PuzzleResource, Serializable {
    /**
     * The maximum allowable name size.
     */
    private static final int MAXIMUM_NAME_SIZE = 255;

    /**
     * The name of this puzzle resource. This member is initialized in the constructor, is immutable, and will
     * never be <code>null</code> or an empty string. In addition, it will not be longer than 255 characters and
     * will not contain any commas.
     */
    private final String name;

    /**
     * The MIME type of this puzzle resource. This member is initialized in the constructor, is immutable, and will
     * never be <code>null</code> or an empty string.
     */
    private final String mediaType;

    /**
     * The binary data associated with this resource, encoded according to the specified media type. This member is
     * initialized in the constructor, is immutable, and will never be <code>null</code>.
     */
    private final byte[] data;

    /**
     * Constructs a new <code>PuzzleResourceImpl</code> with the specified name, media type, and data.
     *
     * @param name the name of this resource
     * @param mediaType the MIME type of this resource
     * @param data the binary data associated with this resource, encoded in the format specified by
     *   <code>mediaType</code>
     * @throws IllegalArgumentException if any argument is <code>null</code>
     * @throws IllegalArgumentException if <code>name</code> or <code>mediaType</code> is an empty string
     * @throws IllegalArgumentException if <code>name</code> is longer than 255 characters
     * @throws IllegalArgumentException if <code>name</code> contains a comma
     */
    public PuzzleResourceImpl(String name, String mediaType, byte[] data) {
        if (name == null) {
            throw new IllegalArgumentException("resource name must not be null");
        }

        if (name.trim().length() == 0) {
            throw new IllegalArgumentException("resource name must not be an empty string");
        }

        if (name.length() > MAXIMUM_NAME_SIZE) {
            throw new IllegalArgumentException("resource name must not be longer than " + MAXIMUM_NAME_SIZE
                                               + " characters");
        }

        if (name.indexOf(',') >= 0) {
            throw new IllegalArgumentException("resource name must not contain a comma");
        }

        if (mediaType == null) {
            throw new IllegalArgumentException("resource media type must not be null");
        }

        if (mediaType.trim().length() == 0) {
            throw new IllegalArgumentException("resource media type must not be an empty string");
        }

        if (data == null) {
            throw new IllegalArgumentException("resource data must not be null");
        }

        this.name = name;
        this.mediaType = mediaType;
        this.data = data;
    }

    /**
     * Returns the name of this resource. The name always be a non-<code>null</code>, non-empty string of less than
     * 256 characters in length and containing no commas.
     *
     * @return the name of this resource
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the MIME type of this resource. The MIME type will always be a non-<code>null</code>, non-empty
     * string.
     *
     * @return the MIME type of this resource
     */
    public String getMediaType() {
        return this.mediaType;
    }

    /**
     * Returns the binary data associated with this resource. The data should be encoded according to the {@link
     * #getMediaType media type} associated with this resource. This method will never return <code>null</code>.
     *
     * @return the binary data associated with this resource
     */
    public byte[] getData() {
        return this.data;
    }
}
