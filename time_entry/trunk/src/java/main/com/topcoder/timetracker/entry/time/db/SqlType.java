/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

/**
 * <p>
 * This is a data class and contains a sql type.
 * </p>
 *
 * <p>
 * This class is used to represents a <code>NULL</code> column value when filling
 * a <code>PreparedStatement</code> instance.
 * </p>
 *
 * <p>
 * Thread Safety : This class is immutable and so is thread safe.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
final class SqlType {
    /**
     * <p>
     * Represents the sql type for a column.
     * </p>
     */
    private int type;

    /**
     * <p>
     * Constructs a <code>SqlType</code> with the sql type given.
     * </p>
     *
     * @param type the sql type
     */
    SqlType(int type) {
        this.type = type;
    }

    /**
     * <p>
     * Gets the sql type for a column.
     * </p>
     *
     * @return the sql type for a column.
     */
    int getType() {
        return type;
    }
}
