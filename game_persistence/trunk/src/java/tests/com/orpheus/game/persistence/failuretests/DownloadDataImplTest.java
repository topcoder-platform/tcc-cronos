/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.entities.DownloadDataImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>DownloadDataImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DownloadDataImplTest extends TestCase {

    /**
     * Test method for DownloadDataImpl#DownloadDataImpl(
     * byte[], java.lang.String, java.lang.String).
     * In this case, the content is null.
     */
    public void testDownloadDataImpl_NullData() {
        try {
            new DownloadDataImpl(null, "name", "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DownloadDataImpl#DownloadDataImpl(
     * byte[], java.lang.String, java.lang.String).
     * In this case, the mediaType is null.
     */
    public void testDownloadDataImpl_NullMediaType() {
        try {
            new DownloadDataImpl(new byte[0], null, "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DownloadDataImpl#DownloadDataImpl(
     * byte[], java.lang.String, java.lang.String).
     * In this case, the mediaType is empty.
     */
    public void testDownloadDataImpl_EmptyMediaType() {
        try {
            new DownloadDataImpl(new byte[0], " ", "name");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
