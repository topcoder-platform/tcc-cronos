/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.failuretests.implement;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.ContestsServiceManagerException;
import com.topcoder.web.tc.SearchContestsManager;
import com.topcoder.web.tc.dto.ContestsFilter;

/**
 * <p>
 * Failure test for SearchContestsManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/failure.xml" })
public class SearchContestsManagerImplFailureTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of SearchContestsManager used in test.
     */
    private SearchContestsManager manager;

    /**
     * Set up for each test.
     */
    @Before
    public void setUp() throws Exception {
        manager = (SearchContestsManager) applicationContext.getBean("searchContestsManager");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests1_pageNumberIsInvalid1() throws Exception {
        manager.searchContests(null, null, 0, 10, "TESTER");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests1_pageNumberIsInvalid2() throws Exception {
        manager.searchContests(null, null, -2, 10, "TESTER");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests1_pageSizeIsInvalid1() throws Exception {
        manager.searchContests(null, null, 1, 0, "TESTER");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests1_pageSizeIsInvalid2() throws Exception {
        manager.searchContests(null, null, 1, -2, "TESTER");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When name is null.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests1_NameIsNull() throws Exception {
        manager.searchContests(null, null, 1, 10, (String) null);
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When name is empty.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests1_NameIsEmpty() throws Exception {
        manager.searchContests(null, null, 1, 10, "  ");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When the connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testsearchContests1_ConnectionIsInvalid() throws Exception {
        manager.searchContests(null, null, 1, 10, "TESTER");
    }

    /**
     * <p>
     * Failure test for searchContests(String name). When name is null.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests2_NameIsNull() throws Exception {
        manager.searchContests((String) null);
    }

    /**
     * <p>
     * Failure test for searchContests(String name). When name is empty.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests2_NameIsEmpty() throws Exception {
        manager.searchContests(" ");
    }

    /**
     * <p>
     * Failure test for searchContests(String name). When the connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testsearchContests2_ConnectionIsInvalid() throws Exception {
        manager.searchContests("TESTER");
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests3_pageNumberIsInvalid1() throws Exception {
        manager.searchContests(null, null, 0, 10, new ContestsFilter());
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). When pageNumber is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests3_pageNumberIsInvalid2() throws Exception {
        manager.searchContests(null, null, -2, 10, new ContestsFilter());
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests3_pageSizeIsInvalid1() throws Exception {
        manager.searchContests(null, null, 1, 0, new ContestsFilter());
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). When pageSize is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests3_pageSizeIsInvalid2() throws Exception {
        manager.searchContests(null, null, 1, -2, new ContestsFilter());
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). When filter is null.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests3_FilterIsNull() throws Exception {
        manager.searchContests(null, null, 1, 10, (ContestsFilter) null);
    }

    /**
     * <p>
     * Failure test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). When connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testsearchContests3_ConnectionIsInvalid() throws Exception {
        manager.searchContests(null, null, 1, 10, new ContestsFilter());
    }

    /**
     * <p>
     * Failure test for searchContests(ContestsFilter filter). When filter is null.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testsearchContests4_FilterIsNull() throws Exception {
        manager.searchContests((ContestsFilter) null);
    }

    /**
     * <p>
     * Failure test for searchContests(ContestsFilter filter). When connection is invalid.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Test(expected = ContestsServiceManagerException.class)
    public void testsearchContests4_ConnectionIsInvalid() throws Exception {
        manager.searchContests(new ContestsFilter());
    }
}