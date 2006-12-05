/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;


/**
 * <p>
 * This is a simple utility class, which will aggregate some useful functionality.
 * The current functionality is basically a factory like method for easy creation of configured managers.
 * It is based on ObjectFactory configuration. Since there is not state this class is thread-safe.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.0
 */
public class GameDataUtility {
    /**
     * The constant String, which is the suffix of the namespace to create the ObjectFactory.
     */
    private static final String OBJECTFACTORY_NS_SUFFIX = "_ObjectFactory";

    /**
     * <p>
     * Private constructor to prevent instantation.
     * </p>
     *
     */
    private GameDataUtility() {
    }

    /**
     * <p>
     * This is a convenience method for instantating configured GameDataManager instances.
     * Configuration here is assume to be with Object Factory and is based on the provided key.
     * </p>
     *
     * @param key Object Factory configured key for the instance
     * @return instance of GameDataManager
     * @throws IllegalArgumentException if the key is null or an empty trimmed String
     * @throws GameDataManagerConfigurationException if there are any issue with configuration.
     * Typically we will just rethrow stuff from the Object factory
     */
    public static GameDataManager getConfiguredGameDataManagerInstance(
        String key) throws GameDataManagerConfigurationException {
        return getConfiguredGameDataManagerInstance(GameDataManager.class.getName() + OBJECTFACTORY_NS_SUFFIX, key);
    }

    /**
     * <p>
     * This is a convenience method for instantating configured GameDataManager instances.
     * Configuration here is assume to be with Object Factory and is based on the provided key.
     * </p>
     *
     * @param namespace object factory namespace for the configuration
     * @param key Object Factory configured key for the instance
     * @return instance of GameDataManager
     * @throws IllegalArgumentException if the key or namespace is null or an empty trimmed String
     * @throws GameDataManagerConfigurationException if there are any issue with configuration.
     * Typically we will just rethrow stuff from the Object factory
     */
    public static GameDataManager getConfiguredGameDataManagerInstance(
        String namespace, String key)
        throws GameDataManagerConfigurationException {
        Helper.checkStringNotNullOrEmpty(namespace, "namespace");
        Helper.checkStringNotNullOrEmpty(key, "Key to get the GameDataManager");

        try {
            // create the object factory.
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(
                        namespace));

            return (GameDataManager) objectFactory.createObject(key);
        } catch (SpecificationConfigurationException e) {
            throw new GameDataManagerConfigurationException(
                "SpecificationConfigurationException occurs when create "
                + "the ObjectFactory.", e);
        } catch (IllegalReferenceException e) {
            throw new GameDataManagerConfigurationException(
                "IllegalReferenceException occurs when create "
                + "the ObjectFactory.", e);
        } catch (ClassCastException e) {
            throw new GameDataManagerConfigurationException(
                "ClassCastException occurs when get "
                + "the GameDataManager from ObjectFactory.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new GameDataManagerConfigurationException(
                "InvalidClassSpecificationException occurs when get "
                + "the GameDataManager from ObjectFactory.", e);
        }
    }
}
