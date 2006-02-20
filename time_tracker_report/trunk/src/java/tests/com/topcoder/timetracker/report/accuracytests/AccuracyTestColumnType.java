/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.dbhandler.ColumnType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


/**
 * Accuracy test for <code>ColumnType</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestColumnType extends TestCase {
    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestColumnType.class);
    }

    /**
     * Tests getType().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetType() throws Exception {
        assertTrue("DATA is not valid.", ColumnType.DATE.getType().equals("DATE"));
        assertTrue("FLOAT is not valid.", ColumnType.FLOAT.getType().equals("FLOAT"));
        assertTrue("INTEGER is not valid.", ColumnType.INTEGER.getType().equals("INTEGER"));
        assertTrue("MONEY is not valid.", ColumnType.MONEY.getType().equals("MONEY"));
        assertTrue("SMALLINT is not valid.", ColumnType.SMALLINT.getType().equals("SMALLINT"));
        assertTrue("VARCHAR is not valid.", ColumnType.VARCHAR.getType().equals("VARCHAR"));
    }

    /**
     * Tests getColumnTypes().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetColumnTypes() throws Exception {
        List types = ColumnType.getColumnTypes();

        assertTrue("There should be six members.", types.size() == 6);

        assertTrue("DATA is not valid.", types.contains(ColumnType.DATE));
        assertTrue("FLOAT is not valid.", types.contains(ColumnType.FLOAT));
        assertTrue("INTEGER is not valid.", types.contains(ColumnType.INTEGER));
        assertTrue("MONEY is not valid.", types.contains(ColumnType.MONEY));
        assertTrue("SMALLINT is not valid.", types.contains(ColumnType.SMALLINT));
        assertTrue("VARCHAR is not valid.", types.contains(ColumnType.VARCHAR));
    }

    /**
     * Tests toString().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testToString() throws Exception {
        assertTrue("DATA is not valid.", ColumnType.DATE.toString().equals("DATE"));
        assertTrue("FLOAT is not valid.", ColumnType.FLOAT.toString().equals("FLOAT"));
        assertTrue("INTEGER is not valid.", ColumnType.INTEGER.toString().equals("INTEGER"));
        assertTrue("MONEY is not valid.", ColumnType.MONEY.toString().equals("MONEY"));
        assertTrue("SMALLINT is not valid.", ColumnType.SMALLINT.toString().equals("SMALLINT"));
        assertTrue("VARCHAR is not valid.", ColumnType.VARCHAR.toString().equals("VARCHAR"));
    }
}
