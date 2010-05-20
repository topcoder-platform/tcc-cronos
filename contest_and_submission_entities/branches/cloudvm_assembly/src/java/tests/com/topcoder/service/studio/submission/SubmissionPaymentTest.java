/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission;

import java.util.Date;

import javax.persistence.Query;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.studio.PaymentType;
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
 * Tests the functionality of {@link SubmissionPayment} class.
 * </p>
 *
 * @author cyberjag, TCSDEVELOPER
 * @version 1.2
 */
public class SubmissionPaymentTest extends TestCase {

    /**
     * Represents the <code>SubmissionPayment</code> instance to test.
     */
    private SubmissionPayment submissionPayment = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        submissionPayment = new SubmissionPayment();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        submissionPayment = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionPaymentTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#SubmissionPayment()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_SubmissionPayment() {
        // check for null
        assertNotNull("SubmissionPayment creation failed", submissionPayment);
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getSubmission()} and
     * {@link SubmissionPayment#setSubmission(Submission)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSubmission() {
        // set the value to test
        submissionPayment.setSubmission(null);
        assertEquals("getSubmission and setSubmission failure occured", null, submissionPayment.getSubmission());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setSubmission(Submission)} and
     * {@link SubmissionPayment#getSubmission()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setSubmission() {
        // set the value to test
        Submission sub = new Submission();
        sub.setSubmissionId(1L);
        submissionPayment.setSubmission(sub);
        assertEquals("getSubmission and setSubmission failure occured", 1L, (long) submissionPayment.getSubmission()
                .getSubmissionId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getStatus()} and {@link SubmissionPayment#setStatus(PaymentStatus)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStatus() {
        // set the value to test
        submissionPayment.setStatus(null);
        assertEquals("getStatus and setStatus failure occured", null, submissionPayment.getStatus());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setStatus(PaymentStatus)} and {@link SubmissionPayment#getStatus()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setStatus() {
        // set the value to test
        PaymentStatus status = new PaymentStatus();
        status.setPaymentStatusId(1L);
        submissionPayment.setStatus(status);
        assertEquals("getStatus and setStatus failure occured", 1L, (long) submissionPayment.getStatus()
                .getPaymentStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setPaymentType(PaymentType)} and
     * {@link SubmissionPayment#getPaymentType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPaymentType() {
        // set the value to test
        PaymentType type = new PaymentType();
        type.setPaymentTypeId(1L);
        submissionPayment.setPaymentType(type);
        assertEquals("getPaymentType and setPaymentType failure occured", 1L, (long) submissionPayment.getPaymentType()
                .getPaymentTypeId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getPaymentType()} and
     * {@link SubmissionPayment#setPaymentType(PaymentType)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPaymentType() {
        // set the value to test
        submissionPayment.setPaymentType(null);
        assertEquals("getPaymentType and setPaymentType failure occured", null, submissionPayment.getPaymentType());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getPrice()} and {@link SubmissionPayment#setPrice(Double)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPrice() {
        // set the value to test
        submissionPayment.setPrice(null);
        assertEquals("getPrice and setPrice failure occured", null, submissionPayment.getPrice());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setPrice(Double)} and {@link SubmissionPayment#getPrice()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setPrice() {
        // set the value to test
        submissionPayment.setPrice(10.0);
        assertEquals("getPrice and setPrice failure occured", 10.0, (double) submissionPayment.getPrice());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getPayPalOrderId()} and
     * {@link SubmissionPayment#setPayPalOrderId(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPayPalOrderId() {
        // set the value to test
        submissionPayment.setPayPalOrderId(null);
        assertEquals("getPayPalOrderId and setPayPalOrderId failure occured", null, submissionPayment
                .getPayPalOrderId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setPayPalOrderId(String)} and
     * {@link SubmissionPayment#getPayPalOrderId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPayPalOrderId() {
        // set the value to test
        submissionPayment.setPayPalOrderId("test");
        assertEquals("getPayPalOrderId and setPayPalOrderId failure occured", "test", submissionPayment
                .getPayPalOrderId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getPaymentReferenceId()} and
     * {@link SubmissionPayment#setPaymentReferenceId(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPaymentReferenceId() {
        // set the value to test
        submissionPayment.setPaymentReferenceId(null);
        assertEquals("getPaymentReferenceId and setPaymentReferenceId failure occured", null, submissionPayment
                .getPaymentReferenceId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setPaymentReferenceId(String)} and
     * {@link SubmissionPayment#getPaymentReferenceId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPaymentReferenceId() {
        // set the value to test
        submissionPayment.setPaymentReferenceId("test");
        assertEquals("getPaymentReferenceId and setPaymentReferenceId failure occured", "test", submissionPayment
                .getPaymentReferenceId());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#getCreateDate()} and {@link SubmissionPayment#setCreateDate(Date)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateDate() {
        // set the value to test
        submissionPayment.setCreateDate(null);
        assertEquals("getCreateDate and setCreateDate failure occured", null, submissionPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#setCreateDate(Date)} and {@link SubmissionPayment#getCreateDate()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setCreateDate() {
        // set the value to test
        Date date = new Date();
        submissionPayment.setCreateDate(date);
        assertEquals("getCreateDate and setCreateDate failure occured", date, submissionPayment.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#equals(Object)}. Both objects are equal.
     * </p>
     */
    public void test_equals_1() {
        SubmissionPayment payment = new SubmissionPayment();
        Submission sub = new Submission();
        sub.setSubmissionId(1L);
        payment.setSubmission(sub);
        submissionPayment.setSubmission(sub);
        assertTrue("failed equals", submissionPayment.equals(payment));
        assertTrue("failed hashCode", submissionPayment.hashCode() == payment.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_2() {
        SubmissionPayment payment = new SubmissionPayment();
        Submission sub = new Submission();
        sub.setSubmissionId(1L);
        Submission sub1 = new Submission();
        sub1.setSubmissionId(2L);
        payment.setSubmission(sub);
        submissionPayment.setSubmission(sub1);
        assertFalse("failed equals", submissionPayment.equals(payment));
        assertFalse("failed hashCode", submissionPayment.hashCode() == payment.hashCode());
    }

    /**
     * <p>
     * Accuracy test for {@link SubmissionPayment#equals(Object)}. Objects are unequal.
     * </p>
     */
    public void test_equals_3() {
        Object payment = new Object();
        Submission sub = new Submission();
        sub.setSubmissionId(1L);
        submissionPayment.setSubmission(sub);
        assertFalse("failed equals", submissionPayment.equals(payment));
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link SubmissionPayment}</code>.
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

            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setDescription("description");
            paymentStatus.setPaymentStatusId(1L);
            HibernateUtil.getManager().persist(paymentStatus);

            Submission submission = new Submission();
            TestHelper.populateSubmission(submission, date, contest, filePath, mimeType, submissionType,
                    submissionStatus);
            HibernateUtil.getManager().persist(submission);

            SubmissionPayment entity = new SubmissionPayment();
            entity.setSubmission(submission);
            entity.setStatus(paymentStatus);
            entity.setPrice(500.0);
            entity.setCreateDate(date);

            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from SubmissionPayment as s");

            SubmissionPayment persisted = (SubmissionPayment) query.getResultList().get(0);

            assertEquals("Failed to persist - price mismatch", entity.getPrice(), persisted.getPrice());
            assertEquals("Failed to persist - submission mismatch", entity.getSubmission(), persisted.getSubmission());
            assertEquals("Failed to persist - status mismatch", entity.getStatus(), persisted.getStatus());

            // update the entity
            entity.setPrice(1200.0);
            HibernateUtil.getManager().merge(entity);

            persisted = (SubmissionPayment) query.getResultList().get(0);
            assertEquals("Failed to update - price mismatch", entity.getPrice(), persisted.getPrice());

            // delete the entity
            HibernateUtil.getManager().remove(entity);
            HibernateUtil.getManager().remove(submission);
            HibernateUtil.getManager().remove(submissionStatus);
            HibernateUtil.getManager().remove(paymentStatus);
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
