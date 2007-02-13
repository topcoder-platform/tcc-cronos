/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.timetracker.entry.time.search.BooleanExpression;
import com.topcoder.timetracker.entry.time.search.ComparisonExpression;
import com.topcoder.timetracker.entry.time.search.ExpressionEvaluator;
import com.topcoder.timetracker.entry.time.search.RangeExpression;
import com.topcoder.timetracker.entry.time.search.SQLBasedCriteriaExpressionEvaluator;
import com.topcoder.timetracker.entry.time.search.SearchExpression;
import com.topcoder.timetracker.entry.time.search.SubstringExpression;
import com.topcoder.timetracker.entry.time.search.TimeEntryCriteria;

import junit.framework.TestCase;

import java.sql.Connection;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * Component demonstration for Time_Entry. It will also show the usage of V1.0.
 * </p>
 *
 * <p>
 * The Time Entry component is part of the Time Tracker application. It provides an abstraction of a time log entry
 * that an employee enters into the system on a regular basis. This component handles the logic of persistence for
 * this.
 * </p>
 *
 * <p>
 * This demo is separated into seven parts.
 * </p>
 *
 * <p>
 * The first part demonstrates the CRUD with TaskType and TaskTypeDAO.
 * </p>
 *
 * <p>
 * The second part demonstrates the CRUD with TimeStatus and TimeStatusDAO.
 * </p>
 *
 * <p>
 * The third part demonstrates the CRUD with TimeEntry and TimeEntryDAO.
 * </p>
 *
 * <p>
 * The fourth part demonstrates using with local transaction control.
 * </p>
 *
 * <p>
 * The fifth part demonstrates managing Synchronous Batch operations.
 * </p>
 *
 * <p>
 * The sixth part demonstrates managing Asynchronous Batch operations.
 * </p>
 *
 * <p>
 * The last part demonstrates the search operations.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class V1Dot1DemoTest extends TestCase {
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

    /**
     * <p>
     * Represents the user that modified this record.
     * </p>
     */
    private static final String MODIFICATION_USER = "TCSDESIGNER";

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
        V1Dot1TestHelper.clearConfiguration();
        V1Dot1TestHelper.addValidConfiguration();

        Connection conn = null;

        try {
            // delete all the records in all tables
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);
            V1Dot1TestHelper.clearDatabase(conn);

            V1Dot1TestHelper.executeSQL("insert into company values(1, 'a', 'a', current, 'a', current, 'a');", conn);
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
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
     * Demonstrates the CRUD with TaskType and TaskTypeDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTaskTypeDAODemo() throws Exception {
        // Create a TaskType
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("Component Specification");

        // Get the DAO
        DAO myDAO = DAOFactory.getDAO(TaskType.class, NAMESPACE);

        // Create a record.
        myDAO.create(type, "ivern");

        // update some info
        type.setDescription("Component Spellification");
        myDAO.update(type, "TangentZ");
        System.out.println(type.getDescription());

        // to do searches:
        // formulate a where clause
        String whereClause = "task_type.creation_user=\'ivern\'";

        // Get TaskTypes for a range of values
        List types = myDAO.getList(whereClause);
        assertEquals(1, types.size());
        System.out.println(((TaskType) types.get(0)).getCreationUser());
        System.out.println(((TaskType) types.get(0)).getModificationUser());

        // Or, get a specific Task Type and see some values
        int id = type.getPrimaryId();
        TaskType myType = (TaskType) myDAO.get(id);

        // make sure the refernece delete.

        // to do deletes:
        // Delete the record with the given Id
        myDAO.delete(id);
        assertEquals(0, myDAO.getList(null).size());
    }

    /**
     * <p>
     * Demonstrates the CRUD with TimeStatus  and TimeStatusDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeStatusDAODemo() throws Exception {
        // Create a TimeStatus
        TimeStatus status = new TimeStatus();
        status.setDescription("Reaby");

        // Get the DAO
        DAO myDAO = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);

        // Create a record.
        myDAO.create(status, "ivern");

        // update some info
        status.setDescription("Ready");
        myDAO.update(status, "TangentZ");
        System.out.println(status.getDescription());

        // to do searches:
        // formulate a where clause
        String whereClause = "creation_user = \'ivern\'";

        // Get TimeStatuses for a range of values
        List statuses = myDAO.getList(whereClause);
        assertEquals(1, statuses.size());
        System.out.println(((TimeStatus) statuses.get(0)).getCreationUser());
        System.out.println(((TimeStatus) statuses.get(0)).getModificationUser());

        // Or, get a specific TimeStatus and see some values
        int id = status.getPrimaryId();
        TimeStatus myStatus = (TimeStatus) myDAO.get(id);

        // to do deletes:
        // Delete the record with the given Id
        myDAO.delete(id);
        assertEquals(0, myDAO.getList(null).size());
    }

    /**
     * <p>
     * Demonstrates the CRUD with TimeEntry and TimeEntryDAO.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testTimeEntryDAODemo() throws Exception {
        Connection conn = null;

        // add task type.
        TaskType type = new TaskType();
        type.setCompanyId(1);
        type.setDescription("Component Specification");
        DAO myDAO = DAOFactory.getDAO(TaskType.class, NAMESPACE);
        myDAO.create(type, "ivern");

        // add time status
        TimeStatus status = new TimeStatus();
        status.setDescription("Reaby");
        myDAO = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);
        myDAO.create(status, "ivern");

        try {
            // Create a TimeEntry
            TimeEntry entry = new TimeEntry();
            entry.setCompanyId(1);
            entry.setDescription("Coding class zec");
            entry.setDate(new Date());
            entry.setHours(2.5F);
            entry.setBillable(true);
            entry.setTaskTypeId(type.getPrimaryId());
            entry.setTimeStatusId(status.getPrimaryId());
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // Get the DAO
            myDAO = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);

            // Create a record. assume key generated = 1
            myDAO.create(entry, "ivern");

            // update some info
            entry.setHours(3.5F);
            myDAO.update(entry, "ivern");
            System.out.println(entry.getDescription());

            // to do searches:
            // formulate a where clause
            String whereClause = "creation_user = \'ivern\'";

            // Get TimeEntries for a range of values
            List entries = myDAO.getList(whereClause);
            System.out.println(((TimeEntry) entries.get(0)).getCreationUser());
            System.out.println(((TimeEntry) entries.get(0)).getModificationUser());

            // Or, get a specific TimeEntry and see some values
            int id = entry.getPrimaryId();
            TimeEntry myEntry = (TimeEntry) myDAO.get(id);
            System.out.println(myEntry.getDescription());
            System.out.println(myEntry.getHours());
            System.out.println(myEntry.isBillable());
            System.out.println(myEntry.getModificationUser());

            // to do deletes:
            // Delete the record with the given Id
            myDAO.delete(id);
            assertEquals(0, myDAO.getList(null).size());

            // Here is the Reject reason operations
            // We create a new rejection reason and set its values
            // before we add it to a time entry
            RejectReason reason1 = new RejectReason();
            reason1.setPrimaryId(3);
            reason1.setDescription("20 hours for a demo? Don¡¯t think so");
            reason1.setCreationDate(V1Dot1TestHelper.createDate(2005, 5, 1));
            reason1.setModificationDate(V1Dot1TestHelper.createDate(2005, 6, 1));
            reason1.setCreationUser("reason1Create");
            reason1.setModificationUser("reason1Modification");
            V1Dot1TestHelper.insertRejectReasons(reason1, conn);

            // make sure the reject is associated with the company
            V1Dot1TestHelper.executeSQL("insert into comp_rej_reason values(1,3,current,'a', current,'a');", conn);

            // add the reason to some time entry
            entry.addRejectReason(reason1);

            // persist the entry
            myDAO.create(entry, "Ivern");

            // get all the rejection reasons for some time entry
            myEntry = (TimeEntry) myDAO.getList(null).get(0);

            RejectReason[] reasons = myEntry.getAllRejectReasons();

            // or we can ask for only one reason if we have the id
            RejectReason someReason = myEntry.getRejectReason(3);

            // remove a specific Reject reason
            myEntry.removeRejectReason(3);

            // update the time entry, the reject reason should be removed
            myDAO.update(myEntry, "Ivern");
            myEntry = (TimeEntry) myDAO.getList(null).get(0);

            // this should not contain the removed entry
            reasons = myEntry.getAllRejectReasons();
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Demonstrates using with local transaction control.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testLocalTransactionControlDemo() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // obtain DAOs for entities
            DAO myTimeStatusDAO = DAOFactory.getDAO(TimeStatus.class, NAMESPACE);
            DAO myTaskTypeDAO = DAOFactory.getDAO(TaskType.class, NAMESPACE);

            // set connections in all DAOs for local transaction. assume connection exists and is ready
            // for local transaction (autoCommit=false);
            myTimeStatusDAO.setConnection(conn);
            myTaskTypeDAO.setConnection(conn);

            // initiate local transaction
            // create new taskType
            TaskType type = new TaskType();
            type.setCompanyId(1);
            type.setDescription("Component Devastation");
            myTaskTypeDAO.create(type, CREATION_USER);
            System.out.println(type.getModificationUser());

            // delete old taskType
            int taskTypeId = type.getPrimaryId();
            myTaskTypeDAO.delete(taskTypeId);
            assertEquals(0, myTaskTypeDAO.getList(null).size());

            // create new timeStatus
            TimeStatus status = new TimeStatus();
            status.setDescription("ready");
            myTimeStatusDAO.create(status, CREATION_USER);
            System.out.println(status.getModificationUser());

            // update the timeStatus to new status
            status.setDescription("down");
            myTimeStatusDAO.update(status, MODIFICATION_USER);
            System.out.println(status.getDescription());

            // remove connections
            myTimeStatusDAO.removeConnection();
            myTaskTypeDAO.removeConnection();
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Demonstrates managing Synchronous Batch operations.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSynchronousBatchDemo() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // Insert an task type
            TaskType type = new TaskType();
            type.setCompanyId(1);
            type.setPrimaryId(1);
            type.setDescription("taskType");
            type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
            type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
            type.setCreationUser("taskTypeCreate");
            type.setModificationUser("taskTypeModification");

            V1Dot1TestHelper.insertTaskTypes(type, conn);

            // Insert the time status
            TimeStatus status = new TimeStatus();

            status.setPrimaryId(2);
            status.setDescription("timeStatus");
            status.setCreationDate(V1Dot1TestHelper.createDate(2005, 3, 1));
            status.setModificationDate(V1Dot1TestHelper.createDate(2005, 4, 1));
            status.setCreationUser("timeStatusCreate");
            status.setModificationUser("timeStatusModification");

            V1Dot1TestHelper.insertTimeStatuses(status, conn);

            // Get a time entry DAO
            DAO myDAO = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);

            // create a number of TimeEntry objects and
            // put then in an array
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            }

            // do a batch create. First we create a ResultData Object where we want the results to be deposited.
            ResultData myResults = new ResultData();

            // do the batch, do NOT make it all or nothing
            try {
                ((TimeEntryDAO) myDAO).batchCreate(myTimeEntrys, "Ivern", false, myResults);
            } catch (BatchOperationException boe) {
                // we had some serious failure inspect
            }

            // we can now inspect the result to see how things went
            // how many failures
            System.out.println(" we had " + myResults.getFailedCount() + " failures");

            // how many succeeded
            System.out.println(" we had " + myResults.getSuccessCount() + " successful operations");

            // Printout all the failed operations
            BatchOperationException[] exceptions = myResults.getExceptionList();
            Object[] operations = myResults.getOperations();

            // determine operation result
            if (exceptions != null) {
                for (int i = 0; i < exceptions.length; i++) {
                    if (exceptions[i] == null) {
                        // statement was successful. No exception
                    } else {
                        // error on statement
                        System.out.println(" For the following create operation: " +
                            ((TimeEntry) (operations[i])).toString());
                        System.out.println(" We had the following failure: " + exceptions[i].toString());
                    }
                }
            }

            // do a batch read
            myResults = new ResultData();

            //             create a number of TimeEntry ids to read, put then in an array
            int[] dataToget = new int[myTimeEntrys.length];

            for (int i = 0; i < myTimeEntrys.length; i++) {
                dataToget[i] = myTimeEntrys[i].getPrimaryId();
            }

            // do the batch, this time we will make it all or nothing
            try {
                ((TimeEntryDAO) myDAO).batchRead(dataToget, true, myResults);
            } catch (BatchOperationException boe) {
                // we had some serious failure
                // inspect
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Demonstrates managing Asynchronous Batch operations.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAsynchronousBatchDemo() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // Insert an task type
            TaskType type = new TaskType();
            type.setCompanyId(1);
            type.setPrimaryId(1);
            type.setDescription("taskType");
            type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
            type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
            type.setCreationUser("taskTypeCreate");
            type.setModificationUser("taskTypeModification");

            V1Dot1TestHelper.insertTaskTypes(type, conn);

            // Insert the time status
            TimeStatus status = new TimeStatus();

            status.setPrimaryId(2);
            status.setDescription("timeStatus");
            status.setCreationDate(V1Dot1TestHelper.createDate(2005, 3, 1));
            status.setModificationDate(V1Dot1TestHelper.createDate(2005, 4, 1));
            status.setCreationUser("timeStatusCreate");
            status.setModificationUser("timeStatusModification");

            V1Dot1TestHelper.insertTimeStatuses(status, conn);

            // Get a time entry DAO
            DAO myDAO = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);

            // we will create a wrapper around our existing batch enabled dao
            AsynchBatchDAOWrapper myAsynchDAO = new AsynchBatchDAOWrapper((TimeEntryDAO) myDAO);

            // create a number of TimeEntry objects and
            // put then in an array
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            }

            MockResultListener myListener = new MockResultListener();

            // do a batch update
            myAsynchDAO.batchCreate(myTimeEntrys, "Ivern", true, myListener);
            while (!myListener.isFinished()) {
                Thread.sleep(10);
            }
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }
    }

    /**
     * <p>
     * Demonstrates search operations.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSearchDemo() throws Exception {
        Connection conn = null;

        try {
            conn = V1Dot1TestHelper.getConnection(NAMESPACE, CONNAME);

            // Insert an task type
            TaskType type = new TaskType();
            type.setCompanyId(1);
            type.setPrimaryId(1);
            type.setDescription("taskType");
            type.setCreationDate(V1Dot1TestHelper.createDate(2005, 1, 1));
            type.setModificationDate(V1Dot1TestHelper.createDate(2005, 2, 1));
            type.setCreationUser("taskTypeCreate");
            type.setModificationUser("taskTypeModification");

            V1Dot1TestHelper.insertTaskTypes(type, conn);

            // Insert the time status
            TimeStatus status = new TimeStatus();

            status.setPrimaryId(2);
            status.setDescription("timeStatus");
            status.setCreationDate(V1Dot1TestHelper.createDate(2005, 3, 1));
            status.setModificationDate(V1Dot1TestHelper.createDate(2005, 4, 1));
            status.setCreationUser("timeStatusCreate");
            status.setModificationUser("timeStatusModification");

            V1Dot1TestHelper.insertTimeStatuses(status, conn);

            // Get a time entry DAO
            DAO myDAO = DAOFactory.getDAO(TimeEntry.class, NAMESPACE);

            // create a number of TimeEntry objects and
            // put then in an array
            TimeEntry[] myTimeEntrys = new TimeEntry[10];

            for (int i = 0; i < 10; i++) {
                myTimeEntrys[i] = new TimeEntry();
                myTimeEntrys[i].setCompanyId(1);
                myTimeEntrys[i].setDescription(DESCRIPTION);
                myTimeEntrys[i].setDate(CREATION_DATE);
                myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
                myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
                myDAO.create(myTimeEntrys[i], "Create");
            }

            // get all time entries that contain the description "Doomed Project"
            SearchExpression expression1 = SubstringExpression.contains(
                TimeEntryCriteria.DESCRIPTION, "Doomed Project");

            // do the actual search
            ExpressionEvaluator evaluator = new SQLBasedCriteriaExpressionEvaluator(myDAO);
            Object[] matches = (Object[]) (evaluator.evaluate(expression1));

            // get all time entries that contain the statusID of "10000"
            SearchExpression expression2 = ComparisonExpression.equals(TimeEntryCriteria.TIME_STATUS_ID, "10000");
            matches = (Object[]) (evaluator.evaluate(expression2));

            // We can do a reverse and by using a boolean NOT we can actual get all
            // that do NOT contain this status id
            SearchExpression expression2NOT = BooleanExpression.not(expression2);
            matches = (Object[]) (evaluator.evaluate(expression2NOT));

            // Search by task types
            SearchExpression expression3 = ComparisonExpression.equals(TimeEntryCriteria.TASK_TYPE_ID, "20000");
            matches = (Object[]) (evaluator.evaluate(expression3));

            // search based on users, Here we will create an OR boolean query
            // Get all the time entries that were created OR modified by a user
            SearchExpression expression4_left = ComparisonExpression.equals(TimeEntryCriteria.CREATION_USER, "'Ivern'");
            SearchExpression expression4_right = ComparisonExpression.equals(TimeEntryCriteria.MODIFICATION_USER,
                    "'Ivern'");
            SearchExpression expression4OR = BooleanExpression.or(expression4_left, expression4_right);
            matches = (Object[]) (evaluator.evaluate(expression4OR));

            // Get all billable entries
            SearchExpression expression5 = ComparisonExpression.equals(TimeEntryCriteria.BILLABLE_FLAG, "0");
            matches = (Object[]) (evaluator.evaluate(expression5));

            // Get entries based on hour ranges
            SearchExpression expression7 = RangeExpression.fromTo(TimeEntryCriteria.HOURS, "5", "10");
            matches = (Object[]) (evaluator.evaluate(expression7));

            // we can also do this based on ComparisonExpression
            SearchExpression expression8a = ComparisonExpression.greaterThan(TimeEntryCriteria.HOURS, "10");
            matches = (Object[]) (evaluator.evaluate(expression8a));

            SearchExpression expression8b = ComparisonExpression.lessThanOrEquals(TimeEntryCriteria.HOURS, "10");
            matches = (Object[]) (evaluator.evaluate(expression8a));
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
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

        myTaskType.setActive(false);
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
}
