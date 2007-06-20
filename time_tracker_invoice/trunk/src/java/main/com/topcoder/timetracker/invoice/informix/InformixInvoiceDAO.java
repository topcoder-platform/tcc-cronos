/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.informix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.common.CommonManagementException;
import com.topcoder.timetracker.common.CommonManager;
import com.topcoder.timetracker.entry.base.BaseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.entry.expense.criteria.FieldMatchCriteria;
import com.topcoder.timetracker.entry.expense.persistence.PersistenceException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryManager;
import com.topcoder.timetracker.invoice.ArgumentCheckUtil;
import com.topcoder.timetracker.invoice.ConfigUtil;
import com.topcoder.timetracker.invoice.Invoice;
import com.topcoder.timetracker.invoice.InvoiceConfigurationException;
import com.topcoder.timetracker.invoice.InvoiceDAO;
import com.topcoder.timetracker.invoice.InvoiceDataAccessException;
import com.topcoder.timetracker.invoice.InvoiceSearchDepth;
import com.topcoder.timetracker.invoice.InvoiceStatusDAO;
import com.topcoder.timetracker.invoice.InvoiceUnrecognizedEntityException;
import com.topcoder.timetracker.invoice.servicedetail.DataAccessException;
import com.topcoder.timetracker.invoice.servicedetail.InvoiceServiceDetail;
import com.topcoder.timetracker.invoice.servicedetail.ServiceDetailManager;
import com.topcoder.timetracker.invoice.servicedetail.TransactionCreationException;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.ProjectUtility;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an implementation of the InvoiceDAO interface. This implementation is to be used with Informix database.
 * The related table in the Time Tracker schema are invoice and invoice_status.
 * </p>
 * <p>
 * Thread Safety: This implementation is required to be thread safe.
 * </p>
 *
 * @author fabrizyo, enefem21
 * @version 1.0
 */
public class InformixInvoiceDAO implements InvoiceDAO {

    /** Represents the property name of object factory namespace in the configuration. */
    private static final String OBJECT_FACTORY_NAMESPACE = "objectFactoryNamespace";

    /** Represents the property name of audit manager key in the object factory. */
    private static final String AUDIT_MANAGER_KEY = "auditManagerKey";

    /** Represents the property name of id generator key in the object factory. */
    private static final String ID_GENERATOR_KEY = "idGeneratorKey";

    /** Represents the property name of expense manager key in the object factory. */
    private static final String EXPENSE_MANAGER_KEY = "expenseManagerKey";

    /** Represents the property name of fixed billing manager key in the object factory. */
    private static final String FIXED_BILLING_MANAGER_KEY = "fixedBillingManagerKey";

    /** Represents the property name of database connection factory key in the object factory. */
    private static final String DB_CONNECTION_FACTORY_KEY = "dbConnectionFactoryKey";

    /** Represents the property name of project utility key in the object factory. */
    private static final String PROJECT_UTILITY_KEY = "projectUtilityKey";

    /** Represents the property name of service detail manager key in the object factory. */
    private static final String SERVICE_DETAIL_MANAGER_KEY = "serviceDetailManagerKey";

    /** Represents the property name of common manager key in the object factory. */
    private static final String COMMON_MANAGER_KEY = "commonManagerKey";

    /** Represents the property name of invoice status DAO key in the object factory. */
    private static final String INVOICE_STATUS_DAO_KEY = "invoiceStatusDAOKey";

    /** Represents the namespace of search bundle manager. */
    private static final String SEARCH_BUNDLE_MANAGER_NAMESPACE = "searchBundleManagerNamespace";

    /** Invoice selection query. */
    private static final String SELECT_FROM_INVOICE = "SELECT * FROM invoice";

    /**
     * <p>
     * This is the connectionFactory that is used to acquire a connection to the persistent store when it is
     * needed. With DBConnectionFactory is possible to configure the JBoss Transaction DataSource
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not accessed.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: All methods of this class.
     * </p>
     * <p>
     * Valid Values: Not null implementation.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * This is the id generator that is used to generate an id for any new Invoice that are added to the persistent
     * store.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not accessed.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: addInvoice
     * </p>
     * <p>
     * Valid Values: Nulls, or Strings that are not empty
     * </p>
     */
    private final IDGenerator idGenerator;

    /**
     * <p>
     * This is the SearchBundleManager used to search the database.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed.
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: searchInvoices
     * </p>
     * <p>
     * Valid Values: Non-null SearchBundleManager object.
     * </p>
     */
    private final SearchBundleManager searchBundleManager;

    /**
     * <p>
     * This is the AuditManager from the Time Tracker Audit component that is used to perform audits upon a
     * database change.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: addInvoice, updateInvoice
     * </p>
     * <p>
     * Valid Values: Non-null AuditManager object.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * This is the ExpenseManager from the Time Tracker Expense component for perform all operations upon the
     * tables involved with this component.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: addInvoice
     * </p>
     * <p>
     * Valid Values: Non-null.
     * </p>
     */
    private final ExpenseEntryManager expenseManager;

    /**
     * <p>
     * This is the FixedBillingManager from the Time Tracker Time component for perform all operations upon the
     * tables involved with this component.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: addInvoice
     * </p>
     * <p>
     * Valid Values: Non-null.
     * </p>
     */
    private final FixedBillingEntryManager fixedBillingEntryManager;

    /**
     * <p>
     * This is the manager instance that is used to retrieve information about a project. It's used for retrieve
     * all entries.
     * </p>
     * <p>
     * Initial Value: From the constructor
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: canCreateInvoice
     * </p>
     * <p>
     * Valid Values: Non-Null
     * </p>
     */
    private final ProjectUtility projectUtility;

    /**
     * <p>
     * This is the ServiceDetailManager from the Time Tracker Service Detail component for perform all operations
     * upon the tables involved with this component. This manager represent the time entries, with this manager is
     * possible to update indirectly all time entries.
     * </p>
     * <p>
     * Initial Value: Initialized in Constructor
     * </p>
     * <p>
     * Accessed In: Not Accessed
     * </p>
     * <p>
     * Modified In: Not Modified.
     * </p>
     * <p>
     * Utilized In: addInvoice
     * </p>
     * <p>
     * Valid Values: Non-null.
     * </p>
     * <p>
     * </p>
     */
    private final ServiceDetailManager serviceDetailManager;

    /**
     * <p>
     * This is the DAO instance that is used to retrieve information about a invoice status. It's used for retrieve
     * the correct invoice status.
     * </p>
     * <p>
     * Initial Value: From the constructor
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: getInvoice,getAllInvoice,searchInvoices
     * </p>
     * <p>
     * Valid Values: Non-Null
     * </p>
     */
    private final InvoiceStatusDAO invoiceStatusDAO;

    /**
     * <p>
     * This is the manager instance that is used to manager information about payment term.
     * </p>
     * <p>
     * Initial Value: From the constructor
     * </p>
     * <p>
     * Accessed In: No Access
     * </p>
     * <p>
     * Modified In: Not Modified
     * </p>
     * <p>
     * Utilized In: getInvoice,getAllInvoice,searchInvoices
     * </p>
     * <p>
     * Valid Values: Non-Null
     * </p>
     */
    private final CommonManager commonManager;

    /**
     * <p>
     * Constructor. Load the configuration values from default namespace.
     * </p>
     *
     * @throws InvoiceConfigurationException
     *             if any configured value is invalid or any required value is missing, it is also used to wrap the
     *             exceptions from ConfigManager
     */
    public InformixInvoiceDAO() throws InvoiceConfigurationException {
        this(InformixInvoiceDAO.class.getName());
    }

    /**
     * <p>
     * <strong>Usage:</strong> Constructor to load configuration values from the given namespace.
     * </p>
     *
     * @param namespace
     *            the namespace to load configuration values
     *
     * @throws IllegalArgumentException
     *             if argument is null or empty string
     * @throws InvoiceConfigurationException
     *             if any configured value is invalid or any required value is missing, it is also used to wrap the
     *             exceptions from ConfigManager
     */
    public InformixInvoiceDAO(String namespace) throws InvoiceConfigurationException {
        ArgumentCheckUtil.checkNotNullAndNotEmpty("namespace", namespace);

        // gets object factory
        ObjectFactory of =
            ConfigUtil.createObjectFactory(ConfigUtil.getRequiredProperty(namespace, OBJECT_FACTORY_NAMESPACE));

        // creates all fields
        auditManager =
            (AuditManager) ConfigUtil.createObject(of, namespace, AUDIT_MANAGER_KEY, AuditManager.class);
        try {
            idGenerator =
                IDGeneratorFactory.getIDGenerator(ConfigUtil.getRequiredProperty(namespace, ID_GENERATOR_KEY));
        } catch (IDGenerationException e) {
            throw new InvoiceConfigurationException("Error creating id generator", e);
        }
        expenseManager =
            (ExpenseEntryManager) ConfigUtil.createObject(of, namespace, EXPENSE_MANAGER_KEY,
                ExpenseEntryManager.class);
        fixedBillingEntryManager =
            (FixedBillingEntryManager) ConfigUtil.createObject(of, namespace, FIXED_BILLING_MANAGER_KEY,
                FixedBillingEntryManager.class);
        connectionFactory =
            (DBConnectionFactory) ConfigUtil.createObject(of, namespace, DB_CONNECTION_FACTORY_KEY,
                DBConnectionFactory.class);
        projectUtility =
            (ProjectUtility) ConfigUtil.createObject(of, namespace, PROJECT_UTILITY_KEY, ProjectUtility.class);
        serviceDetailManager =
            (ServiceDetailManager) ConfigUtil.createObject(of, namespace, SERVICE_DETAIL_MANAGER_KEY,
                ServiceDetailManager.class);
        commonManager =
            (CommonManager) ConfigUtil.createObject(of, namespace, COMMON_MANAGER_KEY, CommonManager.class);
        invoiceStatusDAO =
            (InvoiceStatusDAO) ConfigUtil.createObject(of, namespace, INVOICE_STATUS_DAO_KEY,
                InvoiceStatusDAO.class);

        try {
            searchBundleManager =
                new SearchBundleManager(ConfigUtil.getRequiredProperty(namespace, SEARCH_BUNDLE_MANAGER_NAMESPACE));
        } catch (SearchBuilderConfigurationException e) {
            throw new InvoiceConfigurationException("Error creating search bundle manager", e);
        }
    }

    /**
     * <p>
     * Adds the specified invoice into the persistent store. Unique invoice id will automatically be generated and
     * assigned to the invoice. There is also the option to perform an audit. If an audit is specified, then the
     * audit must be rolled back in the case that the operation fail.
     * </p>
     *
     * @param invoice
     *            invoice for which the operation should be performed.
     * @param audit
     *            Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException
     *             if invoice is null or if the invoice id is set
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public void addInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException {
        ArgumentCheckUtil.checkNotNull("invoice", invoice);
        checkInvoice(invoice);

        if (isIdSet(invoice)) {
            throw new IllegalArgumentException("The id of the invoice has already been set");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection(connectionFactory);
            invoice.setId(idGenerator.getNextID());

            statement =
                connection.prepareStatement("INSERT INTO invoice (invoice_id, project_id, creation_date, "
                    + "creation_user, modification_date, modification_user, salestax, payment_terms_id, "
                    + "invoice_number, po_number, invoice_date, due_date, paid, company_id, invoice_status_id) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            DBUtil.execute(statement, new Object[] {new Long(invoice.getId()), new Long(invoice.getProjectId()),
                invoice.getCreationDate(), invoice.getCreationUser(), invoice.getModificationDate(),
                invoice.getModificationUser(), invoice.getSalesTax(), new Long(invoice.getPaymentTerm().getId()),
                invoice.getInvoiceNumber(), invoice.getPurchaseOrderNumber(), invoice.getInvoiceDate(),
                invoice.getDueDate(), new Boolean(invoice.isPaid()), new Long(invoice.getCompanyId()),
                new Long(invoice.getInvoiceStatus().getId())}, 1);

            InvoiceServiceDetail[] serviceDetails = invoice.getServiceDetails();
            ExpenseEntry[] expenseEntries = invoice.getExpenseEntries();
            FixedBillingEntry[] fixedBillingEntries = invoice.getFixedBillingEntries();
            Date now = new Date();

            for (int i = 0; i < serviceDetails.length; ++i) {
                serviceDetails[i].setInvoice(invoice);
                serviceDetails[i].setModificationDate(now);
                serviceDetails[i].setModificationUser(invoice.getCreationUser());
            }
            for (int i = 0; i < expenseEntries.length; ++i) {
                expenseEntries[i].setInvoiceId(invoice.getId());
                expenseEntries[i].setModificationDate(now);
                expenseEntries[i].setModificationUser(invoice.getCreationUser());
            }
            for (int i = 0; i < fixedBillingEntries.length; ++i) {
                fixedBillingEntries[i].setInvoiceId(invoice.getId());
                fixedBillingEntries[i].setModificationDate(now);
                fixedBillingEntries[i].setModificationUser(invoice.getCreationUser());
            }

            if (serviceDetails.length != 0) {
                serviceDetailManager.addServiceDetails(serviceDetails, audit);
            }
            if (expenseEntries.length != 0) {
                expenseManager.updateEntries(expenseEntries, audit);
            }
            if (fixedBillingEntries.length != 0) {
                fixedBillingEntryManager.updateFixedBillingEntries(fixedBillingEntries, audit);
            }

            if (audit) {
                audit(null, invoice);
            }

            invoice.setChanged(false);

        } catch (IDGenerationException e) {
            throw new InvoiceDataAccessException("Can't generate id for the object", e);
        } catch (SQLException e) {
            throw new InvoiceDataAccessException("Error when inserting the invoice to the database", e);
        } catch (InsufficientDataException e) {
            throw new InvoiceDataAccessException("Can't insert expense entries information", e);
        } catch (PersistenceException e) {
            throw new InvoiceDataAccessException("Can't insert expense entries information", e);
        } catch (com.topcoder.timetracker.entry.fixedbilling.DataAccessException e) {
            throw new InvoiceDataAccessException("Can't insert fixed billing entries information", e);
        } catch (DataAccessException e) {
            throw new InvoiceDataAccessException("Can't insert service details information", e);
        } catch (TransactionCreationException e) {
            throw new InvoiceDataAccessException("Can't insert service details information", e);
        } finally {
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Checks whether the invoice is valid or not.
     *
     * @param invoice
     *            the given invoice
     *
     * @throws IllegalArgumentException
     *             if the invoice is not valid
     */
    private void checkInvoice(Invoice invoice) {
        if (invoice.getProjectId() == Long.MIN_VALUE) {
            throw new IllegalArgumentException("The invoice is not valid, projectId has to be set");
        }
        if (invoice.getCreationDate() == null) {
            throw new IllegalArgumentException("The invoice is not valid, creationDate has to be set");
        }
        if (invoice.getCreationUser() == null) {
            throw new IllegalArgumentException("The invoice is not valid, creationUser has to be set");
        }
        if (invoice.getModificationDate() == null) {
            throw new IllegalArgumentException("The invoice is not valid, modificationDate has to be set");
        }
        if (invoice.getModificationUser() == null) {
            throw new IllegalArgumentException("The invoice is not valid, modificationUser has to be set");
        }
        if (invoice.getSalesTax() == null) {
            throw new IllegalArgumentException("The invoice is not valid, salesTax has to be set");
        }
        if (invoice.getPaymentTerm() == null) {
            throw new IllegalArgumentException("The invoice is not valid, paymentTerms has to be set");
        }
        if (invoice.getInvoiceDate() == null) {
            throw new IllegalArgumentException("The invoice is not valid, invoiceDate has to be set");
        }
        if (invoice.getDueDate() == null) {
            throw new IllegalArgumentException("The invoice is not valid, dueDate has to be set");
        }
        if (invoice.getCompanyId() == Long.MIN_VALUE) {
            throw new IllegalArgumentException("The invoice is not valid, companyId has to be set");
        }
        if (invoice.getInvoiceStatus() == null) {
            throw new IllegalArgumentException("The invoice is not valid, invoiceStatus has to be set");
        }
        if (invoice.getExpenseEntries() == null) {
            throw new IllegalArgumentException("The invoice is not valid, expense entries has to be set");
        }
        if (invoice.getFixedBillingEntries() == null) {
            throw new IllegalArgumentException("The invoice is not valid, fixed billing entries has to be set");
        }
        if (invoice.getServiceDetails() == null) {
            throw new IllegalArgumentException("The invoice is not valid, service details has to be set");
        }
    }

    /**
     * Checks whether the id of the given invoice has been set or not.
     *
     * @param invoice
     *            the given invoice
     *
     * @return true if it's already been set, false otherwise
     */
    private boolean isIdSet(Invoice invoice) {
        return invoice.getId() != -1;
    }

    /**
     * Audits the change.
     *
     * @param oldInvoice
     *            the old invoice
     * @param newInvoice
     *            the new invoice
     *
     * @throws InvoiceDataAccessException
     *             if the audit is failed
     */
    private void audit(Invoice oldInvoice, Invoice newInvoice) throws InvoiceDataAccessException {
        long entityId = -1;
        String creationUser = "";
        Date creationDate = null;
        int auditType = 0;
        long projectId = -1;
        entityId = newInvoice.getId();
        // on update, the value is modificationUser, on insert, creation and modification should be same, so
        // using modificationUser is generic solution
        creationUser = newInvoice.getModificationUser();
        creationDate = newInvoice.getModificationDate();
        if (oldInvoice == null) {
            auditType = AuditType.INSERT;
        } else {
            auditType = AuditType.UPDATE;
        }
        projectId = newInvoice.getProjectId();

        AuditHeader header = new AuditHeader();
        header.setApplicationArea(ApplicationArea.TT_INVOICE);
        header.setTableName("invoice");

        header.setEntityId(entityId);
        header.setCreationUser(creationUser);
        header.setCreationDate(new Timestamp(creationDate.getTime()));
        header.setActionType(auditType);
        header.setCompanyId(newInvoice.getCompanyId());
        header.setProjectId(projectId);

        AuditDetail[] details = new AuditDetail[15];

        details[0] =
            createAuditDetail("invoice_id", oldInvoice == null ? null : oldInvoice.getId() + "", ""
                + newInvoice.getId());
        details[1] =
            createAuditDetail("project_id", oldInvoice == null ? null : oldInvoice.getProjectId() + "", ""
                + newInvoice.getProjectId());
        details[2] =
            createAuditDetail("creation_date", oldInvoice == null ? null : oldInvoice.getCreationDate() + "",
                newInvoice.getCreationDate() + "");
        details[3] =
            createAuditDetail("creation_user", oldInvoice == null ? null : oldInvoice.getCreationUser(),
                newInvoice.getCreationUser());
        details[4] =
            createAuditDetail("modification_date", oldInvoice == null ? null : oldInvoice.getModificationDate()
                + "", newInvoice.getModificationDate() + "");
        details[5] =
            createAuditDetail("modification_user", oldInvoice == null ? null : oldInvoice.getModificationUser(),
                newInvoice.getModificationUser());
        details[6] =
            createAuditDetail("salextax", oldInvoice == null ? null : oldInvoice.getSalesTax() + "", ""
                + newInvoice.getSalesTax());
        details[7] =
            createAuditDetail("payment_terms_id", oldInvoice == null ? null : oldInvoice.getPaymentTerm().getId()
                + "", newInvoice.getPaymentTerm().getId() + "");
        details[8] =
            createAuditDetail("invoice_number", oldInvoice == null ? null : oldInvoice.getInvoiceNumber(),
                newInvoice.getInvoiceNumber());
        details[9] =
            createAuditDetail("po_number", oldInvoice == null ? null : oldInvoice.getPurchaseOrderNumber(),
                newInvoice.getPurchaseOrderNumber());
        details[10] =
            createAuditDetail("invoice_date", oldInvoice == null ? null : oldInvoice.getInvoiceDate() + "",
                newInvoice.getInvoiceDate() + "");
        details[11] =
            createAuditDetail("due_date", oldInvoice == null ? null : oldInvoice.getDueDate() + "", ""
                + newInvoice.getDueDate());
        details[12] =
            createAuditDetail("paid", oldInvoice == null ? null : oldInvoice.isPaid() + "", ""
                + newInvoice.isPaid());
        details[13] =
            createAuditDetail("company_id", oldInvoice == null ? null : oldInvoice.getCompanyId() + "", ""
                + newInvoice.getCompanyId());
        details[14] =
            createAuditDetail("invoice_status_id", oldInvoice == null ? null : ""
                + oldInvoice.getInvoiceStatus().getId(), newInvoice.getInvoiceStatus().getId() + "");

        header.setDetails(details);

        try {
            auditManager.createAuditRecord(header);
        } catch (AuditManagerException e) {
            throw new InvoiceDataAccessException("Can't do the audit correctly", e);
        }

    }

    /**
     * Creates audit detail.
     *
     * @param columnName
     *            the name of the column
     * @param oldValue
     *            the old value
     * @param newValue
     *            the new value
     *
     * @return the audit detail
     */
    private AuditDetail createAuditDetail(String columnName, String oldValue, String newValue) {
        AuditDetail detail = new AuditDetail();
        detail.setColumnName(columnName);
        detail.setOldValue(oldValue);
        detail.setNewValue(newValue);

        return detail;
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided Invoice. There is also the
     * option to perform an audit. If an audit is specified, then the audit must be rolled back in the case that
     * the operation fails.
     * </p>
     *
     * @param invoice
     *            invoice for which the operation should be performed
     * @param audit
     *            Indicates whether an audit should be performed
     *
     * @throws InvoiceUnrecognizedEntityException
     *             if the invoices with the provided id was not found in the data store or id is not set
     * @throws IllegalArgumentException
     *             if invoice is null
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public void updateInvoice(Invoice invoice, boolean audit) throws InvoiceDataAccessException {
        ArgumentCheckUtil.checkNotNull("invoice", invoice);
        checkInvoice(invoice);

        if (!isIdSet(invoice)) {
            throw new InvoiceUnrecognizedEntityException(invoice.getId(), "The id of the invoice is not set");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection(connectionFactory);
            statement =
                connection
                    .prepareStatement("UPDATE invoice SET project_id=?, creation_date=?, "
                        + "creation_user=?, modification_date=?, modification_user=?, salestax=?, payment_terms_id=?, "
                        + "invoice_number=?, po_number=?, invoice_date=?, due_date=?, paid=?, company_id=?, "
                        + "invoice_status_id=? " + "WHERE invoice_id=?");

            DBUtil.execute(statement, new Object[] {new Long(invoice.getProjectId()), invoice.getCreationDate(),
                invoice.getCreationUser(), invoice.getModificationDate(), invoice.getModificationUser(),
                invoice.getSalesTax(), new Long(invoice.getPaymentTerm().getId()), invoice.getInvoiceNumber(),
                invoice.getPurchaseOrderNumber(), invoice.getInvoiceDate(), invoice.getDueDate(),
                new Boolean(invoice.isPaid()), new Long(invoice.getCompanyId()),
                new Long(invoice.getInvoiceStatus().getId()), new Long(invoice.getId())}, 1);

            if (audit) {
                audit(getInvoice(invoice.getId()), invoice);
            }

            invoice.setChanged(false);

        } catch (SQLException e) {
            throw new InvoiceDataAccessException("Error when updating the invoice to the database", e);
        } finally {
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * <p>
     * Retrieves an Invoice that reflects the data in the persistent store on the Time Tracker Invoice with the
     * specified invoice Id.
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
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice getInvoice(long invoiceId) throws InvoiceDataAccessException {
        Connection connection = null;
        ResultSet rs = null;
        Statement statement = null;
        try {
            connection = DBUtil.getConnection(connectionFactory);
            statement = connection.createStatement();

            rs = DBUtil.executeQuery(statement, SELECT_FROM_INVOICE + " WHERE invoice_id=" + invoiceId);

            if (rs.next()) {
                return convertResultSetToInvoice(rs);
            } else {
                throw new InvoiceUnrecognizedEntityException(invoiceId, "The invoice with id [" + invoiceId
                    + "] can't be found");
            }

        } catch (SQLException e) {
            throw new InvoiceDataAccessException("Database-related error happens", e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }
    }

    /**
     * Converts a result set to an <code>Invoice</code> object.
     *
     * @param rs
     *            the given <code>ResultSet</code>
     *
     * @return the invoice
     *
     * @throws InvoiceDataAccessException
     *             if there is some errors when converting the result set
     */
    private Invoice convertResultSetToInvoice(ResultSet rs) throws InvoiceDataAccessException {

        try {
            Invoice invoice = new Invoice();
            long invoiceId = rs.getLong("invoice_id");
            invoice.setId(invoiceId);
            invoice.setProjectId(rs.getLong("project_id"));
            invoice.setCreationDate(rs.getDate("creation_date"));
            invoice.setCreationUser(rs.getString("creation_user"));
            invoice.setModificationDate(rs.getDate("modification_date"));
            invoice.setModificationUser(rs.getString("modification_user"));
            invoice.setSalesTax(rs.getBigDecimal("salestax"));
            invoice.setPaymentTerm(commonManager.retrievePaymentTerm(rs.getLong("payment_terms_id")));
            invoice.setInvoiceNumber(rs.getString("invoice_number"));
            invoice.setPurchaseOrderNumber(rs.getString("po_number"));
            invoice.setInvoiceDate(rs.getDate("invoice_date"));
            invoice.setDueDate(rs.getDate("due_date"));
            invoice.setPaid(rs.getBoolean("paid"));
            invoice.setCompanyId(rs.getLong("company_id"));
            invoice.setInvoiceStatus(invoiceStatusDAO.getInvoiceStatus(rs.getLong("invoice_status_id")));

            Filter fixedBillingFilterByInvoiceId =
                fixedBillingEntryManager.getFixedBillingEntryFilterFactory().createInvoiceIdFilter(invoiceId);
            invoice.setFixedBillingEntries(fixedBillingEntryManager
                .searchFixedBillingEntries(fixedBillingFilterByInvoiceId));

            invoice.setServiceDetails(serviceDetailManager.retrieveServiceDetails(invoiceId));

            Criteria criteria = FieldMatchCriteria.getExpenseEntryInvoiceIdMatchCriteria((int) invoiceId);
            invoice.setExpenseEntries(expenseManager.searchEntries(criteria));

            invoice.setChanged(false);

            return invoice;
        } catch (SQLException e) {
            throw new InvoiceDataAccessException("Exception when converting result set to Invoice", e);
        } catch (CommonManagementException e) {
            throw new InvoiceDataAccessException("Exception when retrieving PaymentTerm", e);
        } catch (com.topcoder.timetracker.entry.fixedbilling.DataAccessException e) {
            throw new InvoiceDataAccessException("Can't retrieve fixed billing entries", e);
        } catch (DataAccessException e) {
            throw new InvoiceDataAccessException("Can't retrieve invoice's service details", e);
        } catch (TransactionCreationException e) {
            throw new InvoiceDataAccessException("Can't retrieve invoice's service details", e);
        } catch (PersistenceException e) {
            throw new InvoiceDataAccessException("Can't retrieve expense entries", e);
        }
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
        ArgumentCheckUtil.checkNotNull("filter", filter);
        SearchBundle searchBundle = null;
        if (depth == InvoiceSearchDepth.INVOICE_ALL) {
            searchBundle = searchBundleManager.getSearchBundle("INVOICE_ALL");
        } else if (depth == InvoiceSearchDepth.INVOICE_ONLY) {
            searchBundle = searchBundleManager.getSearchBundle("INVOICE_ONLY");
        } else {
            throw new IllegalArgumentException("the value of depth is not valid");
        }

        try {
            CustomResultSet result = (CustomResultSet) searchBundle.search(filter);
            List invoices = new ArrayList();
            while (result.next()) {
                invoices.add(getInvoice(result.getLong(1)));
            }

            return (Invoice[]) invoices.toArray(new Invoice[invoices.size()]);
        } catch (SearchBuilderException e) {
            throw new InvoiceDataAccessException("There is an error when searching with defined search bundle", e);
        } catch (InvalidCursorStateException e) {
            throw new InvoiceDataAccessException("There is an error when searching with defined search bundle", e);
        }

    }

    /**
     * <p>
     * Retrieves all the invoices that are currently in the persistent store.
     * </p>
     *
     * @return An array of all invoices retrieved from the persistent store
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     */
    public Invoice[] getAllInvoices() throws InvoiceDataAccessException {
        Connection connection = null;
        ResultSet allInvoicesRs = null;
        Statement statement = null;

        try {
            connection = DBUtil.getConnection(connectionFactory);
            statement = connection.createStatement();

            allInvoicesRs = DBUtil.executeQuery(statement, SELECT_FROM_INVOICE);

            List invoiceList = convertResultSetToInvoices(allInvoicesRs);

            return (Invoice[]) invoiceList.toArray(new Invoice[invoiceList.size()]);
        } catch (SQLException e) {
            throw new InvoiceDataAccessException("Database-related error happens", e);
        } finally {
            DBUtil.closeResultSet(allInvoicesRs);
            DBUtil.closeStatement(statement);
            DBUtil.closeConnection(connection);
        }

    }

    /**
     * Converts a result set to a list of <code>Invoice</code>s object.
     *
     * @param rs
     *            the given <code>ResultSet</code>
     *
     * @return the list of invoices
     *
     * @throws InvoiceDataAccessException
     *             if there is some errors when converting the result set
     */
    private List convertResultSetToInvoices(ResultSet rs) throws InvoiceDataAccessException {
        List tempResult = new ArrayList();

        try {
            while (rs.next()) {
                tempResult.add(convertResultSetToInvoice(rs));
            }
            return tempResult;
        } catch (SQLException e) {
            throw new InvoiceDataAccessException("Database-related error happens", e);
        }
    }

    /**
     * <p>
     * Looks at the particular project and check all entries (Time, Expense and Fixed billing) if there are any
     * that are PENDING then it will return false, else it will return true.
     * </p>
     *
     * @param projectId
     *            the project's id to check
     *
     * @return true if can create Invoice and false otherwise
     *
     * @throws InvoiceDataAccessException
     *             if a problem occurs while accessing the persistent store
     * @throws InvoiceUnrecognizedEntityException
     *             if project with the provided id was not found in the data store
     * @throws IllegalArgumentException
     *             if projectId < 0
     */
    public boolean canCreateInvoice(long projectId) throws InvoiceDataAccessException {
        ArgumentCheckUtil.checkNotNegative("projectId", projectId);

        try {
            // get all fixed billing entries for the project
            long[] fixedBillingEntryIds =
                projectUtility.retrieveEntriesForProject(projectId, EntryType.FIXED_BILLING_ENTRY);
            BaseEntry[] fixedBillingEntries = (fixedBillingEntryIds.length != 0) ?
                    fixedBillingEntryManager.getFixedBillingEntries(fixedBillingEntryIds) : new FixedBillingEntry[0];

            // if there is a pending entries, return false
            if (checkPending(fixedBillingEntries)) {
                return false;
            }

            // get all expense entries for the project
            long[] expenseEntryIds = projectUtility.retrieveEntriesForProject(projectId, EntryType.EXPENSE_ENTRY);
            ExpenseEntry[] expenseEntries = (expenseEntryIds.length != 0) ?
                    expenseManager.retrieveEntries(expenseEntryIds) : new ExpenseEntry[0];

            // if there is a pending entries, return false
            if (checkPending(expenseEntries)) {
                return false;
            }

            // get all time entries for the project
            long[] timeEntryIds = projectUtility.retrieveEntriesForProject(projectId, EntryType.TIME_ENTRY);
            List timeEntryIdList = new ArrayList();
            for (int i = 0; i < timeEntryIds.length; i++) {
                timeEntryIdList.add(new Long(timeEntryIds[i]));
            }
            List timeEntryList = new ArrayList();
            InvoiceServiceDetail[] allServiceDetails = serviceDetailManager.retrieveAllServiceDetails();
            for (int i = 0; i < allServiceDetails.length; i++) {
                if (timeEntryIdList.contains(new Long(allServiceDetails[i].getTimeEntry().getId()))) {
                    timeEntryList.add(allServiceDetails[i].getTimeEntry());
                }
            }

            // if there is a pending entries, return false
            if (checkPending((BaseEntry[]) timeEntryList.toArray(new BaseEntry[timeEntryList.size()]))) {
                return false;
            }

            // otherwise the invoice for the project is ready to be created
            return true;

        } catch (PersistenceException e) {
            throw new InvoiceDataAccessException("Exception when retrieving expense entries", e);
        } catch (com.topcoder.timetracker.entry.fixedbilling.DataAccessException e) {
            throw new InvoiceDataAccessException("Exception when retrieving fixed billing entries", e);
        } catch (DataAccessException e) {
            throw new InvoiceDataAccessException("Exception when retrieving time entries", e);
        } catch (TransactionCreationException e) {
            throw new InvoiceDataAccessException("Exception when retrieving time entries", e);
        } catch (UnrecognizedEntityException e) {
            throw new InvoiceUnrecognizedEntityException(projectId, "There is no such project id");
        } catch (com.topcoder.timetracker.project.DataAccessException e) {
            throw new InvoiceDataAccessException("Exception when retrieving entries", e);
        }
    }

    /**
     * Checks if there is still a Pending entry in the array of entries.
     *
     * @param entries
     *            the given array of entries
     *
     * @return true if there is a PENDING entry, false otherwise
     */
    private boolean checkPending(BaseEntry[] entries) {
        for (int i = 0; i < entries.length; i++) {
            if ("Pending".equalsIgnoreCase(entries[i].getDescription())) {
                return true;
            }
        }
        return false;
    }
}
