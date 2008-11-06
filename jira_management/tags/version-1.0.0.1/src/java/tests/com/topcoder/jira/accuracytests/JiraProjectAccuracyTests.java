/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.accuracytests;

import com.topcoder.jira.managers.entities.JiraProject;

import junit.framework.TestCase;

/**
 * Accuracy tests for the <code>JiraProject</code> class.
 *
 * @author morehappiness
 * @version 1.0
 */
public class JiraProjectAccuracyTests extends TestCase {

    /**
     * <code>JiraProject</code> instance used for testing.
     */
    private JiraProject project;

    /**
     * Creates required objects and sets up environment.
     *
     * @throws Exception if error occurs.
     */
    protected void setUp() throws Exception {
        project = new JiraProject();
    }

    /**
     * Tests <code>getDescription()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetDescription() {
        project.setDescription("abc");
        assertEquals("returned value is incorrect", "abc", project.getDescription());
    }

    /**
     * Tests <code>getId()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetId() {
        project.setId("abc");
        assertEquals("returned value is incorrect", "abc", project.getId());
    }

    /**
     * Tests <code>getKey()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetKey() {
        project.setKey("abc");
        assertEquals("returned value is incorrect", "abc", project.getKey());
    }


    /**
     * Tests <code>getLead()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetLead() {
        project.setLead("abc");
        assertEquals("returned value is incorrect", "abc", project.getLead());
    }

    /**
     * Tests <code>getName()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetName() {
        project.setName("abc");
        assertEquals("returned value is incorrect", "abc", project.getName());
    }

    /**
     * Tests <code>getProjectUrl()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetProjectUrl() {
        project.setProjectUrl("abc");
        assertEquals("returned value is incorrect", "abc", project.getProjectUrl());
    }

    /**
     * Tests <code>getUrl()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetUrl() {
        project.setUrl("abc");
        assertEquals("returned value is incorrect", "abc", project.getUrl());
    }

    /**
     * Tests <code>JiraProject()</code>.
     * <p>
     * Checks that constructor correctly creates new instance.
     */
    public void testCtorOne() {
        assertNotNull("unable to instantiate JiraProject", new JiraProject());
    }

    /**
     * Tests <code>JiraProject(String, String, String, String, String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * non-null values for all parameters.
     */
    public void testJiraProjectTwo() {
        JiraProject jiraProject = new JiraProject("1", "2", "3", "4", "5");
        assertEquals("description was propagated incorrectly", "1", jiraProject.getDescription());
        assertEquals("key was propagated incorrectly", "2", jiraProject.getKey());
        assertEquals("lead was propagated incorrectly", "3", jiraProject.getLead());
        assertEquals("projectUrl was propagated incorrectly", "4", jiraProject.getProjectUrl());
        assertEquals("url was propagated incorrectly", "5", jiraProject.getUrl());
    }

    /**
     * Tests <code>setDescription(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetDescriptionOne() {
        project.setDescription(null);
        assertNull("description was propagated incorrectly", project.getDescription());
    }

    /**
     * Tests <code>setDescription(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetDescriptionTwo() {
        project.setDescription(" ");
        assertEquals("description was propagated incorrectly", " ", project.getDescription());
    }

    /**
     * Tests <code>setDescription(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetDescription_3() {
        project.setDescription("tc");
        assertEquals("description was propagated incorrectly", "tc", project.getDescription());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetIdOne() {
        project.setId(null);
        assertNull("id was propagated incorrectly", project.getId());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetIdTwo() {
        project.setId(" ");
        assertEquals("id was propagated incorrectly", " ", project.getId());
    }

    /**
     * Tests <code>setKey(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetKeyOne() {
        project.setKey(null);
        assertNull("key was propagated incorrectly", project.getKey());
    }

    /**
     * Tests <code>setKey(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetKeyTwo() {
        project.setKey(" ");
        assertEquals("key was propagated incorrectly", " ", project.getKey());
    }

    /**
     * Tests <code>setLead(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetLeadOne() {
        project.setLead(null);
        assertNull("lead was propagated incorrectly", project.getLead());
    }

    /**
     * Tests <code>setLead(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetLeadTwo() {
        project.setLead(" ");
        assertEquals("lead was propagated incorrectly", " ", project.getLead());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetNameOne() {
        project.setName(null);
        assertNull("name was propagated incorrectly", project.getName());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetNameTwo() {
        project.setName(" ");
        assertEquals("name was propagated incorrectly", " ", project.getName());
    }

    /**
     * Tests <code>setProjectUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetProjectUrlOne() {
        project.setProjectUrl(null);
        assertNull("projectUrl was propagated incorrectly", project.getProjectUrl());
    }

    /**
     * Tests <code>setProjectUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetProjectUrlTwo() {
        project.setProjectUrl(" ");
        assertEquals("projectUrl was propagated incorrectly", " ", project.getProjectUrl());
    }

    /**
     * Tests <code>setUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetUrlOne() {
        project.setUrl(null);
        assertNull("url was propagated incorrectly", project.getUrl());
    }

    /**
     * Tests <code>setUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetUrlTwo() {
        project.setUrl(" ");
        assertEquals("url was propagated incorrectly", " ", project.getUrl());
    }
}
