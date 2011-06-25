/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.web.forums.ForumConstants;

/**
 * <p>
 * This action extends the BaseForumAction, and it allows user to manage the forum general preferences.
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
/**
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ForumGeneralPreferencesAction extends BaseForumAction {

    /**
     * <p>
     * Represents audit operation type.
     * </p>
     */
    private static final String AUDIT_OPERATION_TYPE = "Forum General Preferences Update";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "ForumGeneralPreferencesAction.execute()";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "ForumGeneralPreferencesAction.validate()";

    /**
     * <p>
     * Represents minimum value that pages could have.
     * </p>
     */
    private static final int MINIMUM_PAGES_VALUE = 0;

    /**
     * <p>
     * Represents the number of forums per page forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int forumsPerPage;

    /**
     * <p>
     * Represents the number of threads per page forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int threadsPerPage;

    /**
     * <p>
     * Represents the number of messages per page forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int messagesPerPage;

    /**
     * <p>
     * Represents the number of messages per history page forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int messagesPerHistoryPage;

    /**
     * <p>
     * Represents the number of results per search page forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be negative when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private int resultsPerSearchPage;

    /**
     * <p>
     * Represents the thread mode forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String threadMode;

    /**
     * <p>
     * Represents the flat mode forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String flatMode;

    /**
     * <p>
     * Represents the show previuos and next threads forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String showPrevNextThreads;

    /**
     * <p>
     * Represents the display member photo forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String displayMemberPhoto;

    /**
     * <p>
     * Represents the all member photos forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String displayAllMemberPhotos;

    /**
     * <p>
     * Represents the collapse read forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String collapseRead;

    /**
     * <p>
     * Represents the collapse read days forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String collapseReadDays;

    /**
     * <p>
     * Represents the collapse read posts forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String collapseReadPosts;

    /**
     * <p>
     * Represents the collapse read show replied forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String collapseReadShowReplied;

    /**
     * <p>
     * Creates an instance of ForumGeneralPreferencesAction.
     * </p>
     */
    public ForumGeneralPreferencesAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user general forum preferences.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws UserPreferencesActionExecutionException - if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     * @throws PreferencesActionDiscardException - if error occurs when attempting to discard
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
                user.setProperty("jiveForumRange", String.valueOf(forumsPerPage));
                user.setProperty("jiveThreadRange", String.valueOf(threadsPerPage));
                user.setProperty("jiveMessageRange", String.valueOf(messagesPerPage));
                user.setProperty("jiveHistoryRange", String.valueOf(messagesPerHistoryPage));
                user.setProperty("jiveSearchRange", String.valueOf(resultsPerSearchPage));
                user.setProperty("jiveThreadMode", threadMode);
                user.setProperty("jiveFlatMode", flatMode);
                user.setProperty("jiveDisplayMemberPhoto", displayMemberPhoto);
                user.setProperty("jiveDisplayAllMemberPhotos", displayAllMemberPhotos);
                user.setProperty("jiveShowPrevNextThreads", showPrevNextThreads);
                user.setProperty("collapseRead", collapseRead);
                user.setProperty("collapseReadDays", collapseReadDays);
                user.setProperty("collapseReadPosts", collapseReadPosts);
                user.setProperty("collapseReadShowReplied", collapseReadShowReplied);
                // update user and timer in the database
                // getForumFactory().getUserManager().updateUser(user);
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
     * It will get the logged in user and expose its general forum preferences, so then they can be updated.
     * </p>
     * @throws Exception if any values has invalid value after injection
     */
    public void prepare() throws Exception {
        super.prepare();
        User user = Helper.getLoggedInForumUser(this, null);
        forumsPerPage = Integer.parseInt(user.getProperty("jiveForumRange"));
        threadsPerPage = Integer.parseInt(user.getProperty("jiveThreadRange"));
        messagesPerPage = Integer.parseInt(user.getProperty("jiveMessageRange"));
        messagesPerHistoryPage = Integer.parseInt(user.getProperty("jiveHistoryRange"));
        resultsPerSearchPage = Integer.parseInt(user.getProperty("jiveSearchRange"));
        threadMode = user.getProperty("jiveThreadMode");
        flatMode = user.getProperty("jiveFlatMode");
        showPrevNextThreads = user.getProperty("jiveShowPrevNextThreads");
        displayMemberPhoto = user.getProperty("jiveDisplayMemberPhoto");
        displayAllMemberPhotos = user.getProperty("jiveDisplayAllMemberPhotos");
        collapseRead = user.getProperty("collapseRead");
        collapseReadDays = user.getProperty("collapseReadDays");
        collapseReadPosts = user.getProperty("collapseReadPosts");
        collapseReadShowReplied = user.getProperty("collapseReadShowReplied");
    }

    /**
     * <p>
     * Validates the inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        // checkFields
        ForumGeneralPreferencesAction.checkIntegerFieldLess(getLogger(), VALIDATE_METHOD_SIGNATURE, this,
                forumsPerPage, "forumsPerPage", ForumConstants.maxForumsPerPage);
        ForumGeneralPreferencesAction.checkIntegerFieldLess(getLogger(), VALIDATE_METHOD_SIGNATURE, this,
                threadsPerPage, "threadsPerPage", ForumConstants.maxThreadsPerPage);
        ForumGeneralPreferencesAction.checkIntegerFieldLess(getLogger(), VALIDATE_METHOD_SIGNATURE, this,
                messagesPerPage, "messagesPerPage", ForumConstants.maxMessagesPerPage);
        ForumGeneralPreferencesAction.checkIntegerFieldLess(getLogger(), VALIDATE_METHOD_SIGNATURE, this,
                messagesPerHistoryPage, "messagesPerHistoryPage", ForumConstants.maxMessagesPerPage);
        ForumGeneralPreferencesAction.checkIntegerFieldLess(getLogger(), VALIDATE_METHOD_SIGNATURE, this,
                resultsPerSearchPage, "resultsPerSearchPage", ForumConstants.maxSearchResultsPerPage);
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, forumsPerPage, "forumsPerPage",
                MINIMUM_PAGES_VALUE);
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, threadsPerPage,
                "threadsPerPage", MINIMUM_PAGES_VALUE);
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, messagesPerPage,
                "messagesPerPage", MINIMUM_PAGES_VALUE);
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, messagesPerHistoryPage,
                "messagesPerHistoryPage", MINIMUM_PAGES_VALUE);
        Helper.checkIntegerFieldGreater(getLogger(), VALIDATE_METHOD_SIGNATURE, this, resultsPerSearchPage,
                "resultsPerSearchPage", MINIMUM_PAGES_VALUE);
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the forumsPerPage
     */
    public int getForumsPerPage() {
        return forumsPerPage;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param forumsPerPage the forumsPerPage to set
     */
    public void setForumsPerPage(int forumsPerPage) {
        this.forumsPerPage = forumsPerPage;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the threadsPerPage
     */
    public int getThreadsPerPage() {
        return threadsPerPage;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param threadsPerPage the threadsPerPage to set
     */
    public void setThreadsPerPage(int threadsPerPage) {
        this.threadsPerPage = threadsPerPage;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the messagesPerPage
     */
    public int getMessagesPerPage() {
        return messagesPerPage;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param messagesPerPage the messagesPerPage to set
     */
    public void setMessagesPerPage(int messagesPerPage) {
        this.messagesPerPage = messagesPerPage;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the messagesPerHistoryPage
     */
    public int getMessagesPerHistoryPage() {
        return messagesPerHistoryPage;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param messagesPerHistoryPage the messagesPerHistoryPage to set
     */
    public void setMessagesPerHistoryPage(int messagesPerHistoryPage) {
        this.messagesPerHistoryPage = messagesPerHistoryPage;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the resultsPerSearchPage
     */
    public int getResultsPerSearchPage() {
        return resultsPerSearchPage;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param resultsPerSearchPage the resultsPerSearchPage to set
     */
    public void setResultsPerSearchPage(int resultsPerSearchPage) {
        this.resultsPerSearchPage = resultsPerSearchPage;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the threadMode
     */
    public String getThreadMode() {
        return threadMode;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param threadMode the threadMode to set
     */
    public void setThreadMode(String threadMode) {
        this.threadMode = threadMode;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the flatMode
     */
    public String getFlatMode() {
        return flatMode;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param flatMode the flatMode to set
     */
    public void setFlatMode(String flatMode) {
        this.flatMode = flatMode;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the showPrevNextThreads
     */
    public String getShowPrevNextThreads() {
        return showPrevNextThreads;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param showPrevNextThreads the showPrevNextThreads to set
     */
    public void setShowPrevNextThreads(String showPrevNextThreads) {
        this.showPrevNextThreads = showPrevNextThreads;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the displayMemberPhoto
     */
    public String getDisplayMemberPhoto() {
        return displayMemberPhoto;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param displayMemberPhoto the displayMemberPhoto to set
     */
    public void setDisplayMemberPhoto(String displayMemberPhoto) {
        this.displayMemberPhoto = displayMemberPhoto;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the displayAllMemberPhotos
     */
    public String getDisplayAllMemberPhotos() {
        return displayAllMemberPhotos;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param displayAllMemberPhotos the displayAllMemberPhotos to set
     */
    public void setDisplayAllMemberPhotos(String displayAllMemberPhotos) {
        this.displayAllMemberPhotos = displayAllMemberPhotos;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the collapseRead
     */
    public String getCollapseRead() {
        return collapseRead;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param collapseRead the collapseRead to set
     */
    public void setCollapseRead(String collapseRead) {
        this.collapseRead = collapseRead;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the collapseReadDays
     */
    public String getCollapseReadDays() {
        return collapseReadDays;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param collapseReadDays the collapseReadDays to set
     */
    public void setCollapseReadDays(String collapseReadDays) {
        this.collapseReadDays = collapseReadDays;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the collapseReadPosts
     */
    public String getCollapseReadPosts() {
        return collapseReadPosts;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param collapseReadPosts the collapseReadPosts to set
     */
    public void setCollapseReadPosts(String collapseReadPosts) {
        this.collapseReadPosts = collapseReadPosts;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the collapseReadShowReplied
     */
    public String getCollapseReadShowReplied() {
        return collapseReadShowReplied;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param collapseReadShowReplied the collapseReadShowReplied to set
     */
    public void setCollapseReadShowReplied(String collapseReadShowReplied) {
        this.collapseReadShowReplied = collapseReadShowReplied;
    }

    /**
     * <p>
     * Checks whether given fieldValue greater than given value.
     * </p>
     * @param logger the logger to provide logging
     * @param methodSignature the method signature
     * @param action the action to be populated with field errors
     * @param fieldValue the field value to check
     * @param fieldName the field name
     * @param value the that fieldValue should be less
     */
    private static void checkIntegerFieldLess(Log logger, String methodSignature, ActionSupport action,
            int fieldValue, String fieldName, int value) {
        if (fieldValue > value) {
            String errorMessage = fieldName + " should be less than " + value + ".";
            action.addFieldError(fieldName, errorMessage);
            logger.log(Level.WARN, "Validation error in method " + methodSignature + ". " + errorMessage);
        }
    }
}
