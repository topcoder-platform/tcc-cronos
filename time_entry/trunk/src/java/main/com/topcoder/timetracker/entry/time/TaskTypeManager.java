/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the API that may be used in order to manipulate the various details
 * involving a <code>TaskType</code>.
 * </p>
 *
 * <p>
 * CRUDE and search methods are provided to manage the TaskTypes inside a persistent store.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for statuses based on different search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is required for implementations of this interface.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TaskTypeManager {
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
    public void createTaskType(TaskType taskType) throws DataAccessException;

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
    public void updateTaskType(TaskType taskType) throws UnrecognizedEntityException, DataAccessException;

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
    public void deleteTaskType(long taskTypeId) throws UnrecognizedEntityException, DataAccessException;

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
    public TaskType getTaskType(long taskTypeId) throws UnrecognizedEntityException, DataAccessException;

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
    public TaskType[] searchTaskTypes(Filter criteria) throws DataAccessException;

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
    public void createTaskTypes(TaskType[] taskTypes) throws BatchOperationException, DataAccessException;

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
    public void updateTaskTypes(TaskType[] taskTypes) throws BatchOperationException, DataAccessException;

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
    public void deleteTaskTypes(long[] taskTypeIds) throws BatchOperationException, DataAccessException;

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
    public TaskType[] getTaskTypes(long[] taskTypeIds) throws BatchOperationException, DataAccessException;

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
    public TaskTypeFilterFactory getTaskTypeFilterFactory() throws DataAccessException;

    /**
     * <p>
     * Retrieves all the TaskTypes that are currently in the persistent store.
     * </p>
     *
     * @return all the TaskTypes that are currently in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] getAllTaskTypes() throws DataAccessException;
}
