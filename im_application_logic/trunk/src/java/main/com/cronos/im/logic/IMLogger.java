/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.logic;

import com.topcoder.util.log.Level;
import com.topcoder.util.log.LogFactory;
import com.topcoder.util.log.Log;
import java.text.DateFormat;
import java.util.Date;

/**
 * <p>
 * This class provides logging functionalities for this component. All logging in this component should be
 * done through this class.
 * </p>
 * <p>
 * This class can be considered to be the utility class for logging function of this component. Although
 * logging wrapper already contains the utility method to log the message, in order to simplify and unify the
 * logging in this component, this class wraps the logger and date formatter, and provides some log methods to
 * log messages. Of course, this class still uses the logging functionalities that is provided in the logging
 * wrapper component.
 * </p>
 * <p>
 * Thread-safety: this class is immutable and thread-safe. Although the methods of DateFormat are not
 * thread-safe, but in this class, new instance of DateFormat will be cloned in the constructor and its state
 * is never changed after that, so it is thread-safe.
 * </p>
 * 
 * 
 * @author justforplay, TCSDEVELOPER
 * @version 1.0
 */
public class IMLogger {

    /**
     * <p>
     * Represents instance of Log used to log the messages for this component.
     * </p>
     */
    private final Log logger;

    /**
     * <p>
     * Represents the date formatter used to format logging date. If it is null, then the toString() method of
     * the date will be used.
     * </p>
     */
    private final DateFormat dateFormat;

    /**
     * <p>
     * Constructor to initialize this class using the default log name and the specified date formatter. When
     * dateFormat is null, toString() method of the date will be used.
     * </p>
     * 
     * @param dateFormat
     *            date formatter used to format the date value in the logging message. It can be null.
     */
    public IMLogger(DateFormat dateFormat) {
        this(null, dateFormat);
    }

    /**
     * <p>
     * Constructor to initialize this class using the specified log name and specified date formatter. When
     * dateFormat is null, toString() method of the date will be used.
     * </p>
     * 
     * @param logName
     *            log name used to create Log instance. It can be null or any String value.
     * @param dateFormat
     *            date formatter used to format the date value in the logging message. It can be null.
     */
    public IMLogger(String logName, DateFormat dateFormat) {
        logger = LogFactory.getLog(logName);
        this.dateFormat = dateFormat == null ? null : (DateFormat) dateFormat.clone();
    }

    /**
     * <p>
     * Log the action and related entities with given level.
     * </p>
     * <p>
     * Note: Any exceptions except IllegalArgumentException are ignored in this method.
     * </p>
     * 
     * 
     * @param level
     *            level to log. Must not be null.
     * @param action
     *            action taken to log. Must not be null or empty
     * @param entities
     *            entities affected. The string array contains element with entity type and entity id,
     *            separated with "-". For example {"Session - 10", "User - 20"}. Null means that no entities
     *            are affected, null or empty string elements are ignored.
     * @exception IllegalArgumentException
     *                if the level is null, or action is null or empty.
     */
    public void log(Level level, String action, String[] entities) {
        IMHelper.checkNull(level, "level");
        IMHelper.checkString(action, "action");

        // create logging message
        Date currentDate = new Date();
        String currentDateStr = dateFormat == null ? currentDate.toString() : dateFormat.format(currentDate);
        StringBuffer msg = new StringBuffer();
        msg.append("Action: ");
        msg.append(action);
        msg.append("; Time: ");
        msg.append(currentDateStr);
        msg.append("; Entities affected: ");
        if (entities == null || entities.length == 0) {
            msg.append("None");
        } else {
            boolean first = true;
            for (int i = 0; i < entities.length; i++) {
                if (entities[i] != null && entities[i].trim().length() > 0) {
                    if (!first) {
                        msg.append(", ");
                    }
                    msg.append(entities[i]);
                    first = false;
                }
            }
        }
        // do logging with logging wrapper component
        logger.log(level, msg.toString());
    }

    /**
     * <p>
     * Log the given message with given level.
     * </p>
     * <p>
     * Note: Any exceptions except the IllegalArgumentException are ignored in this method.
     * </p>
     * 
     * 
     * @param level
     *            level to log. Must not be null.
     * @param message
     *            message to log. Must not be null or empty.
     * @throws IllegalArgumentException
     *             if the level is null or the message is null or empty.
     */
    public void log(Level level, String message) {
        IMHelper.checkNull(level, "level");
        IMHelper.checkString(message, "message");
        logger.log(level, message);
    }

    /**
     * <p>
     * Log the action and related user and session ids with given level.
     * </p>
     * <p>
     * Note: Any exceptions except IllegalArgumentException are ignored in this method.
     * </p>
     * 
     * 
     * @param level
     *            level to log. Must not be null.
     * @param action
     *            action taken to log. Must not be null or empty
     * @param userIds
     *            affected user ids. Can be null or empty.
     * @param sessionIds
     *            affected session ids. Can be null or empty
     * @exception IllegalArgumentException
     *                if the level is null, or action is null or empty.
     */
    public void log(Level level, String action, long[] userIds, long[] sessionIds) {
        IMHelper.checkNull(level, "level");
        IMHelper.checkString(action, "action");

        if (userIds == null) {
            userIds = new long[0];
        }
        if (sessionIds == null) {
            sessionIds = new long[0];
        }

        // create entries from userIds and sessionIds
        String[] entities = new String[userIds.length + sessionIds.length];
        int pt = 0;
        for (int i = 0; i < userIds.length; i++) {
            entities[pt++] = "User - " + userIds[i];
        }
        for (int j = 0; j < sessionIds.length; j++) {
            entities[pt++] = "Session - " + sessionIds[j];
        }

        // delegate to another log method
        log(level, action, entities);
    }
}
