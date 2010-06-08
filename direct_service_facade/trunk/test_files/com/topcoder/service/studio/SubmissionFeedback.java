/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * It is the DTO class which is used to transfer submission feedback. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters.
 * </p>
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author shailendra_80
 * @version 1.0
 * @since BUGR-1366
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "submissionFeedback", propOrder = { "submissionId", "feedbackText", "feedbackThumb" })
public class SubmissionFeedback implements Serializable {

    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the identifier of the submission for which this feedback is.
     */
    private long submissionId;

    /**
     * Represents the feedback text of the submission.
     */
    private String feedbackText;

    /**
     * Represents the feedback thumb of the submission.
     */
    private int feedbackThumb;

    /**
     * <p>
     * Gets the submission id.
     * </p>
     *
     * @return a <code>long</code> submission identifier.
     */
    public long getSubmissionId() {
        return this.submissionId;
    }

    /**
     * <p>
     * Sets the submission id.
     * </p>
     *
     * @param submissionId a <code>long</code> submission identifier.
     */
    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    /**
     * <p>
     * Gets the submission feedback text.
     * </p>
     *
     * @return a <code>String</code> feedback text.
     */
    public String getFeedbackText() {
        return this.feedbackText;
    }

    /**
     * <p>
     * Sets the submission feedback text.
     * </p>
     *
     * @param feedbackText a <code>long</code> feedback text.
     */
    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    /**
     * <p>
     * Gets the submission feedback thumb.
     * </p>
     *
     * @return a <code>int</code> feedback thumb.
     */
    public int getFeedbackThumb() {
        return this.feedbackThumb;
    }

    /**
     * <p>
     * Sets the submission feedback thumb.
     * </p>
     *
     * @param feedbackThumb a <code>int</code> feedback thumb.
     */
    public void setFeedbackThumb(int feedbackThumb) {
        this.feedbackThumb = feedbackThumb;
    }
}
