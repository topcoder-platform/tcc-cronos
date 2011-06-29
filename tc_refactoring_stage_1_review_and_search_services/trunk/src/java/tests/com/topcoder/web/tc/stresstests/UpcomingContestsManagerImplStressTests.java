/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.stresstests;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.UpcomingContestsManager;
import com.topcoder.web.tc.dto.UpcomingContestDTO;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;

/**
 * Stress test for class UpcomingContestsManagerImpl.
 *
 * @author extra
 * @version 1.0
 */
public class UpcomingContestsManagerImplStressTests extends AbstractStressTests {

    /**
     * The manager.
     */
    private UpcomingContestsManager manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        manager = (UpcomingContestsManager) applicationContext.getBean("upcomingContestsManager");
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
     * Stress test for method retrieveUpcomingContests(UpcomingContestsFilter filter).
     *
     * @throws Exception
     *             O to JUnit
     */
    @Test
    public void testSearchContests_stress() throws Exception {
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        List<UpcomingContestDTO> result = manager.retrieveUpcomingContests(filter);
        Assert.assertTrue("retrived result size", result.size() > 0);
    }

}
