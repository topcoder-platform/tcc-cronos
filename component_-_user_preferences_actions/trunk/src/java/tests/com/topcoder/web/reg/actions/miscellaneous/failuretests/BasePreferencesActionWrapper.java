package com.topcoder.web.reg.actions.miscellaneous.failuretests;

import com.topcoder.web.reg.actions.miscellaneous.BasePreferencesAction;
import com.topcoder.web.reg.actions.miscellaneous.UserPreferencesActionExecutionException;

/**
 * The wrapper of the class to test.
 * @author moon.river
 * @version 1.0
 */
public class BasePreferencesActionWrapper extends BasePreferencesAction {
    /**
     * <p>
     * Sends the email.
     * </p>
     * @throws UserPreferencesActionExecutionException if any error occurs in this method
     */
    public void sendEmail() throws UserPreferencesActionExecutionException {
    	super.sendEmail();
    }
}
