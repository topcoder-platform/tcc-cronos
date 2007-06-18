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
import java.util.List;

import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.project.ConfigurationException;
import com.topcoder.timetracker.project.DataAccessException;
import com.topcoder.timetracker.project.ProjectManagerDAO;
import com.topcoder.timetracker.project.ProjectManager;
import com.topcoder.timetracker.project.ProjectManagerFilterFactory;
import com.topcoder.timetracker.project.UnrecognizedEntityException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.timetracker.audit.ApplicationArea;
import com.topcoder.timetracker.audit.AuditDetail;
import com.topcoder.timetracker.audit.AuditHeader;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.audit.AuditManagerException;
import com.topcoder.timetracker.audit.AuditType;

/**
 * <p>
 * This is default implementation of the <code>ProjectManagerDAO</code>.
 *
 * <p>
 * It supports all the methods needed to manipulate and retrieve a <code>ProjectManager</code>
 * in the data store.
 * </p>
 *
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner. For database access, the Time Tracker
 * Application will be making use of Container managed transactions, so JDBC transactions
 * are NOT be used.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectManagerDAO extends BaseDAO implements ProjectManagerDAO {

    /**
     * <p>
     * This is the application space that will be used and provided to the Time Tracker Auditor
     * if an audit is requested.
     * </p>
     */
    public static final String AUDIT_APPLICATION_AREA = "TT_PROJECT_MANAGER";

    /**
     * <p>
     * Represents the sql script to insert a record to project_manager table.
     * </p>
     */
    private static final String INSERT_PROJECT_MANAGER = "insert into project_manager (project_id, "
        + "user_account_id, pay_rate, cost, active, creation_date, creation_user, modification_date, "
        + "modification_user) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to update a record in project_manager table.
     * </p>
     */
    private static final String UPDATE_PROJECT_MANAGER = "update project_manager set pay_rate = ?, "
        + "cost = ?, active = ?, creation_date = ?, creation_user = ?, modification_date = ?, "
        + "modification_user = ? where project_id = ? and user_account_id = ?";

    /**
     * <p>
     * Represents the sql script to delete a record from project_manager table.
     * </p>
     */
    private static final String DELETE_PROJECT_MANAGER = "delete from project_manager where "
        + "project_id = ? and user_account_id = ?";

    /**
     * <p>
     * Represents the sql script to select a record from project_manager table.
     * </p>
     */
    private static final String SELECT_PROJECT_MANAGER = "select project_id, user_account_id, pay_rate, "
        + "cost, active, creation_date, creation_user, modification_date, modification_user from "
        + "project_manager where project_id = ? and user_account_id = ?";

    /**
     * <p>
     * Represents the sql script to insert all the records in project_manager table.
     * </p>
     */
    private static final String SELECT_ALL_PMS = "select project_id, user_account_id, pay_rate, cost, active, "
        + "creation_date, creation_user, modification_date, modification_user from project_manager";

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for searching the
     * data store for ProjectManagers using this implementation.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It will never be null.
     * </p>
     */
    private final DbProjectManagerFilterFactory filterFactory;

    /**
     * <p>
     * Constructor that accepts the necessary parameters needed for the Database
     * to function properly.
     * </p>
     *
     * @param connName The connection name to use (if null, then default connection is used)
     * @param connFactory The connection factory to use.
     * @param searchManagerNamespace the configuration namespace used to initialize the search bundle manager.
     * @param searchBundleName the name of the search bundle
     * @param auditor The auditor that is going to be used.
     *
     * @throws IllegalArgumentException if connFactory, auditor or searchStrategy is null, or
     * if connName is an empty String.
     * @throws ConfigurationException if unable to create the database search strategy from the given
     * namespace
     */
    public DbProjectManagerDAO(String connName, DBConnectionFactory connFactory, String searchBundleManagerNamespace,
        String searchBundleName, AuditManager auditor) throws ConfigurationException {
        super(connFactory, connName, null, searchBundleManagerNamespace, searchBundleName, auditor);

        Util.checkNull(searchBundleManagerNamespace, "searchBundleManagerNamespace");
        Util.checkNull(auditor, "auditor");

        this.filterFactory = new DbProjectManagerFilterFactory();
    }

    /**
     * <p>
     * Adds the specified project managers into the persistent store.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param managers An array of managers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null elements, or some
     * manager contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectManagers(ProjectManager[] managers, boolean audit) throws DataAccessException {
        this.checkProjectManagers(managers);

        Connection conn = null;

        try {
            conn = this.getConnection();

            List[] allParams = new List[managers.length];

            for (int i = 0; i < managers.length; i++) {
                List params = new ArrayList();
                params.add(new Long(managers[i].getProjectId()));
                params.add(new Long(managers[i].getUserId()));
                params.add(new Double(managers[i].getPayRate()));
                params.add(new Double(managers[i].getCost()));
                params.add(new Integer((managers[i].isActive()) ? 1 : 0));
                params.add(managers[i].getCreationDate());
                params.add(managers[i].getCreationUser());
                params.add(managers[i].getModificationDate());
                params.add(managers[i].getModificationUser());

                allParams[i] = params;
            }

            Util.executeBatchUpdate(conn, INSERT_PROJECT_MANAGER, allParams);

            // perform the audit if necessary
            if (audit) {
                audit(null, managers);
            }

            Util.resetBeanChangedStates(managers);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
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
     * This method audit the given project manager using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * The <code>oldProjectManager</code> is the project instance before update and the
     * <code>newProjectManager</code> is the project instance after update.
     * </p>
     *
     * <p>
     * For INSERT operation, <code>oldProjectManager</code> should be null while <code>newProjectManager</code>
     * should not be null.
     * For UPDATE operation, both <code>oldProjectManager</code> and <code>newProjectManager</code>
     * should not be null.
     * For DELETE operation, <code>oldProjectManager</code> should not be null while
     * <code>newProjectManager</code> should be null.
     * </p>
     *
     * @param newProjectManager the project manager instance after update
     * @param oldProjectManager the project manager instance before update
     *
     * @throws AuditManagerException if fails to audit the project.
     */
    private void audit(ProjectManager oldProjectManager, ProjectManager newProjectManager) throws AuditManagerException {
        ProjectManager manager = (newProjectManager == null) ? oldProjectManager : newProjectManager;
        AuditHeader header = new AuditHeader();

        header.setApplicationArea(ApplicationArea.TT_PROJECT);
        header.setTableName("project_manager");
        header.setEntityId(manager.getUserId());

        header.setCreationUser(manager.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        header.setProjectId(manager.getProjectId());

        List auditDetails = new ArrayList();
        if (newProjectManager != null && oldProjectManager == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(Util.createAuditDetail("project_id", null,
                String.valueOf(newProjectManager.getProjectId())));
            auditDetails.add(Util.createAuditDetail("user_account_id", null,
                String.valueOf(newProjectManager.getUserId())));
            auditDetails.add(Util.createAuditDetail("pay_rate", null,
                    String.valueOf(newProjectManager.getPayRate())));
            auditDetails.add(Util.createAuditDetail("cost", null,
                    String.valueOf(newProjectManager.getCost())));
            auditDetails.add(Util.createAuditDetail("active", null,
                    String.valueOf(newProjectManager.isActive())));
            auditDetails.add(Util.createAuditDetail("creation_date", null,
                newProjectManager.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", null, newProjectManager.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", null,
                newProjectManager.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", null, newProjectManager.getModificationUser()));
        } else if (newProjectManager == null && oldProjectManager != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldProjectManager.getProjectId()),
                null));
            auditDetails.add(Util.createAuditDetail("user_account_id", String.valueOf(oldProjectManager.getUserId()),
                null));
            auditDetails.add(Util.createAuditDetail("pay_rate", String.valueOf(oldProjectManager.getPayRate()), null));
            auditDetails.add(Util.createAuditDetail("cost", String.valueOf(oldProjectManager.getCost()), null));
            auditDetails.add(Util.createAuditDetail("active", String.valueOf(oldProjectManager.isActive()), null));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProjectManager.getCreationDate().toString(),
                null));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProjectManager.getCreationUser(), null));
            auditDetails.add(Util.createAuditDetail("modification_date",
                oldProjectManager.getModificationDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProjectManager.getModificationUser(), null));
        } else {
            // for update operation
            header.setActionType(AuditType.UPDATE);
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldProjectManager.getProjectId()),
                String.valueOf(newProjectManager.getProjectId())));
            auditDetails.add(Util.createAuditDetail("user_account_id", String.valueOf(oldProjectManager.getUserId()),
                String.valueOf(newProjectManager.getUserId())));
            auditDetails.add(Util.createAuditDetail("pay_rate", String.valueOf(oldProjectManager.getPayRate()),
                    String.valueOf(newProjectManager.getPayRate())));
            auditDetails.add(Util.createAuditDetail("cost", String.valueOf(oldProjectManager.getCost()),
                    String.valueOf(newProjectManager.getCost())));
            auditDetails.add(Util.createAuditDetail("active", String.valueOf(oldProjectManager.isActive()),
                    String.valueOf(newProjectManager.isActive())));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProjectManager.getCreationDate().toString(),
                newProjectManager.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProjectManager.getCreationUser(),
                newProjectManager.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date",
                oldProjectManager.getModificationDate().toString(), newProjectManager.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProjectManager.getModificationUser(),
                newProjectManager.getModificationUser()));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        this.getAuditManager().createAuditRecord(header);
    }

    /**
     * <p>
     * This method checks the given project managers.
     * </p>
     *
     * @param managers the given project managers to check
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null values, or some
     * project manager contains null property which is required in the persistence
     */
    private void checkProjectManagers(ProjectManager[] managers) {
        Util.checkNull(managers, "managers");
        if (managers.length == 0) {
            throw new IllegalArgumentException("The given managers is empty.");
        }

        for (int i = 0; i < managers.length; i++) {
            Util.checkNull(managers[i], "ProjectManager in managers");

            // null creation user is not allowed
            if (managers[i].getCreationUser() == null) {
                throw new IllegalArgumentException("Some project manager has null creation user.");
            }

            // null creation date is not allowed
            if (managers[i].getCreationDate() == null) {
                throw new IllegalArgumentException("Some project manager has null creation date.");
            }

            // null modification user is not allowed
            if (managers[i].getModificationUser() == null) {
                throw new IllegalArgumentException("Some project manager has null modification user.");
            }

            // null modification date is not allowed
            if (managers[i].getModificationDate() == null) {
                throw new IllegalArgumentException("Some project manager has null modification date.");
            }
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided array of ProjectManagers.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param managers An array of managers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if managers is null, empty or contains null elements, or some
     * manager contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a manager with the given project id and user id was not
     * found in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectManagers(ProjectManager[] managers, boolean audit) throws DataAccessException {
        // update project_manager set creation_date = ?, creation_user = ?, modification_date = ?
        // modification_user = ? where project_id = ? and user_account_id = ?
        checkProjectManagers(managers);

        Connection conn = null;

        try {
            conn = this.getConnection();

            List[] allParams = new List[managers.length];

            ProjectManager[] oldManagers = new ProjectManager[managers.length];

            for (int i = 0; i < managers.length; i++) {
                oldManagers[i] = getProjectManager(conn, managers[i].getProjectId(), managers[i].getUserId());

                List params = new ArrayList();
                params.add(new Double(managers[i].getPayRate()));
                params.add(new Double(managers[i].getCost()));
                params.add(new Integer((managers[i].isActive()) ? 1 : 0));
                params.add(managers[i].getCreationDate());
                params.add(managers[i].getCreationUser());
                params.add(managers[i].getModificationDate());
                params.add(managers[i].getModificationUser());
                params.add(new Long(managers[i].getProjectId()));
                params.add(new Long(managers[i].getUserId()));

                allParams[i] = params;
            }

            Util.executeBatchUpdate(conn, UPDATE_PROJECT_MANAGER, allParams);

            // perform the audit if necessary
            if (audit) {
                audit(oldManagers, managers);
            }

            Util.resetBeanChangedStates(managers);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
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
     * This method audit the given project managers using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * For detail information, you can refer to <{@link DbProjectManagerDAO#audit(ProjectManager, ProjectManager)}.
     * </p>
     *
     * @param oldManagers the project manager instances after update
     * @param newManagers the project manager instances before update
     *
     * @throws AuditManagerException if fails to audit the projects.
     */
    private void audit(ProjectManager[] oldManagers, ProjectManager[] newManagers) throws AuditManagerException {
        ProjectManager[] managers = (newManagers == null) ? oldManagers : newManagers;
        for (int i = 0; i < managers.length; i++) {
            audit(oldManagers == null ? null : oldManagers[i], newManagers == null ? null : newManagers[i]);
        }
    }

    /**
     * <p>
     * This method queries the database for the <code>ProjectManager</code> instance using the
     * given project and user id.
     * </p>
     *
     * <p>
     * Note, if the project manager cannot be found, <code>UnrecognizedEntityException</code>
     * will be thrown.
     * </p>
     *
     * @param conn the database connection to access database
     * @param projectId the project id
     * @param userId the user id
     * @return the project manager for the given project id and user id
     *
     * @throws SQLException if a database access error occurs
     * @throws UnrecognizedEntityException if the project manager cannot be found
     */
    private ProjectManager getProjectManager(Connection conn, long projectId, long userId) throws SQLException,
        UnrecognizedEntityException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_PROJECT_MANAGER);

            List params = new ArrayList();
            params.add(new Long(projectId));
            params.add(new Long(userId));

            Util.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return getProjectManager(rs);
            } else {
                throw new UnrecognizedEntityException(userId, "No project manager with project id [" + projectId
                    + "] and user id [" + userId + "] in the database.");
            }
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method creates a <code>ProjectManager</code> instance from the given <code>ResultSet</code>
     * instance.
     * </p>
     *
     * @param rs the <code>ResultSet</code> to get a <code>ProjectManager</code> instance
     * @return the <code>ProjectManager</code> from the <code>ResultSet</code>
     *
     * @throws SQLException if a database access error occurs
     */
    private ProjectManager getProjectManager(ResultSet rs) throws SQLException {
        ProjectManager manager = new ProjectManager();

        int index = 1;
        manager.setProjectId(rs.getLong(index++));
        long userId = rs.getLong(index++);
        manager.setId(userId);
        manager.setUserId(userId);

        manager.setPayRate(rs.getDouble(index++));
        manager.setCost(rs.getDouble(index++));
        manager.setActive(rs.getInt(index++) == 1);

        manager.setCreationDate(rs.getDate(index++));
        manager.setCreationUser(rs.getString(index++));
        manager.setModificationDate(rs.getDate(index++));
        manager.setModificationUser(rs.getString(index++));

        manager.setChanged(false);

        return manager;
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectManagers with the
     * specified project and user ids.
     * </p>
     *
     * @param projectIds An array of projectIds for which this operation should be performed.
     * @param userIds An array of userIds for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds or userIds is null, empty or contains values &lt;= 0,
     * or the lengths of the two id array are not the same
     * @throws UnrecognizedEntityException if a manager with the given project and user id was not found
     * in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void removeProjectManagers(long[] projectIds, long[] userIds, boolean audit) throws DataAccessException {
        // delete from project_manager where project_id = ? and user_account_id = ?
        Util.checkNull(projectIds, "projectIds");
        Util.checkNull(userIds, "userIds");

        if (projectIds.length != userIds.length) {
            throw new IllegalArgumentException(
                "The length of the given project id array is not equal to the length of the user id array.");
        }

        for (int i = 0; i < projectIds.length; i++) {
            Util.checkIdValue(projectIds[i], "project");
        }

        for (int i = 0; i < userIds.length; i++) {
            Util.checkIdValue(userIds[i], "user");
        }

        Connection conn = null;
        ProjectManager[] oldManagers = new ProjectManager[projectIds.length];

        try {
            conn = this.getConnection();

            List[] allParams = new List[projectIds.length];
            for (int i = 0; i < projectIds.length; i++) {
                oldManagers[i] = getProjectManager(conn, projectIds[i], userIds[i]);

                List params = new ArrayList();
                params.add(new Long(projectIds[i]));
                params.add(new Long(userIds[i]));

                allParams[i] = params;
            }

            // delete the project maangers
            Util.executeBatchUpdate(conn, DELETE_PROJECT_MANAGER, allParams);

            // perform the audit if necessary
            if (audit) {
                audit(oldManagers, null);
            }

        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
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
     * Searches the persistent store for any ProjectManagers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectManagerFilterFactory</code> returned by {@link ProjectManagerDAO#getProjectManagerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectManagerFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter containing the search criteria.
     * @return An array of managers satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] searchProjectManagers(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(filter);

            int size = result.getRecordCount();

            // get all the project managers from the search result
            ProjectManager[] managers = new ProjectManager[size];
            for (int i = 0; i < size; i++) {
                result.next();
                managers[i] = createProjectManager(result);
            }

            return managers;
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the database according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to read the search result.", e);
        }
    }

    /**
     * <p>
     * This method creates a <code>ProjectManager</code> instance from the given <code>CustomResultSet</code>
     * instance.
     * </p>
     *
     * @param rs the <code>CustomResultSet</code> to get a <code>ProjectManager</code> instance
     * @return the <code>ProjectManager</code> from the <code>CustomResultSet</code>
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance
     */
    private ProjectManager createProjectManager(CustomResultSet rs) throws InvalidCursorStateException {
        ProjectManager manager = new ProjectManager();

        int index = 1;
        manager.setProjectId(rs.getLong(index++));
        long userId = rs.getLong(index++);
        manager.setId(userId);
        manager.setUserId(userId);

        manager.setPayRate(rs.getDouble(index++));
        manager.setCost(rs.getDouble(index++));
        manager.setActive(rs.getInt(index++) == 1);

        manager.setCreationDate(rs.getDate(index++));
        manager.setCreationUser(rs.getString(index++));
        manager.setModificationDate(rs.getDate(index++));
        manager.setModificationUser(rs.getString(index++));

        manager.setChanged(false);

        return manager;
    }

    /**
     * <p>
     * Retrieves the <code>ProjectManagerFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for ProjectManagers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used.  The filters returned
     * by the given factory should be used with {@link ProjectManagerDAO#searchProjectManagers(Filter)}.
     * </p>
     *
     * @return The <code>ProjectManagerFilterFactory</code> used for building search filters.
     */
    public ProjectManagerFilterFactory getProjectManagerFilterFactory() {
        return filterFactory;
    }

    /**
     * <p>
     * Retrieves all the project managers that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of managers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectManager[] enumerateProjectManagers() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_ALL_PMS);
            rs = pstmt.executeQuery();

            List managers = new ArrayList();

            // get all the project managers from the database
            while (rs.next()) {
                managers.add(getProjectManager(rs));
            }

            return (ProjectManager[]) managers.toArray(new ProjectManager[managers.size()]);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }
}
