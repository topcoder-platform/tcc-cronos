/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.timetracker.project.searchfilters.Filter;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

/**
 * <p>
 * This class is a concrete implementation of the TimeTrackerProjectPersistence
 * interface that uses an Informix database as persistence. This implementation
 * uses the DB Connection Factory component to obtain a connection to the
 * database. Transaction should be employed to ensure atomicity.
 * </p>
 *
 * <p>
 * This class provides three constructors to obey the configuration contract of
 * the interface. The default constant will be used if the corresponding field
 * is not specified.
 * </p>
 *
 * <p>
 * For addition operations, the current date will be written to the
 * creation_date and modification_date fields in the database and populated to
 * the supplied instance, and the creation_user will be set as the
 * modification_user. For update operations, the current date will be written to
 * the modification_date field in the database and populated to the supplied
 * instance, and the creation_date and creation_user fields will not be updated
 * in the database. If an exception is thrown during those operations, the
 * populated fields should be treated as invalid and should not be read.
 * </p>
 *
 * <p>
 * Since version 1.1 it supports batch processing of the client/project CRUD
 * operations, and client/project search functionality. The atomicity can be
 * specified for those batch operations. If it is true, the successful
 * operations are rolled back when one operation in a batch fails, and a
 * BatchOperationException will be thrown. If it is false, the operation
 * continues for every element before throwing the BatchOperationException.
 * </p>
 *
 * Version 2.0 added posibility to obtain companyId for a specific id for time
 * tracker entities: (for client, project, user_account, time entry and expense
 * entry)
 *
 * <p>
 * This class is not thread-safe. The methods should not be called concurrently
 * on a single instance. However, the database operations are made atomic using
 * JDBC transactions. So multiple instances of this class can access the
 * database at the same time.
 * </p>
 *
 * @author DanLazar, colau, costty000
 * @author real_vg, TCSDEVELOPER
 * @version 2.0
 *
 * @since 1.0
 */
public class InformixTimeTrackerProjectPersistence implements
        TimeTrackerProjectPersistence {
    /**
     * <p>
     * The default connection producer name used to obtain a Connection from
     * DBConnectionFactoryImpl. It will be used if the user creates an instance
     * of this class using the one-argument constructor.
     * </p>
     */
    public static final String DEFAULT_CONNECTION_PRODUCER_NAME = "Time_Tracker_Project_Informix_Connection_Producer";

    /**
     * <p>
     * The default ConfigManager namespace to be used for loading the
     * configuration of DatabaseSearchUtility used to search the Projects.
     * </p>
     */
    public static final String DEFAULT_PROJECT_SEARCH_UTILITY_NAMESPACE = "com.topcoder.timetracker.project.persistence."
            + "DatabaseSearchUtility.projects";

    /**
     * <p>
     * The default ConfigManager namespace to be used for loading the
     * configuration of DatabaseSearchUtility used to search the clients.
     * </p>
     */
    public static final String DEFAULT_CLIENT_SEARCH_UTILITY_NAMESPACE = "com.topcoder.timetracker.project.persistence."
            + "DatabaseSearchUtility.clients";

    /**
     * <p>
     * The SQL statement to select the time entry by time entry id and project
     * id.
     * </p>
     */
    private static final String SQL_SEL_TIME_ENTRY = "SELECT * FROM project_time WHERE time_entry_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to select the time entries by project id.
     * </p>
     */
    private static final String SQL_SEL_TIME_ENTRIES = "SELECT * FROM project_time WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to insert a time entry.
     * </p>
     */
    private static final String SQL_INS_TIME_ENTRY = "INSERT INTO project_time VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the time entry by time entry id and project
     * id.
     * </p>
     */
    private static final String SQL_DEL_TIME_ENTRY = "DELETE FROM project_time WHERE time_entry_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to delete the time entries by project id.
     * </p>
     */
    private static final String SQL_DEL_TIME_ENTRIES = "DELETE FROM project_time WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to delete all time entries.
     * </p>
     */
    private static final String SQL_DEL_ALL_TIME_ENTRIES = "DELETE FROM project_time";

    /**
     * <p>
     * The SQL statement to select the expense entry by expense entry id and
     * project id.
     * </p>
     */
    private static final String SQL_SEL_EXPENSE_ENTRY = "SELECT * FROM project_expense WHERE expense_entry_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to select the expense entries by project id.
     * </p>
     */
    private static final String SQL_SEL_EXPENSE_ENTRIES = "SELECT * FROM project_expense WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to insert an expense entry.
     * </p>
     */
    private static final String SQL_INS_EXPENSE_ENTRY = "INSERT INTO project_expense VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the expense entry by expense entry id and
     * project id.
     * </p>
     */
    private static final String SQL_DEL_EXPENSE_ENTRY = "DELETE FROM project_expense WHERE expense_entry_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to delete the expense entries by project id.
     * </p>
     */
    private static final String SQL_DEL_EXPENSE_ENTRIES = "DELETE FROM project_expense WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to delete all expense entries.
     * </p>
     */
    private static final String SQL_DEL_ALL_EXPENSE_ENTRIES = "DELETE FROM project_expense";

    /**
     * <p>
     * The SQL statement to select the project manager by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_MANAGER = "SELECT * FROM project_manager WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to select the project manager id by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_MANAGER_ID = "SELECT user_account_id FROM project_manager WHERE "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to insert a project manager.
     * </p>
     */
    private static final String SQL_INS_PROJECT_MANAGER = "INSERT INTO project_manager VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the project manager by manager id and project
     * id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_MANAGER = "DELETE FROM project_manager WHERE user_account_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to delete the project managers by project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_MANAGERS = "DELETE FROM project_manager WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to delete all project managers.
     * </p>
     */
    private static final String SQL_DEL_ALL_PROJECT_MANAGERS = "DELETE FROM project_manager";

    /**
     * <p>
     * The SQL statement to select the project worker by worker id and project
     * id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_WORKER = "SELECT * FROM project_worker WHERE user_account_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to select the project workers by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_WORKERS = "SELECT * FROM project_worker WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to select the project worker ids by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_WORKER_IDS = "SELECT user_account_id FROM project_worker WHERE "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to insert a project worker.
     * </p>
     */
    private static final String SQL_INS_PROJECT_WORKER = "INSERT INTO project_worker VALUES "
            + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the project worker by worker id and project
     * id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_WORKER = "DELETE FROM project_worker WHERE user_account_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to delete the project workers by project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_WORKERS = "DELETE FROM project_worker WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to delete all project workers.
     * </p>
     */
    private static final String SQL_DEL_ALL_PROJECT_WORKERS = "DELETE FROM project_worker";

    /**
     * <p>
     * The SQL statement to update the project worker by project id and worker
     * id.
     * </p>
     */
    private static final String SQL_UPD_PROJECT_WORKER = "UPDATE project_worker SET start_date = ?, end_date = ?, "
            + "pay_rate = ?, modification_date = ?, modification_user = ? WHERE project_id = ? AND user_account_id = ?";

    /**
     * <p>
     * The SQL statement to select the project by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT = "SELECT * FROM project WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to select all project.
     * </p>
     */
    private static final String SQL_SEL_ALL_PROJECTS = "SELECT * FROM project";

    /**
     * <p>
     * The SQL statement to insert a project.
     * </p>
     */
    private static final String SQL_INS_PROJECT = "INSERT INTO project VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the project by project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT = "DELETE FROM project WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to delete all projects.
     * </p>
     */
    private static final String SQL_DEL_ALL_PROJECTS = "DELETE FROM project";

    /**
     * <p>
     * The SQL statement to update the project by project id.
     * </p>
     */
    private static final String SQL_UPD_PROJECT = "UPDATE project SET company_id = ?, name = ?, description = ?,"
            + " start_date = ?, end_date = ?, modification_date = ?, modification_user = ? WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to select the client project by project id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT_PROJECT = "SELECT * FROM client_project WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to select the client project by client id and project
     * id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT_PROJECT_BY_BOTH = "SELECT * FROM client_project WHERE client_id = ?"
            + " AND project_id = ?";

    /**
     * <p>
     * The SQL statement to select the client projects by client id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT_PROJECTS = "SELECT * FROM client_project WHERE client_id = ?";

    /**
     * <p>
     * The SQL statement to insert a client project.
     * </p>
     */
    private static final String SQL_INS_CLIENT_PROJECT = "INSERT INTO client_project VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the client project by project id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT_PROJECT = "DELETE FROM client_project WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement to delete the client project by client id and project
     * id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT_PROJECT_BY_BOTH = "DELETE FROM client_project WHERE client_id = ? AND "
            + "project_id = ?";

    /**
     * <p>
     * The SQL statement to delete the client project by client id and project
     * id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT_PROJECTS = "DELETE FROM client_project WHERE client_id = ?";

    /**
     * <p>
     * The SQL statement to delete all client projects.
     * </p>
     */
    private static final String SQL_DEL_ALL_CLIENT_PROJECTS = "DELETE FROM client_project";

    /**
     * <p>
     * The SQL statement to select the client by client id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT = "SELECT * FROM client WHERE client_id = ?";

    /**
     * <p>
     * The SQL statement to select all clients.
     * </p>
     */
    private static final String SQL_SEL_ALL_CLIENTS = "SELECT * FROM client";

    /**
     * <p>
     * The SQL statement to insert a client.
     * </p>
     */
    private static final String SQL_INS_CLIENT = "INSERT INTO client VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the client by client id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT = "DELETE FROM client WHERE client_id = ?";

    /**
     * <p>
     * The SQL statement to delete all clients.
     * </p>
     */
    private static final String SQL_DEL_ALL_CLIENTS = "DELETE FROM client";

    /**
     * <p>
     * The SQL statement to update the client by client id.
     * </p>
     */
    private static final String SQL_UPD_CLIENT = "UPDATE client SET company_id = ?, name = ?, modification_date = ?, "
            + "modification_user = ? WHERE client_id = ?";

    /**
     * <p>
     * The SQL statement for getting the company Id assigned to a client
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_COMPANY_ID_FOR_CLIENT = "SELECT company_id FROM client WHERE client_id = ?";

    /**
     * <p>
     * The SQL statement for getting the company Id assigned to a project
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_COMPANY_ID_FOR_PROJECT = "SELECT company_id FROM project WHERE project_id = ?";

    /**
     * <p>
     * The SQL statement for getting the company Id assigned to a time_entry
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_COMPANY_ID_FOR_TIME_ENTRY = "SELECT company_id FROM time_entry WHERE "
            + "time_entry_id = ?";

    /**
     * <p>
     * The SQL statement for getting the company Id assigned to a expense_entry
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_COMPANY_ID_FOR_EXPENSE_ENTRY = "SELECT company_id FROM expense_entry WHERE "
            + "expense_entry_id = ?";

    /**
     * <p>
     * The SQL statement for getting the company Id assigned to a user account
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_COMPANY_ID_FOR_USER_ACCOUNT = "SELECT company_id FROM user_account WHERE "
            + "user_account_id = ?";

    /**
     * <p>
     * The SQL statement for getting the client for a specific name and a
     * specific company
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_CLIENT_FOR_NAME_AND_COMPANY = "SELECT client_id FROM client WHERE name = ? "
            + "AND company_id = ?";

    /**
     * <p>
     * The SQL statement for getting the client for a specific name and a
     * specific company excluding the current client
     * </p>
     *
     * @since 2.0
     */
    private static final String SQL_SEL_CLIENT_FOR_NAME_AND_COMPANY_EXCLUDING_CURRENT = "SELECT client_id FROM "
            + "client WHERE name = ? AND company_id = ? AND client_id <> ?";

    /**
     * <p>
     * Represents the connection to the Informix database. It will be
     * initialized in the constructors by providing the appropriate connection
     * producer name to the DBConnectionFactoryImpl.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * The DatabaseSearchUtility used to search the projects. It is initialized
     * in constructor and then never changed. It cannot be null. It is used by
     * the searchForProjects() method.
     * </p>
     */
    private DatabaseSearchUtility projectSearchUtility;

    /**
     * <p>
     * The DatabaseSearchUtility used to search the clients. It is initialized
     * in constructor and then never changed. It cannot be null. It is used by
     * the searchForClients() method.
     * </p>
     */
    private DatabaseSearchUtility clientSearchUtility;

    /**
     * <p>
     * The cache for prepared statements. It is a mapping from SQL query
     * (String) to the corresponding prepared statement (PreparedStatement). It
     * is initialized in constructor and the reference can never change. It
     * cannot be null. All prepared statements to be executed are retrieved from
     * this map. If not exists, the prepared statement is lazily initialized.
     * </p>
     */
    private Map pstmts;

    /**
     * <p>
     * Creates a new instance of this class. The default connection producer
     * name, project search utility namespace and client search utility
     * namespace will be used.
     * </p>
     *
     * @param dbNamespace
     *            the namespace of the DB Connection Factory configuration file
     *
     * @throws NullPointerException
     *             if the dbNamespace is null
     * @throws IllegalArgumentException
     *             if the dbNamespace is the empty String
     * @throws PersistenceException
     *             if the connection to the database cannot be established, or
     *             any error happens when loading the configuration
     */
    public InformixTimeTrackerProjectPersistence(String dbNamespace)
            throws PersistenceException {
        this(dbNamespace, DEFAULT_CONNECTION_PRODUCER_NAME);
    }

    /**
     * <p>
     * Creates a new instance of this class. The default project search utility
     * namespace and client search utility namespace will be used.
     * </p>
     *
     * @param dbNamespace
     *            the namespace of the DB Connection Factory configuration file
     * @param connectionProducerName
     *            the connection producer name used to obtain the connection
     *
     * @throws NullPointerException
     *             if the dbNamespace or connectionProducerName is null
     * @throws IllegalArgumentException
     *             if the dbNamespace or connectionProducerName is the empty
     *             String
     * @throws PersistenceException
     *             if the connection to the database cannot be established, or
     *             any error happens when loading the configuration
     */
    public InformixTimeTrackerProjectPersistence(String dbNamespace,
            String connectionProducerName) throws PersistenceException {
        DBUtil.checkStringNPE(dbNamespace, "dbNamespace");
        DBUtil.checkStringNPE(connectionProducerName, "connectionProducerName");

        init(dbNamespace, connectionProducerName,
                DEFAULT_PROJECT_SEARCH_UTILITY_NAMESPACE,
                DEFAULT_CLIENT_SEARCH_UTILITY_NAMESPACE);
    }

    /**
     * <p>
     * Creates a new instance of this class with the given connection producer
     * name, project search utility namespace and client search utility
     * namespace.
     * </p>
     *
     * @param dbNamespace
     *            the namespace of the DB Connection Factory configuration file
     * @param connectionProducerName
     *            the connection producer name used to obtain the connection
     * @param projectSearchUtilityNamespace
     *            the configuration namespace used to load configuration for the
     *            DatabaseSearchUtility used to search projects
     * @param clientSearchUtilityNamespace
     *            the configuration namespace used to load configuration for the
     *            DatabaseSearchUtility used to search clients
     *
     * @throws IllegalArgumentException
     *             if any of the parameters is null or the empty String
     * @throws PersistenceException
     *             if the connection to the database cannot be established, or
     *             any error happens when loading the configuration
     *
     * @since 1.1
     */
    public InformixTimeTrackerProjectPersistence(String dbNamespace,
            String connectionProducerName,
            String projectSearchUtilityNamespace,
            String clientSearchUtilityNamespace) throws PersistenceException {
        DBUtil.checkStringIAE(dbNamespace, "dbNamespace");
        DBUtil.checkStringIAE(connectionProducerName, "connectionProducerName");
        DBUtil.checkStringIAE(projectSearchUtilityNamespace,
                "projectSearchUtilityNamespace");
        DBUtil.checkStringIAE(clientSearchUtilityNamespace,
                "clientSearchUtilityNamespace");

        init(dbNamespace, connectionProducerName,
                projectSearchUtilityNamespace, clientSearchUtilityNamespace);
    }

    /**
     * <p>
     * Adds a new client to the database. If the id of the client is used false
     * will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the client table. All the projects
     * contained by the client will be added to the client_project table. The
     * project information will also be added to the project table.
     * </p>
     *
     * @param client
     *            the client to add
     *
     * @return true if the client was added, false otherwise
     *
     * @throws NullPointerException
     *             if the client is null
     * @throws PersistenceException
     *             if something wrong happens while adding the client (such as
     *             SQL exception)
     */
    public boolean addClient(Client client) throws PersistenceException {
        if (client == null) {
            throw new NullPointerException("client is null");
        }

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = addClientImpl(client);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Deletes a client from the database. If a client with the specified id
     * does not exist false will be returned.
     * </p>
     *
     * <p>
     * This method removes a row from the client table. It will also remove
     * multiple rows from the client_project table.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return true if the client was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the client (such as
     *             SQL exception)
     */
    public boolean removeClient(int clientId) throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = removeClientImpl(clientId);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Deletes all clients from the database.
     * </p>
     *
     * <p>
     * This method will remove all rows from the client and client_project
     * tables.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the clients (such
     *             as SQL exception)
     */
    public void removeAllClients() throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // remove all clients from client_project table
            remove(SQL_DEL_ALL_CLIENT_PROJECTS);

            // remove all clients from client table
            remove(SQL_DEL_ALL_CLIENTS);

            // commit the transaction
            DBUtil.commit(connection);
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to remove all clients", e);
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Updates a client in the database. If the client does not exist in the
     * database false will be returned.
     * </p>
     *
     * <p>
     * This method will only update the client table. It will not update the
     * projects of the client. Projects will be added or deleted using other
     * methods.
     * </p>
     *
     * @param client
     *            the client to update
     *
     * @return true if the client was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the client is null
     * @throws PersistenceException
     *             if something wrong happens while updating the client (such as
     *             SQL exception)
     */
    public boolean updateClient(Client client) throws PersistenceException {
        if (client == null) {
            throw new NullPointerException("client is null");
        }

        try {
            // use current date for modification_date
            Date date = new Date();

            // update the client in client table
            // no transaction is used here for one SQL statement
            PreparedStatement pstmt = getPreparedStatement(SQL_UPD_CLIENT);

            // Added in version 2.0
            pstmt.setInt(1, client.getCompanyId());

            pstmt.setString(2, client.getName());
            pstmt.setTimestamp(3, DBUtil.toSQLDate(date));
            pstmt.setString(4, client.getModificationUser());
            pstmt.setInt(5, client.getId());

            boolean result = pstmt.executeUpdate() > 0;

            // populate the date
            client.setModificationDate(date);

            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to update the client", e);
        }
    }

    /**
     * <p>
     * Adds a project to a client. If the project already has a client assigned
     * false will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the client_project table. The project
     * information will also be added to the project table.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param project
     *            the project to add to the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws NullPointerException
     *             if the project or user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while adding the project to the
     *             client (such as SQL exception)
     */
    public boolean addProjectToClient(int clientId, Project project, String user)
            throws PersistenceException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }
        DBUtil.checkStringNPE(user, "user");

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = addProjectToClientImpl(clientId, project, user);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves the project with the specified clientId and projectId from the
     * database. If there is no such project associated with the client, null
     * will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param projectId
     *            the id of the project
     *
     * @return the project of the client with the specified clientId and
     *         projectId
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project of the
     *             client (such as SQL exception)
     */
    public Project getClientProject(int clientId, int projectId)
            throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // check if the client is assigned to the project
            Project project = exist(SQL_SEL_CLIENT_PROJECT_BY_BOTH, clientId,
                    projectId) ? getProjectImpl(projectId) : null;
            DBUtil.commit(connection);
            return project;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to get client project", e);
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves all the projects of the client from the database. If there are
     * no projects associated with the client, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return a list containing all the projects of the client
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the projects of the
     *             client (such as SQL exception)
     */
    public List getAllClientProjects(int clientId) throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            List clientProjects = getAllClientProjectsImpl(clientId);
            DBUtil.commit(connection);
            return clientProjects;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves the client with the specified clientId from the database. If
     * the client does not exist, null will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return the client with the given id
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the client (such as
     *             SQL exception)
     */
    public Client getClient(int clientId) throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            Client client = getClientImpl(clientId);
            DBUtil.commit(connection);
            return client;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves all the clients from the database. If there are no clients in
     * the database, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @return a list containing all the clients
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the clients (such as
     *             SQL exception)
     */
    public List getAllClients() throws PersistenceException {
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // get all the clients from client table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_ALL_CLIENTS);

            rs = pstmt.executeQuery();

            List clients = new ArrayList();

            while (rs.next()) {
                // populate the fields of each client
                clients.add(getClientImpl(rs));
            }
            DBUtil.commit(connection);
            return clients;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get all clients", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.rollback(connection);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Adds a new project to the database. If the id of the project is used
     * false will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the project table. It will not add the
     * manager, workers, time entries and expense entries to other tables. They
     * should be added using other methods.
     * </p>
     *
     * @param project
     *            the project to add
     *
     * @return true if the project was added, false otherwise
     *
     * @throws NullPointerException
     *             if the project is null
     * @throws PersistenceException
     *             if something wrong happens while adding the project (such as
     *             SQL exception)
     */
    public boolean addProject(Project project) throws PersistenceException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = addProjectImpl(project);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Updates an project in the database. If the project does not exist in the
     * database false will be returned.
     * </p>
     *
     * <p>
     * This method will only update the project table. It will not update the
     * workers, project manager, time entries, expense entries of the project.
     * They will be added or deleted using other methods.
     * </p>
     *
     * @param project
     *            the project to update
     *
     * @return true if the project was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the project is null
     * @throws PersistenceException
     *             if something wrong happens while updating the project (such
     *             as SQL exception)
     */
    public boolean updateProject(Project project) throws PersistenceException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }

        try {
            // use current date for modification_date
            Date date = new Date();

            // update the project in project table
            // no transaction is used here for one SQL statement
            PreparedStatement pstmt = getPreparedStatement(SQL_UPD_PROJECT);

            // Added in version 2.0
            pstmt.setInt(1, project.getCompanyId());

            pstmt.setString(2, project.getName());
            pstmt.setString(3, project.getDescription());
            pstmt.setTimestamp(4, DBUtil.toSQLDate(project.getStartDate()));
            pstmt.setTimestamp(5, DBUtil.toSQLDate(project.getEndDate()));
            pstmt.setTimestamp(6, DBUtil.toSQLDate(date));
            pstmt.setString(7, project.getModificationUser());
            pstmt.setInt(8, project.getId());

            boolean result = pstmt.executeUpdate() > 0;

            // populate the date
            project.setModificationDate(date);

            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to update project", e);
        }
    }

    /**
     * <p>
     * Deletes a project from the database. If a project with the specified id
     * does not exist false will be returned.
     * </p>
     *
     * <p>
     * This method removes a row from the project table. The workers of this
     * project will be deleted from project_worker table. Expense entries will
     * be deleted from the project_expense table. Time Entries of this project
     * will be deleted from project_time table. The project manager of this
     * project will be deleted from the project_manager table. The client of
     * this project will be deleted from the client_project table.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return true if the project was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project (such
     *             as SQL exception)
     */
    public boolean removeProject(int projectId) throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = removeProjectImpl(projectId);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Deletes all projects from the database.
     * </p>
     *
     * <p>
     * This method will remove all rows from project, project_worker,
     * project_manager, project_expense, client_project and project_time tables.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the projects (such
     *             as SQL exception)
     */
    public void removeAllProjects() throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // remove all expense entries
            remove(SQL_DEL_ALL_EXPENSE_ENTRIES);

            // remove all time entries
            remove(SQL_DEL_ALL_TIME_ENTRIES);

            // remove all project managers
            remove(SQL_DEL_ALL_PROJECT_MANAGERS);

            // remove all project workers
            remove(SQL_DEL_ALL_PROJECT_WORKERS);

            // remove all clients of the projects
            remove(SQL_DEL_ALL_CLIENT_PROJECTS);

            // remove all projects from project table
            remove(SQL_DEL_ALL_PROJECTS);

            // commit the transaction
            DBUtil.commit(connection);
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to remove all projects", e);
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Assigns a client to an existing project. If the project already has a
     * client assigned false will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the client_project table.
     * </p>
     *
     * @param projectId
     *            the id of the project
     * @param clientId
     *            the id of the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return true if the client was assigned to the project, false otherwise
     *
     * @throws NullPointerException
     *             if the user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while assigning the client (such
     *             as SQL exception)
     */
    public boolean assignClient(int projectId, int clientId, String user)
            throws PersistenceException {
        DBUtil.checkStringNPE(user, "user");

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = assignClientImpl(projectId, clientId, user);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves the client of a project. If the project has no client assigned,
     * null will be returned.
     * </p>
     *
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the client of the project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the client of the
     *             project (such as SQL exception)
     */
    public Client getProjectClient(int projectId) throws PersistenceException {
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // get the client from client_project table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_CLIENT_PROJECT);

            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            Client client = rs.next() ? getClientImpl(rs.getInt(1)) : null;
            DBUtil.commit(connection);
            return client;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to get project client", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Assigns a project manager to an existing project. If the project already
     * has a manager assigned false will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the project_manager table.
     * </p>
     *
     * @param projectManager
     *            the project manager to assign
     *
     * @return true if the manager was assigned, false otherwise
     *
     * @throws NullPointerException
     *             if the projectManager is null
     * @throws PersistenceException
     *             if something wrong happens while assigning the project
     *             manager (such as SQL exception)
     */
    public boolean assignProjectManager(ProjectManager projectManager)
            throws PersistenceException {
        if (projectManager == null) {
            throw new NullPointerException("projectManager is null");
        }

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // if a manager was already assigned to project, do not assign again
            // we do not use getProjectManager() here to save access to
            // Projects-related tables
            if (exist(SQL_SEL_PROJECT_MANAGER, projectManager.getProject()
                    .getId())) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the project manager to project_manager table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_PROJECT_MANAGER);

            pstmt.setInt(1, projectManager.getProject().getId());
            pstmt.setInt(2, projectManager.getManagerId());
            pstmt.setTimestamp(3, DBUtil.toSQLDate(date));
            pstmt.setString(4, projectManager.getCreationUser());
            pstmt.setTimestamp(5, DBUtil.toSQLDate(date));
            pstmt.setString(6, projectManager.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            projectManager.setCreationDate(date);
            projectManager.setModificationDate(date);
            projectManager
                    .setModificationUser(projectManager.getCreationUser());

            // commit the transaction
            DBUtil.commit(connection);
            return true;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException(
                    "Fails to assign manager to project", e);
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves the project manager of a project. If there is no project
     * manager associated with the project, null will be returned.
     * </p>
     *
     * <p>
     * The fields of the ProjectManager object will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the project manager of the project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project manager
     *             (such as SQL exception)
     */
    public ProjectManager getProjectManager(int projectId)
            throws PersistenceException {
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // get the project manager from project_manager table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_PROJECT_MANAGER);

            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            ProjectManager manager = new ProjectManager();

            // populate the fields of project manager
            manager.setProject(getProjectImpl(rs.getInt(1)));
            manager.setManagerId(rs.getInt(2));
            manager.setCreationDate(DBUtil.toUtilDate(rs.getTimestamp(3)));
            manager.setCreationUser(rs.getString(4));
            manager.setModificationDate(DBUtil.toUtilDate(rs.getTimestamp(5)));
            manager.setModificationUser(rs.getString(6));

            DBUtil.commit(connection);
            return manager;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project manager", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.rollback(connection);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Deletes a project manager from the database. This means that this manager
     * will no longer be the project manager of this project. If the project
     * does not have the manager assigned, false will be returned.
     * </p>
     *
     * <p>
     * This method removes a row from the project_manager table.
     * </p>
     *
     * @param managerId
     *            the id of the manager
     * @param projectId
     *            the id of the project
     *
     * @return true if the manager was removed from the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project manager
     *             (such as SQL exception)
     */
    public boolean removeProjectManager(int managerId, int projectId)
            throws PersistenceException {
        try {
            // remove the project manager from project_manager table
            return remove(SQL_DEL_PROJECT_MANAGER, managerId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project manager", e);
        }
    }

    /**
     * <p>
     * Assigns a project worker to an existing project. If the project already
     * has this worker assigned false will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the project_worker table.
     * </p>
     *
     * @param projectWorker
     *            the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws NullPointerException
     *             if the projectWorker is null
     * @throws PersistenceException
     *             if something wrong happens while assigning the project worker
     *             (such as SQL exception)
     */
    public boolean addWorker(ProjectWorker projectWorker)
            throws PersistenceException {
        if (projectWorker == null) {
            throw new NullPointerException("projectWorker is null");
        }

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = addWorkerImpl(projectWorker);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Updates a worker in the database. If the worker was not assigned to the
     * project false will be returned.
     * </p>
     *
     * <p>
     * This method will only update the project_worker table.
     * </p>
     *
     * @param projectWorker
     *            the worker to update
     *
     * @return true if the worker was updated, false otherwise
     *
     * @throws NullPointerException
     *             if the projectWorker is null
     * @throws PersistenceException
     *             if something wrong happens while updating the project worker
     *             (such as SQL exception)
     */
    public boolean updateWorker(ProjectWorker projectWorker)
            throws PersistenceException {
        if (projectWorker == null) {
            throw new NullPointerException("projectWorker is null");
        }

        try {

            // use current date for modification_date
            Date date = new Date();

            // update the project worker in project_worker table
            // no transaction is used here for one SQL statement
            PreparedStatement pstmt = getPreparedStatement(SQL_UPD_PROJECT_WORKER);

            pstmt.setTimestamp(1, DBUtil
                    .toSQLDate(projectWorker.getStartDate()));
            pstmt.setTimestamp(2, DBUtil.toSQLDate(projectWorker.getEndDate()));
            pstmt.setBigDecimal(3, projectWorker.getPayRate());
            pstmt.setTimestamp(4, DBUtil.toSQLDate(date));
            pstmt.setString(5, projectWorker.getModificationUser());
            pstmt.setInt(6, projectWorker.getProject().getId());
            pstmt.setInt(7, projectWorker.getWorkerId());

            boolean result = pstmt.executeUpdate() > 0;

            // populate the date
            projectWorker.setModificationDate(date);

            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to update project worker", e);
        }
    }

    /**
     * <p>
     * Deletes a project worker from the database. This means that this worker
     * will no longer be a worker for this project. If the project does not have
     * the worker assigned, false will be returned.
     * </p>
     *
     * <p>
     * This method will remove a row from the project_worker table.
     * </p>
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return true if the worker was removed from the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project worker
     *             (such as SQL exception)
     */
    public boolean removeWorker(int workerId, int projectId)
            throws PersistenceException {
        // call the actual implementation - no transaction is used here for one
        // SQL statement
        return removeWorkerImpl(workerId, projectId);
    }

    /**
     * <p>
     * Deletes all the project workers from the database. This means that the
     * project will have no workers. If the project does not have any worker
     * assigned, false will be returned.
     * </p>
     *
     * <p>
     * This method will remove multiple rows from the project_worker table.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return true if the workers were removed from the project, false
     *         otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project workers
     *             (such as SQL exception)
     */
    public boolean removeWorkers(int projectId) throws PersistenceException {
        try {
            // remove the project workers from project_worker table
            return remove(SQL_DEL_PROJECT_WORKERS, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project workers", e);
        }
    }

    /**
     * <p>
     * Retrieves a worker of a project. If the project does not have this worker
     * assigned null will be returned.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker object will be properly populated.
     * </p>
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return the ProjectWorker of a project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project worker
     *             (such as SQL exception)
     */
    public ProjectWorker getWorker(int workerId, int projectId)
            throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            ProjectWorker projectWorker = getWorkerImpl(workerId, projectId);
            DBUtil.commit(connection);
            return projectWorker;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves all the workers of a project. If there are no workers
     * associated with a project, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return a list containing all the project_worker of a project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project workers
     *             (such as SQL exception)
     */
    public List getWorkers(int projectId) throws PersistenceException {
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // get the project workers from project_worker table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_PROJECT_WORKERS);

            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            List workers = new ArrayList();

            while (rs.next()) {
                // populate the fields of each project worker
                workers.add(getWorkerImpl(rs));
            }
            DBUtil.commit(connection);
            return workers;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to get project workers", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Adds a time entry to an existing project. If the project already has this
     * time entry assigned false will be returned.
     * </p>
     *
     * <p>
     * This method adds a row to the project_time table.
     * </p>
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the time entry for the project
     *
     * @return true if the time entry was added, false otherwise
     *
     * @throws NullPointerException
     *             if the user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while adding the time entry (such
     *             as SQL exception)
     */
    public boolean addTimeEntry(int entryId, int projectId, String user)
            throws PersistenceException {
        DBUtil.checkStringNPE(user, "user");

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = addTimeEntryImpl(entryId, projectId, user);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Deletes a time entry of a project from the database. If the project does
     * not have this time entry assigned false will be returned.
     * </p>
     *
     * <p>
     * This method removes a row from the project_time table.
     * </p>
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the time entry
     *             (such as SQL exception)
     */
    public boolean removeTimeEntry(int entryId, int projectId)
            throws PersistenceException {
        try {
            // remove the time entry from project_time table
            return remove(SQL_DEL_TIME_ENTRY, entryId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove time entry", e);
        }
    }

    /**
     * <p>
     * Retrieves all the time entries of a project. If there are no time entries
     * associated with a project, an empty list will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return a list containing the ids of the time entries
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the time entries
     *             (such as SQL exception)
     */
    public List getTimeEntries(int projectId) throws PersistenceException {
        try {
            // get the time entries from project_time table
            return getIntegers(SQL_SEL_TIME_ENTRIES, projectId, 2);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get time entries", e);
        }
    }

    /**
     * <p>
     * Adds an expense entry to an existing project. If the project already has
     * this expense entry assigned false will be returned.
     * </p>
     *
     * <p>
     * This method adds a row to the project_expense table.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the expense entry for the project
     *
     * @return true if the expense entry was added, false otherwise
     *
     * @throws NullPointerException
     *             if the user is null
     * @throws IllegalArgumentException
     *             if the user is the empty string
     * @throws PersistenceException
     *             if something wrong happens while adding the expense entry
     *             (such as SQL exception)
     */
    public boolean addExpenseEntry(int entryId, int projectId, String user)
            throws PersistenceException {
        DBUtil.checkStringNPE(user, "user");

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            boolean result = addExpenseEntryImpl(entryId, projectId, user);

            // commit the transaction
            DBUtil.commit(connection);
            return result;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Deletes an expense entry of a project from the database. If the project
     * does not have this expense entry assigned false will be returned.
     * </p>
     *
     * <p>
     * This method removes a row from the project_expense table.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the expense entry
     *             (such as SQL exception)
     */
    public boolean removeExpenseEntry(int entryId, int projectId)
            throws PersistenceException {
        try {
            // remove the expense entry from project_expense table
            return remove(SQL_DEL_EXPENSE_ENTRY, entryId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove expense entry", e);
        }
    }

    /**
     * <p>
     * Retrieves all the expense entries of a project. If there are no expense
     * entries associated with a project, an empty list will be returned.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return a list containing the ids of the expense entries
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the expense entries
     *             (such as SQL exception)
     */
    public List getExpenseEntries(int projectId) throws PersistenceException {
        try {
            // get the expense entries from project_expense table
            return getIntegers(SQL_SEL_EXPENSE_ENTRIES, projectId, 2);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get expense entries", e);
        }
    }

    /**
     * <p>
     * Retrieves a project from the database. If the project does not exist,
     * null will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the project with the given id
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project (such as
     *             SQL exception)
     */
    public Project getProject(int projectId) throws PersistenceException {
        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // call the actual implementation
            Project project = getProjectImpl(projectId);
            DBUtil.commit(connection);
            return project;
        } catch (PersistenceException e) {
            DBUtil.rollback(connection);
            throw e;
        } finally {
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Retrieves all the projects from the database. If there are no projects in
     * the database, an empty list will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @return a list containing all the projects
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the projects (such
     *             as SQL exception)
     */
    public List getAllProjects() throws PersistenceException {
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // get all the projects from project table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_ALL_PROJECTS);

            rs = pstmt.executeQuery();

            List projects = new ArrayList();

            while (rs.next()) {
                // populate the fields of each project
                projects.add(getProjectImpl(rs));
            }
            DBUtil.commit(connection);
            return projects;
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to get all projects", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Removes a project from a client. If the client was not assigned the
     * project, false will be returned.
     * </p>
     *
     * <p>
     * This method removes a row from the client_project table.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param projectId
     *            the id of the project
     *
     * @return true if the project was removed from client, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project from
     *             client (such as SQL exception)
     */
    public boolean removeProjectFromClient(int clientId, int projectId)
            throws PersistenceException {
        try {
            // remove the project from client_project table
            return remove(SQL_DEL_CLIENT_PROJECT_BY_BOTH, clientId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Fails to remove project from client", e);
        }
    }

    /**
     * <p>
     * Closes the connection to the database. After this method is called, the
     * instance should be discarded.
     * </p>
     *
     * @throws PersistenceException
     *             if something wrong happens while closing the connection (such
     *             as SQL exception)
     */
    public void closeConnection() throws PersistenceException {
        try {
            // close the cached prepared statements
            for (Iterator i = pstmts.values().iterator(); i.hasNext();) {
                try {
                    ((PreparedStatement) i.next()).close();
                } catch (SQLException ex) {
                    // ignore the exception
                }
            }

            // close the connection
            connection.close();
        } catch (SQLException e) {
            throw new PersistenceException("Fails to close connection", e);
        }
    }

    /**
     * <p>
     * Searches the projects from the database which matches the given search
     * filter. If there are no such projects in the database, an empty array
     * will be returned.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param searchFilter
     *            the search filter to match.
     *
     * @return the array of matching projects.
     *
     * @throws IllegalArgumentException
     *             if the searchFilter is null, or the searchFilter is not
     *             supported, or no column name is found for the column alias.
     * @throws PersistenceException
     *             if something wrong happens while searching the projects (such
     *             as SQL exception)
     *
     * @since 1.1
     */
    public Project[] searchForProjects(Filter searchFilter)
            throws PersistenceException {
        if (searchFilter == null) {
            throw new IllegalArgumentException("searchFilter is null");
        }

        // build the prepared statement with project search utility
        PreparedStatement pstmt = projectSearchUtility
                .prepareSearchStatement(searchFilter);
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // search the projects
            rs = pstmt.executeQuery();

            List projects = new ArrayList();

            while (rs.next()) {
                // populate the fields of each project
                projects.add(getProjectImpl(rs.getInt(1)));
            }
            DBUtil.commit(connection);
            return (Project[]) projects.toArray(new Project[0]);
        } catch (SQLException e) {
            DBUtil.rollback(connection);
            throw new PersistenceException("Fails to search projects", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Searches the clients from the database which matches the given search
     * filter. If there are no such clients in the database, an empty array will
     * be returned.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @param searchFilter
     *            the search filter to match.
     *
     * @return the array of matching clients.
     *
     * @throws IllegalArgumentException
     *             if the searchFilter is null, or the searchFilter is not
     *             supported, or no column name is found for the column alias.
     * @throws PersistenceException
     *             if something wrong happens while searching the clients (such
     *             as SQL exception)
     *
     * @since 1.1
     */
    public Client[] searchForClients(Filter searchFilter)
            throws PersistenceException {
        if (searchFilter == null) {
            throw new IllegalArgumentException("searchFilter is null");
        }

        // build the prepared statement with client search utility
        PreparedStatement pstmt = clientSearchUtility
                .prepareSearchStatement(searchFilter);
        ResultSet rs = null;

        try {
            // start the transaction
            DBUtil.startTransaction(connection);

            // search the clients
            rs = pstmt.executeQuery();

            List clients = new ArrayList();

            while (rs.next()) {
                // populate the fields of each client
                clients.add(getClientImpl(rs.getInt(1)));
            }
            DBUtil.commit(connection);
            return (Client[]) clients.toArray(new Client[0]);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to search clients", e);
        } finally {
            DBUtil.close(rs);
            DBUtil.close(pstmt);
            DBUtil.rollback(connection);
            DBUtil.endTransaction(connection);
        }
    }

    /**
     * <p>
     * Adds the clients to the database. If the id of any client is used, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param clients
     *            the clients to add.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the clients is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while adding the clients in a
     *             batch.
     *
     * @see #addClient(Client)
     * @since 1.1
     */
    public void addClients(Client[] clients, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(clients, "clients");

        Throwable[] causes = new Throwable[clients.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < clients.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!addClientImpl(clients[i])) {
                    throw new PersistenceException("Client already exists");
                }

                // commit the transaction in atomic mode
                if ((i == (clients.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }
        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to add the clients");
    }

    /**
     * <p>
     * Deletes the clients from the database. If a client with any specified id
     * does not exist, a BatchOperationException will be thrown.
     * </p>
     *
     * @param clientIds
     *            the ids of clients to delete.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the clientIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the clients in a
     *             batch.
     *
     * @see #removeClient(int)
     * @since 1.1
     */
    public void removeClients(int[] clientIds, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(clientIds, "clientIds");

        Throwable[] causes = new Throwable[clientIds.length];
        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < clientIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!removeClientImpl(clientIds[i])) {
                    throw new PersistenceException("Client does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (clientIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to remove the clients");
    }

    /**
     * <p>
     * Updates the clients in the database. If any client does not exist in the
     * database, a BatchOperationException will be thrown.
     * </p>
     *
     * @param clients
     *            the clients to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the clients is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the clients in a
     *             batch.
     *
     * @see #updateClient(Client)
     * @since 1.1
     */
    public void updateClients(Client[] clients, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(clients, "clients");

        Throwable[] causes = new Throwable[clients.length];
        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < clients.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!updateClient(clients[i])) {
                    throw new PersistenceException("Client does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (clients.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to update the clients");
    }

    /**
     * <p>
     * Retrieves the clients with the specified clientIds from the database. If
     * any client does not exist, a BatchOperationException will be thrown if it
     * fails to retrieve at least one client (in atomic mode), or if it fails to
     * retrieve all clients (in non-atomic mode). When the operation is not
     * atomic, and at least one client was successfully retrieved, the returned
     * array will contain nulls in the places where the clients cannot be
     * retrieved.
     * </p>
     *
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @param clientIds
     *            the ids of the clients to retrieve.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return the clients with the given ids.
     *
     * @throws IllegalArgumentException
     *             if the clientIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while retrieving the clients in a
     *             batch.
     *
     * @see #getClient(int)
     * @since 1.1
     */
    public Client[] getClients(int[] clientIds, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(clientIds, "clientIds");

        Client[] clients = new Client[clientIds.length];
        Throwable[] causes = new Throwable[clientIds.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < clientIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                clients[i] = getClientImpl(clientIds[i]);

                if (clients[i] == null) {
                    throw new PersistenceException("Client does not exist");
                }

                // end the transaction in atomic mode
                if ((i == (clientIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, end the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkGetCauses(causes, "Fails to get the clients", atomic);

        return clients;
    }

    /**
     * <p>
     * Adds the projects to the persistence. If the id of any project is used, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param projects
     *            the projects to add.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projects is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while adding the projects in a
     *             batch.
     *
     * @see #addProject(Project)
     * @since 1.1
     */
    public void addProjects(Project[] projects, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(projects, "projects");

        Throwable[] causes = new Throwable[projects.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < projects.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!addProjectImpl(projects[i])) {
                    throw new PersistenceException("Project already exists");
                }

                // commit the transaction in atomic mode
                if ((i == (projects.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to add projects");
    }

    /**
     * <p>
     * Deletes the projects from the database. If a project with any specified
     * id does not exist, a BatchOperationException will be thrown.
     * </p>
     *
     * @param projectIds
     *            the ids of the projects to delete.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projectIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the projects in a
     *             batch.
     *
     * @see #removeProject(int)
     * @since 1.1
     */
    public void removeProjects(int[] projectIds, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(projectIds, "projectIds");

        Throwable[] causes = new Throwable[projectIds.length];
        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < projectIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!removeProjectImpl(projectIds[i])) {
                    throw new PersistenceException("Project does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (projectIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to remove projects");
    }

    /**
     * <p>
     * Updates the projects in the database. If any project does not exist in
     * the database, a BatchOperationException will be thrown.
     * </p>
     *
     * @param projects
     *            the projects to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projects is null or empty, or any of its elements is
     *             null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the projects in a
     *             batch.
     *
     * @see #updateProject(Project)
     * @since 1.1
     */
    public void updateProjects(Project[] projects, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(projects, "projects");

        Throwable[] causes = new Throwable[projects.length];
        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < projects.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!updateProject(projects[i])) {
                    throw new PersistenceException("Project does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (projects.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to update projects");
    }

    /**
     * <p>
     * Retrieves the projects from the persistence. If any project does not
     * exist, a BatchOperationException will be thrown if it fails to retrieve
     * at least one project (in atomic mode), or if it fails to retrieve all
     * projects (in non-atomic mode). When the operation is not atomic, and at
     * least one project was successfully retrieved, the returned array will
     * contain nulls in the places where the projects cannot be retrieved.
     * </p>
     *
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param projectIds
     *            the ids of the projects to retrieve.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return the projects with the given ids.
     *
     * @throws IllegalArgumentException
     *             if the projectIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while retrieving the projects in a
     *             batch.
     *
     * @see #getProject(int)
     * @since 1.1
     */
    public Project[] getProjects(int[] projectIds, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(projectIds, "projectIds");

        Project[] projects = new Project[projectIds.length];
        Throwable[] causes = new Throwable[projectIds.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < projectIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                projects[i] = getProjectImpl(projectIds[i]);

                if (projects[i] == null) {
                    throw new PersistenceException("Project does not exist");
                }

                // end the transaction in atomic mode
                if ((i == (projectIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, end the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkGetCauses(causes, "Fails to get projects", atomic);

        return projects;
    }

    /**
     * <p>
     * Assigns the project workers to the existing projects. If any project
     * already has the worker assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * @param workers
     *            the project workers to assign.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projectWorkers is null or empty, or any of its
     *             elements is null.
     * @throws BatchOperationException
     *             if something wrong happens while assigning the project
     *             workers in a batch.
     *
     * @see #addWorker(ProjectWorker)
     * @since 1.1
     */
    public void addWorkers(ProjectWorker[] workers, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(workers, "workers");

        Throwable[] causes = new Throwable[workers.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < workers.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!addWorkerImpl(workers[i])) {
                    throw new PersistenceException(
                            "Project worker already exists");
                }

                // commit the transaction in atomic mode
                if ((i == (workers.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to add project workers");
    }

    /**
     * <p>
     * Deletes the project workers from the database. This means that these
     * workers will no longer be a worker for this project. If the project does
     * not have any of these workers assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * @param workerIds
     *            the ids of the workers to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the workerIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the project workers
     *             in a batch.
     *
     * @see #removeWorker(int, int)
     * @since 1.1
     */
    public void removeWorkers(int[] workerIds, int projectId, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(workerIds, "workerIds");

        Throwable[] causes = new Throwable[workerIds.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < workerIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!removeWorkerImpl(workerIds[i], projectId)) {
                    throw new PersistenceException(
                            "Project worker does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (workerIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to remove project workers");
    }

    /**
     * <p>
     * Updates the workers in the database. If the workers were not assigned to
     * the projects, a BatchOperationException will be thrown.
     * </p>
     *
     * @param workers
     *            the workers to update.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the projectWorkers is null or empty, or any of its
     *             elements is null.
     * @throws BatchOperationException
     *             if something wrong happens while updating the project workers
     *             in a batch.
     *
     * @see #updateWorker(ProjectWorker)
     * @since 1.1
     */
    public void updateWorkers(ProjectWorker[] workers, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(workers, "workers");

        Throwable[] causes = new Throwable[workers.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < workers.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!updateWorker(workers[i])) {
                    throw new PersistenceException(
                            "Project worker does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (workers.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to update project workers");
    }

    /**
     * <p>
     * Retrieves the workers of a project. If the project does not have these
     * workers assigned, a BatchOperationException will be thrown if it fails to
     * retrieve at least one worker (in atomic mode), or if it fails to retrieve
     * all workers (in non-atomic mode). When the operation is not atomic, and
     * at least one worker was successfully retrieved, the returned array will
     * contain nulls in the places where the workers cannot be retrieved.
     * </p>
     *
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param workerIds
     *            the ids of the workers to retrieve.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @return the project workers with the given ids.
     *
     * @throws IllegalArgumentException
     *             if the workerIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while retrieving the project
     *             workers in a batch.
     *
     * @see #getWorker(int, int)
     * @since 1.1
     */
    public ProjectWorker[] getWorkers(int[] workerIds, int projectId,
            boolean atomic) throws BatchOperationException {
        DBUtil.checkArray(workerIds, "workerIds");

        ProjectWorker[] workers = new ProjectWorker[workerIds.length];
        Throwable[] causes = new Throwable[workerIds.length];
        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < workerIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                workers[i] = getWorkerImpl(workerIds[i], projectId);

                if (workers[i] == null) {
                    throw new PersistenceException(
                            "Project worker does not exist");
                }

                // end the transaction in atomic mode
                if ((i == (workerIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, end the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkGetCauses(causes, "Fails to get project workers", atomic);

        return workers;
    }

    /**
     * <p>
     * Adds the time entries to an existing project. If the project already has
     * any of these time entries assigned, a BatchOperationException will be
     * thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the time entries to add.
     * @param projectId
     *            the id of the project.
     * @param user
     *            the user who created the time entries for the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty, or the user is null or the
     *             empty string.
     * @throws BatchOperationException
     *             if something wrong happens while adding the time entries in a
     *             batch.
     *
     * @see #addTimeEntry(int, int, String)
     * @since 1.1
     */
    public void addTimeEntries(int[] entryIds, int projectId, String user,
            boolean atomic) throws BatchOperationException {
        DBUtil.checkArray(entryIds, "entryIds");
        DBUtil.checkStringIAE(user, "user");

        Throwable[] causes = new Throwable[entryIds.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < entryIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!addTimeEntryImpl(entryIds[i], projectId, user)) {
                    throw new PersistenceException("Time entry already exists");
                }

                // commit the transaction in atomic mode
                if ((i == (entryIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to add time entries");
    }

    /**
     * <p>
     * Deletes the time entries of a project from the database. If the project
     * does not have any of these time entries assigned, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the time entries to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the time entries in
     *             a batch.
     *
     * @see #removeTimeEntry(int, int)
     * @since 1.1
     */
    public void removeTimeEntries(int[] entryIds, int projectId, boolean atomic)
            throws BatchOperationException {
        DBUtil.checkArray(entryIds, "entryIds");

        Throwable[] causes = new Throwable[entryIds.length];
        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < entryIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!removeTimeEntry(entryIds[i], projectId)) {
                    throw new PersistenceException("Time entry does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (entryIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to remove time entries");
    }

    /**
     * <p>
     * Adds the expense entries to an existing project. If the project already
     * has any of these expense entries assigned, a BatchOperationException will
     * be thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the expense entries to add.
     * @param projectId
     *            the id of the project.
     * @param user
     *            the user who created the expense entries for the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty, or the user is null or the
     *             empty string.
     * @throws BatchOperationException
     *             if something wrong happens while adding the expense entries
     *             in a batch.
     *
     * @see #addExpenseEntry(int, int, String)
     * @since 1.1
     */
    public void addExpenseEntries(int[] entryIds, int projectId, String user,
            boolean atomic) throws BatchOperationException {
        DBUtil.checkArray(entryIds, "entryIds");
        DBUtil.checkStringIAE(user, "user");

        Throwable[] causes = new Throwable[entryIds.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < entryIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!addExpenseEntryImpl(entryIds[i], projectId, user)) {
                    throw new PersistenceException(
                            "Expense entry already exists");
                }

                // commit the transaction in atomic mode
                if ((i == (entryIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to add expense entries");
    }

    /**
     * <p>
     * Deletes the expense entries of a project from the database. If the
     * project does not have any of these expense entries assigned, a
     * BatchOperationException will be thrown.
     * </p>
     *
     * @param entryIds
     *            the ids of the expense entries to delete.
     * @param projectId
     *            the id of the project.
     * @param atomic
     *            whether the operation should be atomic (all-or-nothing).
     *
     * @throws IllegalArgumentException
     *             if the entryIds is null or empty.
     * @throws BatchOperationException
     *             if something wrong happens while deleting the expense entries
     *             in a batch.
     *
     * @see #removeExpenseEntry(int, int)
     * @since 1.1
     */
    public void removeExpenseEntries(int[] entryIds, int projectId,
            boolean atomic) throws BatchOperationException {
        DBUtil.checkArray(entryIds, "entryIds");

        Throwable[] causes = new Throwable[entryIds.length];

        if (!atomic) {
            DBUtil.endTransaction(connection);
        }
        for (int i = 0; i < entryIds.length; i++) {
            try {
                // start the transaction in atomic mode
                if ((i == 0) && atomic) {
                    DBUtil.startTransaction(connection);
                }

                // call the actual implementation
                if (!removeExpenseEntry(entryIds[i], projectId)) {
                    throw new PersistenceException(
                            "Expense entry does not exist");
                }

                // commit the transaction in atomic mode
                if ((i == (entryIds.length - 1)) && atomic) {
                    DBUtil.commit(connection);
                }
            } catch (PersistenceException e) {
                causes[i] = e;

                // if any error occurs, rollback the transaction in atomic mode
                if (atomic) {
                    DBUtil.rollback(connection);
                    break; // stop further processing
                }
            }
        }
        if (atomic) {
            DBUtil.endTransaction(connection);
        }

        // throw exception if there is any failure
        DBUtil.checkAnyCauses(causes, "Fails to remove expense entries");
    }

    /**
     * <p>
     * A convenient method to check the existence of a row in the database by
     * executing the SQL statement with one integer parameter. The SQL statement
     * should be a SELECT statement.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     * @param param1
     *            the integer parameter
     *
     * @return whether the row exists or not
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private boolean exist(String sql, int param1) throws SQLException {
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = getPreparedStatement(sql);

            // set one integer parameter
            pstmt.setInt(1, param1);
            rs = pstmt.executeQuery();
            return rs.next();
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * A convenient method to check the existence of a row in the database by
     * executing the SQL statement with two integer parameters. The SQL
     * statement should be a SELECT statement.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     * @param param1
     *            first integer parameter
     * @param param2
     *            second integer parameter
     *
     * @return whether the row exists or not
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private boolean exist(String sql, int param1, int param2)
            throws SQLException {
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = getPreparedStatement(sql);

            // set two integer parameters
            pstmt.setInt(1, param1);
            pstmt.setInt(2, param2);
            rs = pstmt.executeQuery();
            return rs.next();
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * A convenient method to remove rows from the database by executing the SQL
     * statement with no integer parameters. The SQL statement should be a
     * DELETE statement.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     *
     * @return whether any row was removed
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private boolean remove(String sql) throws SQLException {
        PreparedStatement pstmt = getPreparedStatement(sql);

        // no integer parameters to set
        return pstmt.executeUpdate() > 0;
    }

    /**
     * <p>
     * A convenient method to remove rows from the database by executing the SQL
     * statement with one integer parameter. The SQL statement should be a
     * DELETE statement.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     * @param param1
     *            the integer parameter
     *
     * @return whether any row was removed
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private boolean remove(String sql, int param1) throws SQLException {
        PreparedStatement pstmt = getPreparedStatement(sql);

        // set one integer parameter
        pstmt.setInt(1, param1);
        return pstmt.executeUpdate() > 0;
    }

    /**
     * <p>
     * A convenient method to remove rows from the database by executing the SQL
     * statement with two integer parameters. The SQL statement should be a
     * DELETE statement.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     * @param param1
     *            first integer parameter
     * @param param2
     *            second integer parameter
     *
     * @return whether any row was removed
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private boolean remove(String sql, int param1, int param2)
            throws SQLException {
        PreparedStatement pstmt = getPreparedStatement(sql);

        // set two integer parameters
        pstmt.setInt(1, param1);
        pstmt.setInt(2, param2);
        return pstmt.executeUpdate() > 0;
    }

    /**
     * <p>
     * A convenient method to get the integer located at the specified column
     * index by executing the SQL statement with one integer parameter. The SQL
     * statement should be a SELECT statement. If no rows are returned, this
     * method returns -1. If multiple rows are returned, only the first row is
     * read.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     * @param param1
     *            the integer parameter
     * @param columnIndex
     *            the column index to get the integer
     *
     * @return the integer located at the specified column index
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private int getInteger(String sql, int param1, int columnIndex)
            throws SQLException {
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = getPreparedStatement(sql);

            // set one integer parameter
            pstmt.setInt(1, param1);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return -1;
            } else {
                return rs.getInt(columnIndex);
            }
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * A convenient method to get the integers located at the specified column
     * index by executing the SQL statement with one integer parameter. The SQL
     * statement should be a SELECT statement. If no rows are returned, this
     * method returns an empty list.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the SQL statement to execute
     * @param param1
     *            the integer parameter
     * @param columnIndex
     *            the column index to get the integers
     *
     * @return the integers located at the specified column index
     *
     * @throws SQLException
     *             if any database error occurs
     */
    private List getIntegers(String sql, int param1, int columnIndex)
            throws SQLException {
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = getPreparedStatement(sql);

            // set one integer parameter
            pstmt.setInt(1, param1);
            rs = pstmt.executeQuery();

            List integers = new ArrayList();

            while (rs.next()) {
                integers.add(new Integer(rs.getInt(columnIndex)));
            }
            return integers;
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * Gets the project with the data from the current row of the given
     * ResultSet. The current row should be a row pointing to the project table.
     * The expense entries, time entries, workers and manager will also be
     * populated.
     * </p>
     *
     * @param rs
     *            the ResultSet containing the project data
     *
     * @return the project with all the fields populated
     *
     * @throws PersistenceException
     *             if something wrong happens while populating the fields
     */
    private Project getProjectImpl(ResultSet rs) throws PersistenceException {
        try {
            Project project = new Project();

            // populate the fields of project
            // added/modified in version 2.0
            project.setId(rs.getInt(1));
            project.setCompanyId(rs.getInt(2));
            project.setName(rs.getString(3));
            project.setDescription(rs.getString(4));
            project.setStartDate(DBUtil.toUtilDate(rs.getTimestamp(5)));
            project.setEndDate(DBUtil.toUtilDate(rs.getTimestamp(6)));
            project.setCreationDate(DBUtil.toUtilDate(rs.getTimestamp(7)));
            project.setCreationUser(rs.getString(8));
            project.setModificationDate(DBUtil.toUtilDate(rs.getTimestamp(9)));
            project.setModificationUser(rs.getString(10));

            int projectId = project.getId();

            // populate the project manager id of project
            project.setManagerId(getInteger(SQL_SEL_PROJECT_MANAGER_ID,
                    projectId, 1));

            // populate the project workers ids of project
            project.setWorkersIds(new HashSet(getIntegers(
                    SQL_SEL_PROJECT_WORKER_IDS, projectId, 1)));

            // populate the expense entries of project
            project
                    .setExpenseEntries(new HashSet(getExpenseEntries(projectId)));

            // populate the time entries of project
            project.setTimeEntries(new HashSet(getTimeEntries(projectId)));

            return project;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project", e);
        }
    }

    /**
     * <p>
     * Gets the project worker with the data from the current row of the given
     * ResultSet. The current row should be a row pointing to the project_worker
     * table.
     * </p>
     *
     * @param rs
     *            the ResultSet containing the project worker data
     *
     * @return the project worker with all the fields populated
     *
     * @throws PersistenceException
     *             if something wrong happens while populating the fields
     */
    private ProjectWorker getWorkerImpl(ResultSet rs)
            throws PersistenceException {
        try {
            ProjectWorker worker = new ProjectWorker();

            // populate the fields of the project worker
            worker.setProject(getProjectImpl(rs.getInt(1)));
            worker.setWorkerId(rs.getInt(2));
            worker.setStartDate(DBUtil.toUtilDate(rs.getTimestamp(3)));
            worker.setEndDate(DBUtil.toUtilDate(rs.getTimestamp(4)));
            worker.setPayRate(rs.getBigDecimal(5));
            worker.setCreationDate(DBUtil.toUtilDate(rs.getTimestamp(6)));
            worker.setCreationUser(rs.getString(7));
            worker.setModificationDate(DBUtil.toUtilDate(rs.getTimestamp(8)));
            worker.setModificationUser(rs.getString(9));

            return worker;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project worker", e);
        }
    }

    /**
     * <p>
     * Gets the client with the data from the current row of the given
     * ResultSet. The current row should be a row pointing to the client table.
     * The projects of the client will also be populated.
     * </p>
     *
     * @param rs
     *            the ResultSet containing the client data
     *
     * @return the client with all the fields populated
     *
     * @throws PersistenceException
     *             if something wrong happens while populating the fields
     */
    private Client getClientImpl(ResultSet rs) throws PersistenceException {
        try {
            Client client = new Client();

            // populate the fields of the client
            // added/modified in version 2.0
            client.setId(rs.getInt(1));
            client.setCompanyId(rs.getInt(2));
            client.setName(rs.getString(3));
            client.setCreationDate(DBUtil.toUtilDate(rs.getTimestamp(4)));
            client.setCreationUser(rs.getString(5));
            client.setModificationDate(DBUtil.toUtilDate(rs.getTimestamp(6)));
            client.setModificationUser(rs.getString(7));

            // populate the projects of the client
            client.setProjects(getAllClientProjectsImpl(client.getId()));

            return client;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get the client", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the addProject() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param project
     *            the project to add
     *
     * @return true if the project was added, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while adding the project (such as
     *             SQL exception)
     */
    private boolean addProjectImpl(Project project) throws PersistenceException {
        try {
            // if the project already exists, do not add it
            // we do not use getProject() here to save access to
            // project-related tables
            if (exist(SQL_SEL_PROJECT, project.getId())) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the project to project table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_PROJECT);

            pstmt.setInt(1, project.getId());
            pstmt.setInt(2, project.getCompanyId());
            pstmt.setString(3, project.getName());
            pstmt.setString(4, project.getDescription());
            pstmt.setTimestamp(5, DBUtil.toSQLDate(project.getStartDate()));
            pstmt.setTimestamp(6, DBUtil.toSQLDate(project.getEndDate()));
            pstmt.setTimestamp(7, DBUtil.toSQLDate(date));
            pstmt.setString(8, project.getCreationUser());
            pstmt.setTimestamp(9, DBUtil.toSQLDate(date));
            pstmt.setString(10, project.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            project.setCreationDate(date);
            project.setModificationDate(date);
            project.setModificationUser(project.getCreationUser());

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add project", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the removeProject() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return true if the project was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project (such
     *             as SQL exception)
     */
    private boolean removeProjectImpl(int projectId)
            throws PersistenceException {
        try {
            // remove the expense entries of the project
            remove(SQL_DEL_EXPENSE_ENTRIES, projectId);

            // remove the time entries of the project
            remove(SQL_DEL_TIME_ENTRIES, projectId);

            // remove the manager of the project
            remove(SQL_DEL_PROJECT_MANAGERS, projectId);

            // remove the workers of the project
            remove(SQL_DEL_PROJECT_WORKERS, projectId);

            // remove the client of the project
            remove(SQL_DEL_CLIENT_PROJECT, projectId);

            // remove the project from project table
            return remove(SQL_DEL_PROJECT, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the getProject() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param projectId
     *            the id of the project
     *
     * @return the project with the given id
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project (such as
     *             SQL exception)
     */
    private Project getProjectImpl(int projectId) throws PersistenceException {
        ResultSet rs = null;

        try {
            // get the project from project table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_PROJECT);

            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getProjectImpl(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project", e);
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * The actual implementation of the addWorker() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param projectWorker
     *            the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while assigning the project worker
     *             (such as SQL exception)
     */
    private boolean addWorkerImpl(ProjectWorker projectWorker)
            throws PersistenceException {
        try {
            // if the worker was already assigned to project, do not add it
            // we do not use getWorker() here to save access to project-related
            // tables
            if (exist(SQL_SEL_PROJECT_WORKER, projectWorker.getWorkerId(),
                    projectWorker.getProject().getId())) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the project worker to project_worker table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_PROJECT_WORKER);

            pstmt.setInt(1, projectWorker.getProject().getId());
            pstmt.setInt(2, projectWorker.getWorkerId());
            pstmt.setTimestamp(3, DBUtil
                    .toSQLDate(projectWorker.getStartDate()));
            pstmt.setTimestamp(4, DBUtil.toSQLDate(projectWorker.getEndDate()));
            pstmt.setBigDecimal(5, projectWorker.getPayRate());
            pstmt.setTimestamp(6, DBUtil.toSQLDate(date));
            pstmt.setString(7, projectWorker.getCreationUser());
            pstmt.setTimestamp(8, DBUtil.toSQLDate(date));
            pstmt.setString(9, projectWorker.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            projectWorker.setCreationDate(date);
            projectWorker.setModificationDate(date);
            projectWorker.setModificationUser(projectWorker.getCreationUser());

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add project worker", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the removeWorker() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return true if the worker was removed from the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the project worker
     *             (such as SQL exception)
     */
    private boolean removeWorkerImpl(int workerId, int projectId)
            throws PersistenceException {
        try {
            // remove the project worker from project_worker table
            return remove(SQL_DEL_PROJECT_WORKER, workerId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project worker", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the getWorker() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param workerId
     *            the id of the worker
     * @param projectId
     *            the id of the project
     *
     * @return the ProjectWorker of a project
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the project worker
     *             (such as SQL exception)
     */
    private ProjectWorker getWorkerImpl(int workerId, int projectId)
            throws PersistenceException {
        ResultSet rs = null;

        try {
            // get the project worker from project_worker table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_PROJECT_WORKER);

            pstmt.setInt(1, workerId);
            pstmt.setInt(2, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getWorkerImpl(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project worker", e);
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * The actual implementation of the addClient() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param client
     *            the client to add
     *
     * @return true if the client was added, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while adding the client (such as
     *             SQL exception)
     */
    private boolean addClientImpl(Client client) throws PersistenceException {
        try {
            // if the client already exists, do not add it
            // we do not use getClient() here to save access to client-related
            // tables
            if (exist(SQL_SEL_CLIENT, client.getId())) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the client to client table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_CLIENT);

            // modified in version 2.0
            pstmt.setInt(1, client.getId());
            pstmt.setInt(2, client.getCompanyId());
            pstmt.setString(3, client.getName());
            pstmt.setTimestamp(4, DBUtil.toSQLDate(date));
            pstmt.setString(5, client.getCreationUser());
            pstmt.setTimestamp(6, DBUtil.toSQLDate(date));
            pstmt.setString(7, client.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            client.setCreationDate(date);
            client.setModificationDate(date);
            client.setModificationUser(client.getCreationUser());

            // add the projects of the client
            for (Iterator i = client.getProjects().iterator(); i.hasNext();) {
                addProjectToClientImpl(client.getId(), (Project) i.next(),
                        client.getCreationUser());
            }
            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add the client", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the removeClient() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return true if the client was removed, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while removing the client (such as
     *             SQL exception)
     */
    private boolean removeClientImpl(int clientId) throws PersistenceException {
        try {
            // remove the client from client_project table
            remove(SQL_DEL_CLIENT_PROJECTS, clientId);

            // remove the client from client table
            return remove(SQL_DEL_CLIENT, clientId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove the client", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the getClient() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return the client with the given id
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the client (such as
     *             SQL exception)
     */
    private Client getClientImpl(int clientId) throws PersistenceException {
        ResultSet rs = null;

        try {
            // get the client from client table
            PreparedStatement pstmt = getPreparedStatement(SQL_SEL_CLIENT);

            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getClientImpl(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get the client", e);
        } finally {
            DBUtil.close(rs);
        }
    }

    /**
     * <p>
     * The actual implementation of the assignClient() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param projectId
     *            the id of the project
     * @param clientId
     *            the id of the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return true if the client was assigned to the project, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while assigning the client (such
     *             as SQL exception)
     */
    private boolean assignClientImpl(int projectId, int clientId, String user)
            throws PersistenceException {
        try {
            // if a client was already assigned to project, do not assign again
            // we do not use getProjectClient() here to save access to
            // client-related tables
            if (exist(SQL_SEL_CLIENT_PROJECT, projectId)) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the client to client_project table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_CLIENT_PROJECT);

            pstmt.setInt(1, clientId);
            pstmt.setInt(2, projectId);
            pstmt.setTimestamp(3, DBUtil.toSQLDate(date));
            pstmt.setString(4, user);
            pstmt.setTimestamp(5, DBUtil.toSQLDate(date));
            pstmt.setString(6, user);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to assign client to project",
                    e);
        }
    }

    /**
     * <p>
     * The actual implementation of the addProjectToClient() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param clientId
     *            the id of the client
     * @param project
     *            the project to add to the client
     * @param user
     *            the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while adding the project to the
     *             client (such as SQL exception)
     */
    private boolean addProjectToClientImpl(int clientId, Project project,
            String user) throws PersistenceException {
        // add the project to project table
        addProjectImpl(project);

        // add the client to client_project table
        return assignClientImpl(project.getId(), clientId, user);
    }

    /**
     * <p>
     * The actual implementation of the getAllClientProjects() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param clientId
     *            the id of the client
     *
     * @return a list containing all the projects of the client
     *
     * @throws PersistenceException
     *             if something wrong happens while getting the projects of the
     *             client (such as SQL exception)
     */
    private List getAllClientProjectsImpl(int clientId)
            throws PersistenceException {
        try {
            // get the projects of the client from client_project table
            List projectsIds = getIntegers(SQL_SEL_CLIENT_PROJECTS, clientId, 2);
            List projects = new ArrayList();

            for (Iterator i = projectsIds.iterator(); i.hasNext();) {
                int projectId = ((Integer) i.next()).intValue();

                // populate the fields of each project
                projects.add(getProjectImpl(projectId));
            }
            return projects;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Fails to get projects of the client", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the addTimeEntry() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param entryId
     *            the id of the time entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the time entry for the project
     *
     * @return true if the time entry was added, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while adding the time entry (such
     *             as SQL exception)
     */
    private boolean addTimeEntryImpl(int entryId, int projectId, String user)
            throws PersistenceException {
        try {
            // if the time entry already exists, do not add it
            if (exist(SQL_SEL_TIME_ENTRY, entryId, projectId)) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the time entry to project_time table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_TIME_ENTRY);

            pstmt.setInt(1, projectId);
            pstmt.setInt(2, entryId);
            pstmt.setTimestamp(3, DBUtil.toSQLDate(date));
            pstmt.setString(4, user);
            pstmt.setTimestamp(5, DBUtil.toSQLDate(date));
            pstmt.setString(6, user);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add time entry", e);
        }
    }

    /**
     * <p>
     * The actual implementation of the addExpenseEntry() method.
     * </p>
     *
     * <p>
     * This method does not deal with transaction. The transaction is expected
     * to be started by the caller so that it can be committed or rolled back in
     * one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param entryId
     *            the id of the expense entry
     * @param projectId
     *            the id of the project
     * @param user
     *            the user who created the expense entry for the project
     *
     * @return true if the expense entry was added, false otherwise
     *
     * @throws PersistenceException
     *             if something wrong happens while adding the expense entry
     *             (such as SQL exception)
     */
    private boolean addExpenseEntryImpl(int entryId, int projectId, String user)
            throws PersistenceException {
        try {
            // if the expense entry already exists, do not add it
            if (exist(SQL_SEL_EXPENSE_ENTRY, entryId, projectId)) {
                return false;
            }

            // use the current date for creation_date and modification_date
            Date date = new Date();

            // add the expense entry to project_expense table
            PreparedStatement pstmt = getPreparedStatement(SQL_INS_EXPENSE_ENTRY);

            pstmt.setInt(1, projectId);
            pstmt.setInt(2, entryId);
            pstmt.setTimestamp(3, DBUtil.toSQLDate(date));
            pstmt.setString(4, user);
            pstmt.setTimestamp(5, DBUtil.toSQLDate(date));
            pstmt.setString(6, user);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add expense entry", e);
        }
    }

    /**
     * <p>
     * Initializes the database connection, project search utility and client
     * search utility. If any error happens, the connection is closed if it was
     * opened.
     * </p>
     *
     * @param dbNamespace
     *            the namespace of the DB Connection Factory configuration file
     * @param connectionProducerName
     *            the connection producer name used to obtain the connection
     * @param projectSearchUtilityNamespace
     *            the configuration namespace used to load configuration for the
     *            DatabaseSearchUtility used to search projects
     * @param clientSearchUtilityNamespace
     *            the configuration namespace used to load configuration for the
     *            DatabaseSearchUtility used to search clients
     *
     * @throws PersistenceException
     *             if the connection to the database cannot be established, or
     *             any error happens when loading the configuration
     */
    private void init(String dbNamespace, String connectionProducerName,
            String projectSearchUtilityNamespace,
            String clientSearchUtilityNamespace) throws PersistenceException {
        try {
            // obtain the connection using the DB Connection Factory
            connection = new DBConnectionFactoryImpl(dbNamespace)
                    .createConnection(connectionProducerName);

            // make sure dirty reads are prevented
//            connection
//                    .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new PersistenceException(
                    "Some error occurs in DB configuration file", e);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Fails to obtain the connection", e);
//        } catch (SQLException e) {
//            DBUtil.close(connection);
//            throw new PersistenceException("Fails to set transaction level", e);
        }

        try {
            // obtain the project search utility and client search utility
            projectSearchUtility = new DatabaseSearchUtility(connection,
                    projectSearchUtilityNamespace);
            clientSearchUtility = new DatabaseSearchUtility(connection,
                    clientSearchUtilityNamespace);
        } catch (ConfigurationException e) {
            DBUtil.close(connection);
            throw new PersistenceException(
                    "Some error occurs in configuration file", e);
        }

        // prepare the SQL queries to be cached
        pstmts = new HashMap();
    }

    /**
     * <p>
     * Gets the prepared statement corresponding to the sql query from the
     * cache. If not exists, the prepared statement is lazily initialized and
     * added to the cache.
     * </p>
     *
     * <p>
     * This method throws SQLException so that the caller can wrap this
     * exception with specific message.
     * </p>
     *
     * @param sql
     *            the sql query to execute.
     *
     * @return the prepared statement from the cache.
     *
     * @throws SQLException
     *             if any database error occurs.
     */
    private PreparedStatement getPreparedStatement(String sql)
            throws SQLException {
        PreparedStatement pstmt = (PreparedStatement) pstmts.get(sql);

        if (pstmt == null) {
            // cache the prepared statements to the map
            // this helps to make the batch operation faster
            pstmt = connection.prepareStatement(sql);
            pstmts.put(sql, pstmt);
        }
        return pstmt;
    }

    /**
     * <p>
     * Return the CompanyId coresponding to the client given as parameter. Will
     * return the id of the company if it is found a client with the given id or
     * -1 if no client is found for the given clientId If the clientId is -1 the
     * method throws IllegalArgumentException. If any errors happend the method
     * throws PersistenceException.
     * </p>
     *
     * @return the id of the company if it is found a client with the given id
     *         or -1 if no client is found for the given clientId
     * @param clientId
     *            the id of the client
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the clientId received as parameter is -1.
     *
     * @since 2.0
     */
    public int getCompanyIdForClient(int clientId) throws PersistenceException {
        return getCompanyIdForSpecificSQL(clientId,
                SQL_SEL_COMPANY_ID_FOR_CLIENT);
    }

    /**
     * <p>
     * Return the CompanyId coresponding to the client given as parameter. Will
     * return the id of the company if it is found a project with the given id
     * or -1 if no project is found for the given projectId If the projectId is
     * -1 the method throws IllegalArgumentException. If any errors happend the
     * method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a project with the given id
     *         or -1 if no project is found for the given projectId
     * @param projectId
     *            the id of the project
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the projectId received as parameter is -1.
     *
     * @since 2.0
     */
    public int getCompanyIdForProject(int projectId)
            throws PersistenceException {
        return getCompanyIdForSpecificSQL(projectId,
                SQL_SEL_COMPANY_ID_FOR_PROJECT);
    }

    /**
     * <p>
     * Return the CompanyId coresponding to the timeEntry given as parameter.
     * Will return the id of the company if it is found a timeEntry with the
     * given id or -1 if no timeEntry is found for the given timeEntryId If the
     * timeEntryId is -1 the method throws IllegalArgumentException. If any
     * errors happend the method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a timeEntry with the given
     *         id or -1 if no timeEntry is found for the given timeEntryId
     * @param timeEntryId
     *            the id of the timeEntry
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the timeEntryId received as parameter is -1.
     *
     * @since 2.0
     */
    public int getCompanyIdForTimeEntry(int timeEntryId)
            throws PersistenceException {
        return getCompanyIdForSpecificSQL(timeEntryId,
                SQL_SEL_COMPANY_ID_FOR_TIME_ENTRY);
    }

    /**
     * <p>
     * Return the CompanyId coresponding to the expenseEntry given as parameter.
     * Will return the id of the company if it is found a expenseEntry with the
     * given id or -1 if no expenseEntry is found for the given expenseEntryId
     * If the expenseEntryId is -1 the method throws IllegalArgumentException.
     * If any errors happend the method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a expenseEntry with the
     *         given id or -1 if no expenseEntry is found for the given
     *         expenseEntryId
     * @param expenseEntryId
     *            the id of the expenseEntry
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the expenseEntryId received as parameter is -1.
     *
     * @since 2.0
     */
    public int getCompanyIdForExpenseEntry(int expenseEntryId)
            throws PersistenceException {
        return getCompanyIdForSpecificSQL(expenseEntryId,
                SQL_SEL_COMPANY_ID_FOR_EXPENSE_ENTRY);
    }

    /**
     * <p>
     * Return the CompanyId coresponding to the userAccount given as parameter.
     * Will return the id of the company if it is found a userAccount with the
     * given id or -1 if no userAccount is found for the given userAccountId If
     * the userAccountId is -1 the method throws IllegalArgumentException. If
     * any errors happend the method throws PersistenceException.
     * </p>
     *
     *
     * @return the id of the company if it is found a userAccount with the given
     *         id or -1 if no userAccount is found for the given userAccountId
     * @param userAccountId
     *            the id of the userAccount
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the userAccountId received as parameter is -1.
     *
     * @since 2.0
     */
    public int getCompanyIdForUserAccount(int userAccountId)
            throws PersistenceException {
        return getCompanyIdForSpecificSQL(userAccountId,
                SQL_SEL_COMPANY_ID_FOR_USER_ACCOUNT);
    }

    /**
     * Execute a sql statement given as parameter, sets a parameter (for the id
     * of the entity), and return the first column value for the executed
     * result, or -1 if no row was found.
     *
     * @param entityId
     *            the id of the entity
     * @param sqlToBeExecuted
     *            the sql string that identify the entoty for witch the search
     *            it is made. Must have a parameter (for the id of the entity)
     *            and must return the companyId in the select
     * @return the id of the company if it is found a entity with the given id
     *         or -1 if no entity is found for the given id
     * @throws PersistenceException -
     *             if any error happens
     * @throws IllegalArgumentException -
     *             if the entityId received as parameter is -1.
     *
     * @since 2.0
     */
    private int getCompanyIdForSpecificSQL(int entityId, String sqlToBeExecuted)
            throws PersistenceException {
        if (entityId == -1) {
            throw new IllegalArgumentException(
                    "The id of the entity that is given to obtain the company associated with entity must not be -1");
        }
        try {
            // return the id for the specific sql
            return getInteger(sqlToBeExecuted, entityId, 1);
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Fails to get company_id for specific entity", e);
        }
    }

    /**
     * <p>
     * Search a client with specific values (name, companyId). Return true if
     * found at least one or false if no client mach the criteria. The isNew
     * parameter specify if, on searching, will pass or not the clientId from
     * the given client.
     * </p>
     *
     *
     * @return true if exist a client with the name given as parameter, assigned
     *         to a company given as parameter
     * @param client
     *            the client that it is checked
     * @param isNew
     *            specify if the client is new (specify if, on searching, will
     *            pass or not the clientId)
     * @throws PersistenceException -
     *             if any error happens
     */
    public boolean existClientWithNameForCompany(
            com.topcoder.timetracker.project.Client client, boolean isNew)
            throws PersistenceException {

        if (client == null || client.getName() == null || client.getCompanyId() == -1){
            throw new IllegalArgumentException("Client parameter must be not null.");
        }

        ResultSet rs = null;

        try {
            // get the project worker from project_worker table
            String statement = SQL_SEL_CLIENT_FOR_NAME_AND_COMPANY;
            if (!isNew) {
                statement = SQL_SEL_CLIENT_FOR_NAME_AND_COMPANY_EXCLUDING_CURRENT;
            }
            PreparedStatement pstmt = getPreparedStatement(statement);
            // set the name parameter
            pstmt.setString(1, client.getName());
            // set the company_id parameter
            pstmt.setInt(2, client.getCompanyId());
            if (!isNew) {
                // if and only if is not a new client then set the id of the
                // client parameter
                pstmt.setInt(3, client.getId());
            }
            rs = pstmt.executeQuery();

            // if we have at least a row then a client with specified parameter
            // was found
            if (!rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Fails to get client with specified name and company_id", e);
        } finally {
            DBUtil.close(rs);
        }
    }

}
