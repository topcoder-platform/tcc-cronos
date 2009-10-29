/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Query;

import com.topcoder.service.studio.contest.HibernateUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link MilestonePrize} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MilestonePrizeTest extends TestCase {
    /**
     * Represents the <code>MilestonePrize</code> instance to test.
     */
    private MilestonePrize milestonePrize = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        milestonePrize = new MilestonePrize();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        milestonePrize = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(MilestonePrizeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#MilestonePrize()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_SubmissionStatus() {
        // check for null
        assertNotNull("MilestonePrize creation failed", milestonePrize);
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#getMilestonePrizeId()} and
     * {@link MilestonePrize#setMilestonePrizeId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getMilestonePrizeId() {
        // set the value to test
        milestonePrize.setMilestonePrizeId(new Long(1));
        assertEquals("getMilestonePrizeId and setMilestonePrizeId failure occured", new Long(1), milestonePrize
                .getMilestonePrizeId());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#setMilestonePrizeId(Long)} and
     * {@link MilestonePrize#getMilestonePrizeId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMilestonePrizeId() {
        // set the value to test
        milestonePrize.setMilestonePrizeId(1L);
        assertEquals("getMilestonePrizeId and setMilestonePrizeId failure occured", 1L, (long) milestonePrize
                .getMilestonePrizeId());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#getCreateDate()} and {@link MilestonePrize#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        milestonePrize.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, milestonePrize.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#setCreateDate(Date)} and {@link MilestonePrize#getCreateDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        milestonePrize.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, milestonePrize.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#getAmount()} and {@link MilestonePrize#setAmount(Double)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getAmount() {
        // set the value to test
        milestonePrize.setAmount(null);
        assertEquals("getAmount and setAmount failure occured", null, milestonePrize.getAmount());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#setAmount(Double)} and {@link MilestonePrize#getAmount()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setAmount() {
        // set the value to test
        milestonePrize.setAmount(10.0);
        assertEquals("getAmount and setAmount failure occured", 10.0, (double) milestonePrize.getAmount());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#getNumberOfSubmissions()} and
     * {@link MilestonePrize#setNumberOfSubmissions(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getNumberOfSubmissions() {
        // set the value to test
        milestonePrize.setNumberOfSubmissions(null);
        assertEquals("getNumberOfSubmissions and setNumberOfSubmissions failure occured", null, milestonePrize
                .getNumberOfSubmissions());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#setNumberOfSubmissions(Integer)} and
     * {@link MilestonePrize#getNumberOfSubmissions()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setNumberOfSubmissions() {
        // set the value to test
        milestonePrize.setNumberOfSubmissions(1);
        assertEquals("getNumberOfSubmissions and setNumberOfSubmissions failure occured", 1, (int) milestonePrize
                .getNumberOfSubmissions());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#getSubmissions()} and
     * {@link MilestonePrize#setSubmissions(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSubmissions() {
        // set the value to test
        milestonePrize.setSubmissions(null);
        assertEquals("getSubmissions and setSubmissions failure occured", null, milestonePrize.getSubmissions());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#setSubmissions(Set)} and
     * {@link MilestonePrize#getSubmissions()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmissions() {
        // set the value to test
        Set<Submission> subs = new HashSet<Submission>();
        subs.add(new Submission());
        milestonePrize.setSubmissions(subs);
        assertEquals("getSubmissions and setSubmissions failure occured", subs.size(), milestonePrize.getSubmissions()
                .size());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#setType(PrizeType)} and {@link MilestonePrize#getType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setType() {
        // set the value to test
        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);
        milestonePrize.setType(type);
        assertEquals("getType and setType failure occured", 1L, (long) milestonePrize.getType().getPrizeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#getType()} and {@link MilestonePrize#setType(Type)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getType() {
        // set the value to test
        milestonePrize.setType(null);
        assertEquals("getType and setType failure occured", null, milestonePrize.getType());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        MilestonePrize milestonePrize1 = new MilestonePrize();
        milestonePrize1.setAmount(10.0);
        milestonePrize1.setCreateDate(new Date());
        milestonePrize1.setNumberOfSubmissions(1);
        milestonePrize1.setMilestonePrizeId(1L);

        milestonePrize.setMilestonePrizeId(1L);
        assertTrue("equals", milestonePrize.equals(milestonePrize1));
        assertTrue("hashCode", milestonePrize.hashCode() == milestonePrize.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        MilestonePrize milestonePrize1 = new MilestonePrize();
        milestonePrize1.setAmount(10.0);
        milestonePrize1.setCreateDate(new Date());
        milestonePrize1.setNumberOfSubmissions(1);
        milestonePrize1.setMilestonePrizeId(1L);

        milestonePrize.setMilestonePrizeId(2L);
        assertFalse("failed equals", milestonePrize.equals(milestonePrize1));
        assertFalse("failed hashCode", milestonePrize.hashCode() == milestonePrize1.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link MilestonePrize#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object prize = new Object();
        assertFalse("failed equals", milestonePrize.equals(prize));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link MilestonePrize}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("Good");
            prizeType.setPrizeTypeId(1L);
            HibernateUtil.getManager().persist(prizeType);

            MilestonePrize entity = new MilestonePrize();
            entity.setAmount(10.0);
            entity.setCreateDate(new Date());
            entity.setNumberOfSubmissions(1);
            entity.setType(prizeType);
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from MilestonePrize as s");

            MilestonePrize persisted = (MilestonePrize) query.getResultList().get(0);

            assertEquals("Failed to persist - amount mismatch", entity.getAmount(), persisted.getAmount());
            assertEquals("Failed to persist - type mismatch", entity.getType(), persisted.getType());
            assertEquals("Failed to persist - create date mismatch", entity.getCreateDate(), persisted.getCreateDate());
            assertEquals("Failed to persist - number of submissions mismatch", entity.getNumberOfSubmissions(),
                    persisted.getNumberOfSubmissions());

            // update the entity
            entity.setAmount(1200.0);
            HibernateUtil.getManager().merge(entity);

            persisted = (MilestonePrize) query.getResultList().get(0);
            assertEquals("Failed to update - amount mismatch", entity.getAmount(), persisted.getAmount());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(prizeType);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
