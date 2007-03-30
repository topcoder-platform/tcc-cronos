/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.failuretests;

import java.io.File;
import java.util.Iterator;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.security.login.LoginRemote;
import com.topcoder.security.login.LoginRemoteHome;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for failure test cases.
 *
 * @author mittu
 * @version 1.0
 */
public class FailureTestHelper {

    /**
     * <p>
     * Represents the user request parameter.
     * </p>
     */
    static final String USER_REQUEST_PARAMETER = "user";

    /**
     * <p>
     * Represents the password request parameter.
     * </p>
     */
    static final String PASSWORD_REQUEST_PARAMETER = "pwd";

    /**
     * <p>
     * Represents the category request parameter.
     * </p>
     */
    static final String CATEGORY_REQUEST_PARAMETER = "cat";

    /**
     * <p>
     * Represents the failure action forward page.
     * </p>
     */
    static final String FAILURE_ACTION_FORWARD = "loginFail";

    /**
     * <p>
     * Represents the type request parameter.
     * </p>
     */
    static final String TYPE_REQUEST_PARAMETER = "type";

    /**
     * Represents the deployment status of mock ejb.
     */
    private static boolean mockEJBDeployed = false;

    /**
     * <p>
     * Private constructor.
     * </p>
     */
    private FailureTestHelper() {

    }

    /**
     * <p>
     * Loads the given configuration file.
     * </p>
     *
     * @param fileName
     *            The file to be loaded
     *
     * @throws Exception
     *             to junit.
     */
    static void loadConfigFile(String fileName) throws Exception {
        releaseConfigFiles();
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add("failure_tests" + File.separator + fileName);
    }

    /**
     * <p>
     * Releases all the configurations.
     * </p>
     *
     * @throws Exception
     *             to junit.
     */
    static void releaseConfigFiles() throws Exception {
        ConfigManager configManager = ConfigManager.getInstance();
        for (Iterator iterator = configManager.getAllNamespaces(); iterator.hasNext();) {
            configManager.removeNamespace((String) iterator.next());
        }
    }

    /**
     * Deploys the MockLoginBean in the place of LoginBean in MockEjBContainer. This method is used to test all ejb
     * related functionalities.
     *
     * @throws Exception
     *             to junit.
     */
    static void deployMockEJB() throws Exception {
        if (!mockEJBDeployed) {
            // deploy ejb's to MockContainer.
            MockContextFactory.setAsInitial();
            MockContainer mockContainer = new MockContainer(new InitialContext());
            // Instead of LoginRemote mock ejb containere returns MockLoginBean now.
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(LoginRemoteHome.EJB_REF_NAME,
                    LoginRemoteHome.class, LoginRemote.class, MockLoginBean.class);
            mockContainer.deploy(beanDescriptor);
            // change deployment status. To avoid unnecessary deployment.
            mockEJBDeployed = true;
        }
    }

    /**
     * Undeploys the mock ejb if it is deployed.
     */
    static void undeployMockEJB() {
        if (mockEJBDeployed) {
            MockContextFactory.revertSetAsInitial();
            mockEJBDeployed = false;
        }
    }

}
