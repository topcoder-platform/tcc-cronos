/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service;

import com.cronos.onlinereview.external.ExternalUser;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.team.service.OperationResult;

/**
 * <p>
 * The interface that defines the contract for validating registration information. The results of
 * this validation are returned as an <code>OperationResult</code> object from the Team Services
 * component.
 * </p>
 * <p>
 * There are no current implementations available in this component version.
 * </p>
 * <p>
 * Thread Safety: There are no restrictions on thread safety in implementations.
 * </p>
 * @author argolite, moonli
 * @version 1.0
 */
public interface RegistrationValidator {

    /**
     * <p>
     * Performs the validation on the passed data.
     * </p>
     * <p>
     * No parameter can be null, but any further checks are assumed to be performed by the validator
     * implementation. This may include checks as to whether these three parameters are consistent
     * with each other.
     * </p>
     * @return OperationResult the result of the registration validation.
     * @param registration
     *            the registration information
     * @param user
     *            the full information about the user associated with the registration
     * @param project
     *            the full project data related to the registration
     * @throws IllegalArgumentException
     *             if any parameter is null
     * @throws RegistrationValidationException
     *             if any unexpected error occurs
     */
    public OperationResult validate(RegistrationInfo registration, ExternalUser user,
        FullProjectData project);
}
