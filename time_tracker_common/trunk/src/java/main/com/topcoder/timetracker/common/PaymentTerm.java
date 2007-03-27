/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

/**
 * <p>
 * This bean represents the Payment Terms that are used on an invoice to determine the payment agreement
 * for that invoice. Clients and projects have default payment terms associated with their records. The
 * payment term extends TimeTrackerBean and has the following attributes:
 *  <ul>
 *      <li>term: Represents the actual number of days before a payment is due.</li>
 *      <li>description: Represent the description which describes the number of days allowed before
 *      payment is due.</li>
 *      <li>active: Represent the flag used by application to delete terms which are no longer valid.
 *      Default to false.</li>
 *  </ul>
 * </p>
 *
 * <p>
 *  <strong>Thread Safety:</strong>
 *  This class is mutable, and not thread-safe. Multiple threads are advised to work with their own
 *  <code>PaymentTerm</code> instance.
 * </p>
 *
 * @author Mafy, liuliquan
 * @version 3.1
 */
public class PaymentTerm extends TimeTrackerBean {

    /**
     * <p>
     * Serial Version UID.
     * </p>
     */
    private static final long serialVersionUID = -7147374021222624781L;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represents the actual number of days before a payment is due.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  It is -1 when a blank bean is defined for initial data population, but it must always
     *  have a long value which is positive when the entity is retrieved from the data store.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *   Corresponding getter and setter which obey JavaBeans conventions.
     * </p>
     */
    private int term = -1;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represent the description which describes the number of days allowed before payment is due.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  It is null when a blank bean is defined for initial data population, but it must always
     *  have a non-null and non-empty string value which has length less than 64 when the entity is
     *  retrieved from the data store.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *   Corresponding getter and setter which obey JavaBeans conventions.
     * </p>
     */
    private String description = null;

    /**
     * <p>
     *  <strong>Usage:</strong>
     *  Represent the flag used by application to delete terms which are no longer valid, a setting of
     *  false will remove it from the active list.
     * </p>
     *
     * <p>
     *  <strong>Value:</strong>
     *  Default to false.
     * </p>
     *
     * <p>
     *  <strong>Accessibility:</strong>
     *   Corresponding getter and setter which obey JavaBeans conventions.
     * </p>
     */
    private boolean active = false;

    /**
     * <p>
     * Default constructor take no-arg to follow the JavaBeans conventions.
     * </p>
     */
    public PaymentTerm() {
        //does nothing
    }

    /**
     * <p>
     * Retrieves the actual number of days before a payment is due.
     * </p>
     *
     * @return the actual number of days before a payment is due.
     */
    public int getTerm() {
        return term;
    }

    /**
     * <p>
     * Set the actual number of days before a payment is due.
     * Manager should check the attribute and should throw IllegalArgumentException if term is not positive.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  No validation is performed here against the argument, so indeed users can set any values(even invalid).
     *  But if you are planning to use {@link SimpleCommonManager} or <code>DatabasePaymentTermDAO</code> to add/update
     *  <code>PaymentTerm</code>, you <b>must</b> ensure that you call this method and set a positive int value,
     *  otherwise <code>IllegalArgumentException</code> will be raised.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If the given term id is valid and does not equal to the one previously set, then this bean is marked as changed.
     * </p>
     *
     * @param term the actual number of days before a payment is due.
     *
     * @see TimeTrackerBean#setChanged(boolean)
     */
    public void setTerm(int term) {
        if (Helper.checkPositive(term) && term != this.term) {
            setChanged(true);
        }
        this.term = term;
    }

    /**
     * <p>
     * Retrieves the description which describes the number of days allowed before payment is due.
     * </p>
     *
     * @return the description which describes the number of days allowed before payment is due.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * Set the description which describes the number of days allowed before payment is due.
     * </p>
     *
     * <p>
     *  <strong>Validation:</strong>
     *  No validation is performed here against the argument, so indeed users can set any values(even invalid).
     *  But if you are planning to use {@link SimpleCommonManager} or <code>DatabasePaymentTermDAO</code> to add/update
     *  <code>PaymentTerm</code>, you <b>must</b> ensure that you call this method and set a non-null, non-empty
     *  (trimmed) string value with length not greater than 64, otherwise <code>IllegalArgumentException</code>
     *  will be raised.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If the given description is valid and does not equal to the one previously set, then this bean is marked as
     *  changed.
     * </p>
     *
     * @param description the description which describes the number of days allowed before payment is due.
     *
     * @see TimeTrackerBean#setChanged(boolean)
     */
    public void setDescription(String description) {
        if (Helper.checkStringWithMaxLength(description) && !description.equals(this.description)) {
            setChanged(true);
        }
        this.description = description;
    }

    /**
     * <p>
     * Checks the flag used by application to determine terms which are active.
     * </p>
     *
     * @return whether the <code>PaymentTerm</code> is active or not.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * <p>
     * Sets the flag used by application to determine terms which are active.
     * </p>
     *
     * <p>
     *  <strong>Note:</strong>
     *  If the given active status does not equal to the one previously set, then this bean is marked as changed.
     * </p>
     *
     * @param active true if the <code>PaymentTerm</code> is active; false otherwise.
     *
     * @see TimeTrackerBean#setChanged(boolean)
     */
    public void setActive(boolean active) {
        if (active != this.active) {
            this.active = active;
            setChanged(true);
        }
    }
}
