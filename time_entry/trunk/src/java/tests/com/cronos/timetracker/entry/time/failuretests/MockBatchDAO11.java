/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.failuretests;

import com.cronos.timetracker.entry.time.BatchDAO;
import com.cronos.timetracker.entry.time.BatchOperationException;
import com.cronos.timetracker.entry.time.DataObject;
import com.cronos.timetracker.entry.time.ResultData;

/**
 * <p>
 * Mock BatchDAO for failure tests.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
class MockBatchDAO11 implements BatchDAO {
    /**
     * <p>
     * .
     * </p>
     * @param dataObjects
     * @param user
     * @param allOrNone
     * @param resultData
     * @throws BatchOperationException
     * @see com.cronos.timetracker.entry.time.BatchDAO#batchCreate(com.cronos.timetracker.entry.time.DataObject[], java.lang.String, boolean, com.cronos.timetracker.entry.time.ResultData)
     */
    public void batchCreate(DataObject[] dataObjects, String user,
            boolean allOrNone, ResultData resultData)
            throws BatchOperationException {
        if (user.equals("illegal user")) {
            throw new BatchOperationException("illegal user.");
        }
    }

    /**
     * <p>
     * .
     * </p>
     * @param idList
     * @param allOrNone
     * @param resultData
     * @throws BatchOperationException
     * @see com.cronos.timetracker.entry.time.BatchDAO#batchDelete(int[], boolean, com.cronos.timetracker.entry.time.ResultData)
     */
    public void batchDelete(int[] idList, boolean allOrNone,
            ResultData resultData) throws BatchOperationException {
    }

    /**
     * <p>
     * .
     * </p>
     * @param dataObjects
     * @param user
     * @param allOrNone
     * @param resultData
     * @throws BatchOperationException
     * @see com.cronos.timetracker.entry.time.BatchDAO#batchUpdate(com.cronos.timetracker.entry.time.DataObject[], java.lang.String, boolean, com.cronos.timetracker.entry.time.ResultData)
     */
    public void batchUpdate(DataObject[] dataObjects, String user,
            boolean allOrNone, ResultData resultData)
            throws BatchOperationException {
    }

    /**
     * <p>
     * .
     * </p>
     * @param idList
     * @param allOrNone
     * @param resultData
     * @throws BatchOperationException
     * @see com.cronos.timetracker.entry.time.BatchDAO#batchRead(int[], boolean, com.cronos.timetracker.entry.time.ResultData)
     */
    public void batchRead(int[] idList, boolean allOrNone, ResultData resultData)
            throws BatchOperationException {
    }

}
