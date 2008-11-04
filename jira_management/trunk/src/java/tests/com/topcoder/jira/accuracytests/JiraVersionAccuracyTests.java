/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.jira.accuracytests;

import java.util.Date;

import com.topcoder.jira.managers.entities.JiraVersion;

import junit.framework.TestCase;

/**
 * Accuracy tests for the <code>JiraVersion</code> class.
 *
 * @author morehappiness
 * @version 1.0
 */
public class JiraVersionAccuracyTests extends TestCase {

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
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetIdTwo() {
        version.setId("abc");
        assertEquals("returned value is incorrect", "abc", version.getId());
    }

    /**
     * Tests <code>getName()</code>.
     * <p>
     * Checks that getter returns correct value. String "abc" is expected.
     */
    public void testGetNameTwo() {
        version.setName("abc");
        assertEquals("returned value is incorrect", "abc", version.getName());
    }

    /**
     * Tests <code>getReleaseDate()</code>.
     * <p>
     * Checks that getter returns correct value.
     */
    public void testGetReleaseDateTwo() {
        Date date = new Date();
        version.setReleaseDate(date);
        assertSame("returned value is incorrect", date, version.getReleaseDate());
    }

    /**
     * Tests <code>getSequence()</code>.
     * <p>
     * Checks that getter returns correct value. Value 239 is expected.
     */
    public void testGetSequenceTwo() {
        version.setSequence(239);
        assertEquals("returned value is incorrect", 239, version.getSequence());
    }

    /**
     * Tests <code>isArchived()</code>.
     * <p>
     * Checks that getter returns correct value. Value 239 is expected.
     */
    public void testIsArchivedTwo() {
        version.setArchived(true);
        assertTrue("returned value is incorrect", version.isArchived());
    }

    /**
     * Tests <code>isReleased()</code>.
     * <p>
     * Checks that getter returns correct value. Value 239 is expected.
     */
    public void testIsReleasedTwo() {
        version.setReleased(true);
        assertTrue("returned value is incorrect", version.isReleased());
    }

    /**
     * Tests <code>JiraVersion()</code>.
     * <p>
     * Checks that constructor correctly creates new instance.
     */
    public void testCtorOne() {
        assertNotNull("unable to instantiate JiraVersion", new JiraVersion());
    }

    /**
     * Tests <code>JiraVersion(Date, long, boolean, boolean)</code>.
     * <p>
     * Checks that constructor correctly creates new instance and propagates parameters. Passes
     * non-null value as the first argument.
     */
    public void testJiraVersionCtorTwo() {
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
    public void testSetArchivedOne() {
        version.setArchived(false);
        assertFalse("archived was propagated incorrectly", version.isArchived());
    }

    /**
     * Tests <code>setArchived(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes true.
     */
    public void testSetArchivedTwo() {
        version.setArchived(true);
        assertTrue("archived was propagated incorrectly", version.isArchived());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetIdOne() {
        version.setId(null);
        assertNull("id was propagated incorrectly", version.getId());
    }

    /**
     * Tests <code>setId(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetIdTwo() {
        version.setId(" ");
        assertEquals("id was propagated incorrectly", " ", version.getId());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetNameOne() {
        version.setName(null);
        assertNull("name was propagated incorrectly", version.getName());
    }

    /**
     * Tests <code>setName(String)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes empty string.
     */
    public void testSetNameTwo() {
        version.setName(" ");
        assertEquals("name was propagated incorrectly", " ", version.getName());
    }

    /**
     * Tests <code>setReleaseDate(Date)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes non-null date.
     */
    public void testSetReleaseDateOne() {
        Date date = new Date();
        version.setReleaseDate(date);
        assertSame("releaseDate was propagated incorrectly", date, version.getReleaseDate());
    }

    /**
     * Tests <code>setReleaseDate(Date)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes null.
     */
    public void testSetReleaseDateTwo() {
        version.setReleaseDate(null);
        assertNull("releaseDate was propagated incorrectly", version.getReleaseDate());
    }

    /**
     * Tests <code>setReleased(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes false.
     */
    public void testSetReleasedOne() {
        version.setReleased(false);
        assertFalse("released was propagated incorrectly", version.isReleased());
    }

    /**
     * Tests <code>setReleased(boolean)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes true.
     */
    public void testSetReleasedTwo() {
        version.setReleased(true);
        assertTrue("released was propagated incorrectly", version.isReleased());
    }

    /**
     * Tests <code>setSequence(long)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes -1.
     */
    public void testSetSequenceOne() {
        version.setSequence(-1);
        assertEquals("sequence was propagated incorrectly", -1, version.getSequence());
    }

    /**
     * Tests <code>setSequence(long)</code>.
     * <p>
     * Checks that setter correctly propagates parameter. Passes 239.
     */
    public void testSetSequenceTwo() {
        version.setSequence(239);
        assertEquals("sequence was propagated incorrectly", 239, version.getSequence());
    }

}
