/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.BallColorImpl;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>BallColorImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class BallColorImplAccTests extends TestCase {
    /**
     * .
     * The BallColorImpl instance to test
     */
    private BallColorImpl color = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        color = new BallColorImpl(new Long(1), "color", 1);
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected id.", 1, color.getId().longValue());
        assertEquals("Not the expected name.", "color", color.getName());
        assertEquals("Not the expected image id.", 1, color.getImageId());
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        color = new BallColorImpl(new Long(2), "test", 10);
        assertEquals("Not the expected id.", 2, color.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getName() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetName() throws Exception {
        color = new BallColorImpl(new Long(2), "test", 10);
        assertEquals("Not the expected name.", "test", color.getName());
    }

    /**
     * <p>
     * Accuracy test of the getImageId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetImageId() throws Exception {
        color = new BallColorImpl(new Long(2), "test", 10);
        assertEquals("Not the expected image id.", 10, color.getImageId());
    }
}
