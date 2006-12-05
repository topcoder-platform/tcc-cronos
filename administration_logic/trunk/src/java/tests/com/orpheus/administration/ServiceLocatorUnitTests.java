/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import junit.framework.TestCase;

import com.orpheus.game.persistence.GameDataHome;

/**
 * <p>
 * Test the <code>ServiceLocator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ServiceLocatorUnitTests extends TestCase {
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
        TestHelper.deployEJB();
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
    public void testConstructor_1_accuracy() throws Exception {
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
    public void testGetRemoteHome_1_accuracy() throws Exception {
        GameDataHome home = (GameDataHome) target.getRemoteHome(
                TestHelper.GAME_DATA_JNDI_NAME, GameDataHome.class);
        assertNotNull("falied to get GameDataHome", home);
    }

    /**
     * <p>
     * Failure test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that IllegalArgumentException should be thrown if any argument is null,
     * if jndiHomeName is empty string or if className is not of type EJBHome.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHome_1_failure() throws Exception {
        try {
            target.getRemoteHome(null, GameDataHome.class);
            fail("IllegalArgumentException should be thrown if any argument is null");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that IllegalArgumentException should be thrown if any argument is null,
     * if jndiHomeName is empty string or if className is not of type EJBHome.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHome_2_failure() throws Exception {
        try {
            target.getRemoteHome(TestHelper.GAME_DATA_JNDI_NAME, null);
            fail("IllegalArgumentException should be thrown if any argument is null");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that IllegalArgumentException should be thrown if any argument is null,
     * if jndiHomeName is empty string or if className is not of type EJBHome.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHome_3_failure() throws Exception {
        try {
            target.getRemoteHome("  ", GameDataHome.class);
            fail("IllegalArgumentException should be thrown if jndiHomeName is empty");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that IllegalArgumentException should be thrown if any argument is null,
     * if jndiHomeName is empty string or if className is not of type EJBHome.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHome_4_failure() throws Exception {
        try {
            target.getRemoteHome(TestHelper.GAME_DATA_JNDI_NAME, String.class);
            fail("IllegalArgumentException should be thrown if className is not of type EJBHome.");
        } catch (IllegalArgumentException e) {
            // ok
        }
    }

    /**
     * <p>
     * Failure test. Tests the <code>getRemoteHome(String, Class)</code> for
     * proper behavior. Verify that ServiceLocatorException should be thrown
     * if any exception occur when retrieve RemoteHome.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testGetRemoteHome_5_failure() throws Exception {
        try {
            target.getRemoteHome(TestHelper.GAME_DATA_JNDI_NAME + 1231,
                    GameDataHome.class);
            fail("ServiceLocatorException should be thrown if any exception occur when retrieve RemoteHome.");
        } catch (ServiceLocatorException e) {
            // ok
        }
    }
}
