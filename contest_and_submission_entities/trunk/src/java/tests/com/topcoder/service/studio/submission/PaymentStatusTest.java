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
 * Tests the functionality of {@link PaymentStatus} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class PaymentStatusTest extends TestCase {

    /**
     * Represents the <code>PaymentStatus</code> instance to test.
     */
    private PaymentStatus paymentStatus = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        paymentStatus = new PaymentStatus();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        paymentStatus = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(PaymentStatusTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#PaymentStatus()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PaymentStatus() {
        // check for null
        assertNotNull("PaymentStatus creation failed", paymentStatus);
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#getPaymentStatusId()} and
     * {@link PaymentStatus#setPaymentStatusId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getPaymentStatusId() {
        // set the value to test
        paymentStatus.setPaymentStatusId(new Long(1));
        assertEquals("getPaymentStatusId and setPaymentStatusId failure occured", new Long(1), paymentStatus
                .getPaymentStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#setPaymentStatusId(Long)} and
     * {@link PaymentStatus#getPaymentStatusId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPaymentStatusId() {
        // set the value to test
        paymentStatus.setPaymentStatusId(1L);
        assertEquals("getPaymentStatusId and setPaymentStatusId failure occured", 1L, (long) paymentStatus
                .getPaymentStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#getDescription()} and {@link PaymentStatus#setDescription(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        paymentStatus.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, paymentStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#setDescription(String)} and {@link PaymentStatus#getDescription()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        paymentStatus.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", paymentStatus.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        PaymentStatus status = new PaymentStatus();
        status.setPaymentStatusId(1L);
        paymentStatus.setPaymentStatusId(1L);
        assertTrue("failed equals", paymentStatus.equals(status));
        assertTrue("failed hashCode", paymentStatus.hashCode() == status.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        PaymentStatus status = new PaymentStatus();
        status.setPaymentStatusId(2L);
        paymentStatus.setPaymentStatusId(1L);
        assertFalse("failed equals", paymentStatus.equals(status));
        assertFalse("failed hashCode", paymentStatus.hashCode() == status.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentStatus#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object status = new Object();
        paymentStatus.setPaymentStatusId(1L);
        assertFalse("failed equals", paymentStatus.equals(status));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link PaymentStatus}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            PaymentStatus entity = new PaymentStatus();
            entity.setDescription("description");
            entity.setPaymentStatusId(1L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            PaymentStatus persisted = (PaymentStatus) HibernateUtil.getManager().find(PaymentStatus.class,
                    entity.getPaymentStatusId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (PaymentStatus) HibernateUtil.getManager().find(PaymentStatus.class,
                    entity.getPaymentStatusId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
