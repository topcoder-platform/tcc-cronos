/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * This interface represents contest data retriever. It consists of single
 * method to retrieve contest data of the given project id.
 * </p>
 * <p>
 * It is used by <code>DefaultCOOGenerator</code> to retrieve the contest
 * data.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> Implementation is not required to be thread
 * safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public interface ContestDataRetriever {
    /**
     * Retrieve contest data of the given project id.
     *
     * @param projectId The project id. must be positive.
     * @return The contest data of the given project.
     * @throws IllegalArgumentException if any argument is invalid
     * @throws ContestDataRetrieverException if there is any error in performing
     *             this method
     */
    public ContestData retrieveContestData(long projectId)
        throws ContestDataRetrieverException;
}
