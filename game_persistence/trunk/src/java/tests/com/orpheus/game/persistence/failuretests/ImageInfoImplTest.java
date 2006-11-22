/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.entities.ImageInfoImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>ImageInfoImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class ImageInfoImplTest extends TestCase {

    /**
     * Test method for ImageInfoImpl(java.lang.Long, long, java.lang.String, java.lang.Boolean).
     * In this case, the id is zero.
     */
    public void testImageInfoImpl_ZeroId() {
        try {
            new ImageInfoImpl(new Long(0), 1, "test", Boolean.TRUE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ImageInfoImpl(java.lang.Long, long, java.lang.String, java.lang.Boolean).
     * In this case, the id is negative.
     */
    public void testImageInfoImpl_NegativeId() {
        try {
            new ImageInfoImpl(new Long(-1), 1, "test", Boolean.TRUE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ImageInfoImpl(java.lang.Long, long, java.lang.String, java.lang.Boolean).
     * In this case, the download id is zero.
     */
    public void testImageInfoImpl_ZeroDownloadId() {
        try {
            new ImageInfoImpl(new Long(1), 0, "test", Boolean.TRUE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ImageInfoImpl(java.lang.Long, long, java.lang.String, java.lang.Boolean).
     * In this case, the download id is negative.
     */
    public void testImageInfoImpl_NegativeDownloadId() {
        try {
            new ImageInfoImpl(new Long(1), -1, "test", Boolean.TRUE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ImageInfoImpl(java.lang.Long, long, java.lang.String, java.lang.Boolean).
     * In this case, the description is null.
     */
    public void testImageInfoImpl_NullDescription() {
        try {
            new ImageInfoImpl(new Long(1), 1, null, Boolean.TRUE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for ImageInfoImpl(java.lang.Long, long, java.lang.String, java.lang.Boolean).
     * In this case, the description is empty.
     */
    public void testImageInfoImpl_EmptyDescription() {
        try {
            new ImageInfoImpl(new Long(1), 1, " ", Boolean.TRUE);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
