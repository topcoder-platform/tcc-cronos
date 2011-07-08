/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.implement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.SearchContestsManager;
import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.dto.ContestDTO;
import com.topcoder.web.tc.dto.ContestsFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.implement.AbstractManagerImpl;
import com.topcoder.web.tc.implement.SearchContestsManagerImpl;

/**
 * <p>
 * Accuracy test for SearchContestsManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SearchContestsManagerImplAccuracyTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of UpcomingContestsManager used in test.
     */
    private SearchContestsManager manager;

    /**
     * <p>
     * Sets up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        manager = (SearchContestsManager) applicationContext.getBean("searchContestsManager");
    }

    /**
     * Accuracy test for SearchContestsManagerImpl(). The instance should be created.
     */
    @Test
    public void testCtor() {
        SearchContestsManagerImpl impl = new SearchContestsManagerImpl();
        assertNotNull("The instance should be created.", impl);
        assertEquals("SearchContestsManagerImpl should extends AbstractManagerImpl.", impl.getClass().getSuperclass(),
            AbstractManagerImpl.class);
        assertTrue("UpcomingContestsManagerImpl should implements SearchContestsManager.",
            impl instanceof SearchContestsManager);
    }

    /**
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). When no record matched, return empty list.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests1_Empty() throws Exception {
        List<ContestDTO> result = manager.searchContests(null, null, 1000, 10, "abc");
        assertEquals("no project should be returned", 0, result.size());
    }

    /**
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). All records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests1() throws Exception {
        List<ContestDTO> result = manager.searchContests("contestName", SortingOrder.DESCENDING, -1, -1, "%%");
        assertEquals("no project should be returned", 5, result.size());

        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }

    /**
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). All records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests1_Page1() throws Exception {
        List<ContestDTO> result = manager.searchContests("contestName", SortingOrder.ASCENDING, 1, 3, "%%");
        assertEquals("no project should be returned", 3, result.size());
    }

    /**
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). All records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests1_Page2() throws Exception {
        List<ContestDTO> result = manager.searchContests("contestName", SortingOrder.ASCENDING, 2, 3, "%%");
        assertEquals("no project should be returned", 2, result.size());
    }

    /**
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * String name). All records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests1_Filter() throws Exception {
        List<ContestDTO> result = manager.searchContests("contestName", SortingOrder.ASCENDING, 1, 100, "test project");
        assertEquals("no project should be returned", 5, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(String name). The matched records should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests2_Empty() throws Exception {
        List<ContestDTO> result = manager.searchContests("project 555");
        assertEquals("The searchContests is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(String name). The matched records should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests2_One() throws Exception {
        List<ContestDTO> result = manager.searchContests("project 5");
        assertEquals("1 project should be returned", 1, result.size());
        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Accuracy test for searchContests(String name). The matched records should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests2_Some() throws Exception {
        List<ContestDTO> result = manager.searchContests("project");
        assertEquals("1 project should be returned", 5, result.size());
        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());

        assertEquals("The searchContests is incorrect.", "test project 4", result.get(1).getContestName());
        assertEquals("The searchContests is incorrect.", "test project 3", result.get(2).getContestName());
        assertEquals("The searchContests is incorrect.", "test project 2", result.get(3).getContestName());
        assertEquals("The searchContests is incorrect.", "test project 1", result.get(4).getContestName());
    }

    /**
     * <p>
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). All matched result should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests3_NoFilter() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests(null, null, -1, -1, filter);

        assertEquals("The searchContests is incorrect.", 5, result.size());
        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). All matched result should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests3_Page1() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests("contestName", SortingOrder.ASCENDING, 1, 2, filter);

        assertEquals("The searchContests is incorrect.", 2, result.size());
        assertEquals("The searchContests is incorrect.", "test project 1", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Component Design", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Component Design", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 1",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 100.0, result.get(0).getWinnerScore());

        assertEquals("The searchContests is incorrect.", "test project 2", result.get(1).getContestName());
    }

    /**
     * <p>
     * Accuracy test for searchContests(String columnName, SortingOrder sortingOrder, int pageNumber, int pageSize,
     * ContestsFilter filter). All matched result should be returned.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests3_Page2() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests("contestName", SortingOrder.ASCENDING, 3, 2, filter);

        assertEquals("The searchContests is incorrect.", 1, result.size());
        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter is empty.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_FilterIsEmpty() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 5, result.size());
        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());

        assertEquals("The searchContests is incorrect.", "test project 4", result.get(1).getContestName());
        assertEquals("The searchContests is incorrect.", "test project 3", result.get(2).getContestName());
        assertEquals("The searchContests is incorrect.", "test project 2", result.get(3).getContestName());
        assertEquals("The searchContests is incorrect.", "test project 1", result.get(4).getContestName());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using content name.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestName1() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        filter.setContestName("project 5");
        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 1, result.size());
        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using content name.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestName2() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        filter.setContestName("project 56");
        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using before type for
     * ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationBefore1() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-24"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using before type for
     * ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationBefore2() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-08-31"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 5, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using after type for
     * ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationAfter1() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-05-31"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 5, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using after type for
     * ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationAfter2() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-08-31"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using on type for ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationOn() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.ON);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-08-31"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using BEFORE_CURRENT_DATE type for
     * ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationBeforeCurrent() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 0, result.size());
    }

    /**
     * <p>
     * Accuracy test for searchContests(ContestsFilter filter). When the filter using AFTER_CURRENT_DATE type for
     * ContestFinalization.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationAfterCurrent() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 5, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the BETWEEN_DATES criteria for contest finalization end
     * date.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalizationBetween() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        date.setFirstDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-08-01"));
        date.setSecondDate(new SimpleDateFormat("yyyy-MM-dd").parse("2011-08-31"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 5, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It would use some filter.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_Filter() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        filter.setContestName("test");

        List<String> subType = new ArrayList<String>();
        subType.add("System Assembly");
        subType.add("Conceptualization");
        filter.setSubtype(subType);

        List<String> type = new ArrayList<String>();
        type.add("Component Design");
        type.add("Conceptualization");
        filter.setType(type);

        List<String> catalog = new ArrayList<String>();
        catalog.add("Design");
        catalog.add("UI Development");
        filter.setCatalog(catalog);

        filter.setWinnerHandle("winner");

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("The searchContests is incorrect.", 1, result.size());

        assertEquals("The searchContests is incorrect.", "test project 5", result.get(0).getContestName());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getType());
        assertEquals("The searchContests is incorrect.", "Conceptualization", result.get(0).getSubtype());
        assertEquals("The searchContests is incorrect.", "Design", result.get(0).getCatalog());
        assertEquals("The searchContests is incorrect.", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("The searchContests is incorrect.", 1, result.get(0).getPassedScreeningCount());
        assertEquals("The searchContests is incorrect.", "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5",
            result.get(0).getWinnerProfileLink());
        assertEquals("The searchContests is incorrect.", 80.0, result.get(0).getWinnerScore());
    }
}
