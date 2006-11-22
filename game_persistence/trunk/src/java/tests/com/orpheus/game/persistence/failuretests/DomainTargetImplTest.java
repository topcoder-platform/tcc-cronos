/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.entities.DomainTargetImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>DomainTargetImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class DomainTargetImplTest extends TestCase {

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the id is zero.
     */
    public void testDomainTargetImpl_ZeroId() {
        try {
            new DomainTargetImpl(new Long(0), 1, "com/topcoder",
                    "topcoder", "hash", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the id is negative.
     */
    public void testDomainTargetImpl_NegativeId() {
        try {
            new DomainTargetImpl(new Long(-1), 1, "com/topcoder",
                    "topcoder", "hash", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the sequence number is zero.
     */
    public void testDomainTargetImpl_ZeroSeqNum() {
        try {
            new DomainTargetImpl(new Long(1), 0, "com/topcoder",
                    "topcoder", "hash", 1);
        } catch (Exception e) {
            fail("no exception expected.");
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the sequence number is nagative.
     */
    public void testDomainTargetImpl_NegativeSeqNum() {
        try {
            new DomainTargetImpl(new Long(1), -1, "com/topcoder",
                    "topcoder", "hash", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the uri is null.
     */
    public void testDomainTargetImpl_NullUri() {
        try {
            new DomainTargetImpl(new Long(1), 1, null,
                    "topcoder", "hash", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the uri is null.
     */
    public void testDomainTargetImpl_EmptyUri() {
        try {
            new DomainTargetImpl(new Long(1), 1, " ",
                    "topcoder", "hash", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the hash is null.
     */
    public void testDomainTargetImpl_NullHash() {
        try {
            new DomainTargetImpl(new Long(1), 1, "com/topcoder",
                    "topcoder", null, 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the hash is null.
     */
    public void testDomainTargetImpl_EmptyHash() {
        try {
            new DomainTargetImpl(new Long(1), 1, "com/topcoder",
                    "topcoder", " ", 1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the clue id is zero.
     */
    public void testDomainTargetImpl_ZeroClueId() {
        try {
            new DomainTargetImpl(new Long(1), 1, "com/topcoder",
                    "topcoder", "hash", 0);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for DomainTargetImpl#DomainTargetImpl(
     * java.lang.Long, int, java.lang.String,
     * java.lang.String, java.lang.String, long).
     *
     * In this case, the clue id is zero.
     */
    public void testDomainTargetImpl_NegativeClueId() {
        try {
            new DomainTargetImpl(new Long(1), 1, "com/topcoder",
                    "topcoder", "hash", -1);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
