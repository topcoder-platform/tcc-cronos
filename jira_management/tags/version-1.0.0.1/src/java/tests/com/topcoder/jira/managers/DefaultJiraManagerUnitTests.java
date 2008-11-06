/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.atlassian.jira.rpc.soap.beans.RemoteScheme;
import com.atlassian.jira.rpc.soap.beans.RemoteVersion;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectCreationAction;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraProjectRetrievalResultAction;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.UnitTestsHelper;
import com.topcoder.jira.generated.JiraSoapService;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.jdk14.Jdk14LogFactory;

/**
 * Unit tests for the <code>DefaultJiraManager</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class DefaultJiraManagerUnitTests extends TestCase {

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
        UnitTestsHelper.cleanJiraSystem();

        // create project
        project = new JiraProject();
        project.setDescription("bla-bla-bla");
        project.setKey(UnitTestsHelper.getPrefix() + "KEY");
        project.setLead(UnitTestsHelper.getUserName());
        project.setName(UnitTestsHelper.getPrefix() + "name");
        project.setProjectUrl("http://projectUrl");
        project.setUrl("http://url");

        // create version
        version = new JiraVersion();
        version.setName("version1");

        // create valid manager
        manager = new DefaultJiraManager();
        manager.setLog(null);
        securityToken = manager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

        // create manager used to test connection issues
        managerToNoWhere =
            new DefaultJiraManager("http://there.is.no.place.like.home", null, null, null, null);

        // common will contain URL and log
        ConfigurationObject common = new DefaultConfigurationObject("jiraManagerNamespace");
        common.setPropertyValue("log_name_token", "configLog");
        common.setPropertyValue("jira_url_token", UnitTestsHelper.getURL());

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

        UnitTestsHelper.cleanJiraSystem();
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_1() throws Exception {
        try {
            manager.addVersionToProject(null, project, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null project.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_2() throws Exception {
        try {
            manager.addVersionToProject(securityToken, null, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null version.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_3() throws Exception {
        try {
            manager.addVersionToProject(securityToken, project, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_4() throws Exception {
        try {
            manager.addVersionToProject(" ", project, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes incorrect token.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_5() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.addVersionToProject("token", project, version);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_6() throws Exception {
        try {
            manager.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes project with null key.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_7() throws Exception {
        project.setKey(null);
        try {
            manager.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes version with empty name.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_8() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        version.setName("");
        try {
            manager.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes version with null name.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_9() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        version.setName("");
        try {
            manager.addVersionToProject(securityToken, project, version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks that method works correctly. Passes existing version.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_10() throws Exception {
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
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks that method works correctly. Passes non-existing version.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_11() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        String projectId = result.getProject().getId();

        version.setName("abc");
        result = manager.addVersionToProject(securityToken, project, version);
        assertEquals("result is incorrect", JiraProjectCreationAction.PROJECT_EXISTED_VERSION_CREATED, result
            .getActionTaken());
        assertEquals("returned project is incorrect", projectId, result.getProject().getId());
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_12() throws Exception {
        try {
            managerToNoWhere.addVersionToProject(securityToken, project, version);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>addVersionToProject(String, JiraProject, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes non-authorized user.
     *
     * @throws Exception if error occurs.
     */
    public void testAddVersionToProject_13() throws Exception {
        // create project by normal user
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.addVersionToProject(token, project, version);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_1() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion(null, project.getKey(), version.getName(), true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes null project.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_2() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion(securityToken, null, version.getName(), true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes null version.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_3() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion(securityToken, project.getKey(), null, true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_4() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion(" ", project.getKey(), version.getName(), true);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_5() throws Exception {
        manager.setLog(LogManager.getLog());
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion("token", project.getKey(), version.getName(), true);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_6() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion(securityToken, project.getKey() + "123", version.getName(), true);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes non-existing version.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_7() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.archiveVersion(securityToken, project.getKey(), version.getName() + "123", true);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes non-authorized user.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_8() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.archiveVersion(token, project.getKey(), version.getName(), true);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks that method works correctly. Archives version.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_9() throws Exception {
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
    public void testArchiveVersion_10() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), true);
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), false);
        List<JiraVersion> versions = manager.getProjectVersions(securityToken, project.getKey());
        assertFalse("version is not unarchived", versions.get(0).isArchived());
    }

    /**
     * Tests <code>archiveVersion(String, String, String, boolean)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testArchiveVersion_11() throws Exception {
        try {
            managerToNoWhere.archiveVersion(securityToken, project.getKey(), version.getName(), true);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>convertToLocalProject(RemoteProject)</code>.
     * <p>
     * Checks method for failure. Passes null.
     */
    public void testConvertToLocalProject_1() {
        try {
            manager.convertToLocalProject(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>convertToLocalProject(RemoteProject)</code>.
     * <p>
     * Checks that method works correctly.
     */
    public void testConvertToLocalProject_2() {
        RemoteProject remoteProject = new RemoteProject();
        remoteProject.setId("id");
        remoteProject.setKey("key");
        remoteProject.setLead("lead");
        remoteProject.setName("name");
        remoteProject.setProjectUrl("projectUrl");
        remoteProject.setUrl("url");

        JiraProject localProject = manager.convertToLocalProject(remoteProject);
        assertEquals("id is incorrect", remoteProject.getId(), localProject.getId());
        assertEquals("key is incorrect", remoteProject.getKey(), localProject.getKey());
        assertEquals("lead is incorrect", remoteProject.getLead(), localProject.getLead());
        assertEquals("name is incorrect", remoteProject.getName(), localProject.getName());
        assertEquals("project url is incorrect", remoteProject.getProjectUrl(), localProject.getProjectUrl());
        assertEquals("url is incorrect", remoteProject.getUrl(), localProject.getUrl());
    }

    /**
     * Tests <code>convertToLocalVersion(RemoteVersion)</code>.
     * <p>
     * Checks method for failure. Passes null.
     */
    public void testConvertToLocalVersion_1() {
        try {
            manager.convertToLocalVersion(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>convertToLocalVersion(RemoteVersion)</code>.
     * <p>
     * Checks that method works correctly.
     */
    public void testConvertToLocalVersion_2() {
        Calendar c = Calendar.getInstance();

        RemoteVersion remoteVersion = new RemoteVersion();
        remoteVersion.setArchived(true);
        remoteVersion.setId("id");
        remoteVersion.setName("name");
        remoteVersion.setReleased(true);
        remoteVersion.setReleaseDate(c);
        remoteVersion.setSequence(Long.valueOf(239));

        JiraVersion localVersion = manager.convertToLocalVersion(remoteVersion);
        assertEquals("archived flag is incorrect", remoteVersion.isArchived(), localVersion.isArchived());
        assertEquals("id is incorrect", remoteVersion.getId(), localVersion.getId());
        assertEquals("name is incorrect", remoteVersion.getName(), localVersion.getName());
        assertEquals("release flag is incorrect", remoteVersion.isReleased(), localVersion.isReleased());
        assertEquals("release date is incorrect", c.getTime(), localVersion.getReleaseDate());
        assertEquals("sequence is incorrect", remoteVersion.getSequence(), Long.valueOf(localVersion
            .getSequence()));
    }

    /**
     * Tests <code>convertToLocalVersion(RemoteVersion)</code>.
     * <p>
     * Checks that method works correctly. Passes null sequence.
     */
    public void testConvertToLocalVersion_3() {
        Calendar c = Calendar.getInstance();

        RemoteVersion remoteVersion = new RemoteVersion();
        remoteVersion.setArchived(true);
        remoteVersion.setId("id");
        remoteVersion.setName("name");
        remoteVersion.setReleased(true);
        remoteVersion.setReleaseDate(c);
        remoteVersion.setSequence(null);

        JiraVersion localVersion = manager.convertToLocalVersion(remoteVersion);
        assertEquals("archived flag is incorrect", remoteVersion.isArchived(), localVersion.isArchived());
        assertEquals("id is incorrect", remoteVersion.getId(), localVersion.getId());
        assertEquals("name is incorrect", remoteVersion.getName(), localVersion.getName());
        assertEquals("release flag is incorrect", remoteVersion.isReleased(), localVersion.isReleased());
        assertEquals("release date is incorrect", c.getTime(), localVersion.getReleaseDate());
        assertEquals("sequence is incorrect", 0, localVersion.getSequence());
    }

    /**
     * Tests <code>convertToRemoteProject(JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes null.
     */
    public void testConvertToRemoteProject_1() {
        try {
            manager.convertToRemoteProject(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>convertToRemoteProject(JiraProject)</code>.
     * <p>
     * Checks that method works correctly.
     */
    public void testConvertToRemoteProject_2() {
        JiraProject localProject = new JiraProject();
        localProject.setId("id");
        localProject.setKey("key");
        localProject.setLead("lead");
        localProject.setName("name");
        localProject.setProjectUrl("projectUrl");
        localProject.setUrl("url");

        RemoteProject remoteProject = manager.convertToRemoteProject(localProject);
        assertEquals("id is incorrect", localProject.getId(), remoteProject.getId());
        assertEquals("key is incorrect", localProject.getKey(), remoteProject.getKey());
        assertEquals("lead is incorrect", localProject.getLead(), remoteProject.getLead());
        assertEquals("name is incorrect", localProject.getName(), remoteProject.getName());
        assertEquals("url is incorrect", localProject.getUrl(), remoteProject.getUrl());
        assertEquals("project url is incorrect", localProject.getProjectUrl(), remoteProject.getProjectUrl());
    }

    /**
     * Tests <code>convertToRemoteVersion(JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null.
     */
    public void testConvertToRemoteVersion_1() {
        try {
            manager.convertToRemoteVersion(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>convertToRemoteVersion(JiraVersion)</code>.
     * <p>
     * Checks that method works correctly.
     */
    public void testConvertToRemoteVersion_2() {
        Date date = new Date();

        JiraVersion localVersion = new JiraVersion();
        localVersion.setArchived(true);
        localVersion.setId("id");
        localVersion.setName("name");
        localVersion.setReleased(true);
        localVersion.setReleaseDate(date);
        localVersion.setSequence(239);

        RemoteVersion remoteVersion = manager.convertToRemoteVersion(localVersion);
        assertEquals("archived flag is incorrect", localVersion.isArchived(), remoteVersion.isArchived());
        assertEquals("id is incorrect", localVersion.getId(), remoteVersion.getId());
        assertEquals("name is incorrect", localVersion.getName(), remoteVersion.getName());
        assertEquals("release flag is incorrect", localVersion.isReleased(), remoteVersion.isReleased());
        assertEquals("release date is incorrect", date, remoteVersion.getReleaseDate().getTime());
        assertEquals("sequence is incorrect", Long.valueOf(localVersion.getSequence()), remoteVersion
            .getSequence());
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_1() throws Exception {
        try {
            manager.createProject(null, project, version, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_2() throws Exception {
        try {
            manager.createProject(" ", project, version, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes null project.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_3() throws Exception {
        try {
            manager.createProject(securityToken, null, version, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes null version.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_4() throws Exception {
        try {
            manager.createProject(securityToken, project, null, ComponentType.APPLICATION);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes null type.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_5() throws Exception {
        try {
            manager.createProject(securityToken, project, version, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_6() throws Exception {
        try {
            manager.createProject("token", project, version, ComponentType.APPLICATION);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes project with empty name.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_7() throws Exception {
        project.setKey("");
        try {
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes project with null name.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_8() throws Exception {
        project.setKey(null);
        try {
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes version with empty name.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_9() throws Exception {
        version.setName("");
        try {
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks that method works correctly. Passes existing project and version.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_10() throws Exception {
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
    public void testCreateProject_11() throws Exception {
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
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks that method works correctly. Passes non-existing project and version.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_12() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        assertEquals("result is incorrect", JiraProjectCreationAction.PROJECT_AND_VERSION_CREATED, result
            .getActionTaken());
        assertNotNull("returned project is incorrect", result.getProject().getId());
        assertNotNull("returned version is incorrect", result.getVersion().getId());
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Uses non-authorized user.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_13() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.createProject(token, project, version, ComponentType.APPLICATION);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }

    /**
     * Tests <code>createProject(String, JiraProject, JiraVersion, ComponentType)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateProject_14() throws Exception {
        try {
            managerToNoWhere.createProject(securityToken, project, version, ComponentType.APPLICATION);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
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
    public void testDefaultJiraManager_A_1() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager();
        assertFalse("verbose logging must be turned off by default", defaultManager.isVerboseLogging());

        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

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
     * Make sure that correct schemes are used when type is custom.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_A_2() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager();
        assertFalse("verbose logging must be turned off by default", defaultManager.isVerboseLogging());

        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

        // create generic project
        JiraProject result =
            defaultManager.createProject(token, project, version, ComponentType.CUSTOM_COMPONENT)
                .getProject();
        // get remote project with schemes
        RemoteProject remote =
            defaultManager.getService().getProjectWithSchemesById(token,
                Long.valueOf(result.getId()).longValue());

        assertEquals("permission scheme is incorrect", "Custom Component Permissions Scheme", remote
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
            assertEquals("issue security scheme is incorrect", "Custom Component Issues Scheme",
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
    public void testDefaultJiraManager_A_3() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager();
        assertFalse("verbose logging must be turned off by default", defaultManager.isVerboseLogging());

        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

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
     * Checks constructor for failure. Passes empty map.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_B_1() throws Exception {
        try {
            new DefaultJiraManager(null, null, new HashMap<ComponentType, String>(), null, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * <p>
     * Checks constructor for failure. Passes map with empty value.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_B_2() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping = new HashMap<ComponentType, String>();
        mapping.put(ComponentType.APPLICATION, "Custom Component Permissions Scheme");
        mapping.put(ComponentType.CUSTOM_COMPONENT, "Generic Component Permission Scheme");
        mapping.put(ComponentType.GENERIC_COMPONENT, " ");

        try {
            new DefaultJiraManager(null, null, null, mapping, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * <p>
     * Checks constructor for failure. Passes map with null value.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_B_3() throws Exception {
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
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * <p>
     * Checks constructor for failure. Passes map with only two items.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_B_4() throws Exception {
        // prepare new scheme
        Map<ComponentType, String> mapping = new HashMap<ComponentType, String>();
        mapping.put(ComponentType.APPLICATION, "Custom Component Permissions Scheme");
        mapping.put(ComponentType.CUSTOM_COMPONENT, "Generic Component Permission Scheme");

        try {
            new DefaultJiraManager(null, null, null, mapping, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, Log, Map&lt;ComponentType,String&gt;,
     * Map&lt;ComponentType,String&gt;, Map&lt;ComponentType,String&gt;)</code>.
     * <p>
     * Checks constructor for failure. Passes malformed URL.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_B_5() throws Exception {
        try {
            new DefaultJiraManager("(mAlF.oRMe_d", null, null, null, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
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
    public void testDefaultJiraManager_B_6() throws Exception {
        Log log = new Jdk14LogFactory().createLog("abc");
        DefaultJiraManager defaultManager =
            new DefaultJiraManager(UnitTestsHelper.getURL(), log, null, null, null);
        assertSame("log was propagated incorrectly", log, defaultManager.getLog());

        // make sure that URL is correct
        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());
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
    public void testDefaultJiraManager_B_7() throws Exception {
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
            new DefaultJiraManager(UnitTestsHelper.getURL(), log, mapping1, mapping2, mapping3);
        assertSame("log was propagated incorrectly", log, defaultManager.getLog());

        // make sure that URL is correct
        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

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
     * Checks constructor for failure. Passes null.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_1() throws Exception {
        try {
            new DefaultJiraManager(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks that constructor correctly creates new instance. All parameters present in
     * configuration.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_2() throws Exception {
        DefaultJiraManager defaultManager = new DefaultJiraManager(configuration);
        // make sure that scheme and URL were propagated
        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

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
     * Checks that constructor correctly creates new instance. Configuration doesn't contain
     * parameters. Default values must be used.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_3() throws Exception {
        configuration.removeChild("jiraManagerNamespace");
        configuration.removeChild("notificationSchemesNamespace");
        configuration.removeChild("permissionSchemesNamespace");
        configuration.removeChild("issueSecuritySchemesNamespace");

        DefaultJiraManager defaultManager = new DefaultJiraManager(configuration);
        // make sure that scheme and URL were propagated
        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

        // check that logging is disabled
        assertNull("logging must be disabled", defaultManager.getLog());

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
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks constructor for failure. JIRA URL is malformed.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_4() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", "m)aLfORm.ED");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks constructor for failure. JIRA URL is empty.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_5() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", " ");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks constructor for failure. JIRA URL is not a String instance.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_6() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("jira_url_token", Boolean.TRUE);

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks constructor for failure. Property with JIRA URL has two values.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_7() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValues("jira_url_token",
            new Object[] {"a", "b"});

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks constructor for failure. Property 'generic_token' is missed from
     * 'notificationSchemesNamespace'.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_8() throws Exception {
        configuration.getChild("notificationSchemesNamespace").removeProperty("generic_token");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks constructor for failure. Property 'generic_token' has empty value.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_9() throws Exception {
        configuration.getChild("notificationSchemesNamespace").setPropertyValue("generic_token", " ");

        try {
            new DefaultJiraManager(configuration);
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks that constructor correctly creates new instance. Property 'log_name_token' has empty
     * value.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_10() throws Exception {
        configuration.getChild("jiraManagerNamespace").setPropertyValue("log_name_token", " ");

        DefaultJiraManager defaultManager = new DefaultJiraManager(configuration);
        assertNotNull("logger must be retrieved from LogManager", defaultManager.getLog());
    }

    /**
     * Tests <code>DefaultJiraManager(ConfigurationObject)</code>.
     * <p>
     * Checks that constructor correctly creates new instance. Property 'log_name_token' is missed.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_C_11() throws Exception {
        configuration.getChild("jiraManagerNamespace").removeProperty("log_name_token");

        DefaultJiraManager defaultManager = new DefaultJiraManager(configuration);
        assertNull("logging must be disabled", defaultManager.getLog());
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks constructor for failure. Passes null file name.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_1() throws Exception {
        try {
            new DefaultJiraManager(null, "com.topcoder.jira");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks constructor for failure. Passes empty file name.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_2() throws Exception {
        try {
            new DefaultJiraManager(" ", "com.topcoder.jira");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks constructor for failure. Passes null namespace.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_3() throws Exception {
        try {
            new DefaultJiraManager("test_files/config.properties", null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks constructor for failure. Passes empty namespace.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_4() throws Exception {
        try {
            new DefaultJiraManager("test_files/config.properties", " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks constructor for failure. Passes empty file.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_5() throws Exception {
        try {
            new DefaultJiraManager("test_files/empty.file", "namespace");
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks constructor for failure. Passes non-existing file.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_6() throws Exception {
        try {
            new DefaultJiraManager("test_files/nonexistingfile", "namespace");
            fail("JiraManagerConfigurationException was expected");
        } catch (JiraManagerConfigurationException e) {
            // expected
        }
    }

    /**
     * Tests <code>DefaultJiraManager(String, String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Only
     * logging is configured.
     *
     * @throws Exception if error occurs.
     */
    public void testDefaultJiraManager_D_7() throws Exception {
        DefaultJiraManager defaultManager =
            new DefaultJiraManager("test_files/config.properties", "com.topcoder.jira");

        // logging must be enabled
        assertNotNull("logging must be enabled", defaultManager.getLog());

        // make sure that scheme and URL were propagated
        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());
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
    public void testDefaultJiraManager_D_8() throws Exception {
        DefaultJiraManager defaultManager =
            new DefaultJiraManager("test_files/schemes.properties", "com.topcoder.jira");

        // logging must be disabled
        assertNull("logging must be disabled", defaultManager.getLog());

        // make sure that scheme and URL were propagated
        String token = defaultManager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());
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
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_1() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.deleteProject(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_2() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.deleteProject(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null key.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_3() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.deleteProject(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty key.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_4() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.deleteProject(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_5() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.deleteProject("token", project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_6() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.deleteProject(securityToken, project.getKey() + "123");
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_7() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        manager.deleteProject(securityToken, project.getKey());
        assertEquals("project was not removed", JiraProjectRetrievalResultAction.PROJECT_NOT_FOUND, manager
            .getProject(securityToken, project.getKey()).getRetrievalResult());
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_8() throws Exception {
        try {
            managerToNoWhere.deleteProject(securityToken, project.getKey());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>deleteProject(String, String)</code>.
     * <p>
     * Checks method for failure. Uses non-authorized user.
     *
     * @throws Exception if error occurs.
     */
    public void testDeleteProject_9() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.deleteProject(token, project.getKey());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
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
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_1() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.getProjectVersions(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_2() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.getProjectVersions(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null key.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_3() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.getProjectVersions(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty key.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_4() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.getProjectVersions(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_5() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.getProjectVersions("token", project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Passes non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_6() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        try {
            manager.getProjectVersions(securityToken, project.getKey() + "1");
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Only one version exists. Uses project key written
     * in lowercase letters.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_7() throws Exception {
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
    public void testGetProjectVersions_8() throws Exception {
        // create first project with one version
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        // create second project with three versions
        project.setKey(UnitTestsHelper.getPrefix() + "AAA");
        project.setName(UnitTestsHelper.getPrefix() + "TTT");
        manager.createProject(securityToken, project, version, ComponentType.CUSTOM_COMPONENT);
        version.setName("versionx");
        manager.addVersionToProject(securityToken, project, version);
        version.setName("versiony");
        manager.addVersionToProject(securityToken, project, version);

        List<JiraVersion> list = manager.getProjectVersions(securityToken, project.getKey());

        assertEquals("three versions are expected", 3, list.size());
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_9() throws Exception {
        try {
            managerToNoWhere.getProjectVersions(securityToken, project.getKey());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProjectVersions(String, String)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to get versions.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProjectVersions_10() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.getProjectVersions(token, project.getKey());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_1() throws Exception {
        try {
            manager.getProject(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_2() throws Exception {
        try {
            manager.getProject(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null key.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_3() throws Exception {
        try {
            manager.getProject(securityToken, (String) null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty key.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_4() throws Exception {
        try {
            manager.getProject(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_5() throws Exception {
        try {
            manager.getProject("token", project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Requires non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_6() throws Exception {
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
    public void testGetProject_A_7() throws Exception {
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
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_8() throws Exception {
        try {
            managerToNoWhere.getProject(securityToken, project.getKey());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to get project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_A_9() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.getProject(token, project.getKey());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_1() throws Exception {
        try {
            manager.getProject(null, project.getKey(), version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_2() throws Exception {
        try {
            manager.getProject(" ", project.getKey(), version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes null project key.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_3() throws Exception {
        try {
            manager.getProject(securityToken, null, version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty project key.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_4() throws Exception {
        try {
            manager.getProject(securityToken, " ", version.getName());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes null version name.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_5() throws Exception {
        try {
            manager.getProject(securityToken, project.getKey(), null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty version name.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_6() throws Exception {
        try {
            manager.getProject(securityToken, project.getKey(), " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_7() throws Exception {
        try {
            manager.getProject("token", project.getKey(), version.getName());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks that getter returns expected value. Project and version don't exist.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_8() throws Exception {
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
    public void testGetProject_B_9() throws Exception {
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
    public void testGetProject_B_10() throws Exception {
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
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_11() throws Exception {
        try {
            managerToNoWhere.getProject(securityToken, project.getKey(), version.getName());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, String, String)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to get project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_B_12() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.getProject(token, project.getKey(), version.getName());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_1() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(result.getProject().getId());
        try {
            manager.getProject(null, id);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_2() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(result.getProject().getId());
        try {
            manager.getProject(" ", id);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks method for failure. Passes null id.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_3() throws Exception {
        try {
            manager.getProject(securityToken, (Long) null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_4() throws Exception {
        JiraProjectCreationResult result =
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        Long id = Long.valueOf(result.getProject().getId());

        try {
            manager.getProject("token", id);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks that getter returns expected value. Requires non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_5() throws Exception {
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
    public void testGetProject_C_6() throws Exception {
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
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_7() throws Exception {
        try {
            managerToNoWhere.getProject(securityToken, Long.valueOf(239));
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>getProject(String, Long)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to get project.
     *
     * @throws Exception if error occurs.
     */
    public void testGetProject_C_8() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.getProject(token, Long.valueOf(239));
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
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
     * Checks that method works correctly. True is expected.
     */
    public void testIsVerboseLogging_1() {
        manager.setVerboseLogging(true);
        assertTrue("returned value is incorrect", manager.isVerboseLogging());
    }

    /**
     * Tests <code>isVerboseLogging()</code>.
     * <p>
     * Checks that method works correctly. False is expected.
     */
    public void testIsVerboseLogging_2() {
        manager.setVerboseLogging(true);
        manager.setVerboseLogging(false);
        assertFalse("returned value is incorrect", manager.isVerboseLogging());
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null user name.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_1() throws Exception {
        manager.logout(securityToken);
        try {
            manager.login(null, UnitTestsHelper.getPassword());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty user name.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_2() throws Exception {
        manager.logout(securityToken);
        try {
            manager.login(" ", UnitTestsHelper.getPassword());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null password.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_3() throws Exception {
        manager.logout(securityToken);
        try {
            manager.login(UnitTestsHelper.getUserName(), null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty password.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_4() throws Exception {
        manager.logout(securityToken);
        try {
            manager.login(UnitTestsHelper.getUserName(), " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect name.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_5() throws Exception {
        manager.logout(securityToken);
        try {
            manager.login("non@existing.name", "123");
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect password.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_6() throws Exception {
        manager.logout(securityToken);
        try {
            manager.login(UnitTestsHelper.getUserName(), "non@existing.password");
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_7() throws Exception {
        manager.logout(securityToken);

        String token = manager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());
        assertNotNull("returned token couldn't be null", token);
    }

    /**
     * Tests <code>login(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testLogin_8() throws Exception {

        try {
            managerToNoWhere.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>logout(String)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_1() throws Exception {
        try {
            manager.logout(null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>logout(String)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_2() throws Exception {
        try {
            manager.logout(" ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>logout(String)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_3() throws Exception {
        manager.logout(securityToken);
        // try to get project
        try {
            manager.getProject(securityToken, project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>logout(String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testLogout_4() throws Exception {

        try {
            managerToNoWhere.logout(securityToken);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_1() throws Exception {
        try {
            manager.projectExists(null, project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_2() throws Exception {
        try {
            manager.projectExists(" ", project.getKey());
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Passes null key.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_3() throws Exception {
        try {
            manager.projectExists(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Passes empty key.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_4() throws Exception {
        try {
            manager.projectExists(securityToken, " ");
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Passes old token.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_5() throws Exception {
        manager.logout(securityToken);
        try {
            manager.projectExists(securityToken, project.getKey());
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks that method works correctly. Project doesn't exist.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_6() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        assertFalse("returned value is incorrect", manager.projectExists(securityToken, project.getName()
            + "abc"));
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks that method works correctly. Project exists (name is written in lowercase characters).
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_7() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        assertTrue("returned value is incorrect", manager.projectExists(securityToken, project.getName()
            .toLowerCase()));
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_8() throws Exception {
        try {
            managerToNoWhere.projectExists(securityToken, project.getName());
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>projectExists(String, String)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to check project.
     *
     * @throws Exception if error occurs.
     */
    public void testProjectExists_9() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.projectExists(token, project.getName());
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_1() throws Exception {
        try {
            manager.releaseVersion(null, project.getKey(), version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_2() throws Exception {
        try {
            manager.releaseVersion(" ", project.getKey(), version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null key.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_3() throws Exception {
        try {
            manager.releaseVersion(securityToken, null, version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes empty key.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_4() throws Exception {
        try {
            manager.releaseVersion(securityToken, " ", version);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes null version.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_5() throws Exception {
        try {
            manager.releaseVersion(securityToken, project.getKey(), null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes old token.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_6() throws Exception {
        manager.logout(securityToken);
        try {
            manager.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes non-existing project.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_7() throws Exception {
        try {
            manager.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes non-existing version.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_8() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        version.setName("abracadabra");
        try {
            manager.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks that method works correctly. Releases version.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_9() throws Exception {
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
    public void testReleaseVersion_10() throws Exception {
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
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_11() throws Exception {
        try {
            managerToNoWhere.releaseVersion(securityToken, project.getKey(), version);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>releaseVersion(String, String, JiraVersion)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to release version.
     *
     * @throws Exception if error occurs.
     */
    public void testReleaseVersion_12() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.releaseVersion(token, project.getKey(), version);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
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
     * Checks method for failure. Passes null token.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_1() throws Exception {
        try {
            manager.updateProject(null, project);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes empty token.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_2() throws Exception {
        try {
            manager.updateProject(" ", project);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes null project.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_3() throws Exception {
        try {
            manager.updateProject(securityToken, null);
            fail("IllegalArgumentException was expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes invalid token.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_4() throws Exception {
        try {
            manager.updateProject("token", project);
            fail("JiraSecurityTokenExpiredException was expected");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes non-existed project.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_5() throws Exception {
        try {
            manager.updateProject(securityToken, project);
            fail("JiraManagerException was expected");
        } catch (JiraManagerException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes malformed project.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_6() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        project.setName("");
        try {
            manager.updateProject(securityToken, project);
            fail("JiraProjectValidationException was expected");
        } catch (JiraProjectValidationException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks that method works correctly.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_7() throws Exception {
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        project.setDescription("xxx");
        project.setProjectUrl("http://xxx");
        manager.updateProject(securityToken, project);

        // check that values were updated
        project = manager.getProject(securityToken, project.getKey()).getProject();
        assertEquals("update was not performed correctly", "xxx", project.getDescription());
        assertEquals("update was not performed correctly", "http://xxx", project.getProjectUrl());
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Passes incorrect URL.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_8() throws Exception {
        try {
            managerToNoWhere.updateProject(securityToken, project);
            fail("JiraConnectionException was expected");
        } catch (JiraConnectionException e) {
            // expected
        }
    }

    /**
     * Tests <code>updateProject(String, JiraProject)</code>.
     * <p>
     * Checks method for failure. Non-authorized user tries to update project.
     *
     * @throws Exception if error occurs.
     */
    public void testUpdateProject_9() throws Exception {
        String token = manager.login(UnitTestsHelper.getGuestName(), UnitTestsHelper.getGuestPassword());
        try {
            manager.updateProject(token, project);
            fail("JiraNotAuthorizedException was expected");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        manager.logout(token);
    }
}
