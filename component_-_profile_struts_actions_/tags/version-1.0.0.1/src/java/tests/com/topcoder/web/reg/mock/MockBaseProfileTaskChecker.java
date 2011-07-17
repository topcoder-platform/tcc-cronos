/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.mock;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;
import com.topcoder.web.reg.actions.profile.BaseProfileTaskChecker;
import com.topcoder.web.reg.actions.profile.ProfileTaskReport;

/**
 * <p>
 * This is mock implementation of BaseProfileTaskChecker.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockBaseProfileTaskChecker extends BaseProfileTaskChecker {

    /**
     * <p>
     * Do nothing.
     * </p>
     * @param user the complete info about the user whose completion check is being done
     * @return null
     * @throws IllegalArgumentException - if user is null or is missing other fields required by the implementation
     * @throws ProfileCompletenessRetrievalException - If there are any errors while performing this operation.
     */
    public ProfileTaskReport getTaskReport(User user) throws ProfileCompletenessRetrievalException {
        return null;
    }
}
