/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * Unit tests for the <code>RemoteOrpheusMessengerPlugin</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RemoteOrpheusMessengerPluginTests extends OrpheusMessengerPluginTests {
    /**
     * Returns an instance of <code>OrpheusMessengerPlugin</code> to use for the test.
     *
     * @return an instance of <code>OrpheusMessengerPlugin</code> to use for the test
     * @throws Exception if an unexpected exception occurs
     */
    OrpheusMessengerPlugin getInstance() throws Exception {
        if (!getConfigManager().existsNamespace("remote.messender.plugin")) {
            getConfigManager().add("remote_messenger_plugin.xml");
        }
        return new RemoteOrpheusMessengerPlugin("remote.messenger.plugin");
    }

    /**
     * Tests the <code>ObjectFactoryHelpers.resolveRemoteEjbReference</code> method.
     *
     * @throws Exception if an unexpected exception occurs
     */
    public void test_resolveRemoteEjbReference() throws Exception {
        if (!getConfigManager().existsNamespace("remote.messender.plugin")) {
            getConfigManager().add("remote_messenger_plugin.xml");
        }

        assertNotNull("resolveRemoteEjbReference should succeed",
                      ObjectFactoryHelpers.resolveRemoteEjbReference("remote.messenger.plugin"));
    }
}
