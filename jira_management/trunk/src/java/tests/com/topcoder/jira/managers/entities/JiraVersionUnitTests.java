/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.managers.entities;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>JiraVersion</code> class.
 *
 * @author agh
 * @version 1.0
 */
public class JiraVersionUnitTests extends TestCase {

    /**
     * <code>JiraVersion</code> instance used for testing.
     */
    private JiraVersion version;

    /**
     * Creates required objects and sets up environment.
     *
     * @throws Exception if error occurs.
     */
    protected void setUp() throws Exception {
        version = new JiraVersion();
    }

    /**
     * Tests <code>getId()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetId_1() {
        assertNull("initial value is incorrect", version.getId());
    }

    /**
     * Tests <code>getId()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetId_2() {
        version.setId("abc");
        assertEquals("returned value is incorrect", "abc", version.getId());
    }

    /**
     * Tests <code>getName()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetName_1() {
        assertNull("initial value is incorrect", version.getName());
    }

    /**
     * Tests <code>getName()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetName_2() {
        version.setName("abc");
        assertEquals("returned value is incorrect", "abc", version.getName());
    }

    /**
     * Tests <code>getReleaseDate()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetReleaseDate_1() {
        assertNull("initial value is incorrect", version.getReleaseDate());
    }

    /**
     * Tests <code>getReleaseDate()</code>.
     * <p>
     * Checks that getter returns correct value.
     */
    public void testGetReleaseDate_2() {
        Date date = new Date();
        version.setReleaseDate(date);
        assertSame("returned value is incorrect", date, version.getReleaseDate());
    }

    /**
     * Tests <code>getSequence()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testGetSequence_1() {
        assertEquals("initial value is incorrect", 0, version.getSequence());
    }

    /**
     * Tests <code>getSequence()</code>.
     * <p>
     * Checks that getter returns correct value. Value 239 is expected.
     */
    public void testGetSequence_2() {
        version.setSequence(239);
        assertEquals("returned value is incorrect", 239, version.getSequence());
    }

    /**
     * Tests <code>isArchived()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testIsArchived_1() {
        assertFalse("initial value is incorrect", version.isArchived());
    }

    /**
     * Tests <code>isArchived()</code>.
     * <p>
     * Checks that getter returns correct value. Value 239 is expected.
     */
    public void testIsArchived_2() {
        version.setArchived(true);
        assertTrue("returned value is incorrect", version.isArchived());
    }

    /**
     * Tests <code>isReleased()</code>.
     * <p>
     * Checks that getter returns correct initial value.
     */
    public void testIsReleased_1() {
        assertFalse("initial value is incorrect", version.isReleased());
    }

    /**
     * Tests <code>isReleased()</code>.
     * <p>
     * Checks that getter returns correct value. Value 239 is expected.
     */
    public void testIsReleased_2() {
        version.setReleased(true);
        assertTrue("returned value is incorrect", version.isReleased());
    }

    /**
     * Tests <code>JiraVersion()</code>.
     * <p>
     * Checks that constructor correctly creates new instance.
     */
    public void testJiraVersion_A() {
        assertNotNull("unable to instantiate JiraVersion", new JiraVersion());
    }

    /**
     * Tests <code>JiraVersion(Date, long, boolean, boolean)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes null
     * value as the first argument.
     */
    public void testJiraVersion_B_1() {
        JiraVersion jiraVersion = new JiraVersion(null, 0, false, false);
        assertNull("releaseDate was propagated incorrectly", jiraVersion.getReleaseDate());
        assertEquals("sequence was propagated incorrectly", 0, jiraVersion.getSequence());
        assertFalse("archived was propagated incorrectly", jiraVersion.isArchived());
        assertFalse("released was propagated incorrectly", jiraVersion.isReleased());
    }

    /**
     * Tests <code>JiraVersion(Date, long, boolean, boolean)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * non-null value as the first argument.
     */
    public void testJiraVersion_B_2() {
        Date date = new Date();

        JiraVersion jiraVersion = new JiraVersion(date, 239, true, true);
        assertSame("releaseDate was propagated incorrectly", date, jiraVersion.getReleaseDate());
        assertEquals("sequence was propagated incorrectly", 239, jiraVersion.getSequence());
        assertTrue("archived was propagated incorrectly", jiraVersion.isArchived());
        assertTrue("released was propagated incorrectly", jiraVersion.isReleased());
    }

    /**
     * Tests <code>setArchived(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes false.
     */
    public void testSetArchived_1() {
        version.setArchived(false);
        assertFalse("archived was propagated incorrectly", version.isArchived());
    }

    /**
     * Tests <code>setArchived(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes true.
     */
    public void testSetArchived_2() {
        version.setArchived(true);
        assertTrue("archived was propagated incorrectly", version.isArchived());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetId_1() {
        version.setId(null);
        assertNull("id was propagated incorrectly", version.getId());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetId_2() {
        version.setId(" ");
        assertEquals("id was propagated incorrectly", " ", version.getId());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetId_3() {
        version.setId("tc");
        assertEquals("id was propagated incorrectly", "tc", version.getId());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetName_1() {
        version.setName(null);
        assertNull("name was propagated incorrectly", version.getName());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetName_2() {
        version.setName(" ");
        assertEquals("name was propagated incorrectly", " ", version.getName());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes string "tc".
     */
    public void testSetName_3() {
        version.setName("tc");
        assertEquals("name was propagated incorrectly", "tc", version.getName());
    }

    /**
     * Tests <code>setReleaseDate(Date)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes non-null date.
     */
    public void testSetReleaseDate_1() {
        Date date = new Date();
        version.setReleaseDate(date);
        assertSame("releaseDate was propagated incorrectly", date, version.getReleaseDate());
    }

    /**
     * Tests <code>setReleaseDate(Date)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetReleaseDate_2() {
        version.setReleaseDate(null);
        assertNull("releaseDate was propagated incorrectly", version.getReleaseDate());
    }

    /**
     * Tests <code>setReleased(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes false.
     */
    public void testSetReleased_1() {
        version.setReleased(false);
        assertFalse("released was propagated incorrectly", version.isReleased());
    }

    /**
     * Tests <code>setReleased(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes true.
     */
    public void testSetReleased_2() {
        version.setReleased(true);
        assertTrue("released was propagated incorrectly", version.isReleased());
    }

    /**
     * Tests <code>setSequence(long)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes -1.
     */
    public void testSetSequence_1() {
        version.setSequence(-1);
        assertEquals("sequence was propagated incorrectly", -1, version.getSequence());
    }

    /**
     * Tests <code>setSequence(long)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes 239.
     */
    public void testSetSequence_2() {
        version.setSequence(239);
        assertEquals("sequence was propagated incorrectly", 239, version.getSequence());
    }

}
