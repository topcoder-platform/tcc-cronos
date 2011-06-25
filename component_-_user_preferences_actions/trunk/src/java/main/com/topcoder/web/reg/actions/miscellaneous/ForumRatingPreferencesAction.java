/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.User;
import com.opensymphony.xwork2.ActionContext;
import com.topcoder.commons.utils.LoggingWrapperUtility;

/**
 * <p>
 * This action extends the BaseForumAction, and it allows user to manage the forum rating preferences.
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
public class ForumRatingPreferencesAction extends BaseForumAction {

    /**
     * <p>
     * Represents audit operation type.
     * </p>
     */
    private static final String AUDIT_OPERATION_TYPE = "Forum Rating Preferences Update";

    /**
     * <p>
     * Represents execute() method signature.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "ForumRatingPreferencesAction.execute()";

    /**
     * <p>
     * Represents validate() method signature.
     * </p>
     */
    private static final String VALIDATE_METHOD_SIGNATURE = "ForumRatingPreferencesAction.validate()";

    /**
     * <p>
     * Represents the show ratings forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String showRatings;

    /**
     * <p>
     * Represents the rating highlight threshold forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String ratingHighlightThreshold;

    /**
     * <p>
     * Represents the rating highlight min count forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String ratingHighlightMinCount;

    /**
     * <p>
     * Represents the rating collapse threshold forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String ratingCollapseThreshold;

    /**
     * <p>
     * Represents the rating collapse min messages forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String ratingCollapseMinMessages;

    /**
     * <p>
     * Represents the rating collapse min count forum preference.
     * </p>
     * <p>
     * It is exposed to front end and is expected to be updated by front end. It is used in corresponding getter/setter
     * and execute method. Can't be null when execute method is called. Used in getter/setter, prepare and execute
     * method.
     * </p>
     */
    private String ratingCollapseMinCount;

    /**
     * <p>
     * Creates an instance of ForumRatingPreferencesAction.
     * </p>
     */
    public ForumRatingPreferencesAction() {
        super();
    }

    /**
     * <p>
     * Execute method is responsible for viewing/updating the user forum rating preferences.
     * </p>
     * @return a string representing the logical result of the execution. If an action succeeds, it returns
     *         Action.SUCCESS. If some error occurs, the exception will be thrown.
     * @throws PreferencesActionDiscardException if error occurs when attempting to discard
     * @throws UserPreferencesActionExecutionException if any unexpected error occurs. (it's used to wrap all
     *             underlying exceptions)
     */
    public String execute() throws PreferencesActionDiscardException, UserPreferencesActionExecutionException {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null);
        if (getAction().equals(Helper.SUBMIT_ACTION)) {
            // get the user:
            try {
                com.jivesoftware.base.User user = Helper.getLoggedInForumUser(this, EXECUTE_METHOD_SIGNATURE);
                // backup the user:
                ActionContext.getContext().getSession().put(getBackupSessionKey(), user);
                // update the user in persistence:
                user.setProperty("showRatings", showRatings);
                user.setProperty("ratingHighlightThreshold", ratingHighlightThreshold);
                user.setProperty("ratingHighlightMinCount", ratingHighlightMinCount);
                user.setProperty("ratingCollapseThreshold", ratingCollapseThreshold);
                user.setProperty("ratingCollapseMinCount", ratingCollapseMinCount);
                user.setProperty("ratingCollapseMinMessages", ratingCollapseMinMessages);
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
     * It will get the logged in user and expose its forum preferences, so then they can be updated.
     * </p>
     * @throws Exception if any field after injection has invalid value
     */
    public void prepare() throws Exception {
        super.prepare();
        User user = Helper.getLoggedInForumUser(this, null);
        showRatings = user.getProperty("showRatings");
        ratingHighlightThreshold = user.getProperty("ratingHighlightThreshold");
        ratingHighlightMinCount = user.getProperty("ratingHighlightMinCount");
        ratingCollapseThreshold = user.getProperty("ratingCollapseThreshold");
        ratingCollapseMinCount = user.getProperty("ratingCollapseMinCount");
        ratingCollapseMinMessages = user.getProperty("ratingCollapseMinMessages");
    }

    /**
     * <p>
     * Validate the inputed parameters.
     * </p>
     */
    public void validate() {
        LoggingWrapperUtility.logEntrance(getLogger(), VALIDATE_METHOD_SIGNATURE, null, null);
        // check fields values
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, showRatings, "showRatings");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, ratingHighlightThreshold,
                "ratingHighlightThreshold");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, ratingHighlightMinCount,
                "ratingHighlightMinCount");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, ratingCollapseThreshold,
                "ratingCollapseThreshold");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, ratingCollapseMinMessages,
                "ratingCollapseMinMessages");
        Helper.checkNotNullNorEmpty(getLogger(), VALIDATE_METHOD_SIGNATURE, this, ratingCollapseMinCount,
                "ratingCollapseMinCount");
        LoggingWrapperUtility.logExit(getLogger(), VALIDATE_METHOD_SIGNATURE, null);
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the showRatings
     */
    public String getShowRatings() {
        return showRatings;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param showRatings the showRatings to set
     */
    public void setShowRatings(String showRatings) {
        this.showRatings = showRatings;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the ratingHighlightThreshold
     */
    public String getRatingHighlightThreshold() {
        return ratingHighlightThreshold;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param ratingHighlightThreshold the ratingHighlightThreshold to set
     */
    public void setRatingHighlightThreshold(String ratingHighlightThreshold) {
        this.ratingHighlightThreshold = ratingHighlightThreshold;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the ratingHighlightMinCount
     */
    public String getRatingHighlightMinCount() {
        return ratingHighlightMinCount;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param ratingHighlightMinCount the ratingHighlightMinCount to set
     */
    public void setRatingHighlightMinCount(String ratingHighlightMinCount) {
        this.ratingHighlightMinCount = ratingHighlightMinCount;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the ratingCollapseThreshold
     */
    public String getRatingCollapseThreshold() {
        return ratingCollapseThreshold;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param ratingCollapseThreshold the ratingCollapseThreshold to set
     */
    public void setRatingCollapseThreshold(String ratingCollapseThreshold) {
        this.ratingCollapseThreshold = ratingCollapseThreshold;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the ratingCollapseMinMessages
     */
    public String getRatingCollapseMinMessages() {
        return ratingCollapseMinMessages;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param ratingCollapseMinMessages the ratingCollapseMinMessages to set
     */
    public void setRatingCollapseMinMessages(String ratingCollapseMinMessages) {
        this.ratingCollapseMinMessages = ratingCollapseMinMessages;
    }

    /**
     * <p>
     * Retrieves the namesake field.
     * </p>
     * @return the ratingCollapseMinCount
     */
    public String getRatingCollapseMinCount() {
        return ratingCollapseMinCount;
    }

    /**
     * <p>
     * Sets the namesake field.
     * </p>
     * @param ratingCollapseMinCount the ratingCollapseMinCount to set
     */
    public void setRatingCollapseMinCount(String ratingCollapseMinCount) {
        this.ratingCollapseMinCount = ratingCollapseMinCount;
    }
}
