/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.TestHelper;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for <code>HostingBlockImpl</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HostingBlockImplTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);

    /** represents the sequenceNumber constants for testing. */
    public static final int SEQUENCE_NUMBER = 1000;

    /** the maxHostingTimePerSlot constants. */
    public static final int MAX_HOSTING_TIME_PER_SLOT = 10000;

    /** HostingBlockImpl instance for testing. */
    private HostingBlock hostingBlock = null;

    /** HostingSlotImpl instance to test against. */
    private HostingSlot[] slots = null;

    /** images. */
    private ImageInfo[] images = null;

    /** DomainImpl instance to test against. */
    private Domain domain = null;

    /** DomainTargetImpl instance to test against. */
    private DomainTarget[] domainTarget = null;

    /** brainTeaserIds array. */
    private long[] brainTeaserIds = null;

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
        brainTeaserIds[1] = 2L;
        //create DomainTarget array
        this.domainTarget = new DomainTarget[1];
        this.domainTarget[0] = new DomainTargetImpl(DomainTargetImplUnitTests.ID,
                DomainTargetImplUnitTests.SEQUENCE_NUMBER, DomainTargetImplUnitTests.URI_PATH,
                DomainTargetImplUnitTests.IDENTIFIER_TEXT, DomainTargetImplUnitTests.IDENTIFIER_HASH,
                DomainTargetImplUnitTests.CULE_IMAGE_ID);

        //create the HostingSlotImpl array 
        slots = new HostingSlotImpl[1];
        this.slots[0] = new HostingSlotImpl(HostingSlotImplUnitTests.ID, domain, HostingSlotImplUnitTests.IMAGE_ID,
                brainTeaserIds, HostingSlotImplUnitTests.PUZZLE_ID, HostingSlotImplUnitTests.SEQUENCE_NUMBER,
                domainTarget, HostingSlotImplUnitTests.WINING_BID, null, null, ID.longValue());

        this.hostingBlock = new HostingBlockImpl(ID, SEQUENCE_NUMBER, slots, MAX_HOSTING_TIME_PER_SLOT);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The HostingBlockImpl can not be instantiate.", hostingBlock);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new HostingBlockImpl(null, SEQUENCE_NUMBER, slots, MAX_HOSTING_TIME_PER_SLOT);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new HostingBlockImpl(new Long(0), SEQUENCE_NUMBER, slots, MAX_HOSTING_TIME_PER_SLOT);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the SequenceNumber is negative, iae expected.
     */
    public void testCtor_negativeSequenceNumber() {
        try {
            new HostingBlockImpl(null, -1, slots, MAX_HOSTING_TIME_PER_SLOT);
            fail("The SequenceNumber is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the maxHostingTimePerSlot is negative, iae expected.
     */
    public void testCtor_negativeMaxHostingTimePerSlot() {
        try {
            new HostingBlockImpl(null, SEQUENCE_NUMBER, slots, -1);
            fail("The maxHostingTimePerSlot is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the slots is null, iae expected.
     */
    public void testCtor_nullSlotArray() {
        this.hostingBlock = new HostingBlockImpl(null, SEQUENCE_NUMBER, null, MAX_HOSTING_TIME_PER_SLOT);
        assertNull(this.hostingBlock.getSlots());
            
    }

    /**
     * test the ctor, the slots is null, iae expected.
     */
    public void testCtor_nullElementSlotArray() {
        try {
            this.slots = new HostingSlotImpl[1];
            this.slots[0] = null;
            new HostingBlockImpl(null, SEQUENCE_NUMBER, slots, MAX_HOSTING_TIME_PER_SLOT);
            fail("The Slots contains null element.");
        } catch (Exception e) {
            //good
        }
    }

    /**
     * verify the shallow copy.
     */
    public void testCtor_shallowCopy() {
        //test the HostingSlot shallow copy
        HostingSlot[] inSlots = (HostingSlot[]) TestHelper.getPrivateField(HostingBlockImpl.class, hostingBlock,
                "slots");
        assertFalse("not the same array.", this.slots == inSlots);
        assertEquals("Shallow copy.", this.slots[0], inSlots[0]);
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, hostingBlock.getId());
    }

    /**
     * simply verify the getSequenceNumber method.
     */
    public void testGetSequenceNumber() {
        assertEquals("The SequenceNumber to set is the not one get.", SEQUENCE_NUMBER,
            hostingBlock.getSequenceNumber());
    }

    /**
     * simply verify the getMaxHostingTimePerSlot method.
     */
    public void testGetMaxHostingTimePerSlot() {
        assertEquals("The MaxHostingTimePerSlot to set is the not one get.", MAX_HOSTING_TIME_PER_SLOT,
            hostingBlock.getMaxHostingTimePerSlot());
    }

    /**
     * simply verify the getSlots method.
     */
    public void testGetSlots() {
        //test the HostingSlot shallow copy
        HostingSlot[] inSlots = (HostingSlot[]) TestHelper.getPrivateField(HostingBlockImpl.class, hostingBlock,
                "slots");
        assertFalse("not the same array.", this.hostingBlock.getSlots() == inSlots);
        assertEquals("Shallow copy.", this.hostingBlock.getSlots()[0], inSlots[0]);
    }
}
