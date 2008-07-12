/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.track.accuracytests;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.service.digitalrun.track.DigitalRunTrackFilterFactory;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The accuracy test for the class {@link DigitalRunTrackFilterFactory}.
 *
 * @author KLW
 * @version 1.0
 */
public class DigitalRunTrackFilterFactoryAccTests extends TestCase {
    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackIdEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackIdEqualsFilter(23);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackIdInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunTrackFilterFactory.createTrackIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackStatusIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackStatusIdEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackStatusIdEqualsFilter(23);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackStatusIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackStatusIdInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunTrackFilterFactory.createTrackStatusIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackDescriptionEqualsFilter(String)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackDescriptionEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackDescriptionEqualsFilter("test");
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackDescriptionLikeFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("SS:test");
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LIKE_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackDescriptionLikeFilter(String,char)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackDescriptionLikeFilter_2() {
        Filter result = DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("SS:test", 'k');
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LIKE_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackTypeIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackTypeIdEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackTypeIdEqualsFilter(23);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackTypeIdInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackTypeIdInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackProjectTypeIdEqualsFilter(long)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackProjectTypeIdEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(23);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackProjectTypedInFilter(java.util.List)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackProjectTypedInFilter() {
        List<Long> list = new ArrayList<Long>();
        list.add(23L);
        list.add(24L);

        Filter result = DigitalRunTrackFilterFactory.createTrackTypeIdInFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.IN_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackStartDateEqualsFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackStartDateEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackStartDateEqualsFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackStartDateGreaterOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackStartDateGreaterOrEqualFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackStartDateGreaterOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.GREATER_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackStartDateLowerOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackStartDateLowerOrEqualFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackStartDateLowerOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LESS_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackEndDateEqualsFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackEndDateEqualsFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackEndDateEqualsFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackEndDateGreaterOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackEndDateGreaterOrEqualFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackStartDateGreaterOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.GREATER_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method
     * {@link DigitalRunTrackFilterFactory#createTrackEndDateLowerOrEqualFilter(java.util.Date)}.
     */
    @SuppressWarnings("deprecation")
    public void test_createTrackEndDateLowerOrEqualFilter() {
        Filter result = DigitalRunTrackFilterFactory.createTrackEndDateLowerOrEqualFilter(new Date());
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.LESS_THAN_OR_EQUAL_TO_FILTER);
    }

    /**
     * The accuracy test for the method {@link DigitalRunTrackFilterFactory#createAndFilter(List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateAndFilter() {
        List<Filter> list = new ArrayList<Filter>();
        list.add(DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("SS:test", 'k'));
        list.add(DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(234));

        Filter result = DigitalRunTrackFilterFactory.createAndFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.AND_FILTER);
    }

    /**
     * The accuracy test for the method {@link DigitalRunTrackFilterFactory#createOrFilter(List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateOrFilter() {
        List<Filter> list = new ArrayList<Filter>();
        list.add(DigitalRunTrackFilterFactory.createTrackDescriptionLikeFilter("SS:test", 'k'));
        list.add(DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(234));

        Filter result = DigitalRunTrackFilterFactory.createOrFilter(list);
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.OR_FILTER);
    }

    /**
     * The accuracy test for the method {@link DigitalRunTrackFilterFactory#createNotFilter(List)}.
     */
    @SuppressWarnings("deprecation")
    public void testcreateNotFilter() {
        Filter result = DigitalRunTrackFilterFactory.createNotFilter(DigitalRunTrackFilterFactory.createTrackProjectTypeIdEqualsFilter(
                    324));
        assertNotNull("The filter should not be null.", result);
        assertEquals("the type is incorrect.", result.getFilterType(), Filter.NOT_FILTER);
    }
}
