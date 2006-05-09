/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

/**
 * <p>
 * This class defines the four new methods for doing bulk operations on sets of times entries. These method can work in
 * atomic mode (a failure on one entry causes the entire operation to fail) or non-atomic (a failure in one entry
 * doesn't affect the other and the user has a way to know which ones failed).
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public interface BatchDAO {
    /**
     * <p>
     * Adds a set of entries to the database.
     * </p>
     *
     * <p>
     * If the addition is atomic then it means that entire retrieval will fail if a single time entry addition fails.
     * </p>
     *
     * <p>
     * If the addition is non-atomic then it means each time entry is added individually. If it fails, that won't
     * affect the others, we create an entry in an exceptionList array and we also need to keep track of the index of
     * the operation that we are on. Exception is related to acquiring an SQL connection or something like that, it is
     * obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to create in DB.
     * @param user creation user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element or
     *         has invalid type; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void batchCreate(DataObject[] dataObjects, String user, boolean allOrNone, ResultData resultData)
        throws BatchOperationException;

    /**
     * <p>
     * Deletes a set of entries from the database with the given IDs from the persistence.
     * </p>
     *
     * <p>
     * If the deletion is atomic then it means that entire retrieval will fail if a single time entry deletion fails.
     * </p>
     *
     * <p>
     * If the deletion is non-atomic then it means each time entry is deleted individually. If it fails, that won't
     * affect the others, we create an entry in an exceptionList array and we also need to keep track of the index of
     * the operation that we are on. Exception is related to acquiring an SQL connection or something like that, it is
     * obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * @param idList list of ids of DataObject records to delete.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void batchDelete(int[] idList, boolean allOrNone, ResultData resultData)
        throws BatchOperationException;

    /**
     * <p>
     * Updates a set of entries to the database.
     * </p>
     *
     * <p>
     * If the update is atomic then it means that entire retrieval will fail if a single time entry update fails.
     * </p>
     *
     * <p>
     * If the update is non-atomic then it means each time entry is updated individually. If it fails, that won't
     * affect the others, we create an entry in an exceptionList array and we also need to keep track of the index of
     * the operation that we are on. Exception is related to acquiring an SQL connection or something like that, it is
     * obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to update in DB.
     * @param user modification user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element or
     *         has invalid type; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void batchUpdate(DataObject[] dataObjects, String user, boolean allOrNone, ResultData resultData)
        throws BatchOperationException;

    /**
     * <p>
     * Retrieves a set of entries with given ids from the database.
     * </p>
     *
     * <p>
     * If the retrieval is atomic then it means that entire retrieval will fail if a single time entry retrieval fails.
     * </p>
     *
     * <p>
     * If the retrieval is non-atomic then it means each expense entry is retrieved individually. If it fails, that
     * won't affect the others, we create an entry in an exceptionList array and we also need to keep track of the
     * index of the operation that we are on. Exception is related to acquiring an SQL connection or something like
     * that, it is obvious that all entries will fail so the exception should be thrown.
     * </p>
     *
     * @param idList list of ids of DataObject records to fetch.
     * @param allOrNone whether the operation should be atomic or not.
     * @param resultData the resultData which will hold batch operation results and exceptions.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty; or any argument is <code>null</code>.
     * @throws BatchOperationException wraps a persistence implementation specific exception (such as SQL exception).
     */
    void batchRead(int[] idList, boolean allOrNone, ResultData resultData)
        throws BatchOperationException;
}
