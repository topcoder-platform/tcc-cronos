/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user;

import com.topcoder.security.authorization.AuthorizationPersistenceException;
import com.topcoder.security.authorization.Principal;
import com.topcoder.security.authorization.SecurityRole;

import java.util.Collection;

/**
 * <p>
 * Implementation of AuthorizationPersistence interface that throws exceptions for the addRole,
 * getRole and removeRole methods. For unit testing purposes only.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ThrowingAuthPersistence extends DummyAuthorizationPersistence {

    /**
     * Always throws an exception.
     * @param principal ignored
     * @param role ignored
     * @throws AuthorizationPersistenceException Always.
     */
    public void addRoleForPrincipal(Principal principal, SecurityRole role)
        throws AuthorizationPersistenceException {

        throw new AuthorizationPersistenceException("Thrown from addRoleForPrincipal");
    }

    /**
     * Always throws an exception.
     * @param principal ignored
     * @return - throws AuthorizationPersistenceException Always.
     * @throws AuthorizationPersistenceException Always.
     */
    public Collection getRolesForPrincipal(Principal principal)
        throws AuthorizationPersistenceException {

        throw new AuthorizationPersistenceException("Thrown from getRolesForPrincipal");
    }

    /**
     * Always throws an exception.
     * @param principal ignored
     * @param role ignored
     * @throws AuthorizationPersistenceException Always.
     */
    public void removeRoleForPrincipal(Principal principal, SecurityRole role)
        throws AuthorizationPersistenceException {

        throw new AuthorizationPersistenceException("Thrown from removeRoleForPrincipal");
    }
}
