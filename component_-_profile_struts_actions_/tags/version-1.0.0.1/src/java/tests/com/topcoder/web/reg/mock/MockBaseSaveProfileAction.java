/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.mock;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.actions.profile.BaseSaveProfileAction;

/**
 * <p>
 * This is mock implementation of BaseSaveProfileAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockBaseSaveProfileAction extends BaseSaveProfileAction {

    /**
     * <p>
     * Creates an instance of MockBaseDisplayProfileAction.
     * </p>
     */
    public MockBaseSaveProfileAction() {
    }

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param user the user
     */
    protected void processInputData(User user) {
        // Do nothing
    }
}
