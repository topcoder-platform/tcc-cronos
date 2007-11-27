/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.timetracker.user.UserTypeManager;
import com.topcoder.timetracker.user.UserTypeManagerImpl;

/**
 * Accuracy test cases for class <code>UserTypeManagerImpl </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserTypeManagerImplAccuracyTests extends AbstractUserTypeManagerAccuracyTests {

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected UserTypeManager getTestInstance() throws Exception {
        DBConnectionFactory dbFactory = AccuracyTestHelper.getDBConnectionFactory();
        return new UserTypeManagerImpl(AccuracyTestHelper.getUserTypeDAO(dbFactory));
    }
}
