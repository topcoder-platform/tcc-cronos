/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import java.util.Date;

import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.HostingSlotImpl;

import junit.framework.TestCase;

/**
 * Test case for <code>HostingSlotImpl</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class HostingSlotImplTest extends TestCase {

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the id is zero.
     */
    public void testHostingSlotImpl_NullId() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[0],
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the id is zero.
     */
    public void testHostingSlotImpl_NegativeId() {
        try {
            new HostingSlotImpl(
                    new Long(-1),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[0],
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the domain is null.
     */
    public void testHostingSlotImpl_NullDomain() {
        try {
            new HostingSlotImpl(
                    new Long(1),
                    null, //new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[0],
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the image id is zero.
     */
    public void testHostingSlotImpl_ZeroImageId() {
        try {
            new HostingSlotImpl(
                    new Long(1),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    0,
                    new long[0],
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the image id is negative.
     */
    public void testHostingSlotImpl_NegativeImageId() {
        try {
            new HostingSlotImpl(
                    new Long(1),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    -1,
                    new long[0],
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the brainTeaserIds  is null.
     */
    public void testHostingSlotImpl_NullbrainTeaserIds() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    null, //new long[0],
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the brainTeaserIds  contains zero.
     */
    public void testHostingSlotImpl_ZeroInbrainTeaserIds() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {0},
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the brainTeaserIds  contains zero.
     */
    public void testHostingSlotImpl_NegativeInbrainTeaserIds() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {-1},
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the brainTeaserIds  contains zero.
     */
    public void testHostingSlotImpl_ZeroPuzzleId() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(0),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the puzzleId is negative.
     */
    public void testHostingSlotImpl_NegativePuzzleId() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(-1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the sequence number is negative.
     */
    public void testHostingSlotImpl_NegativeSeqNum() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(1),
                    -1,
                    new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the domain target is null.
     */
    public void testHostingSlotImpl_NullDomainTarget() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(1),
                    -1,
                    null, //new DomainTarget[0],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the domain target contains null.
     */
    public void testHostingSlotImpl_NullInDomainTarget() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(1),
                    1,
                    new DomainTarget[1],
                    1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the winning bid is zero
     */
    public void testHostingSlotImpl_ZeroWinningBid() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    0,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the winning bid is negative
     */
    public void testHostingSlotImpl_NegativeWinningBid() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    -1,
                    new Date(10),
                    new Date(100)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

    /**
     * Test method for HostingSlotImpl(
     * java.lang.Long,
     * com.orpheus.game.persistence.Domain,
     * long,
     * long[],
     * java.lang.Long,
     * int,
     * com.orpheus.game.persistence.DomainTarget[],
     * int,
     * java.util.Date,
     * java.util.Date).
     *
     * In this case, the start date is later than end date.
     */
    public void testHostingSlotImpl_InvalidDates() {
        try {
            new HostingSlotImpl(
                    new Long(0),
                    new DomainImpl(new Long(1), 1, "test", null, new ImageInfo[0]),
                    1,
                    new long[] {1},
                    new Long(1),
                    1,
                    new DomainTarget[0],
                    1,
                    new Date(100),
                    new Date(10)
                    );
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // should land here
        }
    }

}
