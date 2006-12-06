/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.failuretests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Failure test cases.
 * </p>
 * @author waits
 * @version 1.0
 */
public class FailureTests extends TestCase {
    /**
     * return test suite to junit
     *
     * @return Test suite to junit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(GameDataBeanTest.class);
        suite.addTestSuite(BallColorImplTest.class);
        suite.addTestSuite(DomainImplTest.class);
        suite.addTestSuite(DomainTargetImplTest.class);
        suite.addTestSuite(DownloadDataImplTest.class);
        suite.addTestSuite(GameImplTest.class);
        suite.addTestSuite(HostingBlockImplTest.class);
        suite.addTestSuite(HostingSlotImplTest.class);
        suite.addTestSuite(ImageInfoImplTest.class);
        suite.addTestSuite(SlotCompletionImplTest.class);

//        suite.addTestSuite(SQLServerGameDataDAOTest.class);
        suite.addTestSuite(GameDataDAOFactoryTest.class);
        suite.addTestSuite(LocalCustomDownloadSourceTest.class);
        suite.addTestSuite(RemoteCustomDownloadSourceTest.class);
        return suite;
    }
}
