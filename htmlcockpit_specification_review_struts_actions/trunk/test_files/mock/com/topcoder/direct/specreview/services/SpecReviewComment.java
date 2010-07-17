package com.topcoder.direct.specreview.services;

import java.io.Serializable;
import java.util.List;

/**
 * This entity represents comments for a specific question in the spec review.
 * 
 * Thread-safety: mutable and not thread-safe.
 */
public class SpecReviewComment implements Serializable {

    /**
     * Represents the question id.
     * It has getter & setter.
     * It can be any value.
     */
    private long questionId;

    /**
     * Represents the list of comments for the question.
     * It has getter & setter.
     * It can be any value.
     */
    private List<UserComment> comments;

    /**
     * Empty constructor.
     */
    public SpecReviewComment() {
    }

    /**
     * Getter for the namesake instance variable.
     * Return the namesake instance variable.
     * 
     * @param Return
     * @return
     */
    public long getQuestionId() {
        return questionId;
    }

    /**
     * Setter for the namesake instance variable.
     * 
     * questionId - the namesake instance variable
     * 
     * @param questionId
     * @param Return
     */
    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    /**
     * Getter for the namesake instance variable.
     * Return the namesake instance variable.
     * 
     * @param Return
     * @return
     */
    public List<UserComment> getComments() {
        return comments;
    }

    /**
     * Setter for the namesake instance variable.
     * 
     * comments - the namesake instance variable
     * 
     * @param Return
     * @param comments
     */
    public void setComments(List<UserComment> comments) {
        this.comments = comments;
    }
}
