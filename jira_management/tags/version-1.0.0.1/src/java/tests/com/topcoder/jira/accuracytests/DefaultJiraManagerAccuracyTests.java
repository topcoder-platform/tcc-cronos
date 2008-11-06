/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.accuracytests;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.atlassian.jira.rpc.soap.beans.RemoteScheme;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraProjectCreationAction;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraProjectRetrievalResultAction;
import com.topcoder.jira.accuracytests.AccuracyTestsHelper;
import com.topcoder.jira.generated.JiraSoapService;
import com.topcoder.jira.managers.DefaultJiraManager;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.jdk14.Jdk14LogFactory;

/**
 * Accuracy tests for the <code>DefaultJiraManager</code> class.
 *
 * @author morehappiness
 * @version 1.0
 */
public class DefaultJiraManagerAccuracyTests extends TestCase {

    /**
     * <code>JiraProject</code> instance used for testing.
     */
    private JiraProject project;

    /**
     * <code>JiraVersion</code> instance used for testing.
     */
    private JiraVersion version;

    /**
     * <code>DefaultJiraManager</code> instance used for testing.
     */
    private DefaultJiraManager manager;

    /**
     * <code>DefaultJiraManager</code> instance used to test connection issues.
     */
    private DefaultJiraManager managerToNoWhere;

    /**
     * Security token needed to perform SOAP operation.
     * <p>
     * Initialized during login.
     */
    private String securityToken;

    /**
     * Configuration object used to hold configuration data.
     */
    private ConfigurationObject configuration;

    /**
     * Creates required objects and sets up environment.
     *
     * @throws Exception if error occurs.
     */
    protected void setUp() throws Exception {
        AccuracyTestsHelper.cleanJiraSystem();

        // create project
        project = new JiraProject();
        project.setDescription("bla-bla-bla");
        project.setKey(AccuracyTestsHelper.getPrefix() + "KEY");
        project.setLead(AccuracyTestsHelper.getUserName());
        project.setName(AccuracyTestsHelper.getPrefix() + "name");
        project.setProjectUrl("http://projectUrl");
        project.setUrl("http://url");

        // create version
        version = new JiraVersion();
        version.setName("version1");

        // create valid manager
        manager = new DefaultJiraManager();
        manager.setLog(null);
        securityToken = manager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());

        // create manager used to test connection issues
        managerToNoWhere =
            new DefaultJiraManager("http://there.is.no.place.like.home", null, null, null, null);

        // common will contain URL and log
        ConfigurationObject common = new DefaultConfigurationObject("jiraManagerNamespace");
        common.setPropertyValue("log_name_token", "configLog");
        common.setPropertyValue("jira_url_token", AccuracyTestsHelper.getURL());

        // configuration of notification schemes
        ConfigurationObject notification = new DefaultConfigurationObject("notificationSchemesNamespace");
        notification.setPropertyValue("generic_token", "Client Project Notification Scheme");
        notification.setPropertyValue("custom_token", "Component Management Notification Scheme");
        notification.setPropertyValue("application_token", "Component Management Notification Scheme");

        // configuration of permission schemes
        ConfigurationObject permission = new DefaultConfigurationObject("permissionSchemesNamespace");
        permission.setPropertyValue("generic_token", "Client Projects");
        permission.setPropertyValue("custom_token", "Generic Component Permission Scheme");
        permission.setPropertyValue("application_token", "empty");

        // configuration of issue security schemes
        ConfigurationObject issueSecurity = new DefaultConfigurationObject("issueSecuritySchemesNamespace");
        issueSecurity.setPropertyValue("generic_token", "Client Project Issues Scheme");
        issueSecurity.setPropertyValue("custom_token", "Generic Component Issues Scheme");
        issueSecurity.setPropertyValue("application_token", "Generic Component Issues Scheme");

        // add all parameter to the main ConfigurationObject
        configuration = new DefaultConfigurationObject("root");
        configuration.addChild(common);
        configuration.addChild(notification);
        configuration.addChild(permission);
        configuration.addChild(issueSecurity);
    }

    /**
     * Restores environment.
     *
     * @throws Exception if error occurs.
     */
    protected void tearDown() throws Exception {
        // performs silent logout
        try {
            manager.logout(securityToken);
        } catch (JiraManagerException e) {
            // ignore
        }

        AccuracyTestsHelper.cleanJiraSystem();
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks that method works correctly. Passes existing version.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        String projectId = result.getProject().getId();
        String versionId = result.getVersion().getId();

        result = manager.addVersionToProject(securityToken, project, version);
        assertEquals("result is incorrect", JiraProjectCreationAction.PROJECT_AND_VERSION_EXISTED, result
            .getActionTaken());
        assertEquals("returned project is incorrect", projectId, result.getProject().getId());
        assertEquals("returned version is incorrect", versionId, result.getVersion().getId());
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks that method works correctly. Archives version.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion1() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), true);
        List<JiraVersion> versions = manager.getProjectVersions(securityToken, project.getKey());

        assertTrue("version is not archived", versions.get(0).isArchived());
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks that method works correctly. Unarchives version.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion2() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), true);
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), false);
        List<JiraVersion> versions = manager.getProjectVersions(securityToken, project.getKey());
        assertFalse("version is not unarchived", versions.get(0).isArchived());
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks that method works correctly. Passes existing project and version.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject1() throws Exception {
        JiraProjectCreationResult resultA =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        JiraProjectCreationResult resultB =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        assertEquals("result is incorrect", JiraProjectCreationAction.PROJECT_AND_VERSION_EXISTED, resultB
            .getActionTaken());
        assertEquals("returned project is incorrect", resultA.getProject().getId(), resultB.getProject()
            .getId());
        assertEquals("returned version is incorrect", resultA.getVersion().getId(), resultB.getVersion()
            .getId());
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks that method works correctly. Passes existing project and non-existing version.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject2() throws Exception {
        JiraProjectCreationResult resultA =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        version.setName("somethingnew");
        JiraProjectCreationResult resultB =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        assertEquals("result is incorrect", JiraProjectCreationAction.PROJECT_EXISTED_VERSION_CREATED,
            resultB.getActionTaken());
        assertEquals("returned project is incorrect", resultA.getProject().getId(), resultB.getProject()
            .getId());
        assertNotNull("returned version is incorrect", resultB.getVersion().getId());
    }

    /**
     * Tests <code>DefaultJiraManager()</code>.
     * <p>
     * Checks that constructor correctly creates new instance.
     * <p>
     * Make sure that correct schemes are used when type is generic.
     *
     * @throws Exception if error occurs.
     */
    public void testCtorOne() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager();
        assertFalse("verbose logging must be turned off by default", defaultManager.isVerboseLogging());

        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());

        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.GENERIC_COMPONENT)
                .getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());

        assertEquals("permission scheme is incorrect", "Generic Component Permission Scheme", remote
            .getPermissionScheme().getName());
        RemoteScheme notificationScheme = remote.getNotificationScheme();
        // it's possible that mail server is not configured and notifications
        // are disabled
        if (notificationScheme != null) {
            assertEquals("notification scheme is incorrect", "Component Management Notification Scheme",
                notificationScheme.getName());
        }
        RemoteScheme issueSecurityScheme = remote.getIssueSecurityScheme();
        // not all JIRA systems support issue security
        if (issueSecurityScheme != null) {
            assertEquals("issue security scheme is incorrect", "Generic Component Issues Scheme",
                issueSecurityScheme.getName());
        }
    }

    /**
     * Tests <code>DefaultJiraManager()</code>.
     * <p>
     * Checks that constructor correctly creates new instance.
     * <p>
     * Make sure that correct schemes are used when type is application.
     *
     * @throws Exception if error occurs.
     */
    public void testCtorOne1() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager();
        assertFalse("verbose logging must be turned off by default", defaultManager.isVerboseLogging());

        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());

        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.APPLICATION).getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());

        assertEquals("permission scheme is incorrect", "Client Projects", remote.getPermissionScheme()
            .getName());
        RemoteScheme notificationScheme = remote.getNotificationScheme();
        // it's possible that mail server is not configured and notifications
        // are disabled
        if (notificationScheme != null) {
            assertEquals("notification scheme is incorrect", "Client Project Notification Scheme",
                notificationScheme.getName());
        }
        RemoteScheme issueSecurityScheme = remote.getIssueSecurityScheme();
        // not all JIRA systems support issue security
        if (issueSecurityScheme != null) {
            assertEquals("issue security scheme is incorrect", "Client Project Issues Scheme",
                issueSecurityScheme.getName());
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters.
     *
     * @throws Exception if error occurs.
     */
    public void testCtorTwo() throws Exception {
        Log log = new Jdk14LogFactory().createLog("abc");
        DefaultJiraManager defaultManager =
            new DefaultJiraManager(AccuracyTestsHelper.getURL(), log, null, null, null);
        assertSame("log was propagated incorrectly", log, defaultManager.getLog());

        // make sure that URL is correct
        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());
        defaultManager.logout(token);
    }

    /**
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters.
     *
     * @throws Exception if error occurs.
     */
    public void testCtortwo1() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping1 = new HashMap<ComponentType, String>();
        mapping1.put(ComponentType.APPLICATION, "Component Management Notification Scheme");
        mapping1.put(ComponentType.CUSTOM_COMPONENT, "Component Management Notification Scheme");
        mapping1.put(ComponentType.GENERIC_COMPONENT, "Client Project Notification Scheme");
        Map<ComponentType, String> mapping2 = new HashMap<ComponentType, String>();
        mapping2.put(ComponentType.APPLICATION, "Custom Component Permissions Scheme");
        mapping2.put(ComponentType.CUSTOM_COMPONENT, "Generic Component Permission Scheme");
        mapping2.put(ComponentType.GENERIC_COMPONENT, "Client Projects");
        Map<ComponentType, String> mapping3 = new HashMap<ComponentType, String>();
        mapping3.put(ComponentType.APPLICATION, "Generic Component Issues Scheme");
        mapping3.put(ComponentType.CUSTOM_COMPONENT, "Custom Component Issues Scheme");
        mapping3.put(ComponentType.GENERIC_COMPONENT, "Client Project Issues Scheme");

        Log log = new Jdk14LogFactory().createLog("abc");
        DefaultJiraManager defaultManager =
            new DefaultJiraManager(AccuracyTestsHelper.getURL(), log, mapping1, mapping2, mapping3);
        assertSame("log was propagated incorrectly", log, defaultManager.getLog());

        // make sure that URL is correct
        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());

        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.GENERIC_COMPONENT)
                .getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());

        assertEquals("permission scheme is incorrect", "Client Projects", remote.getPermissionScheme()
            .getName());

        defaultManager.logout(token);
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks that constructor correctly creates new instance. All parameters present in
     * configuration.
     *
     * @throws Exception if error occurs.
     */
    public void testCtorThree() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager(configuration);
        // make sure that scheme and URL were propagated
        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());

        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.GENERIC_COMPONENT)
                .getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());

        assertEquals("permission scheme is incorrect", "Client Projects", remote.getPermissionScheme()
            .getName());

        defaultManager.logout(token);
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Only
     * logging is configured.
     *
     * @throws Exception if error occurs.
     */
    public void testDCtorFour() throws Exception {
        DefaultJiraManager defaultManager =
            new DefaultJiraManager("test_files/config.properties", "com.topcoder.jira");

        // logging must be enabled
        assertNotNull("logging must be enabled", defaultManager.getLog());

        // make sure that scheme and URL were propagated
        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());
        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.GENERIC_COMPONENT)
                .getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());
        assertEquals("permission scheme is incorrect", "Generic Component Permission Scheme", remote
            .getPermissionScheme().getName());

        defaultManager.logout(token);
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Only
     * schemes mappings are configured.
     *
     * @throws Exception if error occurs.
     */
    public void testCtorFour1() throws Exception {
        DefaultJiraManager defaultManager =
            new DefaultJiraManager("test_files/schemes.properties", "com.topcoder.jira");

        // logging must be disabled
        assertNull("logging must be disabled", defaultManager.getLog());

        // make sure that scheme and URL were propagated
        String token = defaultManager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());
        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.GENERIC_COMPONENT)
                .getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());
        assertEquals("permission scheme is incorrect", "Custom Component Permissions Scheme", remote
            .getPermissionScheme().getName());

        defaultManager.logout(token);
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        manager.deleteProject(securityToken, project.getKey());
        assertEquals("project was not removed", JiraProjectRetrievalResultAction.PROJECT_NOT_FOUND, manager
            .getProject(securityToken, project.getKey()).getRetrievalResult());
    }

    /**
     * Tests <code>getLog()</code>.
     * <p>
     * Checks that getter returns expected value.
     */
    public void testGetLog() {
        Jdk14LogFactory factory = new Jdk14LogFactory();
        Log log = factory.createLog("name");

        manager.setLog(log);
        assertSame("returned value is incorrect", log, manager.getLog());
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Only one version exists. Uses project key written
     * in lowercase letters.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        List<JiraVersion> list = manager.getProjectVersions(securityToken, project.getKey());

        assertEquals("only one version is expected", 1, list.size());
        assertEquals("version id is incorrect", result.getVersion().getId(), list.get(0).getId());
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Four versions and two projects exist.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions1() throws Exception {
        // create first project with one version
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        // create second project with three versions
        project.setKey(AccuracyTestsHelper.getPrefix() + "AAA");
        project.setName(AccuracyTestsHelper.getPrefix() + "TTT");
        manager.createProject(securityToken, project, version, ComponentType.CUSTOM_COMPONENT);
        version.setName("versionx");
        manager.addVersionToProject(securityToken, project, version);
        version.setName("versiony");
        manager.addVersionToProject(securityToken, project, version);

        List<JiraVersion> list = manager.getProjectVersions(securityToken, project.getKey());

        assertEquals("three versions are expected", 3, list.size());
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Requires non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectOne() throws Exception {
        JiraProjectRetrievalResult result = manager.getProject(securityToken, project.getKey());
        assertEquals("result is incorrect", JiraProjectRetrievalResultAction.PROJECT_NOT_FOUND, result
            .getRetrievalResult());
        assertNull("null project is expected", result.getProject());
        assertNull("null version is expected", result.getVersion());
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Requires existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectOne1() throws Exception {
        JiraProjectCreationResult creationResult =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        JiraProjectRetrievalResult result = manager.getProject(securityToken, project.getKey());
        assertEquals("result is incorrect", JiraProjectRetrievalResultAction.PROJECT_FOUND_NOT_VERSION,
            result.getRetrievalResult());
        assertEquals("returned project is incorrect", creationResult.getProject().getId(), result
            .getProject().getId());
        assertNull("null version is expected", result.getVersion());
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Project and version don't exist.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectTwo() throws Exception {
        JiraProjectRetrievalResult result =
            manager.getProject(securityToken, project.getKey(), version.getName());
        assertEquals("returned value is incorrect", JiraProjectRetrievalResultAction.PROJECT_NOT_FOUND,
            result.getRetrievalResult());
        assertNull("null project is expected", result.getProject());
        assertNull("null version is expected", result.getVersion());
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Project exists but version doesn't.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectTwo1() throws Exception {
        JiraProjectCreationResult creationResult =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        version.setName("qwert");
        JiraProjectRetrievalResult result =
            manager.getProject(securityToken, project.getKey(), version.getName());

        assertEquals("returned value is incorrect",
            JiraProjectRetrievalResultAction.PROJECT_FOUND_NOT_VERSION, result.getRetrievalResult());
        assertEquals("returned project is incorrect", creationResult.getProject().getId(), result
            .getProject().getId());
        assertNull("null version is expected", result.getVersion());
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Project and version exist.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectTwo2() throws Exception {
        JiraProjectCreationResult creationResult =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        JiraProjectRetrievalResult result =
            manager.getProject(securityToken, project.getKey(), version.getName().toLowerCase());

        assertEquals("returned value is incorrect",
            JiraProjectRetrievalResultAction.PROJECT_AND_VERSION_FOUND, result.getRetrievalResult());
        assertEquals("returned project is incorrect", creationResult.getProject().getId(), result
            .getProject().getId());
        assertEquals("returned version is incorrect", creationResult.getVersion().getId(), result
            .getVersion().getId());
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks that getter returns expected value. Requires non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectThree() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        JiraProjectRetrievalResult result = manager.getProject(securityToken, Long.valueOf(-239));
        assertEquals("result is incorrect", JiraProjectRetrievalResultAction.PROJECT_NOT_FOUND, result
            .getRetrievalResult());
        assertNull("null project is expected", result.getProject());
        assertNull("null version is expected", result.getVersion());
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks that getter returns expected value. Requires existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectThree1() throws Exception {
        JiraProjectCreationResult creationResult =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(creationResult.getProject().getId());

        JiraProjectRetrievalResult result = manager.getProject(securityToken, id);
        assertEquals("result is incorrect", JiraProjectRetrievalResultAction.PROJECT_FOUND_NOT_VERSION,
            result.getRetrievalResult());
        assertEquals("returned project is incorrect", creationResult.getProject().getId(), result
            .getProject().getId());
        assertNull("null version is expected", result.getVersion());
    }

    /**
     * Tests <code>getService()</code>.
     * <p>
     * Checks that getter returns expected value.
     *
     * @throws Exception if error occurs.
     */
    public void testGetService() throws Exception {
        JiraSoapService service = manager.getService();
        // perform logout
        service.logout(securityToken);
    }

    /**
     * Tests <code>isVerboseLogging()</code>.
     * <p>
     * Checks that method works correctly. False is expected.
     */
    public void testIsVerboseLogging() {
        manager.setVerboseLogging(true);
        manager.setVerboseLogging(false);
        assertFalse("returned value is incorrect", manager.isVerboseLogging());
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin() throws Exception {
        manager.logout(securityToken);

        String token = manager.login(AccuracyTestsHelper.getUserName(), AccuracyTestsHelper.getPassword());
        assertNotNull("returned token couldn't be null", token);
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks that method works correctly. Project doesn't exist.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        assertFalse("returned value is incorrect", manager.projectExists(securityToken, project.getName()
            + "abc"));
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks that method works correctly. Releases version.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        Date date = new Date();
        version.setReleased(true);
        version.setReleaseDate(date);
        manager.releaseVersion(securityToken, project.getKey(), version);
        List<JiraVersion> versions = manager.getProjectVersions(securityToken, project.getKey());

        assertTrue("version is not released", versions.get(0).isReleased());
        assertEquals("release date is not released", date, versions.get(0).getReleaseDate());
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks that method works correctly. Unreleases version.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion1() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        Date date = new Date();
        version.setReleased(true);
        version.setReleaseDate(date);
        manager.releaseVersion(securityToken, project.getKey(), version);
        version.setReleased(false);
        manager.releaseVersion(securityToken, project.getKey(), version);
        List<JiraVersion> versions = manager.getProjectVersions(securityToken, project.getKey());

        assertFalse("version is released", versions.get(0).isReleased());
    }

    /**
     * Tests <code>setLog(Log)</code>.
     * <p>
     * Checks that setter correctly propagates parameter.
     */
    public void testSetLog() {
        Jdk14LogFactory factory = new Jdk14LogFactory();
        Log log = factory.createLog("name");

        manager.setLog(log);
        assertSame("value was propagated incorrectly", log, manager.getLog());
    }

    /**
     * Tests <code>setVerboseLogging(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter.
     */
    public void testSetVerboseLogging() {
        manager.setVerboseLogging(true);
        assertTrue("value was propagated incorrectly", manager.isVerboseLogging());
        manager.setVerboseLogging(false);
        assertFalse("value was propagated incorrectly", manager.isVerboseLogging());
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        project.setDescription("xxx");
        project.setProjectUrl("http://xxx");
        manager.updateProject(securityToken, project);

        // check that values were updated
        project = manager.getProject(securityToken, project.getKey()).getProject();
        assertEquals("update was not performed correctly", "xxx", project.getDescription());
        assertEquals("update was not performed correctly", "http://xxx", project.getProjectUrl());
    }
}
