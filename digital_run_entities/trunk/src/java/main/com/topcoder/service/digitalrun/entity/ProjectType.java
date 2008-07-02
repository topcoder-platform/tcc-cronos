/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service.digitalrun.entity;

/**
 * <p>
 * The <code>ProjectType</code> entity. Plus the attributes defined in its base entity, it holds the attributes
 * name, creation user, modification user.
 * </p>
 *
 * <p>
 * Thread Safety: It's mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 1.0
 */
public class ProjectType extends BaseEntity {

    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = 4881411752310606640L;

    /**
     * Represents the name attribute of the ProjectType entity. It should be non-null after set.
     */
    private String name;

    /**
     * Represents the creation user attribute of the ProjectType entity. It should be non-null and non-empty after
     * set.
     */
    private String creationUser;

    /**
     * Represents the modification user attribute of the ProjectType entity. It should be non-null and non-empty
     * after set.
     */
    private String modificationUser;

    /**
     * Creates the instance. Empty constructor.
     */
    public ProjectType() {
        // empty
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name
     *            the name
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or empty
     */
    public void setName(String name) {
        Helper.checkString(name, "name");
        this.name = name;
    }

    /**
     * Gets creation user.
     *
     * @return the creation user
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Sets creation user.
     *
     * @param creationUser
     *            the creation user
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or empty
     */
    public void setCreationUser(String creationUser) {
        Helper.checkString(creationUser, "creationUser");
        this.creationUser = creationUser;
    }

    /**
     * Gets modification user.
     *
     * @return the modification user
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * Set modification user.
     *
     * @param modificationUser
     *            the modification user
     * @throws IllegalArgumentException
     *             if the argument is <code>null</code> or empty
     */
    public void setModificationUser(String modificationUser) {
        Helper.checkString(modificationUser, "modificationUser");
        this.modificationUser = modificationUser;
    }
}
