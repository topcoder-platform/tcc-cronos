/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileActionException;

/**
 * <p>
 * This action extends BaseDisplayProfileAction and processes the output by taking the passed user, which includes the
 * name and contact info, and sets it to the output.
 * </p>
 * <p>
 * Thread Safety: This class is mutable and not thread safe, but used in thread safe manner by framework.
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ViewNameAndContactAction extends BaseDisplayProfileAction {

    /**
     * <p>
     * Creates an instance of ViewNameAndContactAction.
     * </p>
     */
    public ViewNameAndContactAction() {
    }

    /**
     * <p>
     * This method is called by the execute method to process the output data.
     * </p>
     * <p>
     * This method will simply set this to the output and audit the call.
     * </p>
     * @param user the user
     * @throws ProfileActionException - If there are any errors while performing this operation.
     */
    protected void processOutputData(User user) {
        setDisplayedUser(user);
    }
}