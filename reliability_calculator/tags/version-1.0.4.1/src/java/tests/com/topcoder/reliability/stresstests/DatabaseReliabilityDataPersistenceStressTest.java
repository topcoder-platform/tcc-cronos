/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reliability.stresstests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.reliability.impl.UserProjectReliabilityData;
import com.topcoder.reliability.impl.persistence.DatabaseReliabilityDataPersistence;

/**
 * <p>
 * This class contains Stress tests for DatabaseReliabilityDataPersistence.
 * </p>
 * @author sokol
 * @version 1.0
 */
public class DatabaseReliabilityDataPersistenceStressTest extends BaseStressTest {

    /**
     * <p>
     * Represents query for inserting component inquiry.
     * </p>
     */
    private static final String INSERT_COMPONENT_INQUIRY =
            "insert into component_inquiry(component_inquiry_id ,user_id ,agreed_to_terms ,rating, project_id) values ( ?,?,1,1,?)";

    /**
     * <p>
     * Represents query for inserting project.
     * </p>
     */
    private static final String INSERT_PROJECT = "insert into project values ( ?,?,?,?,? ,?,?,? )";

    /**
     * <p>
     * Represents query for inserting project info.
     * </p>
     */
    private static final String INSERT_PROJECT_INFO = "insert into project_info  values (? ,13,'yes' ,? ,? ,? ,?)";

    /**
     * <p>
     * Represents query for inserting project phase.
     * </p>
     */
    private static final String INSERT_PROJECT_PHASE =
            "insert into project_phase  values ( ?,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";

    /**
     * <p>
     * Represents query for inserting project result.
     * </p>
     */
    private static final String INSERT_PROJECT_RESULT =
            "insert into project_result (user_id,project_id,passed_review_ind) values ( ?,? ,1)";

    /**
     * <p>
     * Represents project count constant.
     * </p>
     */
    private static final int PROJECT_COUNT = 5;

    /**
     * <p>
     * Represents submission phase id.
     * </p>
     */
    private static final int SUBMISSION_PHASE = 2;

    /**
     * <p>
     * Represents connection factory for testing.
     * </p>
     */
    private DBConnectionFactory dbConnectionFactory;

    /**
     * <p>
     * Represents DatabaseReliabilityDataPersistence instance for testing.
     * </p>
     */
    private DatabaseReliabilityDataPersistence persistence;

    /**
     * <p>
     * Tests {@link DatabaseReliabilityDataPersistence#getIdsOfUsersWithReliability(long, java.util.Date)} with valid
     * arguments passed and small test.
     * </p>
     * <p>
     * List of ids should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetIdsOfUsersWithReliability_Large() throws Exception {
        Date startDate = new Date(0);
        long projectCategoryId = 2;
        for (int i = 0; i < PROJECT_COUNT; i++) {
            createProject(i + 1, (int) projectCategoryId);
        }
        for (int i = 0; i < getRunCount(); i++) {
            persistence.getIdsOfUsersWithReliability(projectCategoryId, startDate);
        }
    }

    /**
     * <p>
     * Tests {@link DatabaseReliabilityDataPersistence#getIdsOfUsersWithReliability(long, java.util.Date)} with valid
     * arguments passed and small test.
     * </p>
     * <p>
     * List of ids should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetIdsOfUsersWithReliability_Small() throws Exception {
        Date startDate = new Date(0);
        long projectCategoryId = 2;
        for (int i = 0; i < PROJECT_COUNT; i++) {
            createProject(i + 1, (int) projectCategoryId);
        }
        for (int i = 0; i < getRunCount(); i++) {
            persistence.getIdsOfUsersWithReliability(projectCategoryId, startDate);
        }
    }

    /**
     * <p>
     * Tests {@link DatabaseReliabilityDataPersistence#getUserParticipationData(long, long, Date)} with valid arguments
     * passed and large test.
     * </p>
     * <p>
     * List of UserProjectParticipationData should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetUserParticipationData_Large() throws Exception {
        Date startDate = new Date(0);
        long projectCategoryId = 2;
        long userId = 1;
        for (int i = 0; i < PROJECT_COUNT; i++) {
            createProject(i + 1, (int) projectCategoryId);
        }
        for (int i = 0; i < getRunCount(); i++) {
            assertNotNull("Result should be retrieved successfully.", persistence.getUserParticipationData(userId,
                    projectCategoryId, startDate));
        }
    }

    /**
     * <p>
     * Tests {@link DatabaseReliabilityDataPersistence#getUserParticipationData(long, long, Date)} with valid arguments
     * passed and small test.
     * </p>
     * <p>
     * List of UserProjectParticipationData should be retrieved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testGetUserParticipationData_Small() throws Exception {
        Date startDate = new Date(0);
        long projectCategoryId = 2;
        long userId = 1;
        for (int i = 0; i < PROJECT_COUNT; i++) {
            createProject(i + 1, (int) projectCategoryId);
        }
        for (int i = 0; i < getRunCount(); i++) {
            assertNotNull("Result should be retrieved successfully.", persistence.getUserParticipationData(userId,
                    projectCategoryId, startDate));
        }
    }

    /**
     * <p>
     * Tests {@link DatabaseReliabilityDataPersistence#saveUserReliabilityData(List)} with valid arguments passed and
     * small test case.
     * </p>
     * <p>
     * User reliability data should be saved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserReliabilityData_Small() throws Exception {
        List < UserProjectReliabilityData > projects = new ArrayList < UserProjectReliabilityData >();
        int projectCategoryId = 2;
        int userId = 1;
        for (int i = 0; i < getRunCount(); i++) {
            projects.clear();
            for (int j = 0; j < PROJECT_COUNT; j++) {
                int projectId = j + 1;
                createProject(projectId, projectCategoryId);
                projects.add(createUserProjectReliabilityData(projectId, userId));
            }
            persistence.saveUserReliabilityData(projects);
            int rows = getTableRowCount("project_reliability");
            assertEquals("Invalid inserted rows count.", rows, PROJECT_COUNT);
            clearTables();
        }
    }

    /**
     * <p>
     * Tests {@link DatabaseReliabilityDataPersistence#saveUserReliabilityData(List)} with valid arguments passed and
     * large test case.
     * </p>
     * <p>
     * User reliability data should be saved. No exception is expected.
     * </p>
     * @throws Exception if any error occurs
     */
    public void testSaveUserReliabilityData_Large() throws Exception {
        List < UserProjectReliabilityData > projects = new ArrayList < UserProjectReliabilityData >();
        int projectCategoryId = 2;
        int userId = 1;
        // save 500 projects
        int projectsCount = 5;
        setRunCount(1);
        for (int j = 0; j < projectsCount; j++) {
            int projectId = j + 1;
            createProject(projectId, projectCategoryId);
            projects.add(createUserProjectReliabilityData(projectId, userId));
        }
        persistence.saveUserReliabilityData(projects);
        int rows = getTableRowCount("project_reliability");
        assertEquals("Invalid inserted rows count.", rows, projectsCount);
    }

    /**
     * <p>
     * Clears database tables.
     * </p>
     * @throws Exception if any error occurs
     */
    private void clearTables() throws Exception {
        Connection connection = dbConnectionFactory.createConnection();
        try {
            String[] queries = getQueriesFromFile(STRESS_TEST_DIR + "clear.sql");
            for (String sql : queries) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Closes given connection.
     * </p>
     * @param connection the connection to be closed
     */
    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // ignored
            }
        }
    }

    /**
     * <p>
     * Inserts project, project_phase, project_result, project_info into database.
     * </p>
     * @param projectId the user id of project result
     * @param projectCategoryId the project category id
     * @throws Exception if any error occurs
     */
    private void createProject(int projectId, int projectCategoryId) throws Exception {
        Connection connection = dbConnectionFactory.createConnection();
        try {
            // insert project
            insertProject(connection, projectId, projectCategoryId);
            // insert project phase
            insertProjectPhase(connection, projectId, SUBMISSION_PHASE);
            // insert project result
            insertProjectResult(connection, projectId);
            // insert project info
            insertProjectInfo(connection, projectId);
            // insert component inquiry
            insertComponentInquiry(connection, projectId);
        } finally {
            closeConnection(connection);
        }
    }

    /**
     * <p>
     * Creates UserProjectReliabilityData with given values.
     * </p>
     * @param projectId the project id
     * @param userId the user id
     * @return UserProjectReliabilityData with given values
     */
    private UserProjectReliabilityData createUserProjectReliabilityData(int projectId, int userId) {
        UserProjectReliabilityData data = new UserProjectReliabilityData();
        data.setProjectId(projectId);
        data.setUserId(userId);
        data.setReliabilityAfterResolution(0.5);
        data.setReliabilityBeforeResolution(0.2);
        data.setReliabilityOnRegistration(0.9);
        data.setReliable(false);
        data.setResolutionDate(new Date());
        return data;
    }

    /**
     * <p>
     * Retrieves queries from file.
     * </p>
     * @param fileName the file name
     * @return String array of queries
     * @throws Exception if any error occurs
     */
    private String[] getQueriesFromFile(String fileName) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        reader.close();
        return sb.toString().split(";");
    }

    /**
     * <p>
     * Retrieves row count in table.
     * </p>
     * @param tableName the table name
     * @return table row count
     * @throws Exception if any error occurs
     */
    private int getTableRowCount(String tableName) throws Exception {
        Connection connection = dbConnectionFactory.createConnection();
        int result = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" select * from " + tableName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result++;
            }
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    /**
     * <p>
     * Inserts component inquiry into database.
     * </p>
     * @param connection the connection to database
     * @param projectId the project id
     * @throws Exception if any error occurs
     */
    private void insertComponentInquiry(Connection connection, int projectId) throws Exception {
        PreparedStatement statement = connection.prepareStatement(INSERT_COMPONENT_INQUIRY);
        // component inquiry id
        statement.setInt(1, projectId);
        // user id
        statement.setInt(2, projectId);
        // project id
        statement.setInt(3, projectId);
        statement.executeUpdate();
        statement.close();

        statement = connection.prepareStatement("insert into contest_eligibility values (1009, 1009, 0);");
    }

    /**
     * <p>
     * Inserts project into database.
     * </p>
     * @param connection the connection to database
     * @param projectId the project id
     * @param projectCategoryId the project category id
     * @throws Exception if any error occurs
     */
    private void insertProject(Connection connection, int projectId, int projectCategoryId) throws Exception {
        PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT);
        statement.setInt(1, projectId);
        // Completed
        statement.setInt(2, 1);
        // Category
        statement.setInt(3, projectCategoryId);
        statement.setString(4, "create_user");
        statement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        statement.setString(6, "modify_user");
        statement.setDate(7, new java.sql.Date(System.currentTimeMillis()));
        statement.setInt(8, projectId);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * <p>
     * Inserts project info into database.
     * </p>
     * @param connection the connection to database
     * @param projectId the project id
     * @throws Exception if any error occurs
     */
    private void insertProjectInfo(Connection connection, int projectId) throws Exception {
        PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT_INFO);
        statement.setInt(1, projectId);
        statement.setString(2, "create_user");
        statement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
        statement.setString(4, "modify_user");
        statement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        statement.executeUpdate();
        statement.close();
    }

    /**
     * <p>
     * Inserts project phase into database.
     * </p>
     * @param connection the connection to database
     * @param projectId the project id
     * @param phaseType the phase type
     * @throws Exception if any error occurs
     */
    private void insertProjectPhase(Connection connection, int projectId, int phaseType) throws Exception {
        PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT_PHASE);
        // phase id
        statement.setInt(1, projectId);
        // project id
        statement.setInt(2, projectId);
        // phase type
        statement.setInt(3, phaseType);
        // phase status closed
        statement.setInt(4, 3);
        statement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        statement.setDate(6, new java.sql.Date(System.currentTimeMillis()));
        statement.setDate(7, new java.sql.Date(System.currentTimeMillis()));
        statement.setDate(8, new java.sql.Date(System.currentTimeMillis()));
        statement.setDate(9, new java.sql.Date(System.currentTimeMillis()));
        statement.setInt(10, 1);
        statement.setString(11, "create_user");
        statement.setDate(12, new java.sql.Date(System.currentTimeMillis()));
        statement.setString(13, "modify_user");
        statement.setDate(14, new java.sql.Date(System.currentTimeMillis()));
        statement.executeUpdate();
        statement.close();
    }

    /**
     * <p>
     * Inserts project result into database.
     * </p>
     * @param connection the connection to database
     * @param projectId the project id
     * @throws Exception if any error occurs
     */
    private void insertProjectResult(Connection connection, int projectId) throws Exception {
        PreparedStatement statement = connection.prepareStatement(INSERT_PROJECT_RESULT);
        // user id
        statement.setInt(1, projectId);
        // project id
        statement.setInt(2, projectId);
        statement.executeUpdate();
        statement.close();
    }

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        persistence = new DatabaseReliabilityDataPersistence();
        persistence.configure(getConfigurationObject(new String[] {"reliabilityCalculator1", "config",
                                                                   "reliabilityDataPersistenceConfig"}));
        persistence.open();
        dbConnectionFactory =
                new DBConnectionFactoryImpl(getConfigurationObject(new String[] {"reliabilityCalculator1", "config",
                                                                                 "reliabilityDataPersistenceConfig",
                                                                                 "dbConnectionFactoryConfig"}));
        clearTables();
        persistence.setIncludedProjectStatuses(new ArrayList < Long >() {

            {
                add(1L);
                add(2L);
            }
        });
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     * @throws Exception if any error occurs
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        persistence.close();
        persistence = null;
        clearTables();
    }
}
