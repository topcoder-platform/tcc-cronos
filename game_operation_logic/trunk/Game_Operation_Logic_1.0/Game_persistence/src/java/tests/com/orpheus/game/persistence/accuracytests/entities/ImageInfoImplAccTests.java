/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.ImageInfoImpl;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>ImageInfoImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class ImageInfoImplAccTests extends TestCase {
    /**
     * .
     * The ImageInfoImpl instance to test
     */
    private ImageInfoImpl image = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        image = new ImageInfoImpl(new Long(1), 1, "desc", new Boolean(true));
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected id.", 1, image.getId().longValue());
        assertEquals("Not the expected download id.", 1, image.getDownloadId());
        assertEquals("Not the expected description.", "desc", image.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        image = new ImageInfoImpl(new Long(2), 2, "test", new Boolean(false));
        assertEquals("Not the expected id.", 2, image.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getDownloadId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetDownloadId() throws Exception {
        image = new ImageInfoImpl(new Long(2), 2, "test", new Boolean(false));
        assertEquals("Not the expected download id.", 2, image.getDownloadId());
    }

    /**
     * <p>
     * Accuracy test of the getDescription() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetDescription() throws Exception {
        image = new ImageInfoImpl(new Long(2), 2, "test", new Boolean(false));
        assertEquals("Not the expected description.", "test", image.getDescription());
    }
}
