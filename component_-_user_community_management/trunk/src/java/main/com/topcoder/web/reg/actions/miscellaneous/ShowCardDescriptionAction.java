/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import com.topcoder.commons.utils.LoggingWrapperUtility;

/**
 * This is a Struts action that is responsible for showing description of TopCoder member cards. This action does
 * nothing. Actual response rendering is performed with use of JSP.
 * <p>
 * Thread Safety:
 * This class is mutable and not thread safe. But it will be used in thread safe manner by Struts. It's assumed that
 * request scope will be set up for all Struts actions in Spring configuration, thus actions will be accessed from
 * a single thread only.
 *
 * @author saarixx, mumujava
 * @version 1.0
 */
public class ShowCardDescriptionAction extends BaseUserCommunityManagementAction {
    /**
    * <p>
    * The serial version uid.
    * </p>
    */
    private static final long serialVersionUID = 4292340713062814791L;

    /**
     * Creates an instance of ShowCardDescriptionAction.
     */
    public ShowCardDescriptionAction() {
    }

    /**
     * Executes the Struts action. This method simply returns SUCCESS for redirecting to JSP page.
     * @return SUCCESS to indicate that the operation was successful
     */
    @Override
    public String execute() {
        final String methodSignature = "ShowCardDescriptionAction.execute";
        LoggingWrapperUtility.logEntrance(getLog(), methodSignature, null, null);
        LoggingWrapperUtility.logExit(getLog(), methodSignature, null);
        return SUCCESS;
    }
}
