/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.user.UserStatusManager;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerDelegate;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerLocal;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerLocalHome;
import com.topcoder.timetracker.user.j2ee.UserStatusManagerSessionBean;

import junit.framework.TestCase;

/**
 * Accuracy test cases for class <code>UserStatusManagerDelegate </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserStatusManagerDelegateAccuracyTests extends AbstractUserStatusManagerAccuracyTests {
    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected UserStatusManager getTestInstance() throws Exception {
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        UserStatusManagerSessionBean bean = new UserStatusManagerSessionBean();

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("UserStatusManagerLocalHome",
                UserStatusManagerLocalHome.class, UserStatusManagerLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        return new UserStatusManagerDelegate("com.topcoder.timetracker." + "user.j2ee.UserStatusManagerDelegate");
    }

}
