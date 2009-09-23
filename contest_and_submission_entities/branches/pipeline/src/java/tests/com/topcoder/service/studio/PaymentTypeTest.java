/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.HibernateUtil;

/**
 * <p>
 * Tests the functionality of {@link PaymentType} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PaymentTypeTest extends TestCase {

    /**
     * Represents the <code>PaymentType</code> instance to test.
     */
    private PaymentType paymentType = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        paymentType = new PaymentType();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        paymentType = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(PaymentTypeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#PaymentType()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PrizeType() {
        // check for null
        assertNotNull("PaymentType creation failed", paymentType);
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#getPaymentTypeId()} and {@link PaymentType#setPaymentTypeId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getPaymentTypeId() {
        // set the value to test
        paymentType.setPaymentTypeId(new Long(1));
        assertEquals("getPaymentTypeId and setPaymentTypeId failure occured", new Long(1), paymentType
                .getPaymentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#setPaymentTypeId(Long)} and {@link PaymentType#getPaymentTypeId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPaymentTypeId() {
        // set the value to test
        paymentType.setPaymentTypeId(1L);
        assertEquals("getPaymentTypeId and setPaymentTypeId failure occured", 1L,
                (long) paymentType.getPaymentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#getDescription()} and {@link PaymentType#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        paymentType.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, paymentType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#setDescription(String)} and {@link PaymentType#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        paymentType.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", paymentType.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        PaymentType paymentType1 = new PaymentType();
        paymentType1.setPaymentTypeId(1L);
        paymentType.setPaymentTypeId(1L);
        assertTrue("failed equals", paymentType1.equals(paymentType));
        assertTrue("failed hashCode", paymentType1.hashCode() == paymentType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        PaymentType paymentType1 = new PaymentType();
        paymentType1.setPaymentTypeId(2L);
        paymentType.setPaymentTypeId(1L);
        assertFalse("failed equals", paymentType1.equals(paymentType));
        assertFalse("failed hashCode", paymentType1.hashCode() == paymentType.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link PaymentType#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object paymentType1 = new Object();
        paymentType.setPaymentTypeId(1L);
        assertFalse("failed equals", paymentType.equals(paymentType1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link PaymentType}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            PaymentType entity = new PaymentType();
            entity.setDescription("description");
            entity.setPaymentTypeId(1L);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            PaymentType persisted = (PaymentType) HibernateUtil.getManager().find(PaymentType.class,
                    entity.getPaymentTypeId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // update the entity
            entity.setDescription("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (PaymentType) HibernateUtil.getManager().find(PaymentType.class, entity.getPaymentTypeId());
            assertEquals("Failed to persist - description mismatch", entity.getDescription(), persisted
                    .getDescription());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
