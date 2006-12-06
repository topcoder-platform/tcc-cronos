/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.game.persistence.failuretests;

import com.orpheus.game.persistence.GameDataDAOFactory;
import com.orpheus.game.persistence.InstantiationException;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;

import junit.framework.TestCase;

/**
 * Test case for <code>GameDataDAOFactory</code>.
 *
 * @author assistant
 * @version 1.0
 */
public class GameDataDAOFactoryTest extends TestCase {

    /**
     * Set up the environment.
     */
    protected void setUp() throws Exception {
        super.setUp();
        FailureTestHelper.loadConfig();
    }

    /**
     * Clean up the environment.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        FailureTestHelper.unloadConfig();
    }

    /**
     * Test method for getGameDataDAO().
     * In this case, the namespace for this class in configuration manager is removed.
     *
     * @throws Exception to JUnit
     */
    public void testGetGameDataDAO_NoOFNamespace() throws Exception {
        try {
            ConfigManager cm = ConfigManager.getInstance();
            cm.removeNamespace("com.orpheus.game.persistence.GameDataDAOFactory");
            GameDataDAOFactory.getGameDataDAO();
        } catch (InstantiationException e) {
            // should land here
        }
    }

}
