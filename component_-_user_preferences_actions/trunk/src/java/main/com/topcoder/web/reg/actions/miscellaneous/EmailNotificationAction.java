/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.web.common.model.Notification;
import com.topcoder.web.common.model.User;

/**
 * <p>
 * This action extends the BasePreferencesAction, and it allows user to manage the notification preferences.
 * </p>
 * <p>
 * All its instance variables have setters/getters and will be injected.
 * </p>
 * <p>
 * Thread safety: This class is mutable and not thread safe, however will be used thread-safely in Struts framework.
 * </p>
 * @author maksimilian, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class EmailNotificationAction extends BasePreferencesAction {

    /**
     * <p>
     * Represents audit operation type.
     * </p>
     */
    private static final String AUDIT_OPERATION_TYPE = "User Email Notification Update";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "EmailNotificationAction.execute()";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "EmailNotificationAction.validate()";

    /**
     * <p>
     * Represents the set of user notification preferences.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private Set < Notification > notifications;

    /**
     * <p>
     * Creates an instance of EmailNotificationAction.
     * </p>
     */
    public EmailNotificationAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user notification preferences.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     * @throws PreferencesActionDiscardException if error occurs when attempting to discard
     */
    public String execute() throws UserPreferencesActionExecutionException, PreferencesActionDiscardException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        // get the user:
        User user = getLoggedInUser();
        if (getAction().equals(Helper.SUBMIT_ACTION)) {
            try {
                // backup the user:
                ActionContext.getContext().getSession().put(getBackupSessionKey(), user);
                // update the user preferences in persistence and make audit
                String oldValue = String.valueOf(user.getNotifications());
                String newValue = String.valueOf(notifications);
                user.setNotifications(notifications);
                getUserDao().saveOrUpdate(user);
                audit(oldValue, newValue, AUDIT_OPERATION_TYPE);
            } catch (IllegalStateException e) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE, "User has invalid state.", e,
                        UserPreferencesActionExecutionException.class);
            }
        }
        // handle discard action
        if (getAction().equals(Helper.DISCARD_ACTION)) {
            Helper.handleDiscardAction(this, EXECUTE_METHOD_SIGNATURE);
        }
        // send email if needed
        if (isEmailSendFlag()) {
            sendEmail();
        }
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {SUCCESS});
        return SUCCESS;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the notifications
     */
    public Set < Notification > getNotifications() {
        return notifications;
    }

    /**
     * <p>
     * Prepares the action.
     * </p>
     * <p>
     * It will get the logged in user and expose its preferences, so then they can be updated.
     * </p>
     * @throws Exception if any field has not injected correctly
     */
    public void prepare() throws Exception {
        super.prepare();
        try {
            notifications = getLoggedInUser().getNotifications();
        } catch (IllegalStateException e) {
            throw new UserPreferencesActionConfigurationException("User has invalid state.", e);
        }
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param notifications the notifications to set
     */
    public void setNotifications(Set < Notification > notifications) {
        this.notifications = notifications;
    }

    /**
     * <p>
     * Validates the inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        int i = 0;
        for (Notification notification : notifications) {
            // check if any string field has valid value
            Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, notification.getName(),
                    "notifications.element[" + i + "].name");
            Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, notification.getStatus(),
                    "notifications.element[" + i + "].status");
            i++;
        }
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }
}
