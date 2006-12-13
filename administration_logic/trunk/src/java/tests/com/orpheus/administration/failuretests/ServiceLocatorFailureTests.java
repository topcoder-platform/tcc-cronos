/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.failuretests;

import com.orpheus.administration.ServiceLocator;
import com.orpheus.administration.ServiceLocatorException;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataBean;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.ejb.GameDataBean;
import junit.framework.TestCase;
import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * <p>
 * Failure test for <code>ServiceLocator</code> class.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class ServiceLocatorFailureTests extends TestCase {
    /**
     * <p>
     * The <code>ServiceLocator</code> instance to test.
     * </p>
     */
    private ServiceLocator locator = null;

    /**
     * <p>
     * Setup for each test cases.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        MockContextFactory.setAsInitial();
        Context context = new InitialContext();
        MockContainer mockContainer = new MockContainer(context);
        mockContainer.deploy(new SessionBeanDescriptor("GameDataHome",
                GameDataHome.class, GameData.class, new GameDataBean()));
        mockContainer.deploy(new SessionBeanDescriptor("AdminDataHome",
                AdminDataHome.class, AdminData.class, new AdminDataBean()));

        locator = new ServiceLocator();
    }

    /**
     * <p>
     * Failure test of the method <code>getRemoteHome()</code>.
     * In this test case the 'jndiHomeName' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testgetRemoteHomeNullJNDI() throws Exception {
        try {
            locator.getRemoteHome(null, GameDataHome.class);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getRemoteHome()</code>.
     * In this test case the 'jndiHomeName' parameter is empty. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testgetRemoteHomeEmptyJNDI() throws Exception {
        try {
            locator.getRemoteHome(" ", GameDataHome.class);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getRemoteHome()</code>.
     * In this test case the 'jndiHomeName' parameter is invalid. ServiceLocatorException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testgetRemoteHomeInvalidJNDI() throws Exception {
        try {
            locator.getRemoteHome("GameDataHome_Invalid", GameDataHome.class);
            fail("ServiceLocatorException should be thrown.");
        } catch (ServiceLocatorException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getRemoteHome()</code>.
     * In this test case the 'className' parameter is null. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testgetRemoteHomeNullClassName() throws Exception {
        try {
            locator.getRemoteHome("GameDataHome", null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }

    /**
     * <p>
     * Failure test of the method <code>getRemoteHome()</code>.
     * In this test case the 'className' parameter is invalid. IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testgetRemoteHomeInvalidClassName() throws Exception {
        try {
            locator.getRemoteHome("GameDataHome", Boolean.class);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // fine
        }
    }
}
