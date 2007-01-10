/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.accuracytests;

import com.topcoder.timetracker.entry.time.RejectReason;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.search.BooleanExpression;
import com.topcoder.timetracker.entry.time.search.ComparisonExpression;
import com.topcoder.timetracker.entry.time.search.RangeExpression;
import com.topcoder.timetracker.entry.time.search.SQLBasedTimeEntryCriteriaExpressionEvaluator;
import com.topcoder.timetracker.entry.time.search.SearchExpression;
import com.topcoder.timetracker.entry.time.search.SubstringExpression;
import com.topcoder.timetracker.entry.time.search.TimeEntryCriteria;

import junit.framework.TestCase;

import java.sql.Connection;


/**
 * <p>
 * Accuracy tests for <code>SQLBasedTimeEntryCriteriaExpressionEvaluator</code> class.
 * </p>
 *
 * <p>
 * Since 2.0, updated database names and added the company entity to test data.
 * </p>
 *
 * @author oodinary
 * @author kr00tki
 * @version 2.0
 * @since 1.1
 */
public class SQLBasedTimeEntryCriteriaExpressionEvaluatorAccuracyTest extends TestCase {
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
     * Represents the <code>SQLBasedTimeEntryCriteriaExpressionEvaluator</code> instance which will be used for
     * testing.
     */
    private SQLBasedTimeEntryCriteriaExpressionEvaluator evaluator = null;

    /**
     * <p>
     * The TimeEntryDAO instance for testing.
     * </p>
     */
    private TimeEntryDAO timeEntryDAO = null;

    /** Represents a valid time task type instance. */
    private TaskType type;

    /** Represents a valid time status instance. */
    private TimeStatus status;

    /** Represents a valid time reject reason instances. */
    private RejectReason[] reasons;

    /** The connection used for testing. */
    private Connection conn = null;

    /** Represents the TimeEntry instances for testing. */
    private TimeEntry[] myTimeEntrys = null;

    /** Represents the test result. */
    private Object[] matches = null;

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
        AccuracyTestHelper.insertCompany(AccuracyTestHelper.COMPANY_ID, conn);

        // Insert an task type
        type = new TaskType();

        type.setPrimaryId(1);
        type.setDescription("taskType");
        type.setCreationDate(AccuracyTestHelper.createDate(2005, 1, 1));
        type.setModificationDate(AccuracyTestHelper.createDate(2005, 2, 1));
        type.setCreationUser("taskTypeCreate");
        type.setModificationUser("taskTypeModification");
        type.setCompanyId(AccuracyTestHelper.COMPANY_ID);

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
        this.evaluator = new SQLBasedTimeEntryCriteriaExpressionEvaluator(timeEntryDAO);

        // add some records to database
        myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription("description");
            myTimeEntrys[i].setDate(AccuracyTestHelper.createDate(2006, 1, 1));
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reasons[i]);
            myTimeEntrys[i].setCompanyId(AccuracyTestHelper.COMPANY_ID);

            timeEntryDAO.create(myTimeEntrys[i], "create");
        }
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
     * Accuracy test for <code>evaluate(SearchExpression)</code>.
     *
     * @throws Exception to JUnit if there is any problem.
     */
    public void testEvaluateAccuracy1() throws Exception {
        SearchExpression expression1 = SubstringExpression.contains(TimeEntryCriteria.DESCRIPTION, "des");

        matches = evaluator.evaluate(expression1);
        assertEquals("Fail to evaluate", 10, matches.length);
    }

    /**
     * Accuracy test for <code>evaluate(SearchExpression)</code>.
     *
     * @throws Exception to JUnit if there is any problem.
     */
    public void testEvaluateAccuracy2() throws Exception {
        SearchExpression expression2 = ComparisonExpression.equals(TimeEntryCriteria.TIME_STATUS_ID,
                "" + status.getPrimaryId());
        matches = evaluator.evaluate(expression2);
        assertEquals("Fail to evaluate", 10, matches.length);

        SearchExpression expression2NOT = BooleanExpression.not(expression2);
        matches = evaluator.evaluate(expression2NOT);
        assertEquals("Fail to evaluate", 0, matches.length);
    }

    /**
     * Accuracy test for <code>evaluate(SearchExpression)</code>.
     *
     * @throws Exception to JUnit if there is any problem.
     */
    public void testEvaluateAccuracy3() throws Exception {
        //
        // search based on users, Here we will create an OR boolean query
        // Get all the time entries that were created OR modified by a user
        SearchExpression expression4Left = ComparisonExpression.equals(TimeEntryCriteria.CREATION_USER, "'create'");
        SearchExpression expression4Right = ComparisonExpression.equals(TimeEntryCriteria.MODIFICATION_USER, "'create'");
        SearchExpression expression4OR = BooleanExpression.or(expression4Left, expression4Right);
        matches = evaluator.evaluate(expression4OR);
        assertEquals("Fail to evaluate", 10, matches.length);
    }

    /**
     * Accuracy test for <code>evaluate(SearchExpression)</code>.
     *
     * @throws Exception to JUnit if there is any problem.
     */
    public void testEvaluateAccuracy4() throws Exception {
        //
        // Get all billable entries
        SearchExpression expression5 = ComparisonExpression.equals(TimeEntryCriteria.BILLABLE_FLAG, "0");
        matches = evaluator.evaluate(expression5);
        assertEquals("Fail to evaluate", 10, matches.length);
    }

    /**
     * Accuracy test for <code>evaluate(SearchExpression)</code>.
     *
     * @throws Exception to JUnit if there is any problem.
     */
    public void testEvaluateAccuracy5() throws Exception {
        // create the expression and evaluate
        SearchExpression expression1 = ComparisonExpression.equals(TimeEntryCriteria.TIME_STATUS_ID,
                "" + status.getPrimaryId());

        SearchExpression expression2 = ComparisonExpression.notEquals(TimeEntryCriteria.CREATION_USER, "'create'");

        SearchExpression expression3 = RangeExpression.fromTo(TimeEntryCriteria.HOURS, "" + (-1), "" + 100);

        SearchExpression expression4 = SubstringExpression.contains(TimeEntryCriteria.DESCRIPTION, "des");

        SearchExpression expression5 = BooleanExpression.or(expression3, expression4);
        SearchExpression expression6 = BooleanExpression.not(expression2);
        SearchExpression expression7 = BooleanExpression.and(expression1, expression6);
        SearchExpression expression8 = BooleanExpression.and(expression5, expression7);
        matches = evaluator.evaluate(expression8);

        // check the result
        assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys.length, matches.length);

        for (int i = 0; i < myTimeEntrys.length; i++) {
            AccuracyTestHelper.assertEquals("not correctly record returned from the TimeEntries table",
                myTimeEntrys[i], (TimeEntry) matches[i]);
        }
    }
}
