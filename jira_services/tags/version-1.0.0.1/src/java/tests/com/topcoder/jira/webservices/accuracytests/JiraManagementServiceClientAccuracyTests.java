/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.accuracytests;

import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraProjectRetrievalResult;

import java.util.List;

import com.topcoder.jira.webservices.client.JiraManagementServiceClient;
import junit.framework.TestCase;

/**
 * Accuracy tests for JiraManagementServiceClient class.
 * 
 * @author zsudraco
 * @version 1.0
 */
public class JiraManagementServiceClientAccuracyTests extends TestCase {
	/**
	 * The JiraManagementServiceClient instance for the following tests.
	 */
	private JiraManagementServiceClient tester;

	/**
	 * <p>
	 * Sets up test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to junit
	 */
	protected void setUp() throws Exception {
		tester = new JiraManagementServiceClient(
						"http://127.0.0.1:8080/jira_services_accuracy-ejb/JiraManagementServiceBean?wsdl");
	}

	/**
	 * <p>
	 * Tears down test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to junit
	 */
	protected void tearDown() throws Exception {
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_1() throws Exception {
		String res = tester.getJiraManagementServicePort().login("username", "password");
		assertEquals("success", res);
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_2() throws Exception {
		tester.getJiraManagementServicePort().logout("token");
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_3() throws Exception {
		JiraProject project = new JiraProject();
		project.setName("project");
		JiraVersion version = new JiraVersion();
		version.setName("1.0");
		JiraProjectCreationResult res = tester.getJiraManagementServicePort().createProject("token", project, version,
						ComponentType.CUSTOM_COMPONENT);
		assertEquals("project", res.getProject().getName());
		assertEquals("1.0", res.getVersion().getName());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_4() throws Exception {
		JiraProject project = new JiraProject();
		project.setName("project");
		JiraVersion version = new JiraVersion();
		version.setName("1.0");
		JiraProjectCreationResult res = tester.getJiraManagementServicePort().addVersionToProject("token", project,
						version);
		assertEquals("project", res.getProject().getName());
		assertEquals("1.0", res.getVersion().getName());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_5() throws Exception {
		JiraProjectRetrievalResult res = tester.getJiraManagementServicePort().getProject("token", "projectKey");
		assertEquals("projectKey", res.getProject().getKey());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_6() throws Exception {
		JiraProjectRetrievalResult res = tester.getJiraManagementServicePort().getProject("token", "projectKey",
						"versionName");
		assertEquals("projectKey", res.getProject().getKey());
		assertEquals("versionName", res.getVersion().getName());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_7() throws Exception {
		List<JiraVersion> res = tester.getJiraManagementServicePort().getProjectVersions("token", "projectKey");
		assertEquals(1, res.size());
		assertEquals("projectKey", res.get(0).getName());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_8() throws Exception {
		boolean res = tester.getJiraManagementServicePort().projectExists("token", "projectName");
		assertFalse(res);
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_9() throws Exception {
		JiraProjectRetrievalResult res = tester.getJiraManagementServicePort().getProject("token", new Long(123));
		assertEquals("123", res.getProject().getId());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_10() throws Exception {
		tester.getJiraManagementServicePort().deleteProject("token", "projectKey");
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_11() throws Exception {
		JiraProject project = new JiraProject();
		project.setName("project");
		project = tester.getJiraManagementServicePort().updateProject("token", project);
		assertEquals("project", project.getName());
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_12() throws Exception {
		JiraVersion version = new JiraVersion();
		version.setName("1.0");
		tester.getJiraManagementServicePort().releaseVersion("token", "projectKey", version);
	}

	/**
	 * Accuracy test for the JiraManagementServiceClient class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_13() throws Exception {
		tester.getJiraManagementServicePort().archiveVersion("token", "projectKey", "versionName", true);
	}

}
