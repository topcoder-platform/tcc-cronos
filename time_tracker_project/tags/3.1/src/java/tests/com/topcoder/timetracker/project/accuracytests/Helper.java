/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Helper class provides some useful helper methods. Such as create a customer defined connection, insert/select/delete
 * records in the datebase.
 * </p>
 * @author
 * @version 1.0
 */
public class Helper extends TestCase {
    /** Represents the namespace to load manager configuration. */
    public static final String NAMESPACE = "com.topcoder.timetracker.project";

    /** Represents the namespace to load DB connection factory configuration. */
    public static final String DB_NAMESPACE = "com.topcoder.timetracker.project.accuracytests.db";

    /** Represents the namespace to load clients config for DatabaseSearchUtility class. */
    public static final String DB_SEARCH_UTILITY_CLIENTS = "com.topcoder.timetracker.project.accuraytests.DatabaseSearchUtility.clients";

    /** Represents the namespace to load projects config for DatabaseSearchUtility class. */
    public static final String DB_SEARCH_UTILITY_PROJECTS = "com.topcoder.timetracker.project.accuraytests.DatabaseSearchUtility.projects";

    /** Represents the connection productor name. */
    public static final String CONNECTION_PRODUCER_NAME = "Informix_Connection_Producer";

    /**
     * <p>
     * Date for testing.
     * </p>
     */
    public static final Date DATE = createDate(1, 1, 1, 1, 1, 1);

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     * @param namespace
     *            the namespace to load the DBConnectionFactory configuration
     * @return the connection created from DBConnectionFactory
     * @throws Exception
     *             any exception during the create connection process
     */
    public static Connection getConnection() throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(DB_NAMESPACE);
        return factory.createConnection();
    }

    /**
     * Deletes all records from the tables.
     * @param conn
     *            the connection instance to connect the database
     * @throws SQLException
     *             if any SQL error occurs.
     */
    public static void clearRecords(Connection conn) throws SQLException {
        Statement statement = null;

        try {
            statement = conn.createStatement();

            statement.executeUpdate("DELETE FROM ExpenseEntries;");
            statement.executeUpdate("DELETE FROM ExpenseTypes;");
            statement.executeUpdate("DELETE FROM ExpenseStatuses;");
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * release the resources.
     * </p>
     * @param resultSet
     *            the ResultSet to be released
     * @param statement
     *            the Statement to be released
     * @param connection
     *            the Connection to be released
     * @throws SQLException
     *             any exception during the closing process
     */
    public static void closeResources(ResultSet resultSet, Statement statement, Connection connection)
        throws SQLException {
        boolean success = true;

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException se) {
                success = false;
            }
        }

        if (!success) {
            throw new SQLException("fail to close the resources");
        }
    }

    /**
     * <p>
     * clear the config.
     * </p>
     * @throws Exception
     *             unexpected exception.
     */
    public static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
        }

        if (configManager.existsNamespace(DB_SEARCH_UTILITY_PROJECTS)) {
            configManager.removeNamespace(DB_SEARCH_UTILITY_PROJECTS);
        }

        if (configManager.existsNamespace(DB_SEARCH_UTILITY_CLIENTS)) {
            configManager.removeNamespace(DB_SEARCH_UTILITY_CLIENTS);
        }
    }

    /**
     * <p>
     * add the config.
     * </p>
     * @throws Exception
     *             unexpected exception.
     */
    public static void addConfig() throws Exception {
        clearConfig();
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("accuracytests/config.xml");
        configManager.add("accuracytests/db.xml");
        configManager.add("accuracytests/databaseutils.xml");
    }

    /**
     * <p>
     * Creates a Date instance representing the given year, month and date. The time would be 0:0:0 0:0:0.
     * </p>
     * @param year
     *            the year in the instance.
     * @param month
     *            the month in the instance.
     * @param date
     *            the date in the instance.
     * @param hour
     *            the hour in the instance.
     * @param minute
     *            the minute in the instance.
     * @param second
     *            the second in the instance.
     * @return a Date instance representing the year, month, date, hour, minute and second.
     */
    public static Date createDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, hour, minute, second);

        return calendar.getTime();
    }

    /**
     * convert the input Date instance to a long value. Use second accuracy.
     * @param date
     *            the Date instance needed to convert
     * @return the converted value
     */
    public static long convertDate(Date date) {
        long time = date.getTime();

        return ((long) (time / 1000)) * 1000;
    }

    /**
     * Creates a client with the specified id for testing.
     * @param clientId
     *            the id of the client
     * @return a Client instance
     */
    public static Client createClient(int clientId) {
        Client client = new Client();

        client.setId(clientId);
        client.setCompanyId(1);
        client.setName("name" + "_" + clientId);
        client.setCreationUser("creationUser");
        client.setModificationUser("modificationUser");

        return client;
    }

    /**
     * Creates a project with the specified id for testing.
     * @param projectId
     *            the id of the project
     * @return a Project instance
     */
    public static Project createProject(int projectId) {
        Project project = new Project();

        project.setId(projectId);
        project.setCompanyId(1);
        project.setName("name");
        project.setDescription("description");
        project.setCreationUser("creationUser");
        project.setModificationUser("creationUser");
        project.setStartDate(DATE);
        project.setEndDate(DATE);

        return project;
    }

    /**
     * Creates a project manager for testing..
     * @return a project manager
     */
    public static ProjectManager createManager() {
        ProjectManager manager = new ProjectManager();

        manager.setProject(new Project());
        manager.setCreationUser("creationUser");
        manager.setModificationUser("creationUser");

        return manager;
    }

    /**
     * Creates a project worker for testing..
     * @return a project worker
     */
    public static ProjectWorker createWorker() {
        ProjectWorker worker = new ProjectWorker();

        worker.setStartDate(DATE);
        worker.setEndDate(DATE);
        worker.setPayRate(new BigDecimal(1));
        worker.setCreationUser("creationUser");
        worker.setModificationUser("creationUser");

        return worker;
    }

    /**
     * Clears all the tables in the database.
     * @param conn
     *            the connection.
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public static void clearTables() throws Exception {
        Connection conn = getConnection();
        String[] tableNames = {"client_project", "project_expense", "project_manager", "project_worker",
                "project_time", "client", "project"};

        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < tableNames.length; i++) {
                String sql = "DELETE FROM " + tableNames[i];
                pstmt = conn.prepareStatement(sql);

                pstmt.executeUpdate();
            }
        } finally {
            pstmt.close();
            conn.close();
        }
    }

    /**
     * count the number of rows in the table.
     * @param tableName
     *            the name of the table
     * @param conn
     *            the connection
     * @return the rows in the table
     * @throws Exception
     *             if any unexpected exception occurs.
     */
    public static int countTableRows(String tableName, Connection conn) throws Exception {
        String sql = "SELECT * FROM " + tableName;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            int ret = 0;

            while (rs.next()) {
                ret++;
            }

            return ret;
        } finally {
            Helper.closeResources(rs, pstmt, null);
        }
    }

    /**
     * Checks whether two projects are equal.
     * @param p1
     *            the source project
     * @param p2
     *            the target project
     */
    public static void assertProjectsEquals(Project p1, Project p2) {
        assertEquals("creatoin user should be equal", p1.getCreationUser(), p2.getCreationUser());
        assertEquals("modification user should be equal", p1.getModificationUser(), p2.getModificationUser());
        assertEquals("description should be equal", p1.getDescription(), p2.getDescription());
        assertEquals("id should be equal", p1.getId(), p2.getId());
        assertEquals("name should be equal", p1.getName(), p2.getName());
    }

    // since 2.0

    public static void createCompany(int id) throws Exception {
        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO company(company_id, name, passcode, creation_date," +
                "creation_user, modification_date, modification_user) VALUES (?,?,?,?,?,?,?)");

        try {
        pstmt.setInt(1, id);
        pstmt.setString(2, "name");
        pstmt.setString(3, "passcode");
        pstmt.setDate(4, new java.sql.Date(System.currentTimeMillis()));
        pstmt.setString(5, "kr");
        pstmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
        pstmt.setString(7, "kr");

        pstmt.execute();
        } finally {
            pstmt.close();
            conn.close();
        }
    }
}
