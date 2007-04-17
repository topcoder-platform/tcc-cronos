/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time;

import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * TaskTypes.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use.
 * </p>
 *
 * <p>
 * The factory is capable of producing filters that conform to a specific schema, and is associated with
 * the <code>TaskTypeManager</code> that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>TaskTypeManager</code> or
 * <code>TaskTypeDAO</code> implementation that produced this <code>TaskTypeFilterFactory</code> instance.
 * This ensures that the Filters can be recognized.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any
 * of the Composite Filters in the Search Builder Component (<code>AndFilter</code>, <code>OrFilter</code>,
 * etc.)
 * </p>
 *
 * <p>
 * Thread Safety: Implementations of this interface are required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public interface TaskTypeFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * This creates a <code>Filter</code> that will select TaskTypes based on the TaskType's
     * company id.
     * </p>
     *
     * @param companyId The id of the company.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if id is &lt; 0.
     */
    public Filter createCompanyIdFilter(long companyId);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TaskTypes based on the TaskType's
     * description.
     * </p>
     *
     * <p>
     * It can support substring searches based on the given <code>matchType</code>.
     * For {@link StringMatchType#EXACT_MATCH}, it is used search for string that matches the provided
     * value exactly.
     * For {@link StringMatchType#STARTS_WITH}, it is used to search for string that starts with the
     * provided value.
     * For {@link StringMatchType#ENDS_WITH}, it is used to search for string that ends with the
     * provided value.
     * For {@link StringMatchType#SUBSTRING}, it is used to search for strings that contains the
     * provided value.
     * </p>
     *
     * @param description The description of the task type.
     * @param matchType The string matching type to use.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or matchingType is null.
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchType);

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TaskTypes based on the TaskType's
     * active flag.
     * </p>
     *
     * @param isActive Whether the task type is active or not.
     * @return A filter that will be based off the specified criteria.
     */
    public Filter createActiveFilter(boolean isActive);
}
