/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import junit.framework.TestCase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Unit test cases for AsynchBatchDAOWrapper.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1AsynchBatchDAOWrapperUnitTest extends TestCase {
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
     * Represents the date that created this record.
     * </p>
     */
    private static final Date CREATION_DATE = V1Dot1TestHelper.createDate(2006, 1, 1);

    /**
     * <p>
     * Represents the date that last modified this record.
     * </p>
     */
    private static final Date MODIFICATION_DATE = V1Dot1TestHelper.createDate(2006, 2, 1);

    /** Represents a valid time task type instance. */
    private TaskType type;

    /** Represents a valid time status instance. */
    private TimeStatus status;

    /** Represents a valid time reject reason instance. */
    private RejectReason reason1;

    /** Represents a valid time reject reason instance. */
    private RejectReason reason2;

    /**
     * <p>
     * The TimeEntryDAO instance for testing.
     * </p>
     */
    private BaseDAO timeEntryDAO = null;

    /**
     * <p>
     * The AsynchBatchDAOWrapper instance for testing.
     * </p>
     */
    private AsynchBatchDAOWrapper wrapper = null;

    /**
     * <p>
     * The MockResultListener instance for testing.
     * </p>
     */
    private MockResultListener listener = null;

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
        listener = new MockResultListener();

        // load the configuration
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        Connection conn = null;

        try {
            // delete all the records in all tables
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);
            V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a');", conn);

            // Insert an task type
            type = new TaskType();
            type.setCompanyId(1);
            type.setPrimaryId(1);
            type.setDescription("taskType");
            type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
            type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
            type.setCreationUser("taskTypeCreate");
            type.setModificationUser("taskTypeModification");

            V1Dot1TestHelper.insertTaskTypes(type, conn);

            // Insert the time status
            status = new TimeStatus();

            status.setPrimaryId(2);
            status.setDescription("timeStatus");
            status.setCreationDate(V1Dot1TestHelper.createDate(2005, 3, 1));
            status.setModificationDate(V1Dot1TestHelper.createDate(2005, 4, 1));
            status.setCreationUser("timeStatusCreate");
            status.setModificationUser("timeStatusModification");

            V1Dot1TestHelper.insertTimeStatuses(status, conn);

            // Insert the first reject reason
            reason1 = new RejectReason();
            reason1.setPrimaryId(3);
            reason1.setDescription("reason1");
            reason1.setCreationDate(V1Dot1TestHelper.createDate(2005, 5, 1));
            reason1.setModificationDate(V1Dot1TestHelper.createDate(2005, 6, 1));
            reason1.setCreationUser("reason1Create");
            reason1.setModificationUser("reason1Modification");
            V1Dot1TestHelper.insertRejectReasons(reason1, conn);

            reason2 = new RejectReason();
            reason2.setPrimaryId(4);
            reason2.setDescription("reason2");
            reason2.setCreationDate(V1Dot1TestHelper.createDate(2005, 7, 1));
            reason2.setModificationDate(V1Dot1TestHelper.createDate(2005, 8, 1));
            reason2.setCreationUser("reason2Create");
            reason2.setModificationUser("reason2Modification");
            V1Dot1TestHelper.insertRejectReasons(reason2, conn);

            V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1,3,current,'a', current,'a');", conn);
            V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1,4,current,'a', current,'a');", conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }

        timeEntryDAO = new TimeEntryDAO(CONNAME, NAMESPACE);
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
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

        V1Dot1TestHelper.clearConfiguration();
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given dataObjects is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NullDataObjects() throws Exception {
        try {
            wrapper.batchCreate(null, "create", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given dataObjects is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_EmptyDataObjects() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[0], "create", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given dataObjects contains null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_DataObjectsContainsNull() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] { null }, "create", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given dataObjects contains invalid
     * type.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_DataObjectsContainsInvalidType() throws Exception {
        wrapper.batchCreate(new DataObject[] { new TimeStatus() }, "Create", true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given user is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NullUser() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] { new TimeEntry() }, null, true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given user is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_EmptyUser() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] { new TimeEntry() }, " ", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the given resultListener is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NullResultListener() throws Exception {
        try {
            wrapper.batchCreate(new DataObject[] { new TimeEntry() }, "create", true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin1() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchCreate(new DataObject[] { this.getTimeEntry() }, "Create", true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin2() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchCreate(new DataObject[] { this.getTimeEntry() }, "Create", true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin3() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchCreate(new DataObject[] { this.getTimeEntry() }, "Create", false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_ErrorConnectoin4() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchCreate(new DataObject[] { this.getTimeEntry() }, "Create", false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        wrapper.batchCreate(myTimeEntrys, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
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
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        wrapper.batchCreate(myTimeEntrys, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        wrapper.batchCreate(myTimeEntrys, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();
        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when not using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);
            wrapper.batchCreate(myTimeEntrys, CREATION_USER, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
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
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when not using the own connection. The
     * error occurs in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            wrapper.batchCreate(myTimeEntrys, CREATION_USER, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            assertNotNull("Global exception should exist.",
                listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the adding process, BatchOperationException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_InAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            wrapper.batchCreate(myTimeEntrys, CREATION_USER, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            assertNotNull("Global exception should exist.",
                listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 0, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        wrapper.batchCreate(myTimeEntrys, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
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
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        wrapper.batchCreate(myTimeEntrys, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
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

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        wrapper.batchCreate(myTimeEntrys, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the added result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
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

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when not using the own connection. All the
     * records should be added into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);
            wrapper.batchCreate(myTimeEntrys, CREATION_USER, false, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
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
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when not using the own connection. The
     * error occurs in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);

            wrapper.batchCreate(myTimeEntrys, CREATION_USER, false, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
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

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchCreate(DataObject[], String, boolean, ResultListener) when not using the own connection. The
     * error occurs in the adding process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchCreate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);

            wrapper.batchCreate(myTimeEntrys, CREATION_USER, false, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the added result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 9, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
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

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }

            // check the data in resultData
            assertNull("The batchResults field should not be set in creating module.", resultData.getBatchResults());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given dataObjects is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NullDataObjects() throws Exception {
        try {
            wrapper.batchUpdate(null, "create", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given dataObjects is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_EmptyDataObjects() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[0], "Create", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given dataObjects contains null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_DataObjectsContainsNull() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[] { null }, "Create", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given dataObjects contains invalid
     * type.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_DataObjectsContainsInvalidType() throws Exception {
        wrapper.batchUpdate(new DataObject[] { new TimeEntry() }, "user", true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given user is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NullUser() throws Exception {
        try {
            timeEntryDAO.batchUpdate(new DataObject[] { new TimeEntry() }, null, true, new ResultData());
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given user is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_EmptyUser() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[] { new TimeEntry() }, " ", true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the given listener is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NullListener() throws Exception {
        try {
            wrapper.batchUpdate(new DataObject[] { new TimeEntry() }, "Create", true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin1() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin2() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin3() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_ErrorConnectoin4() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchUpdate(new DataObject[] { this.getTimeEntry() }, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. All the
     * records should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set properly.", exceptionList);
        assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertNotNull("Exception should be occur.", exceptionList[9]);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in creating module.", operations);
        assertEquals("The operations field should be set in creating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when not using the own connection. All the
     * records should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);
            this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when not using the own connection. The
     * error occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_InAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            timeEntryDAO.setConnection(conn);

            this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set properly.", exceptionList);
            assertEquals("The exceptionList field should be set properly.", myTimeEntrys.length, exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertNotNull("Exception should be occur.", exceptionList[9]);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in creating module.", operations);
            assertEquals("The operations field should be set in creating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in creating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. All the
     * records should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomUseOwnConnectionAccuracy() throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 10; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNull("No exceptions should be occur.", exceptionList);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, myTimeEntrys.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setTaskTypeId(10);

        this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
        assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when using the own connection. The error
     * occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        TimeEntry[] myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setCompanyId(1);
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(CREATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason2);
            }

            timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
        }

        // update it
        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i].setDescription(DESCRIPTION);
            myTimeEntrys[i].setDate(MODIFICATION_DATE);
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

            if ((i % 2) == 0) {
                myTimeEntrys[i].addRejectReason(reason1);
            }

            if ((i % 2) == 1) {
                myTimeEntrys[i].addRejectReason(reason2);
            }
        }

        myTimeEntrys[9].setDescription(null);

        this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        ResultData resultData = listener.getResultData();

        // check the updated result
        List returnTimeEntrys = timeEntryDAO.getList(null);
        assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

        for (int i = 0; i < 9; i++) {
            assertEquals("not correctly record returned from the TimeEntries table",
                (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
        }

        // check the baseDAO's connection field
        assertNull("The baseDAO's connection field should be set back.", timeEntryDAO.getConnection());

        // check the data in resultData
        assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

        BatchOperationException[] exceptionList = resultData.getExceptionList();
        assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
        assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
            exceptionList.length);

        for (int i = 0; i < (exceptionList.length - 1); i++) {
            assertNull("No exceptions should be occur.", exceptionList[i]);
        }

        assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

        Object[] operations = resultData.getOperations();
        assertNotNull("The operations field should be set in updating module.", operations);
        assertEquals("The operations field should be set in updating module.", operations.length, exceptionList.length);

        for (int i = 0; i < operations.length; i++) {
            assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when not using the own connection. All the
     * records should be updated into the database.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomNotUseOwnConnectionAccuracy() throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);
            this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, false, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 10; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                myTimeEntrys.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when not using the own connection. The
     * error occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy1()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setTaskTypeId(10);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);

            this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, false, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
            assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
                exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof DAOActionException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchUpdate(DataObject[], String, boolean, ResultListener) when not using the own connection. The
     * error occurs in the updating process.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchUpdate_NotInAtomNotUseOwnConnectionErrorOccurAccuracy2()
        throws Exception {
        Connection conn = null;

        try {
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // update it
            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(MODIFICATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());

                if ((i % 2) == 0) {
                    myTimeEntrys[i].addRejectReason(reason1);
                }

                if ((i % 2) == 1) {
                    myTimeEntrys[i].addRejectReason(reason2);
                }
            }

            myTimeEntrys[9].setDescription(null);

            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            timeEntryDAO.setConnection(conn);

            this.wrapper.batchUpdate(myTimeEntrys, CREATION_USER, false, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // check the updated result
            List returnTimeEntrys = timeEntryDAO.getList(null);
            assertEquals("not correctly record returned from the TimeEntries table", 10, returnTimeEntrys.size());

            for (int i = 0; i < 9; i++) {
                assertEquals("not correctly record returned from the TimeEntries table",
                    (TimeEntry) returnTimeEntrys.get(i), myTimeEntrys[i]);
            }

            // check the baseDAO's connection field
            assertEquals("The baseDAO's connection field should be set back.", conn, timeEntryDAO.getConnection());
            assertFalse("The connection should not be closed.", conn.isClosed());

            // check the data in resultData
            assertNull("The batchResults field should not be set in updating module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNotNull("The exceptionList field should be set in updating module.", exceptionList);
            assertEquals("The exceptionList field should be set in updating module.", myTimeEntrys.length,
                exceptionList.length);

            for (int i = 0; i < (exceptionList.length - 1); i++) {
                assertNull("No exceptions should be occur.", exceptionList[i]);
            }

            assertTrue("one should be occur.", exceptionList[9].getCause() instanceof IllegalArgumentException);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in updating module.", operations);
            assertEquals("The operations field should be set in updating module.", operations.length,
                exceptionList.length);

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in updating module.", myTimeEntrys[i], operations[i]);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the given idList is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_NullIdList() throws Exception {
        try {
            wrapper.batchDelete(null, true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the given idList is empty, IllegalArgumentException
     * is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_EmptyIdList() throws Exception {
        try {
            wrapper.batchDelete(new int[0], true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the given ResultListener is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_NullResultListener() throws Exception {
        try {
            wrapper.batchDelete(new int[] { 1 }, true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin1() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchDelete(new int[1], true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin2() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchDelete(new int[1], true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin3() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchDelete(new int[1], false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchDelete(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_ErrorConnectoin4() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchDelete(new int[1], false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the accuracy of batchDelete(int[], boolean, ResultListener).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchDelete_Accuracy() throws Exception {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myTimeEntrys[i].addRejectReason(reason1);
                myTimeEntrys[i].addRejectReason(reason2);

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // delete it
            int[] ids = new int[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                ids[i] = myTimeEntrys[i].getPrimaryId();
            }

            wrapper.batchDelete(ids, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // assert there is no records in the database now
            assertEquals("record was not properly deleted in the database", 0,
                V1Dot1TestHelper.selectTimeEntries(conn).size());

            // The links should also be deleted from the time_reject_reason table
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_reject_reason order by reject_reason_id");
            assertFalse("The links should also be deleted from the time_reject_reason table.", resultSet.next());

            // check the baseDAO's connection field
            assertNull("The baseDAO's connection should be null.", timeEntryDAO.getConnection());

            // check the data in resultData
            assertNull("The batchResults field should not be set in deleting module.", resultData.getBatchResults());

            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in deleting module.", operations);
            assertEquals("The operations field should be set in deleting module.", operations.length,
                myTimeEntrys.length);

            Integer[] expected = new Integer[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                expected[i] = new Integer(ids[i]);
            }

            for (int i = 0; i < operations.length; i++) {
                assertEquals("The operations field should be set in deleting module.", expected[i], operations[i]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(resultSet, statement, conn);
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the given idList is null, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_NullIdList() throws Exception {
        try {
            wrapper.batchRead(null, true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the given idList is empty, IllegalArgumentException is
     * expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_EmptyIdList() throws Exception {
        try {
            wrapper.batchRead(new int[0], true, listener);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the given ResultListener is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_NullResultListener() throws Exception {
        try {
            wrapper.batchRead(new int[] { 1 }, true, null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin1() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchRead(new int[1], true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin2() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchRead(new int[1], true, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin3() throws Exception {
        timeEntryDAO = new TimeEntryDAO(CONNAME, "NotCorrect");
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchRead(new int[1], false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the batchRead(int[], boolean, ResultListener) when the connection can not be got.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_ErrorConnectoin4() throws Exception {
        timeEntryDAO = new TimeEntryDAO("NotCorrect", NAMESPACE);
        this.wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
        wrapper.batchRead(new int[1], false, listener);

        while (!listener.isFinished()) {
            Thread.sleep(10);
        }

        assertNotNull("Global exception should exist.",
            listener.getResultData().getProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY));
    }

    /**
     * <p>
     * Tests the accuracy of batchRead(int[], boolean, ResultListener).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testBatchRead_Accuracy() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myTimeEntrys[i].addRejectReason(reason1);
                myTimeEntrys[i].addRejectReason(reason2);

                timeEntryDAO.create(myTimeEntrys[i], CREATION_USER);
            }

            // read it
            int[] ids = new int[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                ids[i] = myTimeEntrys[i].getPrimaryId();
            }

            wrapper.batchRead(ids, true, listener);

            while (!listener.isFinished()) {
                Thread.sleep(10);
            }

            ResultData resultData = listener.getResultData();

            // assert there is no records in the database now
            assertEquals("record was not properly deleted in the database", 10,
                V1Dot1TestHelper.selectTimeEntries(conn).size());

            // check the baseDAO's connection field
            assertNull("The baseDAO's connection should be null.", timeEntryDAO.getConnection());

            // check the data in resultData
            BatchOperationException[] exceptionList = resultData.getExceptionList();
            assertNull("No exceptions should be occur.", exceptionList);

            Object[] operations = resultData.getOperations();
            assertNotNull("The operations field should be set in reading module.", operations);
            assertEquals("The operations field should be set in reading module.",
                operations.length, myTimeEntrys.length);

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
                assertEquals("The batchResults field should be set in reading module.", myTimeEntrys[i],
                    (TimeEntry) resultData.getBatchResults()[i]);
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * return a TimeEntry instance for testing.
     * </p>
     *
     * @return a TimeEntry instance for testing.
     */
    private TimeEntry getTimeEntry() {
        TimeEntry myTimeEntry = new TimeEntry();

        myTimeEntry.setCompanyId(1);
        myTimeEntry.setDescription(DESCRIPTION);
        myTimeEntry.setCreationUser(CREATION_USER);
        myTimeEntry.setCreationDate(CREATION_DATE);
        myTimeEntry.setModificationUser(CREATION_USER);
        myTimeEntry.setModificationDate(CREATION_DATE);
        myTimeEntry.setDate(CREATION_DATE);
        myTimeEntry.setTaskTypeId(type.getPrimaryId());
        myTimeEntry.setTimeStatusId(status.getPrimaryId());

        return myTimeEntry;
    }

    /**
     * <p>
     * judge whether the two TimeEntry are equals. They are equals when all their fields contain the same value.
     * </p>
     *
     * @param message the error message when the two entry are not equal.
     * @param expected the expected TimeEntry.
     * @param actual the actual TimeEntry.
     */
    private void assertEquals(String message, TimeEntry expected, TimeEntry actual) {
        assertEquals(message, expected.getPrimaryId(), actual.getPrimaryId());
        assertEquals(message, expected.getCompanyId(), actual.getCompanyId());
        assertEquals(message, expected.getTaskTypeId(), actual.getTaskTypeId());
        assertEquals(message, expected.getTimeStatusId(), actual.getTimeStatusId());
        V1Dot1TestHelper.assertEquals(message, expected.getDate(), actual.getDate());
        assertEquals(message, expected.getHours(), actual.getHours(), 1e-8);
        assertEquals(message, expected.isBillable(), actual.isBillable());
        assertEquals(message, expected.getDescription(), actual.getDescription());
        assertEquals(message, expected.getCreationUser(), actual.getCreationUser());
        V1Dot1TestHelper.assertEquals(message, expected.getCreationDate(), actual.getCreationDate());
        assertEquals(message, expected.getModificationUser(), actual.getModificationUser());
        V1Dot1TestHelper.assertEquals(message, expected.getModificationDate(), actual.getModificationDate());
        assertEquals(message, expected.getAllRejectReasons().length, actual.getAllRejectReasons().length);
    }
}
