/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.failuretests;

import com.topcoder.web.common.dao.UserDAO;
import com.topcoder.web.common.model.User;

import org.hibernate.HibernateException;

import java.util.List;


/**
 * Mock for failure!
 *
 * @author $author$
 * @version $Revision$
  */
public class MockUserDAO implements UserDAO {
    private boolean throwException;

    /**
     * Creates a new MockUserDAO object.
     *
     * @param throwException Mock for failure!
     */
    public MockUserDAO(boolean throwException) {
        this.throwException = throwException;
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     *
     * @return Mock for failure!
     */
    public User find(Long arg0) {
        if (throwException) {
            throw new HibernateException("failure");
        }

        return null;
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     * @param arg1 Mock for failure!
     *
     * @return Mock for failure!
     */
    public User find(String arg0, boolean arg1) {
        throw new HibernateException("failure");
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     * @param arg1 Mock for failure!
     * @param arg2 Mock for failure!
     *
     * @return Mock for failure!
     */
    public User find(String arg0, boolean arg1, boolean arg2) {
        throw new HibernateException("failure");
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     * @param arg1 Mock for failure!
     * @param arg2 Mock for failure!
     * @param arg3 Mock for failure!
     *
     * @return Mock for failure!
     */
    public List find(String arg0, String arg1, String arg2, String arg3) {
        throw new HibernateException("failure");
    }

    /**
     * Mock for failure!
     *
     * @param arg0 Mock for failure!
     */
    public void saveOrUpdate(User arg0) {
        
    }

	@Override
	public boolean canChangeHandle(String handle) {
		return false;
	}

	@Override
	public User find(String email) {
		return null;
	}
}
