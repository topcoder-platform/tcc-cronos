/*
 * InformixTimeTrackerProjectPersistence.java
 *
 * Copyright (c) 2005 TopCoder, Inc., All Rights Reserved
 */
package com.topcoder.timetracker.project.persistence;

import com.topcoder.db.connectionfactory.ConfigurationException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


/**
 * <p>
 * This class is a concrete implementation of the TimeTrackerProjectPersistence interface that uses an Informix
 * database as persistence. This implementation uses the DB Connection Factory component to obtain a connection to the
 * database. Transaction should be employed to ensure atomicity.
 * </p>
 *
 * <p>
 * This class provides two constructors. If the connection producer name is not specified, then the
 * DEFAULT_CONNECTION_PRODUCER_NAME will be used to obtain a connection using the DB Connection Factory component.
 * </p>
 *
 * <p>
 * For addition operations, the current date will be written to the CreationDate and ModificationDate fields in the
 * database and populated to the supplied instance, and the CreationUser will be set as the ModificationUser. For
 * update operations, the current date will be written to the ModificationDate field in the database and populated to
 * the supplied instance, and the CreationDate and CreationUser fields will not be updated in the database. If an
 * exception is thrown during those operations, the populated fields should be treated as invalid and should not be
 * read.
 * </p>
 *
 * @author DanLazar, TCSDEVELOPER
 * @version 1.0
 */
public class InformixTimeTrackerProjectPersistence implements TimeTrackerProjectPersistence {
    /**
     * <p>
     * The default connection producer name used to obtain a Connection from DBConnectionFactoryImpl. It will be used
     * if the user creates an instance of this class using the one-argument constructor.
     * </p>
     */
    public static final String DEFAULT_CONNECTION_PRODUCER_NAME = "Time_Tracker_Project_Informix_Connection_Producer";

    /**
     * <p>
     * The SQL statement to select the time entry by time entry id and project id.
     * </p>
     */
    private static final String SQL_SEL_TIME_ENTRY = "SELECT * FROM ProjectTimes WHERE TimeEntriesID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select the time entries by project id.
     * </p>
     */
    private static final String SQL_SEL_TIME_ENTRIES = "SELECT * FROM ProjectTimes WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to insert a time entry.
     * </p>
     */
    private static final String SQL_INS_TIME_ENTRY = "INSERT INTO ProjectTimes VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the time entry by time entry id and project id.
     * </p>
     */
    private static final String SQL_DEL_TIME_ENTRY = "DELETE FROM ProjectTimes WHERE TimeEntriesID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete the time entries by project id.
     * </p>
     */
    private static final String SQL_DEL_TIME_ENTRIES = "DELETE FROM ProjectTimes WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete all time entries.
     * </p>
     */
    private static final String SQL_DEL_ALL_TIME_ENTRIES = "DELETE FROM ProjectTimes";

    /**
     * <p>
     * The SQL statement to select the expense entry by expense entry id and project id.
     * </p>
     */
    private static final String SQL_SEL_EXPENSE_ENTRY = "SELECT * FROM ProjectExpenses WHERE ExpenseEntriesID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select the expense entries by project id.
     * </p>
     */
    private static final String SQL_SEL_EXPENSE_ENTRIES = "SELECT * FROM ProjectExpenses WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to insert an expense entry.
     * </p>
     */
    private static final String SQL_INS_EXPENSE_ENTRY = "INSERT INTO ProjectExpenses VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the expense entry by expense entry id and project id.
     * </p>
     */
    private static final String SQL_DEL_EXPENSE_ENTRY = "DELETE FROM ProjectExpenses WHERE ExpenseEntriesID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete the expense entries by project id.
     * </p>
     */
    private static final String SQL_DEL_EXPENSE_ENTRIES = "DELETE FROM ProjectExpenses WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete all expense entries.
     * </p>
     */
    private static final String SQL_DEL_ALL_EXPENSE_ENTRIES = "DELETE FROM ProjectExpenses";

    /**
     * <p>
     * The SQL statement to select the project manager by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_MANAGER = "SELECT * FROM ProjectManagers WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to insert a project manager.
     * </p>
     */
    private static final String SQL_INS_PROJECT_MANAGER = "INSERT INTO ProjectManagers VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the project manager by manager id and project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_MANAGER = "DELETE FROM ProjectManagers WHERE UsersID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete the project managers by project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_MANAGERS = "DELETE FROM ProjectManagers WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete all project managers.
     * </p>
     */
    private static final String SQL_DEL_ALL_PROJECT_MANAGERS = "DELETE FROM ProjectManagers";

    /**
     * <p>
     * The SQL statement to select the project worker by worker id and project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_WORKER = "SELECT * FROM ProjectWorkers WHERE UsersID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select the project workers by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT_WORKERS = "SELECT * FROM ProjectWorkers WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to insert a project worker.
     * </p>
     */
    private static final String SQL_INS_PROJECT_WORKER =
        "INSERT INTO ProjectWorkers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the project worker by worker id and project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_WORKER = "DELETE FROM ProjectWorkers WHERE UsersID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete the project workers by project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT_WORKERS = "DELETE FROM ProjectWorkers WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete all project workers.
     * </p>
     */
    private static final String SQL_DEL_ALL_PROJECT_WORKERS = "DELETE FROM ProjectWorkers";

    /**
     * <p>
     * The SQL statement to update the project worker by project id and worker id.
     * </p>
     */
    private static final String SQL_UPD_PROJECT_WORKER = "UPDATE ProjectWorkers SET StartDate = ?, EndDate = ?, "
        + "PayRate = ?, ModificationDate = ?, ModificationUser = ? WHERE ProjectsID = ? AND UsersID = ?";

    /**
     * <p>
     * The SQL statement to select the project by project id.
     * </p>
     */
    private static final String SQL_SEL_PROJECT = "SELECT * FROM Projects WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select all projects.
     * </p>
     */
    private static final String SQL_SEL_ALL_PROJECTS = "SELECT * FROM Projects";

    /**
     * <p>
     * The SQL statement to insert a project.
     * </p>
     */
    private static final String SQL_INS_PROJECT = "INSERT INTO Projects VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the project by project id.
     * </p>
     */
    private static final String SQL_DEL_PROJECT = "DELETE FROM Projects WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete all projects.
     * </p>
     */
    private static final String SQL_DEL_ALL_PROJECTS = "DELETE FROM Projects";

    /**
     * <p>
     * The SQL statement to update the project by project id.
     * </p>
     */
    private static final String SQL_UPD_PROJECT = "UPDATE Projects SET Name = ?, Description = ?, StartDate = ?, "
        + "EndDate = ?, ModificationDate = ?, ModificationUser = ? WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select the client project by project id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT_PROJECT = "SELECT * FROM ClientProjects WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select the client project by client id and project id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT_PROJECT_BY_BOTH = "SELECT * FROM ClientProjects WHERE ClientsID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to select the client projects by client id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT_PROJECTS = "SELECT * FROM ClientProjects WHERE ClientsID = ?";

    /**
     * <p>
     * The SQL statement to insert a client project.
     * </p>
     */
    private static final String SQL_INS_CLIENT_PROJECT = "INSERT INTO ClientProjects VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the client project by project id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT_PROJECT = "DELETE FROM ClientProjects WHERE ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete the client project by client id and project id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT_PROJECT_BY_BOTH = "DELETE FROM ClientProjects WHERE ClientsID = ? AND "
        + "ProjectsID = ?";

    /**
     * <p>
     * The SQL statement to delete the client project by client id and project id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT_PROJECTS = "DELETE FROM ClientProjects WHERE ClientsID = ?";

    /**
     * <p>
     * The SQL statement to delete all client projects.
     * </p>
     */
    private static final String SQL_DEL_ALL_CLIENT_PROJECTS = "DELETE FROM ClientProjects";

    /**
     * <p>
     * The SQL statement to select the client by client id.
     * </p>
     */
    private static final String SQL_SEL_CLIENT = "SELECT * FROM Clients WHERE ClientsID = ?";

    /**
     * <p>
     * The SQL statement to select all clients.
     * </p>
     */
    private static final String SQL_SEL_ALL_CLIENTS = "SELECT * FROM Clients";

    /**
     * <p>
     * The SQL statement to insert a client.
     * </p>
     */
    private static final String SQL_INS_CLIENT = "INSERT INTO Clients VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The SQL statement to delete the client by client id.
     * </p>
     */
    private static final String SQL_DEL_CLIENT = "DELETE FROM Clients WHERE ClientsID = ?";

    /**
     * <p>
     * The SQL statement to delete all clients.
     * </p>
     */
    private static final String SQL_DEL_ALL_CLIENTS = "DELETE FROM Clients";

    /**
     * <p>
     * The SQL statement to update the client by client id.
     * </p>
     */
    private static final String SQL_UPD_CLIENT = "UPDATE Clients SET Name = ?, ModificationDate = ?, "
        + "ModificationUser = ? WHERE ClientsID = ?";

    /**
     * <p>
     * Represents the connection to the Informix database. It will be initialized in the constructors by providing the
     * appropriate connection producer name to the DBConnectionFactoryImpl.
     * </p>
     */
    private Connection connection = null;

    /**
     * <p>
     * Creates a new instance of this class. The default connection producer name will be used. The connection will be
     * obtained using the DB Connection Factory component.
     * </p>
     *
     * @param dbNamespace the namespace of the DB Connection Factory configuration file
     *
     * @throws NullPointerException if the dbNamespace is null
     * @throws IllegalArgumentException if the dbNamespace is the empty String
     * @throws PersistenceException if the connection to the database cannot be established
     */
    public InformixTimeTrackerProjectPersistence(String dbNamespace)
        throws PersistenceException
    {
        this(dbNamespace, DEFAULT_CONNECTION_PRODUCER_NAME);
    }

    /**
     * <p>
     * Creates a new instance of this class. The given connection producer name will be used. The connection will be
     * obtained using the DB Connection Factory component.
     * </p>
     *
     * @param dbNamespace the namespace of the DB Connection Factory configuration file
     * @param connectionProducerName the connection producer name used to obtain the connection
     *
     * @throws NullPointerException if the dbNamespace or connectionProducerName is null
     * @throws IllegalArgumentException if the dbNamespace or connectionProducerName is the empty String
     * @throws PersistenceException if the connection to the database cannot be established
     */
    public InformixTimeTrackerProjectPersistence(String dbNamespace, String connectionProducerName)
        throws PersistenceException
    {
        checkString(dbNamespace);
        checkString(connectionProducerName);

        try {
            // obtain the connection using the DB Connection Factory
            DBConnectionFactory factory = new DBConnectionFactoryImpl(dbNamespace);

            connection = factory.createConnection(connectionProducerName);
        } catch (ConfigurationException e) {
            throw new PersistenceException("Some error occurs in DB configuration file", e);
        } catch (DBConnectionException e) {
            throw new PersistenceException("Fails to obtain the connection", e);
        }
    }

    /**
     * <p>
     * Adds a new client to the database. If the id of the client is used false will be returned.
     * </p>
     *
     * <p>
     * This method will add a row into the Clients table. All the projects contained by the client will be added to the
     * ClientProjects table. The project information will also be added to the Projects table.
     * </p>
     *
     * <p>
     * Note that transaction is used here.
     * </p>
     *
     * @param client the client to add
     *
     * @return true if the client was added, false otherwise
     *
     * @throws NullPointerException if the client is null
     * @throws PersistenceException if something wrong happens while adding the client (such as SQL exception)
     */
    public boolean addClient(Client client) throws PersistenceException {
        if (client == null) {
            throw new NullPointerException("client is null");
        }

        PreparedStatement pstmt = null;

        // start the transaction
        enableTransaction();

        try {
            // if the client already exists, do not add it
            // we do not use getClient() here to save access to Clients-related tables
            if (exist(SQL_SEL_CLIENT, client.getId())) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the client to Clients table
            pstmt = connection.prepareStatement(SQL_INS_CLIENT);
            pstmt.setInt(1, client.getId());
            pstmt.setString(2, client.getName());
            pstmt.setTimestamp(3, toSQLDate(date));
            pstmt.setString(4, client.getCreationUser());
            pstmt.setTimestamp(5, toSQLDate(date));
            pstmt.setString(6, client.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            client.setCreationDate(date);
            client.setModificationDate(date);
            client.setModificationUser(client.getCreationUser());

            // add the projects of the client
            for (Iterator i = client.getProjects().iterator(); i.hasNext();) {
                addProjectToClientImpl(client.getId(), (Project) i.next(), client.getCreationUser());
            }
            return true;
        } catch (PersistenceException e) {
            rollback();
            throw e;
        } catch (SQLException e) {
            rollback();
            throw new PersistenceException("Fails to add the client", e);
        } finally {
            disableTransaction();
            close(pstmt);
        }
    }

    /**
     * <p>
     * Deletes a client from the database. If a client with the specified id does not exist false will be returned.
     * </p>
     * 
     * <p>
     * This method removes a row from the Clients table. It will also remove multiple rows from the ClientProjects
     * table.
     * </p>
     * 
     * <p>
     * Note that transaction is used here.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return true if the client was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the client (such as SQL exception)
     */
    public boolean removeClient(int clientId) throws PersistenceException {
        // start the transaction
        enableTransaction();

        try {
            // remove the client from ClientProjects table
            remove(SQL_DEL_CLIENT_PROJECTS, clientId);

            // remove the client from Clients table
            boolean result = remove(SQL_DEL_CLIENT, clientId);

            // commit the transaction
            commit();
            return result;
        } catch (PersistenceException e) {
            rollback();
            throw e;
        } catch (SQLException e) {
            rollback();
            throw new PersistenceException("Fails to remove the client", e);
        } finally {
            disableTransaction();
        }
    }

    /**
     * <p>
     * Deletes all clients from the database.
     * </p>
     * 
     * <p>
     * This method will remove all rows from the Clients and ClientProjects tables.
     * </p>
     * 
     * <p>
     * Note that transaction is used here.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while removing the clients (such as SQL exception)
     */
    public void removeAllClients() throws PersistenceException {
        // start the transaction
        enableTransaction();

        try {
            // remove all clients from ClientProjects table
            remove(SQL_DEL_ALL_CLIENT_PROJECTS);

            // remove all clients from Clients table
            remove(SQL_DEL_ALL_CLIENTS);

            // commit the transaction
            commit();
        } catch (PersistenceException e) {
            rollback();
            throw e;
        } catch (SQLException e) {
            rollback();
            throw new PersistenceException("Fails to remove all clients", e);
        } finally {
            disableTransaction();
        }
    }

    /**
     * <p>
     * Updates a client in the database. If the client does not exist in the database false will be returned.
     * </p>
     * 
     * <p>
     * This method will only update the Clients table. It will not update the projects of the client. Projects will be
     * added or deleted using other methods.
     * </p>
     *
     * @param client the client to update
     *
     * @return true if the client was updated, false otherwise
     *
     * @throws NullPointerException if the client is null
     * @throws PersistenceException if something wrong happens while updating the client (such as SQL exception)
     */
    public boolean updateClient(Client client) throws PersistenceException {
        if (client == null) {
            throw new NullPointerException("client is null");
        }

        PreparedStatement pstmt = null;

        try {
            // use current date for modificationDate
            Date date = new Date();

            // update the client in Clients table
            pstmt = connection.prepareStatement(SQL_UPD_CLIENT);
            pstmt.setString(1, client.getName());
            pstmt.setTimestamp(2, toSQLDate(date));
            pstmt.setString(3, client.getModificationUser());
            pstmt.setInt(4, client.getId());

            boolean result = pstmt.executeUpdate() > 0;

            // populate the date
            client.setModificationDate(date);

            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to update client", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Adds a project to a client. If the project already has a client assigned false will be returned.
     * </p>
     * 
     * <p>
     * This method will add a row into the ClientProjects table. The project information will also be added to the
     * Projects table.
     * </p>
     * 
     * <p>
     * Note that transaction is used here.
     * </p>
     *
     * @param clientId the id of the client
     * @param project the project to add to the client
     * @param user the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws NullPointerException if the project or user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while adding the project to the client (such as SQL
     *         exception)
     */
    public boolean addProjectToClient(int clientId, Project project, String user)
        throws PersistenceException
    {
        if (project == null) {
            throw new NullPointerException("project is null");
        }
        checkString(user);

        // start the transaction
        enableTransaction();

        try {
            // add the project to ClientProjects table
            boolean result = addProjectToClientImpl(clientId, project, user);

            // commit the transaction
            commit();
            return result;
        } catch (PersistenceException e) {
            rollback();
            throw e;
        } finally {
            disableTransaction();
        }
    }

    /**
     * <p>
     * Retrieves the project with the specified clientId and projectId from the database. If there is no such project
     * associated with the client, null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param clientId the id of the client
     * @param projectId the id of the project
     *
     * @return the project of the client with the specified clientId and projectId
     *
     * @throws PersistenceException if something wrong happens while getting the project of the client (such as SQL
     *         exception)
     */
    public Project getClientProject(int clientId, int projectId)
        throws PersistenceException
    {
        try {
            if (!exist(SQL_SEL_CLIENT_PROJECT_BY_BOTH, clientId, projectId)) {
                return null;
            } else {
                return getProject(projectId);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get client project", e);
        }
    }

    /**
     * <p>
     * Retrieves all the projects of the client from the database. If there are no projects associated with the client,
     * an empty list will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return a list containing all the projects of the client
     *
     * @throws PersistenceException if something wrong happens while getting the projects of the client (such as SQL
     *         exception)
     */
    public List getAllClientProjects(int clientId) throws PersistenceException {
        try {
            // get the projects of the client from ClientProjects table
            List projectsIds = getIntegers(SQL_SEL_CLIENT_PROJECTS, clientId, 2);
            List projects = new ArrayList();

            for (Iterator i = projectsIds.iterator(); i.hasNext();) {
                int projectId = ((Integer) i.next()).intValue();

                // populate the fields of each project
                projects.add(getProject(projectId));
            }
            return projects;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get projects of the client", e);
        }
    }

    /**
     * <p>
     * Retrieves the client with the specified clientId from the database. If the client does not exist, null will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param clientId the id of the client
     *
     * @return the client with the given id
     *
     * @throws PersistenceException if something wrong happens while getting the client (such as SQL exception)
     */
    public Client getClient(int clientId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get the client from Clients table
            pstmt = connection.prepareStatement(SQL_SEL_CLIENT);
            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getClient(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get client", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves all the clients from the database. If there are no clients in the database, an empty list will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the Client objects will be properly populated.
     * </p>
     *
     * @return a list containing all the clients
     *
     * @throws PersistenceException if something wrong happens while getting the clients (such as SQL exception)
     */
    public List getAllClients() throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get all the clients from Clients table
            pstmt = connection.prepareStatement(SQL_SEL_ALL_CLIENTS);
            rs = pstmt.executeQuery();

            List clients = new ArrayList();

            while (rs.next()) {
                // populate the fields of each client
                clients.add(getClient(rs));
            }
            return clients;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get all clients", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Adds a new project to the database. If the id of the project is used false will be returned.
     * </p>
     * 
     * <p>
     * This method will add a row into the Projects table. It will not add the manager, workers, time entries and
     * expense entries to other tables. They should be added using other methods.
     * </p>
     *
     * @param project the project to add
     *
     * @return true if the project was added, false otherwise
     *
     * @throws NullPointerException if the project is null
     * @throws PersistenceException if something wrong happens while adding the project (such as SQL exception)
     */
    public boolean addProject(Project project) throws PersistenceException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }

        PreparedStatement pstmt = null;

        try {
            // if the project already exists, do not add it
            // we do not use getProject() here to save access to Projects-related tables
            if (exist(SQL_SEL_PROJECT, project.getId())) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the project to Projects table
            pstmt = connection.prepareStatement(SQL_INS_PROJECT);
            pstmt.setInt(1, project.getId());
            pstmt.setString(2, project.getName());
            pstmt.setString(3, project.getDescription());
            pstmt.setTimestamp(4, toSQLDate(project.getStartDate()));
            pstmt.setTimestamp(5, toSQLDate(project.getEndDate()));
            pstmt.setTimestamp(6, toSQLDate(date));
            pstmt.setString(7, project.getCreationUser());
            pstmt.setTimestamp(8, toSQLDate(date));
            pstmt.setString(9, project.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            project.setCreationDate(date);
            project.setModificationDate(date);
            project.setModificationUser(project.getCreationUser());

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Some SQL error occurs", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Updates an project in the database. If the project does not exist in the database false will be returned.
     * </p>
     * 
     * <p>
     * This method will only update the Projects table. It will not update the workers, project manager, time entries,
     * expense entries of the project. They will be added or deleted using other methods.
     * </p>
     *
     * @param project the project to update
     *
     * @return true if the project was updated, false otherwise
     *
     * @throws NullPointerException if the project is null
     * @throws PersistenceException if something wrong happens while updating the project (such as SQL exception)
     */
    public boolean updateProject(Project project) throws PersistenceException {
        if (project == null) {
            throw new NullPointerException("project is null");
        }

        PreparedStatement pstmt = null;

        try {
            // use current date for modificationDate
            Date date = new Date();

            // update the project in Projects table
            pstmt = connection.prepareStatement(SQL_UPD_PROJECT);
            pstmt.setString(1, project.getName());
            pstmt.setString(2, project.getDescription());
            pstmt.setTimestamp(3, toSQLDate(project.getStartDate()));
            pstmt.setTimestamp(4, toSQLDate(project.getEndDate()));
            pstmt.setTimestamp(5, toSQLDate(date));
            pstmt.setString(6, project.getModificationUser());
            pstmt.setInt(7, project.getId());

            boolean result = pstmt.executeUpdate() > 0;

            // populate the date
            project.setModificationDate(date);

            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to update project", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Deletes a project from the database. If a project with the specified id does not exist false will be returned.
     * </p>
     * 
     * <p>
     * This method removes a row from the Projects table. The workers of this project will be deleted from
     * ProjectWorkers table. Expense entries will be deleted from the ProjectExpenses table. Time Entries of this
     * project will be deleted from ProjectTimes table. The project manager of this project will be deleted from the
     * ProjectManagers table. The client of this project will be deleted from the ClientProjects table.
     * </p>
     * 
     * <p>
     * Note that transaction is used here.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return true if the project was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project (such as SQL exception)
     */
    public boolean removeProject(int projectId) throws PersistenceException {
        // start the transaction
        enableTransaction();

        try {
            // remove the expense entries of the project
            remove(SQL_DEL_EXPENSE_ENTRIES, projectId);

            // remove the time entries of the project
            remove(SQL_DEL_TIME_ENTRIES, projectId);

            // remove the manager of the project
            remove(SQL_DEL_PROJECT_MANAGERS, projectId);

            // remove the workers of the project
            removeWorkers(projectId);

            // remove the client of the project
            remove(SQL_DEL_CLIENT_PROJECT, projectId);

            // remove the project from Projects table
            boolean result = remove(SQL_DEL_PROJECT, projectId);

            // commit the transaction
            commit();
            return result;
        } catch (PersistenceException e) {
            rollback();
            throw e;
        } catch (SQLException e) {
            rollback();
            throw new PersistenceException("Fails to remove the project", e);
        } finally {
            disableTransaction();
        }
    }

    /**
     * <p>
     * Deletes all projects from the database.
     * </p>
     * 
     * <p>
     * This method will remove all rows from Projects, ProjectWorkers, ProjectManagers, ProjectExpenses, ClientProjects
     * and ProjectTimes tables.
     * </p>
     * 
     * <p>
     * Note that transaction is used here.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while removing the projects (such as SQL exception)
     */
    public void removeAllProjects() throws PersistenceException {
        // start the transaction
        enableTransaction();

        try {
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

            // remove all projects from Projects table
            remove(SQL_DEL_ALL_PROJECTS);

            // commit the transaction
            commit();
        } catch (PersistenceException e) {
            rollback();
            throw e;
        } catch (SQLException e) {
            rollback();
            throw new PersistenceException("Fails to remove all projects", e);
        } finally {
            disableTransaction();
        }
    }

    /**
     * <p>
     * Assigns a client to an existing project. If the project already has a client assigned false will be returned.
     * </p>
     * 
     * <p>
     * This method will add a row into the ClientProjects table.
     * </p>
     *
     * @param projectId the id of the project
     * @param clientId the id of the client
     * @param user the user who assigned the client for the project
     *
     * @return true if the client was assigned to the project, false otherwise
     *
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while assigning the client (such as SQL exception)
     */
    public boolean assignClient(int projectId, int clientId, String user)
        throws PersistenceException
    {
        checkString(user);

        PreparedStatement pstmt = null;

        try {
            // if a client was already assigned to project, do not assign again
            // we do not use getProjectClient() here to save access to Clients-related tables
            if (exist(SQL_SEL_CLIENT_PROJECT, projectId)) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the client to ClientProjects table
            pstmt = connection.prepareStatement(SQL_INS_CLIENT_PROJECT);
            pstmt.setInt(1, clientId);
            pstmt.setInt(2, projectId);
            pstmt.setTimestamp(3, toSQLDate(date));
            pstmt.setString(4, user);
            pstmt.setTimestamp(5, toSQLDate(date));
            pstmt.setString(6, user);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to assign client to project", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves the client of a project. If the project has no client assigned, null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Client object will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return the client of the project
     *
     * @throws PersistenceException if something wrong happens while getting the client of the project (such as SQL
     *         exception)
     */
    public Client getProjectClient(int projectId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get the client from ClientProjects table
            pstmt = connection.prepareStatement(SQL_SEL_CLIENT_PROJECT);
            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getClient(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project client", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Assigns a project manager to an existing project. If the project already has a manager assigned false will be
     * returned.
     * </p>
     * 
     * <p>
     * This method will add a row into the ProjectManagers table.
     * </p>
     *
     * @param projectManager the project manager to assign
     *
     * @return true if the manager was assigned, false otherwise
     *
     * @throws NullPointerException if the projectManager is null
     * @throws PersistenceException if something wrong happens while assigning the project manager (such as SQL
     *         exception)
     */
    public boolean assignProjectManager(ProjectManager projectManager)
        throws PersistenceException
    {
        if (projectManager == null) {
            throw new NullPointerException("projectManager is null");
        }

        PreparedStatement pstmt = null;

        try {
            // if a manager was already assigned to project, do not assign again
            // we do not use getProjectManager() here to save access to Projects-related tables
            if (exist(SQL_SEL_PROJECT_MANAGER, projectManager.getProject().getId())) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the project manager to ProjectManagers table
            pstmt = connection.prepareStatement(SQL_INS_PROJECT_MANAGER);
            pstmt.setInt(1, projectManager.getProject().getId());
            pstmt.setInt(2, projectManager.getManagerId());
            pstmt.setTimestamp(3, toSQLDate(date));
            pstmt.setString(4, projectManager.getCreationUser());
            pstmt.setTimestamp(5, toSQLDate(date));
            pstmt.setString(6, projectManager.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            projectManager.setCreationDate(date);
            projectManager.setModificationDate(date);
            projectManager.setModificationUser(projectManager.getCreationUser());

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to assign manager to project", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves the project manager of a project. If there is no project manager associated with the project, null
     * will be returned.
     * </p>
     * 
     * <p>
     * The fields of the ProjectManager object will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return the project manager of the project
     *
     * @throws PersistenceException if something wrong happens while getting the project manager (such as SQL
     *         exception)
     */
    public ProjectManager getProjectManager(int projectId)
        throws PersistenceException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get the project manager from ProjectManagers table
            pstmt = connection.prepareStatement(SQL_SEL_PROJECT_MANAGER);
            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            ProjectManager manager = new ProjectManager();

            // populate the fields of project manager
            manager.setProject(getProject(rs.getInt(1)));
            manager.setManagerId(rs.getInt(2));
            manager.setCreationDate(toUtilDate(rs.getTimestamp(3)));
            manager.setCreationUser(rs.getString(4));
            manager.setModificationDate(toUtilDate(rs.getTimestamp(5)));
            manager.setModificationUser(rs.getString(6));

            return manager;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project manager", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Deletes a project manager from the database. This means that this manager will no longer be the project manager
     * of this project. If the project does not have the manager assigned, false will be returned.
     * </p>
     * 
     * <p>
     * This method removes a row from the ProjectManagers table.
     * </p>
     *
     * @param managerId the id of the manager
     * @param projectId the id of the project
     *
     * @return true if the manager was removed from the project, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project manager (such as SQL
     *         exception)
     */
    public boolean removeProjectManager(int managerId, int projectId)
        throws PersistenceException
    {
        try {
            // remove the project manager from ProjectManagers table
            return remove(SQL_DEL_PROJECT_MANAGER, managerId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project manager", e);
        }
    }

    /**
     * <p>
     * Assigns a project worker to an existing project. If the project already has this worker assigned false will be
     * returned.
     * </p>
     * 
     * <p>
     * This method will add a row into the ProjectWorkers table.
     * </p>
     *
     * @param projectWorker the project worker to assign
     *
     * @return true if the worker was assigned to the project, false otherwise
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws PersistenceException if something wrong happens while assigning the project worker (such as SQL
     *         exception)
     */
    public boolean addWorker(ProjectWorker projectWorker)
        throws PersistenceException
    {
        if (projectWorker == null) {
            throw new NullPointerException("projectWorker is null");
        }

        PreparedStatement pstmt = null;

        try {
            // if the worker was already assigned to project, do not add it
            // we do not use getWorker() here to save access to Projects-related tables
            if (exist(SQL_SEL_PROJECT_WORKER, projectWorker.getWorkerId(), projectWorker.getProject().getId())) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the project worker to ProjectWorkers table
            pstmt = connection.prepareStatement(SQL_INS_PROJECT_WORKER);
            pstmt.setInt(1, projectWorker.getProject().getId());
            pstmt.setInt(2, projectWorker.getWorkerId());
            pstmt.setTimestamp(3, toSQLDate(projectWorker.getStartDate()));
            pstmt.setTimestamp(4, toSQLDate(projectWorker.getEndDate()));
            pstmt.setBigDecimal(5, projectWorker.getPayRate());
            pstmt.setTimestamp(6, toSQLDate(date));
            pstmt.setString(7, projectWorker.getCreationUser());
            pstmt.setTimestamp(8, toSQLDate(date));
            pstmt.setString(9, projectWorker.getCreationUser());
            pstmt.executeUpdate();

            // populate the fields
            projectWorker.setCreationDate(date);
            projectWorker.setModificationDate(date);
            projectWorker.setModificationUser(projectWorker.getCreationUser());

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add project worker", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Updates a worker in the database. If the worker was not assigned to the project false will be returned.
     * </p>
     * 
     * <p>
     * This method will only update the ProjectWorkers table.
     * </p>
     *
     * @param projectWorker the worker to update
     *
     * @return true if the worker was updated, false otherwise
     *
     * @throws NullPointerException if the projectWorker is null
     * @throws PersistenceException if something wrong happens while updating the project worker (such as SQL
     *         exception)
     */
    public boolean updateWorker(ProjectWorker projectWorker)
        throws PersistenceException
    {
        if (projectWorker == null) {
            throw new NullPointerException("projectWorker is null");
        }

        PreparedStatement pstmt = null;

        try {
            // use current date for modificationDate
            Date date = new Date();

            // update the project worker in ProjectWorkers table
            pstmt = connection.prepareStatement(SQL_UPD_PROJECT_WORKER);
            pstmt.setTimestamp(1, toSQLDate(projectWorker.getStartDate()));
            pstmt.setTimestamp(2, toSQLDate(projectWorker.getEndDate()));
            pstmt.setBigDecimal(3, projectWorker.getPayRate());
            pstmt.setTimestamp(4, toSQLDate(date));
            pstmt.setString(5, projectWorker.getModificationUser());
            pstmt.setInt(6, projectWorker.getProject().getId());
            pstmt.setInt(7, projectWorker.getWorkerId());

            boolean result = pstmt.executeUpdate() > 0;

            // populate the date
            projectWorker.setModificationDate(date);

            return result;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to update project worker", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Deletes a project worker from the database. This means that this worker will no longer be a worker for this
     * project. If the project does not have the worker assigned, false will be returned.
     * </p>
     * 
     * <p>
     * This method will remove a row from the ProjectWorkers table.
     * </p>
     *
     * @param workerId the id of the worker
     * @param projectId the id of the project
     *
     * @return true if the worker was removed from the project, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project worker (such as SQL
     *         exception)
     */
    public boolean removeWorker(int workerId, int projectId)
        throws PersistenceException
    {
        try {
            // remove the project worker from ProjectWorkers table
            return remove(SQL_DEL_PROJECT_WORKER, workerId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project worker", e);
        }
    }

    /**
     * <p>
     * Deletes all the project workers from the database. This means that the project will have no workers. If the
     * project does not have any worker assigned, false will be returned.
     * </p>
     * 
     * <p>
     * This method will remove multiple rows from the ProjectWorkers table.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return true if the workers were removed from the project, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project workers (such as SQL
     *         exception)
     */
    public boolean removeWorkers(int projectId) throws PersistenceException {
        try {
            // remove the project workers from ProjectWorkers table
            return remove(SQL_DEL_PROJECT_WORKERS, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project workers", e);
        }
    }

    /**
     * <p>
     * Retrieves a worker of a project. If the project does not have this worker assigned null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the ProjectWorker object will be properly populated.
     * </p>
     *
     * @param workerId the id of the worker
     * @param projectId the id of the project
     *
     * @return the ProjectWorker of a project
     *
     * @throws PersistenceException if something wrong happens while getting the project worker (such as SQL exception)
     */
    public ProjectWorker getWorker(int workerId, int projectId)
        throws PersistenceException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get the project worker from ProjectWorkers table
            pstmt = connection.prepareStatement(SQL_SEL_PROJECT_WORKER);
            pstmt.setInt(1, workerId);
            pstmt.setInt(2, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getWorker(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project worker", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves all the workers of a project. If there are no workers associated with a project, an empty list will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the ProjectWorker objects will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return a list containing all the ProjectWorkers of a project
     *
     * @throws PersistenceException if something wrong happens while getting the project workers (such as SQL
     *         exception)
     */
    public List getWorkers(int projectId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get the project workers from ProjectWorkers table
            pstmt = connection.prepareStatement(SQL_SEL_PROJECT_WORKERS);
            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            List workers = new ArrayList();

            while (rs.next()) {
                // populate the fields of each project worker
                workers.add(getWorker(rs));
            }
            return workers;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project workers", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Adds a time entry to an existing project. If the project already has this time entry assigned false will be
     * returned.
     * </p>
     * 
     * <p>
     * This method adds a row to the ProjectTimes table.
     * </p>
     *
     * @param entryId the id of the time entry
     * @param projectId the id of the project
     * @param user the user who created the time entry for the project
     *
     * @return true if the time entry was added, false otherwise
     *
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while adding the time entry (such as SQL exception)
     */
    public boolean addTimeEntry(int entryId, int projectId, String user)
        throws PersistenceException
    {
        checkString(user);

        PreparedStatement pstmt = null;

        try {
            // if the time entry already exists, do not add it
            if (exist(SQL_SEL_TIME_ENTRY, entryId, projectId)) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the time entry to ProjectTimes table
            pstmt = connection.prepareStatement(SQL_INS_TIME_ENTRY);
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, entryId);
            pstmt.setTimestamp(3, toSQLDate(date));
            pstmt.setString(4, user);
            pstmt.setTimestamp(5, toSQLDate(date));
            pstmt.setString(6, user);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add time entry", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Deletes a time entry of a project from the database. If the project does not have this time entry assigned false
     * will be returned.
     * </p>
     * 
     * <p>
     * This method removes a row from the ProjectTimes table.
     * </p>
     *
     * @param entryId the id of the time entry
     * @param projectId the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the time entry (such as SQL exception)
     */
    public boolean removeTimeEntry(int entryId, int projectId)
        throws PersistenceException
    {
        try {
            // remove the time entry from ProjectTimes table
            return remove(SQL_DEL_TIME_ENTRY, entryId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove time entry", e);
        }
    }

    /**
     * <p>
     * Retrieves all the time entries of a project. If there are no time entries associated with a project, an empty
     * list will be returned.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return a list containing the ids of the time entries
     *
     * @throws PersistenceException if something wrong happens while getting the time entries (such as SQL exception)
     */
    public List getTimeEntries(int projectId) throws PersistenceException {
        try {
            // get the time entries from ProjectTimes table
            return getIntegers(SQL_SEL_TIME_ENTRIES, projectId, 2);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get time entries", e);
        }
    }

    /**
     * <p>
     * Adds an expense entry to an existing project. If the project already has this expense entry assigned false will
     * be returned.
     * </p>
     * 
     * <p>
     * This method adds a row to the ProjectExpenses table.
     * </p>
     *
     * @param entryId the id of the expense entry
     * @param projectId the id of the project
     * @param user the user who created the expense entry for the project
     *
     * @return true if the expense entry was added, false otherwise
     *
     * @throws NullPointerException if the user is null
     * @throws IllegalArgumentException if the user is the empty string
     * @throws PersistenceException if something wrong happens while adding the expense entry (such as SQL exception)
     */
    public boolean addExpenseEntry(int entryId, int projectId, String user)
        throws PersistenceException
    {
        checkString(user);

        PreparedStatement pstmt = null;

        try {
            // if the expense entry already exists, do not add it
            if (exist(SQL_SEL_EXPENSE_ENTRY, entryId, projectId)) {
                return false;
            }

            // use the current date for CreationDate and ModificationDate
            Date date = new Date();

            // add the expense entry to ProjectExpenses table
            pstmt = connection.prepareStatement(SQL_INS_EXPENSE_ENTRY);
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, entryId);
            pstmt.setTimestamp(3, toSQLDate(date));
            pstmt.setString(4, user);
            pstmt.setTimestamp(5, toSQLDate(date));
            pstmt.setString(6, user);
            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to add expense entry", e);
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * Deletes an expense entry of a project from the database. If the project does not have this expense entry
     * assigned false will be returned.
     * </p>
     * 
     * <p>
     * This method removes a row from the ProjectExpenses table.
     * </p>
     *
     * @param entryId the id of the expense entry
     * @param projectId the id of the project
     *
     * @return true if the entry was removed, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the expense entry (such as SQL exception)
     */
    public boolean removeExpenseEntry(int entryId, int projectId)
        throws PersistenceException
    {
        try {
            // remove the expense entry from ProjectExpenses table
            return remove(SQL_DEL_EXPENSE_ENTRY, entryId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove expense entry", e);
        }
    }

    /**
     * <p>
     * Retrieves all the expense entries of a project. If there are no expense entries associated with a project, an
     * empty list will be returned.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return a list containing the ids of the expense entries
     *
     * @throws PersistenceException if something wrong happens while getting the expense entries (such as SQL
     *         exception)
     */
    public List getExpenseEntries(int projectId) throws PersistenceException {
        try {
            // get the expense entries from ProjectExpenses table
            return getIntegers(SQL_SEL_EXPENSE_ENTRIES, projectId, 2);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get expense entries", e);
        }
    }

    /**
     * <p>
     * Retrieves a project from the database. If the project does not exist, null will be returned.
     * </p>
     * 
     * <p>
     * The fields of the Project object will be properly populated.
     * </p>
     *
     * @param projectId the id of the project
     *
     * @return the project with the given id
     *
     * @throws PersistenceException if something wrong happens while getting the project (such as SQL exception)
     */
    public Project getProject(int projectId) throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get the project from Projects table
            pstmt = connection.prepareStatement(SQL_SEL_PROJECT);
            pstmt.setInt(1, projectId);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            } else {
                return getProject(rs);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get project", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves all the projects from the database. If there are no projects in the database, an empty list will be
     * returned.
     * </p>
     * 
     * <p>
     * The fields of the Project objects will be properly populated.
     * </p>
     *
     * @return a list containing all the projects
     *
     * @throws PersistenceException if something wrong happens while getting the projects (such as SQL exception)
     */
    public List getAllProjects() throws PersistenceException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // get all the projects from Projects table
            pstmt = connection.prepareStatement(SQL_SEL_ALL_PROJECTS);
            rs = pstmt.executeQuery();

            List projects = new ArrayList();

            while (rs.next()) {
                // populate the fields of each project
                projects.add(getProject(rs));
            }
            return projects;
        } catch (SQLException e) {
            throw new PersistenceException("Fails to get all projects", e);
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Removes a project from a client. If the client was not assigned the project, false will be returned.
     * </p>
     * 
     * <p>
     * This method removes a row from the ClientProjects table.
     * </p>
     *
     * @param clientId the id of the client
     * @param projectId the id of the project
     *
     * @return true if the project was removed from client, false otherwise
     *
     * @throws PersistenceException if something wrong happens while removing the project from client (such as SQL
     *         exception)
     */
    public boolean removeProjectFromClient(int clientId, int projectId)
        throws PersistenceException
    {
        try {
            // remove the project from ClientProjects table
            return remove(SQL_DEL_CLIENT_PROJECT_BY_BOTH, clientId, projectId);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to remove project from client", e);
        }
    }

    /**
     * <p>
     * Closes the connection to the database. After this method is called, the instance should be discarded.
     * </p>
     *
     * @throws PersistenceException if something wrong happens while closing the connection (such as SQL exception)
     */
    public void closeConnection() throws PersistenceException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new PersistenceException("Fails to close connection", e);
        }
    }

    /**
     * <p>
     * A convenient method to check the existence of a row in the database by executing the SQL statement with one
     * integer parameter. The SQL statement should be a SELECT statement.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     * @param param1 the integer parameter
     *
     * @return whether the row exists or not
     *
     * @throws SQLException if any database error occurs
     */
    private boolean exist(String sql, int param1) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // set one integer parameter
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param1);
            rs = pstmt.executeQuery();

            return rs.next();
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * A convenient method to check the existence of a row in the database by executing the SQL statement with two
     * integer parameters. The SQL statement should be a SELECT statement.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     * @param param1 first integer parameter
     * @param param2 second integer parameter
     *
     * @return whether the row exists or not
     *
     * @throws SQLException if any database error occurs
     */
    private boolean exist(String sql, int param1, int param2)
        throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // set two integer parameters
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param1);
            pstmt.setInt(2, param2);
            rs = pstmt.executeQuery();

            return rs.next();
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * A convenient method to remove rows from the database by executing the SQL statement with no integer parameters.
     * The SQL statement should be a DELETE statement.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     *
     * @return whether any row was removed
     *
     * @throws SQLException if any database error occurs
     */
    private boolean remove(String sql) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // no integer parameters to set
            pstmt = connection.prepareStatement(sql);
            return pstmt.executeUpdate() > 0;
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * A convenient method to remove rows from the database by executing the SQL statement with one integer parameter.
     * The SQL statement should be a DELETE statement.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     * @param param1 the integer parameter
     *
     * @return whether any row was removed
     *
     * @throws SQLException if any database error occurs
     */
    private boolean remove(String sql, int param1) throws SQLException {
        PreparedStatement pstmt = null;

        try {
            // set one integer parameter
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param1);
            return pstmt.executeUpdate() > 0;
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * A convenient method to remove rows from the database by executing the SQL statement with two integer parameters.
     * The SQL statement should be a DELETE statement.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     * @param param1 first integer parameter
     * @param param2 second integer parameter
     *
     * @return whether any row was removed
     *
     * @throws SQLException if any database error occurs
     */
    private boolean remove(String sql, int param1, int param2)
        throws SQLException
    {
        PreparedStatement pstmt = null;

        try {
            // set two integer parameters
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param1);
            pstmt.setInt(2, param2);
            return pstmt.executeUpdate() > 0;
        } finally {
            close(pstmt);
        }
    }

    /**
     * <p>
     * A convenient method to get the integer located at the specified column index by executing the SQL statement with
     * one integer parameter. The SQL statement should be a SELECT statement. If no rows are returned, this method
     * returns -1. If multiple rows are returned, only the first row is read.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     * @param param1 the integer parameter
     * @param columnIndex the column index to get the integer
     *
     * @return the integer located at the specified column index
     *
     * @throws SQLException if any database error occurs
     */
    private int getInteger(String sql, int param1, int columnIndex)
        throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // set one integer parameter
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param1);
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return -1;
            } else {
                return rs.getInt(columnIndex);
            }
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * A convenient method to get the integers located at the specified column index by executing the SQL statement
     * with one integer parameter. The SQL statement should be a SELECT statement. If no rows are returned, this
     * method returns an empty list.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param sql the SQL statement to execute
     * @param param1 the integer parameter
     * @param columnIndex the column index to get the integers
     *
     * @return the integers located at the specified column index
     *
     * @throws SQLException if any database error occurs
     */
    private List getIntegers(String sql, int param1, int columnIndex)
        throws SQLException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // set one integer parameter
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, param1);
            rs = pstmt.executeQuery();

            List integers = new ArrayList();

            while (rs.next()) {
                integers.add(new Integer(rs.getInt(columnIndex)));
            }
            return integers;
        } finally {
            close(rs);
            close(pstmt);
        }
    }

    /**
     * <p>
     * Gets the project with the data from the current row of the given ResultSet. The current row should be a row
     * pointing to the Projects table. The expense entries, time entries, workers and manager will also be populated.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param rs the ResultSet containing the project data
     *
     * @return the project with all the fields populated
     *
     * @throws SQLException if any database error occurs
     * @throws PersistenceException if something wrong happens while populating the fields
     */
    private Project getProject(ResultSet rs) throws SQLException, PersistenceException {
        Project project = new Project();

        // populate the fields of project
        project.setId(rs.getInt(1));
        project.setName(rs.getString(2));
        project.setDescription(rs.getString(3));
        project.setStartDate(toUtilDate(rs.getTimestamp(4)));
        project.setEndDate(toUtilDate(rs.getTimestamp(5)));
        project.setCreationDate(toUtilDate(rs.getTimestamp(6)));
        project.setCreationUser(rs.getString(7));
        project.setModificationDate(toUtilDate(rs.getTimestamp(8)));
        project.setModificationUser(rs.getString(9));

        int projectId = project.getId();

        // populate the project manager id of project
        project.setManagerId(getInteger(SQL_SEL_PROJECT_MANAGER, projectId, 2));

        // populate the project workers ids of project
        project.setWorkersIds(new HashSet(getIntegers(SQL_SEL_PROJECT_WORKERS, projectId, 2)));

        // populate the expense entries of project
        project.setExpenseEntries(new HashSet(getExpenseEntries(projectId)));

        // populate the time entries of project
        project.setTimeEntries(new HashSet(getTimeEntries(projectId)));

        return project;
    }

    /**
     * <p>
     * Gets the project worker with the data from the current row of the given ResultSet. The current row should be a
     * row pointing to the ProjectWorkers table.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param rs the ResultSet containing the project worker data
     *
     * @return the project worker with all the fields populated
     *
     * @throws SQLException if any database error occurs
     * @throws PersistenceException if something wrong happens while populating the fields
     */
    private ProjectWorker getWorker(ResultSet rs) throws SQLException, PersistenceException {
        ProjectWorker worker = new ProjectWorker();

        // populate the fields of the project worker
        worker.setProject(getProject(rs.getInt(1)));
        worker.setWorkerId(rs.getInt(2));
        worker.setStartDate(toUtilDate(rs.getTimestamp(3)));
        worker.setEndDate(toUtilDate(rs.getTimestamp(4)));
        worker.setPayRate(rs.getBigDecimal(5));
        worker.setCreationDate(toUtilDate(rs.getTimestamp(6)));
        worker.setCreationUser(rs.getString(7));
        worker.setModificationDate(toUtilDate(rs.getTimestamp(8)));
        worker.setModificationUser(rs.getString(9));

        return worker;
    }

    /**
     * <p>
     * Gets the client with the data from the current row of the given ResultSet. The current row should be a row
     * pointing to the Clients table. The projects of the client will also be populated.
     * </p>
     * 
     * <p>
     * This method throws SQLException so that the caller can wrap this exception with specific message.
     * </p>
     *
     * @param rs the ResultSet containing the client data
     *
     * @return the client with all the fields populated
     *
     * @throws SQLException if any database error occurs
     * @throws PersistenceException if something wrong happens while populating the fields
     */
    private Client getClient(ResultSet rs) throws SQLException, PersistenceException {
        Client client = new Client();

        // populate the fields of the client
        client.setId(rs.getInt(1));
        client.setName(rs.getString(2));
        client.setCreationDate(toUtilDate(rs.getTimestamp(3)));
        client.setCreationUser(rs.getString(4));
        client.setModificationDate(toUtilDate(rs.getTimestamp(5)));
        client.setModificationUser(rs.getString(6));

        // populate the projects of the client
        client.setProjects(getAllClientProjects(client.getId()));

        return client;
    }

    /**
     * <p>
     * The actual implementation of the addProjectToClient() method.
     * </p>
     * 
     * <p>
     * This method does not deal with transaction. The transaction is expected to be started by the caller so that it
     * can be committed or rolled back in one step, in case there are multiple SQL statements grouped into the
     * transaction. This method also assumes valid arguments.
     * </p>
     *
     * @param clientId the id of the client
     * @param project the project to add to the client
     * @param user the user who assigned the client for the project
     *
     * @return true if the project was added to the client, false otherwise
     *
     * @throws PersistenceException if something wrong happens while adding the project to the client (such as SQL
     *         exception)
     */
    private boolean addProjectToClientImpl(int clientId, Project project, String user)
        throws PersistenceException
    {
        // add the project to Projects table
        addProject(project);

        // add the client to ClientProjects table
        return assignClient(project.getId(), clientId, user);
    }

    /**
     * <p>
     * Checks if the given string is null or empty.
     * </p>
     *
     * @param str the string to check against.
     *
     * @throws NullPointerException if str is null.
     * @throws IllegalArgumentException if str is empty.
     */
    private static void checkString(String str) {
        if (str == null) {
            throw new NullPointerException("String argument is null");
        }
        if (str.trim().length() == 0) {
            throw new IllegalArgumentException("String argument is empty string");
        }
    }

    /**
     * <p>
     * Converts the given java.util.Date to the java.sql.Timestamp for SQL operations.
     * </p>
     *
     * @param date the java.util.Date to be converted
     *
     * @return the converted java.sql.Timestamp
     */
    private static Timestamp toSQLDate(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * <p>
     * Converts the given java.sql.Timestamp to the java.util.Date for setting the fields.
     * </p>
     *
     * @param date the java.sql.Timestamp to be converted
     *
     * @return the converted java.util.Date
     */
    private static Date toUtilDate(Timestamp date) {
        return new Date(date.getTime());
    }

    /**
     * <p>
     * Try to close the prepared statement object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param pstmt the prepared statement object.
     */
    private static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {}
        }
    }

    /**
     * <p>
     * Try to close the result set object if it is not null. Returns silently if any SQL error occurs.
     * </p>
     *
     * @param rs the result set object.
     */
    private static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {}
        }
    }

    /**
     * <p>
     * Commits the transaction for the connection object. If any error occurs, it will be wrapped as a
     * PersistenceException.
     * </p>
     *
     * @throws PersistenceException if transaction cannot be committed
     */
    private void commit() throws PersistenceException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new PersistenceException("Fails to commit transaction", e);
        }
    }

    /**
     * <p>
     * Try to rollback the connection object. Returns silently if any SQL error occurs.
     * </p>
     */
    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {}
    }

    /**
     * <p>
     * Enables transaction for the connection object. If any error occurs, it will be wrapped as a
     * PersistenceException.
     * </p>
     *
     * @throws PersistenceException if transaction cannot be enabled
     */
    private void enableTransaction() throws PersistenceException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new PersistenceException("Fails to enable transaction", e);
        }
    }

    /**
     * <p>
     * Try to disable transaction for the connection object. Returns silently if any SQL error occurs.
     * </p>
     */
    private void disableTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {}
    }
}
