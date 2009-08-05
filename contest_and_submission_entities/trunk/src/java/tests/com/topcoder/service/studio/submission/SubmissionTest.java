/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Tests the functionality of {@link Submission} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionTest extends TestCase {

    /**
     * Represents the <code>Submission</code> instance to test.
     */
    private Submission submission = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        submission = new Submission();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        submission = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#Submission()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Submission() {
        // check for null
        assertNotNull("Submission creation failed", submission);
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getSubmissionId()} and {@link Submission#setSubmissionId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getSubmissionId() {
        // set the value to test
        submission.setSubmissionId(new Long(1));
        assertEquals("getSubmissionId and setSubmissionId failure occured", new Long(1), submission
                .getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setSubmissionId(Long)} and {@link Submission#getSubmissionId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmissionId() {
        // set the value to test
        submission.setSubmissionId(1L);
        assertEquals("getSubmissionId and setSubmissionId failure occured", 1L, (long) submission
                .getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getSubmitterId()} and {@link Submission#setSubmitterId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getSubmitterId() {
        // set the value to test
        submission.setSubmitterId(new Long(1));
        assertEquals("getSubmitterId and setSubmitterId failure occured", new Long(1), submission.getSubmitterId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setSubmitterId(Long)} and {@link Submission#getSubmitterId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmitterId() {
        // set the value to test
        submission.setSubmitterId(1L);
        assertEquals("getSubmitterId and setSubmitterId failure occured", 1L, (long) submission.getSubmitterId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getContest()} and {@link Submission#setContest(Contest)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContest() {
        // set the value to test
        submission.setContest(null);
        assertEquals("getContest and setContest failure occured", null, submission.getContest());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setContest(Contest)} and {@link Submission#getContest()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContest() {
        // set the value to test
        Contest contest = new Contest();
        contest.setContestId(1L);
        submission.setContest(contest);
        assertEquals("getContest and setContest failure occured", 1L, (long) submission.getContest()
                .getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getOriginalFileName()} and
     * {@link Submission#setOriginalFileName(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getOriginalFileName() {
        // set the value to test
        submission.setOriginalFileName(null);
        assertEquals("getOriginalFileName and setOriginalFileName failure occured", null, submission
                .getOriginalFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setOriginalFileName(String)} and
     * {@link Submission#getOriginalFileName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setOriginalFileName() {
        // set the value to test
        submission.setOriginalFileName("test");
        assertEquals("getOriginalFileName and setOriginalFileName failure occured", "test", submission
                .getOriginalFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getSystemFileName()} and {@link Submission#setSystemFileName(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSystemFileName() {
        // set the value to test
        submission.setSystemFileName(null);
        assertEquals("getSystemFileName and setSystemFileName failure occured", null, submission
                .getSystemFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setSystemFileName(String)} and {@link Submission#getSystemFileName()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setSystemFileName() {
        // set the value to test
        submission.setSystemFileName("test");
        assertEquals("getSystemFileName and setSystemFileName failure occured", "test", submission
                .getSystemFileName());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getFullSubmissionPath()} and
     * {@link Submission#setFullSubmissionPath(FilePath)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFullSubmissionPath() {
        // set the value to test
        submission.setFullSubmissionPath(null);
        assertEquals("getFullSubmissionPath and setFullSubmissionPath failure occured", null, submission
                .getFullSubmissionPath());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setFullSubmissionPath(FilePath)} and
     * {@link Submission#getFullSubmissionPath()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setFullSubmissionPath() {
        // set the value to test
        FilePath path = new FilePath();
        path.setFilePathId(1L);
        submission.setFullSubmissionPath(path);
        assertEquals("getFullSubmissionPath and setFullSubmissionPath failure occured", 1L, (long) submission
                .getFullSubmissionPath().getFilePathId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getType()} and {@link Submission#setType(SubmissionType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getType() {
        // set the value to test
        submission.setType(null);
        assertEquals("getType and setType failure occured", null, submission.getType());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setType(SubmissionType)} and {@link Submission#getType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setType() {
        // set the value to test
        SubmissionType type = new SubmissionType();
        type.setSubmissionTypeId(1L);
        submission.setType(type);
        assertEquals("getType and setType failure occured", type.getSubmissionTypeId(), submission.getType()
                .getSubmissionTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getMimeType()} and {@link Submission#setMimeType(MimeType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getMimeType() {
        // set the value to test
        submission.setMimeType(null);
        assertEquals("getMimeType and setMimeType failure occured", null, submission.getMimeType());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setMimeType(MimeType)} and {@link Submission#getMimeType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setMimeType() {
        // set the value to test
        MimeType type = new MimeType();
        type.setMimeTypeId(1L);
        submission.setMimeType(type);
        assertEquals("getMimeType and setMimeType failure occured", 1L, (long) submission.getMimeType()
                .getMimeTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getReview()} and {@link Submission#setReview(List)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getReview() {
        // set the value to test
        submission.setReview(null);
        assertEquals("getReview and setReview failure occured", null, submission.getReview());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setReview(List)} and {@link Submission#getReview()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setReview() {
        // set the value to test
        List<SubmissionReview> review = new ArrayList<SubmissionReview>();
        review.add(new SubmissionReview());
        submission.setReview(review);
        assertEquals("getReview and setReview failure occured", review.size(), submission.getReview().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getResult()} and {@link Submission#setResult(ContestResult)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getResult() {
        // set the value to test
        submission.setResult(null);
        assertEquals("getResult and setResult failure occured", null, submission.getResult());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setResult(ContestResult)} and {@link Submission#getResult()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setResult() {
        // set the value to test
        ContestResult result = new ContestResult();
        result.setFinalScore(91.0F);
        submission.setResult(result);
        assertEquals("getResult and setResult failure occured", 91.0F, (float) submission.getResult()
                .getFinalScore());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getRank()} and {@link Submission#setRank(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getRank() {
        // set the value to test
        submission.setRank(null);
        assertEquals("getRank and setRank failure occured", null, submission.getRank());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setRank(Integer)} and {@link Submission#getRank()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setRank() {
        // set the value to test
        submission.setRank(1);
        assertEquals("getRank and setRank failure occured", 1, (int) submission.getRank());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getCreateDate()} and {@link Submission#setCreateDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        submission.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, submission.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setCreateDate(Date)} and {@link Submission#getCreateDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        submission.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, submission.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getModifyDate()} and {@link Submission#setModifyDate(Date)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getModifyDate() {
        // set the value to test
        submission.setModifyDate(null);
        assertEquals("getModifyDate and setModifyDate failure occured", null, submission.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setModifyDate(Date)} and {@link Submission#getModifyDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setModifyDate() {
        // set the value to test
        Date date = new Date();
        submission.setModifyDate(date);
        assertEquals("getModifyDate and setModifyDate failure occured", date, submission.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getHeight()} and {@link Submission#setHeight(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getHeight() {
        // set the value to test
        submission.setHeight(null);
        assertEquals("getHeight and setHeight failure occured", null, submission.getHeight());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setHeight(Integer)} and {@link Submission#getHeight()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setHeight() {
        // set the value to test
        submission.setHeight(1);
        assertEquals("getHeight and setHeight failure occured", 1, (int) submission.getHeight());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getWidth()} and {@link Submission#setWidth(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getWidth() {
        // set the value to test
        submission.setWidth(null);
        assertEquals("getWidth and setWidth failure occured", null, submission.getWidth());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setWidth(Integer)} and {@link Submission#getWidth()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setWidth() {
        // set the value to test
        submission.setWidth(1);
        assertEquals("getWidth and setWidth failure occured", 1, (int) submission.getWidth());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getStatus()} and {@link Submission#setStatus(SubmissionStatus)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStatus() {
        // set the value to test
        submission.setStatus(null);
        assertEquals("getStatus and setStatus failure occured", null, submission.getStatus());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setStatus(SubmissionStatus)} and {@link Submission#getStatus()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setStatus() {
        // set the value to test
        SubmissionStatus status = new SubmissionStatus();
        status.setSubmissionStatusId(1L);
        submission.setStatus(status);
        assertEquals("getStatus and setStatus failure occured", 1L, (long) submission.getStatus()
                .getSubmissionStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getPrizes()} and {@link Submission#setPrizes(Set)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPrizes() {
        // set the value to test
        submission.setPrizes(null);
        assertEquals("getPrizes and setPrizes failure occured", null, submission.getPrizes());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setPrizes(Set)} and {@link Submission#getPrizes()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPrizes() {
        // set the value to test
        Set<Prize> prizes = new HashSet<Prize>();
        prizes.add(new Prize());
        submission.setPrizes(prizes);
        assertEquals("getPrizes and setPrizes failure occured", prizes.size(), submission.getPrizes().size());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getOrSubmission()} and {@link Submission#setOrSubmission(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getORSubmission() {
        // set the value to test
        submission.setOrSubmission(new Long(1));
        assertEquals("getOrSubmission and setOrSubmission failure occured", new Long(1), submission
                .getOrSubmission());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setOrSubmission(Long)} and {@link Submission#getOrSubmission()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setORSubmission() {
        // set the value to test
        submission.setOrSubmission(1L);
        assertEquals("getOrSubmission and setOrSubmission failure occured", 1L, (long) submission
                .getOrSubmission());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getSubmissionDate()} and {@link Submission#setSubmissionDate(Date)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSubmissionDate() {
        // set the value to test
        submission.setSubmissionDate(null);
        assertEquals("getSubmissionDate and setSubmissionDate failure occured", null, submission
                .getSubmissionDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setSubmissionDate(Date)} and {@link Submission#getSubmissionDate()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmissionDate() {
        // set the value to test
        Date date = new Date();
        submission.setSubmissionDate(date);
        assertEquals("getSubmissionDate and setSubmissionDate failure occured", date, submission
                .getSubmissionDate());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getPaymentId()} and {@link Submission#setPaymentId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getPaymentId() {
        // set the value to test
        submission.setPaymentId(new Long(1));
        assertEquals("getPaymentId and setPaymentId failure occured", new Long(1), submission.getPaymentId());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setPaymentId(Long)} and {@link Submission#getPaymentId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPaymentId() {
        // set the value to test
        submission.setPaymentId(1L);
        assertEquals("getPaymentId and setPaymentId failure occured", 1L, (long) submission.getPaymentId());
    }


    /**
     * <p>
     * Accuracy test for {@link Submission#getFeedbackText()} and {@link Submission#setFeedbackText(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFeedbackText() {
        // set the value to test
        submission.setFeedbackText(null);
        assertEquals("getFeedbackText and setFeedbackText failure occured", null, submission
                .getFeedbackText());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setFeedbackText(String)} and {@link Submission#getFeedbackText()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setFeedbackText() {
        // set the value to test
        submission.setFeedbackText("test");
        assertEquals("getFeedbackText and setFeedbackText failure occured", "test", submission
                .getFeedbackText());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getFeedbackThumb()} and {@link Submission#setFeedbackThumb(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFeedbackThumb() {
        // set the value to test
        submission.setFeedbackThumb(null);
        assertEquals("getFeedbackThumb and setFeedbackThumb failure occured", null, submission.getFeedbackThumb());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setFeedbackThumb(Integer)} and {@link Submission#getFeedbackThumb()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setFeedbackThumb() {
        // set the value to test
        submission.setFeedbackThumb(1);
        assertEquals("getFeedbackThumb and setFeedbackThumb failure occured", 1, (int) submission.getFeedbackThumb());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getUserRank()} and {@link Submission#setUserRank(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getUserRank() {
        // set the value to test
        submission.setUserRank(null);
        assertEquals("getUserRank and setUserRank failure occured", null, submission.getUserRank());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setUserRank(Integer)} and {@link Submission#getUserRank()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setUserRank() {
        // set the value to test
        submission.setUserRank(1);
        assertEquals("getUserRank and setUserRank failure occured", 1, (int) submission.getUserRank());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#getArtifactCount()} and {@link Submission#setArtifactCount(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getArtifactCount() {
        // set the value to test
        submission.setArtifactCount(null);
        assertEquals("getArtifactCount and setArtifactCount failure occured", null, submission.getArtifactCount());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#setArtifactCount(Integer)} and {@link Submission#getArtifactCount()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setArtifactCount() {
        // set the value to test
        submission.setArtifactCount(1);
        assertEquals("getArtifactCount and setArtifactCount failure occured", 1, (int) submission.getArtifactCount());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        Submission submission1 = new Submission();
        submission1.setSubmissionId(1L);
        submission.setSubmissionId(1L);
        assertTrue("failed equals", submission1.equals(submission));
        assertTrue("failed hashCode", submission1.hashCode() == submission.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        Submission submission1 = new Submission();
        submission1.setSubmissionId(2L);
        submission.setSubmissionId(1L);
        assertFalse("failed equals", submission1.equals(submission));
        assertFalse("failed hashCode", submission1.hashCode() == submission.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link Submission#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object submission1 = new Object();
        submission.setSubmissionId(1L);
        assertFalse("failed equals", submission.equals(submission1));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link Submission}</code>.
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

            Submission entity = new Submission();
            TestHelper.populateSubmission(entity, date, contest, filePath, mimeType, submissionType,
                    submissionStatus);
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Submission persisted = (Submission) HibernateUtil.getManager()
                .find(Submission.class, entity.getSubmissionId());
            assertEquals("Failed to persist - contest mismatch", entity.getContest(), persisted.getContest());
            assertEquals("Failed to persist - createDate mismatch", entity.getCreateDate(), persisted
                    .getCreateDate());
            assertEquals("Failed to persist - fullSubmissionPath mismatch", entity.getFullSubmissionPath(),
                    persisted.getFullSubmissionPath());
            assertEquals("Failed to persist - height mismatch", entity.getHeight(), persisted.getHeight());
            assertEquals("Failed to persist - mimeType mismatch", entity.getMimeType(), persisted.getMimeType());
            assertEquals("Failed to persist - modifyDate mismatch", entity.getModifyDate(), persisted
                    .getModifyDate());
            assertEquals("Failed to persist - originalFileName mismatch", entity.getOriginalFileName(), persisted
                    .getOriginalFileName());
            assertEquals("Failed to persist - orSubmission mismatch", entity.getOrSubmission(), persisted
                    .getOrSubmission());
            assertEquals("Failed to persist - rank mismatch", entity.getRank(), persisted.getRank());
            assertEquals("Failed to persist - status mismatch", entity.getStatus(), persisted.getStatus());
            assertEquals("Failed to persist - submissionDate mismatch", entity.getSubmissionDate(), persisted
                    .getSubmissionDate());
            assertEquals("Failed to persist - submitterId mismatch", entity.getSubmitterId(), persisted
                    .getSubmitterId());
            assertEquals("Failed to persist - systemFileName mismatch", entity.getSystemFileName(), persisted
                    .getSystemFileName());
            assertEquals("Failed to persist - type mismatch", entity.getType(), persisted.getType());
            assertEquals("Failed to persist - width mismatch", entity.getWidth(), persisted.getWidth());

            // update the entity
            entity.setOriginalFileName("new originalFileName");

            HibernateUtil.getManager().merge(entity);

            persisted = (Submission) HibernateUtil.getManager().find(Submission.class, entity.getSubmissionId());
            assertEquals("Failed to update - original file name mismatch", entity.getOriginalFileName(), persisted
                    .getOriginalFileName());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
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
