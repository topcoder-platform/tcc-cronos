/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.entry.time.failuretests;

import com.topcoder.timetracker.entry.time.search.SearchExpression;

/**
 * <p>
 * Mock SearchExpression for failure tests.
 * </p>
 *
 * @author GavinWang
 * @version 1.1
 */
class MockSearchExpression implements SearchExpression {
    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.search.SearchExpression#getOperator()
     */
    public String getOperator() {
        return null;
    }

    /**
     * <p>
     * .
     * </p>
     * @return
     * @see com.topcoder.timetracker.entry.time.search.SearchExpression#getSearchExpressionString()
     */
    public String getSearchExpressionString() {
        return null;
    }

}
