/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.mock;

import com.topcoder.web.reg.actions.miscellaneous.BasePreferencesAction;

/**
 * <p>
 * This is mock implementation of BasePreferencesAction.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockBasePreferencesAction extends BasePreferencesAction {

    /**
     * <p>
     * Do nothing.
     * </p>
     * @return SUCCESS
     * @throws Exception if any error occurs
     */
    public String execute() throws Exception {
        return SUCCESS;
    }
}