/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence;

/**
 * Unit tests for the <code>RemoteOrpheusMessageDataStore</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RemoteOrpheusMessageDataStoreTests extends OrpheusMessageDataStoreTests {
    /**
     * Retrieves the concrete subclass of <code>OrpheusMessageDataStore</code> to use for the test.
     *
     * @return the concrete subclass of <code>OrpheusMessageDataStore</code> to use for the test
     * @throws Exception if an unexpected exception occurs
     */
    OrpheusMessageDataStore getInstance() throws Exception {
        if (!getConfigManager().existsNamespace("remote.data.store")) {
            getConfigManager().add("remote_data_store.xml");
        }
        return new RemoteOrpheusMessageDataStore("remote.data.store");
    }
}
