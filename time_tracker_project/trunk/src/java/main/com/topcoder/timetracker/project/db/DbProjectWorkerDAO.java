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
import com.topcoder.timetracker.project.ProjectWorkerDAO;
import com.topcoder.timetracker.project.ProjectWorkerFilterFactory;
import com.topcoder.timetracker.project.ProjectWorker;
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
 * This is default implementation of the <code>ProjectWorkerDAO</code>.
 * </p>
 *
 * <p>
 * It supports all the methods needed to manipulate and retrieve a <code>ProjectWorker</code> in the data store.
 * </p>
 *
 * <p>
 * Thread safety: This class should be thread safe as far as inner state is concerned;
 * Everything should be used in read-only manner.  For database access, the Time Tracker Application
 * will be making use of Container managed transactions, so JDBC transactions are NOT used.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public class DbProjectWorkerDAO extends BaseDAO implements ProjectWorkerDAO {

	/**
     * <p>
     * This is the application space that will be used and provided to the Time Tracker Auditor
     * if an audit is requested.
     * </p>
     */
    public static final String AUDIT_APPLICATION_AREA = "TT_PROJECT_WORKER";

    /**
     * <p>
     * Represents the sql script to insert a record to project_worker table.
     * </p>
     */
    private static final String INSERT_PROJECT_WORKER = "insert into project_worker(project_id, "
        + "user_account_id, start_date, end_date, pay_rate, cost, active, creation_date, creation_user, "
        + "modification_date, modification_user) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to update a record in project_worker table.
     * </p>
     */
    private static final String UPDATE_PROJECT_WORKER = "update project_worker set start_date = ?, "
        + "end_date = ?, pay_rate = ?, cost = ?, active = ?, creation_date = ?, creation_user = ?, modification_date = ?, "
        + "modification_user = ? where project_id = ? and user_account_id = ?";

    /**
     * <p>
     * Represents the sql script to select a record from project_worker table.
     * </p>
     */
    private static final String SELECT_PROJECT_WORKER = "select project_id, user_account_id, "
        + "start_date, end_date, pay_rate, cost, active, creation_date, "
        + "creation_user, modification_date, modification_user from project_worker where "
        + "project_id = ? and user_account_id = ?";

    /**
     * <p>
     * Represents the sql script to delete a record from project_worker table.
     * </p>
     */
    private static final String DELETE_PROJECT_WORKER = "delete from project_worker where "
        + "project_id = ? and user_account_id = ?";

    /**
     * <p>
     * Represents the sql script to select all the records in project_worker table.
     * </p>
     */
    private static final String SELECT_ALL_WORKERS = "select project_id, user_account_id, "
        + "start_date, end_date, pay_rate, cost, active, creation_date, creation_user, "
        + "modification_date, modification_user from project_worker";

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for
     * searching the data store for ProjectWorkersusing this implementation.
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
    private final DbProjectWorkerFilterFactory filterFactory;

    /**
     * <p>
     * Constructor that accepts the necessary parameters needed for the Database
     * to function properly.
     * </p>
     *
     *
     * @param connName The connection name to use (if null,then default connection is used)
     * @param connFactory The connection factory to use.
     * @param searchBundleManagerNamespace the configuration namespace used to initialize the search strategy.
     * @param searchBundleName the name of the search bundle
     * @param auditor The auditor that is going to be used.
     *
     * @throws IllegalArgumentException if connFactory, auditor or searchBundleManagerNamespace, searchBundleName is null,
     * or if connName, searchBundleManagerNamespace, searchBundleName is an empty String.
     * @throws ConfigurationException if unable to create the database search strategy from the given
     * namespace
     */
    public DbProjectWorkerDAO(String connName, DBConnectionFactory connFactory, String searchBundleManagerNamespace,
        String searchBundleName, AuditManager auditor) throws ConfigurationException {
        super(connFactory, connName, null, searchBundleManagerNamespace, searchBundleName, auditor);

        Util.checkNull(searchBundleManagerNamespace, "searchBundleManagerNamespace");
        Util.checkNull(auditor, "auditor");

        this.filterFactory = new DbProjectWorkerFilterFactory();
    }

    /**
     * <p>
     * Adds the specified ProjectWorkers into the persistent store.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void addProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException {
        this.checkProjectWorkers(workers);

        Connection conn = null;

        try {
            conn = this.getConnection();

            List[] allParams = new List[workers.length];

            for (int i = 0; i < workers.length; i++) {
                List params = new ArrayList();
                params.add(new Long(workers[i].getProjectId()));
                params.add(new Long(workers[i].getUserId()));
                params.add(workers[i].getStartDate());
                params.add(workers[i].getEndDate());
                params.add(new Double(workers[i].getPayRate()));
                params.add(new Double(workers[i].getCost()));
                params.add(new Integer((workers[i].isActive()) ? 1 : 0));
                params.add(workers[i].getCreationDate());
                params.add(workers[i].getCreationUser());
                params.add(workers[i].getModificationDate());
                params.add(workers[i].getModificationUser());

                allParams[i] = params;
            }

            // insert all the project workers
            Util.executeBatchUpdate(conn, INSERT_PROJECT_WORKER, allParams);

            // perform the audit if necessary
            if (audit) {
                audit(null, workers);
            }

            Util.resetBeanChangedStates(workers);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the project worker.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method audit the given project worker using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * The <code>oldProjectWorker</code> is the project instance before update and the
     * <code>newProjectWorker</code> is the project instance after update.
     * </p>
     *
     * <p>
     * For INSERT operation, <code>oldProjectWorker</code> should be null while <code>newProjectWorker</code>
     * should not be null.
     * For UPDATE operation, both <code>oldProjectWorker</code> and <code>newProjectWorker</code>
     * should not be null.
     * For DELETE operation, <code>oldProjectWorker</code> should not be null while
     * <code>newProjectWorker</code> should be null.
     * </p>
     *
     * @param newProjectWorker the project worker instance after update
     * @param oldProjectWorker the project worker instance before update
     *
     * @throws AuditManagerException if fails to audit the project.
     */
    private void audit(ProjectWorker oldProjectWorker, ProjectWorker newProjectWorker) throws AuditManagerException {
        ProjectWorker worker = (newProjectWorker == null) ? oldProjectWorker : newProjectWorker;
        AuditHeader header = new AuditHeader();

        header.setApplicationArea(ApplicationArea.TT_PROJECT);
        header.setTableName("project_worker");

        header.setCreationUser(worker.getCreationUser());
        header.setCreationDate(new Timestamp(System.currentTimeMillis()));
        header.setProjectId(worker.getProjectId());
        header.setEntityId(worker.getUserId());

        List auditDetails = new ArrayList();
        if (newProjectWorker != null && oldProjectWorker == null) {
            // for insertion operation
            header.setActionType(AuditType.INSERT);
            auditDetails.add(Util.createAuditDetail("project_id", null, String.valueOf(newProjectWorker.getProjectId())));
            auditDetails.add(Util.createAuditDetail("user_account_id", null,
                String.valueOf(newProjectWorker.getUserId())));
            auditDetails.add(Util.createAuditDetail("start_date", null, newProjectWorker.getStartDate().toString()));
            auditDetails.add(Util.createAuditDetail("end_date", null, newProjectWorker.getEndDate().toString()));
            auditDetails.add(Util.createAuditDetail("pay_rate", null, String.valueOf(newProjectWorker.getPayRate())));
            auditDetails.add(Util.createAuditDetail("cost", null, String.valueOf(newProjectWorker.getCost())));
            auditDetails.add(Util.createAuditDetail("active", null, String.valueOf(newProjectWorker.isActive())));
            auditDetails.add(Util.createAuditDetail("creation_date", null,
                newProjectWorker.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", null, newProjectWorker.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date", null,
                newProjectWorker.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", null, newProjectWorker.getModificationUser()));
        } else if (newProjectWorker == null && oldProjectWorker != null) {
            // for delete operation
            header.setActionType(AuditType.DELETE);
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldProjectWorker.getProjectId()), null));
            auditDetails.add(Util.createAuditDetail("user_account_id", String.valueOf(oldProjectWorker.getUserId()),
                null));
            auditDetails.add(Util.createAuditDetail("start_date", oldProjectWorker.getStartDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("end_date", oldProjectWorker.getEndDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("pay_rate", String.valueOf(oldProjectWorker.getPayRate()), null));
            auditDetails.add(Util.createAuditDetail("cost", String.valueOf(oldProjectWorker.getCost()), null));
            auditDetails.add(Util.createAuditDetail("active", String.valueOf(oldProjectWorker.isActive()), null));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProjectWorker.getCreationDate().toString(),
                null));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProjectWorker.getCreationUser(), null));
            auditDetails.add(Util.createAuditDetail("modification_date",
                oldProjectWorker.getModificationDate().toString(), null));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProjectWorker.getModificationUser(), null));
        } else {
            // for update operation
            header.setActionType(AuditType.UPDATE);
            auditDetails.add(Util.createAuditDetail("project_id", String.valueOf(oldProjectWorker.getProjectId()),
                String.valueOf(newProjectWorker.getProjectId())));
            auditDetails.add(Util.createAuditDetail("user_account_id", String.valueOf(oldProjectWorker.getUserId()),
                String.valueOf(newProjectWorker.getUserId())));
            auditDetails.add(Util.createAuditDetail("start_date", oldProjectWorker.getStartDate().toString(),
                newProjectWorker.getStartDate().toString()));
            auditDetails.add(Util.createAuditDetail("end_date", oldProjectWorker.getEndDate().toString(),
                newProjectWorker.getEndDate().toString()));
            auditDetails.add(Util.createAuditDetail("pay_rate", String.valueOf(oldProjectWorker.getPayRate()),
                String.valueOf(newProjectWorker.getPayRate())));
            auditDetails.add(Util.createAuditDetail("cost", String.valueOf(oldProjectWorker.getCost()),
                    String.valueOf(newProjectWorker.getCost())));
            auditDetails.add(Util.createAuditDetail("active", String.valueOf(oldProjectWorker.isActive()),
                    String.valueOf(newProjectWorker.isActive())));
            auditDetails.add(Util.createAuditDetail("creation_date", oldProjectWorker.getCreationDate().toString(),
                newProjectWorker.getCreationDate().toString()));
            auditDetails.add(Util.createAuditDetail("creation_user", oldProjectWorker.getCreationUser(),
                newProjectWorker.getCreationUser()));
            auditDetails.add(Util.createAuditDetail("modification_date",
                oldProjectWorker.getModificationDate().toString(), newProjectWorker.getModificationDate().toString()));
            auditDetails.add(Util.createAuditDetail("modification_user", oldProjectWorker.getModificationUser(),
                newProjectWorker.getModificationUser()));
        }

        header.setDetails((AuditDetail[]) auditDetails.toArray(new AuditDetail[auditDetails.size()]));
        this.getAuditManager().createAuditRecord(header);
    }

    /**
     * <p>
     * This method audit the given project workers using the Time Tracker Audit component.
     * </p>
     *
     * <p>
     * For detail information, you can refer to <{@link DbProjectWorkerDAO#audit(ProjectWorker, ProjectWorker)}.
     * </p>
     *
     * @param oldWorkers the project worker instances after update
     * @param newWorkers the project worker instances before update
     *
     * @throws AuditManagerException if fails to audit the projects.
     */
    private void audit(ProjectWorker[] oldWorkers, ProjectWorker[] newWorkers) throws AuditManagerException {
        ProjectWorker[] workers = (newWorkers == null) ? oldWorkers : newWorkers;
        for (int i = 0; i < workers.length; i++) {
            audit(oldWorkers == null ? null : oldWorkers[i], newWorkers == null ? null : newWorkers[i]);
        }
    }

    /**
     * <p>
     * This method checks the given project workers.
     * </p>
     *
     * @param workers the given project workers to check
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null values, or some
     * project worker contains null property which is required in the persistence
     */
    private void checkProjectWorkers(ProjectWorker[] workers) {
        Util.checkNull(workers, "workers");
        if (workers.length == 0) {
            throw new IllegalArgumentException("The given workers is empty.");
        }

        for (int i = 0; i < workers.length; i++) {
            Util.checkNull(workers[i], "ProjectWorker in workers");

            // null start date is not allowed
            if (workers[i].getStartDate() == null) {
                throw new IllegalArgumentException("Some project worker has null start date.");
            }

            // null end date is not allowed
            if (workers[i].getEndDate() == null) {
                throw new IllegalArgumentException("Some project worker has null end date.");
            }

            // the pay rate is not set
            if (workers[i].getPayRate() < 0) {
                throw new IllegalArgumentException("Some project worker has unknown pay rate.");
            }

            // the cost is not set
            if (workers[i].getCost() < 0) {
                throw new IllegalArgumentException("Some project worker has unknown cost.");
            }

            // null creation user is not allowed
            if (workers[i].getCreationUser() == null) {
                throw new IllegalArgumentException("Some project worker has null creation user.");
            }

            // null creation date is not allowed
            if (workers[i].getCreationDate() == null) {
                throw new IllegalArgumentException("Some project worker has null creation date.");
            }

            // null modification user is not allowed
            if (workers[i].getModificationUser() == null) {
                throw new IllegalArgumentException("Some project worker has null modification user.");
            }

            // null modification date is not allowed
            if (workers[i].getModificationDate() == null) {
                throw new IllegalArgumentException("Some project worker has null modification date.");
            }
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided array of ProjectWorkers.
     * </p>
     *
     * <p>
     * There is also the option to perform an audit.
     * </p>
     *
     * @param workers An array of workers for which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if workers is null, empty or contains null elements, or some
     * worker contains null property which is required in the persistence
     * @throws UnrecognizedEntityException if a worker with the given project id and user id was not found
     * in the data store.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public void updateProjectWorkers(ProjectWorker[] workers, boolean audit) throws DataAccessException {
        checkProjectWorkers(workers);

        Connection conn = null;

        try {
            conn = this.getConnection();

            List[] allParams = new List[workers.length];

            ProjectWorker[] oldworkers = new ProjectWorker[workers.length];

            for (int i = 0; i < workers.length; i++) {
                oldworkers[i] = getProjectWorker(conn, workers[i].getProjectId(), workers[i].getUserId());

                List params = new ArrayList();
                params.add(workers[i].getStartDate());
                params.add(workers[i].getEndDate());
                params.add(new Double(workers[i].getPayRate()));
                params.add(new Double(workers[i].getCost()));
                params.add(new Integer((workers[i].isActive()) ? 1 : 0));
                params.add(workers[i].getCreationDate());
                params.add(workers[i].getCreationUser());
                params.add(workers[i].getModificationDate());
                params.add(workers[i].getModificationUser());
                params.add(new Long(workers[i].getProjectId()));
                params.add(new Long(workers[i].getUserId()));

                allParams[i] = params;
            }

            // update all the project workers
            Util.executeBatchUpdate(conn, UPDATE_PROJECT_WORKER, allParams);

            // perform the audit if necessary
            if (audit) {
                audit(oldworkers, workers);
            }

            Util.resetBeanChangedStates(workers);
        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the project worker.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method queries the database for the <code>ProjectWorker</code> instance using the
     * given project and user id.
     * </p>
     *
     * <p>
     * Note, if the project worker cannot be found, <code>UnrecognizedEntityException</code>
     * will be thrown.
     * </p>
     *
     * @param conn the database connection to access database
     * @param projectId the project id
     * @param userId the user id
     * @return the project worker for the given project id and user id
     *
     * @throws SQLException if a database access error occurs
     * @throws UnrecognizedEntityException if the project worker cannot be found
     */
    private ProjectWorker getProjectWorker(Connection conn, long projectId, long userId) throws SQLException,
        UnrecognizedEntityException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECT_PROJECT_WORKER);

            List params = new ArrayList();
            params.add(new Long(projectId));
            params.add(new Long(userId));

            Util.fillPreparedStatement(pstmt, params);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                return getProjectWorker(rs);
            } else {
                throw new UnrecognizedEntityException(userId, "No project worker with project id [" + projectId
                    + "] and user id [" + userId + "] in the database.");
            }
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
        }
    }

    /**
     * <p>
     * This method creates a <code>ProjectWorker</code> instance from the given <code>ResultSet</code>
     * instance.
     * </p>
     *
     * @param rs the <code>ResultSet</code> to get a <code>ProjectManager</code> instance
     * @return the <code>ProjectWorker</code> from the <code>ResultSet</code>
     *
     * @throws SQLException if a database access error occurs
     */
    private ProjectWorker getProjectWorker(ResultSet rs) throws SQLException {
        ProjectWorker worker = new ProjectWorker();

        int index = 1;
        worker.setProjectId(rs.getLong(index++));
        long userId = rs.getLong(index++);
        worker.setId(userId);
        worker.setUserId(userId);

        worker.setStartDate(rs.getDate(index++));
        worker.setEndDate(rs.getDate(index++));
        worker.setPayRate(rs.getDouble(index++));
        worker.setCost(rs.getDouble(index++));
        worker.setActive(rs.getInt(index++) == 1);
        worker.setCreationDate(rs.getDate(index++));
        worker.setCreationUser(rs.getString(index++));
        worker.setModificationDate(rs.getDate(index++));
        worker.setModificationUser(rs.getString(index++));

        worker.setChanged(false);

        return worker;
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the ProjectWorkers with the specified
     * <code>prjectIds</code> and <code>userIds</code>.
     * </p>
     *
     * @param projectIds An array of project id for which this operation should be performed.
     * @param userIds An array of user id which this operation should be performed.
     * @param audit Indicates whether an audit should be performed.
     *
     * @throws IllegalArgumentException if projectIds or userIds is null, empty or contains values &lt;= 0,
     * or the lengths of the two id array are not the same
     * @throws DataAccessException if a problem occurs while accessing the data store.
     * @throws UnrecognizedEntityException if a worker with the given project id and user id was not found in the
     * data store.
     */
    public void removeProjectWorkers(long[] projectIds, long[] userIds, boolean audit) throws DataAccessException {
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
        ProjectWorker[] oldWorkers = new ProjectWorker[projectIds.length];

        try {
            conn = this.getConnection();

            List[] allParams = new List[projectIds.length];
            for (int i = 0; i < projectIds.length; i++) {
                oldWorkers[i] = getProjectWorker(conn, projectIds[i], userIds[i]);

                List params = new ArrayList();
                params.add(new Long(projectIds[i]));
                params.add(new Long(userIds[i]));

                allParams[i] = params;
            }

            // deletes the project workers
            Util.executeBatchUpdate(conn, DELETE_PROJECT_WORKER, allParams);

            // perform the audit if necessary
            if (audit) {
                audit(oldWorkers, null);
            }

        } catch (DBConnectionException e) {
            throw new DataAccessException("Failed to get the database connection.", e);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } catch (AuditManagerException e) {
            throw new DataAccessException("Failed to audit the project worker.", e);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Retrieves the <code>ProjectWorkerFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for ProjectWorkers.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned by
     * the given factory should be used with {@link ProjectWorkerDAO#searchProjectWorkers(Filter)}.
     * </p>
     *
     * @return The <code>ProjectWorkerFilterFactory</code> used for building search filters.
     */
    public ProjectWorkerFilterFactory getProjectWorkerFilterFactory() {
        return filterFactory;
    }

    /**
     * <p>
     * Searches the persistent store for any ProjectWorkers that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>ProjectWorkerFilterFactory</code> returned by {@link ProjectWorkerDAO#getProjectWorkerFilterFactory()},
     * or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and <code>NotFilter</code>
     * from Search Builder component) that combines the filters created using <code>ProjectWorkerFilterFactory</code>.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @param filter The filter containing the search criteria.
     * @return An array of workers  satisfying the criteria.
     *
     * @throws IllegalArgumentException if filter is null.
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectWorker[] searchProjectWorkers(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(filter);

            int size = result.getRecordCount();

            // get all the project workers from the search result
            ProjectWorker[] workers = new ProjectWorker[size];
            for (int i = 0; i < size; i++) {
                result.next();
                workers[i] = createProjectWorker(result);
            }

            return workers;
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the database according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to read the search result.", e);
        }
    }

    /**
     * <p>
     * Retrieves all the project workers that are currently in the persistent store.
     * </p>
     *
     * <p>
     * An empty array is returned if no entities are found.
     * </p>
     *
     * @return An array of workers currently in the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the data store.
     */
    public ProjectWorker[] enumerateProjectWorkers() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_ALL_WORKERS);
            rs = pstmt.executeQuery();

            List workers = new ArrayList();

            while (rs.next()) {
                workers.add(getProjectWorker(rs));
            }

            return (ProjectWorker[]) workers.toArray(new ProjectWorker[workers.size()]);
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

    /**
     * <p>
     * This method creates a <code>ProjectWorker</code> instance from the given <code>CustomResultSet</code>
     * instance.
     * </p>
     *
     * @param rs the <code>CustomResultSet</code> to get a <code>ProjectWorker</code> instance
     * @return the <code>ProjectWorker</code> from the <code>CustomResultSet</code>
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance
     */
    private ProjectWorker createProjectWorker(CustomResultSet rs) throws InvalidCursorStateException {
        ProjectWorker worker = new ProjectWorker();

        int index = 1;
        worker.setProjectId(rs.getLong(index++));
        long userId = rs.getLong(index++);
        worker.setId(userId);
        worker.setUserId(userId);
        worker.setStartDate(rs.getDate(index++));
        worker.setEndDate(rs.getDate(index++));
        worker.setPayRate(rs.getDouble(index++));
        worker.setCost(rs.getDouble(index++));
        worker.setActive(rs.getInt(index++) == 1);
        worker.setCreationDate(rs.getDate(index++));
        worker.setCreationUser(rs.getString(index++));
        worker.setModificationDate(rs.getDate(index++));
        worker.setModificationUser(rs.getString(index++));

        worker.setChanged(false);

        return worker;
    }
}
