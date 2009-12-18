/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.PaymentType;
import com.topcoder.service.studio.submission.PaymentStatus;

/**
 * <p>
 * Tests the functionality of {@link ContestPayment} class.
 * </p>
 *
 * @author superZZ, TCSDEVELOPER
 * @version 1.2
 */
public class ContestPaymentTest extends TestCase {

    /**
     * Represents the <code>ContestPayment</code> instance to test.
     */
    private ContestPayment contestPayment = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestPayment = new ContestPayment();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestPayment = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestPaymentTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#ContestPayment()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestPayment() {
        // check for null
        assertNotNull("ContestPayment creation failed", contestPayment);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getContestPaymentId()} and
     * {@link ContestPayment#setContestPaymentId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestPaymentId() {
        // set the value to test
        contestPayment.setContestPaymentId(new Long(1));
        assertEquals("getContestPaymentId and setContestPaymentId failure occured", 1L, (long) contestPayment
                .getContestPaymentId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setContestPaymentId(Long)} and
     * {@link ContestPayment#getContestPaymentId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestPaymentId() {
        // set the value to test
        contestPayment.setContestPaymentId(1L);
        assertEquals("getContestPaymentId and setContestPaymentId failure occured", 1L, (long) contestPayment
                .getContestPaymentId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getContestId()} and {@link ContestPayment#setContestId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_setContestId() {
        // set the value to test
        contestPayment.setContestId(1);
        assertEquals("getContest and setContest failure occured", 1, contestPayment.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getContestId()} and {@link ContestPayment#setContestId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContestId() {
        // set the value to test
        contestPayment.setContestId(1);
        assertEquals("getContest and setContest failure occured", 1, contestPayment.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getCreateDate()} and {@link ContestPayment#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        contestPayment.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, contestPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setCreateDate(Date)} and {@link ContestPayment#getCreateDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        contestPayment.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, contestPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getPaymentReferenceId()} and
     * {@link ContestPayment#setPaymentReferenceId(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPaymentReferenceId() {
        // set the value to test
        contestPayment.setPaymentReferenceId(null);
        assertEquals("getPaymentReferenceId and setPaymentReferenceId failure occured", null, contestPayment
                .getPaymentReferenceId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setPaymentReferenceId(String)} and
     * {@link ContestPayment#getPaymentReferenceId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPaymentReferenceId() {
        // set the value to test
        contestPayment.setPaymentReferenceId("test");
        assertEquals("getPaymentReferenceId and setPaymentReferenceId failure occured", "test", contestPayment
                .getPaymentReferenceId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getPayPalOrderId()} and {@link ContestPayment#setPayPalOrderId(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPayPalOrderId() {
        // set the value to test
        contestPayment.setPayPalOrderId(null);
        assertEquals("getPayPalOrderId and setPayPalOrderId failure occured", null, contestPayment.getPayPalOrderId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setPayPalOrderId(String)} and {@link ContestPayment#getPayPalOrderId()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPayPalOrderId() {
        // set the value to test
        contestPayment.setPayPalOrderId("test");
        assertEquals("getPayPalOrderId and setPayPalOrderId failure occured", "test", contestPayment.getPayPalOrderId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getStatus()} and {@link ContestPayment#setStatus(PaymentStatus)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStatus() {
        // set the value to test
        contestPayment.setStatus(null);
        assertEquals("getStatus and setStatus failure occured", null, contestPayment.getStatus());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setStatus(PaymentStatus)} and {@link ContestPayment#getStatus()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setStatus() {
        // set the value to test
        PaymentStatus status = new PaymentStatus();
        status.setPaymentStatusId(1L);
        contestPayment.setStatus(status);
        assertEquals("getStatus and setStatus failure occured", 1L, (long) contestPayment.getStatus()
                .getPaymentStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getPaymentType()} and {@link ContestPayment#setPaymentType(PaymentType)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPaymentType() {
        // set the value to test
        contestPayment.setPaymentType(null);
        assertEquals("getPaymentType and setPaymentType failure occured", null, contestPayment.getPaymentType());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setPaymentType(PaymentType)} and {@link ContestPayment#getPaymentType()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPaymentType() {
        // set the value to test
        PaymentType paymentType = new PaymentType();
        paymentType.setPaymentTypeId(1L);
        contestPayment.setPaymentType(paymentType);
        assertEquals("getPaymentType and setPaymentType failure occured", 1L, (long) contestPayment.getPaymentType()
                .getPaymentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getPrice()} and {@link ContestPayment#setPrice(Double)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPrice() {
        // set the value to test
        contestPayment.setPrice(null);
        assertEquals("getPrice and setPrice failure occured", null, contestPayment.getPrice());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setPrice(Double)} and {@link ContestPayment#getPrice()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPrice() {
        // set the value to test
        contestPayment.setPrice(10.0);
        assertEquals("getPrice and setPrice failure occured", 10.0, (double) contestPayment.getPrice());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestPayment payment = new ContestPayment();
        payment.setContestId(1L);
        payment.setPayPalOrderId("paypal");
        contestPayment.setContestId(1L);
        contestPayment.setPayPalOrderId("paypal");
        assertTrue("equals", contestPayment.equals(payment));
        assertTrue("hashCode", contestPayment.hashCode() == payment.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#equals(Object)}. Both objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        Object obj = new Object();
        contestPayment.setContestId(1L);
        contestPayment.setPayPalOrderId("paypal");
        assertFalse("equals", contestPayment.equals(obj));
        assertFalse("hashCode", contestPayment.hashCode() == obj.hashCode());
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestPayment}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();

            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setDescription("description");
            paymentStatus.setPaymentStatusId(1L);

            HibernateUtil.getManager().persist(paymentStatus);

            PaymentType type = new PaymentType();
            type.setDescription("description");
            type.setPaymentTypeId(1L);
            HibernateUtil.getManager().persist(type);

            ContestPayment entity = new ContestPayment();
            entity.setContestId(1L);
            entity.setStatus(paymentStatus);
            entity.setPrice(500.0);
            entity.setCreateDate(new Date());
            entity.setPaymentType(type);
            entity.setPaymentReferenceId("reference");
            entity.setPayPalOrderId("paypal");

            HibernateUtil.getManager().persist(entity);
            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from ContestPayment as s");

            ContestPayment persisted = (ContestPayment) query.getResultList().get(0);

            assertEquals("Failed to persist - price mismatch", entity.getPrice(), persisted.getPrice());
            assertEquals("Failed to persist - contest mismatch", entity.getContestId(), persisted.getContestId());
            assertEquals("Failed to persist - status mismatch", entity.getStatus(), persisted.getStatus());

            // update the entity
            entity.setPrice(1200.0);
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestPayment) query.getResultList().get(0);
            assertEquals("Failed to update - price mismatch", entity.getPrice(), persisted.getPrice());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(paymentStatus);
            HibernateUtil.getManager().remove(type);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
