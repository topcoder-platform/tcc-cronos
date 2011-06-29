/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.stresstests;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.ReviewOpportunitiesManager;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilter;
import com.topcoder.web.tc.dto.ReviewOpportunityDTO;

/**
 * Stress test for class ReviewOpportunitiesManagerImpl.
 * @author extra
 * @version 1.0
 */
public class ReviewOpportunitiesManagerImplStressTests extends AbstractStressTests {

    /**
     * The manager.
     */
    private ReviewOpportunitiesManager manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        manager = (ReviewOpportunitiesManager) applicationContext.getBean("reviewOpportunitiesManager");
    }

    /**
     * <p>
     * Tears down testing environment.
     * </p>
     */
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Stress test for method retrieveReviewOpportunities(ReviewOpportunitiesFilter filter).
     *
     * @throws Exception
     * O            to JUnit
     */
    @Test
    public void testRetrieveReviewOpportunities_stress() throws Exception {
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        List<ReviewOpportunityDTO> result = manager.retrieveReviewOpportunities(filter);
        Assert.assertTrue("retrived result size", result.size() > 0);
    }
   
}
