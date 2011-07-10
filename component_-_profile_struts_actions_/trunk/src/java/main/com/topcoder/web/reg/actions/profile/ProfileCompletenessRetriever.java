/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;

/**
 * <p>
 * This interface defines the contract for getting information about the completeness of a profile registration
 * process. It returns a report stating the percentage of completion, as well as a list of tasks and whether each one
 * of them is completed or not. The percentage completion calculation algorithm will be determined by the
 * implementations.
 * </p>
 * <p>
 * Thread Safety: Implementations are expected to be effectively thread-safe
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ProfileCompletenessRetriever {

    /**
     * <p>
     * Retrieves the profile completeness report for the given user. The complete User instance is passed to it.
     * </p>
     * @param user the complete info about the user whose completion check is being done
     * @return the completeness report
     * @throws IllegalArgumentException if user is null or is missing other fields required by the implementation
     * @throws ProfileCompletenessRetrievalException if there are any errors while performing this operation.
     */
    public ProfileCompletenessReport getProfileCompletenessReport(User user)
        throws ProfileCompletenessRetrievalException;
}