/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import com.topcoder.util.log.AbstractLog;
import com.topcoder.util.log.Level;

/**
 * This is a mock implementation of <code>Log</code> used in tests. It provides some methods to get
 * the logged content.
 *
 * @author nowind_lee
 * @version 1.0
 */
public class MockLog extends AbstractLog {

    /**
     * The level enabled for this logger.
     */
    private Level level = Level.DEBUG;

    /**
     * To hold the logged message.
     */
    private OutputStream out = new ByteArrayOutputStream();
    /**
     * To wrap the <code>OutputStream</code>.
     */
    private PrintStream ps = new PrintStream(out);

    /**
     * Default constructor.
     */
    public MockLog() {
        super("test");
    }

    /**
     * Check if the specified level has enabled.
     *
     * @param targetLevel
     *            the level to be checked
     * @return if the specified level has been enabled
     * @see com.topcoder.util.log.Log#isEnabled(com.topcoder.util.log.Level)
     */
    public boolean isEnabled(Level targetLevel) {
        return this.level == null
            || (this.level.intValue() > 0 && targetLevel.intValue() >= this.level.intValue());
    }

    /**
     * Log the exception and message.
     *
     * @param targetLevel
     *            at what level
     * @param cause
     *            the exception
     * @param message
     *            the message
     * @see com.topcoder.util.log.AbstractLog#log(com.topcoder.util.log.Level, java.lang.Throwable,
     *      java.lang.String)
     */
    @Override
    protected void log(Level targetLevel, Throwable cause, String message) {
        ps.print(message + "\n");
        System.out.print(message + "\n");
        if (cause != null) {
            cause.printStackTrace(ps);
            cause.printStackTrace();
        }
    }

    /**
     * Enable the level for this logger.
     *
     * @param targetLevel
     *            the target level to be enabled
     */
    public void enableLevel(Level targetLevel) {
        this.level = targetLevel;
    }

    /**
     * Get the logged messages.
     *
     * @return the logged messages
     */
    public String getLogMessages() {
        return this.out.toString();
    }

    /**
     * Get the first line of logged message.
     *
     * @return the first line of logged message
     */
    public String getFirstLine() {
        String message = this.getLogMessages();
        return message.substring(0, message.indexOf('\n'));
    }
}
