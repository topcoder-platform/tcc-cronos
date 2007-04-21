/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.ejb;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDAOFactory;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceManager;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatus;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <p>
 * This is a stateless <code>SessionBean</code> that is used to provided business services to manage Invoices
 * within the Time Tracker Application. It implements the InvoiceManager interface and delegates to an instance of
 * InvoiceDAO retrieved from InvoiceDAOFactory and an instance of InvoiceStatusDAO retrieved from
 * InvoiceDAOFactory.
 * </p>
 * <p>
 * Thread Safety: The InvoiceManager interface implementations are required to be thread-safe, and so this
 * stateless session bean is thread-safe also.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InvoiceSessionBean implements SessionBean, InvoiceManager {

    /** Serial version UID. */
    private static final long serialVersionUID = -4697342162669997636L;

    /**
     * <p>
     * This is the instance of SessionContext that was provided by the EJB container. It is stored and made
     * available to subclasses.
     * </p>
     * <p>
     * Initial Value: Null
     * </p>
     * <p>
     * Accessed In: getSessionContext();
     * </p>
     * <p>
     * Modified In: setSessionContext
     * </p>
     * <p>
     * Utilized In: Not utilized in this class
     * </p>
     * <p>
     * Valid Values: sessionContext objects (possibly null)
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public InvoiceSessionBean() {
        // nothing to do
    }

    /**
     * <p>
     * Defines a invoice to be recognized within the persistent store managed by this utility. A unique invoice id
     * will automatically be generated and assigned to the invoice. There is also the option to perform an audit.
     * If an audit is specified, then the audit must be rolled back in case the operation fails.
     * </p>
     *
     * @param invoice
     *            The invoice for which the operation should be performed
     * @param audit
     *            Indicates whether an audit should be performed
     * @throws IllegalArgumentException
     *             if invoice is null
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void addInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException {
        try {
            InvoiceDAOFactory.getInvoiceDAO().addInvoice(invoice, audit);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
     *             if invoice is null
     * @throws InvoiceUnrecognizedEntityException
     *             if a invoice with the provided id was not found in the data store
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void updateInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException {
        try {
            InvoiceDAOFactory.getInvoiceDAO().updateInvoice(invoice, audit);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a Invoice object that reflects the data in the persistent store on the Time Tracker Invoice with
     * the specified invoiceId.
     * </p>
     *
     *
     * @param invoiceId
     *            The id of the invoice to retrieve
     *
     * @return The invoice with specified id
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if a invoice with the provided id was not found in the data store
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice getInvoice(long invoiceId) throws InvoiceDataAccessException {
        try {
            return InvoiceDAOFactory.getInvoiceDAO().getInvoice(invoiceId);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Searches the persistent store for any invoices that satisfy the criteria that was specified in the provided
     * search filter with the specified depth.
     * </p>
     *
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
        try {
            return InvoiceDAOFactory.getInvoiceDAO().searchInvoices(filter, depth);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the invoices that are currently in the persistent store.
     * </p>
     *
     * @return An array of invoices retrieved from the persistent store
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice[] getAllInvoices() throws InvoiceDataAccessException {
        try {
            return InvoiceDAOFactory.getInvoiceDAO().getAllInvoices();
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Looks at the particular project and check all entries (Time,Expense and Fixed billing) if there are any that
     * are PENDING then it will return false, else it will return true.
     * </p>
     *
     *
     * @param projectId
     *            the project's id to check
     *
     * @return true if can create Invoice and false otherwise
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     * @throws InvoiceUnrecognizedEntityException
     *             if project with the provided id was not found in the data store.
     * @throws IllegalArgumentException
     *             is projectId < 0
     */
    public boolean canCreateInvoice(long projectId) throws InvoiceDataAccessException {
        try {
            return InvoiceDAOFactory.getInvoiceDAO().canCreateInvoice(projectId);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Returns all invoice statuses that are currently in the persistent store.
     * </p>
     *
     * @return the invoices statuses contained in db, a void arrays if there aren't invoice statuses
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public InvoiceStatus[] getAllInvoiceStatus() throws InvoiceDataAccessException {
        try {
            return InvoiceDAOFactory.getInvoiceStatusDAO().getAllInvoiceStatus();
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
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
        try {
            return InvoiceDAOFactory.getInvoiceStatusDAO().getInvoiceStatus(id);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Returns the invoice status from description. (It's assumed that it's unique for each invoice)
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
        try {
            return InvoiceDAOFactory.getInvoiceStatusDAO().getInvoiceStatus(description);
        } catch (InvoiceConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw new InvoiceDataAccessException("There is an error when creating Invoice DAO", e);
        } catch (InvoiceDataAccessException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbCreate() {
        // nothing to do
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
        // nothing to do
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
        // nothing to do
    }

    /**
     * <p>
     * This method has simply been included to complete the SessionBean interface. It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
        // nothing to do
    }

    /**
     * <p>
     * Sets the SessionContext to use for this session. This method is included to comply with the SessionBean
     * interface.
     * </p>
     *
     * @param context
     *            The SessionContext to use.
     */
    public void setSessionContext(SessionContext context) {
        this.sessionContext = context;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return sessionContext;
    }
}
