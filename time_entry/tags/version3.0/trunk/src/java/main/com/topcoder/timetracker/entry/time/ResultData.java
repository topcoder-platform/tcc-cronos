/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This is a simple class, which allows the caller to retrieve result exceptions, operation result information,
 * original batch operations, as well as the actual result of the operation itself (this is only true when dealing
 * with batched reads). This class is basically a way for the user to get all the necessary information about a batch
 * operation's results.
 * </p>
 *
 * <p>
 * This class is not thread-Safe since it is mutable.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class ResultData {
    /**
     * <p>
     * Represents a key which would be used to store a batch id for a running thread that has finished.
     * </p>
     */
    public static final String BATCH_ID_KEY = "batch.ID";

    /**
     * <p>
     * Represents a key which would be used to store the exception thrown by the asy process.
     * </p>
     */
    public static final String BATCH_FAILURE_EXCEPTION_KEY = "batch.exception.failure";

    /**
     * <p>
     * Represents the actual results of this process. Note that this will only be relevant in a READ situation where we
     * are getting back a number of 'hit' records.
     * </p>
     */
    private DataObject[] batchResults = null;

    /**
     * <p>
     * Represents a batch exception array which is used to wrap around a collection of other exceptions. The idea here
     * is that for every batched operation (i.e. out of all batched operations) we hold a positional slot in this
     * array to hold the exception that was caught during the operation. The array is 1-1 with the operations array
     * and holds a null in the position which did not generate an exception. Can be null, if null this means that
     * there have been no exceptions. Can change. If not null then it will have the exact same number of elements as
     * the dataObjects or idList array in the BatchDAO CRUD API calls.
     * </p>
     */
    private BatchOperationException[] exceptionList = null;

    /**
     * <p>
     * Represents a property bag for this data holder. This is an extension point for other data that might be required
     * to be passed by the batch process in the future. Currently only one key is defined and that is "Batch,ID" which
     * can hold the batch id if the batch process that finished execution in an asynchronous mode. Can be null, but
     * cannot be empty. Once set it will not change.
     * </p>
     */
    private final Map properties = new HashMap();

    /**
     * <p>
     * Represents the original set of operations that were used for this batch. Currently we can have only two types of
     * entities in the array: DataObject for create and update. Int for delete and read. This should not be null. can
     * change. This should be set by the actual implementation of the CRUD batch operations based on the specific
     * input into the CRUD operation. Thus for a batchCreate.
     * </p>
     */
    private Object[] operations = null;

    /**
     * <p>
     * Do nothing constructor.
     * </p>
     */
    public ResultData() {
        // do nothing here
    }

    /**
     * <p>
     * Gets the result batch data which will only be relevant for reads.
     * </p>
     *
     * @return the result batch data.
     */
    public DataObject[] getBatchResults() {
        return batchResults;
    }

    /**
     * <p>
     * Represents the batch process result this will typically only be relevant for reads set the batchResults member.
     * </p>
     *
     * @param batchResults an array of DataObject instances that we got as result
     *
     * @throws IllegalArgumentException if batchResults is null.
     */
    public void setBatchResults(DataObject[] batchResults) {
        if (batchResults == null) {
            throw new IllegalArgumentException("batchResults can not be null.");
        }

        this.batchResults = batchResults;
    }

    /**
     * <p>
     * Represents a batch exception array which is used to wrap around a collection of other exceptions. The idea here
     * is that for every batched operation (i.e. out of all batched operations) we hold a positional slot in this
     * array to hold the exception taht was caught during the operation. The array is 1-1 with the operations array
     * and holds a null in teh position which did not generate an exception. Can be null. Can change. If not null then
     * it will have the exact same number of elements as the dataObjects or idList array in the BatchDAO CRUD API
     * calls.
     * </p>
     *
     * @return an array of exceptions.
     */
    public BatchOperationException[] getExceptionList() {
        return exceptionList;
    }

    /**
     * <p>
     * Represents a batch exception array which is used to wrap around a collection of other exceptions. The idea here
     * is that for every batched operation (i.e. out of all batched operations) we hold a positional slot in this
     * array to hold the exception taht was caught during the operation. The array is 1-1 with the operations array
     * and holds a null in teh position which did not generate an exception. Can be null. If not null then it will
     * have the exact same number of elements as the dataObjects or idList array in the BatchDAO CRUD API calls.
     * </p>
     *
     * @param exceptionList array of exceptions
     */
    public void setExceptionList(BatchOperationException[] exceptionList) {
        this.exceptionList = exceptionList;
    }

    /**
     * <p>
     * Sets a property in the map. Neither null keys nor null values are allowed. These are general purpose properties
     * that can be used by the user to store more information about results.
     * </p>
     *
     * @param key property key
     * @param value property value
     *
     * @throws IllegalArgumentException if either key or value is null.
     */
    public void setProperty(String key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("value can not be null.");
        }

        this.properties.put(key, value);
    }

    /**
     * <p>
     * Gets a property from the map. Null keys are not allowed. These are general purpose properties that can be used
     * by the user to store more information about results.
     * </p>
     *
     * @param key property key
     *
     * @return values associated with the key
     *
     * @throws IllegalArgumentException if key is null
     */
    public Object getProperty(String key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null.");
        }

        return this.properties.get(key);
    }

    /**
     * <p>
     * Gets the number of batch operations that have failed. This is simply done by counting the number of
     * exceptions in the exceptionList. If the list is null then we have 0; otherwise we look through the list and
     * count all exceptions in it (i.e. non-null entries).
     * </p>
     *
     * @return number of failed operations
     */
    public int getFailedCount() {
        if (this.exceptionList == null) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < this.exceptionList.length; ++i) {
            if (this.exceptionList[i] != null) {
                count++;
            }
        }

        return count;
    }

    /**
     * <p>
     * Gets the number of batch operations that have suceeded. This is simply done by subtracting from the total
     * number of operation the number of failed operations.
     * </p>
     *
     * @return number of successful batch operations.
     */
    public int getSuccessCount() {
        return this.operations.length - this.getFailedCount();
    }

    /**
     * <p>
     * returns the operations that were used to produce this result.
     * </p>
     *
     * @return the original set of operations that were used for this batch.
     */
    public Object[] getOperations() {
        return this.operations;
    }

    /**
     * <p>
     * Sets the operations that were used to produce this result.
     * </p>
     *
     * @param operations batch operations used for this result
     *
     * @throws IllegalArgumentException if the input is null or if the element types are not DataObject or Integer.
     *         Also thrown if the elements are not all of the same legal type.
     */
    public void setOperations(Object[] operations) {
        if (operations == null) {
            throw new IllegalArgumentException("operations can not be null.");
        }
        Object pre = null;
        for (int i = 0; i < operations.length; i++) {
            if (!(operations[i] instanceof Integer) && !(operations[i] instanceof DataObject)) {
                throw new IllegalArgumentException("The type is neither DataObject nor Integer.");
            }
            if (pre == null) {
                pre = operations[i];
            } else {
                if (!operations[i].getClass().equals(pre.getClass())) {
                    throw new IllegalArgumentException("the elements are not all of the same legal type.");
                }
            }
        }
        this.operations = operations;
    }
}
