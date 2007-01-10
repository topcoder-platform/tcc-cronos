/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.RejectReason;
import com.topcoder.timetracker.entry.time.ResultData;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Accuracy test cases for TimeEntryDAO.
 * </p>
 *
 * @author oodinary
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 */
public class TimeEntryDAOAccuracyTest extends TestCase {
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
    private static final Date CREATION_DATE = AccuracyTestHelper.createDate(2006, 1, 1);

    /**
     * <p>
     * Represents the date that last modified this record.
     * </p>
     */
    private static final Date MODIFICATION_DATE = AccuracyTestHelper.createDate(2006, 2, 1);

    /**
     * <p>
     * The TimeEntryDAO instance for testing.
     * </p>
     */
    private TimeEntryDAO timeEntryDAO = null;

    /** Represents a valid time entry entry instance. */
    private TimeEntry entry;

    /** Represents a valid time task type instance. */
    private TaskType type;

    /** Represents a valid time status instance. */
    private TimeStatus status;

    /** Represents a valid time reject reason instances. */
    private RejectReason[] reasons;

    /** The connection used for testing. */
    private Connection conn = null;

    /**
     * <p>
     * Set up the environment for testing. Valid configurations are loaded. A valid manager is created. The data table
     * is truncated. A valid expense entry entry is created. One task type and one time status and two reject reasons
     * are added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    protected void setUp() throws Exception {
        // load the configuration
        AccuracyTestHelper.clearConfiguration();
        AccuracyTestHelper.addValidConfiguration();

        // delete all the records in all tables
        conn = AccuracyTestHelper.getConnection(NAMESPACE, CONNAME);
        AccuracyTestHelper.clearDatabase(conn);
        AccuracyTestHelper.insertCompany(10, conn);
        AccuracyTestHelper.insertCompany(20, conn);

        // Insert an task type
        type = new TaskType();

        type.setPrimaryId(1);
        type.setDescription("taskType");
        type.setCreationDate(AccuracyTestHelper.createDate(2005, 1, 1));
        type.setModificationDate(AccuracyTestHelper.createDate(2005, 2, 1));
        type.setCreationUser("taskTypeCreate");
        type.setModificationUser("taskTypeModification");

        AccuracyTestHelper.insertTaskTypes(type, conn, 10);

        // Insert the time status
        status = new TimeStatus();

        status.setPrimaryId(2);
        status.setDescription("timeStatus");
        status.setCreationDate(AccuracyTestHelper.createDate(2005, 3, 1));
        status.setModificationDate(AccuracyTestHelper.createDate(2005, 4, 1));
        status.setCreationUser("timeStatusCreate");
        status.setModificationUser("timeStatusModification");

        AccuracyTestHelper.insertTimeStatuses(status, conn);

        // Insert the first reject reason
        reasons = new RejectReason[10];

        for (int i = 0; i < 10; i++) {
            reasons[i] = new RejectReason();
            reasons[i].setPrimaryId(i);
            reasons[i].setDescription("reason");
            reasons[i].setCreationDate(AccuracyTestHelper.createDate(2005, 5, 1));
            reasons[i].setModificationDate(AccuracyTestHelper.createDate(2005, 6, 1));
            reasons[i].setCreationUser("reasonCreate");
            reasons[i].setModificationUser("reasonModification");
            AccuracyTestHelper.insertRejectReasons(reasons[i], conn, 10);

        }

        this.timeEntryDAO = new TimeEntryDAO(CONNAME, NAMESPACE);
    }

    /**
     * Removes the configuration if it exists.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        // clear the configuration
        AccuracyTestHelper.clearConfiguration();

        // delete all the records in all tables
        AccuracyTestHelper.clearDatabase(conn);

        conn.close();
    }

    /**
     * <p>
     * Tests the create(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testCreateGet_Accuracy() throws Exception {
        entry = new TimeEntry();
        entry.setDescription(DESCRIPTION);
        entry.setDate(CREATION_DATE);
        entry.setTaskTypeId(type.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        entry.setCompanyId(AccuracyTestHelper.COMPANY_ID);

        for (int i = 0; i < reasons.length; i++) {
            entry.addRejectReason(reasons[i]);
        }

        timeEntryDAO.create(entry, CREATION_USER);

        // validate
        AccuracyTestHelper.assertEquals("TimeEntry was not properly created.", entry,
            (TimeEntry) timeEntryDAO.get(entry.getPrimaryId()));

        // The links should also be inserted into the time_reject_reason table
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");

        for (int i = 0; i < reasons.length; i++) {
            // check the record
            assertTrue("There should be ten records in time_reject_reason table.", resultSet.next());

            assertEquals("Table time_reject_reason is not updated.", entry.getPrimaryId(),
                resultSet.getInt("time_entry_id"));
            assertEquals("Table time_reject_reason is not updated.", reasons[i].getPrimaryId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER, resultSet.getString("creation_user"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER,
                resultSet.getString("modification_user"));
            AccuracyTestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            AccuracyTestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("modification_date"));
        }

        assertFalse("There should be ten records in time_reject_reason table.", resultSet.next());
        statement.close();
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdateGet_Accuracy() throws Exception {
        entry = new TimeEntry();
        entry.setDescription(DESCRIPTION);
        entry.setDate(CREATION_DATE);
        entry.setTaskTypeId(type.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        entry.setCompanyId(AccuracyTestHelper.COMPANY_ID);
        timeEntryDAO.create(entry, CREATION_USER);

        for (int i = 0; i < reasons.length; i++) {
            entry.addRejectReason(reasons[i]);
        }

        timeEntryDAO.update(entry, MODIFICATION_USER);

        // validate
        AccuracyTestHelper.assertEquals("TimeEntry was not properly created.", entry,
            (TimeEntry) timeEntryDAO.get(entry.getPrimaryId()));

        // The links should also be inserted into the time_reject_reason table
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");

        for (int i = 0; i < reasons.length; i++) {
            // check the first record
            assertTrue("There should be ten records in time_reject_reason table.", resultSet.next());

            assertEquals("Table time_reject_reason is not updated.", entry.getPrimaryId(),
                resultSet.getInt("Time_Entry_ID"));
            assertEquals("Table time_reject_reason is not updated.", reasons[i].getPrimaryId(),
                resultSet.getInt("reject_reason_id"));
            assertEquals("Table time_reject_reason is not updated.", CREATION_USER, resultSet.getString("creation_user"));
            assertEquals("Table time_reject_reason is not updated.", MODIFICATION_USER,
                resultSet.getString("modification_user"));
            AccuracyTestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getCreationDate(),
                resultSet.getDate("creation_date"));
            AccuracyTestHelper.assertEquals("Table time_reject_reason is not updated.", entry.getModificationDate(),
                resultSet.getDate("modification_date"));
        }

        assertFalse("There should be ten records in time_reject_reason table.", resultSet.next());

        statement.close();
    }

    /**
     * <p>
     * Tests the update(DataObject dataObject, String user).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testUpdate_Accuracy() throws Exception {
        entry = new TimeEntry();
        entry.setDescription(DESCRIPTION);
        entry.setDate(CREATION_DATE);
        entry.setTaskTypeId(type.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        entry.setCompanyId(AccuracyTestHelper.COMPANY_ID);
        timeEntryDAO.create(entry, CREATION_USER);

        entry.setCompanyId(20);
        entry.setTaskTypeId(500);

        type.setCompanyId(20);
        type.setPrimaryId(500);
        AccuracyTestHelper.insertTaskTypes(type, conn, 20);

        timeEntryDAO.update(entry, MODIFICATION_USER);

        // validate
        AccuracyTestHelper.assertEquals("TimeEntry was not properly created.", entry,
            (TimeEntry) timeEntryDAO.get(entry.getPrimaryId()));
    }

    /**
     * <p>
     * Tests the delete(int primaryId). The link of the reject reasons should also be deleted from the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testDelete_Accuracy() throws Exception {
        entry = new TimeEntry();
        entry.setDescription(DESCRIPTION);
        entry.setDate(CREATION_DATE);
        entry.setTaskTypeId(type.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        entry.setCompanyId(AccuracyTestHelper.COMPANY_ID);

        for (int i = 0; i < reasons.length; i++) {
            entry.addRejectReason(reasons[i]);
        }

        timeEntryDAO.create(entry, CREATION_USER);

        timeEntryDAO.delete(entry.getPrimaryId());

        // check whether the record has been added
        List returnTimeEntrys = AccuracyTestHelper.selectTimeEntries(conn);
        assertEquals("record was not properly deleted in the database", 0, returnTimeEntrys.size());

        // The links should also be deleted from the time_reject_reason table
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
        assertFalse("The links should also be deleted from the time_reject_reason table.", resultSet.next());
        statement.close();
    }

    /**
     * Test the accuracy of method getList(String whereClause).
     *
     * @throws Exception if any problem occurs.
     */
    public void testGetList_Accuracy() throws Exception {
        TimeEntry[] timeEntries = new TimeEntry[3];

        for (int i = 0; i < 3; i++) {
            timeEntries[i] = new TimeEntry();
            timeEntries[i].setDescription(DESCRIPTION);
            timeEntries[i].setDate(CREATION_DATE);
            timeEntries[i].setHours(1);
            timeEntries[i].setBillable(true);
            timeEntries[i].setTaskTypeId(type.getPrimaryId());
            timeEntries[i].setTimeStatusId(status.getPrimaryId());
            timeEntries[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);

            if (i == 0) {
                timeEntryDAO.create(timeEntries[i], CREATION_USER);
            } else {
                timeEntryDAO.create(timeEntries[i], MODIFICATION_USER);
            }
        }

        List list = timeEntryDAO.getList("Creation_User='" + MODIFICATION_USER + "'");
        assertEquals("there should be 2 records", 2, list.size());
        list = timeEntryDAO.getList("Creation_User='" + CREATION_USER + "'");
        assertEquals("there should be 1 records", 1, list.size());
        list = timeEntryDAO.getList("   ");
        assertEquals("there should be 3 records", 3, list.size());
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection. All the records
     * should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            AccuracyTestHelper.assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.setConnection(conn);
        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, true, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            AccuracyTestHelper.assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
        assertFalse("The connection should not be closed.", conn.isClosed());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when using the own connection.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
        }

        myTimeEntrys[9].setTaskTypeId(10);

        ResultData resultData = new ResultData();

        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            AccuracyTestHelper.assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in creating module.", exceptionList);
        assertEquals("The exceptionList field should be set in creating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("one should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultData) when not using the own connection.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
        }

        myTimeEntrys[9].setDescription(null);

        ResultData resultData = new ResultData();
        timeEntryDAO.setConnection(conn);
        timeEntryDAO.batchCreate(myTimeEntrys, CREATION_USER, false, resultData);

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            AccuracyTestHelper.assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
        assertFalse("The connection should not be closed.", conn.isClosed());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in creating module.", exceptionList);
        assertEquals("The exceptionList field should be set in creating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("one should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultData).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_Accuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(CREATION_USER);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].removeRejectReason(reasons[i].getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[10 - i - 1]);
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchUpdate(myTimeEntrys, CREATION_USER, true, resultData);

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            AccuracyTestHelper.assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy of batchDelete(int[], boolean, ResultData).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_Accuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // delete it
        int[] ids = new int[myTimeEntrys.length];

        for (int i = 0; i < myTimeEntrys.length; i++) {
            ids[i] = myTimeEntrys[i].getPrimaryId();
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchDelete(ids, true, resultData);

        // assert there is no records in the database now
        assertEquals("record was not properly deleted in the database", 0,
            AccuracyTestHelper.selectTimeEntries(conn).size());

        // The links should also be deleted from the time_reject_reason table
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
        assertFalse("The links should also be deleted from the time_reject_reason table.", resultSet.next());
        statement.close();

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection should be null.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in deleting module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in deleting module.", operations);
        assertEquals("The operations field should be set in deleting module.", operations.length, myTimeEntrys.length);

        Integer[] expected = new Integer[myTimeEntrys.length];

        for (int i = 0; i < myTimeEntrys.length; i++) {
            expected[i] = new Integer(ids[i]);
        }

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in deleting module.", expected[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy of batchRead(int[], boolean, ResultData).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_Accuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);
            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // read it
        int[] ids = new int[myTimeEntrys.length];

        for (int i = 0; i < myTimeEntrys.length; i++) {
            ids[i] = myTimeEntrys[i].getPrimaryId();
        }

        ResultData resultData = new ResultData();
        timeEntryDAO.batchRead(ids, true, resultData);

        // assert there is no records in the database now
        assertEquals("record was not properly deleted in the database", 10,
            AccuracyTestHelper.selectTimeEntries(conn).size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection should be null.", timeEntryDAO.getConnection());

        // check the data in resultData
        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in reading module.", operations);
        assertEquals("The operations field should be set in reading module.", operations.length, myTimeEntrys.length);

        Integer[] expected = new Integer[myTimeEntrys.length];

        for (int i = 0; i < myTimeEntrys.length; i++) {
            expected[i] = new Integer(ids[i]);
        }

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in reading module.", expected[i], operations[i]);
        }

        // check the data in resultData
        assertNotNull("The batchResults field should be set in reading module.", resultData.getBatchResults());

        assertEquals("The batchResults field should be set in reading module.", myTimeEntrys.length,
            resultData.getBatchResults().length);

        for (int i = 0; i < myTimeEntrys.length; i++) {
            AccuracyTestHelper.assertEquals("The batchResults field should be set in reading module.", myTimeEntrys[i],
                (TimeEntry) resultData.getBatchResults()[i]);
        }
    }


}
