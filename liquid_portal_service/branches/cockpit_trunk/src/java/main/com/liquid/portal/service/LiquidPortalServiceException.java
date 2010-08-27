/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service;

import java.util.Map;
import java.util.HashMap;

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
 * user.status is not Active                                                        2006
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
     * Error code for user.status is not active.
     * </p>
     */
    public static final int EC_USER_STATUS_NOT_ACTIVE = 2006;

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
    
    public static final int EC_HANDLE_TOO_SHORT_OR_TOO_LONG = 2010;
    public static final int EC_PASSWORD_TOO_SHORT_OR_TOO_LONG = 2011;
    public static final int EC_HANDLE_CONTAINS_SPECIAL_CHAR = 2012;
    public static final int EC_PASSWORD_CONTAINS_SPECIAL_CHAR = 2013;

    public static final int EC_HANDLE_NOT_UNIQUE = 2009;   
    

    public static final int EC_CANNOT_ADD_USER_TO_GROUPS = 5000;   

    public static final int EC_CANNOT_ADD_USER_TO_TERMS = 5001;   

    public static final int EC_USER_INFO_DOESNOT_MATCH_WITH_PERSISTENCE = 5002;  
    
    public static final int EC_INVALID_REQUESTOR_HANDLE = 5004;  

    public static final int EC_INVALID_USER_HANDLES = 5011;  

    public static final int EC_INVALID_COCKPIT_PROJECT = 5006;  

    public static final int EC_INVALID_BILLING_PROJECT = 5007;  

    public static final int EC_ACTION_NOT_PERMITTED = 5003; 

    public static final int EC_PROJECT_DOESNOT_EXIST = 5008; 

    public static final int EC_START_DATE_NOT_AVAILABLE = 5009; 

    public static final int EC_FAIL_TO_ASSIGN_PERMISSION = 5013; 

    public static final int EC_HANDLE_DOESNOT_EXIST = 5014;
    
    public static final int EC_CANNOT_ADD_USER_TO_JIRA = 5015;   


    private int errorCode;

    private static Map<Integer, String> errorMessages = new HashMap<Integer, String>();

    static {

        errorMessages.put(EC_USER_NULL, "User is null.");
        errorMessages.put(EC_USER_FIRSTNAME_NULL_EMPTY, "First Name is null or emtpy.");
        errorMessages.put(EC_USER_LASTNAME_NULL_EMPTY, "Last Name is null or emtpy.");
        errorMessages.put(EC_USER_EMAIL_NULL_EMPTY, "User email is null or empty.");
        errorMessages.put(EC_USER_PASSWORD_NULL_EMPTY, "Password is null or emtpy.");
        errorMessages.put(EC_USER_HANDLE_NULL_EMPTY, "User.Handle is null or emtpy.");
        errorMessages.put(EC_REQUESTORHANDLE_NULL_EMPTY, "Requestor Handle is null or empty.");
        errorMessages.put(EC_USERHANDLE_NULL_EMPTY, "User Handle is null or emtpy.");
        errorMessages.put(EC_COCKPITPROJECTNAMES_NULL_EMPTY, "Cockpit Project Names is null or emtpy.");
        errorMessages.put(EC_BILLINGPROJECTIDS_NULL_EMPTY, "Billing Projects is null or empty.");
        errorMessages.put(EC_COMPETITIONDATA_NULL, "Competition Data is null or emtpy.");
        errorMessages.put(EC_SUPPORTHANDLES_NULL, "Support Hanldes is null or emtpy.");
        errorMessages.put(EC_CONTESTTYPENAME_NULL_EMPTY, "Contest Type Name is null.");
        errorMessages.put(EC_CONTESTTYPENAME_NULL_EMPTY, "Support Hanldes contains null or emtpy element.");
        errorMessages.put(EC_CONTESTNAME_NULL_EMPTY, "Contest Name is null or emtpy.");
        errorMessages.put(EC_COCKPITPROJECTNAME_NULL_EMPTY, "Cockpit Project Name is null or empty.");
        errorMessages.put(EC_REQUESTEDSTARTDATE_IN_PAST, "Request Start Date is null or emtpy.");
        errorMessages.put(EC_INVALID_CONTESTTYPENAME, "Invalid Contest Type Name.");
        errorMessages.put(EC_INVALID_SUBCONTESTTYPENAME, "Invalid Studio SubContest Type Name.");
        errorMessages.put(EC_HANDLES_NULL_EMPTY, "Handles is null or empty.");
        errorMessages.put(EC_HANDLES_CONTAINS_NULL_EMPTY, "Hanldes contains null or emtpy element.");
        errorMessages.put(EC_REASON_EMPTY, "Reason is null or emtpy.");
        errorMessages.put(EC_TERMSAGREEDDATE_NULL, "Terms Agreed Date is null or empty.");
        errorMessages.put(EC_TERMSAGREEDDATE_IN_FUTURE, "Terms Agreed Date is in the future.");
        errorMessages.put(EC_HANDLE_TOO_SHORT_OR_TOO_LONG, "Handle length must be less than or equal to 15.");
        errorMessages.put(EC_PASSWORD_TOO_SHORT_OR_TOO_LONG, "Password length must be between 7 and 30.");
        errorMessages.put(EC_HANDLE_CONTAINS_SPECIAL_CHAR, "Handle contains special characters.");
        errorMessages.put(EC_PASSWORD_CONTAINS_SPECIAL_CHAR, "Password contains special characters.");
        errorMessages.put(EC_CANNOT_ADD_USER_TO_GROUPS, "Can not add user to notusEligibilityGroup.");
        errorMessages.put(EC_CANNOT_ADD_USER_TO_TERMS, "Can not add user to terms group.");
        errorMessages.put(EC_HANDLE_NOT_UNIQUE, "Handle is not unique.");
        errorMessages.put(EC_USER_INFO_DOESNOT_MATCH_WITH_PERSISTENCE, "User info doesn't math with persisted user info.");
        errorMessages.put(EC_INVALID_REQUESTOR_HANDLE, "Requestor handle is not in Notus Eligibility Groups.");
        errorMessages.put(EC_INVALID_USER_HANDLES, "User Handle is not in Notus Eligibility Groups.");
        errorMessages.put(EC_INVALID_COCKPIT_PROJECT, "Invalid Cockpit Project.");
        errorMessages.put(EC_INVALID_BILLING_PROJECT, "Invalid Billing Project.");
        errorMessages.put(EC_ACTION_NOT_PERMITTED, "Action is not permitted.");
        errorMessages.put(EC_PROJECT_DOESNOT_EXIST, "Project does not exist.");
        errorMessages.put(EC_START_DATE_NOT_AVAILABLE, "Start date is not available.");
        errorMessages.put(EC_FAIL_TO_ASSIGN_PERMISSION, "Fail to assign permission.");
        errorMessages.put(EC_HANDLE_DOESNOT_EXIST, "Handle doset not exist.");
        errorMessages.put(EC_CANNOT_ADD_USER_TO_JIRA, "Can not add user to JIRA.");
        errorMessages.put(EC_USER_STATUS_NOT_ACTIVE, "User's TopCoder Account has not been activated.  User needs to click the activation link from the registration confirmation email or request a new activation link from support@topcoder.com");

        

    }

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

    @Override public String getMessage()
    {

        if (super.getMessage() != null)
        {
            return super.getMessage();
        }

        return getMessage(this.errorCode);
    }

    
    public int getErrorCode()
    {
        return this.errorCode;
    }

    public String getMessage(int errorCode)
    {
        String msg = errorMessages.get(errorCode);

        if (msg != null)
        {
            return msg;
        }

        return "Liquid Portal Service Error.";
    }
}
