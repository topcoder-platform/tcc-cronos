/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.impl;

import com.cronos.onlinereview.external.ExternalUser;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationValidationException;
import com.topcoder.registration.service.RegistrationValidator;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.impl.OperationResultImpl;

/**
 * <p>
 * This is a mock implementation of <code>RegistrationValidator</code>.
 * </p>
 * <p>
 * It is just used for test.
 * </p>
 * @author moonli
 * @version 1.0
 */
public class MockRegistrationValidator implements RegistrationValidator {

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
        FullProjectData project) {
        if (registration.getProjectId() > 100) {
            throw new RegistrationValidationException("Error occurred.");
        }

        OperationResultImpl result = new OperationResultImpl();
        result.setSuccessful(true);

        return result;
    }
}
