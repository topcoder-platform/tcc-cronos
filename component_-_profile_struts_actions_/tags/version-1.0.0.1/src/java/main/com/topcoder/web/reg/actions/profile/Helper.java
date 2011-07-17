/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.common.model.AuditRecord;
import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionException;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;

/**
 * <p>
 * This class contains helper methods and constants.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class Helper {

    /**
     * <p>
     * Represents field validation error constant message.
     * </p>
     */
    static final String FIELD_VALIDATION_ERROR_MESSAGE = "Error occurred while making field validation : ";

    /**
     * <p>
     * Represents validation field error message prefix constant used for logging.
     * </p>
     */
    private static final String VALIDATION_FIELD_ERROR_MESSAGE_PREFIX = "Validation error occurs in method ";

    /**
     * <p>
     * Prevents from instantiating.
     * </p>
     */
    private Helper() {
    }

    /**
     * <p>
     * Appends name-value pair to given audit builder.
     * </p>
     * @param auditBuilder the string builder to append
     * @param name the parameter name
     * @param value the parameter value
     */
    private static void addAuditingData(StringBuilder auditBuilder, String name, String value) {
        if (auditBuilder.length() > 0) {
            auditBuilder.append(", ");
        }
        auditBuilder.append(name).append(":").append(value);
    }

    /**
     * <p>
     * Adds field error to given action if given condition is true.
     * </p>
     * @param action the action to add field error
     * @param condition the flag whether to add field error or not
     * @param fieldName the field name
     * @param errorKey the error key in resource bundle
     * @param methodSignature the method signature for logging
     */
    static void addFieldError(BaseProfileAction action, boolean condition, String fieldName, String errorKey,
            String methodSignature) {
        if (condition) {
            String errorMessage = action.getText(errorKey);
            action.addFieldError(fieldName, errorMessage);
            action.getLogger().log(Level.ERROR,
                    VALIDATION_FIELD_ERROR_MESSAGE_PREFIX + methodSignature + ":" + errorMessage);
        }
    }

    /**
     * <p>
     * Creates tag for given name and value.
     * </p>
     * @param name the tag name
     * @param value the tag value
     * @param escapeValue flag whether we should escape value or not
     * @return created tag for given name and value
     */
    static String createTag(String name, Object value, boolean escapeValue) {
        StringBuilder builder = new StringBuilder();
        builder.append("<").append(name).append(">");
        String tagValue = String.valueOf(value);
        // escape value if needed
        if (escapeValue) {
            tagValue = processStr(tagValue);
        }
        builder.append(tagValue);
        builder.append("</").append(name).append(">");
        return builder.toString();
    }

    /**
     * <p>
     * Retrieves completeness report for given action and user.
     * </p>
     * @param action the action to get report
     * @param user the user to get report
     * @param methodSignature the method signature for logging
     * @throws ProfileActionException if any error occurs in underlying ProfileCompletenessRetriever
     */
    static void getCompletenessReport(BaseProfileAction action, User user, String methodSignature)
        throws ProfileActionException {
        try {
            action.setProfileCompletenessReport(action.getProfileCompletenessRetriever().getProfileCompletenessReport(
                    user));
        } catch (ProfileCompletenessRetrievalException e) {
            throw Helper.logAndWrapException(action.getLogger(), methodSignature,
                    "Error occurred in underlying profile completeness retriever.", e);
        }
    }

    /**
     * <p>
     * Creates email body template data that will be shared by all actions.
     * </p>
     * @param user the user to get parameters
     * @return created email body template data that will be shared by all actions
     */
    static String getPreparedEmailTemplate(User user) {
        StringBuilder builder = new StringBuilder();
        builder.append(createTag("ID", user.getId(), true));
        builder.append(createTag("HANDLE", user.getHandle(), true));
        return builder.toString();
    }

    /**
     * <p>
     * Logs and wraps given exception.
     * </p>
     * @param log the logger for logging
     * @param methodSignature the method signature for logging
     * @param errorMessage the error message
     * @param cause the error cause
     * @return wrapped exception by ProfileActionException instance
     */
    static ProfileActionException logAndWrapException(Log log, String methodSignature, String errorMessage,
            Throwable cause) {
        Throwable exceptionToLog = cause;
        ProfileActionException result;
        if (cause == null) {
            // cause is null, log wrapped exception
            result = new ProfileActionException(errorMessage);
            exceptionToLog = result;
        } else {
            // cause is not null, log original exception
            result = new ProfileActionException(errorMessage, cause);
        }
        LoggingWrapperUtility.logException(log, methodSignature, exceptionToLog);
        return result;
    }

    /**
     * <p>
     * Makes record audit.
     * </p>
     * @param action the action to make audit
     * @param previousValues the previous values, can be null
     * @param newValues the new values, can be null
     * @throws ProfileActionException if any error occurs
     */
    static void makeAudit(BaseProfileAction action, StringBuilder previousValues, StringBuilder newValues)
        throws ProfileActionException {
        AuditRecord record = action.getPreparedAuditRecord();
        if (previousValues != null && previousValues.length() > 0) {
            record.setNewValue(newValues.toString());
            record.setPreviousValue(previousValues.toString());
        }
        action.getAuditDAO().audit(record);
    }

    /**
     * <p>
     * Processes given new and old value. Add audit data if they are not equal.
     * </p>
     * @param previousValues the previous values for audit
     * @param newValues the new values for audit
     * @param previousValue the previous value
     * @param newValue the new value
     * @param dataElementName the data element name
     */
    static void processAuditData(StringBuilder previousValues, StringBuilder newValues, String previousValue,
            String newValue, String dataElementName) {
        if ((previousValue != null && !previousValue.equals(newValue))
                || (newValue != null && !newValue.equals(previousValue))) {
            addAuditingData(previousValues, dataElementName, previousValue);
            addAuditingData(newValues, dataElementName, newValue);
        }
    }

    /**
     * <p>
     * Escapes characters in given string.
     * </p>
     * @param value the string to be escaped
     * @return string with escaped characters
     */
    private static String processStr(String value) {
        return value == null ? value : value.replace("&", "&amp;").replace("'", "&apos;").replace("\"", "&quot;")
                .replace("<", "&lt;").replace(">", "&gt;");
    }

    /**
     * <p>
     * Retrieves logged in user from persistence that is logged in.
     * </p>
     * @param action the action to get logged in user
     * @param methodSignature the method signature for logging
     * @return logged in user
     * @throws ProfileActionException if any error occurs
     */
    static User retrieveLoggedInUser(BaseProfileAction action, String methodSignature) throws ProfileActionException {
        com.topcoder.shared.security.User authenticationUser = retrieveUserFromSession(action, methodSignature);
        return retrieveUserFromDatabase(action, authenticationUser.getId(), methodSignature);
    }

    /**
     * <p>
     * Retrieves user from database via userDAO for given id.
     * </p>
     * @param action the action to get user
     * @param id the user id
     * @param methodSignature the method signature for logging
     * @return retrieved user from database via userDAO
     * @throws ProfileActionException if user is not found or any error occurs
     */
    static User retrieveUserFromDatabase(BaseProfileAction action, Long id, String methodSignature)
        throws ProfileActionException {
        try {
            User user = action.getUserDAO().find(id);
            if (user == null) {
                throw Helper.logAndWrapException(action.getLogger(), methodSignature,
                        "User is not found in database.", null);
            }
            return user;
        } catch (RuntimeException e) {
            throw Helper.logAndWrapException(action.getLogger(), methodSignature,
                    "Error occurred in underlying userDAO.", e);
        }
    }

    /**
     * <p>
     * Retrieves user from session or throws exception if it's not present there.
     * </p>
     * @param action the action to get authentication user
     * @param methodSignature the method signature for logging
     * @throws ProfileActionException if user is not present in session
     * @return retrieved user instance from session
     */
    static com.topcoder.shared.security.User retrieveUserFromSession(BaseProfileAction action, String methodSignature)
        throws ProfileActionException {
        com.topcoder.shared.security.User user = action.getAuthenticationUser();
        if (user == null) {
            throw logAndWrapException(action.getLogger(), methodSignature, "User is not found in session.", null);
        }
        return user;
    }

    /**
     * <p>
     * Checks whether given value is not null or empty.
     * </p>
     * @param value the value to check
     * @return true, if given value is not null nor empty, false otherwise
     */
    static boolean checkNotNullNorEmpty(String value) {
        return value != null && value.trim().length() > 0;
    }
}
