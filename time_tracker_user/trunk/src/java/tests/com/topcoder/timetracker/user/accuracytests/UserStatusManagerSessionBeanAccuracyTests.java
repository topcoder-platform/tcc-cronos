/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.timetracker.user.UserStatusManager;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerSessionBean;

/**
 * Accuracy test cases for class <code>UserStatusManagerSessionBean </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserStatusManagerSessionBeanAccuracyTests extends AbstractUserStatusManagerAccuracyTests {

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected UserStatusManager getTestInstance() throws Exception {
        return new UserStatusManagerSessionBean();
    }
}
