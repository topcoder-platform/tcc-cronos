/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import com.orpheus.administration.AdministrationException;
import com.orpheus.administration.AdministrationManager;
import com.orpheus.administration.ConfigurationException;
import com.topcoder.util.puzzle.PuzzleTypeSource;

/**
 * Dummy implementation of AdministrationManager for test proposes only.
 * 
 * @author kr00tki
 * @version 1.0
 */
public class DummyAdministrationManager extends AdministrationManager {
    /**
     * The id of the block to initialize.
     */
    private long blockId;

    /**
     * <p>
     * Constructor stub.
     * </p>
     */
    public DummyAdministrationManager() throws ConfigurationException {
        this(new MockPuzzleTypeSource(), "namespace");

    }

    /**
     * <p>
     * Constructor stub. Simply propagates arguments to the super class.
     * </p>
     */
    public DummyAdministrationManager(PuzzleTypeSource puzzleTypeSource, String namespace)
        throws ConfigurationException {
        super(puzzleTypeSource, namespace);

    }
    
    /**
     * Sets the given blockId to internal field.
     * 
     * @param the id of clock to initialize.
     * @throws AdministrationException never.
     */
    public void initializeSlotsForBlock(long blockId) throws AdministrationException {
        this.blockId = blockId;
    }

    /**
     * Returns the id of the block.
     * 
     * @return the block id.
     */
    public long getBlockId() {
        return blockId;
    }
    
    

}
