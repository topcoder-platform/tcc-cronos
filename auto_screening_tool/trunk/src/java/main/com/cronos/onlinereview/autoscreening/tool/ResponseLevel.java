/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is a type-safe enum that is used to represent the Level of a response
 * according to the results of applying a screening rule.
 * </p>
 * <p>
 * It is possible to retrieve the different constants via their severity (static
 * Enum.getEnumByStringValue)
 * </p>
 * <p>
 * Thread Safety: This class is immutable and therefore thread-safe.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class ResponseLevel extends Enum {

    /**
     * <p>
     * A response severity that indicates a status of "passing".
     * </p>
     */
    public static final ResponseLevel PASS = new ResponseLevel("pass");

    /**
     * <p>
     * A response severity that indicates a status of "warning".
     * </p>
     */
    public static final ResponseLevel WARN = new ResponseLevel("warn");

    /**
     * <p>
     * A response severity that indicates a status of "failing".
     * </p>
     */
    public static final ResponseLevel FAIL = new ResponseLevel("fail");

    /**
     * <p>
     * This is the Level value. It is also used as the String representation of
     * the ResponseSeverity.
     * </p>
     */
    private final String level;

    /**
     * <p>
     * Private constructor to disallow external invocation.
     * </p>
     * @param level
     *            The Level of the response.
     * @throws IllegalArgumentException
     *             if level is null or an empty String.
     */
    private ResponseLevel(String level) {
        if (level == null) {
            throw new IllegalArgumentException("level should not be null.");
        }
        if (level.trim().length() == 0) {
            throw new IllegalArgumentException("level should not be empty (trimmed).");
        }

        this.level = level;
    }

    /**
     * <p>
     * Returns the String representation of the ResponseLevel, which is equal to
     * the level class variable.
     * </p>
     * @return the String representation of the ResponseLevel
     */
    public String toString() {
        return level;
    }
}
