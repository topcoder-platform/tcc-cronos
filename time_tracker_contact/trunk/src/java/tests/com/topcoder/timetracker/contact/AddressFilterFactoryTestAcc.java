/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>This test case contains accuracy tests for <code>AddressFilterFactory</code>.</p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class AddressFilterFactoryTestAcc extends TestCase {
    /**
     * <p>
     * Test method <code>createCreatedInFilter()</code>.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        Date current = new Date();
        Filter filter = AddressFilterFactory.createCreatedInFilter(current, null);
        assertTrue("To date is null, GreaterThanOrEqualToFilter expected",
            filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("LowerThreshold should be " + current, current,
            ((GreaterThanOrEqualToFilter) filter).getLowerThreshold());
        assertEquals(AddressFilterFactory.CREATION_DATE, ((GreaterThanOrEqualToFilter) filter).getName());

        filter = AddressFilterFactory.createCreatedInFilter(null, current);
        assertTrue("From date is null, LessThanOrEqualToFilter expected",
                filter instanceof LessThanOrEqualToFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((LessThanOrEqualToFilter) filter).getUpperThreshold());
        assertEquals(AddressFilterFactory.CREATION_DATE, ((LessThanOrEqualToFilter) filter).getName());

        filter = AddressFilterFactory.createCreatedInFilter(current, current);
        assertTrue("To date and From date are both not null, BetweenFilter expected",
                filter instanceof BetweenFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((BetweenFilter) filter).getUpperThreshold());
        assertEquals("LowerThreshold should be " + current, current,
                ((BetweenFilter) filter).getLowerThreshold());
        assertEquals(AddressFilterFactory.CREATION_DATE, ((BetweenFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createModifiedInFilter()</code>.
     * </p>
     */
    public void testCreateModifiedInFilter() {
        Date current = new Date();
        Filter filter = AddressFilterFactory.createModifiedInFilter(current, null);
        assertTrue("To date is null, GreaterThanOrEqualToFilter expected",
            filter instanceof GreaterThanOrEqualToFilter);
        assertEquals("LowerThreshold should be " + current, current,
            ((GreaterThanOrEqualToFilter) filter).getLowerThreshold());
        assertEquals(AddressFilterFactory.MODIFICATION_DATE, ((GreaterThanOrEqualToFilter) filter).getName());

        filter = AddressFilterFactory.createModifiedInFilter(null, current);
        assertTrue("From date is null, LessThanOrEqualToFilter expected",
                filter instanceof LessThanOrEqualToFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((LessThanOrEqualToFilter) filter).getUpperThreshold());
        assertEquals(AddressFilterFactory.MODIFICATION_DATE, ((LessThanOrEqualToFilter) filter).getName());

        filter = AddressFilterFactory.createModifiedInFilter(current, current);
        assertTrue("To date and From date are both not null, BetweenFilter expected",
                filter instanceof BetweenFilter);
        assertEquals("UpperThreshold should be " + current, current,
                ((BetweenFilter) filter).getUpperThreshold());
        assertEquals("LowerThreshold should be " + current, current,
                ((BetweenFilter) filter).getLowerThreshold());
        assertEquals(AddressFilterFactory.MODIFICATION_DATE, ((BetweenFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createCreatedByFilter()</code>.
     * </p>
     */
    public void testCreateCreatedByFilter() {
        Filter filter = AddressFilterFactory.createCreatedByFilter("user");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("user", ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.CREATION_USER, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createModifiedByFilter()</code>.
     * </p>
     */
    public void testCreateModifiedByFilter() {
        Filter filter = AddressFilterFactory.createModifiedByFilter("user");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("user", ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.MODIFICATION_USER, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createCityFilter()</code>.
     * </p>
     */
    public void testCreateCityFilter() {
        Filter filter = AddressFilterFactory.createCityFilter("city");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("city", ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.CITY, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createStateIDFilter()</code>.
     * </p>
     */
    public void testCreateStateIDFilter() {
        Filter filter = AddressFilterFactory.createStateIDFilter(3);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(new Long(3), ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.STATE_NAME_ID, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createZipCodeFilter()</code>.
     * </p>
     */
    public void testCreateZipCodeFilter() {
        Filter filter = AddressFilterFactory.createZipCodeFilter("zipCode");
        assertTrue(filter instanceof LikeFilter);
        assertEquals(LikeFilter.CONTAIN_TAGS + "zipCode", ((LikeFilter) filter).getValue());
        assertEquals(AddressFilterFactory.ZIP_CODE, ((LikeFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createCountryIDFilter()</code>.
     * </p>
     */
    public void testCreateCountryIDFilter() {
        Filter filter = AddressFilterFactory.createCountryIDFilter(3);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(new Long(3), ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.COUNTRY_NAME_ID, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createTypeFilter()</code>.
     * </p>
     */
    public void testCreateTypeFilter() {
        Filter filter = AddressFilterFactory.createTypeFilter(AddressType.PROJECT);
        assertTrue(filter instanceof EqualToFilter);
        assertEquals(new Long(AddressType.PROJECT.getId()), ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.ADDRESS_TYPE_ID, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createEntityIDFilter()</code>.
     * </p>
     */
    public void testCreateEntityIDFilter() {
        Filter filter = AddressFilterFactory.createEntityIDFilter(3, AddressType.PROJECT);
        assertTrue(filter instanceof AndFilter);
        assertEquals(2, ((AndFilter) filter).getFilters().size());
        EqualToFilter filter1 = (EqualToFilter) ((AndFilter) filter).getFilters().get(0);
        EqualToFilter filter2 = (EqualToFilter) ((AndFilter) filter).getFilters().get(1);
        assertEquals(new Long(AddressType.PROJECT.getId()), filter1.getValue());
        assertEquals(AddressFilterFactory.ADDRESS_TYPE_ID, filter1.getName());
        assertEquals(new Long(3), filter2.getValue());
        assertEquals(AddressFilterFactory.ENTITY_ID, filter2.getName());
    }
    /**
     * <p>
     * Test method <code>createCountryNameFilter()</code>.
     * </p>
     */
    public void testCreateCountryNameFilter() {
        Filter filter = AddressFilterFactory.createCountryNameFilter("country");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("country", ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.COUNTRY_NAME, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>createStateNameFilter()</code>.
     * </p>
     */
    public void testCreateStateNameFilter() {
        Filter filter = AddressFilterFactory.createStateNameFilter("state");
        assertTrue(filter instanceof EqualToFilter);
        assertEquals("state", ((EqualToFilter) filter).getValue());
        assertEquals(AddressFilterFactory.STATE_NAME, ((EqualToFilter) filter).getName());
    }
    /**
     * <p>
     * Test method <code>andFilter()</code>.
     * </p>
     */
    public void testAndFilter() {
        Filter filter1 = AddressFilterFactory.createCreatedByFilter("user");
        Filter filter2 = AddressFilterFactory.createModifiedByFilter("user");
        Filter filter = AddressFilterFactory.andFilter(filter1, filter2);
        assertEquals(filter1, ((AndFilter) filter).getFilters().get(0));
        assertEquals(filter2, ((AndFilter) filter).getFilters().get(1));
    }
    /**
     * <p>
     * Test method <code>orFilter()</code>.
     * </p>
     */
    public void testOrFilter() {
        Filter filter1 = AddressFilterFactory.createCreatedByFilter("user");
        Filter filter2 = AddressFilterFactory.createModifiedByFilter("user");
        Filter filter = AddressFilterFactory.orFilter(filter1, filter2);
        assertEquals(filter1, ((OrFilter) filter).getFilters().get(0));
        assertEquals(filter2, ((OrFilter) filter).getFilters().get(1));
    }
    /**
     * <p>
     * Test method <code>notFilter()</code>.
     * </p>
     */
    public void testNotFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createCreatedByFilter("user");
        Filter notFilter = AddressFilterFactory.notFilter(filter);
        EqualToFilter notedFilter = (EqualToFilter) ((NotFilter) notFilter).getFilter();
        assertEquals(filter.getName(), notedFilter.getName());
        assertEquals(filter.getValue(), notedFilter.getValue());
    }
}
