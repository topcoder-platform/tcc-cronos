/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for the <code>JiraSecurityTokenExpiredException</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraSecurityTokenExpiredExceptionUnitTests extends TestCase {

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
            JiraSecurityTokenExpiredException.class.getSuperclass());
    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes null.
     */
    public void testJiraSecurityTokenExpiredException_A_1() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException((String) null);
        assertNull("message was propagated incorrectly", e.getMessage());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes empty
     * string.
     */
    public void testJiraSecurityTokenExpiredException_A_2() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException(" ");
        assertEquals("message was propagated incorrectly", " ", e.getMessage());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes
     * "abc".
     */
    public void testJiraSecurityTokenExpiredException_A_3() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException("abc");
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null cause as second argument.
     */
    public void testJiraSecurityTokenExpiredException_B_1() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException((String) null,
            (Throwable) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null cause as second argument.
     */
    public void testJiraSecurityTokenExpiredException_B_2() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException(" ", CAUSE);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null cause as second argument.
     */
    public void testJiraSecurityTokenExpiredException_B_3() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException("abc",
            (Throwable) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null exception data as second argument.
     */
    public void testJiraSecurityTokenExpiredException_C_1() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException((String) null,
            (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null exception data as second argument.
     */
    public void testJiraSecurityTokenExpiredException_C_2() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException(" ", DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
            .getApplicationCode());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null exception data as second argument.
     */
    public void testJiraSecurityTokenExpiredException_C_3() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException("abc",
            (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument, null cause as second argument and null exception data as third argument.
     */
    public void testJiraSecurityTokenExpiredException_D_1() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException((String) null,
            (Throwable) null, (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument, non-null cause as second argument and non-null exception data
     * as third argument.
     */
    public void testJiraSecurityTokenExpiredException_D_2() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException(" ", CAUSE,
            DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
            .getApplicationCode());

    }

    /**
     * Tests <code>JiraSecurityTokenExpiredException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument, null cause as second argument and null exception data as third
     * argument.
     */
    public void testJiraSecurityTokenExpiredException_D_3() {
        JiraSecurityTokenExpiredException e = new JiraSecurityTokenExpiredException("abc",
            (Throwable) null, (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }
}
