/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ReviewApplication class.
 *
 * @author pvmagacho
 * @version 1.1
 */
public class ReviewApplicationUnitTests extends TestCase {
    /**
     * Aggregates all tests in this class.
     *
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ReviewApplicationUnitTests.class);
    }

    /**
     * ReviewAplication instance for testing.
     */
    private ReviewApplication reviewApplication = new ReviewApplication();

    /**
     * Accuracy test of
     * <code>Project(ProjectCategory projectCategory, ProjectStatus projectStatus)</code>
     * constructor.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testProjectAccuracy1() throws Exception {
        assertEquals("id is incorrect.", 0, reviewApplication.getId());
        assertEquals("projectd id is incorrect.", 0, reviewApplication.getProjectId());
        assertEquals("project reviewer is incorrect.", 0, reviewApplication.getReviewerId());
        assertNull("application date is incorrect.", reviewApplication.getApplicationDate());
    }

    /**
     * Accuracy test of <code>setId(long id)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdAccuracy() throws Exception {
        reviewApplication.setId(2);
        assertEquals("id is incorrect.", 2, reviewApplication.getId());
    }

    /**
     * Failure test of <code>setId(long id)</code> method.
     * <p>
     * id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetIdFailure() throws Exception {
        try {
            reviewApplication.setId(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getId()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetIdAccuracy() throws Exception {
        reviewApplication.setId(2);
        assertEquals("id is incorrect.", 2, reviewApplication.getId());
    }

    /**
     * Accuracy test of <code>setReviewerId(long id)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetReviewerIdAccuracy() throws Exception {
        reviewApplication.setReviewerId(2);
        assertEquals("id is incorrect.", 2, reviewApplication.getReviewerId());
    }

    /**
     * Failure test of <code>setReviewerId(long id)</code> method.
     * <p>
     * id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetReviewerIdFailure() throws Exception {
        try {
            reviewApplication.setReviewerId(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setReviewerId()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetReviewerIdAccuracy() throws Exception {
        reviewApplication.setReviewerId(2);
        assertEquals("id is incorrect.", 2, reviewApplication.getReviewerId());
    }

    /**
     * Accuracy test of <code>setProjectId(long id)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectIdAccuracy() throws Exception {
        reviewApplication.setProjectId(2);
        assertEquals("id is incorrect.", 2, reviewApplication.getProjectId());
    }

    /**
     * Failure test of <code>setProjectId(long id)</code> method.
     * <p>
     * id <= 0
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetProjectIdFailure() throws Exception {
        try {
            reviewApplication.setProjectId(0);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>setProjectId()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetProjectIdAccuracy() throws Exception {
        reviewApplication.setProjectId(2);
        assertEquals("id is incorrect.", 2, reviewApplication.getProjectId());
    }

    /**
     * Accuracy test of <code>setApplicationDate(Date date)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetApplicationDateAccuracy() throws Exception {
        Date now = new Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());
        reviewApplication.setApplicationDate(nowSql);
        assertEquals("date is incorrect.", nowSql, reviewApplication.getApplicationDate());
    }

    /**
     * Failure test of <code>setProjectId(long id)</code> method.
     * <p>
     * date is null
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetApplicationDateFailure() throws Exception {
        try {
            reviewApplication.setApplicationDate(null);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getApplicationDate()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetApplicationDateAccuracy() throws Exception {
        Date now = new Date();
        java.sql.Date nowSql = new java.sql.Date(now.getTime());
        reviewApplication.setApplicationDate(nowSql);
        assertEquals("date is incorrect.", nowSql, reviewApplication.getApplicationDate());
    }

    /**
     * Accuracy test of <code>setAcceptPrimary(boolean primary)</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSetAcceptPrimaryAccuracy() throws Exception {
        reviewApplication.setAcceptPrimary(true);
        assertTrue("flag is incorrect.", reviewApplication.isAcceptPrimary());
    }

    /**
     * Accuracy test of <code>getApplicationDate()</code> method.
     *
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAcceptPrimaryAccuracy() throws Exception {
        reviewApplication.setAcceptPrimary(false);
        assertFalse("flag is incorrect.", reviewApplication.isAcceptPrimary());
    }
}
