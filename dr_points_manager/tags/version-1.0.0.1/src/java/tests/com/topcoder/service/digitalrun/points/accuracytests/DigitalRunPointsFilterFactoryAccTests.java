/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.points.accuracytests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.points.DigitalRunPointsFilterFactory;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The accuracy test for the class {@link DigitalRunPointsFilterFactory}.
 *
 * @author KLW
 * @version 1.0
 */
public class DigitalRunPointsFilterFactoryAccTests extends TestCase {
    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testCreatePointsIdEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsIdEqualsFilter(23);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void testCreatePointsIdInFilter() {
        List<Long> pointsIds = new ArrayList<Long>();
        pointsIds.add(23L);
        pointsIds.add(24L);

        Filter result = DigitalRunPointsFilterFactory.createPointsIdInFilter(pointsIds);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createTrackIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateTrackIdEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createTrackIdEqualsFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createTrackIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateTrackIdInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunPointsFilterFactory.createTrackIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsDescriptionEqualsFilter(String)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsDescriptionEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsDescriptionEqualsFilter("test");
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsDescriptionLikeFilter(String)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsDescriptionLikeFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsDescriptionLikeFilter("SS:test");
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LIKE_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsDescriptionLikeFilter(String)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsDescriptionLikeFilter_2() {
        Filter result = DigitalRunPointsFilterFactory.createPointsDescriptionLikeFilter("SS:test", 'c');
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LIKE_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createIsPotentialEqualFilter(boolean)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateIsPotentialEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createIsPotentialEqualFilter(true);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsStatusIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsStatusIdEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsStatusIdEqualsFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsStatusIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsStatusIdInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunPointsFilterFactory.createPointsStatusIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsTypeIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsTypeIdEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsTypeIdEqualsFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsTypedInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsTypedInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunPointsFilterFactory.createPointsTypedInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createReferenceIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateReferenceIdEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createReferenceIdEqualsFilter(23L, 34L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.AND_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createReferenceIdEqualsInFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateReferenceIdEqualsInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunPointsFilterFactory.createReferenceIdEqualsInFilter(23L, list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.AND_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createUserIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateUserIdEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createUserIdEqualsFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createUserIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateUserIdInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunPointsFilterFactory.createUserIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createAmountEqualsFilter(double)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateAmountEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createAmountEqualsFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createAmountGreaterOrEqualFilter(double)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateAmountGreaterOrEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createAmountGreaterOrEqualFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.GREATER_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createAmountLowerOrEqualFilter(double)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateAmountLowerOrEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createAmountLowerOrEqualFilter(23L);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LESS_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsApplicationDateEqualsFilter(java.util.Date}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsApplicationDateEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsApplicationDateEqualsFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsApplicationDateGreaterOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsApplicationDateGreaterOrEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsApplicationDateGreaterOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.GREATER_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsApplicationDateLowerOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsApplicationDateLowerOrEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsApplicationDateLowerOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LESS_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsAwardDateEqualsFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsAwardDateEqualsFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsAwardDateEqualsFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsAwardDateGreaterOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsAwardDateGreaterOrEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsAwardDateGreaterOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.GREATER_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunPointsFilterFactory#createPointsAwardDateLowerOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreatePointsAwardDateLowerOrEqualFilter() {
        Filter result = DigitalRunPointsFilterFactory.createPointsAwardDateLowerOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LESS_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method {@link DigitalRunPointsFilterFactory#createAndFilter(List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateAndFilter() {
        List<Filter> list = new ArrayList<Filter>();
        list.add(DigitalRunPointsFilterFactory.createAmountEqualsFilter(324));
        list.add(DigitalRunPointsFilterFactory.createIsPotentialEqualFilter(false));

        Filter result = DigitalRunPointsFilterFactory.createAndFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.AND_FILTER);
    }

    /**
     * The accuracy test for the method {@link DigitalRunPointsFilterFactory#createOrFilter(List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateOrFilter() {
        List<Filter> list = new ArrayList<Filter>();
        list.add(DigitalRunPointsFilterFactory.createAmountEqualsFilter(324));
        list.add(DigitalRunPointsFilterFactory.createIsPotentialEqualFilter(false));

        Filter result = DigitalRunPointsFilterFactory.createOrFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.OR_FILTER);
    }

    /**
     * The accuracy test for the method {@link DigitalRunPointsFilterFactory#createNotFilter(List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateNotFilter() {
        Filter result = DigitalRunPointsFilterFactory.createNotFilter(DigitalRunPointsFilterFactory.createAmountEqualsFilter(
                    324));
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.NOT_FILTER);
    }
}
