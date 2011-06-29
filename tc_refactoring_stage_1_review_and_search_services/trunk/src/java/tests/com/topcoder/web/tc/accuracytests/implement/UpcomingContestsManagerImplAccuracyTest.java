/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.accuracytests.implement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.SortingOrder;
import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.dto.UpcomingContestDTO;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;
import com.topcoder.web.tc.implement.AbstractManagerImpl;
import com.topcoder.web.tc.implement.UpcomingContestsManagerImpl;

/**
 * <p>
 * Accuracy test for UpcomingContestsManagerImpl class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class UpcomingContestsManagerImplAccuracyTest extends AbstractJUnit4SpringContextTests {
    /**
     * Represents the instance of UpcomingContestsManager used in test.
     */
    private UpcomingContestsManager manager;

    /**
     * <p>
     * Sets up for each test.
     * </p>
     */
    @Before
    public void setUp() {
        manager = (UpcomingContestsManager) applicationContext.getBean("upcomingContestsManager");
    }

    /**
     * Accuracy test for UpcomingContestsManagerImpl(). The instance should be created.
     */
    @Test
    public void testCtor() {
        UpcomingContestsManagerImpl impl = new UpcomingContestsManagerImpl();
        assertNotNull("The instance should be created.", impl);
        assertEquals("UpcomingContestsManagerImpl should extends AbstractManagerImpl.",
            impl.getClass().getSuperclass(), AbstractManagerImpl.class);
        assertTrue("UpcomingContestsManagerImpl should implements UpcomingContestsManager.",
            impl instanceof AbstractManagerImpl);
    }

    /**
     * Accuracy test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). All matched records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_Empty() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(null, null, 100, 10, filter);
        assertEquals("When no record matched, should return null.", 0, result.size());
    }

    /**
     * Accuracy test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). All matched records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_AllRecords() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(null, null, -1, -1, filter);
        assertEquals("When no record matched, should return null.", 4, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
        assertEquals("retrieveUpcomingContests is incorrect.", 1, dto1.getDuration());
        assertEquals("retrieveUpcomingContests is incorrect.", 800.0, dto1.getFirstPrize());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-23 12:00:00.0", dto1.getRegisterDate()
            .toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Draft", dto1.getStatus());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-24 18:00:00.0", dto1.getSubmitDate().toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "System Assembly", dto1.getSubtype());
        assertEquals("retrieveUpcomingContests is incorrect.", "J2EE", dto1.getTechnologies());
        assertEquals("retrieveUpcomingContests is incorrect.", "Assembly", dto1.getType());

        dto1 = result.get(1);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 3 1.0", dto1.getContestName());
        dto1 = result.get(2);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 2 1.0", dto1.getContestName());
        dto1 = result.get(3);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 1 1.0", dto1.getContestName());
    }

    /**
     * Accuracy test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). All matched records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_Sort() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests("firstPrize", SortingOrder.DESCENDING, -1,
            -1, filter);
        assertEquals("When no record matched, should return null.", 4, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 2 1.0", dto1.getContestName());
        assertEquals("retrieveUpcomingContests is incorrect.", 1, dto1.getDuration());
        assertEquals("retrieveUpcomingContests is incorrect.", 1000.0, dto1.getFirstPrize());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-21 12:00:00.0", dto1.getRegisterDate()
            .toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Scheduled", dto1.getStatus());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-22 18:00:00.0", dto1.getSubmitDate().toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Component Design", dto1.getSubtype());
        assertEquals("retrieveUpcomingContests is incorrect.", "J2EE", dto1.getTechnologies());
        assertEquals("retrieveUpcomingContests is incorrect.", "Component Design", dto1.getType());

        dto1 = result.get(1);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
        dto1 = result.get(2);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 3 1.0", dto1.getContestName());
        dto1 = result.get(3);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 1 1.0", dto1.getContestName());
    }

    /**
     * Accuracy test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). All matched records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_Page1() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests("firstPrize", SortingOrder.DESCENDING, 1, 3,
            filter);
        assertEquals("When no record matched, should return null.", 3, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 2 1.0", dto1.getContestName());
        assertEquals("retrieveUpcomingContests is incorrect.", 1, dto1.getDuration());
        assertEquals("retrieveUpcomingContests is incorrect.", 1000.0, dto1.getFirstPrize());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-21 12:00:00.0", dto1.getRegisterDate()
            .toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Scheduled", dto1.getStatus());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-22 18:00:00.0", dto1.getSubmitDate().toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Component Design", dto1.getSubtype());
        assertEquals("retrieveUpcomingContests is incorrect.", "J2EE", dto1.getTechnologies());
        assertEquals("retrieveUpcomingContests is incorrect.", "Component Design", dto1.getType());

        dto1 = result.get(1);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
        dto1 = result.get(2);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 3 1.0", dto1.getContestName());
    }

    /**
     * Accuracy test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). All matched records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_Page2() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests("firstPrize", SortingOrder.DESCENDING, 2, 3,
            filter);
        assertEquals("When no record matched, should return null.", 1, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 1 1.0", dto1.getContestName());
    }

    /**
     * Accuracy test for retrieveUpcomingContests(String columnName, SortingOrder sortingOrder, int pageNumber, int
     * pageSize, UpcomingContestsFilter filter). All matched records should be returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests1_Filter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        filter.setContestName("4 1");
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests("firstPrize", SortingOrder.DESCENDING, 1, 3,
            filter);
        assertEquals("When no record matched, should return null.", 1, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
    }

    /**
     * Accuracy test for retrieveUpcomingContests(UpcomingContestsFilter filter). All matched records should be
     * returned.
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests2_NoFilter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(filter);
        assertEquals("When no record matched, should return null.", 4, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
        assertEquals("retrieveUpcomingContests is incorrect.", 1, dto1.getDuration());
        assertEquals("retrieveUpcomingContests is incorrect.", 800.0, dto1.getFirstPrize());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-23 12:00:00.0", dto1.getRegisterDate()
            .toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Draft", dto1.getStatus());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-24 18:00:00.0", dto1.getSubmitDate().toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "System Assembly", dto1.getSubtype());
        assertEquals("retrieveUpcomingContests is incorrect.", "J2EE", dto1.getTechnologies());
        assertEquals("retrieveUpcomingContests is incorrect.", "Assembly", dto1.getType());

        dto1 = result.get(1);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 3 1.0", dto1.getContestName());
        dto1 = result.get(2);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 2 1.0", dto1.getContestName());
        dto1 = result.get(3);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 1 1.0", dto1.getContestName());
    }

    /**
     * <p>
     * Tests the retrieveUpcomingContests(UpcomingContestsFilter) methods. It uses valid arguments but does not perform
     * any filtering.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testRetrieveUpcomingContests2_Filter() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();

        filter.setContestName("project");
        List<String> subtype = new ArrayList<String>();
        subtype.add("System Assembly");
        subtype.add("Conceptualization");
        filter.setSubtype(subtype);

        List<String> type = new ArrayList<String>();
        type.add("Assembly");
        filter.setType(type);

        List<String> catalog = new ArrayList<String>();
        catalog.add("Development");
        catalog.add("UI Development");
        filter.setCatalog(catalog);

        filter.setPrizeStart(400);
        filter.setPrizeEnd(800);

        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(filter);
        assertEquals("When no record matched, should return null.", 1, result.size());

        UpcomingContestDTO dto1 = result.get(0);
        assertEquals("retrieveUpcomingContests is incorrect.", "test project 4 1.1", dto1.getContestName());
        assertEquals("retrieveUpcomingContests is incorrect.", 1, dto1.getDuration());
        assertEquals("retrieveUpcomingContests is incorrect.", 800.0, dto1.getFirstPrize());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-23 12:00:00.0", dto1.getRegisterDate()
            .toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "Draft", dto1.getStatus());
        assertEquals("retrieveUpcomingContests is incorrect.", "2011-08-24 18:00:00.0", dto1.getSubmitDate().toString());
        assertEquals("retrieveUpcomingContests is incorrect.", "System Assembly", dto1.getSubtype());
        assertEquals("retrieveUpcomingContests is incorrect.", "J2EE", dto1.getTechnologies());
        assertEquals("retrieveUpcomingContests is incorrect.", "Assembly", dto1.getType());
    }
}