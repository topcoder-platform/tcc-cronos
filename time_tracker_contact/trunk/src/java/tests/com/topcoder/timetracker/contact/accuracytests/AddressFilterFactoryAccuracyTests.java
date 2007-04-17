/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.contact.accuracytests;

import java.util.Date;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.contact.AddressFilterFactory;
import com.topcoder.timetracker.contact.AddressType;

/**
 * <p>
 * Accuracy Unit test cases for AddressFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class AddressFilterFactoryAccuracyTests extends TestCase {

    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(AddressFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createCreatedInFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) AddressFilterFactory.createCreatedInFilter(null,
            new Date());
        assertEquals("Failed to create the filter.", "creation_date", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createModifiedInFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateModifiedInFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) AddressFilterFactory.createModifiedInFilter(null,
            new Date());
        assertEquals("Failed to create the filter.", "modification_date", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createCreatedByFilter(String) for accuracy.
     * </p>
     */
    public void testCreateCreatedByFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createCreatedByFilter("userName");
        assertEquals("Failed to create the filter.", "creation_user", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createModifiedByFilter(String) for accuracy.
     * </p>
     */
    public void testCreateModifiedByFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createModifiedByFilter("userName");
        assertEquals("Failed to create the filter.", "modification_user", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createCityFilter(String) for accuracy.
     * </p>
     */
    public void testCreateCityFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createCityFilter("city");
        assertEquals("Failed to create the filter.", "city", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createStateIDFilter(long) for accuracy.
     * </p>
     */
    public void testCreateStateIDFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createStateIDFilter(8);
        assertEquals("Failed to create the filter.", "state_name_id", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createZipCodeFilter(String) for accuracy.
     * </p>
     */
    public void testCreateZipCodeFilter() {
        LikeFilter filter = (LikeFilter) AddressFilterFactory.createZipCodeFilter("zipCode");
        assertEquals("Failed to create the filter.", "zip_code", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createCountryIDFilter(long) for accuracy.
     * </p>
     */
    public void testCreateCountryIDFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createCountryIDFilter(8);
        assertEquals("Failed to create the filter.", "country_name_id", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createTypeFilter(AddressType) for accuracy.
     * </p>
     */
    public void testCreateTypeFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createTypeFilter(AddressType.CLIENT);
        assertEquals("Failed to create the filter.", "address_type_id", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createEntityIDFilter(long,AddressType) for accuracy.
     * </p>
     */
    public void testCreateEntityIDFilter() {
        AndFilter filter = (AndFilter) AddressFilterFactory.createEntityIDFilter(8, AddressType.CLIENT);
        assertEquals("Failed to create the filter.", 2, filter.getFilters().size());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createCountryNameFilter(String) for accuracy.
     * </p>
     */
    public void testCreateCountryNameFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createCountryNameFilter("countryName");
        assertEquals("Failed to create the filter.", "country_name", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#createStateNameFilter(String) for accuracy.
     * </p>
     */
    public void testCreateStateNameFilter() {
        EqualToFilter filter = (EqualToFilter) AddressFilterFactory.createStateNameFilter("stateName");
        assertEquals("Failed to create the filter.", "state_name", filter.getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#andFilter(Filter,Filter) for accuracy.
     * </p>
     */
    public void testAndFilter() {
        Filter first = new EqualToFilter("first", "value");
        Filter second = new EqualToFilter("second", "value");
        AndFilter filter = (AndFilter) AddressFilterFactory.andFilter(first, second);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilters().get(0)).getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#orFilter(Filter,Filter) for accuracy.
     * </p>
     */
    public void testOrFilter() {
        Filter first = new EqualToFilter("first", "value");
        Filter second = new EqualToFilter("second", "value");
        OrFilter filter = (OrFilter) AddressFilterFactory.orFilter(first, second);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilters().get(0)).getName());
    }

    /**
     * <p>
     * Tests AddressFilterFactory#notFilter(Filter) for accuracy.
     * </p>
     */
    public void testNotFilter() {
        Filter first = new EqualToFilter("first", "value");
        NotFilter filter = (NotFilter) AddressFilterFactory.notFilter(first);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilter()).getName());
    }

}