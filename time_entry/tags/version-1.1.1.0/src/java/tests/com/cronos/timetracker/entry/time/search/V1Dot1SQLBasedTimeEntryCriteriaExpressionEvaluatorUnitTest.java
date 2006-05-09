/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import com.cronos.timetracker.entry.time.RejectReason;
import com.cronos.timetracker.entry.time.TaskType;
import com.cronos.timetracker.entry.time.TimeEntry;
import com.cronos.timetracker.entry.time.TimeEntryDAO;
import com.cronos.timetracker.entry.time.TimeStatus;
import com.cronos.timetracker.entry.time.V1Dot1TestHelper;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.sql.Connection;


/**
 * <p>
 * Tests functionality and error cases of <code>SQLBasedTimeEntryCriteriaExpressionEvaluator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class V1Dot1SQLBasedTimeEntryCriteriaExpressionEvaluatorUnitTest extends TestCase {
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

    /** Represents a valid time reject reason instance. */
    private RejectReason reason1;

    /** Represents a valid time reject reason instance. */
    private RejectReason reason2;

    /** Represents the TimeEntry instances for testing. They will be added into the database first. */
    private TimeEntry[] myTimeEntrys = null;

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

            // Insert an task type
            type = new TaskType();

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
        } finally {
            V1Dot1TestHelper.closeResources(null, null, conn);
        }

        this.timeEntryDAO = new TimeEntryDAO(CONNAME, NAMESPACE);
        this.evaluator = new SQLBasedTimeEntryCriteriaExpressionEvaluator(timeEntryDAO);

        // add some records to database
        myTimeEntrys = new TimeEntry[10];

        for (int i = 0; i < 10; i++) {
            myTimeEntrys[i] = new TimeEntry();
            myTimeEntrys[i].setDescription("description");
            myTimeEntrys[i].setDate(V1Dot1TestHelper.createDate(2006, 1, 1));
            myTimeEntrys[i].setTaskTypeId(type.getPrimaryId());
            myTimeEntrys[i].setTimeStatusId(status.getPrimaryId());
            myTimeEntrys[i].addRejectReason(reason1);
            myTimeEntrys[i].addRejectReason(reason2);

            timeEntryDAO.create(myTimeEntrys[i], "create");
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
     * Tests the SQLBasedTimeEntryCriteriaExpressionEvaluator(TimeEntryDAO) when the given timeEntryDAO is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSQLBasedTimeEntryCriteriaExpressionEvaluator_NullTimeEntryDAO()
        throws Exception {
        try {
            new SQLBasedTimeEntryCriteriaExpressionEvaluator(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy of SQLBasedTimeEntryCriteriaExpressionEvaluator(TimeEntryDAO).
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testSQLBasedTimeEntryCriteriaExpressionEvaluator_Accuracy()
        throws Exception {
        assertNotNull("The SQLBasedTimeEntryCriteriaExpressionEvaluator should be created properly.", evaluator);
        assertEquals("The SQLBasedTimeEntryCriteriaExpressionEvaluator should be created properly.", timeEntryDAO,
            V1Dot1TestHelper.getPrivateField(evaluator.getClass(), evaluator, "timeEntryDAO"));
    }

    /**
     * <p>
     * Tests the evaluate(SearchExpression) when the given expression is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testEvaluate_NullExpression() throws Exception {
        try {
            evaluator.evaluate(null);
            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * <p>
     * Tests the accuracy evaluate(SearchExpression) for simple SearchExpression.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testEvaluate_SimpleSearchExpressionAccuracy1() throws Exception {
        // create the expression and evaluate
        SearchExpression expression = ComparisonExpression.equals(TimeEntryCriteria.TIME_STATUS_ID,
                "" + status.getPrimaryId());
        Object[] ret = evaluator.evaluate(expression);

        // check the result
        assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys.length, ret.length);

        for (int i = 0; i < myTimeEntrys.length; i++) {
            assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys[i], (TimeEntry) ret[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy evaluate(SearchExpression) for simple SearchExpression.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testEvaluate_SimpleSearchExpressionAccuracy2() throws Exception {
        // create the expression and evaluate
        SearchExpression expression = ComparisonExpression.notEquals(TimeEntryCriteria.CREATION_USER, "'create'");
        Object[] ret = evaluator.evaluate(expression);

        // check the result
        assertEquals("not correctly record returned from the TimeEntries table", 0, ret.length);
    }

    /**
     * <p>
     * Tests the accuracy evaluate(SearchExpression) for simple SearchExpression.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testEvaluate_SimpleSearchExpressionAccuracy3() throws Exception {
        // create the expression and evaluate
        SearchExpression expression = RangeExpression.fromTo(TimeEntryCriteria.HOURS, "" + (-1), "" + 100);
        Object[] ret = evaluator.evaluate(expression);

        // check the result
        assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys.length, ret.length);

        for (int i = 0; i < myTimeEntrys.length; i++) {
            assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys[i], (TimeEntry) ret[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy evaluate(SearchExpression) for simple SearchExpression.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testEvaluate_SimpleSearchExpressionAccuracy4() throws Exception {
        // create the expression and evaluate
        SearchExpression expression = SubstringExpression.contains(TimeEntryCriteria.DESCRIPTION, "des");
        Object[] ret = evaluator.evaluate(expression);

        // check the result
        assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys.length, ret.length);

        for (int i = 0; i < myTimeEntrys.length; i++) {
            assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys[i], (TimeEntry) ret[i]);
        }
    }

    /**
     * <p>
     * Tests the accuracy evaluate(SearchExpression) for complex SearchExpression.
     * </p>
     *
     * @throws Exception throw Exception to junit
     */
    public void testEvaluate_ComplexSearchExpressionAccuracy() throws Exception {
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
        Object[] ret = evaluator.evaluate(expression8);

        // check the result
        assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys.length, ret.length);

        for (int i = 0; i < myTimeEntrys.length; i++) {
            assertEquals("not correctly record returned from the TimeEntries table", myTimeEntrys[i], (TimeEntry) ret[i]);
        }
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
        Assert.assertEquals(message, expected.getPrimaryId(), actual.getPrimaryId());
        Assert.assertEquals(message, expected.getTaskTypeId(), actual.getTaskTypeId());
        Assert.assertEquals(message, expected.getTimeStatusId(), actual.getTimeStatusId());
        V1Dot1TestHelper.assertEquals(message, expected.getDate(), actual.getDate());
        Assert.assertEquals(message, expected.getHours(), actual.getHours(), 1e-8);
        Assert.assertEquals(message, expected.isBillable(), actual.isBillable());
        Assert.assertEquals(message, expected.getDescription(), actual.getDescription());
        Assert.assertEquals(message, expected.getCreationUser(), actual.getCreationUser());
        V1Dot1TestHelper.assertEquals(message, expected.getCreationDate(), actual.getCreationDate());
        Assert.assertEquals(message, expected.getModificationUser(), actual.getModificationUser());
        V1Dot1TestHelper.assertEquals(message, expected.getModificationDate(), actual.getModificationDate());
        Assert.assertEquals(message, expected.getAllRejectReasons().length, actual.getAllRejectReasons().length);
    }
}
