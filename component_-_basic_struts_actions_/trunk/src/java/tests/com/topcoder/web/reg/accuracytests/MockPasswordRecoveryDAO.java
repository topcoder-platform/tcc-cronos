/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.accuracytests;

import java.util.Date;

import com.topcoder.web.common.dao.PasswordRecoveryDAO;
import com.topcoder.web.common.model.PasswordRecovery;
import com.topcoder.web.common.model.User;

/**
 * Mock class for test.
 *
 * @author extra
 * @version 1.0
 */
public class MockPasswordRecoveryDAO implements PasswordRecoveryDAO {

    /**
     * Mock method.
     */
    public PasswordRecovery find(Long arg0) {
        PasswordRecovery pr = new PasswordRecovery();
        pr.setExpireDate(new Date(2099, 1, 1));
        pr.setId(arg0);
        pr.setUsed(true);
        pr.setNew(true);
        pr.setRecoveryAddress("re@topcoder.com");
        User user = new User();
        user.setId(10L);
        user.setHandle("handle");
        pr.setUser(user);
        return pr;
    }

    /**
     * Mock method.
     */
    public void saveOrUpdate(PasswordRecovery arg0) {
        arg0.setId(100L);
    }

}
