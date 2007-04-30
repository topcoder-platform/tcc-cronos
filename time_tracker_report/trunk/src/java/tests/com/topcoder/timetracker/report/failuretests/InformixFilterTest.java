/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.failuretests;

import com.topcoder.timetracker.report.informix.InformixFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Failure test for <code>InformixFilter</code>.
 *
 * @author enefem21
 * @version 3.1
 */
public class InformixFilterTest extends TestCase {

    /**
     * <p>
     * Return the suite for this unit test.
     * </p>
     *
     * @return the suite
     */
    public static Test suite() {
        return new TestSuite(InformixFilterTest.class);
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: ids is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterCompaniesNull() {
        try {
            InformixFilter.getFilterCompanies(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: ids is empty array. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterCompaniesEmpty() {
        try {
            InformixFilter.getFilterCompanies(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: ids is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterProjectsNull() {
        try {
            InformixFilter.getFilterProjects(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: ids is empty array. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterProjectsEmpty() {
        try {
            InformixFilter.getFilterProjects(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: ids is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterClientsNull() {
        try {
            InformixFilter.getFilterClients(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: ids is empty array. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterClientsEmpty() {
        try {
            InformixFilter.getFilterClients(new long[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: usernames is null. Expect:
     * <code>IllegalArgumentException</code>.
     */
    public void testGetFilterUsernamesNull() {
        try {
            InformixFilter.getFilterUsernames(null);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: usernames is empty array.
     * Expect: <code>IllegalArgumentException</code>.
     */
    public void testGetFilterUsernamesEmpty() {
        try {
            InformixFilter.getFilterUsernames(new String[0]);
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Test <code>getFilterCompanies</code> for failure. Condition: usernames contains null.
     * Expect: <code>IllegalArgumentException</code>.
     */
    public void testGetFilterUsernamesContainNull() {
        try {
            InformixFilter.getFilterUsernames(new String[] {null });
            fail("Should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

}
