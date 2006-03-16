/**
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.user.failuretests;

import com.topcoder.timetracker.user.*;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;

/**
 * Test helper class.
 *
 * @author WishingBone
 * @version 1.0
 */
class TestHelper {

    /**
     * The connection factory cached.
     */
    private static DBConnectionFactory cf = null;

    /**
     * The authorization persistence cached.
     */
    private static AuthorizationPersistence ap = null;

    /**
     * The user persistence cached.
     */
    private static UserPersistence up = null;

    /**
     * The user store cached.
     */
    private static UserStore us = null;

    /**
     * The user store manager cached.
     */
    private static UserStoreManager usm = null;

    /**
     * The user manager cached.
     */
    private static UserManager um = null;

    /**
     * Get the connection factory implementation.
     *
     * @return connection factory implementaiton.
     */
    public static DBConnectionFactory getConnectionFactory() {
        if (cf == null) {
            try {
                ConfigManager cm = ConfigManager.getInstance();
                cm.add("failuretests/ConnectionFactory.xml");
                String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";
                cf = new DBConnectionFactoryImpl(namespace);
                cm.removeNamespace(namespace);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cf;
    }

    /**
     * Get the authorization persistence implementation.
     *
     * @return authorization persistence implementaiton.
     */
    public static AuthorizationPersistence getAuthorizationPersistence() {
        if (ap == null) {
            try {
                ConfigManager cm = ConfigManager.getInstance();
                cm.add("failuretests/AuthorizationPersistence.xml");
                String namespace = "com.topcoder.security.authorization.persistence";
                ap = new SQLAuthorizationPersistence(namespace);
                cm.removeNamespace(namespace);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ap;
    }

    /**
     * Get the user persistence implementation.
     *
     * @return user persistence implementaiton.
     */
    public static UserPersistence getUserPersistence() {
        if (up == null) {
            try {
                up = new UserPersistenceImpl("conn", getConnectionFactory());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return up;
    }

    /**
     * Get the user store implementation.
     *
     * @return user store implementaiton.
     */
    public static UserStore getUserStore() {
        if (us == null) {
            try {
                us = new DbUserStore("conn", getConnectionFactory());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return us;
    }

    /**
     * Get the user store manager implementation.
     *
     * @return user store manager implementaiton.
     */
    public static UserStoreManager getUserStoreManager() {
        if (usm == null) {
            try {
                ConfigManager cm = ConfigManager.getInstance();
                cm.add("failuretests/UserStoreManager.xml");
                String namespace = "com.topcoder.timetracker.user.UserStoreManager.failuretests";
                usm = new UserStoreManagerImpl(namespace);
                cm.removeNamespace(namespace);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return usm;
    }

    /**
     * Get the user manager implementation.
     *
     * @return user manager implementaiton.
     */
    public static UserManager getUserManager() {
        if (um == null) {
            try {
                um = new UserManager(getUserPersistence(), getAuthorizationPersistence(), getUserStoreManager());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return um;
    }

}
