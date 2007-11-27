/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.user.UserStatusManager;
import com.topcoder.timetracker.user.UserStatusManagerImpl;

/**
 * Accuracy test cases for class <code>UserStatusManagerImpl </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserStatusManagerImplAccuracyTests extends AbstractUserStatusManagerAccuracyTests {

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected UserStatusManager getTestInstance() throws Exception {
        DBConnectionFactory dbFactory = AccuracyTestHelper.getDBConnectionFactory();
        return new UserStatusManagerImpl(AccuracyTestHelper.getUserStatusDAO(dbFactory));
    }
}
