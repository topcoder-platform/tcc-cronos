/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.user.persistence.accuracytests.ejb;

import com.orpheus.user.persistence.ejb.UserProfileBean;
import junit.framework.TestCase;

import javax.ejb.SessionBean;

/**
 * Accuracy test cases for <code>UserProfileBean</code> class.
 *
 * @author tuenm
 * @version 1.0
 */
public class UserProfileBeanAccTests extends TestCase {

    /**
     * <p>
     * Accuracy test of the constructor.
     * </p>
     */
    public void testConstructor() {
        UserProfileBean bean = new UserProfileBean();
        // no error expected
    }

    /**
     * <p>
     * Test if the bean implements the right interface.
     * </p>
     */
    public void testInterface() {
        UserProfileBean bean = new UserProfileBean();
        assertTrue("Should implement the SessionBean interface.", bean instanceof SessionBean);
    }
}
