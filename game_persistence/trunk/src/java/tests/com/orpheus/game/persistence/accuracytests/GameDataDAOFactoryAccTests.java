/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests;

import junit.framework.TestCase;
import com.orpheus.game.persistence.GameDataDAO;
import com.orpheus.game.persistence.GameDataDAOFactory;

/**
 * Accuracy test cases for <code>GameDataDAOFactory</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class GameDataDAOFactoryAccTests extends TestCase{

    /**
     * Setup for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void setUp() throws Exception {
        AccuracyHelper.clearAllConfigurationNS();
        AccuracyHelper.loadBaseConfig();
    }

    /**
     * Clean up for each test cases.
     *
     * @throws Exception into Junit
     */
    protected void tearDown()throws Exception{
        AccuracyHelper.clearAllConfigurationNS();
    }

    /**
     * <p>
     * Accuracy test of the <code>gameDataDAO</code> method.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public void testGetGameDataDAO() throws Exception {
        GameDataDAO dao = GameDataDAOFactory.getGameDataDAO();
        assertNotNull("Cannot get GameDataDAO.", dao);
    }
}
