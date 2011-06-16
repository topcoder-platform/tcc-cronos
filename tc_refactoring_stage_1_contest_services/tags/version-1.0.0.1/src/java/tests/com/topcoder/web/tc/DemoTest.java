/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import java.util.List;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.topcoder.web.tc.dto.ActiveContestDTO;
import com.topcoder.web.tc.dto.ActiveContestFilter;
import com.topcoder.web.tc.dto.Filterable;

/**
 * <p>
 * Demo tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DemoTest {
    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DemoTest.class);
    }

    /**
     * <p>
     * The Demo API.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    @SuppressWarnings("unused")
    public void testDemoAPI() throws Exception {
        // Load the Spring context file
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "applicationContext.xml");

        // Get the ActiveContestsManager from Spring
        ActiveContestsManager manager = (ActiveContestsManager) applicationContext
                        .getBean("activeContestsManagerBean");

        // Create a new ActiveContestFilter
        ActiveContestFilter filter = new ActiveContestFilter();

        // The filtered contests' name should start with "TC Refactoring"
        filter.setContestName("TC Refactoring");
        // The lower bound of the prize is -infinite
        filter.setPrizeLowerBound(Filterable.OPEN_INTERVAL);
        // The upper bound of the prize is 1000
        filter.setPrizeUpperBound(1000);

        // This call retrieves all active contests whose name starts with "TC Refactoring" and prize <= 1000
        // sorted in ascending order by "contestName" property. The page size is 10 and only the 2nd page
        // should be returned.
        List<ActiveContestDTO> activeContests = manager.retrieveActiveContests("projectGroupCategory.name",
            SortingOrder.ASCENDING, 2, 10, filter);

        // Get the CategoriesManager from Spring
        CategoriesManager categoriesManager = (CategoriesManager) applicationContext
                        .getBean("categoriesManagerBean");

        // Get all catalogs
        List<String> catalogs = categoriesManager.getCatalogs();

        // Get all types
        List<String> types = categoriesManager.getContestTypes();

        // Get all types of catalog 'application'
        types = categoriesManager.getContestTypes("application");

        // Get all sub-types of type 'architecture'
        List<String> subTypes = categoriesManager.getContestSubTypes("architecture");

    }
}
