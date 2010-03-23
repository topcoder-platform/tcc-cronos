/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

/**
 * <p>
 * This exception is the base exception for all exceptions raised from operations from LiquidPortalService.
 * </p>
 * <p>
 * <pre>
 * user is null                                                                     2000
 * user.firstName is null/empty                                                     2001
 * user.lastName is null/empty                                                      2002
 * user.emailAddress is null/empty                                                  2003
 * user.password is null/empty                                                      2004
 * user.handle is null/empty                                                        2005
 * user.phone is null/empty                                                         2006
 * requestorHandle is null/empty                                                    3000
 * userHandle is null/empty                                                         3001
 * cockpitProjectNames is null/empty                                                3002
 * cockpitProjectNames contains null/empty entries                                  3003
 * billingProjectIds is null/empty                                                  3004
 * competitionData is null                                                          4000
 * supportHandles is null                                                           3005
 * supportHandles contains null/empty elements                                      3006
 * contestTypeName is null/empty                                                    4001
 * subContestTypeName is null/empty and contestTypeName = "studio"                  4002
 * contestName is null/empty                                                        4003
 * cockpitProjectName is null/empty                                                 4004
 * requestedStartDate is null                                                       4005
 * requestedStartDate in the past                                                   4006
 * contestTypeName is not in projectCategories and contestTypeName != "studio"      4007
 * subContestTypeName is not in studioContestTypes and contestTypeName = "studio"   4008
 * handles is null/empty                                                            3007
 * handles contains null/empty elements                                             3008
 * reason is empty                                                                  3009
 * termsAgreedDate is null                                                          2007
 * termsAgreedDate in the future                                                    2008
 * </pre>
 * </p> 
 * <p>
 * <b>Thread Safety:</b> This exception is not thread safe because parent exception is not thread safe. The
 * application should handle this exception in a thread-safe manner.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LiquidPortalServiceException extends Exception {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -543148596996024920L;

    /**
     * <p>
     * Error code for user is null.
     * </p>
     */
    public static final int EC_USER_NULL = 2000;

    /**
     * <p>
     * Error code for user.firstName is null/empty.
     * </p>
     */
    public static final int EC_USER_FIRSTNAME_NULL_EMPTY = 2001;

    /**
     * <p>
     * Error code for user.lastName is null/empty.
     * </p>
     */
    public static final int EC_USER_LASTNAME_NULL_EMPTY = 2002;

    /**
     * <p>
     * Error code for user.emailAddress is null/empty.
     * </p>
     */
    public static final int EC_USER_EMAIL_NULL_EMPTY = 2003;

    /**
     * <p>
     * Error code for user.password is null/empty.
     * </p>
     */
    public static final int EC_USER_PASSWORD_NULL_EMPTY = 2004;

    /**
     * <p>
     * Error code for user.handle is null/empty.
     * </p>
     */
    public static final int EC_USER_HANDLE_NULL_EMPTY = 2005;

    /**
     * <p>
     * Error code for user.phone is null/empty.
     * </p>
     */
    public static final int EC_USER_PHONE_NULL_EMPTY = 2006;

    /**
     * <p>
     * Error code for requestorHandle is null/empty.
     * </p>
     */
    public static final int EC_REQUESTORHANDLE_NULL_EMPTY = 3000;

    /**
     * <p>
     * Error code for userHandle is null/empty.
     * </p>
     */
    public static final int EC_USERHANDLE_NULL_EMPTY = 3001;

    /**
     * <p>
     * Error code for cockpitProjectNames is null/empty.
     * </p>
     */
    public static final int EC_COCKPITPROJECTNAMES_NULL_EMPTY = 3002;

    /**
     * <p>
     * Error code for cockpitProjectNames contains null/empty entries.
     */
    public static final int EC_COCKPITPROJECTNAMES_CONTAINS_NULL_EMPTY = 3003;

    /**
     * <p>
     * Error code for billingProjectIds is null/empty.
     * </p>
     */
    public static final int EC_BILLINGPROJECTIDS_NULL_EMPTY = 3004;

    /**
     * <p>
     * Error code for competitionData is null.
     * </p>
     */
    public static final int EC_COMPETITIONDATA_NULL = 4000;

    /**
     * <p>
     * Error code for supportHandles is null.
     * </p>
     */
    public static final int EC_SUPPORTHANDLES_NULL = 3005;

    /**
     * <p>
     * Error code for supportHandles contains null/empty elements.
     * </p>
     */
    public static final int EC_SUPPORTHANDLES_CONTAINS_NULL_EMPTY = 3006;

    /**
     * <p>
     * Error code for contestTypeName is null/empty.
     * </p>
     */
    public static final int EC_CONTESTTYPENAME_NULL_EMPTY = 4001;

    /**
     * <p>
     * Error code for subContestTypeName is null/empty and contestTypeName = "studio".
     * </p>
     */
    public static final int EC_SUBCONTESTTYPENAME_NULL_EMPTY = 4002;

    /**
     * <p>
     * Error code for contestName is null/empty.
     * </p>
     */
    public static final int EC_CONTESTNAME_NULL_EMPTY = 4003;

    /**
     * <p>
     * Error code for cockpitProjectName is null/empty.
     * </p>
     */
    public static final int EC_COCKPITPROJECTNAME_NULL_EMPTY = 4004;

    /**
     * <p>
     * Error code for requestedStartDate is null.
     * </p>
     */
    public static final int EC_REQUESTEDSTARTDATE_NULL = 4005;

    /**
     * <p>
     * Error code for requestedStartDate in the past.
     * </p>
     */
    public static final int EC_REQUESTEDSTARTDATE_IN_PAST = 4006;

    /**
     * <p>
     * Error code for contestTypeName is not in projectCategories and contestTypeName != "studio".
     * </p>
     */
    public static final int EC_INVALID_CONTESTTYPENAME = 4007;

    /**
     * <p>
     * Error code for subContestTypeName is not in studioContestTypes and contestTypeName = "studio".
     * </p>
     */
    public static final int EC_INVALID_SUBCONTESTTYPENAME = 4008;

    /**
     * <p>
     * Error code for handles is null/empty.
     * </p>
     */
    public static final int EC_HANDLES_NULL_EMPTY = 3007;

    /**
     * <p>
     * Error code for handles contains null/empty elements.
     * </p>
     */
    public static final int EC_HANDLES_CONTAINS_NULL_EMPTY = 3008;

    /**
     * <p>
     * Error code for reason is empty.
     * </p>
     */
    public static final int EC_REASON_EMPTY = 3009;

    /**
     * <p>
     * Error code for termsAgreedDate is null.
     * </p>
     */
    public static final int EC_TERMSAGREEDDATE_NULL = 2007;

    /**
     * <p>
     * Error code for termsAgreedDate in the future.
     * </p>
     */
    public static final int EC_TERMSAGREEDDATE_IN_FUTURE = 2008;    

    private int errorCode;

    /**
     * <p>
     * Constructs a new 'LiquidPortalServiceException' instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public LiquidPortalServiceException(String message) {
        super(message);
    }

    /**
     * <p>
     * Constructs a new 'LiquidPortalServiceException' instance with the given message.
     * </p>
     *
     * @param message
     *            the exception message
     */
    public LiquidPortalServiceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public LiquidPortalServiceException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }    

    /**
     * <p>
     * Constructs a new 'LiquidPortalServiceException' exception with the given message and cause.
     * </p>
     *
     * @param message
     *            the exception message
     * @param cause
     *            the exception cause
     */
    public LiquidPortalServiceException(String message, Throwable cause) {
        super(message, cause);
    }


     /**
     * <p>
     * Creates a new exception instance with no error message and the given
     * cause of error.
     * </p>
     *
     * @param cause
     *            cause of error
     */
    public LiquidPortalServiceException(Throwable cause) {
        super(cause);
    }

    public LiquidPortalServiceException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }


    public LiquidPortalServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    
    public int getErrorCode()
    {
        return this.errorCode;
    }
}
