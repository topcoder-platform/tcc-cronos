/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * This is a wrapper (or close to a wrapper) around any object that implements the <code>BatchDAO</code> interface.
 * This will simply delegate all the calls to the underlying batch DAO but in an asynchronous manner. The user will
 * have to provide a valid implementation of a <code>ResultListener</code> interface to be notified when the batch is
 * done. This is an idea solution for larger batches that might take considerable time and thus would benefit from
 * being run in the background.
 * </p>
 *
 * <p>
 * Thread-safe, needs to synchronize on the two provided maps when updating batch info.
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public class AsynchBatchDAOWrapper {
    /**
     * <p>
     * Represents the identifier of the each thread that holds the batch operations.
     * </p>
     */
    private static Integer threadID = new Integer(0);

    /**
     * <p>
     * Represents the <code>BatchDAO</code> instance that we are decorating.
     * </p>
     */
    private final BatchDAO decoratedBatchDAO;

    /**
     * <p>
     * Represents a list of currently running threads. The map uses a batch id as a key (i.e. Integer) and hold the
     * corresponding <code>Thread</code> instance that is running the batch as the value. When a job is finished we
     * simply remove the entry and notify the listener which is held in the batchJob2Listener map.
     * </p>
     */
    private final Map batchJobs = new HashMap();

    /**
     * <p>
     * Represents the mapping of batch job id to a listener so that we can notify the caller when the job is done. Once
     * the job is done, we just remove the mapping. If for some reason the job is terminated, we do not notify the
     * listener.
     * </p>
     */
    private final Map batchJob2Listener = new HashMap();

    /**
     * <p>
     * Creates a new instance of <code>AsynchBatchDAOWrapper</code> class with a valid <code>BatchDAO</code> instance.
     * </p>
     *
     * @param batchDAO BatchDAO instance to decorate
     *
     * @throws IllegalArgumentException if <code>batchDAO</code> is <code>null</code>.
     */
    public AsynchBatchDAOWrapper(BatchDAO batchDAO) {
        if (batchDAO == null) {
            throw new IllegalArgumentException("batchDAO can not be null.");
        }

        this.decoratedBatchDAO = batchDAO;
    }

    /**
     * <p>
     * Executes an asynchronous batch create. When the creation finished, it will notify the specific listener.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to create in DB.
     * @param user creation user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param listener listener to notify when done.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element; or
     *         any argument is <code>null</code>.
     */
    public void batchCreate(DataObject[] dataObjects, String user, boolean allOrNone, ResultListener listener) {
        // argument validation
        if (dataObjects == null) {
            throw new IllegalArgumentException("dataObjects should not be null.");
        }

        if (dataObjects.length == 0) {
            throw new IllegalArgumentException("dataObjects should not be empty.");
        }

        for (int i = 0; i < dataObjects.length; i++) {
            if (dataObjects[i] == null) {
                throw new IllegalArgumentException("dataObjects should not contain a null element.");
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("user should not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user should not be empty.");
        }

        if (listener == null) {
            throw new IllegalArgumentException("listener should not be null.");
        }

        ResultData resultData = new ResultData();
        Class[] classes = new Class[] {DataObject[].class, String.class, boolean.class, ResultData.class};
        Object[] parameters = new Object[] {dataObjects, user, new Boolean(allOrNone), resultData};
        this.runBatchThread("batchCreate", classes, parameters, resultData, listener);
    }

    /**
     * <p>
     * Executes an asynchronous batch delete. When the creation finished, it will notify the specific listener.
     * </p>
     *
     * @param idList list of ids of DataObject records to delete.
     * @param allOrNone whether the operation should be atomic or not.
     * @param listener listener to notify when done.
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty; or any argument is <code>null</code>.
     */
    public void batchDelete(int[] idList, boolean allOrNone, ResultListener listener) {
        // argument validation
        if (idList == null) {
            throw new IllegalArgumentException("idList should not be null.");
        }

        if (idList.length == 0) {
            throw new IllegalArgumentException("idList should not be empty.");
        }

        if (listener == null) {
            throw new IllegalArgumentException("listener should not be null.");
        }

        ResultData resultData = new ResultData();
        Class[] classes = new Class[] {int[].class, boolean.class, ResultData.class};
        Object[] parameters = new Object[] {idList, new Boolean(allOrNone), resultData};
        this.runBatchThread("batchDelete", classes, parameters, resultData, listener);
    }

    /**
     * <p>
     * Executes an asynchronous batch update. When the creation finished, it will notify the specific listener.
     * </p>
     *
     * @param dataObjects an array of <code>DataObject</code> instances to update in DB.
     * @param user modification user.
     * @param allOrNone whether the operation should be atomic or not.
     * @param listener listener to notify when done
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty or has <code>null</code> element; or
     *         any argument is <code>null</code>.
     */
    public void batchUpdate(DataObject[] dataObjects, String user, boolean allOrNone, ResultListener listener) {
        // argument validation
        if (dataObjects == null) {
            throw new IllegalArgumentException("dataObjects should not be null.");
        }

        if (dataObjects.length == 0) {
            throw new IllegalArgumentException("dataObjects should not be empty.");
        }

        for (int i = 0; i < dataObjects.length; i++) {
            if (dataObjects[i] == null) {
                throw new IllegalArgumentException("dataObjects should not contain a null element.");
            }
        }

        if (user == null) {
            throw new IllegalArgumentException("user should not be null.");
        }

        if (user.trim().length() == 0) {
            throw new IllegalArgumentException("user should not be empty.");
        }

        if (listener == null) {
            throw new IllegalArgumentException("listener should not be null.");
        }

        ResultData resultData = new ResultData();
        Class[] classes = new Class[] {DataObject[].class, String.class, boolean.class, ResultData.class};
        Object[] parameters = new Object[] {dataObjects, user, new Boolean(allOrNone), resultData};
        this.runBatchThread("batchUpdate", classes, parameters, resultData, listener);
    }

    /**
     * <p>
     * Executes an asynchronous batch read. When the creation finished, it will notify the specific listener.
     * </p>
     *
     * @param idList list of ids of DataObject records to fetch.
     * @param allOrNone whether the operation should be atomic or not.
     * @param listener listener to notify when done
     *
     * @throws IllegalArgumentException if the array is <code>null</code>, empty; or any argument is <code>null</code>.
     */
    public void batchRead(int[] idList, boolean allOrNone, ResultListener listener) {
        // argument validation
        if (idList == null) {
            throw new IllegalArgumentException("idList should not be null.");
        }

        if (idList.length == 0) {
            throw new IllegalArgumentException("idList should not be empty.");
        }

        if (listener == null) {
            throw new IllegalArgumentException("listener should not be null.");
        }

        ResultData resultData = new ResultData();
        Class[] classes = new Class[] {int[].class, boolean.class, ResultData.class};
        Object[] parameters = new Object[] {idList, new Boolean(allOrNone), resultData};
        this.runBatchThread("batchRead", classes, parameters, resultData, listener);
    }

    /**
     * <p>
     * Creates a ConversionThread running the method of specified methodName with specified parameters. The arguments
     * passed in here must be correct to cause no exception. Note that on return of this method, the ConversionThread
     * may still be running.
     * </p>
     *
     * @param methodName the name of the method to run.
     * @param classes the classes of the parameters in declared order.
     * @param parameters the parameters used for the method call
     * @param resultData the resultData which will hold batch operation results and exceptions.
     * @param listener listener to notify when done
     */
    private void runBatchThread(String methodName, Class[] classes, Object[] parameters, ResultData resultData,
        ResultListener listener) {
        // get the Method object
        Method method = null;

        try {
            method = decoratedBatchDAO.getClass().getMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            // should not happen
        }

        BatchThread thread = null;

        threadID = new Integer(threadID.intValue() + 1);

        // create the thread
        thread = new BatchThread(decoratedBatchDAO, method, parameters, resultData);

        // register this thread
        resultData.setProperty(ResultData.BATCH_ID_KEY, threadID);
        synchronized (this.batchJobs) {
            this.batchJobs.put(threadID, thread);
        }
        synchronized (this.batchJob2Listener) {
            this.batchJob2Listener.put(threadID, listener);
        }

        // run the thread to do the batch operations
        thread.start();
    }

    /**
     * <p>
     * This Thread class is used to handle the asynchronous processing of batch operations.
     * </p>
     *
     * @author AleaActaEst , TCSDEVELOPER
     * @version 1.1
     */
    private class BatchThread extends Thread {
        /**
         * <p>
         * Represents the object the underlying method is invoked from. If the method is static, the value should be
         * null.
         * </p>
         */
        private Object object = null;

        /**
         * <p>
         * Represents the method that this thread will run. The value cannot be null.
         * </p>
         */
        private Method method = null;

        /**
         * <p>
         * Represents the parameters used for the method call.
         * </p>
         */
        private Object[] parameters = null;

        /**
         * <p>
         * Represents the resultData which will hold batch operation results and exceptions.
         * </p>
         */
        private ResultData resultData = null;

        /**
         * <p>
         * Creates a BatchThread instance used to handle the asynchronous processing. This constructor will be called
         * by the asynchronous methods in AsynchBatchDAOWrapper class.
         * </p>
         *
         * @param object the object the underlying method is invoked from, null value indicates the method is static
         * @param method the method that this thread will run
         * @param parameters the parameters used for the method call
         * @param resultData the resultData which will hold batch operation results and exceptions.
         */
        public BatchThread(Object object, Method method, Object[] parameters, ResultData resultData) {
            this.object = object;
            this.method = method;
            this.parameters = parameters;
            this.resultData = resultData;
        }

        /**
         * <p>
         * This method overrides the run method of Thread class. It does the batch operations, then updates the
         * successfully finished status and notifies the listener that batch operation was finished.
         * </p>
         */
        public void run() {
            // run the method
            Integer id = (Integer) resultData.getProperty(ResultData.BATCH_ID_KEY);
            ResultListener listener = (ResultListener) batchJob2Listener.get(id);

            try {
                // for batch operations, such as batchRead, batchDelete, batchUpdate, batchCreate
                method.invoke(object, parameters);
            } catch (Exception e) {
                BatchOperationException batchOperationException =
                    new BatchOperationException("Exception occurs in the thread.", e);
                resultData.setProperty(ResultData.BATCH_FAILURE_EXCEPTION_KEY, batchOperationException);
            } finally {
                synchronized (batchJobs) {
                    batchJobs.remove(id);
                }
                synchronized (batchJob2Listener) {
                    batchJob2Listener.remove(id);
                }

                // notify listener that batch operation was finished
                if (listener != null) {
                    listener.notify(resultData);
                }
            }
        }
    }
}
