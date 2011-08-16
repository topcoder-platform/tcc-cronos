/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.studio.submission;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents the submission declaration.
 *
 * Mutable and not thread-safe.
 *
 * @author Standlove, orange_cloud
 * @version 1.0
 */
public class SubmissionDeclaration implements Serializable {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 1664029362123588261L;

    /**
     * Represents the submission declaration id.
     */
    private long id;

    /**
     * Represents the submission.
     */
    private Submission submission;

    /**
     * Represents the submission declaration comment.
     */
    private String comment;

    /**
     * Represents it has external content or not.
     */
    private boolean hasExternalContent;

    /**
     * Represents the submission external contents.
     */
    private List<ExternalContent> externalContents;

    /**
     * Returns id.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns submission.
     *
     * @return submission
     */
    public Submission getSubmission() {
        return submission;
    }

    /**
     * Sets submission.
     *
     * @param submission value to set
     */
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    /**
     * Returns comment.
     *
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment value to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns hasExternalContent flag
     *
     * @return hasExternalContent flag
     */
    public boolean getHasExternalContent() {
        return hasExternalContent;
    }

    /**
     * Sets hasExternalContent flag.
     *
     * @param hasExternalContent value to set
     */
    public void setHasExternalContent(boolean hasExternalContent) {
        this.hasExternalContent = hasExternalContent;
    }

    /**
     * Returns external contents.
     *
     * @return external contents
     */
    public List<ExternalContent> getExternalContents() {
        return externalContents;
    }

    /**
     * Sets external contents.
     *
     * @param externalContents value to set
     */
    public void setExternalContents(List<ExternalContent> externalContents) {
        this.externalContents = externalContents;
    }
}
