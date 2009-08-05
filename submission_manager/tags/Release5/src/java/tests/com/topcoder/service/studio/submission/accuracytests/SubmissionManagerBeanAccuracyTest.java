/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.accuracytests;

import com.topcoder.configuration.persistence.ConfigurationFileManager;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.service.studio.submission.BaseTestCase;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.PrizeType;
import com.topcoder.service.studio.submission.ReviewStatus;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManager;
import com.topcoder.service.studio.submission.SubmissionManagerBean;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.SubmissionReview;

import junit.framework.TestCase;

import java.io.File;

import java.sql.Connection;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * The accuracy test for the class {@link SubmissionManagerBean}.
 *
 * @author KLW
 * @version 1.0
 */
public class SubmissionManagerBeanAccuracyTest extends TestCase {
    /**
     * <p>
     * Represents the prefix part sql for insert submission.
     * </p>
     */
    private static final String INSERT_SUBMISSION = "INSERT INTO submission (submission_id," +
        " submission_status_id, submitter_id, contest_id, create_date, original_file_name, system_file_name," +
        " submission_type_id, mime_type_id, submission_date, height, width, modify_date," +
        " or_submission_id, path_id) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert prize.
     * </p>
     */
    private static final String INSERT_PRIZE = "INSERT INTO prize (prize_id, place, amount," +
        " prize_type_id, create_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission review.
     * </p>
     */
    private static final String INSERT_SUBMISSION_REVIEW = "INSERT INTO submission_review" +
        " (submission_id, reviewer_id, text, review_status_id, modify_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission prize relation.
     * </p>
     */
    private static final String INSERT_SUBMISSION_PRIZE = "INSERT INTO submission_prize_xref" +
        " (submission_id, prize_id, create_date) VALUES ";

    /**
     * <p>
     * Represents the prefix part sql for insert submission payment.
     * </p>
     */
    private static final String INSERT_SUBMISSION_PAYMENTS_PREFIX = "INSERT INTO submission_payments" +
        " (submission_id, payment_status_id, price) VALUES ";

    /**
     * The SubmissionManager instance for test.
     */
    private SubmissionManager manager;

    /**
     * sets up the test environment.
     *
     * @throws Excaption
     *             all exceptions throw to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        AccuracyTestHelper.initDatabase();

        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");

        InitialContext ctx = new InitialContext(env);

        manager = (SubmissionManager) ctx.lookup("remote/SubmissionManagerBean");
    }

    /**
     * tears down the test environment.
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        manager = null;
        AccuracyTestHelper.clearDatabase();
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmission(long)}. the submission is in the
     * database, but it is deleted, so it should return null.
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testGetSubmission_1() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 2, 1, 1, '2008-03-23 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-23 09:00:00', 1, 1, '2008-03-23 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        assertNull("The submission should not be found.", manager.getSubmission(1));
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmission(long)}. the submission is in the
     * database, but it is deleted, so it should return null.
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testGetSubmission_2() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = manager.getSubmission(1);

        assertNotNull("The submission should be found.", submission);
        assertEquals("The submission id is incorrect.", 1L, submission.getSubmissionId().longValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmissionsForContest(long, boolean)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testgetSubmissionsForContest_1() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION + " (2, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        List<Submission> list = manager.getSubmissionsForContest(1, true);
        assertNotNull("The list should not be null.", list);
        assertEquals("The list should have 2 elements.", 2, list.size());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmissionsForContest(long, boolean)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testgetSubmissionsForContest_2() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION + " (2, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        List<Submission> list = manager.getSubmissionsForContest(1, false);
        assertNotNull("The list should not null.", list);
        assertEquals("The list should have 2 elements.", 2, list.size());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getAllSubmissionsByMember(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testGetAllSubmissionsByMember() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION + " (2, 1, 2, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        List<Submission> list = manager.getAllSubmissionsByMember(1);

        assertNotNull("Should not null.", list);
        assertEquals("The submissions should have one elements.", 1, list.size());

        assertEquals("The submission id is incorrect.", 1L, list.get(0).getSubmissionId().longValue());
        assertEquals("The submitter id is incorrect.", 1L, list.get(0).getSubmitterId().longValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#updateSubmission(Submission)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testUpdateSubmission() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = manager.getSubmission(1L);
        submission.setPrizes(new HashSet<Prize>());

        submission.setOrSubmission(2L);

        submission.setModifyDate(new Date());
        submission.setOriginalFileName("accuracy_src_dev.zip");
        manager.updateSubmission(submission);
        submission = manager.getSubmission(1);
        assertEquals("The original file name has been updated.", "accuracy_src_dev.zip",
            submission.getOriginalFileName());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#removeSubmission(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testRemoveSubmission() throws Exception {
        assertFalse("The submission does not exists.", manager.removeSubmission(1L));

        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        assertTrue("The submission should be deleted.", manager.removeSubmission(1L));
        // we can not delete it again.
        assertFalse("The submission does not exists.", manager.removeSubmission(1L));
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#updateSubmissionStatus(long, long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testUpdateSubmissionStatus() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        manager.updateSubmissionStatus(1L, 2L);
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#updateSubmissionResult(Submission)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testUpdateSubmissionResult() throws Exception {
        String sql = "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
            " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
            " height, width, modify_date, or_submission_id, path_id, rank) VALUES  (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
            " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
            " height, width, modify_date, or_submission_id, path_id, rank) VALUES  (2, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = new Submission();
        submission.setSubmissionId(2L);
        submission.setRank(1);

        manager.updateSubmissionResult(submission);

        submission = manager.getSubmission(1L);
        assertEquals("The rank of this submission is incorrect.", 2, submission.getRank().intValue());

        submission = manager.getSubmission(2L);
        assertEquals("The rank of this submission is incorrect.", 1, submission.getRank().intValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#addPrize(Prize)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testAddPrize() throws Exception {
        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);
        type.setDescription("Accuracy Test");

        Prize prize = new Prize();
        prize.setAmount(10D);
        prize.setCreateDate(new Date());
        prize.setPlace(1);
        prize.setType(type);
        prize = manager.addPrize(prize);
        assertNotNull("The prize instance should not be null.", prize);
        assertNotNull("The id should be assigned.", prize.getPrizeId());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#updatePrize(Prize)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testUpdatePrize() throws Exception {
        String sql = INSERT_PRIZE + " (1, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        PrizeType type = new PrizeType();
        type.setPrizeTypeId(1L);
        type.setDescription("Accuracy Test");

        Prize prize = new Prize();
        prize.setAmount(10D);
        prize.setCreateDate(new Date());
        prize.setPlace(2);
        prize.setType(type);
        prize.setPrizeId(1L);
        manager.updatePrize(prize);
        prize = manager.getPrize(1L);
        assertNotNull("The prize should not be null.", prize);
        assertEquals("The prize id is incorrect.", 1L, prize.getPrizeId().longValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#removePrize(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testRemovePrize() throws Exception {
        String sql = INSERT_PRIZE + " (1, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);
        assertTrue("prize should be removed.", manager.removePrize(1L));
        assertNull("The prize should be removed.", manager.getPrize(1L));
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getPrize(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testGetPrize() throws Exception {
        String sql = INSERT_PRIZE + " (1, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        Prize prize = manager.getPrize(1L);
        assertNotNull("The prize should not be null.", prize);
        assertEquals("The place of this prize is incorrect.", 1, prize.getPlace().intValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#addPrizeToSubmission(long, long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testAddPrizeToSubmission() throws Exception {
        String sql = "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
        " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
        " height, width, modify_date, or_submission_id, path_id, rank) VALUES  (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
        " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_PRIZE + " (1, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        manager.addPrizeToSubmission(1L, 1L);

        Submission submisssion = manager.getSubmission(1L);
        Set<Prize> set = submisssion.getPrizes();
        assertNotNull("The set should not be null.", set);
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#removePrizeFromSubmission(long, long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testRemovePrizeFromSubmission() throws Exception {
        String sql = "INSERT INTO submission (submission_id, submission_status_id, submitter_id, contest_id, create_date," +
        " original_file_name, system_file_name, submission_type_id, mime_type_id, submission_date," +
        " height, width, modify_date, or_submission_id, path_id, rank) VALUES  (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
        " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_PRIZE + " (1, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        manager.addPrizeToSubmission(1L, 1L);

        Submission submisssion = manager.getSubmission(1L);
        Set<Prize> set = submisssion.getPrizes();
        assertNotNull("The set should not be null.", set);
        // remove the prize.
        assertTrue("The prize should be removed.", manager.removePrizeFromSubmission(1L, 1L));
    }

    /**
     * The accuracy test for the method
     * {@link SubmissionManagerBean#addSubmissionPayment(com.topcoder.service.studio.submission.SubmissionPayment)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testAddSubmissionPayment() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(1.0);
        // add the payment
        manager.addSubmissionPayment(submissionPayment);
        submissionPayment = manager.getSubmissionPayment(1L);
        assertNotNull("The payment should not be null.", submissionPayment);
        assertEquals("The price of the payment is incorrect.", 1.0D, submissionPayment.getPrice().doubleValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#updateSubmissionPayment(SubmissionPayment)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testUpdateSubmissionPayment() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = "INSERT INTO submission_payments (submission_id, payment_status_id, price) VALUES "
            + "(1, 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(2.0);
        manager.updateSubmissionPayment(submissionPayment);
        submissionPayment = manager.getSubmissionPayment(1L);
        assertNotNull("The payment should not be null.", submissionPayment);
        assertEquals("The price of the payment is incorrect.", 2.0D, submissionPayment.getPrice().doubleValue());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmissionPayment(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testGetSubmissionPayment() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setPaymentStatusId(1L);

        SubmissionPayment submissionPayment = new SubmissionPayment();
        submissionPayment.setSubmission(submission);
        submissionPayment.setStatus(paymentStatus);
        submissionPayment.setPrice(1.0);
        // add the payment
        manager.addSubmissionPayment(submissionPayment);
        submissionPayment = manager.getSubmissionPayment(1L);
        assertNotNull("The payment should not be null.", submissionPayment);
        assertEquals("The price of the payment is incorrect.", 1.0D, submissionPayment.getPrice().doubleValue());
    }

    /**
     * The accuracy test for the method
     * {@link SubmissionManagerBean#addSubmissionReview(com.topcoder.service.studio.submission.SubmissionReview)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testAddSubmissionReview() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        Submission submission =  new Submission();
        submission.setSubmissionId(1L);

        ReviewStatus reviewStatus = new ReviewStatus();
        reviewStatus.setReviewStatusId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);
        submissionReview.setModifyDate(new Date());
        submissionReview.setReviewerId(1L);
        submissionReview.setText("Reviewing");
        submissionReview.setStatus(reviewStatus);

        manager.addSubmissionReview(submissionReview);

        submissionReview = manager.getSubmissionReview(1L);
        assertEquals("The reviewer id is incorrect.", 1L, submissionReview.getReviewerId().longValue());
        assertEquals("The text of the submission review is incorrect.", "Reviewing", submissionReview.getText());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#updateSubmissionReview(SubmissionReview)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testUpdateSubmissionReview() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION_REVIEW + " (1, 1, 'Hello', 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        ReviewStatus reviewStatus = new ReviewStatus();
        reviewStatus.setReviewStatusId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);
        submissionReview.setModifyDate(new Date());
        submissionReview.setReviewerId(1L);
        submissionReview.setText("Reviewing");
        submissionReview.setStatus(reviewStatus);
        manager.updateSubmissionReview(submissionReview);
        // retrieve it back
        submissionReview = manager.getSubmissionReview(1L);
        assertEquals("The reviewer id is incorrect.", 1L, submissionReview.getReviewerId().longValue());
        assertEquals("The text of the submission review is incorrect.", "Reviewing", submissionReview.getText());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmissionReview(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testgetSubmissionReview() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION_REVIEW + " (1, 1, 'Hello', 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        ReviewStatus reviewStatus = new ReviewStatus();
        reviewStatus.setReviewStatusId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);
        submissionReview.setModifyDate(new Date());
        submissionReview.setReviewerId(1L);
        submissionReview.setText("Reviewing");
        submissionReview.setStatus(reviewStatus);
        manager.updateSubmissionReview(submissionReview);
        // retrieve it back
        submissionReview = manager.getSubmissionReview(1L);
        assertEquals("The reviewer id is incorrect.", 1L, submissionReview.getReviewerId().longValue());
        assertEquals("The text of the submission review is incorrect.", "Reviewing", submissionReview.getText());
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#removeSubmissionReview(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testRemoveSubmissionReview() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION_REVIEW + " (1, 1, 'Hello', 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        Submission submission = new Submission();
        submission.setSubmissionId(1L);

        ReviewStatus reviewStatus = new ReviewStatus();
        reviewStatus.setReviewStatusId(1L);

        SubmissionReview submissionReview = new SubmissionReview();
        submissionReview.setSubmission(submission);
        submissionReview.setModifyDate(new Date());
        submissionReview.setReviewerId(1L);
        submissionReview.setText("Reviewing");
        submissionReview.setStatus(reviewStatus);
        manager.updateSubmissionReview(submissionReview);
        // retrieve it back
        submissionReview = manager.getSubmissionReview(1L);
        assertEquals("The reviewer id is incorrect.", 1L, submissionReview.getReviewerId().longValue());
        assertEquals("The text of the submission review is incorrect.", "Reviewing", submissionReview.getText());
        assertTrue("The submission review should be removed.", manager.removeSubmissionReview(1L));
        submissionReview = manager.getSubmissionReview(1L);
        assertNull("The submission review should be removed.", submissionReview);
    }

    /**
     * The accuracy test for the method {@link SubmissionManagerBean#getSubmissionPrizes(long)}
     *
     * @throws Exception
     *             all exception throw to JUnit.
     */
    public void testGetSubmissionPrizes() throws Exception {
        String sql = INSERT_SUBMISSION + " (1, 1, 1, 1, '2008-03-26 09:00:00', 'src_dev.zip', 'src_dev.zip', 1, 1," +
            " '2008-03-26 09:00:00', 1, 1, '2008-03-26 09:00:00', 1, 1)";
        AccuracyTestHelper.executeSql(sql);

        sql = INSERT_PRIZE + " (1, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_PRIZE + " (2, 1, 1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION_PRIZE + " (1, 1, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);
        sql = INSERT_SUBMISSION_PRIZE + " (1, 2, '2008-03-26 09:00:00')";
        AccuracyTestHelper.executeSql(sql);

        List<Prize> list = manager.getSubmissionPrizes(1L);
        assertNotNull("The list should not be null..", list);
        assertEquals("The list should contain 2 prizes.", 2, list.size());
    }
}
