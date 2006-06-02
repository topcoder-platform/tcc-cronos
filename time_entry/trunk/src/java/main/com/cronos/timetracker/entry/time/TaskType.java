/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

/**
 * <p>
 * This class represents the Task Type entity. Extends <code>BaseDataObject</code>. Uses its primaryId member for
 * holding the <code>TaskTypesID</code> defined in the database. This entity is used in <code>TaskTypeDAO</code>.
 * As such, when the <code>DAO</code> creates this record, it will ignore the creation and modification fields and
 * assign its own values. It will also ignore the primary Id value as it will assign its own. Simlarly, the <code>
 * DAO</code> will ignore all of these fields when updating. The creation fields and the primary fields are never
 * updated, and the <code>DAO</code> will assign its own values to the modification fields. As such, from the
 * perspective of the application using this entity, these fields can be considered to be effectively read-only
 * (as assigning fresh values to these fields has no impact in the <code>DAO</code>, and thus on the
 * database. Please see <code>TaskTypeDAO</code> for more details.
 * </p>
 *
 * <p>
 * Changes in version 2.0: add active and company id field.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @author arylio
 *
 * @version 2.0
 * @since 1.0
 *
 */
public class TaskType extends BaseDataObject {
    /**
     * <p>
     * Represents the task type is active or not.
     * Intialize to zero, accessed by getActive/setActive.
     * </p>
     *
     * @since 2.0
     */
    private boolean active = false;

    /**
     * <p>
     * Represents the company id of the task type.
     * Intialize to zero, accessed by getCompanyId/setCompanyId.
     * </p>
     *
     * @since 2.0
     */
    private int companyId = 0;

    /**
     * <p>
     * Sole constructor.
     * </p>
     */
    public TaskType() {
        // empty here
    }

    /**
     * <p>
     * Gets the active status.
     * </p>
     *
     * @return the active to this reject reason.
     * @since 2.0
     */
    public boolean isActive() {
        return active;
    }

    /**
     * <p>
     * Set active to new value, no restriction.
     * </p>
     *
     * @param active the active to set.
     * @since 2.0
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * <p>
     * Get the company id.
     * </p>
     *
     * @return the comapny id.
     * @since 2.0
     */
    public int getCompanyId() {
        return this.companyId;
    }

    /**
     * <p>
     * Set the company id to new value
     * </p>
     *
     * @param companyId the new company id.
     */
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
