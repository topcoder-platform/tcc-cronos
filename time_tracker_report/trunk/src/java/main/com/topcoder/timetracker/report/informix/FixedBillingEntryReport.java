/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.informix;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;

/**
 * <p>
 * This class represents a report of a single fixed billing entry and all information about it. This
 * class extends ReportEntryBean.
 * </p>
 *
 * <p>
 * <strong>Thread-safety:</strong> This class isn't thread safe because it's a bean and haven't the
 * setter method synchronized.
 * </p>
 *
 * @author fabrizyo, rinoavd
 * @version 3.1
 */
public class FixedBillingEntryReport extends ReportEntryBean {

    /**
     * Automatically generated unique ID for use qith serialization.
     */
    private static final long serialVersionUID = 3562198654935306492L;

    /**
     * <p>
     * Represents the fixed billing entry. It's changeable , can be null and it's null at the
     * beginning.
     * </p>
     */
    private FixedBillingEntry fixedBillingEntry;

    /**
     * <p>
     * Represents the fixed billing status. It's changeable , can be null and it's null at the
     * beginning.
     * </p>
     */
    private FixedBillingStatus fixedBillingStatus;

    /**
     * <p>
     * Constructs a new <code>FixedBillingEntryReport</code.
     * </p>
     */
    public FixedBillingEntryReport() {
        // empty
    }

    /**
     * <p>
     * Retrieves the fixed billing entry.
     * </p>
     *
     * @return the fixed billing entry.
     */
    public FixedBillingEntry getFixedBillingEntry() {
        return this.fixedBillingEntry;
    }

    /**
     * <p>
     * Sets the fixed billing entry.
     * </p>
     *
     * @param fixedBillingEntry the fixed billing entry to set
     */
    public void setFixedBillingEntry(FixedBillingEntry fixedBillingEntry) {
        this.fixedBillingEntry = fixedBillingEntry;
    }

    /**
     * <p>
     * Retrieves the fixed billing status.
     * </p>
     *
     * @return the fixed billing status
     */
    public FixedBillingStatus getFixedBillingStatus() {
        return this.fixedBillingStatus;
    }

    /**
     * <p>
     * Sets the fixed billing status.
     * </p>
     *
     * @param fixedBillingStatus the fixed billing status
     */
    public void setFixedBillingStatus(FixedBillingStatus fixedBillingStatus) {
        this.fixedBillingStatus = fixedBillingStatus;
    }
}
