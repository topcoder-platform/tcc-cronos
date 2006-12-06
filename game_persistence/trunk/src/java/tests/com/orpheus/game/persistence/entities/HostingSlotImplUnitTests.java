/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.TestHelper;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Unit test case for class <code>HostingSlotImpl</code>.
 * </p>
 * @author waits
 * @version 1.0
 */
public class HostingSlotImplUnitTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);

    /** represents the puzzleId constants for testing. */
    public static final Long PUZZLE_ID = new Long(2);

    /** represents the sequenceNumber constants for testing. */
    public static final int SEQUENCE_NUMBER = 1000;

    /** represents the imageId constants for testing. */
    public static final long IMAGE_ID = 1000L;

    /** represents the winningBid. */
    public static final int WINING_BID = 100;

    /** HostingSlotImpl instance to test against. */
    private HostingSlot hostingSlot = null;

    /** images. */
    private ImageInfo[] images = null;

    /** DomainImpl instance to test against. */
    private Domain domain = null;

    /** DomainTargetImpl instance to test against. */
    private DomainTarget[] domainTarget = null;

    /** brainTeaserIds array. */
    private long[] brainTeaserIds = null;

    /** the hostingStart date. */
    private Date hostingStart = null;

    /** the hostingEnd date. */
    private Date hostingEnd = null;

    /**
     * create instances.
     */
    protected void setUp() {
        //create image array
        images = new ImageInfo[1];
        images[0] = new ImageInfoImpl(ImageInfoImplUnitTests.ID, ImageInfoImplUnitTests.DOWNLOAD_ID,
                ImageInfoImplUnitTests.DESCRIPTION, ImageInfoImplUnitTests.APPROVED);

        //create Domain instance
        this.domain = new DomainImpl(DomainImplUnitTests.ID, DomainImplUnitTests.SPONSOR_ID,
                DomainImplUnitTests.DOMAIN_NAME, DomainImplUnitTests.APPROVED, images);

        //create brainTeaserIds array
        this.brainTeaserIds = new long[2];
        brainTeaserIds[0] = 1L;
        brainTeaserIds[1] = 2;
        //create DomainTarget array
        this.domainTarget = new DomainTarget[1];
        this.domainTarget[0] = new DomainTargetImpl(DomainTargetImplUnitTests.ID,
                DomainTargetImplUnitTests.SEQUENCE_NUMBER, DomainTargetImplUnitTests.URI_PATH,
                DomainTargetImplUnitTests.IDENTIFIER_TEXT, DomainTargetImplUnitTests.IDENTIFIER_HASH,
                DomainTargetImplUnitTests.CULE_IMAGE_ID);
        //create the date
        hostingStart = new Date();
        hostingEnd = new Date();

        //create the HostingSlotImpl instance
        this.hostingSlot = new HostingSlotImpl(ID, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER,
                domainTarget, WINING_BID, hostingStart, hostingEnd);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The HostingSlotImpl can not be instantiate.", hostingSlot);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget,
                WINING_BID, hostingStart, hostingEnd);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new HostingSlotImpl(new Long(0), domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER,
                domainTarget, WINING_BID, hostingStart, hostingEnd);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the puzzleId is not positive, iae expected.
     */
    public void testCtor_notPositivePuzzleId() {
        try {
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, new Long(0), SEQUENCE_NUMBER, domainTarget,
                WINING_BID, hostingStart, hostingEnd);
            fail("The puzzleId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the imageId is not positive, iae expected.
     */
    public void testCtor_notPositiveImageId() {
        try {
            new HostingSlotImpl(null, domain, 0, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget, WINING_BID,
                hostingStart, hostingEnd);
            fail("The imageId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the winningId is negative, iae expected.
     */
    public void testCtor_notPositiveWinningId() {
        try {
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget, -1,
                hostingStart, hostingEnd);
            fail("The winningId should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the SequenceNumber is negative, iae expected.
     */
    public void testCtor_negativeSequenceNumber() {
        try {
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, -1, domainTarget, WINING_BID,
                hostingStart, hostingEnd);
            fail("The SequenceNumber is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the domain is null, iae expected.
     */
    public void testCtor_nullDomain() {
        try {
            new HostingSlotImpl(null, null, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget,
                WINING_BID, hostingStart, hostingEnd);
            fail("The domain is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the brainTeaserIds is null, iae expected.
     */
    public void testCtor_nullBrainTeaserIds() {
        try {
            new HostingSlotImpl(null, domain, IMAGE_ID, null, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget, WINING_BID,
                hostingStart, hostingEnd);
            fail("The brainTeaserIds is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the brainTeaserIds contains not positive element, iae expected.
     */
    public void testCtor_notPositiveElementBrainTeaserIds() {
        try {
            this.brainTeaserIds = new long[2];
            brainTeaserIds[0] = 2L;
            brainTeaserIds[1] -= 2L;
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget,
                WINING_BID, hostingStart, hostingEnd);
            fail("The brainTeaserIds contains not positive element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the DomainTarget array is null, iae expected.
     */
    public void testCtor_nullDomainTargetArray() {
        try {
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, null, WINING_BID,
                hostingStart, hostingEnd);
            fail("The domainTarget array is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the DomainTarget array contains null element, iae expected.
     */
    public void testCtor_nullElementDomainTarget() {
        try {
            this.domainTarget = new DomainTarget[1];
            domainTarget[0] = null;
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget,
                WINING_BID, hostingStart, hostingEnd);
            fail("The DomainTarget array contains null element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the hostingStart is later than hostingEnd, iae expected.
     * @throws Exception into Junit
     */
    public void testCtor_hostingStartLate() throws Exception {
        try {
            Thread.sleep(1000);

            hostingStart = new Date();
            new HostingSlotImpl(null, domain, IMAGE_ID, brainTeaserIds, PUZZLE_ID, SEQUENCE_NUMBER, domainTarget,
                WINING_BID, hostingStart, hostingEnd);
            fail("The hostingStart is later than hostingEnd.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the shallow copy of the ctor.
     */
    public void testCtor_shallowCopy() {
        //test the brainTeaserIds shallow
        long[] inside = (long[]) TestHelper.getPrivateField(HostingSlotImpl.class, hostingSlot, "brainTeaserIds");
        assertFalse("Not the same array.", this.brainTeaserIds == inside);
        assertEquals("The array are the same.", this.brainTeaserIds[0], inside[0]);

        //test the DomainTarget shallow copy
        DomainTarget[] inTargets = (DomainTarget[]) TestHelper.getPrivateField(HostingSlotImpl.class, hostingSlot,
                "domainTargets");
        assertFalse("not the same array.", this.domainTarget == inTargets);
        assertEquals("Shallow copy.", this.domainTarget[0], inTargets[0]);
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, hostingSlot.getId());
    }

    /**
     * simply verify the getPuzzleId method.
     */
    public void testGetPuzzleId() {
        assertEquals("The puzzleId to set is the not one get.", PUZZLE_ID, hostingSlot.getPuzzleId());
    }

    /**
     * simply verify the getWinningBid method.
     */
    public void testGetWinningBidId() {
        assertEquals("The winningBid to set is the not one get.", WINING_BID, hostingSlot.getWinningBid());
    }

    /**
     * simply verify the getSequenceNumber method.
     */
    public void testGetSequenceNumber() {
        assertEquals("The SequenceNumber to set is the not one get.", SEQUENCE_NUMBER, hostingSlot.getSequenceNumber());
    }

    /**
     * simply verify the getImageId method.
     */
    public void testGetImageId() {
        assertEquals("The imageId to set is the not one get.", IMAGE_ID, hostingSlot.getImageId());
    }

    /**
     * simply verify the getDomain method.
     */
    public void testGetDomain() {
        assertEquals("The domain to set is the not one get.", domain, hostingSlot.getDomain());
    }

    /**
     * simply verify the getBrainTeaserIds() method.
     */
    public void testGetBrainTeaserIds() {
        long[] inside = (long[]) TestHelper.getPrivateField(HostingSlotImpl.class, hostingSlot, "brainTeaserIds");
        assertFalse("Not the same array.", this.hostingSlot.getBrainTeaserIds() == inside);
        assertEquals("The array are the same.", this.hostingSlot.getBrainTeaserIds()[0], inside[0]);
    }

    /**
     * simply verify the getDomainTargets method.
     */
    public void testGetDomainTargets() {
        DomainTarget[] targets = this.hostingSlot.getDomainTargets();
        DomainTarget[] inTargets = (DomainTarget[]) TestHelper.getPrivateField(HostingSlotImpl.class, hostingSlot,
                "domainTargets");
        assertFalse("not the same array.", targets == inTargets);
        assertEquals("Shallow copy.", targets[0], inTargets[0]);
    }

    /**
     * simply verify the getHostingStart method.
     */
    public void testGetHostingStart() {
        assertEquals("The hostingStart to set is the not one get.", hostingStart, hostingSlot.getHostingStart());
    }

    /**
     * simply verify the getHostingEnd method.
     */
    public void testGetHostingEnd() {
        assertEquals("The hostingEnd to set is the not one get.", hostingEnd, hostingSlot.getHostingEnd());
    }
}
