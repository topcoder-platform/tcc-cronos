/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

/**
 * <p>
 * This is a data class that is used to provide additional screening data
 * regarding a screening task. It contains only getters and setters.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe. It is expected to be utilized
 * by only a single thread.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ScreeningData {

    /**
     * <p>
     * This is a file identifier that is used to identify the location of the
     * file submission that needs to be screened.
     * </p>
     */
    private String fileIdentifier;

    /**
     * <p>
     * This is an upload identifier that is used to identify the upload process
     * used to place the submission within the screening system.
     * </p>
     */
    private long uploadId = Long.MIN_VALUE;

    /**
     * <p>
     * This is a project identifier that is used to identify the project with
     * which the submission is related to.
     * </p>
     */
    private long projectId = Long.MIN_VALUE;

    /**
     * <p>
     * This is a project category identifier that is used to identify the
     * project with which the submission is related to.
     * </p>
     */
    private long projectCategoryId = Long.MIN_VALUE;

    /**
     * <p>
     * This variable contains the handle of the submitter who submitted the
     * entity that needs to be screened.
     * </p>
     */
    private String submitterHandle;

    /**
     * <p>
     * This variable contains the first name of the submitter who submitted the
     * entity that needs to be screened.
     * </p>
     */
    private String submitterFirstName;

    /**
     * <p>
     * This variable contains the last name of the submitter who submitted the
     * entity that needs to be screened.
     * </p>
     */
    private String submitterLastName;

    /**
     * <p>
     * This variable contains the email address of the submitter who submitted
     * the entity that needs to be screened.
     * </p>
     */
    private String submitterEmail;

    /**
     * <p>
     * This variable contains the alternative email addresses of the submitter
     * who submitted the entity that needs to be screened.
     * </p>
     */
    private String[] submitterAlternativeEmails;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ScreeningData() {
    }

    /**
     * <p>
     * Retrieves the file identifier that is used to identify the location of
     * the file submission that needs to be screened.
     * </p>
     * @return the file identifier that is used to identify the location of the
     *         file submission that needs to be screened.
     */
    public String getFileIdentifier() {
        return fileIdentifier;
    }

    /**
     * <p>
     * Sets the file identifier that is used to identify the location of the
     * file submission that needs to be screened.
     * </p>
     * @param fileIdentifier
     *            the file identifier that is used to identify the location of
     *            the file submission that needs to be screened.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setFileIdentifier(String fileIdentifier) {
        if (fileIdentifier == null) {
            throw new IllegalArgumentException("fileIdentifier should not be null.");
        }
        if (fileIdentifier.trim().length() == 0) {
            throw new IllegalArgumentException("fileIdentifier should not be empty (trimmed).");
        }

        this.fileIdentifier = fileIdentifier;
    }

    /**
     * <p>
     * Retrieves an upload identifier that is used to identify the upload
     * process used to place the submission within the screening system.
     * </p>
     * @return an upload identifier that is used to identify the upload process
     *         used to place the submission within the screening system.
     */
    public long getUploadId() {
        return uploadId;
    }

    /**
     * <p>
     * Sets an upload identifier that is used to identify the upload process
     * used to place the submission within the screening system.
     * </p>
     * @param uploadId
     *            an upload identifier that is used to identify the upload
     *            process used to place the submission within the screening
     *            system.
     * @throws IllegalArgumentException
     *             if uploadId is <= 0
     */
    public void setUploadId(long uploadId) {
        if (uploadId <= 0) {
            throw new IllegalArgumentException("uploadId should be > 0.");
        }

        this.uploadId = uploadId;
    }

    /**
     * <p>
     * Retrieves a project identifier that is used to identify the project with
     * which the submission is related to.
     * </p>
     * @return a project identifier that is used to identify the project with
     *         which the submission is related to.
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * <p>
     * Sets a project identifier that is used to identify the project with which
     * the submission is related to.
     * </p>
     * @param projectId
     *            a project identifier that is used to identify the project with
     *            which the submission is related to.
     * @throws IllegalArgumentException
     *             if projectId is <= 0
     */
    public void setProjectId(long projectId) {
        if (projectId <= 0) {
            throw new IllegalArgumentException("projectId should be > 0.");
        }

        this.projectId = projectId;
    }

    /**
     * <p>
     * Retrieves a project category identifier that is used to identify the
     * project with which the submission is related to.
     * </p>
     * @return a project category identifier that is used to identify the
     *         project with which the submission is related to.
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * <p>
     * Sets a project category identifier that is used to identify the project
     * with which the submission is related to.
     * </p>
     * @param projectCategoryId
     *            a project category identifier that is used to identify the
     *            project with which the submission is related to.
     * @throws IllegalArgumentException
     *             if projectCategoryId is <= 0
     */
    public void setProjectCategoryId(long projectCategoryId) {
        if (projectCategoryId <= 0) {
            throw new IllegalArgumentException("projectCategoryId should be > 0.");
        }

        this.projectCategoryId = projectCategoryId;
    }

    /**
     * <p>
     * Retrieves the alternative email addresses of the submitter who submitted
     * the entity that needs to be screened.
     * </p>
     * @return the alternative email addresses of the submitter who submitted
     *         the entity that needs to be screened.
     */
    public String[] getSubmitterAlternativeEmails() {
        return submitterAlternativeEmails;
    }

    /**
     * <p>
     * Sets the alternative email addresses of the submitter who submitted the
     * entity that needs to be screened.
     * </p>
     * @param submitterAlternativeEmails
     *            the alternative email addresses of the submitter who submitted
     *            the entity that needs to be screened.
     * @throws IllegalArgumentException
     *             if the parameter is null or contains null or an empty String.
     */
    public void setSubmitterAlternativeEmails(String[] submitterAlternativeEmails) {
        if (submitterAlternativeEmails == null) {
            throw new IllegalArgumentException("submitterAlternativeEmails should not be null.");
        }
        for (int i = 0; i < submitterAlternativeEmails.length; ++i) {
            if (submitterAlternativeEmails[i] == null) {
                throw new IllegalArgumentException("submitterAlternativeEmails[" + i
                    + "] should not be null.");
            }
            if (submitterAlternativeEmails[i].trim().length() == 0) {
                throw new IllegalArgumentException("submitterAlternativeEmails[" + i
                    + "] should not be empty (trimmed).");
            }
        }

        this.submitterAlternativeEmails = submitterAlternativeEmails;
    }

    /**
     * <p>
     * Retrieves the email address of the submitter who submitted the entity
     * that needs to be screened.
     * </p>
     * @return the email address of the submitter who submitted the entity that
     *         needs to be screened.
     */
    public String getSubmitterEmail() {
        return submitterEmail;
    }

    /**
     * <p>
     * Sets the email address of the submitter who submitted the entity that
     * needs to be screened.
     * </p>
     * @param submitterEmail
     *            the email address of the submitter who submitted the entity
     *            that needs to be screened.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setSubmitterEmail(String submitterEmail) {
        if (submitterEmail == null) {
            throw new IllegalArgumentException("submitterEmail should not be null.");
        }
        if (submitterEmail.trim().length() == 0) {
            throw new IllegalArgumentException("submitterEmail should not be empty (trimmed).");
        }

        this.submitterEmail = submitterEmail;
    }

    /**
     * <p>
     * Retrieves the first name of the submitter who submitted the entity that
     * needs to be screened.
     * </p>
     * @return the first name of the submitter who submitted the entity that
     *         needs to be screened.
     */
    public String getSubmitterFirstName() {
        return submitterFirstName;
    }

    /**
     * <p>
     * Sets the first name of the submitter who submitted the entity that needs
     * to be screened.
     * </p>
     * @param submitterFirstName
     *            the first name of the submitter who submitted the entity that
     *            needs to be screened.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setSubmitterFirstName(String submitterFirstName) {
        if (submitterFirstName == null) {
            throw new IllegalArgumentException("submitterFirstName should not be null.");
        }
        if (submitterFirstName.trim().length() == 0) {
            throw new IllegalArgumentException("submitterFirstName should not be empty (trimmed).");
        }

        this.submitterFirstName = submitterFirstName;
    }

    /**
     * <p>
     * Retrieves the handle of the submitter who submitted the entity that needs
     * to be screened.
     * </p>
     * @return the handle of the submitter who submitted the entity that needs
     *         to be screened.
     */
    public String getSubmitterHandle() {
        return submitterHandle;
    }

    /**
     * <p>
     * Sets the handle of the submitter who submitted the entity that needs to
     * be screened.
     * </p>
     * @param submitterHandle
     *            the handle of the submitter who submitted the entity that
     *            needs to be screened.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setSubmitterHandle(String submitterHandle) {
        if (submitterHandle == null) {
            throw new IllegalArgumentException("submitterHandle should not be null.");
        }
        if (submitterHandle.trim().length() == 0) {
            throw new IllegalArgumentException("submitterHandle should not be empty (trimmed).");
        }

        this.submitterHandle = submitterHandle;
    }

    /**
     * <p>
     * Retrieves the last name of the submitter who submitted the entity that
     * needs to be screened.
     * </p>
     * @return the last name of the submitter who submitted the entity that
     *         needs to be screened.
     */
    public String getSubmitterLastName() {
        return submitterLastName;
    }

    /**
     * <p>
     * Sets the last name of the submitter who submitted the entity that needs
     * to be screened.
     * </p>
     * @param submitterLastName
     *            the last name of the submitter who submitted the entity that
     *            needs to be screened.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setSubmitterLastName(String submitterLastName) {
        if (submitterLastName == null) {
            throw new IllegalArgumentException("submitterLastName should not be null.");
        }
        if (submitterLastName.trim().length() == 0) {
            throw new IllegalArgumentException("submitterLastName should not be empty (trimmed).");
        }

        this.submitterLastName = submitterLastName;
    }
}
