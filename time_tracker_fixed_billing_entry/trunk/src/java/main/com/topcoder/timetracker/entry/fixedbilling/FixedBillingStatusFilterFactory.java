/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling;

import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * FixedBillingStatuses. It offers a convenient way of specifying search criteria to use. The factory is capable of
 * producing filters that conform to a specific schema, and is associated with the
 * <code>FixedBillingStatusManager</code> that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>FixedBillingStatusManager</code> or
 * <code>FixedBillingStatusDAO</code> implementation that produced this <code>FixedBillingStatusFilterFactory</code>
 * instance. This ensures that the Filters can be recognized by the utility.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any of the
 * Composite Filters in the Search Builder Component (AndFilter, OrFilter, etc.)
 * </p>
 *
 * <p>
 * Thread Safety: This class is required to be thread-safe.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public interface FixedBillingStatusFilterFactory extends BaseFilterFactory {
    /**
     * <p>
     * This creates a Filter that will select FixedBillingStatuses based on the Status' description.
     * </p>
     *
     * @param description The description of the status.
     * @param matchType The String match type that is desired on the search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String or matchType is null.
     */
    Filter createDescriptionFilter(String description, StringMatchType matchType);
}
