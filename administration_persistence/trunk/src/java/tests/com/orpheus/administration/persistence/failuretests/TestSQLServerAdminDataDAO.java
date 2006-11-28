/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.persistence.failuretests;

import com.orpheus.administration.persistence.impl.PendingWinnerImpl;
import com.orpheus.administration.persistence.impl.SQLServerAdminDataDAO;

import com.topcoder.util.puzzle.PuzzleData;

import junit.framework.TestCase;

import java.util.Date;


/**
 * Unit tests for SQLServerAdminDataDAO class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestSQLServerAdminDataDAO extends TestCase {
    /** SQLServerAdminDataDAO used to test. */
    private SQLServerAdminDataDAO dao;

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        Helper.clearNamespaces();
        Helper.loadConfig("test_files/failure/failure.xml");

        dao = new SQLServerAdminDataDAO("admin.dao.valid");
    }

    /**
     * Setup the environment for each testcase.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        Helper.clearNamespaces();
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with null String namespace, Expected
     * IllegalArgumentException.
     */
    public void testSQLServerAdminDataDAO_NullNamespace() {
        try {
            new SQLServerAdminDataDAO(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with empty String namespace, Expected
     * IllegalArgumentException.
     */
    public void testSQLServerAdminDataDAO_EmptyNamespace() {
        try {
            new SQLServerAdminDataDAO(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with invalid configurable file,
     * Expected InstantiationException.
     */
    public void testSQLServerAdminDataDAO_LostAdminFee() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail1");
            fail("InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with empty configurable file, Expected
     * com.orpheus.administration.persistence.InstantiationException.
     */
    public void testSQLServerAdminDataDAO_EmptyAdminFee() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail2");
            fail("com.orpheus.administration.persistence.InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with invalid configurable file,
     * Expected com.orpheus.administration.persistence.InstantiationException.
     */
    public void testSQLServerAdminDataDAO_LostFactoryKey() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail4");
            fail("com.orpheus.administration.persistence.InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with empty configurable file, Expected
     * com.orpheus.administration.persistence.InstantiationException.
     */
    public void testSQLServerAdminDataDAO_EmptyFactoryKey() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail5");
            fail("com.orpheus.administration.persistence.InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with invalid configurable file,
     * Expected com.orpheus.administration.persistence.InstantiationException.
     */
    public void testSQLServerAdminDataDAO_InvalidAdminFee() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail3");
            fail("com.orpheus.administration.persistence.InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with invalid configurable file,
     * Expected com.orpheus.administration.persistence.InstantiationException.
     */
    public void testSQLServerAdminDataDAO_LostSpecNamespace() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail6");
            fail("com.orpheus.administration.persistence.InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests SQLServerAdminDataDAO(String namespace) method with empty configurable file, Expected
     * com.orpheus.administration.persistence.InstantiationException.
     */
    public void testSQLServerAdminDataDAO_EmptySpecNamespace() {
        try {
            new SQLServerAdminDataDAO("admin.dao.fail7");
            fail("com.orpheus.administration.persistence.InstantiationException should be thrown.");
        } catch (com.orpheus.administration.persistence.InstantiationException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests storePuzzles(PuzzleData[] puzzles) method with null PuzzleData[] puzzles, Expected
     * IllegalArgumentException.
     */
    public void testStorePuzzles_NullPuzzles() {
        try {
            dao.storePuzzles(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests storePuzzles(PuzzleData[] puzzles) method with empty PuzzleData[] puzzles, Expected
     * IllegalArgumentException.
     */
    public void testStorePuzzles_EmptyPuzzles() {
        try {
            dao.storePuzzles(new PuzzleData[0]);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests storePuzzles(PuzzleData[] puzzles) method with invalid PuzzleData[] puzzles, Expected
     * IllegalArgumentException.
     */
    public void testStorePuzzles_InvalidPuzzles() {
        try {
            dao.storePuzzles(new PuzzleData[] { null });
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests approveWinner(PendingWinner winner, Date date) method with null PendingWinner winner,
     * Expected IllegalArgumentException.
     */
    public void testApproveWinner_NullWinner() {
        try {
            dao.approveWinner(null, new Date());
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests approveWinner(PendingWinner winner, Date date) method with null Date date, Expected
     * IllegalArgumentException.
     */
    public void testApproveWinner_NullDate() {
        try {
            dao.approveWinner(new PendingWinnerImpl(1, 1, 1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }

    /**
     * Tests rejectWinner(PendingWinner winner) method with null PendingWinner winner, Expected
     * IllegalArgumentException.
     */
    public void testRejectWinner_NullWinner() {
        try {
            dao.rejectWinner(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // ok
        } catch (Exception e) {
            fail("unexpected exception throws.");
        }
    }
}
