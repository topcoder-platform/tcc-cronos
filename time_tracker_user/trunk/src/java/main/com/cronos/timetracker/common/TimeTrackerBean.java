/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.common;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * Base class to represent all entities of the Time Tracker User v2.0 component. It contains all the properties
 * which are common to these entities. It is also Serializable to support network transfer, and state persistence.
 * </p>
 * <p>
 * Thread Safety: - This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 * instance.
 * </p>
 *
 * @author ShindouHikaru
 * @author kr00tki
 * @version 2.0
 */
public abstract class TimeTrackerBean implements Serializable {

    /**
     * <p>
     * This represents the date when this entity was initially defined within the Time Tracker data store. It may
     * be null when a blank bean is defined for initial data population, but it must always have a value when the
     * entity is retrieved from the data store.
     * </p>
     * <p>
     * Initialized In: setCreationDate
     * </p>
     * <p>
     * Modified In: setCreationDate
     * </p>
     * <p>
     * Accessed In: getCreationDate
     * </p>
     *
     */
    private Date creationDate;

    /**
     * <p>
     * This represents the date when any of the fields of this entity was modified within Time Tracker data store.
     * It may be null when a blank bean is defined for initial data population, but it must always have a value
     * when the entity is retrieved from the data store. If the entity has not been modified since its initial
     * definition, then the modificationDate would be equal to creationDate.
     * </p>
     * <p>
     * Initialized In: setModificationDate
     * </p>
     * <p>
     * Modified In: setModificationDate
     * </p>
     * <p>
     * Accessed In: getModificationDate
     * </p>
     *
     */
    private Date modificationDate;

    /**
     * <p>
     * This represents the user that initially defined this entity within the Time Tracker data store. It may be
     * null when a blank bean is defined for initial data population, but it must always have a value when the
     * entity is retrieved from the data store.
     * </p>
     * <p>
     * Initialized In: setCreationUser
     * </p>
     * <p>
     * Modified In: setCreationUser
     * </p>
     * <p>
     * Accessed In: getCreationUser
     * </p>
     *
     */
    private String creationUser;

    /**
     * <p>
     * This represents the user that modified this entity within the Time Tracker data store. It may be null when a
     * blank bean is defined for initial data population, but it must always have a value when the entity is
     * retrieved from the data store. If the entity has not been modified since its initial definition, then the
     * modificationUser would be equal to creationUser.
     * </p>
     * <p>
     * Initialized In: setModificationUser
     * </p>
     * <p>
     * Modified In: setModificationUser
     * </p>
     * <p>
     * Accessed In: getModificationUser
     * </p>
     *
     */
    private String modificationUser;

    /**
     * <p>
     * A unique identifier that distinguishes each entity within the Time Tracker component. An id is automatically
     * assigned by the DAO when the entity is initially placed into the data store. When the entity has not yet
     * been placed in the data store, then it will not have/need an id.
     * </p>
     * <p>
     * Initialized In: setId
     * </p>
     * <p>
     * Modified In: setId
     * </p>
     * <p>
     * Accessed In: getId
     * </p>
     *
     */
    private long id;

    /**
     * <p>
     * This variable represents whether any of the additional bean data has changed. This variable is used to
     * assist in determining whether the modification date and modification user for this bean needs to be modified
     * during persistence. Only implementors of the DAO, or subclasses of TimeTrackerBean need to concern
     * themselves with this.
     * </p>
     * <p>
     * Initialized In: Constructor
     * </p>
     * <p>
     * Modified In: setChanged
     * </p>
     * <p>
     * Accessed In: isChanged
     * </p>
     *
     *
     */
    private boolean changed = false;

    /**
     * <p>
     * Default constructor.
     * </p>
     *
     */
    protected TimeTrackerBean() {
        // empty
    }

    /**
     * <p>
     * Retrieves the date when this entity was initially defined within the Time Tracker data store. It may be null
     * when a blank bean is defined for initial data population, but it must always have a value when the entity is
     * retrieved from the data store.
     * </p>
     *
     *
     * @return the date when this entity was initially defined within the Time Tracker data store.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * <p>
     * Sets the date when this entity was initially defined within the Time Tracker data store. It may be null when
     * a blank bean is defined for initial data population, but it must always have a value when the entity is
     * retrieved from the data store.
     * </p>
     * <p>
     * Note that it is not recommended for this method to be called by normal code. This method is likely to be
     * called only by the Time Tracker DAO and Manager classes and isn't something that the user of this component
     * should concern themselves with.
     * </p>
     *
     *
     * @param creationDate the date when this entity was initially defined within the Time Tracker data store.
     * @throws IllegalArgumentException if the creationDate is null.
     */
    public void setCreationDate(Date creationDate) {
        Utils.checkNull(creationDate, "creationDate");
        this.creationDate = creationDate;
    }

    /**
     * <p>
     * Retrieves the date when any of the fields of this entity was modified within Time Tracker data store. It may
     * be null when a blank bean is defined for initial data population, but it must always have a value when the
     * entity is retrieved from the data store. If the entity has not been modified since its initial definition,
     * then the modificationDate would be equal to creationDate.
     * </p>
     *
     *
     * @return the date when any of the fields of this entity was modified within Time Tracker data store.
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * <p>
     * Sets the date when any of the fields of this entity was modified within Time Tracker data store. It may be
     * null when a blank bean is defined for initial data population, but it must always have a value when the
     * entity is retrieved from the data store. If the entity has not been modified since its initial definition,
     * then the modificationDate would be equal to creationDate.
     * </p>
     * <p>
     * Note that it is not recommended for this method to be called by normal code. This method is likely to be
     * called only by the Time Tracker DAO and Manager classes and isn't something that the user of this component
     * should concern themselves with
     * </p>
     *
     *
     * @param modificationDate the date when any of the fields of this entity was modified within Time Tracker data
     *        store.
     * @throws IllegalArgumentException if the modificationDate is null.
     */
    public void setModificationDate(Date modificationDate) {
        Utils.checkNull(modificationDate, "modificationDate");
        this.modificationDate = modificationDate;
    }

    /**
     * <p>
     * Retrieves the user that initially defined this entity within the Time Tracker data store. It may be null
     * when a blank bean is defined for initial data population, but it must always have a value when the entity is
     * retrieved from the data store.
     * </p>
     *
     *
     * @return the user that initially defined this entity within the Time Tracker data store.
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * <p>
     * This represents the user that initially defined this entity within the Time Tracker data store. It may be
     * null when a blank bean is defined for initial data population, but it must always have a value when the
     * entity is retrieved from the data store.
     * </p>
     * <p>
     * Note that it is not recommended for this method to be called by normal code. This method is likely to be
     * called only by the Time Tracker DAO and Manager classes and isn't something that the user of this component
     * should concern themselves with
     * </p>
     *
     *
     *
     * @param creationUser the user that initially defined this entity within the Time Tracker data store.
     * @exception IllegalArgumentException if the creationUser is null.
     */
    public void setCreationUser(String creationUser) {
        Utils.checkNull(creationUser, "creationUser");
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Retrieves the user that most recently modified this entity within the Time Tracker data store. It may be
     * null when a blank bean is defined for initial data population, but it must always have a value when the
     * entity is retrieved from the data store. If the entity has not been modified since its initial definition,
     * then the modificationUser would be equal to creationUser.
     * </p>
     *
     *
     * @return the user that most recently modified this entity within the Time Tracker data store.
     */
    public String getModificationUser() {
        return modificationUser;
    }

    /**
     * <p>
     * Sets the user that most recently modified this entity within the Time Tracker data store. It may be null
     * when a blank bean is defined for initial data population, but it must always have a value when the entity is
     * retrieved from the data store. If the entity has not been modified since its initial definition, then the
     * modificationUser would be equal to creationUser.
     * </p>
     * <p>
     * Note that it is not recommended for this method to be called by normal code. This method is likely to be
     * called only by the Time Tracker DAO and Manager classes and isn't something that the user of this component
     * should concern themselves with
     * </p>
     *
     *
     *
     * @param modificationUser the user that most recently modified this entity within the Time Tracker data store.
     * @throws IllegalArgumentException if the modificationUser is null.
     */
    public void setModificationUser(String modificationUser) {
        Utils.checkNull(modificationUser, "modificationUser");
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Retrieves the unique identifier that distinguishes each entity within the Time Tracker component. An id is
     * automatically assigned by the DAO when the entity is initially placed into the data store. When the entity
     * has not yet been placed in the data store, then it will not have/need an id.
     * </p>
     *
     *
     *
     * @return the unique identifier that distinguishes each entity within the Time Tracker component.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the unique identifier that distinguishes each entity within the Time Tracker component. An id is
     * automatically assigned by the DAO when the entity is initially placed into the data store. When the entity
     * has not yet been placed in the data store, then it will not have/need an id.
     * </p>
     * <p>
     * Note that it is not recommended for this method to be called by normal code. This method is likely to be
     * called only by the Time Tracker DAO and Manager classes, and isn't something that the user of this component
     * should concern themselves with.
     * </p>
     *
     *
     *
     * @param id the unique identifier that distinguishes each entity within the Time Tracker component.
     * @throws IllegalArgumentException if the id is <=0
     */
    public void setId(long id) {
        Utils.checkPositive(id, "id");
        this.id = id;
    }

    /**
     * <p>
     * Checks whether any of the additional bean data has changed. This variable is used to assist in determining
     * whether the modification date and modification user for this bean needs to be modified during persistence.
     * Only implementors of the DAO, or subclasses of TimeTrackerBean need to concern themselves with this.
     * </p>
     *
     *
     *
     * @return whether any of the additional bean data has changed.
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * <p>
     * Sets whether any of the additional bean data has changed, and needs the modificationDate and user to be
     * updated or not. This variable is used to assist in determining whether the modification date and
     * modification user for this bean needs to be modified during persistence. Only implementors of the DAO, or
     * subclasses of TimeTrackerBean need to concern themselves with this.
     * </p>
     *
     *
     *
     * @param changed Whether the bean data has changed or not.
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * <p>
     * Indicates whether this bean is equal to the specified object. They are considered equal if the other object
     * is also a TimeTrackerBean and they have identical ids.
     * </p>
     *
     *
     * @return True if the 2 objects are equal. False if the other object is a null or is otherwise not considered
     *         equal.
     * @param o The other object to compare to.
     */
    public boolean equals(Object o) {
        if (!(o instanceof TimeTrackerBean)) {
            return false;
        }

        return id == ((TimeTrackerBean) o).id;
    }

    /**
     * <p>
     * Returns a hashCode based on the id of this bean.
     * </p>
     *
     * @return A hashcode based on the id of this bean.
     */
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
