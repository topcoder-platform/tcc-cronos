/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.db;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.fixedbilling.ConfigurationException;
import com.topcoder.timetracker.entry.fixedbilling.DataAccessException;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntry;
import com.topcoder.timetracker.entry.fixedbilling.FixedBillingEntryRejectReasonDAO;
import com.topcoder.timetracker.entry.fixedbilling.Helper;
import com.topcoder.timetracker.entry.fixedbilling.UnrecognizedEntityException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * This is an implementation of the <code>FixedBillingEntryRejectReasonDAO</code> interface that utilizes a database
 * with the schema provided in the Requirements Section of Time Tracker Fixed Billing Entry 1.0.
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
public class DbFixedBillingEntryRejectReasonDAO extends BaseDAO implements FixedBillingEntryRejectReasonDAO {
    /** The SQL String for insert. */
    private static final String INSERT_REASON_SQL = "insert into fb_reject_reason(fix_bill_entry_id, "
        + "reject_reason_id, creation_date, creation_user,modification_date,modification_user) values (?,?,?,?,?,?)";

    /** The SQL String for select. */
    private static final String SELECT_ENTRY_SQL = "select * from fix_bill_entry where fix_bill_entry_id = ? ";

    /** The SQL String for delete. */
    private static final String DELETE_REASON_SQL = "delete from fb_reject_reason where fix_bill_entry_id=? "
        + "and reject_reason_id=?";

    /** The SQL String for delete all. */
    private static final String DELETE_ALL_REASON_SQL = "delete from fb_reject_reason where fix_bill_entry_id=? ";

    /** The SQL String for select. */
    private static final String SELECT_REASON_SQL = "select reject_reason_id from fb_reject_reason where "
        + "fix_bill_entry_id=? ";

    /** The SQL String for select. */
    private static final String SELECT_SINGLE_REASON_SQL = "select * from fb_reject_reason where "
        + "fix_bill_entry_id=? and reject_reason_id=?";

    /** The SQL String field for reject reason id. */
    private static final String REJECT_REASON_ID_FIELD = "reject_reason_id";

    /** The SQL String field for creation date. */
    private static final String CREATION_DATE_FIELD = "creation_date";

    /** The SQL String field for creation user. */
    private static final String CREATION_USER_FIELD = "creation_user";

    /** The SQL String field for modification date. */
    private static final String MODIFICATION_DATE_FIELD = "modification_date";

    /** The SQL String field for modification user. */
    private static final String MODIFICATION_USER_FIELD = "modification_user";

    /** The SQL String field for fix bill entry id. */
    private static final String FIX_BILL_ENTRY_ID = "fix_bill_entry_id";

    /**
     * <p>
     * Constructor accepting the necessary parameters for this implementation to work properly.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use.  If null, the default connection is used.
     * @param auditor The AuditManager to use.
     *
     * @throws IllegalArgumentException if connFactory is null, or if connName is an empty String.
     * @throws ConfigurationException if a problem occurs.
     */
    public DbFixedBillingEntryRejectReasonDAO(DBConnectionFactory connFactory, String connName, AuditManager auditor)
        throws ConfigurationException {
        super(connFactory, connName, null, null, null, auditor);
        Helper.checkNull("auditor", auditor);
    }

    /**
     * <p>
     * This adds RejectReason with the specified id to the specified entry.
     * </p>
     *
     * @param rejectReasonId The id of rejectReason to add to the Entry.
     * @param entry The id of entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id is &lt;= 0.
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addRejectReasonToEntry(long rejectReasonId, FixedBillingEntry entry, boolean audit)
        throws DataAccessException {
        Helper.checkLongValue("rejectReasonId", rejectReasonId);
        checkEntry(entry);
        //Check the required column whether null.
        Helper.checkRequiredColumn("entry.getModificationUser()", entry.getModificationUser());

        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            //The given entry should be in the database, if not, throw exception.
            verifyEntries(entry, conn);
            state = conn.prepareStatement(INSERT_REASON_SQL);

            Timestamp current = new Timestamp(System.currentTimeMillis());
            String userName = entry.getModificationUser();
            int index = 0;
            state.setLong(++index, entry.getId());
            state.setLong(++index, rejectReasonId);
            state.setTimestamp(++index, current);
            state.setString(++index, userName);
            state.setTimestamp(++index, current);
            state.setString(++index, userName);
            state.executeUpdate();

            if (audit) {
                //Do the audit.
                audit(entry, AuditType.INSERT, rejectReasonId, current, userName, current, userName);
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("Unable to audit the records.", ame);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }
    }

    /**
     * Check whether the given FixedBillingEntry is valid.
     *
     * @param entry the FixedBillingEntry instance.
     *
     * @throws IllegalArgumentException if the FixedBillingEntry is invalid.
     */
    private void checkEntry(FixedBillingEntry entry) {
        Helper.checkNull("entry", entry);
        Helper.checkLongValue("entry.getId()", entry.getId());
    }

    /**
     * Audit the changed FixedBillingEntry.
     *
     * @param entry the FixedBillingEntry instance.
     * @param actionType the actionType.
     * @param rejectReasonId the reject reason id.
     * @param createDate the create date.
     * @param createUser the create user.
     * @param modifyDate the modify user.
     * @param modifyUser the modify date.
     *
     * @throws AuditManagerException if fail to audit.
     */
    private void audit(FixedBillingEntry entry, int actionType, long rejectReasonId, Date createDate,
        String createUser, Date modifyDate, String modifyUser)
        throws AuditManagerException {
        //Create the audit header.
        AuditHeader auditHeader = new AuditHeader();
        auditHeader.setCompanyId(entry.getCompanyId());
        auditHeader.setTableName("fb_reject_reason");
        auditHeader.setApplicationArea(ApplicationArea.TT_FIXED_BILLING);
        auditHeader.setEntityId(rejectReasonId);
        auditHeader.setCreationUser(entry.getModificationUser());
        auditHeader.setCreationDate(new Timestamp(System.currentTimeMillis()));
        auditHeader.setActionType(actionType);
        //auditHeader.setResourceId(entry.getId());

        //Create the audit detail.
        List auditDetails = new ArrayList();
        addAuditDetail(auditDetails, FIX_BILL_ENTRY_ID, String.valueOf(entry.getId()), actionType);
        addAuditDetail(auditDetails, REJECT_REASON_ID_FIELD, String.valueOf(rejectReasonId), actionType);
        addAuditDetail(auditDetails, CREATION_DATE_FIELD, createDate.toString(), actionType);
        addAuditDetail(auditDetails, CREATION_USER_FIELD, createUser, actionType);
        addAuditDetail(auditDetails, MODIFICATION_DATE_FIELD, modifyDate.toString(), actionType);
        addAuditDetail(auditDetails, MODIFICATION_USER_FIELD, modifyUser, actionType);
        auditHeader.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        //Create the audit record.
        getAuditManager().createAuditRecord(auditHeader);
    }

    /**
     * Create the audit details.
     *
     * @param auditDetails the details list.
     * @param columnName the column name.
     * @param value the value to insert.
     * @param auditType the audit type.
     */
    private void addAuditDetail(List auditDetails, String columnName, String value, int auditType) {
        AuditDetail auditDetail = new AuditDetail();

        if (auditType == AuditType.INSERT) {
            auditDetail.setNewValue(value);
        } else if (auditType == AuditType.DELETE) {
            auditDetail.setOldValue(value);
        }

        auditDetail.setColumnName(columnName);
        auditDetails.add(auditDetail);
    }

    /**
     * Check whether the given entry is in the database.
     *
     * @param entry the FixedBillingEntry entry.
     * @param conn the given database connection.
     *
     * @throws UnrecognizedEntityException if the entry is not in the database.
     * @throws DataAccessException if any error occurs.
     */
    private void verifyEntries(FixedBillingEntry entry, Connection conn)
        throws DataAccessException {
        PreparedStatement state = null;
        ResultSet rs = null;

        try {
            state = conn.prepareStatement(SELECT_ENTRY_SQL);
            state.setLong(1, entry.getId());
            rs = state.executeQuery();

            //If not found the record, throw exception.
            if (!rs.next()) {
                throw new UnrecognizedEntityException(entry.getId());
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(null, state, rs);
        }
    }

    /**
     * <p>
     * This removes a RejectReason with the specified id from the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param rejectReasonId the id of the rejectReason for which the operation will be performed.
     * @param entry the id of the entry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id is &lt;= 0.
     * @throws UnrecognizedEntityException if a rejectReason or entry was not found with specified id.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeRejectReasonFromEntry(long rejectReasonId, FixedBillingEntry entry, boolean audit)
        throws DataAccessException {
        Helper.checkLongValue("rejectReasonId", rejectReasonId);
        checkEntry(entry);

        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();
            //Check whether the entry is in the database, if not, throw exception.
            verifyEntries(entry, conn);
            //If need to audit, get the date and user at first.
            List list = null;

            if (audit) {
                list = getReasonValues(rejectReasonId, entry);
            }
            //Delete the records.
            state = conn.prepareStatement(DELETE_REASON_SQL);
            state.setLong(1, entry.getId());
            state.setLong(2, rejectReasonId);
            state.executeUpdate();

            //If need to audit and can find the record, do audit.
            int index = 0;
            if (audit && !list.isEmpty()) {
                audit(entry, AuditType.DELETE, rejectReasonId, (Date) list.get(index++), (String) list.get(index++),
                    (Date) list.get(index++), (String) list.get(index++));
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("Unable to audit the records.", ame);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }

    /**
     * Get the reject reason details.
     *
     * @param rejectReasonId the reject reason id.
     * @param entry the given FixedBillingEntry.
     *
     * @return a list contains creation date, creation user, modification user, modification date.
     *
     * @throws DataAccessException If unable to query the database.
     */
    private List getReasonValues(long rejectReasonId, FixedBillingEntry entry)
        throws DataAccessException {
        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try {
            conn = getConnection();

            state = conn.prepareStatement(SELECT_SINGLE_REASON_SQL);
            state.setLong(1, entry.getId());
            state.setLong(2, rejectReasonId);
            rs = state.executeQuery();

            if (rs.next()) {
                list.add(rs.getDate(CREATION_DATE_FIELD));
                list.add(rs.getString(CREATION_USER_FIELD));
                list.add(rs.getDate(MODIFICATION_DATE_FIELD));
                list.add(rs.getString(MODIFICATION_USER_FIELD));
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }

        return list;
    }

    /**
     * <p>
     * This retrieves all RejectReasons for the specified entry.
     * </p>
     *
     * @param entryId the id of the entry for which the operation will be performed.
     *
     * @return The RejectReasons corresponding to given entry.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[] getAllRejectReasonsForEntry(long entryId)
        throws DataAccessException {
        Helper.checkLongValue("entryId", entryId);

        Connection conn = null;
        PreparedStatement state = null;
        ResultSet rs = null;
        long[] result = null;

        try {
            conn = getConnection();

            state = conn.prepareStatement(SELECT_REASON_SQL);
            state.setLong(1, entryId);
            rs = state.executeQuery();

            List list = new ArrayList();

            while (rs.next()) {
                list.add(new Long(rs.getLong(REJECT_REASON_ID_FIELD)));
            }

            //Convert the list to array.
            result = new long[list.size()];

            int i = 0;

            for (Iterator itr = list.iterator(); itr.hasNext();) {
                result[i++] = ((Long) itr.next()).longValue();
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, rs);
        }

        return result;
    }

    /**
     * <p>
     * This removes all RejectReasons from the specified entry.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param entry the id of the entry for which the operation will be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     * @throws IllegalArgumentException if entryId &lt;= 0.
     */
    public void removeAllRejectReasonsFromEntry(FixedBillingEntry entry, boolean audit)
        throws DataAccessException {
        checkEntry(entry);

        long[] reasonIds = null;
        Connection conn = null;
        PreparedStatement state = null;

        try {
            conn = getConnection();
            //Check whether the entry is in the database, if not, throw exception.
            verifyEntries(entry, conn);
            //If need to audit, get the date and user at first.
            Map oldValues = new HashMap();

            if (audit) {
                reasonIds = getAllRejectReasonsForEntry(entry.getId());
                for (int i=0; i<reasonIds.length; i++) {
                    oldValues.put(new Long(reasonIds[i]), getReasonValues(reasonIds[i], entry));
                }
            }
            //Delete the records.
            state = conn.prepareStatement(DELETE_ALL_REASON_SQL);
            state.setLong(1, entry.getId());
            state.executeUpdate();

            //If need to audit and can find the record, do audit.
            if (audit && !oldValues.isEmpty()) {
                int index;
                List list;
                for (int i=0; i<reasonIds.length; i++) {
                    index = 0;
                    list = (List)oldValues.get(new Long(reasonIds[i]));
                    audit(entry, AuditType.DELETE, reasonIds[i], (Date) list.get(index++), (String) list.get(index++),
                        (Date) list.get(index++), (String) list.get(index++));
                }
            }
        } catch (AuditManagerException ame) {
            throw new DataAccessException("Unable to audit the records.", ame);
        } catch (SQLException sqle) {
            throw new DataAccessException("Unable to query the database.", sqle);
        } finally {
            Helper.closeResources(conn, state, null);
        }
    }
}
