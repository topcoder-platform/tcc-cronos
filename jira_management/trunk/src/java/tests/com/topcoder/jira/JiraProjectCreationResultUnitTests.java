/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira;

import junit.framework.TestCase;
import com.topcoder.jira.managers.entities.JiraProject;
import com.topcoder.jira.managers.entities.JiraVersion;

/**
 * Unit tests for the <code>JiraProjectCreationResult</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraProjectCreationResultUnitTests extends TestCase {

    /**
     * Tests <code>getActionTaken()</code>.
     * <p>
     * Checks that getter returns expected value.
     */
    public void testGetActionTaken() {
        JiraProjectCreationAction action = JiraProjectCreationAction.PROJECT_AND_VERSION_CREATED;
        assertEquals("returned value is incorrect", action, new JiraProjectCreationResult(null,
            null, action).getActionTaken());
    }

    /**
     * Tests <code>getProject()</code>.
     * <p>
     * Checks that getter returns expected value.
     */
    public void testGetProject() {
        JiraProject project = new JiraProject();
        assertSame("returned value is incorrect", project, new JiraProjectCreationResult(project,
            null, null).getProject());
    }

    /**
     * Tests <code>getVersion()</code>.
     * <p>
     * Checks that getter returns expected value.
     */
    public void testGetVersion() {
        JiraVersion version = new JiraVersion();
        assertSame("returned value is incorrect", version, new JiraProjectCreationResult(null,
            version, null).getVersion());
    }

    /**
     * Tests
     * <code>JiraProjectCreationResult(JiraProject, JiraVersion, JiraProjectCreationAction)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * values as all parameters.
     */
    public void testJiraProjectCreationResult_1() {
        JiraProjectCreationResult result = new JiraProjectCreationResult(null, null, null);
        assertNull("project was propagated incorrectly", result.getProject());
        assertNull("version was propagated incorrectly", result.getVersion());
        assertNull("action was propagated incorrectly", result.getActionTaken());
    }

    /**
     * Tests
     * <code>JiraProjectCreationResult(JiraProject, JiraVersion, JiraProjectCreationAction)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * non-null values.
     */
    public void testJiraProjectCreationResult_2() {
        JiraProject project = new JiraProject();
        JiraVersion version = new JiraVersion();
        JiraProjectCreationAction action = JiraProjectCreationAction.PROJECT_AND_VERSION_CREATED;
        JiraProjectCreationResult result = new JiraProjectCreationResult(project, version, action);
        assertSame("project was propagated incorrectly", project, result.getProject());
        assertSame("version was propagated incorrectly", version, result.getVersion());
        assertEquals("actioni was propagated incorrectly", action, result.getActionTaken());
    }

}
