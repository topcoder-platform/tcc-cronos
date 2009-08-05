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
 * Tests the functionality of {@link SubmissionReview} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionReviewTest extends TestCase {

    /**
     * Represents the <code>SubmissionReview</code> instance to test.
     */
    private SubmissionReview submissionReview = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        submissionReview = new SubmissionReview();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        submissionReview = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionReviewTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#SubmissionReview()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_SubmissionReview() {
        // check for null
        assertNotNull("SubmissionReview creation failed", submissionReview);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#getReviewerId()} and {@link SubmissionReview#setReviewerId(Long)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getReviewerId() {
        // set the value to test
        submissionReview.setReviewerId(new Long(1));
        assertEquals("getReviewerId and setReviewerId failure occured", new Long(1), submissionReview
                .getReviewerId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#setReviewerId(Long)} and {@link SubmissionReview#getReviewerId()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setReviewerId() {
        // set the value to test
        submissionReview.setReviewerId(1L);
        assertEquals("getReviewerId and setReviewerId failure occured", 1L, (long) submissionReview
                .getReviewerId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#getText()} and {@link SubmissionReview#setText(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getText() {
        // set the value to test
        submissionReview.setText(null);
        assertEquals("getText and setText failure occured", null, submissionReview.getText());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#setText(String)} and {@link SubmissionReview#getText()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setText() {
        // set the value to test
        submissionReview.setText("test");
        assertEquals("getText and setText failure occured", "test", submissionReview.getText());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#getStatus()} and {@link SubmissionReview#setStatus(ReviewStatus)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStatus() {
        // set the value to test
        submissionReview.setStatus(null);
        assertEquals("getStatus and setStatus failure occured", null, submissionReview.getStatus());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#setStatus(ReviewStatus)} and {@link SubmissionReview#getStatus()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setStatus() {
        // set the value to test
        ReviewStatus status = new ReviewStatus();
        status.setReviewStatusId(1L);
        submissionReview.setStatus(status);
        assertEquals("getStatus and setStatus failure occured", 1L, (long) submissionReview.getStatus()
                .getReviewStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#getSubmission()} and
     * {@link SubmissionReview#setSubmission(Submission)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSubmission() {
        // set the value to test
        submissionReview.setSubmission(null);
        assertEquals("getSubmission and setSubmission failure occured", null, submissionReview.getSubmission());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#setSubmission(Submission)} and
     * {@link SubmissionReview#getSubmission()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmission() {
        // set the value to test
        Submission sub = new Submission();
        sub.setSubmissionId(1L);
        submissionReview.setSubmission(sub);
        assertEquals("getSubmission and setSubmission failure occured", 1L, (long) submissionReview
                .getSubmission().getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#getModifyDate()} and {@link SubmissionReview#setModifyDate(Date)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getModifyDate() {
        // set the value to test
        submissionReview.setModifyDate(null);
        assertEquals("getModifyDate and setModifyDate failure occured", null, submissionReview.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#setModifyDate(Date)} and {@link SubmissionReview#getModifyDate()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setModifyDate() {
        // set the value to test
        Date date = new Date();
        submissionReview.setModifyDate(date);
        assertEquals("getModifyDate and setModifyDate failure occured", date, submissionReview.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        SubmissionReview review = new SubmissionReview();
        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        review.setReviewerId(1L);
        review.setSubmission(submission);
        submissionReview.setReviewerId(1L);
        submissionReview.setSubmission(submission);
        assertTrue("failed equals", submissionReview.equals(review));
        assertTrue("failed hashCode", submissionReview.hashCode() == review.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        SubmissionReview review = new SubmissionReview();
        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        review.setReviewerId(1L);
        submissionReview.setReviewerId(1L);
        submissionReview.setSubmission(submission);
        assertFalse("failed equals", submissionReview.equals(review));
        assertFalse("failed hashCode", submissionReview.hashCode() == review.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionReview#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object review = new Object();
        Submission submission = new Submission();
        submission.setSubmissionId(1L);
        submissionReview.setReviewerId(1L);
        submissionReview.setSubmission(submission);
        assertFalse("failed equals", submissionReview.equals(review));
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
        Set<SubmissionReview> set = new HashSet<SubmissionReview>(num);
        // check how the hashcode and equals working for for set insertion.
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            SubmissionReview review = new SubmissionReview();
            Submission submission = new Submission();
            submission.setSubmissionId(new Long(i));
            review.setReviewerId(new Long(i));
            review.setSubmission(submission);
            set.add(review);
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

            ReviewStatus reviewStatus = new ReviewStatus();
            reviewStatus.setDescription("description");
            reviewStatus.setReviewStatusId(1L);
            HibernateUtil.getManager().persist(reviewStatus);

            SubmissionReview entity = new SubmissionReview();
            entity.setReviewerId(101L);
            entity.setModifyDate(date);
            entity.setStatus(reviewStatus);
            entity.setSubmission(submission);
            entity.setText("text");

            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from SubmissionReview as s");

            SubmissionReview persisted = (SubmissionReview) query.getResultList().get(0);
            assertEquals("Failed to persist - text mismatch", entity.getText(), persisted.getText());
            assertEquals("Failed to persist - reviewerId mismatch", entity.getReviewerId(), persisted
                    .getReviewerId());
            assertEquals("Failed to persist - modifyDate mismatch", entity.getModifyDate(), persisted
                    .getModifyDate());
            assertEquals("Failed to persist - status mismatch", entity.getStatus(), persisted.getStatus());
            assertEquals("Failed to persist - submission mismatch", entity.getSubmission(), persisted
                    .getSubmission());

            // update the entity
            entity.setText("new text");
            HibernateUtil.getManager().merge(entity);

            persisted = (SubmissionReview) query.getResultList().get(0);
            assertEquals("Failed to persist - text mismatch", entity.getText(), persisted.getText());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(reviewStatus);
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
