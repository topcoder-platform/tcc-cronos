/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Date;

/**
 * <p>
 * This is a simple data class that encapsulates the different information
 * related to a Screening Task. It contains only getters and setters.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe. It is expected to be utilized
 * by only a single thread.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class ScreeningTask {

    /**
     * <p>
     * This is the date that the Screening task was created.
     * </p>
     */
    private Date creationDate;

    /**
     * <p>
     * This is the operator that created the Screening task.
     * </p>
     */
    private String creationUser;

    /**
     * <p>
     * This is the date that the Screening task was modified.
     * </p>
     */
    private Date modificationDate;

    /**
     * <p>
     * This is the operator that modified the Screening task.
     * </p>
     */
    private String modificationUser;

    /**
     * <p>
     * This is the time that the task started.
     * </p>
     */
    private Date startTimestamp;

    /**
     * <p>
     * This is the id of the screener that is currently working on the task. (It
     * will be Long.MIN_VALUE if no screener is using it yet)
     * </p>
     */
    private long screenerId = Long.MIN_VALUE;

    /**
     * <p>
     * This is the current ScreeningStatus of the task.
     * </p>
     */
    private ScreeningStatus screeningStatus;

    /**
     * <p>
     * This is the id of the screening task.
     * </p>
     */
    private long id = Long.MIN_VALUE;

    /**
     * <p>
     * This is ScreeningData that serves as additional related information to
     * the screening task.
     * </p>
     */
    private ScreeningData screeningData;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ScreeningTask() {
    }

    /**
     * <p>
     * Retrieves the date that the Screening task was created.
     * </p>
     * @return the date that the Screening task was created.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Sets the date that the Screening task was created.
     * </p>
     * @param creationDate
     *            the date that the Screening task was created.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("creationDate should not be null.");
        }

        this.creationDate = creationDate;
    }

    /**
     * <p>
     * Retrieves the operator that created the Screening task.
     * </p>
     * @return the operator that created the Screening task.
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * Sets the operator that created the Screening task.
     * </p>
     * @param creationUser
     *            the operator that created the Screening task.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setCreationUser(String creationUser) {
        if (creationUser == null) {
            throw new IllegalArgumentException("creationUser should not be null.");
        }
        if (creationUser.trim().length() == 0) {
            throw new IllegalArgumentException("creationUser should not be empty (trimmed).");
        }

        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Retrieves the date that the Screening task was modified.
     * </p>
     * @return the date that the Screening task was modified.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Sets the date that the Screening task was modified.
     * </p>
     * @param modificationDate
     *            the date that the Screening task was modified.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public void setModificationDate(Date modificationDate) {
        if (modificationDate == null) {
            throw new IllegalArgumentException("modificationDate should not be null.");
        }

        this.modificationDate = modificationDate;
    }

    /**
     * <p>
     * Retrieves the operator that modified the Screening task.
     * </p>
     * @return the operator that modified the Screening task.
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * <p>
     * Sets the operator that modified the Screening task.
     * </p>
     * @param modificationUser
     *            the operator that modified the Screening task.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setModificationUser(String modificationUser) {
        if (modificationUser == null) {
            throw new IllegalArgumentException("modificationUser should not be null.");
        }
        if (modificationUser.trim().length() == 0) {
            throw new IllegalArgumentException("modificationUser should not be empty (trimmed).");
        }

        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Retrieves the time that the task started.
     * </p>
     * @return the time that the task started.
     */
    public Date getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * <p>
     * Sets the time that the task started.
     * </p>
     * @param startTimestamp
     *            the time that the task started.
     */
    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    /**
     * <p>
     * Retrieves the id of the screener that is currently working on the task.
     * (It will be Long.MIN_VALUE if no screener is using it yet)
     * </p>
     * @return the id of the screener that is currently working on the task. (It
     *         will be Long.MIN_VALUE if no screener is using it yet)
     */
    public long getScreenerId() {
        return screenerId;
    }

    /**
     * <p>
     * Sets the id of the screener that is currently working on the task. (It
     * will be Long.MIN_VALUE if no screener is using it yet)
     * </p>
     * @param screenerId
     *            the id of the screener that is currently working on the task.
     *            (It will be Long.MIN_VALUE if no screener is using it yet)
     * @throws IllegalArgumentException
     *             if screenerId is <= 0 and is not Long.MIN_VALUE
     */
    public void setScreenerId(long screenerId) {
        if (screenerId <= 0 && screenerId != Long.MIN_VALUE) {
            throw new IllegalArgumentException("screenerId should be > 0 or Long.MIN_VALUE.");
        }

        this.screenerId = screenerId;
    }

    /**
     * <p>
     * Retrieves the current ScreeningStatus of the task.
     * </p>
     * @return the current ScreeningStatus of the task.
     */
    public ScreeningStatus getScreeningStatus() {
        return screeningStatus;
    }

    /**
     * <p>
     * Sets the current ScreeningStatus of the task.
     * </p>
     * @param screeningStatus
     *            the current ScreeningStatus of the task.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public void setScreeningStatus(ScreeningStatus screeningStatus) {
        if (screeningStatus == null) {
            throw new IllegalArgumentException("screeningStatus should not be null.");
        }

        this.screeningStatus = screeningStatus;
    }

    /**
     * <p>
     * Retrieves the id of the screening task.
     * </p>
     * @return the id of the screening task.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id of the screening task.
     * </p>
     * @param id
     *            the id of the screening task.
     * @throws IllegalArgumentException
     *             if id is <= 0
     */
    public void setId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id should be > 0.");
        }

        this.id = id;
    }

    /**
     * <p>
     * Retrieves ScreeningData that serves as additional related information to
     * the screening task.
     * </p>
     * @return ScreeningData that serves as additional related information to
     *         the screening task.
     */
    public ScreeningData getScreeningData() {
        return screeningData;
    }

    /**
     * <p>
     * Sets the ScreeningData that serves as additional related information to
     * the screening task.
     * </p>
     * @param screeningData
     *            ScreeningData that serves as additional related information to
     *            the screening task.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public void setScreeningData(ScreeningData screeningData) {
        if (screeningData == null) {
            throw new IllegalArgumentException("screeningData should not be null.");
        }

        this.screeningData = screeningData;
    }
}
