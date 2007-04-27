/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.db;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.entry.time.TimeStatusFilterFactory;
import com.topcoder.timetracker.entry.time.StringMatchType;

/**
 * <p>
 * This class implements the interface <code>TimeStatusFilterFactory</code>.
 * </p>
 *
 * <p>
 * It defines a factory that is capable of creating search filters used for searching through
 * TimeStatuses.
 * </p>
 *
 * <p>
 * It offers a convenient way of specifying search criteria to use.
 * The factory is capable of producing filters that conform to a specific schema, and is associated with
 * the <code>TimeStatusManager</code> that supports the given schema.
 * </p>
 *
 * <p>
 * The filters that are produced by this factory should only be used by the <code>TimeStatusManager</code> or
 * <code>TimeStatusDAO</code> implementation that produced this <code>TimeStatusFilterFactory</code> instance.
 * This ensures that the Filters can be recognized.
 * </p>
 *
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this factory with any of
 * the Composite Filters in the Search Builder Component (<code>AndFilter</code>, <code>OrFilter</code>, etc.)
 * </p>
 *
 * <p>
 * Thread Safety: This class is immutable and so thread safe.
 * </p>
 *
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 3.2
 */
public class DbTimeStatusFilterFactory extends DbBaseFilterFactory implements TimeStatusFilterFactory {
    /**
     * <p>
     * This is the map key to use to specify the column name for the description.
     * </p>
     */
    public static final String DESCRIPTION_COLUMN_NAME = "DESCRIPTION";

    /**
     * <p>
     * Creates a <code>DbBaseFilterFactory</code>.
     * </p>
     */
    public DbTimeStatusFilterFactory() {
        // empty
    }

    /**
     * <p>
     * This creates a <code>Filter</code> that will select TimeStatuses based on the Status' description.
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
     * @param description The description of the status.
     * @param matchType The string matching type to use.
     * @return A filter that will be based off the specified criteria.
     *
     * @throws IllegalArgumentException if the String is null or an empty String, or matchingType is null.
     */
    public Filter createDescriptionFilter(String description, StringMatchType matchType) {
        Util.checkString(description, "description");
        Util.checkNull(matchType, "matchType");

        return Util.createFilter(matchType, DESCRIPTION_COLUMN_NAME, description);
    }
}
