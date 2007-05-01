/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.database.statustracker;

import java.util.Date;

/**
 * <p>
 * This class stores the status of a particular entity instance in persistent storage. It is the
 * core data object of the Status Tracker component.  It directly corresponds to records
 * in persistent storage.
 * </p>
 *
 * <p>
 * The corresponding table defined for this class is <b>entity_status</b>.
 * </p>
 *
 * <p>
 * <b>Thread Safety:</b> This class is mutable, the status instance in this class is mutable so
 * this class is not thread-safe. Users are required to use this class in a thread-safe way.
 * </p>
 *
 * @author dplass, TCSDEVELOPER
 * @version 1.0
 * @see Status
 * @see Entity
 * @see EntityKey
 */
public class EntityStatus {
    /**
     * <p>
     * This variable represents the Entity instance whose status this object describes.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null.
     * </p>
     */
    private final EntityKey key;

    /**
     * <p>
     * This variable represents the status that is described in this object.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null.
     * </p>
     *
     * <p>
     * Note, this status does not necessarily represent the CURRENT status of the given entity instance,
     * as this record may represent a previous historical status of the instance.
     * </p>
     */
    private final Status status;

    /**
     * <p>
     * This variable represents the username of the user who last set the status of this entity instance.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null or empty.
     * </p>
     */
    private final String lastUpdatedByUserName;

    /**
     * <p>
     * This variable indicates if the status in this object represents the current status of the given
     * Entity instance. If this value is true, inactivationDate must be null, and if this value is
     * false, inactivationDate must not be null.
     * </p>
     *
     * <p>
     * Set in one of the constructors and accessed by isCurrent() method.
     * </p>
     */
    private final boolean current;

    /**
     * <p>
     * This variable represents the date that this status was set on this entity instance in persistent storage.
     * </p>
     *
     * <p>
     * It is set in the constructor and will never be null.
     * </p>
     */
    private final Date creationDate;

    /**
     * <p>
     * The date that the status in this object was no longer the current status for the entity
     * instance. Will be null if 'current' is true, and will not be null if 'current' is false.
     * </p>
     *
     * <p>
     * It is only set in the constructor, can be null if the current flag is true.
     * </p>
     */
    private final Date inactivationDate;

    /**
     * <p>
     * Construct this object with the given parameters.
     * </p>
     *
     * <p>
     * This constructor is used when the status does not represent the current status of the given entity instance.
     * </p>
     *
     * @param key the entity instance whose status is stored herein
     * @param status the status that this entity instance had between the creationDate and the
     * inactivationDate
     * @param creationDate the date that this entity instance first had this status
     * @param lastUpdatedByUserName the username of the user who set the status on the given creationDate
     * @param inactivationDate the date that this status ceased to be the current status for this
     * entity.
     *
     * @throws IllegalArgumentException if any argument is null, or if lastUpdatedByUserName is
     * empty after trim, or inactivationDate is less than creationDate, or if status does not represent
     * a valid status for the Entity type within 'key'.
     */
    public EntityStatus(EntityKey key, Status status, Date creationDate, String lastUpdatedByUserName,
        Date inactivationDate) {
        checkCommonArguments(key, status, creationDate, lastUpdatedByUserName);
        Util.checkNull(inactivationDate, "inactivationDate");
        // the inactivationDate should after the creationDate
        if (inactivationDate.before(creationDate)) {
            throw new IllegalArgumentException("The inactivationDate is less than creationDate.");
        }

        this.key = key;
        this.status = status;
        this.creationDate = creationDate;
        this.lastUpdatedByUserName = lastUpdatedByUserName;
        this.current = false;
        this.inactivationDate = inactivationDate;
    }

    /**
     * <p>
     * Construct this object with the given parameters. This constructor is used when the given
     * status is the current status of this entity instance.
     * </p>
     *
     * @param key the entity instance whose status is stored herein
     * @param status the status that this entity instance currently has.
     * @param creationDate the date that this entity instance first had this status
     * @param lastUpdatedByUserName the username of the user who set the status on the given creationDate
     *
     * @throws IllegalArgumentException if any argument is null, or if lastUpdatedByUserName is empty after
     * trim, or if status does not represent a valid status for the Entity type within 'key'.
     */
    public EntityStatus(EntityKey key, Status status, Date creationDate, String lastUpdatedByUserName) {
        checkCommonArguments(key, status, creationDate, lastUpdatedByUserName);

        this.key = key;
        this.status = status;
        this.creationDate = creationDate;
        this.lastUpdatedByUserName = lastUpdatedByUserName;
        current = true;
        inactivationDate = null;
    }

    /**
     * <p>
     * Return the date this entity first had this status.
     * </p>
     *
     * @return Returns the creation date as set in the constructor. Will never be null.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Returns true if this status represents the current status of the entity instance stored in
     * this object, false otherwise.
     * </p>
     *
     * @return Returns the 'current' flag as determined by the constructor.
     */
    public boolean isCurrent() {
        return current;
    }

    /**
     * <p>
     * Return the date/time this status ceased to be the current status for the entity instance
     * stored in this object, or null if the current status is the status in this object.
     * </p>
     *
     * @return Returns the inactivation date, if set in the constructor. May be null.
     */
    public Date getInactivationDate() {
        return inactivationDate;
    }

    /**
     * <p>
     * Return the entity instance of this object.
     * </p>
     *
     * @return Returns the entity instance of this object. Will never be null.
     */
    public EntityKey getKey() {
        return key;
    }

    /**
     * <p>
     * Returns the username of the user who last updated the status of this Entity instance.
     * </p>
     *
     * @return Returns the username that updates this entity status. Will never be null or empty.
     */
    public String getLastUpdatedByUserName() {
        return lastUpdatedByUserName;
    }

    /**
     * <p>
     * Returns the status of the entity instance between the creationDate of this object and either
     * now (if current) or the inactivationDate(if not current).
     * </p>
     *
     * @return Returns the status instance for this entity instance. Will never be null.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>
     * This method performs argument checking for the common arguments in the two constructors.
     * </p>
     *
     * @param key the entity instance whose status is stored herein
     * @param status the status that this entity instance currently has.
     * @param creationDate the date that this entity instance first had this status
     * @param lastUpdatedByUserName the username of the user who set the status on the given creationDate
     *
     * @throws IllegalArgumentException if any argument is null, or if lastUpdatedByUserName is empty after
     * trim, or if status does not represent a valid status for the Entity type within 'key'.
     */
    private void checkCommonArguments(EntityKey key, Status status, Date creationDate, String lastUpdatedByUserName) {
        Util.checkNull(creationDate, "creationDate");
        Util.checkString(lastUpdatedByUserName, "lastUpdateByUserName");
    }
}
