package com.topcoder.timetracker.audit;

/**
 * AuditDetail is a simple bean class for the storage of information about a single column change. It stores its unique
 * ID, as well as the name of the column which was changed, and the value in the column before and after the change.
 * As a bean class, it provides a no-argument constructor, as well as setXXX/getXXX methods named for each member. By
 * design, this class performs no error checking on parameters in setXXX methods, it is left to the handling
 * application to define what constraints are put on the bean's members. Additionally, there is no thread-safety
 * protection placed on the members of this bean - however, in the absence of static members, it is safe to use in its
 * intended web environment.
 */
public class AuditDetail {
    /** The identification number for this detail. Initially -1, any valid long value is allowed. */
    private long id = -1;

    /** The new value stored on the detail. Initially null, any String or null is allowed. */
    private String newValue = null;

    /** The old value stored within the detail. Initially null, any String or null is allowed. */
    private String oldValue = null;

    /**
     * The name of the column that changed from the old to the new Value. Initially null, any String or null is
     * allowed.
     */
    private String columnName = null;

    /**
     * Flag indicating whether this detail has been successfully persisted. This is initially false, set to true when
     * persisted or loaded from persistence.
     */
    private boolean persisted = false;

    /**
     * No-arg constructor for the Audit Details bean.
     */
    public AuditDetail() {
        // your code here
    }

    /**
     * Returns the current ID of the audit detail.
     *
     * @return this.id;
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the audit detail.
     *
     * @param id the new id to use
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the new String value for the detail.
     *
     * @return this.newValue;
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets the new value to the parameter.
     *
     * @param value The new value to use
     */
    public void setNewValue(String value) {
        newValue = value;
    }

    /**
     * Returns the old String value for the detail.
     *
     * @return this.oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Sets the old value to the parameter.
     *
     * @param value the value to use for oldValue.
     */
    public void setOldValue(String value) {
        oldValue = value;
    }

    /**
     * Returns the name of the column the detail relates to
     *
     * @return this.columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Sets the column name to the parameter.
     *
     * @param name The new column name to use
     */
    public void setColumnName(String name) {
        columnName = name;
    }

    /**
     * Returns whether or not the header has been persisted.
     *
     * @return this.ispersisted;
     */
    public boolean isPersisted() {
        return persisted;
    }

    /**
     * Sets the persistence status of the header.
     *
     * @param persisted The new value to use for the flag
     */
    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }
}
