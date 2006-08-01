/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.search.builder.filter.Filter;

/**
 * Mock implementation of UploadManager.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockUploadManager implements UploadManager {

    /**
     * Create submission.
     * @param submission the submission to create
     * @param operator the operator
     */
    public void createSubmission(Submission submission, String operator) {
    }

    /**
     * Create upload.
     * @param upload the upload
     * @param operator the operator
     */
    public void createUpload(Upload upload, String operator) {
    }

    /**
     * Get all submission statuses.
     * @return all the submission statuses.
     */
    public SubmissionStatus[] getAllSubmissionStatuses() {
        return null;
    }

    /**
     * Get all upload statuses.
     * @return all upload statuses
     */
    public UploadStatus[] getAllUploadStatuses() {
        return null;
    }

    /**
     * Get all types.
     * @return all types
     */
    public UploadType[] getAllUploadTypes() {
        return null;
    }

    /**
     * Get submission.
     * @param id the id
     * @return the submission
     */
    public Submission getSubmission(long id) {
        Submission sub = new MockSubmission();
        sub.setId(1);
        Upload upload = new MockUpload();
        upload.setId(1);
        upload.setOwner(2);
        sub.setUpload(upload);
        return sub;
    }

    /**
     * Get upload.
     * @param id the id
     * @return the upload
     */
    public Upload getUpload(long id) {
        return null;
    }

    /**
     * Search submission.
     * @param filter the filter
     * @return submissions found
     */
    public Submission[] searchSubmissions(Filter filter) {
        return null;
    }

    /**
     * Search uploads.
     * @param filter the filter
     * @return the uploads
     */
    public Upload[] searchUploads(Filter filter) {
        return null;
    }

    /**
     * Update submission.
     * @param submission the submission
     * @param operator the operator
     */
    public void updateSubmission(Submission submission, String operator) {

    }

    /**
     * Update upload.
     * @param upload the upload
     * @param operator the operator
     */
    public void updateUpload(Upload upload, String operator) {

    }

}
