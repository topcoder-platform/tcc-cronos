/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.topcoder.web.frontcontroller.results.DownloadData;

import junit.framework.TestCase;

import java.io.InputStream;


/**
 * <p>
 * Unit test cases for class <code>DownloadDataImpl</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DownloadDataImplUnitTests extends TestCase {
    /** the media type. */
    private static final String MEDIA_TYPE = "image/png";

    /** the sugguestedName field. */
    private static final String SUGGESTED_NAME = "puzzle";

    /** the content. */
    private byte[] content = null;

    /** DownloadDataImpl instance to test against. */
    private DownloadData downloadData = null;

    /**
     * create instance.
     */
    protected void setUp() {
        content = MEDIA_TYPE.getBytes();

        downloadData = new DownloadDataImpl(content, MEDIA_TYPE, SUGGESTED_NAME);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The DownloadDataImpl can not be instantiate.", downloadData);
    }

    /**
     * test the ctor, the content is null, iae expected.
     */
    public void testCtor_nullContent() {
        try {
            new DownloadDataImpl(null, MEDIA_TYPE, SUGGESTED_NAME);
            fail("The content is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the mediaType is null, iae expected.
     */
    public void testCtor_nullMediaType() {
        try {
            new DownloadDataImpl(content, null, SUGGESTED_NAME);
            fail("The media type is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the mediaType is empty, iae expected.
     */
    public void testCtor_emptyMediaType() {
        try {
            new DownloadDataImpl(content, "  ", SUGGESTED_NAME);
            fail("The media type is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * simply verify the getMediaType method.
     */
    public void testGetMediaType() {
        assertEquals("The media type to set is the not one get.", MEDIA_TYPE, downloadData.getMediaType());
    }

    /**
     * simply verify the getSuggestedName method.
     */
    public void testGetSuggestedName() {
        assertEquals("The suggestedName to set is the not one get.", SUGGESTED_NAME, downloadData.getSuggestedName());
        this.downloadData = new DownloadDataImpl(content, MEDIA_TYPE, "   ");
        assertNull("The suggestedName is null.", downloadData.getSuggestedName());
    }

    /**
     * simply verify the getContent method.
     *
     * @throws Exception into Junit
     */
    public void testGetContent() throws Exception {
        InputStream stream = this.downloadData.getContent();
        assertNotNull("The inputstream is not null.", stream);

        byte[] out = new byte[MEDIA_TYPE.getBytes().length];
        stream.read(out);
        assertEquals("The out content is the same.", new String(out), MEDIA_TYPE);
    }
}
