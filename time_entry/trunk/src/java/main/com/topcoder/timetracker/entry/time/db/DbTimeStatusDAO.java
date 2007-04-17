/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.entry.time.TimeStatusDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.TimeStatusFilterFactory;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TimeStatus;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an database implementation of the <code>TimeStatusDAO</code> interface.
 * </p>
 *
 * <p>
 * It is responsible for handling the retrieval, storage, and searching of TimeStatus data from
 * a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever an id is required.
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
public class DbTimeStatusDAO extends BaseDAO implements TimeStatusDAO {
    /**
     * <p>
     * Represents the column names mapping used for <code>DbTimeStatusFilterFactory</code>.
     * </p>
     *
     * <p>
     * It is created when declared and initialized in a static initialization block.
     * </p>
     *
     * <p>
     * It will not changed after initialization, including the reference and content.
     * </p>
     */
    private static final Map COLUMNNAMES_MAP = new HashMap();

    /**
     * <p>
     * Represents the sql script to insert a record to <b>time_status</b> table.
     * </p>
     */
    private static final String INSERT_TIME_STATUS = "insert into time_status(time_status_id, description, "
        + "creation_date, creation_user, modification_date, modification_user) values (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to update a record in <b>time_status</b> table.
     * </p>
     */
    private static final String UPDATE_TIME_STATUS = "update time_status set description = ?, "
        + "modification_date = ?, modification_user = ? where time_status_id = ?";

    /**
     * <p>
     * Represents the sql script to delete a record in <b>time_status</b> table.
     * </p>
     */
    private static final String DELETE_TIME_STATUS = "delete from time_status where time_status_id = ?";

    /**
     * <p>
     * Represents the sql script to select some records in <b>time_status</b> table.
     * </p>
     */
    private static final String SELECT_TIME_STATUSES = "select time_status_id, description, creation_date, "
        + "creation_user, modification_date, modification_user from time_status";

    /**
     * <p>
     * Represents the context string for searching. It is used in the
     * {@link DbTimeStatusDAO#searchTimeStatuses(Filter)} to search time statuses.
     * </p>
     *
     * <p>
     * It is created when declared and never changed afterwards.
     * </p>
     */
    private static final String CONTEXT = SELECT_TIME_STATUSES + " where";

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for
     * searching the data store for Time Statuses using this implementation.
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
    private DbTimeStatusFilterFactory timeStatusFilterFactory;

    /**
     * <p>
     * This is a static block and is used to initialize the <code>COLUMNNAMES_MAP</code> variable.
     * </p>
     */
    static {
        COLUMNNAMES_MAP.put(DbTimeStatusFilterFactory.CREATION_DATE_COLUMN_NAME, "time_status.creation_date");
        COLUMNNAMES_MAP.put(DbTimeStatusFilterFactory.MODIFICATION_DATE_COLUMN_NAME, "time_status.modification_date");
        COLUMNNAMES_MAP.put(DbTimeStatusFilterFactory.CREATION_USER_COLUMN_NAME, "time_status.creation_user");
        COLUMNNAMES_MAP.put(DbTimeStatusFilterFactory.MODIFICATION_USER_COLUMN_NAME, "time_status.modification_user");
        COLUMNNAMES_MAP.put(DbTimeStatusFilterFactory.DESCRIPTION_COLUMN_NAME, "time_status.description");
    }

    /**
     * <p>
     * Constructor that accepts the necessary parameters to construct a <code>DbTimeStatusDAO</code>.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use.
     * @param searchStrategyNamespace The configuration namespace of the database search strategy that will be used.
     * @param auditor The auditManager used to perform the edits.
     *
     * @throws IllegalArgumentException if connFactory or auditor is null, or idGen, searchStrategyNamespace is
     * null or empty string, or connName is empty string when it is not null
     * @throws ConfigurationException if unable to create the search strategy from the given namespace or create
     * id generator using the id generator name
     */
    public DbTimeStatusDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchStrategyNamespace, AuditManager auditor) throws ConfigurationException {
        super(connFactory, connName, idGen, searchStrategyNamespace, auditor);

        Util.checkNull(idGen, "idGen");
        Util.checkNull(searchStrategyNamespace, "searchStrategyNamespace");
        Util.checkNull(auditor, "auditor");

        this.timeStatusFilterFactory = new DbTimeStatusFilterFactory(COLUMNNAMES_MAP);
    }

    /**
     * <p>
     * Defines a set of time statuses to be recognized within the persistent store managed by this DAO.
     * </p>
     *
     * <p>
     * A unique time status id will automatically be generated and assigned to the time statuses.
     * </p>
     *
     * @param timeStatuses An array of time statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatuses is null, empty or contains null values or
     * same reference or has ids != -1.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTimeStatuses(TimeStatus[] timeStatuses) throws BatchOperationException, DataAccessException {
        checkTimeStatuses(timeStatuses, true);
        Util.checkSameTimeTrackerBeans(timeStatuses, "timeStatuses");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeStatuses.length];
            List params = new ArrayList();

            // creates the insert parameters for insert operation
            for (int i = 0; i < timeStatuses.length; i++) {
                try {
                    timeStatuses[i].setId(getNextId());
                    params.add(createInsertParam(timeStatuses[i]));
                } catch (DataAccessException e) {
                    causes[i] = e;
                }
            }
            Util.checkStepResult(causes);

            // perform the insert in the database
            try {
                Util.executeBatchUpdate(conn, INSERT_TIME_STATUS, (List[]) params.toArray(new List[params.size()]),
                    new long[params.size()]);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }

            Util.finishBatchOperation(causes);
            Util.resetBeanChangedStates(timeStatuses, causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to insert a <code>TimeStatus</code>.
     * </p>
     *
     * @param timeStatus the <code>TimeStatus</code> to insert to database
     * @return the parameters needed to insert a <code>TimeStatus</code>.
     */
    private List createInsertParam(TimeStatus timeStatus) {
        List params = new ArrayList();
        params.add(new Long(timeStatus.getId()));

        // description can be null in the database
        if (timeStatus.getDescription() == null) {
            params.add(new SqlType(Types.VARCHAR));
        } else {
            params.add(timeStatus.getDescription());
        }

        params.add(timeStatus.getCreationDate());
        params.add(timeStatus.getCreationUser());
        params.add(timeStatus.getModificationDate());
        params.add(timeStatus.getModificationUser());

        return params;
    }

    /**
     * <p>
     * This method checks the given time status instances.
     * </p>
     *
     * @param timeStatuses the time status instances to check
     * @param create the flag to identify whether it is a creation or modification
     *
     * @throws IllegalArgumentException if timeStatuses is null, empty, or has ids != -1 in a creation
     * or ids &lt; 0 in a modification.
     * @throws DataAccessException if some beans contain null property which is required in the persistence
     */
    private void checkTimeStatuses(TimeStatus[] timeStatuses, boolean create) throws DataAccessException {
        Util.checkNull(timeStatuses, "timeStatuses");

        if (timeStatuses.length == 0) {
            throw new IllegalArgumentException("The given time statuses array is empty.");
        }

        for (int i = 0; i < timeStatuses.length; i++) {
            Util.checkNull(timeStatuses[i], "time status in timeStatuses");

            if (create) {
                if (timeStatuses[i].getId() != -1) {
                    throw new IllegalArgumentException(
                        "The time status id is set, which is not allowed for creation operation.");
                }

                // null creation user is not allowed
                if (timeStatuses[i].getCreationUser() == null) {
                    throw new DataAccessException("Some time statuses have null creation user.");
                }

                // null creation date is not allowed
                if (timeStatuses[i].getCreationDate() == null) {
                    throw new DataAccessException("Some time statuses have null creation date.");
                }
            } else {
                if (timeStatuses[i].getId() < 0) {
                    throw new IllegalArgumentException(
                        "The time status id is negative, which is not allowed for update operation.");
                }
            }

            // null modification user is not allowed
            if (timeStatuses[i].getModificationUser() == null) {
                throw new DataAccessException("Some time statuses have null modification user.");
            }

            // null modification date is not allowed
            if (timeStatuses[i].getModificationDate() == null) {
                throw new DataAccessException("Some time statuses have null modification date.");
            }
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TimeStatuses parameter.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeStatus with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeStatuses An array of time statuses for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatuses is null, empty or contains null values or
     * same reference or has ids &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTimeStatuses(TimeStatus[] timeStatuses) throws BatchOperationException, DataAccessException {
        checkTimeStatuses(timeStatuses, false);
        Util.checkSameTimeTrackerBeans(timeStatuses, "timeStatuses");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeStatuses.length];

            long[] timeStatusIds = new long[timeStatuses.length];
            for (int i = 0; i < timeStatuses.length; i++) {
                timeStatusIds[i] = timeStatuses[i].getId();
            }

            // verify the time statuses are present in the database
            try {
                getTimeStatuses(timeStatusIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            Util.skipNotChangedBeans(timeStatuses, causes);

            // create the parameters for the update operation
            List allParams = new ArrayList();
            for (int i = 0; i < timeStatuses.length; i++) {
                if (causes[i] == null) {
                    allParams.add(createUpdateParam(timeStatuses[i]));
                }
            }

            // perform the update in the database
            try {
                Util.executeBatchUpdate(conn, UPDATE_TIME_STATUS,
                    (List[]) allParams.toArray(new List[allParams.size()]), timeStatusIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }

            Util.resetNotChangedExcetionCauses(causes);
            Util.finishBatchOperation(causes);
            Util.resetBeanChangedStates(timeStatuses, causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method creates the parameters needed to update a <code>TimeStatus</code>.
     * </p>
     *
     * @param timeStatus the <code>TimeStatus</code> to update to database
     * @return the parameters needed to update a <code>TimeStatus</code>
     */
    private List createUpdateParam(TimeStatus timeStatus) {
        List params = new ArrayList();

        // description can be null in the database
        if (timeStatus.getDescription() == null) {
            params.add(new SqlType(Types.VARCHAR));
        } else {
            params.add(timeStatus.getDescription());
        }

        params.add(timeStatus.getModificationDate());
        params.add(timeStatus.getModificationUser());
        params.add(new Long(timeStatus.getId()));

        return params;
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the time status
     * with the specified ids.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeStatus with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeStatusIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt; 0 or equal
     * time entry ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTimeStatuses(long[] timeStatusIds) throws DataAccessException {
        checkTimeStatusIds(timeStatusIds);
        Util.checkEqualIds(timeStatusIds, "timeStatusIds");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeStatusIds.length];

            // verify the time status ids are present in the database
            try {
                getTimeStatuses(timeStatusIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            // create the delete parameters
            List allParams = new ArrayList();
            for (int i = 0; i < timeStatusIds.length; i++) {
                if (causes[i] == null) {
                    List params = new ArrayList();
                    params.add(new Long(timeStatusIds[i]));

                    allParams.add(params);
                }
            }

            // perform the delete in the database
            try {
                Util.executeBatchUpdate(conn, DELETE_TIME_STATUS,
                    (List[]) allParams.toArray(new List[allParams.size()]), timeStatusIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }

            Util.finishBatchOperation(causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method checks the given time status id array.
     * </p>
     *
     * @param timeStatusIds the status id array to check
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt;= 0.
     */
    private void checkTimeStatusIds(long[] timeStatusIds) {
        Util.checkNull(timeStatusIds, "timeTrackerIds");

        // length check
        if (timeStatusIds.length == 0) {
            throw new IllegalArgumentException("The given time status ids array is empty.");
        }

        for (int i = 0; i < timeStatusIds.length; i++) {
            Util.checkIdValue(timeStatusIds[i], "time status");
        }
    }

    /**
     * <p>
     * Retrieves an array of TimeStatus objects that reflects the data in the persistent
     * store on the TimeStatus with the specified Ids.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TimeEntry with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param timeStatusIds An array of timeStatusIds for which time status should be retrieved.
     * @return The TimeStatuses corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if timeStatusIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] getTimeStatuses(long[] timeStatusIds) throws BatchOperationException, DataAccessException {
        checkTimeStatusIds(timeStatusIds);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[timeStatusIds.length];

            // select the time statuses using the IN clause
            pstmt = conn.prepareStatement(SELECT_TIME_STATUSES
                + Util.buildInClause("time_status.time_status_id", timeStatusIds));

            rs = pstmt.executeQuery();

            // this is a mapping from the time status id to its time status instance
            Map result = new HashMap();

            while (rs.next()) {
                TimeStatus timeStatus = getTimeStatus(rs);
                result.put(new Long(timeStatus.getId()), timeStatus);
            }

            // aggregate all the time statuses according to the given ids
            TimeStatus[] timeStatuses = new TimeStatus[timeStatusIds.length];
            for (int i = 0; i < timeStatusIds.length; i++) {
                timeStatuses[i] = (TimeStatus) result.get(new Long(timeStatusIds[i]));
                if (timeStatuses[i] == null) {
                    causes[i] = new UnrecognizedEntityException(timeStatusIds[i], "The TimeStatus [" + timeStatusIds[i]
                        + "] is unrecognized.");
                }
            }

            Util.finishBatchOperation(causes);

            return timeStatuses;
        } catch (SQLException e) {
            throw new DataAccessException("Database access error occurs.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method creates a <code>TimeStatus</code> instance from the given <code>ResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>TimeStatus</code> instance will have <tt>unchanged</tt> state.
     * </p>
     *
     * @param rs the <code>ResultSet</code> instance
     * @return the <code>TimeStatus</code> created from the given <code>ResultSet</code> instance
     *
     * @throws SQLException if a database access error occurs
     */
    private TimeStatus getTimeStatus(ResultSet rs) throws SQLException {
        TimeStatus timeStatus = new TimeStatus();

        int index = 1;
        timeStatus.setId(rs.getLong(index++));

        // description can be null in the database
        String description = rs.getString(index++);
        if (description != null) {
            timeStatus.setDescription(description);
        }

        timeStatus.setCreationDate(rs.getDate(index++));
        timeStatus.setCreationUser(rs.getString(index++));
        timeStatus.setModificationDate(rs.getDate(index++));
        timeStatus.setModificationUser(rs.getString(index++));

        timeStatus.setChanged(false);

        return timeStatus;
    }

    /**
     * <p>
     * Searches the persistent store for any time statuses that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>TimeStatusFilterFactory</code> returned by <code>getTimeStatusEntryFilterFactory</code> of this
     * instance, or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created using
     * <code>TimeStatusFilterFactory</code>.
     * </p>
     *
     * @param criteria The filter used to search for statuses.
     * @return The statuses satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] searchTimeStatuses(Filter criteria) throws DataAccessException {
        Util.checkNull(criteria, "criteria");

        try {
            CustomResultSet result = (CustomResultSet) getSearchStrategy().search(CONTEXT, criteria,
                Collections.EMPTY_LIST, Collections.EMPTY_MAP);

            int size = result.getRecordCount();

            // get all the time statuses from the search result
            TimeStatus[] timeStatuses = new TimeStatus[size];
            for (int i = 0; i < size; i++) {
                result.next();
                timeStatuses[i] = getTimeStatus(result);
            }

            return timeStatuses;
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the database according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to retrieve the search result.", e);
        }
    }

    /**
     * <p>
     * This method creates a <code>TimeStatus</code> instance from the given <code>CustomResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>TimeStatus</code> instance will have <tt>unchanged</tt> state.
     * </p>
     *
     * @param result the <code>CustomResultSet</code> instance
     * @return the <code>TimeStatus</code> created from the given <code>CustomResultSet</code> instance
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance
     */
    private TimeStatus getTimeStatus(CustomResultSet result) throws InvalidCursorStateException {
        TimeStatus timeStatus = new TimeStatus();

        int index = 1;
        timeStatus.setId(result.getLong(index++));
        String description = result.getString(index++);

        // description can be null in the database
        if (description != null) {
            timeStatus.setDescription(description);
        }

        timeStatus.setCreationDate(result.getDate(index++));
        timeStatus.setCreationUser(result.getString(index++));
        timeStatus.setModificationDate(result.getDate(index++));
        timeStatus.setModificationUser(result.getString(index++));

        timeStatus.setChanged(false);

        return timeStatus;
    }

    /**
     * <p>
     * Retrieves the <code>TimeStatusFilterFactory</code> that is capable of creating SearchFilters
     * to use when searching for TimeEntries.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned
     * by the given factory should be used with this instance's <code>searchTimeStatuses</code> method.
     * </p>
     *
     * @return the <code>TimeStatusFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for time statuses.
     */
    public TimeStatusFilterFactory getTimeStatusFilterFactory() {
        return timeStatusFilterFactory;
    }

    /**
     * <p>
     * Retrieves all the TimeStatuses that are currently in the persistent store.
     * </p>
     *
     * @return An array of time entries retrieved from the persistent store.
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TimeStatus[] getAllTimeStatuses() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_TIME_STATUSES);
            rs = pstmt.executeQuery();

            List timeStatuses = new ArrayList();

            // get all the time statuses in the database
            while (rs.next()) {
                timeStatuses.add(getTimeStatus(rs));
            }

            return (TimeStatus[]) timeStatuses.toArray(new TimeStatus[timeStatuses.size()]);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }
}
