/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

import java.util.Date;


/**
 * <p>
 * This class represents common fields to all enities. All entities have a description, the name of the user that
 * created this record and the the date of creation, and the name of the user that last modified this record and the
 * the date of this modification. This class extends DataObject to provide the common primary Id field. Since this
 * class does not actually represent an entity, it is declared abstract. All Time Entry entities extend this class.
 * </p>
 *
 * <p>
 * Note on the user and date fields: The DAO methods <code>create</code> and <code>update</code> also contain a
 * <b>user</b> parameter that can be used. The <code>BaseDAO</code> provides the flexibility for deciding which
 * <b>user</b> is used in those methods. All three concrete <code>DAO</code> implementations use the <b>user</b>
 * parameter passed in the method call when creating and updating the user fields. Similarly, they use the date
 * of the action instead of the date contained in this class. As such, the fields are just used to hold information,
 * and are not used when updating and creating a record.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseDataObject extends DataObject {
    /**
     * <p>
     * Represents the description of an entity.
     * </p>
     * <p>
     * If the size is actually longer than 64, then in the <code>create</code> and <code>update</code> methods in
     * all 3 <code>DAO</code> implementations will truncate this to 64.
     * </p>
     */
    private String description = null;

    /**
     * <p>
     * Represents the user that created this record.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * Represents the date when the record was created.
     * </p>
     */
    private Date creationDate = null;

    /**
     * <p>
     * Represents the user that last modified this record.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * Represents the date when this record was last modified.
     * </p>
     */
    private Date modificationDate = null;

    /**
     * <p>
     * Creates a new instance of <code>BaseDataObject</code> class. This protected constructor is used by its
     * sub-class.
     * </p>
     */
    protected BaseDataObject() {
        // do nothing here
    }

    /**
     * <p>
     * Gets the description of this data object.
     * </p>
     *
     * @return the description of this data object.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Sets the description. Size schould be limited to length of 64 but is not enforced here.
     * </p>
     *
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * Gets the user who created this data object.
     * </p>
     *
     * @return the user who created this data object.
     */
    public String getCreationUser() {
        return this.creationUser;
    }

    /**
     * <p>
     * Sets the creation user. Please note that the DAO will ignore this value when creating/updating this field.
     * Please use the user parameter in the DAO call instead. As such, no restrictions are placed on this value.
     * </p>
     *
     * @param creationUser the user who created this data object.
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Gets the time when this data object is created.
     * </p>
     *
     * @return the time when this data object is created.
     */
    public Date getCreationDate() {
        if (this.creationDate == null) {
            return null;
        }
        return new Date(this.creationDate.getTime());
    }

    /**
     * <p>
     * Sets the creation date. Please note that the DAO will ignore this value when creating/updating this field. The
     * current time of creation will be set automatically by the DAO at that time. As such, no restrictions are placed
     * on this value.
     * </p>
     *
     * @param creationDate the time when this data object is created.
     */
    public void setCreationDate(Date creationDate) {
        if (creationDate == null) {
            this.creationDate = null;
        } else {
            this.creationDate = new Date(creationDate.getTime());
        }
    }

    /**
     * <p>
     * Gets the last user who modified this data object.
     * </p>
     *
     * @return the last user who modified this data object.
     */
    public String getModificationUser() {
        return this.modificationUser;
    }

    /**
     * <p>
     * Sets the modification user. Please note that the DAO will ignore this value when creating/updating this field.
     * Please use the user parameter in the DAO call instead. As such, no restrictions are placed on this value.
     * </p>
     *
     * @param modificationUser the last user who modified this data object.
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Gets the last time when this data object is modified.
     * </p>
     *
     * @return the last time when this data object is modified.
     */
    public Date getModificationDate() {
        if (this.modificationDate == null) {
            return null;
        }
        return new Date(this.modificationDate.getTime());
    }

    /**
     * <p>
     * Sets the modification date. Please note that the DAO will ignore this value when creating/updating this field.
     * The current time of modification will be set automatically by the DAO at that time. As such, no restrictions
     * are placed on this value.
     * </p>
     *
     * @param modificationDate the last time when this data object is modified.
     */
    public void setModificationDate(Date modificationDate) {
        if (modificationDate == null) {
            this.modificationDate = null;
        } else {
            this.modificationDate = new Date(modificationDate.getTime());
        }
    }
}
