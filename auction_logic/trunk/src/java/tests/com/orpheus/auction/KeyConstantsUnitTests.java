/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction;

import junit.framework.TestCase;

/**
 * <p>
 * Unit test cases for the KeyConstants class.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class KeyConstantsUnitTests extends TestCase {
    /**
     * <p>
     * Tests that constants in KeyConstants class are properly retrieved from the configuration.
     * </p>
     * @throws Exception to JUnit.
     */
    public void test1() throws Exception {
        UnitTestsHelper.loadConfig(UnitTestsHelper.CONFIG_FILE);
        assertEquals("The constant value is incorrect", "auction_manager",
            KeyConstants.AUCTION_MANAGER_KEY);
        assertEquals("The constant value is incorrect", "game_data_manager",
            KeyConstants.GAME_DATA_MANAGER_KEY);
        assertEquals("The constant value is incorrect", "administration_manager",
            KeyConstants.ADMINISTRATION_MANAGER_KEY);
        UnitTestsHelper.clearConfig();
    }
}
