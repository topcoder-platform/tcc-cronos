/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved.
 */
package com.cronos.timetracker.accuracytests;

import com.cronos.timetracker.user.DbUserAuthenticator;
import com.topcoder.security.authenticationfactory.AuthenticateException;
import com.topcoder.security.authenticationfactory.ConfigurationException;
import com.topcoder.security.authenticationfactory.Principal;
import com.topcoder.security.authenticationfactory.Response;

/**
 * This class is a mock one to make the main api public for test.
 *
 * @author Chenhong
 * @version 2.0
 */
public class MyDbUserAuthenticator extends DbUserAuthenticator {

    /**
     * <p>
     * Constructs the DbUserAuthenticator according to the configuration details specified in the given namespace.
     * </p>
     *
     * @param namespace
     *            The namespace containing the necessary configuration details.
     * @throws ConfigurationException
     *             (from Authentication Factory) if a problem occurs during configuration.
     * @throws IllegalArgumentException
     *             if the namespace is an empty String.
     * @throws NullPointerException
     *             if <code>namespace</code> is <code>null</code>.
     */
    public MyDbUserAuthenticator(String namespace) throws ConfigurationException {
        super(namespace);
    }

    /**
     * <p>
     * Authenticates the provided Principal against the configured data store.
     * </p>
     *
     * @return the authentication response.
     * @param principal
     *            The principal to authenticate. This principal should contain the username/password combination to
     *            authenticate.
     * @throws IllegalArgumentException
     *             if principal is null.
     * @throws MissingPrincipalKeyException
     *             if certain key is missing in the given principal.
     * @throws InvalidPrincipalException
     *             if the principal is invalid, e.g. the type of a certain key's value is invalid.
     * @throws AuthenticateException
     *             if error occurs during the authentication
     */
    public Response doAuthenticate(Principal principal) throws AuthenticateException {
        return super.doAuthenticate(principal);
    }
}
