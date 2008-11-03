/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;


/**
 * <p>
 * Mock implementation of <code>InitialContextFactory</code> to be used in failure testing.
 * </p>
 * <p>
 * There is no object binding in this context factory.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NamingFailureContextFactory implements InitialContextFactory {
    /**
     * Creates an Initial Context for beginning name resolution.
     *
     * @param environment The possibly null environment specifying information to be used in the creation of
     *        the initial context.
     * @return a non-null initial context object that implements the Context interface.
     * @throws NamingException if cannot create an initial context.
     */
    public Context getInitialContext(Hashtable<?, ?> environment)
        throws NamingException {
        return new MockInitialContext();
    }
}
