package com.topcoder.management.project;

import java.util.Date;

/**
 */
public class ReviewApplication {
    /**
     */
    private long id;

    /**
     */
    private long reviewerId;

    /**
     */
    private long projectId;

    /**
     */
    private Date applicationDate;

    /**
     */
    private boolean acceptPrimary;

    /**
     */
    public ReviewApplication() {
    }

    /**
     */
    public long getId() {
        return this.id;
    }

    /**
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     */
    public long getReviewerId() {
        return this.reviewerId;
    }

    /**
     */
    public void setReviewerId(long reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     */
    public Date getApplicationDate() {
        return this.applicationDate;
    }

    /**
     */
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     */
    public boolean isAcceptPrimary() {
        return this.acceptPrimary;
    }

    /**
     */
    public void setAcceptPrimary(boolean primary) {
        this.acceptPrimary = primary;
    }
}