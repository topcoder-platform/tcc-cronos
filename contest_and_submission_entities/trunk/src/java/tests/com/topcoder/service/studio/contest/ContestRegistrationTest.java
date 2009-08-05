/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Query;

import com.topcoder.service.studio.submission.MilestonePrize;
import com.topcoder.service.studio.submission.PrizeType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestRegistration} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ContestRegistrationTest extends TestCase {

    /**
     * Represents the <code>ContestRegistration</code> instance to test.
     */
    private ContestRegistration contestRegistration = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestRegistration = new ContestRegistration();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestRegistration = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestRegistrationTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#ContestRegistration()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestRegistration() {
        // check for null
        assertNotNull("ContestRegistration creation failed", contestRegistration);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#getContest()} and
     * {@link ContestRegistration#setContest(Contest)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContest() {
        // set the value to test
        contestRegistration.setContest(null);
        assertEquals("getContest and setContest failure occured", null, contestRegistration.getContest());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#setContest(Contest)} and
     * {@link ContestRegistration#getContest()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContest() {
        // set the value to test
        Contest contest = new Contest();
        contest.setContestId(1L);
        contestRegistration.setContest(contest);
        assertEquals("getContest and setContest failure occured", 1L, (long) contestRegistration.getContest()
                .getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#getCreateDate()} and
     * {@link ContestRegistration#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        contestRegistration.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, contestRegistration.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#setCreateDate(Date)} and
     * {@link ContestRegistration#getCreateDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        contestRegistration.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, contestRegistration.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#getUserId()} and {@link ContestRegistration#setUserId(Long)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving.
     * </p>
     */
    public void test_accuracy_getUserId() {
        // set the value to test
        contestRegistration.setUserId(1L);
        assertEquals("getUserId and setUserId failure occured", 1L, (long) contestRegistration.getUserId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#getTermsOfUseId()} and
     * {@link ContestRegistration#setTermsOfUseId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving.
     * </p>
     */
    public void test_accuracy_getTermsOfUseId() {
        // set the value to test
        contestRegistration.setTermsOfUseId(1L);
        assertEquals("getTermsOfUseId and setTermsOfUseId failure occured", 1L, (long) contestRegistration
                .getTermsOfUseId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestRegistration reg = new ContestRegistration();
        Contest contest = new Contest();
        contest.setContestId(20L);
        reg.setContest(contest);
        reg.setUserId(1L);
        contestRegistration.setContest(contest);
        contestRegistration.setUserId(1L);
        assertTrue("equals failed", contestRegistration.equals(reg));
        assertTrue("hashCode failed", contestRegistration.hashCode() == reg.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#equals(Object)}. Different composite ids.
     * </p>
     */
    public void test_equals_2() {
        ContestRegistration result = new ContestRegistration();
        Contest contest = new Contest();
        contest.setContestId(20L);
        result.setUserId(1L);
        contestRegistration.setContest(contest);
        contestRegistration.setUserId(1L);
        assertFalse("equals failed", contestRegistration.equals(result));
        assertFalse("hashCode failed", contestRegistration.hashCode() == result.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#equals(Object)}. Different composite ids.
     * </p>
     */
    public void test_equals_3() {
        ContestRegistration result = new ContestRegistration();
        Contest contest = new Contest();
        contest.setContestId(20L);
        result.setContest(contest);
        contestRegistration.setContest(contest);
        contestRegistration.setUserId(1L);
        assertFalse("equals failed", contestRegistration.equals(result));
    }

    /**
     * <p>
     * Accuracy test for {@link ContestRegistration#equals(Object)}. Different composite ids.
     * </p>
     */
    public void test_equals_4() {
        Object result = new Object();
        Contest contest = new Contest();
        contest.setContestId(20L);
        contestRegistration.setContest(contest);
        contestRegistration.setUserId(1L);
        assertFalse("equals failed", contestRegistration.equals(result));
    }

    /**
     * <p>
     * Accuracy test for <code>toString</code> method.
     * </p>
     */
    public void test_toString() {
        ContestRegistration entity = new ContestRegistration();
        Contest contest = new Contest();
        contest.setContestId(1L);
        entity.setContest(contest);
        entity.setCreateDate(new Date());
        entity.setUserId(1L);
        entity.setTermsOfUseId(1L);

        String str = entity.toString();

        assertTrue("invalid string representation.", str.indexOf("ContestId") >= 0);
    }

    /**
     * <p>
     * Checks how the composite id logic in the hash code and equals performs in a set addition.
     * </p>
     * <p>
     * Also the number of elements in the set should be same as the number added. Checks the seconds taking for
     * this operation.
     * </p>
     */
    public void test_hashCode() {
        int num = 10000;
        Set<ContestRegistration> set = new HashSet<ContestRegistration>(num);
        // check how the hashcode and equals working for for set insertion.
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            ContestRegistration result = new ContestRegistration();
            Contest contest = new Contest();
            contest.setContestId(new Long(i));
            result.setUserId(new Long(i));
            set.add(result);
        }
        long endTime = System.currentTimeMillis();
        long sec = (endTime - startTime);
        assertEquals("hashCode failed", num, set.size());
        System.out.println("Total Time : " + sec + " milli seconds for " + num + " elements ");
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link SubmissionReview}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            Date date = new Date();

            ContestChannel channel = new ContestChannel();
            TestHelper.populateContestChannel(channel);
            HibernateUtil.getManager().persist(channel);

            ContestType contestType = new ContestType();
            TestHelper.populateContestType(contestType);
            HibernateUtil.getManager().persist(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("Name");
            status.setContestStatusId(10L);
            status.setStatusId(1L);
            HibernateUtil.getManager().persist(status);

            ContestGeneralInfo generalInfo = new ContestGeneralInfo();
            generalInfo.setBrandingGuidelines("guideline");
            generalInfo.setDislikedDesignsWebsites("disklike");
            generalInfo.setGoals("goal");
            generalInfo.setOtherInstructions("instruction");
            generalInfo.setTargetAudience("target audience");
            generalInfo.setWinningCriteria("winning criteria");

            ContestMultiRoundInformation multiRoundInformation = new ContestMultiRoundInformation();
            multiRoundInformation.setMilestoneDate(new Date());
            multiRoundInformation.setRoundOneIntroduction("round one");
            multiRoundInformation.setRoundTwoIntroduction("round two");

            ContestSpecifications specifications = new ContestSpecifications();
            specifications.setAdditionalRequirementsAndRestrictions("none");
            specifications.setColors("white");
            specifications.setFonts("Arial");
            specifications.setLayoutAndSize("10px");

            PrizeType prizeType = new PrizeType();
            prizeType.setDescription("Good");
            prizeType.setPrizeTypeId(1L);
            HibernateUtil.getManager().persist(prizeType);

            MilestonePrize milestonePrize = new MilestonePrize();
            milestonePrize.setAmount(10.0);
            milestonePrize.setCreateDate(new Date());
            milestonePrize.setNumberOfSubmissions(1);
            milestonePrize.setType(prizeType);

            Contest contest = new Contest();

            TestHelper.populateContest(contest, date, channel, contestType, status, generalInfo, multiRoundInformation,
                    specifications, milestonePrize);
            HibernateUtil.getManager().persist(contest);

            ContestRegistration entity = new ContestRegistration();
            entity.setContest(contest);
            entity.setCreateDate(date);
            entity.setUserId(1L);
            entity.setTermsOfUseId(1L);

            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from ContestRegistration as c");

            ContestRegistration persisted = (ContestRegistration) query.getResultList().get(0);
            assertEquals("Failed to persist - contest mismatch", entity.getContest(), persisted.getContest());
            assertEquals("Failed to persist - createDate mismatch", entity.getCreateDate(), persisted
                    .getCreateDate());
            assertEquals("Failed to persist - termsOfUseId mismatch", entity.getTermsOfUseId(), persisted
                    .getTermsOfUseId());

            // update the entity
            entity.setTermsOfUseId(2L);
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestRegistration) query.getResultList().get(0);
            assertEquals("Failed to update - termsOfUseId mismatch", entity.getTermsOfUseId(), persisted
                    .getTermsOfUseId());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(contest);
            HibernateUtil.getManager().remove(prizeType);
            HibernateUtil.getManager().remove(status);
            HibernateUtil.getManager().remove(contestType);
            HibernateUtil.getManager().remove(channel);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
