/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;

import org.mockejb.jndi.MockContextFactory;

/**
 * Unit tests for the <code>LocalOrpheusMessageDataStore</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class LocalOrpheusMessageDataStoreTests extends OrpheusMessageDataStoreTests {
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
     * Retrieves the concrete subclass of <code>OrpheusMessageDataStore</code> to use for the test.
     *
     * @return the concrete subclass of <code>OrpheusMessageDataStore</code> to use for the test
     * @throws Exception if an unexpected exception occurs
     */
    OrpheusMessageDataStore getInstance() throws Exception {
        if (!getConfigManager().existsNamespace("local.data.store")) {
            getConfigManager().add("local_data_store.xml");
        }
        LocalOrpheusMessageDataStore store = new LocalOrpheusMessageDataStore("local.data.store");
        return store;
    }
}
