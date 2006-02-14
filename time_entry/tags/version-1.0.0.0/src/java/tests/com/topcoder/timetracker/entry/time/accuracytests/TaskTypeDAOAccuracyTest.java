/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.time.BaseDAO;
import com.topcoder.timetracker.entry.time.BaseDataObject;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test for <code>TaskTypeDAO</code>.
 * @author fuyun
 * @version 1.0
 */
public class TaskTypeDAOAccuracyTest extends TestCase {

    /**
     * <p>
     * The <code>TaskTypeDAO</code> instance used for test.
     * </p>
     */
    private TaskTypeDAO taskTypeDAO;

    /**
     * <p>
     * the connection name used for create <code>BaseDAO</code> object.
     * </p>
     */
    private final String connName = "Connection";

    /**
     * <p>
     * the namespace used for create <code>BaseDAO</code> object.
     * </p>
     */
    private final String namespace = "com.topcoder.timetracker.entry.time";

    /**
     * <p>
     * the <code>Connection</code> instance used for test.
     * </p>
     */
    private Connection connection;

    /**
     * <p>
     * the <code>ConfigManager</code> instance used for test.
     * </p>
     */
    private ConfigManager configManager;

    /**
     * <p>
     * the DB connection factory used to create connections.
     * </p>
     */
    private DBConnectionFactory factory;

    /**
     * <p>
     * the description for <code>TaskType</code>.
     * </p>
     */
    private final String desc = "Time Entry Accuracy test";

    /**
     * <p>
     * another description used to test the update method.
     * </p>
     */
    private final String updatedDesc = "updated";

    /**
     * <p>
     * the <code>TaskType</code> instance used for test.
     * </p>
     */
    private TaskType taskType;

    /**
     * <p>
     * Represent the create user.
     * </p>
     */
    private final String createUser = "Topcoder creator";

    /**
     * <p>
     * Represent the modify user.
     * </p>
     */
    private final String modifyUser = "Topcoder modifier";

    /**
     * <p>
     * Set up the test environment.
     * </p>
     * @throws Exception is any problem occurs.
     */
    protected void setUp() throws Exception {
        configManager = ConfigManager.getInstance();

        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
        }

        configManager.add("SampleConfig.xml");
        
        // FIXME: zmianieone
        if (configManager.existsNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl")) {
            configManager.removeNamespace("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        }
        configManager.add("DBFactory.xml");

        taskTypeDAO = new TaskTypeDAO(connName, namespace);

        taskType = new TaskType();

        taskType.setDescription(desc);

        factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connection = factory.createConnection();

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM id_sequences WHERE name = '"
                    + TaskType.class.getName() + "';");
            statement
                    .executeUpdate("INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('"
                            + TaskType.class.getName() + "', 0, 1000);");
            statement.executeUpdate("DELETE FROM TaskTypes;");
        } finally {
            statement.close();
        }
    }

    /**
     * <p>
     * clean up the test environment.
     * </p>
     * @throws Exception if any problem occurs.
     */
    protected void tearDown() throws Exception {
        if (configManager.existsNamespace(namespace)) {
            configManager.removeNamespace(namespace);
        }

        connection.setAutoCommit(true);

        Statement statement = connection.createStatement();

        try {
 //           statement.executeUpdate("DELETE FROM id_sequences WHERE name = '"
 //                   + TaskType.class.getName() + "';");
            statement.executeUpdate("DELETE FROM TaskTypes;");
        } finally {
            statement.close();
            connection.close();
        }
    }

    /**
     * Test constructor TaskTypeDAO(String connName, String namespace).
     */
    public void testTaskTypeDAO() {
        assertNotNull("fail to create the TaskTypeDAO instance.", taskTypeDAO);
    }

    /**
     * Test method create(DataObject dataObject, String user).
     * @throws Exception if any problem occurs.
     */
    public void testCreate() throws Exception {
        taskTypeDAO.create(taskType, createUser);

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TaskTypes;");

            /*
             * there is one record.
             */
            assertTrue("record was not properly created in the database", resultSet.next());

            /*
             * Verify the content in database is correct.
             */
            int id = resultSet.getInt("TaskTypesID");
            assertTrue("record was not properly created in the database", id >= 0 && id < 1000);
            assertEquals("record was not properly created in database", desc, resultSet
                    .getString("Description"));
            assertEquals("record was not properly created in the database", createUser, resultSet
                    .getString("CreationUser"));
            assertEquals("record was not properly created in the database", createUser, resultSet
                    .getString("ModificationUser"));
            AccuracyTestHelper.assertEquals("record was not properly created in the database",
                    taskType.getCreationDate(), new Date(resultSet.getDate("CreationDate")
                            .getTime()));

            AccuracyTestHelper.assertEquals("record was not properly created in the database",
                    taskType.getModificationDate(), new Date(resultSet.getDate("ModificationDate")
                            .getTime()));

            /*
             * The TaskType will also be changed
             */
            assertEquals("taskType was not properly updated", createUser, taskType
                    .getCreationUser());
            assertEquals("taskType was not properly updated", createUser, taskType
                    .getModificationUser());
            assertNotNull("taskType was not properly updated", taskType.getCreationDate());
            assertNotNull("taskType was not properly updated", taskType.getModificationDate());
            assertEquals("the create user should be equals to modification user", taskType
                    .getCreationUser(), taskType.getModificationUser());
            assertEquals("the create date should be equals to modification date", taskType
                    .getCreationDate(), taskType.getModificationDate());

            // Verify no more record
            assertFalse("there should be only one record", resultSet.next());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * Test method update(DataObject dataObject, String user).
     * @throws Exception if any problem occurs.
     */
    public void testUpdate() throws Exception {
        taskTypeDAO.create(taskType, createUser);
        // update the description field
        taskType.setDescription(updatedDesc);
        // update the record
        taskTypeDAO.update(taskType, modifyUser);
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TaskTypes;");

            /*
             * there is one record.
             */
            assertTrue("record was not properly created in the database", resultSet.next());

            /*
             * Verify the content in database is correct.
             */
            int id = resultSet.getInt("TaskTypesID");
            assertTrue("record was not properly updated in the database", id >= 0 && id < 1000);
            assertEquals("record was not properly updated in database", updatedDesc, resultSet
                    .getString("Description"));
            assertEquals("record was not properly updated in the database", createUser, resultSet
                    .getString("CreationUser"));
            assertEquals("record was not properly updated in the database", modifyUser, resultSet
                    .getString("ModificationUser"));
            AccuracyTestHelper.assertEquals("record was not properly updated in the database",
                    taskType.getCreationDate(), new Date(resultSet.getDate("CreationDate")
                            .getTime()));
            AccuracyTestHelper.assertEquals("record was not properly updated in the database",
                    taskType.getModificationDate(), new Date(resultSet.getDate("ModificationDate")
                            .getTime()));

            /*
             * The TaskType will also be changed
             */
            assertEquals("taskType was not properly updated", createUser, taskType
                    .getCreationUser());
            assertEquals("taskType was not properly updated", modifyUser, taskType
                    .getModificationUser());
            assertNotNull("taskType was not properly updated", taskType.getCreationDate());
            assertNotNull("taskType was not properly updated", taskType.getModificationDate());
            assertFalse("the create user should be equals to modification user", taskType
                    .getCreationUser().equals(taskType.getModificationUser()));
            assertFalse("the create date should be equals to modification date", taskType
                    .getCreationDate().equals(taskType.getModificationDate()));

            // Verify no more record
            assertFalse("there should be only one record", resultSet.next());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * Test method delete(long paimaryId).
     * @throws Exception if any problem occurs.
     */
    public void testDelete() throws Exception {
        // Create a record
        taskTypeDAO.create(taskType, createUser);

        // Delete the record
        taskTypeDAO.delete(taskType.getPrimaryId());

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM TaskTypes;");

            assertFalse("There should be no record.", resultSet.next());
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    /**
     * Test method get(long primaryId).
     * @throws Exception if any problem occurs.
     */
    public void testGet() throws Exception {
        taskTypeDAO.create(taskType, createUser);
        TaskType type = (TaskType) taskTypeDAO.get(taskType.getPrimaryId());
        AccuracyTestHelper.assertEquals("the returned TaskType is not equal to the original",
                (BaseDataObject) taskType, (BaseDataObject) type);
    }

    /**
     * Test method getList(String whereClause).
     * @throws Exception if any problem occurs.
     */
    public void testGetList() throws Exception {
        TaskType taskTypes[] = new TaskType[3];
        for (int i = 0; i < 3; i++) {
            taskTypes[i] = new TaskType();
            taskTypes[i].setDescription(desc);
            if (i == 0) {
                taskTypeDAO.create(taskTypes[i], createUser);
            } else {
                taskTypeDAO.create(taskTypes[i], modifyUser);
            }
        }
        List list = taskTypeDAO.getList("CreationUser='" + modifyUser + "'");
        assertEquals("there should be 2 records", 2, list.size());
        list = taskTypeDAO.getList("CreationUser='" + createUser + "'");
        assertEquals("there should be 1 records", 1, list.size());
        list = taskTypeDAO.getList("   ");
        assertEquals("there should be 3 records", 3, list.size());
    }

    /**
     * <p>
     * Test method setConnection(Connection).
     * </p>
     * <p>
     * After the method is called, the useOwnConnection field should be set false.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testSetConnection() throws Exception {
        taskTypeDAO.setConnection(connection);
        // get the connection field through reflect.
        Field connField = BaseDAO.class.getDeclaredField("connection");
        connField.setAccessible(true);
        assertEquals("the connection field is not properly set", connection, connField
                .get(taskTypeDAO));
        // get the useOwnConnection field through reflect.
        Field useOwnConnectionField = BaseDAO.class.getDeclaredField("useOwnConnection");
        useOwnConnectionField.setAccessible(true);
        // the useOwnConnection field should be false
        assertFalse("the useOwnConnection field should be false", useOwnConnectionField
                .getBoolean(taskTypeDAO));
    }

    /**
     * Test method getConnection().
     * @throws Exception if any problem occurs.
     */
    public void testGetConnection() throws Exception {
        assertNull("the default connection value should be null.", taskTypeDAO.getConnection());
    }

    /**
     * <p>
     * Test method removeConnection().
     * </p>
     * <p>
     * After the method is called, the useOwnConnection field should be true.
     * </p>
     * @throws Exception if any problem occurs.
     */
    public void testRemoveConnection() throws Exception {
        taskTypeDAO.setConnection(connection);
        taskTypeDAO.removeConnection();
        assertNull("the connection field should be null after the removeConnection method called",
                taskTypeDAO.getConnection());

        // get the useOwnConnection field
        Field useOwnConnectionField = BaseDAO.class.getDeclaredField("useOwnConnection");
        useOwnConnectionField.setAccessible(true);
        // the useOwnConnection field should be true.
        assertTrue(
                "the useOwnConnection field should be true after removeConnection method called",
                useOwnConnectionField.getBoolean(taskTypeDAO));
    }
}
