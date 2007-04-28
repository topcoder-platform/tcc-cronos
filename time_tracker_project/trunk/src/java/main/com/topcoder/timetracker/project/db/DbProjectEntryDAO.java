/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.DuplicateEntityException;
import com.topcoder.timetracker.project.Project;
import com.topcoder.timetracker.project.ProjectEntryDAO;

/**
 * <p>
 * This is the interface definition for the DAO that is responsible handling the association and
 * de-association of an Entry identifier with a project.
 * </p>
 *
 * <p>
 * It is also capable of retrieving the entries that have been associated with a particular project.
 * </p>
 *
 * <p>
 * The type of Entry (<tt>Time</tt>/<tt>Expense</tt>/<tt>Fixed Billing</tt>) that is being supported
 * will depend on the database table and column names  that are supplied to this class.
 * </p>
 *
 * <p>
 * In the current implementation, the following will need to be provided::
 * <ul>
 * <li>Fixed Billing Entries:
 *                  - Table Name: project_fix_bill
 *                  - Entry Column Name: fix_bill_entry_id
 *                  - Project Column Name: project_id
 * </li>
 * <li>Time Entries:
 *                  - Table Name: project_time
 *                  - Entry Column Name: time_entry_id
 *                  - Project Column Name: project_id
 * </li>
 * <li>Expense Entries:
 *                  - Table Name: project_expense
 *                  - Entry Column Name: expense_entry_id
 *                  - Project Column Name: project_id
 * </li>
 * </ul>
 * </p>
 *
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner. For database access, the Time Tracker Application
 * will be making use of Container managed transactions, so JDBC transactions are NOT used.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectEntryDAO extends BaseDAO implements ProjectEntryDAO {
    /**
     * <p>
     * This is the name of the database table which contains information
     * regarding the relationship between a Project and an Entry.
     * </p>
     *
     * <p>
     * The table name depends on the type of Entry that is being used.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null or empty string.
     * </p>
     */
    private final String tableName;

    /**
     * <p>
     * This is the name of the database column within the table that is used to contain a foreign
     * key to the project.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null or empty string.
     * </p>
     */
    private final String projectColumnName;

    /**
     * <p>
     * This is the name of the database column within the table that is used to contain a foreign
     * key to the entry.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null or empty string.
     * </p>
     */
    private final String entryColumnName;

    /**
     * <p>
     * Represents the sql script to insert a record to associate the project and the entry.
     * </p>
     */
    private final String addEntrySql;

    /**
     * <p>
     * Represents the sql script to delete a record to associate the project and the entry.
     * </p>
     */
    private final String delEntrySql;

    /**
     * <p>
     * Represents the sql script to get the entries for a project.
     * </p>
     */
    private final String getEntrySql;

    /**
     * <p>
     * Represents the sql script to check where there is any association between the project and the entry.
     * </p>
     */
    private final String checkEntrySql;

    /**
     * <p>
     * Constructor that allows a <code>DbProjectEntryDAO</code> to be created with the specified connection
     * options and audit manager, and database information.
     * </p>
     *
     * @param connFact The connection factory to use.
     * @param connName The connection name to use (if null,then default connection is used)
     * @param auditor The audit manager to use.
     * @param projIdColname The column name of the project id.
     * @param entryIdColname The column name of the entry id.
     * @param tableName The database table name.
     *
     * @throws IllegalArgumentException if any parameter is null (except connName) or any String is an empty String.
     * @throws ConfigurationException it will never thrown for this class. It is thrown by super class and so this
     * class have to throw it for compilation
     */
    public DbProjectEntryDAO(DBConnectionFactory connFact, String connName, AuditManager auditor, String projIdColname,
        String entryIdColname, String tableName) throws ConfigurationException {
        super(connFact, connName, null, null, null, auditor);

        Util.checkNull(auditor, "auditor");
        Util.checkString(projIdColname, "projIdColname");
        Util.checkString(entryIdColname, "entryIdColname");
        Util.checkString(tableName, "tableName");

        this.tableName = tableName;
        this.projectColumnName = projIdColname;
        this.entryColumnName = entryIdColname;

        // sql script for inserting project entry record
        addEntrySql = "insert into " + tableName + "(" + projectColumnName + ", " + entryColumnName
            + ", creation_date, creation_user, modification_date, modification_user) values (?,?,?,?,?,?)";

        // sql script for deleting project entry record
        delEntrySql = "delete from " + tableName + " where " + projectColumnName + " = ? and " + entryColumnName
            + " = ?";

        // sql script for getting the project entry record
        getEntrySql = "select " + projectColumnName + ", " + entryColumnName + " from " + tableName + " where ";

        // sql script for checking the project entry record exists or not
        checkEntrySql = "select * from " + tableName + " where " + projectColumnName + " = ? and " + entryColumnName
            + " = ?";
    }

    /**
     * <p>
     * Associates the specified entry with the project.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id has a value &lt;= 0.
     * @throws DuplicateEntityException if the entry has already been associated with the project.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void addEntryToProject(Project project, long entryId, boolean audit) throws DataAccessException {
        this.checkProject(project);
        Util.checkIdValue(entryId, "entry");

        Connection conn = null;

        try {
            conn = this.getConnection();

            if (isEntryAdded(conn, project.getId(), entryId)) {
                throw new DuplicateEntityException(entryId);
            }

            List params = new ArrayList();
            params.add(new Long(project.getId()));
            params.add(new Long(entryId));
            params.add(project.getCreationDate());
            params.add(project.getCreationUser());
            params.add(project.getModificationDate());
            params.add(project.getModificationUser());
            Util.executeUpdate(conn, addEntrySql, params);

            // perform the audit if necessary
            if (audit) {
                audit(null, 0, project, entryId);
            }
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the entry.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given project.
     * </p>
     *
     * @param project the given project to check
     *
     * @throws IllegalArgumentException if project is null or contains null property which
     * is required in the persistence
     */
    private void checkProject(Project project) {
        Util.checkNull(project, "project");

        // the project id is not set
        if (project.getId() <= 0) {
            throw new IllegalArgumentException("The project id is not valid.");
        }

        // null creation date is not allowed
        if (project.getCreationDate() == null) {
            throw new IllegalArgumentException("The project has null creation date.");
        }

        // null creation user is not allowed
        if (project.getCreationUser() == null) {
            throw new IllegalArgumentException("The project has null creation user.");
        }

        // null modification date is not allowed
        if (project.getModificationDate() == null) {
            throw new IllegalArgumentException("The project has null modification date.");
        }

        // null modification user is not allowed
        if (project.getModificationUser() == null) {
            throw new IllegalArgumentException("The project has null modification user.");
        }
    }

    /**
     * <p>
     * This method audit the given project-entry association using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * The <code>oldProject</code> is the project instance before update and the <code>newProject</code>
     * is the project instance after update.
     * </p>
     *
     * <p>
     * For INSERT operation, <code>oldProject</code> should be null while <code>newProject</code> should not be null.
     * For DELETE operation, <code>oldProject</code> should not be null while <code>newProject</code> should be null.
     * </p>
     *
     * @param newProject the project instance after update
     * @param newEntryId the entry id after update
     * @param oldProject the project instance before update
     * @param oldEntryId the entry id before update
     *
     * @throws AuditManagerException if fails to audit the project-entry association.
     */
    private void audit(Project oldProject, long oldEntryId, Project newProject, long newEntryId)
        throws AuditManagerException {
        Project project = (newProject == null) ? oldProject : newProject;
        AuditHeader header = new AuditHeader();
        header.setCompanyId(project.getCompanyId());
        header.setApplicationArea(ApplicationArea.TT_PROJECT);
        header.setTableName(this.tableName);
        header.setEntityId(newProject == null ? oldEntryId : newEntryId);
        header.setCreationUser(project.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        header.setProjectId(project.getId());

        List auditDetails = new ArrayList();
        if (newProject != null && oldProject == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(Util.createAuditDetail(projectColumnName, null, String.valueOf(newProject.getId())));
            auditDetails.add(Util.createAuditDetail(entryColumnName, null, String.valueOf(newEntryId)));
            auditDetails.add(Util.createAuditDetail("creation_date", null, newProject.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", null, newProject.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", null,
                newProject.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", null, newProject.getModificationUser()));
        } else if (newProject == null && oldProject != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(Util.createAuditDetail(projectColumnName, String.valueOf(oldProject.getId()), null));
            auditDetails.add(Util.createAuditDetail(entryColumnName, String.valueOf(oldEntryId), null));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProject.getCreationDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProject.getCreationUser(), null));
            auditDetails.add(Util.createAuditDetail("modification_date", oldProject.getModificationDate().toString(),
                null));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProject.getModificationUser(), null));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        this.getAuditManager().createAuditRecord(header);
    }

    /**
     * <p>
     * This disassociates an Entry with the project.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param project The project for which the operation should be performed.
     * @param entryId The id of the entry for which the operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if any id has a value &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void removeEntryFromProject(Project project, long entryId, boolean audit) throws DataAccessException {
        // delete from project_expense where project_id = ? and expense_entry_id = ?
        this.checkProject(project);
        Util.checkIdValue(entryId, "entry");

        Connection conn = null;

        try {
            conn = this.getConnection();

            List params = new ArrayList();
            params.add(new Long(project.getId()));
            params.add(new Long(entryId));
            Util.executeUpdate(conn, delEntrySql, params);

            // perform the audit if necessary
            if (audit) {
                audit(project, entryId, null, 0);
            }
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the entry.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks whether the project and entry association exists in the database.
     * </p>
     *
     * @param conn the database connection to access database
     * @param projectId the project id
     * @param entryId the entry id
     * @return true if the association exists in database, false otherwise
     *
     * @throws SQLException if a database access error occurs
     */
    private boolean isEntryAdded(Connection conn, long projectId, long entryId) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(checkEntrySql);

            List params = new ArrayList();
            params.add(new Long(projectId));
            params.add(new Long(entryId));
            Util.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            return rs.next();
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * Retrieves all the entries of the specified type that are associated with the specified projects.
     * </p>
     *
     * <p>
     * The return values will be stored such that the return value at position <b>i</b> will
     * correspond to the types input as position <b>i</b>.  If no entities are found at a position,
     * then the corresponding position of the return values should be an empty array.
     * </p>
     *
     * @param projectIds An array of ids of the projects for which the operation should be performed.
     * @return A 2D array representing the entities ids for each project.
     *
     * @throws IllegalArgumentException if projectIds is null, empty or contains elements &lt;= 0.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public long[][] retrieveEntriesForProjects(long[] projectIds) throws DataAccessException {
        Util.checkNull(projectIds, "projectIds");

        if (projectIds.length == 0) {
            throw new IllegalArgumentException("The given project ids array is empty.");
        }

        for (int i = 0; i < projectIds.length; i++) {
            Util.checkIdValue(projectIds[i], "project");
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();

            pstmt = conn.prepareStatement(getEntrySql + Util.buildInClause(projectColumnName, projectIds));
            rs = pstmt.executeQuery();

            // this is a mapping from the project id to a list containing all the entries for the project
            Map result = new HashMap();
            while (rs.next()) {
                Long projectId = new Long(rs.getLong(1));
                Long entryId = new Long(rs.getLong(2));

                // add the mapping of project id and entry id
                List entryIds = (List) result.get(projectId);
                if (entryIds == null) {
                    entryIds = new ArrayList();
                    result.put(projectId, entryIds);
                }
                entryIds.add(entryId);
            }

            long[][] entries = new long[projectIds.length][];
            for (int i = 0; i < projectIds.length; i++) {
                List entriesForProject = (List) result.get(new Long(projectIds[i]));

                // if a project doesn't have any entry, then empty array is used
                if (entriesForProject == null) {
                    entries[i] = new long[0];
                } else {
                    entries[i] = new long[entriesForProject.size()];

                    // converts the list in the mapping to an array
                    for (int j = 0; j < entriesForProject.size(); j++) {
                        entries[i][j] = ((Long) entriesForProject.get(j)).longValue();
                    }
                }
            }

            return entries;
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to query database.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }
}
