/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestGeneralInfo;
import com.topcoder.service.studio.contest.ContestMultiRoundInformation;
import com.topcoder.service.studio.contest.ContestSpecifications;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.HibernateUtil;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.contest.TestHelper;

/**
 * <p>
 * Tests the functionality of {@link ContestResult} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class ContestResultTest extends TestCase {

    /**
     * Represents the <code>ContestResult</code> instance to test.
     */
    private ContestResult contestResult = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestResult = new ContestResult();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestResult = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestResultTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#ContestResult()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_ContestResult() {
        // check for null
        assertNotNull("ContestResult creation failed", contestResult);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#getContest()} and {@link ContestResult#setContest(Contest)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContest() {
        // set the value to test
        contestResult.setContest(null);
        assertEquals("getContest and setContest failure occured", null, contestResult.getContest());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#setContest(Contest)} and {@link ContestResult#getContest()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContest() {
        // set the value to test
        Contest contest = new Contest();
        contest.setContestId(1L);
        contestResult.setContest(contest);
        assertEquals("getContest and setContest failure occured", 1L, (long) contestResult.getContest()
                .getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#getSubmission()} and {@link ContestResult#setSubmission(Submission)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSubmission() {
        // set the value to test
        contestResult.setSubmission(null);
        assertEquals("getSubmission and setSubmission failure occured", null, contestResult.getSubmission());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#setSubmission(Submission)} and {@link ContestResult#getSubmission()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmission() {
        // set the value to test
        Submission sub = new Submission();
        sub.setSubmissionId(1L);
        contestResult.setSubmission(sub);
        assertEquals("getSubmission and setSubmission failure occured", sub.getSubmissionId(), contestResult
                .getSubmission().getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#getCreateDate()} and {@link ContestResult#setCreateDate(Date)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        contestResult.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, contestResult.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#setCreateDate(Date)} and {@link ContestResult#getCreateDate()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        contestResult.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, contestResult.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#getFinalScore()} and {@link ContestResult#setFinalScore(Float)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFinalScore() {
        // set the value to test
        contestResult.setFinalScore(null);
        assertEquals("getFinalScore and setFinalScore failure occured", null, contestResult.getFinalScore());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#setFinalScore(Float)} and {@link ContestResult#getFinalScore()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setFinalScore() {
        // set the value to test
        contestResult.setFinalScore(10.0F);
        assertEquals("getFinalScore and setFinalScore failure occured", 10.0F, (float) contestResult
                .getFinalScore());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#getPlaced()} and {@link ContestResult#setPlaced(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPlaced() {
        // set the value to test
        contestResult.setPlaced(null);
        assertEquals("getPlaced and setPlaced failure occured", null, contestResult.getPlaced());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#setPlaced(Integer)} and {@link ContestResult#getPlaced()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPlaced() {
        // set the value to test
        contestResult.setPlaced(1);
        assertEquals("getPlaced and setPlaced failure occured", 1, (int) contestResult.getPlaced());
    }
    /**
     * <p>
     * Accuracy test for {@link ContestResult#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        ContestResult result = new ContestResult();
        Submission submission = new Submission();
        submission.setSubmissionId(10L);
        Contest contest = new Contest();
        contest.setContestId(20L);
        result.setSubmission(submission);
        result.setContest(contest);
        contestResult.setSubmission(submission);
        contestResult.setContest(contest);
        assertTrue("equals failed", contestResult.equals(result));
        assertTrue("hashCode failed", contestResult.hashCode() == result.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#equals(Object)}. Different composite ids.
     * </p>
     */
    public void test_equals_2() {
        ContestResult result = new ContestResult();
        Submission submission = new Submission();
        submission.setSubmissionId(10L);
        Contest contest = new Contest();
        contest.setContestId(20L);
        result.setContest(contest);
        contestResult.setSubmission(submission);
        contestResult.setContest(contest);
        assertFalse("equals failed", contestResult.equals(result));
        assertFalse("hashCode failed", contestResult.hashCode() == result.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#equals(Object)}. Different composite ids.
     * </p>
     */
    public void test_equals_3() {
        ContestResult result = new ContestResult();
        Submission submission = new Submission();
        submission.setSubmissionId(10L);
        Contest contest = new Contest();
        contest.setContestId(20L);
        result.setSubmission(submission);
        contestResult.setSubmission(submission);
        contestResult.setContest(contest);
        assertFalse("equals failed", contestResult.equals(result));
    }

    /**
     * <p>
     * Accuracy test for {@link ContestResult#equals(Object)}. Different composite ids.
     * </p>
     */
    public void test_equals_4() {
        Object result = new Object();
        Submission submission = new Submission();
        submission.setSubmissionId(10L);
        Contest contest = new Contest();
        contest.setContestId(20L);
        contestResult.setSubmission(submission);
        contestResult.setContest(contest);
        assertFalse("equals failed", contestResult.equals(result));
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
        Set<ContestResult> set = new HashSet<ContestResult>(num);
        // check how the hashcode and equals working for for set insertion.
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            ContestResult result = new ContestResult();
            Submission submission = new Submission();
            submission.setSubmissionId(new Long(i));
            Contest contest = new Contest();
            contest.setContestId(new Long(i));
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
            StudioFileType fileType = new StudioFileType();
            TestHelper.populateStudioFileType(fileType);
            HibernateUtil.getManager().persist(fileType);

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

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("path");

            HibernateUtil.getManager().persist(filePath);

            MimeType mimeType = new MimeType();
            mimeType.setDescription("description");
            mimeType.setStudioFileType(fileType);
            mimeType.setMimeTypeId(1L);
            HibernateUtil.getManager().persist(mimeType);

            SubmissionType submissionType = new SubmissionType();
            submissionType.setDescription("description");
            submissionType.setSubmissionTypeId(1L);
            HibernateUtil.getManager().persist(submissionType);

            SubmissionStatus submissionStatus = new SubmissionStatus();
            submissionStatus.setDescription("description");
            submissionStatus.setSubmissionStatusId(1L);
            HibernateUtil.getManager().persist(submissionStatus);

            Submission submission = new Submission();
            TestHelper.populateSubmission(submission, date, contest, filePath, mimeType, submissionType,
                    submissionStatus);
            HibernateUtil.getManager().persist(submission);

            ContestResult entity = new ContestResult();
            entity.setContest(contest);
            entity.setFinalScore(91.12F);
            entity.setPlaced(1);
            entity.setSubmission(submission);
            entity.setCreateDate(date);

            HibernateUtil.getManager().persist(entity);
            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from ContestResult as c");

            ContestResult persisted = (ContestResult) query.getResultList().get(0);
            assertEquals("Failed to persist - contest mismatch", entity.getContest(), persisted.getContest());
            assertEquals("Failed to persist - finalScore mismatch", entity.getFinalScore(), persisted
                    .getFinalScore());
            assertEquals("Failed to persist - placed mismatch", entity.getPlaced(), persisted.getPlaced());
            assertEquals("Failed to persist - submission mismatch", entity.getSubmission(), persisted
                    .getSubmission());
            assertEquals("Failed to persist - createDate mismatch", entity.getCreateDate(), persisted
                    .getCreateDate());

            // update the entity
            entity.setPlaced(2);
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestResult) query.getResultList().get(0);
            assertEquals("Failed to update - text mismatch", entity.getPlaced(), persisted.getPlaced());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(submission);
            HibernateUtil.getManager().remove(submissionStatus);
            HibernateUtil.getManager().remove(submissionType);
            HibernateUtil.getManager().remove(mimeType);
            HibernateUtil.getManager().remove(filePath);
            HibernateUtil.getManager().remove(contest);
            HibernateUtil.getManager().remove(prizeType);
            HibernateUtil.getManager().remove(status);
            HibernateUtil.getManager().remove(contestType);
            HibernateUtil.getManager().remove(channel);
            HibernateUtil.getManager().remove(fileType);
        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }

    }
}
