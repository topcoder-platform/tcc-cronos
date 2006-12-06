/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.entities;

import com.orpheus.game.persistence.BallColor;
import com.orpheus.game.persistence.Domain;
import com.orpheus.game.persistence.DomainTarget;
import com.orpheus.game.persistence.Game;
import com.orpheus.game.persistence.HostingBlock;
import com.orpheus.game.persistence.HostingSlot;
import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.TestHelper;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>
 * Unit test case for class <code>GameImpl</code>.
 * </p>
 * @author waits
 * @version 1.0
 */
public class GameImplUnitTests extends TestCase {
    /** represents the Id constants for testing. */
    public static final Long ID = new Long(1);

    /** KeyCount constants. */
    public static final int KEY_COUNT = 2;

    /** the startDate. */
    private Date startDate = null;

    /** the endDate. */
    private Date endDate = null;

    /** GameImpl  instance to test against. */
    private Game game = null;

    /** color instance. */
    private BallColor color = null;

    /** blocks. */
    private HostingBlock[] blocks = null;

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
     * create instance.
     */
    protected void setUp() {
        color = new BallColorImpl(BallColorImplUnitTests.ID, BallColorImplUnitTests.NAME,
                BallColorImplUnitTests.IMAGE_ID);
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
                domainTarget, HostingSlotImplUnitTests.WINING_BID, null, null);

        //HostingBlock instance
        this.blocks = new HostingBlock[1];
        blocks[0] = new HostingBlockImpl(HostingBlockImplTests.ID, HostingBlockImplTests.SEQUENCE_NUMBER, slots,
                HostingBlockImplTests.MAX_HOSTING_TIME_PER_SLOT);

        startDate = new Date();
        //create GameImpl instance
        game = new GameImpl(ID, color, KEY_COUNT, startDate, endDate, blocks);
    }

    /**
     * test the ctor, simply verify the instance.
     */
    public void testCtor() {
        assertNotNull("The GameImpl can not be instantiate.", game);
    }

    /**
     * test the ctor, the id can be null, it is an accuracy test case.
     */
    public void testCtor_nullId() {
        try {
            new GameImpl(null, color, KEY_COUNT, startDate, endDate, blocks);
        } catch (Exception e) {
            fail("The id can be null.");
        }
    }

    /**
     * test the ctor, the id is not positive, iae expected.
     */
    public void testCtor_notPositiveId() {
        try {
            new GameImpl(new Long(0), color, KEY_COUNT, startDate, endDate, blocks);
            fail("The id should be positive.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the keyCount is negative, iae expected.
     */
    public void testCtor_negativeSequenceNumber() {
        try {
            new GameImpl(null, color, -1, startDate, endDate, blocks);
            fail("The keyCount is negative.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the blocks is null, iae expected.
     */
    public void testCtor_nullBlockArray() {
        try {
            new GameImpl(null, color, KEY_COUNT, startDate, endDate, null);
            fail("The HostingBlock is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the color is null, iae expected.
     */
    public void testCtor_nullColor() {
        try {
            new GameImpl(ID, null, KEY_COUNT, startDate, endDate, blocks);
            fail("The BolorColor is null.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the block is null, iae expected.
     */
    public void testCtor_nullElementBlockArray() {
        try {
            this.blocks = new HostingBlock[1];
            this.blocks[0] = null;

            new GameImpl(null, color, KEY_COUNT, startDate, endDate, blocks);
            fail("The HostingBlock contains null element.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test the ctor, the startDate is later than endDate, iae expected.
     *
     * @throws Exception into Junit
     */
    public void testCtor_startDateLate() throws Exception {
        try {
            endDate = new Date();
            Thread.sleep(1000);
            startDate = new Date();

            new GameImpl(null, color, KEY_COUNT, startDate, endDate, blocks);
            fail("The startDate is later than endDate.");
        } catch (IllegalArgumentException e) {
            //good
        }
    }

    /**
     * test ctor, the shallow copy.
     */
    public void testCtor_shallowCopy() {
        HostingBlock[] insides = (HostingBlock[]) TestHelper.getPrivateField(GameImpl.class, game, "blocks");
        assertFalse("not the same array.", this.blocks == insides);
        assertEquals("Shallow copy.", this.blocks[0], insides[0]);
    }

    /**
     * simply verify the getId method.
     */
    public void testGetId() {
        assertEquals("The id to set is the not one get.", ID, game.getId());
    }

    /**
     * simply verify the getKeyCount method.
     */
    public void testGetKeyCount() {
        assertEquals("The keyCount to set is the not one get.", KEY_COUNT, game.getKeyCount());
    }

    /**
     * simply verify the getBallColor method.
     */
    public void testGetBallColor() {
        assertEquals("The ballColor to set is the not one get.", this.color, game.getBallColor());
    }

    /**
     * simply verify the getStartDate method.
     */
    public void testGetStartDate() {
        assertEquals("The startDate to set is the not one get.", this.startDate, game.getStartDate());
    }

    /**
     * simply verify the getEndDate method.
     */
    public void testGetEndDate() {
        assertEquals("The endDate to set is the not one get.", this.endDate, game.getEndDate());
    }

    /**
     * simply verify the getName method.
     */
    public void testGetName() {
        assertEquals("The name to set is the not one get.", this.color.getName() + game.getId(), game.getName());
    }

    /**
     * simply verify the getDomainTargets method.
     */
    public void testGetDomainTargets() {
        HostingBlock[] insides = (HostingBlock[]) TestHelper.getPrivateField(GameImpl.class, game, "blocks");
        assertFalse("not the same array.", this.game.getBlocks() == insides);
        assertEquals("Shallow copy.", this.game.getBlocks()[0], insides[0]);
    }
}
