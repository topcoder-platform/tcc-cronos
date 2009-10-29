/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.HibernateUtil;

/**
 * <p>
 * Tests the functionality of {@link Prize} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class PrizeTest extends TestCase {

    /**
     * Represents the <code>Prize</code> instance to test.
     */
    private Prize prize = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        prize = new Prize();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        prize = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(PrizeTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#Prize()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Prize() {
        // check for null
        assertNotNull("Prize creation failed", prize);
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getPrizeId()} and {@link Prize#setPrizeId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getPrizeId() {
        // set the value to test
        prize.setPrizeId(new Long(1));
        assertEquals("getPrizeId and setPrizeId failure occured", new Long(1), prize.getPrizeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#setPrizeId(Long)} and {@link Prize#getPrizeId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPrizeId() {
        // set the value to test
        prize.setPrizeId(1L);
        assertEquals("getPrizeId and setPrizeId failure occured", 1L, (long) prize.getPrizeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getPlace()} and {@link Prize#setPlace(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPlace() {
        // set the value to test
        prize.setPlace(null);
        assertEquals("getPlace and setPlace failure occured", null, prize.getPlace());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#setPlace(Integer)} and {@link Prize#getPlace()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPlace() {
        // set the value to test
        prize.setPlace(1);
        assertEquals("getPlace and setPlace failure occured", 1, (int) prize.getPlace());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getAmount()} and {@link Prize#setAmount(Double)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getAmount() {
        // set the value to test
        prize.setAmount(null);
        assertEquals("getAmount and setAmount failure occured", null, prize.getAmount());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#setAmount(Double)} and {@link Prize#getAmount()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setAmount() {
        // set the value to test
        prize.setAmount(10.0);
        assertEquals("getAmount and setAmount failure occured", 10.0, (double) prize.getAmount());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getContests()} and {@link Prize#setContests(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContests() {
        // set the value to test
        prize.setContests(null);
        assertEquals("getContests and setContests failure occured", null, prize.getContests());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#setContests(Set)} and {@link Prize#getContests()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContests() {
        // set the value to test
        Set<Contest> contests = new HashSet<Contest>();
        contests.add(new Contest());
        prize.setContests(contests);
        assertEquals("getContests and setContests failure occured", contests.size(), prize.getContests().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getSubmissions()} and {@link Prize#setSubmissions(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSubmissions() {
        // set the value to test
        prize.setSubmissions(null);
        assertEquals("getSubmissions and setSubmissions failure occured", null, prize.getSubmissions());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#setSubmissions(Set)} and {@link Prize#getSubmissions()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmissions() {
        // set the value to test
        Set<Submission> subs = new HashSet<Submission>();
        subs.add(new Submission());
        prize.setSubmissions(subs);
        assertEquals("getSubmissions and setSubmissions failure occured", subs.size(), prize.getSubmissions()
                .size());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getType()} and {@link Prize#setType(PrizeType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getType() {
        // set the value to test
        prize.setType(null);
        assertEquals("getType and setType failure occured", null, prize.getType());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#setType(PrizeType)} and {@link Prize#getType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setType() {
        // set the value to test
        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);
        prize.setType(type);
        assertEquals("getType and setType failure occured", 1L, (long) prize.getType().getPrizeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getCreateDate()} and {@link Prize#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        prize.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, prize.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#getCreateDate()} and {@link Prize#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date now = new Date();
        prize.setCreateDate(now);
        assertEquals("getCreateDate and setCreateDate failure occured", now, prize.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        Prize prize1 = new Prize();
        prize1.setPrizeId(1L);
        prize.setPrizeId(1L);
        assertTrue("failed equals", prize1.equals(prize));
        assertTrue("failed hashCode", prize1.hashCode() == prize.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        Prize prize1 = new Prize();
        prize1.setPrizeId(2L);
        prize.setPrizeId(1L);
        assertFalse("failed equals", prize1.equals(prize));
        assertFalse("failed hashCode", prize1.hashCode() == prize.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Prize#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object prize1 = new Object();
        prize.setPrizeId(1L);
        assertFalse("failed equals", prize.equals(prize1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link Prize}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            PrizeType type = new PrizeType();
            type.setDescription("description");
            type.setPrizeTypeId(1L);
            HibernateUtil.getManager().persist(type);

            Prize entity = new Prize();
            entity.setAmount(500.0);
            entity.setPlace(1);
            entity.setType(type);
            entity.setCreateDate(new Date());

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Prize persisted = (Prize) HibernateUtil.getManager().find(Prize.class, entity.getPrizeId());
            assertEquals("Failed to persist - amount mismatch", entity.getAmount(), persisted.getAmount());
            assertEquals("Failed to persist - place mismatch", entity.getPlace(), persisted.getPlace());
            assertEquals("Failed to persist - type mismatch", entity.getType(), persisted.getType());
            assertEquals("Failed to persist - createDate mismatch", entity.getCreateDate(), persisted
                    .getCreateDate());

            // update the entity
            entity.setPlace(2);
            HibernateUtil.getManager().merge(entity);

            persisted = (Prize) HibernateUtil.getManager().find(Prize.class, entity.getPrizeId());
            assertEquals("Failed to update - place mismatch", entity.getPlace(), persisted.getPlace());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(type);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
