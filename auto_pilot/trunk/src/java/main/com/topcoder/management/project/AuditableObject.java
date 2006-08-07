/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.project;

import java.util.Date;

/**
 * This abstract class contains properties for an auditable object such as creation date,
 * modification date, creation user, modification user. Project class extends this class to inherit
 * these properties. Future classes that need audit properties also can extends from this.
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AuditableObject {

    /**
     * Represents the creation user of this instance. Null and empty values are allowed. The value
     * of this field is set by the persistence implementor, not by component users. This variable
     * can be accessed in the corresponding getter/setter method.
     */
    private String creationUser = null;

    /**
     * Represents the modification user of this instance. Null and empty values are allowed. The
     * value of this field is set by the persistence implementor, not by component users. This
     * variable can be accessed in the corresponding getter/setter method.
     */
    private String modificationUser = null;

    /**
     * Represents the creation time stamp of this instance. Null value is allowed. The value of this
     * field is set by the persistence implementor, not by component users. This variable can be
     * accessed in the corresponding getter/setter method.
     */
    private Date creationTimestamp = null;

    /**
     * Represents the time stamp of this instance. Null value is allowed. The value of this field is
     * set by the persistence implementor, not by component users. This variable can be accessed in
     * the corresponding getter/setter method.
     */
    private Date modificationTimestamp = null;

    /**
     * <p>
     * Create a new AuditableObject. This is an empty constructor.
     * </p>
     */
    protected AuditableObject() {
        // your code here
    }

    /**
     * Sets the creation user for this project instance. This method is not supposed to set by the
     * component user. It is used by persistence implementors during loading the project instance
     * from the persistence.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param creationUser The creation user of this project instance.
     */
    public void setCreationUser(String creationUser) {
        // your code here
        this.creationUser = creationUser;
    }

    /**
     * Gets the creation user for this project instance. This method can be used by component user
     * to see who created the project. It is not used by persistence implementors, they use
     * 'operator' parameter instead.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return The creation user of this project instance.
     */
    public String getCreationUser() {
        return this.creationUser;
    }

    /**
     * Sets the creation time stamp for this project instance. This method is not supposed to set by
     * the component user. It is used by persistence implementors during loading the project
     * instance from the persistence.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param creationTimestamp The creation time stamp of this project instance.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        // your code here
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Gets the creation time stamp for this project instance. This method can be used by component
     * user to see who created the project. It is not used by persistence implementors, they use the
     * system time when the project is being updated instead.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return The creation time stamp of this project instance.
     */
    public Date getCreationTimestamp() {
        return this.creationTimestamp;
    }

    /**
     * Sets the modification user for this project instance. This method is not supposed to set by
     * the component user. It is used by persistence implementors during loading the project
     * instance from the persistence.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param modificationUser The modification user of this project instance.
     */
    public void setModificationUser(String modificationUser) {
        // your code here
        this.modificationUser = modificationUser;
    }

    /**
     * Gets the modification user for this project instance. This method can be used by component
     * user to see who created the project. It is not used by persistence implementors, they use
     * 'operator' parameter instead.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return The modification user of this project instance.
     */
    public String getModificationUser() {
        return this.modificationUser;
    }

    /**
     * Sets the modification time stamp for this project instance. This method is not supposed to
     * set by the component user. It is used by persistence implementors during loading the project
     * instance from the persistence.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Set the corresponding member variables.</li>
     * </ul>
     * @param modificationTimestamp The modification time stamp of this project instance.
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        // your code here
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * Gets the modification time stamp for this project instance. This method can be used by
     * component user to see who created the project. It is not used by persistence implementors,
     * they use the system time when the project is being updated instead.
     * <p>
     * Implementation notes:
     * </p>
     * <ul>
     * <li>Return the corresponding member variables.</li>
     * </ul>
     * @return The modification time stamp of this project instance.
     */
    public Date getModificationTimestamp() {
        return this.modificationTimestamp;
    }
}
