/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;


/**
 * <p>
 * Static factory for supplying the game data DAO instance to the EJBs. It uses synchronized lazy instantiation to
 * get the initial instance of the DAO. Supports the creation of the GameDataDAO.
 * </p>
 * <p>This class has five configuration parameters.
 *
 * <ul>
 *   <li><strong>specNamespace</strong> (required): Namespace to use with the ConfigManagerSpecificationFactory</li>
 *   <li><strong>gameDataDAO</strong> (required): Key for the GameDataDAO to pass to ObjectFactory</li>
 * </ul>
 * </p>
 * <p>
 * <b>Thread Safety:</b>This class is mutable and thread-safe.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class GameDataDAOFactory {
    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String GAME_DATA_DAO = "gameDataDAO";

    /**
     * <p>
     * represents the property name in the config file.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the namespace used to retrieve the dao instance in the getter method.
     * </p>
     */
    private static final String NAMESPACE = "com.orpheus.game.persistence.GameDataDAOFactory";

    /**
     * <p>
     * Represents the game data&nbsp; DAO instance. Created when the instance is first requested, and will not change
     * or be null after that.
     * </p>
     */
    private static GameDataDAO gameDataDAO;

    /**
     * <p>
     * Empty constructor.
     * </p>
     */
    private GameDataDAOFactory() {
    }

    /**
     * <p>
     * Obtains the GameDataDAO instance. If it does not exist yet, it will be created using ConfigManager and
     * ObjectFactory. Synchronized to avoid threading&nbsp; issues with lazy instantiation.
     * </p>
     *
     * @return GameDataDAO instance
     *
     * @throws InstantiationException If there is an error with construction
     */
    public static synchronized GameDataDAO getGameDataDAO()
        throws InstantiationException {
        if (gameDataDAO == null) {
            try {
                ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(Helper.getString(
                                NAMESPACE, SPEC_NAMESPACE, true)));

                gameDataDAO = (GameDataDAO) factory.createObject(Helper.getString(NAMESPACE, GAME_DATA_DAO, true));
            } catch (InstantiationException e) {
                throw e;
            } catch (Exception e) {
                throw new InstantiationException("Error in create GameDataDAO instance.", e);
            }
        }

        return gameDataDAO;
    }
}
