/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.common.model;

import java.util.Date;

/**
 * <p>
 * This class is a container for information about a single audit record. It is a simple JavaBean (POJO) that provides
 * getters and setters for all private attributes and performs no argument validation in the setters.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread safe.
 * </p>
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class AuditRecord extends Base {
    /**
     * Generated Serial version id.
     */
    private static final long serialVersionUID = -4430716721368405016L;
    /**
     * The ID of the audit record. Can be any value. Has getter and setter.
     */
    private Long id;
    /**
     * The operation type. Can be any value. Has getter and setter.
     */
    private String operationType;
    /**
     * The handle of the user that performs the operation. Can be any value. Has getter and setter.
     */
    private String handle;
    /**
     * The IP address of the user's machine. Can be any value. Has getter and setter.
     */
    private String ipAddress;
    /**
     * The previous value associated with this audit record. Can be any value. Has getter and setter.
     */
    private String previousValue;
    /**
     * The new value associated with this audit record. Can be any value. Has getter and setter.
     */
    private String newValue;
    /**
     * The timestamp of the operation. Can be any value. Has getter and setter.
     */
    private Date timestamp;

    /**
     * Creates an instance of AuditRecord.
     */
    public AuditRecord() {
    }

    /**
     * Retrieves the ID of the audit record.
     * @return the ID of the audit record
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the audit record.
     * @param id
     *            the ID of the audit record
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the operation type.
     * @return the operation type
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * Sets the operation type.
     * @param operationType
     *            the operation type
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * Retrieves the handle of the user that performs the operation.
     * @return the handle of the user that performs the operation
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle of the user that performs the operation.
     * @param handle
     *            the handle of the user that performs the operation
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Retrieves the IP address of the user's machine.
     * @return the IP address of the user's machine
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets the IP address of the user's machine.
     * @param ipAddress
     *            the IP address of the user's machine
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Retrieves the previous value associated with this audit record.
     * @return the previous value associated with this audit record
     */
    public String getPreviousValue() {
        return previousValue;
    }

    /**
     * Sets the previous value associated with this audit record.
     * @param previousValue
     *            the previous value associated with this audit record
     */
    public void setPreviousValue(String previousValue) {
        this.previousValue = previousValue;
    }

    /**
     * Retrieves the new value associated with this audit record.
     * @return the new value associated with this audit record
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets the new value associated with this audit record.
     * @param newValue
     *            the new value associated with this audit record
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * Retrieves the timestamp of the operation.
     * @return the timestamp of the operation
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the operation.
     * @param timestamp
     *            the timestamp of the operation
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
