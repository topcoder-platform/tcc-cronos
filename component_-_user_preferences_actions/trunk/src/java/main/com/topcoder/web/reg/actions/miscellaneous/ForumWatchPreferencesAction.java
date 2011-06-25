/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.text.ParseException;

import com.jivesoftware.base.JiveGlobals;
import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.jivesoftware.forum.action.UserSettingsAction;
import com.jivesoftware.util.CronTimer;
import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;

/**
 * <p>
 * This action extends the BaseForumAction, and it allows user to manage the forum watch preferences.
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
public class ForumWatchPreferencesAction extends BaseForumAction {

    /**
     * <p>
     * Represents audit operation type.
     * </p>
     */
    private static final String AUDIT_OPERATION_TYPE = "Forum Watch Preferences Update";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "ForumWatchPreferencesAction.execute()";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "ForumWatchPreferencesAction.validate()";

    /**
     * <p>
     * Represents minimum watch frequency value.
     * </p>
     */
    private static final int MINIMUM_WATCH_FREQUENCY = -1;

    /**
     * <p>
     * Represents the watch frequency forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int watchFrequency;

    /**
     * <p>
     * Represents the mark watches read forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String markWatchesRead;

    /**
     * <p>
     * Represents the auto watch new topics forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String autoWatchNewTopics;

    /**
     * <p>
     * Represents the auto watch replies forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String autoWatchReplies;

    /**
     * <p>
     * Creates an instance of ForumWatchPreferencesAction.
     * </p>
     */
    public ForumWatchPreferencesAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user watch forum preferences.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     * @throws PreferencesActionDiscardException if error occurs when attempting to discard
     */
    public String execute() throws UserPreferencesActionExecutionException, PreferencesActionDiscardException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        if (getAction().equals(Helper.SUBMIT_ACTION)) {
            // get the user:
            try {
                com.jivesoftware.base.User user = Helper.getLoggedInForumUser(this, EXECUTE_METHOD_SIGNATURE);
                // backup the user:
                ActionContext.getContext().getSession().put(getBackupSessionKey(), user);
                // update the user in persistence:
                user.setProperty("jiveAutoWatchNewTopics", autoWatchNewTopics);
                user.setProperty("jiveAutoWatchReplies", autoWatchReplies);
                user.setProperty("markWatchesRead", markWatchesRead);
                user.setProperty("watchFrequency", String.valueOf(watchFrequency));
                CronTimer current = createCronTimer(watchFrequency);
                // update user and timer in the database
                // getForumFactory().getUserManager().updateUser(user);
                getForumFactory().getWatchManager().setBatchTimer(user, current);
                audit(Helper.getUserPropertiesStringRepresentation(user),
                        Helper.getUserPropertiesStringRepresentation((User) ActionContext.getContext().getSession()
                                .get(getBackupSessionKey())), AUDIT_OPERATION_TYPE);
            } catch (UnauthorizedException e) {
                throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                        "Unauthorized exception occurred.", e, UserPreferencesActionExecutionException.class);
            }
        }
        // handle discard action
        if (getAction().equals(Helper.DISCARD_ACTION)) {
            Helper.handleDiscardForumAction(this, EXECUTE_METHOD_SIGNATURE);
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
     * Prepares the action.
     * </p>
     * <p>
     * Will get the logged in user and expose its forum watch preferences, so then they can be updated.
     * </p>
     * @throws Exception if any field has invalid value after injection
     */
    public void prepare() throws Exception {
        super.prepare();
        User user = Helper.getLoggedInForumUser(this, null);
        autoWatchNewTopics = user.getProperty("jiveAutoWatchNewTopics");
        autoWatchReplies = user.getProperty("jiveAutoWatchReplies");
        markWatchesRead = user.getProperty("markWatchesRead");
        watchFrequency = Integer.parseInt(user.getProperty("watchFrequency"));
    }

    /**
     * <p>
     * Validates the inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        // check fields values
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, markWatchesRead, "markWatchesRead");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, autoWatchReplies, "autoWatchReplies");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, autoWatchNewTopics,
                "autoWatchNewTopics");
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, watchFrequency,
                "watchFrequency", MINIMUM_WATCH_FREQUENCY);
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }

    /**
     * <p>
     * Helper method to create the Cron timer.
     * </p>
     * @param watchFrequency the watch frequency
     * @return the created timer
     * @throws UserPreferencesActionExecutionException if any error occurs while creating Cron timer
     */
    private CronTimer createCronTimer(int watchFrequency) throws UserPreferencesActionExecutionException {
        try {
            int minute = (int) (Math.random() * 60);
            // 3 am by default, configurable
            int hour =
                    (JiveGlobals.getJiveProperty("watches.email.digest.time") == null) ? 3 : Integer
                            .parseInt(JiveGlobals.getJiveProperty("watches.email.digest.time"));
            String day =
                    (JiveGlobals.getJiveProperty("watches.email.digest.day") == null) ? "SUN" : JiveGlobals
                            .getJiveProperty("watches.email.digest.day");
            if (watchFrequency == UserSettingsAction.FREQUENCY_ONCE_A_DAY) {
                return new CronTimer("0 " + minute + " " + hour + " ? * *");
            } else if (watchFrequency == UserSettingsAction.FREQUENCY_EVERY_OTHER_DAY) {
                return new CronTimer("0 " + minute + " " + hour + " */2 * ?");
            } else if (watchFrequency == UserSettingsAction.FREQUENCY_ONCE_A_WEEK) {
                return new CronTimer("0 " + minute + " " + hour + " ? * " + day);
            } else if (watchFrequency == -1) {
                return new CronTimer("0 15 10 ? * 6L " + 2098); // time never reached
            }
        } catch (ParseException e) {
            throw Helper.logAndWrapException(getLogger(), EXECUTE_METHOD_SIGNATURE,
                    "Parser error occured while creating Cron timer.", e,
                    UserPreferencesActionExecutionException.class);
        }
        return null;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the watchFrequency
     */
    public int getWatchFrequency() {
        return watchFrequency;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param watchFrequency the watchFrequency to set
     */
    public void setWatchFrequency(int watchFrequency) {
        this.watchFrequency = watchFrequency;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the markWatchesRead
     */
    public String getMarkWatchesRead() {
        return markWatchesRead;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param markWatchesRead the markWatchesRead to set
     */
    public void setMarkWatchesRead(String markWatchesRead) {
        this.markWatchesRead = markWatchesRead;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the autoWatchNewTopics
     */
    public String getAutoWatchNewTopics() {
        return autoWatchNewTopics;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param autoWatchNewTopics the autoWatchNewTopics to set
     */
    public void setAutoWatchNewTopics(String autoWatchNewTopics) {
        this.autoWatchNewTopics = autoWatchNewTopics;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the autoWatchReplies
     */
    public String getAutoWatchReplies() {
        return autoWatchReplies;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param autoWatchReplies the autoWatchReplies to set
     */
    public void setAutoWatchReplies(String autoWatchReplies) {
        this.autoWatchReplies = autoWatchReplies;
    }
}
