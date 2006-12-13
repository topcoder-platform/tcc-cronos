/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration.accuracytests;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.administration.ServiceLocator;
import com.orpheus.administration.persistence.AdminData;
import com.orpheus.administration.persistence.AdminDataBean;
import com.orpheus.administration.persistence.AdminDataHome;
import com.orpheus.game.persistence.GameData;
import com.orpheus.game.persistence.GameDataHome;
import com.orpheus.game.persistence.ejb.GameDataBean;

import junit.framework.TestCase;

/**
 * Accuracy test for ServiceLocator.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ServiceLocatorAccuracyTests extends TestCase {
    /**
     * Represents the admin data home.
     */
    private static final String ADMIN_DATE_JNDI_NAME = "AdminDataHome";
    /**
     * Represents the game data home.
     */
    private static final String GAME_DATE_JNDI_NAME = "GameDataHome";

    /**
     * <p>
     * An instance of <code>ServiceLocator</code> which is tested.
     * </p>
     */
    private ServiceLocator target = null;

    /**
     * <p>
     * setUp() routine. Creates test ServiceLocator instance and deploy ejb.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        /* We need to set MockContextFactory as our JNDI provider.
         * This method sets the necessary system properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(new SessionBeanDescriptor(GAME_DATE_JNDI_NAME,
                GameDataHome.class, GameData.class, new GameDataBean()));
        mockContainer.deploy(new SessionBeanDescriptor(ADMIN_DATE_JNDI_NAME,
                AdminDataHome.class, AdminData.class, new AdminDataBean()));

        target = new ServiceLocator();
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>ServiceLocator()</code> for
     * proper behavior.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructorAccuracy() throws Exception {
        assertNotNull("Failed to get ServiceLocator instance.", target);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that remote home get successfully.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHomeAccuracy1() throws Exception {
        GameDataHome home = (GameDataHome) target.getRemoteHome(GAME_DATE_JNDI_NAME,
                GameDataHome.class);
        assertNotNull("falied to get GameDataHome", home);
    }

    /**
     * <p>
     * Accuracy test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that remote home get successfully.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHomeAccuracy2() throws Exception {
        AdminDataHome home = (AdminDataHome) target.getRemoteHome(ADMIN_DATE_JNDI_NAME,
                AdminDataHome.class);
        assertNotNull("falied to get GameDataHome", home);
    }
}
