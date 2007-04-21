/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.delegate;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.topcoder.naming.jndiutility.JNDIUtils;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.invoice.ArgumentCheckUtil;
import com.topcoder.timetracker.invoice.ConfigUtil;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceManager;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocal;
import com.topcoder.timetracker.invoice.ejb.InvoiceManagerLocalHome;
import com.topcoder.util.config.ConfigManagerException;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application. It is responsible for
 * looking up the local interface of the SessionBean for InvoiceManager, and delegating any calls (and the
 * constants) to the InvoiceManagerLocal.
 * </p>
 * <p>
 * Thread Safety: - This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceManagerDelegate implements InvoiceManager {

    /** Represents the default JNDI location of InvoiceManagerLocalHome. */
    public static final String DEFAULT_JNDI_LOCATION_INVOICE_MANAGER_LOCAL_HOME = "invoiceManagerDelegate";

    /** Represents the property name of context name in the configuration. */
    private static final String CONTEXT_NAME = "contextName";

    /** Represents the property name of JNDI location in the configuration. */
    private static final String JNDI_LOCATION = "jndiLocation";

    /**
     * <p>
     * This is the local interface for the InvoiceManager business services. All business calls are delegated here.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not modified after initialization
     * </p>
     * <p>
     * Utilized In: All methods
     * </p>
     * <p>
     * Valid Values: Non-null
     * </p>
     */
    private final InvoiceManagerLocal invoiceManagerLocal;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace
     *            The namespace to use.
     * @throws IllegalArgumentException
     *             if namespace is null or an empty String.
     * @throws InvoiceDataAccessException
     *             if a problem occurs while constructing the instance.
     * @throws InvoiceConfigurationException
     *             is some problems in configuration file occur
     */
    public InvoiceManagerDelegate(String namespace) throws InvoiceConfigurationException,
        InvoiceDataAccessException {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("namespace", namespace);

        String contextName = ConfigUtil.getOptionalProperty(namespace, CONTEXT_NAME);
        String jndiLocation = ConfigUtil.getOptionalProperty(namespace, JNDI_LOCATION);

        try {
            Context jndiContext =
                (contextName == null) ? JNDIUtils.getDefaultContext() : JNDIUtils.getContext(contextName);

            Object ref =
                (jndiLocation == null) ? jndiContext.lookup(DEFAULT_JNDI_LOCATION_INVOICE_MANAGER_LOCAL_HOME)
                    : jndiContext.lookup(jndiLocation);

            // narrow the reference object
            InvoiceManagerLocalHome invoiceManagerLocalHome =
                (InvoiceManagerLocalHome) PortableRemoteObject.narrow(ref, InvoiceManagerLocalHome.class);

            invoiceManagerLocal = invoiceManagerLocalHome.create();

        } catch (ConfigManagerException e) {
            throw new InvoiceConfigurationException("A problem happens when trying to retrieve a context from "
                + contextName, e);
        } catch (NamingException e) {
            throw new InvoiceConfigurationException(
                "A problem happens when trying to retrieve an object from JNDI context", e);
        } catch (ClassCastException e) {
            throw new InvoiceConfigurationException("Referenced object is not of the correct type", e);
        } catch (CreateException e) {
            throw new InvoiceDataAccessException("Error creating the local InvoiceManager", e);
        }
    }

    /**
     * <p>
     * Defines an invoice to be recognized within the persistent store managed by this utility. A unique invoice id
     * will automatically be generated and assigned to the invoice. There is also the option to perform an audit.
     * If an audit is specified, then the audit must be rolled back in case the operation fails.
     * </p>
     *
     * @param invoice
     *            The invoice for which the operation should be performed
     * @param audit
     *            Indicates whether an audit should be performed
     *
     * @throws IllegalArgumentException
     *             if invoice is null
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void addInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException {
        invoiceManagerLocal.addInvoice(invoice, audit);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Invoice parameter. There is
     * also the option to perform an audit. If an audit is specified, then the audit must be rolled back in case
     * the operation fails.
     * </p>
     *
     * @param invoice
     *            The invoice for which the operation should be performed
     * @param audit
     *            Indicates whether an audit should be performed
     *
     * @throws IllegalArgumentException
     *             if invoice is null.
     * @throws InvoiceUnrecognizedEntityException
     *             if a invoice with the provided id was not found in the data store
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void updateInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException {
        invoiceManagerLocal.updateInvoice(invoice, audit);
    }

    /**
     * <p>
     * Retrieves a <code>Invoice</code> object that reflects the data in the persistent store on the Time Tracker
     * Invoice with the specified invoiceId.
     * </p>
     *
     * @param invoiceId
     *            The id of the invoice to retrieve
     *
     * @return The invoice with specified id
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if a invoice with the provided id was not found in the data store
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public Invoice getInvoice(long invoiceId) throws InvoiceDataAccessException {
        return invoiceManagerLocal.getInvoice(invoiceId);
    }

    /**
     * <p>
     * Searches the persistent store for any invoices that satisfy the criteria that was specified in the provided
     * search filter with the specified depth.
     * </p>
     *
     * @param filter
     *            The filter used to search for invoices
     * @param depth
     *            the mode of the invoices returned: INVOICE_ONLY: will return only invoices and no entries,
     *            INVOICE_ALL: will return the invoice and the Entries associated with the Invoice
     *
     * @return The invoices satisfying the conditions in the search filter
     *
     * @throws IllegalArgumentException
     *             if filter is null; if depth doesn't belong to the depths known
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice[] searchInvoices(Filter filter, InvoiceSearchDepth depth) throws InvoiceDataAccessException {
        return invoiceManagerLocal.searchInvoices(filter, depth);
    }

    /**
     * <p>
     * Retrieves all the invoices that are currently in the persistent store.
     * </p>
     *
     * @return An array of invoices retrieved from the persistent store
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public Invoice[] getAllInvoices() throws InvoiceDataAccessException {
        return invoiceManagerLocal.getAllInvoices();
    }

    /**
     * <p>
     * Looks at the particular project and check all entries (Time,Expense and Fixed billing) if there are any that
     * are PENDING then it will return false, else it will return true.
     * </p>
     *
     * @return true if can create Invoice and false otherwise
     *
     * @param projectId
     *            the project's id to check
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     * @throws InvoiceUnrecognizedEntityException
     *             if project with the provided id was not found in the data store
     * @throws IllegalArgumentException
     *             is projectId < 0
     */
    public boolean canCreateInvoice(long projectId) throws InvoiceDataAccessException {
        return invoiceManagerLocal.canCreateInvoice(projectId);
    }

    /**
     * <p>
     * Returns all invoice statuses that are currently in the persistent store.
     * </p>
     *
     * @return the invoices statuses contained in db, a void arrays if there aren't invoice statuses.
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public InvoiceStatus[] getAllInvoiceStatus() throws InvoiceDataAccessException {
        return invoiceManagerLocal.getAllInvoiceStatus();
    }

    /**
     * <p>
     * Returns the invoice status from id.
     * </p>
     *
     * @param id
     *            the id of invoice statuses from what is retrieved
     *
     * @return the invoice status that has the id used
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if an invoice status doesn't exist with the specified id
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public InvoiceStatus getInvoiceStatus(long id) throws InvoiceDataAccessException {
        return invoiceManagerLocal.getInvoiceStatus(id);
    }

    /**
     * <p>
     * Returns the invoice status from description. (It's assumed that it's unique for each invoice).
     * </p>
     *
     * @param description
     *            the description of invoice status from what is retrieved
     *
     * @return the invoice status that has the description used
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if an invoice status doesn't exist with the specified description
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws IllegalArgumentException
     *             if description is null or empty.
     */
    public InvoiceStatus getInvoiceStatus(String description) throws InvoiceDataAccessException {
        return invoiceManagerLocal.getInvoiceStatus(description);
    }
}
