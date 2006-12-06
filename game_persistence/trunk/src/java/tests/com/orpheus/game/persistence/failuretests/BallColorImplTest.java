/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.entities.BallColorImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>BallColorImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class BallColorImplTest extends TestCase {

    /**
     * Test method for {@link BallColorImpl(java.lang.Long, java.lang.String, long)}.
     * In this case, the id is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testBallColorImpl_ZeroId() {
        try {
            new BallColorImpl(new Long(0), "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link BallColorImpl(java.lang.Long, java.lang.String, long)}.
     * In this case, the id is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testBallColorImpl_NegativeId() {
        try {
            new BallColorImpl(new Long(-1), "test", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link BallColorImpl(java.lang.Long, java.lang.String, long)}.
     * In this case, the name is null.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testBallColorImpl_NullName() {
        try {
            new BallColorImpl(new Long(1), null, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link BallColorImpl(java.lang.Long, java.lang.String, long)}.
     * In this case, the name is empty.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testBallColorImpl_EmptyName() {
        try {
            new BallColorImpl(new Long(1), " ", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link BallColorImpl(java.lang.Long, java.lang.String, long)}.
     * In this case, the image id is zero.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testBallColorImpl_ZeroImageId() {
        try {
            new BallColorImpl(new Long(1), "test", 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for {@link BallColorImpl(java.lang.Long, java.lang.String, long)}.
     * In this case, the image id is negative.
     * Expected : {@link IllegalArgumentException}.
     */
    public void testBallColorImpl_NegativeImageId() {
        try {
            new BallColorImpl(new Long(1), "test", -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
