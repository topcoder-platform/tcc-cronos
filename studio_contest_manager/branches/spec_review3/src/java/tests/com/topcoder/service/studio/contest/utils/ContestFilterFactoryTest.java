/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

/**
 * <p>
 * Unit tests for <code>ContestFilterFactory</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestFilterFactoryTest extends TestCase {
    /**
     * <p>
     * Sets up the fixture. This method is called before a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the fixture. This method is called after a test is executed.
     * </p>
     *
     * @throws Exception if any error occurs.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Gets the test suite for <code>ContestFilterFactory</code> class.
     * </p>
     *
     * @return a <code>TestSuite</code> providing the tests for <code>ContestFilterFactory</code> class.
     */
    public static Test suite() {
        return new TestSuite(ContestFilterFactoryTest.class);
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestFileTypeIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestFileTypeIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestFileTypeIdFilter(1);
        assertEqualFilter(filter, "contest_channel_lu.file_type_id", new Long(1));
    }

    /**
     * Asserts a filter which should be a EqualFilter.
     *
     * @param filter to be checked
     * @param name expected
     * @param value expected
     */
    private void assertEqualFilter(Filter filter, String name, Object value) {
        assertTrue("It should be equal filter.", filter instanceof EqualToFilter);
        EqualToFilter ef = (EqualToFilter) filter;
        //assertEquals("name should be '" + name + "'.", name, ef.getName());
        assertEquals("value is not as expected.", value, ef.getValue());
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioFileTypeExtensionFilter(String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioFileTypeExtensionFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioFileTypeExtensionFilter("jpeg");
        assertEqualFilter(filter, "file_type_lu.extension", "jpeg");
    }

    /**
     * <p>
     * Failure test for <code>createStudioFileTypeExtensionFilter(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioFileTypeExtensionFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioFileTypeExtensionFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createStudioFileTypeExtensionFilter(String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioFileTypeExtensionFilter_Failure2() throws Exception {
        try {
            ContestFilterFactory.createStudioFileTypeExtensionFilter(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestChannelIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestChannelIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestChannelIdFilter(1);
        assertEqualFilter(filter, "contest.contest_channel_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestChannelNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestChannelNameFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestChannelNameFilter("channel");
        assertEqualFilter(filter, "contest_channel_lu.channel_name", "channel");
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestChannelNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestChannelNameFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioContestChannelNameFilter(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestChannelNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestChannelNameFilter_Failure2() throws Exception {
        try {
            ContestFilterFactory.createStudioContestChannelNameFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestStatusIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestStatusIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestStatusIdFilter(1);
        assertEqualFilter(filter, "contest.contest_status_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestStatusNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestStatusNameFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestStatusNameFilter("status");
        assertEqualFilter(filter, "contest_status_lu.name", "status");
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestStatusNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestStatusNameFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioContestStatusNameFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestStatusNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestStatusNameFilter_Failure2() throws Exception {
        try {
            ContestFilterFactory.createStudioContestStatusNameFilter(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestIdFilter(1);
        assertEqualFilter(filter, "contest.contest_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestNameFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestNameFilter("name");
        assertEqualFilter(filter, "contest.name", "name");
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestNameFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioContestNameFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestNameFilter(String)</code>.
     * </p>
     * <p>
     * Passes in empty value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestNameFilter_Failure2() throws Exception {
        try {
            ContestFilterFactory.createStudioContestNameFilter(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestProjectIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestProjectIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestProjectIdFilter(1);
        assertEqualFilter(filter, "contest.project_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestDirectProjectIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestDirectProjectIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestDirectProjectIdFilter(1);
        assertEqualFilter(filter, "contest.tc_direct_project_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestForumIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestForumIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestForumIdFilter(1);
        assertEqualFilter(filter, "contest.forum_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestEventIdFilter(long)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestEventIdFilter_Accuracy() {
        Filter filter = ContestFilterFactory.createStudioContestEventIdFilter(1);
        assertEqualFilter(filter, "contest.event_id", new Long(1));
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestStartDateFilter(Date)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestStartDateFilter_Accuracy() {
        Date date = new Date();
        Filter filter = ContestFilterFactory.createStudioContestStartDateFilter(date);
        assertEqualFilter(filter, "contest.start_time", date);
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestStartDateFilter(Date)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestStartDateFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioContestStartDateFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestEndDateFilter(Date)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestEndDateFilter_Accuracy() {
        Date date = new Date();
        Filter filter = ContestFilterFactory.createStudioContestEndDateFilter(date);
        assertEqualFilter(filter, "contest.end_date", date);
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestEndDateFilter(Date)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestEndDateFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioContestEndDateFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createStudioContestWinnerAnnouncementDeadlineDateFilter(Date)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateStudioContestWinnerAnnouncementDeadlineDateFilter_Accuracy() {
        Date date = new Date();
        Filter filter = ContestFilterFactory.createStudioContestWinnerAnnouncementDeadlineDateFilter(date);
        assertEqualFilter(filter, "contest.winner_annoucement_deadline", date);
    }

    /**
     * <p>
     * Failure test for <code>createStudioContestWinnerAnnouncementDeadlineDateFilter(Date)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateStudioContestWinnerAnnouncementDeadlineDateFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createStudioContestWinnerAnnouncementDeadlineDateFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createAndFilter(Filter,Filter)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateAndFilter_Accuracy() {
        AndFilter andFilter = (AndFilter) ContestFilterFactory.createAndFilter(ContestFilterFactory
            .createStudioContestForumIdFilter(1), ContestFilterFactory.createStudioContestChannelIdFilter(1));
        assertEquals("It should contain 2 filters.", 2, andFilter.getFilters().size());
    }

    /**
     * <p>
     * Failure test for <code>createAndFilter(Filter,Filter)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateAndFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(null, ContestFilterFactory.createStudioContestChannelIdFilter(1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createAndFilter(Filter,Filter)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateAndFilter_Failure2() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(ContestFilterFactory.createStudioContestForumIdFilter(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createAndFilter(Filter[])</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateAndFilterWithArray_Accuracy() {
        AndFilter andFilter = (AndFilter) ContestFilterFactory.createAndFilter(new Filter[] {
            ContestFilterFactory.createStudioContestForumIdFilter(1),
            ContestFilterFactory.createStudioContestChannelIdFilter(1)});
        assertEquals("It should contain 2 filters.", 2, andFilter.getFilters().size());
    }

    /**
     * <p>
     * Failure test for <code>createAndFilter(Filter[])</code>.
     * </p>
     * <p>
     * Passes in value with null element. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateAndFilterWithArray_Failure1() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(new Filter[] {
                ContestFilterFactory.createStudioContestForumIdFilter(1), null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createAndFilter(Filter[])</code>.
     * </p>
     * <p>
     * Passes in empty array. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateAndFilterWithArray_Failure2() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(new Filter[] {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createAndFilter(Filter[])</code>.
     * </p>
     * <p>
     * Passes in null. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateAndFilterWithArray_Failure3() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createOrFilter(Filter,Filter)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateOrFilter_Accuracy() {
        OrFilter orFilter = (OrFilter) ContestFilterFactory.createOrFilter(ContestFilterFactory
            .createStudioContestForumIdFilter(1), ContestFilterFactory.createStudioContestChannelIdFilter(1));
        assertEquals("It should contain 2 filters.", 2, orFilter.getFilters().size());
    }

    /**
     * <p>
     * Failure test for <code>createOrFilter(Filter,Filter)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateOrFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(null, ContestFilterFactory.createStudioContestChannelIdFilter(1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Failure test for <code>createOrFilter(Filter,Filter)</code>.
     * </p>
     * <p>
     * Passes in null value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateOrFilter_Failure2() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(ContestFilterFactory.createStudioContestChannelIdFilter(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createOrFilter(Filter[])</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateOrFilterWithArray_Accuracy() {
        OrFilter orFilter = (OrFilter) ContestFilterFactory.createOrFilter(new Filter[] {
            ContestFilterFactory.createStudioContestForumIdFilter(1),
            ContestFilterFactory.createStudioContestChannelIdFilter(1)});
        assertEquals("It should contain 2 filters.", 2, orFilter.getFilters().size());
    }

    /**
     * <p>
     * Failure test for <code>createOrFilter(Filter[])</code>.
     * </p>
     * <p>
     * Passes in value with null element. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateOrFilterWithArray_Failure1() throws Exception {
        try {
            OrFilter orFilter = (OrFilter) ContestFilterFactory.createOrFilter(new Filter[] {
                ContestFilterFactory.createStudioContestForumIdFilter(1), null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Accuracy test for <code>createNotFilter(Filter)</code>.
     * </p>
     * <p>
     * Passes in valid value. No exception should be thrown.
     * </p>
     */
    public void testCreateNotFilter_Accuracy() {
        NotFilter notFilter = (NotFilter) ContestFilterFactory.createNotFilter(ContestFilterFactory
            .createStudioContestForumIdFilter(1));
        assertNotNull("It should contain a component filter.", notFilter.getFilter());

    }

    /**
     * <p>
     * Failure test for <code>createNotFilter(Filter)</code>.
     * </p>
     * <p>
     * Passes in invalid value. A <code>IllegalArgumentException</code> should be thrown.
     * </p>
     *
     * @throws Exception to JUnit, indicates an error
     */
    public void testCreateNotFilter_Failure1() throws Exception {
        try {
            ContestFilterFactory.createNotFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test to see if a private Ctor exists.
     * </p>
     *
     * @throws Exception to JUnit, indicates error
     */
    public void testPrivateCtor() throws Exception {
        try {
            Constructor privateCtor = ContestFilterFactory.class.getDeclaredConstructors()[0];
            assertTrue(Modifier.isPrivate(privateCtor.getModifiers()));
            privateCtor.setAccessible(true);
            privateCtor.newInstance(new Object[] {});
        } catch (SecurityException e) {
            System.out.println("Skip test private constructor due to security reason.");
        }
    }
}
