/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report;

import junit.framework.TestCase;

import java.util.List;


/**
 * This class contains the unit tests for {@link com.cronos.timetracker.report.Column}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ColumnTest extends TestCase {

    /**
     * This method tests {@link Column#getName()} for correctness.
     */
    public void testGetName() {
        assertEquals("AMOUNT", Column.AMOUNT.getName());
        assertEquals("BILLABLE", Column.BILLABLE.getName());
        assertEquals("CLIENT", Column.CLIENT.getName());
        assertEquals("DATE", Column.DATE.getName());
        assertEquals("DESCRIPTION", Column.DESCRIPTION.getName());
        assertEquals("EMPLOYEE", Column.EMPLOYEE.getName());
        assertEquals("HOURS", Column.HOURS.getName());
        assertEquals("PAY_RATE", Column.PAY_RATE.getName());
        assertEquals("PROJECT", Column.PROJECT.getName());
        assertEquals("TASK", Column.TASK.getName());
        assertEquals("TYPE", Column.TYPE.getName());
        assertEquals("STATUS", Column.STATUS.getName());
        assertEquals("COMPANY", Column.COMPANY.getName());
    }

    /**
     * This method tests {@link Column#toString()} for correctness.
     */
    public void testToString() {
        assertEquals("AMOUNT", Column.AMOUNT.toString());
        assertEquals("BILLABLE", Column.BILLABLE.toString());
        assertEquals("CLIENT", Column.CLIENT.toString());
        assertEquals("DATE", Column.DATE.toString());
        assertEquals("DESCRIPTION", Column.DESCRIPTION.toString());
        assertEquals("EMPLOYEE", Column.EMPLOYEE.toString());
        assertEquals("HOURS", Column.HOURS.toString());
        assertEquals("PAY_RATE", Column.PAY_RATE.toString());
        assertEquals("PROJECT", Column.PROJECT.toString());
        assertEquals("TASK", Column.TASK.toString());
        assertEquals("TYPE", Column.TYPE.toString());
        assertEquals("STATUS", Column.STATUS.toString());
        assertEquals("COMPANY", Column.COMPANY.toString());
    }

    /**
     * This method tests {@link Column#getColumns()}.
     */
    public void testGetColumns() {
        final List columns = Column.getColumns();
        assertTrue(columns.contains(Column.AMOUNT));
        assertTrue(columns.contains(Column.BILLABLE));
        assertTrue(columns.contains(Column.CLIENT));
        assertTrue(columns.contains(Column.DATE));
        assertTrue(columns.contains(Column.DESCRIPTION));
        assertTrue(columns.contains(Column.EMPLOYEE));
        assertTrue(columns.contains(Column.HOURS));
        assertTrue(columns.contains(Column.PAY_RATE));
        assertTrue(columns.contains(Column.PROJECT));
        assertTrue(columns.contains(Column.TASK));
        assertTrue(columns.contains(Column.TYPE));
        assertTrue(columns.contains(Column.STATUS));
        assertTrue(columns.contains(Column.COMPANY));
        assertEquals(13, columns.size());
        try {
            columns.add(new Object());
            fail("should throw");
        } catch (UnsupportedOperationException expected) {
            //expected
        }
    }

}
