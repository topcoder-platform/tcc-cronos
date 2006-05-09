/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;



/**
 * A mock class which implements the <code>ResultListener</code> for testing.
 *
 * @author TCSDEVELOPER
 * @version 1.1
 */
public class MockResultListener implements ResultListener {
    /** Represents the actual result data to check. */
    private ResultData resultData = null;

    /** Represents whether the listerner has been notified. */
    private boolean finished = false;

    /**
     * Sets the actual result data for further checking.
     *
     * @param resultData the input result data.
     */
    public void notify(ResultData resultData) {
        this.resultData = resultData;
        finished = true;
    }

    /**
     * Gets the actual result data to check.
     *
     * @return the actual result data to check.
     */
    public ResultData getResultData() {
        return this.resultData;
    }

    /**
     * Checks whether the listerner has been notified.
     *
     * @return whether the listerner has been notified.
     */
    public boolean isFinished() {
        return this.finished;
    }
}
