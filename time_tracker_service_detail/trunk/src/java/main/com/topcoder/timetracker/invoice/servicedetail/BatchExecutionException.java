/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.invoice.servicedetail;

/**
 * <p>
 * This exception should be thrown if batch execution failed. Arrays of ids stored in this class allows to get
 * concrete ids of details for each batch operation fails
 * </p>
 * <p>
 * Class is thread safe because it is immutable
 * </p>
 *
 * @author tushak, enefem21
 * @version 1.0
 */
public class BatchExecutionException extends DataAccessException {

    /** Serial version UID. */
    private static final long serialVersionUID = -5800862156491199287L;

    /**
     * <p>
     * This attribute represents ids of detail. This is immutable, sets by constructor. It is accessible by
     * corresponding getter method.
     * </p>
     */
    private final long[] ids;

    /**
     * <p>
     * Constructor which create exception with given detail ids.
     * </p>
     *
     * @param ids
     *            array of ids
     */
    public BatchExecutionException(long[] ids) {
        super("These ids cause error: " + arrayToString(ids));
        this.ids = ids;
    }

    /**
     * <p>
     * Constructor which create exception with given detail ids and error message.
     * </p>
     *
     * @param ids
     *            array of ids
     * @param cause
     *            An exception representing the cause of this exception.
     */
    public BatchExecutionException(long[] ids, Throwable cause) {
        super("These ids cause error: " + arrayToString(ids), cause);
        this.ids = ids;
    }

    /**
     * <p>
     * This method is used to get entity ids.
     * </p>
     *
     * @return the array of ids
     */
    public long[] getIds() {
        return this.ids;
    }

    /**
     * Converting array of ids to String representation.
     *
     * @param ids
     *            array of ids
     *
     * @return String representation
     */
    private static String arrayToString(long[] ids) {
        StringBuffer a = new StringBuffer("[");
        for (int i = 0; i < ids.length; i++) {
            a.append(ids[i]);
            if (i != ids.length - 1) {
                a.append(",");
            }
        }
        a.append("]");
        return a.toString();
    }
}
