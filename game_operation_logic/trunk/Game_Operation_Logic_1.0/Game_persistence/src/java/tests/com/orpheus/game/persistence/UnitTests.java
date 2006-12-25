/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence;

import com.orpheus.game.persistence.dao.SQLServerGameDataDAOCtorUnitTests;
import com.orpheus.game.persistence.dao.SQLServerGameDataDAOUnitTests;
import com.orpheus.game.persistence.ejb.GameDataBeanUnitTests;
import com.orpheus.game.persistence.entities.*;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * <p>
 * This test case aggregates all Unit test cases.
 * </p>
 */
public class UnitTests extends TestCase {
    /**
     * return test suite to junit
     *
     * @return Test suite to junit
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();
        suite.addTestSuite(GameDataBeanUnitTests.class);
        suite.addTestSuite(BallColorImplUnitTests.class);
        suite.addTestSuite(DomainImplUnitTests.class);
        suite.addTestSuite(DomainTargetImplUnitTests.class);
        suite.addTestSuite(DownloadDataImplUnitTests.class);
        suite.addTestSuite(GameImplUnitTests.class);
        suite.addTestSuite(HostingBlockImplTests.class);
        suite.addTestSuite(HostingSlotImplUnitTests.class);
        suite.addTestSuite(ImageInfoImplUnitTests.class);
        suite.addTestSuite(SlotCompletionImplUnitTests.class);

        suite.addTestSuite(HelperTests.class);
        suite.addTestSuite(DuplicateEntryExceptionUnitTests.class);
        suite.addTestSuite(EntryNotFoundExceptionUnitTests.class);
        suite.addTestSuite(InstantiationExceptionUnitTests.class);
        suite.addTestSuite(InvalidEntryExceptionUnitTests.class);
        suite.addTestSuite(PersistenceExceptionUnitTests.class);
        suite.addTestSuite(SQLServerGameDataDAOCtorUnitTests.class);
        suite.addTestSuite(SQLServerGameDataDAOUnitTests.class);
        suite.addTestSuite(GameDataDAOFactoryUnitTests.class);
        suite.addTestSuite(LocalCustomDownloadSourceUnitTests.class);
        suite.addTestSuite(RemoteCustomDownloadSourceUnitTests.class);

        return suite;
    }
}
