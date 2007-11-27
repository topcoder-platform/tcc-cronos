/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import com.topcoder.timetracker.user.UserTypeManager;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerSessionBean;

/**
 * Accuracy test cases for class <code>UserTypeManagerSessionBean </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserTypeManagerSessionBeanAccuracyTests extends AbstractUserTypeManagerAccuracyTests {

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected UserTypeManager getTestInstance() throws Exception {
        return new UserTypeManagerSessionBean();
    }

}
