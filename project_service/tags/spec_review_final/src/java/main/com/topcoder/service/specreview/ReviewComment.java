/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * It represents the review comment for a review section.
 * 
 * @author snow01
 * @version 1.0
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "reviewComment", propOrder = { "commentId", "specReviewId", "comment", "createUser", "createTime",
        "roleType", "isViewed" })
public class ReviewComment implements Serializable {

    /**
     * Default Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    /** The comment id. */
    private long commentId;

    /** The spec review id. */
    private long specReviewId;

    /** The comment. */
    private String comment;

    /** The create user. */
    private String createUser;

    /** The create time. */
    private Date createTime;

    /** The role type. */
    private ReviewUserRoleType roleType;

    /** The is viewed. */
    private boolean isViewed;

    /**
     * Instantiates a new review comment.
     */
    public ReviewComment() {

    }

    /**
     * Gets the comment id.
     * 
     * @return the comment id
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Sets the comment id.
     * 
     * @param commentId
     *            the new comment id
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
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
     * Gets the comment.
     * 
     * @return the comment
     */
    public String getComment() {
        return comment;
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
     * Gets the role type.
     * 
     * @return the role type
     */
    public ReviewUserRoleType getRoleType() {
        return roleType;
    }

    /**
     * Sets the role type.
     * 
     * @param roleType
     *            the new role type
     */
    public void setRoleType(ReviewUserRoleType roleType) {
        this.roleType = roleType;
    }

    /**
     * Checks if is viewed.
     * 
     * @return true, if is viewed
     */
    public boolean isViewed() {
        return isViewed;
    }

    /**
     * Sets the viewed.
     * 
     * @param isViewed
     *            the new viewed
     */
    public void setViewed(boolean isViewed) {
        this.isViewed = isViewed;
    }
}
