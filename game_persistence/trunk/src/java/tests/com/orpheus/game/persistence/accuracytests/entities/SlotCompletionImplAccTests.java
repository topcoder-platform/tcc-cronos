/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.SlotCompletionImpl;
import com.orpheus.game.persistence.accuracytests.AccuracyHelper;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>SlotCompletionImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class SlotCompletionImplAccTests extends TestCase {
    /**
     * .
     * The SlotCompletionImpl instance to test
     */
    private SlotCompletionImpl sc = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        sc = new SlotCompletionImpl(1, 1, AccuracyHelper.convertStringToDate("2006/1/1"), "key", 1);
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected slot id.", 1, sc.getSlotId());
        assertEquals("Not the expected player id.", 1, sc.getPlayerId());
        assertEquals("Not the expected image id.", 1, sc.getKeyImageId());
    }

    /**
     * <p>
     * Accuracy test of the getSlotId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetSlotId() throws Exception {
        sc = new SlotCompletionImpl(2, 2, AccuracyHelper.convertStringToDate("2006/1/1"), "key1", 2);
        assertEquals("Not the expected slot id.", 2, sc.getSlotId());
    }

    /**
     * <p>
     * Accuracy test of the getPlayerId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetPlayerId() throws Exception {
        sc = new SlotCompletionImpl(2, 2, AccuracyHelper.convertStringToDate("2006/1/1"), "key1", 2);
        assertEquals("Not the expected player id.", 2, sc.getPlayerId());
    }

    /**
     * <p>
     * Accuracy test of the getKeyImageId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetKeyImageId() throws Exception {
        sc = new SlotCompletionImpl(2, 2, AccuracyHelper.convertStringToDate("2006/1/1"), "key1", 2);
        assertEquals("Not the expected image id.", 2, sc.getKeyImageId());
    }

    /**
     * <p>
     * Accuracy test of the getKeyText() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetKeyText() throws Exception {
        sc = new SlotCompletionImpl(2, 2, AccuracyHelper.convertStringToDate("2006/1/1"), "key1", 2);
        assertEquals("Not the expected key text.", "key1", sc.getKeyText());
    }

    /**
     * <p>
     * Accuracy test of the getTimestamp() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetTimestamp() throws Exception {
        assertEquals("Not the expected timestamp.", AccuracyHelper.convertStringToDate("2006/1/1"), sc.getTimestamp());
    }
}
