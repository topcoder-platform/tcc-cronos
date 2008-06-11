/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import com.topcoder.util.log.AbstractLog;
import com.topcoder.util.log.Level;

/**
 * The log for test.
 *
 * @author telly12
 * @version 1.0
 */
public class MyLog extends AbstractLog {

    /**
     * <p>
     * The constructor.
     * </p>
     *
     * @param name
     *            the log name
     */
    public MyLog(String name) {
        super(name);
    }

    /**
     * <p>
     * This is the abstract method that will be called from the other log methods when the level is enabled and a
     * message should be logged.
     * </p>
     * <p>
     * Subclasses will need to implement this method to log the message to the underlying logging system.
     * </p>
     *
     * @param level
     *            A possibly null level at which the message should be logged
     * @param cause
     *            A possibly null throwable describing an error that occurred
     * @param message
     *            A possibly null, possibly empty (trim'd) string representing the logging message
     */
    protected void log(Level level, Throwable cause, String message) {
        // do nothing
        // System.out.println(message);
//        if (cause != null) {
//            cause.printStackTrace();
//        }
    }

    /**
     * <p>
     * Returns whether the given level is enabled for a specific implementation.
     * </p>
     * <p>
     * A null level should simply return false.
     * </p>
     *
     * @param level
     *            A possibly null level to check
     * @return true always
     */
    public boolean isEnabled(Level level) {
        return true;
    }
}
