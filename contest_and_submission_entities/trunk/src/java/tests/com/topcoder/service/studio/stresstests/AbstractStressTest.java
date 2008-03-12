/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.topcoder.service.studio.contest.Contest;
import com.topcoder.service.studio.contest.ContestChannel;
import com.topcoder.service.studio.contest.ContestStatus;
import com.topcoder.service.studio.contest.ContestType;
import com.topcoder.service.studio.contest.FilePath;
import com.topcoder.service.studio.contest.MimeType;
import com.topcoder.service.studio.contest.StudioFileType;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionStatus;
import com.topcoder.service.studio.submission.SubmissionType;


/**
 * <p>
 * The abstract stress test class used for all stress test cases.
 * </p>
 *
 * @author WN
 * @version 1.0
 *
 */
public class AbstractStressTest extends TestCase {
    /**
     * The Session instance used for tests.
     */
    protected Session session = null;

    /**
     * <p>
     * Sets up the necessary environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        session = createSessionFactory().openSession();
    }

    /**
     * <p>
     * Tears down the environment.
     * </P>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearTables();
        session.close();
        session = null;
    }

    /**
     * <p>
     * Creates a SessionFactory instance used for tests.
     * </p>
     *
     * @return the SessionFactory instance
     *
     * @throws Exception
     *             to JUnit.
     */
    private SessionFactory createSessionFactory() throws Exception {
        Configuration config = new Configuration().configure(
                "hibernate.cfg.xml");

        return config.buildSessionFactory();
    }

    /**
     * <p>
     * Clears all tables in database.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    private void clearTables() throws Exception {
        Session session = createSessionFactory().openSession();

        Transaction t = session.beginTransaction();
        String[] tables = new String[] {
            "delete from submission", "delete from submission_type_lu",
            "delete from submission_status_lu",
            "delete from submission_review",
            "delete from submission_prize_xref",
            "delete from submission_payments",
            "delete from review_status_lu", "delete from prize",
            "delete from prize_type_lu", "delete from payment_status",
            "delete from path", "delete from mime_type_lu",
            "delete from document",
            "delete from document_type_lu", "delete from contest",
            "delete from contest_type_lu", "delete from contest_type_config",
            "delete from contest_status_relation",
            "delete from contest_status_lu",
            "delete from contest_result",
            "delete from contest_registration",
            "delete from contest_prize_xref",
            "delete from contest_file_type_xref",
            "delete from contest_document_xref",
            "delete from contest_config", "delete from contest_channel_lu",
            "delete from contest_config", "delete from file_type_lu"
        };

        for (int i = 0; i < tables.length; i++) {
            Query query = session.createSQLQuery(tables[i]);
            query.executeUpdate();
        }

        t.commit();
        session.close();
    }

    /**
     * <p>
     * Create a Contest instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the Contest instance
     */
    public Contest createContest(int i) {
        Contest contest = new Contest();
        contest.setContestId(i + 0L);

        ContestType contestType = new ContestType();
        contest.setContestType(contestType);
        contest.setProjectId(i + 0L);
        contest.setTcDirectProjectId(i + 0L);
        contest.setEventId(i + 0L);
        contest.setForumId(i + 0L);
        contest.setName("name" + i);
        contest.setCreatedUser(i + 0L);
        contest.setEndDate(new Date());
        contest.setStartDate(new Date());
        contest.setWinnerAnnoucementDeadline(new Date());

        // create StudioFileType
        StudioFileType fileType = new StudioFileType();
        fileType.setStudioFileType(i + 0L);
        fileType.setDescription("description" + i);
        fileType.setSort(i);
        fileType.setImageFile(true);
        fileType.setExtension("extension" + i);

        // create ContestCategory
        ContestChannel category = new ContestChannel();
        category.setContestChannelId(i + 0L);
        category.setFileType(fileType);
        // set ContestCategory
        contest.setContestChannel(category);

        // ContestStatus
        ContestStatus contestStatus = new ContestStatus();
        contestStatus.setContestStatusId(i + 0L);
        contestStatus.setDescription("description" + i);
        contestStatus.setName("name");
        contest.setStatus(contestStatus);

        return contest;
    }

    /**
     * <p>
     * Create a Submission instance used for tests.
     * </p>
     *
     * @param i used for loop index
     * @return the Submission instance
     */
    public Submission createSubmission(int i) {
        Submission submission = new Submission();
        submission.setSubmissionId(i + 0L);
        submission.setSubmitterId(i + 0L);
        // set contest
        submission.setContest(createContest(i));

        // create MimeType
        MimeType mimeType = new MimeType();
        mimeType.setMimeTypeId(i + 0L);
        mimeType.setStudioFileType(new StudioFileType());
        mimeType.setDescription("description" + i);
        submission.setMimeType(mimeType);

        // create SubmissionType
        SubmissionType submissionType = new SubmissionType();
        submissionType.setSubmissionTypeId(i + 0L);
        submissionType.setDescription("description" + i);
        submission.setType(submissionType);
        // set or_submission_id
        submission.setOrSubmission(i + 0L);

        // set watermarked_path
        FilePath filePath = new FilePath();
        filePath.setModifyDate(new Date());
        filePath.setPath("path" + i);
        // set full_path
        submission.setFullSubmissionPath(filePath);
        // set original_file_name
        submission.setOriginalFileName("originalFileName" + i);
        // set system_file_name
        submission.setSystemFileName("systemFileName" + i);
        // set create_date
        submission.setCreateDate(new Date());
        // set height
        submission.setHeight(i + 100);
        // set width
        submission.setWidth(i + 200);
        // set modify_date
        submission.setModifyDate(new Date());
        // set submission_date
        submission.setSubmissionDate(new Date());

        SubmissionStatus status = new SubmissionStatus();
        status.setDescription("description");
        submission.setStatus(status);

        return submission;
    }
}
