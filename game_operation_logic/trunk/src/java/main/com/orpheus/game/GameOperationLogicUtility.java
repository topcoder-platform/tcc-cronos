/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.GameDataLocalHome;

import com.topcoder.naming.jndiutility.JNDIUtils;

import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

import javax.naming.Context;
import javax.naming.NamingException;


/**
 * A utility class whose methods will be used by all the handlers and results in this component. The variable values
 * are loaded from ConfigurationManager. This class is thread safe since it's immutable.  This class is used by the
 * handler classes as well as MessagePollResult.
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class GameOperationLogicUtility {
    /** Represents property name where useLocalInterface is configured in ConfigManager. */
    private static final String PROP_USE_LOCAL_INTERFACE = "use_local_interface";

    /** Represents property name where puzzleTypeSourceKey is configured in ConfigManager. */
    private static final String PROP_PUZLE_TYPE_SOURCE = "puzzle_type_source";

    /** Represents property name where gameDataManagerKey is configured in ConfigManager. */
    private static final String PROP_GAME_DATA_MANAGER = "game_data_manager";

    /** Represents property name where gameDataEJBRemoteHomeName is configured in ConfigManager. */
    private static final String PROP_GAME_DATA_REMOTEL = "game_data_remote";

    /** Represents property name where gameDAtaEJBLocalHomeName is configured in ConfigManager. */
    private static final String PROP_GAME_DATA_LOCAL = "game_data_local";

    /** Represents property name where dataStoreKey is configured in ConfigManager. */
    private static final String PROP_DATA_STORE = "data_store";

    /** Represents property name where contextName is configured in ConfigManager. */
    private static final String PROP_CONTEXT_NAME = "context_name";

    /** Represents default namespace of this class. */
    private static final String DEFAULT_NAMESPACE = "com.orpheus.game.GameOperationLogicUtility";

    /** The unique GameOperationLogicUtility instance created in the getInstacne() method. */
    private static GameOperationLogicUtility instance;

    /** The initial context name used by JNDIUtils to search for the ejb home instance. */
    private final String contextName;

    /** The key used to get DataStore from the SevletContext attribute. */
    private final String dataStoreKey;

    /** the GameData EJB local home name used by JNDIUtils to search for the ejb local home instance. */
    private final String gameDAtaEJBLocalHomeName;

    /** the GameData EJB remote home name used by JNDIUtils to search for the ejb remote home instance. */
    private final String gameDataEJBRemoteHomeName;

    /** The key used to get GameDataManager from the SevletContext attribute. */
    private final String gameDataManagerKey;

    /** The key used to get PuzzelTypeSource from the SevletContext attribute. */
    private final String puzzleTypeSourceKey;

    /** The value indicates if the local or remote EJB interface is used. */
    private final boolean useLocalInterface;

    /**
     * This private constructor block used to initialize the above static variables centrally.
     *
     * @throws GameOperationConfigException if failed to load the configured values or they are invalid.
     */
    private GameOperationLogicUtility() throws GameOperationConfigException {
        ConfigManager cm = ConfigManager.getInstance();
        contextName = getPropertyAsRequired(cm, PROP_CONTEXT_NAME);
        dataStoreKey = getPropertyAsRequired(cm, PROP_DATA_STORE);
        gameDAtaEJBLocalHomeName = getPropertyAsRequired(cm, PROP_GAME_DATA_LOCAL);

        gameDataEJBRemoteHomeName = getPropertyAsRequired(cm, PROP_GAME_DATA_REMOTEL);
        gameDataManagerKey = getPropertyAsRequired(cm, PROP_GAME_DATA_MANAGER);
        puzzleTypeSourceKey = getPropertyAsRequired(cm, PROP_PUZLE_TYPE_SOURCE);

        String useLocalInterfaceValue = getPropertyAsRequired(cm, PROP_USE_LOCAL_INTERFACE);

        if (useLocalInterfaceValue.equalsIgnoreCase("true") || useLocalInterfaceValue.equalsIgnoreCase("false")) {
            useLocalInterface = Boolean.valueOf(useLocalInterfaceValue).booleanValue();
        } else {
            throw new GameOperationConfigException("property [" + PROP_USE_LOCAL_INTERFACE +
                "] must be either 'true' or 'false', but is [" + useLocalInterfaceValue + "]");
        }
    }

    /**
     * Obtains the GameOperationLogicUtility instance with singleton pattern. This method is thread-safe.
     *
     * @return instance of GameOperationLogicUtility
     *
     * @throws GameOperationConfigException if GameOperationLogicUtility is not configured properly.
     */
    public static GameOperationLogicUtility getInstance() {
        if (instance == null) {
            synchronized (GameOperationLogicUtility.class) {
                if (instance == null) {
                    instance = new GameOperationLogicUtility();
                }
            }
        }

        return instance;
    }

    /**
     * Get the data store key name.
     *
     * @return the data store key name
     */
    public String getDataStoreKey() {
        return this.dataStoreKey;
    }

    /**
     * Get the GameDataLocalHome.
     *
     * @return the GameDataLocalHome instance obtained from JNDI
     *
     * @throws NamingException if a naming exception is encountered
     * @throws ConfigManagerException if JNDIUtils is not configured properly
     */
    public GameDataLocalHome getGameDataLocalHome() throws ConfigManagerException, NamingException {
        Context context = JNDIUtils.getContext(contextName);

        return (GameDataLocalHome) JNDIUtils.getObject(context, this.gameDAtaEJBLocalHomeName);
    }

    /**
     * Get the GameDataHome Impl.
     *
     * @return the GameDataHome instance obtained from JNDI
     *
     * @throws NamingException if a naming exception is encountered
     * @throws ConfigManagerException if JNDIUtils is not configured properly
     */
    public GameDataHome getGameDataRemoteHome() throws ConfigManagerException, NamingException {
        Context context = JNDIUtils.getContext(contextName);

        return (GameDataHome) JNDIUtils.getObject(context, this.gameDataEJBRemoteHomeName);
    }

    /**
     * Get the game manager key name.
     *
     * @return the game manager key name
     */
    public String getGameManagerKey() {
        return this.gameDataManagerKey;
    }

    /**
     * Get the puzzle type source key name.
     *
     * @return the puzzle type source key name
     */
    public String getPuzzleTypeSourceKey() {
        return this.puzzleTypeSourceKey;
    }

    /**
     * Get the userLocalInterface variable.
     *
     * @return the userLocalInterface variable
     */
    public boolean isUseLocalInterface() {
        return this.useLocalInterface;
    }

    /**
     * Obtains string value of the given property from specified namespace. If the property does not exist,
     * GameOperationConfigException will be thrown.
     *
     * @param cm ConfigManager
     * @param propertyName the name of the required property from which value will be obtained.
     *
     * @return string value of the property
     *
     * @throws GameOperationConfigException if the property does not exist
     */
    private String getPropertyAsRequired(ConfigManager cm, String propertyName)
        throws GameOperationConfigException {
        String value;

        try {
            value = cm.getString(DEFAULT_NAMESPACE, propertyName);
        } catch (UnknownNamespaceException e) {
            String msg = "namespace [" + DEFAULT_NAMESPACE + "] is unknown";
            throw new GameOperationConfigException(msg, e);
        }

        if ((value == null) || (value.trim().length() == 0)) {
            throw new GameOperationConfigException("property [" + propertyName + "] is required in namespace [" +
                DEFAULT_NAMESPACE + "]");
        }

        return value;
    }
}
