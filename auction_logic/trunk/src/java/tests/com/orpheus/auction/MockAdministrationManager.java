/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import com.orpheus.administration.AdministrationException;
import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.ConfigurationException;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * A mock extension of the AdministrationManager class. Allows to store the argument that is passed
 * to the initializeSlotsForBlock method.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAdministrationManager extends AdministrationManager {
    /**
     * <p>
     * Block id.
     * </p>
     */
    private long blockId = 0;

    /**
     * <p>
     * Constructor stub.
     * </p>
     */
    public MockAdministrationManager()
        throws ConfigurationException {
        this(new MockPuzzleTypeSource(), "namespace");

    }

    /**
     * <p>
     * Constructor stub. Simply propagates arguments to the super class.
     * </p>
     */
    public MockAdministrationManager(PuzzleTypeSource puzzleTypeSource, String namespace)
        throws ConfigurationException {
        super(puzzleTypeSource, namespace);

    }

    /**
     * <p>
     * Method stub. Saves block id.
     * </p>
     */
    public void initializeSlotsForBlock(long blockId) throws AdministrationException {
        this.blockId = blockId;
    }

    /**
     * <p>
     * Returns the block id, stored in the last call of recordWinningBids method.
     * </p>
     * @return the block id, stored in the last call of recordWinningBids method.
     */
    public long getLastBlockId() {
        return blockId;
    }
}
