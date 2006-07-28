package com.topcoder.management.deliverable;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * The AuditedDeliverableStructure is the base class for the modeling classes in this component. It holds the
 * information about when the structure was created and updated. This class simply holds the four data fields
 * needed for this auditing information (as well as the id of the item) and exposes both getters and setters for
 * these fields.
 * </p>
 *
 * <p>
 * The only thing to take note of when developing this class is that the setId method throws the
 * IdAlreadySetException.
 * </p>
 *
 * <p>
 * This class is highly mutable. All fields can be changed.
 * </p>
 *
 *
 */
public abstract class AuditedDeliverableStructure implements Serializable {

    /**
     * id: The id of the deliverable structure. This field is set in the constructor and is mutable. The value must
     * always be >0 or UNSET_ID. The default value is UNSET_ID. Once set to a value besides UNSET_ID, the id can
     * not be set to another value. This allows the class (subclasses actually, since this class is abstract) to
     * have a Java Bean API but still have an essentially unchangeable id. This field is set in the constructor and
     * setId method and is retrieved through the getId method.
     *
     */
    private long id;

    /**
     * UNSET_ID: The value that the id field will have (and that the getId method will return) when the id field
     * has not been set in the constructor or through the setId method. This field is public, static, and final.
     *
     */
    public static final long UNSET_ID = -1;

    /**
     * creationUser: The name of the user that was responsible for creating the AuditedDeliverableStructure. This
     * field is mutable, and can be null or non-null. A null value indicates that this field has not been set in
     * the through the setCreationUser method. When non-null, no value is considered invalid for this field - the
     * user may be the empty string, all whitespace, etc. Although most applications will probably not change the
     * creation user once it is set, this class does allow this field to be changed. This field is set through the
     * setCreationUser method and retrieved through the getCreationUser method.
     *
     */
    private String creationUser = null;

    /**
     * creationTimestamp: The date/time that the AuditedDeliverableStructure was created. This field is mutable,
     * and can be null or non-null. A null value indicates that this field has not been through the
     * setCreationTimestamp method. No value is considered invalid for this field. Although most applications will
     * probably not change the creation timestamp once it is set, this class does allow this field to be changed.
     * This field is set through the setCreationTimestamp method and retrieved through the getCreationTimestamp
     * method.
     *
     */
    private Date creationTimestamp = null;

    /**
     * modificationUser: The name of the user that was responsible for the last modification to the
     * AuditedDeliverableStructure. This field is mutable, and can be null or non-null. A null value indicates that
     * this field has not been set through the setModificationUser method. When non-null, no value is considered
     * invalid for this field - the user may be the empty string, all whitespace, etc. This field is set through
     * the setModificationUser method and retrieved through the getModificationUser method. This field is not
     * automatically updated when changes are made to this class.
     *
     */
    private String modificationUser = null;

    /**
     * modificationTimestamp: The date/time that the AuditedDeliverableStructure was last modified. This field is
     * initialized to null, is mutable, and can be null or non-null. A null value indicates that this field has not
     * been set through the setModificationTimestamp method. No value is considered invalid for this field. This
     * field is set through the setModificationTimestamp method and retrieved through the getModificationTimestamp
     * method. This field is not automatically updated when any setXXX method is called.
     *
     */
    private java.util.Date modificationTimestamp = null;

    /**
     * AuditedDeliverableStructure constructor: Creates a new AuditedDeliverableStructure, setting the id field to
     * UNSET_ID.
     *
     */
    protected AuditedDeliverableStructure() {
        this.id = UNSET_ID;
    }

    /**
     * AuditedDeliverableStructure constructor: Creates a new AuditedDeliverableStructure. Sets the id field to the
     * given value.
     *
     *
     * @param id The id of the structure
     * @throws IllegalArgumentException If id is <= 0
     */
    protected AuditedDeliverableStructure(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }

        this.id = id;
    }

    /**
     * setId: Sets the id of the structure. The setId method only allows the id to be set once (if it was not set
     * in the constructor). After that the id value is locked in.
     *
     *
     * @param id The id for the structure
     * @throws IllegalArgumentException If id is <= 0
     * @throws IdAlreadySetException If the id has already been set (i.e. the id field is not UNSET_ID)
     */
    public void setId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }

        this.id = id;
    }

    /**
     * getId: Gets the id of the structure. The return is either UNSET_ID_VALUE or > 0.
     *
     *
     * @return The id of the structure
     */
    public long getId() {
        return id;
    }

    /**
     * setCreationUser: Sets the user that is responsible for the creation of the AuditedDeliverableStructure. The
     * value can be null or non-null, and no value is considered invalid.
     *
     *
     * @param creationUser The user that created the AuditedDeliverableStructure.
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * getCreationUser: Gets the user responsible for creating the AuditedDeliverableStructure. The return may be
     * null which indicates that the creation user has not been set.
     *
     *
     * @return The user that created the AuditedDeliverableStructure
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * setCreationTimestamp: Sets the date/time at which the AuditedDeliverableStructure was created. This value
     * may be null or non-null and no value is considered invalid.
     *
     *
     * @param creationTimestamp The date/time the AuditedDeliverableStructure was created.
     */
    public void setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * getCreationTimestamp: Gets the date/time the AuditedDeliverableStructure was created. The return may be
     * null, indicating that the creation date/time has not been set.
     *
     *
     * @return The date/time the AuditedDeliverableStructure was created.
     */
    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * setModificationUser: Sets the user that is responsible for the last modification to the the
     * AuditedDeliverableStructure. The value can be null or non-null, and no value is considered invalid.
     *
     *
     * @param modificationUser The user responsible for the last AuditedDeliverableStructure modification, can be
     *        null
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * getModificationUser: Gets the user responsible for last modifying the AuditedDeliverableStructure. The
     * return may be null which indicates that the modification user has not been set.
     *
     *
     * @return The user that last modified the AuditedDeliverableStructure
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * setModificationTimestamp: Sets the date/time at which the AuditedDeliverableStructure was last modified.
     * This value may be null or non-null and no value is considered invalid.
     *
     *
     * @param modificationTimestamp The date/time the AuditedDeliverableStructure was last modified
     */
    public void setModificationTimestamp(Date modificationTimestamp) {
        this.modificationTimestamp = modificationTimestamp;
    }

    /**
     * getModificationTimestamp: Gets the date/time the AuditedDeliverableStructure was last modified. The return
     * may be null, indicating that the modification date/time has not been set.
     *
     *
     * @return The date/time the AuditedDeliverableStructure was last modified
     */
    public Date getModificationTimestamp() {
        return modificationTimestamp;
    }

    /**
     * isValidToPersist: Tells whether all the required fields of this structure have values set. This method
     * returns true only all of the following are true: creationUser is not null creationTimestamp is not null
     * modificationUser is not null modificationTimestamp is not null
     *
     *
     * @return True if all fields required for persistence are present
     */
    protected boolean isValidToPersist() {
        return (creationUser != null) && (creationTimestamp != null) && (modificationTimestamp != null)
                && (modificationUser != null);
    }
}
