/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.ImageInfo;

import junit.framework.TestCase;


/**
 * Unit test cases for <code>ImageInfoImpl</code> class.
 * @author waits
 * @version 1.0
 */
public class ImageInfoImplUnitTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);

    /** represents the description constants for testing. */
    public static final String DESCRIPTION = "description";

    /** represents the downloadId constants for testing. */
    public static final long DOWNLOAD_ID = 1000L;

    /** respresents the approved constants for testing. */
    public static final Boolean APPROVED = new Boolean(true);

    /** ImageInfoImpl instance to test against. */
    private ImageInfo imageInfo = null;

    /**
     * Create instance.
     */
    protected void setUp() {
        imageInfo = new ImageInfoImpl(ID, DOWNLOAD_ID, DESCRIPTION, APPROVED);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The ImageInfoImpl can not be instantiate.", imageInfo);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new ImageInfoImpl(null, DOWNLOAD_ID, DESCRIPTION, APPROVED);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new ImageInfoImpl(new Long(0), DOWNLOAD_ID, DESCRIPTION, APPROVED);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the downloadId is not positive, iae expected.
     */
    public void testCtor_notPositiveDownLoadId() {
        try {
            new ImageInfoImpl(null, 0, DESCRIPTION, APPROVED);
            fail("The downloadId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the description is null, iae expected.
     */
    public void testCtor_nullDescription() {
        try {
            new ImageInfoImpl(null, DOWNLOAD_ID, null, APPROVED);
            fail("The description is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the description is empty, iae expected.
     */
    public void testCtor_emptyDescription() {
        try {
            new ImageInfoImpl(null, DOWNLOAD_ID, "  ", APPROVED);
            fail("The description is empty.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, this.imageInfo.getId());
    }

    /**
     * simply verify the isApproved method.
     */
    public void testIsApproved() {
        assertEquals("The approve to set is the not one get.", APPROVED, this.imageInfo.isApproved());
    }

    /**
     * simply verify the getDescription method.
     */
    public void testGetName() {
        assertEquals("The Description to set is the not one get.", DESCRIPTION, this.imageInfo.getDescription());
    }

    /**
     * simply verify the getDownloadId method.
     */
    public void testGetImageId() {
        assertEquals("The downloadId to set is the not one get.", DOWNLOAD_ID, this.imageInfo.getDownloadId());
    }
}
