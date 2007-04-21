/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is the enum for the possible depths search in searchInvoices method in InvoiceDAO.
 * </p>
 * <p>
 * Thread safe: this class is thread safe because the parent is thread safe and this class not add variables state
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceSearchDepth extends Enum {

    /**
     * This indicate that the search fills only the fields into Invoice.
     */
    public static final InvoiceSearchDepth INVOICE_ONLY = new InvoiceSearchDepth();

    /**
     * This indicate that the search fills the fields into Invoice and the entries in Invoice.
     */
    public static final InvoiceSearchDepth INVOICE_ALL = new InvoiceSearchDepth();

    /** Serial version UID. */
    private static final long serialVersionUID = -3457806862370262005L;

    /**
     * The private constructor for satisfy the enumeration pattern.
     */
    private InvoiceSearchDepth() {
        // nothing to do
    }
}
