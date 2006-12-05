/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import java.util.Arrays;

import com.orpheus.game.persistence.HostingSlot;

import junit.framework.TestCase;

/**
 * <p>
 * Test the <code>HostingBlockImpl</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HostingBlockImplUnitTests extends TestCase {
    /**
     * will the ID of this block.
     *
     */
    private final Long id = new Long(234234);

    /**
     * will the sequence number of this block within its game.
     *
     */
    private final int sequenceNumber = 1234;

    /**
     * will an array of HostingSlot objects representing all the slots associated with this block.
     *
     */
    private final HostingSlot[] slots = new HostingSlot[] {
        new HostingSlotImpl(), new HostingSlotImpl()};

    /**
     * will the maximum hosting time for this block, in minutes.
     *
     */
    private final int maxHostingTimePerSlot = 18;

    /**
     * HostingBlockImpl instance to test.
     */
    private HostingBlockImpl target = null;

    /**
     * <p>
     * setUp() routine. Creates test HostingBlockImpl instance.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new HostingBlockImpl();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>HostingBlockImpl()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor_1_accuracy() throws Exception {
        assertNotNull("Failed to get HostingBlockImpl instance.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getId()</code> for
     * proper behavior. Verify that Id got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetId_1_accuracy() throws Exception {
        target.setId(id);
        assertEquals("id got incorrectly.", id, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setId(Long)</code> for
     * proper behavior. Verify that Id set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetId_1_accuracy() throws Exception {
        target.setId(id);
        assertEquals("id set incorrectly.", id, target.getId());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getMaxHostingTimePerSlot()</code> for
     * proper behavior. Verify that MaxHostingTimePerSlot got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetMaxHostingTimePerSlot_1_accuracy() throws Exception {
        target.setMaxHostingTimePerSlot(maxHostingTimePerSlot);
        assertEquals("maxHostingTimePerSlot got incorrectly.",
                maxHostingTimePerSlot, target.getMaxHostingTimePerSlot());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setMaxHostingTimePerSlot(int)</code> for
     * proper behavior. Verify that MaxHostingTimePerSlot set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetMaxHostingTimePerSlot_1_accuracy() throws Exception {
        target.setMaxHostingTimePerSlot(maxHostingTimePerSlot);
        assertEquals("maxHostingTimePerSlot set incorrectly.",
                maxHostingTimePerSlot, target.getMaxHostingTimePerSlot());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getSequenceNumber()</code> for
     * proper behavior. Verify that SequenceNumber got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSequenceNumber_1_accuracy() throws Exception {
        target.setSequenceNumber(sequenceNumber);
        assertEquals("SequenceNumber got incorrectly.", sequenceNumber, target
                .getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setSequenceNumber(int)</code> for
     * proper behavior. Verify that SequenceNumber set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetSequenceNumber_1_accuracy() throws Exception {
        target.setSequenceNumber(sequenceNumber);
        assertEquals("SequenceNumber set incorrectly.", sequenceNumber, target
                .getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getSlots()</code> for
     * proper behavior. Verify that Slots got correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetSlots_1_accuracy() throws Exception {
        target.setSlots(slots);
        assertTrue("Slots got incorrectly.", Arrays.equals(slots, target
                .getSlots()));
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>setSlots(HostingSlot[])</code> for
     * proper behavior. Verify that Slots set correctly.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testSetSlots_1_accuracy() throws Exception {
        target.setSlots(slots);
        assertTrue("Slots set incorrectly.", Arrays.equals(slots, target
                .getSlots()));
    }
}
