/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.entities;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * This provides an enumeration of result codes denoting the result of a handler
 * operation. These could denote why a handler operation failed, for eg, an
 * operation could fail due to exception or missing request parameters or if
 * certain validation fails such as a sponsor not belonging to specified domain,
 * etc. These are used by the HandlerResult class which can be used by the
 * component user to generate different response (eg: displaying a different
 * error message) for different failure reasons.<br/> Thread-Safety: This class
 * is immutable and hence thread-safe.
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public class ResultCode extends Enum {

    /**
     * denotes a failure due to missing request parameter values.
     *
     */
    public static final ResultCode MISSING_PARAMETERS = new ResultCode();

    /**
     * denotes a failure due to request parameter value not being parseable into
     * long value.
     *
     */
    public static final ResultCode PARAMETER_NOT_LONG = new ResultCode();

    /**
     * denotes a failure due to request parameter value not being parseable into
     * integer value.
     *
     */
    public static final ResultCode PARAMETER_NOT_INTEGER = new ResultCode();

    /**
     * denotes a failure due to request parameter value not being parseable into
     * date value.
     *
     */
    public static final ResultCode PARAMETER_NOT_DATE = new ResultCode();

    /**
     * denotes a failure due to an exception when performing an operation.
     *
     */
    public static final ResultCode EXCEPTION_OCCURRED = new ResultCode();

    /**
     * denotes a failure due sponsor,domain,etc. already being approved or
     * rejected.
     *
     */
    public static final ResultCode APPROVAL_NOT_PENDING = new ResultCode();

    /**
     * denotes a failure due to sponsor not belonging to domain.
     *
     */
    public static final ResultCode SPONSOR_NOT_BELONG_TO_DOMAIN = new ResultCode();

    /**
     * denotes a failure due to image not belonging to domain.
     *
     */
    public static final ResultCode IMAGE_NOT_BELONG_TO_DOMAIN = new ResultCode();

    /**
     * denotes a failure due to a user not being the first pending winner for a
     * specified game.
     *
     */
    public static final ResultCode WINNER_NOT_FIRST = new ResultCode();

    /**
     * denotes a failure due to a slot already finished hosting.
     *
     */
    public static final ResultCode SLOT_FINISHED_HOSTING = new ResultCode();

    /**
     * denotes a failure due to a slot already started hosting.
     *
     */
    public static final ResultCode SLOT_STARTED_HOSTING = new ResultCode();

    /**
     * denotes a failure due to being unable to move a hosting slot beyond the
     * last position in its block.
     *
     */
    public static final ResultCode CANNOT_MOVE_SLOT_BEYOND_LAST = new ResultCode();

    /**
     * denotes a failure due to no matching ball color.
     *
     */
    public static final ResultCode NO_MATCHING_BALLCOLOR = new ResultCode();

    /**
     * denotes a failure due to block info length not match the configed value.
     *
     */
    public static final ResultCode BLOCK_INFO_LENGTH_NOT_MATCH = new ResultCode();

    /**
     * denotes a failure due to block info length not matching pending winner.
     *
     */
    public static final ResultCode NO_MATCHING_PENDING_WINNER = new ResultCode();

    /**
     * do-nothing private constructor to prevent instantiation.
     *
     */
    private ResultCode() {

    }
}
