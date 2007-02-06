/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.orpheus.administration.persistence.AdminDataLocal;
import com.orpheus.administration.persistence.AdminDataLocalHome;
import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;


/**
 * <p>
 * Unit test cases for GameDataUtility.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataUtilityTests extends TestCase {
    /**
     * The setUp of the unit test.
     *
     * @throws Exception to JUnit
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

        /* Create deployment descriptor of our sample bean.
         * MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor
            = new SessionBeanDescriptor("localjbossBean",
                    GameDataLocalHome.class, GameDataLocal.class, new GameDataBean());
        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        sampleServiceDescriptor = new SessionBeanDescriptor("localAdminDataBean",
                AdminDataLocalHome.class, AdminDataLocal.class, new MockAdminDataBean());
        mockContainer.deploy(sampleServiceDescriptor);

        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
        cm.add("config.xml");
        cm.add("objectFactory.xml");
    }

    /**
     * The tearDown of the unit test.
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
    }
    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with key,
     * the key is null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure1()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }

    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with key,
     * the key is empty,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure2()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }

    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with namespace and key,
     * the key is null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure3()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance("com.orpheus.game.GameDataManager_ObjectFactory",
                null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }

    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with namespace and key,
     * the key is empty,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure4()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance("com.orpheus.game.GameDataManager_ObjectFactory",
                " ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }

    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with namespace and key,
     * the namespace is null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure5()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance(null, "manager");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }

    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with namespace and key,
     * the namespace is empty,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure6()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance(" ", "manager");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //ok
        }
    }

    /**
     * The failure test of the method getConfiguredGameDataManagerInstance with namespace and key,
     * the namespace does not exist,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_failure7()
        throws Exception {
        try {
            GameDataUtility.getConfiguredGameDataManagerInstance("no such namespace",
                "manager");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //ok
        }
    }
    /**
     * The accuracy test of the method getConfiguredGameDataManagerInstance with key.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_withKey_accuracy() throws Exception {
        GameDataManager manager = GameDataUtility.getConfiguredGameDataManagerInstance("manager");
        assertNotNull("create failed.", manager);
        ((GameDataManagerImpl) manager).stopManager();
    }
    /**
     * The accuracy test of the method getConfiguredGameDataManagerInstance with key and namespace.
     *
     * @throws Exception to JUnit
     */
    public void test_getConfiguredGameDataManagerInstance_withKeyAndNamespace_accuracy() throws Exception {
        GameDataManager manager = GameDataUtility.getConfiguredGameDataManagerInstance(
                "com.orpheus.game.GameDataManager_ObjectFactory", "manager");
        assertNotNull("create failed.", manager);
        ((GameDataManagerImpl) manager).stopManager();
    }
}
