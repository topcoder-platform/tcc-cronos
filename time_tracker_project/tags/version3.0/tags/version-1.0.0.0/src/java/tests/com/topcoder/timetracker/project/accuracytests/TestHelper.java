/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.timetracker.project.Client;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectWorker;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.Date;


/**
 * <p>
 * Helper class provides some useful helper methods. Such as create a customer defined connection, insert/select/delete
 * records in the datebase.
 * </p>
 *
 * @author PE
 * @version 1.0
 */
class TestHelper extends TestCase {
    /** Represents the namespace to load manager configuration. */
    static final String NAMESPACE = "com.topcoder.timetracker.project";

    /** Represents the namespace to load DB connection factory configuration. */
    static final String DB_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Date for testing.
     * </p>
     */
    static final Date DATE = createDate(1, 1, 1, 1, 1, 1);

    /**
     * <p>
     * Get the Connection from DBConnectionFactory.
     * </p>
     *
     * @param namespace the namespace to load the DBConnectionFactory configuration
     *
     * @return the connection created from DBConnectionFactory
     *
     * @throws Exception any exception during the create connection process
     */
    static Connection getConnection(String namespace) throws Exception {
        // get the Connection instance
        DBConnectionFactory factory = new DBConnectionFactoryImpl(namespace);

        return factory.createConnection();
    }

    /**
     * Deletes all records from the tables.
     *
     * @param conn the connection instance to connect the database
     *
     * @throws SQLException if any SQL error occurs.
     */
    static void clearRecords(Connection conn) throws SQLException {
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
     *
     * @param resultSet the ResultSet to be released
     * @param statement the Statement to be released
     * @param connection the Connection to be released
     *
     * @throws SQLException any exception during the closing process
     */
    static void closeResources(ResultSet resultSet, Statement statement, Connection connection)
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
     *
     * @throws Exception unexpected exception.
     */
    static void clearConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
        }
    }

    /**
     * <p>
     * add the config.
     * </p>
     *
     * @throws Exception unexpected exception.
     */
    static void addConfig() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(NAMESPACE)) {
            configManager.removeNamespace(NAMESPACE);
        }

        if (configManager.existsNamespace(DB_NAMESPACE)) {
            configManager.removeNamespace(DB_NAMESPACE);
        }

        configManager.add(NAMESPACE, "accuracytests/config.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        configManager.add(DB_NAMESPACE, "accuracytests/config.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * <p>
     * Creates a Date instance representing the given year, month and date. The time would be 0:0:0 0:0:0.
     * </p>
     *
     * @param year the year in the instance.
     * @param month the month in the instance.
     * @param date the date in the instance.
     * @param hour the hour in the instance.
     * @param minute the minute in the instance.
     * @param second the second in the instance.
     *
     * @return a Date instance representing the year, month, date, hour, minute and second.
     */
    static Date createDate(int year, int month, int date, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(year, month - 1, date, hour, minute, second);

        return calendar.getTime();
    }

    /**
     * convert the input Date instance to a long value. Use second accuracy.
     *
     * @param date the Date instance needed to convert
     *
     * @return the converted value
     */
    static long convertDate(Date date) {
        long time = date.getTime();

        return ((long) (time / 1000)) * 1000;
    }

    /**
     * Creates a client with the specified id for testing.
     *
     * @param clientId the id of the client
     *
     * @return a Client instance
     */
    static Client createClient(int clientId) {
        Client client = new Client();

        client.setId(clientId);
        client.setName("name");
        client.setCreationUser("creationUser");
        client.setModificationUser("modificationUser");

        return client;
    }

    /**
     * Creates a project with the specified id for testing.
     *
     * @param projectId the id of the project
     *
     * @return a Project instance
     */
    static Project createProject(int projectId) {
        Project project = new Project();

        project.setId(projectId);
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
     *
     * @return a project manager
     */
    static ProjectManager createManager() {
        ProjectManager manager = new ProjectManager();

        manager.setProject(new Project());
        manager.setCreationUser("creationUser");
        manager.setModificationUser("creationUser");

        return manager;
    }

    /**
     * Creates a project worker for testing..
     *
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
     *
     * @param conn the connection.
     *
     * @throws Exception if any unexpected exception occurs.
     */
    static void clearTables(Connection conn) throws Exception {
        String[] tableNames = {
                "ClientProjects", "ProjectExpenses", "ProjectManagers", "ProjectWorkers", "ProjectTimes", "Clients",
                "Projects"
            };

        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < tableNames.length; i++) {
                String sql = "DELETE FROM " + tableNames[i];
                pstmt = conn.prepareStatement(sql);

                pstmt.executeUpdate();
            }
        } finally {
            pstmt.close();
        }
    }

    /**
     * count the number of rows in the table.
     *
     * @param tableName the name of the table
     * @param conn the connection
     *
     * @return the rows in the table
     *
     * @throws Exception if any unexpected exception occurs.
     */
    static int countTableRows(String tableName, Connection conn)
        throws Exception {
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
            TestHelper.closeResources(rs, pstmt, null);
        }
    }

    /**
     * Checks whether two projects are equal.
     *
     * @param p1 the source project
     * @param p2 the target project
     */
    static void assertProjectsEquals(Project p1, Project p2) {
        assertEquals("creatoin user should be equal", p1.getCreationUser(), p2.getCreationUser());
        assertEquals("modification user should be equal", p1.getModificationUser(), p2.getModificationUser());
        assertEquals("description should be equal", p1.getDescription(), p2.getDescription());
        assertEquals("id should be equal", p1.getId(), p2.getId());
        assertEquals("name should be equal", p1.getName(), p2.getName());
    }
}
