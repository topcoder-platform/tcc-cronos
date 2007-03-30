/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login;

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
 * <p>
 * Test Helper class for unit testing.
 * </p>
 *
 * @author tyrian
 * @version 1.0
 */
class TestHelper {
    /**
     * Represents the user profile key for testing.
     */
    static final String USER_PROFILE_KEY = "userProfile";

    /**
     * Represents the login success key for testing.
     */
    static final String LOGIN_SUCCESS_FWD = "loginSucceed";

    /**
     * Represents the user login failure key for testing.
     */
    static final String LOGIN_FAIL_FWD = "loginFail";

    /**
     * Represents the unchattable key for testing.
     */
    static final String UNCHATTABLE = "unchattable";

    /**
     * Represents the category key for testing.
     */
    static final String CATEGORY_KEY = "category";

    /**
     * Represents the role key for testing.
     */
    static final String ROLE_KEY = "role";

    /**
     * Represents the authentication url for testing.
     */
    static final String AUTH_URL = "http://locahost";

    /**
     * Represents the authentication context factory for testing.
     */
    static final String AUTH_CTX_FACTORY = "org.mockejb.jndi.MockContextFactory";

    /**
     * Represents the connection name for testing.
     */
    static final String CONNECTION_NAME = "informix_connect";

    /**
     * Represents the family name key for testing.
     */
    static final String FAMILY_NAME_KEY = "familyName";

    /**
     * Represents the last name key for testing.
     */
    static final String LAST_NAME_KEY = "lastName";

    /**
     * Represents the title key for testing.
     */
    static final String TITLE = "title";

    /**
     * Represents the email key for testing.
     */
    static final String EMAIL = "email";

    /**
     * Represents the company key for testing.
     */
    static final String COMPANY = "company";

    /**
     * Represents the test_files folder.
     */
    static final String TEST_FILES = System.getProperty("user.dir") + File.separator + "test_files"
            + File.separator;

    /**
     * Represents the unit tests folder.
     */
    static final String UNIT_TESTS = TEST_FILES + "unit_tests" + File.separator;

    /**
     * Represents whether the mock ejb to be deployed.
     */
    private static boolean deployed = false;

    /**
     * <p>
     * Private constructor. No instances allowed.
     * </p>
     */
    private TestHelper() {
    }

    /**
     * <p>
     * Loads the configuration from the given configuration file.
     * </p>
     *
     * @param acc
     *            flag for accuracy test
     * @param file
     *            the xml to load
     * @throws Exception
     *             exception to junit.
     */
    static void loadConfigs(boolean acc, String file) throws Exception {
        releaseConfigs();
        ConfigManager cm = ConfigManager.getInstance();
        if (acc) {
            cm.add(file);
        } else {
            cm.add(UNIT_TESTS + file);
        }
    }

    /**
     * <p>
     * Releases the configurations.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    static void releaseConfigs() throws Exception {
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
     * @throws Exception
     *             exception to junit.
     */
    static void deployEJB() throws Exception {
        if (!deployed) {
            /*
             * Deploy EJBs to the MockContainer if we run outside of the app server In cactus mode all but one EJB
             * are deployed by the app server, so we don't need to do it.
             */

            MockContextFactory.setAsInitial();

            // Create an instance of the MockContainer and pass the JNDI context that
            // it will use to bind EJBs.
            MockContainer mockContainer = new MockContainer(new InitialContext());

            /*
             * Create the deployment descriptor of the bean. Stateless and Stateful beans both use
             * SessionBeanDescriptor.
             */
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(LoginRemoteHome.EJB_REF_NAME,
                    LoginRemoteHome.class, LoginRemote.class, MockBean.class);

            mockContainer.deploy(beanDescriptor);
            deployed = true;
        }
    }

    /**
     * <p>
     * Undeploys the mock ejb.
     * </p>
     */
    static void undeployEJB() {
        if (deployed) {
            MockContextFactory.revertSetAsInitial();
            deployed = false;
        }
    }
}