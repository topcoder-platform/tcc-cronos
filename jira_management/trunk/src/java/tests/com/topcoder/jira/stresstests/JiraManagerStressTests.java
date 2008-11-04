/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.stresstests;

import java.util.Date;

import junit.framework.TestCase;

import com.atlassian.jira.rpc.soap.beans.RemoteProject;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.managers.DefaultJiraManager;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

/**
 * <p>
 * Stress Tests for Jira Management.
 * </p>
 *
 * @author onsky
 * @version 1.0
 */
public class JiraManagerStressTests extends TestCase {

    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final long TOTAL_RUNS = 1000;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

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
     * Set up test environment.
     *
     * @throws Exception if error occurs.
     */
    protected void setUp() throws Exception {
        cleanJiraSystem();

        // create project
        project = new JiraProject();
        project.setDescription("bla-bla-bla");
        project.setKey("TCJ" + "KEY");
        project.setLead("root");
        project.setName("TCJ" + "name");
        project.setProjectUrl("http://projectUrl");
        project.setUrl("http://url");

        // create version
        version = new JiraVersion();
        version.setName("version1");

        // create valid manager
        manager = new DefaultJiraManager();
    }

    /**
     * Restores environment.
     *
     * @throws Exception if error occurs.
     */
    protected void tearDown() throws Exception {
        cleanJiraSystem();
    }

    /**
     * <p>
     * The stress test for creating project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testCreateProject() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        }

        this.printResult("creating project", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for adding version to project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddVersionToProject() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.addVersionToProject(securityToken, project, version);
        }

        this.printResult("adding version to project", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for getting project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProject() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.getProject(securityToken, project.getKey());
        }

        this.printResult("getting project", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for getting project versions.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testGetProjectVersions() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.getProjectVersions(securityToken, project.getKey());
        }

        this.printResult("getting project versions", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for updating project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdateProject() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.updateProject(securityToken, project);
        }

        this.printResult("updating project", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for deleting project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteProject() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
            manager.deleteProject(securityToken, project.getKey());
        }

        this.printResult("deleting project", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for checking if project eixsts.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testProjectExists() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.projectExists(securityToken, "test");
        }

        this.printResult("checking if project exists", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for deleting project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testReleaseVersion() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        for (int i = 0; i < TOTAL_RUNS; i++) {
            version.setReleased(true);
            version.setReleaseDate(new Date());
            manager.releaseVersion(securityToken, project.getKey(), version);
        }

        this.printResult("releasing version", TOTAL_RUNS);
    }

    /**
     * <p>
     * The stress test for deleting project.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testArchiveVersion() throws Exception {
        String securityToken = manager.login("root", "123");
        start();

        manager.createProject(securityToken, project, version, ComponentType.APPLICATION);
        for (int i = 0; i < TOTAL_RUNS; i++) {
            manager.archiveVersion(securityToken, project.getKey(), version.getName(), true);
        }

        this.printResult("releasing version", TOTAL_RUNS);
    }

    /**
     * <p>
     * Starts to count time.
     * </p>
     */
    private void start() {
        current = System.currentTimeMillis();
    }

    /**
     * <p>
     * Prints test result.
     * </p>
     *
     * @param name the test name
     * @param count the run count
     */
    private void printResult(String name, long count) {
        System.out.println("The test [" + name + "] run " + count + " times, took time: "
            + (System.currentTimeMillis() - current) + " ms");
    }

    /**
     * Removes all created projects and logs out.
     *
     * @throws Exception if error occurs.
     */
    private void cleanJiraSystem() throws Exception {
        DefaultJiraManager manager = new DefaultJiraManager();
        // login
        String token = manager.login("root", "123");
        // get projects
        RemoteProject[] projects = manager.getService().getProjectsNoSchemes(token);

        for (RemoteProject project : projects) {
            if (project.getKey().startsWith("TCJ")) {
                manager.deleteProject(token, project.getKey());
            }
        }
        // logout
        manager.logout(token);
    }
}
