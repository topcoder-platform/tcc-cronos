/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.ImageInfo;
import com.orpheus.game.persistence.entities.DomainImpl;
import com.orpheus.game.persistence.entities.ImageInfoImpl;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>DomainImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class DomainImplAccTests extends TestCase {
    /**
     * .
     * The DomainImpl instance to test
     */
    private DomainImpl domain = null;

    /**
     * The array of image instances used in test cases.
     */
    private ImageInfo[] images = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        images = new ImageInfo[1];
        images[0] = new ImageInfoImpl(new Long(1), 1,
                "desc", new Boolean(true));

        this.domain = new DomainImpl(new Long(1), 1, "domain", new Boolean(true), images);
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected id.", 1, domain.getId().longValue());
        assertEquals("Not the expected name.", "domain", domain.getDomainName());
        assertEquals("Not the expected images length.", 1, domain.getImages().length);
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        this.domain = new DomainImpl(new Long(2), 1, "test", new Boolean(false), images);

        assertEquals("Not the expected id.", 2, domain.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getName() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetName() throws Exception {
        this.domain = new DomainImpl(new Long(2), 1, "test", new Boolean(false), images);
        assertEquals("Not the expected name.", "test", domain.getDomainName());
    }

    /**
     * <p>
     * Accuracy test of the getImageId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetImages() throws Exception {
        this.domain = new DomainImpl(new Long(2), 1, "test", new Boolean(false), images);
        assertEquals("Not the expected image length.", 1, domain.getImages().length);
        assertEquals("Not the expected image id.", 1, domain.getImages()[0].getId().longValue());
    }
}
