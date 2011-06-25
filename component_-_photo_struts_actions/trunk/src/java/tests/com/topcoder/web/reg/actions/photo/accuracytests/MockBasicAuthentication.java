/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.photo.accuracytests;

import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockBasicAuthentication extends BasicAuthentication {

    /**
     * Mock method.
     */
    public User getActiveUser() {
        return new User() {
            public boolean isAnonymous() {
                return false;
            }

            public String getUserName() {
                return null;
            }

            public String getPassword() {
                return null;
            }

            public long getId() {
                return 10;
            }
        };
    }
}
