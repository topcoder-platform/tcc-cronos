/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import junit.framework.TestCase;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

/**
 * <p>
 * Unit tests for {@link ContestFilterFactory}.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ContestFilterFactoryUnitTests extends TestCase {

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestIdEqualsFilter(long)}.
     * </p>
     */
    public void testCreateContestIdEqualsFilter_Acc() {
        Filter filter = ContestFilterFactory.createContestIdEqualsFilter(1L);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(ContestFilterFactory.CONTEST_ID, ((EqualToFilter) filter).getName());
        assertEquals(1L, ((EqualToFilter) filter).getValue());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestIdEqualsFilter(long)}.
     * </p>
     *
     * <p>
     * Given id is negative.
     * </p>
     */
    public void testCreateContestIdEqualsFilter_NegativeId() {
        try {
            ContestFilterFactory.createContestIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array is null.
     * </p>
     */
    public void testCreateContestIdsInFilter_NullArray() {
        try {
            ContestFilterFactory.createContestIdsInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array is empty.
     * </p>
     */
    public void testCreateContestIdsInFilter_EmptyArray() {
        try {
            ContestFilterFactory.createContestIdsInFilter(new long[]{});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array contains negative id.
     * </p>
     */
    public void testCreateContestIdsInFilter_ArrayContainsNegative() {
        try {
            ContestFilterFactory.createContestIdsInFilter(new long[]{-1L});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestIdsInFilter(long[])}.
     * </p>
     */
    public void testCreateContestIdsInFilter_Acc() {

        Filter filter = ContestFilterFactory.createContestIdsInFilter(new long[]{1L, 2L});
        assertTrue(filter instanceof InFilter);
        assertEquals(ContestFilterFactory.CONTEST_ID, ((InFilter) filter).getName());
        assertEquals(2, ((InFilter) filter).getList().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestTypeIdEqualsFilter(long)}.
     * </p>
     */
    public void testCreateContestTypeIdEqualsFilter_Acc() {
        Filter filter = ContestFilterFactory.createContestTypeIdEqualsFilter(1L);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(ContestFilterFactory.CONTEST_TYPE_ID, ((EqualToFilter) filter).getName());
        assertEquals(1L, ((EqualToFilter) filter).getValue());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestTypeIdEqualsFilter(long)}.
     * </p>
     *
     * <p>
     * Given id is negative.
     * </p>
     */
    public void testCreateContestTypeIdEqualsFilter_NegativeId() {
        try {
            ContestFilterFactory.createContestTypeIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestTypeIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array is null.
     * </p>
     */
    public void testCreateContestTypeIdsInFilter_NullArray() {
        try {
            ContestFilterFactory.createContestTypeIdsInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestTypeIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array is empty.
     * </p>
     */
    public void testCreateContestTypeIdsInFilter_EmptyArray() {
        try {
            ContestFilterFactory.createContestTypeIdsInFilter(new long[]{});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestTypeIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array contains negative id.
     * </p>
     */
    public void testCreateContestTypeIdsInFilter_ArrayContainsNegative() {
        try {
            ContestFilterFactory.createContestTypeIdsInFilter(new long[]{-1L});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestTypeIdsInFilter(long[])}.
     * </p>
     */
    public void testCreateContestTypeIdsInFilter_Acc() {

        Filter filter = ContestFilterFactory.createContestTypeIdsInFilter(new long[]{1L, 2L});
        assertTrue(filter instanceof InFilter);
        assertEquals(ContestFilterFactory.CONTEST_TYPE_ID, ((InFilter) filter).getName());
        assertEquals(2, ((InFilter) filter).getList().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createTrackIdEqualsFilter(long)}.
     * </p>
     */
    public void testCreateTrackIdEqualsFilter_Acc() {
        Filter filter = ContestFilterFactory.createTrackIdEqualsFilter(1L);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(ContestFilterFactory.TRACK_ID, ((EqualToFilter) filter).getName());
        assertEquals(1L, ((EqualToFilter) filter).getValue());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createTrackIdEqualsFilter(long)}.
     * </p>
     *
     * <p>
     * Given id is negative.
     * </p>
     */
    public void testCreateTrackIdEqualsFilter_NegativeId() {
        try {
            ContestFilterFactory.createTrackIdEqualsFilter(-1L);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createTrackIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array is null.
     * </p>
     */
    public void testCreateTrackIdsInFilter_NullArray() {
        try {
            ContestFilterFactory.createTrackIdsInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createTrackIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array is empty.
     * </p>
     */
    public void testCreateTrackIdsInFilter_EmptyArray() {
        try {
            ContestFilterFactory.createTrackIdsInFilter(new long[]{});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createTrackIdsInFilter(long[])}.
     * </p>
     *
     * <p>
     * Given array contains negative id.
     * </p>
     */
    public void testCreateTrackIdsInFilter_ArrayContainsNegative() {
        try {
            ContestFilterFactory.createTrackIdsInFilter(new long[]{-1L});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createTrackIdsInFilter(long[])}.
     * </p>
     */
    public void testCreateTrackIdsInFilter_Acc() {

        Filter filter = ContestFilterFactory.createTrackIdsInFilter(new long[]{1L, 2L});
        assertTrue(filter instanceof InFilter);
        assertEquals(ContestFilterFactory.TRACK_ID, ((InFilter) filter).getName());
        assertEquals(2, ((InFilter) filter).getList().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createAndFilter(Filter[])}.
     * </p>
     */
    public void testCreateAndFilter_1_Acc() {
        Filter filter = ContestFilterFactory.createAndFilter(new Filter[]{
            ContestFilterFactory.createContestIdEqualsFilter(1L),
            ContestFilterFactory.createContestTypeIdEqualsFilter(1L),
            ContestFilterFactory.createTrackIdEqualsFilter(1L)});

        assertTrue(filter instanceof AndFilter);
        assertEquals(2 + 1, ((AndFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createAndFilter(Filter[])}.
     * </p>
     *
     * <p>
     * Given array is null.
     * </p>
     */
    public void testCreateAndFilter_1_NullArray() {
        try {
            ContestFilterFactory.createAndFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createAndFilter(Filter[])}.
     * </p>
     *
     * <p>
     * Given array contains null.
     * </p>
     */
    public void testCreateAndFilter_1_ArrayContainsNull() {
        try {
            ContestFilterFactory.createAndFilter(new Filter[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createAndFilter(Filter, Filter)}.
     * </p>
     */
    public void testCreateAndFilter_2_Acc() {
        Filter filter = ContestFilterFactory.createAndFilter(
            ContestFilterFactory.createContestIdEqualsFilter(1L),
            ContestFilterFactory.createContestTypeIdEqualsFilter(1L));

        assertTrue(filter instanceof AndFilter);
        assertEquals(2, ((AndFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createAndFilter(Filter, Filter)}.
     * </p>
     *
     * <p>
     * Given first filter is null.
     * </p>
     */
    public void testCreateAndFilter_2_FirstNull() {
        try {
            ContestFilterFactory.createAndFilter(null,
                ContestFilterFactory.createContestTypeIdEqualsFilter(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createAndFilter(Filter, Filter)}.
     * </p>
     *
     * <p>
     * Given second filter is null.
     * </p>
     */
    public void testCreateAndFilter_2_SecondNull() {
        try {
            ContestFilterFactory.createAndFilter(
                ContestFilterFactory.createContestTypeIdEqualsFilter(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createOrFilter(Filter[])}.
     * </p>
     */
    public void testCreateOrFilter_1_Acc() {
        Filter filter = ContestFilterFactory.createOrFilter(new Filter[]{
            ContestFilterFactory.createContestIdEqualsFilter(1L),
            ContestFilterFactory.createContestTypeIdEqualsFilter(1L),
            ContestFilterFactory.createTrackIdEqualsFilter(1L)});

        assertTrue(filter instanceof OrFilter);
        assertEquals(2 + 1, ((OrFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createOrFilter(Filter[])}.
     * </p>
     *
     * <p>
     * Given array is null.
     * </p>
     */
    public void testCreateOrFilter_1_NullArray() {
        try {
            ContestFilterFactory.createOrFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createOrFilter(Filter[])}.
     * </p>
     *
     * <p>
     * Given array contains null.
     * </p>
     */
    public void testCreateOrFilter_1_ArrayContainsNull() {
        try {
            ContestFilterFactory.createOrFilter(new Filter[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createOrFilter(Filter, Filter)}.
     * </p>
     */
    public void testCreateOrFilter_2_Acc() {
        Filter filter = ContestFilterFactory.createOrFilter(
            ContestFilterFactory.createContestIdEqualsFilter(1L),
            ContestFilterFactory.createContestTypeIdEqualsFilter(1L));

        assertTrue(filter instanceof OrFilter);
        assertEquals(2, ((OrFilter) filter).getFilters().size());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createOrFilter(Filter, Filter)}.
     * </p>
     *
     * <p>
     * Given first filter is null.
     * </p>
     */
    public void testCreateOrFilter_2_FirstNull() {
        try {
            ContestFilterFactory.createOrFilter(null,
                ContestFilterFactory.createContestTypeIdEqualsFilter(1L));
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createOrFilter(Filter, Filter)}.
     * </p>
     *
     * <p>
     * Given second filter is null.
     * </p>
     */
    public void testCreateOrFilter_2_SecondNull() {
        try {
            ContestFilterFactory.createOrFilter(
                ContestFilterFactory.createContestTypeIdEqualsFilter(1L), null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createNotFilter(Filter)}.
     * </p>
     *
     * <p>
     * Given filter is null.
     * </p>
     */
    public void testCreateNotFilter_NullFilter() {
        try {
            ContestFilterFactory.createNotFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createNotFilter(Filter)}.
     * </p>
     */
    public void testCreateNotFilter_Acc() {
        Filter target = ContestFilterFactory.createTrackIdEqualsFilter(1L);
        Filter filter = ContestFilterFactory.createNotFilter(target);

        assertTrue(filter instanceof NotFilter);
        assertTrue(((NotFilter) filter).getFilter() instanceof EqualToFilter);
        assertEquals(ContestFilterFactory.TRACK_ID,
            ((EqualToFilter) ((NotFilter) filter).getFilter()).getName());
        assertEquals(1L,
            ((EqualToFilter) ((NotFilter) filter).getFilter()).getValue());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionEqualsFilter(String)}.
     * </p>
     */
    public void testCreateContestDescriptionEqualsFilter_Acc() {
        Filter filter = ContestFilterFactory.createContestDescriptionEqualsFilter("description");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(ContestFilterFactory.CONTEST_DESCRIPTION,
                ((EqualToFilter) filter).getName());
        assertEquals("description",
                ((EqualToFilter) filter).getValue());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionEqualsFilter(String)}.
     * </p>
     *
     * <p>
     * Given string is null.
     * </p>
     */
    public void testCreateContestDescriptionEqualsFilter_NullString() {
        try {
            ContestFilterFactory.createContestDescriptionEqualsFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionEqualsFilter(String)}.
     * </p>
     *
     * <p>
     * Given string is empty.
     * </p>
     */
    public void testCreateContestDescriptionEqualsFilter_EmptyString() {
        try {
            ContestFilterFactory.createContestDescriptionEqualsFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionLikeFilter(String)}.
     * </p>
     */
    public void testCreateContestDescriptionLikeFilter_Acc() {
        Filter filter = ContestFilterFactory.createContestDescriptionLikeFilter("SS:description");
        assertTrue(filter instanceof LikeFilter);
        assertEquals(ContestFilterFactory.CONTEST_DESCRIPTION,
                ((LikeFilter) filter).getName());
        assertEquals("SS:description",
                ((LikeFilter) filter).getValue());
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionLikeFilter(String)}.
     * </p>
     *
     * <p>
     * Given string is null.
     * </p>
     */
    public void testCreateContestDescriptionLikeFilter_NullString() {
        try {
            ContestFilterFactory.createContestDescriptionLikeFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionLikeFilter(String)}.
     * </p>
     *
     * <p>
     * Given string is empty.
     * </p>
     */
    public void testCreateContestDescriptionLikeFilter_EmptyString() {
        try {
            ContestFilterFactory.createContestDescriptionLikeFilter(" ");
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionInFilter(string[])}.
     * </p>
     *
     * <p>
     * Given array is null.
     * </p>
     */
    public void testCreateContestDescriptionInFilter_NullArray() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(null);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionInFilter(string[])}.
     * </p>
     *
     * <p>
     * Given array is empty.
     * </p>
     */
    public void testCreateContestDescriptionInFilter_EmptyArray() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(new String[]{});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionInFilter(string[])}.
     * </p>
     *
     * <p>
     * Given array contains null.
     * </p>
     */
    public void testCreateContestDescriptionInFilter_ArrayContainsNull() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(new String[]{null});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionInFilter(string[])}.
     * </p>
     *
     * <p>
     * Given array contains empty.
     * </p>
     */
    public void testCreateContestDescriptionInFilter_ArrayContainsEmpty() {
        try {
            ContestFilterFactory.createContestDescriptionInFilter(new String[]{" "});
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * <p>
     * Test method {@link ContestFilterFactory#createContestDescriptionInFilter(string[])}.
     * </p>
     */
    public void testCreateContestDescriptionInFilter_Acc() {

        Filter filter = ContestFilterFactory.createContestDescriptionInFilter(new String[]{"s1", "s2"});
        assertTrue(filter instanceof InFilter);
        assertEquals(ContestFilterFactory.CONTEST_DESCRIPTION, ((InFilter) filter).getName());
        assertEquals(2, ((InFilter) filter).getList().size());
    }
}
