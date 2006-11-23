/**
 *
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.orpheus.auction.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>This test case aggregates all Accuracy test cases.</p>
 *
 * @author waits
 * @version 1.0
 */
public class AccuracyTests extends TestCase {

    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(DefaultAuctionTranslatorAccTests.class);
        suite.addTestSuite(LocalCustomAuctionPersistenceAccTests.class);
        suite.addTestSuite(RemoteCustomAuctionPersistenceAccTests.class);
        suite.addTestSuite(SQLServerAuctionDAOAccTests.class);
        return suite;
    }

}
