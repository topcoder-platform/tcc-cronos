/*
 * Copyright (c) 2005, TopCoder, Inc. All rights reserved
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.entry.time.BaseDAO;
import com.topcoder.timetracker.entry.time.BaseDataObject;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test for <code>timeEntryDAO</code>.
 * @author fuyun
 * @version 1.0
 */
public class TimeEntryDAOAccuracyTest extends TestCase {

    /**
     * <p>
     * The <code>timeEntryDAO</code> instance used for test.
     * </p>
     */
    private TimeEntryDAO timeEntryDAO;

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
     * the description for <code>TimeEntry</code>.
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
     * the <code>TimeEntry</code> instance used for test.
     * </p>
     */
    private TimeEntry timeEntry;

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

        timeEntryDAO = new TimeEntryDAO(connName, namespace);

        timeEntry = new TimeEntry();

        timeEntry.setDescription(desc);
        timeEntry.setDate(AccuracyTestHelper.generateDate(2005, 1, 1, 1, 1, 1));
        timeEntry.setHours(1);
        timeEntry.setBillable(true);
        timeEntry.setTaskTypeId(1);
        timeEntry.setTimeStatusId(2);

        factory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        connection = factory.createConnection();

        Statement statement = connection.createStatement();

        try {
            statement.executeUpdate("DELETE FROM id_sequences WHERE name = '"
                    + TimeEntry.class.getName() + "';");
            statement
                    .executeUpdate("INSERT INTO id_sequences (name, next_block_start, block_size) VALUES ('"
                            + TimeEntry.class.getName() + "', 0, 1000);");
            statement.executeUpdate("DELETE FROM TimeEntries;");
            statement.executeUpdate("DELETE FROM TaskTypes;");
            statement.executeUpdate("DELETE FROM TimeStatuses;");
            PreparedStatement ps = connection.prepareStatement("INSERT INTO TaskTypes(TaskTypesID, Description, "
                    + "CreationUser, CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

            try {
                ps.setInt(1, 1);
                ps.setString(2, "test");
                ps.setString(3, "test");
                ps.setDate(4, new java.sql.Date(AccuracyTestHelper.generateDate(2005, 1, 1, 1, 1, 1).getTime()));
                ps.setString(5, "test");
                ps.setDate(6, new java.sql.Date(AccuracyTestHelper.generateDate(2005, 1, 1, 1, 1, 2).getTime()));
                ps.executeUpdate();
            } finally {
                ps.close();
            }

            ps = connection.prepareStatement("INSERT INTO TimeStatuses(TimeStatusesID, Description, CreationUser, "
                    + "CreationDate, ModificationUser, ModificationDate) VALUES (?,?,?,?,?,?)");

            try {
                ps.setInt(1, 2);
                ps.setString(2, "test");
                ps.setString(3, "test");
                ps.setDate(4, new java.sql.Date(AccuracyTestHelper.generateDate(2005, 1, 1, 1, 1, 1).getTime()));
                ps.setString(5, "test");
                ps.setDate(6, new java.sql.Date(AccuracyTestHelper.generateDate(2005, 1, 1, 1, 1, 2).getTime()));
                ps.executeUpdate();
            } finally {
                ps.close();
            }
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
          //  statement.executeUpdate("DELETE FROM id_sequences WHERE name = '" + TimeEntry.class.getName() + "';");
            statement.executeUpdate("DELETE FROM TimeEntries;");
            statement.executeUpdate("DELETE FROM TaskTypes;");
            statement.executeUpdate("DELETE FROM TimeStatuses;");
        } finally {
            statement.close();
            connection.close();
        }
    }

    /**
     * Test constructor timeEntryDAO(String connName, String namespace).
     */
    public void testTaskTypeDAO() {
        assertNotNull("fail to create the timeEntryDAO instance.", timeEntryDAO);
    }

    /**
     * Test method create(DataObject dataObject, String user).
     * @throws Exception if any problem occurs.
     */
    public void testCreate() throws Exception {
        timeEntryDAO.create(timeEntry, createUser);

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TimeEntries;");

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
            AccuracyTestHelper.assertEquals("record was not properly created in the database", timeEntry
                    .getCreationDate(), new Date(resultSet.getDate("CreationDate").getTime()));
            AccuracyTestHelper.assertEquals("record was not properly created in the database", timeEntry
                    .getModificationDate(), new Date(resultSet.getDate("ModificationDate").getTime()));

            /*
             * The TimeEntry will also be changed
             */
            assertEquals("TimeEntry was not properly updated", createUser, timeEntry
                    .getCreationUser());
            assertEquals("TimeEntry was not properly updated", createUser, timeEntry
                    .getModificationUser());
            assertNotNull("TimeEntry was not properly updated", timeEntry.getCreationDate());
            assertNotNull("TimeEntry was not properly updated", timeEntry.getModificationDate());
            assertEquals("the create user should be equals to modification user", timeEntry
                    .getCreationUser(), timeEntry.getModificationUser());
            assertEquals("the create date should be equals to modification date", timeEntry
                    .getCreationDate(), timeEntry.getModificationDate());

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
        timeEntryDAO.create(timeEntry, createUser);
        // update the description field
        timeEntry.setDescription(updatedDesc);
        // update the record
        timeEntryDAO.update(timeEntry, modifyUser);
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM TimeEntries;");

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
            AccuracyTestHelper.assertEquals("record was not properly updated in the database", timeEntry
                    .getCreationDate(), new Date(resultSet.getDate("CreationDate").getTime()));
            AccuracyTestHelper.assertEquals("record was not properly updated in the database", timeEntry
                    .getModificationDate(), new Date(resultSet.getDate("ModificationDate").getTime()));

            /*
             * The TimeEntry will also be changed
             */
            assertEquals("TimeEntry was not properly updated", createUser, timeEntry
                    .getCreationUser());
            assertEquals("TimeEntry was not properly updated", modifyUser, timeEntry
                    .getModificationUser());
            assertNotNull("TimeEntry was not properly updated", timeEntry.getCreationDate());
            assertNotNull("TimeEntry was not properly updated", timeEntry.getModificationDate());
            assertFalse("the create user should be equals to modification user", timeEntry
                    .getCreationUser().equals(timeEntry.getModificationUser()));
            assertFalse("the create date should be equals to modification date", timeEntry
                    .getCreationDate().equals(timeEntry.getModificationDate()));

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
        timeEntryDAO.create(timeEntry, createUser);

        // Delete the record
        timeEntryDAO.delete(timeEntry.getPrimaryId());

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM TimeEntries;");

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
        timeEntryDAO.create(timeEntry, createUser);
        TimeEntry entry = (TimeEntry) timeEntryDAO.get(timeEntry.getPrimaryId());
        AccuracyTestHelper.assertEquals("the returned TimeEntry is not equal to the original",
                (BaseDataObject) timeEntry, (BaseDataObject) entry);
    }

    /**
     * Test method getList(String whereClause).
     * @throws Exception if any problem occurs.
     */
    public void testGetList() throws Exception {
        TimeEntry timeEntries[] = new TimeEntry[3];
        for (int i = 0; i < 3; i++) {
            timeEntries[i] = new TimeEntry();
            timeEntries[i].setDescription(desc);
            timeEntries[i].setDate(AccuracyTestHelper.generateDate(2005, 1, 1, 1, 1, 1));
            timeEntries[i].setHours(1);
            timeEntries[i].setBillable(true);
            timeEntries[i].setTaskTypeId(1);
            timeEntries[i].setTimeStatusId(2);
            if (i == 0) {
                timeEntryDAO.create(timeEntries[i], createUser);
            } else {
                timeEntryDAO.create(timeEntries[i], modifyUser);
            }
        }
        List list = timeEntryDAO.getList("CreationUser='" + modifyUser + "'");
        assertEquals("there should be 2 records", 2, list.size());
        list = timeEntryDAO.getList("CreationUser='" + createUser + "'");
        assertEquals("there should be 1 records", 1, list.size());
        list = timeEntryDAO.getList("   ");
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
        timeEntryDAO.setConnection(connection);
        // get the connection field through reflect.
        Field connField = BaseDAO.class.getDeclaredField("connection");
        connField.setAccessible(true);
        assertEquals("the connection field is not properly set", connection, connField.get(timeEntryDAO));
        // get the useOwnConnection field through reflect.
        Field useOwnConnectionField = BaseDAO.class.getDeclaredField("useOwnConnection");
        useOwnConnectionField.setAccessible(true);
        // the useOwnConnection field should be false
        assertFalse("the useOwnConnection field should be false", useOwnConnectionField
                .getBoolean(timeEntryDAO));
    }

    /**
     * Test method getConnection().
     * @throws Exception if any problem occurs.
     */
    public void testGetConnection() throws Exception {
        assertNull("the default connection value should be null.", timeEntryDAO.getConnection());
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
        timeEntryDAO.setConnection(connection);
        timeEntryDAO.removeConnection();
        assertNull("the connection field should be null after the removeConnection method called",
                timeEntryDAO.getConnection());

        // get the useOwnConnection field
        Field useOwnConnectionField = BaseDAO.class.getDeclaredField("useOwnConnection");
        useOwnConnectionField.setAccessible(true);
        // the useOwnConnection field should be true.
        assertTrue(
                "the useOwnConnection field should be true after removeConnection method called",
                useOwnConnectionField.getBoolean(timeEntryDAO));
    }
}
