/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import junit.framework.TestCase;

import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;


/**
 * Test case for GameOperationLogicUtility.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameOperationLogicUtilityTest extends TestCase {
    /** Represents GameOperationLogicUtility instance used in this test. */
    private GameOperationLogicUtility golu;

    /**
     * Test method for getDataStoreKey(), the return result is expected to be the value set in config.
     */
    public void testGetDataStoreKey() {
        assertEquals("getDataStoreKey() should return", "Data_store", golu.getDataStoreKey());
    }

    /**
     * Test method for getGameDataLocalHome(), the return result is expected to be the value set in config.
     */
    public void testGetGameDataLocalHome() throws Exception {
        assertTrue("getGameDataLocalHome() should return instance of GameDataLocalHome",
            golu.getGameDataLocalHome() instanceof GameDataLocalHome);
    }

    /**
     * Test method for getGameDataRemoteHome(), the return result is expected to be the value set in config.
     */
    public void testGetGameDataRemoteHome() throws Exception {
        assertTrue("getGameDataRemoteHome() should return instance of GameDataHome",
            golu.getGameDataRemoteHome() instanceof GameDataHome);
    }

    /**
     * Test method for getGameManagerKey(), the return result is expected to be the value set in config.
     */
    public void testGetGameManagerKey() {
        assertEquals("getGameManagerKey() should return", "GameDataManager", golu.getGameManagerKey());
    }

    /**
     * Test getInstance() with valid config.
     */
    public void testGetInstance() {
        assertNotNull("GameOperationLogicUtility should be instantiated successfully with valid config", golu);
    }

    /**
     * Test method for getPuzzleTypeSourceKey(), the return result is expected to be the value set in config.
     */
    public void testGetPuzzleTypeSourceKey() {
        assertEquals("getPuzzleTypeSourceKey() should return", "PuzzleTypeSource", golu.getPuzzleTypeSourceKey());
    }

    /**
     * Test method for isUseLocalInterface(), the return result is expected to be the value set in config.
     */
    public void testIsUseLocalInterface() {
        assertEquals("isUseLocalInterface() should return", true, golu.isUseLocalInterface());
    }

    /**
     * Sets up test.
     *
     * @throws Exception to Junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        tearDown();
        TestHelper.loadConfig();
        JNDIHelper.initJNDI();
        golu = GameOperationLogicUtility.getInstance();
    }

    /**
     * Clears test environment.
     *
     * @throws Exception to Junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        /*removes all namespaces */
        TestHelper.unloadConfig();
    }
}
