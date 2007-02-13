/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit test cases for TaskTypeDAO.
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
public class TaskTypeDAOUnitTest extends TestCase {
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
     * The TaskTypeDAO instance for testing.
     * </p>
     */
    private BaseDAO taskTypeDAO = null;

    /**
     * <p>
     * TaskType instance for testing.
     * </p>
     */
    private DataObject taskType = new TaskType();

    /**
     * <p>
     * TimeStatus instance for testing. This will be regarded as an illagal argment for some verify methods.
     * </p>
     */
    private DataObject timeStatus = new TimeStatus();

    /**
     * <p>
     * Creates a test suite of the tests contained in this class.
     * </p>
     *
     * @return a test suite of the tests contained in this class.
     */
    public static Test suite() {
        return new TestSuite(TaskTypeDAOUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        this.taskTypeDAO = new TaskTypeDAO(CONNAME, NAMESPACE);
        ((TaskType) taskType).setDescription(DESCRIPTION);

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
        // delete all the records in TaskTypes table
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.clearTaskTypes(conn);
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
     * Tests the constructor - TaskTypeDAO(String connName, String namespace).
     * </p>
     */
    public void testTaskTypeDAO1() {
        assertNotNull("The constructor does not work well", this.taskTypeDAO);
    }

    /**
     * <p>
     * Tests the constructor - TaskTypeDAO(String connName, String namespace). With null connName, NullPointerException
     * should be thrown
     * </p>
     */
    public void testTaskTypeDAO2() {
        try {
            new TaskTypeDAO(null, NAMESPACE);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TaskTypeDAO(String connName, String namespace). With null namspace, NullPointerException
     * should be thrown
     * </p>
     */
    public void testTaskTypeDAO3() {
        try {
            new TaskTypeDAO(CONNAME, null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TaskTypeDAO(String connName, String namespace). With empty connName,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testTaskTypeDAO4() {
        try {
            new TaskTypeDAO(" ", NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TaskTypeDAO(String connName, String namespace). With empty namspace,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testTaskTypeDAO5() {
        try {
            new TaskTypeDAO(CONNAME, " ");
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
        taskTypeDAO.verifyCreateDataObject(taskType);
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject2() {
        try {
            taskTypeDAO.verifyCreateDataObject(null);
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
     * TaskType, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject3() {
        try {
            taskTypeDAO.verifyCreateDataObject(timeStatus);
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
            ((TaskType) taskType).setDescription(null);
            taskTypeDAO.verifyCreateDataObject(taskType);
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
        assertEquals("the create Sql string is not properly got", TaskTypeDAO.SQL_CREATE_STATEMENT,
            taskTypeDAO.getCreateSqlString());
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
            conn =  UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            statement = conn.prepareStatement(TaskTypeDAO.SQL_CREATE_STATEMENT);
            taskTypeDAO.fillCreatePreparedStatement(statement, taskType, CREATION_USER, CREATION_DATE);
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
        taskTypeDAO.setCreationParametersInDataObject(taskType, CREATION_USER, CREATION_DATE);
        assertEquals("creationUser field is not properly set", CREATION_USER, ((TaskType) taskType).getCreationUser());
        assertEquals("creationDate field is not properly set", CREATION_DATE, ((TaskType) taskType).getCreationDate());
        assertEquals("modificationUser field incorrectly set", CREATION_USER,
            ((TaskType) taskType).getModificationUser());
        assertEquals("modificationDate field incorrectly set", CREATION_DATE,
            ((TaskType) taskType).getModificationDate());
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
        taskTypeDAO.verifyUpdateDataObject(taskType);
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject2() {
        try {
            taskTypeDAO.verifyUpdateDataObject(null);
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
     * TaskType, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject3() {
        try {
            taskTypeDAO.verifyUpdateDataObject(timeStatus);
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
            ((TaskType) taskType).setDescription(null);
            taskTypeDAO.verifyUpdateDataObject(taskType);
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
        assertEquals("the update Sql string is not properly got", TaskTypeDAO.SQL_UPDATE_STATEMENT,
            taskTypeDAO.getUpdateSqlString());
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
            conn =  UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            PreparedStatement statement = conn.prepareStatement(TaskTypeDAO.SQL_UPDATE_STATEMENT);
            taskTypeDAO.fillUpdatePreparedStatement(statement, taskType, MODIFICATION_USER, MODIFICATION_DATE);
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
        taskTypeDAO.setModificationParametersInDataObject(taskType, MODIFICATION_USER, MODIFICATION_DATE);

        // set the creation user and date and check these value are changed after this method
        ((TaskType) taskType).setCreationUser(CREATION_USER);
        ((TaskType) taskType).setCreationDate(CREATION_DATE);

        // check the creation user and date
        assertEquals("creationUser field should not be set in this method", CREATION_USER,
            ((TaskType) taskType).getCreationUser());
        assertEquals("creationDate field should not be set in this method", CREATION_DATE,
            ((TaskType) taskType).getCreationDate());

        // check the modification user and date
        assertEquals("modificationUser field incorrectly set", MODIFICATION_USER,
            ((TaskType) taskType).getModificationUser());
        assertEquals("modificationDate field incorrectly set", MODIFICATION_DATE,
            ((TaskType) taskType).getModificationDate());
    }

    /**
     * <p>
     * Tests the getDeleteSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDeleteSqlString() throws Exception {
        assertEquals("the delete Sql string is not properly got", TaskTypeDAO.SQL_DELETE_STATEMENT,
            taskTypeDAO.getDeleteSqlString());
    }

    /**
     * <p>
     * Tests the getReadSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetReadSqlString() throws Exception {
        assertEquals("the read Sql string is not properly got", TaskTypeDAO.SQL_GET_STATEMENT,
            taskTypeDAO.getReadSqlString());
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
            TaskType myTaskType = (TaskType) getTaskType();
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // assert this method returns a valid value
            statement = conn.prepareStatement(TaskTypeDAO.SQL_GET_STATEMENT);
            statement.setInt(1, myTaskType.getPrimaryId());

            resultSet = statement.executeQuery();
            assertTrue("not correctly record returned from the TaskTypes table",
                judgeEquals(myTaskType, (TaskType) taskTypeDAO.processReadResultSet(resultSet)));
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
            taskTypeDAO.processReadResultSet(null);
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

            statement = conn.prepareStatement(TaskTypeDAO.SQL_GET_STATEMENT);
            statement.setInt(1, 1);

            resultSet = statement.executeQuery();

            // assert no record retrieved
            assertNull("not correctly record returned from the TaskTypes table",
                taskTypeDAO.processReadResultSet(resultSet));
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
        assertEquals("the read list Sql string is not properly got", TaskTypeDAO.SQL_GET_LIST_STATEMENT,
            taskTypeDAO.getReadListSqlString());
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
            List myTaskTypes = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TaskType myTaskType = (TaskType) getTaskType();
                myTaskType.setPrimaryId(i);
                UnitTestHelper.insertTaskTypes(myTaskType, conn);
                myTaskTypes.add(myTaskType);
            }

            // assert this method returns the valid value
            statement = conn.createStatement();
            resultSet = statement.executeQuery(TaskTypeDAO.SQL_GET_LIST_STATEMENT);

            List returnTaskTypes = taskTypeDAO.processReadListResultSet(resultSet);

            assertEquals("not correctly record returned from the TaskTypes table", 10, returnTaskTypes.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the TaskTypes table",
                    judgeEquals((TaskType) returnTaskTypes.get(i), (TaskType) myTaskTypes.get(i)));
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
            taskTypeDAO.processReadListResultSet(null);
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
            resultSet = statement.executeQuery(TaskTypeDAO.SQL_GET_LIST_STATEMENT);

            // assert no record retrieved
            List list = taskTypeDAO.processReadListResultSet(resultSet);
            assertEquals("not correctly record returned from the TaskTypes table", 0, list.size());
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
            /*
             * insert a record to the database with create method and the initial record will be:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: default: null
             * creationDate: default: null
             * modificationUser: default: null
             * modificationDate: default: null
             */
            TaskType myTaskType = new TaskType();
            myTaskType.setDescription(DESCRIPTION);
            taskTypeDAO.create(myTaskType, CREATION_USER);

            /*
             * get the connection and fetch the record from the database.
             * The record which has just insert into the datebase should be:
             * primaryID: an id generated by IDGenerator
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: new Date
             * modificationUser: CREATION_USER
             * modificationDate: new Date
             */
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);

            List returnTaskTypes = UnitTestHelper.selectTaskTypes(conn);
            assertEquals("record was not properly created in the database", 1, returnTaskTypes.size());

            TaskType returnTaskType = (TaskType) returnTaskTypes.get(0);
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTaskType.getCreationUser());
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTaskType.getModificationUser());
            assertNotNull("record was not properly created in the database", returnTaskType.getCreationDate());
            assertNotNull("record was not properly created in the database", returnTaskType.getModificationDate());

            /*
             * The myTaskType will also be changed
             */
            assertEquals("myTaskType was not properly updated", CREATION_USER, myTaskType.getCreationUser());
            assertEquals("myTaskType was not properly updated", CREATION_USER, myTaskType.getModificationUser());
            assertNotNull("myTaskType was not properly updated", myTaskType.getCreationDate());
            assertNotNull("myTaskType was not properly updated", myTaskType.getModificationDate());
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
    public void testUpdate1() throws Exception {
        Connection conn = null;

        try {
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TaskType myTaskType = (TaskType) getTaskType();
            conn =  UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // update the record
            taskTypeDAO.update(myTaskType, MODIFICATION_USER);

            /*
             * Fetch the record from the database and this record should be:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: MODIFICATION_USER
             * modificationDate: a new date
             */
            List returnTaskTypes = UnitTestHelper.selectTaskTypes(conn);
            assertEquals("record was not properly updated in the database", 1, returnTaskTypes.size());

            TaskType returnTaskType = (TaskType) returnTaskTypes.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTaskType.getCreationUser());
            assertEquals("record was not properly updated in the database", MODIFICATION_USER,
                returnTaskType.getModificationUser());

            assertFalse("record was not properly updated in the database",
                MODIFICATION_DATE.equals(returnTaskType.getModificationDate()));

            /*
             * The myTaskType will also be updated
             */
            assertEquals("myTaskType was not properly updated", CREATION_USER, myTaskType.getCreationUser());
            assertEquals("myTaskType was not properly updated", MODIFICATION_USER, myTaskType.getModificationUser());
            assertTrue("myTaskType was not properly updated", CREATION_DATE.equals(myTaskType.getCreationDate()));
            assertFalse("myTaskType was not properly updated",
                MODIFICATION_DATE.equals(myTaskType.getModificationDate()));
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
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TaskType myTaskType = (TaskType) getTaskType();
            conn =  UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // update the record
            myTaskType.setPrimaryId(10);
            taskTypeDAO.update(myTaskType, MODIFICATION_USER);

            /*
             * for the myTaskType does not exist in the database, the record in the database will not change.
             * But the myTaskType self will be changed
             */
            List returnTaskTypes = UnitTestHelper.selectTaskTypes(conn);
            assertEquals("record was not properly updated in the database", 1, returnTaskTypes.size());

            TaskType returnTaskType = (TaskType) returnTaskTypes.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTaskType.getCreationUser());
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTaskType.getModificationUser());

            /*
             * The myTaskType will be updated
             */
            assertEquals("myTaskType was not properly updated", CREATION_USER, myTaskType.getCreationUser());
            assertEquals("myTaskType was not properly updated", MODIFICATION_USER, myTaskType.getModificationUser());
            assertTrue("myTaskType was not properly updated", CREATION_DATE.equals(myTaskType.getCreationDate()));
            assertFalse("myTaskType was not properly updated",
                MODIFICATION_DATE.equals(myTaskType.getModificationDate()));
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
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TaskType myTaskType = (TaskType) getTaskType();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // delete the record
            taskTypeDAO.delete(0);

            // assert there is no records in the database now
            List returnTaskTypes = UnitTestHelper.selectTaskTypes(conn);
            assertEquals("record was not properly deleted in the database", 0, returnTaskTypes.size());
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
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TaskType myTaskType = (TaskType) getTaskType();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // delete the record
            taskTypeDAO.delete(1);

            // assert there are still records in the database now
            List returnTaskTypes = UnitTestHelper.selectTaskTypes(conn);
            assertEquals("record was not properly deleted in the database", 1, returnTaskTypes.size());
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
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TaskType myTaskType = (TaskType) getTaskType();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // get the record
            TaskType returnTaskType = (TaskType) taskTypeDAO.get(0);

            // assert the return value is the exactly one which has just inserted into the database
            assertTrue("record was not properly got", judgeEquals(returnTaskType, myTaskType));
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
            /*
             * insert a record to the database in manual:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: CREATION_USER
             * modificationDate: CREATION_DATE
             */
            TaskType myTaskType = (TaskType) getTaskType();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTaskTypes(myTaskType, conn);

            // get the record
            TaskType returnTaskType = (TaskType) taskTypeDAO.get(1);

            // assert the return value is null
            assertNull("record was not properly got", returnTaskType);
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
            List myTaskTypes = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TaskType myTaskType = (TaskType) getTaskType();
                myTaskType.setPrimaryId(i);
                UnitTestHelper.insertTaskTypes(myTaskType, conn);
                myTaskTypes.add(myTaskType);
            }

            // assert records properly got from database
            List returnTaskTypes = taskTypeDAO.getList(null);

            assertEquals("not correctly record returned from the TaskTypes table", 10, returnTaskTypes.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the TaskTypes table",
                    judgeEquals((TaskType) returnTaskTypes.get(i), (TaskType) myTaskTypes.get(i)));
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
            List myTaskTypes = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TaskType myTaskType = (TaskType) getTaskType();
                myTaskType.setPrimaryId(i);
                UnitTestHelper.insertTaskTypes(myTaskType, conn);
                myTaskTypes.add(myTaskType);
            }

            // assert records properly got from database
            List returnTaskTypes = taskTypeDAO.getList("CreationUser = \'ivern\'");

            assertEquals("not correctly record returned from the TaskTypes table", 0, returnTaskTypes.size());
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
            List myTaskTypes = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TaskType myTaskType = (TaskType) getTaskType();
                myTaskType.setPrimaryId(i);
                UnitTestHelper.insertTaskTypes(myTaskType, conn);
                myTaskTypes.add(myTaskType);
            }

            // assert records properly got from database
            taskTypeDAO.delete(0);
            String whereCause = "CreationUser=\'" + CREATION_USER + "\'";
            List returnTaskTypes = taskTypeDAO.getList(whereCause);

            assertEquals("not correctly record returned from the TaskTypes table", 9, returnTaskTypes.size());

            for (int i = 0; i < 9; i++) {
                assertTrue("not correctly record returned from the TaskTypes table",
                    judgeEquals((TaskType) returnTaskTypes.get(i), (TaskType) myTaskTypes.get(i + 1)));
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
            taskTypeDAO.getList("CreationUser");
            fail("DAOActionException should be thrown here");
        } catch (DAOActionException e) {
            // good
        }
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
     * judge whether the two TaskType are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param taskType1 the first TaskType
     * @param taskType2 the second TaskType
     *
     * @return whether the two TaskType are equals
     */
    private boolean judgeEquals(TaskType taskType1, TaskType taskType2) {
        if (taskType1.getPrimaryId() != taskType2.getPrimaryId()) {
            return false;
        }

        if (!taskType1.getDescription().equals(taskType2.getDescription())) {
            return false;
        }

        if (!taskType1.getCreationUser().equals(taskType2.getCreationUser())) {
            return false;
        }

        long creationDate1 = UnitTestHelper.convertDate(taskType1.getCreationDate());
        long creationDate2 = UnitTestHelper.convertDate(taskType2.getCreationDate());
        if (creationDate1 != creationDate2) {
            return false;
        }

        if (!taskType1.getModificationUser().equals(taskType2.getModificationUser())) {
            return false;
        }

        long modificationDate1 = UnitTestHelper.convertDate(taskType1.getModificationDate());
        long modificationDate2 = UnitTestHelper.convertDate(taskType2.getModificationDate());
        if (modificationDate1 != modificationDate2) {
            return false;
        }

        return true;
    }
}
