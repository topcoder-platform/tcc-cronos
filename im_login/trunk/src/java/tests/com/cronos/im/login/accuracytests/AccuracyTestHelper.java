/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.im.login.accuracytests;

import java.lang.reflect.Method;
import java.util.Iterator;

import javax.naming.InitialContext;

import org.mockejb.MockContainer;
import org.mockejb.SessionBeanDescriptor;
import org.mockejb.jndi.MockContextFactory;

import com.topcoder.security.login.LoginRemote;
import com.topcoder.security.login.LoginRemoteHome;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.UnknownNamespaceException;

/**
 * <p>
 * Defines helper methods used in accuracy tests.
 * </p>
 *
 * @author stylecheck
 * @version 1.0
 */

public class AccuracyTestHelper {

    /** Current User Directory. */
    public static final String TEMP = System.getProperty("user.dir");

    /** Test Files Directory. */
    public static final String TEST_FILES_DIR = TEMP + "/test_files/";

    /** Correct GraphImageGeneratorJob config file. */
    public static final String IM_LOGIN_CONFIG = TEMP + "/test_files/accuracy/config.xml";

    /** Request parameter for user. */
    public static final String USER_PARAM = "user";

    /** Request parameter for password. */
    public static final String PWD_PARAM = "pwd";

    /** Request parameter for category. */
    public static final String CAT_PARAM = "cat";

    /** Status representing the deployment of the container. */
    private static boolean initialized = false;

    /**
     * <p>
     * Creates a new instance of <code>AccuracyTestHelper</code> class. The private constructor prevents the
     * creation of a new instance.
     * </p>
     */
    private AccuracyTestHelper() {
        // Empty constructor
    }

    /**
     * <p>
     * Adds the valid config files for testing.
     * </p>
     *
     * @throws Exception
     *             unexpected exception.
     */
    public static void loadConfig() throws Exception {
        releaseConfig();
        ConfigManager configManager = ConfigManager.getInstance();
        configManager.add(IM_LOGIN_CONFIG);
    }

    /**
     * <p>
     * Clears the config instance.
     * </p>
     */
    public static void releaseConfig() {
        ConfigManager configManager = ConfigManager.getInstance();
        // remove all the name space from the config manager.
        for (Iterator iter = configManager.getAllNamespaces(); iter.hasNext();) {
            try {
                configManager.removeNamespace((String) iter.next());
            } catch (UnknownNamespaceException e) {
                // ignore
            }
        }
    }

    /**
     * <p>
     * Reloads the entire config file using the link to file given.
     * </p>
     *
     * @param link
     *            the location configuration file to be taken.
     */
    public static void reloadConfig(String link) {
        releaseConfig();
        ConfigManager configManager = ConfigManager.getInstance();
        try {
            configManager.add(TEST_FILES_DIR + "accuracy/" + link);
        } catch (ConfigManagerException e) {
            // ignore
        }
    }

    /**
     * <p>
     * This method returns a <code>Method</code> using Reflection.
     * </p>
     *
     * @param className
     *            name of the class.
     * @param methodName
     *            name of the method.
     * @param parameterTypes
     *            parameter types of the method.
     *
     * @return the <code>Method</code> instance.
     */
    public static Method getMethod(String className, String methodName, Class[] parameterTypes) {
        Method method = null;
        try {
            method = Class.forName(className).getDeclaredMethod(methodName, parameterTypes);

        } catch (SecurityException e) {
            // ignore
        } catch (NoSuchMethodException e) {
            // ignore
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return method;
    }

    /**
     * <p>
     * Initializes the mock container. This method uses MockEjb container for deployment.
     * </p>
     *
     * @throws Exception
     *             exception to junit.
     */
    static void initializeMockEjbContainer() throws Exception {
        if (!initialized) {
            // initializes the mock ejb container and SampleLoginBean is returned as the bean object to calling
            // method.
            MockContextFactory.setAsInitial();
            MockContainer mockContainer = new MockContainer(new InitialContext());
            SessionBeanDescriptor beanDescriptor = new SessionBeanDescriptor(LoginRemoteHome.EJB_REF_NAME,
                    LoginRemoteHome.class, LoginRemote.class, SampleLoginBean.class);
            mockContainer.deploy(beanDescriptor);
            initialized = true;
        }
    }

}
