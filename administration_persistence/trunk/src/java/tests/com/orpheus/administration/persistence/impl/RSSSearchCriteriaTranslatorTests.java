/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.orpheus.administration.persistence.impl;

import com.orpheus.administration.persistence.SearchCriteriaDTO;

import com.topcoder.search.builder.filter.AbstractAssociativeFilter;
import com.topcoder.search.builder.filter.AbstractSimpleFilter;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.BetweenFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanFilter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;
import com.topcoder.search.builder.filter.LikeFilter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.OrFilter;

import com.topcoder.util.rssgenerator.SearchCriteria;

import com.topcoder.util.rssgenerator.datastore.SearchCriteriaImpl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * Unit tests for the <code>RSSSearchCriteriaTranslator</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */

public class RSSSearchCriteriaTranslatorTests extends TestCase {
    /**
     * The translator to use for the tests.
     */
    private static final RSSSearchCriteriaTranslator TRANSLATOR = new RSSSearchCriteriaTranslator();

    /**
     * Tests that <code>assembleSearchCriteriaDTO</code> throws <code>IllegalArgumentException</code> when passed a
     * <code>null</code> search criteria.
     */
    public void test_assembleSearchCriteriaDTO_null_arg1() {
        try {
            TRANSLATOR.assembleSearchCriteriaDTO(null);
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // pass
        }
    }

    /**
     * Tests the conversion of <i>and</i> filters.
     */
    public void test_convert_and_filter() {
        AndFilter filter = new AndFilter(new EqualToFilter("test", "test"), new EqualToFilter("test", "test"));
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        AndFilter newFilter = (AndFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>between</i> filters.
     */
    public void test_convert_between_filter() {
        BetweenFilter filter = new BetweenFilter("test", "upper", "lower");
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        BetweenFilter newFilter = (BetweenFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>equalTo</i> filters.
     */
    public void test_convert_equalTo_filter() {
        EqualToFilter filter = new EqualToFilter("test", "value");
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        EqualToFilter newFilter = (EqualToFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>greaterThan</i> filters.
     */
    public void test_convert_greaterThan_filter() {
        GreaterThanFilter filter = new GreaterThanFilter("test", "value");
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        GreaterThanFilter newFilter =
            (GreaterThanFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>greaterThanOrEqualTo</i> filters.
     */
    public void test_convert_greaterThanOrEqualTo_filter() {
        GreaterThanOrEqualToFilter filter = new GreaterThanOrEqualToFilter("test", "value");
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        GreaterThanOrEqualToFilter newFilter =
            (GreaterThanOrEqualToFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>in</i> filters.
     */
    public void test_convert_in_filter() {
        List values = new ArrayList();
        values.add("one");
        values.add(new Integer(2));
        values.add(new Double(3));

        InFilter filter = new InFilter("test", values);
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        InFilter newFilter = (InFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());

        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>lessThan</i> filters.
     */
    public void test_convert_lessThan_filter() {
        LessThanFilter filter = new LessThanFilter("test", "value");
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        LessThanFilter newFilter = (LessThanFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>lessThanOrEqualTo</i> filters.
     */
    public void test_convert_lessThanOrEqualTo_filter() {
        LessThanOrEqualToFilter filter = new LessThanOrEqualToFilter("test", "value");
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        LessThanOrEqualToFilter newFilter =
            (LessThanOrEqualToFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>like</i> filters.
     */
    public void test_convert_like_filter() {
        LikeFilter filter = new LikeFilter("test", "EW:value", 'b');
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        LikeFilter newFilter = (LikeFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>not</i> filters.
     */
    public void test_convert_not_filter() {
        NotFilter filter = new NotFilter(new EqualToFilter("name", "value"));
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        NotFilter newFilter = (NotFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Tests the conversion of <i>or</i> filters.
     */
    public void test_convert_or_filter() {
        OrFilter filter = new OrFilter(new EqualToFilter("test", "test"), new EqualToFilter("test", "test"));
        SearchCriteria criteria = new SearchCriteriaImpl(filter);
        SearchCriteriaDTO dto = TRANSLATOR.assembleSearchCriteriaDTO(criteria);
        OrFilter newFilter = (OrFilter) RSSSearchCriteriaTranslator.convertFilter(dto.getSearchFilter());
        compareFilters(filter, newFilter);
    }

    /**
     * Compares two <code>AbstractAssociativeFilter</code> instances.
     *
     * @param expected the expected value
     * @param returned the actual value
     * @throws junit.framework.AssertionFailedError if the two filters are not equal
     */
    private static void compareFilters(AbstractAssociativeFilter expected, AbstractAssociativeFilter returned) {
        int size = expected.getFilters().size();
        assertEquals("the converted filter should equal the original filter", size, returned.getFilters().size());

        for (int idx = 0; idx < size; ++idx) {
            compareFilters((Filter) expected.getFilters().get(idx), (Filter) returned.getFilters().get(idx));
        }
    }

    /**
     * Compares two <code>Filter</code> instances.
     *
     * @param expected the expected value
     * @param returned the actual value
     * @throws junit.framework.AssertionFailedError if the two filters are not equal
     */
    private static void compareFilters(Filter expected, Filter returned) {
        if (expected instanceof AbstractAssociativeFilter) {
            compareFilters((AbstractAssociativeFilter) expected, (AbstractAssociativeFilter) returned);
        } else if (expected instanceof AbstractSimpleFilter) {
            compareFilters((AbstractSimpleFilter) expected, (AbstractSimpleFilter) returned);
        } else {
            fail("unknown filter type");
        }
    }

    /**
     * Compares two <code>AbstractSimpleFilter</code> instances.
     *
     * @param expected the expected value
     * @param returned the actual value
     * @throws junit.framework.AssertionFailedError if the two filters are not equal
     */
    private static void compareFilters(AbstractSimpleFilter expected, AbstractSimpleFilter returned) {
        assertEquals("the converted filter should equal the original", expected.getName(), returned.getName());
        assertEquals("the converted filter should equal the original", expected.getValue(), returned.getValue());
        assertEquals("the converted filter should equal the original",
                     expected.getUpperThreshold(), returned.getUpperThreshold());
        assertEquals("the converted filter should equal the original",
                     expected.getLowerThreshold(), returned.getLowerThreshold());
        assertEquals("the converted filter should equal the original",
                     expected.isUpperInclusive(), returned.isUpperInclusive());
        assertEquals("the converted filter should equal the original",
                     expected.isLowerInclusive(), returned.isLowerInclusive());
    }

    /**
     * Compares two <code>InFilter</code> instances.
     *
     * @param expected the expected value
     * @param returned the actual value
     * @throws junit.framework.AssertionFailedError if the two filters are not equal
     */
    private static void compareFilters(InFilter expected, InFilter returned) {
        assertEquals("the converted filter should equal the original", expected.getName(), returned.getName());
        assertEquals("the converted filter should equal the original", expected.getList(), returned.getList());
    }

    /**
     * Compares two <code>LikeFilter</code> likestances.
     *
     * @param expected the expected value
     * @param returned the actual value
     * @throws junit.framework.AssertionFailedError if the two filters are not equal
     */
    private static void compareFilters(LikeFilter expected, LikeFilter returned) {
        assertEquals("the converted filter should equal the original", expected.getName(), returned.getName());
        assertEquals("the converted filter should equal the original", expected.getValue(), returned.getValue());
        assertEquals("the converted filter should equal the original",
                     expected.getEscapeCharacter(), returned.getEscapeCharacter());
    }

    /**
     * Compares two <code>NotFilter</code> notstances.
     *
     * @param expected the expected value
     * @param returned the actual value
     * @throws junit.framework.AssertionFailedError if the two filters are not equal
     */
    private static void compareFilters(NotFilter expected, NotFilter returned) {
        compareFilters(expected.getFilter(), returned.getFilter());
    }
}
