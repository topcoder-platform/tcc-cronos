/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers.entities;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>JiraProject</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraProjectUnitTests extends TestCase {

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
     * Checks that getter returns correct initial value.
     */
    public void testGetDescription_1() {
        assertNull("initial value is incorrect", project.getDescription());
    }

    /**
     * Tests <code>getDescription()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetDescription_2() {
        project.setDescription("abc");
        assertEquals("returned value is incorrect", "abc", project.getDescription());
    }

    /**
     * Tests <code>getId()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetId_1() {
        assertNull("initial value is incorrect", project.getId());
    }

    /**
     * Tests <code>getId()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetId_2() {
        project.setId("abc");
        assertEquals("returned value is incorrect", "abc", project.getId());
    }

    /**
     * Tests <code>getKey()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetKey_1() {
        assertNull("initial value is incorrect", project.getKey());
    }

    /**
     * Tests <code>getKey()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetKey_2() {
        project.setKey("abc");
        assertEquals("returned value is incorrect", "abc", project.getKey());
    }

    /**
     * Tests <code>getLead()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetLead_1() {
        assertNull("initial value is incorrect", project.getLead());
    }

    /**
     * Tests <code>getLead()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetLead_2() {
        project.setLead("abc");
        assertEquals("returned value is incorrect", "abc", project.getLead());
    }

    /**
     * Tests <code>getName()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetName_1() {
        assertNull("initial value is incorrect", project.getName());
    }

    /**
     * Tests <code>getName()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetName_2() {
        project.setName("abc");
        assertEquals("returned value is incorrect", "abc", project.getName());
    }

    /**
     * Tests <code>getProjectUrl()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetProjectUrl_1() {
        assertNull("initial value is incorrect", project.getProjectUrl());
    }

    /**
     * Tests <code>getProjectUrl()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetProjectUrl_2() {
        project.setProjectUrl("abc");
        assertEquals("returned value is incorrect", "abc", project.getProjectUrl());
    }

    /**
     * Tests <code>getUrl()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetUrl_1() {
        assertNull("initial value is incorrect", project.getUrl());
    }

    /**
     * Tests <code>getUrl()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetUrl_2() {
        project.setUrl("abc");
        assertEquals("returned value is incorrect", "abc", project.getUrl());
    }

    /**
     * Tests <code>JiraProject()</code>.
     * <p>
     * Checks that constructor correctly creates new instance.
     */
    public void testJiraProject_A() {
        assertNotNull("unable to instantiate JiraProject", new JiraProject());
    }

    /**
     * Tests <code>JiraProject(String, String, String, String, String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * values for all parameters.
     */
    public void testJiraProject_B_1() {
        JiraProject jiraProject = new JiraProject(null, null, null, null, null);
        assertNull("description was propagated incorrectly", jiraProject.getDescription());
        assertNull("key was propagated incorrectly", jiraProject.getKey());
        assertNull("lead was propagated incorrectly", jiraProject.getLead());
        assertNull("projectUrl was propagated incorrectly", jiraProject.getProjectUrl());
        assertNull("url was propagated incorrectly", jiraProject.getUrl());
    }

    /**
     * Tests <code>JiraProject(String, String, String, String, String)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * non-null values for all parameters.
     */
    public void testJiraProject_B_2() {
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
    public void testSetDescription_1() {
        project.setDescription(null);
        assertNull("description was propagated incorrectly", project.getDescription());
    }

    /**
     * Tests <code>setDescription(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetDescription_2() {
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
    public void testSetId_1() {
        project.setId(null);
        assertNull("id was propagated incorrectly", project.getId());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetId_2() {
        project.setId(" ");
        assertEquals("id was propagated incorrectly", " ", project.getId());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetId_3() {
        project.setId("tc");
        assertEquals("id was propagated incorrectly", "tc", project.getId());
    }

    /**
     * Tests <code>setKey(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetKey_1() {
        project.setKey(null);
        assertNull("key was propagated incorrectly", project.getKey());
    }

    /**
     * Tests <code>setKey(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetKey_2() {
        project.setKey(" ");
        assertEquals("key was propagated incorrectly", " ", project.getKey());
    }

    /**
     * Tests <code>setKey(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetKey_3() {
        project.setKey("tc");
        assertEquals("key was propagated incorrectly", "tc", project.getKey());
    }

    /**
     * Tests <code>setLead(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetLead_1() {
        project.setLead(null);
        assertNull("lead was propagated incorrectly", project.getLead());
    }

    /**
     * Tests <code>setLead(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetLead_2() {
        project.setLead(" ");
        assertEquals("lead was propagated incorrectly", " ", project.getLead());
    }

    /**
     * Tests <code>setLead(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetLead_3() {
        project.setLead("tc");
        assertEquals("lead was propagated incorrectly", "tc", project.getLead());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetName_1() {
        project.setName(null);
        assertNull("name was propagated incorrectly", project.getName());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetName_2() {
        project.setName(" ");
        assertEquals("name was propagated incorrectly", " ", project.getName());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetName_3() {
        project.setName("tc");
        assertEquals("name was propagated incorrectly", "tc", project.getName());
    }

    /**
     * Tests <code>setProjectUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetProjectUrl_1() {
        project.setProjectUrl(null);
        assertNull("projectUrl was propagated incorrectly", project.getProjectUrl());
    }

    /**
     * Tests <code>setProjectUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetProjectUrl_2() {
        project.setProjectUrl(" ");
        assertEquals("projectUrl was propagated incorrectly", " ", project.getProjectUrl());
    }

    /**
     * Tests <code>setProjectUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetProjectUrl_3() {
        project.setProjectUrl("tc");
        assertEquals("projectUrl was propagated incorrectly", "tc", project.getProjectUrl());
    }

    /**
     * Tests <code>setUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetUrl_1() {
        project.setUrl(null);
        assertNull("url was propagated incorrectly", project.getUrl());
    }

    /**
     * Tests <code>setUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetUrl_2() {
        project.setUrl(" ");
        assertEquals("url was propagated incorrectly", " ", project.getUrl());
    }

    /**
     * Tests <code>setUrl(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetUrl_3() {
        project.setUrl("tc");
        assertEquals("url was propagated incorrectly", "tc", project.getUrl());
    }
}
