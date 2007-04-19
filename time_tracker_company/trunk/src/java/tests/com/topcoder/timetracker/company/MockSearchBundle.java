/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.company;

import java.util.HashMap;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.Filter;


/**
 * <p>
 * Defines a mock class which implements the <code>SearchBundle</code> interface for testing.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
public class MockSearchBundle extends SearchBundle {
    /** The SearchBundle instance. */
    private SearchBundle bundle = null;

    /**
     * Creates a new MockSearchBundle object.
     */
    public MockSearchBundle() {
        super("name", new HashMap(), "context");

        try {
            bundle = UnitTestHelper.createSearchBundle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * Execut the search with given constructed Filter,both simple and AssociativeFilter will do. The result will be
     * returned as an object instanceof CustomResultSet for the DataBase search. The result will be returned as an
     * abject instanceof Iterator of the Entry for the LDAP.
     * </p>
     *
     * @param filter The Filter object used to conduct the search
     *
     * @return an Object holding the search result
     *
     * @throws IllegalArgumentException if any parameter is null
     * @throws SearchBuilderException if the filter is invalid
     */
    public Object search(Filter filter) throws SearchBuilderException {
        return bundle.search(filter);
    }
}
