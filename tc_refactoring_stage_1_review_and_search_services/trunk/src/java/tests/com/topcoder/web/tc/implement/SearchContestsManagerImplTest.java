/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.implement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
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

/**
 * <p>
 * Tests the SearchContestsManagerImpl class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SearchContestsManagerImplTest extends AbstractJUnit4SpringContextTests {

    /**
     * Represents an instance of SearchContestsManager for test.
     */
    private SearchContestsManager manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        manager = (SearchContestsManager) applicationContext.getBean("searchContestsManager");
    }

    /**
     * <p>
     * Tears down testing environment. Sets variable to null.
     * </p>
     */
    @After
    public void tearDown() {
        manager = null;
    }

    /**
     * <p>
     * Tests that SearchContestsManagerImpl is extended from AbstractManagerImpl.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("SearchContestsManagerImpl should extends from AbstractManagerImpl",
                manager instanceof AbstractManagerImpl);
    }

    /**
     * <p>
     * Tests that SearchContestsManagerImpl is extended from SearchContestsManager.
     * </p>
     */
    @Test
    public void testImplements() {
        assertTrue("SearchContestsManagerImpl should implement SearchContestsManager",
                manager instanceof SearchContestsManager);
    }

    /**
     * <p>
     * Tests the default constructor. Since the constructor does nothing, this test case does not verify anything.
     * </p>
     */
    @Test
    public void testCtor() {
        new SearchContestsManagerImpl();
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses negative page number (other
     * than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests1_InvalidPageNumber_Negative() throws Exception {
        manager.searchContests(null, null, -3, 10, "contest");
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses zero page number.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests1_InvalidPageNumber_Zero() throws Exception {
        manager.searchContests(null, null, 0, 10, "contest");
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses negative page size (other than
     * -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests1_InvalidPageSize_Negative() throws Exception {
        manager.searchContests(null, null, 1, -5, "contest");
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses zero page size.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests1_InvalidPageSize_Zero() throws Exception {
        manager.searchContests(null, null, 1, 0, "contest");
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses null name.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests1_InvalidName_Null() throws Exception {
        manager.searchContests(null, null, 1, 10, (String) null);
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses empty name.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests1_InvalidName_Empty() throws Exception {
        manager.searchContests(null, null, 1, 10, " ");
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, String) methods. It uses valid arguments but does not
     * perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests1() throws Exception {
        List<ContestDTO> result = manager.searchContests(null, null, -1, -1, "abc");
        assertEquals("no project should be returned", 0, result.size());

        result = manager.searchContests("contestName", SortingOrder.ASCENDING, 1, 2, "test");
        assertEquals("2 projects should be returned", 2, result.size());
        assertEquals("contest name should be returned", "test project 1", result.get(0).getContestName());
        assertEquals("type should be returned", "Component Design", result.get(0).getType());
        assertEquals("subtype should be returned", "Component Design", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Design", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 0, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 0, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 1", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 100.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Tests the searchContests(String) methods. It uses null name. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests2_InvalidName_Null() throws Exception {
        manager.searchContests((String) null);
    }

    /**
     * <p>
     * Tests the searchContests(String) methods. It uses empty name. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests2_InvalidName_Empty() throws Exception {
        manager.searchContests(" ");
    }

    /**
     * <p>
     * Tests the searchContests(String) methods. It uses valid argument.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests2() throws Exception {
        List<ContestDTO> result = manager.searchContests("test project 3");
        assertEquals("1 project should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test project 3", result.get(0).getContestName());
        assertEquals("type should be returned", "Component Development", result.get(0).getType());
        assertEquals("subtype should be returned", "Component Development", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Development", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 2, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 1, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 3", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 90.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestsFilter) methods. It uses negative page number
     * (other than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests3_InvalidPageNumber_Negative() throws Exception {
        manager.searchContests(null, null, -3, 10, new ContestsFilter());
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestsFilter) methods. It uses zero page number.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests3_InvalidPageNumber_Zero() throws Exception {
        manager.searchContests(null, null, 0, 10, new ContestsFilter());
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestsFilter) methods. It uses negative page size
     * (other than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests3_InvalidPageSize_Negative() throws Exception {
        manager.searchContests(null, null, 1, -5, new ContestsFilter());
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestsFilter) methods. It uses zero page size.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests3_InvalidPageSize_Zero() throws Exception {
        manager.searchContests(null, null, 1, 0, new ContestsFilter());
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestsFilter) methods. It uses null filter.
     * IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests3_InvalidFilter_Null() throws Exception {
        manager.searchContests(null, null, 1, 10, (ContestsFilter) null);
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestFilter) methods. It uses valid arguments but does
     * not perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests3_NoFilter() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests(null, null, -1, -1, filter);
        assertEquals("all projects should be returned", 5, result.size());
        assertEquals("contest name should be returned", "test project 5", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Design", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 1, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 80.0, result.get(0).getWinnerScore());

        result = manager.searchContests("contestName", SortingOrder.ASCENDING, 1, 2, filter);
        assertEquals("2 projects should be returned", 2, result.size());
        assertEquals("contest name should be returned", "test project 1", result.get(0).getContestName());
        assertEquals("type should be returned", "Component Design", result.get(0).getType());
        assertEquals("subtype should be returned", "Component Design", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Design", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 0, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 0, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 1", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 100.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Tests the searchContests(String, SortingOrder, int, int, ContestFilter) methods. It uses valid arguments and uses
     * filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests3_Filter() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        filter.setContestName("test");
        List<String> list = new ArrayList<String>();
        list.add("Component Design");
        list.add("System Assembly");
        list.add("Conceptualization");
        filter.setSubtype(list);
        list = new ArrayList<String>();
        list.add("Component Design");
        list.add("Assembly");
        filter.setType(list);
        list = new ArrayList<String>();
        list.add("Design");
        list.add("UI Development");
        filter.setCatalog(list);
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110801"));
        date.setSecondDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setContestFinalization(date);
        filter.setWinnerHandle("winner");

        List<ContestDTO> result = manager.searchContests(null, null, -1, -1, filter);
        assertEquals("2 projects should be returned", 2, result.size());
        assertEquals("contest name should be returned", "test project 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Component Design", result.get(0).getType());
        assertEquals("subtype should be returned", "Component Design", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Design", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 2, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 2, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 2, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 2", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 95.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Tests the searchContests(ContestsFilter) methods. It uses null filter. IllegalArgumentException is expected to be
     * thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchContests4_InvalidFilter_Null() throws Exception {
        manager.searchContests((ContestsFilter) null);
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It uses valid arguments but does not perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_NoFilter() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("all projects should be returned", 5, result.size());
        assertEquals("contest name should be returned", "test project 5", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Design", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 0, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 1, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 1, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 5", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 80.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It uses valid arguments and uses filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_Filter() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        filter.setContestName("test");
        List<String> list = new ArrayList<String>();
        list.add("Component Design");
        list.add("System Assembly");
        list.add("Conceptualization");
        filter.setSubtype(list);
        list = new ArrayList<String>();
        list.add("Component Design");
        list.add("Assembly");
        filter.setType(list);
        list = new ArrayList<String>();
        list.add("Design");
        list.add("UI Development");
        filter.setCatalog(list);
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110801"));
        date.setSecondDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setContestFinalization(date);
        filter.setWinnerHandle("winner");

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("2 projects should be returned", 2, result.size());
        assertEquals("contest name should be returned", "test project 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Component Design", result.get(0).getType());
        assertEquals("subtype should be returned", "Component Design", result.get(0).getSubtype());
        assertEquals("catalog should be returned", "Design", result.get(0).getCatalog());
        assertEquals("number of registrants should be returned", 2, result.get(0).getNumberOfRegistrants());
        assertEquals("number of submissions should be returned", 2, result.get(0).getNumberOfSubmissions());
        assertEquals("passed screening count should be returned", 2, result.get(0).getPassedScreeningCount());
        assertEquals("winner profile link should be returned",
                "http://www.topcoder.com/tc?module=MemberProfile&cr=winner 2", result.get(0).getWinnerProfileLink());
        assertEquals("winner score should be returned", 95.0, result.get(0).getWinnerScore());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the BEFORE criteria for contest finalization end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalization_Before() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("5 projects should be returned", 5, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the AFTER criteria for contest finalization end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalization_After() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110801"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("5 projects should be returned", 5, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the ON criteria for contest finalization end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalization_On() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.ON);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110826"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("0 project should be returned", 0, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the BEFORE_CURRENT_DATE criteria for contest
     * finalization end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalization_BeforeCurrent() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("0 project should be returned", 0, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the AFTER_CURRENT_DATE criteria for contest
     * finalization end date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalization_AfterCurrent() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("5 projects should be returned", 5, result.size());
    }

    /**
     * <p>
     * Tests the searchContests(ContestFilter) methods. It tests the BETWEEN_DATES criteria for contest finalization end
     * date.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSearchContests4_ContestFinalization_Between() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BETWEEN_DATES);
        date.setFirstDate(new SimpleDateFormat("yyyyMMdd").parse("20110801"));
        date.setSecondDate(new SimpleDateFormat("yyyyMMdd").parse("20110831"));
        filter.setContestFinalization(date);

        List<ContestDTO> result = manager.searchContests(filter);
        assertEquals("5 projects should be returned", 5, result.size());
    }
}
