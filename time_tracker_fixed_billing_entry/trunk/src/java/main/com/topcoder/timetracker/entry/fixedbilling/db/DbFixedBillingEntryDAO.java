/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.search.builder.PersistenceOperationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.UnrecognizedFilterException;
import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.fixedbilling.BatchOperationException;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryDAO;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatus;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusDAO;
import com.topcoder.timetracker.entry.fixedbilling.Helper;
import com.topcoder.timetracker.entry.fixedbilling.InvalidFilterException;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.fixedbilling.filterfactory.MappedFixedBillingEntryFilterFactory;

import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This is an implementation of the <code>FixedBillingEntryDAO</code> interface that utilizes a database with the
 * schema provided in the Requirements Section of Time Tracker Fixed Billing Entry 1.0.
 * </p>
 *
 * <p>
 * Thread Safety: This implementation relies on Container-Managed Transactions to maintain thread-safety. All its state
 * is initialized during construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class DbFixedBillingEntryDAO extends BaseDAO implements FixedBillingEntryDAO {
    /**
     * <p>
     * A String representing the Fixed Billing component in the Time Tracker component. It is used during auditing.
     * </p>
     */
    public static final ApplicationArea APPLICATION_AREA = ApplicationArea.TT_FIXED_BILLING;

    /** The SQL String for insert. */
    private static final String INSERT_ENTRY_SQL = "insert into fix_bill_entry(fix_bill_entry_id, company_id, client_id, project_id,"
        + "invoice_id, fix_bill_status_id, description, entry_date, amount, creation_date, creation_user, "
        + "modification_date, modification_user) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /** The SQL String for update. */
    private static final String UPDATE_ENTRY_SQL = "update fix_bill_entry set company_id=?, invoice_id=?, client_id=?, project_id=?, "
        + "fix_bill_status_id=?, description=?, entry_date=?, amount=?, creation_date=?, "
        + "creation_user=?, modification_date=?, modification_user=? where fix_bill_entry_id= ?";

    /** The SQL String for delete. */
    private static final String DELETE_ENTRY_SQL = "delete from fix_bill_entry where fix_bill_entry_id= ?";

    /** The SQL String for select. */
    private static final String SELECT_ENTRY_SQL = "select * from fix_bill_entry where fix_bill_entry_id in (";

    /** The SQL String for select. */
    private static final String SELECT_ALL_ENTRY_SQL = "select * from fix_bill_entry";

    /** The SQL String field for fix_bill_entry_id. */
    private static final String FIX_BILL_ENTRY_ID_FIELD = "fix_bill_entry_id";

    /** The SQL String field for company_id. */
    private static final String COMPANY_ID_FIELD = "company_id";

    /** The SQL String field for client_id. */
    private static final String CLIENT_ID_FIELD = "client_id";

    /** The SQL String field for project_id. */
    private static final String PROJECT_ID_FIELD = "project_id";

    /** The SQL String field for fix_bill_status_id. */
    private static final String FIX_BILL_STATUS_ID_FIELD = "fix_bill_status_id";

    /** The SQL String field for description. */
    private static final String DESCRIPTION_FIELD = "description";

    /** The SQL String field for entry_date. */
    private static final String ENTRY_DATE_FIELD = "entry_date";

    /** The SQL String field for amount. */
    private static final String AMOUNT_FIELD = "amount";

    /** The SQL String field for creation_date. */
    private static final String CREATION_DATE_FIELD = "creation_date";

    /** The SQL String field for creation_user. */
    private static final String CREATION_USER_FIELD = "creation_user";

    /** The SQL String field for modification_date. */
    private static final String MODIFICATION_DATE_FIELD = "modification_date";

    /** The SQL String field for modification_user. */
    private static final String MODIFICATION_USER_FIELD = "modification_user";

    /** The SQL String field for invoice_id. */
    private static final String INVOICE_ID_FIELD = "invoice_id";

    /**
     * Represents formatting object used to format dates as simple date, in US format.
     */
    private static final Format dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * Represents formatting object used to format dates as date with time of the date, in US
     * format.
     */
    private static final Format timestampFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for searching the datastore for
     * FixedBillingEntries using this implementation. It will be initialized in the constructor with the parameters
     * depending on the search query used by the developer.
     * </p>
     */
    private final MappedFixedBillingEntryFilterFactory filterFactory;

    /**
     * <p>
     * This is an instance of <code>FixedBillingStatusDAO</code> to which persisting the status may be delegated.
     * </p>
     */
    private final FixedBillingStatusDAO fixedBillingStatusDAO;

    /**
     * <p>
     * Constructor accepting the necessary parameters for this implementation to function properly.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use. If none is provided, then default is used.
     * @param idGen The id generator name to use.
     * @param searchBundleNamespace The search bundle namespace used to initialize the search strategy.
     * @param auditor The AuditManager used to perform the audits.
     * @param statusDAO The FixedBillingStatusDAO to delegate status calls to.
     *
     * @throws IllegalArgumentException if any argument is null, except for connName, or if any String argument is an
     *         empty String.
     * @throws ConfigurationException if any configuration error.
     */
    public DbFixedBillingEntryDAO(DBConnectionFactory connFactory, String connName, String idGen,
            String searchBundleNamespace, AuditManager auditor, FixedBillingStatusDAO statusDAO)
        throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleNamespace, "EntrySearchBundle", auditor);
        Helper.checkNull("auditor", auditor);
        Helper.checkNull("statusDAO", statusDAO);
        //The idGen and searchBundleNamespace should not be null.
        Helper.checkNull("idGen", idGen);
        Helper.checkNull("searchStrategyNamespace", searchBundleNamespace);

        this.fixedBillingStatusDAO = statusDAO;

        this.filterFactory = new MappedFixedBillingEntryFilterFactory();
    }

    /**
     * <p>
     * Adds the specified entries into the persistent store. Unique entry ids will automatically be generated and
     * assigned to the entries. There is also the option to perform an audit. After create, the entries' changed flag
     * will set to false.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws DataAccessException if a problem occurs.
     */
    public void createFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException {
        //Check the entry array item.
        checkEntries(entries, false);

        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();

            state = conn.prepareStatement(INSERT_ENTRY_SQL);

            for (int i = 0; i < entries.length; i++) {
                insertStatus(entries[i].getFixedBillingStatus());

                int index = 0;
                entries[i].setId(getNextId());
                state.setLong(++index, entries[i].getId());
                state.setLong(++index, entries[i].getCompanyId());
                state.setLong(++index, entries[i].getClientId());
                state.setLong(++index, entries[i].getProjectId());

                if (entries[i].getInvoiceId() == 0) {
                    state.setNull(++index, Types.DOUBLE);
                } else {
                    state.setLong(++index, entries[i].getInvoiceId());
                }

                state.setLong(++index, entries[i].getFixedBillingStatus().getId());
                state.setString(++index, entries[i].getDescription());
                state.setDate(++index, new Date(entries[i].getDate().getTime()));
                state.setDouble(++index, entries[i].getAmount());

                state.setTimestamp(++index, new Timestamp(entries[i].getCreationDate().getTime()));
                state.setString(++index, entries[i].getCreationUser());
                state.setTimestamp(++index, new Timestamp(entries[i].getModificationDate().getTime()));
                state.setString(++index, entries[i].getModificationUser());

                state.executeUpdate();

                //If update success, set the changed flag to false.
                entries[i].setChanged(false);

                if (audit) {
                    audit(entries[i], null, entries[i], AuditType.INSERT);
                }
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("Unable to audit the records.", ame);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to insert the records.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * Insert the status.
     *
     * @param status the FixedBillingStatus.
     *
     * @throws DataAccessException If unable to insert or update.
     */
    private void insertStatus(FixedBillingStatus status)
        throws DataAccessException {
        if (status.getId() == -1) {
            fixedBillingStatusDAO.createFixedBillingStatuses(new FixedBillingStatus[] {status });
        } else {
            fixedBillingStatusDAO.updateFixedBillingStatuses(new FixedBillingStatus[] {status });
        }
    }

    /**
     * Audit the changed entry.
     *
     * @param entry the FixedBillingEntry instance.
     * @param oldEntry the original FixedBillingEntry.
     * @param newEntry the new FixedBillingEntry.
     * @param actionType the actionType.
     * @throws AuditManagerException if audit fail.
     */
    private void audit(FixedBillingEntry entry, FixedBillingEntry oldEntry, FixedBillingEntry newEntry, int actionType)
        throws AuditManagerException {
        //Create the audit header.
        AuditHeader auditHeader = new AuditHeader();
        auditHeader.setCompanyId(entry.getCompanyId());
        auditHeader.setClientId(entry.getClientId());
        auditHeader.setProjectId(entry.getProjectId());
        auditHeader.setTableName("fix_bill_entry");
        auditHeader.setApplicationArea(ApplicationArea.TT_FIXED_BILLING);
        auditHeader.setEntityId(entry.getId());
        auditHeader.setCreationUser(entry.getCreationUser());
        auditHeader.setCreationDate(new Timestamp(System.currentTimeMillis()));
        auditHeader.setActionType(actionType);
        if (entry.getModificationUserId() > 0) {
            auditHeader.setResourceId(entry.getModificationUserId());
        }

        //Create the audit detail.
        List auditDetails = new ArrayList();
        addAuditDetail(auditDetails, COMPANY_ID_FIELD,
            (oldEntry == null) ? null : String.valueOf(oldEntry.getCompanyId()),
            (newEntry == null) ? null : String.valueOf(newEntry.getCompanyId()), actionType);
        addAuditDetail(auditDetails, CLIENT_ID_FIELD,
                (oldEntry == null) ? null : String.valueOf(oldEntry.getClientId()),
                (newEntry == null) ? null : String.valueOf(newEntry.getClientId()), actionType);
        addAuditDetail(auditDetails, PROJECT_ID_FIELD,
                (oldEntry == null) ? null : String.valueOf(oldEntry.getProjectId()),
                (newEntry == null) ? null : String.valueOf(newEntry.getProjectId()), actionType);
        addAuditDetail(auditDetails, FIX_BILL_STATUS_ID_FIELD,
            (oldEntry == null) ? null : String.valueOf(oldEntry.getFixedBillingStatus().getId()),
            (newEntry == null) ? null : String.valueOf(newEntry.getFixedBillingStatus().getId()), actionType);
        addAuditDetail(auditDetails, DESCRIPTION_FIELD, (oldEntry == null) ? null : oldEntry.getDescription(),
            (newEntry == null) ? null : newEntry.getDescription(), actionType);
        addAuditDetail(auditDetails, ENTRY_DATE_FIELD, (oldEntry == null) ? null : formatDate(oldEntry.getDate()),
            (newEntry == null) ? null : formatDate(newEntry.getDate()), actionType);
        addAuditDetail(auditDetails, AMOUNT_FIELD, (oldEntry == null) ? null : String.valueOf(oldEntry.getAmount()),
            (newEntry == null) ? null : String.valueOf(newEntry.getAmount()), actionType);
        addAuditDetail(auditDetails, CREATION_DATE_FIELD,
            (oldEntry == null) ? null : formatTimestamp(oldEntry.getCreationDate()),
            (newEntry == null) ? null : formatTimestamp(newEntry.getCreationDate()), actionType);
        addAuditDetail(auditDetails, CREATION_USER_FIELD, (oldEntry == null) ? null : oldEntry.getCreationUser(),
            (newEntry == null) ? null : newEntry.getCreationUser(), actionType);
        addAuditDetail(auditDetails, MODIFICATION_DATE_FIELD,
            (oldEntry == null) ? null : formatTimestamp(oldEntry.getModificationDate()),
            (newEntry == null) ? null : formatTimestamp(newEntry.getModificationDate()), actionType);
        addAuditDetail(auditDetails, MODIFICATION_USER_FIELD,
            (oldEntry == null) ? null : oldEntry.getModificationUser(),
            (newEntry == null) ? null : newEntry.getModificationUser(), actionType);
        addAuditDetail(auditDetails, INVOICE_ID_FIELD,
            (oldEntry == null) ? null : String.valueOf(oldEntry.getInvoiceId()),
            (newEntry == null) ? null : String.valueOf(newEntry.getInvoiceId()), actionType);
        auditHeader.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        //Do the audit.
        getAuditManager().createAuditRecord(auditHeader);
    }

    /**
     * Add the audit details.
     *
     * @param auditDetails the list of audit details.
     * @param columnName the column name.
     * @param oldValue the original value.
     * @param newValue the new value.
     * @param auditType the audit type.
     */
    private static void addAuditDetail(List auditDetails, String columnName, String oldValue, String newValue, int auditType)
    {
        AuditDetail auditDetail = new AuditDetail();

        if (auditType == AuditType.INSERT) {
            auditDetail.setNewValue(newValue);
        } else if (auditType == AuditType.DELETE) {
            auditDetail.setOldValue(oldValue);
        } else if (auditType == AuditType.UPDATE) {
            auditDetail.setNewValue(newValue);
            auditDetail.setOldValue(oldValue);
        }

        auditDetail.setColumnName(columnName);
        auditDetails.add(auditDetail);
    }

    /**
     * Formats specified date as simple date (i.e. without time of the day).
     *
     * @return string representation of the date formatted as date.
     * @param date
     *            a date to format.
     */
    private static String formatDate(java.util.Date date) {
        return dateFormat.format(date);
    }

    /**
     * Formats specified date as time stamp (i.e. with time of the day).
     *
     * @return string representation of the date formatted as time stamp.
     * @param date
     *            a date to format.
     */
    private static String formatTimestamp(java.util.Date date) {
        return timestampFormat.format(date);
    }

    /**
     * Check whether the given FixedBillingEntry array is valid.
     *
     * @param entries the FixedBillingEntry array.
     * @param inited the flag whether is inited.
     *
     * @throws IllegalArgumentException if the array is invalid.
     * @throws DataAccessException if any error occurs.
     */
    private void checkEntries(FixedBillingEntry[] entries, boolean inited)
        throws DataAccessException {
        //The array should not be null.
        Helper.checkNull("entries", entries);

        //The array should not be empty.
        if (entries.length == 0) {
            throw new IllegalArgumentException("the array should not be empty.");
        }
        for (int i=0; i<entries.length; i++) {
            //The array item should not be null.
            Helper.checkNull("entries[" + i + "]", entries[i]);
        }

        for (int i = 0; i < entries.length; i++) {
            if (inited) {
                if (entries[i].getId() == -1) {
                    throw new IllegalArgumentException("The id of entries[" + i + "] should not be -1 when updating.");
                }
                //The array should not contain duplicate item.
                for (int j = 0; j < entries.length; j++) {
                    if ((i != j) && (entries[i].getId() == entries[j].getId())) {
                        throw new IllegalArgumentException("The array contains duplicate item.");
                    }
                }
            } else {
                if (entries[i].getId() != -1) {
                    throw new IllegalArgumentException("The id of entries[" + i + "] should be -1 when inserting.");
                }
                //The array should not contain duplicate item.
                for (int j = 0; j < entries.length; j++) {
                    if ((i != j) && (entries[i] == entries[j])) {
                        throw new IllegalArgumentException("The array contains duplicate item.");
                    }
                }
            }

            //Check the required fields.
            Helper.checkRequiredColumn("entries[" + i + "].getFixedBillingStatus()",
                    entries[i].getFixedBillingStatus());
            Helper.checkRequiredColumn("entries[" + i + "].getDescription()", entries[i].getDescription());
            Helper.checkRequiredColumn("entries[" + i + "].getDate()", entries[i].getDate());
            Helper.checkRequiredColumn("entries[" + i + "].getCreationUser()", entries[i].getCreationUser());
            Helper.checkRequiredColumn("entries[" + i + "].getModificationUser()", entries[i].getModificationUser());
            Helper.checkRequiredColumn("entries[" + i + "].getCreationDate()", entries[i].getCreationDate());
            Helper.checkRequiredColumn("entries[" + i + "].getModificationDate()", entries[i].getModificationDate());
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided entries array. There is also the
     * option to perform an audit. Note that it only update the entry which the changed flag is true. And after update,
     * the entries' changed flag will set to false.
     * </p>
     *
     * @param entries An array of entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entries is null or contains null values.
     * @throws UnrecognizedEntityException if the entry is not in the database.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void updateFixedBillingEntries(FixedBillingEntry[] entries, boolean audit)
        throws DataAccessException {
        //Check the entry array item.
        checkEntries(entries, true);

        long[] ids = new long[entries.length];

        for (int i = 0; i < entries.length; i++) {
            ids[i] = entries[i].getId();
        }

        //Verify the entries, if not in the database, throw exception.
        FixedBillingEntry[] oldEntries = verifyEntries(ids);

        Connection conn = null;
        PreparedStatement state = null;
        FixedBillingEntry oldEntry = null;

        try {
            conn = getConnection();

            state = conn.prepareStatement(UPDATE_ENTRY_SQL);

            for (int i = 0; i < entries.length; i++) {
                //Only update the changed entry.
                if (entries[i].isChanged()) {
                    if (audit) {
                        //If need to audit, need to get the original entry.
                        for (int k=0; k<oldEntries.length; k++) {
                            if (oldEntries[k].getId() == entries[i].getId()) {
                                oldEntry = oldEntries[k];
                            }
                        }
                    }

                    //Insert the status.
                    insertStatus(entries[i].getFixedBillingStatus());

                    int index = 0;
                    state.setLong(++index, entries[i].getCompanyId());

                    //If invoice id is 0, should set it as null.
                    if (entries[i].getInvoiceId() == 0) {
                        state.setNull(++index, Types.DOUBLE);
                    } else {
                        state.setLong(++index, entries[i].getInvoiceId());
                    }

                    state.setLong(++index, entries[i].getClientId());
                    state.setLong(++index, entries[i].getProjectId());
                    state.setLong(++index, entries[i].getFixedBillingStatus().getId());
                    state.setString(++index, entries[i].getDescription());
                    state.setDate(++index, new Date(entries[i].getDate().getTime()));
                    state.setDouble(++index, entries[i].getAmount());
                    state.setTimestamp(++index, new Timestamp(entries[i].getCreationDate().getTime()));
                    state.setString(++index, entries[i].getCreationUser());
                    state.setTimestamp(++index, new Timestamp(entries[i].getModificationDate().getTime()));
                    state.setString(++index, entries[i].getModificationUser());
                    state.setLong(++index, entries[i].getId());

                    state.executeUpdate();

                    //If update success, set the changed flag to false.
                    entries[i].setChanged(false);

                    if (audit) {
                        audit(entries[i], oldEntry, entries[i], AuditType.UPDATE);
                    }
                }
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("Unable to audit the records.", ame);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to update the records.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the entry with the specified entryIds.
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entryIds An array of entryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if entryIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteFixedBillingEntries(long[] entryIds, boolean audit)
        throws DataAccessException {
        Helper.checkIdArray("entryIds", entryIds);
        Helper.checkDuplicateId(entryIds);

        //For audit, need to get the entry first, then remove it one by one.
        FixedBillingEntry[] entries = verifyEntries(entryIds);

        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();

            state = conn.prepareStatement(DELETE_ENTRY_SQL);

            for (int i = 0; i < entries.length; i++) {
                state.setLong(1, entries[i].getId());

                //If unable to delete the record, throw exception.
                if (state.executeUpdate() != 1) {
                    throw new UnrecognizedEntityException(entryIds[i], "Unable to delete the entryIds[" + i + "]");
                }

                if (audit) {
                    audit(entries[i], entries[i], null, AuditType.DELETE);
                }
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("Unable to audit the records.", ame);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to delete the records.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * Verify whether the entry ids are in the database. If not, throw exception.
     *
     * @param entryIds the entry ids.
     *
     * @return the FixedBillingEntry array.
     *
     * @throws UnrecognizedEntityException if unable to find the record by id.
     * @throws DataAccessException if unable to search the database.
     */
    private FixedBillingEntry[] verifyEntries(long[] entryIds)
        throws DataAccessException {
        FixedBillingEntry[] entries = null;

        try {
            //Get the entries by given array ids.
            entries = getFixedBillingEntries(entryIds);
        } catch (BatchOperationException boe) {
            //If can not find some entry by given id, throw the UnrecognizedEntityException.
            Throwable[] exceptions = boe.getCauses();

            for (int i = 0; i < exceptions.length; i++) {
                if (exceptions[i] != null) {
                    throw (UnrecognizedEntityException) exceptions[i];
                }
            }
        }

        return entries;
    }

    /**
     * <p>
     * Retrieves an array of FixedBillingEntry objects that reflects the data in the persistent store on the Time
     * Tracker FixedBilling Entry with the specified Ids. Note the return entries' changed flag will set to false.
     * </p>
     *
     * @param entryIds An array of entryIds for which the operation should be performed.
     *
     * @return The entries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if entryIds is null or contains values &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws BatchOperationException if unable to find the entry.
     */
    public FixedBillingEntry[] getFixedBillingEntries(long[] entryIds)
        throws DataAccessException {
        //Check the id array.
        Helper.checkIdArray("entryIds", entryIds);

        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        FixedBillingEntry[] entryArray = null;

        try {
            conn = getConnection();
            state = Helper.createInStatement(conn, state, entryIds, SELECT_ENTRY_SQL);
            //Get the entry from database.
            rs = state.executeQuery();
            //Convert the result set to the array.
            entryArray = createEntriesArray(rs);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }

        //Check whether the length equals to the ids' length, if not, throw exception.
        Helper.checkResultItems(entryArray, entryIds);

        return entryArray;
    }

    /**
     * Convert the result set to the FixedBillingEntry array.
     *
     * @param rs the given result set.
     *
     * @return the FixedBillingEntry array.
     *
     * @throws SQLException if unable to read the result set.
     * @throws UnrecognizedEntityException if unable to get the status by id.
     * @throws DataAccessException if unable to get the status.
     */
    private FixedBillingEntry[] createEntriesArray(ResultSet rs)
        throws SQLException, DataAccessException {
        List entryList = new ArrayList();

        while (rs.next()) {
            FixedBillingEntry entry = new FixedBillingEntry();
            entry.setId(rs.getLong(FIX_BILL_ENTRY_ID_FIELD));
            entry.setCompanyId(rs.getLong(COMPANY_ID_FIELD));
            entry.setClientId(rs.getLong(CLIENT_ID_FIELD));
            entry.setProjectId(rs.getLong(PROJECT_ID_FIELD));
            entry.setInvoiceId(rs.getLong(INVOICE_ID_FIELD));

            FixedBillingStatus[] status = fixedBillingStatusDAO.getFixedBillingStatuses(new long[] {
                        rs.getLong(FIX_BILL_STATUS_ID_FIELD)
                    });

            if (status.length == 0) {
                throw new UnrecognizedEntityException(rs.getLong(FIX_BILL_STATUS_ID_FIELD),
                    "Unable to get the FixedBillingStatus.");
            }

            entry.setFixedBillingStatus(status[0]);
            entry.setDescription(rs.getString(DESCRIPTION_FIELD));
            entry.setDate(rs.getDate(ENTRY_DATE_FIELD));
            entry.setAmount(rs.getDouble(AMOUNT_FIELD));
            entry.setCreationDate(rs.getTimestamp(CREATION_DATE_FIELD));
            entry.setCreationUser(rs.getString(CREATION_USER_FIELD));
            entry.setModificationDate(rs.getTimestamp(MODIFICATION_DATE_FIELD));
            entry.setModificationUser(rs.getString(MODIFICATION_USER_FIELD));
            entry.setChanged(false);
            entryList.add(entry);
        }

        return (FixedBillingEntry[]) entryList.toArray(new FixedBillingEntry[entryList.size()]);
    }

    /**
     * <p>
     * Searches the persistent store for any fixed billing entries  that satisfy the criteria that was specified in the
     * provided search filter.  The provided filter should be created using either the filters that are created using
     * the FixedBillingEntryFilterFactory returned by getFixedBillingEntryFilterFactory of this instance, or a
     * composite Search Filters (such as AndFilter, OrFilter and NotFilter from Search Builder component) that
     * combines the filters created using FixedBillingEntryFilterFactory. Note that the return entries' changed flag
     * will set to false.
     * </p>
     *
     * @param filter The filter used to search for entries.
     *
     * @return The entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws InvalidFilterException if the filter cannot be recognized.
     */
    public FixedBillingEntry[] searchFixedBillingEntries(Filter filter)
        throws DataAccessException, InvalidFilterException {
        Helper.checkNull("filter", filter);

        FixedBillingEntry[] entries = null;

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(filter);

            List ids = new ArrayList();
            while (result.next()) {
                ids.add(new Long(result.getLong(1)));
            }
            long[] idArray = new long[ids.size()];
            int i = 0;
            for (Iterator itr = ids.iterator(); itr.hasNext(); ) {
                idArray[i] = ((Long)itr.next()).longValue();
                i++;
            }
            if (idArray.length == 0) {
            	return new FixedBillingEntry[0];
            }
            entries = this.getFixedBillingEntries(idArray);
        } catch (PersistenceOperationException poe) {
            throw new DataAccessException("Unable to search the records with search builder error.", poe);
        } catch (UnrecognizedFilterException ufe) {
            throw new InvalidFilterException("Unable to search the records with not exist status.", ufe);
        } catch (InvalidCursorStateException icse) {
            throw new DataAccessException("Unable to search the records with invalid cursor state.", icse);
        } catch (NullPointerException npe) {
            throw new DataAccessException("Unable to search the records with null pointer exception.", npe);
        } catch (SearchBuilderException sbe) {
            throw new DataAccessException("Unable to search the records with null pointer exception.", sbe);
        }

        return entries;
    }

    /**
     * <p>
     * Retrieves the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     * FixedBillingEntries.  This is used to conveniently specify the search criteria that should be used.  The
     * filters returned by the given factory should be used with this instance's searchFixedBillingEntries method.
     * </p>
     *
     * @return the FixedBillingEntryFilterFactory that is capable of creating SearchFilters to use when searching for
     *         fixed billing entries.
     */
    public FixedBillingEntryFilterFactory getFixedBillingEntryFilterFactory() {
        return filterFactory;
    }

    /**
     * <p>
     * Retrieves all the FixedBillingEntries that are currently in the persistent store. Note that the return entries'
     * changed flag will set to false.
     * </p>
     *
     * @return An array of all entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public FixedBillingEntry[] getAllFixedBillingEntries()
        throws DataAccessException {
        FixedBillingEntry[] entries = null;
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            state = conn.prepareStatement(SELECT_ALL_ENTRY_SQL);
            rs = state.executeQuery();

            entries = createEntriesArray(rs);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }

        return entries;
    }
}
