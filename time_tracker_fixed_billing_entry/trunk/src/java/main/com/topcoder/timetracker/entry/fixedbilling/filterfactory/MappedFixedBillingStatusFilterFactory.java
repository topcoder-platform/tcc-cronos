/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.fixedbilling.filterfactory;

import com.topcoder.search.builder.filter.Filter;

import com.topcoder.timetracker.entry.fixedbilling.FixedBillingStatusFilterFactory;
import com.topcoder.timetracker.entry.fixedbilling.Helper;
import com.topcoder.timetracker.entry.fixedbilling.StringMatchType;


/**
 * <p>
 * This is an implementation of the <code>FixedBillingStatusFilterFactory</code> that may be used to build filters.
 * </p>
 *
 * <p>
 * Thread Safety: This is thread-safe, since it is not modified after construction.
 * </p>
 *
 * @author ShindouHikaru, flytoj2ee
 * @version 1.0
 */
public class MappedFixedBillingStatusFilterFactory extends MappedBaseFilterFactory
    implements FixedBillingStatusFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the Description.
     * </p>
     */
    public static final String DESCRIPTION_COLUMN_NAME = "DESCRIPTION_COLUMN_NAME";

    /**
     * <p>
     * Creates a MappedFixedBillingStatusFilterFactory.
     * </p>
     *
     */
    public MappedFixedBillingStatusFilterFactory() {
    }

    /**
     * <p>
     * This creates a Filter that will select FixedBillingStatuses based on the Status' description.
     * </p>
     *
     * @param description The description of the status.
     * @param matchType The String matching that is desired on the search.
     *
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String or matchType is null.
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchType) {
        return Helper.createFilterByMatchType(DESCRIPTION_COLUMN_NAME, description,
            matchType);
    }
}
