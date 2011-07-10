/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.profile;

import com.topcoder.web.common.model.User;
import com.topcoder.web.reg.ProfileCompletenessRetrievalException;

/**
 * <p>
 * This interface defines the contract for getting information about how complete a given task is. It provides a report
 * that states the task name, a count of total fields being checked and a count of how many are provided, and a flag to
 * state if all fields that need to be provided are indeed provided. The interface expects that the passed User
 * instance is the complete instance that is to be checked, and that no additional steps are required to get more of
 * the user information.
 * </p>
 * <p>
 * Thread Safety: Implementations are expected to be effectively thread-safe
 * </p>
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public interface ProfileTaskChecker {

    /**
     * <p>
     * Retrieves a profile task report for the given user. The complete User instance is passed to it.
     * </p>
     * @param user the complete info about the user whose completion check is being done
     * @return the profile task report
     * @throws IllegalArgumentException - if user is null or is missing other fields required by the implementation
     * @throws ProfileCompletenessRetrievalException - If there are any errors while performing this operation.
     */
    public ProfileTaskReport getTaskReport(User user) throws ProfileCompletenessRetrievalException;
}