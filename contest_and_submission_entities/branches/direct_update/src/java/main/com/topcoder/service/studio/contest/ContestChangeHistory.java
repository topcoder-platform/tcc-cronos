/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Represents the entity class contains information about changes made to a
 * contest, including the contest ID, the field changed, the username making the
 * change, a transaction ID that groups changes made at the same time, and the
 * old data and new data that represents the change.
 * </p>
 *
 * <p>
 * Thread Safety: This entity is not thread safe since it is mutable.
 * </p>
 *
 * @author Ghostar, superZZ
 * @version 1.0
 */
public class ContestChangeHistory implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 4782740922219724079L;

    /**
     * Represents the ContestId.
     */
    private Long contestId;

    /**
     * Represents the TransactionId.
     */
    private Long transactionId;

    /**
     * Represents the Timestamp .
     */
    private Date timestamp;

    /**
     * Represents the UserName.
     */
    private String userName;

    /**
     * Represents the IsUserAdmin.
     */
    private boolean userAdmin;

    /**
     * Represents the FieldName.
     */
    private String fieldName;

    /**
     * Represents the OldData.
     */
    private String oldData;

    /**
     * Represents the NewData.
     */
    private String newData;

    /**
     * Returns the contestId.
     *
     * @return the contestId.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Returns the fieldName.
     *
     * @return the fieldName.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Returns the newData.
     *
     * @return the newData.
     */
    public String getNewData() {
        return newData;
    }

    /**
     * Returns the oldData.
     *
     * @return the oldData.
     */
    public String getOldData() {
        return oldData;
    }

    /**
     * Returns the timestamp.
     *
     * @return the timestamp.
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the transactionId.
     *
     * @return the transactionId.
     */
    public Long getTransactionId() {
        return transactionId;
    }

    /**
     * Returns the userName.
     *
     * @return the userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the isUserAdmin.
     *
     * @return the isUserAdmin.
     */
    public boolean isUserAdmin() {
        return userAdmin;
    }

    /**
     * Updates the ContestId with the specified value.
     *
     * @param contestId
     *            the contestId to set.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Updates the FieldName with the specified value.
     *
     * @param fieldName
     *            the fieldName to set.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Updates the NewData with the specified value.
     *
     * @param newData
     *            the newData to set.
     */
    public void setNewData(String newData) {
        this.newData = newData;
    }

    /**
     * Updates the OldData with the specified value.
     *
     * @param oldData
     *            the oldData to set.
     */
    public void setOldData(String oldData) {
        this.oldData = oldData;
    }

    /**
     * Updates the Timestamp with the specified value.
     *
     * @param timestamp
     *            the timestamp to set.
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Updates the TransactionId with the specified value.
     *
     * @param transactionId
     *            the transactionId to set.
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Updates the IsUserAdmin with the specified value.
     *
     * @param isUserAdmin
     *            the isUserAdmin to set.
     */
    public void setUserAdmin(boolean isUserAdmin) {
        this.userAdmin = isUserAdmin;
    }

    /**
     * Updates the UserName with the specified value.
     *
     * @param userName
     *            the userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
