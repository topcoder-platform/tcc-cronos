/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time;

/**
 * <p>
 * This is a simple notification interface, which would be implemented by the caller of the asynchronous batch process.
 * When the process is done, it will notify the caller via this interface and will deposit all the necessary result
 * information as part of the notification (using the <code>ResultData</code> instance).
 * </p>
 *
 * @author AleaActaEst, TCSDEVELOPER
 * @version 1.1
 */
public interface ResultListener {
    /**
     * <p>
     * Called by the asynchronous observable process (batch process for us). Implementation classes will write their
     * code here for processing the ResultData.
     * </p>
     *
     * @param resultData result data to process
     */
    void notify(ResultData resultData);
}
