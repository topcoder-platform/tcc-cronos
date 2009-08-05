/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.HibernateUtil;

/**
 * <p>
 * Tests the functionality of {@link ReviewStatus} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ReviewStatusTest extends TestCase {

    /**
     * Represents the <code>ReviewStatus</code> instance to test.
     */
    private ReviewStatus reviewStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        reviewStatus = new ReviewStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        reviewStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ReviewStatusTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#ReviewStatus()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ReviewStatus() {
        // check for null
        assertNotNull("ReviewStatus creation failed", reviewStatus);
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#getReviewStatusId()} and {@link ReviewStatus#setReviewStatusId(Long)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getReviewStatusId() {
        // set the value to test
        reviewStatus.setReviewStatusId(new Long(1));
        assertEquals("getReviewStatusId and setReviewStatusId failure occured", new Long(1), reviewStatus
                .getReviewStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#setReviewStatusId(Long)} and {@link ReviewStatus#getReviewStatusId()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setReviewStatusId() {
        // set the value to test
        reviewStatus.setReviewStatusId(1L);
        assertEquals("getReviewStatusId and setReviewStatusId failure occured", 1L, (long) reviewStatus
                .getReviewStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#getDescription()} and {@link ReviewStatus#setDescription(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        reviewStatus.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, reviewStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#setDescription(String)} and {@link ReviewStatus#getDescription()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        reviewStatus.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", reviewStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ReviewStatus status = new ReviewStatus();
        status.setReviewStatusId(1L);
        reviewStatus.setReviewStatusId(1L);
        assertTrue("failed equals", status.equals(reviewStatus));
        assertTrue("failed hashCode", status.hashCode() == reviewStatus.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ReviewStatus status = new ReviewStatus();
        status.setReviewStatusId(2L);
        reviewStatus.setReviewStatusId(1L);
        assertFalse("failed equals", status.equals(reviewStatus));
        assertFalse("failed hashCode", status.hashCode() == reviewStatus.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ReviewStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object status = new Object();
        reviewStatus.setReviewStatusId(1L);
        assertFalse("failed equals", reviewStatus.equals(status));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ReviewStatus}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ReviewStatus entity = new ReviewStatus();
            entity.setReviewStatusId(1L);
            entity.setDescription("description");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ReviewStatus persisted = (ReviewStatus) HibernateUtil.getManager().find(ReviewStatus.class,
                    entity.getReviewStatusId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ReviewStatus) HibernateUtil.getManager().find(ReviewStatus.class,
                    entity.getReviewStatusId());
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
