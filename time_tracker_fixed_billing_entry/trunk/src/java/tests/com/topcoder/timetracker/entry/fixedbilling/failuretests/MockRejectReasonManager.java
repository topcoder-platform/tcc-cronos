/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.failuretests;

import com.topcoder.timetracker.rejectreason.ejb.RejectReasonManager;

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
     */
    public MockRejectReasonManager(String namespace) {
        super(namespace);
    }

}
