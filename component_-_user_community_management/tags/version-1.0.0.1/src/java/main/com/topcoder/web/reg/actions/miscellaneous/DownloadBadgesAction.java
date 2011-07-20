/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.web.common.NavigationException;
import com.topcoder.web.common.TCWebException;

/**
 * This is a Struts action that is responsible for showing badge download info to the user. This action simply checks
 * whether the user is rated and throws an exception if not. Actual response rendering is performed with use of JSP.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from a
 * single thread only.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class DownloadBadgesAction extends BaseRatedUserCommunityManagementAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = -4164041609104409130L;

    /**
     * Creates an instance of DownloadBadgesAction.
     */
    public DownloadBadgesAction() {
    }

    /**
     * Executes the Struts action. This method just checks whether the user is rated, and if not throws an exception.
     * @throws NavigationException if the user is not rated
     * @throws TCWebException if any other occurred
     * @return SUCCESS to indicate that the operation was successful
     */
    @Override
    public String execute() throws TCWebException {
        final String methodSignature = "DownloadBadgesAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        try {
            if (!isRated()) {
                throw new NavigationException("Sorry you have not been rated in competition.");
            }
        } catch (TCWebException e) {
            throw LoggingWrapperUtility.logException(getLog(), methodSignature, e);
        }
        LoggingWrapperUtility.logExit(getLog(), methodSignature, null);
        return SUCCESS;
    }
}
