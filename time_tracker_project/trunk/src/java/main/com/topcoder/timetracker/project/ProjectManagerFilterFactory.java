/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Project Managers.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use.
 * The factory is capable of producing filters that conform to a specific schema, and is associated with
 * the <code>ProjectManagerUtility</code> that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>ProjectManagerUtility</code>
 * implementation that produced this <code>ProjectManagerFilterFactory</code> instance.
 * </p>
 *
 * <p>
 * This ensures that the Filters can be recognized by the utility.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this
 * factory with any of the Composite Filters in the Search Builder Component (<code>AndFilter</code>,
 * <code>OrFilter</code>, etc.)
 * </p>
 *
 * <p>
 * Note that all ranges specified are inclusive of the boundaries.
 * </p>
 *
 * <p>
 * Thread Safety: Implementations of this interface are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, bendlund, TCSDEVELOPER
 * @version 3.2
 */
public interface ProjectManagerFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * Creates a filter that will select ProjectManagers based on the id of the TIme Tracker project they
     * are managing.
     * </p>
     *
     * @return A filter that will be based off the specified criteria.
     * @param projectId The id of the project which the manager is associated with.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createProjectIdFilter(long projectId);

    /**
     * <p>
     * Creates a filter that will select ProjectManagers based on the id of the TIme Tracker user they
     * are managing.
     * </p>
     *
     * @return A filter that will be based off the specified criteria.
     * @param userId The id of the user which the manager is associated with.
     *
     * @throws IllegalArgumentException if the id &lt;= 0.
     */
    public Filter createUserIdFilter(long userId);
}
