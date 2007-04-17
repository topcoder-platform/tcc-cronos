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
import com.topcoder.timetracker.contact.ContactFilterFactory;
import com.topcoder.timetracker.contact.ContactType;

/**
 * <p>
 * Accuracy Unit test cases for ContactFilterFactory.
 * </p>
 *
 * @author victorsam
 * @version 3.2
 */
public class ContactFilterFactoryAccuracyTests extends TestCase {
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(ContactFilterFactoryAccuracyTests.class);
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createCreatedInFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateCreatedInFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) ContactFilterFactory.createCreatedInFilter(null,
            new Date());
        assertEquals("Failed to create the filter.", "creation_date", filter.getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createModifiedInFilter(Date,Date) for accuracy.
     * </p>
     */
    public void testCreateModifiedInFilter() {
        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) ContactFilterFactory.createModifiedInFilter(null,
            new Date());
        assertEquals("Failed to create the filter.", "modification_date", filter.getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createCreatedByFilter(String) for accuracy.
     * </p>
     */
    public void testCreateCreatedByFilter() {
        EqualToFilter filter = (EqualToFilter) ContactFilterFactory.createCreatedByFilter("userName");
        assertEquals("Failed to create the filter.", "creation_user", filter.getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createModifiedByFilter(String) for accuracy.
     * </p>
     */
    public void testCreateModifiedByFilter() {
        EqualToFilter filter = (EqualToFilter) ContactFilterFactory.createModifiedByFilter("userName");
        assertEquals("Failed to create the filter.", "modification_user", filter.getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createTypeFilter(ContactType) for accuracy.
     * </p>
     */
    public void testCreateTypeFilter() {
        EqualToFilter filter = (EqualToFilter) ContactFilterFactory.createTypeFilter(ContactType.CLIENT);
        assertEquals("Failed to create the filter.", "contact_type_id", filter.getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createEntityIDFilter(long,ContactType) for accuracy.
     * </p>
     */
    public void testCreateEntityIDFilter() {
        AndFilter filter = (AndFilter) ContactFilterFactory.createEntityIDFilter(8, ContactType.CLIENT);
        assertEquals("Failed to create the filter.", 2, filter.getFilters().size());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#andFilter(Filter,Filter) for accuracy.
     * </p>
     */
    public void testAndFilter() {
        Filter first = new EqualToFilter("first", "value");
        Filter second = new EqualToFilter("second", "value");
        AndFilter filter = (AndFilter) ContactFilterFactory.andFilter(first, second);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilters().get(0)).getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#orFilter(Filter,Filter) for accuracy.
     * </p>
     */
    public void testOrFilter() {
        Filter first = new EqualToFilter("first", "value");
        Filter second = new EqualToFilter("second", "value");
        OrFilter filter = (OrFilter) ContactFilterFactory.orFilter(first, second);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilters().get(0)).getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#notFilter(Filter) for accuracy.
     * </p>
     */
    public void testNotFilter() {
        Filter first = new EqualToFilter("first", "value");
        NotFilter filter = (NotFilter) ContactFilterFactory.notFilter(first);
        assertEquals("Failed to create the filter.", "first", ((EqualToFilter) filter.getFilter()).getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createNameKeywordFilter(String) for accuracy.
     * </p>
     */
    public void testCreateNameKeywordFilter() {
        OrFilter filter = (OrFilter) ContactFilterFactory.createNameKeywordFilter("keyword");
        assertEquals("Failed to create the filter.", "first_name", ((LikeFilter) filter.getFilters().get(0)).getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createEmailAddressFilter(String) for accuracy.
     * </p>
     */
    public void testCreateEmailAddressFilter() {
        EqualToFilter filter = (EqualToFilter) ContactFilterFactory.createEmailAddressFilter("emailAddress");
        assertEquals("Failed to create the filter.", "email", filter.getName());
    }

    /**
     * <p>
     * Tests ContactFilterFactory#createPhoneNumberFilter(String) for accuracy.
     * </p>
     */
    public void testCreatePhoneNumberFilter() {
        EqualToFilter filter = (EqualToFilter) ContactFilterFactory.createPhoneNumberFilter("phoneNumber");
        assertEquals("Failed to create the filter.", "phone", filter.getName());
    }

}