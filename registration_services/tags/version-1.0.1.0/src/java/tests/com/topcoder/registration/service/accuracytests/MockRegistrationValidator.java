/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.service.accuracytests;

import com.cronos.onlinereview.external.ExternalUser;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.registration.service.RegistrationInfo;
import com.topcoder.registration.service.RegistrationValidator;
import com.topcoder.registration.team.service.OperationResult;
import com.topcoder.registration.team.service.impl.OperationResultImpl;

/**
 * <p>
 * Mock RegistrationValidator for testing.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockRegistrationValidator implements RegistrationValidator {

    /**
     * @see com.topcoder.registration.service.RegistrationValidator#validate(
     *      com.topcoder.registration.service.RegistrationInfo, com.cronos.onlinereview.external.ExternalUser,
     *      com.topcoder.project.service.FullProjectData)
     */
    public OperationResult validate(RegistrationInfo registration, ExternalUser user, FullProjectData project) {
        OperationResultImpl result = new OperationResultImpl();
        result.setSuccessful(true);
        return result;
    }
}
