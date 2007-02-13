/**
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.stresstests;

import com.topcoder.timetracker.entry.time.ResultData;
import com.topcoder.timetracker.entry.time.ResultListener;

/**
 * An mock implementation of interface <code>ResultListener</code>.
 * <p>
 * This class is used in the test cases for <code>AsynchBatchDAOWrapper</code>.
 * </p>
 * @author fuyun
 * @version 1.1
 */
public class StressResultListener implements ResultListener {

    /**
     * Represents the <code>ResultData</code> instance.
     */
    private ResultData resultData = null;

    /**
     * <p>
     * Called by the asynchronous Observable process.
     * </p>
     * @param resultData result data to process
     */
    public void notify(ResultData resultData) {
        this.resultData = resultData;
    }

    /**
     * Get the <code>ResultData</code> field.
     * @return the <code>ResultData</code> field.
     */
    public ResultData getResultData() {
        return resultData;
    }

    /**
     * Set the the <code>ResultData</code> field.
     * @param resultData the <code>ResultData</code> value.
     */
    public void setResultData(ResultData resultData) {
        this.resultData = resultData;
    }
}
