/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.failuretests.managers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.DefaultJiraManager;
import com.topcoder.jira.managers.JiraManagerConfigurationException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

/**
 * Failure tests for <code>DefaultJiraManager</code> class.
 *
 * @author Thinfox
 * @version 1.0
 */
public class DefaultJiraManagerFailureTests extends TestCase {

    /**
     * JIRA URL.
     */
    private static String url = System.getProperty("tcj-url");

    /**
     * JIRA user name.
     */
    private static String userName = System.getProperty("tcj-user");

    /**
     * JIRA user password.
     */
    private static String password = System.getProperty("tcj-pwd");

    /**
     * JIRA guest name.
     */
    private static String guestName = System.getProperty("tcj-guest");

    /**
     * JIRA guest password.
     */
    private static String guestPassword = System.getProperty("tcj-guest-pwd");

    /**
     * Projects prefix.
     */
    private static String prefix = System.getProperty("tcj-prefix");

    static {
        if (url == null) {
            url = "http://localhost:8090/rpc/soap/jirasoapservice-v2";
        }

        if (prefix == null) {
            prefix = "TCJ";
        }

        if (userName == null) {
            userName = "root";
        }

        if (password == null) {
            password = "123";
        }

        if (guestName == null) {
            guestName = "guest";
        }

        if (guestPassword == null) {
            guestPassword = "guest";
        }
    }

    /**
     * The default instance of <code>JiraProject</code> used for tests.
     */
    private JiraProject project;

    /**
     * The default instance of <code>JiraVersion</code> used for tests.
     */
    private JiraVersion version;

    /**
     * The default instance of <code>DefaultJiraManager</code> used for tests.
     */
    private DefaultJiraManager mgr;

    /**
     * The default instance of <code>DefaultJiraManager</code> used to test connection issues.
     */
    private DefaultJiraManager invalidMgr;

    /**
     * The default security token needed to perform SOAP operation.
     */
    private String securityToken;

    /**
     * The defauult configuration object used to hold configuration data.
     */
    private ConfigurationObject configuration;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if error occurs.
     */
    protected void setUp() throws Exception {
        cleanJiraSystem();
        configuration = getConfig();
        project = getJiraProject();
        version = new JiraVersion();
        version.setName("version1");

        invalidMgr = new DefaultJiraManager("http://localhost:9999/some?invalid=url", null, null, null, null);

        mgr = new DefaultJiraManager();
        mgr.setLog(null);
        securityToken = mgr.login(userName, password);
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception if error occurs.
     */
    protected void tearDown() throws Exception {
        try {
            mgr.logout(securityToken);
        } catch (JiraManagerException e) {
            // ignore
        }

        cleanJiraSystem();
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the notificationSchemes map is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_EmptyNotificationSchemes() throws Exception {
        try {
            new DefaultJiraManager(null, null, new HashMap<ComponentType, String>(), null, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the permissionSchemes map is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_EmptyPermissionSchemes() throws Exception {
        try {
            new DefaultJiraManager(null, null, null, new HashMap<ComponentType, String>(), null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the issueSecuritySchemes map is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_EmptyIssueSecuritySchemes() throws Exception {
        try {
            new DefaultJiraManager(null, null, null, null, new HashMap<ComponentType, String>());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the map contains a null value.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_NullMapValue() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping = new HashMap<ComponentType, String>();
        mapping.put(ComponentType.APPLICATION, "Custom Component Permissions Scheme");
        mapping.put(ComponentType.CUSTOM_COMPONENT, "Generic Component Permission Scheme");
        mapping.put(ComponentType.GENERIC_COMPONENT, null);

        try {
            new DefaultJiraManager(null, null, null, mapping, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the map contains an empty value.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_EmptyMapValue() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping = new HashMap<ComponentType, String>();
        mapping.put(ComponentType.APPLICATION, "Client Projects");
        mapping.put(ComponentType.CUSTOM_COMPONENT, "Custom Component Permission Scheme");
        mapping.put(ComponentType.GENERIC_COMPONENT, " ");

        try {
            new DefaultJiraManager(null, null, null, mapping, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when some key is missing.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_MissingKey() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping = new HashMap<ComponentType, String>();
        mapping.put(ComponentType.APPLICATION, "Client Projects");
        mapping.put(ComponentType.CUSTOM_COMPONENT, "Custom Component Permission Scheme");

        try {
            new DefaultJiraManager(null, null, null, mapping, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when some redundant key exists.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_RedundantKey() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping = new HashMap<ComponentType, String>();
        mapping.put(ComponentType.APPLICATION, "Client Projects");
        mapping.put(ComponentType.CUSTOM_COMPONENT, "Custom Component Permission Scheme");
        mapping.put(ComponentType.GENERIC_COMPONENT, "Generic Component Permission Scheme");
        mapping.put(null, "Custom Component Permission Scheme");

        try {
            new DefaultJiraManager(null, null, null, mapping, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the url is malformed.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_MalformedURL() throws Exception {
        try {
            new DefaultJiraManager("(#o2i8", null, null, null, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the url is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor1_EmptyURL() throws Exception {
        try {
            new DefaultJiraManager("(#o2i8", null, null, null, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the configuration object is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_NullConfig() throws Exception {
        try {
            new DefaultJiraManager(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when the url is malformed.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_EmptyURL() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", "");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when the url is malformed.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_MalformedURL() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", ")!@#_D");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when the url is not a string.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_NonStringURL() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", Boolean.TRUE);

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when the log name is not a string.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_NonStringLogName() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", Boolean.TRUE);

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when property 'generic_token' is missing.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_MissingGenericToken() throws Exception {
        configuration.getChild("notificationSchemesNamespace").removeProperty("generic_token");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when property 'generic_token' is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_EmptyGenericToken() throws Exception {
        configuration.getChild("notificationSchemesNamespace").setPropertyValue("generic_token", " ");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when property 'generic_token' is not a string.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor2_NonStringGenericToken() throws Exception {
        configuration.getChild("notificationSchemesNamespace").setPropertyValue("generic_token", Boolean.TRUE);

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when file name is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_NullFileName() throws Exception {
        try {
            new DefaultJiraManager(null, "com.topcoder.jira");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when file name is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_EmptyFileName() throws Exception {
        try {
            new DefaultJiraManager(" ", "com.topcoder.jira");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when namespace is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_NullNamespace() throws Exception {
        try {
            new DefaultJiraManager("test_files/failure/config.properties", null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when file namespace is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_EmptyNamespace() throws Exception {
        try {
            new DefaultJiraManager("test_files/failure/config.properties", " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when the namespace is unknown.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_UnknownNamespace() throws Exception {
        try {
            new DefaultJiraManager("test_files/failure/config.properties", "unknown");
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when file name is malformed.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_MalformedFileName() throws Exception {
        try {
            new DefaultJiraManager("#i@??", "namespace");
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * </p>
     * <p>
     * JiraManagerConfigurationException is expected when file is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCtor3_InexistentFile() throws Exception {
        try {
            new DefaultJiraManager("test_files/failure/inexistent.file", "namespace");
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the user name is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_NullUserName() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.login(null, password);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the user name is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_EmptyUserName() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.login(" ", password);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the password is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_NullPassword() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.login(userName, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the password is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_EmptyPassword() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.login(userName, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_InexistentUser() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.login("inexistent.user", "123");
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the password is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_InvalidPassword() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.login(userName, "non@existing.password");
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>login(String, String)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the url is incorrect.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_InvalidURL() throws Exception {

        try {
            invalidMgr.login(userName, password);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>logout(String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_NullToken() throws Exception {
        try {
            mgr.logout(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>logout(String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_EmptyToken() throws Exception {
        try {
            mgr.logout(" ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>logout(String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_InvalidURL() throws Exception {

        try {
            invalidMgr.logout(securityToken);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_NullToken() throws Exception {
        try {
            mgr.createProject(null, project, version, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the project is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_NullProject() throws Exception {
        try {
            mgr.createProject(securityToken, null, version, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the version is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_NullVersion() throws Exception {
        try {
            mgr.createProject(securityToken, project, null, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the type is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_NullType() throws Exception {
        try {
            mgr.createProject(securityToken, project, version, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_EmptyToken() throws Exception {
        try {
            mgr.createProject(" ", project, version, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_InvalidToken() throws Exception {
        try {
            mgr.createProject("token", project, version, ComponentType.APPLICATION);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * JiraProjectValidationException is expected when the project key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_NullProjectKey() throws Exception {
        project.setKey(null);
        try {
            mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * JiraProjectValidationException is expected when the project key is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_EmptyProjectKey() throws Exception {
        project.setKey(" ");
        try {
            mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * JiraProjectValidationException is expected when the version name is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_NullVersionName() throws Exception {
        version.setName(null);
        try {
            mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.createProject(token, project, version, ComponentType.APPLICATION);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_InvalidURL() throws Exception {
        try {
            invalidMgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_NullToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.deleteProject(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_EmptyToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.deleteProject(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is null.
     * </p>
     *
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_NullKey() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.deleteProject(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_EmptyKey() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.deleteProject(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_InvalidToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.deleteProject("token", project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the project is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_InexistentProject() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.deleteProject(securityToken, project.getKey() + "123");
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_InvalidURL() throws Exception {
        try {
            invalidMgr.deleteProject(securityToken, project.getKey());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_NullToken() throws Exception {
        try {
            mgr.addVersionToProject(null, project, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when project is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_NullProject() throws Exception {
        try {
            mgr.addVersionToProject(securityToken, null, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when version is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_NullVersion() throws Exception {
        try {
            mgr.addVersionToProject(securityToken, project, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_EmptyToken() throws Exception {
        try {
            mgr.addVersionToProject(" ", project, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_InvalidToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.addVersionToProject("token", project, version);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when project is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_InexistentProject() throws Exception {
        try {
            mgr.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when project key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_NullProjectKey() throws Exception {
        project.setKey(null);
        try {
            mgr.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when version name is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_NullVersionName() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        version.setName(null);
        try {
            mgr.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_InvalidURL() throws Exception {
        try {
            invalidMgr.addVersionToProject(securityToken, project, version);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_UnauthorizedUser() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.addVersionToProject(token, project, version);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_NullToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion(null, project.getKey(), version.getName(), true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the project is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_NullProject() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion(securityToken, null, version.getName(), true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the version is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_NullVersion() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion(securityToken, project.getKey(), null, true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_EmtyToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion(" ", project.getKey(), version.getName(), true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_InvalidToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion("token", project.getKey(), version.getName(), true);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the project is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_InexistentProject() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion(securityToken, project.getKey() + "123", version.getName(), true);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the version is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_InexistentVersion() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.archiveVersion(securityToken, project.getKey(), version.getName() + "123", true);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_UnauthorizedUser() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);

        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.archiveVersion(token, project.getKey(), version.getName(), true);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_InvlaidURL() throws Exception {
        try {
            invalidMgr.archiveVersion(securityToken, project.getKey(), version.getName(), true);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>deleteProject(String, String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.deleteProject(token, project.getKey());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_NullToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.getProjectVersions(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_EmptyToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.getProjectVersions(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_NullKey() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.getProjectVersions(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_EmptyKey() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.getProjectVersions(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_InvalidToken() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.getProjectVersions("token", project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the project is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_InexistentProject() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            mgr.getProjectVersions(securityToken, project.getKey() + "1");
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_InvalidURL() throws Exception {
        try {
            invalidMgr.getProjectVersions(securityToken, project.getKey());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProjectVersions(String, String)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.getProjectVersions(token, project.getKey());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_NullToken() throws Exception {
        try {
            mgr.getProject(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_EmptyToken() throws Exception {
        try {
            mgr.getProject(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_NullKey() throws Exception {
        try {
            mgr.getProject(securityToken, (String) null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_EmptyKey() throws Exception {
        try {
            mgr.getProject(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_InvalidToken() throws Exception {
        try {
            mgr.getProject("token", project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_InvalidURL() throws Exception {
        try {
            invalidMgr.getProject(securityToken, project.getKey());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject1_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.getProject(token, project.getKey());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_NullToken() throws Exception {
        try {
            mgr.getProject(null, project.getKey(), version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_EmptyToken() throws Exception {
        try {
            mgr.getProject(" ", project.getKey(), version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_NullKey() throws Exception {
        try {
            mgr.getProject(securityToken, null, version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_EmptyKey() throws Exception {
        try {
            mgr.getProject(securityToken, " ", version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the version name is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_NullVersionName() throws Exception {
        try {
            mgr.getProject(securityToken, project.getKey(), null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_EmptyVersionName() throws Exception {
        try {
            mgr.getProject(securityToken, project.getKey(), " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_InvalidToken() throws Exception {
        try {
            mgr.getProject("token", project.getKey(), version.getName());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_InvalidURL() throws Exception {
        try {
            invalidMgr.getProject(securityToken, project.getKey(), version.getName());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, String, Strng)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject2_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.getProject(token, project.getKey(), version.getName());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>getProject(String, Long)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject3_NullToken() throws Exception {
        JiraProjectCreationResult result = mgr
            .createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(result.getProject().getId());
        try {
            mgr.getProject(null, id);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, Long)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject3_EmptyToken() throws Exception {
        JiraProjectCreationResult result = mgr
            .createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(result.getProject().getId());
        try {
            mgr.getProject(" ", id);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, Long)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the id is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject3_NullID() throws Exception {
        try {
            mgr.getProject(securityToken, (Long) null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, Long)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject3_InvalidToken() throws Exception {
        JiraProjectCreationResult result = mgr
            .createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(result.getProject().getId());

        try {
            mgr.getProject("token", id);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, Long)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject3_InvalidURL() throws Exception {
        try {
            invalidMgr.getProject(securityToken, Long.valueOf(239));
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>getProject(String, Long)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject3_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.getProject(token, Long.valueOf(239));
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_NullToken() throws Exception {
        try {
            mgr.projectExists(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_EmptyToken() throws Exception {
        try {
            mgr.projectExists(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_NullKey() throws Exception {
        try {
            mgr.projectExists(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_EmptyKey() throws Exception {
        try {
            mgr.projectExists(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is expired.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_ExpiredToken() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.projectExists(securityToken, project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_InvalidURL() throws Exception {
        try {
            invalidMgr.projectExists(securityToken, project.getName());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>projectExists(String, String)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.projectExists(token, project.getName());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_NullToken() throws Exception {
        try {
            mgr.releaseVersion(null, project.getKey(), version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_EmptyToken() throws Exception {
        try {
            mgr.releaseVersion(" ", project.getKey(), version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the key is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_NullKey() throws Exception {
        try {
            mgr.releaseVersion(securityToken, null, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_EmptyKey() throws Exception {
        try {
            mgr.releaseVersion(securityToken, " ", version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the version is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_NullVersion() throws Exception {
        try {
            mgr.releaseVersion(securityToken, project.getKey(), null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is expired.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_ExpiredToken() throws Exception {
        mgr.logout(securityToken);
        try {
            mgr.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the project is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_InexistentProject() throws Exception {
        try {
            mgr.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the version is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_InexistentVersion() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        version.setName("inexistent");
        try {
            mgr.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_InvalidURL() throws Exception {
        try {
            invalidMgr.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_NullToken() throws Exception {
        try {
            mgr.updateProject(null, project);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the token is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_EmptyToken() throws Exception {
        try {
            mgr.updateProject(" ", project);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * IllegalArgumentException is expected when the project is null.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_NullProject() throws Exception {
        try {
            mgr.updateProject(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * JiraSecurityTokenExpiredException is expected when the token is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_InvalidToken() throws Exception {
        try {
            mgr.updateProject("token", project);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * JiraManagerException is expected when the project is inexistent.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_InexistentProject() throws Exception {
        try {
            mgr.updateProject(securityToken, project);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * JiraProjectValidationException is expected when the project name is empty.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_EmptyProjectName() throws Exception {
        mgr.createProject(securityToken, project, version, ComponentType.APPLICATION);
        project.setName("");
        try {
            mgr.updateProject(securityToken, project);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * JiraConnectionException is expected when the url is invalid.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_InvalidURL() throws Exception {
        try {
            invalidMgr.updateProject(securityToken, project);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>updateProject(String, JiraProject)</code>.
     * </p>
     * <p>
     * JiraNotAuthorizedException is expected when the user is unauthorized.
     * </p>
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_UnauthorizedUser() throws Exception {
        String token = mgr.login(guestName, guestPassword);
        try {
            mgr.updateProject(token, project);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        mgr.logout(token);
    }

    /**
     * <p>
     * Gets the configuration object for tests.
     * </p>
     *
     * @return the configuration object.
     * @throws Exception if error occurs.
     */
    private static ConfigurationObject getConfig() throws Exception {
        ConfigurationObject config = new DefaultConfigurationObject("root");

        // common
        ConfigurationObject common = new DefaultConfigurationObject("jiraManagerNamespace");
        common.setPropertyValue("log_name_token", "configLog");
        common.setPropertyValue("jira_url_token", url);
        config.addChild(common);

        // notification schemes
        ConfigurationObject notification = new DefaultConfigurationObject("notificationSchemesNamespace");
        notification.setPropertyValue("generic_token", "Client Project Notification Scheme");
        notification.setPropertyValue("custom_token", "Component Management Notification Scheme");
        notification.setPropertyValue("application_token", "Component Management Notification Scheme");
        config.addChild(notification);

        // permission schemes
        ConfigurationObject permission = new DefaultConfigurationObject("permissionSchemesNamespace");
        permission.setPropertyValue("generic_token", "Client Projects");
        permission.setPropertyValue("custom_token", "Generic Component Permission Scheme");
        permission.setPropertyValue("application_token", "empty");
        config.addChild(permission);

        // issue security schemes
        ConfigurationObject issueSecurity = new DefaultConfigurationObject("issueSecuritySchemesNamespace");
        issueSecurity.setPropertyValue("generic_token", "Client Project Issues Scheme");
        issueSecurity.setPropertyValue("custom_token", "Generic Component Issues Scheme");
        issueSecurity.setPropertyValue("application_token", "Generic Component Issues Scheme");
        config.addChild(issueSecurity);

        return config;
    }

    /**
     * Gets a JIRA project for tests.
     *
     * @return the JIRA project.
     */
    private static JiraProject getJiraProject() {
        JiraProject project = new JiraProject();
        project.setDescription("description");
        project.setKey(prefix + "KEY");
        project.setLead(userName);
        project.setName(prefix + "name");
        project.setProjectUrl("http://project.url");
        project.setUrl("http://url");
        return project;
    }

    /**
     * Removes all created projects and logs out.
     *
     * @throws Exception if error occurs.
     */
    private static void cleanJiraSystem() throws Exception {
        DefaultJiraManager manager = new DefaultJiraManager();
        String token = manager.login(userName, password);
        RemoteProject[] projects = manager.getService().getProjectsNoSchemes(token);
        for (RemoteProject project : projects) {
            if (project.getKey().startsWith(prefix)) {
                manager.deleteProject(token, project.getKey());
            }
        }
        manager.logout(token);
    }

}
