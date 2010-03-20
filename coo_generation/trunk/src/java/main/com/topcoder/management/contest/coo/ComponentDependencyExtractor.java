/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * This interface represents component dependency extractor. It consists of
 * single method to extract dependencies from the given build file (represented
 * as input stream).
 * </p>
 * <p>
 * It is used by <code>DefaultCOOGenerator</code> to retrieve the dependencies
 * of a contest.
 * </p>
 * <p>
 * <strong> Thread safety:</strong> Implementation is not required to be thread
 * safe.
 * </p>
 *
 * @author bramandia, TCSDEVELOPER
 * @version 1.1
 */
public interface ComponentDependencyExtractor {
    /**
     * <p>
     * Extract the dependencies from the given build file (represented as input
     * stream).
     * </p>
     *
     * @param input The input stream of the build file. must not be null.
     * @return list of dependencies extracted from the build file.
     * @throws IllegalArgumentException if any argument is invalid.
     * @throws ComponentDependencyExtractorException if there is error in
     *             performing this method.
     */
    public List<ComponentDependency> extractDependencies(InputStream input)
        throws ComponentDependencyExtractorException;
}
