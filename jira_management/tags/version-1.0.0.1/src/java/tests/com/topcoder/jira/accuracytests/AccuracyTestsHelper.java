/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.accuracytests;

import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.topcoder.jira.managers.DefaultJiraManager;

/**
 * This class holds common utility methods used during testing.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
final class AccuracyTestsHelper {

    /**
     * Key used to retrieve JIRA URL from system properties.
     */
    private static final String URL_CONFIG_KEY = "tcj-url";

    /**
     * Key used to retrieve user name from system properties.
     */
    private static final String USER_CONFIG_KEY = "tcj-user";

    /**
     * Key used to retrieve user password from system properties.
     */
    private static final String PASSWORD_CONFIG_KEY = "tcj-pwd";

    /**
     * Key used to retrieve guest name from system properties.
     */
    private static final String GUEST_CONFIG_KEY = "tcj-guest";

    /**
     * Key used to retrieve guest password from system properties.
     */
    private static final String GUEST_PASSWORD_CONFIG_KEY = "tcj-guest-pwd";

    /**
     * Key used to retrieve projects prefix from system properties.
     */
    private static final String PREFIX_CONFIG_KEY = "tcj-prefix";

    /**
     * Default URL. Used if configuration doesn't contain URL.
     */
    private static final String DEFAULT_URL = "http://localhost:8090/rpc/soap/jirasoapservice-v2";

    /**
     * Default user name. Used if configuration doesn't contain such name.
     */
    private static final String DEFAULT_USER = "root";

    /**
     * Default user password. Used if configuration doesn't contain such password.
     */
    private static final String DEFAULT_PASSWORD = "123";

    /**
     * Default guest name. Used if configuration doesn't contain such name.
     */
    private static final String DEFAULT_GUEST_NAME = "guest";

    /**
     * Default guest password. Used if configuration doesn't contain such password.
     */
    private static final String DEFAULT_GUEST_PASSWORD = "guest";

    /**
     * Default projects prefix. Used if configuration doesn't contain such prefix.
     */
    private static final String DEFAULT_PREFIX = "TCJ";

    /**
     * JIRA URL.
     * <p>
     * Initialized lazily.
     */
    private static String url = null;

    /**
     * JIRA user name.
     * <p>
     * Initialized lazily.
     */
    private static String userName = null;

    /**
     * JIRA user password.
     * <p>
     * Initialized lazily.
     */
    private static String password = null;

    /**
     * JIRA guest name.
     * <p>
     * Initialized lazily.
     */
    private static String guestName = null;

    /**
     * JIRA guest password.
     * <p>
     * Initialized lazily.
     */
    private static String guestPassword = null;

    /**
     * Projects prefix.
     * <p>
     * Initialized lazily.
     */
    private static String prefix = null;

    /**
     * This class couldn't be instantiated.
     */
    private AccuracyTestsHelper() {
        // do nothing
    }

    /**
     * Returns configured JIRA URL.
     * <p>
     * If not defined then 'http://localhost:8090/rpc/soap/jirasoapservice-v2' will be returned.
     *
     * @return configured JIRA URL.
     */
    static String getURL() {
        if (url == null) {
            // initialize field
            url = System.getProperty(URL_CONFIG_KEY);
            if (url == null) {
                url = DEFAULT_URL;
            }
        }
        return url;
    }

    /**
     * Returns configured name of the JIRA user.
     * <p>
     * If not defined then 'root' will be returned.
     *
     * @return configured name of the JIRA user.
     */
    static String getUserName() {
        if (userName == null) {
            // initialize field
            userName = System.getProperty(USER_CONFIG_KEY);
            if (userName == null) {
                userName = DEFAULT_USER;
            }
        }
        return userName;
    }

    /**
     * Returns configured password of the JIRA user.
     * <p>
     * If not defined then '123' will be returned.
     *
     * @return configured password of the JIRA user.
     */
    static String getPassword() {
        if (password == null) {
            // initialize field
            password = System.getProperty(PASSWORD_CONFIG_KEY);
            if (password == null) {
                password = DEFAULT_PASSWORD;
            }
        }
        return password;
    }

    /**
     * Returns configured name of the JIRA guest.
     * <p>
     * If not defined then 'guest' will be returned.
     *
     * @return configured name of the JIRA guest.
     */
    static String getGuestName() {
        if (guestName == null) {
            // initialize field
            guestName = System.getProperty(GUEST_CONFIG_KEY);
            if (guestName == null) {
                guestName = DEFAULT_GUEST_NAME;
            }
        }
        return guestName;
    }

    /**
     * Returns configured password of the JIRA guest.
     * <p>
     * If not defined then 'guest' will be returned.
     *
     * @return configured password of the JIRA guest.
     */
    static String getGuestPassword() {
        if (guestPassword == null) {
            // initialize field
            guestPassword = System.getProperty(GUEST_PASSWORD_CONFIG_KEY);
            if (guestPassword == null) {
                guestPassword = DEFAULT_GUEST_PASSWORD;
            }
        }
        return guestPassword;
    }

    /**
     * Returns prefix that will be added all created project names and keys.
     * <p>
     * If not defined then 'TCJ' will be returned.
     *
     * @return configured prefix.
     */
    static String getPrefix() {
        if (prefix == null) {
            // initialize field
            prefix = System.getProperty(PREFIX_CONFIG_KEY);
            if (prefix == null) {
                prefix = DEFAULT_PREFIX;
            }
        }
        return prefix;
    }

    /**
     * Removes all created projects and logs out.
     *
     * @throws Exception if error occurs.
     */
    static void cleanJiraSystem() throws Exception {
        DefaultJiraManager manager = new DefaultJiraManager();
        // login
        String token = manager.login(getUserName(), getPassword());
        // get projects
        RemoteProject[] projects = manager.getService().getProjectsNoSchemes(token);
        // remove projects where key starts with our prefix
        getPrefix();

        for (RemoteProject project : projects) {
            if (project.getKey().startsWith(prefix)) {
                manager.deleteProject(token, project.getKey());
            }
        }
        // logout
        manager.logout(token);
    }
}
