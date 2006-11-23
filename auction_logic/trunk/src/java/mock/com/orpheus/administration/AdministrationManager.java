/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import com.topcoder.util.auction.ConfigurationException;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * <p>
 * This class is referenced from the Auction Logic component but the Administration Logic component
 * where it is defined is still under development, so default implementation is used here. After the
 * Administration Logic will be released this class can be removed.
 * </p>
 * <p>
 * Auction Logic component uses initializeSlotsForBlock method. So only this method is left in the
 * default implementation. Also constructor is used to create instance and test Auction Logic
 * component. Note that in this default implementation constructor doesn't actually read the
 * configuration parameters and there are no configuration issues during testing currently. But with
 * real Administration Logic some problems can appear. In order to test component after removing
 * this class please ensure that configuration data is valid.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AdministrationManager {

    /**
     * <p>
     * Creates an AdministrationManager instance with given PuzzleTypeSource to use when generating
     * puzzles and brain teasers and set up with configuration values from the given namespace.
     * </p>
     * @param puzzleTypeSource PuzzleTypeSource to use when generating puzzles and brain teasers.
     * @param namespace namespace to load configuration details from.
     * @throws ConfigurationException if configuration values are missing or invalid.
     * @throws IllegalArgumentException if puzzleTypeSource is null, or if namespace is null or
     *         empty.
     */
    public AdministrationManager(PuzzleTypeSource puzzleTypeSource, String namespace)
            throws ConfigurationException {
    }

    /**
     * <p>
     * Initializes all the slots in the specified hosting block by generating minihunt targets,
     * brain teasers, and game-win puzzles for them. It will retrieve the slot ids for the given
     * block and call regeneratePuzzle(), regenerateBrainTeaser() and generateHuntTargets() for each
     * slot id.
     * </p>
     * @param blockId block id.
     * @throws AdministrationException if a checked exception prevents this method from completing
     *         normally
     */
    public void initializeSlotsForBlock(long blockId) throws AdministrationException {
    }
}
