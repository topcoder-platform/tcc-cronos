/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryDAO;
import com.topcoder.timetracker.entry.time.TimeEntryFilterFactory;
import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an database implementation of the <code>TimeEntryDAO</code> interface.
 * </p>
 *
 * <p>
 * It is responsible for handling the retrieval, storage, and searching of Time Tracker TimeEntry
 * data from a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 *
 * <p>
 * It delegates certain Status and TaskType persistence operations to their respective DAOs.
 * </p>
 *
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner. For database access, the Time Tracker
 * Application will be making use of Container managed transactions, so JDBC transactions
 * are NOT used.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeEntryDAO extends BaseDAO implements TimeEntryDAO {

    /**
     * <p>
     * Represents the sql script to insert a record to <b>time_entry</b> table.
     * </p>
     */
    private static final String INSERT_TIME_ENTRY = "insert into time_entry(time_entry_id, company_id, "
        + "client_id, project_id, invoice_id, time_status_id, task_type_id, description, entry_date, hours, billable, creation_date, "
        + "creation_user, modification_date, modification_user) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to update a record in <b>time_entry</b> table.
     * </p>
     */
    private static final String UPDATE_TIME_ENTRY = "update time_entry set company_id = ?, client_id = ?, project_id = ?, invoice_id = ?, "
        + "time_status_id = ?, task_type_id = ?, description = ?, entry_date = ?, hours = ?, billable = ?, "
        + "modification_date = ?, modification_user= ? where time_entry_id = ?";

    /**
     * <p>
     * Represents the sql script to delete a record in <b>time_entry</b> table.
     * </p>
     */
    private static final String DELETE_TIME_ENTRY = "delete from time_entry where time_entry_id = ?";

    /**
     * <p>
     * Represents the sql script to select some records in <b>time_entry</b> table.
     * </p>
     */
    private static final String SELECT_TIME_ENTRIES = "select time_entry_id, company_id, client_id, project_id, invoice_id, time_status_id, "
        + "task_type_id, description, entry_date, hours, billable, creation_date, creation_user, modification_date, "
        + "modification_user from time_entry";

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
     * This is the <code>TaskTypeDAO</code> which is used to retrieve any TaskTypes related
     * to the <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It cannot be null.
     * </p>
     */
    private final TaskTypeDAO taskTypeDao;

    /**
     * <p>
     * This is the <code>TimeStatusDAO</code> which is used to retrieve any TimeStatus related to the
     * <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It cannot be null.
     * </p>
     */
    private final TimeStatusDAO timeStatusDao;

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for
     * searching the data store for Time Entries using this implementation.
     * </p>
     *
     * <p>
     * It will be initialized in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final DbTimeEntryFilterFactory timeEntryFilterFactory;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to construct a <code>DbTimeEntryDAO</code>.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use.
     * @param searchStrategyNamespace The configuration namespace of the database search strategy that will be used.
     * @param auditor The AuditManager used to create audit entries.
     * @param taskTypeDao The task type dao to use.
     * @param timeStatusDao The time status dao to use.
     *
     * @throws IllegalArgumentException if connFactory, auditor, taskTypeDao or timeStatusDao is null, or
     * idGen, searchBundleManagerNamespace, searchBundleName is null or empty string, or connName is
     * empty string when it is not null
     * @throws ConfigurationException if unable to create the search bundle from the given namespace or create
     * id generator using the id generator name
     */
    public DbTimeEntryDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleManagerNamespace, String searchBundleName, AuditManager auditor, TaskTypeDAO taskTypeDao,
        TimeStatusDAO timeStatusDao) throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleManagerNamespace, searchBundleName, auditor);

        Util.checkNull(idGen, "idGen");
        Util.checkNull(searchBundleManagerNamespace, "searchBundleManagerNamespace");
        Util.checkNull(auditor, "auditor");
        Util.checkNull(taskTypeDao, "taskTypeDao");
        Util.checkNull(timeStatusDao, "timeStatusDao");

        this.taskTypeDao = taskTypeDao;
        this.timeStatusDao = timeStatusDao;
        this.timeEntryFilterFactory = new DbTimeEntryFilterFactory();
    }

    /**
     * <p>
     * Adds the specified time entries into the persistent store.
     * </p>
     *
     * <p>
     * Unique time entry ids will automatically be generated and assigned to the time entries.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param timeEntries An array of time entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null, empty or contains null values or
     * same reference or has ids != -1.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeEntries(TimeEntry[] timeEntries, boolean audit) throws BatchOperationException,
        DataAccessException {
        checkTimeEntries(timeEntries, true);
        Util.checkSameTimeTrackerBeans(timeEntries, "timeEntries");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeEntries.length];

            List params = new ArrayList();

            // get the insert parameters for the time entries to create
            for (int i = 0; i < timeEntries.length; i++) {
                try {
                    if (causes[i] == null) {
                        timeEntries[i].setId(getNextId());
                        params.add(createInsertParam(timeEntries[i]));
                    }
                } catch (DataAccessException e) {
                    causes[i] = e;
                }
            }
            Util.checkStepResult(causes);

            // perform the insert operation in database
            try {
                Util.executeBatchUpdate(conn, INSERT_TIME_ENTRY, (List[]) params.toArray(new List[params.size()]),
                    new long[params.size()]);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            if (audit) {
                audit(null, timeEntries, causes);
            }

            Util.finishBatchOperation(causes);
            Util.resetBeanChangedStates(timeEntries, causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to insert a <code>TimeEntry</code>.
     * </p>
     *
     * @param timeEntry the <code>TimeEntry</code> to insert to database
     * @return the parameters needed to insert a <code>TimeEntry</code>.
     */
    private List createInsertParam(TimeEntry timeEntry) {
        List params = new ArrayList();

        params.add(new Long(timeEntry.getId()));
        params.add(new Long(timeEntry.getCompanyId()));
        params.add(new Long(timeEntry.getClientId()));
        params.add(new Long(timeEntry.getProjectId()));
        if (timeEntry.getInvoiceId() > 0) {
            params.add(new Long(timeEntry.getInvoiceId()));
        } else {
            params.add(new SqlType(Types.INTEGER));
        }
        params.add(new Long(timeEntry.getStatus().getId()));
        params.add(new Long(timeEntry.getTaskType().getId()));

        // description can be null in the database
        if (timeEntry.getDescription() != null) {
            params.add(timeEntry.getDescription());
        } else {
            params.add(new SqlType(Types.VARCHAR));
        }

        params.add(timeEntry.getDate());
        params.add(new Double(timeEntry.getHours()));
        params.add(new Long(timeEntry.getBillable() ? 1 : 0));
        params.add(timeEntry.getCreationDate());
        params.add(timeEntry.getCreationUser());
        params.add(timeEntry.getModificationDate());
        params.add(timeEntry.getModificationUser());

        return params;
    }

    /**
     * <p>
     * This method checks the given time entry instances.
     * </p>
     *
     * @param timeEntries the time entry instances to check
     * @param create the flag to identify whether it is a creation or modification
     *
     * @throws IllegalArgumentException if timeEntries is null, empty, or has ids != -1 in a creation
     * or ids &lt; 0 in a modification.
     * @throws DataAccessException if some beans contain null property which is required in the persistence
     */
    private void checkTimeEntries(TimeEntry[] timeEntries, boolean create) throws DataAccessException {
        Util.checkNull(timeEntries, "timeEntries");

        if (timeEntries.length == 0) {
            throw new IllegalArgumentException("The given time entries array is empty.");
        }

        for (int i = 0; i < timeEntries.length; i++) {
            Util.checkNull(timeEntries[i], "time entry in timeEntries");

            if (create) {
                if (timeEntries[i].getId() != -1) {
                    throw new IllegalArgumentException(
                        "The time entry id is set, which is not allowed for creation operation.");
                }

            } else {
                if (timeEntries[i].getId() < 0) {
                    throw new IllegalArgumentException(
                        "The task type id is zero or negative, which is not allowed for update operation.");
                }
            }

            // null creation user is not allowed
            if (timeEntries[i].getCreationUser() == null) {
                throw new DataAccessException("Some time entries have null creation user.");
            }

            // null creation date is not allowed
            if (timeEntries[i].getCreationDate() == null) {
                throw new DataAccessException("Some time entries have null creation date.");
            }

            // null entry date is not allowed
            if (timeEntries[i].getDate() == null) {
                throw new DataAccessException("Some time entries have null entry date.");
            }

            // null modification user is not allowed
            if (timeEntries[i].getModificationUser() == null) {
                throw new DataAccessException("Some time entries have null modification user.");
            }

            // null modification date is not allowed
            if (timeEntries[i].getModificationDate() == null) {
                throw new DataAccessException("Some time entries have null modification date.");
            }
        }
    }

    /**
     * <p>
     * This method audit the given time entries using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * For detail information, you can refer to {@link DbTimeEntryDAO#audit(TimeEntry, TimeEntry)}.
     * </p>
     *
     * @param oldTimeEntries the time entry instances after update
     * @param newTimeEntries the time entry instances before update
     * @param causes the inner causes of the batch operation
     */
    private void audit(TimeEntry[] oldTimeEntries, TimeEntry[] newTimeEntries, Throwable[] causes) {
        TimeEntry[] timeEntries = (newTimeEntries == null) ? oldTimeEntries : newTimeEntries;
        for (int i = 0; i < timeEntries.length; i++) {
            // only the time entry that doesn't have any exception will be audited
            if (causes[i] == null) {
                try {
                    audit((oldTimeEntries != null) ? oldTimeEntries[i] : null,
                            (newTimeEntries != null) ? newTimeEntries[i] : null);
                } catch (AuditManagerException e) {
                    causes[i] = new DataAccessException("Failed to audit the time entry.", e);
                }
            }
        }
    }

    /**
     * <p>
     * This method audit the given time entry using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * The <code>oldTimeEntry</code> is the time entry instance before update and the <code>newTimeEntry</code>
     * is the time entry instance after update.
     * </p>
     *
     * <p>
     * For INSERT operation, <code>oldTimeEntry</code> should be null while <code>newTimeEntry</code>
     * should not be null.
     * For UPDATE operation, both <code>oldTimeEntry</code> and <code>newTimeEntry</code> should not be null.
     * For DELETE operation, <code>oldTimeEntry</code> should not be null while <code>newTimeEntry</code>
     * should be null.
     * </p>
     *
     * @param newTimeEntry the time entry instance after update
     * @param oldTimeEntry the time entry instance before update
     *
     * @throws AuditManagerException if fails to audit the project.
     */
    private void audit(TimeEntry oldTimeEntry, TimeEntry newTimeEntry) throws AuditManagerException {
        TimeEntry timeEntry = (newTimeEntry == null) ? oldTimeEntry : newTimeEntry;
        AuditHeader header = new AuditHeader();
        header.setCompanyId(timeEntry.getCompanyId());
        header.setClientId(timeEntry.getClientId());
        header.setProjectId(timeEntry.getProjectId());
        header.setApplicationArea(ApplicationArea.TT_TIME);
        header.setTableName("time_entry");
        header.setEntityId(timeEntry.getId());
        header.setCreationUser(timeEntry.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        if (timeEntry.getModificationUserId() > 0) {
            header.setResourceId(timeEntry.getModificationUserId());
        }

        List auditDetails = new ArrayList();
        if (newTimeEntry != null && oldTimeEntry == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(Util.createAuditDetail("time_entry_id", null, String.valueOf(newTimeEntry.getId())));
            auditDetails.add(Util.createAuditDetail("company_id", null, String.valueOf(newTimeEntry.getCompanyId())));
            auditDetails.add(Util.createAuditDetail("client_id", null, String.valueOf(newTimeEntry.getClientId())));
            auditDetails.add(Util.createAuditDetail("project_id", null, String.valueOf(newTimeEntry.getProjectId())));
            auditDetails.add(Util.createAuditDetail("invoice_id", null, String.valueOf(newTimeEntry.getInvoiceId())));
            auditDetails.add(Util.createAuditDetail("time_status_id", null,
                String.valueOf(newTimeEntry.getStatus().getId())));
            auditDetails.add(Util.createAuditDetail("task_type_id", null,
                String.valueOf(newTimeEntry.getTaskType().getId())));
            auditDetails.add(Util.createAuditDetail("description", null, newTimeEntry.getDescription()));
            auditDetails.add(Util.createAuditDetail("entry_date", null, formatDate(newTimeEntry.getDate())));
            auditDetails.add(Util.createAuditDetail("hours", null, String.valueOf(newTimeEntry.getHours())));
            auditDetails.add(Util.createAuditDetail("billable", null, String.valueOf(newTimeEntry.getBillable())));
            auditDetails.add(Util.createAuditDetail("creation_date", null, formatTimestamp(newTimeEntry.getCreationDate())));
            auditDetails.add(Util.createAuditDetail("creation_user", null, newTimeEntry.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", null,
                    formatTimestamp(newTimeEntry.getModificationDate())));
            auditDetails.add(Util.createAuditDetail("modification_user", null, newTimeEntry.getModificationUser()));
        } else if (newTimeEntry == null && oldTimeEntry != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(Util.createAuditDetail("time_entry_id", String.valueOf(oldTimeEntry.getId()), null));
            auditDetails.add(Util.createAuditDetail("company_id", String.valueOf(oldTimeEntry.getCompanyId()), null));
            auditDetails.add(Util.createAuditDetail("client_id", String.valueOf(oldTimeEntry.getClientId()), null));
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldTimeEntry.getProjectId()), null));
            auditDetails.add(Util.createAuditDetail("invoice_id", String.valueOf(oldTimeEntry.getInvoiceId()), null));
            auditDetails.add(Util.createAuditDetail("time_status_id", String.valueOf(oldTimeEntry.getStatus().getId()),
                null));
            auditDetails.add(Util.createAuditDetail("task_type_id", String.valueOf(oldTimeEntry.getTaskType().getId()),
                null));
            auditDetails.add(Util.createAuditDetail("description", oldTimeEntry.getDescription(), null));
            auditDetails.add(Util.createAuditDetail("entry_date", formatDate(oldTimeEntry.getDate()), null));
            auditDetails.add(Util.createAuditDetail("hours", String.valueOf(oldTimeEntry.getHours()), null));
            auditDetails.add(Util.createAuditDetail("billable", String.valueOf(oldTimeEntry.getBillable()), null));
            auditDetails.add(Util.createAuditDetail("creation_date", formatTimestamp(oldTimeEntry.getCreationDate()), null));
            auditDetails.add(Util.createAuditDetail("creation_user", oldTimeEntry.getCreationUser(), null));
            auditDetails.add(Util.createAuditDetail("modification_date", formatTimestamp(oldTimeEntry.getModificationDate()),
                null));
            auditDetails.add(Util.createAuditDetail("modification_user", oldTimeEntry.getModificationUser(), null));
        } else {
            // for update operation
            header.setActionType(AuditType.UPDATE);
            auditDetails.add(Util.createAuditDetail("time_entry_id", String.valueOf(oldTimeEntry.getId()),
                String.valueOf(newTimeEntry.getId())));
            auditDetails.add(Util.createAuditDetail("company_id", String.valueOf(oldTimeEntry.getCompanyId()),
                String.valueOf(newTimeEntry.getCompanyId())));
            auditDetails.add(Util.createAuditDetail("client_id", String.valueOf(oldTimeEntry.getClientId()),
                    String.valueOf(newTimeEntry.getClientId())));
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldTimeEntry.getProjectId()),
                    String.valueOf(newTimeEntry.getProjectId())));
            auditDetails.add(Util.createAuditDetail("invoice_id", String.valueOf(oldTimeEntry.getInvoiceId()),
                String.valueOf(newTimeEntry.getInvoiceId())));
            auditDetails.add(Util.createAuditDetail("time_status_id", String.valueOf(oldTimeEntry.getStatus().getId()),
                String.valueOf(newTimeEntry.getStatus().getId())));
            auditDetails.add(Util.createAuditDetail("task_type_id", String.valueOf(oldTimeEntry.getTaskType().getId()),
                String.valueOf(newTimeEntry.getTaskType().getId())));
            auditDetails.add(Util.createAuditDetail("description", oldTimeEntry.getDescription(),
                newTimeEntry.getDescription()));
            auditDetails.add(Util.createAuditDetail("entry_date", formatDate(oldTimeEntry.getDate()),
                formatDate(newTimeEntry.getDate())));
            auditDetails.add(Util.createAuditDetail("hours", String.valueOf(oldTimeEntry.getHours()),
                String.valueOf(newTimeEntry.getHours())));
            auditDetails.add(Util.createAuditDetail("billable", String.valueOf(oldTimeEntry.getBillable()),
                String.valueOf(newTimeEntry.getBillable())));
            auditDetails.add(Util.createAuditDetail("creation_date", formatTimestamp(oldTimeEntry.getCreationDate()),
                formatTimestamp(newTimeEntry.getCreationDate())));
            auditDetails.add(Util.createAuditDetail("creation_user", oldTimeEntry.getCreationUser(),
                newTimeEntry.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", formatTimestamp(oldTimeEntry.getModificationDate()),
                formatTimestamp(newTimeEntry.getModificationDate())));
            auditDetails.add(Util.createAuditDetail("modification_user", oldTimeEntry.getModificationUser(),
                newTimeEntry.getModificationUser()));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));

        this.getAuditManager().createAuditRecord(header);
    }

    /**
     * Formats specified date as simple date (i.e. without time of the day).
     *
     * @return string representation of the date formatted as date.
     * @param date
     *            a date to format.
     */
    private static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * Formats specified date as time stamp (i.e. with time of the day).
     *
     * @return string representation of the date formatted as time stamp.
     * @param date
     *            a date to format.
     */
    private static String formatTimestamp(Date date) {
        return timestampFormat.format(date);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeEntries array.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeEntries An array of time entries for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntries is null, empty or contains null values or
     * same reference or has ids &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeEntries(TimeEntry[] timeEntries, boolean audit) throws BatchOperationException,
        DataAccessException {
        checkTimeEntries(timeEntries, false);
        Util.checkSameTimeTrackerBeans(timeEntries, "timeEntries");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeEntries.length];
            Util.skipNotChangedBeans(timeEntries, causes);

            List[] params = new List[timeEntries.length];
            long[] timeEntryIds = new long[timeEntries.length];

            for (int i = 0; i < timeEntries.length; i++) {
                params[i] = createUpdateParam(timeEntries[i]);
                timeEntryIds[i] = timeEntries[i].getId();
            }

            TimeEntry[] oldTimeEntries = getTimeEntries(conn, timeEntryIds, causes);

            // update the time entries in database
            try {
                Util.executeBatchUpdate(conn, UPDATE_TIME_ENTRY, params, timeEntryIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            if (audit) {
                audit(oldTimeEntries, timeEntries, causes);
            }

            Util.resetNotChangedExcetionCauses(causes);
            Util.finishBatchOperation(causes);
            Util.resetBeanChangedStates(timeEntries, causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to update a <code>TimeEntry</code>.
     * </p>
     *
     * @param entry the <code>TimeEntry</code> to update
     * @return the parameters needed to update a <code>TimeEntry</code>.
     */
    private List createUpdateParam(TimeEntry entry) {
        List params = new ArrayList();

        params.add(new Long(entry.getCompanyId()));
        params.add(new Long(entry.getClientId()));
        params.add(new Long(entry.getProjectId()));
        if (entry.getInvoiceId() > 0) {
            params.add(new Long(entry.getInvoiceId()));
        } else {
            params.add(new SqlType(Types.INTEGER));
        }
        params.add(new Long(entry.getStatus().getId()));
        params.add(new Long(entry.getTaskType().getId()));

        // description can be null in the database
        if (entry.getDescription() != null) {
            params.add(entry.getDescription());
        } else {
            params.add(new SqlType(Types.VARCHAR));
        }

        params.add(entry.getDate());
        params.add(new Double(entry.getHours()));
        params.add(new Long(entry.getBillable() ? 1 : 0));
        params.add(entry.getModificationDate());
        params.add(entry.getModificationUser());
        params.add(new Long(entry.getId()));

        return params;
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the TimeEntry with the
     * specified timeEntryIds.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0 or equal
     * time entry ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeEntries(long[] timeEntryIds, boolean audit) throws BatchOperationException,
        DataAccessException {
        checkTimeEntryIds(timeEntryIds);
        Util.checkEqualIds(timeEntryIds, "timeEntryIds");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeEntryIds.length];

            TimeEntry[] oldTimeEntries = getTimeEntries(conn, timeEntryIds, causes);
            Util.checkStepResult(causes);

            // get the delete parameters for the batch operation
            List allParams = new ArrayList();
            List deleteTimeEntryIds = new ArrayList();
            for (int i = 0; i < timeEntryIds.length; i++) {
                if (causes[i] == null) {
                    List params = new ArrayList();
                    params.add(new Long(timeEntryIds[i]));
                    deleteTimeEntryIds.add(new Long(timeEntryIds[i]));
                    allParams.add(params);
                }
            }

            long[] entityIds = new long[deleteTimeEntryIds.size()];
            for (int i = 0; i < deleteTimeEntryIds.size(); i++) {
                entityIds[i] = ((Long) deleteTimeEntryIds.get(i)).longValue();
            }

            // remove the records in the database
            try {
                Util.executeBatchUpdate(conn, DELETE_TIME_ENTRY,
                    (List[]) allParams.toArray(new List[allParams.size()]), entityIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            if (audit) {
                audit(oldTimeEntries, null, causes);
            }

            Util.finishBatchOperation(causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given time entry id array.
     * </p>
     *
     * @param timeTrackerIds the project id array to check
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains values &lt;= 0.
     */
    private void checkTimeEntryIds(long[] timeTrackerIds) {
        Util.checkNull(timeTrackerIds, "projectIds");

        // length check
        if (timeTrackerIds.length == 0) {
            throw new IllegalArgumentException("The given time entry ids array is empty.");
        }

        for (int i = 0; i < timeTrackerIds.length; i++) {
            Util.checkIdValue(timeTrackerIds[i], "time entry");
        }
    }

    /**
     * <p>
     * Retrieves an array of TimeEntry objects that reflects the data in the persistent store on the
     * Time Tracker Time Entry with the specified Ids.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeEntryIds An array of timeEntryIds for which the operation should be performed.
     * @return The TimeEntries corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeEntryIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getTimeEntries(long[] timeEntryIds) throws BatchOperationException, DataAccessException {
        checkTimeEntryIds(timeEntryIds);

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeEntryIds.length];

            TimeEntry[] timeEntries = getTimeEntries(conn, timeEntryIds, causes);

            Util.finishBatchOperation(causes);

            return timeEntries;

        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves an array of <code>TimeEntry</code> objects that reflects the data in the persistent
     * store on the Time Tracker Time Entry with the specified <code>timeEntryIds</code>.
     * </p>
     *
     * <p>
     * Note, the returned <code>TimeEntry</code> instances will have <tt>unchanged</tt> states.
     * </p>
     *
     *
     * @param conn the database connection to access the database
     * @param timeEntryIds An array of time entry id to get the complete beans
     * @param causes the inner causes of the batch operation
     * @return The time entries corresponding to the provided ids.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private TimeEntry[] getTimeEntries(Connection conn, long[] timeEntryIds, Throwable[] causes)
        throws DataAccessException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_TIME_ENTRIES
                + " WHERE " + Util.buildInClause("time_entry.time_entry_id", timeEntryIds));

            rs = pstmt.executeQuery();

            // this is a mapping from the time entry id to its time entry instance
            Map result = new HashMap();

            while (rs.next()) {
                TimeEntry timeEntry = getTimeEntry(rs);
                result.put(new Long(timeEntry.getId()), timeEntry);
            }

            TimeEntry[] timeEntries = new TimeEntry[timeEntryIds.length];

            // aggregate all the time entries according to given ids
            for (int i = 0; i < timeEntryIds.length; i++) {
                timeEntries[i] = (TimeEntry) result.get(new Long(timeEntryIds[i]));
                if (timeEntries[i] == null) {
                    causes[i] = new UnrecognizedEntityException(timeEntryIds[i], "The TimeEntry [" + timeEntryIds[i]
                        + "] is unrecognized.");
                }
            }

            return timeEntries;
        } catch (SQLException e) {
            throw new DataAccessException("Database access error occurs.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method creates a <code>TimeEntry</code> instance from the given <code>ResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>TimeEntry</code> instance will have <tt>unchanged</tt> state.
     * </p>
     *
     * @param rs the <code>ResultSet</code> instance
     * @return the <code>TimeEntry</code> created from the given <code>ResultSet</code> instance
     *
     * @throws SQLException if a database access error occurs
     * @throws UnrecognizedEntityException if the id is not in entity
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private TimeEntry getTimeEntry(ResultSet rs) throws SQLException, UnrecognizedEntityException,
        BatchOperationException, DataAccessException {
        TimeEntry timeEntry = new TimeEntry();

        int index = 1;
        timeEntry.setId(rs.getLong(index++));
        timeEntry.setCompanyId(rs.getLong(index++));
        timeEntry.setClientId(rs.getLong(index++));
        timeEntry.setProjectId(rs.getLong(index++));
        timeEntry.setInvoiceId(rs.getLong(index++));

        // get the time status association
        long statusId = rs.getLong(index++);
        timeEntry.setStatus(timeStatusDao.getTimeStatuses(new long[] {statusId})[0]);

        // get the task type association
        long taskTypeId = rs.getLong(index++);
        timeEntry.setTaskType(taskTypeDao.getTaskTypes(new long[] {taskTypeId})[0]);

        // description can be null in the database
        String description = rs.getString(index++);
        if (description != null) {
            timeEntry.setDescription(description);
        }

        timeEntry.setDate(rs.getDate(index++));
        timeEntry.setHours(rs.getDouble(index++));
        timeEntry.setBillable(rs.getInt(index++) == 1);
        timeEntry.setCreationDate(rs.getDate(index++));
        timeEntry.setCreationUser(rs.getString(index++));
        timeEntry.setModificationDate(rs.getDate(index++));
        timeEntry.setModificationUser(rs.getString(index++));

        timeEntry.setChanged(false);

        return timeEntry;
    }

    /**
     * <p>
     * Searches the persistent store for any time entries that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>TimeEntryFilterFactory</code> returned by <code>getTimeEntryFilterFactory</code> of this
     * instance, or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created using
     * <code>TimeEntryFilterFactory</code>.
     * </p>
     *
     * @param filter The filter used to search for TimeEntry.
     * @return The time entries satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] searchTimeEntries(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(filter);

            int size = result.getRecordCount();

            // get all the time entries from the search result
            TimeEntry[] timeEntries = new TimeEntry[size];
            for (int i = 0; i < size; i++) {
                result.next();
                timeEntries[i] = getTimeEntry(result);
            }

            return timeEntries;
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the database according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to retrieve the search result.", e);
        }
    }

    /**
     * <p>
     * This method creates a <code>TimeEntry</code> instance from the given <code>CustomResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>TimeEntry</code> instance will have <tt>unchanged</tt> state.
     * </p>
     *
     * @param result the <code>CustomResultSet</code> instance
     * @return the <code>TimeEntry</code> created from the given <code>CustomResultSet</code> instance
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance
     * @throws UnrecognizedEntityException if the id is not in entity
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private TimeEntry getTimeEntry(CustomResultSet result) throws InvalidCursorStateException,
        UnrecognizedEntityException, BatchOperationException, DataAccessException {
        TimeEntry timeEntry = new TimeEntry();

        int index = 1;
        timeEntry.setId(result.getLong(index++));
        timeEntry.setCompanyId(result.getLong(index++));
        timeEntry.setClientId(result.getLong(index++));
        timeEntry.setProjectId(result.getLong(index++));

        if (result.getObject(index) != null) {
            timeEntry.setInvoiceId(result.getLong(index++));
        } else {
            timeEntry.setInvoiceId(-1);
            ++index;
        }

        // get the time status association
        long statusId = result.getLong(index++);
        timeEntry.setStatus(timeStatusDao.getTimeStatuses(new long[] {statusId})[0]);

        // get the task type association
        long taskTypeId = result.getLong(index++);
        timeEntry.setTaskType(taskTypeDao.getTaskTypes(new long[] {taskTypeId})[0]);

        // the description can be null in the database
        String description = result.getString(index++);
        if (description != null) {
            timeEntry.setDescription(description);
        }

        timeEntry.setDate(result.getDate(index++));
        timeEntry.setHours(result.getDouble(index++));
        timeEntry.setBillable(result.getInt(index++) == 1);
        timeEntry.setCreationDate(result.getDate(index++));
        timeEntry.setCreationUser(result.getString(index++));
        timeEntry.setModificationDate(result.getDate(index++));
        timeEntry.setModificationUser(result.getString(index++));

        timeEntry.setChanged(false);

        return timeEntry;
    }

    /**
     * <p>
     * Retrieves the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for TimeEntries.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned
     * by the given factory should be used with this instance's <code>searchTimeEntries</code> method.
     * </p>
     *
     * @return the <code>TimeEntryFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for time entries.
     */
    public TimeEntryFilterFactory getTimeEntryFilterFactory() {
        return timeEntryFilterFactory;
    }

    /**
     * <p>
     * Retrieves all the TimeEntries that are currently in the persistent store.
     * </p>
     *
     * @return An array of all time entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeEntry[] getAllTimeEntries() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_TIME_ENTRIES);
            rs = pstmt.executeQuery();

            List timeEntries = new ArrayList();

            // get all the time entries
            while (rs.next()) {
                timeEntries.add(getTimeEntry(rs));
            }

            return (TimeEntry[]) timeEntries.toArray(new TimeEntry[timeEntries.size()]);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }
}
