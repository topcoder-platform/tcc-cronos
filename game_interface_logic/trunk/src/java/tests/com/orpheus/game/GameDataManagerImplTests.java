/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game;

import com.orpheus.game.persistence.GameDataLocal;
import com.orpheus.game.persistence.GameDataLocalHome;
import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;


/**
 * <p>
 * The unit test of GameDataManagerImpl.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class GameDataManagerImplTests extends TestCase {
    /**
     * The GameDataManagerImpl instance for test.
     */
    private GameDataManagerImpl manager;
    /**
     * The context used to lookup EJB.
     */
    private Context context;

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
        context = new InitialContext();

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

        ConfigManager cm = ConfigManager.getInstance();
        Iterator it = cm.getAllNamespaces();

        while (it.hasNext()) {
            cm.removeNamespace((String) it.next());
        }
        cm.add("config.xml");
        manager = new GameDataManagerImpl();
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
        //stop the manager
        manager.stopManager();
        MockContextFactory.revertSetAsInitial();
    }

    /**
     * The accuracy test of the constructor with parameters.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_accuracy1() throws Exception {
        GameDataManagerImpl instance = new GameDataManagerImpl(
                new String[] {"localjbossBean"}, new String[]{"local"}, 1000, 1000, 1000, (float)0.5, "namespace", "c");
        assertNotNull("create failed", instance);
        instance.stopManager();
    }
    /**
     * The accuracy test of the constructor with namespace.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_accuracy2() throws Exception {
        GameDataManagerImpl instance = new GameDataManagerImpl(GameDataManagerImpl.class.getName());
        assertNotNull("create failed", instance);
        instance.stopManager();
    }
    /**
     * The accuracy test of the default constructor.
     *
     */
    public void test_ctor_accuracy3() {
        assertNotNull("create failed", manager);
    }
    /**
     * The failure test of the constructor with namespace,
     * the namespace can not be null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure1() throws Exception {
        try {
            new GameDataManagerImpl(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace can not be empty,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure2() throws Exception {
        try {
            new GameDataManagerImpl(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace not exist,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure3() throws Exception {
        try {
            new GameDataManagerImpl("no such namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the jndiNames is null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure4() throws Exception {
        try {
            new GameDataManagerImpl(null, new String[] {"local"}, 5000, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the jndiNames contains null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure5() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {null},
                new String[] {"local"}, 5000, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the jndiDesignations is null,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure6() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {"name"}, null, 5000, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the jndiDesignations contains invalid String, not local or remote,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure7() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {"name"},
                new String[] {"not local or remote"}, 5000, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the jndiNames contains empty String,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure8() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {" "},
                new String[] {"local"}, 5000, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the jndiNames and jndiDesignations are not of the same length,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure9() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {"name"},
                new String[] {"local", "remote"}, 5000, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the interval <= 0,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure10() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {"name"},
                new String[] {"local"}, 0, 5000, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with parameters,
     * the interval <= 0,
     * so IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_failure11() throws Exception {
        try {
            new GameDataManagerImpl(new String[] {"name"},
                new String[] {"local"}, 5000, 0, 1000, (float)0.5, "namespace", "c");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure1() throws Exception {
        ConfigManager.getInstance().add("invalid_config_no_JNDI.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure2() throws Exception {
        ConfigManager.getInstance().add("invalid_config_wrong_JNDI1.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure3() throws Exception {
        ConfigManager.getInstance().add("invalid_config_wrong_JNDI2.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure4() throws Exception {
        ConfigManager.getInstance().add("invalid_config_wrong_JNDI3.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure5() throws Exception {
        ConfigManager.getInstance().add("invalid_config_no_interval1.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure6() throws Exception {
        ConfigManager.getInstance().add("invalid_config_no_interval2.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure7() throws Exception {
        ConfigManager.getInstance().add("invalid_config_wrong_interval1.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }

    /**
     * The failure test of the constructor with namespace,
     * the namespace configuration is invalid,
     * so GameDataManagerConfigurationException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_ctor_invalidconfig_failure8() throws Exception {
        ConfigManager.getInstance().add("invalid_config_wrong_interval2.xml");

        try {
            new GameDataManagerImpl("Invalid_namespace");
            fail("GameDataManagerConfigurationException should be thrown.");
        } catch (GameDataManagerConfigurationException e) {
            //pass
        }
    }
    /**
     * The failure test of the method testUpcomingDomain,
     * the slot can not be null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_testUpcomingDomain_failure1() throws Exception {
        try {
            manager.testUpcomingDomain(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method testUpcomingDomain,
     * the manager is stopped,
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_testUpcomingDomain_failure2() throws Exception {
        manager.stopManager();
        try {
            manager.testUpcomingDomain(new HostingSlotImpl());
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }
    /**
     * The accuracy test of the method testUpcomingDomain.
     *
     * @throws Exception to JUnit
     */
    public void test_testUpcomingDomain_failure3() throws Exception {
        assertTrue("should pass the test", manager.testUpcomingDomain(new HostingSlotImpl()));
    }
    /**
     * The failure test of the method recordWinningBids,
     * the ids can not be null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_recordWinningBids_failure1() throws Exception {
        try {
            manager.recordWinningBids(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            //pass
        }
    }
    /**
     * The failure test of the method recordWinningBids,
     * the manager is stopped,
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_recordWinningBids_failure2() throws Exception {
        manager.stopManager();
        try {
            manager.recordWinningBids(1, new long[]{1, 2});
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }
    /**
     * The accuracy test of the method recordWinningBids.
     *
     * @throws Exception to JUnit
     */
    public void test_recordWinningBids_accuracy() throws Exception {
        manager.recordWinningBids(1, new long[]{1, 2, 3, 4});
    }
    /**
     * The failure test of the method advanceHostingSlot,
     * the manager is stopped,
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_advanceHostingSlot_failure() throws Exception {
        manager.stopManager();
        try {
            manager.advanceHostingSlot(1);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            //pass
        }
    }
    /**
     * The accuracy test of the method advanceHostingSlot,
     * the manager is stopped,
     * IllegalStateException is expected.
     *
     * @throws Exception to JUnit
     */
    public void test_advanceHostingSlot_accuracy() throws Exception {
        manager.advanceHostingSlot(1);
    }
    /**
     * The accuracy test of the stop manager.
     *
     * @throws Exception to JUnit
     */
    public void test_stopManager() throws Exception  {
        manager.stopManager();
        assertTrue("The manager can not be stopped.", manager.isStopped());
    }
}
