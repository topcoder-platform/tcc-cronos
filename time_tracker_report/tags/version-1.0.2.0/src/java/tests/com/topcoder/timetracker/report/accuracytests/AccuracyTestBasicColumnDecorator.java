/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.timetracker.report.BasicColumnDecorator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Accuracy test for <code>BasicColumnDecorator</code>.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AccuracyTestBasicColumnDecorator extends TestCase {
    /** The value of the display text used for test. */
    private static final String DISPLAY_LABEL = "TEST LABEL";

    /** The value of the column name used for test. */
    private static final String COLUMN_NAME = "TEST COLUMN";

    /** The value of the prefix used for test. */
    private static final String PREFIX = "PREFIX";

    /** The value of the suffix used for test. */
    private static final String SUFFIX = "SUFFIX";

    /** The data used for test. */
    private static final String DATA = "TEST DATA";

    /** The instance of the <code>BasicColumnDecorator</code> used for test. */
    private BasicColumnDecorator decorator = null;

    /**
     * Returns the suit to run the test.
     *
     * @return suite to run the test.
     */
    public static Test suite() {
        return new TestSuite(AccuracyTestBasicColumnDecorator.class);
    }

    /**
     * See javadoc for junit.framework.TestCase#setUp().
     *
     * @throws Exception exception that occurs during the setup process.
     */
    protected void setUp() throws Exception {
        decorator = new BasicColumnDecorator(COLUMN_NAME, DISPLAY_LABEL);
    }

    /**
     * Tests creating <code>BasicColumnDecorator</code> with legal parameters.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testConstructor() throws Exception {
        assertTrue("The display text of the decorator is not valid.",
            decorator.getColumnDisplayText().equals(DISPLAY_LABEL));
        assertTrue("The column name of the decorator is not valid.", decorator.getColumnName().equals(COLUMN_NAME));
    }

    /**
     * Tests getColumnName().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetColumnName() throws Exception {
        assertEquals("The column name of the decorator is not valid.", COLUMN_NAME, decorator.getColumnName());
    }

    /**
     * Tests getColumnDisplayTex().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testGetColumnDisplayText() throws Exception {
        assertEquals("The display text of the decorator is not valid.",
            DISPLAY_LABEL, decorator.getColumnDisplayText());
    }

    /**
     * Tests setPrefix().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetPrefix() throws Exception {
        decorator.setPrefix(PREFIX);
        assertTrue("The prefix of the decorator is not valid.", decorator.decorateColumn(DATA).equals(PREFIX + DATA));
    }

    /**
     * Tests setSuffix().
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testSetSuffix() throws Exception {
        decorator.setSuffix(SUFFIX);
        assertTrue("The suffix of the decorator is not valid.", decorator.decorateColumn(DATA).equals(DATA + SUFFIX));
    }

    /**
     * Tests decorateColumn() with null parameter.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDecorateColumnNull() throws Exception {
        assertNull("The decorated column data is not valid.", decorator.decorateColumn(null));
    }

    /**
     * Tests decorateColumn() with none prefix and suffix.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDecorateColumnNone() throws Exception {
        assertTrue("The suffix of the decorator is not valid.", decorator.decorateColumn(DATA).equals(DATA));
        assertTrue("The prefix of the decorator is not valid.", decorator.decorateColumn(DATA).equals(DATA));
    }

    /**
     * Tests decorateColumn() with legal prefix and suffix.
     *
     * @throws Exception to JUnit from test cases.
     */
    public void testDecorateColumn() throws Exception {
        decorator.setPrefix(PREFIX);
        decorator.setSuffix(SUFFIX);
        assertEquals("The decorated column data is not valid.", PREFIX + DATA + SUFFIX, decorator.decorateColumn(DATA));
    }
}
