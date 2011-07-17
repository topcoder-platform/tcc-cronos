/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseProfileAction to simply forward to the appropriate save action based on the current tab.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CompleteProfileAction extends BaseProfileAction {

    /**
     * <p>
     * Represents execute() method constant signature name used for logging.
     * </p>
     */
    private static final String EXECUTE_METHOD_SIGNATURE = "CompleteProfileAction.execute()";

    /**
     * <p>
     * Represents the tab that is being serviced.
     * </p>
     * <p>
     * It is set in the setter It can be retrieved in the getter.
     * </p>
     * <p>
     * Initially null, it will be set to a non-null value during the execute method. This field will be set during the
     * request phase by the container, and will not change afterwards.
     * </p>
     */
    private CurrentTab currentTab;

    /**
     * <p>
     * Creates an instance of CompleteProfileAction.
     * </p>
     */
    public CompleteProfileAction() {
    }

    /**
     * <p>
     * This action will simply forward to the appropriate save action based on the current tab.
     * </p>
     * @return a string representing the logical result of the execution.
     * @throws ProfileActionException - If there are any errors in the action
     */
    public String execute() {
        LoggingWrapperUtility.logEntrance(getLogger(), EXECUTE_METHOD_SIGNATURE, null, null, true, Level.DEBUG);
        String result;
        if (this.currentTab == CurrentTab.CONTACT_TAB) {
            result = "Save Contact";
        } else if (this.currentTab == CurrentTab.DEMOGRAPHIC_TAB) {
            result = "Save Demographic";
        } else {
            result = "Save Account";
        }
        LoggingWrapperUtility.logExit(getLogger(), EXECUTE_METHOD_SIGNATURE, new String[] {result}, null, true,
                Level.DEBUG);
        return result;
    }

    /**
     * <p>
     * Retrieves the namesake field instance value.
     * </p>
     * @return the namesake field instance value
     */
    public CurrentTab getCurrentTab() {
        return currentTab;
    }

    /**
     * <p>
     * Sets the namesake field instance value.
     * </p>
     * @param currentTab the namesake field instance value to set
     */
    public void setCurrentTab(CurrentTab currentTab) {
        this.currentTab = currentTab;
    }
}