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
 * Tests the functionality of {@link SubmissionType} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionTypeTest extends TestCase {

    /**
     * Represents the <code>SubmissionType</code> instance to test.
     */
    private SubmissionType submissionType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        submissionType = new SubmissionType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        submissionType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#SubmissionType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_SubmissionType() {
        // check for null
        assertNotNull("SubmissionType creation failed", submissionType);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#getSubmissionTypeId()} and
     * {@link SubmissionType#setSubmissionTypeId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getSubmissionTypeId() {
        // set the value to test
        submissionType.setSubmissionTypeId(new Long(1));
        assertEquals("getSubmissionTypeId and setSubmissionTypeId failure occured", new Long(1), submissionType
                .getSubmissionTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#setSubmissionTypeId(Long)} and
     * {@link SubmissionType#getSubmissionTypeId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmissionTypeId() {
        // set the value to test
        submissionType.setSubmissionTypeId(1L);
        assertEquals("getSubmissionTypeId and setSubmissionTypeId failure occured", 1L, (long) submissionType
                .getSubmissionTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#getDescription()} and {@link SubmissionType#setDescription(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        submissionType.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, submissionType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#setDescription(String)} and {@link SubmissionType#getDescription()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        submissionType.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", submissionType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        SubmissionType type = new SubmissionType();
        type.setSubmissionTypeId(1L);
        submissionType.setSubmissionTypeId(1L);
        assertTrue("failed equals", type.equals(submissionType));
        assertTrue("failed hashCode", type.hashCode() == submissionType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        SubmissionType type = new SubmissionType();
        type.setSubmissionTypeId(2L);
        submissionType.setSubmissionTypeId(1L);
        assertFalse("failed equals", type.equals(submissionType));
        assertFalse("failed hashCode", type.hashCode() == submissionType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object type = new Object();
        submissionType.setSubmissionTypeId(1L);
        assertFalse("failed equals", submissionType.equals(type));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link SubmissionType}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            SubmissionType entity = new SubmissionType();
            entity.setDescription("description");
            entity.setSubmissionTypeId(1L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            SubmissionType persisted = (SubmissionType) HibernateUtil.getManager()
                .find(SubmissionType.class, new Long(entity
                    .getSubmissionTypeId()));
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (SubmissionType) HibernateUtil.getManager()
                .find(SubmissionType.class, new Long(entity.getSubmissionTypeId()));
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
