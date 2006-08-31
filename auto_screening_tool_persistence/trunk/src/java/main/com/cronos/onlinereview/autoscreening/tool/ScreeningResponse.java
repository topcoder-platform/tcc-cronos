/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Date;

/**
 * <p>
 * This is a simple data object that is used to encapsulate the data that is
 * associated with the screening response. It contains only getters and setters.
 * </p>
 * <p>
 * Thread Safety: This class is not thread-safe. It is expected to be utilized
 * by only a single thread.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ScreeningResponse {

    /**
     * <p>
     * This is the unique identifier for a ScreeningResponse. (It will be
     * Long.MIN_VALUE if it's unset)
     * </p>
     */
    private long id = Long.MIN_VALUE;

    /**
     * <p>
     * This is the identifier of the ScreeningTask for which this response is
     * being generated.
     * </p>
     */
    private long screeningTaskId = Long.MIN_VALUE;

    /**
     * <p>
     * This is the identifier of the response code for which this response is
     * being generated. The response code will provide a quick and general way
     * of identifying a response.
     * </p>
     */
    private long responseCodeId = Long.MIN_VALUE;

    /**
     * <p>
     * This is a message that provides more detailed information with regard to
     * the screening than the response code.
     * </p>
     */
    private String detailMessage;

    /**
     * <p>
     * This is the operator that created the Screening response.
     * </p>
     */
    private String createUser;

    /**
     * <p>
     * This is the date that the Screening response was created.
     * </p>
     */
    private Date createDate;

    /**
     * <p>
     * This is the operator that modified the Screening response.
     * </p>
     */
    private String modificationUser;

    /**
     * <p>
     * This is the date that the Screening response was modified.
     * </p>
     */
    private Date modificationDate;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ScreeningResponse() {
    }

    /**
     * <p>
     * Retrieves the unique identifier for a ScreeningResponse.
     * </p>
     * @return the unique identifier for a ScreeningResponse.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the unique identifier for a ScreeningResponse.
     * </p>
     * @param id
     *            the unique identifier for a ScreeningResponse.
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
     * Retrieves the identifier of the ScreeningTask for which this response is
     * being generated.
     * </p>
     * @return the identifier of the ScreeningTask for which this response is
     *         being generated.
     */
    public long getScreeningTaskId() {
        return screeningTaskId;
    }

    /**
     * <p>
     * Sets the identifier of the ScreeningTask for which this response is being
     * generated.
     * </p>
     * @param screeningTaskId
     *            the identifier of the ScreeningTask for which this response is
     *            being generated.
     * @throws IllegalArgumentException
     *             if screeningTaskId is <= 0
     */
    public void setScreeningTaskId(long screeningTaskId) {
        if (screeningTaskId <= 0) {
            throw new IllegalArgumentException("screeningTaskId should be > 0.");
        }

        this.screeningTaskId = screeningTaskId;
    }

    /**
     * <p>
     * Retrieves the identifier of the response code for which this response is
     * being generated. The response code will provide a quick and general way
     * of identifying a response.
     * </p>
     * @return the identifier of the response code for which this response is
     *         being generated.
     */
    public long getResponseCodeId() {
        return responseCodeId;
    }

    /**
     * <p>
     * Sets the identifier of the response code for which this response is being
     * generated. The response code will provide a quick and general way of
     * identifying a response.
     * </p>
     * @param responseCodeId
     *            the identifier of the response code for which this response is
     *            being generated.
     * @throws IllegalArgumentException
     *             if responseCodeId is <= 0
     */
    public void setResponseCodeId(long responseCodeId) {
        if (responseCodeId <= 0) {
            throw new IllegalArgumentException("responseCodeId should be > 0.");
        }

        this.responseCodeId = responseCodeId;
    }

    /**
     * <p>
     * Retrives the message that provides more detailed information with regard
     * to the screening than the response code.
     * </p>
     * @return the message that provides more detailed information with regard
     *         to the screening than the response code.
     */
    public String getDetailMessage() {
        return detailMessage;
    }

    /**
     * <p>
     * Sets the message that provides more detailed information with regard to
     * the screening than the response code. .
     * </p>
     * @param detailMessage
     *            the message that provides more detailed information with
     *            regard to the screening than the response code.
     * @throws IllegalArgumentException
     *             if detialMessage is null or an empty String.
     */
    public void setDetailMessage(String detailMessage) {
        if (detailMessage == null) {
            throw new IllegalArgumentException("detailMessage should not be null.");
        }
        if (detailMessage.trim().length() == 0) {
            throw new IllegalArgumentException("detailMessage should not be empty (trimmed).");
        }

        this.detailMessage = detailMessage;
    }

    /**
     * <p>
     * Retrives the operator that created the Screening response.
     * </p>
     * @return the operator that created the Screening response.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>
     * Sets the operator that created the Screening response.
     * </p>
     * @param createUser
     *            the operator that created the Screening response.
     * @throws IllegalArgumentException
     *             if the parameter is null or an empty String.
     */
    public void setCreateUser(String createUser) {
        if (createUser == null) {
            throw new IllegalArgumentException("createUser should not be null.");
        }
        if (createUser.trim().length() == 0) {
            throw new IllegalArgumentException("createUser should not be empty (trimmed).");
        }

        this.createUser = createUser;
    }

    /**
     * <p>
     * Retrieves the date that the Screening response was created.
     * </p>
     * @return the date that the Screening response was created.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>
     * Sets the date that the Screening response was created.
     * </p>
     * @param createDate
     *            the date that the Screening response was created.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public void setCreateDate(Date createDate) {
        if (createDate == null) {
            throw new IllegalArgumentException("createDate should not be null.");
        }

        this.createDate = createDate;
    }

    /**
     * <p>
     * Retrieves the operator that modified the Screening response.
     * </p>
     * @return the operator that modified the Screening response.
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * <p>
     * Sets the operator that modified the Screening response.
     * </p>
     * @param modificationUser
     *            the operator that modified the Screening response.
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
     * Retrieves the date that the Screening response was modified.
     * </p>
     * @return the date that the Screening response was modified.
     */
    public java.util.Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Sets the date that the Screening response was modified.
     * </p>
     * @param modificationDate
     *            the date that the Screening response was modified.
     * @throws IllegalArgumentException
     *             if the parameter is null.
     */
    public void setModificationDate(Date modificationDate) {
        if (modificationDate == null) {
            throw new IllegalArgumentException("modificationDate should not be null.");
        }

        this.modificationDate = modificationDate;
    }
}
