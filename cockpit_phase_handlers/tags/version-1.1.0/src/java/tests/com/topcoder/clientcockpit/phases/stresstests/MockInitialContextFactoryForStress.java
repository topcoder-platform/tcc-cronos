/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases.stresstests;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;


/**
 * <p>
 * Mock implementation of <code>InitialContextFactoryBuilder</code> for stress tests.
 * </p>
 *
 * @author Littleken
 * @version 1.0
 */
public class MockInitialContextFactoryForStress implements InitialContextFactory {

    /**
     * <p>
     * The mock instance of <code>MockContext</code>.
     * </p>
     */
    private static final MockContextForStress CONTEXT = new MockContextForStress();

    /**
     * <p>
     * Return mock instance of <code>MockContext</code>.
     * </p>
     *
     * @param environment Useless parameter.
     * @return mock instance of <code>MockContext</code>.
     * @throws NamingException never.
     */
    public Context getInitialContext(Hashtable environment) throws NamingException {
        return CONTEXT;
    }
}
