/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Helper;

import com.topcoder.web.frontcontroller.results.ContentRetrievalException;
import com.topcoder.web.frontcontroller.results.DownloadData;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;


/**
 * <p>
 * Simple serializable implementation of the DownloadData. Represents a complete, downloadable entity with associated
 * media type and (possibly) suggested file name.
 * </p>
 *
 * <p>
 * <strong>Thread Safety</strong>:This class is immutable and thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class DownloadDataImpl implements Serializable, DownloadData {
    /**
     * <p>
     * Represents acontent as byte[] from which this entity's content can be read. The getContent will wrap this
     * around a new ByteArrayInputStream. The stream is initially positioned at the first byte of the download data,
     * and the download data continue to end of stream. Set in the constructor, cannot be null, and will not change.
     * </p>
     */
    private final byte[] content;

    /**
     * <p>
     * Represents the media type of the download data, in MIME format. Set in the constructor, cannot be null/empty,
     * and will not change.
     * </p>
     */
    private final String mediaType;

    /**
     * <p>
     * Represents a suggested client-side name for the download data, or null if there is no suggestion. Absence of a
     * suggested name may be interpreted as a hint that the object should be displayed by the user agent directly.
     * Set in the constructor, can be null/empty, and will not change. Empty will be set to null.
     * </p>
     */
    private final String suggestedName;

    /**
     * <p>
     * Constructor.
     * </p>
     *
     * @param content the content as a byte array
     * @param mediaType the media type
     * @param suggestedName the suggested name, if it is empty, it will be regarded as null
     *
     * @throws IllegalArgumentException If content is null or mediaType is null/empty
     */
    public DownloadDataImpl(byte[] content, String mediaType, String suggestedName) {
        Helper.checkNotNull(content, "Content");
        Helper.checkNotNullOrEmpty(mediaType, "MediaType");

        this.content = content;
        this.mediaType = mediaType;

        if ((suggestedName != null) && (suggestedName.trim().length() == 0)) {
            this.suggestedName = null;
        } else {
            this.suggestedName = suggestedName;
        }
    }

    /**
     * <p>
     * Returns an InputStream from which this entity's content can be read. The stream is initially positioned at the
     * first byte of the download data, and the download data continue to end of stream.
     * </p>
     *
     * @return the content as a stream
     *
     * @throws ContentRetrievalException if error occurs while get content
     */
    public InputStream getContent() throws ContentRetrievalException{
        return new ByteArrayInputStream(this.content);
    }

    /**
     * <p>
     * Returns the media type of the download data, in MIME format.
     * </p>
     *
     * @return the media type
     */
    public String getMediaType() {
        return this.mediaType;
    }

    /**
     * <p>
     * Returns a suggested client-side name for the download data, or null if there is no suggestion. Absence of a
     * suggested name may be interpreted as a hint that the object should be displayed by the user agent directly.
     * </p>
     *
     * @return the suggested name
     */
    public String getSuggestedName() {
        return this.suggestedName;
    }
}
