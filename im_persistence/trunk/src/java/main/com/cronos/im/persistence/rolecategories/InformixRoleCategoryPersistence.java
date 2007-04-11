/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.rolecategories;

import com.cronos.im.persistence.AbstractPersistenceWithValidator;
import com.cronos.im.persistence.ConfigurationException;
import com.cronos.im.persistence.ParameterHelpers;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.util.datavalidator.ObjectValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>An implementation of <code>RoleCategoryPersistence</code> that uses an Informix database as the backing
 * storage. This implementation uses the <i>principal</i>, <i>role</i>, and <i>principal_role</i> tables for user
 * operations and the <i>principal</i>, <i>category</i>, and <i>manager_category</i> tables for categories.
 *
 * <p>The following configuration parameters are supported.
 *
 * <ul>
 *   <li><strong>specification_factory_namespace</strong> (required): the namespace containing the object factory
 *      specifications</li>
 *   <li><strong>db_connection_factory_key</strong> (required): the object factory key used to instantiate the DB
 *     connection factory</li>
 *   <li><strong>connection_name</strong> (required): the name used to retrieve connections from the DB connection
 *     factory</li>
 *   <li><strong>validator_key</strong> (optional): the object factory key used to instantiate the object
 *     validator; if omitted, no validation is done</li>
 * </ul>
 *
 * <p><strong>Thread Safety</strong>: This class is immutable and thread safe.
 *
 * @author codedoc, TCSDEVELOPER
 * @version 1.0
 */

public class InformixRoleCategoryPersistence extends AbstractPersistenceWithValidator
    implements RoleCategoryPersistence {
    /**
     * The namespace used by the {@link #InformixRoleCategoryPersistence(String) default constructor} to configure
     * a new instance.
     */
    public static final String DEFAULT_NAMESPACE =
        "com.cronos.im.persistence.rolecategories.InformixRoleCategoryPersistence";

    /**
     * Constructs a new <code>InformixRoleCategoryPersistence</code> based on the specified namespace. See the
     * class documentation for a description of the configuration parameters.
     *
     * @param namespace the configuration namespace
     * @throws IllegalArgumentException if <code>namespace</code> is <code>null</code> or an empty string
     * @throws ConfigurationException if the namespace is missing or invalid or a required property is missing or a
     *   required object cannot be instantiated
     */
    public InformixRoleCategoryPersistence(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * Constructs a new <code>InformixRoleCategoryPersistence</code> from the specified parameters.
     *
     * @param connectionFactory the DB connection factory
     * @param connectionName the DB connection name
     * @param validator the object validator
     * @throws IllegalArgumentException if any argument other than <code>validator</code> is <code>null</code> or
     *   <code>connectionName</code> is an empty string
     */
    public InformixRoleCategoryPersistence(DBConnectionFactory connectionFactory, String connectionName,
                                           ObjectValidator validator) {
        super(connectionFactory, connectionName, validator);
    }

    /**
     * Equivalent to {@link #InformixRoleCategoryPersistence(String)
     * InformixRoleCategoryPersistence(DEFAULT_NAMESPACE)}.
     *
     * @throws ConfigurationException if the namespace is missing or invalid or a required property is missing or a
     *   required object cannot be instantiated
     */
    public InformixRoleCategoryPersistence() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * Retrieves all users matching the specified role.
     *
     * @param role the role for which to retrieve users
     * @return a non-<code>null</code>, possibly empty array of all the users for the specified role
     * @throws IllegalArgumentException if <code>role</code> is <code>null</code> or an empty string
     * @throws RoleNotFoundException if the role is not found in persistence
     * @throws RoleCategoryPersistenceException if a database error occurs other than the role not being found
     */
    public User[] getUsers(String role) throws RoleCategoryPersistenceException {
        ParameterHelpers.checkParameter(role, "role");

        Connection connection = getConnection();

        try {
            try {
                // see if the role exists
                PreparedStatement statement =
                    connection.prepareStatement("SELECT role_name FROM role WHERE role_name = ?");

                try {
                    statement.setString(1, role);

                    ResultSet results = statement.executeQuery();
                    try {
                        if (!results.next()) {
                            throw new RoleNotFoundException("role '" + role + "' was not found");
                        }
                    } finally {
                        closeResults(results);
                    }
                } finally {
                    closeStatement(statement);
                }

                // find all users for the role
                statement =
                    connection.prepareStatement("SELECT principal.principal_id, principal.principal_name FROM "
                                                + "principal, role, "
                                                + "principal_role WHERE principal.principal_id = "
                                                + "principal_role.principal_id AND role.role_id = "
                                                + "principal_role.role_id AND role.role_name = ?");

                try {
                    statement.setString(1, role);

                    ResultSet results = statement.executeQuery();
                    try {
                        List ret = new ArrayList();

                        while (results.next()) {
                            try {
                                ret.add(new User(results.getLong("principal_id"),
                                                 results.getString("principal_name")));
                            } catch (IllegalArgumentException ex) {
                                throw new RoleCategoryPersistenceException("failed to instantiate user", ex);
                            }
                        }

                        return (User[]) ret.toArray(new User[ret.size()]);
                    } finally {
                        closeResults(results);
                    }
                } finally {
                    closeStatement(statement);
                }
            } catch (SQLException ex) {
                throw new RoleCategoryPersistenceException("SQL error retrieving users: " + ex.getMessage(), ex);
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Retrieves all categories in the database.
     *
     * @return a non-<code>null</code>, possibly empty array of all the categories in the database
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    public Category[] getAllCategories() throws RoleCategoryPersistenceException {
        Connection connection = getConnection();

        try {
            return queryCategories(connection, "SELECT * FROM category", null);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Similar to {@link #getAllCategories() getAllCategories()}, except only retrieves categories matching the
     * specified chattable flag.
     *
     * @param chattable the chattable flag used to filter the results
     * @return a possibly empty array of categories with the specified chattable flag
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    public Category[] getCategories(boolean chattable) throws RoleCategoryPersistenceException {
        Connection connection = getConnection();

        try {
            return queryCategories(connection, "SELECT * FROM category WHERE chattable_flag = ?",
                                   chattable ? "Y" : "N");
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Returns all categories with the specified manager.
     *
     * @param manager the manager to use to filter the results
     * @return a possibly empty array of all the categories with the specified manager
     * @throws IllegalArgumentException if <code>manager</code> is <code>null</code> or an empty string
     * @throws ManagerNotFoundException if the manager is not found in the database
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    public Category[] getCategories(String manager) throws RoleCategoryPersistenceException {
        ParameterHelpers.checkParameter(manager, "manager");

        Connection connection = getConnection();

        try {
            try {
                assertManagerExists(connection, manager);
            } catch (SQLException ex) {
                throw new RoleCategoryPersistenceException("error verifying manager: " + ex.getMessage(), ex);
            }

            return queryCategories(connection,
                                   "SELECT * FROM principal, category, manager_category WHERE "
                                   + "principal.principal_id = manager_category.manager_id AND "
                                   + "category.category_id = manager_category.category_id AND "
                                   + "principal.principal_name = ?", manager);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Associates the specified categories with the specified manager, replacing any existing associations.
     *
     * @param manager the manager
     * @param categories the categories to associate with the manager
     * @throws IllegalArgumentException if either argument is <code>null</code> or <code>manager</code> is an empty
     *   string or <code>categories</code> contains <code>null</code> elements
     * @throws ManagerNotFoundException if the manager does not exist in the database
     * @throws CategoryValidationException if one or more categories fail validation
     * @throws CategoryNotFoundException if one or more categories do not exist in the database
     * @throws RoleCategoryPersistenceException if a database error occurs unrelated to the above exceptions
     */
    public void updateCategories(String manager, Category[] categories) throws RoleCategoryPersistenceException {
        ParameterHelpers.checkParameter(manager, "manager");
        ParameterHelpers.checkParameter(categories, "categories");

        for (int idx = 0; idx < categories.length; ++idx) {
            ParameterHelpers.checkParameter(categories[idx], "category");
        }

        ObjectValidator validator = getValidator();
        if (validator != null && !validator.valid(categories)) {
            throw new CategoryValidationException(validator.getMessage(categories));
        }

        Connection connection = getConnection();
        try {
            try {
                connection.setAutoCommit(false);

                long principalId = assertManagerExists(connection, manager);

                // first, remove all existing associations
                PreparedStatement statement =
                    connection.prepareStatement("DELETE FROM manager_category WHERE manager_id = ?");
                try {
                    statement.setLong(1, principalId);

                    statement.executeUpdate();
                } finally {
                    closeStatement(statement);
                }

                // next, add the new associations
                statement =
                    connection.prepareStatement("INSERT INTO manager_category (manager_id, category_id, "
                                                + "create_date, modify_date, create_user, modify_user) VALUES "
                                                + "(?, ?, CURRENT, CURRENT, USER, USER)");

                try {
                    for (int idx = 0; idx < categories.length; ++idx) {
                        assertCategoryExists(connection, categories[idx].getId());

                        statement.setLong(1, principalId);
                        statement.setLong(2, categories[idx].getId());

                        statement.executeUpdate();
                    }

                    statement.executeBatch();
                } finally {
                    closeStatement(statement);
                }

                connection.commit();
            } catch (RoleCategoryPersistenceException ex) {
                rollbackConnection(connection);
                throw ex;
            } catch (SQLException ex) {
                rollbackConnection(connection);
                throw new RoleCategoryPersistenceException("error updating categories: " + ex.getMessage(), ex);
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * Verifies that the specified category exists.
     *
     * @param connection the DB connection
     * @param category the category ID
     * @throws CategoryNotFoundException if the category does not exist
     * @throws SQLException if a database error occurs
     * @throws RoleCategoryPersistenceException if the DB resources cannot be closed
     */
    private static void assertCategoryExists(Connection connection, long category)
        throws SQLException, RoleCategoryPersistenceException {
        PreparedStatement statement =
            connection.prepareStatement("SELECT category_id FROM category WHERE category_id = ?");

        try {
            statement.setLong(1, category);

            ResultSet results = statement.executeQuery();
            try {
                if (!results.next()) {
                    throw new CategoryNotFoundException("category '" + category + "' not found");
                }
            } finally {
                closeResults(results);
            }
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Verifies that the specified manager exists in the database.
     *
     * @param connection the DB connection
     * @param manager the manager name
     * @return the <i>principal_id</i> of the specified manager
     * @throws ManagerNotFoundException if the manager does not exist
     * @throws SQLException if a database error occurs
     * @throws RoleCategoryPersistenceException if the DB resources cannot be closed properly
     */
    private static long assertManagerExists(Connection connection, String manager)
        throws SQLException, RoleCategoryPersistenceException {
        PreparedStatement statement =
            connection.prepareStatement("SELECT principal_id FROM principal WHERE principal_name = ?");

        try {
            statement.setString(1, manager);

            ResultSet results = statement.executeQuery();

            try {
                if (!results.next()) {
                    throw new ManagerNotFoundException("manager '" + manager + "' does not exist");
                }

                return results.getLong("principal_id");
            } finally {
                closeResults(results);
            }
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Helper method that creates a database connection and rethrows any exceptions as
     * <code>ChatUserProfilePersistenceException</code>.
     *
     * @return a new database connection
     * @throws RoleCategoryPersistenceException if the connection cannot be created
     */
    private Connection getConnection() throws RoleCategoryPersistenceException {
        try {
            return getConnectionFactory().createConnection(getConnectionName());
        } catch (DBConnectionException ex) {
            throw new RoleCategoryPersistenceException("error creating connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the database connection and rethrows any exceptions as
     * <code>RoleCategoryPersistenceException</code>.
     *
     * @param connection the database connection to close
     * @throws RoleCategoryPersistenceException if a SQL error occurs
     */
    private static void closeConnection(Connection connection) throws RoleCategoryPersistenceException {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RoleCategoryPersistenceException("error closing connection: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the result set and rethrows any exceptions as <code>RoleCategoryPersistenceException</code>.
     *
     * @param results the result set to close
     * @throws RoleCategoryPersistenceException if a SQL error occurs
     */
    private static void closeResults(ResultSet results) throws RoleCategoryPersistenceException {
        try {
            results.close();
        } catch (SQLException ex) {
            throw new RoleCategoryPersistenceException("error closing result set: " + ex.getMessage(), ex);
        }
    }

    /**
     * Closes the prepared statement and rethrows any exceptions as
     * <code>RoleCategoryPersistenceException</code>.
     *
     * @param statement the statement to close
     * @throws RoleCategoryPersistenceException if a SQL error occurs
     */
    private static void closeStatement(PreparedStatement statement) throws RoleCategoryPersistenceException {
        try {
            statement.close();
        } catch (SQLException ex) {
            throw new RoleCategoryPersistenceException("error closing prepared statement: " + ex.getMessage(), ex);
        }
    }

    /**
     * A helper method that executes the specified category query and populates the result array.
     *
     * @param connection the database connection
     * @param query the query to execute
     * @param arg the query argument or <code>null</code> if no argument is required
     * @return an array of categories matching the query
     * @throws RoleCategoryPersistenceException if a database error occurs
     */
    private Category[] queryCategories(Connection connection, String query, Object arg)
        throws RoleCategoryPersistenceException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            try {
                if (arg != null) {
                    statement.setObject(1, arg);
                }

                ResultSet results = statement.executeQuery();

                try {
                    List ret = new ArrayList();

                    while (results.next()) {
                        try {
                            ret.add(new Category(results.getLong("category_id"), results.getString("name"),
                                                 results.getString("description"),
                                                 results.getString("chattable_flag").equalsIgnoreCase("Y")));
                        } catch (IllegalArgumentException ex) {
                            throw new RoleCategoryPersistenceException("failed to instantiate category", ex);
                        }
                    }

                    return (Category[]) ret.toArray(new Category[ret.size()]);
                } finally {
                    closeResults(results);
                }
            } finally {
                closeStatement(statement);
            }
        } catch (SQLException ex) {
            throw new RoleCategoryPersistenceException("error retrieving categories: " + ex.getMessage(), ex);
        }
    }
}
