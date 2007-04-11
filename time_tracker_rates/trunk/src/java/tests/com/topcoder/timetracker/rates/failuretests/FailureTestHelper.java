/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rates.failuretests;

import java.io.File;
import java.util.Date;
import java.util.Iterator;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.mockrunner.mock.ejb.MockUserTransaction;
import com.topcoder.timetracker.rates.Rate;
import com.topcoder.timetracker.rates.ejb.LocalHomeRate;
import com.topcoder.timetracker.rates.ejb.LocalRate;
import com.topcoder.timetracker.rates.ejb.RateEjb;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * Test Helper class for failure testing.
 * </p>
 *
 * @author mittu
 * @version 3.2
 */
class FailureTestHelper {
    /**
     * Represents the test_files config.
     */
    public static final String CONFIG = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator + "failure" + File.separator + "config.xml";

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * <p>
     * Private constructor. No instances allowed.
     * </p>
     */
    private FailureTestHelper() {
    }

    /**
     * <p>
     * Loads the configuration from the given configuration file.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void addConfig() throws Exception {
        releaseConfigs();
        ConfigManager cm = ConfigManager.getInstance();
        cm.add(CONFIG);
    }

    /**
     * <p>
     * Releases the configurations.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    public static void releaseConfigs() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iterator = cm.getAllNamespaces(); iterator.hasNext();) {
            cm.removeNamespace((String) iterator.next());
        }
    }

    /**
     * <p>
     * Deploys the mock ejb for testing purpose.
     * </p>
     *
     * @param context
     *            the initial context to set.
     * @throws Exception
     *             exception to junit.
     */
    public static void mockEJBDeploy(InitialContext context) throws Exception {
        if (!deployed) {
            // Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one
            // EJB are deployed by the app server, so we don't need to do it.
            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(context);

            // we use MockTransaction outside of the app server
            MockUserTransaction mockTransaction = new MockUserTransaction();
            context.rebind("javax.transaction.UserTransaction", mockTransaction);

            // Create the deployment descriptor of the bean. Stateless and Stateful beans both use
            // SessionBeanDescriptor.
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(
                    "java:comp/env/rates", LocalHomeRate.class, LocalRate.class, RateEjb.class);

            mockContainer.deploy(beanDescriptor);
            deployed = true;
        }
    }

    /**
     * <p>
     * Undeploys the mock ejb.
     * </p>
     */
    public static void mockEJBRevert() {
        if (deployed) {
            MockContextFactory.revertSetAsInitial();
            deployed = false;
        }
    }

    /**
     * Creates a rate for testing.
     *
     * @param id
     *            the id of rate
     * @param rate
     *            the rate value
     * @return the rate bean
     */
    public static Rate createRate(long id, double rate) {
        Rate bean = new Rate();
        bean.setId(id);
        bean.setRate(rate);
        bean.setCreationDate(new Date());
        bean.setCreationUser("failure_tester");
        bean.setModificationDate(new Date());
        bean.setModificationUser("failure_tester");
        return bean;
    }
}