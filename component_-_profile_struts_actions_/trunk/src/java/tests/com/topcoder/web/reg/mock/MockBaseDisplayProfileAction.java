/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.mock;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.BaseDisplayProfileAction;

/**
 * <p>
 * This is mock implementation of BaseDisplayProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockBaseDisplayProfileAction extends BaseDisplayProfileAction {

    /**
     * <p>
     * Creates an instance of MockBaseDisplayProfileAction.
     * </p>
     */
    public MockBaseDisplayProfileAction() {
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param user the user
     */
    protected void processOutputData(User user) {
    }
}
