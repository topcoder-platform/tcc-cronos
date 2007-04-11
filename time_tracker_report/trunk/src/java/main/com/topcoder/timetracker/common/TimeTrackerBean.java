/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * This is a bean base class used to represent all entities of the Time Tracker Application, which
 * it persisted into the database. It contains all the properties which are common to these entities
 * (id, creation date, modification date, creation user, modification user and changed flag). It is
 * also Serializable to support network transfer, and state persistence.
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable, and not thread-safe. Multiple threads are
 * advised to work with their own bean instance.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 3.1
 */
public abstract class TimeTrackerBean implements Serializable {

    /**
     * <p>
     * The 32 bits for Zero-Fill Right Shift.
     * </p>
     */
    private static final int BITS = 32;

    /**
     * <p>
     * <strong>Usage:</strong> Represents the date when this entity was initially defined within
     * the Time Tracker data store.
     * </p>
     *
     * <p>
     * <strong>Value:</strong> It is null when a blank bean is defined for initial data population,
     * but it must always have a non-null value when the entity is retrieved from the data store.
     * </p>
     *
     * <p>
     * <strong>Accessibility:</strong> Corresponding getter and setter which obey JavaBeans
     * conventions.
     * </p>
     */
    private Date creationDate = null;

    /**
     * <p>
     * <strong>Usage:</strong> Represents the date when any of the fields of this entity was
     * modified within Time Tracker data store. If the entity has not been modified since its
     * initial definition, then this value would be equal to creationDate.
     * </p>
     *
     * <p>
     * <strong>Value:</strong> It is null when a blank bean is defined for initial data population,
     * but it must always have a non-null value when the entity is retrieved from the data store.
     * </p>
     *
     * <p>
     * <strong>Accessibility:</strong> Corresponding getter and setter which obey JavaBeans
     * conventions.
     * </p>
     */
    private Date modificationDate = null;

    /**
     * <p>
     * <strong>Usage:</strong> Represents the user that initially defined this entity within the
     * Time Tracker data store.
     * </p>
     *
     * <p>
     * <strong>Value:</strong> It is null when a blank bean is defined for initial data population,
     * but it must always have a non-null, non-empty string value with length not greater than 64
     * when the entity is retrieved from the data store.
     * </p>
     *
     * <p>
     * <strong>Accessibility:</strong> Corresponding getter and setter which obey JavaBeans
     * conventions.
     * </p>
     */
    private String creationUser = null;

    /**
     * <p>
     * <strong>Usage:</strong> Represents the user that modified this entity within the Time
     * Tracker data store. If the entity has not been modified since its initial definition, then
     * this value would be equal to creationUser.
     * </p>
     *
     * <p>
     * <strong>Value:</strong> It is null when a blank bean is defined for initial data population,
     * but it must always have a non-null, non-empty string value with length not greater than 64
     * when the entity is retrieved from the data store.
     * </p>
     *
     * <p>
     * <strong>Accessibility:</strong> Corresponding getter and setter which obey JavaBeans
     * conventions.
     * </p>
     */
    private String modificationUser = null;

    /**
     * <p>
     * <strong>Usage:</strong> Represent a unique identifier that distinguishes each entity within
     * the Time Tracker application. An id is automatically assigned by the manager of this
     * component when the entity is initially placed into the data store. When the entity has not
     * yet been placed in the data store, then it will not have/need an id.
     * </p>
     *
     * <p>
     * <strong>Value:</strong> It is -1 when a blank bean is defined for initial data population,
     * but it will always have a long value which is positive when the entity is placed
     * into/retrieved from the data store.
     * </p>
     *
     * <p>
     * <strong>Accessibility:</strong> Corresponding getter and setter which obey JavaBeans
     * conventions.
     * </p>
     */
    private long id = 0;

    /**
     * <p>
     * <strong>Usage:</strong> Represent the flag used by application to determine if the objects
     * values have changed. This variable represents whether any of the additional bean data has
     * changed. This variable is used to assist in determining whether the modification date and
     * modification user for this bean needs to be modified during persistence. Only implementors of
     * the DAO, or subclasses of TimeTrackerBean need to concern themselves with this.
     * </p>
     *
     * <p>
     * <strong>Value:</strong> Most in this application it will end up being set to true given the
     * nature of the application.
     * </p>
     *
     * <p>
     * <strong>Accessibility:</strong> Corresponding getter and setter which obey JavaBeans
     * conventions.
     * </p>
     */
    private boolean changed = false;

    /**
     * <p>
     * Default constructor take no-arg to follow the JavaBeans conventions.
     * </p>
     */
    protected TimeTrackerBean() {
        // do nothing
    }

    /**
     * <p>
     * Retrieves the date when this entity was initially defined within the Time Tracker data store.
     * </p>
     *
     * <p>
     * <strong>Note:</strong>
     * <ul>
     * <li> The returned value may be null when this is a blank bean for initial data population,
     * but it will always be non-null if this bean is populated from the data store. </li>
     * <li> Changes on the returned <code>Date</code> object will not affect the inner state of
     * this bean. </li>
     * </ul>
     * </p>
     *
     * @return the date when this entity was initially defined within the Time Tracker data store.
     */
    public Date getCreationDate() {
        return this.creationDate == null ? null : new Date(this.creationDate.getTime());
    }

    /**
     * <p>
     * Sets the date when this entity was initially defined within the Time Tracker data store.
     * </p>
     *
     * <p>
     * <Strong>Note:</strong> No validation is performed here against the argument, so indeed users
     * can set any values(even invalid). But for the Time Tracker Common component:
     * <ul>
     * <li> If you are planning to call {@link SimpleCommonManager#addPaymentTerm(PaymentTerm)} with
     * this bean, calling this method will not make any sense. The value you set will always be
     * replaced by current date. </li>
     * <li> If you are planning to call {@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}
     * with this bean, you <b>must</b> ensure that you call this method and set a non-null value,
     * otherwise <code>IllegalArgumentException</code> will be raised. </li>
     * <li> If you are not planning to use {@link SimpleCommonManager} but instead use
     * <code>DatabasePaymentTermDAO</code> to add/update <code>PaymentTerm</code> directly, you
     * <b>must</b> ensure that you call this method and set a non-null value, otherwise
     * <code>IllegalArgumentException</code> will be raised. </li>
     * </ul>
     * </p>
     *
     * <p>
     * Changes on the passed <code>Date</code> object will not affect the inner state of this
     * bean.
     * </p>
     *
     * @param creationDate the date when this entity was initially defined within the Time Tracker
     *        data store.
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate == null ? null : new Date(creationDate.getTime());
    }

    /**
     * <p>
     * Retrieves the date when any of the fields of this entity was modified within Time Tracker
     * data store.
     * </p>
     *
     * <p>
     * <strong>Note:</strong>
     * <ul>
     * <li> The returned value may be null when this is a blank bean for initial data population,
     * but it will always be non-null if this bean is populated from the data store. </li>
     * <li> Changes on the returned <code>Date</code> object will not affect the inner state of
     * this bean. </li>
     * </ul>
     * </p>
     *
     * @return the date when any of the fields of this entity was modified within Time Tracker data
     *         store.
     */
    public Date getModificationDate() {
        return this.modificationDate == null ? null : new Date(this.modificationDate.getTime());
    }

    /**
     * <p>
     * Sets the date when any of the fields of this entity was modified within Time Tracker data
     * store.
     * </p>
     *
     * <p>
     * <strong>Recommendation:</strong> It is not recommended for this method to be called by
     * normal code. This method is likely to be called only by the {@link SimpleCommonManager}
     * class, and isn't something that the user of this component should concern themselves with.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> No validation is performed here against the argument, so indeed users
     * can set any values(even invalid). But for the Time Tracker Common component:
     * <ul>
     * <li> If you are planning to call {@link SimpleCommonManager#addPaymentTerm(PaymentTerm)} or
     * {@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)} with this bean, calling this
     * method will not make any sense. The value you set will always be replaced by current date.
     * </li>
     * <li> If you are not planning to use {@link SimpleCommonManager} but instead use
     * <code>DatabasePaymentTermDAO</code> to add/update <code>PaymentTerm</code> directly, you
     * <b>must</b> ensure that you call this method and set a non-null value, otherwise
     * <code>IllegalArgumentException</code> will be raised. </li>
     * </ul>
     * </p>
     *
     * <p>
     * Changes on the passed <code>Date</code> object will not affect the inner state of this
     * bean.
     * </p>
     *
     * @param modificationDate the date when any of the fields of this entity was modified within
     *        Time Tracker data store.
     */
    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate == null ? null : new Date(modificationDate.getTime());
    }

    /**
     * <p>
     * Retrieves the user that initially defined this entity within the Time Tracker data store.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> The returned value may be null this is a blank bean for initial data
     * population, but it will always be non-null, non-empty and with length not greater than 64 if
     * this bean is populated from the data store.
     * </p>
     *
     * @return the user that initially defined this entity within the Time Tracker data store.
     */
    public String getCreationUser() {
        return this.creationUser;
    }

    /**
     * <p>
     * Sets the user that initially defined this entity within the Time Tracker data store.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> No validation is performed here against the argument, so indeed users
     * can set any values(even invalid). But for the Time Tracker Common component, if you are
     * planning to use {@link SimpleCommonManager} or <code>DatabasePaymentTermDAO</code> to
     * add/update <code>PaymentTerm</code>, you <b>must</b> ensure that you call this method and
     * set a non-null, non-empty(trimmed) string value with length not greater than 64, otherwise
     * <code>IllegalArgumentException</code> will be raised.
     * </p>
     *
     * @param creationUser the user that initially defined this entity within the Time Tracker data
     *        store.
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }

    /**
     * <p>
     * Retrieves the user that most recently modified this entity within the Time Tracker data
     * store.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> The returned value may be null when this is a blank bean for initial
     * data population, but it will always be non-null, non-empty and with length not greater than
     * 64 if this bean is populated from the data store.
     * </p>
     *
     * @return the user that most recently modified this entity within the Time Tracker data store.
     */
    public String getModificationUser() {
        return this.modificationUser;
    }

    /**
     * <p>
     * Sets the user that most recently modified this entity within the Time Tracker data store.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> No validation is performed here against the argument, so indeed users
     * can set any values(even invalid). But for the Time Tracker Common component:
     * <ul>
     * <li> If you are planning to call {@link SimpleCommonManager#addPaymentTerm(PaymentTerm)} with
     * this bean, and if you set a null modification user or even do not call this method, then the
     * modification user will be replaced by creation user. If you do call this method and set a
     * non-null modification user, please ensure it is also non-empty(trimmed) and with length not
     * greater than 64, otherwise <code>IllegalArgumentException</code> will be raised. </li>
     * <li> If you are planning to call {@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)}
     * with this bean, you <b>must</b> ensure that you call this method and set a non-null,
     * non-empty(trimmed) string value with length not greater than 64, otherwise
     * <code>IllegalArgumentException</code> will be raised. </li>
     * <li> If you are not planning to use {@link SimpleCommonManager} but instead use
     * <code>DatabasePaymentTermDAO</code> to add/update <code>PaymentTerm</code> directly, you
     * <b>must</b> ensure that you call this method and set a non-null, non-empty(trimmed) string
     * value with length not greater than 64, otherwise <code>IllegalArgumentException</code> will
     * be raised. </li>
     * </ul>
     * </p>
     *
     * @param modificationUser the user that most recently modified this entity within the Time
     *        Tracker data store.
     */
    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }

    /**
     * <p>
     * Retrieves the unique identifier that distinguishes each entity within the Time Tracker
     * application.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> The returned value will be -1 when this is a blank bean for initial
     * data population, but it will always be positive if this bean is populated from the data
     * store.
     * </p>
     *
     * @return the unique identifier that distinguishes each entity within the Time Tracker
     *         application
     */
    public long getId() {
        return this.id;
    }

    /**
     * <p>
     * Sets the unique identifier that distinguishes each entity within the Time Tracker
     * application.
     * </p>
     *
     * <p>
     * <Strong>Note:</strong> No validation is performed here against the argument, so indeed users
     * can set any values(even invalid). But for the Time Tracker Common component:
     * <ul>
     * <li> If you are planning to use {@link SimpleCommonManager} or
     * <code>DatabasePaymentTermDAO</code> to add <code>PaymentTerm</code>, calling this method
     * will not make any sense. The value you set will always be replaced by an id auto generated by
     * <code>IDGenerator</code>. Also you should not add a duplicate <code>PaymentTerm</code>
     * by setting a duplicate unique identifier. </li>
     * <li> If you are planning to use {@link SimpleCommonManager} or
     * <code>DatabasePaymentTermDAO</code> to update <code>PaymentTerm</code>, you <b>must</b>
     * ensure that you call this method and set a positive value, otherwise
     * <code>IllegalArgumentException</code> will be raised. Also you should not update a
     * non-exist <code>PaymentTerm</code> by setting a non-exist unique identifier. </li>
     * </ul>
     * </p>
     *
     * @param id the unique identifier that distinguishes each entity within the Time Tracker
     *        application
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>
     * Checks whether any of the additional bean data has changed.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> For the Time Tracker Common component, the
     * {@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)} will check the changed status to
     * determine whether to update or not.
     * </p>
     *
     * @return whether any of the additional bean data has changed.
     */
    public boolean isChanged() {
        return this.changed;
    }

    /**
     * <p>
     * Generally this method will called within the setters of some bean class which extends this
     * abstract bean to indicate that whether any of the additional data has been changed.
     * </p>
     *
     * <p>
     * <strong>Note:</strong> For the Time Tracker Common component,
     * <ul>
     * <li> The {@link SimpleCommonManager#addPaymentTerm(PaymentTerm)} will set the changed status
     * to false after success adding. </li>
     * <li> The {@link SimpleCommonManager#updatePaymentTerm(PaymentTerm)} will set the changed
     * status to false after success updating. </li>
     * <li> All the retrieve methods of {@link SimpleCommonManager} will set the changed status to
     * false after success retrieving. </li>
     * </ul>
     * </p>
     *
     * @param changed true If the bean is changed and need be updated; false otherwise.
     *
     * @see SimpleCommonManager#updatePaymentTerm(PaymentTerm)
     */
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    /**
     * <p>
     * Returns the hash code based on the id of this bean. The calculation is same as the
     * {@link Long#hashCode()}.
     * </p>
     *
     * @return The hash code based on the id of this bean.
     */
    public int hashCode() {
        return (int) (this.id ^ (this.id >>> BITS));
    }
}
