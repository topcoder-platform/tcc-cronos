/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * The Class SpecReview.
 * 
 * @author snow01
 * @version 1.0
 * @since Cockpit Launch Contest - Inline Spec Reviews Part 2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "specReview", propOrder = { "specReviewId", "contestId", "reviewStatus", "sectionType", "studio",
        "comments", "createUser", "createTime", "modifyUser", "modifyTime" })
public class SpecReview implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The spec review id. */
    private long specReviewId;

    /** The contest id. */
    private long contestId;

    /** The review status. */
    private ReviewStatus reviewStatus;

    /** The section type. */
    private SectionType sectionType;

    /** The studio. */
    private boolean studio;

    /** The comments. */
    private Set<ReviewComment> comments;

    /** The create user. */
    private String createUser;

    /** The create time. */
    private Date createTime;

    /** The modify user. */
    private String modifyUser;

    /** The modify time. */
    private Date modifyTime;

    /**
     * Instantiates a new spec review.
     */
    public SpecReview() {

    }

    /**
     * Gets the spec review id.
     * 
     * @return the spec review id
     */
    public long getSpecReviewId() {
        return specReviewId;
    }

    /**
     * Sets the spec review id.
     * 
     * @param specReviewId
     *            the new spec review id
     */
    public void setSpecReviewId(long specReviewId) {
        this.specReviewId = specReviewId;
    }

    /**
     * Gets the contest id.
     * 
     * @return the contest id
     */
    public long getContestId() {
        return contestId;
    }

    /**
     * Sets the contest id.
     * 
     * @param contestId
     *            the new contest id
     */
    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * Gets the review status.
     * 
     * @return the review status
     */
    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    /**
     * Sets the review status.
     * 
     * @param reviewStatus
     *            the new review status
     */
    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    /**
     * Gets the section type.
     * 
     * @return the section type
     */
    public SectionType getSectionType() {
        return sectionType;
    }

    /**
     * Sets the section type.
     * 
     * @param sectionType
     *            the new section type
     */
    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    /**
     * Checks if is studio.
     * 
     * @return true, if is studio
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Sets the studio.
     * 
     * @param studio
     *            the new studio
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Gets the comments.
     * 
     * @return the comments
     */
    public Set<ReviewComment> getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     * 
     * @param comments
     *            the new comments
     */
    public void setComments(Set<ReviewComment> comments) {
        this.comments = comments;
    }

    /**
     * Gets the creates the user.
     * 
     * @return the creates the user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Sets the creates the user.
     * 
     * @param createUser
     *            the new creates the user
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Gets the creates the time.
     * 
     * @return the creates the time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets the creates the time.
     * 
     * @param createTime
     *            the new creates the time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets the modify user.
     * 
     * @return the modify user
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /**
     * Sets the modify user.
     * 
     * @param modifyUser
     *            the new modify user
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /**
     * Gets the modify time.
     * 
     * @return the modify time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * Sets the modify time.
     * 
     * @param modifyTime
     *            the new modify time
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
