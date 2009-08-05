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
 * Tests the functionality of {@link SubmissionStatus} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionStatusTest extends TestCase {

    /**
     * Represents the <code>SubmissionStatus</code> instance to test.
     */
    private SubmissionStatus submissionStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        submissionStatus = new SubmissionStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        submissionStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionStatusTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#SubmissionStatus()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_SubmissionStatus() {
        // check for null
        assertNotNull("SubmissionStatus creation failed", submissionStatus);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#getSubmissionStatusId()} and
     * {@link SubmissionStatus#setSubmissionStatusId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getSubmissionStatusId() {
        // set the value to test
        submissionStatus.setSubmissionStatusId(new Long(1));
        assertEquals("getSubmissionStatusId and setSubmissionStatusId failure occured", new Long(1),
                submissionStatus.getSubmissionStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#setSubmissionStatusId(Long)} and
     * {@link SubmissionStatus#getSubmissionStatusId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmissionStatusId() {
        // set the value to test
        submissionStatus.setSubmissionStatusId(1L);
        assertEquals("getSubmissionStatusId and setSubmissionStatusId failure occured", 1L,
                (long) submissionStatus.getSubmissionStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#getDescription()} and
     * {@link SubmissionStatus#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        submissionStatus.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, submissionStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#setDescription(String)} and
     * {@link SubmissionStatus#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        submissionStatus.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", submissionStatus
                .getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        SubmissionStatus status = new SubmissionStatus();
        status.setSubmissionStatusId(1L);
        submissionStatus.setSubmissionStatusId(1L);
        assertTrue("failed equals", status.equals(submissionStatus));
        assertTrue("failed hashCode", status.hashCode() == submissionStatus.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        SubmissionStatus status = new SubmissionStatus();
        status.setSubmissionStatusId(2L);
        submissionStatus.setSubmissionStatusId(1L);
        assertFalse("failed equals", status.equals(submissionStatus));
        assertFalse("failed hashCode", status.hashCode() == submissionStatus.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object status = new Object();
        submissionStatus.setSubmissionStatusId(1L);
        assertFalse("failed equals", submissionStatus.equals(status));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link SubmissionStatus}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            SubmissionStatus entity = new SubmissionStatus();
            entity.setDescription("description");
            entity.setSubmissionStatusId(1L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            SubmissionStatus persisted = (SubmissionStatus) HibernateUtil.getManager()
                .find(SubmissionStatus.class, entity
                    .getSubmissionStatusId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (SubmissionStatus) HibernateUtil.getManager()
                .find(SubmissionStatus.class, entity.getSubmissionStatusId());
            assertEquals("Failed to update - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
