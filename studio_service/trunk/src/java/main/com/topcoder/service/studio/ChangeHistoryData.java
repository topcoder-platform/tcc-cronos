/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio;

import java.io.Serializable;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * It is the DTO class which is used to transfer change history data. The
 * information can be null or can be empty, therefore this check is not present
 * in the setters. It is related with the Prize entity.
 * </p>
 *
 * <p>
 * This class is not thread safe because it's highly mutable
 * </p>
 *
 * @author Ghosta, superZZ
 * @version 1.0
 */
public class ChangeHistoryData implements Serializable {
    /**
     * Default serial version id.
     */
    private static final long serialVersionUID = 2547409770660879115L;

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
    private XMLGregorianCalendar timestamp;

    /**
     * Represents the UserName.
     */
    private String userName;

    /**
     * Represents the IsUserAdmin.
     */
    private boolean isUserAdmin;

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
     * Updates the ContestId with the specified value.
     *
     * @param contestId the contestId to set.
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Returns the contestId.
     *
     * @return the contestId.
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Updates the TransactionId with the specified value.
     *
     * @param transactionId the transactionId to set.
     */
    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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
     * Updates the Timestamp with the specified value.
     *
     * @param timestamp the timestamp to set.
     */
    public void setTimestamp(XMLGregorianCalendar timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the timestamp.
     *
     * @return the timestamp.
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Updates the UserName with the specified value.
     *
     * @param userName the userName to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * Updates the IsUserAdmin with the specified value.
     *
     * @param isUserAdmin the isUserAdmin to set.
     */
    public void setUserAdmin(boolean isUserAdmin) {
        this.isUserAdmin = isUserAdmin;
    }

    /**
     * Returns the isUserAdmin.
     *
     * @return the isUserAdmin.
     */
    public boolean isUserAdmin() {
        return isUserAdmin;
    }

    /**
     * Updates the FieldName with the specified value.
     *
     * @param fieldName the fieldName to set.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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
     * Updates the OldData with the specified value.
     *
     * @param oldData the oldData to set.
     */
    public void setOldData(String oldData) {
        this.oldData = oldData;
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
     * Updates the NewData with the specified value.
     *
     * @param newData the newData to set.
     */
    public void setNewData(String newData) {
        this.newData = newData;
    }

    /**
     * Returns the newData.
     *
     * @return the newData.
     */
    public String getNewData() {
        return newData;
    }
}
