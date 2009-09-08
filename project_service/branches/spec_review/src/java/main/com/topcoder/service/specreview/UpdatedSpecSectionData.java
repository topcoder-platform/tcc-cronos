/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;

/**
 * The Class UpdatedSpecSectionData - represents the updates made to various sections.
 * 
 * This is used to notify the end users about updates made to the spec review.
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since Spec Review Finishing Touches v1.0
 */
public class UpdatedSpecSectionData implements Serializable {

    /** Default serial version id. */
    private static final long serialVersionUID = 1L;

    /** The section name. */
    private String sectionName;

    /** The status. */
    private String status;

    /** The comment. */
    private String comment;

    /** The user. */
    private String user;

    /**
     * Gets the section name.
     * 
     * @return the section name
     */
    public String getSectionName() {
        return this.sectionName;
    }

    /**
     * Sets the section name.
     * 
     * @param sectionName
     *            the new section name
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     * 
     * @param status
     *            the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Sets the comment.
     * 
     * @param comment
     *            the new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the user.
     * 
     * @return the user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Sets the user.
     * 
     * @param user
     *            the new user
     */
    public void setUser(String user) {
        this.user = user;
    }
}
