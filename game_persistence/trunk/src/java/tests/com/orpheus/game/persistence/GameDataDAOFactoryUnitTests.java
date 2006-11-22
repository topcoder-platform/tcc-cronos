/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for class <code>GameDataDAOFactory</code>.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataDAOFactoryUnitTests extends TestCase {
    /**
     * setup the environment.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        TestHelper.clearConfigManager();
        TestHelper.addConfigFile(TestHelper.DBFACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.OBJECT_FACTORY_CONFIG_XML);
        TestHelper.addConfigFile(TestHelper.GAME_PERSISTENCE_CONFIG_FILE);
    }

    /**
     * <p>
     * Test the gameDataDAO method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetGameDataDAO() throws Exception {
        GameDataDAO dao = GameDataDAOFactory.getGameDataDAO();
        assertNotNull("The dao fails to instantiate.", dao);
    }

    /**
     * remove the environment.
     *
     * @throws Exception into Junit
     */
    protected void tearDown() throws Exception {
        TestHelper.clearConfigManager();
    }
}
