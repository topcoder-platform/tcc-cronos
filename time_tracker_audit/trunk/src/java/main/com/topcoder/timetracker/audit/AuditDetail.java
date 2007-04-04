/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.audit;

import java.io.Serializable;


/**
 * <p>
 * This class is a simple bean class for the storage of information about a single column change. It stores its unique
 * ID, as well as the name of the column which was changed, and the value in the column before and after the change.
 * </p>
 *
 * <p>
 * As a bean class, it provides a no-argument constructor, as well as setXXX/getXXX methods named for each member. This
 * class performs no error checking on parameters in setXXX methods, it is left to the handling application to define
 * what constraints are put on the bean's members.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>There is no thread-safety protection placed on the members of this bean - however, in the
 * absense of static members, it is safe to use in its intended web environment.
 * </p>
 *
 * @author sql_lall, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class AuditDetail implements Serializable {
    /**
     * <p>
     * Represents the identification number for this detail.
     * </p>
     *
     * <p>
     * Initially -1, any valid long value is allowed.
     * </p>
     */
    private long id = -1;

    /**
     * <p>
     * Represents the new value stored on the detail.
     * </p>
     *
     * <p>
     * Initially null, any String or null is allowed.
     * </p>
     */
    private String newValue = null;

    /**
     * <p>
     * Represents the old value stored on the detail.
     * </p>
     *
     * <p>
     * Initially null, any String or null is allowed.
     * </p>
     */
    private String oldValue = null;

    /**
     * <p>
     * Represents the name of the column that changed from the old to the new Value.
     * </p>
     *
     * <p>
     * Initially null, any String or null is allowed.
     * </p>
     */
    private String columnName = null;

    /**
     * <p>
     * Represents the flag indicating whether this detail has been successfully persisted.
     * </p>
     *
     * <p>
     * This is initially false, set to true when persisted or loaded from persistence.
     * </p>
     */
    private boolean persisted = false;

    /**
     * <p>
     * No-arg constructor for the Audit Details bean.
     * </p>
     */
    public AuditDetail() {
    }

    /**
     * <p>
     * Gets the identification number for this detail.
     * </p>
     *
     * @return the identification number for this detail.
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the identification number for this detail.
     * </p>
     *
     * @param id the new identification number for this detail.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the new value stored on the detail.
     * </p>
     *
     * @return the new value stored on the detail.
     */
    public String getNewValue() {
        return this.newValue;
    }

    /**
     * <p>
     * Sets the new value stored on the detail.
     * </p>
     *
     * @param value the new value stored on the detail.
     */
    public void setNewValue(String value) {
        this.newValue = value;
    }

    /**
     * <p>
     * Gets the old value stored on the detail.
     * </p>
     *
     * @return the old value stored on the detail.
     */
    public String getOldValue() {
        return this.oldValue;
    }

    /**
     * <p>
     * Sets the old value stored on the detail.
     * </p>
     *
     * @param value the old value stored on the detail.
     */
    public void setOldValue(String value) {
        this.oldValue = value;
    }

    /**
     * <p>
     * Gets the name of the column that changed from the old to the new Value.
     * </p>
     *
     * @return the name of the column that changed from the old to the new Value.
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * <p>
     * Sets the name of the column that changed from the old to the new Value.
     * </p>
     *
     * @param name the name of the column that changed from the old to the new Value.
     */
    public void setColumnName(String name) {
        this.columnName = name;
    }

    /**
     * <p>
     * Gets whether or not the header has been persisted.
     * </p>
     *
     * @return whether or not the header has been persisted.
     */
    public boolean isPersisted() {
        return this.persisted;
    }

    /**
     * <p>
     * Sets whether or not the header has been persisted.
     * </p>
     *
     * @param persisted whether or not the header has been persisted.
     */
    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}
