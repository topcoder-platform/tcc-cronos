/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.orpheus.auction.accuracytests;

import junit.framework.TestCase;

import com.orpheus.auction.KeyConstants;

/**
 * Accuracy unit test for the {@link KeyConstants} class. 
 * 
 * @author kr00tki
 * @version 1.0
 */
public class KeyConstantsAccuracyTest extends TestCase {

    /**
     * Loads the configuration.
     * 
     * @throws to JUnit.
     */
    protected void setUp() throws Exception {
        TestHelper.loadConfig();
    }

    /**
     * Clears the configuration.
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfig();
    }

    /**
     * Tests the static variables {@link KeyConstants#ADMINISTRATION_MANAGER_KEY}, 
     * {@link KeyConstants#AUCTION_MANAGER_KEY} and {@link KeyConstants#GAME_DATA_MANAGER_KEY} and checks if
     * the values are properly loaded from file.
     *  
     * @throws Exception to JUnit.
     */
    public void testValues() throws Exception {
        assertEquals("Incorrect auction manager key", "auction_manager", KeyConstants.AUCTION_MANAGER_KEY);
        assertEquals("Incorrect game data key", "game_data_manager", KeyConstants.GAME_DATA_MANAGER_KEY);
        assertEquals("Incorrect administration manager key", "administration_manager", KeyConstants.ADMINISTRATION_MANAGER_KEY);
    }
    
}
