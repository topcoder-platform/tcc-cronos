/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.ejb;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import com.topcoder.timetracker.entry.time.TaskTypeFilterFactory;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.BatchOperationException;
import com.topcoder.timetracker.entry.time.DataAccessException;
import com.topcoder.timetracker.entry.time.TaskType;
import com.topcoder.timetracker.entry.time.TaskTypeManager;
import com.topcoder.timetracker.entry.time.ConfigurationException;
import com.topcoder.timetracker.entry.time.ManagerFactory;
import com.topcoder.timetracker.entry.time.UnrecognizedEntityException;

/**
 * <p>
 * This is a Stateless SessionBean that is used to provided business services to manage TaskTypes
 * within the Time Tracker Application.
 * </p>
 *
 * <P>
 * It contains the same methods as <code>TaskTypeManager</code>, and delegates to
 * an instance of <code>TaskTypeManager</code>. The instance of <code>TaskTypeManager</code>
 * to use is retrieved from <code>ManagerFactory</code>.
 * </p>
 *
 * <p>
 * Transactions for this bean are handled by the EJB Container. It is expected that the transaction
 * level declared in the deployment descriptor for this class will be <tt>REQUIRED</tt>.
 * </p>
 *
 * <p>
 * Thread Safety: This class is managed by the EJB Container, which will ensure that it's accessed
 * by no more than one thread at the same time.
 * </p>
 *
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class TaskTypeManagerSessionBean implements TaskTypeManager, SessionBean {
    /**
     * <p>
     * This is the instance of <code>SessionContext</code> that was provided by the
     * EJB container.
     * </p>
     *
     * <p>
     * It is stored and made available to subclasses. It is also used when performing a rollback
     * in the case that an exception occurred.
     * </p>
     *
     * <p>
     * It is null initially and will be set by EJB container.
     * </p>
     *
     * <p>
     * Getter and setter are provided.
     * </p>
     */
    private SessionContext context;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public TaskTypeManagerSessionBean() {
        // empty
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
            getTaskTypeManager().createTaskType(taskType);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
            getTaskTypeManager().updateTaskType(taskType);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Modifies the persistent store so that it  no longer contains data on the TaskType
     * with the specified id.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
            getTaskTypeManager().deleteTaskType(taskTypeId);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves a TaskType object that reflects the data in the persistent store on the Time Tracker
     * TaskType with the specified taskTypeId.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
            return getTaskTypeManager().getTaskType(taskTypeId);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @param criteria The filter used to search for TaskTypes.
     * @return The TaskTypes satisfying the conditions in the search filter.
     *
     * @throws IllegalArgumentException if criteria is null.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] searchTaskTypes(Filter criteria) throws DataAccessException {
        try {
            return getTaskTypeManager().searchTaskTypes(criteria);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This is a batch version of the <code>createTaskType</code> method.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getTaskTypeManager().createTaskTypes(taskTypes);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getTaskTypeManager().updateTaskTypes(taskTypes);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            getTaskTypeManager().deleteTaskTypes(taskTypeIds);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
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
        try {
            return getTaskTypeManager().getTaskTypes(taskTypeIds);
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
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
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return the <code>TaskTypeFilterFactory</code> that is capable of creating SearchFilters to use
     * when searching for TaskTypes.
     *
     * @throws DataAccessException if unable to get the task type filter factory for any reason
     */
    public TaskTypeFilterFactory getTaskTypeFilterFactory() throws DataAccessException {
        try {
            return getTaskTypeManager().getTaskTypeFilterFactory();
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Retrieves all the TaskTypes that are currently in the persistent store.
     * </p>
     *
     * <p>
     * Note, if any exception is thrown, {@link javax.ejb.EJBContext#setRollbackOnly()} will
     * be used to rollback the transaction.
     * </p>
     *
     * @return all the TaskTypes that are currently in the persistent store.
     * @throws DataAccessException if a problem occurs while accessing the persistent store.
     */
    public TaskType[] getAllTaskTypes() throws DataAccessException {
        try {
            return getTaskTypeManager().getAllTaskTypes();
        } catch (DataAccessException e) {
            getSessionContext().setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbCreate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbActivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbPassivate() {
        // empty
    }

    /**
     * <p>
     * This method has simply been included to complete the <code>SessionBean</code> interface.
     * </p>
     *
     * <p>
     * It has an empty method body.
     * </p>
     */
    public void ejbRemove() {
        // empty
    }

    /**
     * <p>
     * Sets the <code>SessionContext</code> to use for this session.
     * </p>
     *
     * <p>
     * This method is included to comply with the <code>SessionBean</code> interface.
     * </p>
     *
     * @param ctx The <code>SessionContext</code> to use
     */
    public void setSessionContext(SessionContext ctx) {
        this.context = ctx;
    }

    /**
     * <p>
     * Protected method that allows subclasses to access the session context
     * if necessary.
     * </p>
     *
     * @return The session context provided by the EJB container.
     */
    protected SessionContext getSessionContext() {
        return context;
    }

    /**
     * <p>
     * This method gets the current task type manager in the factory.
     * </p>
     *
     * @return the current task type manager in the factory
     *
     * @throws DataAccessException to wrap the <code>ConfigurationException</code> thrown by
     * <code>ManagerFactory</code>
     */
    private TaskTypeManager getTaskTypeManager() throws DataAccessException {
        try {
            return ManagerFactory.getTaskTypeManager();
        } catch (ConfigurationException e) {
            throw new DataAccessException("Unable to get the task type manager.", e);
        }
    }
}
