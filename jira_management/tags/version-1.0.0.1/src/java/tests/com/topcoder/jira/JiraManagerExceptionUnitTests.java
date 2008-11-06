/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.BaseCriticalException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for the <code>JiraManagerException</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraManagerExceptionUnitTests extends TestCase {

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
        assertEquals("incorrect super class", BaseCriticalException.class,
            JiraManagerException.class.getSuperclass());
    }

    /**
     * Tests <code>JiraManagerException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes null.
     */
    public void testJiraManagerException_A_1() {
        JiraManagerException e = new JiraManagerException(null);
        assertNull("message was propagated incorrectly", e.getMessage());

    }

    /**
     * Tests <code>JiraManagerException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes empty
     * string.
     */
    public void testJiraManagerException_A_2() {
        JiraManagerException e = new JiraManagerException(" ");
        assertEquals("message was propagated incorrectly", " ", e.getMessage());

    }

    /**
     * Tests <code>JiraManagerException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes
     * "abc".
     */
    public void testJiraManagerException_A_3() {
        JiraManagerException e = new JiraManagerException("abc");
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());

    }

    /**
     * Tests <code>JiraManagerException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null cause as second argument.
     */
    public void testJiraManagerException_B_1() {
        JiraManagerException e = new JiraManagerException(null, (Throwable) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraManagerException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null cause as second argument.
     */
    public void testJiraManagerException_B_2() {
        JiraManagerException e = new JiraManagerException(" ", CAUSE);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());

    }

    /**
     * Tests <code>JiraManagerException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null cause as second argument.
     */
    public void testJiraManagerException_B_3() {
        JiraManagerException e = new JiraManagerException("abc", (Throwable) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraManagerException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null exception data as second argument.
     */
    public void testJiraManagerException_C_1() {
        JiraManagerException e = new JiraManagerException(null, (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null exception data as second argument.
     */
    public void testJiraManagerException_C_2() {
        JiraManagerException e = new JiraManagerException(" ", DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
            .getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null exception data as second argument.
     */
    public void testJiraManagerException_C_3() {
        JiraManagerException e = new JiraManagerException("abc", (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument, null cause as second argument and null exception data as third argument.
     */
    public void testJiraManagerException_D_1() {
        JiraManagerException e = new JiraManagerException(null, null, null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument, non-null cause as second argument and non-null exception data
     * as third argument.
     */
    public void testJiraManagerException_D_2() {
        JiraManagerException e = new JiraManagerException(" ", CAUSE, DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
            .getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument, null cause as second argument and null exception data as third
     * argument.
     */
    public void testJiraManagerException_D_3() {
        JiraManagerException e = new JiraManagerException("abc", null, null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }
}
