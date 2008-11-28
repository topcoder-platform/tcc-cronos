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

import com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate;
import junit.framework.TestCase;

/**
 * Accuracy tests for JiraManagerWebServiceDelegate class.
 * 
 * @author zsudraco
 * @version 1.0
 */
public class JiraManagerWebServiceDelegateAccuracyTests extends TestCase {
	/**
	 * The JiraManagerWebServiceDelegate instance for the following tests.
	 */
	private JiraManagerWebServiceDelegate tester;

	/**
	 * <p>
	 * Sets up test environment.
	 * </p>
	 * 
	 * @throws Exception
	 *             to junit
	 */
	protected void setUp() throws Exception {
		tester = new JiraManagerWebServiceDelegate(
						"accuracy/com/topcoder/jira/webservices/delegate/JiraManagerWebServiceDelegate.properties",
						"com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate");
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
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_1() throws Exception {
		String res = tester.login("username", "password");
		assertEquals("success", res);
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_2() throws Exception {
		tester.logout("token");
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_3() throws Exception {
		JiraProject project = new JiraProject();
		project.setName("project");
		JiraVersion version = new JiraVersion();
		version.setName("1.0");
		JiraProjectCreationResult res = tester.createProject("token", project, version, ComponentType.CUSTOM_COMPONENT);
		assertEquals("project", res.getProject().getName());
		assertEquals("1.0", res.getVersion().getName());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_4() throws Exception {
		JiraProject project = new JiraProject();
		project.setName("project");
		JiraVersion version = new JiraVersion();
		version.setName("1.0");
		JiraProjectCreationResult res = tester.addVersionToProject("token", project, version);
		assertEquals("project", res.getProject().getName());
		assertEquals("1.0", res.getVersion().getName());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_5() throws Exception {
		JiraProjectRetrievalResult res = tester.getProject("token", "projectKey");
		assertEquals("projectKey", res.getProject().getKey());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_6() throws Exception {
		JiraProjectRetrievalResult res = tester.getProject("token", "projectKey", "versionName");
		assertEquals("projectKey", res.getProject().getKey());
		assertEquals("versionName", res.getVersion().getName());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_7() throws Exception {
		List<JiraVersion> res = tester.getProjectVersions("token", "projectKey");
		assertEquals(1, res.size());
		assertEquals("projectKey", res.get(0).getName());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_8() throws Exception {
		boolean res = tester.projectExists("token", "projectName");
		assertFalse(res);
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_9() throws Exception {
		JiraProjectRetrievalResult res = tester.getProject("token", new Long(123));
		assertEquals("123", res.getProject().getId());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_10() throws Exception {
		tester.deleteProject("token", "projectKey");
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_11() throws Exception {
		JiraProject project = new JiraProject();
		project.setName("project");
		project = tester.updateProject("token", project);
		assertEquals("project", project.getName());
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_12() throws Exception {
		JiraVersion version = new JiraVersion();
		version.setName("1.0");
		tester.releaseVersion("token", "projectKey", version);
	}

	/**
	 * Accuracy test for the JiraManagerWebServiceDelegate class.
	 * 
	 * @throws Exception
	 *             to JUnit
	 */
	public void test_13() throws Exception {
		tester.archiveVersion("token", "projectKey", "versionName", true);
	}

}
