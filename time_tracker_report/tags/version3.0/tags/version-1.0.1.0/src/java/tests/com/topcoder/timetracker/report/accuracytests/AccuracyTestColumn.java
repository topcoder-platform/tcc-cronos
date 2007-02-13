/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.Column;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>Column</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestColumn extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestColumn.class);
    }

    /**
     * Tests getName().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetName() throws Exception {
        assertTrue("AMOUNT is not valid.", Column.AMOUNT.getName().equals("AMOUNT"));
        assertTrue("BILLABLE is not valid.", Column.BILLABLE.getName().equals("BILLABLE"));
        assertTrue("CLIENT is not valid.", Column.CLIENT.getName().equals("CLIENT"));
        assertTrue("DATE is not valid.", Column.DATE.getName().equals("DATE"));
        assertTrue("DESCRIPTION is not valid.", Column.DESCRIPTION.getName().equals("DESCRIPTION"));
        assertTrue("EMPLOYEE is not valid.", Column.EMPLOYEE.getName().equals("EMPLOYEE"));
        assertTrue("HOURS is not valid.", Column.HOURS.getName().equals("HOURS"));
        assertTrue("PAY_RATE rate is not valid.", Column.PAY_RATE.getName().equals("PAY_RATE"));
        assertTrue("PROJECT is not valid.", Column.PROJECT.getName().equals("PROJECT"));
        assertTrue("TASK is not valid.", Column.TASK.getName().equals("TASK"));
        assertTrue("TYPE is not valid.", Column.TYPE.getName().equals("TYPE"));
    }

    /**
     * Tests toString().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testToString() throws Exception {
        assertTrue("AMOUNT is not valid.", Column.AMOUNT.toString().equals("AMOUNT"));
        assertTrue("BILLABLE is not valid.", Column.BILLABLE.toString().equals("BILLABLE"));
        assertTrue("CLIENT is not valid.", Column.CLIENT.toString().equals("CLIENT"));
        assertTrue("DATE is not valid.", Column.DATE.toString().equals("DATE"));
        assertTrue("DESCRIPTION is not valid.", Column.DESCRIPTION.toString().equals("DESCRIPTION"));
        assertTrue("EMPLOYEE is not valid.", Column.EMPLOYEE.toString().equals("EMPLOYEE"));
        assertTrue("HOURS is not valid.", Column.HOURS.toString().equals("HOURS"));
        assertTrue("PAY_RATE rate is not valid.", Column.PAY_RATE.toString().equals("PAY_RATE"));
        assertTrue("PROJECT is not valid.", Column.PROJECT.toString().equals("PROJECT"));
        assertTrue("TASK is not valid.", Column.TASK.toString().equals("TASK"));
        assertTrue("TYPE is not valid.", Column.TYPE.toString().equals("TYPE"));
    }

    /**
     * Tests getColumns().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetColumns() throws Exception {
        List columns = Column.getColumns();

        assertTrue("There should be eleven members.", columns.size() == 11);

        assertTrue("AMOUNT is not valid.", columns.contains(Column.AMOUNT));
        assertTrue("BILLABLE is not valid.", columns.contains(Column.BILLABLE));
        assertTrue("CLIENT is not valid.", columns.contains(Column.CLIENT));
        assertTrue("DATE is not valid.", columns.contains(Column.DATE));
        assertTrue("DESCRIPTION is not valid.", columns.contains(Column.DESCRIPTION));
        assertTrue("EMPLOYEE is not valid.", columns.contains(Column.EMPLOYEE));
        assertTrue("HOURS is not valid.", columns.contains(Column.HOURS));
        assertTrue("PAY_RATE is not valid.", columns.contains(Column.PAY_RATE));
        assertTrue("PROJECT is not valid.", columns.contains(Column.PROJECT));
        assertTrue("TASK is not valid.", columns.contains(Column.TASK));
        assertTrue("TYPE is not valid.", columns.contains(Column.TYPE));
    }
}
