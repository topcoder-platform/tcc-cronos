
package com.topcoder.timetracker.project;
import com.topcoder.search.builder.filter.Filter;
/**
 * <p>
 * This interface defines a factory that is capable of creating search filters used for searching through
 * Project Managers.  It offers a convenient way of specifying search criteria to use.  The
 * factory is capable of producing filters that conform to a specific schema, and is associated with
 * the ProjectManagerUtility that supports the given schema.
 * </p>
 * <p>
 * The filters that are produced by this factory should only be used by the ProjectManagerUtility implementation
 * that produced this ProjectManagerFilterFactory instance.  This ensures that the Filters can be recognized by
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
 * @poseidon-object-id [I7d5b1a38m110c4518e28mmc5]
 */
public interface ProjectManagerFilterFactory extends com.topcoder.timetracker.project.BaseFilterFactory {
/**
 * <p>
 * Creates a filter that will select ProjectManagers based on the start date of the manager.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mmbb]
 * @return A filter that will be based off the specified criteria.
 * @param rangeStart The lower bound of the date criterion.
 * @param rangeEnd The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createStartDateFilter(java.util.Date rangeStart, java.util.Date rangeEnd);
/**
 * <p>
 * Creates a filter that will select ProjectManagers based on the end date of the manager.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mmaf]
 * @return A filter that will be based off the specified criteria.
 * @param startDate The lower bound of the date criterion.
 * @param endDate The upper bound of the date criterion.
 * @throws IllegalArgumentException the range specified is invalid (eg. rangeStart > rangeEnd), or if both arguments are null.
 */
    public com.topcoder.search.builder.filter.Filter createEndDateFilter(java.util.Date startDate, java.util.Date endDate);
/**
 * <p>
 * Creates a filter that will select ProjectManagers based on the id of the TIme Tracker project they
 * are managing.
 * </p>
 * 
 * 
 * @poseidon-object-id [I7d5b1a38m110c4518e28mma4]
 * @return A filter that will be based off the specified criteria.
 * @param projectId The id of the project which the manager is associated with.
 * @throws IllegalArgumentException if the id <= 0.
 */
    public com.topcoder.search.builder.filter.Filter createProjectIdFilter(long projectId);
}


