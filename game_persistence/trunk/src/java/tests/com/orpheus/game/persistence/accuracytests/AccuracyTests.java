/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.game.persistence.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.orpheus.game.persistence.accuracytests.dao.SQLServerGameDataDAOAccTests;
import com.orpheus.game.persistence.accuracytests.ejb.GameDataBeanAccTests;
import com.orpheus.game.persistence.accuracytests.entities.BallColorImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.DomainImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.DownloadDataImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.DomainTargetImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.GameImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.HostingBlockImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.HostingSlotImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.ImageInfoImplAccTests;
import com.orpheus.game.persistence.accuracytests.entities.SlotCompletionImplAccTests;

/**
 * <p>
 * This class aggregates all accuracy test cases.
 * </p>
 *
 * @author tuenm
 * @version 1.0
 */
public class AccuracyTests extends TestCase {
    /**
     * <p>
     * This test suite contains all accuracy test cases.
     * </p>
     *
     * @return a test suite contains all accuracy test cases.
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(GameDataDAOFactoryAccTests.class);
        suite.addTestSuite(LocalCustomDownloadSourceAccTests.class);
        suite.addTestSuite(RemoteCustomDownloadSourceAccTests.class);

        suite.addTestSuite(SQLServerGameDataDAOAccTests.class);
        suite.addTestSuite(GameDataBeanAccTests.class);

        suite.addTestSuite(BallColorImplAccTests.class);
        suite.addTestSuite(DomainImplAccTests.class);
        suite.addTestSuite(DomainTargetImplAccTests.class);
        suite.addTestSuite(DownloadDataImplAccTests.class);
        suite.addTestSuite(GameImplAccTests.class);
        suite.addTestSuite(HostingBlockImplAccTests.class);
        suite.addTestSuite(HostingSlotImplAccTests.class);
        suite.addTestSuite(ImageInfoImplAccTests.class);
        suite.addTestSuite(SlotCompletionImplAccTests.class);

        return suite;
    }
}
