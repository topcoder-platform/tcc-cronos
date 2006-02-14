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
 * Unit test cases for TimeStatusDAO.
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
public class TimeStatusDAOUnitTest extends TestCase {
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
     * The TimeStatusDAO instance for testing.
     * </p>
     */
    private BaseDAO timeStatusDAO = null;

    /**
     * <p>
     * TimeStatus instance for testing.
     * </p>
     */
    private DataObject timeStatus = new TimeStatus();

    /**
     * <p>
     * TaskType instance for testing. This will be regarded as an illagal argment for some verify methods.
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
        return new TestSuite(TimeStatusDAOUnitTest.class);
    }

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        this.timeStatusDAO = new TimeStatusDAO(CONNAME, NAMESPACE);
        ((TimeStatus) timeStatus).setDescription(DESCRIPTION);

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
        // delete all the records in TimeStatuss table
        Connection conn = null;

        try {
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
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
     * Tests the constructor - TimeStatusDAO(String connName, String namespace).
     * </p>
     */
    public void testTimeStatusDAO1() {
        assertNotNull("The constructor does not work well", this.timeStatusDAO);
    }

    /**
     * <p>
     * Tests the constructor - TimeStatusDAO(String connName, String namespace). With null connName,
     * NullPointerException should be thrown
     * </p>
     */
    public void testTimeStatusDAO2() {
        try {
            new TimeStatusDAO(null, NAMESPACE);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TimeStatusDAO(String connName, String namespace). With null namspace,
     * NullPointerException should be thrown
     * </p>
     */
    public void testTimeStatusDAO3() {
        try {
            new TimeStatusDAO(CONNAME, null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TimeStatusDAO(String connName, String namespace). With empty connName,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testTimeStatusDAO4() {
        try {
            new TimeStatusDAO(" ", NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - TimeStatusDAO(String connName, String namespace). With empty namspace,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testTimeStatusDAO5() {
        try {
            new TimeStatusDAO(CONNAME, " ");
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
        timeStatusDAO.verifyCreateDataObject(timeStatus);
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject2() {
        try {
            timeStatusDAO.verifyCreateDataObject(null);
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
     * TimeStatus, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject3() {
        try {
            timeStatusDAO.verifyCreateDataObject(taskType);
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
            ((TimeStatus) timeStatus).setDescription(null);
            timeStatusDAO.verifyCreateDataObject(timeStatus);
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
        assertEquals("the create Sql string is not properly got", TimeStatusDAO.SQL_CREATE_STATEMENT,
            timeStatusDAO.getCreateSqlString());
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
            statement = conn.prepareStatement(TimeStatusDAO.SQL_CREATE_STATEMENT);
            timeStatusDAO.fillCreatePreparedStatement(statement, timeStatus, CREATION_USER, CREATION_DATE);
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
        timeStatusDAO.setCreationParametersInDataObject(timeStatus, CREATION_USER, CREATION_DATE);
        assertEquals("creationUser field is not properly set", CREATION_USER,
            ((TimeStatus) timeStatus).getCreationUser());
        assertEquals("creationDate field is not properly set", CREATION_DATE,
            ((TimeStatus) timeStatus).getCreationDate());
        assertEquals("modificationUser field incorrectly set", CREATION_USER,
            ((TimeStatus) timeStatus).getModificationUser());
        assertEquals("modificationDate field incorrectly set", CREATION_DATE,
            ((TimeStatus) timeStatus).getModificationDate());
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
        timeStatusDAO.verifyUpdateDataObject(timeStatus);
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With null dataObject, NullPointerException
     * should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject2() {
        try {
            timeStatusDAO.verifyUpdateDataObject(null);
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
     * TimeStatus, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject3() {
        try {
            timeStatusDAO.verifyUpdateDataObject(taskType);
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
            ((TimeStatus) timeStatus).setDescription(null);
            timeStatusDAO.verifyUpdateDataObject(timeStatus);
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
        assertEquals("the update Sql string is not properly got", TimeStatusDAO.SQL_UPDATE_STATEMENT,
            timeStatusDAO.getUpdateSqlString());
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

            PreparedStatement statement = conn.prepareStatement(TimeStatusDAO.SQL_UPDATE_STATEMENT);
            timeStatusDAO.fillUpdatePreparedStatement(statement, timeStatus, MODIFICATION_USER, MODIFICATION_DATE);
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
        timeStatusDAO.setModificationParametersInDataObject(timeStatus, MODIFICATION_USER, MODIFICATION_DATE);

        // set the creation user and date and check these value are changed after this method
        ((TimeStatus) timeStatus).setCreationUser(CREATION_USER);
        ((TimeStatus) timeStatus).setCreationDate(CREATION_DATE);

        // check the creation user and date
        assertEquals("creationUser field should not be set in this method", CREATION_USER,
            ((TimeStatus) timeStatus).getCreationUser());
        assertEquals("creationDate field should not be set in this method", CREATION_DATE,
            ((TimeStatus) timeStatus).getCreationDate());

        // check the modification user and date
        assertEquals("modificationUser field incorrectly set", MODIFICATION_USER,
            ((TimeStatus) timeStatus).getModificationUser());
        assertEquals("modificationDate field incorrectly set", MODIFICATION_DATE,
            ((TimeStatus) timeStatus).getModificationDate());
    }

    /**
     * <p>
     * Tests the getDeleteSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDeleteSqlString() throws Exception {
        assertEquals("the delete Sql string is not properly got", TimeStatusDAO.SQL_DELETE_STATEMENT,
            timeStatusDAO.getDeleteSqlString());
    }

    /**
     * <p>
     * Tests the getReadSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetReadSqlString() throws Exception {
        assertEquals("the read Sql string is not properly got", TimeStatusDAO.SQL_GET_STATEMENT,
            timeStatusDAO.getReadSqlString());
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // assert this method returns a valid value
            statement = conn.prepareStatement(TimeStatusDAO.SQL_GET_STATEMENT);
            statement.setInt(1, myTimeStatus.getPrimaryId());

            resultSet = statement.executeQuery();
            assertTrue("not correctly record returned from the TimeStatuses table",
                judgeEquals(myTimeStatus, (TimeStatus) timeStatusDAO.processReadResultSet(resultSet)));
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
            timeStatusDAO.processReadResultSet(null);
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

            statement = conn.prepareStatement(TimeStatusDAO.SQL_GET_STATEMENT);
            statement.setInt(1, 1);

            resultSet = statement.executeQuery();

            // assert no record retrieved
            assertNull("not correctly record returned from the TimeStatuses table",
                timeStatusDAO.processReadResultSet(resultSet));
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
        assertEquals("the read list Sql string is not properly got", TimeStatusDAO.SQL_GET_LIST_STATEMENT,
            timeStatusDAO.getReadListSqlString());
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
            List myTimeStatuss = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
                myTimeStatus.setPrimaryId(i);
                UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
                myTimeStatuss.add(myTimeStatus);
            }

            // assert this method returns the valid value
            statement = conn.createStatement();
            resultSet = statement.executeQuery(TimeStatusDAO.SQL_GET_LIST_STATEMENT);

            List returnTimeStatuss = timeStatusDAO.processReadListResultSet(resultSet);

            assertEquals("not correctly record returned from the TimeStatuses table", 10, returnTimeStatuss.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the TimeStatuses table",
                    judgeEquals((TimeStatus) returnTimeStatuss.get(i), (TimeStatus) myTimeStatuss.get(i)));
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
            timeStatusDAO.processReadListResultSet(null);
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
            resultSet = statement.executeQuery(TimeStatusDAO.SQL_GET_LIST_STATEMENT);

            // assert no record retrieved
            List list = timeStatusDAO.processReadListResultSet(resultSet);
            assertEquals("not correctly record returned from the TimeStatuses table", 0, list.size());
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
            TimeStatus myTimeStatus = new TimeStatus();
            myTimeStatus.setDescription(DESCRIPTION);
            timeStatusDAO.create(myTimeStatus, CREATION_USER);

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

            List returnTimeStatuss = UnitTestHelper.selectTimeStatuses(conn);
            assertEquals("record was not properly created in the database", 1, returnTimeStatuss.size());

            TimeStatus returnTimeStatus = (TimeStatus) returnTimeStatuss.get(0);
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTimeStatus.getCreationUser());
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnTimeStatus.getModificationUser());
            assertNotNull("record was not properly created in the database", returnTimeStatus.getCreationDate());
            assertNotNull("record was not properly created in the database", returnTimeStatus.getModificationDate());

            /*
             * The myTimeStatus will also be changed
             */
            assertEquals("myTimeStatus was not properly updated", CREATION_USER, myTimeStatus.getCreationUser());
            assertEquals("myTimeStatus was not properly updated", CREATION_USER, myTimeStatus.getModificationUser());
            assertNotNull("myTimeStatus was not properly updated", myTimeStatus.getCreationDate());
            assertNotNull("myTimeStatus was not properly updated", myTimeStatus.getModificationDate());
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            conn =  UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // update the record
            timeStatusDAO.update(myTimeStatus, MODIFICATION_USER);

            /*
             * Fetch the record from the database and this record should be:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: MODIFICATION_USER
             * modificationDate: a new date
             */
            List returnTimeStatuss = UnitTestHelper.selectTimeStatuses(conn);
            assertEquals("record was not properly updated in the database", 1, returnTimeStatuss.size());

            TimeStatus returnTimeStatus = (TimeStatus) returnTimeStatuss.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeStatus.getCreationUser());
            assertEquals("record was not properly updated in the database", MODIFICATION_USER,
                returnTimeStatus.getModificationUser());

            assertFalse("record was not properly updated in the database",
                MODIFICATION_DATE.equals(returnTimeStatus.getModificationDate()));

            /*
             * The myTimeStatus will also be updated
             */
            assertEquals("myTimeStatus was not properly updated", CREATION_USER, myTimeStatus.getCreationUser());
            assertEquals("myTimeStatus was not properly updated", MODIFICATION_USER,
                myTimeStatus.getModificationUser());
            assertTrue("myTimeStatus was not properly updated", CREATION_DATE.equals(myTimeStatus.getCreationDate()));
            assertFalse("myTimeStatus was not properly updated",
                MODIFICATION_DATE.equals(myTimeStatus.getModificationDate()));
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            conn =  UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // update the record
            myTimeStatus.setPrimaryId(10);
            timeStatusDAO.update(myTimeStatus, MODIFICATION_USER);

            /*
             * for the myTimeStatus does not exist in the database, the record in the database will not change.
             * But the myTimeStatus self will be changed
             */
            List returnTimeStatuss = UnitTestHelper.selectTimeStatuses(conn);
            assertEquals("record was not properly updated in the database", 1, returnTimeStatuss.size());

            TimeStatus returnTimeStatus = (TimeStatus) returnTimeStatuss.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeStatus.getCreationUser());
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnTimeStatus.getModificationUser());

            /*
             * The myTimeStatus will be updated
             */
            assertEquals("myTimeStatus was not properly updated", CREATION_USER, myTimeStatus.getCreationUser());
            assertEquals("myTimeStatus was not properly updated",
                MODIFICATION_USER, myTimeStatus.getModificationUser());
            assertTrue("myTimeStatus was not properly updated", CREATION_DATE.equals(myTimeStatus.getCreationDate()));
            assertFalse("myTimeStatus was not properly updated",
                MODIFICATION_DATE.equals(myTimeStatus.getModificationDate()));
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // delete the record
            timeStatusDAO.delete(0);

            // assert there is no records in the database now
            List returnTimeStatuss = UnitTestHelper.selectTimeStatuses(conn);
            assertEquals("record was not properly deleted in the database", 0, returnTimeStatuss.size());
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // delete the record
            timeStatusDAO.delete(1);

            // assert there are still records in the database now
            List returnTimeStatuss = UnitTestHelper.selectTimeStatuses(conn);
            assertEquals("record was not properly deleted in the database", 1, returnTimeStatuss.size());
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // get the record
            TimeStatus returnTimeStatus = (TimeStatus) timeStatusDAO.get(0);

            // assert the return value is the exactly one which has just inserted into the database
            assertTrue("record was not properly got", judgeEquals(returnTimeStatus, myTimeStatus));
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
            TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
            conn = UnitTestHelper.getConnection(NAMESPACE, CONNAME);
            UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);

            // get the record
            TimeStatus returnTimeStatus = (TimeStatus) timeStatusDAO.get(1);

            // assert the return value is null
            assertNull("record was not properly got", returnTimeStatus);
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
            List myTimeStatuss = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
                myTimeStatus.setPrimaryId(i);
                UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
                myTimeStatuss.add(myTimeStatus);
            }

            // assert records properly got from database
            List returnTimeStatuss = timeStatusDAO.getList(null);

            assertEquals("not correctly record returned from the TimeStatuses table", 10, returnTimeStatuss.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the TimeStatuses table",
                    judgeEquals((TimeStatus) returnTimeStatuss.get(i), (TimeStatus) myTimeStatuss.get(i)));
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
            List myTimeStatuss = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
                myTimeStatus.setPrimaryId(i);
                UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
                myTimeStatuss.add(myTimeStatus);
            }

            // assert records properly got from database
            List returnTimeStatuss = timeStatusDAO.getList("CreationUser = \'ivern\'");

            assertEquals("not correctly record returned from the TimeStatuses table", 0, returnTimeStatuss.size());
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
            List myTimeStatuss = new ArrayList();

            for (int i = 0; i < 10; i++) {
                TimeStatus myTimeStatus = (TimeStatus) getTimeStatus();
                myTimeStatus.setPrimaryId(i);
                UnitTestHelper.insertTimeStatuses(myTimeStatus, conn);
                myTimeStatuss.add(myTimeStatus);
            }

            // assert records properly got from database
            timeStatusDAO.delete(0);
            String whereCause = "CreationUser=\'" + CREATION_USER + "\'";
            List returnTimeStatuss = timeStatusDAO.getList(whereCause);

            assertEquals("not correctly record returned from the TimeStatuses table", 9, returnTimeStatuss.size());

            for (int i = 0; i < 9; i++) {
                assertTrue("not correctly record returned from the TimeStatuses table",
                    judgeEquals((TimeStatus) returnTimeStatuss.get(i), (TimeStatus) myTimeStatuss.get(i + 1)));
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
            timeStatusDAO.getList("CreationUser");
            fail("DAOActionException should be thrown here");
        } catch (DAOActionException e) {
            // good
        }
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
     * judge whether the two TimeStatus are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param timeStatus1 the first TimeStatus
     * @param timeStatus2 the second TimeStatus
     *
     * @return whether the two TimeStatus are equals
     */
    private boolean judgeEquals(TimeStatus timeStatus1, TimeStatus timeStatus2) {
        if (timeStatus1.getPrimaryId() != timeStatus2.getPrimaryId()) {
            return false;
        }

        if (!timeStatus1.getDescription().equals(timeStatus2.getDescription())) {
            return false;
        }

        if (!timeStatus1.getCreationUser().equals(timeStatus2.getCreationUser())) {
            return false;
        }

        long creationDate1 = UnitTestHelper.convertDate(timeStatus1.getCreationDate());
        long creationDate2 = UnitTestHelper.convertDate(timeStatus2.getCreationDate());
        if (creationDate1 != creationDate2) {
            return false;
        }

        if (!timeStatus1.getModificationUser().equals(timeStatus2.getModificationUser())) {
            return false;
        }

        long modificationDate1 = UnitTestHelper.convertDate(timeStatus1.getModificationDate());
        long modificationDate2 = UnitTestHelper.convertDate(timeStatus2.getModificationDate());
        if (modificationDate1 != modificationDate2) {
            return false;
        }

        return true;
    }
}
