/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.implement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.UpcomingContestDTO;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;

/**
 * <p>
 * Tests the UpcomingContestsManagerImpl class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UpcomingContestsManagerImplTest extends AbstractJUnit4SpringContextTests {

    /**
     * Represents an instance of UpcomingContestsManager for test.
     */
    private UpcomingContestsManager manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() {
        manager = (UpcomingContestsManager) applicationContext.getBean("upcomingContestsManager");
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
     * Tests that UpcomingContestsManagerImpl is extended from AbstractManagerImpl.
     * </p>
     */
    @Test
    public void testExtends() {
        assertTrue("UpcomingContestsManagerImpl should extends from AbstractManagerImpl",
                manager instanceof AbstractManagerImpl);
    }

    /**
     * <p>
     * Tests that UpcomingContestsManagerImpl is extended from UpcomingContestsManager.
     * </p>
     */
    @Test
    public void testImplements() {
        assertTrue("SearchContestsManagerImpl should implement UpcomingContestsManager",
                manager instanceof UpcomingContestsManager);
    }

    /**
     * <p>
     * Tests the default constructor. Since the constructor does nothing, this test case does not verify anything.
     * </p>
     */
    @Test
    public void testCtor() {
        new UpcomingContestsManagerImpl();
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses
     * negative page number (other than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveUpcomingContests1_InvalidPageNumber_Negative() throws Exception {
        manager.retrieveUpcomingContests(null, null, -3, 10, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses zero
     * page number. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveUpcomingContests1_InvalidPageNumber_Zero() throws Exception {
        manager.retrieveUpcomingContests(null, null, 0, 10, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses
     * negative page size (other than -1). IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveUpcomingContests1_InvalidPageSize_Negative() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, -5, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses zero
     * page size. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveUpcomingContests1_InvalidPageSize_Zero() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, 0, new UpcomingContestsFilter());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses null
     * filter. IllegalArgumentException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveUpcomingContests1_InvalidFilter_Null() throws Exception {
        manager.retrieveUpcomingContests(null, null, 1, 10, null);
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses valid
     * arguments but does not perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_NoFilter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(null, null, 2, 1, filter);
        assertEquals("1 project should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test project 3 1.0", result.get(0).getContestName());
        assertEquals("type should be returned", "Component Development", result.get(0).getType());
        assertEquals("subtype should be returned", "Component Development", result.get(0).getSubtype());
        assertEquals("register date should be returned", "2011-08-22 12:00:00.0", result.get(0).getRegisterDate().toString());
        assertEquals("submit date should be returned", "2011-08-23 18:00:00.0", result.get(0).getSubmitDate().toString());
        assertEquals("duration should be returned", 1, result.get(0).getDuration());
        assertEquals("technologies should be returned", "J2EE", result.get(0).getTechnologies());
        assertEquals("status should be returned", "Completed", result.get(0).getStatus());
        assertEquals("first prize should be returned", 600.0, result.get(0).getFirstPrize());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(String, SortingOrder, int, int, UpcomingContestsFilter) methods. It uses valid
     * arguments but does not perform any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_Filter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
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
        list.add("Development");
        list.add("Design");
        filter.setCatalog(list);
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        filter.setPrizeStart(0);
        filter.setPrizeEnd(800);

        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests("firstPrize", SortingOrder.ASCENDING, 2, 1,
                filter);
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test project 4 1.1", result.get(0).getContestName());
        assertEquals("type should be returned", "Assembly", result.get(0).getType());
        assertEquals("subtype should be returned", "System Assembly", result.get(0).getSubtype());
        assertEquals("register date should be returned", "2011-08-23 12:00:00.0", result.get(0).getRegisterDate().toString());
        assertEquals("submit date should be returned", "2011-08-24 18:00:00.0", result.get(0).getSubmitDate().toString());
        assertEquals("duration should be returned", 1, result.get(0).getDuration());
        assertEquals("technologies should be returned", "J2EE", result.get(0).getTechnologies());
        assertEquals("status should be returned", "Draft", result.get(0).getStatus());
        assertEquals("first prize should be returned", 800.0, result.get(0).getFirstPrize());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(UpcomingContestsFilter) methods. It uses null filter. IllegalArgumentException
     * is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveUpcomingContests2_InvalidFilter_Null() throws Exception {
        manager.retrieveUpcomingContests((UpcomingContestsFilter) null);
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(UpcomingContestsFilter) methods. It uses valid arguments but does not perform
     * any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests2_NoFilter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(filter);
        assertEquals("4 projects should be returned", 4, result.size());
        assertEquals("contest name should be returned", "test project 4 1.1", result.get(0).getContestName());
        assertEquals("type should be returned", "Assembly", result.get(0).getType());
        assertEquals("subtype should be returned", "System Assembly", result.get(0).getSubtype());
        assertEquals("register date should be returned", "2011-08-23 12:00:00.0", result.get(0).getRegisterDate().toString());
        assertEquals("submit date should be returned", "2011-08-24 18:00:00.0", result.get(0).getSubmitDate().toString());
        assertEquals("duration should be returned", 1, result.get(0).getDuration());
        assertEquals("technologies should be returned", "J2EE", result.get(0).getTechnologies());
        assertEquals("status should be returned", "Draft", result.get(0).getStatus());
        assertEquals("first prize should be returned", 800.0, result.get(0).getFirstPrize());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(UpcomingContestsFilter) methods. It uses valid arguments but does not perform
     * any filtering.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests2_Filter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
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
        list.add("Development");
        list.add("UI Development");
        filter.setCatalog(list);
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.AFTER_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        filter.setPrizeStart(600);
        filter.setPrizeEnd(800);

        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(filter);
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test project 4 1.1", result.get(0).getContestName());
        assertEquals("type should be returned", "Assembly", result.get(0).getType());
        assertEquals("subtype should be returned", "System Assembly", result.get(0).getSubtype());
        assertEquals("register date should be returned", "2011-08-23 12:00:00.0", result.get(0).getRegisterDate().toString());
        assertEquals("submit date should be returned", "2011-08-24 18:00:00.0", result.get(0).getSubmitDate().toString());
        assertEquals("duration should be returned", 1, result.get(0).getDuration());
        assertEquals("technologies should be returned", "J2EE", result.get(0).getTechnologies());
        assertEquals("status should be returned", "Draft", result.get(0).getStatus());
        assertEquals("first prize should be returned", 800.0, result.get(0).getFirstPrize());
    }
}
