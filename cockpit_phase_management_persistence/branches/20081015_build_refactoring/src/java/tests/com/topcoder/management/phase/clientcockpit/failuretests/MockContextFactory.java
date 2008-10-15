/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase.clientcockpit.failuretests;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import com.topcoder.service.studio.contest.ContestManager;


/**
 * <p>
 * Mock implementation of <code>InitialContextFactory</code> to be used in testing.
 * </p>
 *
 * @author hfx
 * @version 1.0
 */
public class MockContextFactory implements InitialContextFactory {
    /**
     * Creates an Initial Context for beginning name resolution.
     *
     * @param environment The possibly null environment specifying information to be used in the creation of
     *        the initial context.
     * @return a non-null initial context object that implements the Context interface.
     * @throws NamingException to JUnit
     */
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        Context context = new MockInitialContext();
        ContestManager manager = new MockContestManager();

        context.bind(CockpitPhaseManagerTests.CONTEST_MANAGER_KEY, manager);
        context.bind("draftPhaseHandlerKey", "abc");

        return context;
    }
}
