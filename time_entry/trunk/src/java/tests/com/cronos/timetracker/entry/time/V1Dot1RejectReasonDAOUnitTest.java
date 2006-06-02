/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit test cases for RejectReasonDAO.
 * </p>
 *
 * <p>
 * This class will test all the implemented abstract helper methods. The five CRUD(Create, Read, Update, and Delete)
 * database related operation methods will also be test here at last.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1RejectReasonDAOUnitTest extends TestCase {
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
    private static final String NAMESPACE = "com.cronos.timetracker.entry.time.myconfig";

    /**
     * <p>
     * The RejectReasonDAO instance for testing.
     * </p>
     */
    private BaseDAO rejectReasonDAO = null;

    /**
     * <p>
     * RejectReason instance for testing.
     * </p>
     */
    private DataObject rejectReason = new RejectReason();

    /**
     * <p>
     * TaskType instance for testing. This will be regarded as an illagal argment for some verify methods.
     * </p>
     */
    private DataObject taskType = new TaskType();

    /**
     * <p>
     * Set up the environment for testing.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        // load the configuration
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        // delete all the records in all tables
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }

        // create the new instance
        this.rejectReasonDAO = new RejectReasonDAO(CONNAME, NAMESPACE);
        ((RejectReason) rejectReason).setDescription(DESCRIPTION);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // delete all the records in all tables
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the constructor - RejectReasonDAO(String connName, String namespace).
     * </p>
     */
    public void testRejectReasonDAO1() {
        assertNotNull("The constructor does not work well", this.rejectReasonDAO);
    }

    /**
     * <p>
     * Tests the constructor - RejectReasonDAO(String connName, String namespace). With null connName,
     * NullPointerException should be thrown
     * </p>
     */
    public void testRejectReasonDAO2() {
        try {
            new RejectReasonDAO(null, NAMESPACE);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - RejectReasonDAO(String connName, String namespace). With null namspace,
     * NullPointerException should be thrown
     * </p>
     */
    public void testRejectReasonDAO3() {
        try {
            new RejectReasonDAO(CONNAME, null);
            fail("NullPointerException should be thrown.");
        } catch (NullPointerException npe) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - RejectReasonDAO(String connName, String namespace). With empty connName,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testRejectReasonDAO4() {
        try {
            new RejectReasonDAO(" ", NAMESPACE);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException iae) {
            // good
        }
    }

    /**
     * <p>
     * Tests the constructor - RejectReasonDAO(String connName, String namespace). With empty namspace,
     * IllegalArgumentException should be thrown
     * </p>
     */
    public void testRejectReasonDAO5() {
        try {
            new RejectReasonDAO(CONNAME, " ");
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
        rejectReasonDAO.verifyCreateDataObject(rejectReason);
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With null dataObject, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject2() {
        try {
            rejectReasonDAO.verifyCreateDataObject(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException npe) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the verifyCreateDataObject(DataObject dataObject) method. With dataObject which is not instance of
     * RejectReason, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyCreateDataObject3() {
        try {
            rejectReasonDAO.verifyCreateDataObject(taskType);
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
            ((RejectReason) rejectReason).setDescription(null);
            rejectReasonDAO.verifyCreateDataObject(rejectReason);
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
        assertEquals("the create Sql string is not properly got", RejectReasonDAO.SQL_CREATE_STATEMENT,
            rejectReasonDAO.getCreateSqlString());
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            statement = conn.prepareStatement(RejectReasonDAO.SQL_CREATE_STATEMENT);
            rejectReasonDAO.fillCreatePreparedStatement(statement, rejectReason, CREATION_USER, CREATION_DATE);
        } finally {
            V1Dot1TestHelper.closeResources(null, statement, conn);
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
    public void testSetCreationParametersInDataObject() throws Exception {
        rejectReasonDAO.setCreationParametersInDataObject(rejectReason, CREATION_USER, CREATION_DATE);
        assertEquals("creationUser field is not properly set", CREATION_USER,
            ((RejectReason) rejectReason).getCreationUser());
        assertEquals("creationDate field is not properly set", CREATION_DATE,
            ((RejectReason) rejectReason).getCreationDate());
        assertEquals("modificationUser field incorrectly set", CREATION_USER,
            ((RejectReason) rejectReason).getModificationUser());
        assertEquals("modificationDate field incorrectly set", CREATION_DATE,
            ((RejectReason) rejectReason).getModificationDate());
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
        ((RejectReason) rejectReason).setCreationDate(new Date());
        rejectReasonDAO.verifyUpdateDataObject(rejectReason);
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With null dataObject, IllegalArgumentException
     * should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject2() {
        try {
            rejectReasonDAO.verifyUpdateDataObject(null);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException npe) {
            // good
        } catch (DAOActionException e) {
            fail("No DAOActionException should be thrown");
        }
    }

    /**
     * <p>
     * Tests the verifyUpdateDataObject(DataObject dataObject) method. With dataObject which is not instance of
     * RejectReason, IllegalArgumentException should be thrown.
     * </p>
     */
    public void testVerifyUpdateDataObject3() {
        try {
            rejectReasonDAO.verifyUpdateDataObject(taskType);
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
            ((RejectReason) rejectReason).setDescription(null);
            rejectReasonDAO.verifyUpdateDataObject(rejectReason);
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
        assertEquals("the update Sql string is not properly got", RejectReasonDAO.SQL_UPDATE_STATEMENT,
            rejectReasonDAO.getUpdateSqlString());
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            PreparedStatement statement = conn.prepareStatement(RejectReasonDAO.SQL_UPDATE_STATEMENT);
            rejectReasonDAO.fillUpdatePreparedStatement(statement, rejectReason, MODIFICATION_USER, MODIFICATION_DATE);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
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
    public void testSetModificationParametersInDataObject() throws Exception {
        rejectReasonDAO.setModificationParametersInDataObject(rejectReason, MODIFICATION_USER, MODIFICATION_DATE);

        // set the creation user and date and check these value are changed after this method
        ((RejectReason) rejectReason).setCreationUser(CREATION_USER);
        ((RejectReason) rejectReason).setCreationDate(CREATION_DATE);

        // check the creation user and date
        assertEquals("creationUser field should not be set in this method", CREATION_USER,
            ((RejectReason) rejectReason).getCreationUser());
        assertEquals("creationDate field should not be set in this method", CREATION_DATE,
            ((RejectReason) rejectReason).getCreationDate());

        // check the modification user and date
        assertEquals("modificationUser field incorrectly set", MODIFICATION_USER,
            ((RejectReason) rejectReason).getModificationUser());
        assertEquals("modificationDate field incorrectly set", MODIFICATION_DATE,
            ((RejectReason) rejectReason).getModificationDate());
    }

    /**
     * <p>
     * Tests the getDeleteSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetDeleteSqlString() throws Exception {
        assertEquals("the delete Sql string is not properly got", RejectReasonDAO.SQL_DELETE_STATEMENT,
            rejectReasonDAO.getDeleteSqlString());
    }

    /**
     * <p>
     * Tests the getReadSqlString() method.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetReadSqlString() throws Exception {
        assertEquals("the read Sql string is not properly got", RejectReasonDAO.SQL_GET_STATEMENT,
            rejectReasonDAO.getReadSqlString());
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // insert a record to the database
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // assert this method returns a valid value
            statement = conn.prepareStatement(RejectReasonDAO.SQL_GET_STATEMENT);
            statement.setInt(1, myRejectReason.getPrimaryId());

            resultSet = statement.executeQuery();
            assertTrue("not correctly record returned from the RejectReasones table",
                judgeEquals(myRejectReason, (RejectReason) rejectReasonDAO.processReadResultSet(resultSet)));
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
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
            rejectReasonDAO.processReadResultSet(null);
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            statement = conn.prepareStatement(RejectReasonDAO.SQL_GET_STATEMENT);
            statement.setInt(1, 1);

            resultSet = statement.executeQuery();

            // assert no record retrieved
            assertNull("not correctly record returned from the RejectReasones table",
                rejectReasonDAO.processReadResultSet(resultSet));
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
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
        assertEquals("the read list Sql string is not properly got", RejectReasonDAO.SQL_GET_LIST_STATEMENT,
            rejectReasonDAO.getReadListSqlString());
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database
            List myRejectReasons = new ArrayList();

            for (int i = 0; i < 10; i++) {
                RejectReason myRejectReason = (RejectReason) getRejectReason();
                myRejectReason.setPrimaryId(i);
                V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);
                myRejectReasons.add(myRejectReason);
            }

            // assert this method returns the valid value
            statement = conn.createStatement();
            resultSet = statement.executeQuery(RejectReasonDAO.SQL_GET_LIST_STATEMENT);

            List returnRejectReasons = rejectReasonDAO.processReadListResultSet(resultSet);

            assertEquals("not correctly record returned from the RejectReasones table",
                    10, returnRejectReasons.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the RejectReasones table",
                    judgeEquals((RejectReason) returnRejectReasons.get(i), (RejectReason) myRejectReasons.get(i)));
            }
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
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
            rejectReasonDAO.processReadListResultSet(null);
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            statement = conn.createStatement();
            resultSet = statement.executeQuery(RejectReasonDAO.SQL_GET_LIST_STATEMENT);

            // assert no record retrieved
            List list = rejectReasonDAO.processReadListResultSet(resultSet);
            assertEquals("not correctly record returned from the RejectReasones table", 0, list.size());
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
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
            RejectReason myRejectReason = new RejectReason();
            myRejectReason.setDescription(DESCRIPTION);
            rejectReasonDAO.create(myRejectReason, CREATION_USER);

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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            List returnRejectReasons = V1Dot1TestHelper.selectRejectReasons(conn);
            assertEquals("record was not properly created in the database", 1, returnRejectReasons.size());

            RejectReason returnRejectReason = (RejectReason) returnRejectReasons.get(0);
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnRejectReason.getCreationUser());
            assertEquals("record was not properly created in the database", CREATION_USER,
                returnRejectReason.getModificationUser());
            assertNotNull("record was not properly created in the database", returnRejectReason.getCreationDate());
            assertNotNull("record was not properly created in the database", returnRejectReason.getModificationDate());

            /*
             * The myRejectReason will also be changed
             */
            assertEquals("myRejectReason was not properly updated", CREATION_USER, myRejectReason.getCreationUser());
            assertEquals("myRejectReason was not properly updated", CREATION_USER,
                    myRejectReason.getModificationUser());
            assertNotNull("myRejectReason was not properly updated", myRejectReason.getCreationDate());
            assertNotNull("myRejectReason was not properly updated", myRejectReason.getModificationDate());
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
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
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // update the record
            rejectReasonDAO.update(myRejectReason, MODIFICATION_USER);

            /*
             * Fetch the record from the database and this record should be:
             * primaryID: default: 0
             * decription: DESCRIPTION
             * creationUser: CREATION_USER
             * creationDate: CREATION_DATE
             * modificationUser: MODIFICATION_USER
             * modificationDate: a new date
             * active:0
             */
            List returnRejectReasons = V1Dot1TestHelper.selectRejectReasons(conn);
            assertEquals("record was not properly updated in the database", 1, returnRejectReasons.size());

            RejectReason returnRejectReason = (RejectReason) returnRejectReasons.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnRejectReason.getCreationUser());
            assertEquals("record was not properly updated in the database", MODIFICATION_USER,
                returnRejectReason.getModificationUser());

            assertFalse("record was not properly updated in the database",
                MODIFICATION_DATE.equals(returnRejectReason.getModificationDate()));

            /*
             * The myRejectReason will also be updated
             */
            assertEquals("myRejectReason was not properly updated", CREATION_USER, myRejectReason.getCreationUser());
            assertEquals("myRejectReason was not properly updated", MODIFICATION_USER,
                myRejectReason.getModificationUser());
            assertTrue("myRejectReason was not properly updated",
                    CREATION_DATE.equals(myRejectReason.getCreationDate()));
            assertFalse("myRejectReason was not properly updated",
                MODIFICATION_DATE.equals(myRejectReason.getModificationDate()));
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user). With not existed dataObject.
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
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // update the record
            myRejectReason.setPrimaryId(10);
            rejectReasonDAO.update(myRejectReason, MODIFICATION_USER);

            /*
             * for the myRejectReason does not exist in the database, the record in the database will not change.
             * But the myRejectReason self will be changed
             */
            List returnRejectReasons = V1Dot1TestHelper.selectRejectReasons(conn);
            assertEquals("record was not properly updated in the database", 1, returnRejectReasons.size());

            RejectReason returnRejectReason = (RejectReason) returnRejectReasons.get(0);
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnRejectReason.getCreationUser());
            assertEquals("record was not properly updated in the database", CREATION_USER,
                returnRejectReason.getModificationUser());

            /*
             * The myRejectReason will be updated
             */
            assertEquals("myRejectReason was not properly updated", CREATION_USER, myRejectReason.getCreationUser());
            assertEquals("myRejectReason was not properly updated", MODIFICATION_USER,
                myRejectReason.getModificationUser());
            assertTrue("myRejectReason was not properly updated",
                    CREATION_DATE.equals(myRejectReason.getCreationDate()));
            assertFalse("myRejectReason was not properly updated",
                MODIFICATION_DATE.equals(myRejectReason.getModificationDate()));
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
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
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // delete the record
            rejectReasonDAO.delete(0);

            // assert there is no records in the database now
            List returnRejectReasons = V1Dot1TestHelper.selectRejectReasons(conn);
            assertEquals("record was not properly deleted in the database", 0, returnRejectReasons.size());
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the delete(int primaryId). With not existed primaryId
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
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // delete the record
            rejectReasonDAO.delete(1);

            // assert there are still records in the database now
            List returnRejectReasons = V1Dot1TestHelper.selectRejectReasons(conn);
            assertEquals("record was not properly deleted in the database", 1, returnRejectReasons.size());
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
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
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // get the record
            RejectReason returnRejectReason = (RejectReason) rejectReasonDAO.get(0);

            // assert the return value is the exactly one which has just inserted into the database
            assertTrue("record was not properly got", judgeEquals(returnRejectReason, myRejectReason));
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the get(int primaryId). With not existed primaryId.
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
            RejectReason myRejectReason = (RejectReason) getRejectReason();
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);

            // get the record
            RejectReason returnRejectReason = (RejectReason) rejectReasonDAO.get(1);

            // assert the return value is null
            assertNull("record was not properly got", returnRejectReason);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
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
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database in manual
            List myRejectReasons = new ArrayList();

            for (int i = 0; i < 10; i++) {
                RejectReason myRejectReason = (RejectReason) getRejectReason();
                myRejectReason.setPrimaryId(i);
                V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);
                myRejectReasons.add(myRejectReason);
            }

            // assert records properly got from database
            List returnRejectReasons = rejectReasonDAO.getList(null);

            assertEquals("not correctly record returned from the RejectReasones table",
                    10, returnRejectReasons.size());

            for (int i = 0; i < 10; i++) {
                assertTrue("not correctly record returned from the RejectReasones table",
                    judgeEquals((RejectReason) returnRejectReasons.get(i), (RejectReason) myRejectReasons.get(i)));
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause). With whereClause = "creation_user = \'ivern\'"
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList2() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database in manual
            List myRejectReasons = new ArrayList();

            for (int i = 0; i < 10; i++) {
                RejectReason myRejectReason = (RejectReason) getRejectReason();
                myRejectReason.setPrimaryId(i);
                V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);
                myRejectReasons.add(myRejectReason);
            }

            // assert records properly got from database
            List returnRejectReasons = rejectReasonDAO.getList("creation_user = \'ivern\'");

            assertEquals("not correctly record returned from the RejectReasones table", 0, returnRejectReasons.size());
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause). With whereClause = "creation_user = CREATIONUSER"
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList3() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // insert several record to the database in manual
            List myRejectReasons = new ArrayList();

            for (int i = 0; i < 10; i++) {
                RejectReason myRejectReason = (RejectReason) getRejectReason();
                myRejectReason.setPrimaryId(i);
                V1Dot1TestHelper.insertRejectReasons(myRejectReason, conn);
                myRejectReasons.add(myRejectReason);
            }

            // assert records properly got from database
            rejectReasonDAO.delete(0);

            String whereCause = "creation_user=\'" + CREATION_USER + "\'";
            List returnRejectReasons = rejectReasonDAO.getList(whereCause);

            assertEquals("not correctly record returned from the RejectReasones table", 9, returnRejectReasons.size());

            for (int i = 0; i < 9; i++) {
                assertTrue("not correctly record returned from the RejectReasones table",
                    judgeEquals((RejectReason) returnRejectReasons.get(i), (RejectReason) myRejectReasons.get(i + 1)));
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Tests the getList(String whereClause). With incorrect whereClause
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testGetList4() throws Exception {
        try {
            rejectReasonDAO.getList("creation_user");
            fail("DAOActionException should be thrown here");
        } catch (DAOActionException e) {
            // good
        }
    }

    /**
     * <p>
     * return a RejectReason instance for testing.
     * </p>
     *
     * @return a RejectReason instance for testing.
     */
    private DataObject getRejectReason() {
        RejectReason myRejectReason = new RejectReason();

        myRejectReason.setDescription(DESCRIPTION);
        myRejectReason.setCreationUser(CREATION_USER);
        myRejectReason.setCreationDate(CREATION_DATE);
        myRejectReason.setModificationUser(CREATION_USER);
        myRejectReason.setModificationDate(CREATION_DATE);

        return myRejectReason;
    }

    /**
     * <p>
     * judge whether the two RejectReason are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param rejectReason1 the first RejectReason
     * @param rejectReason2 the second RejectReason
     *
     * @return whether the two RejectReason are equals
     */
    private boolean judgeEquals(RejectReason rejectReason1, RejectReason rejectReason2) {
        if (rejectReason1.getPrimaryId() != rejectReason2.getPrimaryId()) {
            return false;
        }

        if (!rejectReason1.getDescription().equals(rejectReason2.getDescription())) {
            return false;
        }

        if (!rejectReason1.getCreationUser().equals(rejectReason2.getCreationUser())) {
            return false;
        }

        long creationDate1 = V1Dot1TestHelper.convertDate(rejectReason1.getCreationDate());
        long creationDate2 = V1Dot1TestHelper.convertDate(rejectReason2.getCreationDate());

        if (creationDate1 != creationDate2) {
            return false;
        }

        if (!rejectReason1.getModificationUser().equals(rejectReason2.getModificationUser())) {
            return false;
        }

        long modificationDate1 = V1Dot1TestHelper.convertDate(rejectReason1.getModificationDate());
        long modificationDate2 = V1Dot1TestHelper.convertDate(rejectReason2.getModificationDate());

        if (modificationDate1 != modificationDate2) {
            return false;
        }
        return true;
    }
}
