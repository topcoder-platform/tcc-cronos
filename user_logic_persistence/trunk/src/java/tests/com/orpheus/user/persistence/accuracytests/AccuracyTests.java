/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests;

import com.orpheus.user.persistence.accuracytests.ejb.ConfirmationMessageDTOAccTests;
import com.orpheus.user.persistence.accuracytests.ejb.PendingConfirmationBeanAccTests;
import com.orpheus.user.persistence.accuracytests.ejb.UserProfileBeanAccTests;
import com.orpheus.user.persistence.accuracytests.ejb.UserProfileDTOAccTests;
import com.orpheus.user.persistence.accuracytests.impl.AdminAccTests;
import com.orpheus.user.persistence.accuracytests.impl.PlayerAccTests;
import com.orpheus.user.persistence.accuracytests.impl.SponsorAccTests;
import com.orpheus.user.persistence.accuracytests.impl.UserAccTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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

        suite.addTestSuite(DAOFactoryTestAccTests.class);
        suite.addTestSuite(LocalOrpheusPendingConfirmationAccTests.class);
        suite.addTestSuite(LocalOrpheusUserProfilePersistenceAccTests.class);
        suite.addTestSuite(RemoteOrpheusPendingConfirmationAccTests.class);
        suite.addTestSuite(RemoteOrpheusUserProfilePersistenceAccTests.class);

        suite.addTestSuite(UserProfileDTOAccTests.class);
        suite.addTestSuite(ConfirmationMessageDTOAccTests.class);
        suite.addTestSuite(PendingConfirmationBeanAccTests.class);
        suite.addTestSuite(UserProfileBeanAccTests.class);

        suite.addTestSuite(AdminAccTests.class);
        suite.addTestSuite(PlayerAccTests.class);
        suite.addTestSuite(SponsorAccTests.class);
        suite.addTestSuite(UserAccTests.class);

        return suite;
    }
}
