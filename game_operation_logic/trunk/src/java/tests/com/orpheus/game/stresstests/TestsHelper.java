/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.stresstests;

import java.util.Iterator;

import com.topcoder.util.config.ConfigManager;

/**
 * Helper class used for providing utility method for the development of tests *
 *
 * @author catcher
 * @version 1.0
 */
public final class TestsHelper {
    /** Represents the number of requests to process, used in stress tests. */
    public static final int MAX_COUNT = 500;

    /** the name space that should be removed. */
    public static final String[] NAMESPACES = { "com.orpheus.game.GameOperationLogicUtility" };

    /**
     * the config file ot the GameOperationLogicUtility
     */
    public static final String GameOperationLogicUtility = "stress/GameOperationLogicUtility.xml";

    /**
     * This is a utility class, so it shouldn't be instantiated.
     */
    private TestsHelper() {
    }

    /**
     * Load the configurations.
     * @throws Exception to invoker
     */
    public static void loadConfig() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        //cm.add("com.topcoder.util.config.ConfigManager","com/topcoder/util/config/ConfigManager.properties",ConfigManager.CONFIG_PROPERTIES_FORMAT);
        cm.add("com.topcoder.naming.jndiutility","stress/jndiutility/JNDIUtils.properties",ConfigManager.CONFIG_PROPERTIES_FORMAT);
        //cm.add("FrontControllerConfig.xml");
        cm.add(GameOperationLogicUtility);
        cm.add("com.topcoder.user.profile.ConfigProfileType.base","com/topcoder/user/profile/ConfigProfileType.base.properties",ConfigManager.CONFIG_PROPERTIES_FORMAT);
        //cm.add("UserProfileManager.xml");
        //cm.add("DBConnectionFactory.xml");
        //cm.add("ObjectFactory.xml");
    }

    /**
     * Method that removes all namespaces from ConfigManager component.
     *
     * @throws Exception
     *         propagated to JUnit
     */
    public static void removeAllCMNamespaces() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();
        while (it.hasNext()) {
            cm.removeNamespace(it.next().toString());
        }
    }
}

