/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.stresstests;

import java.lang.reflect.Field;

import org.easymock.EasyMock;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraManager;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.webservices.bean.JiraManagementServiceBean;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Stress test cases for JIRA Services component.
 * </p>
 *
 * @author victorsam
 * @version 1.0
 */
public class JIRAServiceStressTests extends TestCase {

    /**
     * <p>
     * This constant represents the test count used for testing.
     * </p>
     */
    private static final int NUMBER = 10000;

    /**
     * <p>
     * This constant represents the current time used for testing.
     * </p>
     */
    private long current = -1;

    /**
     * <p>
     * This JiraManagementServiceBean instance used for testing.
     * </p>
     */
    private JiraManagementServiceBean instance;

    /**
     * <p>
     * This JiraManager instance used for testing.
     * </p>
     */
    private JiraManager manager;

    /**
     * <p>
     * This JiraProject instance used for testing.
     * </p>
     */
    private JiraProject project;

    /**
     * <p>
     * This JiraVersion instance used for testing.
     * </p>
     */
    private JiraVersion version;

    /**
     * <p>
     * This JiraProjectCreationResult instance used for testing.
     * </p>
     */
    private JiraProjectCreationResult creationResult;

    /**
     * <p>
     * This JiraProjectRetrievalResult instance used for testing.
     * </p>
     */
    private JiraProjectRetrievalResult retrievalResult;

    /**
     * <p>Setup test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void setUp() throws Exception {
        instance = new JiraManagementServiceBean();
        manager = EasyMock.createStrictMock(JiraManager.class);
        inject(instance, "jiraManager", manager);
        project = new JiraProject();
        version = new JiraVersion();
        creationResult = new JiraProjectCreationResult();
        retrievalResult = new JiraProjectRetrievalResult();
    }

    /**
     * <p>Tears down test environment.</p>
     * @throws Exception to JUnit
     *
     */
    protected void tearDown() throws Exception {
        instance = null;
    }

    /**
     * <p>Return all tests.</p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(JIRAServiceStressTests.class);
    }

    /**
     * <p>Tests JiraManagementServiceBean#login(String, String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testLogin() throws Exception {
        EasyMock.expect(instance.login("userName", "password")).andReturn("success");
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertEquals("Failed to login correctly.", "success", instance.login("userName", "password"));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#login(String, String)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#logout(String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testLogout() throws Exception {
        instance.logout("my token");
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            instance.logout("my token");
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#logout(String)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#createProject(String, JiraProject, JiraVersion, ComponentType)
     * for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testCreateProject() throws Exception {
        EasyMock.expect(instance.createProject("token", project, version, ComponentType.CUSTOM_COMPONENT)).andReturn(
            creationResult);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertSame("Failed to create project correctly.", creationResult, instance.createProject("token", project,
                version, ComponentType.CUSTOM_COMPONENT));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#createProject(String, JiraProject, JiraVersion, ComponentType)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#addVersionToProject(String, JiraProject, JiraVersion)
     * for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testAddVersionToProject() throws Exception {
        EasyMock.expect(instance.addVersionToProject("token", project, version)).andReturn(creationResult);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertSame("Failed to add version to project correctly.", creationResult, instance.addVersionToProject(
                "token", project, version));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#addVersionToProject(String, JiraProject, JiraVersion)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#getProject(String, String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetProject() throws Exception {
        EasyMock.expect(instance.getProject("token", "projectKey")).andReturn(retrievalResult);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertSame("Failed to get project correctly.", retrievalResult, instance.getProject("token", "projectKey"));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#getProject(String, String)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#getProject(String, String, String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetProject2() throws Exception {
        EasyMock.expect(instance.getProject("token", "projectKey", "version")).andReturn(retrievalResult);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertSame("Failed to get project correctly.", retrievalResult, instance.getProject("token", "projectKey",
                "version"));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#getProject(String, String, String)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#getProject(String, Long) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testGetProject3() throws Exception {
        EasyMock.expect(instance.getProject("token", 1L)).andReturn(retrievalResult);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertSame("Failed to get project correctly.", retrievalResult, instance.getProject("token", 1L));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#getProject(String, Long)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#deleteProject(String, String) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testDeleteProject() throws Exception {
        instance.deleteProject("token", "projectKey");
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            instance.deleteProject("token", "projectKey");
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#deleteProject(String, String)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#updateProject(String, JiraProject) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testUpdateProject() throws Exception {
        EasyMock.expect(instance.updateProject("token", project)).andReturn(project);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            assertSame("Failed to update project correctly.", project, instance.updateProject("token", project));
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#updateProject(String, JiraProject)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#releaseVersion(String, String, JiraVersion) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testReleaseVersion() throws Exception {
        instance.releaseVersion("token", "projectKey", version);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            instance.releaseVersion("token", "projectKey", version);
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#releaseVersion(String, String, JiraVersion)", NUMBER);
    }

    /**
     * <p>Tests JiraManagementServiceBean#archiveVersion(String, String, String, boolean) for stress purpose.</p>
     * @throws Exception to JUnit
     */
    public void testArchiveVersion() throws Exception {
        instance.archiveVersion("token", "projectKey", "version", true);
        EasyMock.expectLastCall().times(NUMBER);
        EasyMock.replay(manager);

        start();

        for (int i = 0; i < NUMBER; i++) {
            instance.archiveVersion("token", "projectKey", "version", true);
        }

        EasyMock.verify(manager);

        printResult("JiraManagementServiceBean#archiveVersion(String, String, String, boolean)", NUMBER);
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
     * Injects provided object into property of another object.
     *
     * @param target   object to inject in
     * @param property name of the property to inject in
     * @param value    value to inject
     * @throws Exception when it occurs deeper
     */
    private static void inject(Object target, String property, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(property);
        field.setAccessible(true);
        field.set(target, value);
    }
}