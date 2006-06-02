/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.timetracker.entry.time.search;

import com.topcoder.util.collection.typesafeenum.Enum;

/**
 * <p>
 * This is the abstract class for Criteria. All criteria of entity should
 * extends from this class. It is used in ComparisonExpression, SubstringExpression,
 * RangeExpression to construct the search expression.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 2.0
 * @since 2.0
 */
public abstract class Criteria extends Enum {
    /**
     * <p>
     * Gets the name of this criterion.
     * </p>
     *
     * @return Name of this criterion.
     */
    abstract String getName();
}
