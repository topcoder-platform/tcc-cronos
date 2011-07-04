/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.reg.profilecompleteness.filter.impl;

import com.topcoder.web.common.model.User;

/**
 * This interfaces defines the contract for checking profile completeness for the specific user
 * action. The concrete details of data retrieval is up to the implementation.
 * <p>
 * <b>Thread-safety</b>
 * <p>
 * The implementations are required to be thread-safe.
 *
 * @author faeton, nowind_lee
 * @version 1.0
 */
public interface ProfileCompletenessChecker {

    /**
     * This method checks whether the profile is complete for specific action.
     *
     * @param user
     *            the user to check user profile
     * @return True, if the user has completed enough fields in the profile, false otherwise.
     * @throws IllegalArgumentException
     *             if user is null
     * @throws ProfileCompletenessCheckerException
     *             if any exception occurs while checking profile completeness
     */
    boolean isProfileComplete(User user) throws ProfileCompletenessCheckerException;

}
