/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.accuracytests.submission;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Query;
import org.hibernate.Session;

import com.topcoder.service.studio.accuracytests.HibernateUtil;
import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.PaymentStatus;
import com.topcoder.service.studio.submission.ReviewStatus;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionReview;
import com.topcoder.service.studio.submission.SubmissionStatus;
import com.topcoder.service.studio.submission.SubmissionType;


/**
 * <p>
 * Accuracy test for <code>SubmissionReview</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SubmissionReviewUnitTest extends TestCase {
    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SubmissionReviewUnitTest.class);
    }

    /**
     * <p>
     * Test the mapping of this entity.
     * </p>
     */
    public void testSubmissionReviewMapping() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            StudioFileType fileType = new StudioFileType();
            fileType.setSort(new Integer(1));
            fileType.setImageFile(true);
            fileType.setDescription("desc");
            fileType.setExtension(".jar");
            session.save(fileType);

            ContestChannel category = new ContestChannel();
            category.setFileType(fileType);
            session.save(category);

            ContestType contestType = new ContestType();
            contestType.setDescription("desc");
            contestType.setRequirePreviewFile(false);
            contestType.setRequirePreviewImage(true);
            session.save(contestType);

            ContestStatus status = new ContestStatus();
            status.setDescription("description");
            status.setName("name");
            session.save(status);

            Contest contest = new Contest();
            contest.setContestChannel(category);
            contest.setContestType(contestType);
            contest.setStatus(status);
            contest.setName("contest1");
            contest.setProjectId(new Long(1));
            contest.setTcDirectProjectId(new Long(2));
            contest.setStartDate(new Date());
            contest.setEndDate(new Date());
            contest.setWinnerAnnoucementDeadline(new Date());
            contest.setCreatedUser(new Long(1));
            session.save(contest);

            FilePath filePath = new FilePath();
            filePath.setModifyDate(new Date());
            filePath.setPath("path");
            session.save(filePath);

            MimeType mimeType = new MimeType();
            mimeType.setDescription("description");
            mimeType.setStudioFileType(fileType);
            session.save(mimeType);

            SubmissionType submissionType = new SubmissionType();
            submissionType.setDescription("description");
            session.save(submissionType);

            PaymentStatus paymentStatus = new PaymentStatus();
            paymentStatus.setDescription("description");
            session.save(paymentStatus);

            SubmissionStatus submissionStatus = new SubmissionStatus();
            submissionStatus.setDescription("description1");
            session.save(submissionStatus);

            Submission submission = new Submission();
            submission.setContest(contest);
            submission.setSystemFileName("submission3");
            submission.setSubmitterId(new Long(1));
            submission.setCreateDate(new Date());
            submission.setModifyDate(new Date());
            submission.setHeight(new Integer(100));
            submission.setWidth(new Integer(100));
            submission.setOrSubmission(new Long(1));
            submission.setSubmissionDate(new Date());
            submission.setOriginalFileName("file1");
            submission.setFullSubmissionPath(filePath);
            submission.setMimeType(mimeType);
            submission.setType(submissionType);
            submission.setStatus(submissionStatus);

            session.save(submission);

            ReviewStatus reviewStatus = new ReviewStatus();
            reviewStatus.setDescription("passed");
            session.save(reviewStatus);

            SubmissionReview submissionReview = new SubmissionReview();
            submissionReview.setModifyDate(new Date());
            submissionReview.setReviewerId(new Long(1));
            submissionReview.setSubmission(submission);
            submissionReview.setStatus(reviewStatus);
            submissionReview.setText("string");
            session.save(submissionReview);

            Query query = session.createQuery("from SubmissionReview as s");

            SubmissionReview persisted = (SubmissionReview) query.list().get(query.list().size() - 1);
            assertNotNull("The submission mapping is wrong.",
                persisted.getSubmission());
            assertEquals("The submission mapping is wrong.", "submission3",
                persisted.getSubmission().getSystemFileName());
            assertNotNull("The status mapping is wrong.", persisted.getStatus());
            assertEquals("The status mapping is wrong.", "passed",
                persisted.getStatus().getDescription());

            session.delete(submissionReview);
            session.delete(reviewStatus);
            session.delete(submission);
            session.delete(paymentStatus);
            session.delete(submissionStatus);
            session.delete(submissionType);
            session.delete(mimeType);
            session.delete(filePath);
            session.delete(contest);
            session.delete(status);
            session.delete(contestType);
            session.delete(category);
            session.delete(fileType);

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
