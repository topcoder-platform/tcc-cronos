/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.util.config.ConfigManager;


/**
 * <p>
 * Unit test cases for TimeEntryDAO.
 * </p>
 *
 * <p>
 * This class will test all the implemented abstract helper methods. The five CRUD(Create, Read, Update, and Delete)
 * database related operation methods will also be test here at last.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TimeEntryDAOUnitTest extends TestCase {
    /**
     * <p>
     * The ConfigManager instance to load the config file.
     * </p>
     */
    private static ConfigManager cm = ConfigManager.getInstance();

    /**
     * <p>
     * Represents the description that this dataObject holds.
     * </p>
     */
    private static final String DESCRIPTION = "foo";

    /**
     * <p>
     * Represents the user that created this record.
     * </p>
     */
    private static final String CREATION_USER = "TCSDEVELOPER";

    /**
     * <p>
     * Represents the user that last modified this record.
     * </p>
     */
    private static final String MODIFICATION_USER = "TCSDESIGNER";

    /**
     * <p>
     * Represents the date that created this record.
     * </p>
     */
    private static final Date CREATION_DATE = new Date();

    /**
     * <p>
     * Represents the date that last modified this record.
     * </p>
     */
    private static final Date MODIFICATION_DATE = new Date();

    /**
     * <p>
     * The name of the Connection to fetch from the DBConnectionFactory.
     * </p>
     */
    private static final String CONNAME = "informix";

    /**
     * <p>
     * Namespace to be used in the DBConnection Factory component to load the configuration.
     * </p>
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.myconfig";

    /**
     * <p>
     * Namespace to be used in the IDGeneration component to load the configuration.
     * </p>
     */
    private static final String IDGENERATION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * The TimeEntryDAO instance for testing.
     * </p>
     */
    private BaseDAO timeEntryDAO = null;

    /**
     * <p>
     * TimeEntry instance for testing.
     * </p>
     */
    private DataObject timeEntry = new TimeEntry();

    /**
     * <p>
     * TimeEntry instance for testing. This will be regarded as an illagal argment for some verify methods.
     * </p>
     */
    private DataObject taskType = new TaskType();

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(TimeEntryDAOUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        this.timeEntryDAO = new TimeEntryDAO(CONNAME, NAMESPACE);
        ((TimeEntry) timeEntry).setDescription(DESCRIPTION);
        ((TimeEntry) timeEntry).setDate(CREATION_DATE);

        // load the configuration
        if (cm.existsNamespace(NAMESPACE)) {
            cm.removeNamespace(NAMESPACE);
        }

        if (cm.existsNamespace(IDGENERATION_NAMESPACE)) {
            cm.removeNamespace(IDGENERATION_NAMESPACE);
        }

        cm.add(NAMESPACE, "SampleXML.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
        cm.add(IDGENERATION_NAMESPACE, "SampleXML.xml", ConfigManager.CONFIG_MULTIPLE_XML_FORMAT);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // delete all the records in TimeEntrys table
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.clearTimeEntries(conn);
            UnitTestHelper.clearTaskTypes(conn);
            UnitTestHelper.clearTimeStatuses(conn);
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }

        // remove the configuration
        if (cm.existsNamespace(NAMESPACE)) {
            cm.existsNamespace(NAMESPACE);
        }

        if (cm.existsNamespace(IDGENERATION_NAMESPACE)) {
            cm.removeNamespace(IDGENERATION_NAMESPACE);
        }

    }

    /**
     * <p>
     * Tests the constructor - TimeEntryDAO(String connName, String namespace).
     * </p>
     */
    public void testTimeEntryDAO1() {
        assertNotNull("The constructor does not work well", this.timeEntryDAO);
    }

    /**
     * <p>
     * Tests the constructor - TimeEntryDAO(String connName, String namespace). With null connName,
     * NullPointerException should be thrown
     * </p>
     */
    public void testTimeEntryDAO2() {
        try {
            new TimeEntryDAO(null, NAMESPACE);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TimeEntryDAO(String connName, String namespace). With null namspace,
     * NullPointerException should be thrown
     * </p>
     */
    public void testTimeEntryDAO3() {
        try {
            new TimeEntryDAO(CONNAME, null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TimeEntryDAO(String connName, String namespace). With empty connName,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testTimeEntryDAO4() {
        try {
            new TimeEntryDAO(" ", NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TimeEntryDAO(String connName, String namespace). With empty namspace,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testTimeEntryDAO5() {
        try {
            new TimeEntryDAO(CONNAME, " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. Since it is a legal argument, no Exception
     * should be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testVerifyCreateDataObject1() throws Exception {
        timeEntryDAO.verifyCreateDataObject(timeEntry);
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject2() {
        try {
            timeEntryDAO.verifyCreateDataObject(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With dataObject which is not instance of
     * TimeEntry, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject3() {
        try {
            timeEntryDAO.verifyCreateDataObject(taskType);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With null DESCRIPTION field in dataObject,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject4() {
        try {
            ((TimeEntry) timeEntry).setDescription(null);
            timeEntryDAO.verifyCreateDataObject(timeEntry);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the getCreateSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetCreateSqlString() throws Exception {
        assertEquals("the create Sql string is not properly got", TimeEntryDAO.SQL_CREATE_STATEMENT,
            timeEntryDAO.getCreateSqlString());
    }

    /**
     * <p>
     * Tests the fillCreatePreparedStatement(PreparedStatement statement, DataObject dataObject, String creationUser,
     * Date creationDate) method. No Exception should be thrown here.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testFillCreatePreparedStatement() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            statement = conn.prepareStatement(TimeEntryDAO.SQL_CREATE_STATEMENT);
            timeEntryDAO.fillCreatePreparedStatement(statement, timeEntry, CREATION_USER, CREATION_DATE);
        } finally {
            UnitTestHelper.closeResources(null, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the setCreationParametersInDataObject(DataObject dataObject, String creationUser, Date creationDate)
     * method. No Exception should be thrown here.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSetCreationParametersInDataObject()
        throws Exception {
        timeEntryDAO.setCreationParametersInDataObject(timeEntry, CREATION_USER, CREATION_DATE);
        assertEquals("creationUser field is not properly set", CREATION_USER,
            ((TimeEntry) timeEntry).getCreationUser());
        assertEquals("creationDate field is not properly set", CREATION_DATE,
            ((TimeEntry) timeEntry).getCreationDate());
        assertEquals("modificationUser field incorrectly set", CREATION_USER,
            ((TimeEntry) timeEntry).getModificationUser());
        assertEquals("modificationDate field incorrectly set", CREATION_DATE,
            ((TimeEntry) timeEntry).getModificationDate());
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. Since it is a legal argument, no Exception
     * should be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testVerifyUpdateDataObject1() throws Exception {
        timeEntryDAO.verifyUpdateDataObject(timeEntry);
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject2() {
        try {
            timeEntryDAO.verifyUpdateDataObject(null);
            fail("NullPointerException should be thrown");
        } catch (NullPointerException npe) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With dataObject which is not instance of
     * TimeEntry, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject3() {
        try {
            timeEntryDAO.verifyUpdateDataObject(taskType);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With null DESCRIPTION field in dataObject,
     * IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject4() {
        try {
            ((TimeEntry) timeEntry).setDescription(null);
            timeEntryDAO.verifyUpdateDataObject(timeEntry);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException iae) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the getUpdateSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetUpdateSqlString() throws Exception {
        assertEquals("the update Sql string is not properly got", TimeEntryDAO.SQL_UPDATE_STATEMENT,
            timeEntryDAO.getUpdateSqlString());
    }

    /**
     * <p>
     * Tests the fillUpdatePreparedStatement(PreparedStatement statement, DataObject dataObject, String
     * modificationUser, Date modificationDate) method. No Exception should be thrown here.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testFillUpdatePreparedStatement() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            PreparedStatement statement = conn.prepareStatement(TimeEntryDAO.SQL_UPDATE_STATEMENT);
            timeEntryDAO.fillUpdatePreparedStatement(statement, timeEntry, MODIFICATION_USER, MODIFICATION_DATE);
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the setModificationParametersInDataObject(DataObject dataObject, String modificationUser, Date
     * modificationDate) method. No Exception should be thrown here.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSetModificationParametersInDataObject()
        throws Exception {
        timeEntryDAO.setModificationParametersInDataObject(timeEntry, MODIFICATION_USER, MODIFICATION_DATE);

        // set the creation user and date and check these value are changed after this method
        ((TimeEntry) timeEntry).setCreationUser(CREATION_USER);
        ((TimeEntry) timeEntry).setCreationDate(CREATION_DATE);

        // check the creation user and date
        assertEquals("creationUser field should not be set in this method", CREATION_USER,
            ((TimeEntry) timeEntry).getCreationUser());
        assertEquals("creationDate field should not be set in this method", CREATION_DATE,
            ((TimeEntry) timeEntry).getCreationDate());

        // check the modification user and date
        assertEquals("modificationUser field incorrectly set", MODIFICATION_USER,
            ((TimeEntry) timeEntry).getModificationUser());
        assertEquals("modificationDate field incorrectly set", MODIFICATION_DATE,
            ((TimeEntry) timeEntry).getModificationDate());
    }

    /**
     * <p>
     * Tests the getDeleteSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDeleteSqlString() throws Exception {
        assertEquals("the delete Sql string is not properly got", TimeEntryDAO.SQL_DELETE_STATEMENT,
            timeEntryDAO.getDeleteSqlString());
    }

    /**
     * <p>
     * Tests the getReadSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetReadSqlString() throws Exception {
        assertEquals("the read Sql string is not properly got", TimeEntryDAO.SQL_GET_STATEMENT,
            timeEntryDAO.getReadSqlString());
    }

    /**
     * <p>
     * Tests the processReadResultSet(ResultSet resultSet) method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testProcessReadResultSet1() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            // insert a record to the database
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // assert this method returns a valid value
            statement = conn.prepareStatement(TimeEntryDAO.SQL_GET_STATEMENT);
            statement.setInt(1, myTimeEntry.getPrimaryId());

            resultSet = statement.executeQuery();
            assertTrue("not correctly record returned from the TimeEntrys table",
                judgeEquals(myTimeEntry, (TimeEntry) timeEntryDAO.processReadResultSet(resultSet)));
        } finally {
            UnitTestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the processReadResultSet(ResultSet resultSet) method. With null resultSet, DAOActionException should be
     * thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testProcessReadResultSet2() throws Exception {
        try {
            timeEntryDAO.processReadResultSet(null);
            fail("DAOActionException should be thrown");
        } catch (DAOActionException dae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the processReadResultSet(ResultSet resultSet) method. With no record in the result, null should be return.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testProcessReadResultSet3() throws Exception {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            statement = conn.prepareStatement(TimeEntryDAO.SQL_GET_STATEMENT);
            statement.setInt(1, 1);

            resultSet = statement.executeQuery();

            // assert no record retrieved
            assertNull("not correctly record returned from the TimeEntries table",
                timeEntryDAO.processReadResultSet(resultSet));
        } finally {
            UnitTestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the getReadListSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetReadListSqlString() throws Exception {
        assertEquals("the read list Sql string is not properly got", TimeEntryDAO.SQL_GET_LIST_STATEMENT,
            timeEntryDAO.getReadListSqlString());
    }

    /**
     * <p>
     * Tests the processReadListResultSet(ResultSet resultSet) method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testProcessReadListResultSet1() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            List myTimeEntrys = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
                myTimeEntry.setPrimaryId(i);
                UnitTestHelper.insertTimeEntries(myTimeEntry, conn);
                myTimeEntrys.add(myTimeEntry);
            }

            // assert this method returns the valid value
            statement = conn.createStatement();
            resultSet = statement.executeQuery(TimeEntryDAO.SQL_GET_LIST_STATEMENT);

            List returnTimeEntrys = timeEntryDAO.processReadListResultSet(resultSet);

            assertEquals("not correctly record returned from the TimeEntrys table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the TimeEntrys table",
                    judgeEquals((TimeEntry) returnTimeEntrys.get(i), (TimeEntry) myTimeEntrys.get(i)));
            }
        } finally {
            UnitTestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the processReadListResultSet(ResultSet resultSet) method. With null resultSet, DAOActionException should
     * be thrown.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testProcessReadListResultSet2() throws Exception {
        try {
            timeEntryDAO.processReadListResultSet(null);
            fail("DAOActionException should be thrown");
        } catch (DAOActionException dae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the processReadListResultSet(ResultSet resultSet) method. With no record in the result, null should be
     * return.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testProcessReadListResultSet3() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // get the record
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            statement = conn.createStatement();
            resultSet = statement.executeQuery(TimeEntryDAO.SQL_GET_LIST_STATEMENT);

            // assert no record retrieved
            List list = timeEntryDAO.processReadListResultSet(resultSet);
            assertEquals("not correctly record returned from the TimeEntries table", 0, list.size());
        } finally {
            UnitTestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the create(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testCreate() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            // Before insert the timeEntry into timeEntries table, the timeStatus with primaryID as
            // timeEntry.timeStatusesID and the taskType with primaryID as timeEntry.taskTypesID should be
            // inserted to the database first. Otherwise "Exception: Missing key in referenced table
            // forreferential constraint" will be thrown
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            TimeEntry myTimeEntry = new TimeEntry();
            myTimeEntry.setDescription(DESCRIPTION);
            myTimeEntry.setDate(CREATION_DATE);
            timeEntryDAO.create(myTimeEntry, CREATION_USER);

            /*
             * get the connection and fetch the record from the database.
             * The record which has just insert into the datebase should be:
             * primaryID: an id generated by IDGenerator
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: a new Date
             * modificationUser: CREATION_USER
             * modificationDate: a new Date
             */

            List returnTimeEntrys = UnitTestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeEntrys.size());

            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTimeEntry.getCreationUser());
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTimeEntry.getModificationUser());
            assertNotNull("record was not properly created in the database", returnTimeEntry.getCreationDate());
            assertNotNull("record was not properly created in the database", returnTimeEntry.getModificationDate());

            /*
             * The myTimeEntry will also be changed
             */
            assertEquals("myTimeEntry was not properly updated", CREATION_USER, myTimeEntry.getCreationUser());
            assertEquals("myTimeEntry was not properly updated", CREATION_USER, myTimeEntry.getModificationUser());
            assertNotNull("myTimeEntry was not properly updated", myTimeEntry.getCreationDate());
            assertNotNull("myTimeEntry was not properly updated", myTimeEntry.getModificationDate());
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // update the record and update the billable field to true
            myTimeEntry.setBillable(true);
            timeEntryDAO.update(myTimeEntry, MODIFICATION_USER);

            /*
             * Fetch the record from the database and this record should be:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: true
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: MODIFICATION_USER
             * modificationDate: a new date
             */
            List returnTimeEntrys = UnitTestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly updated in the database", 1, returnTimeEntrys.size());

            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeEntry.getCreationUser());
            assertEquals("record was not properly updated in the database", MODIFICATION_USER,
                returnTimeEntry.getModificationUser());

            assertFalse("record was not properly updated in the database",
                MODIFICATION_DATE.equals(returnTimeEntry.getModificationDate()));
            assertTrue("record was not properly updated in the database", returnTimeEntry.isBillable());

            /*
             * The myTimeEntry will also be updated
             */
            assertEquals("myTimeEntry was not properly updated", CREATION_USER, myTimeEntry.getCreationUser());
            assertEquals("myTimeEntry was not properly updated", MODIFICATION_USER, myTimeEntry.getModificationUser());
            assertTrue("myTimeEntry was not properly updated", CREATION_DATE.equals(myTimeEntry.getCreationDate()));
            assertFalse("myTimeEntry was not properly updated",
                MODIFICATION_DATE.equals(myTimeEntry.getModificationDate()));
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user).
     * With not existed dataObject.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate2() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // update the record and update the billable field to true
            myTimeEntry.setBillable(true);
            myTimeEntry.setPrimaryId(10);
            timeEntryDAO.update(myTimeEntry, MODIFICATION_USER);

            /*
             * for the myTimeEntry does not exist in the database, the record in the database will not change.
             * But the myTimeEntry self will be changed
             */
            List returnTimeEntrys = UnitTestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly updated in the database", 1, returnTimeEntrys.size());

            TimeEntry returnTimeEntry = (TimeEntry) returnTimeEntrys.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeEntry.getCreationUser());
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeEntry.getModificationUser());

            assertFalse("record was not properly updated in the database", returnTimeEntry.isBillable());

            /*
             * The myTimeEntry will be updated
             */
            assertEquals("myTimeEntry was not properly updated", CREATION_USER, myTimeEntry.getCreationUser());
            assertEquals("myTimeEntry was not properly updated", MODIFICATION_USER, myTimeEntry.getModificationUser());
            assertTrue("myTimeEntry was not properly updated", CREATION_DATE.equals(myTimeEntry.getCreationDate()));
            assertFalse("myTimeEntry was not properly updated",
                MODIFICATION_DATE.equals(myTimeEntry.getModificationDate()));
            assertTrue("myTimeEntry was not properly updated", myTimeEntry.isBillable());
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the delete(int primaryId).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testDelete1() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // delete the record
            timeEntryDAO.delete(0);

            // assert there is no records in the database now
            List returnTimeEntrys = UnitTestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly deleted in the database", 0, returnTimeEntrys.size());
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the delete(int primaryId).
     * With not existed primaryId
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testDelete2() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // delete the record
            timeEntryDAO.delete(1);

            // assert there are still records in the database now
            List returnTimeEntrys = UnitTestHelper.selectTimeEntries(conn);
            assertEquals("record was not properly deleted in the database", 1, returnTimeEntrys.size());
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the get(int primaryId).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGet1() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // get the record
            TimeEntry returnTimeEntry = (TimeEntry) timeEntryDAO.get(0);

            // assert the return value is the exactly one which has just inserted into the database
            assertTrue("record was not properly got", judgeEquals(returnTimeEntry, myTimeEntry));
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the get(int primaryId).
     * With not existed primaryId.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGet2() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * taskTypesID: default: 0
             * timeStatusesID: default: 0
             * decription: DESCRIPTION
             * entryDate: CREATION_DATE
             * hours: default: 0.0F
             * billable: default: false
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
            UnitTestHelper.insertTimeEntries(myTimeEntry, conn);

            // get the record
            TimeEntry returnTimeEntry = (TimeEntry) timeEntryDAO.get(1);

            // assert the return value is null
            assertNull("record was not properly got", returnTimeEntry);
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList1() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database in manual
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            List myTimeEntrys = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
                myTimeEntry.setPrimaryId(i);
                UnitTestHelper.insertTimeEntries(myTimeEntry, conn);
                myTimeEntrys.add(myTimeEntry);
            }

            // assert records properly got from database
            List returnTimeEntrys = timeEntryDAO.getList(null);

            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the TimeEntries table",
                    judgeEquals((TimeEntry) returnTimeEntrys.get(i), (TimeEntry) myTimeEntrys.get(i)));
            }
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause).
     * With whereClause = "CreationUser = \'ivern\'"
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList2() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database in manual
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            List myTimeEntrys = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
                myTimeEntry.setPrimaryId(i);
                UnitTestHelper.insertTimeEntries(myTimeEntry, conn);
                myTimeEntrys.add(myTimeEntry);
            }

            // assert records properly got from database
            List returnTimeEntrys = timeEntryDAO.getList("CreationUser = \'ivern\'");

            assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause).
     * With whereClause = "CreationUser = CREATIONUSER"
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList3() throws Exception {
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database in manual
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            List myTimeEntrys = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeEntry myTimeEntry = (TimeEntry) getTimeEntry();
                myTimeEntry.setPrimaryId(i);
                UnitTestHelper.insertTimeEntries(myTimeEntry, conn);
                myTimeEntrys.add(myTimeEntry);
            }

            // assert records properly got from database
            timeEntryDAO.delete(0);
            String whereCause = "CreationUser=\'" + CREATION_USER + "\'";
            List returnTimeEntrys = timeEntryDAO.getList(whereCause);

            assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertTrue("not correctly record returned from the TimeEntries table",
                    judgeEquals((TimeEntry) returnTimeEntrys.get(i), (TimeEntry) myTimeEntrys.get(i + 1)));
            }
        } finally {
            UnitTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause).
     * With incorrect whereClause
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList4() throws Exception {
        try {
            timeEntryDAO.getList("CreationUser");
            fail("DAOActionException should be thrown here");
        } catch (DAOActionException e) {
            // good
        }
    }

    /**
     * <p>
     * return a TimeEntry instance for testing.
     * </p>
     *
     * @return a TimeEntry instance for testing.
     */
    private DataObject getTimeEntry() {
        TimeEntry myTimeEntry = new TimeEntry();

        myTimeEntry.setDescription(DESCRIPTION);
        myTimeEntry.setCreationUser(CREATION_USER);
        myTimeEntry.setCreationDate(CREATION_DATE);
        myTimeEntry.setModificationUser(CREATION_USER);
        myTimeEntry.setModificationDate(CREATION_DATE);
        myTimeEntry.setDate(CREATION_DATE);

        return myTimeEntry;
    }

    /**
     * <p>
     * return a TaskType instance for testing.
     * </p>
     *
     * @return a TaskType instance for testing.
     */
    private DataObject getTaskType() {
        TaskType myTaskType = new TaskType();

        myTaskType.setDescription(DESCRIPTION);
        myTaskType.setCreationUser(CREATION_USER);
        myTaskType.setCreationDate(CREATION_DATE);
        myTaskType.setModificationUser(CREATION_USER);
        myTaskType.setModificationDate(CREATION_DATE);

        return myTaskType;
    }

    /**
     * <p>
     * return a TimeStatus instance for testing.
     * </p>
     *
     * @return a TimeStatus instance for testing.
     */
    private DataObject getTimeStatus() {
        TimeStatus myTimeStatus = new TimeStatus();

        myTimeStatus.setDescription(DESCRIPTION);
        myTimeStatus.setCreationUser(CREATION_USER);
        myTimeStatus.setCreationDate(CREATION_DATE);
        myTimeStatus.setModificationUser(CREATION_USER);
        myTimeStatus.setModificationDate(CREATION_DATE);

        return myTimeStatus;
    }

    /**
     * <p>
     * judge whether the two TimeEntry are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param timeEntry1 the first TimeEntry
     * @param timeEntry2 the second TimeEntry
     *
     * @return whether the two TimeEntry are equals
     */
    private boolean judgeEquals(TimeEntry timeEntry1, TimeEntry timeEntry2) {
        if (timeEntry1.getPrimaryId() != timeEntry2.getPrimaryId()) {
            return false;
        }

        if (timeEntry1.getTaskTypeId() != timeEntry2.getTaskTypeId()) {
            return false;
        }

        if (timeEntry1.getTimeStatusId() != timeEntry2.getTimeStatusId()) {
            return false;
        }

        long date1 = UnitTestHelper.convertDate(timeEntry1.getDate());
        long date2 = UnitTestHelper.convertDate(timeEntry2.getDate());
        if (date1 != date2) {
            return false;
        }

        if (timeEntry1.getHours() != timeEntry2.getHours()) {
            return false;
        }

        if (timeEntry1.isBillable() != timeEntry2.isBillable()) {
            return false;
        }

        if (!timeEntry1.getDescription().equals(timeEntry2.getDescription())) {
            return false;
        }

        if (!timeEntry1.getCreationUser().equals(timeEntry2.getCreationUser())) {
            return false;
        }

        long creationDate1 = UnitTestHelper.convertDate(timeEntry1.getCreationDate());
        long creationDate2 = UnitTestHelper.convertDate(timeEntry2.getCreationDate());
        if (creationDate1 != creationDate2) {
            return false;
        }

        if (!timeEntry1.getModificationUser().equals(timeEntry2.getModificationUser())) {
            return false;
        }

        long modificationDate1 = UnitTestHelper.convertDate(timeEntry1.getModificationDate());
        long modificationDate2 = UnitTestHelper.convertDate(timeEntry2.getModificationDate());
        if (modificationDate1 != modificationDate2) {
            return false;
        }

        return true;
    }
}
