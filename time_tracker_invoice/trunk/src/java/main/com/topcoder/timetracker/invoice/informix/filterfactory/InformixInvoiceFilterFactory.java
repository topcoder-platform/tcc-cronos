/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix.filterfactory;

import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.timetracker.invoice.ArgumentCheckUtil;

import java.util.Date;

/**
 * <p>
 * This class may be used for building searches in the database. It builds filters according to the specified
 * column names. The column names are defined in the config file for Search Bundle, so the names of the columns
 * used are related to it.
 * </p>
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any of the
 * Composite Filters in the Search Builder Component (AndFilter, OrFilter, etc.)
 * </p>
 * <p>
 * Thread Safety: This class is thread safe because contains only static methods
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InformixInvoiceFilterFactory {

    /**
     * <p>
     * This is the empty constructor for preventing the instances of this class.
     * </p>
     */
    private InformixInvoiceFilterFactory() {
        // nothing to do
    }

    /**
     * <p>
     * This creates a Filter that will select Invoice based on the id of the Time Tracker Company.
     * </p>
     *
     * @param companyId
     *            the id of the Time Tracker Company to search
     *
     * @return A filter that will be based off the specified criteria
     *
     * @throws IllegalArgumentException
     *             if the id < 0
     */
    public static Filter createCompanyIdFilter(long companyId) {
        ArgumentCheckUtil.checkNotNegative("companyId", companyId);

        return new EqualToFilter("company_id", new Long(companyId));
    }

    /**
     * <p>
     * This creates a Filter that will select Invoice based on the id of the Time Tracker Project.
     * </p>>
     *
     * @param projectId
     *            the id of the Time Tracker Project to search
     *
     * @return A filter that will be based off the specified criteria
     *
     * @throws IllegalArgumentException
     *             if the id < 0
     */
    public static Filter createProjectIdFilter(long projectId) {
        ArgumentCheckUtil.checkNotNegative("projectId", projectId);

        return new EqualToFilter("project_id", new Long(projectId));
    }

    /**
     * <p>
     * This creates a Filter that will select Invoice based on the id of the Time Tracker Client.
     * </p>
     *
     * @param clientId
     *            the id of the Time Tracker Client to search
     *
     * @return A filter that will be based off the specified criteria
     *
     * @throws IllegalArgumentException
     *             if the id < 0
     */
    public static Filter createClientIdFilter(long clientId) {
        ArgumentCheckUtil.checkNotNegative("clientId", clientId);

        return new EqualToFilter("client_id", new Long(clientId));
    }

    /**
     * <p>
     * This creates a Filter that will select Invoice based on the id of the Time Tracker Invoice Status.
     * </p>
     *
     * @param invoiceStatusId
     *            the id of the Time Tracker Invoice Status to search
     *
     * @return A filter that will be based off the specified criteria
     *
     *
     * @throws IllegalArgumentException
     *             if the id < 0
     */
    public static Filter createInvoiceStatusIdFilter(long invoiceStatusId) {
        ArgumentCheckUtil.checkNotNegative("invoiceStatusId", invoiceStatusId);

        return new EqualToFilter("invoice_invoice_status_id", new Long(invoiceStatusId));
    }

    /**
     * <p>
     * This creates a Filter that will select Invoice based on invoice number.
     * </p>
     *
     * @param invoiceNumber
     *            the invoice number of Invoice to search
     *
     * @return A filter that will be based off the specified criteria
     *
     * @throws IllegalArgumentException
     *             if invoiceNumber is null or empty
     */
    public static Filter createInvoiceNumberFilter(String invoiceNumber) {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("invoiceNumber", invoiceNumber);

        return new EqualToFilter("invoice_number", invoiceNumber);
    }

    /**
     * <p>
     * This create the filter that filter the invoices by the invoice date within a given inclusive range.
     * <p>
     *
     * @param from
     *            the initial date, if null the range is considered open-ended
     * @param to
     *            the final date, if null the range is considered open-ended
     *
     * @return the filter that filter the invoices by the invoice date within a given inclusive range
     *
     * @throws IllegalArgumentException
     *             if both argument are null
     */
    public static Filter createInvoiceDateFilter(Date from, Date to) {
        return createDateFilter(from, to, "invoice_date");
    }

    /**
     * Creates duration date filter. Open end OR open star is supported if either the parameter is null, but not
     * both.
     *
     * @param from
     *            the start date, inclusive
     * @param to
     *            the end date, inclusive
     * @param name
     *            the name of the column
     *
     * @return the date filter
     */
    private static Filter createDateFilter(Date from, Date to, String name) {
        if (from == null && to == null) {
            throw new IllegalArgumentException("At least one 'from' or 'to' need to be available");
        }

        if (to == null) {
            return new GreaterThanOrEqualToFilter(name, from);
        } else if (from == null) {
            return new LessThanOrEqualToFilter(name, to);
        }

        if (from.after(to)) {
            throw new IllegalArgumentException("'from' cannot be after 'to'.");
        }
        return new BetweenFilter(name, to, from);
    }

    /**
     * <p>
     * This create the filter that filter the invoices by the creation date within a given inclusive range.
     * <p>
     *
     * @param from
     *            the initial date, if null the range is considered open-ended
     * @param to
     *            the final date, if null the range is considered open-ended
     *
     * @return the filter that filter the invoices by the creation date within a given inclusive range
     *
     * @throws IllegalArgumentException
     *             if both argument are null
     */
    public static Filter createCreationDateFilter(Date from, Date to) {
        return createDateFilter(from, to, "creation_date");
    }

    /**
     * <p>
     * This create the filter that filter the invoices by the due date within a given inclusive range.
     * <p>
     *
     * @param from
     *            the initial date, if null the range is considered open-ended
     * @param to
     *            the final date, if null the range is considered open-ended
     *
     * @return the filter the filter that filter the invoices by the due date within a given inclusive range
     *
     * @throws IllegalArgumentException
     *             if both argument are null
     */
    public static Filter createDueDateFilter(Date from, Date to) {
        return createDateFilter(from, to, "due_date");
    }

    /**
     * <p>
     * This create the filter that filter the invoices by the modification date within a given inclusive range.
     * <p>
     *
     * @param from
     *            the initial date, if null the range is considered open-ended
     * @param to
     *            the final date, if null the range is considered open-ended
     *
     * @return the filter that filter the invoices by the modification date within a given inclusive range
     *
     * @throws IllegalArgumentException
     *             if both argument are null
     */
    public static Filter createModificationDateFilter(Date from, Date to) {
        return createDateFilter(from, to, "modification_date");
    }

    /**
     * <p>
     * This creates a Filter that will select Invoices based on the creation username.
     * </p>
     * <p>
     * It can also support substring searches by prefixing the String with 'SW:', 'EW:', and 'SS:'. SW: is used to
     * search for strings that "Start With" the provided value. 'EW:' is used to search for strings that 'End With'
     * the provided value. 'SS:' is used to search for Strings that 'Contain' the provided value.
     * </p>
     * <p>
     * Some examples would be: 'SW:Data' would search for all Strings that started with 'Data'. [Database,
     * Datastore, etc.] 'EW:Builder' would search for all strings that ended with 'Builder'. [SearchBuilder,
     * DocumentBuilder, etc.] 'SS:ing' would search for all Strings that contained the substring 'ing'. [coding,
     * ingratitude, dingdong, etc.]
     * </p>
     *
     * @param creationUsername
     *            The creation username
     *
     * @return A filter that will be based off the specified criteria
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String
     */
    public static Filter createCreationUsernameFilter(String creationUsername) {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("creationUsername", creationUsername);

        return new LikeFilter("creation_user", creationUsername);
    }

    /**
     * <p>
     * This creates a Filter that will select Invoices based on the modification username.
     * </p>
     * <p>
     * It can also support substring searches by prefixing the String with 'SW:', 'EW:', and 'SS:'. SW: is used to
     * search for strings that "Start With" the provided value. 'EW:' is used to search for strings that 'End With'
     * the provided value. 'SS:' is used to search for Strings that 'Contain' the provided value.
     * </p>
     * <p>
     * Some example would be: 'SW:Data' would search for all Strings that started with 'Data'. [Database,
     * Datastore, etc.] 'EW:Builder' would search for all strings that ended with 'Builder'. [SearchBuilder,
     * DocumentBuilder, etc.] 'SS:ing' would search for all Strings that contained the substring 'ing'. [coding,
     * ingratitude, dingdong, etc.]
     * </p>
     *
     * @param modificationUsername
     *            The modification username
     *
     * @return A filter that will be based off the specified criteria
     *
     * @throws IllegalArgumentException
     *             if the String is null or an empty String
     */
    public static Filter createModificationUsernameFilter(String modificationUsername) {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("modificationUsername", modificationUsername);

        return new LikeFilter("modification_user", modificationUsername);
    }

    /**
     * <p>
     * This creates a Filter that will select Invoice based on the value of paid set/unset.
     * </p>
     *
     * @return A filter that will be based off the specified criteria
     *
     * @param paid
     *            the paid set/unset
     */
    public static Filter createPaidFilter(boolean paid) {
        return new EqualToFilter("paid", paid ? new Integer(1) : new Integer(0));
    }
}
