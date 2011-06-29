/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import static junit.framework.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.topcoder.web.tc.dto.ContestDTO;
import com.topcoder.web.tc.dto.ContestsFilter;
import com.topcoder.web.tc.dto.DateIntervalSpecification;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilter;
import com.topcoder.web.tc.dto.ReviewOpportunityDTO;
import com.topcoder.web.tc.dto.UpcomingContestDTO;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;

/**
 * <p>
 * This demonstrates the use of this component.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DemoTest extends AbstractJUnit4SpringContextTests {

    /**
     * <p>
     * Demonstrates the API use of ReviewOpportunitiesManager.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoApi_ReviewOpportunitiesManager() throws Exception {
        // 1. Create ReviewOpportunitiesFilter
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        // 2. Set filter fields
        filter.setContestName("test");
        List<String> list = new ArrayList<String>();
        list.add("Component Design");
        list.add("System Assembly");
        list.add("Conceptualization");
        filter.setSubtype(list);
        list = new ArrayList<String>();
        list.add("Component Design");
        list.add("Conceptualization");
        filter.setType(list);
        list = new ArrayList<String>();
        list.add("Design");
        list.add("UI Development");
        filter.setCatalog(list);
        DateIntervalSpecification date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE);
        date.setFirstDate(new Date());
        filter.setRegistrationStart(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setSubmissionEnd(date);
        date = new DateIntervalSpecification();
        date.setIntervalType(DateIntervalType.BEFORE_CURRENT_DATE);
        filter.setReviewEndDate(date);
        filter.setPaymentStart(400);
        filter.setPaymentEnd(1000);
        // 3. Create ReviewOpportunitiesManager instance using Spring
        ReviewOpportunitiesManager manager = (ReviewOpportunitiesManager) applicationContext
                .getBean("reviewOpportunitiesManager");
        // 4. Retrieve review opportunities using manager
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        // 5. You may verify that the DTOs contain correct information
        assertEquals("1 projects should be returned", 1, result.size());
        assertEquals("contest name should be returned", "test component 2", result.get(0).getContestName());
        assertEquals("type should be returned", "Conceptualization", result.get(0).getType());
        assertEquals("subtype should be returned", "Conceptualization", result.get(0).getSubtype());
        assertEquals("primary reviewer payment should be returned", 500.0, result.get(0).getPrimaryReviewerPayment());
        assertEquals("secondary reviewer payment should be returned", 300.0, result.get(0)
                .getSecondaryReviewerPayment());
        assertEquals("submission number should be returned", 2, result.get(0).getSubmissionsNumber());
        assertEquals("opens on should be returned", "2011-05-24 18:00:00.0", result.get(0).getOpensOn().toString());
        assertEquals("review start should be returned", "2011-05-24 18:00:00.0", result.get(0).getReviewStart().toString());
        assertEquals("review end should be returned", "2011-05-25 18:00:00.0", result.get(0).getReviewEnd().toString());
        assertEquals("number of available review positions should be returned", 1, result.get(0)
                .getNumberOfReviewPositionsAvailable());
    }

    /**
     * <p>
     * Demonstrates the API use of SearchContestsManager.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoApi_SearchContestsManager() throws Exception {
        // 1. Create ContestsFilter
        ContestsFilter filter = new ContestsFilter();
        // 2. Set filter fields
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
        // 3. Create SearchContestsManager instance using Spring
        SearchContestsManager manager = (SearchContestsManager) applicationContext.getBean("searchContestsManager");
        // 4. Retrieve contests using manager
        List<ContestDTO> result = manager.searchContests(filter);
        // 5. You may verify that the DTOs contain correct information
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
     * Demonstrates the API use of UpcomingContestsManager.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testDemoApi_UpcomingContestsManager() throws Exception {
        // 1. Create UpcomingContestsFilter
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        // 2. Set filter fields
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
        // 3. Create UpcomingContestsManager instance using Spring
        UpcomingContestsManager manager = (UpcomingContestsManager) applicationContext
                .getBean("upcomingContestsManager");
        // 4. Retrieve upcoming contests using manager
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(filter);
        // 5. You may verify that the DTOs contain correct information
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
     * Tests the component on a web server.
     * </p>
     */
    @Test
    public void testDemoWeb() {
        // please follow the instruction on HOW_TO_TEST_UNDER_WEB_SERVER.TXT to test the component on a real web server
    }
}
