/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.base.persistence;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.entry.base.BaseDao;
import com.topcoder.timetracker.entry.base.ConfigurationException;
import com.topcoder.timetracker.entry.base.CutoffTimeBean;
import com.topcoder.timetracker.entry.base.CutoffTimeDao;
import com.topcoder.timetracker.entry.base.DuplicateEntryException;
import com.topcoder.timetracker.entry.base.EntryNotFoundException;
import com.topcoder.timetracker.entry.base.IdGenerationException;
import com.topcoder.timetracker.entry.base.ParameterCheck;
import com.topcoder.timetracker.entry.base.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>This is a specific implementation of the CutoffTimeDao interface and it implements the CRUD functionality for
 * a specific cut_off_time table presented for the Informix database.</p>
 *  <p>Thread Safety: It's thread-safe since it's immutable.</p>
 *
 * @author AleaActaEst, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class InformixCutoffTimeDao extends BaseDao implements CutoffTimeDao {
    /** Sql to insert record. */
    private static final String SQL_INSERT = "insert into cut_off_time (cut_off_time_id, company_id, cut_off_time,"
            + " creation_date, creation_user, modification_date, modification_user) values (?,?,?,?,?,?,?)";

    /** Sql to delete record by id. */
    private static final String SQL_DELETE_BY_ID = "delete from cut_off_time where cut_off_time_id = ?";

    /** Sql to update record. */
    private static final String SQL_UPDATE = "update cut_off_time set cut_off_time = ?, modification_date = ?,"
        + " modification_user =?  where cut_off_time_id = ? and company_id =?";

    /** Sql to select record by company id. */
    private static final String SQL_SELECT_BY_COMP_ID = "select cut_off_time_id, company_id, cut_off_time,"
        + " creation_date, creation_user, modification_date, modification_user from cut_off_time where company_id = ?";

    /** Sql to select record by id. */
    private static final String SQL_SELECT_BY_ID = "select cut_off_time_id, company_id, cut_off_time, creation_date,"
        + " creation_user, modification_date, modification_user from cut_off_time where cut_off_time_id = ?";

    /** Table name for audit message. */
    private static final String AUDIT_TABLE_NAME = "cut_off_time";

    /** Error code of UNIQUE_CONSTRAINT error for Informix DB. */
    private static final int ERR_UNIQUE_CONSTRAINT = -268;

    /** Error code of UNIQUE_INDEX error for Informix DB. */
    private static final int ERR_UNIQUE_INDEX = -239;

    /** Represents the columns structure of table "cut_off_time". This array is used to create AuditDetail. */
    private static final String[] CUT_OFF_TIME_COLUMNS = {
        "cut_off_time_id", "company_id", "cut_off_time", "creation_date", "creation_user", "modification_date",
        "modification_user"
    };

    /**
     * <p>
     * Creates a new InformixCutoffTimeDao instance with given arguments.
     * </p>
     *
     * @param connectionName configured connection name. Can be null, but not empty. If null is given, the default
     *  connection will be retrieved
     * @param idGeneratorName configured id generator name. Not null
     * @param dbConnectionFactory connection factory to use when generating new connections, not null.
     * @param auditManager Audit Manager to audit methods that change data when necessary. not null
     * @throws ConfigurationException  if failed to get IDGenerator from the given name
     * @throws IllegalArgumentException if any argument is invalid
     */
    public InformixCutoffTimeDao(String connectionName, String idGeneratorName,
        DBConnectionFactory dbConnectionFactory, AuditManager auditManager)
        throws ConfigurationException {
        super(connectionName, idGeneratorName, dbConnectionFactory, auditManager);
    }

    /**
     * <p>Creates a new cutoff time. cutoffTime, companyId, creationUser should be set as requirement for
     * creation, and the company must be existent.</p>
     *  <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean the java bean encapsulating the record data to create
     * @param audit boolean indicating whether to audit this operation
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null
     * @throws DuplicateEntryException if the id for that entry already exists, or the company has cutoff time already
     * @throws PersistenceException if any other persistence error occurs
     */
    public void createCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws PersistenceException {
        ParameterCheck.checkNull("cutoffTimeBean", cutoffTimeBean);

        long id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            long compId = cutoffTimeBean.getCompanyId();

            //checks if the cut off time for the same company exists, cutoffTime to company is one-one
            //restriction by program
            if (getCutoffTimeBeanByCompId(conn, compId) != null) {
                throw new DuplicateEntryException("duplicate cut off time for company:" + compId);
            }

            //generates id for the entry if its id is not set(<=0)
            id = cutoffTimeBean.getId();

            if (id <= 0) {
                id = nextId();
            }

            //uses current time for creation and modification date
            Timestamp now = getNow();
            String creationUser = cutoffTimeBean.getCreationUser();

            //creates the record
            pstmt = conn.prepareStatement(SQL_INSERT);
            pstmt.setLong(1, id);
            pstmt.setLong(2, compId);
            pstmt.setTimestamp(3, toTimestamp(cutoffTimeBean.getCutoffTime()));
            pstmt.setTimestamp(4, now);
            pstmt.setString(5, creationUser);
            pstmt.setTimestamp(6, now);
            pstmt.setString(7, creationUser); //uses creation user as modification user

            pstmt.executeUpdate();

            //sets data back to bean after creation
            cutoffTimeBean.setCreationDate(now);
            cutoffTimeBean.setModificationUser(creationUser);
            cutoffTimeBean.setModificationDate(now);
            cutoffTimeBean.setId(id);

            //sets is changed to false after creation
            cutoffTimeBean.setChanged(false);

            //create audit
            createAudit(null, cutoffTimeBean, audit);
        } catch (SQLException e) {
            //For Informix DB, use hard coded errorCode to perform direct judgment of duplication
            int errorCode = e.getErrorCode();

            if ((errorCode == ERR_UNIQUE_INDEX) || (errorCode == ERR_UNIQUE_CONSTRAINT)) {
                throw new DuplicateEntryException("duplicate cut off time id:", id);
            } else {
                throw new PersistenceException("failed to persist CutoffTimeBean", e);
            }
        } catch (IdGenerationException e) {
            throw new PersistenceException("failed to generate id for CutoffTimeBean", e);
        } finally {
            releaseResource(conn, pstmt);
        }
    }

    /**
     * <p>This is a method for deletion of a an existing cut off time record (in the persistence store).</p>
     *  <p>Note that for deletion, modification user should be set.</p>
     *  <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean the java bean encapsulating the record data to delete
     * @param audit boolean indicating whether to audit this operation
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null or its id is not set(&lt;=0) or no modification user
     *         is set
     * @throws EntryNotFoundException if the the entry does not exist in the database
     * @throws PersistenceException if any other persistence error occurs
     */
    public void deleteCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws PersistenceException {
        ParameterCheck.checkNull("cutoffTimeBean", cutoffTimeBean);

        long id = cutoffTimeBean.getId();
        ParameterCheck.checkNotPositive("cutoffTimeBean.id", id);

        String modificationUser = cutoffTimeBean.getModificationUser();
        ParameterCheck.checkNull("cutoffTimeBean.modificationUser", modificationUser);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            CutoffTimeBean oldValue = getCutoffTimeByID(conn, id);

            if (oldValue == null) {
                throw new EntryNotFoundException("cut off time with id:" + id + " is not found");
            }

            //checks if the cut off time for the same company exists
            pstmt = conn.prepareStatement(SQL_DELETE_BY_ID);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            //set the modification user from cutoffTimeBean for audit
            oldValue.setModificationUser(modificationUser);
            createAudit(oldValue, null, audit);
        } catch (SQLException e) {
            throw new PersistenceException("failed to delete CutoffTimeBean, id:" + id, e);
        } finally {
            releaseResource(conn, pstmt);
        }
    }

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * company id.</p>
     *
     * @param companyId company id
     *
     * @return CutoffTimeBean with given company id, null if the record not found
     *
     * @throws IllegalArgumentException if companyId is &lt;=0
     * @throws PersistenceException if any other persistence error occurs
     */
    public CutoffTimeBean fetchCutoffTimeByCompanyID(long companyId)
        throws PersistenceException {
        ParameterCheck.checkNotPositive("companyId", companyId);

        CutoffTimeBean result;
        Connection conn = null;

        try {
            conn = getConnection();

            result = getCutoffTimeBeanByCompId(conn, companyId);
        } catch (SQLException e) {
            throw new PersistenceException("failed to fetch CutoffTimeBean with companyId:" + companyId, e);
        } finally {
            releaseResource(conn, null);
        }

        return result;
    }

    /**
     * <p>This is a method for reading of a an existing cut off time record (in the persistence store) by
     * actual record PK.</p>
     *
     * @param cutoffTimeId cutoff time id
     *
     * @return CutoffTimeBean with given id, null if the record not found
     *
     * @throws IllegalArgumentException if companyId is &lt;=0
     * @throws PersistenceException if any other persistence error occurs
     */
    public CutoffTimeBean fetchCutoffTimeById(long cutoffTimeId)
        throws PersistenceException {
        ParameterCheck.checkNotPositive("cutoffTimeId", cutoffTimeId);

        CutoffTimeBean result;
        Connection conn = null;

        try {
            conn = getConnection();
            result = getCutoffTimeByID(conn, cutoffTimeId);
        } catch (SQLException e) {
            throw new PersistenceException("failed to fetch CutoffTimeBean, id:" + cutoffTimeId, e);
        } finally {
            releaseResource(conn, null);
        }

        return result;
    }

    /**
     * <p>This is a method for updating of a an existing cut off time record (in the persistence store). This
     * method will not update the companyId, creationUser, creationDate, and cutoffTime, modificationUser should not
     * be null. And note that if {@link CutoffTimeBean#isChanged()} is false, the updating will not take effect.</p>
     *  <p>Audit could be created by Time Tracker Audit component if audit flag is true.</p>
     *
     * @param cutoffTimeBean data bean
     * @param audit indicates whether to audit using Time Tracker Audit component
     *
     * @throws IllegalArgumentException if cutoffTimeBean is null or its id is not set(&lt;=0)
     * @throws EntryNotFoundException if the the entry does not exist in the database
     * @throws PersistenceException if any other persistence error occurs
     */
    public void updateCutoffTime(CutoffTimeBean cutoffTimeBean, boolean audit)
        throws PersistenceException {
        ParameterCheck.checkNull("cutoffTimeBean", cutoffTimeBean);

        long id = cutoffTimeBean.getId();
        ParameterCheck.checkNotPositive("cutoffTimeBean.id", id);

        //if the entry is not modified since the last retrieve, skip the update
        if (!cutoffTimeBean.isChanged()) {
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            CutoffTimeBean oldValue = getCutoffTimeByID(conn, id);

            //if the entry does not exit, throws EntryNotFoundException
            if (oldValue == null) {
                throw new EntryNotFoundException("cut off time with id:" + id + " is not found");
            }

            long compId = cutoffTimeBean.getCompanyId();

            //uses current time for modification time
            Timestamp now = getNow();

            pstmt = conn.prepareStatement(SQL_UPDATE);
            pstmt.setTimestamp(1, toTimestamp(cutoffTimeBean.getCutoffTime()));
            pstmt.setTimestamp(2, now);
            pstmt.setString(3, cutoffTimeBean.getModificationUser());
            pstmt.setLong(4, id);
            pstmt.setLong(5, compId);

            //no record being updated means the cutoffTime for that company does not exist
            if (pstmt.executeUpdate() != 1) {
                throw new EntryNotFoundException("cutoffTime id:" + id + " compId:" + compId
                    + " does not exist in persistence");
            }

            //changes the entry's state with new values
            cutoffTimeBean.setModificationDate(now);
            cutoffTimeBean.setChanged(false);

            //creates audit after update
            createAudit(oldValue, cutoffTimeBean, audit);
        } catch (SQLException e) {
            throw new PersistenceException("failed to update CutoffTimeBean, id:" + id, e);
        } finally {
            releaseResource(conn, pstmt);
        }
    }

    /**
     * Releases the given resources.
     *
     * @param conn connection to be closed
     * @param pstmt statement to be closed
     */
    private static void releaseResource(Connection conn, PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //does nothing
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                //does nothing
            }
        }
    }

    /**
     * Creates audit with TT Audit component with the given oldValue and newValue if audit flag is true.
     *
     * @param oldValue old value
     * @param newValue new value
     * @param audit audit flag whether to create audit message for the change of the value
     *
     * @throws PersistenceException if error occurs in Audit component
     */
    private void createAudit(CutoffTimeBean oldValue, CutoffTimeBean newValue, boolean audit)
        throws PersistenceException {
        if (audit) {
            AuditHeader header = new AuditHeader();

            //operation type
            int type;

            if (oldValue == null) {
                type = AuditType.INSERT;
            } else if (newValue == null) {
                type = AuditType.DELETE;
            } else {
                type = AuditType.UPDATE;
            }

            //columns changed detail
            String[] oldColumns = parseValues(oldValue);
            String[] newColumns = parseValues(newValue);
            List details = new ArrayList();

            for (int i = 0; i < oldColumns.length; i++) {
                //when in update type, the non-changed columns will not be audited
                if ((type == AuditType.UPDATE) && oldColumns[i].equals(newColumns[i])) {
                    continue;
                }

                AuditDetail detail = new AuditDetail();
                detail.setColumnName(CUT_OFF_TIME_COLUMNS[i]);
                detail.setOldValue(oldColumns[i]);
                detail.setNewValue(newColumns[i]);
                details.add(detail);
            }

            header.setDetails((AuditDetail[]) details.toArray(new AuditDetail[details.size()]));

            //the entity used to create header
            CutoffTimeBean entity = (oldValue == null) ? newValue : oldValue;
            header.setActionType(type);
            header.setApplicationArea(ApplicationArea.TT_TIME);
            header.setEntityId(entity.getId());
            header.setTableName(AUDIT_TABLE_NAME);
            header.setCompanyId(entity.getCompanyId());
            header.setCreationDate(getNow());

            if (type == AuditType.INSERT) {
                header.setCreationUser(entity.getCreationUser());
            } else {
                header.setCreationUser(entity.getModificationUser());
            }

            try {
                getAuditManager().createAuditRecord(header);
            } catch (AuditManagerException e) {
                throw new PersistenceException("failed to create audit", e);
            }
        }
    }

    /**
     * Extracts the next line of rs to CutoffTimeBean. If rs has no record, returns null.
     *
     * @param rs ResultSet from which the CutoffTimeBean to be extracted
     *
     * @return the extracted CutoffTimeBean
     *
     * @throws SQLException if any error occurs
     */
    private CutoffTimeBean extractRS(ResultSet rs) throws SQLException {
        if (rs.next()) {
            CutoffTimeBean bean = new CutoffTimeBean();
            bean.setId(rs.getLong(1));
            bean.setCompanyId(rs.getLong(2));
            bean.setCutoffTime(rs.getTimestamp(3));
            bean.setCreationDate(rs.getTimestamp(4));
            bean.setCreationUser(rs.getString(5));
            bean.setModificationDate(rs.getTimestamp(6));
            bean.setModificationUser(rs.getString(7));
            bean.setChanged(false);

            return bean;
        } else {
            return null;
        }
    }

    /**
     * Fetches the CutoffTimeBean with given company id.
     *
     * @param conn Connection which to make the query from
     * @param compId company id whose CutoffTimeBean to be obtained
     *
     * @return CutoffTimeBean, null if no record found
     *
     * @throws SQLException if error occurs in persistence
     */
    private CutoffTimeBean getCutoffTimeBeanByCompId(Connection conn, long compId)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(SQL_SELECT_BY_COMP_ID);
            pstmt.setLong(1, compId);

            ResultSet rs = pstmt.executeQuery();

            return extractRS(rs);
        } finally {
            releaseResource(null, pstmt);
        }
    }

    /**
     * Fetches the CutoffTimeBean with given id.
     *
     * @param conn Connection
     * @param id id whose CutoffTimeBean to be obtained
     *
     * @return CutoffTimeBean, null if no record found
     *
     * @throws SQLException if error occurs in persistence
     */
    private CutoffTimeBean getCutoffTimeByID(Connection conn, long id)
        throws SQLException {
        PreparedStatement pstmt = null;

        try {
            pstmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            pstmt.setLong(1, id);

            ResultSet rs = pstmt.executeQuery();

            return extractRS(rs);
        } finally {
            releaseResource(null, pstmt);
        }
    }

    /**
     * Get current time, and get rid of the nanos part.
     *
     * @return now without nanos
     */
    private Timestamp getNow() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        now.setNanos(0);

        return now;
    }

    /**
     * Parses the object's fields to String array by converting their values into string, and ordered them
     * corresponding to CUT_OFF_TIME_COLUMNS, the columns defined in DB.
     *
     * @param bean to parse
     *
     * @return string array of bean fields
     */
    private String[] parseValues(CutoffTimeBean bean) {
        if (bean == null) {
            return new String[CUT_OFF_TIME_COLUMNS.length];
        } else {
            return new String[] {
                String.valueOf(bean.getId()), String.valueOf(bean.getCompanyId()), bean.getCutoffTime().toString(),
                bean.getCreationDate().toString(), bean.getCreationUser(), bean.getModificationDate().toString(),
                bean.getModificationUser()
            };
        }
    }

    /**
     * Converts the date to timestamp.
     *
     * @param date date to convert
     *
     * @return Timestamp, null if date is null
     */
    private Timestamp toTimestamp(Date date) {
        if (date == null) {
            return null;
        }

        return new Timestamp(date.getTime());
    }
}
