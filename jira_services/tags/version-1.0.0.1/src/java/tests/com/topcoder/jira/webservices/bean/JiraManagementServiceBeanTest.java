/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.jira.webservices.bean;

import com.topcoder.jira.ComponentType;
import com.topcoder.jira.JiraConnectionException;
import com.topcoder.jira.JiraManager;
import com.topcoder.jira.JiraManagerException;
import com.topcoder.jira.JiraNotAuthorizedException;
import com.topcoder.jira.JiraProjectCreationResult;
import com.topcoder.jira.JiraProjectRetrievalResult;
import com.topcoder.jira.JiraProjectValidationException;
import com.topcoder.jira.JiraSecurityTokenExpiredException;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;
import com.topcoder.jira.webservices.JiraServiceConnectionException;
import com.topcoder.jira.webservices.JiraServiceException;
import com.topcoder.jira.webservices.JiraServiceNotAuthorizedException;
import com.topcoder.jira.webservices.JiraServiceProjectValidationException;
import com.topcoder.jira.webservices.JiraServiceSecurityTokenExpiredException;
import com.topcoder.jira.webservices.TestHelper;
import com.topcoder.jira.webservices.JiraServiceConfigurationException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.easymock.EasyMock;

import java.util.ArrayList;
import java.util.List;

/**
 * Some tests for JiraManagementServiceBean class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class JiraManagementServiceBeanTest extends TestCase {
    /**
     * Instance to test.
     */
    private JiraManagementServiceBean target;

    /**
     * Underlying manager to use in tests.
     */
    private JiraManager manager;

    /**
     * Jira project to use as parameter/return value.
     */
    private JiraProject project;

    /**
     * Jira version to use as parameter/return value.
     */
    private JiraVersion version;

    /**
     * Component type to use as parameter/return value.
     */
    private ComponentType type;

    /**
     * Jira project creation result to use as parameter/return value.
     */
    private JiraProjectCreationResult creationResult;

    /**
     * Jira project retrieval result to use as parameter/return value.
     */
    private JiraProjectRetrievalResult retrievalResult;

    /**
     * List of jira versions to use as parameter/return value.
     */
    private List<JiraVersion> versions;

    /**
     * <p>Returns the test suite of this class.</p>
     *
     * @return the test suite of this class.
     */
    public static Test suite() {
        return new TestSuite(JiraManagementServiceBeanTest.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to junit
     */
    protected void setUp() throws Exception {
        super.setUp();
        target = new JiraManagementServiceBean();
        manager = EasyMock.createStrictMock(JiraManager.class);
        TestHelper.inject(target, "jiraManager", manager);

        project = new JiraProject();
        version = new JiraVersion();
        type = ComponentType.APPLICATION;
        creationResult = new JiraProjectCreationResult();
        retrievalResult = new JiraProjectRetrievalResult();
        versions = new ArrayList<JiraVersion>();
        versions.add(new JiraVersion());
        versions.add(new JiraVersion());
        versions.add(new JiraVersion());
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
     * Tests JiraManagementServiceBean method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testConstructor() throws Exception {
        assertNotNull("created instance", new JiraManagementServiceBean());
    }

    /**
     * Tests login method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogin() throws Exception {
        EasyMock.expect(target.login("kit", "abc")).andReturn("ok");
        EasyMock.replay(manager);
        assertEquals("return value", "ok", target.login("kit", "abc"));
        EasyMock.verify(manager);
    }

    /**
     * Tests login method for null username parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLoginForNullUsername() throws Exception {
        try {
            target.login(null, "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests login method for null password parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLoginForNullPassword() throws Exception {
        try {
            target.login("kit", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests login method for empty string as username parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLoginForEmptyUsername() throws Exception {
        try {
            target.login(" \n  \t", "abc");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests login method for empty string as password parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLoginForEmptyPassword() throws Exception {
        try {
            target.login("kit", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests login method for error from underlying service. JiraServiceException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLoginForError() throws Exception {
        EasyMock.expect(target.login("kit", "abc")).andThrow(new JiraManagerException("error description"));
        EasyMock.replay(manager);

        try {
            target.login("kit", "abc");
            fail("JiraServiceException expected.");
        } catch (JiraServiceException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests logout method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogout() throws Exception {
        target.logout("my token");
        EasyMock.expectLastCall();
        EasyMock.replay(manager);
        target.logout("my token");
        EasyMock.verify(manager);
    }

    /**
     * Tests logout method for null parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogoutForNull() throws Exception {
        try {
            target.logout(null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests logout method for empty string as parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogoutForEmpty() throws Exception {
        try {
            target.logout(" \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests logout method for error from underlying manager. JiraServiceConnectionException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testLogoutForError() throws Exception {
        target.logout("x");
        EasyMock.expectLastCall().andThrow(new JiraConnectionException("error description"));
        EasyMock.replay(manager);

        try {
            target.logout("x");
            fail("JiraServiceConnectionException expected.");
        } catch (JiraServiceConnectionException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests createProject method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProject() throws Exception {
        EasyMock.expect(target.createProject("token", project, version, type)).andReturn(creationResult);
        EasyMock.replay(manager);
        assertSame("return value", creationResult, target.createProject("token", project, version, type));
        EasyMock.verify(manager);
    }

    /**
     * Tests logout method for error from underlying manager. JiraServiceNotAuthorizedException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProjectForError() throws Exception {
        target.createProject("x", project, version, type);
        EasyMock.expectLastCall().andThrow(new JiraNotAuthorizedException("error description"));
        EasyMock.replay(manager);

        try {
            target.createProject("x", project, version, type);
            fail("JiraServiceNotAuthorizedException expected.");
        } catch (JiraServiceNotAuthorizedException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests createProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProjectForNullToken() throws Exception {
        try {
            target.createProject(null, project, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createProject method for null project parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProjectForNullProject() throws Exception {
        try {
            target.createProject("t", null, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createProject method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProjectForNullVersion() throws Exception {
        try {
            target.createProject("t", project, null, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createProject method for null type parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProjectForNullType() throws Exception {
        try {
            target.createProject("t", project, version, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests createProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testCreateProjectForEmptyToken() throws Exception {
        try {
            target.createProject(" \n  \t", project, version, type);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addVersionToProject method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddVersionToProject() throws Exception {
        EasyMock.expect(target.addVersionToProject("token", project, version)).andReturn(creationResult);
        EasyMock.replay(manager);
        assertSame("return value", creationResult, target.addVersionToProject("token", project, version));
        EasyMock.verify(manager);
    }

    /**
     * Tests addVersionToProject method for error from underlying manager. JiraServiceProjectValidationException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddVersionToProjectForError() throws Exception {
        target.addVersionToProject("x", project, version);
        EasyMock.expectLastCall().andThrow(new JiraProjectValidationException("error description"));
        EasyMock.replay(manager);

        try {
            target.addVersionToProject("x", project, version);
            fail("JiraServiceProjectValidationException expected.");
        } catch (JiraServiceProjectValidationException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests addVersionToProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddVersionToProjectForNullToken() throws Exception {
        try {
            target.addVersionToProject(null, project, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addVersionToProject method for null project parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddVersionToProjectForNullProject() throws Exception {
        try {
            target.addVersionToProject("t", null, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addVersionToProject method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddVersionToProjectForNullVersion() throws Exception {
        try {
            target.addVersionToProject("t", project, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests addVersionToProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testAddVersionToProjectForEmptyToken() throws Exception {
        try {
            target.addVersionToProject(" \n  \t", project, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject(String, String) method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProject() throws Exception {
        EasyMock.expect(target.getProject("token", "key")).andReturn(retrievalResult);
        EasyMock.replay(manager);
        assertSame("return value", retrievalResult, target.getProject("token", "key"));
        EasyMock.verify(manager);
    }

    /**
     * Tests getProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectForError() throws Exception {
        target.getProject("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.getProject("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests getProject(String, String, String) method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersion() throws Exception {
        EasyMock.expect(target.getProject("token", "key", "version")).andReturn(retrievalResult);
        EasyMock.replay(manager);
        assertSame("return value", retrievalResult, target.getProject("token", "key", "version"));
        EasyMock.verify(manager);
    }

    /**
     * Tests getProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForError() throws Exception {
        target.getProject("x", "y", "z");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.getProject("x", "y", "z");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests getProjectVersions method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectVersions() throws Exception {
        EasyMock.expect(target.getProjectVersions("token", "key")).andReturn(versions);
        EasyMock.replay(manager);
        assertSame("return value", versions, target.getProjectVersions("token", "key"));
        EasyMock.verify(manager);
    }

    /**
     * Tests getProjectVersions method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectVersionsForError() throws Exception {
        target.getProjectVersions("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.getProjectVersions("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests getProjectVersions method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectVersionsForNullToken() throws Exception {
        try {
            target.getProjectVersions(null, "k");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProjectVersions method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectVersionsForNullKey() throws Exception {
        try {
            target.getProjectVersions("t", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProjectVersions method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectVersionsForEmptyToken() throws Exception {
        try {
            target.getProjectVersions(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProjectVersions method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectVersionsForEmptyKey() throws Exception {
        try {
            target.getProjectVersions("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests projectExists method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testProjectExists() throws Exception {
        target.projectExists("my token", "name");
        EasyMock.expectLastCall().andReturn(true);
        EasyMock.replay(manager);
        assertTrue("return value", target.projectExists("my token", "name"));
        EasyMock.verify(manager);
    }

    /**
     * Tests projectExists method for error from underlying manager. JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testProjectExistsForError() throws Exception {
        target.projectExists("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.projectExists("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests projectExists method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testProjectExistsForNullToken() throws Exception {
        try {
            target.projectExists(null, "n");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests projectExists method for null name parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testProjectExistsForNullName() throws Exception {
        try {
            target.projectExists("a", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests projectExists method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testProjectExistsForEmptyToken() throws Exception {
        try {
            target.projectExists(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests projectExists method for empty string as name parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testProjectExistsForEmptyName() throws Exception {
        try {
            target.projectExists("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject(String, Long) method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectById() throws Exception {
        EasyMock.expect(target.getProject("token", 3l)).andReturn(retrievalResult);
        EasyMock.replay(manager);
        assertSame("return value", retrievalResult, target.getProject("token", 3l));
        EasyMock.verify(manager);
    }

    /**
     * Tests getProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByIdForError() throws Exception {
        target.getProject("x", 3l);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.getProject("x", 3l);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectForNullToken() throws Exception {
        try {
            target.getProject(null, "k");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectForNullKey() throws Exception {
        try {
            target.getProject("t", (String) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectForEmptyToken() throws Exception {
        try {
            target.getProject(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectForEmptyKey() throws Exception {
        try {
            target.getProject("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForNullToken() throws Exception {
        try {
            target.getProject(null, "k", "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForNullKey() throws Exception {
        try {
            target.getProject("t", null, "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForNullVersion() throws Exception {
        try {
            target.getProject("x", "k", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForEmptyToken() throws Exception {
        try {
            target.getProject(" \n  \t", "a", "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForEmptyKey() throws Exception {
        try {
            target.getProject("a", " \n  \t", "x");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for empty string as version parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByVersionForEmptyVersion() throws Exception {
        try {
            target.getProject("a", "x", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByIdForNullToken() throws Exception {
        try {
            target.getProject(null, 1l);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for null id parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByIdForNullId() throws Exception {
        try {
            target.getProject("t", (Long) null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests getProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testGetProjectByIdForEmptyToken() throws Exception {
        try {
            target.getProject(" \n  \t", 3l);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests deleteProject method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDeleteProject() throws Exception {
        target.deleteProject("my token", "key");
        EasyMock.expectLastCall();
        EasyMock.replay(manager);
        target.deleteProject("my token", "key");
        EasyMock.verify(manager);
    }

    /**
     * Tests deleteProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDeleteProjectForError() throws Exception {
        target.deleteProject("x", "y");
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.deleteProject("x", "y");
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests deleteProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDeleteProjectForNullToken() throws Exception {
        try {
            target.deleteProject(null, "k");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests deleteProject method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDeleteProjectForNullKey() throws Exception {
        try {
            target.deleteProject("t", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests deleteProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDeleteProjectForEmptyToken() throws Exception {
        try {
            target.deleteProject(" \n  \t", "a");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests deleteProject method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testDeleteProjectForEmptyKey() throws Exception {
        try {
            target.deleteProject("a", " \n  \t");
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateProject method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateProject() throws Exception {
        EasyMock.expect(target.updateProject("token", project)).andReturn(project);
        EasyMock.replay(manager);
        assertSame("return value", project, target.updateProject("token", project));
        EasyMock.verify(manager);
    }

    /**
     * Tests updateProject method for error from underlying manager. JiraServiceSecurityTokenExpiredException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateProjectForError() throws Exception {
        target.updateProject("x", project);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.updateProject("x", project);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests updateProject method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateProjectForNullToken() throws Exception {
        try {
            target.updateProject(null, project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateProject method for null project parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateProjectForNullProject() throws Exception {
        try {
            target.updateProject("a", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests updateProject method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testUpdateProjectForEmptyToken() throws Exception {
        try {
            target.updateProject(" \n  \t", project);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests releaseVersion method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersion() throws Exception {
        target.releaseVersion("my token", "key", version);
        EasyMock.expectLastCall();
        EasyMock.replay(manager);
        target.releaseVersion("my token", "key", version);
        EasyMock.verify(manager);
    }

    /**
     * Tests releaseVersion method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersionForError() throws Exception {
        target.releaseVersion("x", "y", version);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.releaseVersion("x", "y", version);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests releaseVersion method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersionForNullToken() throws Exception {
        try {
            target.releaseVersion(null, "k", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests releaseVersion method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersionForNullKey() throws Exception {
        try {
            target.releaseVersion("t", null, version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests releaseVersion method for null version parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersionForNullVersion() throws Exception {
        try {
            target.releaseVersion("t", "x", null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests releaseVersion method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersionForEmptyToken() throws Exception {
        try {
            target.releaseVersion(" \n  \t", "a", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests releaseVersion method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testReleaseVersionForEmptyKey() throws Exception {
        try {
            target.releaseVersion("a", " \n  \t", version);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests archiveVersion method for standard data.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersion() throws Exception {
        target.archiveVersion("my token", "key", "version", false);
        EasyMock.expectLastCall();
        EasyMock.replay(manager);
        target.archiveVersion("my token", "key", "version", false);
        EasyMock.verify(manager);
    }

    /**
     * Tests archiveVersion method for error from underlying manager. JiraServiceSecurityTokenExpiredException
     * expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForError() throws Exception {
        target.archiveVersion("x", "y", "z", true);
        EasyMock.expectLastCall().andThrow(new JiraSecurityTokenExpiredException("error description"));
        EasyMock.replay(manager);

        try {
            target.archiveVersion("x", "y", "z", true);
            fail("JiraServiceSecurityTokenExpiredException expected.");
        } catch (JiraServiceSecurityTokenExpiredException e) {
            assertEquals("error message", "error description", e.getMessage());
        }
        EasyMock.verify(manager);
    }

    /**
     * Tests archiveVersion method for null token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForNullToken() throws Exception {
        try {
            target.archiveVersion(null, "k", "v", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests archiveVersion method for null key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForNullKey() throws Exception {
        try {
            target.archiveVersion("t", null, "a", false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests archiveVersion method for null name parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForNullName() throws Exception {
        try {
            target.archiveVersion("t", "x", null, false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests archiveVersion method for empty string as token parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForEmptyToken() throws Exception {
        try {
            target.archiveVersion(" \n  \t", "a", "x", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests archiveVersion method for empty string as key parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForEmptyKey() throws Exception {
        try {
            target.archiveVersion("a", " \n  \t", "x", true);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests archiveVersion method for empty string as name parameter. IllegalArgumentException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testArchiveVersionForEmptyName() throws Exception {
        try {
            target.archiveVersion("a", "x", " \n  \t", false);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException ex) {
            // success
        }
    }

    /**
     * Tests initialize method.
     *
     * @throws Exception when it occurs deeper
     */
    public void testInitialize() throws Exception {
        target.initialize();
    }

    /**
     * Tests initialize method when bean can't be initialized. JiraServiceConfigurationException expected.
     *
     * @throws Exception when it occurs deeper
     */
    public void testInitializeForError() throws Exception {
        TestHelper.inject(target, "jiraManagerFile", "no_such_file");
        TestHelper.inject(target, "jiraManagerNamespace", "no_such_namespace");
        try {
            target.initialize();
            fail("JiraServiceConfigurationException expected.");
        } catch (JiraServiceConfigurationException e) {
            // success
        }
    }
}