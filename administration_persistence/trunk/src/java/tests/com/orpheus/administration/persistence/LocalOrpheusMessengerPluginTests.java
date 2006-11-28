/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

/**
 * Unit tests for the <code>LocalOrpheusMessengerPlugin</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class LocalOrpheusMessengerPluginTests extends OrpheusMessengerPluginTests {
    /**
     * Pre-test setup: initializes the database, configuration manager, and the MockEJB context.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void setUp() throws Exception {
        MockContextFactory.setAsInitial();

        MockContainer container = new MockContainer(new InitialContext());

        SessionBeanDescriptor descriptor =
            new SessionBeanDescriptor("MessageLocal", MessageLocalHome.class, MessageLocal.class, new MessageBean());
        container.deploy(descriptor);

        super.setUp();
    }

    /**
     * Post-test cleanup: restored the environment to the pre-test settings.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();

        MockContextFactory.revertSetAsInitial();
    }

    /**
     * Returns an instance of <code>OrpheusMessengerPlugin</code> to use for the test.
     *
     * @return an instance of <code>OrpheusMessengerPlugin</code> to use for the test
     * @throws Exception if an unexpected exception occurs
     */
    OrpheusMessengerPlugin getInstance() throws Exception {
        if (!getConfigManager().existsNamespace("local.messender.plugin")) {
            getConfigManager().add("local_messenger_plugin.xml");
        }
        return new LocalOrpheusMessengerPlugin("local.messenger.plugin");
    }

    /**
     * Tests the <code>ObjectFactoryHelpers.resolveLocalEjbReference</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_resolveLocalEjbReference() throws Exception {
        if (!getConfigManager().existsNamespace("local.messender.plugin")) {
            getConfigManager().add("local_messenger_plugin.xml");
        }

        assertNotNull("resolveLocalEjbReference should succeed",
                      ObjectFactoryHelpers.resolveLocalEjbReference("local.messenger.plugin"));
    }
}
