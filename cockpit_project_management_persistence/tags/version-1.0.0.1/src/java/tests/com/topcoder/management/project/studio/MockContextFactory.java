/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.studio;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * <p>
 * Mock implementation of <code>InitialContextFactory</code> to be used in testing.
 * </p>
 * <p>
 * This context factory has one binding, that is &quot;java:/comp/UserTransaction&quot; is bound to
 * MockBaseUserTransaction instance.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContextFactory implements InitialContextFactory {

    /**
     * Creates a new MockContextFactory object.
     */
    public MockContextFactory() {
    }

    /**
     * Creates an Initial Context for beginning name resolution.
     * @param environment
     *        The possibly null environment specifying information to be used in the creation of the initial
     *        context.
     * @return a non-null initial context object that implements the Context interface.
     * @throws NamingException
     *         if cannot create an initial context.
     */
    public Context getInitialContext(Hashtable environment) throws NamingException {
        Context context = new MockInitialContext();

        context.bind("contest_manager", new MockContestManager());
        context.bind("invalid_contest_manager", null);
        context.bind("java:comp/env/contestManagerRemote", new MockContestManager());

        return context;
    }
}
