
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Project Workers.  It offers a convenient way of specifying search criteria to use.  The
 * factory is capable of producing filters that conform to a specific schema, and is associated with
 * the ProjectWorkerUtility that supports the given schema.
 * </p>
 * <p>
 * The filters that are produced by this factory should only be used by the ProjectWorkerUtility implementation
 * that produced this ProjectWorkerFilterFactory instance.  This ensures that the Filters can be recognized by
 * the utility.
 * </p>
 * <p>
 * It may be possible to create complex criterion by combining the filters produced by this 
 * factory with any of the Composite Filters in the Search Builder Component (AndFilter, OrFilter,
 * etc.)
 * </p>
 * <p>
 * Note that all ranges specified are inclusive of the boundaries.
 * </p>
 * <p>
 * Thread Safety: This class is required to be thread-safe.
 * </p>
 * 
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm74d4]
 */
public interface ProjectWorkerFilterFactory extends com.topcoder.timetracker.project.BaseFilterFactory {
/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the id of the TIme Tracker project they 
 * are working on.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm749c]
 * @param projectId the id of the TIme Tracker project.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createProjectIdFilter(long projectId);
/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the start date of the worker.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm7484]
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createStartDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the end date of the worker.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm746c]
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createEndDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
/**
 * <p>
 * Creates a filter that will select ProjectWorkers based on the end date of the worker.
 * </p>
 * 
 * @poseidon-object-id [Im73f44d97m110bc60be12mm732b]
 * @param rangeStart The lower bound of the int criterion.
 * @param rangeEnd The upper bound of the int criterion.
 * @return A filter that will be based off the specified criteria.
 * @throws IllegalArgumentException if the range is invalid (eg. rangeStart is > rangeEnd)
 */
    public com.topcoder.search.builder.filter.Filter createPayRateFilter(int rangeStart, int rangeEnd);
}


