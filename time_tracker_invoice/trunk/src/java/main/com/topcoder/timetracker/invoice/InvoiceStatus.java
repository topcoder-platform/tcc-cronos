/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.util.collection.typesafeenum.Enum;
import java.util.Date;

/**
 * <p>
 * This class includes getters and setters to access the various properties of a Time Tracker Invoice Status, This
 * class encapsulates the invoice status information within the Time Tracker component.
 * </p>
 * <p>
 * Thread Safety: This class is not thread safe; Each thread is expected to work on it's own instance, or this
 * class should be used in a read-only manner for concurrent access. The method for retrieve InvoiceStatuses should
 * be synchronized to ensure only a single instance of the invoiceStatuses is instantiated.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceStatus extends Enum {

    /** Serial version UID. */
    private static final long serialVersionUID = 4471461248188046313L;

    /**
     * <p>
     * A brief description of the status.
     * </p>
     * <p>
     * Initial Value:not null, initialized in constructor
     * </p>
     * <p>
     * Accessed In: getDescription
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: not null,not empty
     * </p>
     */
    private final String description;

    /**
     * <p>
     * A brief description of the status.
     * </p>
     * <p>
     * Initial Value: initialized in constructor
     * </p>
     * <p>
     * Accessed In: getId
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: all values
     * </p>
     */
    private final long id;

    /**
     * <p>
     * The creation user.
     * </p>
     * <p>
     * Initial Value: not null, initialized in constructor
     * </p>
     * <p>
     * Accessed In: getCreationUser
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: not null,not empty
     * </p>
     */
    private final String creationUser;

    /**
     * <p>
     * The modification user.
     * </p>
     * <p>
     * Initial Value: not null, initialized in constructor
     * </p>
     * <p>
     * Accessed In: getModificationUser
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: not null,not empty
     * </p>
     */
    private final String modificationUser;

    /**
     * <p>
     * The creation date.
     * </p>
     * <p>
     * Initial Value:not null, initialized in constructor
     * </p>
     * <p>
     * Accessed In: getCreationDate
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: not null
     * </p>
     */
    private final Date creationDate;

    /**
     * <p>
     * The creation date.
     * </p>
     * <p>
     * Initial Value:not null, initialized in constructor
     * </p>
     * <p>
     * Accessed In: getModificationDate
     * </p>
     * <p>
     * Modified In: none
     * </p>
     * <p>
     * Utilized In: Not Utilized
     * </p>
     * <p>
     * Valid Values: not null
     * </p>
     */
    private final Date modificationDate;

    /**
     * <p>
     * Construct an InvoiceStatus and assign the the fields to each other.
     * </p>
     *
     * @param id
     *            the id of invoice status
     * @param description
     *            the description of invoice status
     * @param creationUser
     *            the creation user of invoice status
     * @param modificationUser
     *            the modification user of invoice status
     * @param creationDate
     *            the creation date of invoice status
     * @param modificationDate
     *            the modification date of invoice status
     * @throws IllegalArgumentException
     *             if description or creationUser or modificationUser or creationDate or modificationDate are null,
     *             if description or creationUser or modificationUser are empty
     */
    public InvoiceStatus(final long id, final String description, final String creationUser,
        final String modificationUser, final Date creationDate, final Date modificationDate) {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("description", description);
        ArgumentCheckUtil.checkNotNullAndNotEmpty("creationUser", creationUser);
        ArgumentCheckUtil.checkNotNullAndNotEmpty("modificationUser", modificationUser);
        ArgumentCheckUtil.checkNotNull("creationDate", creationDate);
        ArgumentCheckUtil.checkNotNull("modificationDate", modificationDate);

        this.description = description;
        this.id = id;
        this.creationUser = creationUser;
        this.modificationUser = modificationUser;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
    }

    /**
     * Return the creation date.
     *
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Return the creation user.
     *
     * @return the creationUser
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * Return the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Return the modification date.
     *
     * @return the modificationDate
     */
    public Date getModificationDate() {
        return modificationDate;
    }

    /**
     * Return the modification user.
     *
     * @return the modificationUser
     */
    public String getModificationUser() {
        return modificationUser;
    }
}
