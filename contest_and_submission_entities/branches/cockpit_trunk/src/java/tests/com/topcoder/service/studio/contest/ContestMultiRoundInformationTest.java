/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestMultiRoundInformation} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestMultiRoundInformationTest extends TestCase {

    /**
     * Represents the <code>ContestMultiRoundInformation</code> instance to test.
     */
    private ContestMultiRoundInformation contestMultiRoundInformation = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestMultiRoundInformation = new ContestMultiRoundInformation();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestMultiRoundInformation = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestMultiRoundInformationTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#ContestMultiRoundInformation()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_PrizeType() {
        // check for null
        assertNotNull("ContestMultiRoundInformation creation failed", contestMultiRoundInformation);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#getContestMultiRoundInformationId()} and
     * {@link ContestMultiRoundInformation#setContestMultiRoundInformationId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestMultiRoundInformationId() {
        // set the value to test
        contestMultiRoundInformation.setContestMultiRoundInformationId(new Long(1));
        assertEquals("getContestMultiRoundInformationId and setContestMultiRoundInformationId failure occured",
                new Long(1), contestMultiRoundInformation.getContestMultiRoundInformationId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#setContestMultiRoundInformationId(Long)} and
     * {@link ContestMultiRoundInformation#getContestMultiRoundInformationId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestMultiRoundInformationId() {
        // set the value to test
        contestMultiRoundInformation.setContestMultiRoundInformationId(1L);
        assertEquals("getContestMultiRoundInformationId and setContestMultiRoundInformationId failure occured", 1L,
                (long) contestMultiRoundInformation.getContestMultiRoundInformationId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#isSubmittersLockedBetweenRounds()} and
     * {@link ContestMultiRoundInformation#setSubmittersLockedBetweenRounds(Boolean)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_isSubmittersLockedBetweenRounds() {
        // set the value to test
        contestMultiRoundInformation.setSubmittersLockedBetweenRounds(null);
        assertEquals("isSubmittersLockedBetweenRounds and setSubmittersLockedBetweenRounds failure occured", null,
                contestMultiRoundInformation.isSubmittersLockedBetweenRounds());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#setSubmittersLockedBetweenRounds(Boolean)} and
     * {@link ContestMultiRoundInformation#isSubmittersLockedBetweenRounds()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmittersLockedBetweenRounds() {
        // set the value to test
        contestMultiRoundInformation.setSubmittersLockedBetweenRounds(true);
        assertEquals("getSubmittersLockedBetweenRounds and setSubmittersLockedBetweenRounds failure occured", true,
                (boolean) contestMultiRoundInformation.isSubmittersLockedBetweenRounds());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#getMilestoneDate()} and
     * {@link ContestMultiRoundInformation#setMilestoneDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getMilestoneDate() {
        // set the value to test
        contestMultiRoundInformation.setMilestoneDate(null);
        assertEquals("getMilestoneDate and setMilestoneDate failure occured", null, contestMultiRoundInformation
                .getMilestoneDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#setMilestoneDate(Date)} and
     * {@link ContestMultiRoundInformation#getMilestoneDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMilestoneDate() {
        // set the value to test
        Date date = new Date();
        contestMultiRoundInformation.setMilestoneDate(date);
        assertEquals("getMilestoneDate and setMilestoneDate failure occured", date, contestMultiRoundInformation
                .getMilestoneDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#getRoundOneIntroduction()} and
     * {@link ContestMultiRoundInformation#setRoundOneIntroduction(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getRoundOneIntroduction() {
        // set the value to test
        contestMultiRoundInformation.setRoundOneIntroduction(null);
        assertEquals("getRoundOneIntroduction and setRoundOneIntroduction failure occured", null,
                contestMultiRoundInformation.getRoundOneIntroduction());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#setRoundOneIntroduction(String)} and
     * {@link ContestMultiRoundInformation#getRoundOneIntroduction()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setRoundOneIntroduction() {
        // set the value to test
        contestMultiRoundInformation.setRoundOneIntroduction("test");
        assertEquals("getRoundOneIntroduction and setRoundOneIntroduction failure occured", "test",
                contestMultiRoundInformation.getRoundOneIntroduction());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#getRoundTwoIntroduction()} and
     * {@link ContestMultiRoundInformation#setRoundTwoIntroduction(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getRoundTwoIntroduction() {
        // set the value to test
        contestMultiRoundInformation.setRoundTwoIntroduction(null);
        assertEquals("getRoundTwoIntroduction and setRoundTwoIntroduction failure occured", null,
                contestMultiRoundInformation.getRoundTwoIntroduction());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#setRoundTwoIntroduction(String)} and
     * {@link ContestMultiRoundInformation#getRoundTwoIntroduction()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setRoundTwoIntroduction() {
        // set the value to test
        contestMultiRoundInformation.setRoundTwoIntroduction("test");
        assertEquals("getRoundTwoIntroduction and setRoundTwoIntroduction failure occured", "test",
                contestMultiRoundInformation.getRoundTwoIntroduction());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestMultiRoundInformation resource = new ContestMultiRoundInformation();
        resource.setContestMultiRoundInformationId(1L);
        contestMultiRoundInformation.setContestMultiRoundInformationId(1L);
        assertTrue("equals", resource.equals(contestMultiRoundInformation));
        assertTrue("hashCode", resource.hashCode() == contestMultiRoundInformation.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        ContestMultiRoundInformation resource = new ContestMultiRoundInformation();
        resource.setContestMultiRoundInformationId(2L);
        contestMultiRoundInformation.setContestMultiRoundInformationId(1L);
        assertFalse("failed equals", resource.equals(contestMultiRoundInformation));
        assertFalse("failed hashCode", resource.hashCode() == contestMultiRoundInformation.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestMultiRoundInformation#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object specifications = new Object();
        contestMultiRoundInformation.setContestMultiRoundInformationId(1L);
        assertFalse("failed equals", contestMultiRoundInformation.equals(specifications));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestMultiRoundInformation}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestMultiRoundInformation entity = new ContestMultiRoundInformation();
            entity.setRoundOneIntroduction("test_color");
            entity.setRoundTwoIntroduction("test_fonts");
            entity.setMilestoneDate(new Date());
            entity.setSubmittersLockedBetweenRounds(Boolean.FALSE);

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            ContestMultiRoundInformation persisted = (ContestMultiRoundInformation) HibernateUtil.getManager().find(
                    ContestMultiRoundInformation.class, entity.getContestMultiRoundInformationId());
            assertEquals("Failed to persist - colors mismatch", entity.getRoundOneIntroduction(), persisted
                    .getRoundOneIntroduction());
            assertEquals("Failed to persist - fonts mismatch", entity.getRoundTwoIntroduction(), persisted
                    .getRoundTwoIntroduction());

            // update the entity
            entity.setRoundOneIntroduction("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestMultiRoundInformation) HibernateUtil.getManager().find(
                    ContestMultiRoundInformation.class, entity.getContestMultiRoundInformationId());
            assertEquals("Failed to persist - color mismatch", entity.getRoundOneIntroduction(), persisted
                    .getRoundOneIntroduction());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
