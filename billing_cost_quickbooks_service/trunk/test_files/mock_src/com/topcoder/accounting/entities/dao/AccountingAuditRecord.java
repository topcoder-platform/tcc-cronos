package com.topcoder.accounting.entities.dao;

import java.util.*;
import java.lang.*;

/**
 * #### Purpose This class represents a record of a single audit This class provides a method that serializes the entity
 * contents into a JSON string. #### Thread Safety This class is mutable and not thread safe
 */
public class AccountingAuditRecord extends IdentifiableEntity {
    /**
     * #### Purpose Represents the name of the audited user action. #### Usage it is managed with a getter and setter
     * #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private String action;
    /**
     * #### Purpose Represents the username of the User who performed the action. #### Usage it is managed with a getter
     * and setter #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private String userName;
    /**
     * #### Purpose Represents the timestamp of the auditing moment #### Usage it is managed with a getter and setter
     * #### Legal Values It may have any value #### Mutability It is fully mutable
     */
    private Date timestamp;

    /**
     * #### Purpose Empty constructor
     */
    public AccountingAuditRecord() {
    }

    /**
     * <p>
     * Getter method for the action.
     * </p>
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * <p>
     * Setter method for the action.
     * </p>
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * <p>
     * Getter method for the userName.
     * </p>
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>
     * Setter method for the userName.
     * </p>
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * <p>
     * Getter method for the timestamp.
     * </p>
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * <p>
     * Setter method for the timestamp.
     * </p>
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * #### Purpose Provides the JSON representation of the contents of this entity. #### Parameters return - the JSON
     * representation of the contents of this entity. #### Implementation Notes See CS 1.3.2 for details
     * @param Return
     * @return
     */
    public String toJSONString() {
        throw new UnsupportedOperationException("Not implemented in mock.");
    }
}
