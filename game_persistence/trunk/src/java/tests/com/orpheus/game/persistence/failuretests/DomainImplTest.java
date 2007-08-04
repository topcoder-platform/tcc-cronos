/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.entities.DomainImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>DomainImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DomainImplTest extends TestCase {

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_ZeroId() {
        try {
            new DomainImpl(new Long(0), new Long(1), "name", Boolean.TRUE, new ImageInfo[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the id is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_NegativeId() {
        try {
            new DomainImpl(new Long(-1), new Long(1), "name", Boolean.TRUE, new ImageInfo[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the image id is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_ZeroImageId() {
        try {
            new DomainImpl(new Long(1), new Long(0), "name", Boolean.TRUE, new ImageInfo[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the image id is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_NegativeImageId() {
        try {
            new DomainImpl(new Long(1), new Long(-1), "name", Boolean.TRUE, new ImageInfo[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the name is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_NullName() {
        try {
            new DomainImpl(new Long(1), new Long(1), null, Boolean.TRUE, new ImageInfo[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the name is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_EmptyName() {
        try {
            new DomainImpl(new Long(1), new Long(1), " ", Boolean.TRUE, new ImageInfo[0]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the image info is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_NullImageInfo() {
        try {
            new DomainImpl(new Long(1), new Long(1), "name", Boolean.TRUE, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainImpl(
     * java.lang.Long, long, java.lang.String,
     * java.lang.Boolean, com.orpheus.game.persistence.ImageInfo[]).
     * In this case, the image info contains null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testDomainImpl_NullInImageInfo() {
        try {
            new DomainImpl(new Long(1), new Long(1), "name", Boolean.TRUE, new ImageInfo[1]);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
