/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.delegate;

import com.topcoder.timetracker.entry.time.TaskTypeFilterFactory;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeManager;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;
import com.topcoder.timetracker.entry.time.db.Util;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerLocal;
import com.topcoder.timetracker.entry.time.ejb.TaskTypeManagerLocalHome;

/**
 * <p>
 * This is a Business Delegate/Service Locator that may be used within a J2EE application.
 * </p>
 *
 * <p>
 * It is responsible for looking up the local interface of the SessionBean for <code>TaskTypeManager</code>,
 * and delegating any calls to the bean.
 * </p>
 *
 * <p>
 * Thread Safety: This class is thread safe, since all state is modified at construction.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TaskTypeManagerDelegate implements TaskTypeManager {
    /**
     * <p>
     * This is the local interface for the <code>TaskTypeManager</code> business services.
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
    private final TaskTypeManagerLocal local;

    /**
     * <p>
     * Constructor that accepts configuration from the specified ConfigManager namespace.
     * </p>
     *
     * @param namespace The namespace to use.
     * @throws IllegalArgumentException if namespace is null or an empty String.
     *
     * @throws ConfigurationException if a problem occurs while constructing the instance.
     */
    public TaskTypeManagerDelegate(String namespace) throws ConfigurationException {
        TaskTypeManagerLocalHome home = (TaskTypeManagerLocalHome) Util.createEJBLocalHome(namespace,
            TaskTypeManagerLocalHome.class);

        this.local = home.create();
    }

    /**
     * <p>
     * Defines a set of TaskType to be recognized within the persistent store managed by this DAO.
     * </p>
     *
     * <p>
     * A unique task type id will automatically be generated and assigned to the task types.
     * </p>
     *
     * <p>
     * It will set the TaskType's' creation and modification date to the current date.
     * These creation/modification details will also reflect in the persistent store.
     * The creation and modification user is the responsibility of the calling application.
     * </p>
     *
     * @param taskType The TaskType for which the operation should be performed.
     *
     * @throws IllegalArgumentException if taskType is null or has id != -1.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void createTaskType(TaskType taskType) throws DataAccessException {
        local.createTaskType(taskType);
    }

    /**
     * <p>
     * Modifies the persistent store so that it now reflects the data in the provided TaskType parameter.
     * </p>
     *
     * <p>
     * It will set the TaskType's modification details to the current date.
     * These modification details will also reflect in the persistent store.
     * The modification user is the responsibility of the calling application.
     * </p>
     *
     * @param taskType The TaskType for which the operation should be performed.
     *
     * @throws IllegalArgumentException if taskType is null or has id &lt; 0
     * @throws UnrecognizedEntityException if a TaskType with the provided id was not found
     * in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store, or some
     * beans contain null property which is required in the persistence
     */
    public void updateTaskType(TaskType taskType) throws UnrecognizedEntityException, DataAccessException {
        local.updateTaskType(taskType);
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the TaskType
     * with the specified id.
     * </p>
     *
     * @param taskTypeId The id of the taskType for which the operation should be performed.
     *
     * @throws IllegalArgumentException if taskTypeId &lt; 0.
     * @throws UnrecognizedEntityException if a task type with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public void deleteTaskType(long taskTypeId) throws UnrecognizedEntityException, DataAccessException {
        local.deleteTaskType(taskTypeId);
    }

    /**
     * <p>
     * Retrieves a TaskType object that reflects the data in the persistent store on the Time Tracker
     * TaskType with the specified taskTypeId.
     * </p>
     *
     * @param taskTypeId The id of the TaskType to retrieve.
     * @return The TaskType with specified id.
     *
     * @throws IllegalArgumentException if taskTypeId &lt; 0.
     * @throws UnrecognizedEntityException if a TaskType with the provided id was not found in the data store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType getTaskType(long taskTypeId) throws UnrecognizedEntityException, DataAccessException {
        return local.getTaskType(taskTypeId);

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
     * @param criteria The filter used to search for TaskTypes.
     * @return The TaskTypes satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] searchTaskTypes(Filter criteria) throws DataAccessException {
        return local.searchTaskTypes(criteria);

    }

    /**
     * <p>
     * This is a batch version of the <code>createTaskType</code> method.
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
        local.createTaskTypes(taskTypes);
    }

    /**
     * <p>
     * This is a batch version of the <code>updateTaskType</code> method.
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
        local.updateTaskTypes(taskTypes);
    }

    /**
     * <p>
     * This is a batch version of the <code>deleteTaskType</code> method.
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
        local.deleteTaskTypes(taskTypeIds);
    }

    /**
     * <p>
     * This is a batch version of the <code>getTaskType</code> method.
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
        return local.getTaskTypes(taskTypeIds);

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
     *
     * @throws DataAccessException if unable to get the task type filter factory for any reason
     */
    public TaskTypeFilterFactory getTaskTypeFilterFactory() throws DataAccessException {
        return local.getTaskTypeFilterFactory();

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
        return local.getAllTaskTypes();
    }
}
