/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;
import com.topcoder.timetracker.rejectreason.ejb.RejectReasonDAOConfigurationException;

/**
 * A mock RejectReasonManager for testing.
 *
 * @author mittu
 * @version 1.0
 */
public class MockRejectReasonManager extends RejectReasonManager {

    /**
     * Creates the MockRejectReasonManager.
     *
     * @param namespace
     * @throws RejectReasonDAOConfigurationException
     */
    public MockRejectReasonManager(String namespace) throws RejectReasonDAOConfigurationException {
        super(namespace);
    }

}
