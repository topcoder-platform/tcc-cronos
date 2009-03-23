/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.contest;

import java.util.Date;

import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionStatus;
import com.topcoder.service.studio.submission.SubmissionType;

/**
 * <p>
 * Test helper class.
 * </p>
 *
 * @author cyberjag
 * @version 1.0
 */
public class TestHelper {
    /**
     * No instances allowed.
     */
    private TestHelper() {
        // empty
    }

    /**
     * Sets all required values for contest.
     *
     * @param entity
     *            the contest entity
     * @param date
     *            date to set
     * @param contestCategory
     *            category to set
     * @param contestType
     *            contest type
     * @param status
     *            contest status
     */
    public static void populateContest(Contest entity, Date date, ContestChannel contestCategory,
            ContestType contestType, ContestStatus status) {
        entity.setContestChannel(contestCategory);
        entity.setContestType(contestType);
        entity.setCreatedUser(10L);
        entity.setEndDate(date);
        entity.setEventId(101L);
        entity.setForumId(1000L);
        entity.setName("name");
        entity.setProjectId(101L);
        entity.setStartDate(date);
        entity.setStatus(status);
        entity.setStatusId(1L);
        entity.setTcDirectProjectId(1101L);
        entity.setWinnerAnnoucementDeadline(date);
    }

    /**
     * Sets all the required values studio file type.
     *
     * @param entity
     *            the entity to set
     */
    public static void populateStudioFileType(StudioFileType entity) {
        entity.setDescription("description");
        entity.setExtension("extension");
        entity.setImageFile(true);
        entity.setSort(10);
    }

    /**
     * Sets all the required values for contest category.
     *
     * @param entity
     *            the entity to be set
     * @param fileType
     *            the studio file type
     */
    public static void populateContestCategory(ContestChannel entity, StudioFileType fileType) {
        entity.setDescription("description");
    }

    /**
     * Sets all the required values for contest type.
     *
     * @param contestType
     *            the entity to be set.
     */
    public static void populateContestType(ContestType contestType) {
        contestType.setDescription("description");
        contestType.setRequirePreviewFile(true);
        contestType.setRequirePreviewImage(true);
    }

    /**
     * Sets all the required values for submission.
     *
     * @param entity
     *            the entity to be set
     * @param date
     *            the date to be used
     * @param contest
     *            the contest
     * @param filePath
     *            the file path
     * @param mimeType
     *            the mime type
     * @param submissionType
     *            the submission type
     * @param status
     *            the submission status
     */
    public static void populateSubmission(Submission entity, Date date, Contest contest, FilePath filePath,
            MimeType mimeType, SubmissionType submissionType, SubmissionStatus status) {
        entity.setContest(contest);
        entity.setCreateDate(date);
        entity.setFullSubmissionPath(filePath);
        entity.setHeight(20);
        entity.setMimeType(mimeType);
        entity.setOriginalFileName("originalFileName");
        entity.setOrSubmission(10L);
        entity.setRank(2);
        entity.setSubmissionDate(date);
        entity.setSubmitterId(100L);
        entity.setSystemFileName("systemFileName");
        entity.setType(submissionType);
        entity.setWidth(10);
        entity.setModifyDate(date);
        entity.setStatus(status);
    }
}
