/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import java.sql.Connection;
import java.util.Date;

import junit.framework.TestCase;

import com.topcoder.timetracker.entry.time.DAOActionException;
import com.topcoder.timetracker.entry.time.DataObject;
import com.topcoder.timetracker.entry.time.RejectReason;
import com.topcoder.timetracker.entry.time.ResultData;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Failure tests for TimeEntryDAO.
 * </p>
 *
 * <p>
 * In 2.0, new tests added for the company id entity.
 * </p>
 *
 * @author GavinWang
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 */
public class TimeEntryDAO11FailureTests extends TestCase {
    /**
     * TimeEntryDAO instance for testing.
     */
    private TimeEntryDAO dao;

    /**
     * TimeStatusDAO instance for testing.
     */
    private TimeStatusDAO statusDAO;

    /**
     * TimeTaskDAO instance for testing.
     */
    private TaskTypeDAO typeDAO;

    /**
     * The task type used in tests.
     */
    private TaskType task = null;

    /**
     * The time status used in tests.
     */
    private TimeStatus status = null;

    /**
     * The reject reason used in tests.
     */
    private RejectReason rejectReason = null;

    /**
     * <p>
     * Set up tests.
     * </p>
     *
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        ConfigManager.getInstance().add("failure/config.xml");
        dao = new TimeEntryDAO("mysql", "com.topcoder.timetracker.entry.time.failuretests");
        typeDAO = new TaskTypeDAO("mysql", "com.topcoder.timetracker.entry.time.failuretests");
        statusDAO = new TimeStatusDAO("mysql", "com.topcoder.timetracker.entry.time.failuretests");

        Connection conn = FailureTestHelper.getConnection(DBConnectionFactoryImpl.class.getName(), "mysql");

        FailureTestHelper.clearDatabase(conn);
        FailureTestHelper.insertCompany(10, conn);
        FailureTestHelper.insertCompany(20, conn);
        // add reason
        createRejectReason();
        FailureTestHelper.insertRejectReasons(rejectReason, conn, 20);
        conn.close();

        task  = createTaskType(10);
        status = createTimeStatus(10);
        typeDAO.create(task, "tester");
        statusDAO.create(status, "tester");
    }

    /**
     * Create the reject reason.
     */
    private void createRejectReason() {
        rejectReason = new RejectReason();
        rejectReason.setDescription("desc");
        rejectReason.setCreationDate(new Date());
        rejectReason.setModificationDate(new Date());
        rejectReason.setCreationUser("tester");
        rejectReason.setModificationUser("tester");
        rejectReason.setPrimaryId(100);
    }

    /**
     * <p>
     * Tear down tests.
     * </p>
     *
     * @throws Exception to JUnit
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        Connection conn = FailureTestHelper.getConnection(DBConnectionFactoryImpl.class.getName(), "mysql");
        FailureTestHelper.clearDatabase(conn);
        conn.close();
        FailureTestHelper.clearConfiguration();
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with null DataObject, IAE or NPE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateNullDataObject() throws Exception {
        try {
            dao.create(null, "topcoder");
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }

        fail("IllegalArgumentException or NullPointerException should be thrown.");
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with non TimeEntry DataObject, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateNonTimeEntry() throws Exception {
        try {
            dao.create(new DataObject() {
            }, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with TimeEntry DataObject whose description is
     * null, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntryNoDesc() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDate(new Date());
        entry.setHours(2.0f);

        try {
            dao.create(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with TimeEntry DataObject whose date is null, IAE
     * is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntryNoDate() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(2.0f);

        try {
            dao.create(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with TimeEntry DataObject whose hours is illegal,
     * IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateTimeEntryIllegalHours() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDate(new Date());
        entry.setHours(-1.0f);
        entry.setDescription("desc");

        try {
            dao.create(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with null user, IAE or NPE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateNullUser() throws Exception {
        try {
            dao.create(new TimeEntry(), null);
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }

        fail("IllegalArgumentException or NullPointerException should be thrown.");
    }

    /**
     * Failure test for TimeEntryDAO.create(DataObject, String), with empty user, IAE or NPE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testCreateEmptyUser() throws Exception {
        try {
            dao.create(new TimeEntry(), "   ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String), with null DataObject, IAE or NPE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNullDataObject() throws Exception {
        try {
            dao.update(null, "topcoder");
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }

        fail("IllegalArgumentException or NullPointerException should be thrown.");
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String), with non TimeEntry DataObject, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateNonTimeEntry() throws Exception {
        try {
            dao.update(new DataObject() {
            }, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String), with TimeEntry DataObject with no description, IAE
     * is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryNoDesc() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDate(new Date());
        entry.setHours(2.0f);

        try {
            dao.update(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String), with TimeEntry DataObject with no date, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryNoDate() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(2.0f);

        try {
            dao.update(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String), with TimeEntry DataObject with illegal hours, IAE
     * is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryIllegalHours() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(-1.0f);
        entry.setDate(new Date());

        try {
            dao.update(entry, "topcoder");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test forTimeEntryDAO.update(DataObject, String), with null user, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testUpdateTimeEntryNullUser() throws Exception {
        TimeEntry entry = new TimeEntry();
        entry.setDescription("desc");
        entry.setHours(1.0f);
        entry.setDate(new Date());

        try {
            dao.update(entry, null);
        } catch (IllegalArgumentException e) {
            // expected
            return;
        } catch (NullPointerException e) {
            // expected
            return;
        }

        fail("IllegalArgumentException or NullPointerExceptioin should be thrown.");
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData), with null DataObjects, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullDataObjects() throws Exception {
        try {
            dao.batchCreate(null, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData), with empty DataObjects, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchCreateEmptyDataObjects() throws Exception {
        try {
            dao.batchCreate(new DataObject[0], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData), with null DataObject in the array,
     * IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullDataObject() throws Exception {
        try {
            dao.batchCreate(new DataObject[2], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData), with non TimeEntry DataObject in
     * the array, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchCreateNonTimeEntry() throws Exception {
        try {
            dao.batchCreate(new DataObject[] {new DataObject() {
            }}, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData), with null user, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullUser() throws Exception {
        try {
            dao.batchCreate(new DataObject[] {new TimeEntry()}, null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchCreate(DataObject[], String, boolean, ResultData), with null ResultData, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchCreateNullResultData() throws Exception {
        try {
            dao.batchCreate(new DataObject[] {new TimeEntry()}, "topcoder", true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultData), with null idList, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchDeleteNullIdList() throws Exception {
        try {
            dao.batchDelete(null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultData), with empty idList, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchDeleteEmptyIdList() throws Exception {
        try {
            dao.batchDelete(new int[0], true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchDelete(int[], boolean, ResultData), with null ResultData, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchDeleteNullResultData() throws Exception {
        try {
            dao.batchDelete(new int[] {1, 2}, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData), with null DataObjects, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullDataObjects() throws Exception {
        try {
            dao.batchUpdate(null, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData), with empty DataObjects, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchUpdateEmptyDataObjects() throws Exception {
        try {
            dao.batchUpdate(new DataObject[0], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData), with null DataObject, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullDataObject() throws Exception {
        try {
            dao.batchUpdate(new DataObject[2], "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData), with non TimeEntry DataObject, IAE
     * is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNonTimeEntry() throws Exception {
        try {
            dao.batchUpdate(new DataObject[] {new DataObject() {
            }}, "topcoder", true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData), with null user, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullUser() throws Exception {
        try {
            dao.batchUpdate(new DataObject[] {new TimeEntry()}, null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for batchUpdate(DataObject[], String, boolean, ResultData), with null ResultData, IAE is
     * expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchUpdateNullResultData() throws Exception {
        try {
            dao.batchUpdate(new DataObject[] {new TimeEntry()}, "topcoder", true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.batchRead(int[], boolean, ResultData), with null idList, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchReadNullIdList() throws Exception {
        try {
            dao.batchRead(null, true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.batchRead(int[], boolean, ResultData), with empty idList, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchReadEmptyIdList() throws Exception {
        try {
            dao.batchRead(new int[0], true, new ResultData());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test for TimeEntryDAO.batchRead(int[], boolean, ResultData), with null ResultData, IAE is expected.
     *
     * @throws Exception to JUnit
     */
    public void testBatchReadNullResultData() throws Exception {
        try {
            dao.batchRead(new int[] {1, 2}, true, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    // since 2.0

    /**
     * Create TimeEntry object for test proposes.
     *
     * @param companyId the id of the company.
     * @return the TimeEntry instance.
     */
    private TimeEntry createTimeEntry(int companyId) {
        TimeEntry entry = new TimeEntry();
        entry.setCompanyId(companyId);
        entry.setDescription("desc");
        entry.setDate(new Date());
        entry.setHours(10.5f);

        return entry;
    }

    /**
     * Create TaskType object for test proposes.
     *
     * @param companyId the id of the company.
     *
     * @return the TaskType instance.
     */
    private TaskType createTaskType(int companyId) {
        TaskType type = new TaskType();
        type.setCompanyId(companyId);
        type.setDescription("desc");

        return type;
    }

    /**
     * Create TimeStatus object for test proposes.
     *
     * @param id the id of the status.
     *
     * @return the TimeStatus instance.
     */
    private TimeStatus createTimeStatus(int id) {
        TimeStatus status = new TimeStatus();
        status.setPrimaryId(id);
        status.setDescription("desc");

        return status;
    }

    /**
     * Tests the {@link TimeEntryDAO#create(DataObject, String)} method failure.
     * Checks if exception is thrown when companies not match.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateEntry_DifferentCompany() throws Exception {
        TimeEntry entry = createTimeEntry(20);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        try {
            dao.create(entry, "tester");
            fail("Different task type and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#batchCreate(DataObject[], String, boolean, ResultData)} method failure.
     * Checks if exception is thrown when companies not match.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateEntryBatch_DifferentCompany() throws Exception {
        TimeEntry entry = createTimeEntry(20);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        ResultData data = new ResultData();
        try {
            dao.batchCreate(new DataObject[] {entry}, "tester", true, data);
            fail("Different task type and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#update(DataObject, String)} method failure.
     * Checks if exception is thrown when companies not match.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntry_DifferentCompany() throws Exception {
        TimeEntry entry = createTimeEntry(10);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        dao.create(entry, "tester");

        entry.setCompanyId(20);
        try {
            dao.update(entry, "tester");
            fail("Different task type and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#batchUpdate(DataObject[], String, boolean, ResultData)} method failure.
     * Checks if exception is thrown when companies not match.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryBatch_DifferentCompany() throws Exception {
        TimeEntry entry = createTimeEntry(10);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        dao.create(entry, "tester");

        entry.setCompanyId(20);
        ResultData data = new ResultData();
        try {
            dao.batchUpdate(new DataObject[] {entry}, "tester", true, data);
            fail("Different task type and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#create(DataObject, String)} method failure.
     * Checks if exception is thrown when companies not match in reject reason and time entry.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateEntry_DifferentRejectReasonCompany() throws Exception {
        TimeEntry entry = createTimeEntry(10);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());

        entry.addRejectReason(rejectReason);
        try {
            dao.create(entry, "tester");
            fail("Different reject reason and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#batchCreate(DataObject[], String, boolean, ResultData)} method failure.
     * Checks if exception is thrown when companies not match in reject reason and time entry.
     *
     * @throws Exception to JUnit.
     */
    public void testCreateEntryBatch_DifferentRejectReasonCompany() throws Exception {
        TimeEntry entry = createTimeEntry(10);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        ResultData data = new ResultData();
        entry.addRejectReason(rejectReason);
        try {
            dao.batchCreate(new DataObject[] {entry}, "tester", true, data);
            fail("Different reject reason and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#update(DataObject, String)} method failure.
     * Checks if exception is thrown when companies not match in reject reason and time entry.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntry_DifferentRejectReasonCompany() throws Exception {
        TimeEntry entry = createTimeEntry(10);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        dao.create(entry, "tester");

        entry.addRejectReason(rejectReason);
        try {
            dao.update(entry, "tester");
            fail("Different reject reason and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

    /**
     * Tests the {@link TimeEntryDAO#batchUpdate(DataObject[], String, boolean, ResultData)} method failure.
     * Checks if exception is thrown when companies not match in reject reason and time entry.
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateEntryBatch_DifferentRejectReasonCompany() throws Exception {
        TimeEntry entry = createTimeEntry(10);
        entry.setTaskTypeId(task.getPrimaryId());
        entry.setTimeStatusId(status.getPrimaryId());
        ResultData data = new ResultData();
        entry.addRejectReason(rejectReason);
        try {
            dao.batchUpdate(new DataObject[] {entry}, "tester", true, data);
            fail("Different reject reason and time company.");
        } catch (DAOActionException ex) {
            // ok
        }
    }

}
