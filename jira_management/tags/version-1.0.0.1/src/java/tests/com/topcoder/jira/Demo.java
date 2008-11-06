/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.defaults.DefaultConfigurationObject;
import com.topcoder.jira.managers.DefaultJiraManager;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.log.jdk14.Jdk14LogFactory;

/**
 * This class demonstrates usage of the JIRA Management component. It shows how to:
 * <ul>
 * <li>create manager</li>
 * <li>manager projects</li>
 * <li>manage versions</li>
 * </ul>
 *
 * @author agh
 * @version 1.0
 */
public class Demo extends TestCase {

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
        // create configuration
        configuration = new DefaultConfigurationObject("root");
    }

    /**
     * Restores environment.
     *
     * @throws Exception if error occurs.
     */
    protected void tearDown() throws Exception {
        UnitTestsHelper.cleanJiraSystem();
    }

    /**
     * Demo on how to create manager.
     *
     * @throws Exception if error occurs.
     */
    public void testCreateManager() throws Exception {
        // create manager using default constructor
        manager = new DefaultJiraManager();

        // create manager by propagating all required arguments
        Map<ComponentType, String> mapping1 = new HashMap<ComponentType, String>();
        mapping1.put(ComponentType.APPLICATION, "Client Project Notification Scheme");
        mapping1.put(ComponentType.CUSTOM_COMPONENT, "Component Management Notification Scheme");
        mapping1.put(ComponentType.GENERIC_COMPONENT, "Component Management Notification Scheme");
        Map<ComponentType, String> mapping2 = new HashMap<ComponentType, String>();
        mapping2.put(ComponentType.APPLICATION, "Client Projects");
        mapping2.put(ComponentType.CUSTOM_COMPONENT, "Custom Component Permissions Scheme");
        mapping2.put(ComponentType.GENERIC_COMPONENT, "Generic Component Permission Scheme");
        Map<ComponentType, String> mapping3 = new HashMap<ComponentType, String>();
        mapping3.put(ComponentType.APPLICATION, "Client Project Issues Scheme");
        mapping3.put(ComponentType.CUSTOM_COMPONENT, "Generic Component Issues Scheme");
        mapping3.put(ComponentType.GENERIC_COMPONENT, "Custom Component Issues Scheme");

        Log log = new Jdk14LogFactory().createLog("abc");
        String url = UnitTestsHelper.getURL();
        manager = new DefaultJiraManager(url, log, mapping1, mapping2, mapping3);

        // create manager via configuration
        manager = new DefaultJiraManager(configuration);

        // create manager using configuration file
        manager = new DefaultJiraManager("test_files/config.properties", "com.topcoder.jira");
    }

    /**
     * Demo on how to manage projects.
     *
     * @throws Exception if error occurs.
     */
    public void testManageProjects() throws Exception {
        // before usage user can turn on/off logging (null log means that logging is disabled)
        manager.setLog(LogManager.getLog());
        // verbose or standard logging can be used
        manager.setVerboseLogging(true);

        // at first, user must login (returned security token will be used in other methods)
        String securityToken = manager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());

        // after login we can create project
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        // we can call this method once more with the same project and version (nothing will happen)
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        // however if new version is passed then it will be added
        version.setName("otherVersion");
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        // we can also add versions by calling addVersionToProject
        version.setName("otherVersion2");
        manager.addVersionToProject(securityToken, project, version);

        // getProject methods are used to retrieve projects (key or id must be specified)
        manager.getProject(securityToken, project.getKey());
        manager.getProject(securityToken, Long.valueOf(239));
        // to retrieve project with specified key and version other method is used
        manager.getProject(securityToken, project.getKey(), "otherVersion2");

        // all project versions can also be fetched
        manager.getProjectVersions(securityToken, project.getKey());

        // method updateProject is used to update projects
        project.setDescription("new description");
        manager.updateProject(securityToken, project);

        // we can check whether the project with given name exists or not
        manager.projectExists(securityToken, "some-name");

        // project can be removed
        manager.deleteProject(securityToken, project.getKey());

        // at the end we should logout from system
        manager.logout(securityToken);
    }

    /**
     * Demo on how to manage versions.
     *
     * @throws Exception if error occurs.
     */
    public void testManageVersions() throws Exception {
        // turn logging on
        manager.setLog(LogManager.getLog());
        // use standard logging
        manager.setVerboseLogging(false);
        // perform login
        String securityToken = manager.login(UnitTestsHelper.getUserName(), UnitTestsHelper.getPassword());
        // create project
        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);

        // we can add versions to the project
        version.setName("otherVersion2");
        manager.addVersionToProject(securityToken, project, version);

        // all project versions can be fetched
        manager.getProjectVersions(securityToken, project.getKey());

        // version can be released
        version.setReleased(true);
        version.setReleaseDate(new Date());
        manager.releaseVersion(securityToken, project.getKey(), version);
        // or unreleased
        version.setReleased(false);
        manager.releaseVersion(securityToken, project.getKey(), version);

        // version can be archived
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), true);
        // or unarchived
        manager.archiveVersion(securityToken, project.getKey(), version.getName(), false);

        // logout
        manager.logout(securityToken);
    }
}
