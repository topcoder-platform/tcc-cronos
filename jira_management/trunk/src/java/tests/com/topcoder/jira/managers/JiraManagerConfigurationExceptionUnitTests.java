/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers;

import junit.framework.TestCase;

import com.topcoder.util.errorhandling.BaseRuntimeException;
import com.topcoder.util.errorhandling.ExceptionData;

/**
 * Unit tests for the <code>JiraManagerConfigurationException</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraManagerConfigurationExceptionUnitTests extends TestCase {

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
        assertEquals("incorrect super class", BaseRuntimeException.class,
                JiraManagerConfigurationException.class.getSuperclass());
    }

    /**
     * Tests <code>JiraManagerConfigurationException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes null.
     */
    public void testJiraManagerConfigurationException_A_1() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException((String) null);
        assertNull("message was propagated incorrectly", e.getMessage());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes empty
     * string.
     */
    public void testJiraManagerConfigurationException_A_2() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException(" ");
        assertEquals("message was propagated incorrectly", " ", e.getMessage());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameter. Passes
     * "abc".
     */
    public void testJiraManagerConfigurationException_A_3() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException("abc");
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null cause as second argument.
     */
    public void testJiraManagerConfigurationException_B_1() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException((String) null,
                (Throwable) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null cause as second argument.
     */
    public void testJiraManagerConfigurationException_B_2() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException(" ", CAUSE);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, Throwable)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null cause as second argument.
     */
    public void testJiraManagerConfigurationException_B_3() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException("abc", (Throwable) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument and null exception data as second argument.
     */
    public void testJiraManagerConfigurationException_C_1() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException((String) null,
                (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument and non-null exception data as second argument.
     */
    public void testJiraManagerConfigurationException_C_2() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException(" ", DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
                .getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument and null exception data as second argument.
     */
    public void testJiraManagerConfigurationException_C_3() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException("abc",
                (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * as first argument, null cause as second argument and null exception data as third argument.
     */
    public void testJiraManagerConfigurationException_D_1() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException((String) null,
                (Throwable) null, (ExceptionData) null);
        assertNull("message was propagated incorrectly", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * empty string as first argument, non-null cause as second argument and non-null exception data
     * as third argument.
     */
    public void testJiraManagerConfigurationException_D_2() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException(" ", CAUSE, DATA);
        assertEquals("message was propagated incorrectly", " ", e.getMessage());
        assertEquals("cause was propagated incorrectly", CAUSE, e.getCause());
        DATA.setApplicationCode("abc");
        assertEquals("exception data were propagated incorrectly", DATA.getApplicationCode(), e
                .getApplicationCode());

    }

    /**
     * Tests <code>JiraManagerConfigurationException(String, Throwable, ExceptionData)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * "abc" as first argument, null cause as second argument and null exception data as third
     * argument.
     */
    public void testJiraManagerConfigurationException_D_3() {
        JiraManagerConfigurationException e = new JiraManagerConfigurationException("abc", (Throwable) null,
                (ExceptionData) null);
        assertEquals("message was propagated incorrectly", "abc", e.getMessage());
        assertNull("cause was propagated incorrectly", e.getCause());
        assertNull("exception data were propagated incorrectly", e.getApplicationCode());

    }
}
