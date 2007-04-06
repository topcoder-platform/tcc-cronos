/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.notification;

import com.topcoder.util.collection.typesafeenum.Enum;

import junit.framework.TestCase;


/**
 * Unit test for the <code>StringMatchType</code> class.
 *
 * @author kzhu
 * @version 3.2
 */
public class StringMatchTypeUnitTests extends TestCase {
    /** Represents the private instance used for test. */
    private StringMatchType stringMatchType = null;

    /**
     * Sets up the test environment.
     */
    protected void setUp() {
        stringMatchType = StringMatchType.ENDS_WITH;
    }

    /**
     * <p>
     * Accuracy test for inheritance, should inherit from Enum.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testInheritance() throws Exception {
        assertTrue("StringMatchType should inherit from Enum", stringMatchType instanceof Enum);
    }

    /**
     * <p>
     * Accuracy test for method <code>START_WIDTH</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSTART_WIDTHAccuracy() throws Exception {
        assertNotNull("START_WITH must be initialized.", StringMatchType.STARTS_WITH);
    }

    /**
     * <p>
     * Accuracy test for method <code>END_WIDTH</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testEND_WIDTHAccuracy() throws Exception {
        assertNotNull("END_WITH must be initialized.", StringMatchType.ENDS_WITH);
    }

    /**
     * <p>
     * Accuracy test for method <code>SUBSTRING</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testSUBSTRINGAccuracy() throws Exception {
        assertNotNull("SUBSTRING must be initialized.", StringMatchType.SUBSTRING);
    }

    /**
     * <p>
     * Accuracy test for method <code>EXACT_MATCH</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testEXACT_MATCHAccuracy() throws Exception {
        assertNotNull("EXACT must be initialized.", StringMatchType.EXACT_MATCH);
    }

    /**
     * <p>
     * Accuracy test for method <code>getFilterPrefix</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testgetFilterPrefixAccuracy() throws Exception {
        assertEquals("ENDS_WIDTH should be EW:", "EW:", stringMatchType.getFilterPrefix());
    }

    /**
     * <p>
     * Accuracy test for method <code>toString</code>.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    public void testtoStringAccuracy() throws Exception {
        assertEquals("ENDS_WIDTH should be EW:", "EW:", stringMatchType.toString());
    }
}
