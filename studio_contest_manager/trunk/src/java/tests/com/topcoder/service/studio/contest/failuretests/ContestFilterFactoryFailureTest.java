/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest.failuretests;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.studio.contest.utils.ContestFilterFactory;

/**
 * <p>
 * Failure tests for the <code>ContestFilterFactory</code> class.
 * </p>
 *
 * @author KSD
 * @version 1.1
 */
public class ContestFilterFactoryFailureTest extends TestCase {
    /**
     * <p>
     * Failure test for the <code>createStudioFileTypeExtensionFilter(String)</code> method with the argument is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioFileTypeExtensionFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioFileTypeExtensionFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioFileTypeExtensionFilter(String)</code> method with the argument is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioFileTypeExtensionFilterWithEmpty() throws Exception {
        try {
            ContestFilterFactory.createStudioFileTypeExtensionFilter(" ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestChannelNameFilter(String)</code> method with the argument is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestChannelNameFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioContestChannelNameFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestChannelNameFilter(String)</code> method with the argument is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestChannelNameFilterWithEmpty() throws Exception {
        try {
            ContestFilterFactory.createStudioContestChannelNameFilter("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestStatusNameFilter(String)</code> method with the argument is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestStatusNameFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioContestStatusNameFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestStatusNameFilter(String)</code> method with the argument is
     * empty, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestStatusNameFilterWithEmpty() throws Exception {
        try {
            ContestFilterFactory.createStudioContestStatusNameFilter("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestNameFilter(String)</code> method with the argument is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestNameFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioContestNameFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestNameFilter(String)</code> method with the argument is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestNameFilterWithEmpty() throws Exception {
        try {
            ContestFilterFactory.createStudioContestNameFilter("  ");
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestStartDateFilter()</code> method with the argument is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestStartDateFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioContestStartDateFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestEndDateFilter()</code> method with the argument is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestEndDateFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioContestEndDateFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createStudioContestWinnerAnnouncementDeadlineDateFilter()</code> method with the
     * argument is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateStudioContestWinnerAnnouncementDeadlineDateFilterWithNull() throws Exception {
        try {
            ContestFilterFactory.createStudioContestWinnerAnnouncementDeadlineDateFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createAndFilter(Filter first, Filter second)</code> method with the first filter
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAndFilter1WithFirstFilterNull() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(null, ContestFilterFactory.createStudioContestChannelIdFilter(1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createAndFilter(Filter first, Filter second)</code> method with the second filter
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAndFilter1WithSecondFilterNull() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(ContestFilterFactory.createStudioContestChannelIdFilter(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createAndFilter(Filter[] filters)</code> method with the filters is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAndFilter2WithFiltersNull() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createAndFilter(Filter[] filters)</code> method with the filters is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAndFilter2WithFiltersEmpty() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(new Filter[] {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createAndFilter(Filter[] filters)</code> method with the filters contains null
     * element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateAndFilter2WithFiltersContainsNull() throws Exception {
        try {
            ContestFilterFactory.createAndFilter(new Filter[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createOrFilter(Filter first, Filter second)</code> method with the first filter is
     * null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrFilter1WithFirstFilterNull() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(null, ContestFilterFactory.createStudioContestChannelIdFilter(1));
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createOrFilter(Filter first, Filter second)</code> method with the second filter
     * is null, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrFilter1WithSecondFilterNull() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(ContestFilterFactory.createStudioContestChannelIdFilter(1), null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createOrFilter(Filter[] filters)</code> method with the filters is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrFilter2WithFiltersNull() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createOrFilter(Filter[] filters)</code> method with the filters is empty,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrFilter2WithFiltersEmpty() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(new Filter[] {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createOrFilter(Filter[] filters)</code> method with the filters contains null
     * element, IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateOrFilter2WithFiltersContainsNull() throws Exception {
        try {
            ContestFilterFactory.createOrFilter(new Filter[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the <code>createNotFilter(Filter filter)</code> method with the filter is null,
     * IllegalArgumentException is expected.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testCreateNotFilter2WithFilterNull() throws Exception {
        try {
            ContestFilterFactory.createNotFilter(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
