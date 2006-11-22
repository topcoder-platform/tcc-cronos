/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests.entities;

import com.orpheus.game.persistence.entities.DomainTargetImpl;
import junit.framework.TestCase;

/**
 * Accuracy test cases for <code>DomainTargetImpl</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class DomainTargetImplAccTests extends TestCase {
    /**
     * The DomainTargetImpl instance to test
     */
    private DomainTargetImpl target = null;

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        this.target = new DomainTargetImpl(new Long(1), 1, "uri", "idText", "idHash", 1);
    }

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testConstructor() throws Exception {
        assertEquals("Not the expected id.", 1, target.getId().longValue());
        assertEquals("Not the expected uri path.", "uri", target.getUriPath());
        assertEquals("Not the expected identifier text.", "idText", target.getIdentifierText());
        assertEquals("Not the expected identifier hash.", "idHash", target.getIdentifierHash());
        assertEquals("Not the expected sequence number.", 1, target.getSequenceNumber());
        assertEquals("Not the expected clue image id.", 1, target.getClueImageId());
    }

    /**
     * <p>
     * Accuracy test of the getId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetId() throws Exception {
        this.target = new DomainTargetImpl(new Long(2), 2, "uri1", "idText1", "idHash1", 2);

        assertEquals("Not the expected id.", 2, target.getId().longValue());
    }

    /**
     * <p>
     * Accuracy test of the getUriPath() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetUriPath() throws Exception {
        this.target = new DomainTargetImpl(new Long(2), 2, "uri1", "idText1", "idHash1", 2);
        assertEquals("Not the expected uri path.", "uri1", target.getUriPath());
    }

    /**
     * <p>
     * Accuracy test of the getIdentifierText() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetIdentifierText() throws Exception {
        this.target = new DomainTargetImpl(new Long(2), 2, "uri1", "idText1", "idHash1", 2);
        assertEquals("Not the expected identifier text.", "idText1", target.getIdentifierText());
    }

    /**
     * <p>
     * Accuracy test of the getIdentifierHash() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetIdentifierHash() throws Exception {
        this.target = new DomainTargetImpl(new Long(2), 2, "uri1", "idText1", "idHash1", 2);
        assertEquals("Not the expected identifier hash.", "idHash1", target.getIdentifierHash());
    }

    /**
     * <p>
     * Accuracy test of the getSequenceNumber() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetSequenceNumber() throws Exception {
        this.target = new DomainTargetImpl(new Long(2), 2, "uri1", "idText1", "idHash1", 2);
        assertEquals("Not the expected sequence number.", 2, target.getSequenceNumber());
    }

    /**
     * <p>
     * Accuracy test of the getClueImageId() method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetClueImageId() throws Exception {
        this.target = new DomainTargetImpl(new Long(2), 2, "uri1", "idText1", "idHash1", 2);
        assertEquals("Not the expected clue image id.", 2, target.getClueImageId());
    }
}
