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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topcoder.timetracker.entry.time.TaskTypeDAO;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.entry.time.TaskTypeFilterFactory;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This is an database implementation of the <code>TaskTypeDAO</code> interface.
 * </p>
 *
 * <p>
 * It is responsible for handling the retrieval, storage, and searching of <code>TaskType</code>
 * data from a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever
 * an id is required.
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
public class DbTaskTypeDAO extends BaseDAO implements TaskTypeDAO {
    /**
     * <p>
     * Represents the sql script to insert a record to <b>task_type</b> table.
     * </p>
     */
    private static final String INSERT_TASK_TYPE = "insert into task_type(task_type_id, description, "
        + "active, creation_date, creation_user, modification_date, modification_user) values (?, ?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to insert a record to <b>comp_task_type</b> table.
     * </p>
     */
    private static final String INSERT_COMP_TASK_TYPE = "insert into comp_task_type(company_id, task_type_id, "
        + "creation_date, creation_user, modification_date, modification_user) values (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * Represents the sql script to update a record in <b>comp_task_type</b> table.
     * </p>
     */
    private static final String UPDATE_TASK_TYPE = "update task_type set description = ?, active = ?, "
        + "modification_date = ?, modification_user = ? where task_type_id = ?";

    /**
     * <p>
     * Represents the sql script to delete the records related to a task type in <b>comp_task_type</b> table.
     * </p>
     */
    private static final String DELETE_COMP_TASK_TYPE = "delete from comp_task_type where task_type_id = ?";

    /**
     * <p>
     * Represents the sql script to delete a record in <b>task_type</b> table.
     * </p>
     */
    private static final String DELETE_TASK_TYPE = "delete from task_type where task_type_id = ?";

    /**
     * <p>
     * Represents the sql script to select some records in <b>task_type</b> table.
     * </p>
     *
     * <p>
     * Note, the <b>comp_task_type</b> table is joined.
     * </p>
     */
    private static final String SELECT_TASK_TYPES = "select task_type.task_type_id, comp_task_type.company_id, "
        + "task_type.description, task_type.active, task_type.creation_date, task_type.creation_user, "
        + "task_type.modification_date, task_type.modification_user from task_type, comp_task_type "
        + "where task_type.task_type_id = comp_task_type.task_type_id";

    /**
     * Represents the sql script to count time entries that reference a given task type.
     */
    private static final String COUNT_REFERENCES = "SELECT COUNT(time_entry_id) FROM time_entry WHERE task_type_id = ?";

    /**
     * <p>
     * This is the filter factory that is used to create Search Filters for
     * searching the data store for Task Types using this implementation.
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
    private final DbTaskTypeFilterFactory taskTypeFilterFactory;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to construct a <code>DbTaskTypeDAO</code>.
     * </p>
     *
     * @param connFactory The connection factory to use.
     * @param connName The connection name to use (or null if the default connection should be used).
     * @param idGen The name of the id generator to use.
     * @param searchStrategyNamespace The configuration namespace of the database search strategy that will be used.
     * @param auditor The auditManager used to perform the edits.
     *
     * @throws IllegalArgumentException if connFactory or auditor is null, or idGen, searchBundleManagerNamespace,
     * searchBundleName is null or empty string, or connName is empty string when it is not null
     * @throws ConfigurationException if unable to create the search strategy from the given namespace or create
     * id generator using the id generator name
     */
    public DbTaskTypeDAO(DBConnectionFactory connFactory, String connName, String idGen,
        String searchBundleManagerNamespace, String searchBundleName, AuditManager auditor)
        throws ConfigurationException {
        super(connFactory, connName, idGen, searchBundleManagerNamespace, searchBundleName, auditor);

        Util.checkNull(idGen, "idGen");
        Util.checkNull(searchBundleManagerNamespace, "searchBundleManagerNamespace");
        Util.checkNull(auditor, "auditor");

        this.taskTypeFilterFactory = new DbTaskTypeFilterFactory();
    }

    /**
     * <p>
     * Defines a set of TaskTypes to be recognized within the persistent store managed by this DAO.
     * </p>
     *
     * <p>
     * A unique task type id will automatically be generated and assigned to the task types.
     * </p>
     *
     * @param taskTypes An array of taskTypes for which the operation should be performed.
     *
     * @throws IllegalArgumentException if taskTypes is null, empty or contains null values or
     * same reference or has ids != -1.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTaskTypes(TaskType[] taskTypes) throws BatchOperationException, DataAccessException {
        checkTaskTypes(taskTypes, true);
        Util.checkSameTimeTrackerBeans(taskTypes, "taskTypes");

        Connection conn = null;
        try {
            conn = getConnection();

            // the inner causes for the batch operation
            Throwable[] causes = new Throwable[taskTypes.length];

            List allParams = new ArrayList();

            for (int i = 0; i < taskTypes.length; i++) {
                // get new id from id generator
                taskTypes[i].setId(getNextId());

                List params = new ArrayList();
                params.add(new Long(taskTypes[i].getId()));

                // description can be null in the database
                if (taskTypes[i].getDescription() == null) {
                    params.add(new SqlType(Types.VARCHAR));
                } else {
                    params.add(taskTypes[i].getDescription());
                }

                params.add(new Long(taskTypes[i].isActive() ? 1 : 0));
                params.add(taskTypes[i].getCreationDate());
                params.add(taskTypes[i].getCreationUser());
                params.add(taskTypes[i].getModificationDate());
                params.add(taskTypes[i].getModificationUser());

                allParams.add(params);
            }

            // inserts the records
            try {
                Util.executeBatchUpdate(conn, INSERT_TASK_TYPE, (List[]) allParams.toArray(new List[allParams.size()]),
                    new long[allParams.size()]);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            // inserts the relations in comp_task_type
            addCompTaskTypes(conn, taskTypes, causes);

            Util.finishBatchOperation(causes);

            Util.resetBeanChangedStates(taskTypes, causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * This method inserts the records in <b>comp_task_type</b> according to the given task type instances.
     * </p>
     *
     * <p>
     * The insert operation will be executed in a batch operation.
     * </p>
     *
     * @param conn the database connection to access database
     * @param taskTypes the task type instances to add their associated records in <b>comp_task_type</b> table
     * @param causes the inner causes of the batch operation
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private void addCompTaskTypes(Connection conn, TaskType[] taskTypes, Throwable[] causes) throws DataAccessException {
        TaskType[] remainTaskTypes = getNextStepTaskTypes(taskTypes, causes);

        List allParams = new ArrayList();
        for (int i = 0; i < remainTaskTypes.length; i++) {
            List params = new ArrayList();
            params.add(new Long(remainTaskTypes[i].getCompanyId()));
            params.add(new Long(remainTaskTypes[i].getId()));
            params.add(taskTypes[i].getCreationDate());
            params.add(taskTypes[i].getCreationUser());
            params.add(taskTypes[i].getModificationDate());
            params.add(taskTypes[i].getModificationUser());

            allParams.add(params);
        }

        // insert the records
        try {
            Util.executeBatchUpdate(conn, INSERT_COMP_TASK_TYPE,
                (List[]) allParams.toArray(new List[allParams.size()]), new long[allParams.size()]);
        } catch (BatchOperationException e) {
            Util.updateCauses(causes, e);
        }
    }

    /**
     * <p>
     * This method returns the task type instances for the next step operation.
     * </p>
     *
     * <p>
     * Note, any task type has exception in the previous steps will be skipped in the next step.
     * </p>
     *
     * @param taskTypes the original task type instance in the batch operation
     * @param causes the inner causes of the batch operation
     * @return the task type instances for the next step
     */
    private TaskType[] getNextStepTaskTypes(TaskType[] taskTypes, Throwable[] causes) {
        List nextStepTaskTypes = new ArrayList();

        for (int i = 0; i < causes.length; i++) {
            if (causes[i] == null) {
                nextStepTaskTypes.add(taskTypes[i]);
            }
        }

        return (TaskType[]) nextStepTaskTypes.toArray(new TaskType[nextStepTaskTypes.size()]);
    }

    /**
     * <p>
     * This method checks the given task type instances.
     * </p>
     *
     * @param taskTypes the task type instances to check
     * @param create the flag to identify whether it is a creation or modification
     *
     * @throws IllegalArgumentException if taskTypes is null, empty, or has ids != -1 in a creation
     * or ids &lt; 0 in a modification.
     * @throws DataAccessException if some beans contain null property which is required in the persistence
     */
    private void checkTaskTypes(TaskType[] taskTypes, boolean create) throws DataAccessException {
        Util.checkNull(taskTypes, "taskTypes");

        if (taskTypes.length == 0) {
            throw new IllegalArgumentException("The given task types array is empty.");
        }

        for (int i = 0; i < taskTypes.length; i++) {
            Util.checkNull(taskTypes[i], "task type in taskTypes");

            if (create) {
                if (taskTypes[i].getId() != -1) {
                    throw new IllegalArgumentException(
                        "The task type id is set, which is not allowed for creation operation.");
                }

                // null creation user is not allowed
                if (taskTypes[i].getCreationUser() == null) {
                    throw new DataAccessException("Some task types have null creation user.");
                }

                // null creation date is not allowed
                if (taskTypes[i].getCreationDate() == null) {
                    throw new DataAccessException("Some task types have null creation date.");
                }
            } else {
                if (taskTypes[i].getId() < 0) {
                    throw new IllegalArgumentException(
                        "The task type id is negative, which is not allowed for update operation.");
                }
            }

            // null modification user is not allowed
            if (taskTypes[i].getModificationUser() == null) {
                throw new DataAccessException("Some task types have null modification user.");
            }

            // null modification date is not allowed
            if (taskTypes[i].getModificationDate() == null) {
                throw new DataAccessException("Some task types have null modification date.");
            }
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TaskTypes array.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TaskType with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param taskTypes An array of task types for which the operation should be performed.
     *
     * @throws IllegalArgumentException if taskTypes is null, empty or contains null values or
     * same reference or has ids &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTaskTypes(TaskType[] taskTypes) throws BatchOperationException, DataAccessException {
        checkTaskTypes(taskTypes, false);
        Util.checkSameTimeTrackerBeans(taskTypes, "taskTypes");

        Connection conn = null;

        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[taskTypes.length];

            long[] taskTypeIds = new long[taskTypes.length];
            for (int i = 0; i < taskTypeIds.length; i++) {
                taskTypeIds[i] = taskTypes[i].getId();
            }

            // verify the task types are present in the database
            try {
                getTaskTypes(taskTypeIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            Util.skipNotChangedBeans(taskTypes, causes);

            List[] params = new List[taskTypes.length];

            for (int i = 0; i < taskTypeIds.length; i++) {
                params[i] = new ArrayList();

                // description can be null in the database
                if (taskTypes[i].getDescription() == null) {
                    params[i].add(new SqlType(Types.VARCHAR));
                } else {
                    params[i].add(taskTypes[i].getDescription());
                }

                params[i].add(new Long(taskTypes[i].isActive() ? 1 : 0));
                params[i].add(taskTypes[i].getModificationDate());
                params[i].add(taskTypes[i].getModificationUser());
                params[i].add(new Long(taskTypes[i].getId()));
            }

            // update the records in the database
            try {
                Util.executeBatchUpdate(conn, UPDATE_TASK_TYPE, params, taskTypeIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            // remove the associations and then insert the new associations
            deleteCompTaskTypes(conn, taskTypeIds, causes);
            Util.checkStepResult(causes);

            addCompTaskTypes(conn, taskTypes, causes);

            Util.resetNotChangedExcetionCauses(causes);
            Util.finishBatchOperation(causes);
            Util.resetBeanChangedStates(taskTypes, causes);
        } finally {
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it no longer contains data on the TaskType with the specified ids.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TaskType with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param taskTypeIds An array of ids for which the operation should be performed.
     *
     * @throws IllegalArgumentException if taskTypeIds is null, empty or contains values &lt; 0 or equal
     * task type ids.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTaskTypes(long[] taskTypeIds) throws BatchOperationException, DataAccessException {
        checkTaskTypeIds(taskTypeIds);
        Util.checkEqualIds(taskTypeIds, "taskTypeIds");

        Connection conn = null;
        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[taskTypeIds.length];

            // verify all the task type ids are present
            try {
                getTaskTypes(taskTypeIds);
            } catch (BatchOperationException e) {
                Util.updateCauses(causes, e);
            }
            Util.checkStepResult(causes);

            // remove the associations in the database
            deleteCompTaskTypes(conn, taskTypeIds, causes);
            Util.checkStepResult(causes);

            List[] params = new List[taskTypeIds.length];
            for (int i = 0; i < taskTypeIds.length; i++) {
                params[i] = new ArrayList();
                params[i].add(new Long(taskTypeIds[i]));
            }

            // remove the records in the database
            try {
                Util.executeBatchUpdate(conn, DELETE_TASK_TYPE, params, taskTypeIds);
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
     * This method deletes the records in <b>comp_task_type</b> according to the given task type id array.
     * </p>
     *
     * <p>
     * The delete operation will be executed in a batch operation.
     * </p>
     *
     * @param conn the database connection to access database
     * @param taskTypeIds the task type id array to delete their associated records in <b>comp_task_type</b> table
     * @param causes the inner causes of the batch operation
     *
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    private void deleteCompTaskTypes(Connection conn, long[] taskTypeIds, Throwable[] causes)
        throws DataAccessException {
        List allParams = new ArrayList();

        for (int i = 0; i < taskTypeIds.length; i++) {
            if (causes[i] == null) {
                List params = new ArrayList();
                params.add(new Long(taskTypeIds[i]));

                allParams.add(params);
            }
        }

        // remove the records
        try {
            Util.executeBatchUpdate(conn, DELETE_COMP_TASK_TYPE,
                (List[]) allParams.toArray(new List[allParams.size()]), new long[allParams.size()]);
        } catch (BatchOperationException e) {
            Util.updateCauses(causes, e);
        }
    }

    /**
     * <p>
     * This method checks the given task type id array.
     * </p>
     *
     * @param taskTypeIds the task type id array to check
     *
     * @throws IllegalArgumentException if taskTypeIds is null, empty or contains values &lt;= 0.
     */
    private void checkTaskTypeIds(long[] taskTypeIds) {
        Util.checkNull(taskTypeIds, "taskTypeIds");

        // length check
        if (taskTypeIds.length == 0) {
            throw new IllegalArgumentException("The given task type ids array is empty.");
        }

        for (int i = 0; i < taskTypeIds.length; i++) {
            Util.checkIdValue(taskTypeIds[i], "task type");
        }
    }

    /**
     * <p>
     * Retrieves an array of TaskType objects that reflect the data in the persistent store on the
     * Time Tracker TaskType with the specified taskTypeIds.
     * </p>
     *
     * <p>
     * Note, <code>UnrecognizedEntityException</code> will be thrown as an cause of
     * <code>BatchOperationException</code> if a TaskType with the provided id was not
     * found in the persistence store.
     * </p>
     *
     * @param taskTypeIds An array of ids for which the operation should be performed.
     * @return The TaskTypes corresponding to the provided ids.
     *
     * @throws IllegalArgumentException if taskTypeIds is null, empty or contains values &lt; 0.
     * @throws BatchOperationException if a problem occurs while processing the batch.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] getTaskTypes(long[] taskTypeIds) throws BatchOperationException, DataAccessException {
        checkTaskTypeIds(taskTypeIds);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            Throwable[] causes = new Throwable[taskTypeIds.length];

            // select all the task types using the IN clause
            pstmt = conn.prepareStatement(SELECT_TASK_TYPES + " AND "
                + Util.buildInClause("task_type.task_type_id", taskTypeIds));

            rs = pstmt.executeQuery();

            // this is a mapping from the task type id to its task type instance
            Map result = new HashMap();

            while (rs.next()) {
                TaskType timeStatus = getTaskType(rs);
                result.put(new Long(timeStatus.getId()), timeStatus);
            }

            // aggregate all the task types according to the given ids
            TaskType[] types = new TaskType[taskTypeIds.length];
            for (int i = 0; i < taskTypeIds.length; i++) {
                types[i] = (TaskType) result.get(new Long(taskTypeIds[i]));
                if (types[i] == null) {
                    causes[i] = new UnrecognizedEntityException(taskTypeIds[i], "The TaskType [" + taskTypeIds[i]
                        + "] is unrecognized.");
                }
            }

            Util.finishBatchOperation(causes);

            return types;
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
     * This method creates a <code>TaskType</code> instance from the given <code>ResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>TaskType</code> instance will have <tt>unchanged</tt> state.
     * </p>
     *
     * @param rs the <code>ResultSet</code> instance
     * @return the <code>TaskType</code> created from the given <code>ResultSet</code> instance
     *
     * @throws SQLException if a database access error occurs
     */
    private TaskType getTaskType(ResultSet rs) throws SQLException {
        TaskType taskType = new TaskType();

        int index = 1;
        taskType.setId(rs.getLong(index++));
        taskType.setCompanyId(rs.getLong(index++));

        String description = rs.getString(index++);
        // the description can be null in the database
        if (description != null) {
            taskType.setDescription(description);
        }

        taskType.setActive(rs.getInt(index++) == 1);
        taskType.setCreationDate(rs.getDate(index++));
        taskType.setCreationUser(rs.getString(index++));
        taskType.setModificationDate(rs.getDate(index++));
        taskType.setModificationUser(rs.getString(index++));

        taskType.setChanged(false);

        return taskType;
    }

    /**
     * This is a batch version of the <code>isTaskTypeReferenced</code> method.
     *
     * @return a <code>boolean</code> array containing exactly the same count of elements as the
     *         <code>taskTypeIds</code> parameter does. Every entry in the resulting array
     *         specifies whether a task type is being referenced.
     * @param taskTypeIds
     *            An array of ids for which the operation should be performed.
     * @throws IllegalArgumentException
     *             if taskTypeIds is null, empty or contains values &lt; 0.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public boolean[] areTaskTypesReferenced(long[] taskTypeIds) throws DataAccessException {
        checkTaskTypeIds(taskTypeIds);

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            boolean[] referenced = new boolean[taskTypeIds.length];

            conn = getConnection();

            for (int i = 0; i < taskTypeIds.length; i++) {
                pstmt = conn.prepareStatement(COUNT_REFERENCES);
                pstmt.setLong(1, taskTypeIds[i]);

                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    throw new DataAccessException("Query for task type ID [" + taskTypeIds[i] + "] returned 0 results.");
                }

                referenced[i] = (rs.getInt(1) != 0);
            }

            return referenced;
        } catch (SQLException se) {
            throw new DataAccessException(
                    "An error occurred while trying to determine whether one or more entries are being referenced.");
        } finally {
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }

    /**
     * <p>
     * Searches the persistent store for any time statuses that satisfy the criteria that was specified in the
     * provided search filter.
     * </p>
     *
     * <p>
     * The provided filter should be created using either the filters that are created using the
     * <code>TaskTypeFilterFactory</code> returned by <code>getTaskTypeEntryFilterFactory</code> of this
     * instance, or a composite Search Filters (such as <code>AndFilter</code>, <code>OrFilter</code> and
     * <code>NotFilter</code> from Search Builder component) that combines the filters created using
     * <code>TaskTypeFilterFactory</code>.
     * </p>
     *
     * @param filter The filter used to search for TaskTypes.
     * @return The TaskTypes satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] searchTaskTypes(Filter filter) throws DataAccessException {
        Util.checkNull(filter, "filter");

        try {
            CustomResultSet result = (CustomResultSet) getSearchBundle().search(filter);

            int size = result.getRecordCount();

            // get all the task types from the search result
            TaskType[] taskTypes = new TaskType[size];
            for (int i = 0; i < size; i++) {
                result.next();
                taskTypes[i] = getTaskType(result);
            }

            return taskTypes;
        } catch (SearchBuilderException e) {
            throw new DataAccessException("Failed to search the database according to the given filter.", e);
        } catch (InvalidCursorStateException e) {
            throw new DataAccessException("Failed to retrieve the search result.", e);
        }
    }

    /**
     * <p>
     * This method creates a <code>TaskType</code> instance from the given <code>CustomResultSet</code> instance.
     * </p>
     *
     * <p>
     * Note, the returned <code>TaskType</code> instance will have <tt>unchanged</tt> state.
     * </p>
     *
     * @param result the <code>CustomResultSet</code> instance
     * @return the <code>TaskType</code> created from the given <code>CustomResultSet</code> instance
     *
     * @throws InvalidCursorStateException if unable to read data from the given <code>CustomResultSet</code>
     * instance
     */
    private TaskType getTaskType(CustomResultSet result) throws InvalidCursorStateException {
        TaskType taskType = new TaskType();

        int index = 1;
        taskType.setId(result.getLong(index++));
        taskType.setCompanyId(result.getLong(index++));

        String description = result.getString(index++);
        // the description can be null in the database
        if (description != null) {
            taskType.setDescription(description);
        }

        taskType.setActive(result.getInt(index++) == 1);
        taskType.setCreationDate(result.getDate(index++));
        taskType.setCreationUser(result.getString(index++));
        taskType.setModificationDate(result.getDate(index++));
        taskType.setModificationUser(result.getString(index++));

        taskType.setChanged(false);

        return taskType;
    }

    /**
     * <p>
     * Retrieves the <code>TaskTypeFilterFactory</code> that is capable of creating SearchFilters to
     * use when searching for TaskTypes.
     * </p>
     *
     * <p>
     * This is used to conveniently specify the search criteria that should be used. The filters returned
     * by the given factory should be used with this instance's <code>searchTaskTypes</code> method.
     * </p>
     *
     * @return the <code>TaskTypeFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for TaskTypes.
     */
    public TaskTypeFilterFactory getTaskTypeFilterFactory() {
        return taskTypeFilterFactory;
    }

    /**
     * <p>
     * Retrieves all the TaskTypes that are currently in the persistent store.
     * </p>
     *
     * @return all the TaskTypes that are currently in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] getAllTaskTypes() throws DataAccessException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(SELECT_TASK_TYPES);
            rs = pstmt.executeQuery();

            List taskTypes = new ArrayList();

            // aggregate all the task types
            while (rs.next()) {
                taskTypes.add(getTaskType(rs));
            }

            return (TaskType[]) taskTypes.toArray(new TaskType[taskTypes.size()]);
        } catch (SQLException e) {
            throw new DataAccessException("Exception occurs in database operation.", e);
        } finally {
            Util.closeResultSet(rs);
            Util.closeStatement(pstmt);
            Util.closeConnection(conn);
        }
    }
}
