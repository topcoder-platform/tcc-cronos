/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.failuretests;

import com.orpheus.administration.AdministrationException;
import com.orpheus.administration.AdministrationManager;
import com.topcoder.util.auction.ConfigurationException;
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
     * Whether an exception should be thrown.
     */
    private boolean failed = false;

    /**
     * <p>
     * Constructor stub. Simply propagates arguments to the super class.
     * </p>
     * @param puzzleTypeSource
     *            ignore
     * @param namespace
     *            ignore
     * @throws ConfigurationException
     *             ignore
     */
    public MockAdministrationManager(PuzzleTypeSource puzzleTypeSource, String namespace)
        throws ConfigurationException {
        super(puzzleTypeSource, namespace);
    }

    /**
     * Sets whether an exception should be thrown.
     * @param failed
     *            whether an exception should be thrown
     */
    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    /**
     * <p>
     * Method stub. Saves block id.
     * </p>
     * @param blockId
     *            ignore
     * @throws AdministrationException
     *             ignore
     */
    public void initializeSlotsForBlock(long blockId) throws AdministrationException {
        if (failed) {
            throw new AdministrationException("MockAdministrationManager");
        }

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
