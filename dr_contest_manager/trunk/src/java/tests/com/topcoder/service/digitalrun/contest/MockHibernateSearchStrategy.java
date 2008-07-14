/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.digitalrun.contest;

import com.topcoder.search.builder.PersistenceOperationException;
import com.topcoder.search.builder.SearchStrategy;
import com.topcoder.search.builder.UnrecognizedFilterException;
import com.topcoder.search.builder.filter.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * The mock Hibernate search strategy.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockHibernateSearchStrategy implements SearchStrategy {

    /**
     * <p>
     * Whether throw error. Default to false.
     * </p>
     */
    private boolean throwErr = false;

    /**
     * <p>
     * Default Constructor.
     * </p>
     */
    public MockHibernateSearchStrategy() {
        // empty
    }

    /**
     * <p>
     * Mock implementation.
     * </p>
     *
     * @param arg0 Useless parameter.
     * @param arg1 Useless parameter.
     * @param arg2 Useless parameter.
     * @param arg3 Useless parameter.
     *
     * @return An empty list.
     *
     * @throws UnrecognizedFilterException never.
     * @throws PersistenceOperationException if <code>throwErr</code> is set to true.
     */
    public Object search(String arg0, Filter arg1, List arg2, Map arg3)
        throws PersistenceOperationException, UnrecognizedFilterException {
        if (throwErr) {
            throw new PersistenceOperationException("mock exception");
        }

        return new ArrayList();
    }

    /**
     * <p>
     * Get the value of <code>throwErr</code> property.
     * </p>
     *
     * @return the value of <code>throwErr</code> property.
     */
    public boolean isThrowErr() {
        return throwErr;
    }

    /**
     * <p>
     * Set the value of <code>throwErr</code> property.
     * </p>
     *
     * @param throwErr the value of <code>throwErr</code> property to set.
     */
    public void setThrowErr(boolean throwErr) {
        this.throwErr = throwErr;
    }
}
