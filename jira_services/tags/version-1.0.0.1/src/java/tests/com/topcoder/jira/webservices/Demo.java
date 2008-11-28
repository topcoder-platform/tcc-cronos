/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.webservices.client.JiraManagementServiceClient;
import com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.ws.WebServiceException;
import javax.xml.soap.SOAPFactory;
import java.net.URL;
import java.util.List;

/**
 * Demonstration for this component.
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends TestCase {
    /**
     * Default location where jboss puts wsdl for the deployed application.
     */
    public static final String WSDL_LOCATION =
            "http://127.0.0.1:8080/jira_services-jira_services/JiraManagementServiceBean?wsdl";

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * This demo is similar the one available in the JIRA Management component. This shows that the delegate usage is
     * the same, which is the point of the delegate.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDelegate() throws Exception {
        // create a JiraManagerWebServiceDelegate
        JiraManagerWebServiceDelegate jiraManager = new JiraManagerWebServiceDelegate(
                JiraManagerWebServiceDelegate.DEFAULT_CONFIG_PATH,
                "com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate2");

        // log in to JIRA and retrieve the needed token to perform the operations
        String token = jiraManager.login("ivern", "password");

        // initialize some variables to use
        // cause back-end is mocked, we does not care about contents
        JiraProjectRetrievalResult jiraProjectRetrievalResult;
        JiraProject jiraProject = new JiraProject();
        JiraProject otherJiraProject = new JiraProject();
        JiraVersion jiraVersion = new JiraVersion();
        JiraVersion otherJiraVersion = new JiraVersion();
        JiraVersion otherJiraVersion1 = new JiraVersion();

        // create a new Project in JIRA:
        jiraManager.createProject(token, jiraProject, jiraVersion, ComponentType.CUSTOM_COMPONENT);

        // add version to project in JIRA:
        jiraManager.addVersionToProject(token, jiraProject, otherJiraVersion);

        // get project with the given key from JIRA:
        jiraProjectRetrievalResult = jiraManager.getProject(token, "projectKey1");

        // get project with the given key and version name from JIRA:
        jiraProjectRetrievalResult = jiraManager.getProject(token, "projectKey2", "1.01");

        // get JIRA versions for JIRA project
        List<JiraVersion> versions = jiraManager.getProjectVersions(token, "projectKey2");

        // Check if a JIRA project exists:
        boolean exists = jiraManager.projectExists(token, "projectKey2");

        // get project with the given id from JIRA:
        jiraProjectRetrievalResult = jiraManager.getProject(token, new Long(111222));

        // update a JIRA project:
        jiraManager.updateProject(token, otherJiraProject);

        // release a JIRA version:
        jiraManager.releaseVersion(token, "projectKey2", otherJiraVersion1);

        // archive a JIRA version:
        jiraManager.archiveVersion(token, "projectKey2", "1.0", true);

        // delete a JIRA project:
        jiraManager.deleteProject(token, "projectKey2");
    }

    /**
     * This demo will briefly show how to use the JiraManagementServiceClient directly.
     *
     * @throws Exception when it occurs deeper
     */
    public void testClient() throws Exception {
        // create a JiraManagementServiceClient using the default qualified name and a specific address of the service,
        // and obtain a proxy:
        URL url = new URL(WSDL_LOCATION);
        JiraManagementServiceClient client = new JiraManagementServiceClient(url);
        JiraManagementService service = client.getJiraManagementServicePort();

        // we can now access the service as we did in previous test
        service.login("a", "b");
    }
}