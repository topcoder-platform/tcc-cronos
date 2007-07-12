/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is an interface definition for the DAO that is responsible for handling the retrieval, storage,
 * and searching of <code>TaskType</code> data from a persistent store.
 * </p>
 *
 * <p>
 * It is also responsible for generating ids for any entities within it's scope, whenever an id is required.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TaskTypeDAO {
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
    public void createTaskTypes(TaskType[] taskTypes) throws BatchOperationException, DataAccessException;

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
    public void updateTaskTypes(TaskType[] taskTypes) throws BatchOperationException, DataAccessException;

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
    public void deleteTaskTypes(long[] taskTypeIds) throws BatchOperationException, DataAccessException;

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
    public TaskType[] getTaskTypes(long[] taskTypeIds) throws BatchOperationException, DataAccessException;
    
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
    public boolean[] areTaskTypesReferenced(long[] taskTypeIds) throws DataAccessException;

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
    public TaskType[] searchTaskTypes(Filter filter) throws DataAccessException;

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
    public TaskTypeFilterFactory getTaskTypeFilterFactory();

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
