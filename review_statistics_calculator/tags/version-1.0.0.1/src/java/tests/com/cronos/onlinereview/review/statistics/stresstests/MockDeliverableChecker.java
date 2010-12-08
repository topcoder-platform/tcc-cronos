/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.DeliverableChecker;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockDeliverableChecker implements DeliverableChecker {

    /**
     * Ctor.
     */
    public MockDeliverableChecker() {
    }

    /**
     * Do nothing.
     *
     * @param arg0
     *            the arg
     * @throws DeliverableCheckingException
     *             to JUnit
     */
    public void check(Deliverable arg0) throws DeliverableCheckingException {
    }

}
