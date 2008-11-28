/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.failuretests;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManager;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.webservices.JiraServiceConnectionException;
import com.topcoder.jira.webservices.JiraServiceException;
import com.topcoder.jira.webservices.JiraServiceNotAuthorizedException;
import com.topcoder.jira.webservices.JiraServiceProjectValidationException;
import com.topcoder.jira.webservices.JiraServiceSecurityTokenExpiredException;
import com.topcoder.jira.webservices.bean.JiraManagementServiceBean;

import junit.framework.TestCase;
import org.easymock.EasyMock;

import java.util.ArrayList;
import java.util.List;

/**
 * Failure tests for JiraManagementServiceBean class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiraManagementServiceBeanFailureTest extends TestCase {
    /**
     * <p>
     * Represents the <code>JiraManagementServiceBean</code> instance for test.
     * </p>
     */
    private JiraManagementServiceBean bean;

    /**
     * <p>
     * Represents the <code>JiraManager</code> instance for test.
     * </p>
     */
    private JiraManager manager;

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
        bean = new JiraManagementServiceBean();
        manager = EasyMock.createStrictMock(JiraManager.class);
        TestHelper.inject(bean, "jiraManager", manager);

        project = new JiraProject();
        version = new JiraVersion();
        type = ComponentType.APPLICATION;
        versions = new ArrayList<JiraVersion>();
        versions.add(new JiraVersion());
        versions.add(new JiraVersion());
        versions.add(new JiraVersion());
    }

    /**
     * Failure test login method for null username parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginWithNullUsername() throws Exception {
        try {
            bean.login(null, "abc");
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
            bean.login("kit", null);
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
            bean.login(" \n  \t", "abc");
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
            bean.login("kit", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test login method for error from underlying service. JiraServiceException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLoginFailure() throws Exception {
        EasyMock.expect(bean.login("kit", "abc")).andThrow(new JiraManagerException("error description"));
        EasyMock.replay(manager);

        try {
            bean.login("kit", "abc");
            fail("JiraServiceException expected.");
        } catch (JiraServiceException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test logout method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLogoutWithNull() throws Exception {
        try {
            bean.logout(null);
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
            bean.logout(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test logout method for error from underlying manager. JiraServiceConnectionException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testLogoutFailure() throws Exception {
        bean.logout("x");
        EasyMock.expectLastCall().andThrow(new JiraConnectionException("error description"));
        EasyMock.replay(manager);

        try {
            bean.logout("x");
            fail("JiraServiceConnectionException expected.");
        } catch (JiraServiceConnectionException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test logout method for error from underlying manager. JiraServiceNotAuthorizedException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectFailure() throws Exception {
        bean.createProject("x", project, version, type);
        EasyMock.expectLastCall().andThrow(new JiraNotAuthorizedException("error description"));
        EasyMock.replay(manager);

        try {
            bean.createProject("x", project, version, type);
            fail("JiraServiceNotAuthorizedException expected.");
        } catch (JiraServiceNotAuthorizedException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test createProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testCreateProjectWithNullToken() throws Exception {
        try {
            bean.createProject(null, project, version, type);
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
            bean.createProject("t", null, version, type);
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
            bean.createProject("t", project, null, type);
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
            bean.createProject("t", project, version, null);
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
            bean.createProject(" \n  \t", project, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test addVersionToProject method for error from underlying manager.
     * JiraServiceProjectValidationException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testAddVersionToProjectFailure() throws Exception {
        bean.addVersionToProject("x", project, version);
        EasyMock.expectLastCall().andThrow(new JiraProjectValidationException("error description"));
        EasyMock.replay(manager);

        try {
            bean.addVersionToProject("x", project, version);
            fail("JiraServiceProjectValidationException expected.");
        } catch (JiraServiceProjectValidationException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test addVersionToProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testAddVersionToProjectWithNullToken() throws Exception {
        try {
            bean.addVersionToProject(null, project, version);
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
            bean.addVersionToProject("t", null, version);
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
            bean.addVersionToProject("t", project, null);
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
            bean.addVersionToProject(" \n  \t", project, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectFailure() throws Exception {
        bean.getProject("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.getProject("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test getProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByVersionFailure() throws Exception {
        bean.getProject("x", "y", "z");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.getProject("x", "y", "z");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test getProjectVersions method for error from underlying manager.
     * JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsFailure() throws Exception {
        bean.getProjectVersions("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.getProjectVersions("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test getProjectVersions method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectVersionsWithNullToken() throws Exception {
        try {
            bean.getProjectVersions(null, "k");
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
            bean.getProjectVersions("t", null);
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
            bean.getProjectVersions(" \n  \t", "a");
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
            bean.getProjectVersions("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test projectExists method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsFailure() throws Exception {
        bean.projectExists("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.projectExists("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test projectExists method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testProjectExistsWithNullToken() throws Exception {
        try {
            bean.projectExists(null, "n");
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
            bean.projectExists("a", null);
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
            bean.projectExists(" \n  \t", "a");
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
            bean.projectExists("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test getProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectByIdFailure() throws Exception {
        bean.getProject("x", 3l);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.getProject("x", 3l);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testGetProjectWithNullToken() throws Exception {
        try {
            bean.getProject(null, "k");
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
            bean.getProject("t", (String) null);
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
            bean.getProject(" \n  \t", "a");
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
            bean.getProject("a", " \n  \t");
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
            bean.getProject(null, "k", "x");
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
            bean.getProject("t", null, "x");
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
            bean.getProject("x", "k", null);
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
            bean.getProject(" \n  \t", "a", "x");
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
            bean.getProject("a", " \n  \t", "x");
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
            bean.getProject("a", "x", " \n  \t");
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
            bean.getProject(null, 1l);
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
            bean.getProject("t", (Long) null);
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
            bean.getProject(" \n  \t", 3l);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test deleteProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectFailure() throws Exception {
        bean.deleteProject("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.deleteProject("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test deleteProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testDeleteProjectWithNullToken() throws Exception {
        try {
            bean.deleteProject(null, "k");
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
            bean.deleteProject("t", null);
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
            bean.deleteProject(" \n  \t", "a");
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
            bean.deleteProject("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test updateProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testUpdateProjectFailure() throws Exception {
        bean.updateProject("x", project);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.updateProject("x", project);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test updateProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testUpdateProjectWithNullToken() throws Exception {
        try {
            bean.updateProject(null, project);
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
            bean.updateProject("a", null);
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
            bean.updateProject(" \n  \t", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test releaseVersion method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionFailure() throws Exception {
        bean.releaseVersion("x", "y", version);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.releaseVersion("x", "y", version);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test releaseVersion method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testReleaseVersionWithNullToken() throws Exception {
        try {
            bean.releaseVersion(null, "k", version);
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
            bean.releaseVersion("t", null, version);
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
            bean.releaseVersion("t", "x", null);
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
            bean.releaseVersion(" \n  \t", "a", version);
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
            bean.releaseVersion("a", " \n  \t", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }

    /**
     * Failure test archiveVersion method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionFailure() throws Exception {
        bean.archiveVersion("x", "y", "z", true);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            bean.archiveVersion("x", "y", "z", true);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            // expected
        }
        EasyMock.verify(manager);
    }

    /**
     * Failure test archiveVersion method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception
     *             when it occurs deeper
     */
    public void testArchiveVersionWithNullToken() throws Exception {
        try {
            bean.archiveVersion(null, "k", "v", true);
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
            bean.archiveVersion("t", null, "a", false);
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
            bean.archiveVersion("t", "x", null, false);
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
            bean.archiveVersion(" \n  \t", "a", "x", true);
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
            bean.archiveVersion("a", " \n  \t", "x", true);
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
            bean.archiveVersion("a", "x", " \n  \t", false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // expected
        }
    }
}