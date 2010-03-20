/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

/**
 * <p>
 * This interface represents report generator. It has single method to generate
 * COO report based on the given project id.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> Implementation is not required to be thread
 * safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public interface COOReportGenerator {
    /**
     * Generate COO Report based on the given project id.
     *
     * @param projectId The project id. must be positive.
     * @return The generated report
     * @throws IllegalArgumentException if any argument is invalid
     * @throws COOReportGeneratorException if there is error in executing this
     *             method
     */
    public COOReport generateCOOReport(long projectId)
        throws COOReportGeneratorException;
}
