/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.user.accuracytests;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.timetracker.user.UserTypeManager;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerDelegate;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerLocal;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerLocalHome;
import com.topcoder.timetracker.user.j2ee.UserTypeManagerSessionBean;

/**
 * Accuracy test cases for class <code>UserTypeManagerImpl </code>.
 * @author Chenhong
 * @version 3.2.1
 */
public class UserTypeManagerDelegateAccuracyTests extends AbstractUserTypeManagerAccuracyTests {

    /**
     * Get test instance
     * @return test instance.
     * @throws Exception
     *             to junit.
     */
    protected UserTypeManager getTestInstance() throws Exception {

        /*
         * We need to set MockContextFactory as our JNDI provider. This method sets the necessary system
         * properties.
         */
        MockContextFactory.setAsInitial();

        // create the initial context that will be used for binding EJBs
        Context context = new InitialContext();

        // Create an instance of the MockContainer
        MockContainer mockContainer = new MockContainer(context);

        // we use MockTransaction outside of the app server
        MockUserTransaction mockTransaction = new MockUserTransaction();
        context.rebind("javax.transaction.UserTransaction", mockTransaction);

        UserTypeManagerSessionBean bean = new UserTypeManagerSessionBean();

        /*
         * Create deployment descriptor of our sample bean. MockEjb uses it instead of XML deployment descriptors
         */
        SessionBeanDescriptor sampleServiceDescriptor = new SessionBeanDescriptor("UserTypeManagerLocalHome",
                UserTypeManagerLocalHome.class, UserTypeManagerLocal.class, bean);

        // Deploy operation creates Home and binds it to JNDI
        mockContainer.deploy(sampleServiceDescriptor);

        return new UserTypeManagerDelegate("com.topcoder.timetracker." + "user.j2ee.UserTypeManagerDelegate");
    }

}
