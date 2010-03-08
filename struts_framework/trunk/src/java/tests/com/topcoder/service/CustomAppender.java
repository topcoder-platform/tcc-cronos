/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <p>
 * The custom log appender used in the unit tests to validate log messages.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CustomAppender extends AppenderSkeleton {

    /**
     * The list of logged messages (includes stack trace messages also).
     */
    private static List<String> messages = new ArrayList<String>();

    /**
     * Appends the message information to messages.
     *
     * @param event the logging event containing the message information
     */
    protected void append(LoggingEvent event) {
        // add the log message
        messages.add(event.getRenderedMessage());

        // add the stack trace messages
        for (String str : event.getThrowableStrRep()) {
            messages.add(str);
        }
    }

    /**
     * The close method for the appender.
     */
    public void close() {
        // do nothing
    }

    /**
     * Returns whether the appender requires a layout.
     *
     * @return whether the appender requires a layout
     */
    public boolean requiresLayout() {
        return false;
    }

    /**
     * Clears the stored messages for the appender.
     */
    public static void clear() {
        messages.clear();
    }

    /**
     * Gets all the logged messages for the appender.
     *
     * @return the logged messages for the appender
     */
    public static List<String> getMessages() {
        return messages;
    }

    /**
     * Returns whether the given argument is contained within the appender's messages.
     *
     * @param msg the message to check
     * @return whether the given argument is contained within the appender's messages.
     */
    public static boolean containsMessage(String msg) {
        // see if the message exists in the logged messages
        for (String logMsg : messages) {
            if (logMsg.contains(msg)) {
                // found the message
                return true;
            }
        }

        // message wasn't found
        return false;
    }

}
