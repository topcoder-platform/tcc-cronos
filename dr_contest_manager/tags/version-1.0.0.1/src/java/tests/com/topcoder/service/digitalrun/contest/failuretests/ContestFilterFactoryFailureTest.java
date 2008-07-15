/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest.failuretests;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.service.digitalrun.contest.ContestFilterFactory;

/**
 * <p>
 * Failure tests for the <cod>ContestFilterFactory</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestFilterFactoryFailureTest extends TestCase {

    /**
     * <p>
     * Failure test for the method <code>createContestIdEqualsFilter(long)</code> with the id is negative,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestIdEqualsFilterWithNegativeId() {
        try {
            ContestFilterFactory.createContestIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestIdEqualsFilter(long)</code> with the array is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestIdsInFilterWithNullArray() {
        try {
            ContestFilterFactory.createContestIdsInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestIdEqualsFilter(long)</code> with the array is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestIdsInFilterWithEmptyArray() {
        try {
            ContestFilterFactory.createContestIdsInFilter(new long[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestIdEqualsFilter(long)</code> with the array contains negative
     * id, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestIdsInFilterWithArrayContainsNegative() {
        try {
            ContestFilterFactory.createContestIdsInFilter(new long[] {-1L});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestTypeIdEqualsFilter(long)</code> with the array contains
     * negative id, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestTypeIdEqualsFilterWithNegativeId() {
        try {
            ContestFilterFactory.createContestTypeIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestTypeIdsInFilter(long)</code> with the array is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestTypeIdsInFilterWithNullArray() {
        try {
            ContestFilterFactory.createContestTypeIdsInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestTypeIdsInFilter(long)</code> with the array is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestTypeIdsInFilterWithEmptyArray() {
        try {
            ContestFilterFactory.createContestTypeIdsInFilter(new long[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestTypeIdsInFilter(long)</code> with the array contains negative
     * id, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestTypeIdsInFilterWithArrayContainsNegative() {
        try {
            ContestFilterFactory.createContestTypeIdsInFilter(new long[] {-1L});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createTrackIdEqualsFilter(long)</code> with the id is negative,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateTrackIdEqualsFilterWithNegativeId() {
        try {
            ContestFilterFactory.createTrackIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createTrackIdEqualsFilter(long)</code> with the array is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateTrackIdsInFilterWithNullArray() {
        try {
            ContestFilterFactory.createTrackIdsInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createTrackIdEqualsFilter(long)</code> with the array is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateTrackIdsInFilterWithEmptyArray() {
        try {
            ContestFilterFactory.createTrackIdsInFilter(new long[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createTrackIdEqualsFilter(long)</code> with the array contains negative id,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateTrackIdsInFilterWithArrayContainsNegative() {
        try {
            ContestFilterFactory.createTrackIdsInFilter(new long[] {-1L});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createAndFilter(Filter[])</code> with the array is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateAndFilter1WithNullArray() {
        try {
            ContestFilterFactory.createAndFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createAndFilter(Filter[])</code> with the array contains null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateAndFilter1WithArrayContainsNull() {
        try {
            ContestFilterFactory.createAndFilter(new Filter[] {null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createAndFilter(Filter, Filter)</code> with the first filter is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateAndFilter2WithFirstNull() {
        try {
            ContestFilterFactory.createAndFilter(null, ContestFilterFactory.createContestTypeIdEqualsFilter(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createAndFilter(Filter, Filter)</code> with the second filter is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateAndFilter2WithSecondNull() {
        try {
            ContestFilterFactory.createAndFilter(ContestFilterFactory.createContestTypeIdEqualsFilter(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createOrFilter(Filter[])</code> with the array is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateOrFilter1WithNullArray() {
        try {
            ContestFilterFactory.createOrFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createOrFilter(Filter[])</code> with the array contains null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateOrFilter1WithArrayContainsNull() {
        try {
            ContestFilterFactory.createOrFilter(new Filter[] {null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createOrFilter(Filter, Filter)</code> with the first filter is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateOrFilter2WithFirstNull() {
        try {
            ContestFilterFactory.createOrFilter(null, ContestFilterFactory.createContestTypeIdEqualsFilter(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createOrFilter(Filter, Filter)</code> with the second filter is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateOrFilter2WithSecondNull() {
        try {
            ContestFilterFactory.createOrFilter(ContestFilterFactory.createContestTypeIdEqualsFilter(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createNotFilter(Filter, Filter)</code> with the filter is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateNotFilterWithNullFilter() {
        try {
            ContestFilterFactory.createNotFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionEqualsFilter(String)</code> with the description is
     * null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionEqualsFilterWithNullDescription() {
        try {
            ContestFilterFactory.createContestDescriptionEqualsFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionEqualsFilter(String)</code> with the description is
     * empty, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionEqualsFilterWithEmptyDescription() {
        try {
            ContestFilterFactory.createContestDescriptionEqualsFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionLikeFilter(String)</code> with the string is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionLikeFilter_NullString() {
        try {
            ContestFilterFactory.createContestDescriptionLikeFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionLikeFilter(String)</code> with the string is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionLikeFilterWithEmptyString() {
        try {
            ContestFilterFactory.createContestDescriptionLikeFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionLikeFilter(String[])</code> with the array is null,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionInFilterWithNullArray() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionInFilter(String[])</code> with the array is empty,
     * IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionInFilterWithEmptyArray() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(new String[] {});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionInFilter(String[])</code> with the array contains
     * null, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionInFilterWithArrayContainsNull() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(new String[] {null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Failure test for the method <code>createContestDescriptionInFilter(String[])</code> with the array contains
     * empty, IllegalArgumentException is expected.
     * </p>
     */
    public void testCreateContestDescriptionInFilterWithArrayContainsEmpty() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(new String[] {" "});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
