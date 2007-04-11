/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.im.persistence.stresstests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This test case aggregates all stress test cases for the <i>IM Persistence </i> component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class StressTests extends TestCase {
    /**
     * Returns the stress test cases for the <i>IM Persistence </i> component.
     *
     * @return the stress test cases for the <i>IM Persistence </i> component
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite();

        suite.addTestSuite(TestInformixRoleCategoryPersistenceStress.class);

        suite.addTestSuite(TestInformixProfileKeyManagerStressWithCreate.class);
        suite.addTestSuite(TestInformixProfileKeyManagerStressWithAllGet.class);

        suite.addTestSuite(TestUnregisteredChatUserProfileInformixPersistenceStress.class);
        suite.addTestSuite(TestUnregisteredChatUserProfileInformixPersistenceCreateStress.class);

        suite.addTestSuite(TestRegisteredChatUserProfileInformixPersistenceStress.class);

        return suite;
    }
}