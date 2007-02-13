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
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test for <code>timeStatusDAO</code>.
 * @author fuyun
 * @version 1.0
 */
public class TimeStatusDAOAccuracyTest extends TestCase {

    /**
     * <p>
     * The <code>timeStatusDAO</code> instance used for test.
     * </p>
     */
    private TimeStatusDAO timeStatusDAO;

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
     * the description for <code>TimeStatus</code>.
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
     * the <code>TimeStatus</code> instance used for test.
     * </p>
     */
    private TimeStatus timeStatus;

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

        timeStatusDAO = new TimeStatusDAO(connName, namespace);

        timeStatus = new TimeStatus();

        timeStatus.setDescription(desc);

        factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connection = factory.createConnection();

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM id_sequences WHERE name = '"
                    + TimeStatus.class.getName() + "';");
            statement
                    .executeUpdate("INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('"
                            + TimeStatus.class.getName() + "', 0, 1000);");
            statement.executeUpdate("DELETE FROM TimeStatuses;");
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
  //          statement.executeUpdate("DELETE FROM id_sequences WHERE name = '"
  //                  + TimeStatus.class.getName() + "';");
            statement.executeUpdate("DELETE FROM TimeStatuses;");
        } finally {
            statement.close();
            connection.close();
        }
    }

    /**
     * Test constructor timeStatusDAO(String connName, String namespace).
     */
    public void testTaskTypeDAO() {
        assertNotNull("fail to create the timeStatusDAO instance.", timeStatusDAO);
    }

    /**
     * Test method create(DataObject dataObject, String user).
     * @throws Exception if any problem occurs.
     */
    public void testCreate() throws Exception {
        timeStatusDAO.create(timeStatus, createUser);

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TimeStatuses;");

            /*
             * there is one record.
             */
            assertTrue("record was not properly created in the database", resultSet.next());

            /*
             * Verify the content in database is correct.
             */
            int id = resultSet.getInt("TimeStatusesID");
            assertTrue("record was not properly created in the database", id >= 0 && id < 1000);
            assertEquals("record was not properly created in database", desc, resultSet
                    .getString("Description"));
            assertEquals("record was not properly created in the database", createUser, resultSet
                    .getString("CreationUser"));
            assertEquals("record was not properly created in the database", createUser, resultSet
                    .getString("ModificationUser"));
            AccuracyTestHelper.assertEquals("record was not properly created in the database", timeStatus
                    .getCreationDate(), new Date(resultSet.getDate("CreationDate").getTime()));
            AccuracyTestHelper.assertEquals("record was not properly created in the database", timeStatus
                    .getModificationDate(), new Date(resultSet.getDate("ModificationDate").getTime()));

            /*
             * The TimeStatus will also be changed
             */
            assertEquals("TimeStatus was not properly updated", createUser, timeStatus
                    .getCreationUser());
            assertEquals("TimeStatus was not properly updated", createUser, timeStatus
                    .getModificationUser());
            assertNotNull("TimeStatus was not properly updated", timeStatus.getCreationDate());
            assertNotNull("TimeStatus was not properly updated", timeStatus.getModificationDate());
            assertEquals("the create user should be equals to modification user", timeStatus
                    .getCreationUser(), timeStatus.getModificationUser());
            assertEquals("the create date should be equals to modification date", timeStatus
                    .getCreationDate(), timeStatus.getModificationDate());

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
        timeStatusDAO.create(timeStatus, createUser);
        // update the description field
        timeStatus.setDescription(updatedDesc);
        // update the record
        timeStatusDAO.update(timeStatus, modifyUser);
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TimeStatuses;");

            /*
             * there is one record.
             */
            assertTrue("record was not properly created in the database", resultSet.next());

            /*
             * Verify the content in database is correct.
             */
            int id = resultSet.getInt("TimeStatusesID");
            assertTrue("record was not properly updated in the database", id >= 0 && id < 1000);
            assertEquals("record was not properly updated in database", updatedDesc, resultSet
                    .getString("Description"));
            assertEquals("record was not properly updated in the database", createUser, resultSet
                    .getString("CreationUser"));
            assertEquals("record was not properly updated in the database", modifyUser, resultSet
                    .getString("ModificationUser"));
            AccuracyTestHelper.assertEquals("record was not properly updated in the database", timeStatus
                    .getCreationDate(), new Date(resultSet.getDate("CreationDate").getTime()));
            AccuracyTestHelper.assertEquals("record was not properly updated in the database", timeStatus
                    .getModificationDate(), new Date(resultSet.getDate("ModificationDate").getTime()));

            /*
             * The TimeStatus will also be changed
             */
            assertEquals("TimeStatus was not properly updated", createUser, timeStatus
                    .getCreationUser());
            assertEquals("TimeStatus was not properly updated", modifyUser, timeStatus
                    .getModificationUser());
            assertNotNull("TimeStatus was not properly updated", timeStatus.getCreationDate());
            assertNotNull("TimeStatus was not properly updated", timeStatus.getModificationDate());
            assertFalse("the create user should be equals to modification user", timeStatus
                    .getCreationUser().equals(timeStatus.getModificationUser()));
            assertFalse("the create date should be equals to modification date", timeStatus
                    .getCreationDate().equals(timeStatus.getModificationDate()));

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
        timeStatusDAO.create(timeStatus, createUser);

        // Delete the record
        timeStatusDAO.delete(timeStatus.getPrimaryId());

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM TimeStatuses;");

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
        timeStatusDAO.create(timeStatus, createUser);
        TimeStatus status = (TimeStatus) timeStatusDAO.get(timeStatus.getPrimaryId());
        AccuracyTestHelper.assertEquals("the returned TimeStatus is not equal to the original",
                (BaseDataObject) timeStatus, (BaseDataObject) status);
    }

    /**
     * Test method getList(String whereClause).
     * @throws Exception if any problem occurs.
     */
    public void testGetList() throws Exception {
        TimeStatus timeStatuses[] = new TimeStatus[3];
        for (int i = 0; i < 3; i++) {
            timeStatuses[i] = new TimeStatus();
            timeStatuses[i].setDescription(desc);
            if (i == 0) {
                timeStatusDAO.create(timeStatuses[i], createUser);
            } else {
                timeStatusDAO.create(timeStatuses[i], modifyUser);
            }
        }
        List list = timeStatusDAO.getList("CreationUser='" + modifyUser + "'");
        assertEquals("there should be 2 records", 2, list.size());
        list = timeStatusDAO.getList("CreationUser='" + createUser + "'");
        assertEquals("there should be 1 records", 1, list.size());
        list = timeStatusDAO.getList("   ");
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
        timeStatusDAO.setConnection(connection);
        // get the connection field through reflect.
        Field connField = BaseDAO.class.getDeclaredField("connection");
        connField.setAccessible(true);
        assertEquals("the connection field is not properly set", connection, connField.get(timeStatusDAO));
        // get the useOwnConnection field through reflect.
        Field useOwnConnectionField = BaseDAO.class.getDeclaredField("useOwnConnection");
        useOwnConnectionField.setAccessible(true);
        // the useOwnConnection field should be false
        assertFalse("the useOwnConnection field should be false", useOwnConnectionField
                .getBoolean(timeStatusDAO));
    }

    /**
     * Test method getConnection().
     * @throws Exception if any problem occurs.
     */
    public void testGetConnection() throws Exception {
        assertNull("the default connection value should be null.", timeStatusDAO.getConnection());
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
        timeStatusDAO.setConnection(connection);
        timeStatusDAO.removeConnection();
        assertNull("the connection field should be null after the removeConnection method called",
                timeStatusDAO.getConnection());

        // get the useOwnConnection field
        Field useOwnConnectionField = BaseDAO.class.getDeclaredField("useOwnConnection");
        useOwnConnectionField.setAccessible(true);
        // the useOwnConnection field should be true.
        assertTrue(
                "the useOwnConnection field should be true after removeConnection method called",
                useOwnConnectionField.getBoolean(timeStatusDAO));
    }
}
