/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.impl;

import com.topcoder.management.contest.coo.ContestData;
import com.topcoder.management.contest.coo.ContestDataRetriever;

/**
 *
 * <p>
 * mock class for <code>ContestDataRetriever</code> used for test.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockContestDataRetriever implements ContestDataRetriever {

    /**
     * the contest type to set.
     */
    private String type;

    /**
     * constructor with given contest type.
     *
     * @param type the contest type.
     */
    public MockContestDataRetriever(String type) {
        this.type = type;
    }

    /**
     * generate contest data with given type used for testing.
     *
     * @param projectId the project id.
     * @return the contest data generated.
     */
    public ContestData retrieveContestData(long projectId) {
        return TestHelper.getContestData(type);
    }

}
