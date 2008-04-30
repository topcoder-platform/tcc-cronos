/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clientcockpit.phases;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;


/**
 * <p>
 * Mock implementation of <code>InitialContextFactoryBuilder</code>.
 * It will build the instance of <code>MockContext</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockInitialContextFactory implements InitialContextFactory {

    /**
     * <p>
     * The mock instance of <code>MockContext</code>.
     * </p>
     */
    private static final MockContext CONTEXT = new MockContext();

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
