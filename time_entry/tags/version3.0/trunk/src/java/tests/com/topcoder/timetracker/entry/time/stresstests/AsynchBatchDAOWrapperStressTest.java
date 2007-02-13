package com.topcoder.timetracker.entry.time.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.AsynchBatchDAOWrapper;
import com.topcoder.timetracker.entry.time.DAOFactory;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.RejectReason;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.util.config.ConfigManager;

/**
 * Stress test for class <code>AsynchBatchDAOWrapper</code>.
 * @author fuyun
 * @version 1.1
 */
public class AsynchBatchDAOWrapperStressTest extends TestCase {

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
    private static final String TIME_ENTRY_SELECT_SQL = "select * from Time_Entry";

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
     * The <code>AsynchBatchDAOWrapper</code> instance.
     */
    private AsynchBatchDAOWrapper wrapper = null;

    /**
     * The <code>TimeEntryDAO</code> instance.
     */
    private TimeEntryDAO timeEntryDAO = null;

    /**
     * Represents the <code>DataObject</code> array.
     */
    private DataObject[] dataObjects = null;



    /**
     * Represents the <code>ResultListener</code> instance.
     */
    private StressResultListener listener = null;

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

        listener = new StressResultListener();

        Connection conn = null;

        try {
            conn = StressTestHelper.getConnection(DBNAMESPACE,
                    StressTestHelper.CONNECTION_NAME);

            TimeStatus myTimeStatus = (TimeStatus) StressTestHelper
                    .getTimeStatus(0);
            TaskType myTaskType = (TaskType) StressTestHelper.getTaskType(0);
            StressTestHelper.insertCompany(10, conn);
            StressTestHelper.insertTaskTypes(myTaskType, conn, 10);
            StressTestHelper.insertTimeStatuses(myTimeStatus, conn);

            dataObjects = new DataObject[TIME_ENTRY_NUM];

            TimeEntry timeEntry = new TimeEntry();
            timeEntry.addRejectReason(REJECT_REASON_1);
            timeEntry.addRejectReason(REJECT_REASON_2);
            timeEntry.setDescription(StressTestHelper.DESCRIPTION);
            timeEntry.setDate(StressTestHelper.CREATION_DATE);
            StressTestHelper.insertRejectReason(REJECT_REASON_1, conn, 10);
            StressTestHelper.insertRejectReason(REJECT_REASON_2, conn, 10);
            StressTestHelper.insertRejectReason(StressTestHelper.getRejectReason(3), conn, 10);

            for (int i = 0; i < TIME_ENTRY_NUM; i++) {
                dataObjects[i] = StressTestHelper.getTimeEntry(REJECT_REASON_1,
                        REJECT_REASON_2, 10);
            }

            wrapper = new AsynchBatchDAOWrapper(timeEntryDAO);
            wrapper.batchCreate(dataObjects, StressTestHelper.CREATION_USER,
                    false, listener);

            while (listener.getResultData() == null) {
                Thread.sleep(50);
            }

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
        listener = null;
        StressTestHelper.cleanDB(DBNAMESPACE, StressTestHelper.CONNECTION_NAME);

        if (MANAGER.existsNamespace(NAMESPACE)) {
            MANAGER.removeNamespace(NAMESPACE);
        }
        if (MANAGER.existsNamespace(DBNAMESPACE)) {
            MANAGER.removeNamespace(DBNAMESPACE);
        }

    }

    /**
     * Stress test for
     * <code>batchCreate(DataObject[], String, boolean, ResultListener)</code>.
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
     * Stress test for <code>batchDelete(int[],  boolean, ResultListener)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchDelete() throws Exception {

        listener = new StressResultListener();

        int idList[] = new int[TIME_ENTRY_NUM];

        for (int i = 0; i < TIME_ENTRY_NUM; i++) {
            idList[i] = dataObjects[i].getPrimaryId();
        }
        wrapper.batchDelete(idList, false, listener);

        while (listener.getResultData() == null) {
            Thread.sleep(50);
        }

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
     * <code>batchUpdate(DataObject[], String, boolean, ResultListener)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchUpdate() throws Exception {

        RejectReason rejectReason = StressTestHelper.getRejectReason(3);
        for (int i = 0; i < TIME_ENTRY_NUM; i++) {
            ((TimeEntry) dataObjects[i]).removeRejectReason(REJECT_REASON_1
                    .getPrimaryId());
            ((TimeEntry) dataObjects[i]).addRejectReason(rejectReason);
        }

        listener = new StressResultListener();

        wrapper.batchUpdate(dataObjects, StressTestHelper.CREATION_USER, false,
                listener);

        while (listener.getResultData() == null) {
            Thread.sleep(50);
        }

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
     * Stress test for <code>batchRead(int[], boolean, ResultListener)</code>.
     * @throws Exception when there is unexpected exception.
     */
    public void testBatchRead() throws Exception {

        int idList[] = new int[TIME_ENTRY_NUM];

        for (int i = 0; i < TIME_ENTRY_NUM; i++) {
            idList[i] = dataObjects[i].getPrimaryId();
        }

        listener = new StressResultListener();

        wrapper.batchRead(idList, false, listener);

        while (listener.getResultData() == null) {
            Thread.sleep(50);
        }

        assertEquals("Fail to batch read.", TIME_ENTRY_NUM, listener
                .getResultData().getBatchResults().length);
    }

}
