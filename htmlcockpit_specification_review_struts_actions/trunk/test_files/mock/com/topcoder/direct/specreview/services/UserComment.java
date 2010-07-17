package com.topcoder.direct.specreview.services;

import java.util.*;
import java.io.Serializable;
import java.lang.*;

/**
 * This entity represents the comment for a specific question in the review scorecard.
 * 
 * Thread-safety: mutable and not thread-safe.
 */
public class UserComment implements Serializable {

    /**
     * Represents the comment id.
     * It has getter & setter.
     * It can be any value.
     */
    private long commentId;

    /**
     * Represents the user making the comment.
     * It has getter & setter.
     * It can be any value.
     */
    private String commentBy;

    /**
     * Represents the comment date.
     * It has getter & setter.
     * It can be any value.
     */
    private Date commentDate;

    /**
     * Represents the comment.
     * It has getter & setter.
     * It can be any value.
     */
    private String comment;

    /**
     * Empty constructor.
     */
    public UserComment() {
    }

    /**
     * Getter for the namesake instance variable.
     * Return the namesake instance variable.
     * 
     * @param Return
     * @return
     */
    public long getCommentId() {
        return commentId;
    }

    /**
     * Setter for the namesake instance variable.
     * 
     * commentId - the namesake instance variable
     * 
     * @param Return
     * @param commentId
     */
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    /**
     * Getter for the namesake instance variable.
     * Return the namesake instance variable.
     * 
     * @param Return
     * @return
     */
    public String getCommentBy() {
        return commentBy;
    }

    /**
     * Setter for the namesake instance variable.
     * 
     * commentBy - the namesake instance variable
     * 
     * @param Return
     * @param commentBy
     */
    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    /**
     * Getter for the namesake instance variable.
     * Return the namesake instance variable.
     * 
     * @param Return
     * @return
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * Setter for the namesake instance variable.
     * 
     * commentDate - the namesake instance variable
     * 
     * @param commentDate
     * @param Return
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * Getter for the namesake instance variable.
     * Return the namesake instance variable.
     * 
     * @param Return
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * Setter for the namesake instance variable.
     * 
     * comments - the namesake instance variable
     * 
     * @param Return
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
