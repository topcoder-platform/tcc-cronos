/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.expense.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.expense.ConfigurationException;
import com.topcoder.timetracker.entry.expense.ExpenseEntry;
import com.topcoder.timetracker.entry.expense.ExpenseEntryHelper;
import com.topcoder.timetracker.entry.expense.ExpenseStatusManager;
import com.topcoder.timetracker.entry.expense.ExpenseTypeManager;
import com.topcoder.timetracker.entry.expense.InsufficientDataException;
import com.topcoder.timetracker.entry.expense.criteria.Criteria;
import com.topcoder.timetracker.invoice.InvoiceException;
import com.topcoder.timetracker.invoice.InvoiceManager;
import com.topcoder.timetracker.rejectreason.RejectReason;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;

import com.topcoder.util.objectfactory.ObjectFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * This class is a concrete implementation of the <code>ExpenseEntryDAO</code> interface that uses an Informix database
 * as the data store. This implementation uses the DB Connection Factory component to obtain a connection to the
 * database, the GUID Generator component to create IDs for new Expense Entries. This class provides two constructors
 * that will use Config Manager and Object Factory components to obtain instances of the connection factory and audit
 * manager. One constructor will take a namespace, and the second will work with a default namespace. The expense
 * entries are stored in the database using the following DDL:
 * <pre>
 * CREATE TABLE expense_entry (
 *     expense_entry_id     integer NOT NULL,
 *     company_id           integer NOT NULL,
 *     invoice_id,
 *     expense_type_id      integer NOT NULL,
 *     expense_status_id    integer NOT NULL,
 *     description          VARCHAR(255),
 *     entry_date           datetime year to second NOT NULL,
 *     amount               MONEY NOT NULL,
 *     billable             smallint NOT NULL,
 *     creation_date        datetime year to second NOT NULL,
 *     creation_user        varchar(64) NOT NULL,
 *     modification_date    datetime year to second NOT NULL,
 *     modification_user    varchar(64) NOT NULL,
 *     mileage              INTEGER,
 *     PRIMARY KEY (expense_entry_id),
 *     FOREIGN KEY (invoice_id) REFERENCES invoice,
 *     FOREIGN KEY (company_id) REFERENCES company,
 *     FOREIGN KEY (expense_status_id) REFERENCES expense_status,
 *     FOREIGN KEY (expense_type_id) REFERENCES expense_type
 * );
 * </pre>
 * </p>
 *
 * <p>
 * This implementation is tailored to work in an EJB container and will rely on it to manage all transactions. To this
 * end, it does not implement any of the batch operations that give the caller the choice to select whether the
 * operations are atomic or not. In the case of the EJBs that come with this component, these will perform non-atomic
 * operations by using the single entity methods.
 * </p>
 *
 * <p>
 * As mentioned, the caller will have the chance to audit all writeable operations using the Audit Manager.
 * </p>
 *
 * <p>
 * <b>Thread Safety: </b>This class is immutable and thread-safe as long as the <code>ExpenseEntry</code> is no
 * manipulated by more than one thread.
 * </p>
 *
 * @author AleaActaEst, argolite, TCSDEVELOPER
 * @version 3.2
 */
public class InformixExpenseEntryDAO implements ExpenseEntryDAO {
    /** Represents the prepared SQL statement to add an expense entry. */
    private static final String ADD_ENTRY_SQL =
        "INSERT INTO expense_entry (expense_entry_id, company_id, client_id, project_id, invoice_id, expense_type_id, "
        + "expense_status_id, description, entry_date, amount, billable, creation_date, creation_user, "
        + "modification_date, modification_user, mileage) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to add into the exp_reject_reason table. */
    private static final String ADD_EXP_REJECT_REASON_SQL = "INSERT INTO exp_reject_reason (expense_entry_id,"
        + "reject_reason_id, creation_date, creation_user, modification_date, modification_user) "
        + "VALUES (?,?,?,?,?,?)";

    /** Represents the prepared SQL statement to update an expense entry. */
    private static final String UPDATE_ENTRY_SQL =
        "UPDATE expense_entry SET company_id=?, client_id=?, projectId=?, invoice_id=?, expense_type_id=?, expense_status_id=?, "
        + "description=?, entry_date=?, amount=?, billable=?, modification_date=?, modification_user=?, "
        + "mileage=? WHERE expense_entry_id=?";

    /** Represents the prepared SQL statement to delete an expense entry. */
    private static final String DELETE_ENTRY_SQL = "DELETE FROM expense_entry WHERE expense_entry_id=?";

    /** Represents the prepared SQL statement to delete the exp_reject_reason table. */
    private static final String DELETE_EXP_REJECT_REASON_SQL = "DELETE FROM exp_reject_reason "
        + "WHERE expense_entry_id=?";

    /** Represents the prepared SQL statement to delete all expense entries. */
    private static final String DELETE_ALL_ENTRY_SQL = "DELETE FROM expense_entry";

    /** Represents the prepared SQL statement to delete all the records in exp_reject_reason table. */
    private static final String DELETE_ALL_EXP_REJECT_REASON_SQL = "DELETE FROM exp_reject_reason";

    /** Represents the prepared SQL statement to check the existence of an expense entry. */
    private static final String EXIST_ENTRY_SQL = "SELECT 1 counts FROM expense_entry WHERE expense_entry_id=?";

    /** Represents the prepared SQL statement to get the reject reasons with given ExpenseEntriesID. */
    private static final String RETRIEVE_REJECT_REASON_SQL = "SELECT reject_reason_id " + "FROM exp_reject_reason "
        + "WHERE expense_entry_id=?";

    /** Represents the prepared SQL statement to get all expense entries, including their entry types and statuses. */
    private static final String RETRIEVE_ALL_ENTRY_SQL =
        "SELECT expense_entry.expense_entry_id, expense_entry.company_id, expense_entry.client_id, expense_entry.project_id, expense_entry.invoice_id, "
        + "expense_entry.expense_type_id, expense_entry.expense_status_id, expense_entry.description, "
        + "expense_entry.entry_date, expense_entry.amount, expense_entry.billable, expense_entry.creation_date, "
        + "expense_entry.creation_user, expense_entry.modification_date, expense_entry.modification_user,"
        + "expense_entry.mileage FROM expense_entry "
        + ", expense_type, expense_status "
        + "WHERE expense_entry.expense_type_id = expense_type.expense_type_id "
        + "AND expense_entry.expense_status_id = expense_status.expense_status_id";

    /** Represents the prepared SQL statement to check the expense type id is associated with the company id. */
    private static final String CHECK_COMPANY_ID_EXPENSE_TYPE_SQL = "Select 1 counts from comp_exp_type "
            + "where company_id=? and expense_type_id=?";

    /** Represents the prepared SQL statement to check the reject reason id is associated with the company id. */
    private static final String CHECK_COMPANY_ID_REJECT_REASON_SQL = "Select 1 counts from comp_rej_reason "
            + "where company_id=? and reject_reason_id=?";

    /** Represents the name for expense_entry table. */
    private static final String EXPENSE_ENTRY_TABLE = "expense_entry";

    /** Represents the column name for expense entry ID. */
    private static final String ID_COLUMN = "expense_entry_id";

    /** Represents the column name for company id. */
    private static final String COMPANY_ID_COLUMN = "company_id";
    
    /** Represents the column name for client id. */
    private static final String CLIENT_ID_COLUMN = "client_id";
    
    /** Represents the column name for project id. */
    private static final String PROJECT_ID_COLUMN = "project_id";

    /** Represents the column name for invoice id. */
    private static final String INVOICE_ID_COLUMN = "invoice_id";

    /** Represents the column name for the expense type ID of the expense entry. */
    private static final String TYPE_ID_COLUMN = "expense_type_id";

    /** Represents the column name for the expense status ID of the expense entry. */
    private static final String STATUS_ID_COLUMN = "expense_status_id";

    /** Represents the column name for expense entry description. */
    private static final String DESCRIPTION_COLUMN = "description";

    /** Represents the column name for expense entry date. */
    private static final String DATE_COLUMN = "entry_date";

    /** Represents the column name for expense entry amount. */
    private static final String AMOUNT_COLUMN = "amount";

    /** Represents the column name for a flag indicating whether the expense entry is billable. */
    private static final String BILLABLE_COLUMN = "billable";

    /** Represents the column name for expense entry creation date. */
    private static final String CREATION_DATE_COLUMN = "creation_date";

    /** Represents the column name for expense entry creation user. */
    private static final String CREATION_USER_COLUMN = "creation_user";

    /** Represents the column name for expense entry modification date. */
    private static final String MODIFICATION_DATE_COLUMN = "modification_date";

    /** Represents the column name for expense entry modification user. */
    private static final String MODIFICATION_USER_COLUMN = "modification_user";

    /** Represents the column name for expense entry mileage. */
    private static final String MILEAGE_COLUMN = "mileage";

    /** Represents the column name for the reject reason ID of the expense entry. */
    private static final String REASON_ID_COLUMN = "reject_reason_id";

    /** Represents the property name to retrieve the specification_namespace value. */
    private static final String SPECIFICATION_NAMESPACE_PROPERTY = "specification_namespace";

    /** Represents the property name to retrieve the db_connection_factory_key value. */
    private static final String DB_CONNECTION_FACTORY_KEY_PROPERTY = "db_connection_factory_key";

    /** Represents the property name to retrieve the connection_name value. */
    private static final String CONNECTION_NAME_PROPERTY = "connection_name";

    /** Represents the property name to retrieve the audit_manager_key value. */
    private static final String AUDIT_MANAGER_KEY_PROPERTY = "audit_manager_key";

    /** Represents the property name to retrieve the invoice_manager_key value. */
    private static final String INVOICE_MANAGER_KEY_PROPERTY = "invoice_manager_key";

    /** Represents the property name to retrieve the type_manager_key value. */
    private static final String EXPENSE_TYPE_MANAGER_KEY_PROPERTY = "type_manager_key";

    /** Represents the property name to retrieve the status_manager_key value. */
    private static final String EXPENSE_STATUS_MANAGER_KEY_PROPERTY = "status_manager_key";

    /** Represents the property name to retrieve the rejectreason_dao_key value. */
    private static final String REJECT_REASON_DAO_KEY_PROPERTY = "rejectreason_dao_key";

    /** Represents all the columns hold be the expense entry table. */
    private static final String[] ENTRY_COLUMNS = new String[] {
        ID_COLUMN, COMPANY_ID_COLUMN, CLIENT_ID_COLUMN, PROJECT_ID_COLUMN, INVOICE_ID_COLUMN, TYPE_ID_COLUMN, STATUS_ID_COLUMN, DESCRIPTION_COLUMN,
        DATE_COLUMN, AMOUNT_COLUMN, BILLABLE_COLUMN, CREATION_DATE_COLUMN, CREATION_USER_COLUMN,
        MODIFICATION_DATE_COLUMN, MODIFICATION_USER_COLUMN, MILEAGE_COLUMN
    };

    /**
     * <p>
     * Represents the connection producer name used to obtain a new database connection from DB connection factory.
     * </p>
     *
     * <p>
     * This variable is set in constructor. It is possible null, possible empty(trim'd) which means the default
     * connection name will be used.
     * </p>
     */
    private final String connectionName;

    /**
     * <p>
     * Represents the DB connection factory instance used to create connections.
     * </p>
     *
     * <p>
     * This variable is set in constructor. It is immutable and never be null.
     * </p>
     */
    private final DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Represents the invoice manager to be used to perform the CRUD operations of invoice instances.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null.
     * </p>
     */
    private final InvoiceManager invoiceManager;

    /**
     * <p>
     * Represents the expenseType manager to be used to perform the CRUD operations of expenseType instances.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null.
     * </p>
     */
    private final ExpenseTypeManager expenseTypeManager;

    /**
     * <p>
     * Represents the expenseStatus manager to be used to perform the CRUD operations of expenseStatus instances.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null.
     * </p>
     */
    private final ExpenseStatusManager expenseStatusManager;

    /**
     * <p>
     * Represents the rejectReason dao to be used to perform the CRUD operations of expenseReason instances.
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null.
     * </p>
     */
    private final RejectReasonDAO rejectReasonDAO;

    /**
     * <p>
     * Represents the audit manager to be used to perform audit of the operations performed through this class. The
     * user will be able to control if the audit is done or not through the auditFlag boolean parameter on specific
     * method (which modify some data - thus read method are not auditable).
     * </p>
     *
     * <p>
     * This variable is immutable, it is initialized in constructor and will never be null.
     * </p>
     */
    private final AuditManager auditManager;

    /**
     * <p>
     * Creates a new instance of <code>InformixExpenseEntryDAO</code> class with default namespace to load the
     * connection name, DB connection factory and the audit manager instance.
     * </p>
     *
     * @throws ConfigurationException if any exception prevents creating the connection factory or audit manager
     *         successfully.
     */
    public InformixExpenseEntryDAO() throws ConfigurationException {
        this(InformixExpenseEntryDAO.class.getName());
    }

    /**
     * <p>
     * Creates a new instance of <code>InformixExpenseEntryDAO</code> class with given namespace to load the connection
     * name, DB connection factory and the audit manager instance.
     * </p>
     *
     * @param namespace the given namespace to load the connection name, DB connection factory and the audit manager
     *        instance.
     *
     * @throws IllegalArgumentException if the argument is the empty string or if the argument is null.
     * @throws ConfigurationException if any exception prevents creating the connection factory or audit manager
     *         successfully.
     */
    public InformixExpenseEntryDAO(String namespace) throws ConfigurationException {
        ExpenseEntryHelper.validateString(namespace, "namespace");

        String specificationNamespace = ExpenseEntryHelper.getStringPropertyValue(namespace,
                SPECIFICATION_NAMESPACE_PROPERTY, true);
        String dbConnectionFactoryKey = ExpenseEntryHelper.getStringPropertyValue(namespace,
                DB_CONNECTION_FACTORY_KEY_PROPERTY, true);
        String auditManagerKey = ExpenseEntryHelper.getStringPropertyValue(namespace, AUDIT_MANAGER_KEY_PROPERTY, true);
        String expenseTypeManagerKey = ExpenseEntryHelper.getStringPropertyValue(namespace,
                EXPENSE_TYPE_MANAGER_KEY_PROPERTY, true);
        String expenseStatusManagerKey = ExpenseEntryHelper.getStringPropertyValue(namespace,
                EXPENSE_STATUS_MANAGER_KEY_PROPERTY, true);
        String invoiceManagerKey = ExpenseEntryHelper.getStringPropertyValue(namespace, INVOICE_MANAGER_KEY_PROPERTY,
                true);
        String rejectReasonDAOKey = ExpenseEntryHelper.getStringPropertyValue(namespace,
                REJECT_REASON_DAO_KEY_PROPERTY, true);
        this.connectionName = ExpenseEntryHelper.getStringPropertyValue(namespace, CONNECTION_NAME_PROPERTY, false);

        ObjectFactory objectFactory = ExpenseEntryHelper.createObjectFactory(specificationNamespace);
        this.connectionFactory = (DBConnectionFactory) ExpenseEntryHelper.createObject(objectFactory,
                dbConnectionFactoryKey, DBConnectionFactory.class);

        // create the manager instances
        this.auditManager = (AuditManager) ExpenseEntryHelper.createObject(objectFactory, auditManagerKey,
                AuditManager.class);
        this.expenseTypeManager = (ExpenseTypeManager) ExpenseEntryHelper.createObject(objectFactory,
                expenseTypeManagerKey, ExpenseTypeManager.class);
        this.expenseStatusManager = (ExpenseStatusManager) ExpenseEntryHelper.createObject(objectFactory,
                expenseStatusManagerKey, ExpenseStatusManager.class);
        this.invoiceManager = (InvoiceManager) ExpenseEntryHelper.createObject(objectFactory, invoiceManagerKey,
                InvoiceManager.class);
        this.rejectReasonDAO = (RejectReasonDAO) ExpenseEntryHelper.createObject(objectFactory, rejectReasonDAOKey,
                RejectReasonDAO.class);
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * <p>
     * If the id of the argument is not positive then an id for the entry will be generated using GUID Generator
     * component. If the id is positive then it means that the user(application) has assigned an id for this entry.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. For each contained reject reason, a row needs to be
     * inserted in the exp_reject_reason table. The company id fieled added and in the component level, it will check
     * the expense type and each reject reason is associated with the company id.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input entry instance will be updated to the current datetime if
     * the new entry is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param entry the expense entry to be added to the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if the argument is null.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException if error occurs when adding the expense entry to the persistence, or if the expsen
     *         entry's company id or status id or type id or invoice id is not in persistence, or if the expense type
     *         or any of the reject reason is not matched with the compay id.
     */
    public boolean addEntry(ExpenseEntry entry, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(entry, "entry");
        ExpenseEntryHelper.validateNewInfo(entry, "entry");
        ExpenseEntryHelper.validateExpenseEntryData(entry);

        if (entry.getId() <= 0) {
            entry.setId(ExpenseEntryHelper.generateId());
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return addEntry(conn, entry, auditFlag);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the database using the given connection. Audit actions will be
     * done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. For each contained reject reason, a row needs to be
     * inserted in the exp_reject_reason table. The company id fieled added and in the component level, it will check
     * the expense type and each reject reason is associated with the company id.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input entry instance will be updated to the current datetime if
     * the new entry is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection.
     * @param entry the expense entry to be added to the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the ID does not exist in database and the expense entry is added;
     *         <code>false</code> otherwise.
     *
     * @throws PersistenceException if error occurs when adding the expense entry to the persistence, or if the expsen
     *         entry's company id or status id or type id or invoice id is not in persistence, or if the expense type
     *         or any of the reject reason is not matched with the compay id.
     */
    private boolean addEntry(Connection conn, ExpenseEntry entry, boolean auditFlag) throws PersistenceException {

        // Check if the ID already exists in database
        if (existEntry(conn, entry.getId())) {
            return false;
        }

        checkCompanyIdMatched(conn, entry);

        if (!(insertEntry(conn, entry) > 0)) {
            return false;
        }

        if (auditFlag) {
            createAudit(null, entry, AuditType.INSERT);
        }

        return true;
    }

    /**
     * <p>
     * Checks whether the expense type or any of the reject reason is not matched with the compay id.
     * </p>
     *
     * @param conn the connection.
     * @param entry the expense entry to check.
     *
     * @throws PersistenceException if the expense type or any of the reject reason is not matched with the compay id.
     */
    private void checkCompanyIdMatched(Connection conn, ExpenseEntry entry) throws PersistenceException {
        Map rejectReasons = entry.getRejectReasons();

        if (rejectReasons != null) {
            for (Iterator iter = rejectReasons.values().iterator(); iter.hasNext();) {
                RejectReason rejectReason = (RejectReason) iter.next();

                if (!checkRejectReason(conn, entry.getCompanyId(), rejectReason.getId())) {
                    throw new PersistenceException("The reject reason is not matched with the compay id.");
                }
            }
        }

        if (!checkExpenseType(conn, entry.getCompanyId(), entry.getExpenseType().getId())) {
            throw new PersistenceException("The expense type id is not matched with the compay id.");
        }
    }

    /**
     * <p>
     * Creates audit with TT Audit component with the given oldValue and newValue.
     * </p>
     *
     * @param oldValue old value
     * @param newValue new value
     * @param auditType the audit type.
     *
     * @throws PersistenceException if error occurs in Audit component.
     */
    private void createAudit(ExpenseEntry oldValue, ExpenseEntry newValue, int auditType) throws PersistenceException {
        AuditHeader header = new AuditHeader();

        //columns changed detail
        String[] oldColumns = parseValues(oldValue);
        String[] newColumns = parseValues(newValue);
        List details = new ArrayList();

        for (int i = 0; i < oldColumns.length; i++) {
            //when in update type, the non-changed columns will not be audited
            if ((auditType == AuditType.UPDATE)) {
                if (oldColumns[i] == null) {
                    if (newColumns[i] == null) {
                        continue;
                    }
                } else if (oldColumns[i].equals(newColumns[i])) {
                    continue;
                }
                if (ENTRY_COLUMNS[i].equals(CREATION_USER_COLUMN)) {
                    continue;
                }
                if (ENTRY_COLUMNS[i].equals(CREATION_DATE_COLUMN)) {
                    continue;
                }
            }

            AuditDetail detail = new AuditDetail();
            detail.setColumnName(ENTRY_COLUMNS[i]);
            detail.setOldValue(oldColumns[i]);
            detail.setNewValue(newColumns[i]);
            details.add(detail);
        }

        header.setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));

        //the entity used to create header
        ExpenseEntry entity = (newValue == null) ? oldValue : newValue;
        header.setEntityId(entity.getId());
        header.setActionType(auditType);
        header.setApplicationArea(ApplicationArea.TT_EXPENSE);
        header.setCompanyId(entity.getCompanyId());
        header.setTableName(EXPENSE_ENTRY_TABLE);

        header.setCreationDate(new Timestamp((auditType == AuditType.INSERT)
                ? entity.getCreationDate().getTime()
                : entity.getModificationDate().getTime()));

        header.setCreationUser((auditType == AuditType.INSERT)
                ? entity.getCreationUser()
                : entity.getModificationUser());

        try {
            this.auditManager.createAuditRecord(header);
        } catch (AuditManagerException e) {
            throw new PersistenceException("failed to create audit", e);
        }
    }

    /**
     * <p>
     * Parses the object's fields to String array by converting their values into string, and ordered them
     * corresponding to ENTRY_COLUMNS, the columns defined in DB.
     * </p>
     *
     * @param entry to parse.
     *
     * @return string array of entry fields.
     */
    private String[] parseValues(ExpenseEntry entry) {
        if (entry == null) {
            return new String[ENTRY_COLUMNS.length];
        } else {
            return new String[] {
                String.valueOf(entry.getId()), String.valueOf(entry.getCompanyId()),
                String.valueOf((entry.getInvoice() == null) ? (-1) : entry.getInvoice().getId()),
                String.valueOf(entry.getExpenseType().getId()), String.valueOf(entry.getStatus().getId()),
                entry.getDescription(), String.valueOf(entry.getDate()), String.valueOf(entry.getAmount()),
                String.valueOf(entry.isBillable()), String.valueOf(entry.getCreationDate()), entry.getCreationUser(),
                String.valueOf(entry.getModificationDate()), entry.getModificationUser(),
                String.valueOf(entry.getMileage())
            };
        }
    }

    /**
     * <p>
     * Check the expense type id is assocaited with the company id.
     * </p>
     *
     * @param conn the connection.
     * @param companyId the company id.
     * @param expenseTypeId the expense type id.
     *
     * @return true for matched, false otherwise.
     *
     * @throws PersistenceException exception thrown when close resource.
     */
    private boolean checkExpenseType(Connection conn, long companyId, long expenseTypeId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.prepareStatement(CHECK_COMPANY_ID_EXPENSE_TYPE_SQL);
            statement.setLong(1, companyId);
            statement.setLong(2, expenseTypeId);
            rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new PersistenceException("Failed to check the expense type.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(rs, statement);
        }
    }

    /**
     * <p>
     * Check the reject reason id is assocaited with the company id.
     * </p>
     *
     * @param conn the connection.
     * @param companyId the company id.
     * @param rejectReasonId the reject reason id.
     *
     * @return true for matched, false otherwise.
     *
     * @throws PersistenceException exception thrown when close resource.
     */
    private boolean checkRejectReason(Connection conn, long companyId, long rejectReasonId)
        throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.prepareStatement(CHECK_COMPANY_ID_REJECT_REASON_SQL);
            statement.setLong(1, companyId);
            statement.setLong(2, rejectReasonId);
            rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new PersistenceException("Failed to check the reject reason.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(rs, statement);
        }
    }

    /**
     * <p>
     * Adds a new <code>ExpenseEntry</code> instance to the database using the given connection.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. For each contained reject reason, a row needs to be
     * inserted in the exp_reject_reason table.
     * </p>
     *
     * <p>
     * Note: creationDate and modificationDate of the input entry instance will be updated to the current datetime if
     * the new entry is added into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection.
     * @param entry the expense entry to be added to the database.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when adding the expense entry to the persistence.
     */
    private int insertEntry(Connection conn, ExpenseEntry entry) throws PersistenceException {
        PreparedStatement statement = null;

        Timestamp now = ExpenseEntryHelper.date2Timestamp(new Date());

        try {
            statement = conn.prepareStatement(ADD_ENTRY_SQL);

            // Set parameters
            int index = 0;
            statement.setLong(++index, entry.getId());
            statement.setLong(++index, entry.getCompanyId());
            statement.setLong(++index, entry.getClientId());
            statement.setLong(++index, entry.getProjectId());

            if (entry.getInvoice() != null) {
                statement.setLong(++index, entry.getInvoice().getId());
            } else {
                statement.setNull(++index, Types.INTEGER);
            }

            statement.setLong(++index, entry.getExpenseType().getId());
            statement.setLong(++index, entry.getStatus().getId());
            statement.setString(++index, entry.getDescription());
            statement.setTimestamp(++index, ExpenseEntryHelper.date2Timestamp(entry.getDate()));
            statement.setBigDecimal(++index, entry.getAmount());
            statement.setShort(++index, (short) (entry.isBillable() ? 1 : 0));
            statement.setTimestamp(++index, now);
            statement.setString(++index, entry.getCreationUser());
            statement.setTimestamp(++index, now);
            statement.setString(++index, entry.getModificationUser());
            statement.setInt(++index, entry.getMileage());

            // Execute
            int ret = statement.executeUpdate();

            if (ret > 0) {
                // add into the exp_reject_reason table
                Map rejectReasons = entry.getRejectReasons();

                if (rejectReasons != null) {
                    statement.close();
                    statement = conn.prepareStatement(ADD_EXP_REJECT_REASON_SQL);

                    for (Iterator iter = rejectReasons.values().iterator(); iter.hasNext();) {
                        RejectReason rejectReason = (RejectReason) iter.next();

                        statement.clearParameters();
                        index = 0;
                        statement.setLong(++index, entry.getId());
                        statement.setLong(++index, rejectReason.getId());
                        statement.setTimestamp(++index, now);
                        statement.setString(++index, entry.getCreationUser());
                        statement.setTimestamp(++index, now);
                        statement.setString(++index, entry.getModificationUser());
                        statement.executeUpdate();
                    }
                }

                // Set property only after successful execution of the query
                entry.setCreationDate(now);
                entry.setModificationDate(now);
                entry.setChanged(false);
            }

            return ret;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the database. Audit actions will be done if
     * the auditFlag is specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. When deleting an entry, the linked rows from
     * exp_reject_reason must be deleted too.
     * </p>
     *
     * @param entryId the ID of the expense entry to be deleted.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is deleted; <code>false</code> otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry.
     */
    public boolean deleteEntry(long entryId, boolean auditFlag) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return deleteEntry(conn, entryId, auditFlag);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the database using the given connection.
     * Audit actions will be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. When deleting an entry, the linked rows from
     * exp_reject_reason must be deleted too.
     * </p>
     *
     * @param conn the given connection.
     * @param entryId the ID of the expense entry to be deleted.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is deleted; <code>false</code> otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry.
     */
    private boolean deleteEntry(Connection conn, long entryId, boolean auditFlag) throws PersistenceException {
        ExpenseEntry entry = null;

        if (auditFlag) {
            entry = this.retrieveEntry(conn, entryId);
        }

        if (!(deleteEntry(conn, entryId) > 0)) {
            return false;
        }

        if (auditFlag) {
            this.createAudit(entry, null, AuditType.DELETE);
        }

        return true;
    }

    /**
     * <p>
     * Deletes an <code>ExpenseEntry</code> instance with the given ID from the database using the given connection.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. When deleting an entry, the linked rows from
     * exp_reject_reason must be deleted too.
     * </p>
     *
     * @param conn the given connection.
     * @param entryId the ID of the expense entry to be deleted.
     *
     * @return <code>true</code> if the expense entry exists in database and is deleted; <code>false</code> otherwise.
     *
     * @throws PersistenceException if error occurs when deleting the expense entry.
     */
    private int deleteEntry(Connection conn, long entryId) throws PersistenceException {
        PreparedStatement statement = null;

        try {
            // delete from the exp_reject_reason table
            statement = conn.prepareStatement(DELETE_EXP_REJECT_REASON_SQL);
            statement.setLong(1, entryId);
            statement.executeUpdate();
            statement.close();

            // delete from the table expense entry
            statement = conn.prepareStatement(DELETE_ENTRY_SQL);
            statement.setLong(1, entryId);

            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. When deleting an entry, the linked rows from
     * exp_reject_reason must be deleted too.
     * </p>
     *
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws PersistenceException if error occurs when deleting the expense entries.
     */
    public void deleteAllEntries(boolean auditFlag) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            deleteAllEntries(conn, auditFlag);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the database using the given connection. Audit actions will
     * be done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. When deleting an entry, the linked rows from
     * exp_reject_reason must be deleted too.
     * </p>
     *
     * @param conn the given connection.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws PersistenceException if error occurs when deleting the expense entries.
     */
    private void deleteAllEntries(Connection conn, boolean auditFlag) throws PersistenceException {
        ExpenseEntry[] entries = null;

        if (auditFlag) {
            entries = this.retrieveAllEntries();
        }

        deleteAllEntries(conn);

        if (auditFlag) {
            for (int i = 0; i < entries.length; i++) {
                this.createAudit(entries[i], null, AuditType.DELETE);
            }
        }
    }

    /**
     * <p>
     * Deletes all <code>ExpenseEntry</code> instances in the database using the given connection.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. When deleting an entry, the linked rows from
     * exp_reject_reason must be deleted too.
     * </p>
     *
     * @param conn the given connection.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when deleting the expense entries.
     */
    private int deleteAllEntries(Connection conn) throws PersistenceException {
        Statement statement = null;

        try {
            statement = conn.createStatement();
            statement.execute(DELETE_ALL_EXP_REJECT_REASON_SQL);

            return statement.executeUpdate(DELETE_ALL_ENTRY_SQL);
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the database. Audit actions will be done if the auditFlag is
     * specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The exp_reject_reason stores in persistence the reject
     * reason ids for an expense entry. The updated entry may contain a totally different set of reject reasons. In
     * order to update the table we have to retrieve all reject records for the expense entry. The common reject
     * entries (all fields, including users and dates) from the retrieved reject entries and from the update object
     * must be eliminated (they don't need updating). All reject entries present in the update object but not in the
     * table, must be added. All reject entries present in the table but not in the update object, must be deleted.
     * The reject entries present in both but with different users/dates, must be updated. The company id field added
     * and in the component level, it will check the expense type and each reject reason is associated with the
     * company id.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input entry instance will be updated to the current datetime if the new entry is
     * updated into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param entry the expense entry to be updated in the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is updated; <code>false</code> otherwise.
     *
     * @throws IllegalArgumentException if <code>entry</code> is <code>null</code>.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException if error occurs when updating the expense entry to the persistence, or if the
     *         expsen entry's company id or status id or type id or invoice id is not in persistence, or if the
     *         expense type or any of the reject reason is not matched with the compay id.
     */
    public boolean updateEntry(ExpenseEntry entry, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(entry, "entry");
        ExpenseEntryHelper.validateExpenseEntryData(entry);

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return updateEntry(conn, entry, auditFlag);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the database using the given connection. Audit actions will be
     * done if the auditFlag is specified.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The exp_reject_reason stores in persistence the reject
     * reason ids for an expense entry. The updated entry may contain a totally different set of reject reasons. In
     * order to update the table we have to retrieve all reject records for the expense entry. The common reject
     * entries (all fields, including users and dates) from the retrieved reject entries and from the update object
     * must be eliminated (they don't need updating). All reject entries present in the update object but not in the
     * table, must be added. All reject entries present in the table but not in the update object, must be deleted.
     * The reject entries present in both but with different users/dates, must be updated. The company id field added
     * and in the component level, it will check the expense type and each reject reason is associated with the
     * company id.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input entry instance will be updated to the current datetime if the new entry is
     * updated into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection.
     * @param entry the expense entry to be updated in the database.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return <code>true</code> if the expense entry exists in database and is updated; <code>false</code> otherwise.
     *
     * @throws PersistenceException if error occurs when updating the expense entry to the persistence, or if the
     *         expsen entry's company id or status id or type id or invoice id is not in persistence, or if the
     *         expense type or any of the reject reason is not matched with the compay id.
     */
    private boolean updateEntry(Connection conn, ExpenseEntry entry, boolean auditFlag) throws PersistenceException {

        // Check if the ID already exists in database
        if (!this.existEntry(conn, entry.getId())) {
            return false;
        }

        checkCompanyIdMatched(conn, entry);

        ExpenseEntry oldEntry = this.retrieveEntry(conn, entry.getId());

        if (!(updateEntry(conn, oldEntry, entry) > 0)) {
            return false;
        }

        if (auditFlag) {
            this.createAudit(oldEntry, entry, AuditType.UPDATE);
        }

        return true;
    }

    /**
     * <p>
     * Updates an <code>ExpenseEntry</code> instance to the database using the given connection.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The exp_reject_reason stores in persistence the reject
     * reason ids for an expense entry. The updated entry may contain a totally different set of reject reasons. In
     * order to update the table we have to retrieve all reject records for the expense entry. The common reject
     * entries (all fields, including users and dates) from the retrieved reject entries and from the update object
     * must be eliminated (they don't need updating). All reject entries present in the update object but not in the
     * table, must be added. All reject entries present in the table but not in the update object, must be deleted.
     * The reject entries present in both but with different users/dates, must be updated. The company id field added
     * and in the component level, it will check the expense type and each reject reason is associated with the
     * company id.
     * </p>
     *
     * <p>
     * Note: modificationDate of the input entry instance will be updated to the current datetime if the new entry is
     * updated into the database, and the changed flag will be set to false.
     * </p>
     *
     * @param conn the given connection.
     * @param oldEntry the old expense entry in the database.
     * @param entry the expense entry to be updated in the database.
     *
     * @return the records count effected in the db operation.
     *
     * @throws PersistenceException if error occurs when updating the expense entry to the persistence, or if the
     *         expsen entry's company id or status id or type id or invoice id is not in persistence, or if the
     *         expense type or any of the reject reason is not matched with the compay id.
     */
    private int updateEntry(Connection conn, ExpenseEntry oldEntry, ExpenseEntry entry) throws PersistenceException {
        PreparedStatement statement = null;

        Timestamp now = ExpenseEntryHelper.date2Timestamp(new Date());

        try {
            // update the expense_entry table
            statement = conn.prepareStatement(UPDATE_ENTRY_SQL);

            // Set parameters
            int index = 0;
            statement.setLong(++index, entry.getCompanyId());
            statement.setLong(++index, entry.getClientId());
            statement.setLong(++index, entry.getProjectId());

            if (entry.getInvoice() != null) {
                statement.setLong(++index, entry.getInvoice().getId());
            } else {
                statement.setNull(++index, Types.INTEGER);
            }

            statement.setLong(++index, entry.getExpenseType().getId());
            statement.setLong(++index, entry.getStatus().getId());
            statement.setString(++index, entry.getDescription());
            statement.setTimestamp(++index, ExpenseEntryHelper.date2Timestamp(entry.getDate()));
            statement.setBigDecimal(++index, entry.getAmount());
            statement.setShort(++index, (short) (entry.isBillable() ? 1 : 0));
            statement.setTimestamp(++index, now);
            statement.setString(++index, entry.getModificationUser());
            statement.setInt(++index, entry.getMileage());
            statement.setLong(++index, entry.getId());

            int ret = statement.executeUpdate();

            if (ret > 0) {
                // update the exp_reject_reason table
                // first delete all the records by given ExpenseEntriesID
                statement.close();
                statement = conn.prepareStatement(DELETE_EXP_REJECT_REASON_SQL);
                statement.setLong(1, entry.getId());
                statement.executeUpdate();

                Map rejectReasons = entry.getRejectReasons();

                if (rejectReasons != null) {
                    // then add all the records into the exp_reject_reason table.
                    statement.close();
                    statement = conn.prepareStatement(ADD_EXP_REJECT_REASON_SQL);

                    for (Iterator iter = rejectReasons.values().iterator(); iter.hasNext();) {
                        RejectReason rejectReason = (RejectReason) iter.next();

                        statement.clearParameters();
                        index = 0;
                        statement.setLong(++index, entry.getId());
                        statement.setLong(++index, rejectReason.getId());
                        statement.setTimestamp(++index, ExpenseEntryHelper.date2Timestamp(oldEntry.getCreationDate()));
                        statement.setString(++index, oldEntry.getCreationUser());
                        statement.setTimestamp(++index, now);
                        statement.setString(++index, entry.getModificationUser());
                        statement.executeUpdate();
                    }
                }

                // Set property only after successful execution of the query
                entry.setModificationDate(now);
                entry.setChanged(false);
            }

            return ret;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(null, statement);
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the database. The related
     * <code>Invoice</code>, <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The retrieval query must now be joined with
     * exp_reject_reason on the ExpenseEntriesID field (a separate query is acceptable too). The reject reason fields
     * will populate instances of the <code>RejectReason</code> class.
     * </p>
     *
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot be
     *         found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry, or the value in database is
     *         invalid.
     */
    public ExpenseEntry retrieveEntry(long entryId) throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return retrieveEntry(conn, entryId);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an <code>ExpenseEntry</code> instance with the given ID from the database using the given connection.
     * The related <code>Invoice</code>, <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also
     * retrieved.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The retrieval query must now be joined with
     * exp_reject_reason on the ExpenseEntriesID field (a separate query is acceptable too). The reject reason fields
     * will populate instances of the <code>RejectReason</code> class.
     * </p>
     *
     * @param conn the given connection.
     * @param entryId the ID of the expense entry to be retrieved.
     *
     * @return an <code>ExpenseEntry</code> instance with the given ID; or <code>null</code> if such instance cannot be
     *         found in the database.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry, or the value in database is
     *         invalid.
     */
    private ExpenseEntry retrieveEntry(Connection conn, long entryId) throws PersistenceException {
        ExpenseEntry[] entries = retrieveEntries(conn, " AND expense_entry.expense_entry_id=?",
                new Object[] {new Long(entryId)});

        return (entries.length == 0) ? null : entries[0];
    }

    /**
     * <p>
     * Checkes whether there is one record with the given entry id in the persistence.
     * </p>
     *
     * @param conn the given connection.
     * @param entryId the given entry id.
     *
     * @return whether there is one record with the given entry id in the persistence.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entry.
     */
    private boolean existEntry(Connection conn, long entryId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(EXIST_ENTRY_SQL);

            // Set parameter
            statement.setLong(1, entryId);

            // Execute
            resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Creates an expense entry instance from the given result set. The next record in the result set is used to create
     * the instance. If there is no more record in the result set, <code>null</code> is returned. The entry type and
     * entry status instances are also created from the result set.
     * </p>
     *
     * @param conn the connection.
     * @param resultSet the result set used to create expense entry status instance.
     *
     * @return a new <code>ExpenseEntry</code> instance created from the next record in the result set; or
     *         <code>null</code> if there is no more record.
     *
     * @throws SQLException if error occurs when accessing the result set.
     * @throws PersistenceException if the flag for billable is neither 0 nor 1; or the column value is
     *         <code>null</code>, empty string or invalid value.
     */
    private ExpenseEntry createExpenseEntry(Connection conn, ResultSet resultSet) throws SQLException,
        PersistenceException {
        if (!resultSet.next()) {
            return null;
        }

        ExpenseEntry entry;
        try {

            // Set properties
            entry = new ExpenseEntry(resultSet.getLong(ID_COLUMN));
            entry.setCompanyId(resultSet.getLong(COMPANY_ID_COLUMN));
            entry.setCompanyId(resultSet.getLong(CLIENT_ID_COLUMN));
            entry.setCompanyId(resultSet.getLong(PROJECT_ID_COLUMN));
            
            long invoiceId = resultSet.getLong(INVOICE_ID_COLUMN);

            if (!resultSet.wasNull()) {
                entry.setInvoice(this.invoiceManager.getInvoice(invoiceId));
            }

            entry.setExpenseType(this.expenseTypeManager.retrieveType(resultSet.getLong(TYPE_ID_COLUMN)));
            entry.setStatus(this.expenseStatusManager.retrieveStatus(resultSet.getLong(STATUS_ID_COLUMN)));

            String description = resultSet.getString(DESCRIPTION_COLUMN);
            if (!resultSet.wasNull()) {
                entry.setDescription(description);
            }
            entry.setDate(resultSet.getDate(DATE_COLUMN));
            entry.setAmount(resultSet.getBigDecimal(AMOUNT_COLUMN));

            entry.setCreationDate(resultSet.getDate(CREATION_DATE_COLUMN));
            entry.setCreationUser(resultSet.getString(CREATION_USER_COLUMN));
            entry.setModificationDate(resultSet.getDate(MODIFICATION_DATE_COLUMN));
            entry.setModificationUser(resultSet.getString(MODIFICATION_USER_COLUMN));
            entry.setMileage(resultSet.getInt(MILEAGE_COLUMN));

            // Set billable
            switch (resultSet.getShort(BILLABLE_COLUMN)) {
            case 0:
                entry.setBillable(false);

                break;

            case 1:
                entry.setBillable(true);

                break;

            default:
                throw new PersistenceException("Billable column is neither 0 nor 1.");
            }

        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Column value cannot be empty string.", e);
        } catch (InvoiceException e) {
            throw new PersistenceException("Fails to get the invoice.", e);
        }

        // retrieve the reject reasons
        entry.setRejectReasons(retrieveRejectReasons(conn, entry.getId()));
        entry.setChanged(false);

        return entry;
    }

    /**
     * <p>
     * Retrieve the reject reasons with given expense entry. All the retrieved values will be added into the expense
     * entry instance.
     * </p>
     *
     * @param conn the given connection.
     * @param entryId the id of the expense entry instance to retrieve the reject reasons.
     *
     * @return the map of the rejectReasons, key is the id and value is the Reject Reason object.
     *
     * @throws PersistenceException any exception during the database operations.
     */
    private Map retrieveRejectReasons(Connection conn, long entryId) throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(RETRIEVE_REJECT_REASON_SQL);

            statement.setLong(1, entryId);
            resultSet = statement.executeQuery();

            Map rejectReasons = new HashMap();

            while (resultSet.next()) {
                long reasonId = resultSet.getLong(REASON_ID_COLUMN);
                RejectReason reason = this.rejectReasonDAO.retrieveRejectReason(reasonId);
                rejectReasons.put(new Long(reasonId), reason);
            }

            return rejectReasons;
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } catch (RejectReasonDAOException e) {
            throw new PersistenceException("Fails to get the reject reason.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }
    }

    /**
     * <p>
     * Retrieves all <code>ExpenseEntry</code> instances from the database. The related <code>Invoice</code>,
     * <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The retrieval query must now be joined with
     * exp_reject_reason on the ExpenseEntriesID field (a separate query is acceptable too and in this case perhaps it
     * is recommended since retrieving all the information in one query is probably too much, especially because of
     * the one-to-many join between expense entries and reject reasons). The reject reason fields will populate
     * instances of the <code>RejectReason</code> class.
     * </p>
     *
     * @return an array of <code>ExpenseEntry</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entries, or the value in database is
     *         invalid.
     */
    public ExpenseEntry[] retrieveAllEntries() throws PersistenceException {
        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            return retrieveEntries(conn, "", new Object[0]);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves <code>ExpenseEntry</code> instances from the database using the given connection. The related
     * <code>Invoice</code>, <code>ExpenseType</code> and <code>ExpenseStatus</code> instances are also retrieved.
     * </p>
     *
     * <p>
     * The expense entry may contain now several reject reasons. The retrieval query must now be joined with
     * exp_reject_reason on the ExpenseEntriesID field (a separate query is acceptable too and in this case perhaps it
     * is recommended since retrieving all the information in one query is probably too much, especially because of
     * the one-to-many join between expense entries and reject reasons). The reject reason fields will populate
     * instances of the <code>RejectReason</code> class.
     * </p>
     *
     * @param conn the given connection.
     * @param whereClause the where clause.
     * @param parameters the parameters for the where clause.
     *
     * @return an array of <code>ExpenseEntry</code> instances retrieved from the database, or an empty array if there
     *         is no record.
     *
     * @throws PersistenceException if error occurs when retrieving the expense entries, or the value in database is
     *         invalid.
     */
    private ExpenseEntry[] retrieveEntries(Connection conn, String whereClause, Object[] parameters)
        throws PersistenceException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List list = new ArrayList();

        try {
            statement = conn.prepareStatement(RETRIEVE_ALL_ENTRY_SQL + whereClause);

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            // Execute
            resultSet = statement.executeQuery();

            // For each record, create an expense entry instance
            ExpenseEntry entry;

            while ((entry = createExpenseEntry(conn, resultSet)) != null) {
                list.add(entry);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Something wrong in the database related operations.", e);
        } finally {
            ExpenseEntryHelper.releaseResource(resultSet, statement);
        }

        return (ExpenseEntry[]) list.toArray(new ExpenseEntry[list.size()]);
    }

    /**
     * <p>
     * Performs a search for expense entries matching a given criteria. The criteria is abstracted using the <code>
     * Criteria</code> interface. The <code>Criteria</code> implementations cover the basic SQL filtering abilities
     * (=, like, between, or, and, not). The result of the search is an array with the matched expense entries. It is
     * empty if no matches found (but it can't be <code>null</code> or contain <code>null</code>) elements.
     * </p>
     *
     * @param criteria the criteria to be used in the search.
     *
     * @return the results of the search (can be empty if no matches found).
     *
     * @throws IllegalArgumentException if the argument is <code>null</code>
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry[] searchEntries(Criteria criteria) throws PersistenceException {
        ExpenseEntryHelper.validateNotNull(criteria, "criteria");

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            Object[] parameters = criteria.getParameters();

            for (int i = 0; i < parameters.length; ++i) {
                if (parameters[i] instanceof Date) {
                    parameters[i] = ExpenseEntryHelper.date2Timestamp((Date) parameters[i]);
                }
            }

            return retrieveEntries(conn, " AND " + criteria.getWhereClause(), parameters);
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Adds a set of entries to the database.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire retrieval will fail if a single expense entry addition
     * fails. If the addition is non-atomic then it means each expense entry is added individually. If it fails, that
     * won't affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * <p>
     * Note: This method is not supported in current version. Non-atomic batch operations are done at the level of the
     * EJB as to make use of CMTD. Atomic batch operations can be done using the new overloaded method.
     * </p>
     *
     * @param entries the entries to add.
     * @param isAtomic whether the operation should be atomic or not.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return the entries that failed to be added in non atomic mode (null in atomic mode).
     *
     * @throws UnsupportedOperationException This operation is not supported.
     */
    public ExpenseEntry[] addEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * Deletes a set of entries from the database.
     * </p>
     *
     * <p>
     * If the deletion is atomic then it means that entire retrieval will fail if a single expense entry deletion
     * fails. If the deletion is non-atomic then it means each expense entry is deleted individually. If it fails,
     * that won't affect the others. A list with the failed ids is returned to the user (empty if no error occurs).
     * </p>
     *
     * <p>
     * Note: This method is not supported in current version. Non-atomic batch operations are done at the level of the
     * EJB as to make use of CMTD. Atomic batch operations can be done using the new overloaded method.
     * </p>
     *
     * @param entryIds the ids of the entries to delete.
     * @param isAtomic whether the operation should be atomic or not.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @return the entries that failed to be deleted in non atomic mode (null in atomic mode).
     *
     * @throws UnsupportedOperationException This operation is not supported.
     */
    public long[] deleteEntries(long[] entryIds, boolean isAtomic, boolean auditFlag) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * Updates a set of entries in the database.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire retrieval will fail if a single expense entry update fails. If
     * the update is non-atomic then it means each expense entry is updated individually. If it fails, that won't
     * affect the others. A list with the failed entries is returned to the user (empty if no error occurs).
     * </p>
     *
     * <p>
     * Note: This method is not supported. Non-atomic batch operations are done at the level of the EJB as to make use
     * of CMTD. Atomic batch operations can be done using the new overloaded method.
     * </p>
     *
     * @param entries the ids of the entries to update.
     * @param isAtomic whether the operation should be atomic or not.
     * @param auditFlag audit flag which specifies if the user want to audit the action
     *
     * @return the entries that failed to be updated in non atomic mode (null in atomic mode).
     *
     * @throws UnsupportedOperationException This operation is not supported.
     */
    public ExpenseEntry[] updateEntries(ExpenseEntry[] entries, boolean isAtomic, boolean auditFlag) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * <p>
     * If the retrieval is atomic then it means that entire retrieval will fail if a single expense entry retrieval
     * fails. If the retrieval is non-atomic then it means each expense entry is retrieved individually. If it fails
     * that won't affect the others. The potentially partial list of results will be returned
     * </p>
     *
     * <p>
     * Note: This method is not supported. Non-atomic batch operations are done at the level of the EJB as to make use
     * of CMTD. Atomic batch operations can be done using the new overloaded method.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     * @param isAtomic whether the operation should be atomic or not.
     *
     * @return the entries that were retrieved in both modes.
     *
     * @throws UnsupportedOperationException This operation is not supported.
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds, boolean isAtomic) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    /**
     * <p>
     * Adds a set of entries to the database. See {@link InformixExpenseEntryDAO#addEntry(ExpenseEntry, boolean)}.
     * </p>
     *
     * @param entries the entries to add.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set for any expense entry.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void addEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(entries, "entries");
        ExpenseEntryHelper.validateObjectArray(entries, "entries", 1);

        for (int i = 0; i < entries.length; i++) {
            ExpenseEntryHelper.validateNewInfo(entries[i], "entry");
            ExpenseEntryHelper.validateExpenseEntryData(entries[i]);
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            for (int i = 0; i < entries.length; i++) {
                if (entries[i].getId() <= 0) {
                    entries[i].setId(ExpenseEntryHelper.generateId());
                }

                if (!this.addEntry(conn, entries[i], auditFlag)) {
                    throw new PersistenceException("Duplicate records for " + entries[i].getId() + " exists.");
                }
            }
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Deletes a set of entries from the database. See {@link InformixExpenseEntryDAO#deleteEntry(long, boolean)}.
     * </p>
     *
     * @param entryIds the ids of the entries to delete.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception)
     */
    public void deleteEntries(long[] entryIds, boolean auditFlag) throws PersistenceException {
        ExpenseEntryHelper.validateNotNull(entryIds, "entryIds");

        if (entryIds.length == 0) {
            throw new IllegalArgumentException("EntryIds should not be empty array.");
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            for (int i = 0; i < entryIds.length; i++) {
                if (!this.deleteEntry(conn, entryIds[i], auditFlag)) {
                    throw new PersistenceException("No records for " + entryIds[i] + " exists.");
                }
            }
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Updates a set of entries in the database. See {@link InformixExpenseEntryDAO#updateEntry(ExpenseEntry,
     * boolean)}.
     * </p>
     *
     * @param entries the entries to update.
     * @param auditFlag audit flag which specifies if the user want to audit the action.
     *
     * @throws IllegalArgumentException if the array is null, empty or has nulls.
     * @throws InsufficientDataException If fields other than description, invoice, creation date and modification date
     *         are not set.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public void updateEntries(ExpenseEntry[] entries, boolean auditFlag)
        throws InsufficientDataException, PersistenceException {
        ExpenseEntryHelper.validateNotNull(entries, "entries");
        ExpenseEntryHelper.validateObjectArray(entries, "entries", 1);

        for (int i = 0; i < entries.length; i++) {
            ExpenseEntryHelper.validateExpenseEntryData(entries[i]);
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            for (int i = 0; i < entries.length; i++) {

                if (!this.updateEntry(conn, entries[i], auditFlag)) {
                    throw new PersistenceException("No records for " + entries[i].getId() + " exists.");
                }
            }
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * @param entryIds the ids of the entries to retrieve.
     *
     * @return the entries that were retrieved in both modes.
     *
     * @throws IllegalArgumentException if the array is null or empty.
     * @throws PersistenceException wraps a persistence implementation specific exception (such as SQL exception).
     */
    public ExpenseEntry[] retrieveEntries(long[] entryIds) throws PersistenceException {
        ExpenseEntryHelper.validateNotNull(entryIds, "entryIds");

        if (entryIds.length == 0) {
            throw new IllegalArgumentException("EntryIds should not be empty array.");
        }

        Connection conn = null;

        try {
            conn = ExpenseEntryHelper.createConnection(connectionFactory, connectionName);

            // use the where clause of "id in ()" to be more efficient
            StringBuffer buffer = new StringBuffer();
            buffer.append(" AND expense_entry.expense_entry_id in (");

            for (int i = 0; i < entryIds.length; i++) {
                buffer.append(entryIds[i]);

                if (i != (entryIds.length - 1)) {
                    buffer.append(",");
                }
            }

            buffer.append(")");

            ExpenseEntry[] entries = this.retrieveEntries(conn, buffer.toString(), new Object[0]);

            // build the return array.
            Map entriesMap = new HashMap();

            for (int i = 0; i < entries.length; i++) {
                entriesMap.put(new Long(entries[i].getId()), entries[i]);
            }

            ExpenseEntry[] ret = new ExpenseEntry[entryIds.length];

            for (int i = 0; i < entryIds.length; i++) {
                ret[i] = (ExpenseEntry) entriesMap.get(new Long(entryIds[i]));
                if (ret[i] == null) {
                    throw new PersistenceException("No records for " + entryIds[i] + " exists.");
                }
            }

            return ret;
        } finally {
            ExpenseEntryHelper.releaseConnection(conn);
        }
    }
}
