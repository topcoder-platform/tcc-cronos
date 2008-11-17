/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit;

import com.topcoder.service.studio.contest.ContestManager;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;


/**
 * <p>
 * Mock implementation of <code>InitialContextFactory</code> to be used in testing.
 * </p>
 * <p>
 * This context factory has one binding, that is {@link CockpitPhaseManagerTest#CONTEST_MANAGER_JNDI_NAME} is
 * bound to MockContestManager instance.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContextFactory implements InitialContextFactory {
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
        Context context = new MockInitialContext();
        ContestManager manager = new MockContestManager();

        context.bind(CockpitPhaseManagerTest.CONTEST_MANAGER_JNDI_NAME, manager);

        return context;
    }
}
