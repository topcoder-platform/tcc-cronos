/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence;

import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;

import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.User;


/**
 * <p>
 * This interface defines the contract for implementations that will convert registration entities to
 * a <code>Resource</code> object.
 * </p>
 *
 * <p>
 *    <strong>Thread Safety:</strong>
 *    Implementations are required to be thread safe.
 * </p>
 *
 * @author TCSDESIGNER, TCSDEVELOPER
 * @version 1.0
 */
public interface RegistrationEntitiesToResourceConverter {

    /**
     * <p>
     * This method will convert the registration entities (contest, user, role) into a <code>Resource</code> object.
     * It will return the created <code>Resource</code> object.
     * </p>
     *
     * @param contest The contest.
     * @param user The user.
     * @param contestRole The role.
     * @param resourceRoles The resource roles.
     *
     * @return the created <code>Resource</code> object
     *
     * @throws IllegalArgumentException If any argument is null. Or if the array of <code>ResourceRole</code> is empty
     *         or contains null elements.
     * @throws RegistrationEntitiesToResourceConversionException If any error occur while performing the conversion.
     */
    Resource convertRegistrationEntitiesToResource(Contest contest, User user, ContestRole contestRole,
        ResourceRole[] resourceRoles) throws RegistrationEntitiesToResourceConversionException;
}
