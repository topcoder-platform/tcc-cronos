/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.stresstests;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.web.tc.SearchContestsManager;
import com.topcoder.web.tc.dto.ContestDTO;
import com.topcoder.web.tc.dto.ContestsFilter;

/**
 * Stress test for class SearchContestsManagerImpl.
 * @author extra
 * @version 1.0
 */
public class SearchContestsManagerImplStressTests extends AbstractStressTests {

    /**
     * The manager.
     */
    private SearchContestsManager manager;

    /**
     * <p>
     * Sets up testing environment. Initializes variable.
     * </p>
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        manager = (SearchContestsManager) applicationContext.getBean("searchContestsManager");
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
     * Stress test for method searchContests(ContestsFilter filter).
     *
     * @throws Exception
     * O            to JUnit
     */
    @Test
    public void testSearchContests_stress() throws Exception {
        ContestsFilter filter = new ContestsFilter();
        List<ContestDTO> result = manager.searchContests(filter);
        Assert.assertTrue("retrived result size", result.size() > 0);
    }
   
}
