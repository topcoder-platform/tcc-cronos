/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.HibernateUtil;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.TestHelper;
import com.topcoder.service.studio.submission.PaymentStatus;

/**
 * <p>
 * Tests the functionality of {@link ContestPayment} class.
 * </p>
 * 
 * @author superZZ
 * @version 1.0
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
     * Accuracy test for {@link ContestPayment#getContest()} and
     * {@link ContestPayment#setContest(Contest)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getContest() {
        // set the value to test
        contestPayment.setContestId(1);
        assertEquals("getContest and setContest failure occured", 1, contestPayment.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#getStatus()} and
     * {@link ContestPayment#setStatus(PaymentStatus)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getStatus() {
        // set the value to test
        contestPayment.setStatus(null);
        assertEquals("getStatus and setStatus failure occured", null, contestPayment.getStatus());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setStatus(PaymentStatus)} and
     * {@link ContestPayment#getStatus()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
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
     * Accuracy test for {@link ContestPayment#getPrice()} and
     * {@link ContestPayment#setPrice(Double)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * null.
     * </p>
     */
    public void test_accuracy_getPrice() {
        // set the value to test
        contestPayment.setPrice(null);
        assertEquals("getPrice and setPrice failure occured", null, contestPayment.getPrice());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#setPrice(Double)} and
     * {@link ContestPayment#getPrice()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is
     * Valid.
     * </p>
     */
    public void test_accuracy_setPrice() {
        // set the value to test
        contestPayment.setPrice(10.0);
        assertEquals("getPrice and setPrice failure occured", 10.0, (double) contestPayment.getPrice());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestPayment#equals(Object)}. Both objects are
     * equal.
     * </p>
     */
    public void test_equals_1() {
        ContestPayment payment = new ContestPayment();
        payment.setContestId(1L);
        assertTrue("failed equals", contestPayment.equals(payment));
        assertTrue("failed hashCode", contestPayment.hashCode() == payment.hashCode());
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestPayment}</code>.
     * </p>
     */
    public void test_persistence() {
        try {
            HibernateUtil.getManager().getTransaction().begin();
            PaymentStatus paymentStatus = HibernateUtil.getManager().find(PaymentStatus.class, 1L);
            ContestPayment entity = new ContestPayment();
            entity.setContestId(2011);
            entity.setStatus(paymentStatus);
            entity.setPrice(500.0);

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

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
