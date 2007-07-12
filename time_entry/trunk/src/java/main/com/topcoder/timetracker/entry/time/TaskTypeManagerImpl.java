/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.db.Util;

/**
 * <p>
 * This is a default implementation of the <code>TaskTypeManager</code> interface.
 * </p>
 *
 * <p>
 * It utilizes instances of the <code>TaskTypeDAO</code> in order to fulfill the necessary CRUDE and
 * search operations defined for the Time Tracker TaskType.
 * </p>
 *
 * <p>
 * It is also possible to search the persistent store for task types based on different search criteria.
 * </p>
 *
 * <p>
 * Thread safety: Thread safety is dependent on the DAO implementations. Since the DAOs are also required
 * to be thread-safe, this class is thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TaskTypeManagerImpl implements TaskTypeManager {
    /**
     * <p>
     * This is the DAO instance that is used to retrieve data from and
     * modify the persistent store when dealing with the TaskTypes.
     * </p>
     *
     * <p>
     * It is set in the constructor and never changed afterwards.
     * </p>
     *
     * <p>
     * It cannot be null.
     * </p>
     */
    private final TaskTypeDAO taskTypeDao;

    /**
     * <p>
     * Constructor that accepts the necessary parameters to initialize the <code>TaskTypeManagerImpl</code>.
     * </p>
     *
     * @param taskTypeDAO The TaskTypeDAO to use.
     *
     * @throws IllegalArgumentException if any argument is null.
     */
    public TaskTypeManagerImpl(TaskTypeDAO taskTypeDAO) {
        Util.checkNull(taskTypeDAO, "taskTypeDAO");

        this.taskTypeDao = taskTypeDAO;
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
        try {
            createTaskTypes(new TaskType[] {taskType});
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to create the task type.");
        }
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
        try {
            updateTaskTypes(new TaskType[] {taskType});
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to update the task type.");
        }
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
        try {
            deleteTaskTypes(new long[] {taskTypeId});
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to delete the task type.");
        }
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
        try {
            return taskTypeDao.getTaskTypes(new long[] {taskTypeId})[0];
        } catch (BatchOperationException e) {
            Util.extractBatchOperationException(e, "Unable to get the task type.");
            // never reach here
            return null;
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
     * @param criteria The filter used to search for TaskTypes.
     * @return The TaskTypes satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] searchTaskTypes(Filter criteria) throws DataAccessException {
        return this.taskTypeDao.searchTaskTypes(criteria);
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
        Util.updateTimeTrackerBeanDates(taskTypes, "taskTypes", true);

        this.taskTypeDao.createTaskTypes(taskTypes);

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
        Util.updateTimeTrackerBeanDates(taskTypes, "taskTypes", false);

        this.taskTypeDao.updateTaskTypes(taskTypes);
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
        this.taskTypeDao.deleteTaskTypes(taskTypeIds);
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
        return this.taskTypeDao.getTaskTypes(taskTypeIds);
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
     * @throws BatchOperationException
     *             if a problem occurs while processing the batch.
     * @throws DataAccessException
     *             if a problem occurs while accessing the persistent store.
     */
    public boolean[] areTaskTypesReferenced(long[] taskTypeIds) throws BatchOperationException, DataAccessException {
        return this.taskTypeDao.areTaskTypesReferenced(taskTypeIds);
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
        return this.taskTypeDao.getTaskTypeFilterFactory();
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
        return this.taskTypeDao.getAllTaskTypes();
    }
}
