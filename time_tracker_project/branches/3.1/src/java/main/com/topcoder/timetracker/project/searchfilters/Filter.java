/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.project.searchfilters;

/**
 * <p>
 * A search filter is used to specify what entities to search. In this component it is used for specifying what
 * projects or clients to search. Note, that the filter itself acts just like a data structure specifying the
 * parameters of search, it doesn't contain any search logic.
 * </p>
 *
 * <p>
 * The implementations of this interface are required to be immutable, i.e. thread-safe. This interface is defined to
 * provide a degree of type safety.
 * </p>
 *
 * @author real_vg, TCSDEVELOPER
 * @version 1.1
 */
public interface Filter {
}
