/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeEntry;
import com.topcoder.timetracker.entry.time.TimeEntryRejectReasonDAO;

/**
 * <p>
 * This is an implementation of the <code>TimeEntryRejectReasonDAO</code> interface.
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
public class DbTimeEntryRejectReasonDAO extends BaseDAO implements TimeEntryRejectReasonDAO {
    /**
     * <p>
     * This is the sql script to insert a record to <b>time_reject_reason</b> table.
     * </p>
     */
    private static final String INSERT_TIME_REJECT_REASON = "insert into time_reject_reason(time_entry_id, "
        + "reject_reason_id, creation_date, creation_user, modification_date, modification_user) "
        + "values (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * This is the sql script to delete a record from <b>time_reject_reason</b> table.
     * </p>
     */
    private static final String DELETE_TIME_REJECT_REASON = "delete from time_reject_reason where "
        + "time_entry_id = ? and reject_reason_id = ?";

    /**
     * <p>
     * This is the sql script to select a record from <b>time_reject_reason</b> table.
     * </p>
     */
    private static final String SELECT_TIME_REJECT_REASON = "select reject_reason_id from "
        + "time_reject_reason where time_entry_id = ?";

    /**
     * <p>
     * This is the sql script to delete all the records for the given time entry id
     * in <b>time_reject_reason</b> table.
     * </p>
     */
    private static final String DELETE_ALL_TIME_REJECT_REASON = "delete from time_reject_reason "
        + "where time_entry_id = ?";

    /**
     * <p>
     * Constructor that accepts the necessary parameters to construct a DbTimeEntryDAO.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param auditManager The audit manager to use.
     *
     * @throws IllegalArgumentException if auditManager, connFactory or idGen is null or if connName,
     * idGen is an empty String.
     * @throws ConfigurationException it will never be thrown in this constructor
     */
    public DbTimeEntryRejectReasonDAO(DBConnectionFactory connFactory, String connName, AuditManager auditManager)
        throws ConfigurationException {
        super(connFactory, connName, null, null, null, auditManager);

        Util.checkNull(auditManager, "auditManager");
    }

    /**
     * <p>
     * This adds a <code>RejectReason</code> with the specified id to the specified TimeEntry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param timeEntry The TimeEntry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void addRejectReasonToEntry(long rejectReasonId, TimeEntry timeEntry, boolean audit)
        throws DataAccessException {
        Util.checkIdValue(rejectReasonId, "rejectReasonId");
        checkTimeEntrie(timeEntry);

        Connection conn = null;

        try {
            conn = this.getConnection();

            List params = new ArrayList();
            params.add(new Long(timeEntry.getId()));
            params.add(new Long(rejectReasonId));
            params.add(timeEntry.getCreationDate());
            params.add(timeEntry.getCreationUser());
            params.add(timeEntry.getModificationDate());
            params.add(timeEntry.getModificationUser());

            Util.executeUpdate(conn, INSERT_TIME_REJECT_REASON, params);

            // perform the audit if necessary
            if (audit) {
                audit(null, 0, timeEntry, rejectReasonId);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the manager.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given time entry.
     * </p>
     *
     * @param timeEntry the time entry to check
     *
     * @throws IllegalArgumentException if the time entry is null or has id &lt; 0
     * @throws DataAccessException if the time entry contain null property which is required in the persistence
     */
    private void checkTimeEntrie(TimeEntry timeEntry) throws DataAccessException {
        Util.checkNull(timeEntry, "timeEntrie");
        Util.checkIdValue(timeEntry.getId(), "time entry");

        // null entry date is not allowed
        if (timeEntry.getDate() == null) {
            throw new DataAccessException("Some time entries have null entry date.");
        }

        // null creation user is not allowed
        if (timeEntry.getCreationUser() == null) {
            throw new DataAccessException("Some time entries have null creation user.");
        }

        // null creation date is not allowed
        if (timeEntry.getCreationDate() == null) {
            throw new DataAccessException("Some time entries have null creation date.");
        }

        // null modification user is not allowed
        if (timeEntry.getModificationUser() == null) {
            throw new DataAccessException("Some time entries have null modification user.");
        }

        // null modification date is not allowed
        if (timeEntry.getModificationDate() == null) {
            throw new DataAccessException("Some time entries have null modification date.");
        }
    }

    /**
     * <p>
     * This method audit the given time entry association using the Time Tracker Audit component.
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
     * For DELETE operation, <code>oldTimeEntry</code> should not be null while <code>newTimeEntry</code>
     * should be null.
     * </p>
     *
     * @param newTimeEntry the project instance after update
     * @param newRejectReasonId the entry id after update
     * @param oldTimeEntry the project instance before update
     * @param oldRejectReasonId the entry id before update
     *
     * @throws AuditManagerException if fails to audit the time entry association.
     */
    private void audit(TimeEntry oldTimeEntry, long oldRejectReasonId, TimeEntry newTimeEntry, long newRejectReasonId)
        throws AuditManagerException {
        TimeEntry timeEntry = (newTimeEntry == null) ? oldTimeEntry : newTimeEntry;
        AuditHeader header = new AuditHeader();
        header.setCompanyId(timeEntry.getCompanyId());
        header.setApplicationArea(ApplicationArea.TT_TIME);
        header.setTableName("time_reject_reason");
        header.setEntityId(newTimeEntry == null ? oldRejectReasonId : newRejectReasonId);
        header.setCreationUser(timeEntry.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        //header.setResourceId(timeEntry.getId());

        List auditDetails = new ArrayList();
        if (newTimeEntry != null && oldTimeEntry == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(Util.createAuditDetail("time_entry_id", null, String.valueOf(newTimeEntry.getId())));
            auditDetails.add(Util.createAuditDetail("reject_reason_id", null, String.valueOf(newRejectReasonId)));
            auditDetails.add(Util.createAuditDetail("creation_date", null, newTimeEntry.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", null, newTimeEntry.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", null,
                newTimeEntry.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", null, newTimeEntry.getModificationUser()));
        } else if (newTimeEntry == null && oldTimeEntry != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(Util.createAuditDetail("time_entry_id", String.valueOf(oldTimeEntry.getId()), null));
            auditDetails.add(Util.createAuditDetail("reject_reason_id", String.valueOf(oldRejectReasonId), null));
            auditDetails.add(Util.createAuditDetail("creation_date", oldTimeEntry.getCreationDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("creation_user", oldTimeEntry.getCreationUser(), null));
            auditDetails.add(Util.createAuditDetail("modification_date", oldTimeEntry.getModificationDate().toString(),
                null));
            auditDetails.add(Util.createAuditDetail("modification_user", oldTimeEntry.getModificationUser(), null));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        this.getAuditManager().createAuditRecord(header);
    }

    /**
     * <p>
     * This removes a <code>RejectReason</code> with the specified id from the specified <code>TimeEntry</code>.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param rejectReasonId the id of the rejectReason for which the operation will be performed.
     * @param timeEntry the TimeEntry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if rejectReasonId is &lt; 0 or timeEntry is null or has id &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeRejectReasonFromEntry(long rejectReasonId, TimeEntry timeEntry, boolean audit)
        throws DataAccessException {
        this.checkTimeEntrie(timeEntry);
        Util.checkIdValue(rejectReasonId, "rejectReasonId");

        Connection conn = null;

        try {
            conn = this.getConnection();

            List params = new ArrayList();
            params.add(new Long(timeEntry.getId()));
            params.add(new Long(rejectReasonId));
            Util.executeUpdate(conn, DELETE_TIME_REJECT_REASON, params);

            // perform the audit if necessary
            if (audit) {
                audit(timeEntry, rejectReasonId, null, 0);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the reject reason.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This retrieves all RejectReasons for the specified <code>TimeEntry</code>.
     * </p>
     *
     * @param timeEntryId the id of the TimeEntry for which the operation will be performed.
     * @return The RejectReasons corresponding to given entry.
     *
     * @throws IllegalArgumentException if timeEntryId is &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] getAllRejectReasonsForEntry(long timeEntryId) throws DataAccessException {
        Util.checkIdValue(timeEntryId, "time entry");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_TIME_REJECT_REASON);
            List params = new ArrayList();
            params.add(new Long(timeEntryId));
            Util.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            // get all the reject reason ids for the given time entry id in the database
            List rejectReasonIds = new ArrayList();
            while (rs.next()) {
                rejectReasonIds.add(new Long(rs.getLong(1)));
            }

            return this.toLongArray(rejectReasonIds);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Converts the List to a long array.
     * </p>
     *
     * <p>
     * Note, the given List contains only Long element.
     * </p>
     *
     * @param ids the <code>List</code> instance containing a list of id in Long type
     *
     * @return the long array containing all the ids
     *
     * @throws IllegalArgumentException if ids is null
     */
    private long[] toLongArray(List ids) {
        Util.checkNull(ids, "ids");

        long[] idsArray = new long[ids.size()];

        // for each element in the list, converts to the corresponding element in the array
        Iterator it = ids.iterator();
        for (int i = 0; i < idsArray.length; i++) {
            idsArray[i] = ((Long) it.next()).longValue();
        }

        return idsArray;
    }

    /**
     * <p>
     * This removes all RejectReasons from the specified TimeEntry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param timeEntry the TimeEntry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if timeEntry is null or has id &lt; 0
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or the
     * time entry contain null property which is required in the persistence
     */
    public void removeAllRejectReasonsFromEntry(TimeEntry timeEntry, boolean audit) throws DataAccessException {
        this.checkTimeEntrie(timeEntry);

        Connection conn = null;

        try {
            conn = this.getConnection();

            long[] rejectReasonIds = getAllRejectReasonsForEntry(timeEntry.getId());

            List params = new ArrayList();
            params.add(new Long(timeEntry.getId()));
            Util.executeUpdate(conn, DELETE_ALL_TIME_REJECT_REASON, params);

            // perform the audit if necessary
            if (audit) {
                for (int i = 0; i < rejectReasonIds.length; i++) {
                    audit(timeEntry, rejectReasonIds[i], null, 0);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the reject reason.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }
}
