/**
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DAOFactory;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.RejectReason;
import com.topcoder.timetracker.entry.time.ResultData;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Stress test for class <code>TimeEntryDAO</code>.
 * </p>
 * <p>
 * Here we focus on the five batch operations. Because the batch operations
 * invoke the create/delete/update/get/getlist method, so the test cases will
 * also contain test for other methods.
 * </p>
 * <p>
 * The class is designed thread-safe, we will not do multi-thread testing here.
 * The efficiency of the code is be tested here by using large numbers of data.
 * </p>
 * @author fuyun
 * @version 1.1
 */
public class TimeEntryDAOStressTest extends TestCase {

    /**
     * Represents the namespace used to load the configuration.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.entry.time.stress";

    /**
     * Represents the namespace used to load the configuration for DB Connection
     * Factory.
     */
    private static final String DBNAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Represents the config file name which is used to load the config info.
     */
    private static final String CONFIG_FILE = "stress/StressConfig.xml";

    /**
     * Represents the <code>ConfigManager</code> insatnce.
     */
    private static final ConfigManager MANAGER = ConfigManager.getInstance();

    /**
     * Represents the <code>RejectReason</code>.
     */
    private static final RejectReason REJECT_REASON_1 = StressTestHelper
            .getRejectReason(1);

    /**
     * Represents the <code>RejectReason</code>.
     */
    private static final RejectReason REJECT_REASON_2 = StressTestHelper
            .getRejectReason(2);

    /**
     * Represents the SQL statement to select records from TimeEntries table.
     */
    private static final String TIME_ENTRY_SELECT_SQL = "select * from time_entry";

    /**
     * Represents the SQL statement to select records from time_reject_reason
     * table.
     */
    private static final String TIME_REJECT_REASON_SELECT_SQL = "select * from time_reject_reason";

    /**
     * Represents the number of <code>TimeEntry</code> instances.
     */
    private static final int TIME_ENTRY_NUM = 10;

    /**
     * The <code>TimeEntryDAO</code> instance.
     */
    private TimeEntryDAO timeEntryDAO = null;

    /**
     * Represents the <code>DataObject</code> array.
     */
    private DataObject[] dataObjects = null;

    /**
     * Represents the <code>ResultData</code> instance.
     */
    private ResultData resultData = null;

    /**
     * <p>
     * Set the test env.
     * </p>
     * <p>
     * The namespace is refreshed and the Database is prepared for test.
     * </p>
     * @throws Exception if there is any unexpected exception.
     */
    protected void setUp() throws Exception {
        if (MANAGER.existsNamespace(NAMESPACE)) {
            MANAGER.removeNamespace(NAMESPACE);
        }
        if (MANAGER.existsNamespace(DBNAMESPACE)) {
            MANAGER.removeNamespace(DBNAMESPACE);
        }
        MANAGER.add(CONFIG_FILE);

        StressTestHelper.cleanDB(DBNAMESPACE, StressTestHelper.CONNECTION_NAME);

        timeEntryDAO = (TimeEntryDAO) DAOFactory.getDAO(TimeEntry.class,
                NAMESPACE);

        Connection conn = null;

        try {
            conn = StressTestHelper.getConnection(DBNAMESPACE,
                    StressTestHelper.CONNECTION_NAME);

            TimeStatus myTimeStatus = (TimeStatus) StressTestHelper
                    .getTimeStatus(0);
            TaskType myTaskType = (TaskType) StressTestHelper.getTaskType(0);

            StressTestHelper.insertTimeStatuses(myTimeStatus, conn);
            StressTestHelper.insertCompany(10, conn);
            StressTestHelper.insertCompany(20, conn);
            StressTestHelper.insertTaskTypes(myTaskType, conn, 10);

            StressTestHelper.insertRejectReason(REJECT_REASON_1, conn, 10);
            StressTestHelper.insertRejectReason(REJECT_REASON_2, conn, 10);
            StressTestHelper.insertRejectReason(StressTestHelper.getRejectReason(3), conn, 10);

            dataObjects = new DataObject[TIME_ENTRY_NUM];

            for (int i = 0; i < TIME_ENTRY_NUM; i++) {
                dataObjects[i] = StressTestHelper.getTimeEntry(REJECT_REASON_1, REJECT_REASON_2, 10);
                ((TimeEntry) dataObjects[i]).setTaskTypeId(myTaskType.getPrimaryId());
            }

            resultData = new ResultData();
            timeEntryDAO.batchCreate(dataObjects,
                    StressTestHelper.CREATION_USER, true, resultData);

        } finally {
            StressTestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Clean up the test env.
     * </p>
     * <p>
     * Remove the namespace and clean up the Database.
     * </p>
     * @throws Exception if there is any unexpected exception.
     */
    protected void tearDown() throws Exception {
        timeEntryDAO = null;
        dataObjects = null;

        StressTestHelper.cleanDB(DBNAMESPACE, StressTestHelper.CONNECTION_NAME);

        if (MANAGER.existsNamespace(NAMESPACE)) {
            MANAGER.removeNamespace(NAMESPACE);
        }
        if (MANAGER.existsNamespace(DBNAMESPACE)) {
            MANAGER.removeNamespace(DBNAMESPACE);
        }

    }

    /**
     * Stress test for method
     * <code>batchCreate(DataObject[], String, boolean, ResultData)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchCreate() throws Exception {
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;
        try {
            conn = StressTestHelper.getConnection(DBNAMESPACE,
                    StressTestHelper.CONNECTION_NAME);
            pstmt = conn.prepareStatement(TIME_ENTRY_SELECT_SQL);

            resultSet = pstmt.executeQuery();

            int resultNum = 0;

            while (resultSet.next()) {
                resultNum++;
            }

            assertEquals("Fail to create object into database.",
                    TIME_ENTRY_NUM, resultNum);

        } finally {
            StressTestHelper.closeResources(resultSet, pstmt, conn);
        }
    }

    /**
     * Stress test for <code>batchDelete(int[], boolean, ResultData)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchDelete() throws Exception {

        int idList[] = new int[TIME_ENTRY_NUM];

        for (int i = 0; i < TIME_ENTRY_NUM; i++) {
            idList[i] = dataObjects[i].getPrimaryId();
        }

        timeEntryDAO.batchDelete(idList, false, resultData);
        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;

        try {


            conn = StressTestHelper.getConnection(DBNAMESPACE,
                    StressTestHelper.CONNECTION_NAME);
            pstmt = conn.prepareStatement(TIME_ENTRY_SELECT_SQL);

            resultSet = pstmt.executeQuery();

            assertFalse("Fail to delete Time Entry.", resultSet.next());

            pstmt = conn.prepareStatement(TIME_REJECT_REASON_SELECT_SQL);

            resultSet = pstmt.executeQuery();

            assertFalse("Fail to delete link.", resultSet.next());

        } finally {
            StressTestHelper.closeResources(resultSet, pstmt, conn);
        }

    }

    /**
     * Stress test for
     * <code>batchUpdate(DataObject[], String, boolean, ResultData)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchUpdate() throws Exception {
        RejectReason rejectReason = StressTestHelper.getRejectReason(3);

        for (int i = 0; i < TIME_ENTRY_NUM; i++) {
            ((TimeEntry) dataObjects[i]).removeRejectReason(REJECT_REASON_1
                    .getPrimaryId());
            ((TimeEntry) dataObjects[i]).addRejectReason(rejectReason);
        }

        timeEntryDAO.batchUpdate(dataObjects, StressTestHelper.CREATION_USER,
                true, resultData);

        Connection conn = null;
        ResultSet resultSet = null;
        PreparedStatement pstmt = null;

        try {

            conn = StressTestHelper.getConnection(DBNAMESPACE,
                    StressTestHelper.CONNECTION_NAME);
            pstmt = conn.prepareStatement(TIME_REJECT_REASON_SELECT_SQL);

            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                assertTrue("Fail to update", 1 != resultSet
                        .getInt("reject_reason_id"));
            }

        } finally {
            StressTestHelper.closeResources(resultSet, pstmt, conn);
        }

    }

    /**
     * Stress Test for <code>batchRead(int[], boolean, ResultData)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchRead() throws Exception {

        int idList[] = new int[TIME_ENTRY_NUM];

        for (int i = 0; i < TIME_ENTRY_NUM; i++) {
            idList[i] = dataObjects[i].getPrimaryId();
        }
        timeEntryDAO.batchRead(idList, false, resultData);

        assertEquals("Fail to batch read.", TIME_ENTRY_NUM, resultData
                .getBatchResults().length);

    }

}
