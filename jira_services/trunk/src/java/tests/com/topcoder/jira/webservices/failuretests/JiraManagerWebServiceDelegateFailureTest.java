/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.failuretests;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.webservices.JiraManagementService;
import com.topcoder.jira.webservices.JiraServiceConnectionException;
import com.topcoder.jira.webservices.JiraServiceException;
import com.topcoder.jira.webservices.JiraServiceNotAuthorizedException;
import com.topcoder.jira.webservices.JiraServiceSecurityTokenExpiredException;
import com.topcoder.jira.webservices.delegate.JiraManagerWebServiceDelegate;

import junit.framework.TestCase;
import org.easymock.EasyMock;

import java.util.ArrayList;
import java.util.List;

/**
 * Failure test for JiraManagerWebServiceDelegate class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiraManagerWebServiceDelegateFailureTest extends TestCase {
    /**
     * <p>
     * Represents the <code>JiraManagerWebServiceDelegate</code> instance for test.
     * </p>
     */
    private JiraManagerWebServiceDelegate delegate;

    /**
     * <p>
     * Represents the <code>JiraManagementService</code> instance for test.
     * </p>
     */
    private JiraManagementService service;

    /**
     * <p>
     * Represents the <code>JiraProject</code> instance for test.
     * </p>
     */
    private JiraProject project;

    /**
     * <p>
     * Represents the <code>JiraVersion</code> instance for test.
     * </p>
     */
    private JiraVersion version;

    /**
     * <p>
     * Represents the <code>ComponentType</code> instance for test.
     * </p>
     */
    private ComponentType type;

    /**
     * <p>
     * Represents the <code>JiraVersion</code> list instance for test.
     * </p>
     */
    private List<JiraVersion> versions;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    protected void setUp() throws Exception {
        delegate = new JiraManagerWebServiceDelegate();

        service = EasyMock.createStrictMock(JiraManagementService.class);
        MockJiraManagementServiceClient.setService(service);

        project = new JiraProject();
        version = new JiraVersion();
        type = ComponentType.APPLICATION;
        versions = new ArrayList<JiraVersion>();
        versions.add(new JiraVersion());
        versions.add(new JiraVersion());
        versions.add(new JiraVersion());
    }

    /**
     * Failure test forth constructor null service client. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCtor4WithNullClient() throws Exception {
        try {
            assertNotNull("created instance", new JiraManagerWebServiceDelegate(null, "abc"));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Failure test login method for null username parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginWithNullUsername() throws Exception {
        try {
            delegate.login(null, "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test login method for null password parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginWithNullPassword() throws Exception {
        try {
            delegate.login("kit", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test login method for empty string as username parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginWithEmptyUsername() throws Exception {
        try {
            delegate.login(" \n  \t", "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test login method for empty string as password parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginWithEmptyPassword() throws Exception {
        try {
            delegate.login("kit", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test login method for error from underlying service. JiraManagerException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginFailure() throws Exception {
        EasyMock.expect(delegate.login("kit", "abc")).andThrow(new JiraServiceException("error description"));
        EasyMock.replay(service);

        try {
            delegate.login("kit", "abc");
            fail("JiraManagerException expected.");
        } catch (JiraManagerException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test logout method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLogoutWithNull() throws Exception {
        try {
            delegate.logout(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test logout method for empty string as parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLogoutWithEmpty() throws Exception {
        try {
            delegate.logout(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test logout method for error from underlying service. JiraConnectionException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLogoutFailure() throws Exception {
        delegate.logout("x");
        EasyMock.expectLastCall().andThrow(new JiraServiceConnectionException("error description"));
        EasyMock.replay(service);

        try {
            delegate.logout("x");
            fail("JiraConnectionException expected.");
        } catch (JiraConnectionException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test logout method for error from underlying service. JiraNotAuthorizedException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectFailure() throws Exception {
        delegate.createProject("x", project, version, type);
        EasyMock.expectLastCall().andThrow(new JiraServiceNotAuthorizedException("error description"));
        EasyMock.replay(service);

        try {
            delegate.createProject("x", project, version, type);
            fail("JiraNotAuthorizedException expected.");
        } catch (JiraNotAuthorizedException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test createProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectWithNullToken() throws Exception {
        try {
            delegate.createProject(null, project, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test createProject method for null project parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectWithNullProject() throws Exception {
        try {
            delegate.createProject("t", null, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test createProject method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectWithNullVersion() throws Exception {
        try {
            delegate.createProject("t", project, null, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test createProject method for null type parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectWithNullType() throws Exception {
        try {
            delegate.createProject("t", project, version, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test createProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectWithEmptyToken() throws Exception {
        try {
            delegate.createProject(" \n  \t", project, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test addVersionToProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testAddVersionToProjectWithNullToken() throws Exception {
        try {
            delegate.addVersionToProject(null, project, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test addVersionToProject method for null project parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testAddVersionToProjectWithNullProject() throws Exception {
        try {
            delegate.addVersionToProject("t", null, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test addVersionToProject method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testAddVersionToProjectWithNullVersion() throws Exception {
        try {
            delegate.addVersionToProject("t", project, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test addVersionToProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testAddVersionToProjectWithEmptyToken() throws Exception {
        try {
            delegate.addVersionToProject(" \n  \t", project, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for error from underlying service. JiraSecurityTokenExpiredException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectFailure() throws Exception {
        delegate.getProject("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.getProject("x", "y");
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test getProject method for error from underlying service. JiraSecurityTokenExpiredException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionFailure() throws Exception {
        delegate.getProject("x", "y", "z");
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.getProject("x", "y", "z");
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test getProjectVersions method for error from underlying service. JiraSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsFailure() throws Exception {
        delegate.getProjectVersions("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.getProjectVersions("x", "y");
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test getProjectVersions method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsWithNullToken() throws Exception {
        try {
            delegate.getProjectVersions(null, "k");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProjectVersions method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsWithNullKey() throws Exception {
        try {
            delegate.getProjectVersions("t", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProjectVersions method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsWithEmptyToken() throws Exception {
        try {
            delegate.getProjectVersions(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProjectVersions method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsWithEmptyKey() throws Exception {
        try {
            delegate.getProjectVersions("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test projectExists method for error from underlying service. JiraSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsFailure() throws Exception {
        delegate.projectExists("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.projectExists("x", "y");
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test projectExists method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsWithNullToken() throws Exception {
        try {
            delegate.projectExists(null, "n");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test projectExists method for null name parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsWithNullName() throws Exception {
        try {
            delegate.projectExists("a", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test projectExists method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsWithEmptyToken() throws Exception {
        try {
            delegate.projectExists(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test projectExists method for empty string as name parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsWithEmptyName() throws Exception {
        try {
            delegate.projectExists("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for error from underlying service. JiraSecurityTokenExpiredException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByIdFailure() throws Exception {
        delegate.getProject("x", 3l);
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.getProject("x", 3l);
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectWithNullToken() throws Exception {
        try {
            delegate.getProject(null, "k");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectWithNullKey() throws Exception {
        try {
            delegate.getProject("t", (String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectWithEmptyToken() throws Exception {
        try {
            delegate.getProject(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectWithEmptyKey() throws Exception {
        try {
            delegate.getProject("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionWithNullToken() throws Exception {
        try {
            delegate.getProject(null, "k", "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionWithNullKey() throws Exception {
        try {
            delegate.getProject("t", null, "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionWithNullVersion() throws Exception {
        try {
            delegate.getProject("x", "k", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionWithEmptyToken() throws Exception {
        try {
            delegate.getProject(" \n  \t", "a", "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionWithEmptyKey() throws Exception {
        try {
            delegate.getProject("a", " \n  \t", "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for empty string as version parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionWithEmptyVersion() throws Exception {
        try {
            delegate.getProject("a", "x", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByIdWithNullToken() throws Exception {
        try {
            delegate.getProject(null, 1l);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for null id parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByIdWithNullId() throws Exception {
        try {
            delegate.getProject("t", (Long) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByIdWithEmptyToken() throws Exception {
        try {
            delegate.getProject(" \n  \t", 3l);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test deleteProject method for error from underlying service. JiraSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectFailure() throws Exception {
        delegate.deleteProject("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.deleteProject("x", "y");
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test deleteProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectWithNullToken() throws Exception {
        try {
            delegate.deleteProject(null, "k");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test deleteProject method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectWithNullKey() throws Exception {
        try {
            delegate.deleteProject("t", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test deleteProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectWithEmptyToken() throws Exception {
        try {
            delegate.deleteProject(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test deleteProject method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectWithEmptyKey() throws Exception {
        try {
            delegate.deleteProject("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test updateProject method for error from underlying service. JiraSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testUpdateProjectFailure() throws Exception {
        delegate.updateProject("x", project);
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.updateProject("x", project);
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test updateProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testUpdateProjectWithNullToken() throws Exception {
        try {
            delegate.updateProject(null, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test updateProject method for null project parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testUpdateProjectWithNullProject() throws Exception {
        try {
            delegate.updateProject("a", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test updateProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testUpdateProjectWithEmptyToken() throws Exception {
        try {
            delegate.updateProject(" \n  \t", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test releaseVersion method for error from underlying service. JiraSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionFailure() throws Exception {
        delegate.releaseVersion("x", "y", version);
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.releaseVersion("x", "y", version);
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test releaseVersion method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionWithNullToken() throws Exception {
        try {
            delegate.releaseVersion(null, "k", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test releaseVersion method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionWithNullKey() throws Exception {
        try {
            delegate.releaseVersion("t", null, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test releaseVersion method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionWithNullVersion() throws Exception {
        try {
            delegate.releaseVersion("t", "x", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test releaseVersion method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionWithEmptyToken() throws Exception {
        try {
            delegate.releaseVersion(" \n  \t", "a", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test releaseVersion method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionWithEmptyKey() throws Exception {
        try {
            delegate.releaseVersion("a", " \n  \t", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for error from underlying service. JiraSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionFailure() throws Exception {
        delegate.archiveVersion("x", "y", "z", true);
        EasyMock.expectLastCall().andThrow(new JiraServiceSecurityTokenExpiredException("error description"));
        EasyMock.replay(service);

        try {
            delegate.archiveVersion("x", "y", "z", true);
            fail("JiraSecurityTokenExpiredException expected.");
        } catch (JiraSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(service);
    }

    /**
     * Failure test archiveVersion method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithNullToken() throws Exception {
        try {
            delegate.archiveVersion(null, "k", "v", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithNullKey() throws Exception {
        try {
            delegate.archiveVersion("t", null, "a", false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for null name parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithNullName() throws Exception {
        try {
            delegate.archiveVersion("t", "x", null, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithEmptyToken() throws Exception {
        try {
            delegate.archiveVersion(" \n  \t", "a", "x", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithEmptyKey() throws Exception {
        try {
            delegate.archiveVersion("a", " \n  \t", "x", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for empty string as name parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithEmptyName() throws Exception {
        try {
            delegate.archiveVersion("a", "x", " \n  \t", false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}