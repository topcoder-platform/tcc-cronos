/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report;

import junit.framework.TestCase;


/**
 * This class contains the unit tests for {@link BasicColumnDecorator}.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class BasicColumnDecoratorTest extends TestCase {
    /**
     * This is a simple String constant used in the test cases.
     */
    private static final String STRING1 = "string1";
    /**
     * This is a simple String constant used in the test cases.
     */

    private static final String STRING2 = "string2";
    /**
     * This is the {@link BasicColumnDecorator} instance tested in the test cases. It is instantiated in {@link
     * #setUp()}.
     */
    private BasicColumnDecorator basicColumnDecorator;

    /**
     * This method tests {@link BasicColumnDecorator#BasicColumnDecorator(String, String)} for correctness.
     */
    public void testCtor() {
        final BasicColumnDecorator decorator = new BasicColumnDecorator(STRING1, STRING2);
        assertEquals(STRING1, decorator.getColumnName());
        assertEquals(STRING2, decorator.getColumnDisplayText());
        assertEquals(STRING1, decorator.decorateColumn(STRING1));
    }

    /**
     * This method tests {@link BasicColumnDecorator#setPrefix(String)}, {@link BasicColumnDecorator#setSuffix(String)}
     * and {@link BasicColumnDecorator#decorateColumn(String)} for correctness.
     */
    public void testPrefixSuffixDecorate() {
        assertEquals(STRING1, basicColumnDecorator.decorateColumn(STRING1));
        basicColumnDecorator.setPrefix(STRING2);
        assertEquals(STRING2 + STRING1, basicColumnDecorator.decorateColumn(STRING1));
        basicColumnDecorator.setSuffix(STRING2);
        assertEquals(STRING2 + STRING1 + STRING2, basicColumnDecorator.decorateColumn(STRING1));

    }

    /**
     * This method tests {@link BasicColumnDecorator#BasicColumnDecorator(String, String)} for correct failure
     * behavior.
     * <p/>
     * <b>Failure reason</b>: columnName is <tt>null</tt>
     */
    public void testCtorFailNullColName() {
        try {
            new BasicColumnDecorator(null, STRING2);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#BasicColumnDecorator(String, String)} for correct failure
     * behavior.
     * <p/>
     * <b>Failure reason</b>: displayLabel is <tt>null</tt>
     */
    public void testCtorFailNullDisplayLabel() {
        try {
            new BasicColumnDecorator(STRING1, null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#BasicColumnDecorator(String, String)} for correct failure
     * behavior.
     * <p/>
     * <b>Failure reason</b>: columnName is empty String
     */
    public void testCtorFailEmptyColName() {
        try {
            new BasicColumnDecorator("  \t", STRING2);
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#BasicColumnDecorator(String, String)} for correct failure
     * behavior.
     * <p/>
     * <b>Failure reason</b>: displayLabel is empty String
     */
    public void testCtorFailEmptyDisplayLabel() {
        try {
            new BasicColumnDecorator(STRING1, "  \t \n");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#setPrefix(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: prefix is <tt>null</tt>
     */
    public void testSetPrefixFailNullPrefix() {
        try {
            basicColumnDecorator.setPrefix(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#setPrefix(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: prefix is empty String
     */
    public void testSetPrefixFailEmptyPrefix() {
        try {
            basicColumnDecorator.setPrefix("  \t ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#setSuffix(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: suffix is <tt>null</tt>
     */
    public void testSetSuffixFailNullSuffix() {
        try {
            basicColumnDecorator.setSuffix(null);
            fail("should throw");
        } catch (NullPointerException expected) {
            //expected
        }
    }

    /**
     * This method tests {@link BasicColumnDecorator#setSuffix(String)} for correct failure behavior.
     * <p/>
     * <b>Failure reason</b>: suffix is empty String
     */
    public void testSetSuffixFailEmptySuffix() {
        try {
            basicColumnDecorator.setSuffix("  \t ");
            fail("should throw");
        } catch (IllegalArgumentException expected) {
            //expected
        }
    }

    /**
     * This method does the test setup needed.
     *
     * @throws Exception in case some unexpected Exception occurs
     */
    protected void setUp() throws Exception {
        super.setUp();
        basicColumnDecorator = new BasicColumnDecorator(STRING1, STRING2);
    }
}