/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.web.common.NavigationException;
import com.topcoder.web.common.TCWebException;

/**
 * This is a Struts action that is responsible for previewing TopCoder member card. This action checks whether the user
 * is rated and whether the member card is unlocked. Actual response rendering is performed with use of JSP.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from a
 * single thread only.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class PreviewCardAction extends BaseRatedUserCommunityManagementAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -7856121513068720574L;
    /**
     * The value indicating whether user's member card is unlocked. It is initialized in execute() and is used for
     * passing data from this Struts action to JSP. Has a getter.
     */
    private boolean cardUnlocked;

    /**
     * Creates an instance of PreviewCardAction.
     */
    public PreviewCardAction() {
    }

    /**
     * Executes the Struts action. This method checks whether the user is rated, and if not throws an exception. Also
     * it passes the value indicating whether the member card is unlocked to JSP via action property.
     *
     * @throws NavigationException if the user is not rated
     * @throws TCWebException if any other error occurred
     * @return SUCCESS to indicate that the operation was successful
     */
    @Override
    public String execute() throws TCWebException {
        final String methodSignature = "PreviewCardAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        try {
            if (!isRated()) {
                throw new NavigationException("Sorry you have not been rated in competition.");
            }
            long userId = getUserId();
            cardUnlocked = CardHelper.isUnlocked(userId);
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLog(), methodSignature, e);
        }
        LoggingWrapperUtility.logExit(getLog(), methodSignature, new Object[]{cardUnlocked});
        return SUCCESS;
    }

    /**
     * Retrieves the value indicating whether user's member card is unlocked.
     *
     * @return the value indicating whether user's member card is unlocked
     */
    public boolean isCardUnlocked() {
        return cardUnlocked;
    }
}
