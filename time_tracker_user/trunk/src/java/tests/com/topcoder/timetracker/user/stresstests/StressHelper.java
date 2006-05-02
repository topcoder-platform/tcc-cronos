/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.stresstests;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Iterator;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.security.authorization.AuthorizationPersistence;
import com.topcoder.security.authorization.persistence.SQLAuthorizationPersistence;
import com.topcoder.timetracker.user.User;
import com.topcoder.timetracker.user.UserManager;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.idgenerator.IDGenerator;

/**
 * <p>
 * Helper class for stress test.
 * </p>
 *
 * @author arylio
 * @version 1.0
 *
 */
final class StressHelper {

    /**
     * <p>
     * Represents the namespace, used by stress test.
     * </p>
     */
    public static final String NAMESPACE = UserManager.CONFIG_NAMESPACE;

    /**
     * <p>
     * Represents the config file, used by stress test.
     * </p>
     */
    public static final String CONFIG_FILE = "stress/TimeTrackerUser.xml";

    /**
     * <p>
     * Represents the config file for cache, used by stress test.
     * </p>
     */
    public static final String CONFIG_CACHE = "stress/Cache.xml";

    /**
     * <p>
     * Represents the config file for log, used by stress test.
     * </p>
     */
    public static final String CONFIG_LOGGING = "stress/Logging.xml";

    /**
     * <p>
     * Represents the config file for db connection factory, used by stress test.
     * </p>
     */
    public static final String CONFIG_DB_FACTORY = "stress/DBFactory.xml";

    /**
     * <p>
     * Represents the property name in the config file.
     * </p>
     */
    public static final String PROPERTY_CONNECTION = "Connection";

    /**
     * The database connection instance.
     */
    private static Connection connection = null;

    /**
     * Represents the id generator name for this component.
     */
    public static final String IDGENERATOR_NAME = "TimeTrackerUser";

    /**
     * <p>
     * Represents the insert sql statement for users.
     * </p>
     */
    private static final String SQL_INSERT_USER =
        "INSERT into Users (UsersID, Name, UserStore) VALUES (?, ?, ?)";

    /**
     * <p>
     * Represents the insert sql statement for userstore.
     * </p>
     */
    private static final String SQL_INSERT_USERSTORE =
        "INSERT into DefaultUsers (DefaultUsersID, UserName, Password, CreationDate,"
        + " CreationUser, ModificationDate, ModificationUser) VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the insert sql statement for principal.
     * </p>
     */
    private static final String SQL_INSERT_PRINCIPAL =
        "INSERT INTO Principal (principal_id, principal_name) VALUES (?, ?)";

    /**
     * <p>
     * Add config file for testing.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void addConfigFile() throws Exception {
        removeConfigFile();

        ConfigManager.getInstance().add(CONFIG_CACHE);
        ConfigManager.getInstance().add(CONFIG_DB_FACTORY);
        ConfigManager.getInstance().add(CONFIG_LOGGING);
        ConfigManager.getInstance().add(NAMESPACE, CONFIG_FILE,
                ConfigManager.CONFIG_XML_FORMAT);
    }

    /**
     * <p>
     * Remove config file for testing.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void removeConfigFile() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator itor = cm.getAllNamespaces();

        while (itor.hasNext()) {
            cm.removeNamespace((String) itor.next());
        }
    }

    /**
     * <p>
     * Clear the test datas from the tables.
     * </p>
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void clearTables() throws Exception {
        StressHelper.clearTable("DefaultUsers");
        StressHelper.clearTable("Users");
        StressHelper.clearTable("principal_role");
        StressHelper.clearTable("principal");
    }

    /**
     * <p>
     * Delete all rows from the specific table.
     * </p>
     *
     * @param tableName
     *            the name of the table to delete rows.
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void clearTable(String tableName) throws Exception {
        getConnection().createStatement().executeUpdate(
                "DELETE FROM " + tableName);
    }

    /**
     * Get a connection to the database.
     *
     * @return a connection to the database.
     *
     * @throws Exception
     *             to JUnit
     */
    private static Connection getConnection() throws Exception {
        if (connection == null || connection.isClosed()) {
            // create a new Connection if the connection is not initialized or
            // closed.
            DBConnectionFactory factory = new DBConnectionFactoryImpl(NAMESPACE);
            connection = factory.createConnection();
        }
        return connection;
    }

    /**
     * <p>
     * Add specified record into users table.
     * </p>
     *
     * @param generator
     *            the id generator
     * @param count
     *            the record count to add
     * @param users
     *            the array to save users added.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void addTestUser(IDGenerator generator, int count,
            User[] users) throws Exception {
        PreparedStatement stmt = null;

        getConnection();
        AuthorizationPersistence authPersistence = new SQLAuthorizationPersistence(
                UserManager.CONFIG_NAMESPACE);
        for (int i = 0; i < count; ++i) {
            stmt = connection.prepareStatement(SQL_INSERT_USER);

            // Set the values of the columns to insert
            int id = (int) generator.getNextID();
            String name = "topcoder_" + id;

            users[i] = new User(id, name, "StressTest"
                    + (((i % 2) == 0) ? "1" : "2"), null);

            stmt.setLong(1, id);
            stmt.setString(2, name);
            stmt.setString(3, users[i].getUserStoreName());

            // insert the row and close the statement.
            stmt.executeUpdate();
            stmt.close();
            addUserStoreTestData(users[i]);
            authPersistence.addPrincipal(users[i]);
        }
    }

    /**
     * <p>
     * Add a user to table.
     * </p>
     *
     * @param user
     *            the user to add.
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void addUserStoreTestData(User user) throws Exception {
        getConnection();
        PreparedStatement stmt = connection
                .prepareStatement(SQL_INSERT_USERSTORE);
        stmt.setLong(1, user.getId());
        stmt.setString(2, user.getName());
        stmt.setString(3, user.getName());
        stmt.setDate(4, new Date(new java.util.Date().getTime()));
        stmt.setString(5, user.getName());
        stmt.setDate(6, new Date(new java.util.Date().getTime()));
        stmt.setString(7, user.getName());
        // insert the row and close the statement.
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * <p>
     * Add a pricipal to table.
     * </p>
     *
     * @param id
     *            the principal id.
     * @param principal
     *            the principal name.
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void addPrincipal(long id, String principal) throws Exception {
        getConnection();
        PreparedStatement stmt = connection
                .prepareStatement(SQL_INSERT_PRINCIPAL);
        stmt.setLong(1, id);
        stmt.setString(2, principal);
        stmt.executeUpdate();
        stmt.close();
    }

    /**
     * Add Roles and principal to table for test.
     *
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void addRoles() throws Exception {
        AuthorizationPersistence authPersistence = new SQLAuthorizationPersistence(
                NAMESPACE);

        addPrincipal(UserManager.ACCOUNT_MANAGER.getId(),
                UserManager.ACCOUNT_MANAGER.getName());
        addPrincipal(UserManager.CONTRACTOR.getId(), UserManager.CONTRACTOR
                .getName());
        addPrincipal(UserManager.EMPLOYEE.getId(), UserManager.EMPLOYEE
                .getName());
        addPrincipal(UserManager.HUMAN_RESOURCE.getId(),
                UserManager.HUMAN_RESOURCE.getName());
        addPrincipal(UserManager.PROJECT_MANAGER.getId(),
                UserManager.PROJECT_MANAGER.getName());
        addPrincipal(UserManager.SUPER_ADMIN.getId(), UserManager.SUPER_ADMIN
                .getName());
    }

    /**
     * <p>
     * Add test data for UserManager.
     * </p>
     *
     * @param generator
     *            the idgenereator.
     * @param count
     *            the count of user to add.
     * @param users
     *            the array to save the user.
     * @throws Exception
     *             Exception to JUnit.
     */
    public static void addUserManagerTestData(IDGenerator generator, int count,
            User[] users) throws Exception {
        addTestUser(generator, count, users);
        addRoles();
    }

}