/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.client.accuracytests;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.timetracker.client.ClientFilterFactory;

/**
 * <p>
 * Accuracy Unit test cases for ClientFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ClientFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ClientFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createCompanyIdFilter(long) for accuracy.
     * </p>
     */
    public void testCreateCompanyIdFilter() {
        EqualToFilter filter = (EqualToFilter) ClientFilterFactory.createCompanyIdFilter(8);
        assertEquals("Failed to create the filter.", "COMPANY_ID", filter.getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createNameKeywordFilter(String) for accuracy.
     * </p>
     */
    public void testCreateNameKeywordFilter() {
        LikeFilter filter = (LikeFilter) ClientFilterFactory.createNameKeywordFilter("keyword");
        assertEquals("Failed to create the filter.", "NAME", filter.getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createCreatedInFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) ClientFilterFactory.createCreatedInFilter(
            new Date(), null);
        assertEquals("Failed to create the filter.", "CREATION_DATE", filter.getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createModifiedInFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateModifiedInFilter() {
        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) ClientFilterFactory.createModifiedInFilter(
            new Date(), null);
        assertEquals("Failed to create the filter.", "MODIFICATION_DATE", filter.getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createCreatedByFilter(String) for accuracy.
     * </p>
     */
    public void testCreateCreatedByFilter() {
        EqualToFilter filter = (EqualToFilter) ClientFilterFactory.createCreatedByFilter("userName");
        assertEquals("Failed to create the filter.", "CREATION_USER", filter.getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createModifiedByFilter(String) for accuracy.
     * </p>
     */
    public void testCreateModifiedByFilter() {
        EqualToFilter filter = (EqualToFilter) ClientFilterFactory.createModifiedByFilter("userName");
        assertEquals("Failed to create the filter.", "MODIFICATION_USER", filter.getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#andFilter(Filter,Filter) for accuracy.
     * </p>
     */
    public void testAndFilter() {
        Filter first = new EqualToFilter("first", "value");
        Filter second = new EqualToFilter("second", "value");
        AndFilter filter = (AndFilter) ClientFilterFactory.andFilter(first, second);
        assertEquals("Failed to create the filter.", 2, filter.getFilters().size());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#orFilter(Filter,Filter) for accuracy.
     * </p>
     */
    public void testOrFilter() {
        Filter first = new EqualToFilter("first", "value");
        Filter second = new EqualToFilter("second", "value");
        OrFilter filter = (OrFilter) ClientFilterFactory.orFilter(first, second);
        assertEquals("Failed to create the filter.", 2, filter.getFilters().size());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#notFilter(Filter) for accuracy.
     * </p>
     */
    public void testNotFilter() {
        Filter first = new EqualToFilter("first", "value");
        NotFilter filter = (NotFilter) ClientFilterFactory.notFilter(first);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilter()).getName());
    }

    /**
     * <p>
     * Tests ClientFilterFactory#createActiveFilter(boolean) for accuracy.
     * </p>
     */
    public void testCreateActiveFilter() {
        EqualToFilter filter = (EqualToFilter) ClientFilterFactory.createActiveFilter(true);
        assertEquals("Failed to create the filter.", "ACTIVE", filter.getName());
    }

}