/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * This is the entity class the represents a review application. It holds the association between reviewer and the
 * project and type of the review (primary or secondary).
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: this class is mutable and not thread safe.
 * </p>
 *
 * @author moonli, pvmagacho
 * @version 1.1
 * @since 1.1
 */
public class ReviewApplication implements Serializable {
    /**
     * Represents id of the review application entity. It is initialized in constructor to 0, cannot be negative. Has
     * setter and getter.
     */
    private long id;

    /**
     * Represents id of the reviewer. It is initialized in constructor to 0, cannot be negative. Has setter and
     * getter.
     */
    private long reviewerId;

    /**
     * Represents id of the project. It is initialized in constructor to 0, cannot be negative. Has setter and getter.
     */
    private long projectId;

    /**
     * Represents the date when review application occurs. It's initialized in constructor to null. Has getter and
     * setter. Can not be null after being set by setter.
     */
    private Date applicationDate;

    /**
     * Represents whether this review application is for primary review. It's initialized to false in constructor, has
     * getter and setter.
     */
    private boolean acceptPrimary;

    /**
     * Creates an instance of this class.
     */
    public ReviewApplication() {
        // does nothing
    }

    /**
     * Gets the application date.
     *
     * @return the application date
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * Sets the application date.
     *
     * @param date the application date
     * @throws IllegalArgumentException if argument is null
     */
    public void setApplicationDate(Date date) {
        Helper.checkObjectNotNull(date, "date");
        this.applicationDate = date;
    }

    /**
     * Gets the reviewer id.
     *
     * @return the reviewer id
     */
    public long getReviewerId() {
        return reviewerId;
    }

    /**
     * Sets the reviewer id.
     *
     * @param id the id of the reviewer
     * @throws IllegalArgumentException if argument is negative
     */
    public void setReviewerId(long id) {
        Helper.checkNumberPositive(id, "reviewer id");
        this.reviewerId = id;
    }

    /**
     * Gets the project id.
     *
     * @return the project id
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the project id.
     *
     * @param id the project id
     * @throws IllegalArgumentException if project id is negative
     */
    public void setProjectId(long id) {
        Helper.checkNumberPositive(id, "project id");
        this.projectId = id;
    }

    /**
     * Gets whether this review application is for primary reviewer.
     *
     * @return true if application review is for primary reviewer
     */
    public boolean isAcceptPrimary() {
        return acceptPrimary;
    }

    /**
     * Sets whether this is for primary review.
     *
     * @param primary if application review is for primary reviewer
     */
    public void setAcceptPrimary(boolean primary) {
        this.acceptPrimary = primary;
    }

    /**
     * Gets id of the review application entity.
     *
     * @return the review application id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id of the review application entity.
     *
     * @param id the review application entity id
     * @throws IllegalArgumentException if review application id is negative
     */
    public void setId(long id) {
        Helper.checkNumberPositive(id, "review application id");
        this.id = id;
    }
}
