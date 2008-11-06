/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for the <code>JiraConnectionException</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraConnectionExceptionUnitTests extends TestCase {

    /**
     * Non-null <code>ExceptionData</code> instance used for testing.
     */
    private static final ExceptionData DATA = new ExceptionData();

    /**
     * Non-null <code>Throwable</code> instance used for testing.
     */
    private static final Throwable CAUSE = new Exception();

    /**
     * Tests super class.
     */
    public void testInheritance() {
        assertEquals("incorrect super class", JiraManagerException.class,
            JiraConnectionException.class.getSuperclass());
    }

    /**
     * Tests <code>JiraConnectionException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes null.
     */
    public void testJiraConnectionException_A_1() {
        JiraConnectionException e = new JiraConnectionException((String) null);
        assertNull("message was propagated incorrectly", e.getMessage());

    }

    /**
     * Tests <code>JiraConnectionException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes empty
     * string.
     */
    public void testJiraConnectionException_A_2() {
        JiraConnectionException e = new JiraConnectionException(" ");
        assertEquals("message was propagated incorrectly", " ", e.getMessage());

    }

    /**
     * Tests <code>JiraConnectionException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes
     * "abc".
     */
    public void testJiraConnectionException_A_3() {
        JiraConnectionException e = new JiraConnectionException("abc");
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());

    }

    /**
     * Tests <code>JiraConnectionException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null cause as second argument.
     */
    public void testJiraConnectionException_B_1() {
        JiraConnectionException e = new JiraConnectionException((String) null, (Throwable) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraConnectionException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null cause as second argument.
     */
    public void testJiraConnectionException_B_2() {
        JiraConnectionException e = new JiraConnectionException(" ", CAUSE);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());

    }

    /**
     * Tests <code>JiraConnectionException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null cause as second argument.
     */
    public void testJiraConnectionException_B_3() {
        JiraConnectionException e = new JiraConnectionException("abc", (Throwable) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraConnectionException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null exception data as second argument.
     */
    public void testJiraConnectionException_C_1() {
        JiraConnectionException e = new JiraConnectionException((String) null, (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraConnectionException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null exception data as second argument.
     */
    public void testJiraConnectionException_C_2() {
        JiraConnectionException e = new JiraConnectionException(" ", DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
            .getApplicationCode());

    }

    /**
     * Tests <code>JiraConnectionException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null exception data as second argument.
     */
    public void testJiraConnectionException_C_3() {
        JiraConnectionException e = new JiraConnectionException("abc", (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraConnectionException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument, null cause as second argument and null exception data as third argument.
     */
    public void testJiraConnectionException_D_1() {
        JiraConnectionException e = new JiraConnectionException((String) null, (Throwable) null,
            (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraConnectionException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument, non-null cause as second argument and non-null exception data
     * as third argument.
     */
    public void testJiraConnectionException_D_2() {
        JiraConnectionException e = new JiraConnectionException(" ", CAUSE, DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
            .getApplicationCode());

    }

    /**
     * Tests <code>JiraConnectionException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument, null cause as second argument and null exception data as third
     * argument.
     */
    public void testJiraConnectionException_D_3() {
        JiraConnectionException e = new JiraConnectionException("abc", (Throwable) null,
            (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }
}
