/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.report.dbhandler;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link ColumnType}.
 *
 * @author TCSDEVELOPER
 * @version 2.0
 */
public class ColumnTypeTest extends TestCase {

    /**
     * This method tests {@link ColumnType#getType()} for correctness.
     */
    public void testGetType() {
        assertEquals("DATE", ColumnType.DATE.getType());
        assertEquals("FLOAT", ColumnType.FLOAT.getType());
        assertEquals("INTEGER", ColumnType.INTEGER.getType());
        assertEquals("MONEY", ColumnType.MONEY.getType());
        assertEquals("SMALLINT", ColumnType.SMALLINT.getType());
        assertEquals("VARCHAR", ColumnType.VARCHAR.getType());
    }

    /**
     * This method tests {@link ColumnType#toString()} for correctness.
     */
    public void testToString() {
        assertEquals("DATE", ColumnType.DATE.toString());
        assertEquals("FLOAT", ColumnType.FLOAT.toString());
        assertEquals("INTEGER", ColumnType.INTEGER.toString());
        assertEquals("MONEY", ColumnType.MONEY.toString());
        assertEquals("SMALLINT", ColumnType.SMALLINT.toString());
        assertEquals("VARCHAR", ColumnType.VARCHAR.toString());
    }

}
