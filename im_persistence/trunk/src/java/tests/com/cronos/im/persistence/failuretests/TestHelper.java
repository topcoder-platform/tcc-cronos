/*
 * Copyright (C) 2007 Topcoder Inc., All Rights Reserved.
 */
package com.cronos.im.persistence.failuretests;

import com.topcoder.chat.user.profile.ProfileKey;

import com.topcoder.database.statustracker.Entity;
import com.topcoder.database.statustracker.EntityKey;
import com.topcoder.database.statustracker.EntityStatus;
import com.topcoder.database.statustracker.Status;

import com.topcoder.util.config.ConfigManager;

import java.sql.Connection;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;


/**
 * Test helper class.
 */
public class TestHelper {
    /** namespace for db connection. */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /** ObjectFactory namespace. */
    public static final String OF_NAMESPACE = "com.cronos.im.persistence.objectfactory";

    /** Config file for the component. */
    public static final String CONFIG_FILE = "failure" + java.io.File.separator + "config.xml";

    /** Namespace for this InformixEntryStatusTracker class. */
    public static final String INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE = "com.cronos.im.persistence.InformixEntityStatusTracker.failure";

    /** Namespace for this InformixProfileKeyManager class. */
    public static final String INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE = "com.cronos.im.persistence.InformixProfileKeyManager.failure";

    /** Namespace for this RegisteredChatUserProfileInformixPersistence class. */
    public static final String REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE = "com.cronos.im.persistence.RegisteredChatUserProfileInformixPersistence.failure";

    /** Namespace for this UnregisteredChatUserProfileInformixPersistence class. */
    public static final String UN_REGISTER_CHAT_USER_PROFILE_INFORMIX_NAMESPACE = INFORMIX_PROFILE_KEY_MANAGER_NAMESPACE;

    /** Namespace for this InformixRoleCategoryPersistence class. */
    public static final String INFORMIX_ROLE_CATEGORY_PERSISTENCE_NAMESPACE = INFORMIX_ENTRY_STATUS_TRAKER_NAMESPACE;

    /**
     * <p>
     * Creates a new instance of TestHelper class. The private constructor prevents the creation of a new instance.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Add the config file.
     * </p>
     *
     * @throws Exception into JUnit
     */
    public static void setUpConfiguration() throws Exception {
        ConfigManager.getInstance().add(CONFIG_FILE);
    }

    /**
     * This method clears all the namespaces from ConfigManager.
     *
     * @throws Exception if any error occurs when clearing ConfigManager
     */
    public static void clearConfiguration() throws Exception {
        ConfigManager manager = ConfigManager.getInstance();

        for (Iterator iter = manager.getAllNamespaces(); iter.hasNext();) {
            manager.removeNamespace((String) iter.next());
        }
    }

    /**
     * Clear the tables.
     *
     * @param conn DOCUMENT ME!
     *
     * @throws Exception into Junit
     */
    public static void clearTables(Connection conn) throws Exception {
        Statement stmt = conn.createStatement();

        try {
            stmt.execute("delete from all_user");
            stmt.execute("delete from client");
            stmt.execute("delete from principal_role");
            stmt.execute("delete from principal");
            stmt.execute("delete from role");
            stmt.execute("delete from category");
        } finally {
            stmt.close();
        }
    }

    /**
     * <p>
     * Creates an Entity instance from a property file.
     * </p>
     *
     * @param name the property file path
     *
     * @return a new Entity instance
     *
     * @throws Exception to JUnit
     */
    public static Entity createEntity(String name) throws Exception {
        return new Entity(1, name, new String[] { "entity_status_id" },
            new Status[] { createStatus(1), createStatus(2) });
    }

    /**
     * <p>
     * Creates an EntityKey instance from a property file.
     * </p>
     *
     * @param entityName the property file path
     *
     * @return a new EntityKey instance
     *
     * @throws Exception to JUnit
     */
    public static EntityKey createEntityKey(String entityName)
        throws Exception {
        return new EntityKey(createEntity(entityName), "1");
    }

    /**
     * <p>
     * Creates an EntityStatus instance from a property file.
     * </p>
     *
     * @return a new EntityStatus instance
     *
     * @throws Exception to JUnit
     */
    public static EntityStatus createEntityStatus() throws Exception {
        return new EntityStatus(createEntityKey("entity"), createStatus(1), parseDate("06052006"), "john");
    }

    /**
     * <p>
     * Creates a Status instance from a property file.
     * </p>
     *
     * @param statusId the property file path
     *
     * @return a new Status instance
     *
     * @throws Exception to JUnit
     */
    public static Status createStatus(long statusId) throws Exception {
        Status status = new Status(statusId);
        status.setName((statusId == 1) ? "Open" : "closed");
        status.setShortDesc((statusId == 1) ? "Open" : "closed");
        status.setLongDesc((statusId == 1) ? "Open" : "closed");
        status.setCreationDate((statusId == 1) ? parseDate("05052006") : parseDate("07052006"));
        status.setUpdateDate((statusId == 1) ? parseDate("06052006") : parseDate("08052006"));

        return status;
    }

    /**
     * <p>
     * Parses a date string to a Date instance using format "ddMMyyyy".
     * </p>
     *
     * @param date a date string in "ddMMyyyy" format
     *
     * @return the corresponding Date instance.
     *
     * @throws Exception to  JUnit
     */
    public static Date parseDate(String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");

        return format.parse(date);
    }

    /**
     * Simply create profileKey instance.
     *
     * @param id the id
     * @param userName the username
     * @param type the type
     *
     * @return ProfileKey instance with given parameters
     */
    public static ProfileKey createProfileKey(long id, String userName, String type) {
        return new ProfileKey(id, userName, type);
    }
}
